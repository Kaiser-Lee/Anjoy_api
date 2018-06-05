package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsUserValueAddedTaxInvoiceMapper;
import com.coracle.dms.dto.DmsUserInfoDto;
import com.coracle.dms.po.DmsUser;
import com.coracle.dms.po.DmsUserValueAddedTaxInvoice;
import com.coracle.dms.service.DmsMessageService;
import com.coracle.dms.service.DmsUserService;
import com.coracle.dms.service.DmsUserValueAddedTaxInvoiceService;
import com.coracle.dms.vo.DmsUserValueAddedTaxInvoiceVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.constants.CommonConstants;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DmsUserValueAddedTaxInvoiceServiceImpl extends BaseServiceImpl<DmsUserValueAddedTaxInvoice> implements DmsUserValueAddedTaxInvoiceService {
    private static final Logger logger = Logger.getLogger(DmsUserValueAddedTaxInvoiceServiceImpl.class);

    @Autowired
    private DmsUserValueAddedTaxInvoiceMapper dmsUserValueAddedTaxInvoiceMapper;
    @Autowired
    private DmsMessageService dmsMessageService;
    @Autowired
    private DmsUserService dmsUserService;

    @Override
    public IMybatisDao<DmsUserValueAddedTaxInvoice> getBaseDao() {
        return dmsUserValueAddedTaxInvoiceMapper;
    }

    /**
     * 增加增值税发票信息
     * @param invoice
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertInvoice(DmsUserValueAddedTaxInvoice invoice) {
        invoice.setStatus(CommonConstants.APPROVE_STATUS_DSP);
        //失效历史记录
        dmsUserValueAddedTaxInvoiceMapper.failureOldDmsUserValueAddedTaxInvoice(invoice);
        dmsUserValueAddedTaxInvoiceMapper.insert(invoice);
        //消息推送
        sendMessage(invoice, DmsModuleEnums.APPROVE_STATUS.APPROVE_STATUS_DSP.getType(),invoice.getUserId());
    }

    /**
     * 更新增值税发票信息
     * @param invoice
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DmsUserValueAddedTaxInvoice invoice) {
        DmsUserValueAddedTaxInvoice dmsUserValueAddedTaxInvoice = dmsUserValueAddedTaxInvoiceMapper.selectByPrimaryKey(invoice.getId());
        if (BlankUtil.isEmpty(dmsUserValueAddedTaxInvoice)) {
            throw new ServiceException("未查询到增票信息！");
        }
        dmsUserValueAddedTaxInvoice.setStatus(DmsModuleEnums.APPROVE_STATUS.APPROVE_STATUS_DSP.getType());
        dmsUserValueAddedTaxInvoiceMapper.updateByPrimaryKeySelective(invoice);
        //消息推送
        sendMessage(invoice, DmsModuleEnums.APPROVE_STATUS.APPROVE_STATUS_DSP.getType(),invoice.getUserId());
    }

    /**
     * 获取用户当前增值税发票信息
     * @param userId
     * @return
     */
    @Override
    public DmsUserValueAddedTaxInvoiceVo getUserCurrentInvoice(long userId) {
        List<DmsUserValueAddedTaxInvoiceVo> invoices = dmsUserValueAddedTaxInvoiceMapper.selectVoByUserId(userId);
        if(BlankUtil.isNotEmpty(invoices)) {
            if(invoices.size() == 1) {
                return invoices.get(0);
            } else {
                throw new ServiceException("查询到多个增值税发票信息，"+userId);
            }
        } else {
            return null;
        }
    }

    /**
     * 提交审批
     * @param id
     * @param userId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitToApprove(Long id, long userId) {
        changeStatus(id, DmsModuleEnums.APPROVE_STATUS.APPROVE_STATUS_DSP.getType(), userId,"");
    }

    @Override
    public DmsUserValueAddedTaxInvoiceVo getApprovedInvoice(Long userId) {
        DmsUserValueAddedTaxInvoice invoice = new DmsUserValueAddedTaxInvoice();
        invoice.setRemoveFlag(0);
        invoice.setUserId(userId);
        invoice.setStatus(DmsModuleEnums.APPROVE_STATUS.APPROVE_STATUS_TG.getType());//审批通过
        List<DmsUserValueAddedTaxInvoiceVo> invoices = dmsUserValueAddedTaxInvoiceMapper.selectVoByCondition(invoice);
        if(BlankUtil.isNotEmpty(invoices)) {
            if(invoices.size() == 1) {
                return invoices.get(0);
            } else {
                throw new ServiceException("查询到多个审批通过的增值税发票信息，"+userId);
            }
        } else {
            return null;
        }
    }

    /**
     * 分页查询全部的增值税发票信息
     * @param invoice
     * @return
     */
    @Override
    public PageHelper.Page<DmsUserValueAddedTaxInvoiceVo> selectForPageList(DmsUserValueAddedTaxInvoiceVo invoice) {
        try {
            PageHelper.startPage(invoice.getP(), invoice.getS());
            dmsUserValueAddedTaxInvoiceMapper.getPageList(invoice);
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            throw new ServiceException(e,"分页查询异常");
        }
    }

    @Override
    public DmsUserValueAddedTaxInvoiceVo selectVoByPrimaryKey(Long id) {
        return dmsUserValueAddedTaxInvoiceMapper.selectVoByPrimaryKey(id);
    }

    /**
     * 提交审批
     * @param id
     * @param userId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditInvoice(Long id, Long userId, Integer ststus, String remark) {
        if (ststus==2){
            changeStatus(id,DmsModuleEnums.APPROVE_STATUS.APPROVE_STATUS_TG.getType(),userId,remark);
        }else {
            changeStatus(id,DmsModuleEnums.APPROVE_STATUS.APPROVE_STATUS_BTG.getType(),userId,remark);
        }
    }

    /**
     * 按照要求修改增值税发票信息的状态
     * @param id
     * @param status
     * @param userId
     */
    private void changeStatus(Long id, int status, long userId,String remark) {
        DmsUserValueAddedTaxInvoice old = dmsUserValueAddedTaxInvoiceMapper.selectByPrimaryKey(id);
        if (BlankUtil.isEmpty(old)||old.getRemoveFlag()==1){
            throw new ServiceException("id为"+id+"的 发票信息不存在或已删除！");
        }
        if (status==DmsModuleEnums.APPROVE_STATUS.APPROVE_STATUS_TG.getType()){
            old.setAuditOpinion("同意");
            old.setApproveUserId(userId);
            old.setApproveDate(new Date());
        }else if (status==DmsModuleEnums.APPROVE_STATUS.APPROVE_STATUS_BTG.getType()){
            old.setAuditOpinion("拒绝");
            old.setApproveUserId(userId);
            old.setRejectDate(new Date());
        }
        old.setRemark(remark);
        old.setStatus(status);
        Date oldDate = old.getLastUpdatedDate();
        old.setLastUpdatedBy(userId);
        old.setLastUpdatedDate(new Date());
        dmsUserValueAddedTaxInvoiceMapper.updateByPrimaryKeySelective(old);
        // 消息推送
        sendMessage(old, status,userId);
    }

    private void sendMessage(DmsUserValueAddedTaxInvoice invoice, Integer statusDicId,Long userId){
        //发送状态变更提醒
        String message = "";
        String title = "";
        Long staffId = 0L;
        if(statusDicId == DmsModuleEnums.APPROVE_STATUS.APPROVE_STATUS_BTG.getType()) {
            title="商家已拒绝了资料更新";
            message = "您提交的增票资质审核不通过";
            staffId = invoice.getUserId();
        } else if(statusDicId == DmsModuleEnums.APPROVE_STATUS.APPROVE_STATUS_TG.getType()){
            title="商家已同意了资料更新";
            message = "商家同意了本次增票资质信息更新。";
            staffId = invoice.getUserId();
        } else {
            DmsUser dmsUser = dmsUserService.selectByPrimaryKey(invoice.getUserId());
            if (BlankUtil.isEmpty(dmsUser)){
                throw new ServiceException("未获取到用户信息");
            }
            DmsUserInfoDto dmsUserInfoDto = new DmsUserInfoDto();
            dmsUserInfoDto.setSource(dmsUser.getSource());
            dmsUserInfoDto.setStaffId(dmsUser.getStaffId());
            List<Map<String,Object>> list = dmsUserService.selectUserDetail(dmsUserInfoDto);
            if (BlankUtil.isEmpty(list)){
                throw new ServiceException("未获取到用户组织信息");
            }
            title="增票资质更新需要您审核";
            message = list.get(0).get("orgName")==null?"更新了增票资质，需要您审核":list.get(0).get("orgName").toString()+"更新了增票资质，需要您审核";
            staffId = 0L;
            List<Long> staffList = new ArrayList<>();
            staffList.add(invoice.getUserId());
            //先给用户自己增加一条记录但设置为列表不显示
            dmsMessageService.sendMessage(DmsModuleEnums.MESSAGE_TYPE.MESSAGE_INVOICE.getType(), DmsModuleEnums.MESSAGE_ENTITY_TYPE.DMS_INVOICE.getType(), invoice.getId(),
                   "您提交了增票资质更新。" , list.get(0).get("orgName")==null?"提交了资料更新":list.get(0).get("orgName").toString()+"提交了资料更新",list.get(0).get("orgName")==null?"提交了资料更新":list.get(0).get("orgName").toString()+"提交了资料更新",staffList,0,userId);
        }
        List<Long> staffs = new ArrayList<>();
        staffs.add(staffId);
        dmsMessageService.sendMessage(DmsModuleEnums.MESSAGE_TYPE.MESSAGE_INVOICE.getType(), DmsModuleEnums.MESSAGE_ENTITY_TYPE.DMS_INVOICE.getType(), invoice.getId(),title,message, message, staffs,1,userId);
    }
}