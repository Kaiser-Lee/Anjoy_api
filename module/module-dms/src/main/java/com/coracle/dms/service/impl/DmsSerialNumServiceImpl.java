package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsSerialNumMapper;
import com.coracle.dms.po.DmsSerialNum;
import com.coracle.dms.service.DmsSerialNumService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class DmsSerialNumServiceImpl extends BaseServiceImpl<DmsSerialNum> implements DmsSerialNumService {
    private static final Logger logger = Logger.getLogger(DmsSerialNumServiceImpl.class);

    @Autowired
    private DmsSerialNumMapper dmsSerialNumMapper;

    @Override
    public IMybatisDao<DmsSerialNum> getBaseDao() {
        return dmsSerialNumMapper;
    }

    @Override
    public DmsSerialNum getInfoByPrimaryKey(String serialKey){
        return dmsSerialNumMapper.getInfoByPrimaryKey(serialKey);
    }

    @Override
    public String createSerialNumStr(String type){
        if (BlankUtil.isNotEmpty(type)) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
            //获取今天的日期
            String nowDay = sf.format(new Date());
            switch (type){
                case DmsModuleEnums.CHANNEL_SERIAL_KEY: return "2"+dealSerialNumStr(type,5,false);
                case DmsModuleEnums.STORE_SERIAL_KEY: return "3"+dealSerialNumStr(type,6,false);
                case DmsModuleEnums.ORDER_SERIAL_KEY: return nowDay+dealSerialNumStr(type,4,true);
                case DmsModuleEnums.SELL_OUT_SERIAL_KEY: return nowDay+dealSerialNumStr(type,5,true);
                case DmsModuleEnums.SALES_RETURNS_SERIAL_KEY: return nowDay+dealSerialNumStr(type,5,true);
                case DmsModuleEnums.PURCHASE_IN_STORAGE_SERIAL_KEY: return nowDay+dealSerialNumStr(type,5,true);
                case DmsModuleEnums.OTHER_IN_STORAGE_SERIAL_KEY: return nowDay+dealSerialNumStr(type,5,true);
                case DmsModuleEnums.INVENTORY_ADJUSTMENT_SERIAL_KEY: return nowDay+dealSerialNumStr(type,5,true);
                case DmsModuleEnums.TRANSFER_SERIAL_KEY: return nowDay+dealSerialNumStr(type,5,true);
                case DmsModuleEnums.PROMOTION_SERIAL_KEY: return "9" + dealSerialNumStr(type, 7, false);
                case DmsModuleEnums.BACK_GOODS_SERIAL_KEY: return nowDay+dealSerialNumStr(type, 4, true);
            }
        }
        return "";
    }

    private String dealSerialNumStr(String type,int length,boolean havaDate){
        boolean isAdd = false;
        if (BlankUtil.isNotEmpty(type)&&length>0){
            String serialNumStr = "";
            DmsSerialNum dmsSerialNum = dmsSerialNumMapper.getInfoByPrimaryKey(type);
            if (BlankUtil.isEmpty(dmsSerialNum)){//todo 需要判断是否需要后台增加判断
                dmsSerialNum = new DmsSerialNum();
                dmsSerialNum.setSerialKey(type);
                dmsSerialNum.setSerialNum(0L);
                dmsSerialNum.setSerialDate(0L);
                isAdd = true;
            }
            Date now = new Date();
            //当前时间
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
            //获取今天的日期
            Long nowDay = Long.parseLong(sf.format(now));
            if (havaDate) {//是否有日期判断重置
                if (BlankUtil.isEmpty(dmsSerialNum.getSerialDate())||dmsSerialNum.getSerialDate()==0) {
                    dmsSerialNum.setSerialDate(nowDay);
                    dmsSerialNum.setSerialNum(0L);
                } else {
                    if (nowDay > dmsSerialNum.getSerialDate()) {
                        dmsSerialNum.setSerialDate(nowDay);
                        dmsSerialNum.setSerialNum(0L);
                    }
                }
            }else {
                dmsSerialNum.setSerialDate(nowDay);
            }
            long num = dmsSerialNum.getSerialNum();
            num ++;
            String numStr = String.valueOf(num);
            int numLength = numStr.length();
            String ser = "";
            for(int i=0;i<(length-numLength);i++){
                ser += "0";
            }
            serialNumStr = ser + numStr;
            dmsSerialNum.setSerialNum(num);
            if (isAdd){
                dmsSerialNumMapper.insertHavePrimaryKey(dmsSerialNum);
            }else {
                dmsSerialNumMapper.updateByPrimaryKeySelective(dmsSerialNum);
            }
            return serialNumStr;
        }
        return "";
    }
}