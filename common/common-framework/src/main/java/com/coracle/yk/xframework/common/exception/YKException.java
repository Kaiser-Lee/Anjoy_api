package com.coracle.yk.xframework.common.exception;

import com.coracle.yk.xframework.common.XframeworkException;

/**     
 *      
 * 系统自定义异常
 * @author jianglfa       
 * @version 1.0     
 * @created Aug 10, 2011 12:57:53 PM    
 */

public class YKException extends XframeworkException {

	/**  描述  */     
	
	private static final long serialVersionUID = 1L;

	public YKException() {
		this("未知的服务异常");
	}

	public YKException(String message) {
		super(message);
	}

	public YKException(String message, Throwable cause) {
		super(message, cause);
	}

	public YKException(Throwable cause) {
		super(cause);
	}
}
