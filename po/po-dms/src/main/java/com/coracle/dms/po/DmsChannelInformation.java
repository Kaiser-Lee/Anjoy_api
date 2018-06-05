/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
@ApiModel(description = "DMS渠道赋能表")
public class DmsChannelInformation extends AdapterEntity implements Serializable {
    @ApiModelProperty("主键ID")
    private Long id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("类型：1渠道政策，2理论学习")
    private String type;
    @ApiModelProperty("图片")
    private Long picId;
    @ApiModelProperty("发布用户ID")
    private Long publishUserId;
    @ApiModelProperty("是否可评论(0,否，1是)")
    private Integer isComment;
    @ApiModelProperty("是否发布（0，否，1是）")
    private Integer isPublish;
    @ApiModelProperty("发布日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date publishDate;
    @ApiModelProperty("正文")
    private String content;
    @ApiModelProperty("点击量")
    private Long clickCount;
    @ApiModelProperty("评论量")
    private Long commentCount;
    @ApiModelProperty("转发量")
    private Long forwardCount;
    @ApiModelProperty("是否首页显示（0,否，1是）")
    private Integer isShow;

    private Long createdBy;

    private Long lastUpdatedBy;

    private Date createdDate;

    private Date lastUpdatedDate;
    @ApiModelProperty("软删除标识（0：未删除，1已删除）")
    private Integer removeFlag;
    @ApiModelProperty("是否可以转发（0，否，1是）")
    private Integer isCanForward;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long picId) {
        this.picId = picId;
    }

    public Long getPublishUserId() {
        return publishUserId;
    }

    public void setPublishUserId(Long publishUserId) {
        this.publishUserId = publishUserId;
    }

    public Integer getIsComment() {
        return isComment;
    }

    public void setIsComment(Integer isComment) {
        this.isComment = isComment;
    }

    public Integer getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Integer isPublish) {
        this.isPublish = isPublish;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public Long getForwardCount() {
        return forwardCount;
    }

    public void setForwardCount(Long forwardCount) {
        this.forwardCount = forwardCount;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Integer getRemoveFlag() {
        return removeFlag;
    }

    public void setRemoveFlag(Integer removeFlag) {
        this.removeFlag = removeFlag;
    }

    public Integer getIsCanForward() {
        return isCanForward;
    }

    public void setIsCanForward(Integer isCanForward) {
        this.isCanForward = isCanForward;
    }
}