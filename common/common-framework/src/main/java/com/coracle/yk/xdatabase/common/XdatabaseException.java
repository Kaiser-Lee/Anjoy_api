package com.coracle.yk.xdatabase.common;

import com.coracle.yk.xframework.common.XframeworkException;

/**     
 *      
 * 系统自定义异常
 * @author jianglfa       
 * @version 1.0     
 * @created Aug 10, 2011 12:57:53 PM    
 */

public class XdatabaseException extends XframeworkException {

	/**  描述  */     
	
	private static final long serialVersionUID = 1L;

	public XdatabaseException() {
		this("未知的任务异常");
	}

	public XdatabaseException(String message) {
		super(message);
	}

	public XdatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public XdatabaseException(Throwable cause) {
		super(cause);
	}
}
