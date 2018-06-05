package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsStorageSaleOutMapper;
import com.coracle.dms.po.DmsStorageSaleOut;
import com.coracle.dms.service.DmsSerialNumService;
import com.coracle.dms.service.DmsStorageInventoryService;
import com.coracle.dms.service.DmsStorageSaleOutService;
import com.coracle.dms.vo.DmsStorageInventoryVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.coracle.dms.constant.DmsModuleEnums.PURCHASE_IN_STORAGE_SERIAL_KEY;
import static com.coracle.dms.constant.DmsModuleEnums.SELL_OUT_SERIAL_KEY;

@Service
public class DmsStorageSaleOutServiceImpl extends BaseServiceImpl<DmsStorageSaleOut> implements DmsStorageSaleOutService {
    private static final Logger logger = Logger.getLogger(DmsStorageSaleOutServiceImpl.class);

    @Autowired
    private DmsStorageSaleOutMapper dmsStorageSaleOutMapper;

    @Autowired
    private DmsStorageInventoryService dmsStorageInventoryService;

    @Autowired
    private DmsSerialNumService dmsSerialNumService;

    @Override
    public IMybatisDao<DmsStorageSaleOut> getBaseDao() {
        return dmsStorageSaleOutMapper;
    }

    /**
     * 分页查询
     */
    @SuppressWarnings("unchecked")
    @Override
    public PageHelper.Page<DmsStorageSaleOut> selectForListPage(DmsStorageSaleOut search) {
        try {
            PageHelper.startPage(search.getP(), search.getS());
            this.dmsStorageSaleOutMapper.getPageList(search);
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            this.logger.error("分页查询列表失败！",e);
            throw new ServiceException("分页查询列表异常--->>>");
        }
    }

    /**
     * 新增销售出库
     */
    @Override
    public void insertTo(DmsStorageSaleOut search,UserSession userSession) {
        //销售出库规则流水号(废弃)
        //String saleOrder = dmsSerialNumService.createSerialNumStr(SELL_OUT_SERIAL_KEY);
        //search.setSaleOrder(saleOrder);
        dmsStorageSaleOutMapper.insert(search);

        //扣库存
        DmsStorageInventoryVo inventory = new DmsStorageInventoryVo();
        inventory.setOutOrgId(search.getSendUnit());
        inventory.setOutOrgType(2);
        inventory.setAssignWay(DmsModuleEnums.STORAGE_OUTIN_TYPE.SALE_OUT.getType());
        inventory.setProductId(search.getProductId());
        inventory.setProductSpecId(search.getProductSpecId());
        inventory.setStorage(search.getStorage());
        inventory.setStorageLocal(search.getStorageLocal());
        inventory.setOutInType(DmsModuleEnums.STORAGE_OUTIN.OUT.getType());
        inventory.setAddOrSubtractNum(search.getNum());
        inventory.setTransFlag(false);
        dmsStorageInventoryService.addOrSubtract(inventory, userSession);
    }
}