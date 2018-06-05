/**
 * create by tanyb
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsTransferDelivery;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

public interface DmsTransferDeliveryMapper extends IMybatisDao<DmsTransferDelivery> {
	
	DmsTransferDelivery selectByApplyId(Long applyId);
}