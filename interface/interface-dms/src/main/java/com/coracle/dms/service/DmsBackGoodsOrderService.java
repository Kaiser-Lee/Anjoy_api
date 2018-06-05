package com.coracle.dms.service;

import com.coracle.dms.po.DmsBackGoodsOrder;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;
import org.springframework.web.bind.annotation.RequestBody;

public interface DmsBackGoodsOrderService extends IBaseService<DmsBackGoodsOrder> {

    void insertObject(DmsBackGoodsOrder dmsBackGoodsOrder);
    PageHelper.Page selectPageList(DmsBackGoodsOrder vo);

}