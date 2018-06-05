package com.coracle.dms.enums;

/**
 * 字段类型
 * @author sunyu_000
 *
 */
public enum UserStateEnum {
	invalid (0, "无效"),
	effective (1, "有效"),
	otAudit (2, "未审核"),
	auditNoPass(3, "审核未通过");


    private Integer state;
    private String desc;
	private UserStateEnum(Integer state, String desc) {
		this.state = state;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public static String getDesc(Integer state) {
    	if (state == null) {
    		return null;
    	}
    	for (UserStateEnum e : UserStateEnum.values()) {
    		if (e.state.equals(state)) {
    			return e.getDesc();
    		}
    	}
    	
    	return null;
    }
    

    
}
