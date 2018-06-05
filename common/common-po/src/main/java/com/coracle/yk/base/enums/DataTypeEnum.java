package com.coracle.yk.base.enums;

/**
 * 字段类型
 * @author sunyu_000
 *
 */
public enum DataTypeEnum  {
	DAN_HANG_WEN_ZI(1, "单行文字", "TextField"),
	DUO_HANG_WEN_ZI(2, "多行文字", "TextArea"),
	SHU_ZI(3, "数字", "Number"),
	RI_QI(4, "日期", "Date"),
	DAN_XUAN(5, "单选", "SingleSelection"),
	DUO_XUAN(6, "多选", "MultipleSelection"),
	YU_YIN(7, "语音", "Voice"),
	DIAN_HUA(8, "电话", "Phone"),
	DUAN_XIN(9, "短信", "Sms"),
	TU_PIAN(10, "图片", "Image");
	 
    private Integer id;
    private String name;
    private String code;
    
    private DataTypeEnum(Integer id, String name, String code) {
    	this.id = id;
    	this.name = name;
        this.code = code;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public String getName() {
    	return this.name;
    }
    
    public String getCode() {
    	return this.code;
    }
    
    public String getName(Integer id) {
    	if (id == null) {
    		return null;
    	}
    	for (DataTypeEnum e : DataTypeEnum.values()) {
    		if (e.getId().equals(id)) {
    			return e.getName();
    		}
    	}
    	
    	return null;
    }
    
    public String getCode(Integer id) {
    	if (id == null) {
    		return null;
    	}
    	for (DataTypeEnum e : DataTypeEnum.values()) {
    		if (e.getId().equals(id)) {
    			return e.getCode();
    		}
    	}
    	
    	return null;
    }
    
}
