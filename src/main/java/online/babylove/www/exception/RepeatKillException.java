package online.babylove.www.exception;

/** 
 * �ظ���ɱ�쳣���������쳣��
 * @author  zhang will 
 * @date created by��2016-10-19 09:05
 */
public class RepeatKillException extends SeckillException {
	
	private static final long serialVersionUID = 1050352234385512006L;

	public RepeatKillException(String message) {
		super(message);
	}

	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
	}
}
