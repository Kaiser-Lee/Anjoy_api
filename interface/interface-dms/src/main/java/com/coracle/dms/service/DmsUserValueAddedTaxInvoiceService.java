package com.coracle.dms.service;

import com.coracle.dms.po.DmsUserValueAddedTaxInvoice;
import com.coracle.dms.vo.DmsUserValueAddedTaxInvoiceVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

public interface DmsUserValueAddedTaxInvoiceService extends IBaseService<DmsUserValueAddedTaxInvoice> {

    /**
     * 增加增值税发票信息
     * @param invoice
     */
    void insertInvoice(DmsUserValueAddedTaxInvoice invoice);

    /**
     * 更新增值税发票信息
     * @param invoice
     */
    void update(DmsUserValueAddedTaxInvoice invoice);

    /**
     * 获取用户当前增值税发票信息
     * @param userId
     * @return
     */
    DmsUserValueAddedTaxInvoiceVo getUserCurrentInvoice(long userId);

    /**
     * 提交审批
     * @param id
     * @param userId
     */
    void submitToApprove(Long id, long userId);

    /**
     * 获取用户审核通过增值税发票信息
     * @param userId
     * @return
     */
    DmsUserValueAddedTaxInvoiceVo getApprovedInvoice(Long userId);

    /**
     * 分页查询全部的增值税发票信息
     * @param invoice
     * @return
     */
    PageHelper.Page<DmsUserValueAddedTaxInvoiceVo> selectForPageList(DmsUserValueAddedTaxInvoiceVo invoice);

    /**
     * 查询增票的详细信息包括附件信息
     * @param id
     * @return
     */
    DmsUserValueAddedTaxInvoiceVo selectVoByPrimaryKey(Long id);

    /**
     * 审核增票资质
     * @param id
     * @param userId
     * @param ststus
     * @param remark
     */
    void auditInvoice(Long id, Long userId,Integer ststus,String remark);

}