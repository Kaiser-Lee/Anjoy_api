package com.coracle.dms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsChannelProductAnjoyWhiteListMapper;
import com.coracle.dms.po.DmsChannelProductAnjoyWhiteList;
import com.coracle.dms.service.DmsChannelProductAnjoyWhiteListService;
import com.coracle.dms.vo.DmdChannelProductAnjoyVo;
import com.coracle.dms.webservice.AnjoySynClient;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DmsChannelProductAnjoyWhiteListServiceImpl extends BaseServiceImpl<DmsChannelProductAnjoyWhiteList> implements DmsChannelProductAnjoyWhiteListService {
    private static final Logger logger = Logger.getLogger(DmsChannelProductAnjoyWhiteListServiceImpl.class);

    @Autowired
    private DmsChannelProductAnjoyWhiteListMapper anjoyProductWhiteListMapper;

    @Override
    public IMybatisDao<DmsChannelProductAnjoyWhiteList> getBaseDao() {
        return anjoyProductWhiteListMapper;
    }


    /**
     * 同步安井-经销商产品白名单接口
     */
    @Override
    @Transactional(readOnly = false)
    public void anjoySyn() {
        JSONArray jsonArray = AnjoySynClient.getAnjoyChannelProductWhiteList();
        if(jsonArray == null || jsonArray.size() < 1){
            logger.info("************************ 安井-经销商产品白名单接口 没有返回数据 ************************");
            return;
        }

        List<DmsChannelProductAnjoyWhiteList> dataList = new ArrayList<>();
        //先将dms_channel_product_anjoy_white_list 渠道-销售产品(安井)白名单表 数据清空
        Integer deleteResult = anjoyProductWhiteListMapper.deleteProductWhiteList();
        Date date = new Date();
        Long userId = 0L;
        //渠道编码  客户编码  客户名称  物料编码  物料名称  规格型号  是否生效  控制状态
        //0010	 01.2102.X001	大连泰连商贸有限公司	  001.003.001.003	  450g*12袋装黑米馒头	450g*12  生效  可销
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
            //String channel_cfqdnumber = jsonObject.getString("渠道编码");
            String channel_fnumber = jsonObject.getString("客户编码");
            //String channel_fname_l2 = jsonObject.getString("客户名称");
            String product_fnumber = jsonObject.getString("物料编码");
            //String product_fname_l2 = jsonObject.getString("物料名称");
            //String product_cfspecification = jsonObject.getString("规格型号");
            //String product_cfiseffective = jsonObject.getString("是否生效");
            //String product_cfcontrolstate = jsonObject.getString("控制状态");

            /*Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("channelCode", channel_fnumber);
            paramMap.put("productCode", product_fnumber);
            DmsChannelProductAnjoyWhiteList entity = anjoyProductWhiteListMapper.selectChannelProductExist(paramMap);
            if(entity == null || entity.getId() == null){*/
                DmsChannelProductAnjoyWhiteList anjoyChannelProduct = new DmsChannelProductAnjoyWhiteList();

                anjoyChannelProduct.setChannelCode(channel_fnumber);
                anjoyChannelProduct.setProductCode(product_fnumber);
                anjoyChannelProduct.setCreatedDate(date);
                anjoyChannelProduct.setCreatedBy(userId);
                anjoyChannelProduct.setLastUpdatedDate(date);
                anjoyChannelProduct.setLastUpdatedBy(userId);
                anjoyChannelProduct.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

                dataList.add(anjoyChannelProduct);
            /*    anjoyProductWhiteListMapper.insertSelective(anjoyChannelProduct);
            }else {
                entity.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
                anjoyProductWhiteListMapper.updateByPrimaryKeySelective(entity);
            }*/
        }

        anjoyProductWhiteListMapper.batchInsert(dataList);
        Integer updateResult = anjoyProductWhiteListMapper.updateChannelProductRelation();
    }

    /**
     * 手动导入Excel 安井渠道-产品关系数据
     */
    public Map<String, Object> importAnjoyExcel(List<DmdChannelProductAnjoyVo> dataList) {
        Map<String, Object> dataMap = new HashMap<>();
        //先清空渠道-产品关系数据


        return dataMap;
    }

}