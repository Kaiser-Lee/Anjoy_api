package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsSellerMapper;
import com.coracle.dms.po.DmsContactInfo;
import com.coracle.dms.po.DmsSeller;
import com.coracle.dms.po.DmsUser;
import com.coracle.dms.service.*;
import com.coracle.dms.vo.DmsSellerVo;
import com.coracle.dms.vo.DmsUserVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DmsSellerServiceImpl extends BaseServiceImpl<DmsSeller> implements DmsSellerService {
    private static final Logger logger = Logger.getLogger(DmsSellerServiceImpl.class);

    @Autowired
    private DmsSellerMapper dmsSellerMapper;
    @Autowired
    private DmsStoreService dmsStoreService;
    @Autowired
    private DmsContactInfoService dmsContactInfoService;
    @Autowired
    private DmsAttachmentRelationService dmsAttachmentRelationService;
    @Autowired
    private DmsRemarkService dmsRemarkService;
    @Autowired
    private DmsUserService dmsUserService;
    @Autowired
    private DmsDataDictionayDependentService dmsDataDictionayDependentService;
    @Autowired
    private DmsDataDictionayService dmsDataDictionayService;

    @Override
    public IMybatisDao<DmsSeller> getBaseDao() {
        return dmsSellerMapper;
    }

    /***
     * 新增或者修改门店店员
     *
     * @param dmsSellerVo
     * @param session
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void saveOrUpdate(DmsSellerVo dmsSellerVo, UserSession session) {
        checkParamSeller(dmsSellerVo);
        //通过手机号码查询联系人
        List<DmsContactInfo> contactInfoList = dmsSellerVo.getDmsContactInfoList();
        //获取手机号列表
        //List<String> mobiles = getMobiles(contactInfoList,1);
        Long userId = session.getId();
        if (BlankUtil.isEmpty(dmsSellerVo.getSex())) {
            dmsSellerVo.setSex("0");
        }

        //新增
        if (BlankUtil.isEmpty(dmsSellerVo.getId()) || dmsSellerVo.getId() == 0) {
            dmsSellerVo.setId(null);
            dmsSellerVo.setCreatedBy(userId);
            dmsSellerVo.setLastUpdatedBy(userId);
            dmsSellerVo.setRemoveFlag(0);
            dmsSellerMapper.insert(dmsSellerVo);
            //保存联系方式信息
            if (BlankUtil.isNotEmpty(contactInfoList)) {
                batchAdd(contactInfoList, dmsSellerVo, userId);
            }
        } else {//修改
            DmsSeller dmsSeller = dmsSellerMapper.selectByPrimaryKey(dmsSellerVo.getId());
            if (BlankUtil.isEmpty(dmsSeller)) {
                throw new ServiceException("无法获取当前id为" + dmsSeller.getId() + "的信息！");
            }
            //如果是店长,校验原先是否是店长，或者门店是否存在店长
            if (BlankUtil.isNotEmpty(dmsSellerVo.getPost()) && "1".equals(dmsSellerVo.getPost())) {
                DmsSeller ds = new DmsSeller();
                ds.setShopId(dmsSellerVo.getShopId());
                ds.setRemoveFlag(0);
                ds.setPost("1");
                ds.setActive(1);
                List<DmsSeller> dmsSellerList = dmsSellerMapper.selectByCondition(ds);
                if (BlankUtil.isNotEmpty(dmsSellerList)) {
                    if (dmsSellerList.get(0).getId() != dmsSeller.getId().longValue()) {
                        throw new ServiceException("已存在店长，无法新增店长！");
                    }
                }
            }
            dmsSellerVo.setLastUpdatedBy(userId);
            dmsSellerMapper.updateByPrimaryKeySelective(dmsSellerVo);

            DmsContactInfo dci = new DmsContactInfo();
            dci.setRelatedTableId(dmsSeller.getId());
            dci.setRelatedTableType(DmsModuleEnums.CONTACT_RELATED_TABLE_TYPE.STORE_CONTACT.getType());
            dci.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
            List<DmsContactInfo> contactInfos = dmsContactInfoService.selectByCondition(dci);
            if (BlankUtil.isEmpty(contactInfos)) {//当联系信息为空时，为插入
                if (BlankUtil.isNotEmpty(contactInfoList)) {
                    batchAdd(contactInfoList, dmsSellerVo, userId);
                }
            } else {//否则走修改
                List<Long> ids = new ArrayList<>();
                for (DmsContactInfo d : contactInfos) {
                    ids.add(d.getId());
                }
                DmsContactInfo dmsContactInfo = new DmsContactInfo();
                dmsContactInfo.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.REMOVE.getType());

                dmsContactInfoService.updateByIdsSelective(dmsContactInfo, ids);
                if (BlankUtil.isNotEmpty(contactInfoList)) {
                    batchAdd(contactInfoList, dmsSellerVo, userId);
                }
            }
        }
        if (BlankUtil.isNotEmpty(dmsSellerVo.getIsOpenAccount()) && dmsSellerVo.getIsOpenAccount() == 1) {
            openAccount(dmsSellerVo.getId(), session);
        }
    }

    /**
     * 获取手机号码集合
     *
     * @param contactInfoList
     * @param type            联系方式类型：1-手机；2-座机；3-Email；4-QQ；5-微信；6-钉钉
     * @return
     */
    private List<String> getMobiles(List<DmsContactInfo> contactInfoList, Integer type) {
        List<String> ids = new ArrayList<>();
        for (DmsContactInfo contactInfo : contactInfoList) {
            if (contactInfo.getType().equals(type)) {
                ids.add(contactInfo.getContent());
            }
        }
        return ids;
    }


    /**
     * 批量添加
     *
     * @param contactInfoList
     * @param dmsSellerVo
     * @param userId
     */
    private void batchAdd(List<DmsContactInfo> contactInfoList, DmsSellerVo dmsSellerVo, Long userId) {
        for (DmsContactInfo contactInfo : contactInfoList) {
            contactInfo.setRelatedTableType(DmsModuleEnums.CONTACT_RELATED_TABLE_TYPE.STORE_CONTACT.getType());
            contactInfo.setRelatedTableId(dmsSellerVo.getId());
            contactInfo.setCreatedBy(userId);
            contactInfo.setCreatedDate(new Date());
            contactInfo.setRemoveFlag(0);
        }
        dmsContactInfoService.batchInsert(contactInfoList);
    }

    /**
     * 更新联系人信息
     *
     * @param dmsContactInfoL
     * @param dmsContactInfo
     * @param userSession
     */
    private void updateContactInfo(DmsContactInfo dmsContactInfoL, DmsContactInfo dmsContactInfo, UserSession userSession) {
        dmsContactInfoL.setContent(dmsContactInfo.getContent());
        dmsContactInfoL.setLastUpdatedDate(new Date());
        dmsContactInfoL.setLastUpdatedBy(userSession.getId());
        dmsContactInfoService.updateByPrimaryKeySelective(dmsContactInfoL);
    }

    @Override
    public List<DmsSellerVo> selectVoByCondition(DmsSellerVo dmsSellerVo, UserSession userSession) {
        return dmsSellerMapper.selectVoByCondition(dmsSellerVo);
    }

    @Override
    public PageHelper.Page<DmsSellerVo> pageList(DmsSellerVo dmsSellerVo) {
        try {
            PageHelper.startPage(dmsSellerVo.getP(), dmsSellerVo.getS());
            dmsSellerMapper.selectVoByCondition(dmsSellerVo);
        } catch (Exception e) {
            logger.error("账号分页查询异常!", e);
            throw new ServiceException("账号分页查询异常!");
        } finally {
            return PageHelper.endPage();
        }
    }

    @Override
    public DmsSellerVo selectVoByPrimaryKey(Long id) {
        return dmsSellerMapper.selectVoByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> getSellerDetail(Long id, UserSession userSession) {
        Map<String, Object> result = new HashMap<String, Object>();
        //店员基本信息
        DmsSellerVo dmsSellerVo = selectVoByPrimaryKey(id);
        if (BlankUtil.isEmpty(dmsSellerVo) || BlankUtil.isEmpty(dmsSellerVo.getId()) || dmsSellerVo.getId() == 0) {//未查到数据
            throw new ServiceException("无法获取id为[" + id + "]的门店店员信息！");
        }
        if (BlankUtil.isNotEmpty(dmsSellerVo.getDmsContactInfoMapList())) {
            for (Map<String, Object> map : dmsSellerVo.getDmsContactInfoMapList()) {
                if (BlankUtil.isNotEmpty(map.get("type"))) {
                    int type = Integer.parseInt(map.get("type").toString());
                    switch (type) {
                        case 1:
                            if (BlankUtil.isEmpty(dmsSellerVo.getMobileText()))
                                dmsSellerVo.setMobileText(map.get("content") == null ? "" : map.get("content").toString());
                            break;
                        case 2:
                            dmsSellerVo.setPhoneText(map.get("content") == null ? "" : map.get("content").toString());
                            break;
                        case 3:
                            dmsSellerVo.setEmailText(map.get("content") == null ? "" : map.get("content").toString());
                            break;
                        case 4:
                            dmsSellerVo.setQqText(map.get("content") == null ? "" : map.get("content").toString());
                            break;
                        case 5:
                            dmsSellerVo.setWeiText(map.get("content") == null ? "" : map.get("content").toString());
                            break;
                    }
                }
            }
        }
        result.put("dmsSellerVo", dmsSellerVo);
        return result;
    }

    /**
     * 开通账号
     */
    @Override
    public String openAccount(Long sellerId, UserSession session) {
        DmsSellerVo dmsSellerVo = dmsSellerMapper.selectVoByPrimaryKey(sellerId);
        if (BlankUtil.isEmpty(dmsSellerVo)) {
            throw new ServiceException("无法获取id为[" + sellerId + "]的门店店员信息！");
        }
        DmsContactInfo dmsContactInfo = new DmsContactInfo();
        dmsContactInfo.setRelatedTableType(DmsModuleEnums.CONTACT_RELATED_TABLE_TYPE.STORE_CONTACT.getType());
        dmsContactInfo.setRelatedTableId(sellerId);
        dmsContactInfo.setType(DmsModuleEnums.CONTACT_TYPE.MOBILE.getType());
        List<DmsContactInfo> list = dmsContactInfoService.selectByCondition(dmsContactInfo);
        if (list == null || list.size() == 0) {
            throw new ServiceException("无法获取id为[" + sellerId + "]的门店店员手机号码！");
        }
        DmsUserVo dmsUserVo = new DmsUserVo();
        dmsUserVo.setCreatedBy(session.getId());
        dmsUserVo.setAccount(list.get(0).getContent());//如果手机有多个获取第一个开通账号
        dmsUserVo.setName(dmsSellerVo.getName());
        dmsUserVo.setSource(DmsModuleEnums.ACCOUNT_SOURCE_TYPE.SELLER.getType());
        dmsUserVo.setStaffId(dmsSellerVo.getId());
        DmsUser dmsUser = dmsUserService.create(dmsUserVo, session);
        //增加账号ID
        DmsSeller dmsSeller = new DmsSeller();
        dmsSeller.setUserId(dmsUser.getId());
        dmsSeller.setId(dmsSellerVo.getId());
        dmsSellerMapper.updateByPrimaryKeySelective(dmsSeller);
        return dmsContactInfo.getContent();
    }

    /**
     * 根据门店id列表获取用户ID列表
     *
     * @param ids
     * @return
     */
    @Override
    public List<Long> getUserIdsByStoreIds(List<Long> ids) {
        return dmsSellerMapper.getUserIdsByStoreIds(ids);
    }

    public Long getStoreIdByUserId(Long id) {
        return dmsSellerMapper.getStoreIdByUserId(id);
    }

    /**
     * 统一检验参数
     *
     * @param dmsSellerVo
     */
    private void checkParamSeller(DmsSellerVo dmsSellerVo) {
        if (dmsSellerVo == null) {
            throw new ServiceException("参数异常");
        }
        if (BlankUtil.isEmpty(dmsSellerVo.getName())) {
            throw new ServiceException("门店店员姓名不能为空");
        }
        if (BlankUtil.isEmpty(dmsSellerVo.getActive())) {
            throw new ServiceException("门店店员状态不能为空");
        }
        if (dmsSellerVo.getPost() == null) {
            throw new ServiceException("门店店员岗位不能为空");
        }
    }
}