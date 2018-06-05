package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsTianSubStoreMapper;
import com.coracle.dms.po.DmsTianGoodsStore;
import com.coracle.dms.po.DmsTianSubStore;
import com.coracle.dms.service.DmsTianSubStoreService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmsTianSubStoreServiceImpl extends BaseServiceImpl<DmsTianSubStore> implements DmsTianSubStoreService {

    private static final Logger logger = Logger.getLogger(DmsTianSubStoreServiceImpl.class);

    @Autowired
    private DmsTianSubStoreMapper dmsTianSubStoreMapper;

    @Override
    public IMybatisDao<DmsTianSubStore> getBaseDao() {
        return dmsTianSubStoreMapper;
    }


    @Override
    public PageHelper.Page selectPageList(DmsTianSubStore vo) {
        try {
            PageHelper.startPage(vo.getP(), vo.getS());
            List<DmsTianSubStore> data =
                    dmsTianSubStoreMapper.selectByCondition(vo);
        } catch (Exception e) {
            logger.error("分页查询异常!", e);
            throw new ServiceException("分页查询异常!");
        } finally {
            return PageHelper.endPage();
        }
    }


}





