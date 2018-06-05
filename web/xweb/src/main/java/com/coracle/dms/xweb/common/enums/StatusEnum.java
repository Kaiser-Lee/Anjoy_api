package com.coracle.dms.xweb.common.enums;

public enum StatusEnum {
	SUCCESS(true),
	ERROR(false);
	
	private boolean status;
	
	StatusEnum(boolean status) {
		this.status = status;
	}
	
	public boolean getStatus() {
		return this.status;
	}
	
}
