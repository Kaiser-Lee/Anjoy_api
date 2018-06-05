package com.coracle.dms.service;

import com.coracle.dms.po.DmsMessage;
import com.coracle.dms.vo.DmsMessageVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsMessageService extends IBaseService<DmsMessage> {

    /**
     * 发送消息
     * @param dmsMessage
     */
    void sendMessage(DmsMessage dmsMessage);

    void sendMessage(Integer messageType, Integer entityType, Long entityId, String title,String subject, String content, List<Long> staffIds,Integer isShow,Long createBy);

    /**
     * 发消息给品牌商，由于品牌商共用所有消息，所以没有接收人
     * @param messageType
     * @param title
     * @param content
     */
    void sendMessage(Integer messageType,Integer entityType, Long entityId, String title,String subject, String content);

    /**
     * 发送消息
     * @param messageType
     * @param entityType
     * @param entityId
     * @param title
     * @param content
     * @param id
     */
    void sendMessage(Integer messageType, Integer entityType, Long entityId, String title,String subject, String content, Long id);

    /**
     * 发送消息
     * @param messageType
     * @param entityType
     * @param entityId
     * @param title
     * @param content
     * @param staffIds
     */
    void sendMessage(Integer messageType, Integer entityType, Long entityId, String title,String subject, String content, List<Long> staffIds);

    /**
     * 发消息给指定用户群体
     * @param messageType
     * @param entityType
     * @param entityId
     * @param title
     * @param content
     * @param staffIds
     */
    void sendMessage(Integer messageType, Integer entityType, Long entityId,
                            String title,String subject, String content, List<Long> staffIds,Integer isSend);

    /**
     * 获取未读消息数量
     * @param userId
     * @return
     */
    Long getCount(long userId);

    /**
     * 设置消息已读
     */
    void read(List<Long> ids);

    /**
     * 获取pc端的未读消息数量
     *
     * @return
     */
    Long getCountForPC();

    /**
     * 获取我的消息列表
     */
    PageHelper.Page<DmsMessageVo> selectForListPage(DmsMessageVo message);

    /**
     * 获取未发送的消息，但存在还未进行审核就已经再次修改，有发送了一条消息，所以获取最新的一条
     * @param messageType
     * @param entityType
     * @param entityId
     * @param staffId
     * @return
     */
    DmsMessage getNoSendMessage(Integer messageType,Integer entityType,Long entityId,Long staffId);

    /**
     * 更新发送状态
     * @param id
     */
    void updateSendStatus(Long id,Integer status);

    void push(String title, String content, List<Long> userIdList);
}