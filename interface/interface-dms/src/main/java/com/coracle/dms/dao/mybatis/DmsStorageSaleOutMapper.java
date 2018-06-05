/**
 * create by xiaobu2012
 * @date 2017-09
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsStorageSaleOut;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsStorageSaleOutMapper extends IMybatisDao<DmsStorageSaleOut> {

    List<DmsStorageSaleOut> getPageList(DmsStorageSaleOut search);
}