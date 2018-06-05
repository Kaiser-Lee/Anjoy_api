package com.coracle.dms.vo;

import com.coracle.dms.po.DmsNotice;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/24.
 */
public class DmsNoticeVo extends DmsNotice {
    @ApiModelProperty("发布日期查询时间起 精确到年月日(格式yyyy-MM-dd)")
    private String startDate;
    @ApiModelProperty("发布日期查询时间止 精确到年月日(格式yyyy-MM-dd)")
    private String endDate;
    @ApiModelProperty("发布用户姓名")
    private String publishUserName;
    @ApiModelProperty(value = "账号来源类型表",hidden = true)
    private Integer source;//账号来源类型表
    @ApiModelProperty(value = "账号来源类型表对应表id",hidden = true)
    private Long staffId;//对应表id

    private String userName;

    private String organizationName;
    @ApiModelProperty("发布范围ID列表")
    private List<Long> rangeList;
    @ApiModelProperty(value = "发布范围ID名称map列表",hidden = true)
    private List<Map<String,Object>> rangeInfoList;//对应表id
    @ApiModelProperty(value = "渠道赋能资讯类型文本",hidden = true)
    private String typeText;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPublishUserName() {
        return publishUserName;
    }

    public List<Map<String, Object>> getRangeInfoList() {
        return rangeInfoList;
    }

    public void setRangeInfoList(List<Map<String, Object>> rangeInfoList) {
        this.rangeInfoList = rangeInfoList;
    }

    public void setPublishUserName(String publishUserName) {
        this.publishUserName = publishUserName;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public List<Long> getRangeList() {
        return rangeList;
    }

    public void setRangeList(List<Long> rangeList) {
        this.rangeList = rangeList;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }
}
