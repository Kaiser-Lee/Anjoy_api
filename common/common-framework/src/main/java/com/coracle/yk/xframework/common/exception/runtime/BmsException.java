package com.coracle.yk.xframework.common.exception.runtime;

/**
 * Created by huangbaidong on 2017/5/22.
 * 支付请求异常
 */
public class BmsException extends ServiceException {
    /**
     * Creates a new instance of ServiceException
     *
     */
    public BmsException() {
        super();
    }

    /**
     * Creates a new instance of ServiceException
     *
     * @param cause
     */
    public BmsException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new instance of ServiceException
     *
     * @param code
     * @param params
     */
    public BmsException(Object code, Object... params) {
        super(code, params);
    }

    /**
     * Creates a new instance of ServiceException
     *
     * @param cause
     * @param code
     * @param params
     */
    public BmsException(Throwable cause, Object code, Object... params) {
        super(cause, code, params);
    }

}
