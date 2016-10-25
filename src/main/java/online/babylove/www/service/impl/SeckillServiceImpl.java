package online.babylove.www.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import online.babylove.www.dao.SeckillDao;
import online.babylove.www.dao.SuccessKilledDao;
import online.babylove.www.dto.Exposer;
import online.babylove.www.dto.SeckillExecution;
import online.babylove.www.entity.Seckill;
import online.babylove.www.entity.SuccessKilled;
import online.babylove.www.enums.SeckillStateEnum;
import online.babylove.www.exception.RepeatKillException;
import online.babylove.www.exception.SeckillCloseException;
import online.babylove.www.exception.SeckillException;
import online.babylove.www.service.SeckillService;

/**
 * @author  zhang will 
 * @date created by：2016-10-19 09:35
 */
@Service
public class SeckillServiceImpl implements SeckillService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillDao seckillDao;

	@Autowired
	private SuccessKilledDao successKilledDao;

	//md5盐值字符串，用于混淆md5
	private final String slat = "dkelwle*#lkjd3@#kdaELKJSkw*AS8@#";

	public List<Seckill> getSeckills() {
		return seckillDao.queryAll(0, 4);
	}

	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill = seckillDao.queryById(seckillId);
		if(seckill == null){
			return new Exposer(false, seckillId);
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		//系统当前时间
		Date nowTime = new Date();
		if(nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()){
			return new Exposer(false, nowTime.getTime(), startTime.getTime(), endTime.getTime(), seckillId);
		}
		//转发特定字符串的过程，不可逆
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	/**
	 * 使用注解控制事务方法的优点
	 * 1.开发团队达成一致约定，明确标注事务方法编程风格
	 * 2.保证事务方法的执行时间尽可能短，不要穿插其他的网络操作（rpc/http请求或者剥离到事务方法外部)
	 * 3.不是所有的方法都需要事务，如只有一条io操作或者只读操作
	 */
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillException {
		if(md5 == null || !md5.equals(getMD5(seckillId))){
			throw new SeckillException("seckill data rewrite");
		}
		//执行秒杀逻辑
		Date nowTime = new Date();
		try {
			//减库存
			int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
			if (updateCount <= 0) {
				//减库存没有成功，没有库存或者秒杀已经结束
				throw new SeckillCloseException("seckill is closed");
			} else {
				//记录购买行为
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				if(insertCount <= 0){
					//重复秒杀
					throw new RepeatKillException("seckill repeated");
				}else{
					//秒杀成功
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
				}

			}
		} catch (SeckillCloseException sce) {
			throw sce;
		} catch (RepeatKillException rke) {
			throw rke;
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			//所有编译期异常 转化为运行期异常
			throw new SeckillException("seckill error:" + e.getMessage());
		}
	}

	/**
	 * 生成md5值
	 * @param seckillId
	 * @return
	 */
	private String getMD5(long seckillId){
		String base = seckillId+"/"+slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
}
