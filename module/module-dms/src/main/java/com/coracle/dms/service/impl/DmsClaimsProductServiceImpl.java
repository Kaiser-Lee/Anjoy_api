package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsClaimsProductMapper;
import com.coracle.dms.po.DmsClaimsProduct;
import com.coracle.dms.service.DmsClaimsProductService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmsClaimsProductServiceImpl extends BaseServiceImpl<DmsClaimsProduct> implements DmsClaimsProductService {
    private static final Logger logger = Logger.getLogger(DmsClaimsProductServiceImpl.class);

    @Autowired
    private DmsClaimsProductMapper dmsClaimsProductMapper;

    @Override
    public IMybatisDao<DmsClaimsProduct> getBaseDao() {
        return dmsClaimsProductMapper;
    }


    @Override
    public PageHelper.Page selectPageList(DmsClaimsProduct vo) {
        try {
            PageHelper.startPage(vo.getP(), vo.getS());
            List<DmsClaimsProduct> data =
                    dmsClaimsProductMapper.selectByCondition(vo);
        } catch (Exception e) {
            logger.error("分页查询异常!", e);
            throw new ServiceException("分页查询异常!");
        } finally {
            return PageHelper.endPage();
        }
    }



}










