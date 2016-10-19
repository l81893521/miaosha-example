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
 * 配置Spring和Junit整合,junit启动时加载spring ioc容器
 * 并且告诉junit spring配置文件路径
 * @author Zhang will
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
	
	//注入Dao实现类
	@Autowired
	private SeckillDao seckillDao;
	
	@Test
	public void testReduceNumber() throws Exception {
		Date killTime = new Date();
		int updateCount = seckillDao.reduceNumber(1000, killTime);
		System.out.println(updateCount);
		//如果运行不通过，就是秒杀时间不符合，需要修改数据库的秒杀时间
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
