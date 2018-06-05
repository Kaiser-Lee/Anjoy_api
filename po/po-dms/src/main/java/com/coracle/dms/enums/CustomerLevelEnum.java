package com.coracle.dms.enums;

import java.math.BigDecimal;

/**
 * 字段类型
 * @author sunyu_000
 *
 */
public enum CustomerLevelEnum {
	COMM_LEVEL(0l, "普通会员", new BigDecimal(0d)),
	FIRST_LEVEL(1l, "一级会员",new BigDecimal(10000d) ),
	SECOND_LEVEL(2l, "二级会员", new BigDecimal(50000d)),
	THREE_LEVEL(3l, "三级会员",  new BigDecimal(100000d)),
	FOUR_LEVEL(4l, "四级会员", new BigDecimal(200000d)),
	FIVE_LEVEL(5l, "五级会员", new BigDecimal(500000d));

    private Long type;
    private String desc;
	private BigDecimal limit;
	private CustomerLevelEnum(Long type, String desc, BigDecimal limit) {
		this.type = type;
		this.desc = desc;
		this.limit = limit;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public BigDecimal getLimit() {
		return limit;
	}

	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}


    

    
    public String getDesc(Long type) {
    	if (type == null) {
    		return null;
    	}
    	for (CustomerLevelEnum e : CustomerLevelEnum.values()) {
    		if (e.type.equals(type)) {
    			return e.getDesc();
    		}
    	}
    	
    	return null;
    }
    
    public BigDecimal getLimit(Long type) {
    	if (type == null) {
    		return null;
    	}
    	for (CustomerLevelEnum e : CustomerLevelEnum.values()) {
    		if (e.getType().equals(type)) {
    			return e.getLimit();
    		}
    	}
    	
    	return null;
    }
    
}
