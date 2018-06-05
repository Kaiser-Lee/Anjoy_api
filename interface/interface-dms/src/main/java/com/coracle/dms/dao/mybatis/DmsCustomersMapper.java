/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsCustomers;
import com.coracle.dms.vo.DmsCustomersVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsCustomersMapper extends IMybatisDao<DmsCustomers> {

    List<DmsCustomersVo> getPageList(DmsCustomersVo dmsCustomersVo);

    DmsCustomersVo selectVoByPrimaryKey(Long id);
}