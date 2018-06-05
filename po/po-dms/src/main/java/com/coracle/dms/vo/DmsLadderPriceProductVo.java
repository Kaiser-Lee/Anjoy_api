package com.coracle.dms.vo;

import com.coracle.dms.po.DmsLadderPrice;
import com.coracle.dms.po.DmsLadderPriceProduct;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by Administrator on 2018-1-11.
 */
@ApiModel("阶梯价格产品vo")
public class DmsLadderPriceProductVo  extends DmsLadderPriceProduct {
    @ApiModelProperty("状态")
    private String activeText;

    @ApiModelProperty("列表筛选条件：活动状态：0-未开始；1-进行中；2-已结束；3-已下架。多选时用','分隔")
    private String status;

    @ApiModelProperty("活动状态")
    private String statusText;

    @ApiModelProperty("创建人")
    private String creator;

    @ApiModelProperty("适用范围")
    private String scope;

    @ApiModelProperty("列表筛选条件：开始日期")
    private String startDate;

    @ApiModelProperty("列表筛选条件：结束日期")
    private String endDate;

    @ApiModelProperty("是否可以修改")
    private Boolean modifiable;

    @ApiModelProperty("促销适用范围id列表，对应dms_tree_relation表的主键id")
    private List<Long> scopeIdList;

    @ApiModelProperty("促销适用范围列表")
    private List<DmsLadderProductScopeVo> scopeList;

    @ApiModelProperty("阶梯价格列表")
    private List<DmsLadderPrice> priceList;





    public String getActiveText() {
        return activeText;
    }

    public void setActiveText(String activeText) {
        this.activeText = activeText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }


    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

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

    public Boolean getModifiable() {
        return modifiable;
    }

    public void setModifiable(Boolean modifiable) {
        this.modifiable = modifiable;
    }

    public List<Long> getScopeIdList() {
        return scopeIdList;
    }

    public void setScopeIdList(List<Long> scopeIdList) {
        this.scopeIdList = scopeIdList;
    }

    public List<DmsLadderProductScopeVo> getScopeList() {
        return scopeList;
    }

    public void setScopeList(List<DmsLadderProductScopeVo> scopeList) {
        this.scopeList = scopeList;
    }

    public List<DmsLadderPrice> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<DmsLadderPrice> priceList) {
        this.priceList = priceList;
    }




}
