package com.coracle.dms.vo;

import java.io.Serializable;

/**
 * @title：
 * @describe：
 * @copyright：Copyright (c) 1997-2017 coracle, Inc.
 * @company：圆舟科技
 * @author：taok
 * @date：2018-03-22 23:22
 * @version：1.0
 */
public class UserSessionVo implements Serializable {
    private static final long serialVersionUID = 3398702162942798755L;
    private String user;
    private Object user2;
    private boolean status;
    private Object object;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Object getUser2() {
        return user2;
    }

    public void setUser2(Object user2) {
        this.user2 = user2;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
