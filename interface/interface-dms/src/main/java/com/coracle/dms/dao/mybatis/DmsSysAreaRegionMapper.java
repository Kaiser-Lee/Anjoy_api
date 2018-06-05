/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsSysAreaRegion;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

public interface DmsSysAreaRegionMapper extends IMybatisDao<DmsSysAreaRegion> {

    void deleteByAreaId(Long areaId);
}