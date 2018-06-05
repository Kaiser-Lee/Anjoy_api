package com.coracle.yk.xframework.common.exception.runtime;


import com.coracle.yk.xframework.common.exception.BaseRuntimeException;

/**
 * 与用户操作或实际业务逻辑相关的异常<br>
 * 由框架统一捕获并处理,将异常信息显示给用户
 * @author hbd
 * @version 2017-03
 */
public class ServiceException extends BaseRuntimeException {

	private static final long	serialVersionUID	= -4942849136285353014L;

	/**
	 * Creates a new instance of ServiceException
	 * 
	 */
	public ServiceException() {
		super();
	}

	/**
	 * Creates a new instance of ServiceException
	 * 
	 * @param cause
	 */
	public ServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new instance of ServiceException
	 * 
	 * @param code
	 * @param params
	 */
	public ServiceException(Object code, Object... params) {
		super(code, params);
	}

	/**
	 * Creates a new instance of ServiceException
	 * 
	 * @param cause
	 * @param code
	 * @param params
	 */
	public ServiceException(Throwable cause, Object code, Object... params) {
		super(cause, code, params);
	}

}
