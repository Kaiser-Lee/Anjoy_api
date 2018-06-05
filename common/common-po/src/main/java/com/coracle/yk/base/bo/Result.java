/**
 * 
 */
package com.coracle.yk.base.bo;

import java.io.Serializable;

/**
 * @author Xiruo
 *
 */
public class Result implements Serializable {
	protected static final String RESULT_MESSAGE_SUCCESS = "success";
	protected static final String RESULT_MESSAGE_ERROR = "error";
	protected static final int RESULT_CODE_SUCCESS = 0;
	protected static final int RESULT_CODE_ERROR = -1;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3391651969164766078L;

	private boolean success;
	private int code;
	private String message;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
}
