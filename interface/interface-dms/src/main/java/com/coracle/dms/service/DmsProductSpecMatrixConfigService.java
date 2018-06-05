package com.coracle.dms.service;

import com.coracle.dms.po.DmsProductSpecMatrixConfig;
import com.coracle.dms.vo.DmsProductSpecMatrixConfigVo;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsProductSpecMatrixConfigService extends IBaseService<DmsProductSpecMatrixConfig> {

    /**
     * 根据产品id获取产品的规格属性矩阵vo
     * @param product
     * @return
     */
    List<DmsProductSpecMatrixConfigVo> selectVoByProductId(DmsProductVo product);

    /**
     * 根据产品id和规格属性获取产品的库存
     * @param dmsProductSpecMatrixConfigVo
     * @return
     */
    List<DmsProductSpecMatrixConfigVo> selectVoByProductIdSpec(DmsProductSpecMatrixConfigVo dmsProductSpecMatrixConfigVo, UserSession userSession);
}