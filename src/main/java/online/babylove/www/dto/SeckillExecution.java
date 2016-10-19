package online.babylove.www.dto;

import online.babylove.www.entity.SuccessKilled;
import online.babylove.www.enums.SeckillStateEnum;

/** 
 * ��װ��ɱִ�к���
 * @author  zhang will 
 * @date created by��2016-10-19 09:01
 */
public class SeckillExecution {
	
	//��ɱid
	private long seckillId;
	
	//��ɱִ�н��״̬
	private int state;
	
	//��ɱִ�н��״̬����
	private String stateInfo;
	
	//��ɱ�ɹ�����
	private SuccessKilled successKilled;
	
	public SeckillExecution(long seckillId, SeckillStateEnum stateEnum) {
		super();
		this.seckillId = seckillId;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	public SeckillExecution(long seckillId, SeckillStateEnum stateEnum, SuccessKilled successKilled) {
		super();
		this.seckillId = seckillId;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.successKilled = successKilled;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}

	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	}

	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", state=" + state + ", stateInfo=" + stateInfo
				+ ", successKilled=" + successKilled + "]";
	}
}
