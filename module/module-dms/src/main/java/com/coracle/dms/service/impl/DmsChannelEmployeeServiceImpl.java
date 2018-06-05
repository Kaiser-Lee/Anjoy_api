package com.coracle.dms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsChannelEmployeeMapper;
import com.coracle.dms.dao.mybatis.DmsUserMapper;
import com.coracle.dms.dao.mybatis.JobEntityMapper;
import com.coracle.dms.po.DmsChannel;
import com.coracle.dms.po.DmsChannelEmployee;
import com.coracle.dms.po.DmsUser;
import com.coracle.dms.po.JobEntity;
import com.coracle.dms.service.DmsChannelEmployeeService;
import com.coracle.dms.service.DmsChannelService;
import com.coracle.dms.service.DmsEmployeeService;
import com.coracle.dms.vo.DmsEmployeeVo;
import com.coracle.dms.webservice.AnjoySynClient;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.po.DmsChannelMutilVo;
import com.coracle.yk.xframework.util.StringUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.xiruo.medbid.components.date.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DmsChannelEmployeeServiceImpl extends BaseServiceImpl<DmsChannelEmployee> implements DmsChannelEmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(DmsChannelEmployeeServiceImpl.class);

    @Autowired
    private DmsChannelEmployeeMapper dmsChannelEmployeeMapper;
    @Autowired
    private JobEntityMapper jobEntityMapper;
    @Autowired
    private DmsChannelService dmsChannelService;
    @Autowired
    private DmsUserMapper dmsUserMapper;
    @Autowired
    private DmsEmployeeService dmsEmployeeService;

    @Override
    public IMybatisDao<DmsChannelEmployee> getBaseDao() {
        return dmsChannelEmployeeMapper;
    }

    @Override
    public void deleteByEmployeeID(Long EmployeeId) {
        dmsChannelEmployeeMapper.deleteByEmployeeID(EmployeeId);
    }


    /**
     * 通过业务员获取经销商
     * @param userId：用户ID
     * @return
     */
    @Override
    public List<DmsChannelMutilVo> selectChannelList(Long userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);

        return dmsChannelEmployeeMapper.selectChannelList(paramMap);
    }

    /**
     * 同步安井 经销商-业务员 关系数据
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public void anjoySyn() {
        logger.info("*************************** start 开始同步安井渠道信息 ***************************");

        JobEntity jobEntity = null;
        String lastUpdateString = null;
        try {
            JobEntity jobEntityCondition = new JobEntity();
            jobEntityCondition.setJobMethod("syncChannelEmployee");
            jobEntity = jobEntityMapper.selectOneByCondition(jobEntityCondition);
            Date lastUpdate = jobEntity.getLastUpdatedDate();
            if(lastUpdate != null){
                lastUpdateString = DateUtils.formatDate(lastUpdate, DateUtils.DATETIME);
            }
        }catch (Exception e){
            logger.error("经销商-业务员定时任务获取失败：syncChannelEmployee");
        }

        JSONArray jsonArray = AnjoySynClient.getAnjoyChannelEmployee(lastUpdateString);
        if(jsonArray == null || jsonArray.size() < 1){
            logger.info("***************** 安井没返回渠道-业务员数据 *****************");
            return;
        }

        /**
         * 同步安井渠道-业务员数据前先将DMS所有渠道-业务员数据置为：已删除状态
         */
        Integer batchDeleteResult = dmsChannelEmployeeMapper.deleteChannelEmployeeSyncAnjoy();
        logger.info("DMD，共删除：{} 条渠道-业务员数据", batchDeleteResult);

        //anjoy code - 渠道实体 映射集
        Map<String, DmsChannel> dmsChannelMap = dmsChannelService.getChannelMap();
        List<DmsChannelEmployee> dmsChannelEmployeeList = new ArrayList<>();

        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
            DmsChannelEmployee channelEmployee = new DmsChannelEmployee();
            String channelCode = jsonObject.getString("CUSTOMERNUMER");//渠道CODE
            String channelName = jsonObject.getString("FCUSTOMERNAME");//渠道名称
            String empFid = jsonObject.getString("FID");//业务员账号
            String empAccount = jsonObject.getString("ASSESSMENTSALENUMBER");//业务员账号
            String empName = jsonObject.getString("ASSESSMENTSALENAME");//业务员名

            channelEmployee.setAnjoyId(empFid);
            if(StringUtil.isNotBlank(channelCode) && dmsChannelMap.get(channelCode) != null){
                DmsChannel dmsChannel = dmsChannelMap.get(channelCode);

                DmsUser dmsUserCondition = new DmsUser();
                dmsUserCondition.setAccount(empAccount);
                dmsUserCondition.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
                List<DmsUser> dmsUserList = dmsUserMapper.selectByCondition(dmsUserCondition);
                DmsUser dmsUser = null;//判断账号是否已存在
                if(dmsUserList != null && dmsUserList.size() > 0){
                    dmsUser = dmsUserList.get(0);
                }

                if(dmsUser != null && dmsUser.getId() != null){
                    channelEmployee.setChannelId(dmsChannel.getId());
                    channelEmployee.setUserId(dmsUser.getId());
                    channelEmployee.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
                    List<DmsChannelEmployee> list = dmsChannelEmployeeMapper.selectByCondition(channelEmployee);
                    if(list != null && list.size() > 0){
                        channelEmployee = list.get(0);
                        channelEmployee.setLastUpdateBy(0L);
                        channelEmployee.setLastUpdateDate(new Date());
                        channelEmployee.setName(empName);

                        dmsChannelEmployeeMapper.updateByPrimaryKeySelective(channelEmployee);
                    }else {
                        channelEmployee.setCreateBy(0L);
                        channelEmployee.setCreateDate(new Date());
                        channelEmployee.setLastUpdateBy(0L);
                        channelEmployee.setLastUpdateDate(new Date());
                        channelEmployee.setName(empName);

                        dmsChannelEmployeeMapper.insert(channelEmployee);
                    }
                }else {
                    channelEmployee.setChannelId(dmsChannel.getId());
                    channelEmployee.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
                    channelEmployee.setCreateBy(0L);
                    channelEmployee.setCreateDate(new Date());
                    channelEmployee.setLastUpdateBy(0L);
                    channelEmployee.setLastUpdateDate(new Date());
                    channelEmployee.setName(empName);
                    channelEmployee.setAccount(empAccount);

                    dmsChannelEmployeeList.add(channelEmployee);
                }
            }
        }

        if(dmsChannelEmployeeList.size() > 0){
            for (DmsChannelEmployee dmsChannelEmployee : dmsChannelEmployeeList){
                DmsEmployeeVo employee = new DmsEmployeeVo();
                employee.setCreatedBy(dmsChannelEmployee.getCreateBy());
                employee.setCreatedDate(dmsChannelEmployee.getCreateDate());
                employee.setLastUpdatedBy(dmsChannelEmployee.getLastUpdateBy());
                employee.setLastUpdatedDate(dmsChannelEmployee.getLastUpdateDate());
                employee.setName(dmsChannelEmployee.getName());
                employee.setAccount(dmsChannelEmployee.getAccount());
                employee.setPlainPassword(DmsModuleEnums.DEFAULT_PASSWORD);
                employee.setStatus(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
                employee.setSex(0);
                employee.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

                dmsEmployeeService.insertOrUpdate(employee);

                dmsChannelEmployee.setUserId(employee.getUserId());
                dmsChannelEmployeeMapper.insert(dmsChannelEmployee);
            }
        }

    }

}