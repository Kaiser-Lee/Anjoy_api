/**
 * create by hcs
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsOrderDelivery;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DmsOrderDeliveryMapper extends IMybatisDao<DmsOrderDelivery> {

    Integer deleteOrderDeliveryHistory(@Param(value = "ids") List<Long> ids);

    Integer deleteOrderDeliveryItemHistory(@Param(value = "ids")List<Long> ids);

}