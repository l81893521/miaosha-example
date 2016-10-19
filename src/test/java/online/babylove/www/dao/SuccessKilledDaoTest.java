package online.babylove.www.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import online.babylove.www.entity.SuccessKilled;
/**
 * ����Spring��Junit����,junit����ʱ����spring ioc����
 * ���Ҹ���junit spring�����ļ�·��
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
		 * �޷������ظ����ݣ����ݿ�������seckillId��userPhoneΪ��������
		 * �ڶ��ε��ò��ᱨ��ֻ�᷵��0����Ϊsql������ignore
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
