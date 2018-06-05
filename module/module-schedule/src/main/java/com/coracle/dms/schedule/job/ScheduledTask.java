package com.coracle.dms.schedule.job;

import com.coracle.dms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by
 * author：arno
 * Date：2018/3/3-10:44
 */
@Component
public class ScheduledTask {
    @Autowired
    private DmsOrganizationService dmsOrganizationService;
    @Autowired
    private DmsEmployeeService dmsEmployeeService;
    @Autowired
    private DmsChannelService dmsChannelService;
    @Autowired
    private DmsChannelAddressService dmsChannelAddressService;
    @Autowired
    private DmsProductCategoryService dmsProductCategoryService;
    @Autowired
    private DmsProductService dmsProductService;
    @Autowired
    private DmsChannelProductAnjoyWhiteListService dmsChannelProductAnjoyWhiteListService;
    @Autowired
    private DmsSysAreaService dmsSysAreaService;
    @Autowired
    private DmsChannelEmployeeService dmsChannelEmployeeService;

    /***
     * 同步组织（每天凌晨：一点）
     */
    public void syncOrg(){
        dmsOrganizationService.anjoySyn();
    }

    /***
     * 同步账号信息（每天凌晨：1点半）
     */
    public void syncEmpl(){
        dmsEmployeeService.anjoySyn();
    }

    /***
     * 同步渠道信息（每天凌晨：2 点）
     */
    public void syncChannel() {
        dmsChannelService.anjoySyn();
    }

    /***
     * 同步渠道-收货人信息（每隔3小时）
     */
    public void syncChannelAddress() {
        dmsChannelAddressService.anjoySyn();
    }

    /**
     * 同步产品类型（每天凌晨：3:30）
     */
    public void syncCategory(){
        dmsProductCategoryService.anjoySyn();
    }

    /**
     * 同步产品（每天凌晨：3 点 15）
     */
    public void syncProduct(){
        dmsProductService.anjoySyn();
    }

    /**
     * 同步产品白名单数据（每天凌晨：3 点 30）
     */
    public void syncProductWhiteList(){
        dmsChannelProductAnjoyWhiteListService.anjoySyn();
    }

    /**
     * 同步产品-规格数据（每天凌晨：3 点 45）
     */
    public void syncProductSpecifications(){
        dmsProductService.anjoySpecificationSyn();
    }

    /***
     * 同步区域（每天凌晨：4 点）
     */
    public void syncArea(){
        dmsSysAreaService.anjoySync();
    }

    /**
     * 同步渠道-业务员（每周五 凌晨 4 ：30）
     */
    public void syncChannelEmployee(){
        dmsChannelEmployeeService.anjoySyn();
    }
}