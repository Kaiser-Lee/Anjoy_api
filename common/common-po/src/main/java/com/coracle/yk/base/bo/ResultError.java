package com.coracle.yk.base.bo;

public class ResultError extends Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8894880561729275089L;
	public ResultError() {
		setSuccess(false);
		setMessage(RESULT_MESSAGE_ERROR);
		setCode(RESULT_CODE_ERROR);;
	}
}
