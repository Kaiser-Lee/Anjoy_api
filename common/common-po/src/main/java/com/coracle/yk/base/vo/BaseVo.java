package com.coracle.yk.base.vo;

import java.io.Serializable;

/**
 * 只包含id和name的基础vo
 * @author sunyu_000
 *
 */
public class BaseVo implements Serializable {

	private static final long serialVersionUID = -5610093525262683796L;
	
	private Integer id;
	private String name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
