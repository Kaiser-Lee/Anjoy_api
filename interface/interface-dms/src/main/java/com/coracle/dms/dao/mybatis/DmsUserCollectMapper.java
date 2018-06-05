/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsUserCollect;
import com.coracle.dms.vo.DmsUserCollectVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DmsUserCollectMapper extends IMybatisDao<DmsUserCollect> {
    /**
     * 获取用户收藏信息，不包含库存
     * @param userId
     * @return
     */
    List<DmsUserCollectVo> getPageList(Long userId);

    /**
     * 获取用户收藏信息，包含库存
     * @param userId
     * @return
     */
    List<DmsUserCollectVo> getAllPageList(Long userId);

    /**
     * 删除根据ID列表
     */
    void deleteCollectByInfo(@Param("userId") Long userId,@Param("productId") Long productId,@Param("specUnionKey")String specUnionKey);
}