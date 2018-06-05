package com.coracle.dms.service;

import com.coracle.dms.po.DmsChannelMinimum;
import com.coracle.dms.vo.DmsChannelVo;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsChannelMinimumService extends IBaseService<DmsChannelMinimum> {



    DmsChannelVo findProductByID(Long id);

    PageHelper.Page<DmsProductVo> findProductForMinimum(DmsProductVo product);

    DmsChannelVo findProductForMinimum2(DmsChannelVo dmsChannelVo);

    void insert(DmsChannelVo dmsChannelVo, UserSession userSession);


    void updateMinOrderQuantity(Long minOrderQuantity, Long id);

    public String selectPathId(Long categoryId);


    void batchDelete(List<DmsChannelMinimum> list);

    void batchInsert(List<DmsChannelMinimum> list);





}