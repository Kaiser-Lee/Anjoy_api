/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsStorageTransportation;
import com.coracle.dms.vo.DmsStorageTransportationVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsStorageTransportationMapper extends IMybatisDao<DmsStorageTransportation> {

    List<DmsStorageTransportationVo> getPageList(DmsStorageTransportationVo search);
    void updateById(Long id);

}