package com.coracle.dms.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsChannelContactsMapper;
import com.coracle.dms.po.DmsChannel;
import com.coracle.dms.po.DmsChannelContacts;
import com.coracle.dms.po.DmsContactInfo;
import com.coracle.dms.po.DmsUser;
import com.coracle.dms.service.DmsAttachmentRelationService;
import com.coracle.dms.service.DmsChannelContactsService;
import com.coracle.dms.service.DmsChannelService;
import com.coracle.dms.service.DmsContactInfoService;
import com.coracle.dms.service.DmsRemarkService;
import com.coracle.dms.service.DmsUserService;
import com.coracle.dms.vo.DmsChannelContactsVo;
import com.coracle.dms.vo.DmsUserVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper.Page;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.xiruo.medbid.util.BeanConvertHelper;

@Service
public class DmsChannelContactsServiceImpl extends BaseServiceImpl<DmsChannelContacts> implements DmsChannelContactsService {
	
	private final Logger logger = LoggerFactory.getLogger(DmsChannelServiceImpl.class.getName());

    @Autowired
    private DmsChannelContactsMapper dmsChannelContactsMapper;
    @Autowired
    private DmsContactInfoService dmsContactInfoService;
    @Autowired
    private DmsChannelService dmsChannelService;
    @Autowired
	private DmsRemarkService dmsRemarkService;
    @Autowired
    private DmsAttachmentRelationService dmsAttachmentRelationService;
    @Autowired
	private DmsUserService dmsUserService;

    @Override
    public IMybatisDao<DmsChannelContacts> getBaseDao() {
        return dmsChannelContactsMapper;
    }
    
    @SuppressWarnings("unchecked")
	/**
	 * 分页查询
	 */

	@Override
	public Page<DmsChannelContactsVo> selectForListPage(DmsChannelContactsVo searchVo) {
		try {
            PageHelper.startPage(searchVo.getP(), searchVo.getS());
            dmsChannelContactsMapper.getPageList(searchVo);
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            this.logger.error("查询渠道联系人异常！",e);
            throw new ServiceException("渠道联系人分页查询异常--->>>");
        }
	}

	@Override
	public Map<String, Object> detail(Long id, UserSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		DmsChannelContactsVo dmsChannelContact = this.dmsChannelContactsMapper.selectVoByPrimaryKey(id);
		if (dmsChannelContact == null || dmsChannelContact.getId() == null) {
			throw new ControllerException("获取渠道联系人数据为空！");
		}
		if (BlankUtil.isNotEmpty(dmsChannelContact.getContactInfoList())) {
			for (DmsContactInfo contactInfo : dmsChannelContact.getContactInfoList()) {
				switch (contactInfo.getType()){
	                case 1:
						if(!BlankUtil.isEmpty(dmsChannelContact.getMobileText())){
							dmsChannelContact.setMobileText(dmsChannelContact.getMobileText()+"/"+contactInfo.getContent());
						}else {
							dmsChannelContact.setMobileText(contactInfo.getContent());
						}
						break;
	                case 2:dmsChannelContact.setPhoneText(contactInfo.getContent());break;
	                case 3:dmsChannelContact.setEmailText(contactInfo.getContent());break;
	                case 4:dmsChannelContact.setQqText(contactInfo.getContent());break;
	                case 5:dmsChannelContact.setWeiText(contactInfo.getContent());break;
				}
			}
		}
		result.put("dmsChannelContact", dmsChannelContact);
		return result;
	}
    /**
     * 保存渠道联系人信息
     * 针对于联系信息保存在中间表DmsContactInfo
     */
	@Override
	public void create(DmsChannelContactsVo contactsVo, UserSession session) {
		this.checkParam(contactsVo);
		this.dmsChannelContactsMapper.insert(contactsVo);
		Long contactId = contactsVo.getId();
		// 保存联系方式信息
		List<DmsContactInfo> contactInfoList = contactsVo.getContactInfoList();
		if (BlankUtil.isNotEmpty(contactInfoList)) {
			for (DmsContactInfo contactInfo : contactInfoList) {
				contactInfo.setRelatedTableType(DmsModuleEnums.CONTACT_RELATED_TABLE_TYPE.CHANNEL_CONTACT.getType());
				contactInfo.setRelatedTableId(contactId);
				contactInfo.setCreatedBy(contactsVo.getCreatedBy());
				contactInfo.setCreatedDate(new Date());
				contactInfo.setRemoveFlag(0);
			}
			dmsContactInfoService.batchInsert(contactInfoList);
		}
	}
	@Override
	public void update(DmsChannelContactsVo contactsVo, UserSession session) {
		if (contactsVo == null || contactsVo.getId() == null) {
			throw new ServiceException("参数异常");
		}
		DmsChannelContacts entity = this.dmsChannelContactsMapper.selectByPrimaryKey(contactsVo.getId());
		if (entity == null) {
			throw new ServiceException("数据库获取渠道联系人信息为空");
		}
		this.dmsChannelContactsMapper.updateByPrimaryKeySelective(contactsVo);
		DmsContactInfo dci = new DmsContactInfo();
		dci.setRelatedTableId(contactsVo.getId());
		dci.setRelatedTableType(DmsModuleEnums.CONTACT_RELATED_TABLE_TYPE.CHANNEL_CONTACT.getType());
		List<DmsContactInfo> contactInfos = dmsContactInfoService.selectByCondition(dci);
		List<DmsContactInfo> contactInfoList = contactsVo.getContactInfoList();
		if (BlankUtil.isNotEmpty(contactInfos)) {
			for (DmsContactInfo dd : contactInfos) {
				this.dmsContactInfoService.deleteByRelated(dd);
			}
		}
		if (BlankUtil.isNotEmpty(contactInfoList)) {
			for (DmsContactInfo contactInfo : contactInfoList) {
				contactInfo.setRelatedTableType(DmsModuleEnums.CONTACT_RELATED_TABLE_TYPE.CHANNEL_CONTACT.getType());
				contactInfo.setRelatedTableId(contactsVo.getId());
				contactInfo.setCreatedBy(session.getId());
				contactInfo.setCreatedDate(new Date());
				contactInfo.setRemoveFlag(0);
			}
			dmsContactInfoService.batchInsert(contactInfoList);
		}
	}
	@Override
	public void createAccount(Long contactId, UserSession session) {
		DmsChannelContacts contact = this.dmsChannelContactsMapper.selectByPrimaryKey(contactId);
		if (contact == null) {
			throw new ServiceException("渠道联系人信息不存在！");
		}
		// 从联系方式表中获取手机号码
		DmsContactInfo contactInfoSearch = new DmsContactInfo();
		contactInfoSearch.setRelatedTableType(DmsModuleEnums.CONTACT_RELATED_TABLE_TYPE.CHANNEL_CONTACT.getType());
		contactInfoSearch.setRelatedTableId(contactId);
		contactInfoSearch.setType(DmsModuleEnums.CONTACT_TYPE.MOBILE.getType());
		List<DmsContactInfo> contactInfoList = dmsContactInfoService.selectByCondition(contactInfoSearch);
		if (BlankUtil.isEmpty(contactInfoList)) {
			throw new ServiceException("渠道联系人手机号码为空！");
		}
		DmsUserVo userVo = new DmsUserVo();
		userVo.setAccount(contactInfoList.get(0).getContent());
		userVo.setName(contact.getName());
		userVo.setSource(DmsModuleEnums.ACCOUNT_SOURCE_TYPE.CHANNEL_CONTACTS.getType());
		userVo.setStaffId(contactId);
		userVo.setCreatedBy(session.getId());
		DmsUser user = dmsUserService.create(userVo, session);
		// 回写联系人用户id
		contact.setUserId(user.getId());
		contact.setLastUpdatedDate(new Date());
		contact.setLastUpdatedBy(session.getId());
		this.dmsChannelContactsMapper.updateByPrimaryKeySelective(contact);
	}
	/**
	 * 根据渠道id列表获取用户ID列表
	 * @param ids
	 * @return
	 */
	@Override
	public List<Long> getUserIdsByChannelIds(List<Long> ids) {
		return dmsChannelContactsMapper.getUserIdsByChannelIds(ids);
	}

	/**
	 * 根据用户id查询渠道联系人.包括手机号码
	 */
	@Override
	public DmsChannelContactsVo queryContactByUserId(Long userId) {
		DmsChannelContacts channelContact = this.dmsChannelContactsMapper.queryContactByUserId(userId);
		DmsChannelContactsVo channelContactVo = BeanConvertHelper.copyProperties(channelContact, DmsChannelContactsVo.class);
		// 从联系方式表中获取手机号码
		DmsContactInfo contactInfoSearch = new DmsContactInfo();
		contactInfoSearch.setRelatedTableType(DmsModuleEnums.CONTACT_RELATED_TABLE_TYPE.CHANNEL_CONTACT.getType());
		contactInfoSearch.setRelatedTableId(channelContactVo.getId());
		contactInfoSearch.setType(DmsModuleEnums.CONTACT_TYPE.MOBILE.getType());
		List<DmsContactInfo> contactInfoList = dmsContactInfoService.selectByCondition(contactInfoSearch);
		if (BlankUtil.isEmpty(contactInfoList)) {
			throw new ServiceException("渠道联系人手机号码为空！");
		}
		channelContactVo.setMobile(contactInfoList.get(0).getContent());
		return channelContactVo;
	}
	/**
	 * 数据检验
	 * @param contactsVo
	 */
	private void checkParam(DmsChannelContactsVo contactsVo) {
		if(contactsVo == null){
			throw new ServiceException("参数异常");
		}
		if(contactsVo.getChannelId() == null || contactsVo.getChannelId().intValue() == 0){
			throw new ServiceException("渠道ID不能为空");
		}
		DmsChannel channel = dmsChannelService.selectByPrimaryKey(contactsVo.getChannelId());
		if(channel == null){
			throw new ServiceException("输入的渠道ID不存在，请重新输入");

		}



		//查询部门、岗位是否存在判断
		
	}

}