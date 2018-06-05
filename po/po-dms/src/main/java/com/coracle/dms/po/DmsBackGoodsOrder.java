/**
 * create by Administrator
 * @date 2018-01
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class DmsBackGoodsOrder extends AdapterEntity implements Serializable {
    private Long id;

    private String backOrderNumber;

    private Date lookupDate;

    private String backPlace;

    private BigDecimal yearGoodsAmount;

    private String suggestionInfo;

    private String customer;

    private String unit;

    private String backSubRepository;

    private BigDecimal yearBackGoodsAmount;

    private Date createdDate;

    private Long createdBy;

    private Date lastUpdatedDate;

    private Long lastUpdatedBy;

    private Integer removeFlag;

    private String backGoodsType;

    private String customerNumber;

    private String signType;

    private BigDecimal returnAmount;

    private String realReturnAmount;

    private Date returnDate;

    private String phone;

    private String returnType;

    private BigDecimal returningAmount;

    private ArrayList<DmsBackGoodsMaterial> dmsBackGoodsMaterialList;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBackOrderNumber() {
        return backOrderNumber;
    }

    public void setBackOrderNumber(String backOrderNumber) {
        this.backOrderNumber = backOrderNumber == null ? null : backOrderNumber.trim();
    }

    public void setDmsBackGoodsMaterialList(ArrayList<DmsBackGoodsMaterial> dmsBackGoodsMaterialList) {
        this.dmsBackGoodsMaterialList = dmsBackGoodsMaterialList;
    }

    public ArrayList<DmsBackGoodsMaterial> getDmsBackGoodsMaterialList() {
        return dmsBackGoodsMaterialList;
    }

    public Date getLookupDate() {
        return lookupDate;
    }

    public void setLookupDate(Date lookupDate) {
        this.lookupDate = lookupDate;
    }

    public String getBackPlace() {
        return backPlace;
    }

    public void setBackPlace(String backPlace) {
        this.backPlace = backPlace == null ? null : backPlace.trim();
    }

    public BigDecimal getYearGoodsAmount() {
        return yearGoodsAmount;
    }

    public void setYearGoodsAmount(BigDecimal yearGoodsAmount) {
        this.yearGoodsAmount = yearGoodsAmount;
    }

    public String getSuggestionInfo() {
        return suggestionInfo;
    }

    public void setSuggestionInfo(String suggestionInfo) {
        this.suggestionInfo = suggestionInfo == null ? null : suggestionInfo.trim();
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer == null ? null : customer.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getBackSubRepository() {
        return backSubRepository;
    }

    public void setBackSubRepository(String backSubRepository) {
        this.backSubRepository = backSubRepository == null ? null : backSubRepository.trim();
    }

    public BigDecimal getYearBackGoodsAmount() {
        return yearBackGoodsAmount;
    }

    public void setYearBackGoodsAmount(BigDecimal yearBackGoodsAmount) {
        this.yearBackGoodsAmount = yearBackGoodsAmount;
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

    public String getBackGoodsType() {
        return backGoodsType;
    }

    public void setBackGoodsType(String backGoodsType) {
        this.backGoodsType = backGoodsType == null ? null : backGoodsType.trim();
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber == null ? null : customerNumber.trim();
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType == null ? null : signType.trim();
    }

    public BigDecimal getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(BigDecimal returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getRealReturnAmount() {
        return realReturnAmount;
    }

    public void setRealReturnAmount(String realReturnAmount) {
        this.realReturnAmount = realReturnAmount == null ? null : realReturnAmount.trim();
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType == null ? null : returnType.trim();
    }

    public BigDecimal getReturningAmount() {
        return returningAmount;
    }

    public void setReturningAmount(BigDecimal returningAmount) {
        this.returningAmount = returningAmount;
    }
}