package online.babylove.www.dao;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import online.babylove.www.entity.Seckill;

/**
 * ����Spring��Junit����,junit����ʱ����spring ioc����
 * ���Ҹ���junit spring�����ļ�·��
 * @author Zhang will
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
	
	//ע��Daoʵ����
	@Autowired
	private SeckillDao seckillDao;
	
	@Test
	public void testReduceNumber() throws Exception {
		Date killTime = new Date();
		int updateCount = seckillDao.reduceNumber(1000, killTime);
		System.out.println(updateCount);
		//������в�ͨ����������ɱʱ�䲻���ϣ���Ҫ�޸����ݿ����ɱʱ��
		//Assert.assertEquals(1, updateCount);
	}
	
	@Test
	public void testQueryById() throws Exception {
		long id = 1000;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill);
	}
	
	@Test
	public void testQueryAll() throws Exception {
		List<Seckill> seckills = seckillDao.queryAll(0, 100);
		for (Seckill seckill : seckills) {
			System.out.println(seckill);
		}
	}
}
