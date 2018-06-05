/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsNewsComment;
import com.coracle.dms.vo.DmsNewsCommentVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DmsNewsCommentMapper extends IMybatisDao<DmsNewsComment> {

    /**
     * 按照查询条件查询评论
     * @param condition
     * @return
     */
    List<DmsNewsCommentVo> selectSomeByCondition(DmsNewsCommentVo condition);

    /**
     * 按照评论的id列表查询对应新闻的id列表
     * @param ids
     * @return
     */
    Map<String,Object> selectNewsId(@Param(value = "ids") List<Long> ids);
}