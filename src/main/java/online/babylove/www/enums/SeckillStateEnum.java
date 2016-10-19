package online.babylove.www.enums;

/** 
 * ��ɱ״̬ö�ٳ���
 * @author  zhang will 
 * @date created by��2016-10-19 10:03
 */
public enum SeckillStateEnum {
	SUCCESS(1,"��ɱ�ɹ�"),
	END(0,"��ɱ����"),
	REPEAT_KILL(-1,"�ظ���ɱ"),
	INNER_ERROR(-2,"ϵͳ�쳣"),
	DATE_REWRITE(-3,"���ݴ۸�");
	
	private int state;
	
	private String stateInfo;

	private SeckillStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}
	
	public static SeckillStateEnum stateOf(int index){
		for (SeckillStateEnum seckillStateEnum : values()) {
			if(seckillStateEnum.getState() == index){
				return seckillStateEnum;
			}
		}
		return null;
	}
}
