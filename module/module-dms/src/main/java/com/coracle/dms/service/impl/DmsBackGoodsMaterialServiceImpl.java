package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsBackGoodsMaterialMapper;
import com.coracle.dms.po.DmsBackGoodsMaterial;
import com.coracle.dms.po.DmsClaimsProduct;
import com.coracle.dms.service.DmsBackGoodsMaterialService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmsBackGoodsMaterialServiceImpl extends BaseServiceImpl<DmsBackGoodsMaterial> implements DmsBackGoodsMaterialService {

    private static final Logger logger = Logger.getLogger(DmsBackGoodsMaterialServiceImpl.class);

    @Autowired
    private DmsBackGoodsMaterialMapper dmsBackGoodsMaterialMapper;

    @Override
    public IMybatisDao<DmsBackGoodsMaterial> getBaseDao() {
        return dmsBackGoodsMaterialMapper;
    }

    @Override
    public PageHelper.Page selectPageList(DmsBackGoodsMaterial vo) {
        try {
            PageHelper.startPage(vo.getP(), vo.getS());
            List<DmsBackGoodsMaterial> data =
                    dmsBackGoodsMaterialMapper.selectByCondition(vo);
        } catch (Exception e) {
            logger.error("分页查询异常!", e);
            throw new ServiceException("分页查询异常!");
        } finally {
            return PageHelper.endPage();
        }
    }


}