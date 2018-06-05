package com.coracle.dms.enums;

/**
 * 字段类型
 * @author sunyu_000
 *
 */
public enum UserTypeEnum {
	brandProvider(0, "品牌商"),
	serviceProvider(1, "服务商"),
	retailer(2, "零售商"),
	customerService(3, "服务商客服"),
	warehouse(4, "仓管"),
	admin(5, "管理员"),
	bandCustomerService(6, "品牌商客服"),
	salesman(7, "销售员"),
	salesSupport(8, "销售支持");

    private Integer type;
    private String desc;
	private UserTypeEnum(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


    

    
    public static String getDesc(Integer type) {
    	if (type == null) {
    		return null;
    	}
    	for (UserTypeEnum e : UserTypeEnum.values()) {
    		if (e.type.equals(type)) {
    			return e.getDesc();
    		}
    	}
    	
    	return null;
    }
    

    
}
