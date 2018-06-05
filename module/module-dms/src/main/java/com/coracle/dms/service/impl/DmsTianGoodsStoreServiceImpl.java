package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsTianGoodsStoreMapper;
import com.coracle.dms.po.DmsClaimsProduct;
import com.coracle.dms.po.DmsTianGoodsStore;
import com.coracle.dms.service.DmsTianGoodsStoreService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmsTianGoodsStoreServiceImpl extends BaseServiceImpl<DmsTianGoodsStore> implements DmsTianGoodsStoreService {
    private static final Logger logger = Logger.getLogger(DmsTianGoodsStoreServiceImpl.class);

    @Autowired
    private DmsTianGoodsStoreMapper dmsTianGoodsStoreMapper;

    @Override
    public IMybatisDao<DmsTianGoodsStore> getBaseDao() {
        return dmsTianGoodsStoreMapper;
    }


    @Override
    public PageHelper.Page selectPageList(DmsTianGoodsStore vo) {
        try {
            PageHelper.startPage(vo.getP(), vo.getS());
            List<DmsTianGoodsStore> data =
                    dmsTianGoodsStoreMapper.selectByCondition(vo);
        } catch (Exception e) {
            logger.error("分页查询异常!", e);
            throw new ServiceException("分页查询异常!");
        } finally {
            return PageHelper.endPage();
        }
    }


}


