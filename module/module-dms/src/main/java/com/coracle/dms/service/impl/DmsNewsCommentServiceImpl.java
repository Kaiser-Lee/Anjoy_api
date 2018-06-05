package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsNewsCommentMapper;
import com.coracle.dms.dao.mybatis.DmsNewsMapper;
import com.coracle.dms.dao.mybatis.DmsUserMapper;
import com.coracle.dms.po.DmsNews;
import com.coracle.dms.po.DmsNewsComment;
import com.coracle.dms.service.DmsNewsCommentService;
import com.coracle.dms.vo.DmsNewsCommentVo;
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

import java.util.*;

@Service
public class DmsNewsCommentServiceImpl extends BaseServiceImpl<DmsNewsComment> implements DmsNewsCommentService {
    private static final Logger logger = Logger.getLogger(DmsNewsCommentServiceImpl.class);

    @Autowired
    private DmsNewsCommentMapper dmsNewsCommentMapper;
    @Autowired
    private DmsNewsMapper dmsNewsMapper;
    @Autowired
    private DmsUserMapper dmsUserMapper;

    @Override
    public IMybatisDao<DmsNewsComment> getBaseDao() {
        return dmsNewsCommentMapper;
    }

    /****
     * 插入新闻评论
     * @param dmsNewsComment
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void insertNewsComment(DmsNewsComment dmsNewsComment) {
        checkParams(dmsNewsComment);
        Long newsId=dmsNewsComment.getNewsId();//资讯id
        DmsNews dmsNews=dmsNewsMapper.selectByPrimaryKey(newsId);
        if(null==dmsNews){
            throw new ServiceException("无法获取id为"+newsId+"的咨讯信息！");
        }
        if(dmsNewsComment.getType()==0&&dmsNews.getIsComment()==0){
            throw new ServiceException("该条资讯不支持评论！");
        }
        if (dmsNews.getCommentCount()==null){
            dmsNews.setCommentCount(0L);
        }
        if (dmsNews.getForwardCount()==null){
            dmsNews.setForwardCount(0L);
        }
        //插入评论信息
        dmsNewsCommentMapper.insert(dmsNewsComment);
        //转发或者是评论量+1
        DmsNews news=new DmsNews();
        news.setId(newsId);
        news.setLastUpdatedBy(dmsNewsComment.getLastUpdatedBy());
        news.setLastUpdatedDate(new Date());
        if(dmsNewsComment.getType()==0){
            news.setCommentCount(dmsNews.getCommentCount()+1);
            dmsNewsMapper.updateByPrimaryKeySelective(news);
        }else if(dmsNewsComment.getType()==1){
            news.setForwardCount(dmsNews.getForwardCount()+1);
            dmsNewsMapper.updateByPrimaryKeySelective(news);
        }
    }

    /**
     * 分页查询评论列表
     * @param dmsNewsCommentVo
     * @return
     */
    @Override
    public PageHelper.Page<DmsNewsCommentVo> selectDmsNewsCommentPage(DmsNewsCommentVo dmsNewsCommentVo) {
        try {
            PageHelper.startPage(dmsNewsCommentVo.getP(),dmsNewsCommentVo.getS());
            dmsNewsCommentMapper.selectSomeByCondition(dmsNewsCommentVo);
        } catch (Exception e) {
            System.out.println("分页查询异常"+e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }

    /**
     * 删除评论，同时修改新闻中的评论数目
     * @param dmsNewsComment
     * @param ids
     */
    public void deleteCommentByDeal(DmsNewsComment dmsNewsComment,List<Long> ids){
        updateByIdsSelective(dmsNewsComment,ids);
        List<Long> newIds = findNewsIds(ids);
        if(BlankUtil.isNotEmpty(newIds)){
            dmsNewsMapper.reductionComentCount(newIds,1);
        }
    }

    /**
     * 按照评论的id列表查询对应新闻的id列表
     * @param ids
     * @return
     */
    public List<Long> findNewsIds(List<Long> ids) {
        Map<String,Object> map= dmsNewsCommentMapper.selectNewsId(ids);
        if(BlankUtil.isNotEmpty(map)){
            List<Long> list=new ArrayList<>();
            for(String newId:(map.get("newIds")+"").split(",")){
                list.add(Long.parseLong(newId));
            }
            return  list;
        }
        return new ArrayList<Long>();
    }

    /**
     * 校验参数
     * @param dmsNewsComment
     */
    private void checkParams(DmsNewsComment dmsNewsComment){
        if (dmsNewsComment == null) {
            throw new ServiceException("参数异常");
        }
        if (StringUtils.isBlank(dmsNewsComment.getContent())) {
            throw new ServiceException("评论内容不能为空");
        }
    }
}