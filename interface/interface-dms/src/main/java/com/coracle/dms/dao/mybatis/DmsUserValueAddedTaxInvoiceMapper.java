/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsUserValueAddedTaxInvoice;
import com.coracle.dms.vo.DmsUserValueAddedTaxInvoiceVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsUserValueAddedTaxInvoiceMapper extends IMybatisDao<DmsUserValueAddedTaxInvoice> {

	List<DmsUserValueAddedTaxInvoiceVo> getPageList(DmsUserValueAddedTaxInvoiceVo search);
    /***
     * 失效当前用户的发票信息
     * @param dmsUserValueAddedTaxInvoice
     */
    void  failureOldDmsUserValueAddedTaxInvoice(DmsUserValueAddedTaxInvoice dmsUserValueAddedTaxInvoice);

    /**
     * 查询增票的详细信息包括附件信息
     * @param id
     * @return
     */
    DmsUserValueAddedTaxInvoiceVo selectVoByPrimaryKey(Long id);

    /**
     * 查询增票的详细信息包括附件信息
     * @param id 用户ID
     * @return
     */
    List<DmsUserValueAddedTaxInvoiceVo> selectVoByUserId(Long id);

    /**
     * 查询增票的详细信息包括附件信息
     * @param dmsUserValueAddedTaxInvoice
     * @return
     */
    List<DmsUserValueAddedTaxInvoiceVo> selectVoByCondition(DmsUserValueAddedTaxInvoice dmsUserValueAddedTaxInvoice);
}