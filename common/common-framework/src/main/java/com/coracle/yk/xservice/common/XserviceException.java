package com.coracle.yk.xservice.common;

import com.coracle.yk.xframework.common.XframeworkException;

/**     
 *      
 * 系统自定义异常
 * @author jianglfa       
 * @version 1.0     
 * @created Aug 10, 2011 12:57:53 PM    
 */

public class XserviceException extends XframeworkException {

	/**  描述  */     
	
	private static final long serialVersionUID = 1L;

	public XserviceException() {
		this("未知的服务异常");
	}

	public XserviceException(String message) {
		super(message);
	}

	public XserviceException(String message, Throwable cause) {
		super(message, cause);
	}

	public XserviceException(Throwable cause) {
		super(cause);
	}
}
