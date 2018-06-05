package com.coracle.dms.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsStorageAreaMapper;
import com.coracle.dms.dao.mybatis.DmsStorageInventoryMapper;
import com.coracle.dms.dao.mybatis.DmsStorageLocalMapper;
import com.coracle.dms.dao.mybatis.DmsStorageMapper;
import com.coracle.dms.po.DmsStorage;
import com.coracle.dms.po.DmsStorageArea;
import com.coracle.dms.po.DmsStorageLocal;
import com.coracle.dms.service.DmsStorageService;
import com.coracle.dms.vo.DmsStorageVo;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper.Page;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.util.StringUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.xiruo.medbid.components.StringUtils;

/**
 * 仓库业务层
 * @author tanyb
 *
 */
@Service
public class DmsStorageServiceImpl extends BaseServiceImpl<DmsStorage> implements DmsStorageService {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DmsStorageMapper dmsStorageMapper;
    @Autowired
    private DmsStorageAreaMapper dmsStorageAreaMapper;
    @Autowired
    private DmsStorageLocalMapper dmsStorageLocalMapper;
    @Autowired
    private DmsStorageInventoryMapper dmsStorageInventoryMapper;

    @Override
    public IMybatisDao<DmsStorage> getBaseDao() {
        return dmsStorageMapper;
    }
    
    /**
     * 仓库分页查询
     * @param search 分页查询条件
     */
    @SuppressWarnings("unchecked")
	@Override
	public Page<DmsStorageVo> selectForListPage(DmsStorageVo search) {
		try {
            PageHelper.startPage(search.getP(), search.getS());
            this.dmsStorageMapper.getPageList(search);
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            this.logger.error("分页查询仓库列表失败！",e);
            throw new ServiceException("分页查询仓库列表异常--->>>");
        }
	}

	/**
     * 入库单仓库列表（入库单只能选择品牌商仓库）
     * @param search 分页查询条件
     */
    @SuppressWarnings("unchecked")
	@Override
	public Page<DmsStorageVo> selectForBillListPage(DmsStorageVo search) {
		try {
            PageHelper.startPage(search.getP(), search.getS());
            this.dmsStorageMapper.getPageListForBill(search);
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            this.logger.error("分页查询仓库列表失败！",e);
            throw new ServiceException("分页查询仓库列表异常--->>>");
        }
	}
    @Override
    public DmsStorageVo detail(Long id) {
    	if(id == null){
    		throw new ServiceException("参数异常");
    	}
    	return this.dmsStorageMapper.selectDetailByPrimaryKey(id);
    }
    @Override
    public List<TreeNode> selectStorageTree(DmsStorageVo paramVo) {
    	return this.dmsStorageMapper.selectStorageTree(paramVo);
    }
	@Override
	public void create(DmsStorageVo paramVo) {
		this.checkParam(paramVo,true);
		paramVo.setCreatedDate(paramVo.getCreatedDate() == null ? new Date() : paramVo.getCreatedDate());
		paramVo.setRemoveFlag(paramVo.getRemoveFlag() == null ? 0 : paramVo.getRemoveFlag());
		paramVo.setStorageType(paramVo.getStorageType() == null ? DmsModuleEnums.STORAGE_TYPE.BRAND.getType() : paramVo.getStorageType());
		this.dmsStorageMapper.insert(paramVo);
		
		Long storageId = paramVo.getId();
		List<Long> areaIdList = paramVo.getAreaIdList();
		List<DmsStorageLocal> localList = paramVo.getStorageLocalList();
		List<DmsStorageArea> areaList = Lists.newArrayList();
		if(StringUtil.isListNotEmpty(areaIdList)){
			for (Long areaId : areaIdList) {
				DmsStorageArea area = new DmsStorageArea();
				area.setStorageId(storageId);
				area.setAreaId(areaId);
				area.setCreatedBy(paramVo.getCreatedBy());
				area.setCreatedDate(new Date());
				area.setRemoveFlag(0);
				areaList.add(area);
			}
		}
		if(areaList != null && !areaList.isEmpty()){
			this.dmsStorageAreaMapper.batchInsert(areaList);
		}
		//保存货位数据.先设置创建人和创建时间
		if(StringUtil.isListNotEmpty(localList)){
			for(DmsStorageLocal local : localList){
				local.setStorageId(storageId);
				local.setCreatedBy(paramVo.getCreatedBy());
				local.setCreatedDate(new Date());
				local.setRemoveFlag(0);
			}
			this.dmsStorageLocalMapper.batchInsert(localList);
		}
	}

	@Override
	public void update(DmsStorageVo paramVo) {
		this.checkParam(paramVo,false);
		if(paramVo == null || paramVo.getId() == null){
    		throw new ServiceException("参数异常");
    	}
		DmsStorage entity = this.dmsStorageMapper.selectByPrimaryKey(paramVo.getId());
        if(entity == null){  
        	throw new ServiceException("数据库获取数据不存在！");
        }
        int num = this.dmsStorageMapper.getCountStorageCode(entity.getCode(), paramVo.getCode());
        if(num > 0){
        	throw new ServiceException("仓库编码已经存在！");
        }
        //判断库存数量
        if(paramVo.getActive() != null && paramVo.getActive() == 0){
        	int inventoryNumber = this.dmsStorageInventoryMapper.getStorageLocalCount(paramVo.getId(), null);
        	if(inventoryNumber > 0){
        		throw new ServiceException("该仓库已经存在库存不能修改状态为无效！");
        	}
        }
        paramVo.setOrgId(null);//所属组织不处理
		this.dmsStorageMapper.updateByPrimaryKeySelective(paramVo);
		//修改辐射区域数据
		this.dmsStorageAreaMapper.deleteByStorageId(entity.getId());
		List<Long> areaIdList = paramVo.getAreaIdList();
		List<DmsStorageArea> areaList = Lists.newArrayList();
		if(StringUtil.isListNotEmpty(areaIdList)){
			for (Long areaId : areaIdList) {
				DmsStorageArea area = new DmsStorageArea();
				area.setStorageId(entity.getId());
				area.setAreaId(areaId);
				area.setCreatedBy(paramVo.getLastUpdatedBy());
				area.setCreatedDate(new Date());
				area.setRemoveFlag(0);
				areaList.add(area);
			}
		}
		if(areaList != null && !areaList.isEmpty()){
			this.dmsStorageAreaMapper.batchInsert(areaList);
		}
		//修改货位数据
		List<DmsStorageLocal> localList = paramVo.getStorageLocalList();
		if(StringUtil.isListNotEmpty(localList)){
			//不能直接删除，因为库存那边是保存货位id
//			this.dmsStorageLocalMapper.deleteByStorageId(entity.getId());
//			for(DmsStorageLocal local : localList){
//				local.setStorageId(entity.getId());
//				local.setCreatedBy(paramVo.getLastUpdatedBy());
//				local.setCreatedDate(new Date());
//				local.setRemoveFlag(0);
//			}
//			this.dmsStorageLocalMapper.batchInsert(localList);
			for (DmsStorageLocal local : localList) {
				if (local.getId() == null || local.getId() == 0) { // 说明是新建的货位
					local.setStorageId(entity.getId());
					local.setCreatedBy(paramVo.getLastUpdatedBy());
					local.setCreatedDate(new Date());
					local.setRemoveFlag(0);
					this.dmsStorageLocalMapper.insert(local);
				} else {
					DmsStorageLocal localEntity = this.dmsStorageLocalMapper.selectByPrimaryKey(local.getId());
					if (localEntity == null) {
						throw new ServiceException("数据库获取数据不存在！");
					}
					//判断货位库存数量
			        if(local.getActive() != null && local.getActive() == 0){
			        	int inventoryNumber = this.dmsStorageInventoryMapper.getStorageLocalCount(null, local.getId());
			        	if(inventoryNumber > 0){
			        		throw new ServiceException("该货位已经存在库存数据不能修改状态为无效！");
			        	}
			        }
			        local.setLastUpdatedBy(paramVo.getLastUpdatedBy());
					local.setLastUpdatedDate(new Date());
					this.dmsStorageLocalMapper.updateByPrimaryKeySelective(local);
				}
			}
		}
	}
	/**
	 * 根据组织id查询仓库
	 * @param orgId
	 * @return
	 */
	@Override
	public DmsStorage selectByOrgId(Long orgId){
		return this.dmsStorageMapper.selectByOrgId(orgId);
	}
	@Override
	public void createLocal(DmsStorageLocal local) {
		if (local == null || local.getStorageId() == null) {
			throw new ServiceException("参数异常");
		}
		this.dmsStorageLocalMapper.insert(local);
	}
	@Override
	public void updateLocal(DmsStorageLocal local){
		if (local == null || local.getId() == null) {
			throw new ServiceException("参数异常");
		}
		DmsStorageLocal entity = this.dmsStorageLocalMapper.selectByPrimaryKey(local.getId());
		if(entity == null){
			throw new ServiceException("查询数据不存在");
		}
		//判断货位库存数量
        if(local.getActive() != null && local.getActive() == 0){
        	int inventoryNumber = this.dmsStorageInventoryMapper.getStorageLocalCount(null, local.getId());
        	if(inventoryNumber > 0){
        		throw new ServiceException("该货位已经存在库存数据不能修改状态为无效！");
        	}
        }
		this.dmsStorageLocalMapper.updateByPrimaryKeySelective(local);
	}
	private void checkParam(DmsStorageVo paramVo,boolean addFlag) {
		if (paramVo == null) {
			throw new ServiceException("参数异常");
		}
		if (StringUtils.isBlank(paramVo.getName())) {
			throw new ServiceException("仓库名称不能为空");
		}
		if (StringUtils.isBlank(paramVo.getCode())) {
			throw new ServiceException("仓库编码不能为空");
		}
		if(addFlag){
			int storageNum = this.dmsStorageMapper.getCountStorageCode(null, paramVo.getCode());
			if(storageNum > 0){
				throw new ServiceException("仓库编码已经存在！");
			}
		}
		if (StringUtils.isBlank(paramVo.getContacts())) {
			throw new ServiceException("联系人不能为空");
		}
		if (StringUtils.isBlank(paramVo.getContactsMobile())) {
			throw new ServiceException("联系人手机不能为空");
		}
//		List<Long> areaIdList = paramVo.getAreaIdList();
//		if(areaIdList == null || areaIdList.isEmpty()){
//			throw new ServiceException("辐射区域不能为空");
//		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<DmsStorageLocal> selectLocalForListPage(DmsStorageLocal search) {
		if (search == null || search.getStorageId() == null || search.getStorageId() == 0) {
			throw new ServiceException("参数异常");
		}
		PageHelper.startPage(search.getP(), search.getS());
		this.dmsStorageLocalMapper.selectByCondition(search);
		return PageHelper.endPage();
	}

	@Override
	public void deleteLocal(Long id) {
		if (id == null) {
			throw new ServiceException("参数异常");
		}
		DmsStorageLocal local = this.dmsStorageLocalMapper.selectByPrimaryKey(id);
		if(local == null){
			throw new ServiceException("查询数据不存在");
		}
		//判断货位库存数量
    	int inventoryNumber = this.dmsStorageInventoryMapper.getStorageLocalCount(null, local.getId());
    	if(inventoryNumber > 0){
    		throw new ServiceException("该货位已经存在库存数据不能删除！");
    	}
    	local.setRemoveFlag(1);
    	local.setLastUpdatedDate(new Date());
		this.dmsStorageLocalMapper.updateByPrimaryKeySelective(local);
	}

	/**
	 * 根据渠道联系人的账号id获取渠道商的仓库
	 * @param userId
	 * @return
	 */
	@Override
	public DmsStorage selectByChannelContactUserId(Long userId) {
	    return dmsStorageMapper.selectByChannelContactUserId(userId);
	}

	@Override
	public DmsStorage selectByRelation(Long relationId, Integer relationType) {
		return this.dmsStorageMapper.selectByRelation(relationId, relationType);
	}
}