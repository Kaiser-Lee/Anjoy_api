package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsNoticeMapper;
import com.coracle.dms.dao.mybatis.DmsUserMapper;
import com.coracle.dms.po.DmsNotice;
import com.coracle.dms.po.DmsPublishRrange;
import com.coracle.dms.po.DmsTreeRelation;
import com.coracle.dms.service.*;
import com.coracle.dms.vo.DmsNoticeVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.xiruo.medbid.components.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DmsNoticeServiceImpl extends BaseServiceImpl<DmsNotice> implements DmsNoticeService {
    private static final Logger logger = Logger.getLogger(DmsNoticeServiceImpl.class);

    @Autowired
    private DmsNoticeMapper dmsNoticeMapper;
    @Autowired
    private DmsUserMapper dmsUserMapper;
    @Autowired
    private DmsMessageService dmsMessageService;
    @Autowired
    private DmsPublishRrangeService dmsPublishRrangeService;
    @Autowired
    private DmsTreeRelationService dmsTreeRelationService;
    @Autowired
    private DmsChannelContactsService dmsChannelContactsService;
    @Autowired
    private DmsSellerService dmsSellerService;

    @Override
    public IMybatisDao<DmsNotice> getBaseDao() {
        return dmsNoticeMapper;
    }

    /**
     * 添加通知，返回主键ID
     * @param dmsNoticeVo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public long insertNotice(DmsNoticeVo dmsNoticeVo){
        checkParams(dmsNoticeVo);
        List<Long> rangeList = dmsNoticeVo.getRangeList();//tree_relation中的id列表 不考虑品牌商，只考虑渠道，门店
        if (BlankUtil.isEmpty(rangeList)){
            rangeList = dmsTreeRelationService.getAllIdsByNewsType();
        }
        dmsNoticeVo.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        dmsNoticeVo.setIsPublish(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
        dmsNoticeMapper.insert(dmsNoticeVo);
        //添加发布范围
        List<DmsPublishRrange> dmsPublishRrangeList = new ArrayList<>();
        if(BlankUtil.isNotEmpty(rangeList)) {
            for (Long id : rangeList) {
                DmsPublishRrange dmsPublishRrange = new DmsPublishRrange();
                dmsPublishRrange.setCreatedBy(dmsNoticeVo.getCreatedBy());
                dmsPublishRrange.setCreatedDate(new Date());
                dmsPublishRrange.setLastUpdatedBy(dmsNoticeVo.getCreatedBy());
                dmsPublishRrange.setLastUpdatedDate(new Date());
                dmsPublishRrange.setRelatedType(DmsModuleEnums.NEWS_RELATED_TYPE.DMS_NOTICE.getType());
                dmsPublishRrange.setRelatedId(dmsNoticeVo.getId());
                dmsPublishRrange.setPublishRangeId(id);
                dmsPublishRrange.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
                dmsPublishRrangeList.add(dmsPublishRrange);
            }
            dmsPublishRrangeService.batchInsertRange(dmsPublishRrangeList);
            // 发送消息记录 ，需要处理用户ID,到底给谁发消息,根据发布范围控制
            List<DmsTreeRelation> list = dmsTreeRelationService.getListByIds(rangeList);
            List<Long> channelList = new ArrayList<>();
            List<Long> storeList = new ArrayList<>();
            if (BlankUtil.isNotEmpty(list)) {
                for (DmsTreeRelation dtr : list) {
                    if (dtr.getRelatedType() == DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType())
                        channelList.add(dtr.getRelatedId());
                    else if (dtr.getRelatedType() == DmsModuleEnums.TREE_RELATED_TYPE.DMS_STORE.getType())
                        storeList.add(dtr.getRelatedId());
                }
            }
            List<Long> staffIds = new ArrayList<>();
            if (BlankUtil.isNotEmpty(channelList)) {
                List<Long> channelUserIds = dmsChannelContactsService.getUserIdsByChannelIds(channelList);
                if (BlankUtil.isNotEmpty(channelUserIds)) staffIds.addAll(channelUserIds);
            }
            if (BlankUtil.isNotEmpty(storeList)) {
                List<Long> storeUserIds = dmsSellerService.getUserIdsByStoreIds(storeList);
                if (BlankUtil.isNotEmpty(storeUserIds)) staffIds.addAll(storeUserIds);
            }
            int messageType = 0;
            if ("1".equals(dmsNoticeVo.getType())) messageType = DmsModuleEnums.MESSAGE_TYPE.MESSAGE_NOTICE.getType();
            else if ("2".equals(dmsNoticeVo.getType()))
                messageType = DmsModuleEnums.MESSAGE_TYPE.MESSAGE_PROMOTION.getType();
            dmsMessageService.sendMessage(messageType, DmsModuleEnums.MESSAGE_ENTITY_TYPE.DMS_NOTICE.getType(), dmsNoticeVo.getId(),
                    dmsNoticeVo.getTitle(), dmsNoticeVo.getContent(), dmsNoticeVo.getContent(), staffIds);
        }
        return dmsNoticeVo.getId();
    }

    /**
     * 分页查询通知列表，pc查询，app端后面处理
     * @param dmsNoticeVo
     * @return
     */
    public PageHelper.Page<DmsNoticeVo> selectForPcList(DmsNoticeVo dmsNoticeVo){
        try {
            PageHelper.startPage(dmsNoticeVo.getP(),dmsNoticeVo.getS());
            dmsNoticeMapper.selectPageByCondition(dmsNoticeVo);
        } catch (Exception e) {
            System.out.println("分页查询异常"+e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }

    public DmsNoticeVo getNoticeDetail(Long id){
        DmsNoticeVo dmsNoticeVo = dmsNoticeMapper.selectVoByPrimaryKey(id);
        List<Long> rangeList = dmsNoticeVo.getRangeList();
        if (BlankUtil.isNotEmpty(rangeList)) {
            List<Map<String, Object>> rangeInforList = dmsPublishRrangeService.getPublishRangeNameIds(rangeList);
            dmsNoticeVo.setRangeInfoList(rangeInforList);
        }
        return dmsNoticeVo;
    }

    /**
     * 校验参数
     * @param dmsNoticeVo
     */
    private void checkParams(DmsNoticeVo dmsNoticeVo){
        if (dmsNoticeVo == null) {
            throw new ServiceException("参数异常");
        }
        if (StringUtils.isBlank(dmsNoticeVo.getTitle())) {
            throw new ServiceException("标题不能为空");
        }
        if (StringUtils.isBlank(dmsNoticeVo.getContent())) {
            throw new ServiceException("正文不能为空");
        }
        if (dmsNoticeVo.getType()==null){
            throw new ServiceException("请选择分类");
        }
    }
}