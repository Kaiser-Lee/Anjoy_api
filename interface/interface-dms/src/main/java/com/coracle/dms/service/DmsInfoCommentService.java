package com.coracle.dms.service;

import com.coracle.dms.po.DmsInfoComment;
import com.coracle.dms.vo.DmsInfoCommentVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsInfoCommentService extends IBaseService<DmsInfoComment> {

    /****
     * 插入渠道赋能资讯类评论
     * @param dmsInfoComment
     */
    void insertNewsComment(DmsInfoComment dmsInfoComment);

    /**
     * 分页查询评论列表
     * @param dmsInfoCommentVo
     * @return
     */
    PageHelper.Page<DmsInfoCommentVo> selectDmsInforsCommentPage(DmsInfoCommentVo dmsInfoCommentVo);

    /**
     * 删除评论，同时修改渠道赋能资讯中的评论数目
     * @param dmsInfoComment
     * @param ids
     */
    void deleteInforsCommentByDeal(DmsInfoComment dmsInfoComment,List<Long> ids);

    /**
     * 按照渠道赋能资讯评论的id列表查询对应渠道赋能资讯的id列表
     * @param ids
     * @return
     */
    List<Long> findInforsIds(List<Long> ids);
}