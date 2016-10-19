package online.babylove.www.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import online.babylove.www.entity.Seckill;
/**
 * @author Zhang will
 *
 */
public interface SeckillDao {
	
	/**
	 * �����
	 * @param seckillId
	 * @param killTime
	 * @return ���Ӱ������>=1,��ʾ���µļ�¼����
	 */
	int reduceNumber(@Param("seckillId")long seckillId, @Param("killTime")Date killTime);
	
	/**
	 * ����id��ѯ��ɱ����
	 * @param seckillId
	 * @return
	 */
	Seckill queryById(long seckillId);
	
	/**
	 * ����ƫ������ѯ��ɱ��Ʒ�б�
	 * @param offet
	 * @param limit
	 * @return
	 */
	List<Seckill> queryAll(@Param("offset")int offset, @Param("limit")int limit);
}
