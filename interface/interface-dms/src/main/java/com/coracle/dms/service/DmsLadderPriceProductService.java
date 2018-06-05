package com.coracle.dms.service;

import com.coracle.dms.po.DmsLadderPriceProduct;
import com.coracle.dms.vo.DmsLadderPriceProductVo;
import com.coracle.dms.vo.DmsPromotionVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsLadderPriceProductService extends IBaseService<DmsLadderPriceProduct> {
    /**
     * 查询详情
     */

    DmsLadderPriceProductVo selectDetailById(Long ladderPriceProductId);


    /**
     * 根据条件查询
     */
    PageHelper.Page<DmsLadderPriceProductVo> pageList(DmsLadderPriceProductVo dmsLadderPriceProductVo);
    /**
     * 新增或更新阶梯价格项
     */

    void insertOrUpdate(DmsLadderPriceProductVo dmsLadderPriceProductVo, UserSession userSession);



}