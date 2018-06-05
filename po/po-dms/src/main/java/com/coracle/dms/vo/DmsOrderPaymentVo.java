package com.coracle.dms.vo;

import com.coracle.dms.po.DmsCommonAttachFile;
import com.coracle.dms.po.DmsOrderPayment;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class DmsOrderPaymentVo extends DmsOrderPayment{

    @ApiModelProperty("收款状态")
    private String statusText;

    @ApiModelProperty("支付类型")
    private String typeText;

    @ApiModelProperty("转账凭证图片id列表")
    private List<Long> picIdList;

    @ApiModelProperty("转账凭证图片列表")
    private List<DmsCommonAttachFile> picFileList;

    @ApiModelProperty("附件关联表关联类型")
    private Integer relatedTableType;

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public List<Long> getPicIdList() {
        return picIdList;
    }

    public void setPicIdList(List<Long> picIdList) {
        this.picIdList = picIdList;
    }

    public List<DmsCommonAttachFile> getPicFileList() {
        return picFileList;
    }

    public void setPicFileList(List<DmsCommonAttachFile> picFileList) {
        this.picFileList = picFileList;
    }

    public Integer getRelatedTableType() {
        return relatedTableType;
    }

    public void setRelatedTableType(Integer relatedTableType) {
        this.relatedTableType = relatedTableType;
    }
}
