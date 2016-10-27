package online.babylove.www.service;


import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import online.babylove.www.dto.Exposer;
import online.babylove.www.dto.SeckillExecution;
import online.babylove.www.entity.Seckill;
import online.babylove.www.exception.RepeatKillException;
import online.babylove.www.exception.SeckillCloseException;

/**
 * @author  zhang will 
 * @date created by：2016-10-19 11:20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"classpath:spring/spring-dao.xml",
		"classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillService seckillService;

	@Test
	public void testGetSeckills() throws Exception {
		List<Seckill> seckills = seckillService.getSeckills();
		logger.info("list={}",seckills);
	}

	@Test
	public void testGetById() throws Exception {
		long seckillId = 1000;
		Seckill seckill = seckillService.getById(seckillId);
		logger.info("seckill={}",seckill);
	}

	@Test
	public void testSeckillLogic() throws Exception {
		long seckillId = 1000;
		//获取秒杀信息
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		logger.info("exposer={}",exposer);
		//如果秒杀已经开始
		if(exposer.isExposed()){
			long userPhone = 13888888887l;
			String md5 = exposer.getMd5();
			try {
				SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
				logger.info("seckillExecution={}",seckillExecution);
			} catch (RepeatKillException rke) {
				logger.error(rke.getMessage());
			} catch (SeckillCloseException sce) {
				logger.error(sce.getMessage());
			}
		}else{
			logger.warn("exposer={}",exposer);
		}
	}
}
