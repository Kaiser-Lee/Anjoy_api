package com.coracle.dms.service;

import com.coracle.dms.po.DmsNewsComment;
import com.coracle.dms.vo.DmsNewsCommentVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsNewsCommentService extends IBaseService<DmsNewsComment> {
    /****
     * 插入新闻评论
     * @param dmsNewsComment
     */
    void insertNewsComment(DmsNewsComment dmsNewsComment);

    /**
     * 分页查询评论列表
     * @param dmsNewsCommentVo
     * @return
     */
    PageHelper.Page<DmsNewsCommentVo> selectDmsNewsCommentPage(DmsNewsCommentVo dmsNewsCommentVo);

    /**
     * 按照评论的id列表查询对应新闻的id列表
     * @param ids
     * @return
     */
    List<Long> findNewsIds(List<Long> ids);

    /**
     * 删除评论，同时修改新闻中的评论数目
     * @param dmsNewsComment
     * @param ids
     */
    void deleteCommentByDeal(DmsNewsComment dmsNewsComment,List<Long> ids);
}