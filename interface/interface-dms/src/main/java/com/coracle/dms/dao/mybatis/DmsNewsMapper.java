/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsNews;
import com.coracle.dms.vo.DmsNewsVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DmsNewsMapper extends IMybatisDao<DmsNews> {
    /**
     * 分页获取dms新闻列表
     * @param dmsNewsVo
     * @return
     */
    List<DmsNewsVo> findDmsNewsPageList(DmsNewsVo dmsNewsVo);

    void reductionComentCount(@Param(value = "ids") List<Long> ids, @Param(value = "count") int count);

    DmsNewsVo selectVoByPrimaryKey(Long id);

    /**
     * 获取好可以看到的新闻
     * @param id
     * @return
     */
    List<DmsNewsVo> getNewsListByUser(@Param("id") Long id,@Param("isShow") Integer isShow);
}