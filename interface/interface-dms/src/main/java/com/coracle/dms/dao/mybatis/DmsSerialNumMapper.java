/**
 * create by Administrator
 * @date 2017-09
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsSerialNum;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import org.apache.ibatis.annotations.Param;

public interface DmsSerialNumMapper extends IMybatisDao<DmsSerialNum> {

    DmsSerialNum getInfoByPrimaryKey(@Param("serialKey") String serialKey);

    int insertHavePrimaryKey(DmsSerialNum dmsSerialNum);
}