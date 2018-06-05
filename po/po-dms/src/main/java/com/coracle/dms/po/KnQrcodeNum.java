/**
 * create by hcs
 * @date 2018-01
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import java.io.Serializable;

public class KnQrcodeNum extends AdapterEntity implements Serializable {
    private String id;

    private Long createTime;

    private Boolean loginStatus;

    private String loginName;

    private String appKey;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Boolean getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
    }
}