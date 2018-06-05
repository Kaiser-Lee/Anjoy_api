package com.coracle.yk.xframework.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @title：多户头实体
 * @describe：
 * @copyright：Copyright (c) 1997-2017 coracle, Inc.
 * @company：圆舟科技
 * @author：taok
 * @date：2018-03-13 22:13
 * @version：1.0
 */
public class DmsChannelMutilVo implements Serializable {
    private static final long serialVersionUID = -2920226919876828288L;
    private Long id;//主键ID
    private String name;//渠道名称
    private String code;//渠道编号
    //private String shortName;//渠道简称
    //private String rank;//等级.大客户、中客户、小客户
    //private String channelType;//渠道类型.销售公司、代理商、经销商、分销商
    private String address;//详细地址
    private String contacts;//联系人
    //private String contactsMobile;//手机号码
    //private Integer removeFlag;//软删除标识
    //private Integer active;//是否开启（安井）
    //private BigDecimal creditLimit;//信用额度（安井）
    //private Long accountLimit;//账期（安井）
    //private String anjoyId;//安井id
    //private String anjoyParentId;//安井父id
    //private String anjoyCfbibscidId;//安井归属区域id
    //private String anjoySaleOrgId;//安井销售区域id
    private Long userId;

    //private List<DmsChannelMutilVo> parentChannelList = new ArrayList<>();//上级渠道
    private List<DmsChannelMutilVo> subsetChannelList = new ArrayList<>();//下级渠道

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public List<DmsChannelMutilVo> getSubsetChannelList() {
        return subsetChannelList;
    }

    public void setSubsetChannelList(List<DmsChannelMutilVo> subsetChannelList) {
        this.subsetChannelList = subsetChannelList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
