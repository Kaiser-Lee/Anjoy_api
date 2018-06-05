package com.coracle.yk.base.param;

/**
 * 基础搜索条件类
 * @author sunyu_000
 *
 */
public class BaseParam extends PageParam {

	private static final long serialVersionUID = -1408019772079413548L;
	
	private String kw;

	public String getKw() {
		return kw;
	}

	public void setKw(String kw) {
		this.kw = kw;
	}
	
}
