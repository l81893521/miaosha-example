package online.babylove.www.exception;

/** 
 * ��ɱ�ر��쳣(����û��棬ʱ����ڣ�����ʱ�쳣)
 * @author  zhang will 
 * @date created by��2016-10-19 09:07
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
