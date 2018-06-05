package com.coracle.yk.xframework.common.exception.runtime;

/**
 * Created by huangbaidong on 2017/3/3.
 * 控制层传参错误异常类
 */
public class ParamException extends ServiceException {
    /**
     * Creates a new instance of ServiceException
     *
     */
    public ParamException() {
        super();
    }

    /**
     * Creates a new instance of ServiceException
     *
     * @param cause
     */
    public ParamException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new instance of ServiceException
     *
     * @param code
     * @param params
     */
    public ParamException(Object code, Object... params) {
        super(code, params);
    }

    /**
     * Creates a new instance of ServiceException
     *
     * @param cause
     * @param code
     * @param params
     */
    public ParamException(Throwable cause, Object code, Object... params) {
        super(cause, code, params);
    }

}
