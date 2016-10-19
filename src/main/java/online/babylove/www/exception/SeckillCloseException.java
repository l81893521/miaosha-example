package online.babylove.www.exception;

/** 
 * 秒杀关闭异常(例如没库存，时间过期，运行时异常)
 * @author  zhang will 
 * @date created by：2016-10-19 09:07
 */
public class SeckillCloseException extends SeckillException{

	private static final long serialVersionUID = 6217115874677044706L;
	
	public SeckillCloseException(String message) {
		super(message);
	}

	public SeckillCloseException(String message, Throwable cause) {
		super(message, cause);
	}

}
