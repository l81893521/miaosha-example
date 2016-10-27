package online.babylove.www.service;

import java.util.List;
import online.babylove.www.dto.Exposer;
import online.babylove.www.dto.SeckillExecution;
import online.babylove.www.entity.Seckill;
import online.babylove.www.exception.RepeatKillException;
import online.babylove.www.exception.SeckillException;

/**
 * 业务接口
 * @author Zhang will
 *
 */
public interface SeckillService {
	
	/**
	 * 查询所有秒杀
	 * @return
	 */
	List<Seckill> getSeckills();
	
	/**
	 * 查询单个秒杀
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 秒杀开启时输出秒杀接口地址，否则输出系统时间和秒杀时间
	 * 预防用户预测或通过规则拼接出秒杀地址，秒杀开始前，谁都不知道秒杀地址
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * 执行秒杀
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
		throws SeckillException, RepeatKillException, SeckillException;
}
