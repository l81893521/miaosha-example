package online.babylove.www.exception;

/** 
 * 秒杀相关业务异常
 * @author  zhang will 
 * @date created by：2016-10-19 09:09
 */
public class SeckillException extends RuntimeException{

	private static final long serialVersionUID = 2563150010500756846L;
	
	public SeckillException(String message) {
		super(message);
	}

	public SeckillException(String message, Throwable cause) {
		super(message, cause);
	}

}
