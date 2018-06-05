package com.coracle.dms.enums;

/**
 * 咨询类型
 *
 */
public enum NewsTypeEnum {
	NEWS_COMMENT(0, "新闻资讯"),
    ACTIVITY (1, "活动"),
    PROMOTION (2, "促销"),
    NOTICE(3, "通知"),
    POLICY_SUPPORT (4, "政策与规范"),
    BUSINESS_CASE (5, "行业案例"),
    SUCCESSFUL_CUSTOMER  (6, "成功客户"),
    SELLER (7, "经销商");



    private int type;
    private String desc;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private NewsTypeEnum(int type, String desc) {
        this.type =type;
        this.desc = desc;
    }

    
    public String getDesc() {
    	return this.desc;
    }

    
    public static String getDesc(int type) {
    	for (NewsTypeEnum e : NewsTypeEnum.values()) {
    		if (e.getType() == type) {
    			return e.getDesc();
    		}
    	}
    	
    	return null;
    }
    
}
