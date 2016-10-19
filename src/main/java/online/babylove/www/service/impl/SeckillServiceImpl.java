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
 * @date created by��2016-10-19 09:35
 */
@Service
public class SeckillServiceImpl implements SeckillService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillDao seckillDao;
	
	@Autowired
	private SuccessKilledDao successKilledDao;
	
	//md5��ֵ�ַ��������ڻ���md5
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
		//ϵͳ��ǰʱ��
		Date nowTime = new Date();
		if(nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()){
			return new Exposer(false, nowTime.getTime(), startTime.getTime(), endTime.getTime(), seckillId);
		}
		//ת���ض��ַ����Ĺ��̣�������
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}
	
	/**
	 * ʹ��ע��������񷽷����ŵ�
	 * 1.�����ŶӴ��һ��Լ������ȷ��ע���񷽷���̷��
	 * 2.��֤���񷽷���ִ��ʱ�価���̣ܶ���Ҫ�������������������rpc/http������߰��뵽���񷽷��ⲿ)
	 * 3.�������еķ�������Ҫ������ֻ��һ��io��������ֻ������
	 */
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillException {
		if(md5 == null || !md5.equals(getMD5(seckillId))){
			throw new SeckillException("seckill data rewrite");
		}
		//ִ����ɱ�߼�
		Date nowTime = new Date();
		try {
			//�����
			int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
			if (updateCount <= 0) {
				//�����û�гɹ���û�п�������ɱ�Ѿ�����
				throw new SeckillCloseException("seckill is closed");
			} else {
				//��¼������Ϊ
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				if(insertCount <= 0){
					//�ظ���ɱ
					throw new RepeatKillException("seckill repeated");
				}else{
					//��ɱ�ɹ�
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
			//���б������쳣 ת��Ϊ�������쳣
			throw new SeckillException("seckill error:" + e.getMessage());
		}
	}
	
	/**
	 * ����md5ֵ
	 * @param seckillId
	 * @return
	 */
	private String getMD5(long seckillId){
		String base = seckillId+"/"+slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
}
