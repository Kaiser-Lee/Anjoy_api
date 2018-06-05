package com.coracle.dms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.constant.DmsRoleCodeConstants;
import com.coracle.dms.dao.mybatis.*;
import com.coracle.dms.enums.PlatformEnum;
import com.coracle.dms.po.*;
import com.coracle.dms.service.*;
import com.coracle.dms.support.SystemParamerConfiguration;
import com.coracle.dms.vo.*;
import com.coracle.dms.webservice.AnjoySynClient;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper.Page;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.DmsChannelInfoVo;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xframework.util.StringUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiruo.medbid.components.StringUtils;
import com.xiruo.medbid.util.ImportExcel;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;

/**
 * 渠道管理业务实现类
 * @author tanyb
 *
 */
@Service
public class DmsChannelServiceImpl extends BaseServiceImpl<DmsChannel> implements DmsChannelService {
	private final Logger logger = LoggerFactory.getLogger(DmsChannelServiceImpl.class.getName());
	@Autowired
	private DmsChannelMapper dmsChannelMapper;
	@Autowired
	private DmsChannelAreaMapper dmsChannelAreaMapper;
	@Autowired
	private DmsChannelProductMapper dmsChannelProductMapper;
	@Autowired
	private DmsChannelAddressMapper dmsChannelAddressMapper;
	@Autowired
	private DmsChannelEmployeeMapper dmsChannelEmployeeMapper;
	@Autowired
	private DmsRemarkService dmsRemarkService;
	@Autowired
	private DmsStorageService dmsStorageService;
	@Autowired
	private DmsTreeRelationService dmsTreeRelationService;
	@Autowired
	private DmsTreeRelationMapper dmsTreeRelationMapper;
	@Autowired
	private DmsChannelContactsService dmsChannelContactsService;
	@Autowired
	private DmsAttachmentRelationService dmsAttachmentRelationService;
	@Autowired
	private DmsStoreService dmsStoreService;
	@Autowired
	private DmsProductMapper dmsProductMapper;
	@Autowired
	private DmsSysAreaMapper dmsSysAreaMapper;
	@Autowired
	private DmsSysAreaService dmsSysAreaService;
	@Autowired
	private DmsDataDictionayDependentService dmsDataDictionayDependentService;
	@Autowired
	private DmsOrderMapper dmsOrderMapper;
	@Override
	public IMybatisDao<DmsChannel> getBaseDao() {
		return dmsChannelMapper;
	}

	/**
	 * 渠道分页查询
	 * @param searchVo 分页查询条件
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page<DmsChannelVo> selectForListPage(DmsChannelVo searchVo,UserSession session) {

		//设置当前登录人的userId，查找该业务员负责的渠道
		String  roleCode = session.getRoleCode();
		if(roleCode.equals(DmsRoleCodeConstants.ZSYWY) ||roleCode.equals(DmsRoleCodeConstants.NQWLRY)){
			Long id = session.getId();
			searchVo.setUserId(id);

		}

		try {
			PageHelper.startPage(searchVo.getP(), searchVo.getS());
			dmsChannelMapper.getPageList(searchVo);
			return PageHelper.endPage();
		} catch (Exception e) {
			PageHelper.endPage();
			throw new ServiceException("渠道分页查询异常--->>>");
		}
	}

	@Override
	public List<Map<String,Object>> getList(DmsChannelVo searchVo) {
		return this.dmsChannelMapper.getList(searchVo);
	}
	/**
	 * 获取渠道详情
	 */
	@Override
	public Map<String,Object> detail(Long id,UserSession session) {
		if (BlankUtil.isEmpty(id)){
			throw new ControllerException("参数异常！");
		}
		Map<String,Object> result = new HashMap<String,Object>();
		DmsChannelVo dmsChannel = this.dmsChannelMapper.selectDetailByPrimaryKey(id);
		if (dmsChannel == null || dmsChannel.getId() == null){
			throw new ControllerException("获取渠道数据为空！");
		}
		result.put("dmsChannel", dmsChannel);
		return result;
	}
	/**
	 * 安井获取渠道详情
	 */
	@Override
	public Map<String,Object> ajdetail(Long id,UserSession session) {
		if (BlankUtil.isEmpty(id)){
			throw new ControllerException("参数异常！");
		}
		Map<String,Object> result = new HashMap<String,Object>();
		DmsChannelVo dmsChannel = this.dmsChannelMapper.selectDetailByPrimaryKey(id);

		//业务员列表
		List <DmsChannelEmployee> dmsChannelEmployeeList =this.dmsChannelEmployeeMapper.selectByChannelId(id);
		if(dmsChannel!=null){
			dmsChannel.setEmployeelist(dmsChannelEmployeeList);
		}


		//收货地址列表
		List<DmsChannelAddressVo> dmsChannelAddressVoList = this.dmsChannelAddressMapper.selectByChannelId(id);
		if(dmsChannel!=null){
			dmsChannel.setAddressList(dmsChannelAddressVoList);
		}



		if (dmsChannel == null || dmsChannel.getId() == null){
			throw new ControllerException("获取渠道数据为空！");
		}
		result.put("dmsChannel", dmsChannel);
		return result;
	}

	/**
	 * 添加渠道
	 */
	@Override
	public void create(DmsChannelVo paramVo,UserSession session) {
		paramVo.setCode(SystemParamerConfiguration.getInstance().createSerialNumStr(DmsModuleEnums.CHANNEL_SERIAL_KEY));
		this.checkParam(paramVo);
		//保存渠道基本信息
		this.dmsChannelMapper.insert(paramVo);

		Long channelId = paramVo.getId();
		if (paramVo.getParentId()==null||paramVo.getParentId()==0) {
//			dmsTreeRelationService.addRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType(), channelId, paramVo.getName(), session.getId(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType());
			//当渠道没有父渠道的时候，设置其父节点为所属区域id
			dmsTreeRelationService.addRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType(), channelId,
					DmsModuleEnums.TREE_RELATED_TYPE.DMS_AREA.getType(), paramVo.getAreaId(), paramVo.getName(),
					session.getId(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType());
		}else {
			dmsTreeRelationService.addRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType(),channelId,
					DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType(),paramVo.getParentId(),paramVo.getName(),
					session.getId(),DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType());
		}
		this.logger.info("获取渠道id为{}",channelId);
		//获取产品类别的id  list
		List<Long> productTypeIdList = paramVo.getProductTypeIdList();
		//获取地区 id list
		List<Long> areaIdList = paramVo.getAreaIdList();

		/**
		 * 暂存产品类型和区域List集合
		 */
		List<DmsChannelProduct> productTypeList = Lists.newArrayList();
		List<DmsChannelArea> areaList = Lists.newArrayList();

		if(productTypeIdList != null && !productTypeIdList.isEmpty()){
			for (Long productTypeId : productTypeIdList) {
				DmsChannelProduct productType = new DmsChannelProduct();
				productType.setChannelId(channelId);
				productType.setProductTypeId(productTypeId);
				productType.setCreatedBy(paramVo.getCreatedBy());
				productType.setCreatedDate(new Date());
				productType.setRemoveFlag(0);
				productTypeList.add(productType);
			}
		}

		if(productTypeList != null && !productTypeList.isEmpty()){
			this.dmsChannelProductMapper.batchInsert(productTypeList);
		}

		if(areaIdList != null && !areaIdList.isEmpty()){
			for (Long areaId : areaIdList) {
				DmsChannelArea area = new DmsChannelArea();
				area.setChannelId(channelId);
				area.setAreaId(areaId);
				area.setCreatedBy(paramVo.getCreatedBy());
				area.setCreatedDate(new Date());
				area.setRemoveFlag(0);
				areaList.add(area);
			}
		}

		if(areaList != null && !areaList.isEmpty()){
			this.dmsChannelAreaMapper.batchInsert(areaList);
		}

		//保存渠道联系人信息
		DmsChannelContactsVo channelContactVo = new DmsChannelContactsVo();
		channelContactVo.setChannelId(channelId);
		channelContactVo.setName(paramVo.getContacts());
		channelContactVo.setSex("0");

		List<DmsContactInfo> contactInfoList = new ArrayList<>();
		DmsContactInfo dmsContactInfo = new DmsContactInfo();
		dmsContactInfo.setType(DmsModuleEnums.CONTACT_TYPE.MOBILE.getType());
		dmsContactInfo.setContent(paramVo.getContactsMobile());
		contactInfoList.add(dmsContactInfo);

		channelContactVo.setContactInfoList(contactInfoList);
		channelContactVo.setCreatedBy(session.getId());
		channelContactVo.setCreatedDate(new Date());
		channelContactVo.setLastUpdatedDate(new Date());
		channelContactVo.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

		this.dmsChannelContactsService.create(channelContactVo, session);

		//创建仓库数据.后续考虑异步处理
		DmsStorageVo storageVo = new DmsStorageVo();
		storageVo.setName(paramVo.getName());
		storageVo.setCode("S"+paramVo.getCode());
		storageVo.setActive(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
		storageVo.setContacts(paramVo.getContacts());
		storageVo.setContactsMobile(paramVo.getContactsMobile());
		if(paramVo.getAreaId() != null && paramVo.getAreaId() != 0){
			List<Long> storageAreaIds = Lists.newArrayList();
			storageAreaIds.add(paramVo.getAreaId());
			storageVo.setAreaIdList(storageAreaIds);
		}
		storageVo.setRelationId(channelId);
		storageVo.setOrgId(channelId);
		storageVo.setCreatedBy(session.getId());
		storageVo.setCreatedDate(new Date());
		storageVo.setLastUpdatedBy(session.getId());
		storageVo.setRemoveFlag(0);
		storageVo.setLastUpdatedDate(new Date());
		storageVo.setStorageType(DmsModuleEnums.STORAGE_TYPE.CHANNEL.getType());
		storageVo.setProvince(paramVo.getProvince());
		storageVo.setCity(paramVo.getCity());
		storageVo.setCounty(paramVo.getCounty());
		storageVo.setAddress(paramVo.getAddress());
		this.dmsStorageService.create(storageVo);
	}
	/**
	 * 安井添加渠道
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public void ajcreate(DmsChannelVo paramVo,UserSession session) {
		paramVo.setCode(SystemParamerConfiguration.getInstance().createSerialNumStr(DmsModuleEnums.CHANNEL_SERIAL_KEY));
		this.checkParam(paramVo);
		//保存渠道基本信息
		this.dmsChannelMapper.insert(paramVo);

		Long channelId = paramVo.getId();
		if (paramVo.getParentId()==null||paramVo.getParentId()==0) {
//			dmsTreeRelationService.addRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType(), channelId, paramVo.getName(), session.getId(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType());
			//当渠道没有父渠道的时候，设置其父节点为所属区域id
			dmsTreeRelationService.addRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType(), channelId,
				DmsModuleEnums.TREE_RELATED_TYPE.DMS_AREA.getType(), paramVo.getAreaId(), paramVo.getName(),
				session.getId(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType()
			);
		}else {
			dmsTreeRelationService.addRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType(),channelId,
				DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType(),paramVo.getParentId(),paramVo.getName(),
				session.getId(),DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType()
			);
		}
		this.logger.info("获取渠道id为：{}",channelId);
		//获取产品类别的id  list
		List<Long> productTypeIdList = paramVo.getProductTypeIdList();
		//获取地区 id list
		List<Long> areaIdList = paramVo.getAreaIdList();
		//获取收货地址 list
		List<DmsChannelAddressVo> dmsChannelAddressList = paramVo.getAddressList();
		//获取业务员 list
		List<DmsChannelEmployee> dmsChannelEmployeesList = paramVo.getEmployeelist();

		/**
		 * 暂存产品类型和区域List集合
		 */
		List<DmsChannelProduct> productTypeList = Lists.newArrayList();
		List<DmsChannelArea> areaList = Lists.newArrayList();
		List<DmsChannelAddressVo> addressList = Lists.newArrayList();
		List<DmsChannelEmployee> employeeList = Lists.newArrayList();

		if(productTypeIdList != null && !productTypeIdList.isEmpty()){
			for (Long productTypeId : productTypeIdList) {
				DmsChannelProduct productType = new DmsChannelProduct();
				productType.setChannelId(channelId);
				productType.setProductTypeId(productTypeId);
				productType.setCreatedBy(paramVo.getCreatedBy());
				productType.setCreatedDate(new Date());
				productType.setRemoveFlag(0);
				productTypeList.add(productType);
			}
		}
		if(productTypeList != null && !productTypeList.isEmpty()){
			this.dmsChannelProductMapper.batchInsert(productTypeList);
		}

		if(areaIdList != null && !areaIdList.isEmpty()){
			for (Long areaId : areaIdList) {
				DmsChannelArea area = new DmsChannelArea();
				area.setChannelId(channelId);
				area.setAreaId(areaId);
				area.setCreatedBy(paramVo.getCreatedBy());
				area.setCreatedDate(new Date());
				area.setRemoveFlag(0);
				areaList.add(area);
			}
		}
		if(areaList != null && !areaList.isEmpty()){
			this.dmsChannelAreaMapper.batchInsert(areaList);
		}
		//批量插入收货地址
		if(dmsChannelAddressList != null && !dmsChannelAddressList.isEmpty()){
			for(DmsChannelAddressVo  dmsChannelAddressVo:dmsChannelAddressList){
				dmsChannelAddressVo.setChannelId(paramVo.getId());
				dmsChannelAddressVo.setCreatedDate(new Date());
				dmsChannelAddressVo.setCreatedBy(session.getId());
				dmsChannelAddressVo.setRemoveFlag(0);
				addressList.add(dmsChannelAddressVo);
			}
		}

		if(addressList != null && !addressList.isEmpty()){
			dmsChannelAddressMapper.batchInsert(addressList);
		}

		/**
		 * 批量插入业务员，暂时屏蔽（直接从客户那边同步过来）
		 */
		/*if(dmsChannelEmployeesList != null && !dmsChannelEmployeesList.isEmpty()){
			for(DmsChannelEmployee dmsChannelEmployee:dmsChannelEmployeesList){
				dmsChannelEmployee.setChannelId(paramVo.getId());
				dmsChannelEmployee.setCreateDate(new Date());
				dmsChannelEmployee.setCreateBy(session.getId());
				dmsChannelEmployee.setRemoveFlag(0);
				employeeList.add(dmsChannelEmployee);
			}
		}

		if(employeeList != null && !employeeList.isEmpty()){
			dmsChannelEmployeeMapper.batchInsert(employeeList);
		}*/

		//保存渠道联系人信息
		DmsChannelContactsVo channelContactVo = new DmsChannelContactsVo();
		channelContactVo.setChannelId(channelId);
		channelContactVo.setName(paramVo.getContacts());
		channelContactVo.setSex("0");
		List<DmsContactInfo> contactInfoList = new ArrayList<>();
		DmsContactInfo dmsContactInfo = new DmsContactInfo();
		dmsContactInfo.setType(DmsModuleEnums.CONTACT_TYPE.MOBILE.getType());
		dmsContactInfo.setContent(paramVo.getContactsMobile());
		contactInfoList.add(dmsContactInfo);
		channelContactVo.setContactInfoList(contactInfoList);
		channelContactVo.setCreatedBy(session.getId());
		channelContactVo.setCreatedDate(new Date());
		channelContactVo.setLastUpdatedDate(new Date());
		channelContactVo.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
		this.dmsChannelContactsService.create(channelContactVo, session);

		//创建仓库数据.后续考虑异步处理
		DmsStorageVo storageVo = new DmsStorageVo();
		storageVo.setName(paramVo.getName());
		storageVo.setCode("S"+paramVo.getCode());
		storageVo.setActive(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
		storageVo.setContacts(paramVo.getContacts());
		storageVo.setContactsMobile(paramVo.getContactsMobile());
		if(paramVo.getAreaId() != null && paramVo.getAreaId() != 0){
			List<Long> storageAreaIds = Lists.newArrayList();
			storageAreaIds.add(paramVo.getAreaId());
			storageVo.setAreaIdList(storageAreaIds);
		}
		storageVo.setRelationId(channelId);
		storageVo.setOrgId(channelId);
		storageVo.setCreatedBy(session.getId());
		storageVo.setCreatedDate(new Date());
		storageVo.setLastUpdatedBy(session.getId());
		storageVo.setRemoveFlag(0);
		storageVo.setLastUpdatedDate(new Date());
		storageVo.setStorageType(DmsModuleEnums.STORAGE_TYPE.CHANNEL.getType());
		storageVo.setProvince(paramVo.getProvince());
		storageVo.setCity(paramVo.getCity());
		storageVo.setCounty(paramVo.getCounty());
		storageVo.setAddress(paramVo.getAddress());
		this.dmsStorageService.create(storageVo);
	}

	/**
	 * 编辑更新
	 */
	@Override
	public void update(DmsChannelVo paramVo, UserSession session) {
		this.checkParam(paramVo);
		if (paramVo == null || paramVo.getId() == null) {
			throw new ServiceException("参数异常");
		}
		DmsChannel entity = this.dmsChannelMapper.selectByPrimaryKey(paramVo.getId());
		if (entity == null) {
			throw new ServiceException("数据库获取数据不存在！");
		}
		this.dmsChannelMapper.updateByPrimaryKeySelective(paramVo);

		//编辑树形结构信息
		if (paramVo.getParentId() == null || paramVo.getParentId() == 0) {
			//当渠道没有父渠道的时候，设置其父节点为所属区域id
			dmsTreeRelationService.updateRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType(), paramVo.getId(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_AREA.getType(), paramVo.getAreaId(), paramVo.getName(), session.getId(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType());
		} else {
			dmsTreeRelationService.updateRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType(), paramVo.getId(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType(), paramVo.getParentId(), paramVo.getName(), session.getId(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType());
		}

		// 针对于销售产品和区域先删除后添加
		List<Long> productTypeIdList = paramVo.getProductTypeIdList();
		List<Long> areaIdList = paramVo.getAreaIdList();
		/**
		 * 暂存产品类型和区域List集合
		 */
		List<DmsChannelProduct> productTypeList = Lists.newArrayList();
		List<DmsChannelArea> areaList = Lists.newArrayList();
		if (productTypeIdList != null && !productTypeIdList.isEmpty()) {
			this.dmsChannelProductMapper.deleteByChannelId(entity.getId());
			for (Long productTypeId : productTypeIdList) {
				DmsChannelProduct productType = new DmsChannelProduct();
				productType.setChannelId(entity.getId());
				productType.setProductTypeId(productTypeId);
				productType.setCreatedBy(paramVo.getLastUpdatedBy());
				productType.setCreatedDate(new Date());
				productType.setRemoveFlag(0);
				productTypeList.add(productType);
			}
		}
		if (productTypeList != null && !productTypeList.isEmpty()) {
			this.dmsChannelProductMapper.batchInsert(productTypeList);
		}

		if (areaIdList != null && !areaIdList.isEmpty()) {
			this.dmsChannelAreaMapper.deleteByChannelId(entity.getId());
			for (Long areaId : areaIdList) {
				DmsChannelArea area = new DmsChannelArea();
				area.setChannelId(entity.getId());
				area.setAreaId(areaId);
				area.setCreatedBy(paramVo.getLastUpdatedBy());
				area.setCreatedDate(new Date());
				area.setRemoveFlag(0);
				areaList.add(area);
			}
		}
		if (areaList != null && !areaList.isEmpty()) {
			this.dmsChannelAreaMapper.batchInsert(areaList);
		}
	}
	/**
	 * 安井编辑更新
	 */

	@Override
	public void ajupdate(DmsChannelVo paramVo) {
		this.checkParam(paramVo);
		if (paramVo == null || paramVo.getId() == null) {
			throw new ServiceException("参数异常");
		}
		DmsChannel entity = this.dmsChannelMapper.selectByPrimaryKey(paramVo.getId());
		if (entity == null) {
			throw new ServiceException("数据库获取数据不存在！");
		}
		this.dmsChannelMapper.updateByPrimaryKeySelective(paramVo);
//		dmsTreeRelationService.updateName(DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType(), paramVo.getId(), paramVo.getName(), paramVo.getLastUpdatedBy());
//		dmsTreeRelationService.updateRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType(), paramVo.getId(), paramVo.getParentId(), paramVo.getName(), paramVo.getLastUpdatedBy());
		//编辑树形结构信息
		if (paramVo.getParentId() == null || paramVo.getParentId() == 0) {
			//当渠道没有父渠道的时候，设置其父节点为所属区域id
			dmsTreeRelationService.updateRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType(), paramVo.getId(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_AREA.getType(), paramVo.getAreaId(), paramVo.getName(), paramVo.getLastUpdatedBy(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType());
		} else {
			dmsTreeRelationService.updateRelation(DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType(), paramVo.getId(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType(), paramVo.getParentId(), paramVo.getName(), paramVo.getLastUpdatedBy(), DmsModuleEnums.TREE_RELATED_TYPE.DMS_CHANNEL.getType());
		}
		// 针对于销售产品和区域和收货地址先删除后添加
		List<Long> productTypeIdList = paramVo.getProductTypeIdList();
		List<Long> areaIdList = paramVo.getAreaIdList();
		List<DmsChannelAddressVo> channelAddressList = paramVo.getAddressList();
		List<DmsChannelEmployee> channelEmployeesList = paramVo.getEmployeelist();
		/**
		 * 暂存产品类型和区域List集合
		 */
		List<DmsChannelProduct> productTypeList = Lists.newArrayList();
		List<DmsChannelArea> areaList = Lists.newArrayList();
		List<DmsChannelAddressVo> addresslist = Lists.newArrayList();
		List<DmsChannelEmployee> employeeList = Lists.newArrayList();

		if (productTypeIdList != null && !productTypeIdList.isEmpty()) {
			this.dmsChannelProductMapper.deleteByChannelId(entity.getId());
			for (Long productTypeId : productTypeIdList) {
				DmsChannelProduct productType = new DmsChannelProduct();
				productType.setChannelId(entity.getId());
				productType.setProductTypeId(productTypeId);
				productType.setCreatedBy(paramVo.getLastUpdatedBy());
				productType.setCreatedDate(new Date());
				productType.setRemoveFlag(0);
				productTypeList.add(productType);
			}
		}
		if (productTypeList != null && !productTypeList.isEmpty()) {
			this.dmsChannelProductMapper.batchInsert(productTypeList);
		}

		if (areaIdList != null && !areaIdList.isEmpty()) {
			this.dmsChannelAreaMapper.deleteByChannelId(entity.getId());
			for (Long areaId : areaIdList) {
				DmsChannelArea area = new DmsChannelArea();
				area.setChannelId(entity.getId());
				area.setAreaId(areaId);
				area.setCreatedBy(paramVo.getLastUpdatedBy());
				area.setCreatedDate(new Date());
				area.setRemoveFlag(0);
				areaList.add(area);
			}
		}
		if (areaList != null && !areaList.isEmpty()) {
			this.dmsChannelAreaMapper.batchInsert(areaList);
		}
		if(channelAddressList!=null&&!channelAddressList.isEmpty()){
			//this.dmsChannelAddressMapper.deleteByChannelId(entity.getId());
			for(DmsChannelAddressVo dmsChannelAddressVo :channelAddressList){
				if(dmsChannelAddressVo.getId() == null){
					dmsChannelAddressVo.setChannelId(entity.getId());
					dmsChannelAddressVo.setUpdateDate(new Date());
					dmsChannelAddressVo.setUpdateBy(paramVo.getLastUpdatedBy());
					dmsChannelAddressVo.setRemoveFlag(0);
					addresslist.add(dmsChannelAddressVo);
				}else {
					dmsChannelAddressMapper.updateByPrimaryKeySelective(dmsChannelAddressVo);
				}
			}
		}

		if(addresslist!=null&&!addresslist.isEmpty()){
			this.dmsChannelAddressMapper.batchInsert(addresslist);
		}

		/*if(channelEmployeesList!=null&&!channelEmployeesList.isEmpty()){
			this.dmsChannelEmployeeMapper.deleteByChannelId(entity.getId());
			for (DmsChannelEmployee dmsChannelEmploy:channelEmployeesList){
				dmsChannelEmploy.setChannelId(entity.getId());
				dmsChannelEmploy.setLastUpdateDate(new Date());
				dmsChannelEmploy.setLastUpdateBy(paramVo.getLastUpdatedBy());
				dmsChannelEmploy.setRemoveFlag(0);
				employeeList.add(dmsChannelEmploy);
			}
		}else{
			this.dmsChannelEmployeeMapper.deleteByChannelId(entity.getId());
		}

		if(employeeList!=null && !employeeList.isEmpty()){
			this.dmsChannelEmployeeMapper.batchInsert(employeeList);
		}*/
	}

	@Override
	public List<Long> selectChannelId(String name) {
		if (BlankUtil.isEmpty(name)){
			throw new ServiceException("渠道名称不能为空");
		}
		return dmsChannelMapper.selectChannelId(name);
	}

	@Override
	public DmsChannel getChannelInfoByUserId(Long id) {
		return dmsChannelMapper.getChannelInfoByUserId(id);
	}

	/**
	 * 统一检验参数
	 * @param paramVo
	 */
	private void checkParam(DmsChannelVo paramVo) {
		if (paramVo == null) {
			throw new ServiceException("参数异常");
		}
		if(StringUtils.isBlank(paramVo.getEasNum())){
			if(!paramVo.getPlatformEnum().equals(PlatformEnum.YANGGUANG)){
				throw new ServiceException("EAS编码不能为空");
			}
		}
		if (StringUtils.isBlank(paramVo.getName())) {
			throw new ServiceException("渠道名称不能为空");
		}
		if (StringUtils.isBlank(paramVo.getCode())) {
			throw new ServiceException("渠道编码不能为空");
		}
		if (StringUtils.isBlank(paramVo.getChannelType())) {
			throw new ServiceException("渠道类型不能为空");
		}
//		if(StringUtils.isBlank(paramVo.getAddress())){
//			throw new ServiceException("地址不能为空");
//		}
//		if (StringUtils.isBlank(paramVo.getContacts())) {
//			throw new ServiceException("联系人不能为空");
//		}
//		if (StringUtils.isBlank(paramVo.getContactsMobile())) {
//			throw new ServiceException("联系人手机不能为空");
//		}
//		List<Long> productTypeIdList = paramVo.getProductTypeIdList();
//		if(productTypeIdList == null || productTypeIdList.isEmpty()){
//			throw new ServiceException("销售产品类别不能为空");
//		}
//		List<Long> areaIdList = paramVo.getAreaIdList();
//		if(areaIdList == null || areaIdList.isEmpty()){
//			throw new ServiceException("销售地区不能为空");
//		}
		//查询数据检验
		if(paramVo.getEmployeeId() != null && paramVo.getEmployeeId() !=0){

		}
	}


	/**
	 *根据Userid 获取渠道详情
	 */
	@Override
	public DmsChannelVo selectByUserId(Long userId){

		return dmsChannelMapper.selectByUserId(userId);

	}
	/**
	 * 根据渠道id 获取渠道下的商品列表
	 */
	@Override
	public Page<DmsProductVo> findProductForMinimum(DmsProductVo product){

		try {
			PageHelper.startPage(product.getP(), product.getS());
			dmsProductMapper.findProductForMinimum(product);
			return PageHelper.endPage();
		} catch (Exception e) {
			PageHelper.endPage();
			throw new ServiceException("渠道分页查询异常--->>>");
		}

	}

	/**
	 * 递归算法解析成树形结构
	 * @param cid
	 * @return
	 * @author
	 */
	public List<TreeNode> selectRecursiveTree(Long cid) {
		if (cid == null) {
			cid = 1L;
		}

		return this.dmsChannelMapper.selectByParentId(cid);
	}

	/**
	 * 导入
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> importExcel(MultipartFile file) throws Exception {
	    Map<String, Object> result = Maps.newHashMap();
		ImportExcel excel = new ImportExcel(file, 0, 0);
		boolean isCheckPass = true;
		List<Integer> emptyRow = Lists.newArrayList();  //存在必填字段为空的行数
		Set<String> repeatCodeSet = Sets.newHashSet();  //系统中已存在的渠道编码集合

		int rowNum = 0;
		for (int i = excel.getDataRowNum(); i <= excel.getLastDataRowNum(); ++i) {

		    rowNum = i + 1;  //行号

			Row row = excel.getRow(i);
			String name = excel.getCellValue(row, 0);  //渠道名称
			String code = excel.getCellValue(row, 1);  //渠道编码
            String shortName = excel.getCellValue(row, 2);  //渠道简称
			String parentCode = excel.getCellValue(row, 3);  //上级渠道编码
			String rank = excel.getCellValue(row, 4);  //渠道等级
			String type = excel.getCellValue(row, 5);  //渠道类型
			String contacts = excel.getCellValue(row, 6);  //渠道联系人
			String contactsMobile = excel.getCellValue(row, 7);  //联系人手机
			String province = excel.getCellValue(row, 8);  //渠道地址-省
			String city = excel.getCellValue(row, 9);  //渠道地址-市
			String county = excel.getCellValue(row, 10);  //渠道地址-区
			String address = excel.getCellValue(row, 11);  //渠道地址-详细地址
			String area = excel.getCellValue(row, 12);  //归属区域
			String belongEmployee = excel.getCellValue(row, 13);  //所属业务员
			String companyPhone = excel.getCellValue(row, 14);  //公司电话
			String companyFax = excel.getCellValue(row, 15);  //公司传真
			String employeeNum = excel.getCellValue(row, 16);  //员工人数
			String companyWebsite = excel.getCellValue(row, 17);  //公司官网
            String companyCEO = excel.getCellValue(row, 18);  //ceo
			String companyIncome = excel.getCellValue(row, 19);  //年收入（万）

			//非空判断：渠道名称、渠道编码 不能为空
			if (StringUtils.isEmpty(name) || StringUtils.isEmpty(code)) {
			    emptyRow.add(rowNum);
			}

			DmsChannel channel = new DmsChannel();

			//校验渠道编码唯一
            Integer count = dmsChannelMapper.countByCode(code);
            if (count > 0) {
            	repeatCodeSet.add(code);
			} else {
                channel.setCode(code);
			}

			channel.setCompnyPhone(companyPhone);
            channel.setCompanyFax(companyFax);

			try {
				if (employeeNum != null) {
                    channel.setEmployeeNum(Long.valueOf(employeeNum));
                }
			} catch (NumberFormatException e) {

			}

		}

		return result;
	}

	/**
	 * 返回 渠道的anjoy fnumber
     * anjoy code - 渠道实体 映射集
	 * @return
	 */
	@Override
	public Map<String, DmsChannel> getChannelMap() {
	    DmsChannel param = new DmsChannel();
	    //param.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
	    List<DmsChannel> channelList = dmsChannelMapper.selectByCondition(param);

	    Map<String, DmsChannel> channelMap = Maps.newHashMap();
	    for (DmsChannel channel : channelList) {
	    	channelMap.put(channel.getCode(), channel);
		}
		return channelMap;
	}

	/**
	 * 同步安井渠道信息
	 * 调用安井的渠道同步接口，返回JSON数组格式数据
	 */
	@Override
	@Transactional(readOnly = false)
	public void anjoySyn() {
		logger.info("*************************** start 开始同步安井渠道信息 ***************************");
		/**
		 安井-渠道状态（呆死：KHZT04，停止：KHZT03，正常：KHZT01，待清	KHZT02）
		 */
		JSONArray jsonArray = AnjoySynClient.getAnjoySynDataBySQLType(AnjoySynClient.SYN_TYPE.CHANNEL);
		if(jsonArray == null || jsonArray.size() < 1){
			logger.info("***************** 安井没有返回渠道数据 *****************");
			return;
		}
		String channelTypeSkey = null;
		List<DmsDataDictionayDependent> channelTypeList = dmsDataDictionayDependentService.getListByDictKey("channel_type");
		if(!channelTypeList.isEmpty() && channelTypeList.size() > 0){
			for (DmsDataDictionayDependent dictChannelType : channelTypeList) {
				String dictName = dictChannelType.getName();
				if(StringUtil.isNotBlank(dictName) && "经销商".equalsIgnoreCase(dictName.trim())){
					channelTypeSkey = dictChannelType.getsKey();
					break;
				}
			}
			if(channelTypeSkey == null){
				channelTypeSkey = channelTypeList.get(0).getsKey();
			}
		}else {
			throw new ServiceException("获取渠道类型：channel_type 字典数据失败");
		}

		/**
		 * 同步安井渠道数据前先将DMS所有渠道数据置为：已删除状态
		 */
		Integer batchDeleteResult = dmsChannelMapper.deleteChannelSyncAnjoy();
		logger.info("DMD，共删除：{} 条渠道数据", batchDeleteResult);

		List<DmsChannel> channelList = Lists.newArrayList();
		for (Object object : jsonArray) {
			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
			DmsChannel channel = new DmsChannel();
			channel.setAnjoyId(jsonObject.getString("FID"));
			channel.setName(jsonObject.getString("FNAME_L2"));
			channel.setCode(jsonObject.getString("FNUMBER"));
			channel.setEasNum(channel.getCode());
			//channel.setActive(jsonObject.getInteger("FEFFECTEDSTATUS"));
			//渠道状态：停止	KHZT03
			/*String khzt = jsonObject.getString("KHZT");
			if(StringUtils.isNotBlank(khzt) && "KHZT03".equalsIgnoreCase(khzt)){
				//无效状态：0
				channel.setActive(DmsModuleEnums.ACTIVE_STATUS.DIABLE.getType());
				channel.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.REMOVE.getType());
			}else {
				//有效状态：1
				channel.setActive(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
				channel.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
			}*/
			channel.setActive(DmsModuleEnums.ACTIVE_STATUS.ENABLE.getType());
			channel.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
			channel.setCreatedBy(0L);
			channel.setCreatedDate(jsonObject.getDate("FCREATETIME"));
			channel.setLastUpdatedDate(jsonObject.getDate("FLASTUPDATETIME"));
			channel.setAddress(jsonObject.getString("FADDRESS"));

			if (jsonObject.get("FPARENTID") != null) {
				channel.setAnjoyParentId("FPARENTID");
			}
			//归属区域 adminOrgUnit FID：CFBIBSCID
			channel.setAnjoyCfbibscidId(jsonObject.getString("CFBIBSCID"));
			//销售区域 saleOrgUnit FID：SALEORGID
			channel.setAnjoySaleOrgId(jsonObject.getString("SALEORGID"));
			//渠道类型
			channel.setChannelType(channelTypeSkey);

			channelList.add(channel);
		}

		List<DmsChannel> newChannelList = Lists.newArrayList();
		Map<String, DmsChannel> channelMap = this.getChannelMap();
		/*DmsChannel param = new DmsChannel();
		param.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
		channelList = DmsChannelMapper.selectByCondition(param);*/

		for (DmsChannel channel : channelList) {
			DmsChannel currentChannel = channelMap.get(channel.getCode());
			if(currentChannel != null){
				channel.setId(currentChannel.getId());
				dmsChannelMapper.updateByPrimaryKeySelective(channel);
			}else {
				newChannelList.add(channel);
			}
		}

		//批量新增
		if (newChannelList.size() > 0) {
			dmsChannelMapper.batchInsert(newChannelList);
		}

		Map<String, DmsChannel> parentDmsChannelMap = this.getChannelMap();
		Collection<DmsChannel> parentDmsChannelList =  parentDmsChannelMap.values();
		for (DmsChannel dmsChannel : parentDmsChannelList){
			if(dmsChannel != null && (dmsChannel.getAnjoyParentId() != null
				&& !"".equalsIgnoreCase(dmsChannel.getAnjoyParentId()))
			){
				DmsChannel dmsChannel2 = parentDmsChannelMap.get(dmsChannel.getCode());
				if(dmsChannel2 != null && dmsChannel2.getId() != null){
					dmsChannel.setParentId(dmsChannel2.getId());
					this.updateByPrimaryKeySelective(dmsChannel);
				}
			}
		}

		/**
		 * 将渠道维护到归属区域下（dms_tree_relation）
		 */
		int count = dmsSysAreaMapper.findCount();
		if(count <= 0){//如果区域表没有数据，则先同步下数据
			dmsSysAreaService.anjoySync();
			logger.info("*************************** 同步安井-区域数据 ***************************");
		}

		logger.info("*************************** start 维护区域-渠道关系 dms_tree_relation 开始 ***************************");
		Map<String, DmsChannel> dmsChannelMap = this.getChannelMap();

		DmsSysArea dmsSysAreaCondition = new DmsSysArea();
		List<DmsSysArea> dmsSysAreaList = dmsSysAreaMapper.selectByCondition(dmsSysAreaCondition);
		Map<String, DmsSysArea> dmsSysAreaMap = new HashMap<>();
		for (DmsSysArea dmsSysArea : dmsSysAreaList){
			dmsSysAreaMap.put(dmsSysArea.getAnjoyId(), dmsSysArea);
		}
		Collection<DmsChannel> dmsChannelList = dmsChannelMap.values();
		for (DmsChannel dmsChannel : dmsChannelList) {
			// `related_type` int(11) DEFAULT NULL COMMENT '关联表类型1-dms_channel:渠道；
			// 2-dms_store：门店；3-dms_organization：组织；4-dms_sys_area：区域',
			Date curDate = new Date();
			String anjoySaleAreaId = dmsChannel.getAnjoyCfbibscidId();//安井-归属区域，DMS 销售区域
			DmsSysArea dmsSysArea = dmsSysAreaMap.get(anjoySaleAreaId);

			if(dmsSysArea != null){
				//更新渠道-区域关系
				dmsChannel.setAreaId(dmsSysArea.getId());
				this.updateByPrimaryKeySelective(dmsChannel);

				DmsTreeRelation dmsAreaTreeRelationCondition = new DmsTreeRelation();
				dmsAreaTreeRelationCondition.setRelatedType(4);
				dmsAreaTreeRelationCondition.setRelatedId(dmsSysArea.getId());
				DmsTreeRelation dmsAreaTreeRelation = dmsTreeRelationMapper.selectOneByCondition(dmsAreaTreeRelationCondition);
				//新增区域关系数据
				if(dmsAreaTreeRelation == null){
					dmsAreaTreeRelation = new DmsTreeRelation();
					dmsAreaTreeRelation.setRelatedType(4);
					dmsAreaTreeRelation.setRelatedId(dmsSysArea.getId());
					dmsAreaTreeRelation.setName(dmsSysArea.getName());
					dmsAreaTreeRelation.setParentId(0L);
					dmsAreaTreeRelation.setType(4);
					dmsAreaTreeRelation.setCreatedDate(curDate);
					dmsAreaTreeRelation.setCreatedBy(0L);
					dmsAreaTreeRelation.setLastUpdatedDate(curDate);
					dmsAreaTreeRelation.setLastUpdatedBy(0L);
					dmsAreaTreeRelation.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

					dmsTreeRelationMapper.insertSelective(dmsAreaTreeRelation);
					if(dmsAreaTreeRelation.getId() != null){
						dmsAreaTreeRelation.setPath(dmsAreaTreeRelation.getId()+".");
					}
					dmsTreeRelationMapper.updateByPrimaryKeySelective(dmsAreaTreeRelation);
				}

				//新增或修改：渠道所在区域 关系
				DmsTreeRelation dmsChannelTreeRelationCondition = new DmsTreeRelation();
				dmsChannelTreeRelationCondition.setRelatedType(1);
				dmsChannelTreeRelationCondition.setRelatedId(dmsChannel.getId());
				DmsTreeRelation dmsChannelTreeRelation = dmsTreeRelationMapper.selectOneByCondition(dmsChannelTreeRelationCondition);
				if(dmsChannelTreeRelation == null){
					dmsChannelTreeRelation = new DmsTreeRelation();
					dmsChannelTreeRelation.setRelatedType(1);
					dmsChannelTreeRelation.setRelatedId(dmsChannel.getId());
					dmsChannelTreeRelation.setName(dmsChannel.getName());
					dmsChannelTreeRelation.setParentId(dmsAreaTreeRelation.getId());
					if(dmsAreaTreeRelation.getId() != null){
						dmsChannelTreeRelation.setPath(dmsAreaTreeRelation.getId()+".");
					}
					dmsChannelTreeRelation.setType(1);
					dmsChannelTreeRelation.setCreatedDate(curDate);
					dmsChannelTreeRelation.setCreatedBy(0L);
					dmsChannelTreeRelation.setLastUpdatedDate(curDate);
					dmsChannelTreeRelation.setLastUpdatedBy(0L);
					dmsChannelTreeRelation.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

					dmsTreeRelationMapper.insertSelective(dmsChannelTreeRelation);

					String path = "";
					if(StringUtils.isNotBlank(dmsChannelTreeRelation.getPath())){
						path = dmsChannelTreeRelation.getPath();
					}
					if(dmsChannelTreeRelation.getId() != null){
						path = path + dmsChannelTreeRelation.getId()+".";
					}
					dmsChannelTreeRelation.setPath(path);
					dmsTreeRelationMapper.updateByPrimaryKeySelective(dmsChannelTreeRelation);
				}else {
					dmsChannelTreeRelation.setRelatedType(1);
					dmsChannelTreeRelation.setRelatedId(dmsChannel.getId());
					dmsChannelTreeRelation.setName(dmsChannel.getName());
					dmsChannelTreeRelation.setParentId(dmsAreaTreeRelation.getId());
					String path = "";
					if(StringUtils.isNotBlank(dmsAreaTreeRelation.getPath())){
						path = dmsAreaTreeRelation.getPath();
					}
					if(dmsChannelTreeRelation.getId() != null){
						path = path + dmsChannelTreeRelation.getId()+".";
					}
					dmsChannelTreeRelation.setPath(path);
					dmsTreeRelationMapper.updateByPrimaryKeySelective(dmsChannelTreeRelation);
				}

				//UPDATE DMS_TREE_RELATION SET PATH=REPLACE(PATH,"null",'') ;
			}else{
				try {
					String dmsChannelJson = JSON.toJSONString(dmsChannel);
					String dmsSysAreaJson = JSON.toJSONString(dmsSysArea);
					logger.error("渠道：{}, 未关联到区域数据：{}", dmsChannelJson, dmsSysAreaJson);
				}catch (Exception e){
					logger.error(e.getMessage(), e);
				}
			}
		}

		logger.info("*************************** end 维护区域-渠道关系 dms_tree_relation 结束 ***************************");
		logger.info("*************************** end 结束同步安井渠道信息 ***************************");
	}

	/**
	 * 获取渠道信用额度数据
	 * @return
	 */
	public DmsChannelInfoVo getChannelCredit(Long channelId) {
		DmsChannelInfoVo channelCreditVo = new DmsChannelInfoVo();
		BigDecimal auditAwaitSum = BigDecimal.ZERO;
		BigDecimal balance =  BigDecimal.ZERO;
		//获取当前用户待审批订单总金额
		DmsOrder param = new DmsOrder();
		param.setChannelId(channelId);
		param.setOrderStatus(DmsModuleEnums.ORDER_STATUS_TYPE.AUDIT_AWAIT.getType());
		auditAwaitSum = dmsOrderMapper.selectSumByCondition(param);
		try {
			DmsChannel dmsChannel = this.selectByPrimaryKey(channelId);
			JSONObject jsonObject = AnjoySynClient.getCreditLimitByChannelAnjoyId(dmsChannel.getAnjoyId());
			channelCreditVo.setChannelId(channelId);//渠道ID
			channelCreditVo.setFamount(new BigDecimal(jsonObject.getString("FAMOUNT")));//授信总额
			balance =new BigDecimal(jsonObject.getString("BALANCE"));
			//授信余额减去未审批订单总金额
			channelCreditVo.setBalance(balance.subtract(auditAwaitSum));
			channelCreditVo.setQk(new BigDecimal(jsonObject.getString("QK")));//欠款
			channelCreditVo.setSaleamount(new BigDecimal(jsonObject.getString("SALEAMOUNT")));//销售金额
			channelCreditVo.setCfcountenddate(jsonObject.getString("CFCOUNTENDDATE"));//账期为空，就是没有限制
			logger.info("渠道：{}, 信用额度数据：{}", JSON.toJSONString(dmsChannel), JSON.toJSONString(channelCreditVo));
		}catch (Exception e){
			logger.error("取渠道信用额度数据异常, 渠道ID：{}, {}", channelId, e.getMessage());
		}
		return channelCreditVo;
	}

	/**
	 * 判断渠道是否已被锁单
	 * true：已锁定，不允许下单
	 * false：未锁定，允许下单
	 * @param channelAnjoyId
	 * @return
     */
	public boolean getAnjoyChannelIsLock(String channelAnjoyId) {
		boolean islocked = false;
		try {
			JSONObject object = AnjoySynClient.getAnjoyChannelIsLock(channelAnjoyId);
			if(object != null){
				int cfislock = object.getIntValue("CFISLOCK");
				String cftime = object.getString("CFTIME");
				if(cfislock == 1){
					islocked = true;
				}
			}
		}catch (Exception e){
			logger.error("锁单判断异常：{}", e.getMessage(), e);
		}
		return islocked;
	}

	/**
	 * 判断渠道是否超账期
	 * true：已超，不允许下单
	 * false：未超，允许下单
	 * @param channelAnjoyCode
	 * @return
	 */
	public boolean getAnjoyChannelOverAccountPeriod(String channelAnjoyCode) {
		boolean isOvered = false;
		try {
			JSONObject object = AnjoySynClient.getAnjoyChannelOverAccountPeriod(channelAnjoyCode);
			if(object != null){
				String supplierName = object.getString("SUPPLIERNAME");
				String supplierNumber = object.getString("SUPPLIERNUMBER");
				int isOutEndDate = object.getIntValue("ISOUTENDDATE");
				if(isOutEndDate == 1){
					isOvered = true;
				}
			}
		}catch (Exception e){
			logger.error("是否超账期判断异常：{}", e.getMessage(), e);
		}
		return isOvered;
	}

	@Override
	public DmsChannelInfoVo getChannelInfo(Long channelId) {
		DmsChannelInfoVo dmsChannelInfoVo = new DmsChannelInfoVo();
		try {
			DmsChannel dmsChannel = this.selectByPrimaryKey(channelId);
			JSONObject jsonObject = AnjoySynClient.getChannelInfoByAnjoyId(dmsChannel.getAnjoyId());
			dmsChannelInfoVo.setChannelId(channelId);//渠道ID
			//获取当前用户待审批订单总金额
			DmsOrder param = new DmsOrder();
			param.setChannelId(channelId);
			param.setOrderStatus(DmsModuleEnums.ORDER_STATUS_TYPE.AUDIT_AWAIT.getType());
			BigDecimal auditAwaitSum = dmsOrderMapper.selectSumByCondition(param);
			BigDecimal availableLimit =new BigDecimal(jsonObject.getString("BALANCE"));
			dmsChannelInfoVo.setFamount(new BigDecimal(jsonObject.getString("FAMOUNT")));//授信总额
			//授信余额(需减掉当前用户未审批的订单金额)
			dmsChannelInfoVo.setBalance(availableLimit.subtract(auditAwaitSum));
			dmsChannelInfoVo.setQk(new BigDecimal(jsonObject.getString("QK")));//欠款
			dmsChannelInfoVo.setSaleamount(new BigDecimal(jsonObject.getString("SALEAMOUNT")));//销售金额
			dmsChannelInfoVo.setCfcountenddate(jsonObject.getString("CFCOUNTENDDATE"));//账期为空，就是没有限制
			String isOvered = jsonObject.getString("ISOUTENDDATE");
			String isLocked = jsonObject.getString("ISLOKED");
			try {
				if(StringUtil.isNotBlank(isOvered)){
					int over = Integer.parseInt(isOvered);
					if(over == 1){
						dmsChannelInfoVo.setIsovered(true);
					}
				}
			}catch (Exception e){
				logger.error("渠道是否超期值转化错误：{}", e.getMessage());
			}
			try {
				if(StringUtil.isNotBlank(isLocked)){
					int lock = Integer.parseInt(isLocked);
					if(lock == 1){
						dmsChannelInfoVo.setIsloked(true);
					}
				}
			}catch (Exception e){
				logger.error("渠道是否被锁定值转化错误：{}", e.getMessage());
			}
			logger.info("DMS渠道数据：{}, 安井经销商数据：{}", JSON.toJSONString(dmsChannel), JSON.toJSONString(dmsChannelInfoVo));
		}catch (Exception e){
			logger.error("取渠道数据异常, 渠道ID：{}, {}", channelId, e.getMessage());
		}
		return dmsChannelInfoVo;
	}

}