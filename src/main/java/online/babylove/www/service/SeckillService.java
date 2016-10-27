package online.babylove.www.service;

import java.util.List;
import online.babylove.www.dto.Exposer;
import online.babylove.www.dto.SeckillExecution;
import online.babylove.www.entity.Seckill;
import online.babylove.www.exception.RepeatKillException;
import online.babylove.www.exception.SeckillException;

/**
 * ҵ��ӿ�
 * @author Zhang will
 *
 */
public interface SeckillService {
	
	/**
	 * ��ѯ������ɱ
	 * @return
	 */
	List<Seckill> getSeckills();
	
	/**
	 * ��ѯ������ɱ
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	
	/**
	 * ��ɱ����ʱ�����ɱ�ӿڵ�ַ���������ϵͳʱ�����ɱʱ��
	 * Ԥ���û�Ԥ���ͨ������ƴ�ӳ���ɱ��ַ����ɱ��ʼǰ��˭����֪����ɱ��ַ
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * ִ����ɱ
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
		throws SeckillException, RepeatKillException, SeckillException;
}
