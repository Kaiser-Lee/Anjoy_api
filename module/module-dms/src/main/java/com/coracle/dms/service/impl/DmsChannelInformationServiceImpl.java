package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsChannelInformationMapper;
import com.coracle.dms.dao.mybatis.DmsUserMapper;
import com.coracle.dms.po.DmsChannelInformation;
import com.coracle.dms.po.DmsPublishRrange;
import com.coracle.dms.service.DmsChannelInformationService;
import com.coracle.dms.service.DmsPublishRrangeService;
import com.coracle.dms.service.DmsTreeRelationService;
import com.coracle.dms.vo.DmsChannelInformationVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
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
public class DmsChannelInformationServiceImpl extends BaseServiceImpl<DmsChannelInformation> implements DmsChannelInformationService {
    private static final Logger logger = Logger.getLogger(DmsChannelInformationServiceImpl.class);

    @Autowired
    private DmsChannelInformationMapper dmsChannelInformationMapper;
    @Autowired
    private DmsPublishRrangeService dmsPublishRrangeService;
    @Autowired
    private DmsTreeRelationService dmsTreeRelationService;

    @Override
    public IMybatisDao<DmsChannelInformation> getBaseDao() {
        return dmsChannelInformationMapper;
    }

    /**
     * 添加渠道赋能，返回主键ID
     * @param dmsChannelInformationVo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public long insertChannelInformation(DmsChannelInformationVo dmsChannelInformationVo){
        checkParams(dmsChannelInformationVo);
        List<Long> rangeList = dmsChannelInformationVo.getRangeList();
        if (BlankUtil.isEmpty(rangeList)){
            rangeList = dmsTreeRelationService.getAllIdsByNewsType();
        }
        dmsChannelInformationVo.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        dmsChannelInformationVo.setIsPublish(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
        dmsChannelInformationVo.setIsShow(0);
        dmsChannelInformationVo.setCommentCount(0L);
        dmsChannelInformationVo.setForwardCount(0L);
        dmsChannelInformationVo.setClickCount(0L);
        dmsChannelInformationMapper.insert(dmsChannelInformationVo);
        List<DmsPublishRrange> dmsPublishRrangeList = new ArrayList<>();
        if(BlankUtil.isNotEmpty(rangeList)) {
            for (Long id : rangeList) {
                DmsPublishRrange dmsPublishRrange = new DmsPublishRrange();
                dmsPublishRrange.setCreatedBy(dmsChannelInformationVo.getCreatedBy());
                dmsPublishRrange.setCreatedDate(new Date());
                dmsPublishRrange.setLastUpdatedBy(dmsChannelInformationVo.getCreatedBy());
                dmsPublishRrange.setLastUpdatedDate(new Date());
                dmsPublishRrange.setRelatedType(DmsModuleEnums.NEWS_RELATED_TYPE.DMS_CHANNEL_INFORMATION.getType());
                dmsPublishRrange.setRelatedId(dmsChannelInformationVo.getId());
                dmsPublishRrange.setPublishRangeId(id);
                dmsPublishRrange.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
                dmsPublishRrangeList.add(dmsPublishRrange);
            }
            dmsPublishRrangeService.batchInsertRange(dmsPublishRrangeList);
        }
        return dmsChannelInformationVo.getId();
    }


    /**
     * 修改渠道赋能
     * @param dmsChannelInformationVo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void modifyChannelInformation(DmsChannelInformationVo dmsChannelInformationVo){
        checkParams(dmsChannelInformationVo);
        List<Long> rangeList = dmsChannelInformationVo.getRangeList();
        if (BlankUtil.isEmpty(rangeList)){
            rangeList = dmsTreeRelationService.getAllIdsByNewsType();
        }
        DmsChannelInformation dmsChannelInformation2=dmsChannelInformationMapper.selectByPrimaryKey(dmsChannelInformationVo.getId());
        if(dmsChannelInformation2==null){
            throw new ServiceException("无法获取id为"+dmsChannelInformationVo.getId()+"的渠道赋能资讯信息！");
        }
        dmsChannelInformationVo.setIsPublish(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
        dmsChannelInformationMapper.updateByPrimaryKeySelective(dmsChannelInformationVo);
        //先把原先的范围删除
        DmsPublishRrange range = new DmsPublishRrange();
        range.setRelatedType(DmsModuleEnums.NEWS_RELATED_TYPE.DMS_CHANNEL_INFORMATION.getType());
        range.setRelatedId(dmsChannelInformationVo.getId());
        dmsPublishRrangeService.deleteByRelatedInfo(range);
        //再添加新的范围
        List<DmsPublishRrange> dmsPublishRrangeList = new ArrayList<>();
        if(BlankUtil.isNotEmpty(rangeList)) {
            for (Long id : rangeList) {
                DmsPublishRrange dmsPublishRrange = new DmsPublishRrange();
                dmsPublishRrange.setCreatedBy(dmsChannelInformationVo.getCreatedBy());
                dmsPublishRrange.setCreatedDate(new Date());
                dmsPublishRrange.setLastUpdatedBy(dmsChannelInformationVo.getCreatedBy());
                dmsPublishRrange.setLastUpdatedDate(new Date());
                dmsPublishRrange.setRelatedType(DmsModuleEnums.NEWS_RELATED_TYPE.DMS_CHANNEL_INFORMATION.getType());
                dmsPublishRrange.setRelatedId(dmsChannelInformationVo.getId());
                dmsPublishRrange.setPublishRangeId(id);
                dmsPublishRrange.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
                dmsPublishRrangeList.add(dmsPublishRrange);
            }
            dmsPublishRrangeService.batchInsertRange(dmsPublishRrangeList);
        }
    }

    /**
     * 分页获取渠道赋能列表
     * @param dmsChannelInformationVo
     * @return
     */
    public PageHelper.Page<DmsChannelInformationVo> selectInformationPageList(DmsChannelInformationVo dmsChannelInformationVo){
        try {
            PageHelper.startPage(dmsChannelInformationVo.getP(),dmsChannelInformationVo.getS());
            dmsChannelInformationMapper.selectPageByCondition(dmsChannelInformationVo);
        } catch (Exception e) {
            System.out.println("分页查询异常"+e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }

    @Override
    public DmsChannelInformationVo selectVoByPrimaryKey(Long id) {
        DmsChannelInformationVo dmsChannelInformationVo = dmsChannelInformationMapper.selectVoByPrimaryKey(id);
        List<Long> rangeList = dmsChannelInformationVo.getRangeList();
        if (BlankUtil.isNotEmpty(rangeList)) {
            List<Map<String, Object>> rangeInforList = dmsPublishRrangeService.getPublishRangeNameIds(rangeList);
            dmsChannelInformationVo.setRangeInfoList(rangeInforList);
        }
        return dmsChannelInformationVo;
    }

    /**
     * 分页获取渠道赋能列表
     * @param id
     * @param p
     * @param s
     * @return
     */
    public PageHelper.Page<DmsChannelInformationVo> selectChannelInforsForPageList(Long id,Integer p,Integer s,String type,String title){
        try {
            PageHelper.startPage(p,s);
            dmsChannelInformationMapper.getChannelInforsList(id,type,title);
        } catch (Exception e) {
            System.out.println("分页查询异常"+e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }

    /**
     * 校验参数
     * @param dmsChannelInformationVo
     */
    private void checkParams(DmsChannelInformationVo dmsChannelInformationVo){
        if (dmsChannelInformationVo == null) {
            throw new ServiceException("参数异常");
        }
        if (BlankUtil.isEmpty(dmsChannelInformationVo.getTitle())) {
            throw new ServiceException("标题不能为空");
        }
        if (BlankUtil.isEmpty(dmsChannelInformationVo.getContent())) {
            throw new ServiceException("正文不能为空");
        }
        if (dmsChannelInformationVo.getType()==null){
            throw new ServiceException("请选择分类");
        }
    }
}