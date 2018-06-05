package com.coracle.dms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsChannelAddressMapper;
import com.coracle.dms.po.DmsChannel;
import com.coracle.dms.po.DmsChannelAddress;
import com.coracle.dms.po.DmsChannelContacts;
import com.coracle.dms.service.DmsChannelAddressService;
import com.coracle.dms.service.DmsChannelContactsService;
import com.coracle.dms.service.DmsChannelService;
import com.coracle.dms.vo.DmsChannelAddressVo;
import com.coracle.dms.vo.DmsChannelContactsVo;
import com.coracle.dms.webservice.AnjoySynClient;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.StringUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DmsChannelAddressServiceImpl extends BaseServiceImpl<DmsChannelAddress> implements DmsChannelAddressService {
    private static final Logger logger = LoggerFactory.getLogger(DmsChannelAddressServiceImpl.class);

    @Autowired
    private DmsChannelAddressMapper dmsChannelAddressMapper;

    @Autowired
    private DmsChannelContactsService dmsChannelContactsService;

    @Autowired
    private DmsChannelService dmsChannelService;

    @Override
    public IMybatisDao<DmsChannelAddress> getBaseDao() {
        return dmsChannelAddressMapper;
    }


    @Override
    public void deleteByAddressID(Long addressId) {

        dmsChannelAddressMapper.deleteByAddressID(addressId);

    }


    /**
     * 根据渠道id获取收货地址列表
     *
     * @return
     * @param session
     */
    @Override
    public List<DmsChannelAddressVo> list(UserSession session) {
        DmsChannelContactsVo channelContacts = dmsChannelContactsService.queryContactByUserId(session.getId());
        Long channelId = channelContacts.getChannelId();
        DmsChannel channel = dmsChannelService.selectByPrimaryKey(channelId);
        return dmsChannelAddressMapper.selectByChannelId(channelId);
    }

    /**
     * 设置默认收货地址
     *
     * @param param
     * @param session
     */
    @Override
    public void setDefault(DmsChannelAddress param, UserSession session) {
        DmsChannelContacts channelContacts = dmsChannelContactsService.queryContactByUserId(session.getId());
        if (channelContacts == null) {
            throw new ServiceException("当前登录账号所属渠道商异常");
        } else {
            Long channelId = channelContacts.getChannelId();
            //先取消掉该渠道的所有默认收货地址
            dmsChannelAddressMapper.cancelDefaultByChannelId(channelId);
        }

        //然后再将用户所选的收货地址设置为该渠道的默认收货地址
        DmsChannelAddress entity = dmsChannelAddressMapper.selectByPrimaryKey(param.getId());
        entity.setIsDefault(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
        dmsChannelAddressMapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 根据渠道id获取默认收货地址
     *
     * @param channelId
     * @return
     */
    @Override
    public DmsChannelAddressVo getDefault(Long channelId) {
        return dmsChannelAddressMapper.getDefaultByChannelId(channelId);
    }

    /**
     * 根据主键获取vo对象
     *
     * @param id
     * @return
     */
    @Override
    public DmsChannelAddressVo getVoByPrimaryKey(Long id) {
        return dmsChannelAddressMapper.getVoByPrimaryKey(id);
    }


    /**
     * 同步安井渠道-收货人地址信息
     * 调用安井的渠道-收货人地址同步接口，返回JSON数组格式数据
     */
    @Override
    @Transactional(readOnly = false)
    public void anjoySyn() {
        logger.info("*************************** start 开始同步安井渠道-收货人地址信息 ***************************");
        JSONArray jsonArray = AnjoySynClient.getAnjoySynDataBySQLType(AnjoySynClient.SYN_TYPE.CHANNEL_ADDRESS);
        if(jsonArray == null || jsonArray.size() < 1){
            logger.info("***************** 安井没有渠道-收货人地址返回道数据 *****************");
            return;
        }

        Integer deleteResult = dmsChannelAddressMapper.deleteChannelAddressSyncAnjoy();
        logger.info("共删除：{} 条渠道-收货地址数据", deleteResult);

        Date curDate = new Date();
        List<DmsChannelAddress> dmsChannelAddressList = new ArrayList<>();
        List<Object> problemDataList = new ArrayList<>();//记录问题数据
        //安井FID-渠道实体
        Map<String, DmsChannel> dmsChannelMap = dmsChannelService.getChannelMap();
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
            String anjoyId = jsonObject.getString("客户ID");//安井渠道客户FID
            String anjoyCode = jsonObject.getString("客户代码");//安井渠道客户编码
            String sendAddress = jsonObject.getString("送货地址");//送货地址
            String linkMan = jsonObject.getString("联系人");
            String mobile = jsonObject.getString("手机");
            String phone = jsonObject.getString("电话");

            DmsChannel dmsChannel = dmsChannelMap.get(anjoyCode);
            if(dmsChannel != null){
                DmsChannelAddress dmsChannelAddress = new DmsChannelAddress();


                if(StringUtil.isNotBlank(sendAddress) && StringUtil.isNotBlank(linkMan)){
                    DmsChannelAddress addressCondition = new DmsChannelAddress();
                    addressCondition.setChannelId(dmsChannel.getId());
                    addressCondition.setReceiveAddress(sendAddress);
                    addressCondition.setRecipientName(linkMan);
                    List<DmsChannelAddress> updateAddressList = dmsChannelAddressMapper.selectByCondition(addressCondition);
                    if(updateAddressList != null && updateAddressList.size() > 0){
                        dmsChannelAddress = updateAddressList.get(0);
                        dmsChannelAddress.setChannelId(dmsChannel.getId());
                        dmsChannelAddress.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

                        this.updateByPrimaryKeySelective(dmsChannelAddress);
                    }else {
                        dmsChannelAddress.setChannelId(dmsChannel.getId());
                        dmsChannelAddress.setReceiveAddress(sendAddress);
                        dmsChannelAddress.setRecipientName(linkMan);
                        dmsChannelAddress.setMobile(mobile);
                        dmsChannelAddress.setCreatedDate(curDate);
                        dmsChannelAddress.setCreatedBy(0L);
                        dmsChannelAddress.setUpdateDate(curDate);
                        dmsChannelAddress.setUpdateBy(0L);
                        dmsChannelAddress.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
                        dmsChannelAddress.setIsDefault(0);

                        dmsChannelAddressList.add(dmsChannelAddress);
                    }
                }else {
                    jsonObject.put("ANJOY-DATA-ERROR-ADDRESS-MOBILE", "电话 或 收货地址 有问题!!!");
                    problemDataList.add(jsonObject);
                }
            }else {
                jsonObject.put("ANJOY-DATA-ERROR-CHANNEL", "经销商不存在!!!");
                problemDataList.add(jsonObject);
            }
        }

        if(dmsChannelAddressList.size() > 0){
            dmsChannelAddressMapper.batchInsert2(dmsChannelAddressList);
        }
        if(problemDataList.size() > 0){
            logger.info("安井渠道-收货人 异常数据：{}", JSON.toJSONString(problemDataList));
        }
        logger.info("*************************** end 结束同步安井渠道-收货人地址信息 ***************************");
    }
}