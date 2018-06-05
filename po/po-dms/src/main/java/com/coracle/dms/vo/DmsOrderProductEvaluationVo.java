package com.coracle.dms.vo;

import com.coracle.dms.po.DmsCommonAttachFile;
import com.coracle.dms.po.DmsOrderProductEvaluation;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class DmsOrderProductEvaluationVo extends DmsOrderProductEvaluation {

    @ApiModelProperty("图片文件id列表")
    private List<Long> picFileIdList;

    @ApiModelProperty("图片文件详情")
    private List<DmsCommonAttachFile> picFileList;

    @ApiModelProperty("语音文件")
    private DmsCommonAttachFile audioFile;

    @ApiModelProperty("评价人")
    private String evaluator;

    @ApiModelProperty("评价人头像")
    private String picUrl;

    @ApiModelProperty("评价图片关联类型")
    private Integer relatedTableType;

    @ApiModelProperty("产品规格，格式：颜色:黑色;尺码:M")
    private String specUnionKey;

    public List<Long> getPicFileIdList() {
        return picFileIdList;
    }

    @ApiModelProperty("产品id参数")
    private Long productId;

    public void setPicFileIdList(List<Long> picFileIdList) {
        this.picFileIdList = picFileIdList;
    }

    public List<DmsCommonAttachFile> getPicFileList() {
        return picFileList;
    }

    public void setPicFileList(List<DmsCommonAttachFile> picFileList) {
        this.picFileList = picFileList;
    }

    public DmsCommonAttachFile getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(DmsCommonAttachFile audioFile) {
        this.audioFile = audioFile;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getRelatedTableType() {
        return relatedTableType;
    }

    public void setRelatedTableType(Integer relatedTableType) {
        this.relatedTableType = relatedTableType;
    }

    public String getSpecUnionKey() {
        return specUnionKey;
    }

    public void setSpecUnionKey(String specUnionKey) {
        this.specUnionKey = specUnionKey;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
