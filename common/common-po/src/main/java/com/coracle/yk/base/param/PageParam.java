package com.coracle.yk.base.param;

import java.io.Serializable;

/**
 * 页码搜索param
 * @author sunyu_000
 *
 */
public class PageParam implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer p;
	private Integer s;
	
	public Integer getP() {
		return p;
	}
	public void setP(Integer p) {
		this.p = p;
	}
	public Integer getS() {
		return s;
	}
	public void setS(Integer s) {
		this.s = s;
	}

}
