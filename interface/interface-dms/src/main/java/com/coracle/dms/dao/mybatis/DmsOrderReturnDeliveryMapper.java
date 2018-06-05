/**
 * create by tanyb
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsOrderReturnDelivery;
import com.coracle.dms.vo.DmsOrderReturnDeliveryVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

public interface DmsOrderReturnDeliveryMapper extends IMybatisDao<DmsOrderReturnDelivery> {
	
	DmsOrderReturnDeliveryVo getDeliveryInfo(Long id);
	
	DmsOrderReturnDeliveryVo getDeliveryInfoByCahnnel(Long id);
}