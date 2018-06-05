package com.coracle.yk.base.bo;

public class ResultSuccess extends Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8894880561729275089L;

	public ResultSuccess() {
		setSuccess(true);
		setMessage(RESULT_MESSAGE_SUCCESS);
		setCode(RESULT_CODE_SUCCESS);;
	}
}
