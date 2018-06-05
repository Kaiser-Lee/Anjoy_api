package com.coracle.dms.schedule.job;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coracle.dms.po.AnjoyCustomerPrice;
import com.coracle.dms.po.DmsQuartzSync;
import com.coracle.dms.service.AnjoyCustomerPriceService;
import com.coracle.dms.service.DmsQuartzSyncService;
import com.coracle.dms.webservice.AnjoySynClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 定时同步经销商价格
 * Created by
 * author：arno
 * Date：2018/3/1-14:47
 */
@Component
public class PriceScheduledTask {

    @Resource
    private DmsQuartzSyncService dmsQuartzSyncService;

    @Resource
    private AnjoyCustomerPriceService anjoyCustomerPriceService;

    public void execute() {
        /*DmsQuartzSyncService dmsQuartzSyncService = (DmsQuartzSyncService) SpringContextUtil.getBeanByClass(DmsQuartzSyncService.class);
        AnjoyCustomerPriceService anjoyCustomerPriceService = (AnjoyCustomerPriceService) SpringContextUtil.getBeanByClass(AnjoyCustomerPriceService.class);*/
        DmsQuartzSync quartzSync = dmsQuartzSyncService.selectByPrimaryKey(AnjoyCustomerPrice.class.getName());
        JSONArray array = AnjoySynClient.getUpdatePriceData(quartzSync == null ? 1519952400 : (quartzSync.getLastUpdatedDate().getTime() / 1000));
        if (quartzSync == null) {
            quartzSync = new DmsQuartzSync();
            quartzSync.setTableName(AnjoyCustomerPrice.class.getName());
        }
        quartzSync.setLastUpdatedDate(new Date());
        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = array.getJSONObject(i);
                AnjoyCustomerPrice price = new AnjoyCustomerPrice();
                price.setEasChannelCode(object.getString("customernumber"));
                price.setEasProductCode(object.getString("materialnumber"));
                List<AnjoyCustomerPrice> prices = anjoyCustomerPriceService.selectByCondition(price);
                price.setFprice(object.getDouble("fprice"));
                if (prices.isEmpty()) {
                    anjoyCustomerPriceService.insert(price);
                } else {
                    anjoyCustomerPriceService.updateByPrimaryKey(price);
                }
            }
        }
    }

}