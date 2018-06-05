/**
 * create by Administrator
 * @date 2018-01
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@ApiModel(description ="最小起订量")
public class DmsChannelMinimum extends AdapterEntity implements Serializable {

    @ApiModelProperty("主键id")
    private Long id;
    @ApiModelProperty("渠道id")
    private Long channelId;
    @ApiModelProperty("产品id")
    private Long productId;
    @ApiModelProperty("是否整板下单")
    private Integer isboard;
    @ApiModelProperty("整板下单量")
    private Long boardQuantity;
    @ApiModelProperty("最小起订量")
    private Long minOrderQuantity;
    @ApiModelProperty("同步价格")
    private BigDecimal synPrice;

    private Date createDate;

    private Long createBy;

    private Date lastUpdateDate;

    private Long lastUpdateBy;

    private Integer removeFlag;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }


    public Integer getIsboard() {
        return isboard;
    }

    public void setIsboard(Integer isboard) {
        this.isboard = isboard;
    }

    public Long getBoardQuantity() {
        return boardQuantity;
    }

    public void setBoardQuantity(Long boardQuantity) {
        this.boardQuantity = boardQuantity;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Long getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(Long lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Integer getRemoveFlag() {
        return removeFlag;
    }

    public void setRemoveFlag(Integer removeFlag) {
        this.removeFlag = removeFlag;
    }

    public Long getMinOrderQuantity() {
        return minOrderQuantity;
    }

    public void setMinOrderQuantity(Long minOrderQuantity) {
        this.minOrderQuantity = minOrderQuantity;
    }

    public BigDecimal getSynPrice() {
        return synPrice;
    }

    public void setSynPrice(BigDecimal synPrice) {
        this.synPrice = synPrice;
    }
}