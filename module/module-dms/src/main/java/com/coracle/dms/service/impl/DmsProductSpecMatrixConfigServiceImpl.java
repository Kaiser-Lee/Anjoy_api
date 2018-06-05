package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsProductSpecMatrixConfigMapper;
import com.coracle.dms.po.DmsProductSpecMatrixConfig;
import com.coracle.dms.service.DmsProductSpecMatrixConfigService;
import com.coracle.dms.vo.DmsProductSpecMatrixConfigVo;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmsProductSpecMatrixConfigServiceImpl extends BaseServiceImpl<DmsProductSpecMatrixConfig> implements DmsProductSpecMatrixConfigService {
    private static final Logger logger = Logger.getLogger(DmsProductSpecMatrixConfigServiceImpl.class);

    @Autowired
    private DmsProductSpecMatrixConfigMapper dmsProductSpecMatrixConfigMapper;

    @Override
    public IMybatisDao<DmsProductSpecMatrixConfig> getBaseDao() {
        return dmsProductSpecMatrixConfigMapper;
    }

    /**
     * 根据产品id获取产品的规格属性矩阵vo
     * @param product
     * @return
     */
    @Override
    public List<DmsProductSpecMatrixConfigVo> selectVoByProductId(DmsProductVo product) {
        return dmsProductSpecMatrixConfigMapper.selectVoByProductId(product);
    }



    /**
     * 根据产品id和规格属性获取产品的库存
     * @param dmsProductSpecMatrixConfigVo
     * @return
     */
    @Override
    public List<DmsProductSpecMatrixConfigVo> selectVoByProductIdSpec(DmsProductSpecMatrixConfigVo dmsProductSpecMatrixConfigVo,UserSession userSession) {
        if("SR".equals(userSession.getRoleCode())){//零售端----门店
            dmsProductSpecMatrixConfigVo.setType("2");
        }else{
            dmsProductSpecMatrixConfigVo.setType("1");//品牌商、渠道、管理员
        }
        dmsProductSpecMatrixConfigVo.setUserId(userSession.getId());
        return dmsProductSpecMatrixConfigMapper.selectVoByProductIdSpec(dmsProductSpecMatrixConfigVo);
    }
}