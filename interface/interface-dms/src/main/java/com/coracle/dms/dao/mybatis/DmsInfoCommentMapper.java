/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsInfoComment;
import com.coracle.dms.vo.DmsInfoCommentVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DmsInfoCommentMapper extends IMybatisDao<DmsInfoComment> {

    /**
     * 分页查询渠道赋能资讯的评论
     * @param dmsInfoCommentVo
     * @return
     */
    List<DmsInfoCommentVo> selectPageByCondition(DmsInfoCommentVo dmsInfoCommentVo);

    /**
     * 按照渠道赋能资讯评论的id列表查询对应新闻的id列表
     * @param ids
     * @return
     */
    Map<String,Object> selectInforsId(@Param(value = "ids") List<Long> ids);
}