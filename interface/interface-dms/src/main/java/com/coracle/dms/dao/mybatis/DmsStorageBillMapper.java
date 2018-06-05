/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsStorageBill;
import com.coracle.dms.vo.DmsStorageBillVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsStorageBillMapper extends IMybatisDao<DmsStorageBill> {

    List<DmsStorageBillVo> getPageList(DmsStorageBillVo search);


}