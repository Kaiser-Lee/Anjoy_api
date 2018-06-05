package com.coracle.dms.vo;

import com.coracle.dms.po.DmsSeller;
import com.coracle.dms.po.DmsStore;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/19.
 */
public class DmsStoreVo extends DmsStore {
    @ApiModelProperty("店员Vo集合")
    private List<DmsSellerVo> dmsSellerList;
    @ApiModelProperty("店员集合")
    private List<Map<String,Object>> dmsSellerVoList;
    @ApiModelProperty("门店图片地址")
    private String picUrl;
    @ApiModelProperty("App端新增门店与修改门店用")
    private String remark;
    @ApiModelProperty(value = "渠道id集合",hidden = true)
    private List<Long> channelIds;
    @ApiModelProperty("上级渠道名称,所属经销商查询也用她")
    private String dealerName;
    @ApiModelProperty(value = "运营方式文本",hidden = true)
    private String operateWayText;
    @ApiModelProperty(value = "门店类型文本",hidden = true)
    private String storeTypeText;
    @ApiModelProperty(value = "所属业务员名称",hidden = true)
    private String employeeName;
    @ApiModelProperty(value = "归属区域名称名称",hidden = true)
    private String areaText;
    @ApiModelProperty(value = "压缩图路径",hidden = true)
    private String compressPath;
    @ApiModelProperty(value = "缩略图路径",hidden = true)
    private String previewPath;
    @ApiModelProperty(value = "省份文本",hidden = true)
    private String provinceText;
    @ApiModelProperty(value = "城市文本",hidden = true)
    private String cityText;
    @ApiModelProperty(value = "区县文本",hidden = true)
    private String countyText;
    @ApiModelProperty("店长")
    private String shopowner;
    @ApiModelProperty("店长电话")
    private String shopownerPhone;

    public List<DmsSellerVo> getDmsSellerList() {
        return dmsSellerList;
    }

    public void setDmsSellerList(List<DmsSellerVo> dmsSellerList) {
        this.dmsSellerList = dmsSellerList;
    }

    public List<Map<String, Object>> getDmsSellerVoList() {
        return dmsSellerVoList;
    }

    public void setDmsSellerVoList(List<Map<String, Object>> dmsSellerVoList) {
        this.dmsSellerVoList = dmsSellerVoList;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public DmsStoreVo() {
    }

    public DmsStoreVo(DmsStore dmsStore) {
        super.setActive(dmsStore.getActive());
        super.setId(dmsStore.getId());
        super.setName(dmsStore.getName());
        super.setCode(dmsStore.getCode());
        super.setPicId(dmsStore.getPicId());
        super.setOperateWay(dmsStore.getOperateWay());
        super.setStoreType(dmsStore.getStoreType());
        super.setStoreAddress(dmsStore.getStoreAddress());
        super.setBelongArea(dmsStore.getBelongArea());
        super.setBelongEmployee(dmsStore.getBelongEmployee());
        super.setBelongDealer(dmsStore.getBelongDealer());
        super.setStorePhone(dmsStore.getStorePhone());
        super.setStoreEmployeeNum(dmsStore.getStoreEmployeeNum());
        super.setOpenDate(dmsStore.getOpenDate());
        super.setCloseDate(dmsStore.getCloseDate());
        super.setStoreArea(dmsStore.getStoreArea());
        super.setSellVolume(dmsStore.getSellVolume());
        super.setCreatedBy(dmsStore.getCreatedBy());
        super.setCreatedDate(dmsStore.getCreatedDate());
        super.setLastUpdatedBy(dmsStore.getLastUpdatedBy());
        super.setLastUpdatedDate(dmsStore.getLastUpdatedDate());
        super.setRemoveFlag(dmsStore.getRemoveFlag());
        super.setExpand1(dmsStore.getExpand1());
        super.setExpand2(dmsStore.getExpand2());
        super.setExpand3(dmsStore.getExpand3());
        super.setExpand4(dmsStore.getExpand4());
        super.setExpand5(dmsStore.getExpand5());
        super.setExpand6(dmsStore.getExpand6());
        super.setExpand7(dmsStore.getExpand7());
        super.setExpand8(dmsStore.getExpand8());
        super.setExpand9(dmsStore.getExpand9());
        super.setExpand10(dmsStore.getExpand10());
        super.setExpand11(dmsStore.getExpand11());
        super.setExpand12(dmsStore.getExpand12());
        super.setExpand13(dmsStore.getExpand13());
        super.setExpand14(dmsStore.getExpand14());
        super.setExpand15(dmsStore.getExpand15());
        super.setProvince(dmsStore.getProvince());
        super.setCity(dmsStore.getCity());
        super.setCounty(dmsStore.getCounty());
    }

    public List<Long> getChannelIds() {
        return channelIds;
    }

    public void setChannelIds(List<Long> channelIds) {
        this.channelIds = channelIds;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getStoreTypeText() {
        return storeTypeText;
    }

    public void setStoreTypeText(String storeTypeText) {
        this.storeTypeText = storeTypeText;
    }

    public String getOperateWayText() {

        return operateWayText;
    }

    public void setOperateWayText(String operateWayText) {
        this.operateWayText = operateWayText;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getAreaText() {
        return areaText;
    }

    public void setAreaText(String areaText) {
        this.areaText = areaText;
    }

    public String getCompressPath() {
        return compressPath;
    }

    public void setCompressPath(String compressPath) {
        this.compressPath = compressPath;
    }

    public String getPreviewPath() {
        return previewPath;
    }

    public void setPreviewPath(String previewPath) {
        this.previewPath = previewPath;
    }

    public String getProvinceText() {
        return provinceText;
    }

    public void setProvinceText(String provinceText) {
        this.provinceText = provinceText;
    }

    public String getCityText() {
        return cityText;
    }

    public void setCityText(String cityText) {
        this.cityText = cityText;
    }

    public String getCountyText() {
        return countyText;
    }

    public void setCountyText(String countyText) {
        this.countyText = countyText;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShopowner() {
        return shopowner;
    }

    public void setShopowner(String shopowner) {
        this.shopowner = shopowner == null ? null : shopowner.trim();
    }

    public String getShopownerPhone() {
        return shopownerPhone;
    }

    public void setShopownerPhone(String shopownerPhone) {
        this.shopownerPhone = shopownerPhone == null ? null : shopownerPhone.trim();
    }
}
