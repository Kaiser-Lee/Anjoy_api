package com.coracle.dms.service;

import com.coracle.dms.po.DmsUserCollect;
import com.coracle.dms.vo.DmsUserCollectVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsUserCollectService extends IBaseService<DmsUserCollect> {
    /**
     * 分页获取用户收藏没有库存的记录详情
     * @param dmsUserCollect
     * @return
     */
    PageHelper.Page<DmsUserCollectVo> selectForNoNumList(DmsUserCollect dmsUserCollect);

    /**
     * 分页获取用户收藏有库存的记录详情
     * @param dmsUserCollect
     * @return
     */
    PageHelper.Page<DmsUserCollectVo> selectForByNumList(DmsUserCollect dmsUserCollect);

    /**
     * 获取收藏，没有库存 不分页
     * @param id
     * @return
     */
    List<DmsUserCollectVo> selectNoNumList(Long id);

    /**
     * 获取收藏，有库存 不分页
     * @param id
     * @return
     */
    List<DmsUserCollectVo> selectNumList(Long id);

    /**
     * 删除根据ID列表
     */
    void deleteCollectByInfo(Long userId, Long productId,String specUnionKey);
}