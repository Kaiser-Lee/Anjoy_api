package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsMessageMapper;
import com.coracle.dms.po.DmsMessage;
import com.coracle.dms.service.DmsMessageService;
import com.coracle.dms.service.DmsUserService;
import com.coracle.dms.vo.DmsMessageVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Maps;
import com.xiruo.medbid.components.HttpRequestUtils;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DmsMessageServiceImpl extends BaseServiceImpl<DmsMessage> implements DmsMessageService {
    private static final Logger logger = LoggerFactory.getLogger(DmsMessageServiceImpl.class);

    @Value("${mxm.url}")
    private String mxmUrl;

    @Autowired
    private DmsMessageMapper dmsMessageMapper;

    @Autowired
    private DmsUserService dmsUserService;

    @Override
    public IMybatisDao<DmsMessage> getBaseDao() {
        return dmsMessageMapper;
    }

    /**
     * 发消息给品牌商，由于品牌商共用所有消息，所以没有接收人
     * @param messageType
     * @param title
     * @param content
     */
    public void sendMessage(Integer messageType,Integer entityType, Long entityId, String title,String subject, String content){
        sendMessage(messageType,entityType,entityId,title,subject,content,0L);
    }

    /**
     * 发消息给指定用户
     * @param messageType
     * @param entityType
     * @param entityId
     * @param title
     * @param content
     * @param id
     */
    public void sendMessage(Integer messageType, Integer entityType, Long entityId,
                            String title,String subject, String content, Long id) {
        List<Long> staffIds = new ArrayList<>();
        staffIds.add(id);
        sendMessage(messageType, entityType, entityId, title,subject, content, staffIds);
    }

    /**
     * 发消息给指定用户群体
     * @param messageType
     * @param entityType
     * @param entityId
     * @param title
     * @param content
     * @param staffIds
     */
    public void sendMessage(Integer messageType, Integer entityType, Long entityId,
                            String title,String subject, String content, List<Long> staffIds){
        sendMessage(messageType,entityType,entityId,title,subject,content,staffIds,1);
    }

    /**
     * 发消息给指定用户群体
     * @param messageType
     * @param entityType
     * @param entityId
     * @param title
     * @param content
     * @param staffIds
     */
    public void sendMessage(Integer messageType, Integer entityType, Long entityId,
                            String title,String subject, String content, List<Long> staffIds,Integer isShow){
        sendMessage(messageType,entityType,entityId,title,subject,content,staffIds,isShow,0L);
    }
   /**
     * 发消息给指定用户群体
     * @param messageType
     * @param entityType
     * @param entityId
     * @param title
     * @param content
     * @param staffIds
     */
    public void sendMessage(Integer messageType, Integer entityType, Long entityId,
                            String title,String subject, String content, List<Long> staffIds,Integer isShow,Long createBy){
        Long time1 = System.currentTimeMillis();
        if(BlankUtil.isNotEmpty(staffIds)){
            List<DmsMessage> dmsMessageList=new ArrayList<>();
            DmsMessage messageEntity = null;
            Date date = new Date();
            for(Long staffId:staffIds){
                messageEntity=new DmsMessage();
                //消息类型，
                if(messageType==null){
                    messageEntity.setMessageType(DmsModuleEnums.MESSAGE_TYPE.MESSAGE_SYSTEM.getType());
                } else {
                    messageEntity.setMessageType(messageType);
                }
                messageEntity.setEntityId(entityId);
                messageEntity.setIsSend(1);
                messageEntity.setIsShow(isShow);
                messageEntity.setIsRead(0);
                if (entityType==null){
                    messageEntity.setEntityType(DmsModuleEnums.MESSAGE_ENTITY_TYPE.SYS_MESSAGE.getType());
                }else {
                    messageEntity.setEntityType(entityType);
                }
                //设置消息的title
                messageEntity.setTitle(title);
                messageEntity.setSubject(subject);
                //设置消息的内容
                messageEntity.setContent(content);
                messageEntity.setCreatedDate(date);
                if (BlankUtil.isEmpty(createBy)) createBy = 0L;
                messageEntity.setCreatedBy(createBy);
                messageEntity.setLastUpdatedDate(date);
                messageEntity.setLastUpdatedBy(createBy);
                messageEntity.setRemoveFlag(0);
                messageEntity.setId(null);
                messageEntity.setStaffId(staffId);
                messageEntity.setRemark("");
                dmsMessageList.add(messageEntity);
            }
            if(BlankUtil.isNotEmpty(dmsMessageList)){
                dmsMessageMapper.batchInsert(dmsMessageList);
            }
        }

        /* 调用消息推送服务 */
        if (!BlankUtil.isEmpty(staffIds) && isShow == 1) {
            push(title, subject, staffIds);
        }
        Long time2 = System.currentTimeMillis();

        logger.info("************消息推送2:{}********",(time2-time1)/1000);
    }

    /**
     * 发送消息
     * @param dmsMessage
     */
    public void sendMessage(DmsMessage dmsMessage){
        Date date = new Date();
        dmsMessage.setId(null);
        dmsMessage.setIsSend(1);
        dmsMessage.setIsRead(0);
        dmsMessage.setCreatedDate(date);
        dmsMessage.setLastUpdatedDate(date);
        dmsMessage.setRemoveFlag(0);
        dmsMessage.setRemark("");
        dmsMessageMapper.insert(dmsMessage);
    }

    /**
     * 设置消息已读
     */
    @Transactional(rollbackFor = Exception.class)
    public void read(List<Long> ids) {
        dmsMessageMapper.read(ids);
    }

    /**
     * 获取未读消息数量
     * @param userId
     * @return
     */
    public Long getCount(long userId) {
        /*Long staffId=userSession.getId();
        System.out.println(dmsMessageMapper.getCount(staffId));*/
        return dmsMessageMapper.getCount(userId);
    }

    /**
     * 获取pc端的未读消息数量
     *
     * @return
     */
    @Override
    public Long getCountForPC() {
        return dmsMessageMapper.getCountForPC();
    }

    /**
     * 获取我的消息列表
     */
    public PageHelper.Page<DmsMessageVo> selectForListPage(DmsMessageVo message) {
        try{
            PageHelper.startPage(message.getP(),message.getS());
            dmsMessageMapper.getPageList(message);
            return PageHelper.endPage();
        }catch (Exception e){
            PageHelper.endPage();
            logger.error("获取我的消息列表异常--->",e);
            throw new ServiceException("获取我的消息列表异常",e);
        }
    }

    /**
     * 获取未发送的消息，但存在还未进行审核就已经再次修改，有发送了一条消息，所以获取最新的一条
     * @param messageType
     * @param entityType
     * @param entityId
     * @param staffId
     * @return
     */
    public DmsMessage getNoSendMessage(Integer messageType,Integer entityType,Long entityId,Long staffId){
        DmsMessage dm = new DmsMessage();
        dm.setStaffId(staffId);
        dm.setIsSend(0);
        dm.setMessageType(messageType);
        dm.setEntityType(entityType);
        dm.setEntityId(entityId);
        dm.setOrderField(" id ");
        dm.setOrderString(" desc ");
        List<DmsMessage> dmsMessageList = dmsMessageMapper.selectByCondition(dm);
        if (BlankUtil.isNotEmpty(dmsMessageList))
            return dmsMessageList.get(0);
        return null;
    }

    /**
     * 更新发送状态
     * @param id
     */
    public void updateSendStatus(Long id,Integer status){
        DmsMessage dmsMessage = new DmsMessage();
        dmsMessage.setIsSend(status);
        dmsMessage.setId(id);
        dmsMessageMapper.updateByPrimaryKeySelective(dmsMessage);
    }

    /**
     * 消息推送
     * @param title
     * @param content
     * @param userIdList
     */
    @Override
    public void push(String title, String content, List<Long> userIdList) {
        List<Long> mxmUserIdList = dmsUserService.selectMxmIdByUserIdList(userIdList);

        System.out.println("url:"+mxmUrl);
        String url = mxmUrl + "/push/systemMsg";
        Map<String, Object> param = Maps.newHashMap();
        param.put("title", title);
        param.put("content", content);
        param.put("userIdList", mxmUserIdList);

        JSONObject json = JSONObject.fromObject(param);

        try {
            JSONObject response = HttpRequestUtils.doPostByJson(url, json);
            Boolean success = response.getBoolean("status");
            if (!success) {
                logger.error("消息推送失败: " + response.getString("errorMessage"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("消息推送异常: " + e.getMessage());
        }
    }
}