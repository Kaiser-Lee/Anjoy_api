package com.coracle.yk.xframework.common.exception;

import org.apache.commons.lang.StringUtils;

/**
 * 基本异常类，初始化基本返回类型
 * @author hbd
 * @date 2017年3月3日
 */
public class BaseRuntimeException extends RuntimeException {

	private static final long	serialVersionUID	= -3637172659954598718L;

	private Object					code;

	/**
	 * Creates a new instance of BaseException
	 * 
	 */
	public BaseRuntimeException() {
		super();
	}

	/**
	 * Creates a new instance of BaseException.
	 * 
	 * @param cause
	 */
	public BaseRuntimeException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new instance of BaseException
	 * 
	 * @param code
	 * @param params
	 */
	public BaseRuntimeException(Object code, Object... params) {
		super(String.valueOf(code));
		this.code = code;
	}


	/**
	 * Getter method for property <tt>code</tt>
	 * 
	 * @return property value of code
	 */
	public Object getCode() {
		return code;
	}

	/**
	 * Setter method for property <tt>code</tt>
	 * 
	 * @param code
	 *            value to be assigned to property code
	 */
	public void setCode(Object code) {
		this.code = code;
	}
}
