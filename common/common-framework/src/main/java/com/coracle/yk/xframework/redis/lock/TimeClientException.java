package com.coracle.yk.xframework.redis.lock;

import java.net.SocketAddress;

/**
 * Created by huangbaidong
 * 2017/3/30.
 */
public class TimeClientException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TimeClientException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public TimeClientException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public TimeClientException(SocketAddress address) {
        super(address.toString());
    }

}
