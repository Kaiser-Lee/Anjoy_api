package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsProductMapper;
import com.coracle.dms.dao.mybatis.DmsStorageInventoryMapper;
import com.coracle.dms.dao.mybatis.DmsStorageOutInRecordMapper;
import com.coracle.dms.po.DmsProduct;
import com.coracle.dms.po.DmsStorageInventory;
import com.coracle.dms.po.DmsStorageOutInRecord;
import com.coracle.dms.service.DmsStorageInventoryService;
import com.coracle.dms.service.DmsStoreService;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.dms.vo.DmsStorageInventoryVo;
import com.coracle.dms.vo.DmsStorageOutInRecordVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DmsStorageInventoryServiceImpl extends BaseServiceImpl<DmsStorageInventory> implements DmsStorageInventoryService {
    private static final Logger logger = Logger.getLogger(DmsStorageInventoryServiceImpl.class);

    @Autowired
    private DmsStorageInventoryMapper dmsStorageInventoryMapper;

    @Autowired
    private DmsProductMapper dmsProductMapper;

    @Autowired
    private DmsStorageOutInRecordMapper dmsStorageOutInRecordMapper;

    @Autowired
    private DmsStoreService dmsStoreService;

    @Override
    public IMybatisDao<DmsStorageInventory> getBaseDao() {
        return dmsStorageInventoryMapper;
    }

    @Override
    public PageHelper.Page<DmsStorageInventoryVo> findStorageInventoryPageList(DmsStorageInventoryVo dmsStorageInventoryVo) {
        try {
            Long orgType = dmsStorageInventoryVo.getOrgType();

            if (orgType == 1) {  //品牌商库存列表
                dmsStorageInventoryVo.setOrgType(1L);
                dmsStorageInventoryVo.setStorageType("1");
            } else if (orgType == 2){  //渠道库存列表
                Long channelId = dmsStorageInventoryVo.getOrgId();

                //查询渠道库存列表时，只查出渠道和渠道下门店的库存
                dmsStorageInventoryVo.setChannelId(channelId);
                //根据渠道id获取渠道下的所有门店id列表
                List<Long> storeIdList = dmsStoreService.selectStoreIdListByChannelId(channelId);
                dmsStorageInventoryVo.setStoreIdList(storeIdList);

                dmsStorageInventoryVo.setOrgType(2L);
                dmsStorageInventoryVo.setStorageType("2");
            }
            PageHelper.startPage(dmsStorageInventoryVo.getP(), dmsStorageInventoryVo.getS());
            dmsStorageInventoryMapper.findStorageInventoryPageList(dmsStorageInventoryVo);
        } catch (Exception e) {
            System.out.println("分页查询异常" + e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }

    @Override
    public PageHelper.Page<Map<String,Object>> findStorageInventoryListByProduct(DmsStorageInventoryVo dmsStorageInventoryVo,UserSession userSession) {
        try {
            Long channelId = 0L;

            //如果是渠道则是渠道的id，如果是门店则是上级渠道id
            if("CO".equals(userSession.getRoleCode())){//渠道
//                dmsStorageInventoryVo.setOrgId(userSession.getOrgId());
                channelId = userSession.getOrgId();
            }if("SR".equals(userSession.getRoleCode())){//门店
//                dmsStorageInventoryVo.setOrgId(userSession.getSuperiorOrgId());
                channelId = userSession.getSuperiorOrgId();
            }
            List<Long> storeIdList = dmsStoreService.selectStoreIdListByChannelId(channelId);
            dmsStorageInventoryVo.setChannelId(channelId);
            dmsStorageInventoryVo.setStoreIdList(storeIdList);

            PageHelper.startPage(dmsStorageInventoryVo.getP(), dmsStorageInventoryVo.getS());
            dmsStorageInventoryMapper.findStorageInventoryListByProduct(dmsStorageInventoryVo);
        }catch (Exception e) {
            System.out.println("分页查询异常" + e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }

    @Override
    public PageHelper.Page<Map<String,Object>> findStorageInventoryListByStoreList(DmsStorageInventoryVo dmsStorageInventoryVo,UserSession userSession) {
        try {
            Long channelId = 0L;

            //如果是渠道则是渠道的id，如果是门店则是上级渠道id
            if("CO".equals(userSession.getRoleCode())){//渠道
//                dmsStorageInventoryVo.setOrgId(userSession.getOrgId());
                channelId = userSession.getOrgId();
            }if("SR".equals(userSession.getRoleCode())){//门店
//                dmsStorageInventoryVo.setOrgId(userSession.getSuperiorOrgId());
                channelId = userSession.getSuperiorOrgId();
            }
            List<Long> storeIdList = dmsStoreService.selectStoreIdListByChannelId(channelId);
            dmsStorageInventoryVo.setChannelId(channelId);
            dmsStorageInventoryVo.setStoreIdList(storeIdList);

            PageHelper.startPage(dmsStorageInventoryVo.getP(), dmsStorageInventoryVo.getS());
            dmsStorageInventoryMapper.findStorageInventoryListByStoreList(dmsStorageInventoryVo);
        }catch (Exception e) {
            System.out.println("分页查询异常" + e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }

    @Override
    public PageHelper.Page<Map<String,Object>> findStorageInventoryListByStore(DmsStorageInventoryVo dmsStorageInventoryVo,UserSession userSession) {
        try {
            Map<String, Object> map = new HashMap<>();
            Long storeId = dmsStorageInventoryVo.getStoreId();
            Long channelId = dmsStorageInventoryVo.getChannelId();
            if (storeId != null) {
                map.put("storeId", dmsStorageInventoryVo.getStoreId());
            }
            if (channelId != null) {
                map.put("channelId", dmsStorageInventoryVo.getChannelId());
            }
            PageHelper.startPage(dmsStorageInventoryVo.getP(), dmsStorageInventoryVo.getS());
            dmsStorageInventoryMapper.findStorageInventoryListByStore(map);
        }catch (Exception e) {
            System.out.println("分页查询异常" + e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }

    public DmsStorageInventory getDetails(Long id) {
        DmsStorageInventory outInStorageRecord = dmsStorageInventoryMapper.getDetails(id);
        return outInStorageRecord;
    }

    @Transactional(rollbackFor = Exception.class)
    public void create(DmsStorageInventory dmsStorageInventory) {
        dmsStorageInventory.setRemoveFlag(0);
        dmsStorageInventoryMapper.insert(dmsStorageInventory);
    }

    @Transactional(rollbackFor = Exception.class)
    public void adjustment(DmsStorageInventoryVo paramVo,UserSession userSession) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", paramVo.getId());
        map.put("productId", paramVo.getProductId());
        map.put("productSpecId", paramVo.getProductSpecId());
        map.put("storage", paramVo.getStorage());
        map.put("storageLocal", paramVo.getStorageLocal());
        //查询视图中库存的累加和
        DmsStorageInventoryVo oldInventory = dmsStorageInventoryMapper.selectByPrimaryKeyForView(map);
        if(oldInventory==null){
            throw new ServiceException("查询库存异常");
        }
        //先判断调整库存是新增库存还是扣减库存
        if(paramVo.getAddOrSubtractNum()-oldInventory.getStorageNum()>0){//增加库存
            //插入库存明细表
            insertStorageInventory(oldInventory.getStorage(),oldInventory.getStorageLocal(),paramVo.getAddOrSubtractNum()-oldInventory.getStorageNum(),paramVo.getAddOrSubtractNum()-oldInventory.getStorageNum(),oldInventory.getProductId(),oldInventory.getProductSpecId(),new Date(),userSession.getId(),0);
            //插入出入库记录表
            insertOutInRecord(paramVo.getOutOrgId(), paramVo.getOutOrgType(),userSession.getOrgId(), 1,oldInventory.getStorage(),oldInventory.getStorageLocal(),paramVo.getAddOrSubtractNum()-oldInventory.getStorageNum().longValue(),DmsModuleEnums.STORAGE_OUTIN.IN.getType(),paramVo.getAssignWay(),oldInventory.getProductId(),oldInventory.getProductSpecId(),oldInventory.getProductName(),oldInventory.getSpecName(),new Date(),new Date(),userSession.getId(),0);

        }else{//扣减库存
            //减少出库记录，扣库存
            DmsStorageInventoryVo inventory = new DmsStorageInventoryVo();
            inventory.setOutOrgId(userSession.getOrgId());
            inventory.setOutOrgType(DmsModuleEnums.STORAGE_TYPE.BRAND.getType());
            inventory.setAssignWay(DmsModuleEnums.STORAGE_OUTIN_TYPE.INVENTORY_ADJUSTMENT.getType());
            inventory.setProductId(oldInventory.getProductId());
            inventory.setProductSpecId(oldInventory.getProductSpecId());
            inventory.setStorage(oldInventory.getStorage());
            inventory.setStorageLocal(oldInventory.getStorageLocal());
            inventory.setOutInType(DmsModuleEnums.STORAGE_OUTIN.OUT.getType());
            inventory.setAddOrSubtractNum(oldInventory.getStorageNum()-paramVo.getAddOrSubtractNum());
            inventory.setTransFlag(false);
            this.addOrSubtract(inventory, userSession);

        }
    }

    @Transactional(rollbackFor = Exception.class)
	public void addOrSubtract(DmsStorageInventoryVo paramVo, UserSession userSession) {
		if (paramVo == null || userSession == null) {
			throw new ServiceException("参数异常");
		}
        logger.info("系统时间=="+new Date()+"===传入的数量==="+paramVo.getAddOrSubtractNum());
        final Integer addOrSubtractNum = paramVo.getAddOrSubtractNum();
        Map<String,Object> map = new HashMap<>();
        map.put("productId",paramVo.getProductId());
        map.put("productSpecId",paramVo.getProductSpecId());
        Map<String, Object> productNameSpecName = dmsProductMapper.findProductNameSpecName(map);

		checkParam(paramVo);
		// 库存新增
		if (paramVo.getOutInType() == DmsModuleEnums.STORAGE_OUTIN.IN.getType()) {
			// 插入库存明细表
			insertStorageInventory(paramVo.getStorage(), paramVo.getStorageLocal(), addOrSubtractNum, addOrSubtractNum, paramVo.getProductId(),paramVo.getProductSpecId(), new Date(), userSession.getId(), 0);

			// 插入出入库记录表
            if(productNameSpecName!=null){
			    insertOutInRecord(paramVo.getOutOrgId(), paramVo.getOutOrgType(),paramVo.getInOrgId(), paramVo.getInOrgType(), paramVo.getStorage(),paramVo.getStorageLocal(),addOrSubtractNum.longValue(), paramVo.getOutInType(), paramVo.getAssignWay(), paramVo.getProductId(),paramVo.getProductSpecId(),productNameSpecName.get("productName").toString(),productNameSpecName.get("specName").toString(), new Date(), new Date(), userSession.getId(), 0);
            }
		} else if (paramVo.getOutInType() == DmsModuleEnums.STORAGE_OUTIN.OUT.getType()) {// 减库存
			// 根据仓库id，货位id(如果存在货位id前提下),产品id,产品规格id(如果存在规格id前提下)查询是否存库存数量
			List<DmsStorageInventory> dmsStorageInventoryList = dmsStorageInventoryMapper.findBySidLidPid(paramVo);
			if (BlankUtil.isEmpty(dmsStorageInventoryList)) {
				throw new ServiceException("找不到产品对应库存信息！");
			}
			if (dmsStorageInventoryList.size() == 1) {
				if (addOrSubtractNum - dmsStorageInventoryList.get(0).getStorageNum() <= 0) {
					int num = dmsStorageInventoryList.get(0).getStorageNum() - addOrSubtractNum;
					// 在途的，扣减可用量，暂不减库存量，待确认收货后减库存量和可用量(二期)
					if (paramVo.getTransFlag()) {
                        //第一版暂不做可用量和库存量分开，故加上库存量扣减，二期去掉
                        dmsStorageInventoryList.get(0).setStorageNum(num);
						dmsStorageInventoryList.get(0).setUseNum(num);
						// 插入出入库记录表
                        if(productNameSpecName!=null) {
                            insertOutInRecord(paramVo.getOutOrgId(), paramVo.getOutOrgType(), paramVo.getInOrgId(), paramVo.getInOrgType(), paramVo.getStorage(), paramVo.getStorageLocal(), addOrSubtractNum.longValue(), paramVo.getOutInType(), paramVo.getAssignWay(), paramVo.getProductId(), paramVo.getProductSpecId(), productNameSpecName.get("productName").toString(), productNameSpecName.get("specName").toString(), new Date(), new Date(), userSession.getId(), 0);
                        }
                    } else {
						dmsStorageInventoryList.get(0).setStorageNum(num);
						dmsStorageInventoryList.get(0).setUseNum(num);
                        //这里为了防止其他类型在在途的时候插入了出入库记录，避免重复录入出入库记录，这里判断如果是库存调整，则插入出入库记录表。
                        //if(paramVo.getAssignWay()==DmsModuleEnums.STORAGE_OUTIN_TYPE.INVENTORY_ADJUSTMENT.getType()){
                            // 插入出入库记录表
                            if(productNameSpecName!=null) {
                                insertOutInRecord(paramVo.getOutOrgId(), paramVo.getOutOrgType(), paramVo.getInOrgId(), paramVo.getInOrgType(), paramVo.getStorage(), paramVo.getStorageLocal(), addOrSubtractNum.longValue(), paramVo.getOutInType(), paramVo.getAssignWay(), paramVo.getProductId(), paramVo.getProductSpecId(), productNameSpecName.get("productName").toString(), productNameSpecName.get("specName").toString(), new Date(), new Date(), userSession.getId(), 0);
                            }
                        //}
					}
					dmsStorageInventoryList.get(0).setId(dmsStorageInventoryList.get(0).getId());
					dmsStorageInventoryMapper.updateByPrimaryKeySelective(dmsStorageInventoryList.get(0));

				} else {
					// 库存不足
					throw new ServiceException("库存不足！");
				}
			} else {
				Integer dmsStorageNum = addOrSubtractNum;// 减的库存数量
				for (int i = 0; i < dmsStorageInventoryList.size(); i++) {
					DmsStorageInventory dmsStorageInventory = new DmsStorageInventory();
					if (dmsStorageNum - dmsStorageInventoryList.get(i).getStorageNum() > 0) {
						// 在途的，扣减可用量，暂不减库存量，待确认收货后减库存量和可用量
						if (paramVo.getTransFlag()) {
                            //第一版暂不做可用量和库存量分开，故加上库存量扣减，二期去掉
                            dmsStorageInventory.setStorageNum(0);

							dmsStorageInventory.setUseNum(0);
						} else {
							dmsStorageInventory.setStorageNum(0);
							dmsStorageInventory.setUseNum(0);
                        }
						dmsStorageInventory.setId(dmsStorageInventoryList.get(i).getId());
						dmsStorageInventoryMapper.updateByPrimaryKeySelective(dmsStorageInventory);
						// 获取扣减后还剩余的扣减库存
						dmsStorageNum = dmsStorageNum - dmsStorageInventoryList.get(i).getStorageNum();

					} else {
						// 在途的，扣减可用量，暂不减库存量，待确认收货后减库存量和可用量
						if (dmsStorageInventoryList.get(i).getStorageNum() - dmsStorageNum < 0) {
							// 库存不足
							throw new ServiceException("库存不足！");
						}
						if (paramVo.getTransFlag()) {
						    //第一版暂不做可用量和库存量分开，故加上库存量扣减，二期去掉
                            dmsStorageInventory.setStorageNum(dmsStorageInventoryList.get(i).getStorageNum() - dmsStorageNum);

							dmsStorageInventory.setUseNum(dmsStorageInventoryList.get(i).getStorageNum() - dmsStorageNum);
						} else {
							dmsStorageInventory.setStorageNum(dmsStorageInventoryList.get(i).getStorageNum() - dmsStorageNum);
							dmsStorageInventory.setUseNum(dmsStorageInventoryList.get(i).getStorageNum() - dmsStorageNum);

						}
						dmsStorageInventory.setId(dmsStorageInventoryList.get(i).getId());
						dmsStorageInventoryMapper.updateByPrimaryKeySelective(dmsStorageInventory);

                        break;
					}
				}
				//有在途记录，确认收货后不再插入出入库记录
                //if (paramVo.getTransFlag()) {
                // 插入出入库记录表
                if(productNameSpecName!=null) {
                    insertOutInRecord(paramVo.getOutOrgId(), paramVo.getOutOrgType(), paramVo.getInOrgId(), paramVo.getInOrgType(), paramVo.getStorage(), paramVo.getStorageLocal(), addOrSubtractNum.longValue(), paramVo.getOutInType(), paramVo.getAssignWay(), paramVo.getProductId(), paramVo.getProductSpecId(), productNameSpecName.get("productName").toString(), productNameSpecName.get("specName").toString(), new Date(), new Date(), userSession.getId(), 0);
                }//}
                //这里为了防止其他类型在在途的时候插入了出入库记录，避免重复录入出入库记录，这里判断如果是库存调整，则插入出入库记录表。
                /*if(paramVo.getAssignWay()==DmsModuleEnums.STORAGE_OUTIN_TYPE.INVENTORY_ADJUSTMENT.getType()){
                    // 插入出入库记录表
                    if(productNameSpecName!=null) {
                        insertOutInRecord(paramVo.getOutOrgId(), paramVo.getOutOrgType(), paramVo.getInOrgId(), paramVo.getInOrgType(), paramVo.getStorage(), paramVo.getStorageLocal(), addOrSubtractNum.longValue(), paramVo.getOutInType(), paramVo.getAssignWay(), paramVo.getProductId(), paramVo.getProductSpecId(), productNameSpecName.get("productName").toString(), productNameSpecName.get("specName").toString(), new Date(), new Date(), userSession.getId(), 0);
                    }
				}*/
			}
		} else {
			throw new ServiceException("出入库类型不正确！");
		}
	}

    //插入库存明细表
    public void insertStorageInventory(Long StorageId,Long StorageLocalId,Integer storageNum,Integer useNum,Long productId,Long productSpecId,Date createDate,Long createBy,Integer removeFlag) {
        DmsStorageInventory dmsStorageInventory = new DmsStorageInventory();
        dmsStorageInventory.setStorage(StorageId);
        dmsStorageInventory.setStorageLocal(StorageLocalId);
        dmsStorageInventory.setStorageNum(storageNum);
        dmsStorageInventory.setUseNum(useNum);
        dmsStorageInventory.setProductId(productId);
        dmsStorageInventory.setProductSpecId(productSpecId);
        dmsStorageInventory.setCreatedDate(createDate);
        dmsStorageInventory.setCreatedBy(createBy);
        dmsStorageInventory.setRemoveFlag(removeFlag);
        dmsStorageInventoryMapper.insert(dmsStorageInventory);
    }

    //插入出入库记录表
    public void insertOutInRecord(Long outOrgId,Integer outOrgType,Long inOrgId,Integer inOrgType,Long storage,Long storageLocal,Long num,Integer outInType,Integer assignWay,Long productId,Long productSpecId,String productName,String specName,Date outInTime,Date createDate,Long createBy,Integer removeFlag) {
        DmsStorageOutInRecord dmsStorageOutInRecord = new DmsStorageOutInRecord();
        dmsStorageOutInRecord.setOutOrgId(outOrgId);
        dmsStorageOutInRecord.setOutOrgType(outOrgType);
        dmsStorageOutInRecord.setInOrgId(inOrgId);
        dmsStorageOutInRecord.setInOrgType(inOrgType);
        dmsStorageOutInRecord.setStorage(storage);
        dmsStorageOutInRecord.setStorageLocal(storageLocal);
        dmsStorageOutInRecord.setNum(num);
        dmsStorageOutInRecord.setOutInType(outInType);
        dmsStorageOutInRecord.setAssignWay(assignWay);
        dmsStorageOutInRecord.setProductId(productId);
        dmsStorageOutInRecord.setProductSpecId(productSpecId);
        dmsStorageOutInRecord.setProductName(productName);
        dmsStorageOutInRecord.setSpecName(specName);
        dmsStorageOutInRecord.setOutInTime(outInTime);
        dmsStorageOutInRecord.setCreatedDate(createDate);
        dmsStorageOutInRecord.setCreatedBy(createBy);
        dmsStorageOutInRecord.setRemoveFlag(removeFlag);
        dmsStorageOutInRecordMapper.insert(dmsStorageOutInRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    public Long findByProductId(DmsStorageInventoryVo paramVo) {
        Long storageNum = dmsStorageInventoryMapper.findByProductId(paramVo);
        return storageNum;
    }

    private void checkParam(DmsStorageInventoryVo param) {
        if (param == null) {
            throw new ServiceException("参数异常");
        }
        if (param.getStorage() == null) {
            throw new ServiceException("仓库id不能为空");
        }
        if (param.getProductId() == null) {
            throw new ServiceException("产品id不能为空");
        }
        /*if (param.getProductSpecId() == null) {
            throw new ServiceException("产品规格id不能为空");
        }*/
        if (param.getAddOrSubtractNum() == null) {
            throw new ServiceException("增减库存数量不能为空");
        }
    }


    @Override
    public PageHelper.Page<Map<String, Object>> getDetail (DmsStorageInventoryVo paramVo, UserSession userSession) {
        try {
            Long channelId = 0L;
            //如果是渠道则是渠道的id，如果是门店则是上级渠道id
            if ("CO".equals(userSession.getRoleCode())) {//渠道
//                paramVo.setOrgId(userSession.getOrgId());
                channelId = userSession.getOrgId();
            }
            if ("SR".equals(userSession.getRoleCode())) {//门店
//                paramVo.setOrgId(userSession.getSuperiorOrgId());
                channelId = userSession.getSuperiorOrgId();
            }

            //如果channelId和storeId都为空，则查询全部(包括渠道和渠道下门店的所有数据)
            if (paramVo.getChannelId() == null && paramVo.getStoreId() == null) {
                paramVo.setChannelId(channelId);
                List<Long> storeIdList = dmsStoreService.selectStoreIdListByChannelId(channelId);
                paramVo.setStoreIdList(storeIdList);
            } else if (paramVo.getChannelId() != null) {  //如果channelId不为空，storeId为空，则只查询渠道的
                paramVo.setStoreId(null);
            } else if (paramVo.getStoreId() != null) {  //如果channelId为空，storeId不为空，则只查询门店的
                paramVo.setChannelId(null);
            }

            //查询库存数量统计，不分页
            int totalNum = dmsStorageInventoryMapper.findStorageInventoryListByProductIdNum(paramVo);
            PageHelper.startPage(paramVo.getP(), paramVo.getS());
            List<Map<String, Object>> list = dmsStorageInventoryMapper.findStorageInventoryListByProductId(paramVo);

            if (totalNum != 0) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).put("totalNum", totalNum);
                }
            }
        } catch (Exception e) {
            System.out.println("分页查询异常" + e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }

    /**
     * 根据条件获取可用量
     * @param storageInventory
     * @return
     */
    @Override
    public DmsStorageInventoryVo selectVoCondition(DmsStorageInventory storageInventory) {
        return dmsStorageInventoryMapper.selectVoByCondition(storageInventory);
    }
}