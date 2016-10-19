package online.babylove.www.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import online.babylove.www.entity.SuccessKilled;
/**
 * 配置Spring和Junit整合,junit启动时加载spring ioc容器
 * 并且告诉junit spring配置文件路径
 * @author Zhang will
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
	
	@Autowired
	private SuccessKilledDao successKilledDao;
	
	@Test
	public void testInsertSuccessKilled() throws Exception {
		long id = 1000l;
		long userPhone = 13888888888l;
		int insert = successKilledDao.insertSuccessKilled(id, userPhone);
		/*
		 * 无法插入重复数据，数据库设置了seckillId和userPhone为复合主键
		 * 第二次调用不会报错只会返回0，因为sql增加了ignore
		 */
		Assert.assertEquals(1, insert);
	}
	
	@Test
	public void testqueryByIdWithSeckill() throws Exception {
		long id = 1000l;
		long userPhone = 13888888888l;
		SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, userPhone);
		System.out.println(successKilled);
		System.out.println(successKilled.getSeckill());
	}
}
