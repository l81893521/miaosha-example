package online.babylove.www.dao;

import org.apache.ibatis.annotations.Param;

import online.babylove.www.entity.SuccessKilled;

/**
 * @author Zhang will
 *
 */
public interface SuccessKilledDao {

	/**
	 * ���빺����ϸ,�ɹ����ظ�
	 * @param seckillId
	 * @param userPhone
	 * @return ���������
	 */
	int insertSuccessKilled(@Param("seckillId")long seckillId, @Param("userPhone")long userPhone);
	
	/**
	 * ����id��ѯSuccessKilled��Я����ɱ��Ʒ����
	 * @param seckillId
	 * @return
	 */
	SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId, @Param("userPhone")long userPhone);
}
