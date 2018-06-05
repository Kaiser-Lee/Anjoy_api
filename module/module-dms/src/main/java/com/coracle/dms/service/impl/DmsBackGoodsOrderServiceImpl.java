package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsBackGoodsMaterialMapper;
import com.coracle.dms.dao.mybatis.DmsBackGoodsOrderMapper;
import com.coracle.dms.po.DmsBackGoodsMaterial;
import com.coracle.dms.po.DmsBackGoodsOrder;
import com.coracle.dms.po.DmsClaimsProduct;
import com.coracle.dms.service.DmsBackGoodsMaterialService;
import com.coracle.dms.service.DmsBackGoodsOrderService;
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
public class DmsBackGoodsOrderServiceImpl extends BaseServiceImpl<DmsBackGoodsOrder> implements DmsBackGoodsOrderService {
    private static final Logger logger = Logger.getLogger(DmsBackGoodsOrderServiceImpl.class);

    @Autowired
    private DmsBackGoodsOrderMapper dmsBackGoodsOrderMapper;

    @Autowired
    private DmsBackGoodsMaterialMapper dmsBackGoodsMaterialMapper;

    @Autowired
    private DmsSerialNumServiceImpl dmsSerialNumServiceImpl;




    @Override
    public IMybatisDao<DmsBackGoodsOrder> getBaseDao() {
        return dmsBackGoodsOrderMapper;
    }

    @Override
    public void insertObject(DmsBackGoodsOrder dmsBackGoodsOrder) {
        String no = dmsSerialNumServiceImpl.createSerialNumStr(DmsModuleEnums.BACK_GOODS_SERIAL_KEY);

        dmsBackGoodsOrder.setBackOrderNumber(no);
        dmsBackGoodsOrderMapper.insertSelective(dmsBackGoodsOrder);

        ArrayList<DmsBackGoodsMaterial> list = dmsBackGoodsOrder.getDmsBackGoodsMaterialList();
        if (list!=null){
            for (DmsBackGoodsMaterial dmsBackGoodsMaterial: list) {
                dmsBackGoodsMaterial.setBackGoodsOrderId(dmsBackGoodsOrder.getId());
                dmsBackGoodsMaterialMapper.insertSelective(dmsBackGoodsMaterial);
            }
        }

    }



    public PageHelper.Page selectPageList(DmsBackGoodsOrder vo) {
        try {
            PageHelper.startPage(vo.getP(), vo.getS());
            List<DmsBackGoodsOrder> data =
                    dmsBackGoodsOrderMapper.selectByCondition(vo);
        } catch (Exception e) {
            logger.error("订单分页查询异常!", e);
            throw new ServiceException("订单分页查询异常!");
        } finally {
            return PageHelper.endPage();
        }
    }




}



