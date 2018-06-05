package com.coracle.yk.xframework.common;

/**     
 *      
 * 系统自定义异常
 * @author jianglfa       
 * @version 1.0     
 * @created Aug 10, 2011 12:57:53 PM    
 */

public class XframeworkException extends Exception {

	/**  描述  */     
	
	private static final long serialVersionUID = 1L;

	public XframeworkException() {
	}

	public XframeworkException(String message) {
		super(message);
	}

	public XframeworkException(String message, Throwable cause) {
		super(message, cause);
	}

	public XframeworkException(Throwable cause) {
		super(cause);
	}
}
