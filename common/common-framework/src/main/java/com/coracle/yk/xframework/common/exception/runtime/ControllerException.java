package com.coracle.yk.xframework.common.exception.runtime;

/**
 * Created by huangbaidong on 2017/3/3.
 * 控制层异常信息补货类
 */
public class ControllerException extends ServiceException {
    /**
     * Creates a new instance of ServiceException
     *
     */
    public ControllerException() {
        super();
    }

    /**
     * Creates a new instance of ServiceException
     *
     * @param cause
     */
    public ControllerException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new instance of ServiceException
     *
     * @param code
     * @param params
     */
    public ControllerException(Object code, Object... params) {
        super(code, params);
    }

    /**
     * Creates a new instance of ServiceException
     *
     * @param cause
     * @param code
     * @param params
     */
    public ControllerException(Throwable cause, Object code, Object... params) {
        super(cause, code, params);
    }

}
