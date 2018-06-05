/**
 * create by Administrator
 * @date 2018-01
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import java.io.Serializable;
import java.util.Date;

public class DmsTianSubStore extends AdapterEntity implements Serializable {
    private Integer id;

    private Date createdDate;

    private Long createdBy;

    private Date lastUpdatedDate;

    private Long lastUpdatedBy;

    private Integer removeFlag;

    private String subStoreNumber;

    private String subStoreName;

    private String sutStoreText;

    private String masterStore;

    private String takeEffect;

    private Date postDue;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Integer getRemoveFlag() {
        return removeFlag;
    }

    public void setRemoveFlag(Integer removeFlag) {
        this.removeFlag = removeFlag;
    }

    public String getSubStoreNumber() {
        return subStoreNumber;
    }

    public void setSubStoreNumber(String subStoreNumber) {
        this.subStoreNumber = subStoreNumber == null ? null : subStoreNumber.trim();
    }

    public String getSubStoreName() {
        return subStoreName;
    }

    public void setSubStoreName(String subStoreName) {
        this.subStoreName = subStoreName == null ? null : subStoreName.trim();
    }

    public String getSutStoreText() {
        return sutStoreText;
    }

    public void setSutStoreText(String sutStoreText) {
        this.sutStoreText = sutStoreText == null ? null : sutStoreText.trim();
    }

    public String getMasterStore() {
        return masterStore;
    }

    public void setMasterStore(String masterStore) {
        this.masterStore = masterStore == null ? null : masterStore.trim();
    }

    public String getTakeEffect() {
        return takeEffect;
    }

    public void setTakeEffect(String takeEffect) {
        this.takeEffect = takeEffect == null ? null : takeEffect.trim();
    }

    public Date getPostDue() {
        return postDue;
    }

    public void setPostDue(Date postDue) {
        this.postDue = postDue;
    }
}