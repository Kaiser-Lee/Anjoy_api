package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsClaimsMapper;
import com.coracle.dms.dao.mybatis.DmsClaimsProductMapper;
import com.coracle.dms.po.DmsClaims;
import com.coracle.dms.po.DmsClaimsProduct;
import com.coracle.dms.service.DmsClaimsService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DmsClaimsServiceImpl extends BaseServiceImpl<DmsClaims> implements DmsClaimsService {

    private static final Logger logger = Logger.getLogger(DmsClaimsServiceImpl.class);

    @Autowired
    private DmsClaimsMapper dmsClaimsMapper;

    @Autowired
    private DmsClaimsProductMapper dmsClaimsProductMapper;



    @Override
    public IMybatisDao<DmsClaims> getBaseDao() {
        return dmsClaimsMapper;
    }


    @Override
    public int insertObject(DmsClaims dmsClaims) {
        dmsClaimsMapper.insertSelective(dmsClaims);
        ArrayList<DmsClaimsProduct> productsList = dmsClaims.getProductsList();
        if(productsList!=null){
            for (DmsClaimsProduct product : productsList) {
                product.setDmsClaimsId(dmsClaims.getId());
                dmsClaimsProductMapper.insertSelective(product);
            }
        }
        return 1;
    }

    @Override
    public PageHelper.Page selectPageList(DmsClaims vo) {
        try {
            PageHelper.startPage(vo.getP(), vo.getS());
            List<DmsClaims> data =
                    dmsClaimsMapper.selectByCondition(vo);
        } catch (Exception e) {
            logger.error("分页查询异常!", e);
            throw new ServiceException("分页查询异常!");
        } finally {
            return PageHelper.endPage();
        }
    }


}




