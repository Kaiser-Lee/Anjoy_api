/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.po;

import com.coracle.yk.base.po.AdapterEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
@ApiModel(description = "DMS门店店员表")
public class DmsSeller extends AdapterEntity implements Serializable {
    @ApiModelProperty("主键ID")
    private Long id;
    @ApiModelProperty("门店Id")
    private Long shopId;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("岗位（0店员，1店长）")
    private String post;
    @ApiModelProperty("生日")
    private Date birthday;
    @ApiModelProperty("创建时间")
    private Date createdDate;
    @ApiModelProperty("创建人")
    private Long createdBy;
    @ApiModelProperty("最后修改时间")
    private Date lastUpdatedDate;
    @ApiModelProperty("最后修改人")
    private Long lastUpdatedBy;
    @ApiModelProperty("软删除标识（0：未删除，1已删除）")
    private Integer removeFlag;
    @ApiModelProperty("启用状态（1有效，失效）")
    private Integer active;
    @ApiModelProperty("扩展字段")
    private String expand1;
    @ApiModelProperty("扩展字段")
    private String expand2;
    @ApiModelProperty("扩展字段")
    private String expand3;
    @ApiModelProperty("扩展字段")
    private String expand4;
    @ApiModelProperty("扩展字段")
    private String expand5;
    @ApiModelProperty("扩展字段")
    private String expand6;
    @ApiModelProperty("扩展字段")
    private String expand7;
    @ApiModelProperty("扩展字段")
    private String expand8;
    @ApiModelProperty("扩展字段")
    private String expand9;
    @ApiModelProperty("扩展字段")
    private String expand10;
    @ApiModelProperty("扩展字段")
    private String expand11;
    @ApiModelProperty("扩展字段")
    private String expand12;
    @ApiModelProperty("扩展字段")
    private String expand13;
    @ApiModelProperty("扩展字段")
    private String expand14;
    @ApiModelProperty("扩展字段")
    private String expand15;
    @ApiModelProperty("关联的账号的ＩＤ")
    private Long userId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getExpand1() {
        return expand1;
    }

    public void setExpand1(String expand1) {
        this.expand1 = expand1 == null ? null : expand1.trim();
    }

    public String getExpand2() {
        return expand2;
    }

    public void setExpand2(String expand2) {
        this.expand2 = expand2 == null ? null : expand2.trim();
    }

    public String getExpand3() {
        return expand3;
    }

    public void setExpand3(String expand3) {
        this.expand3 = expand3 == null ? null : expand3.trim();
    }

    public String getExpand4() {
        return expand4;
    }

    public void setExpand4(String expand4) {
        this.expand4 = expand4 == null ? null : expand4.trim();
    }

    public String getExpand5() {
        return expand5;
    }

    public void setExpand5(String expand5) {
        this.expand5 = expand5 == null ? null : expand5.trim();
    }

    public String getExpand6() {
        return expand6;
    }

    public void setExpand6(String expand6) {
        this.expand6 = expand6 == null ? null : expand6.trim();
    }

    public String getExpand7() {
        return expand7;
    }

    public void setExpand7(String expand7) {
        this.expand7 = expand7 == null ? null : expand7.trim();
    }

    public String getExpand8() {
        return expand8;
    }

    public void setExpand8(String expand8) {
        this.expand8 = expand8 == null ? null : expand8.trim();
    }

    public String getExpand9() {
        return expand9;
    }

    public void setExpand9(String expand9) {
        this.expand9 = expand9 == null ? null : expand9.trim();
    }

    public String getExpand10() {
        return expand10;
    }

    public void setExpand10(String expand10) {
        this.expand10 = expand10 == null ? null : expand10.trim();
    }

    public String getExpand11() {
        return expand11;
    }

    public void setExpand11(String expand11) {
        this.expand11 = expand11 == null ? null : expand11.trim();
    }

    public String getExpand12() {
        return expand12;
    }

    public void setExpand12(String expand12) {
        this.expand12 = expand12 == null ? null : expand12.trim();
    }

    public String getExpand13() {
        return expand13;
    }

    public void setExpand13(String expand13) {
        this.expand13 = expand13 == null ? null : expand13.trim();
    }

    public String getExpand14() {
        return expand14;
    }

    public void setExpand14(String expand14) {
        this.expand14 = expand14 == null ? null : expand14.trim();
    }

    public String getExpand15() {
        return expand15;
    }

    public void setExpand15(String expand15) {
        this.expand15 = expand15 == null ? null : expand15.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}