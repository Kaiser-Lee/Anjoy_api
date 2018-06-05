package com.coracle.dms.vo;

import com.coracle.dms.po.DmsMessage;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */
public class DmsMessageVo extends DmsMessage {
    @ApiModelProperty("要查询的消息类型列表")
    private List<Integer> messageTypeList;
    @ApiModelProperty("要查询的内容")
    private String searchContent;
    @ApiModelProperty("pc显示头像")
    private String photoUrl;

    public List<Integer> getMessageTypeList() {
        return messageTypeList;
    }

    public void setMessageTypeList(List<Integer> messageTypeList) {
        this.messageTypeList = messageTypeList;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
