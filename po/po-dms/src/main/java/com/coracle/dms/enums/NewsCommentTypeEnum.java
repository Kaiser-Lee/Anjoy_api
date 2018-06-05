package com.coracle.dms.enums;

/**
 * 咨询类型
 *
 */
public enum NewsCommentTypeEnum {
	NEWS_COMMENT(0, "评论"),
    FORWARDING  (1, "转发");

    private int type;
    private String desc;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private NewsCommentTypeEnum(int type, String desc) {
        this.type =type;
        this.desc = desc;
    }

    
    public String getDesc() {
    	return this.desc;
    }

    
    public static String getDesc(int type) {
    	for (NewsCommentTypeEnum e : NewsCommentTypeEnum.values()) {
    		if (e.getType() == type) {
    			return e.getDesc();
    		}
    	}
    	
    	return null;
    }
    
}
