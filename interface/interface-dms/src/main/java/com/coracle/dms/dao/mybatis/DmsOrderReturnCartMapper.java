/**
 * create by tanyb
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import java.util.List;

import com.coracle.dms.po.DmsOrderReturnCart;
import com.coracle.dms.vo.DmsOrderReturnCartVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

public interface DmsOrderReturnCartMapper extends IMybatisDao<DmsOrderReturnCart> {
	
	List<DmsOrderReturnCartVo> getPageList(DmsOrderReturnCartVo search);
	
	void deleteByIdSoft(Object id);
	
	void batchRemove(List<Long> ids);
	
	Integer getOrderReturnCartCount(DmsOrderReturnCart paramVo);
	
	Integer selectReturnCount(DmsOrderReturnCart paramVo);
}