/**
 * create by Administrator
 * @date 2018-01
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiParam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class DmsClaims extends AdapterEntity implements Serializable {
    private Long id;

    private String title;

    private String processNumber;

    private Date applerDate;

    private String applerPerson;

    private String claimsProportion;

    private String nature;

    private Integer customerNumber;

    private String customerName;

    private String office;

    private String bigRegional;

    private String phone;

    private String deductAmount;

    private String claimsAmount;

    private Date arrivalDate;

    private Integer shippingOrderNo;

    private String claimsArea;

    private String signaMeaning;

    private Date createdDate;

    private Long createdBy;

    private Date lastUpdatedDate;

    private Long lastUpdatedBy;

    private Integer removeFlag;

    //1待审批2已审批3已驳回
    @ApiParam("1待审批2已审批3已驳回")
    private Integer state;

    private String approvePerson;

    private Date approveDate;

    //
    private ArrayList<DmsClaimsProduct> productsList;


    private static final long serialVersionUID = 1L;

    public ArrayList<DmsClaimsProduct> getProductsList() {
        return productsList;
    }

    public void setProductsList(ArrayList<DmsClaimsProduct> productsList) {
        this.productsList = productsList;
    }

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

    public String getProcessNumber() {
        return processNumber;
    }

    public void setProcessNumber(String processNumber) {
        this.processNumber = processNumber == null ? null : processNumber.trim();
    }

    public Date getApplerDate() {
        return applerDate;
    }

    public void setApplerDate(Date applerDate) {
        this.applerDate = applerDate;
    }

    public String getApplerPerson() {
        return applerPerson;
    }

    public void setApplerPerson(String applerPerson) {
        this.applerPerson = applerPerson == null ? null : applerPerson.trim();
    }

    public String getClaimsProportion() {
        return claimsProportion;
    }

    public void setClaimsProportion(String claimsProportion) {
        this.claimsProportion = claimsProportion == null ? null : claimsProportion.trim();
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature == null ? null : nature.trim();
    }

    public Integer getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office == null ? null : office.trim();
    }

    public String getBigRegional() {
        return bigRegional;
    }

    public void setBigRegional(String bigRegional) {
        this.bigRegional = bigRegional == null ? null : bigRegional.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getDeductAmount() {
        return deductAmount;
    }

    public void setDeductAmount(String deductAmount) {
        this.deductAmount = deductAmount == null ? null : deductAmount.trim();
    }

    public String getClaimsAmount() {
        return claimsAmount;
    }

    public void setClaimsAmount(String claimsAmount) {
        this.claimsAmount = claimsAmount == null ? null : claimsAmount.trim();
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Integer getShippingOrderNo() {
        return shippingOrderNo;
    }

    public void setShippingOrderNo(Integer shippingOrderNo) {
        this.shippingOrderNo = shippingOrderNo;
    }

    public String getClaimsArea() {
        return claimsArea;
    }

    public void setClaimsArea(String claimsArea) {
        this.claimsArea = claimsArea == null ? null : claimsArea.trim();
    }

    public String getSignaMeaning() {
        return signaMeaning;
    }

    public void setSignaMeaning(String signaMeaning) {
        this.signaMeaning = signaMeaning == null ? null : signaMeaning.trim();
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getApprovePerson() {
        return approvePerson;
    }

    public void setApprovePerson(String approvePerson) {
        this.approvePerson = approvePerson == null ? null : approvePerson.trim();
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }
}