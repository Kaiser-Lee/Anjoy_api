package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.*;
import com.coracle.dms.po.DmsStorageInventory;
import com.coracle.dms.po.DmsStorageOutInRecord;
import com.coracle.dms.po.DmsStorageBill;
import com.coracle.dms.po.DmsStorageBillProduct;
import com.coracle.dms.service.DmsSerialNumService;
import com.coracle.dms.service.DmsStorageBillService;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.dms.vo.DmsStorageBillVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.StringUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.coracle.dms.constant.DmsModuleEnums.PURCHASE_IN_STORAGE_SERIAL_KEY;

@Service
public class DmsStorageBillServiceImpl extends BaseServiceImpl<DmsStorageBill> implements DmsStorageBillService {
    private static final Logger logger = Logger.getLogger(DmsStorageBillServiceImpl.class);

    @Autowired
    private DmsStorageBillMapper dmsStorageBillMapper;

    @Autowired
    private DmsStorageBillProductMapper dmsStorageBillProductMapper;

    @Autowired
    private DmsStorageInventoryMapper dmsStorageInventoryMapper;

    @Autowired
    private DmsStorageOutInRecordMapper dmsStorageOutInRecordMapper;

    @Autowired
    private DmsSerialNumService dmsSerialNumService;

    @Autowired
    private DmsProductMapper dmsProductMapper;

    @Override
    public IMybatisDao<DmsStorageBill> getBaseDao() {
        return dmsStorageBillMapper;
    }

    /**
     * 入库单分页查询
     * @param search 分页查询条件
     */
    @Override
    public PageHelper.Page<DmsStorageBillVo> selectForListPage(DmsStorageBillVo search) {
        try {
            PageHelper.startPage(search.getP(), search.getS());
            dmsStorageBillMapper.getPageList(search);
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            logger.error("分页查询入库单列表失败！",e);
            throw new ServiceException("分页查询入库单列表异常--->>>");
        }
    }

    @Override
    public void create(DmsStorageBillVo paramVo,UserSession userSession) {
        checkParam(paramVo);
        //入库单规则流水号
        String billNo = dmsSerialNumService.createSerialNumStr(PURCHASE_IN_STORAGE_SERIAL_KEY);
        paramVo.setBillNo(billNo);
        dmsStorageBillMapper.insert(paramVo);
        Long storageBillId = paramVo.getId();
        //用来临时存储产品信息
        List<Map<String,Object>> dmsStorageBillProductList = paramVo.getStorageBillProduct();
        List<DmsStorageBillProduct> billProductList = Lists.newArrayList();
        //用来临时存储库存信息
        List<DmsStorageInventory> dmsStorageInventoryList = Lists.newArrayList();
        //用来临时存储出入库记录
        List<DmsStorageOutInRecord> dmsStorageOutInRecordList = Lists.newArrayList();

        //获取产品名称，产品规格名称。冗余到出入库记录表中
    //    Map<String,Object> map = new HashMap<>();
    //    map.put("id",paramVo.getProductId());
       // DmsProductVo dmsProductvo = dmsProductMapper.getProductDetails(map);

        if(StringUtil.isListNotEmpty(dmsStorageBillProductList)){
            for (Map<String,Object> map : dmsStorageBillProductList) {

                if(map.get("storageNum")==null){
                    throw new ServiceException("库存数量为空，请检查！");
                }

                //存入临时存储产品信息
                DmsStorageBillProduct billProduct = new DmsStorageBillProduct();
                billProduct.setStorageBillId(storageBillId);
                billProduct.setProductId(Long.parseLong(map.get("productId").toString()));//产品id
                if(map.get("productSpecId")!=null){
                    billProduct.setProductSpecId(Long.parseLong(map.get("productSpecId").toString()));//产品规格id
                }
                billProduct.setStorageNum(Integer.parseInt(map.get("storageNum").toString()));
                billProduct.setUseNum(Integer.parseInt(map.get("storageNum").toString()));
                billProduct.setCreatedBy(paramVo.getCreatedBy());
                billProduct.setCreatedDate(new Date());
                billProduct.setRemoveFlag(0);
                billProductList.add(billProduct);

                //存入临时库存
                DmsStorageInventory storageInventory = new DmsStorageInventory();
                storageInventory.setProductId(Long.parseLong(map.get("productId").toString()));//产品id
                if(map.get("productSpecId")!=null) {
                    storageInventory.setProductSpecId(Long.parseLong(map.get("productSpecId").toString()));//产品规格id.
                }
                storageInventory.setStorage(paramVo.getStorageId());
                storageInventory.setStorageLocal(paramVo.getLocalId());
                storageInventory.setStorageNum(Integer.parseInt(map.get("storageNum").toString()));
                storageInventory.setUseNum(Integer.parseInt(map.get("storageNum").toString()));
                storageInventory.setCreatedBy(paramVo.getCreatedBy());
                storageInventory.setCreatedDate(new Date());
                storageInventory.setRemoveFlag(0);
                dmsStorageInventoryList.add(storageInventory);

                //存入临时存储出入库记录
                DmsStorageOutInRecord dmsStorageOutInRecord = new DmsStorageOutInRecord();
                dmsStorageOutInRecord.setInOrgId(paramVo.getRelationId());
                dmsStorageOutInRecord.setInOrgType(DmsModuleEnums.STORAGE_TYPE.BRAND.getType());
                dmsStorageOutInRecord.setNum(Long.parseLong(map.get("storageNum").toString()));
                dmsStorageOutInRecord.setStorage(paramVo.getStorageId());
                dmsStorageOutInRecord.setStorageLocal(paramVo.getLocalId());
                dmsStorageOutInRecord.setOutInType(DmsModuleEnums.STORAGE_OUTIN.IN.getType());//入库
                dmsStorageOutInRecord.setAssignWay(paramVo.getType());
                dmsStorageOutInRecord.setProductId(Long.parseLong(map.get("productId").toString()));//产品id
                if(map.get("productSpecId")!=null) {
                    dmsStorageOutInRecord.setProductSpecId(Long.parseLong(map.get("productSpecId").toString()));//产品规格id
                }
                dmsStorageOutInRecord.setProductName(map.get("name").toString());//产品名称
                if(map.get("specName")!=null) {
                    dmsStorageOutInRecord.setSpecName(map.get("specName").toString());//产品规格名称
                }
                dmsStorageOutInRecord.setOutInTime(new Date());
                dmsStorageOutInRecord.setCreatedDate(new Date());
                dmsStorageOutInRecord.setCreatedBy(userSession.getId());
                dmsStorageOutInRecord.setLastUpdatedDate(new Date());
                dmsStorageOutInRecord.setLastUpdatedBy(userSession.getId());
                dmsStorageOutInRecord.setRemoveFlag(0);
                dmsStorageOutInRecordList.add(dmsStorageOutInRecord);
            }
        }
        //插入入库单产品信息
        if(billProductList != null && !billProductList.isEmpty()){
            dmsStorageBillProductMapper.batchInsert(billProductList);
        }
        //插入库存表
        if(dmsStorageInventoryList != null && !dmsStorageInventoryList.isEmpty()){
            dmsStorageInventoryMapper.batchInsert(dmsStorageInventoryList);
        }
        //插入出入库记录表
        if(dmsStorageOutInRecordList != null && !dmsStorageOutInRecordList.isEmpty()){
            dmsStorageOutInRecordMapper.batchInsert(dmsStorageOutInRecordList);
        }
    }

    @Override
    public void update(DmsStorageBillVo paramVo) {
        this.checkParam(paramVo);
        if(paramVo == null || paramVo.getId() == null){
            throw new ServiceException("参数异常");
        }
        DmsStorageBill entity = this.dmsStorageBillMapper.selectByPrimaryKey(paramVo.getId());
        if(entity == null){
            throw new ServiceException("数据库获取数据不存在！");
        }
        dmsStorageBillMapper.updateByPrimaryKeySelective(paramVo);
        //修改产品数据
        dmsStorageBillProductMapper.deleteByStorageBillProductId(entity.getId());
        List<Map<String,Object>> dmsStorageBillProductList = paramVo.getStorageBillProduct();
        List<DmsStorageBillProduct> billProductList = Lists.newArrayList();
        if(StringUtil.isListNotEmpty(dmsStorageBillProductList)){
            for (Map<String,Object> map  : dmsStorageBillProductList) {
                DmsStorageBillProduct billProduct = new DmsStorageBillProduct();
                billProduct.setStorageBillId(entity.getId());
                billProduct.setProductId(Long.parseLong(map.get("productId").toString()));
                billProduct.setStorageNum(Integer.parseInt(map.get("storageNum").toString()));
                billProduct.setUseNum(Integer.parseInt(map.get("useNum").toString()));
                billProduct.setCreatedBy(paramVo.getCreatedBy());
                billProduct.setCreatedDate(new Date());
                billProduct.setRemoveFlag(0);
                billProductList.add(billProduct);
            }
        }
        if(billProductList != null && !billProductList.isEmpty()){
            dmsStorageBillProductMapper.batchInsert(billProductList);
        }

    }

    private void checkParam(DmsStorageBillVo paramVo) {
        if (paramVo == null) {
            throw new ServiceException("参数异常");
        }
        if (paramVo.getStorageId()==null) {
            throw new ServiceException("仓库不能为空");
        }
    }
}