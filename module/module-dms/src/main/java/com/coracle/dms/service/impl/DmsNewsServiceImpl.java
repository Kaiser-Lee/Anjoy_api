package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsNewsMapper;
import com.coracle.dms.dao.mybatis.DmsUserMapper;
import com.coracle.dms.po.DmsNews;
import com.coracle.dms.po.DmsPublishRrange;
import com.coracle.dms.service.DmsNewsService;
import com.coracle.dms.service.DmsPublishRrangeService;
import com.coracle.dms.service.DmsTreeRelationService;
import com.coracle.dms.vo.DmsNewsVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DmsNewsServiceImpl extends BaseServiceImpl<DmsNews> implements DmsNewsService {
    private static final Logger logger = Logger.getLogger(DmsNewsServiceImpl.class);

    @Autowired
    private DmsNewsMapper dmsNewsMapper;
    @Autowired
    private DmsPublishRrangeService dmsPublishRrangeService;
    @Autowired
    private DmsTreeRelationService dmsTreeRelationService;

    @Override
    public IMybatisDao<DmsNews> getBaseDao() {
        return dmsNewsMapper;
    }

    @Override
    public long insertNews(DmsNewsVo dmsNewsVo) {
        checkParams(dmsNewsVo);
        List<Long> rangeList = dmsNewsVo.getRangeList();
        if (BlankUtil.isEmpty(rangeList)){
            rangeList = dmsTreeRelationService.getAllIdsByNewsType();
        }
        if (BlankUtil.isEmpty(dmsNewsVo.getIsShow())) dmsNewsVo.setIsShow(0);
        if (BlankUtil.isEmpty(dmsNewsVo.getIsComment())) dmsNewsVo.setIsComment(1);//不传默认不可评论，最终看需求定
        dmsNewsVo.setIsPublish(1);//新建完以后默认发布
        dmsNewsVo.setCommentCount(0L);
        dmsNewsVo.setClickCount(0L);
        dmsNewsVo.setForwardCount(0L);
        dmsNewsVo.setPublishDate(new Date());
        dmsNewsMapper.insert(dmsNewsVo);
        //添加发布范围
        List<DmsPublishRrange> dmsPublishRrangeList = new ArrayList<>();
        if(BlankUtil.isNotEmpty(rangeList)) {
            for (Long id : rangeList) {
                DmsPublishRrange dmsPublishRrange = new DmsPublishRrange();
                dmsPublishRrange.setCreatedBy(dmsNewsVo.getCreatedBy());
                dmsPublishRrange.setCreatedDate(new Date());
                dmsPublishRrange.setLastUpdatedBy(dmsNewsVo.getCreatedBy());
                dmsPublishRrange.setLastUpdatedDate(new Date());
                dmsPublishRrange.setRelatedType(DmsModuleEnums.NEWS_RELATED_TYPE.DMS_NEWS.getType());
                dmsPublishRrange.setRelatedId(dmsNewsVo.getId());
                dmsPublishRrange.setPublishRangeId(id);
                dmsPublishRrange.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
                dmsPublishRrangeList.add(dmsPublishRrange);
            }
            dmsPublishRrangeService.batchInsertRange(dmsPublishRrangeList);
        }
        return dmsNewsVo.getId();
    }

    /**
     * 修改资讯新闻
     * @param dmsNewsVo
     */
    public void updateNews(DmsNewsVo dmsNewsVo){
        DmsNewsVo newsVo = dmsNewsMapper.selectVoByPrimaryKey(dmsNewsVo.getId());
        if(newsVo==null){
            throw new ServiceException("无法获取id为"+dmsNewsVo.getId()+"的新闻资讯信息！");
        }
        List<Long> rangeList = dmsNewsVo.getRangeList();
        if (BlankUtil.isEmpty(rangeList)){
            rangeList = dmsTreeRelationService.getAllIdsByNewsType();
        }
        if (BlankUtil.isEmpty(dmsNewsVo.getIsShow())) dmsNewsVo.setIsShow(0);
        if (BlankUtil.isEmpty(dmsNewsVo.getIsComment())) dmsNewsVo.setIsComment(1);//不传默认不可评论，最终看需求定
        dmsNewsVo.setIsPublish(1);//新建完以后默认发布
        dmsNewsVo.setPublishDate(new Date());
        dmsNewsMapper.updateByPrimaryKeySelective(dmsNewsVo);
        //先把原先的范围删除
        DmsPublishRrange range = new DmsPublishRrange();
        range.setRelatedType(DmsModuleEnums.NEWS_RELATED_TYPE.DMS_NEWS.getType());
        range.setRelatedId(newsVo.getId());
        range.setLastUpdatedBy(dmsNewsVo.getLastUpdatedBy());
        dmsPublishRrangeService.deleteByRelatedInfo(range);
        //添加发布范围
        List<DmsPublishRrange> dmsPublishRrangeList = new ArrayList<>();
        if(BlankUtil.isNotEmpty(rangeList)) {
            for (Long id : rangeList) {
                DmsPublishRrange dmsPublishRrange = new DmsPublishRrange();
                dmsPublishRrange.setCreatedBy(dmsNewsVo.getLastUpdatedBy());
                dmsPublishRrange.setCreatedDate(new Date());
                dmsPublishRrange.setLastUpdatedBy(dmsNewsVo.getLastUpdatedBy());
                dmsPublishRrange.setLastUpdatedDate(new Date());
                dmsPublishRrange.setRelatedType(DmsModuleEnums.NEWS_RELATED_TYPE.DMS_NEWS.getType());
                dmsPublishRrange.setRelatedId(dmsNewsVo.getId());
                dmsPublishRrange.setPublishRangeId(id);
                dmsPublishRrange.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
                dmsPublishRrangeList.add(dmsPublishRrange);
            }
            dmsPublishRrangeService.batchInsertRange(dmsPublishRrangeList);
        }
    }

    public PageHelper.Page<DmsNewsVo> findNewsPageList(DmsNewsVo dmsNewsVo){
        try {
            PageHelper.startPage(dmsNewsVo.getP(),dmsNewsVo.getS());
            dmsNewsMapper.findDmsNewsPageList(dmsNewsVo);
        } catch (Exception e) {
            System.out.println("分页查询异常"+e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }

    @Override
    public DmsNewsVo selectNewByPrimaryKey(Long id) {
        DmsNewsVo dmsNewsVo = dmsNewsMapper.selectVoByPrimaryKey(id);
        if (BlankUtil.isEmpty(dmsNewsVo)){
            throw new ServiceException("无法获取id为"+id+"的咨询信息！");
        }
        List<Long> rangeList = dmsNewsVo.getRangeList();
        if (BlankUtil.isNotEmpty(rangeList)) {
            List<Map<String, Object>> rangeInforList = dmsPublishRrangeService.getPublishRangeNameIds(rangeList);
            dmsNewsVo.setRangeInfoList(rangeInforList);
            String nameListStr = "";
            if (BlankUtil.isNotEmpty(rangeInforList)){
                for (Map<String,Object> map:rangeInforList){
                    if (BlankUtil.isNotEmpty(map.get("name"))){
                        nameListStr = nameListStr + map.get("name").toString()+";";
                    }
                }
            }
            dmsNewsVo.setRangeText(nameListStr);
        }
        return dmsNewsVo;
    }

    /**
     * 分页获取用户可以看到的新闻
     * @param id 用户所属范围ID
     * @param isShow 是否查询首页显示新闻 1查询首页显示新闻，其他查询全部
     * @param p
     * @param s
     * @return
     */
    public PageHelper.Page<DmsNewsVo> selectNewsPageByUser(Long id,Integer isShow,Integer p,Integer s){
        try {
            PageHelper.startPage(p,s);
            dmsNewsMapper.getNewsListByUser(id,isShow);
            return PageHelper.endPage();
        } catch (Exception e) {
            System.out.println("分页查询异常"+e.getMessage());
            throw new ServiceException("分页查询异常");
        }
    }

    /**
     * 校验参数
     * @param dmsNewsVo
     */
    private void checkParams(DmsNewsVo dmsNewsVo) {
        if (dmsNewsVo == null) {
            throw new ServiceException("参数异常");
        }
        if (BlankUtil.isEmpty(dmsNewsVo.getTitle())) {
            throw new ServiceException("标题不能为空");
        }
        if (BlankUtil.isEmpty(dmsNewsVo.getContent())) {
            throw new ServiceException("正文不能为空");
        }
    }
}