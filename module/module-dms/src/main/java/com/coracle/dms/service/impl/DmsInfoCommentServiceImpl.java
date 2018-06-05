package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsChannelInformationMapper;
import com.coracle.dms.dao.mybatis.DmsInfoCommentMapper;
import com.coracle.dms.dao.mybatis.DmsUserMapper;
import com.coracle.dms.dto.DmsUserInfoDto;
import com.coracle.dms.po.DmsChannelInformation;
import com.coracle.dms.po.DmsInfoComment;
import com.coracle.dms.service.DmsInfoCommentService;
import com.coracle.dms.vo.DmsInfoCommentVo;
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
public class DmsInfoCommentServiceImpl extends BaseServiceImpl<DmsInfoComment> implements DmsInfoCommentService {
    private static final Logger logger = Logger.getLogger(DmsInfoCommentServiceImpl.class);

    @Autowired
    private DmsInfoCommentMapper dmsInfoCommentMapper;
    @Autowired
    private DmsChannelInformationMapper dmsChannelInformationMapper;
    @Autowired
    private DmsUserMapper dmsUserMapper;

    @Override
    public IMybatisDao<DmsInfoComment> getBaseDao() {
        return dmsInfoCommentMapper;
    }

    /****
     * 插入新闻评论
     * @param dmsInfoComment
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void insertNewsComment(DmsInfoComment dmsInfoComment) {
        checkParams(dmsInfoComment);
        Long newsId=dmsInfoComment.getNewsId();//资讯id
        DmsChannelInformation dmsChannelInformation=dmsChannelInformationMapper.selectByPrimaryKey(newsId);
        if(null==dmsChannelInformation){
            throw new ServiceException("无法获取id为"+newsId+"的渠道赋能资讯信息！");
        }
        if(dmsInfoComment.getType()==0&&dmsChannelInformation.getIsComment()==0){
            throw new ServiceException("该条渠道赋能资讯不支持评论！");
        }
        dmsInfoComment.setRelatedType(0);
        //插入评论信息
        dmsInfoCommentMapper.insert(dmsInfoComment);
        //转发或者是评论量+1
        DmsChannelInformation news=new DmsChannelInformation();
        news.setId(newsId);
        news.setLastUpdatedBy(dmsInfoComment.getLastUpdatedBy());
        news.setLastUpdatedDate(new Date());
        if(dmsInfoComment.getType()==0){
            news.setCommentCount(dmsChannelInformation.getCommentCount()+1);
            dmsChannelInformationMapper.updateByPrimaryKeySelective(news);
        }else if(dmsInfoComment.getType()==1){
            news.setForwardCount(dmsChannelInformation.getForwardCount()+1);
            dmsChannelInformationMapper.updateByPrimaryKeySelective(news);
        }
    }

    /**
     * 分页查询评论列表
     * @param dmsInfoCommentVo
     * @return
     */
    @Override
    public PageHelper.Page<DmsInfoCommentVo> selectDmsInforsCommentPage(DmsInfoCommentVo dmsInfoCommentVo) {
        try {
            PageHelper.startPage(dmsInfoCommentVo.getP(),dmsInfoCommentVo.getS());
            dmsInfoCommentMapper.selectPageByCondition(dmsInfoCommentVo);
        } catch (Exception e) {
            System.out.println("分页查询异常"+e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }

    /**
     * 删除评论，同时修改新闻中的评论数目
     * @param dmsInfoComment
     * @param ids
     */
    public void deleteInforsCommentByDeal(DmsInfoComment dmsInfoComment,List<Long> ids){
        updateByIdsSelective(dmsInfoComment,ids);
        List<Long> infors = findInforsIds(ids);
        if(BlankUtil.isNotEmpty(infors)){
            dmsChannelInformationMapper.modifyComentCount(infors,1);
        }
    }

    /**
     * 按照评论的id列表查询对应新闻的id列表
     * @param ids
     * @return
     */
    public List<Long> findInforsIds(List<Long> ids) {
        Map<String,Object> map= dmsInfoCommentMapper.selectInforsId(ids);
        if(BlankUtil.isNotEmpty(map)){
            List<Long> list=new ArrayList<>();
            for(String newId:(map.get("inforsIds")+"").split(",")){
                list.add(Long.parseLong(newId));
            }
            return  list;
        }
        return new ArrayList<Long>();
    }

    /**
     * 校验参数
     * @param dmsInfoComment
     */
    private void checkParams(DmsInfoComment dmsInfoComment){
        if (dmsInfoComment == null) {
            throw new ServiceException("参数异常");
        }
        if (StringUtils.isBlank(dmsInfoComment.getContent())) {
            throw new ServiceException("评论内容不能为空");
        }
    }
}