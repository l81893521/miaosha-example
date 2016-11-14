package online.babylove.www.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import online.babylove.www.dao.cache.RedisDao;
import org.apache.commons.collections.MapUtils;
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

	@Autowired
	private RedisDao redisDao;

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

	public Exposer exportSeckillUrlByRedis(long seckillId) {
		//缓存优化

		//1.访问redis
		Seckill seckill = redisDao.getSeckill(seckillId);
		if(seckill == null){
			//2.访问数据库
			seckill = seckillDao.queryById(seckillId);
			//秒杀不存在
			if(seckill == null){
				return new Exposer(false, seckillId);
			}else{
				redisDao.putSeckill(seckill);
			}
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
	 * 这个方法跟上面的executeSeckill其实要执行的任务是一样
	 * 不同点在于,executeSeckill是先扣库存,再插入购买明细(1.获取行级锁 2.更新库存 3.插入购买明细 4.释放行级锁)
	 * 而这个方法是先插入购买明细,再修改库存(1.插入购买明细 2.获取行级锁 3.更新库存 4.释放行级锁)
	 * 也就是说减少了一倍的行级锁等待时间
	 * 只是通过简单的修改顺序
	 */
	@Transactional
	public SeckillExecution executeSeckillNew(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillException {
		if(md5 == null || !md5.equals(getMD5(seckillId))){
			throw new SeckillException("seckill data rewrite");
		}
		//执行秒杀逻辑
		Date nowTime = new Date();
		try {
			//记录购买行为
			int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
			if(insertCount <= 0){
				//重复秒杀
				throw new RepeatKillException("seckill repeated");
			}else{
				//减库存
				int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
				if (updateCount <= 0) {
					//减库存没有成功，没有库存或者秒杀已经结束
					throw new SeckillCloseException("seckill is closed");
				} else {
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
	 * 秒杀执行的业务还是一样
	 * 但是把代码放到存储过程了
	 * 存储过程优化:
	 * 1.事务行级锁持有时间
	 * 2.不要过渡依赖存储过程
	 * 3.简单的逻辑,可以应用存储过程
	 */
	public SeckillExecution executeSeckillByProcedure(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillException {
		if(md5 == null || !md5.equals(getMD5(seckillId))){
			return new SeckillExecution(seckillId, SeckillStateEnum.DATE_REWRITE);
		}
		//执行秒杀逻辑
		Date nowTime = new Date();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seckillId",seckillId);
		map.put("phone",userPhone);
		map.put("killTime",nowTime);
		map.put("result",null);
		try {
			seckillDao.killByProcedure(map);
			int result = MapUtils.getInteger(map, "result", -2);
			if(result == 1){
				//秒杀成功
				SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
			}else{
				return new SeckillExecution(seckillId, SeckillStateEnum.stateOf(result));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
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
