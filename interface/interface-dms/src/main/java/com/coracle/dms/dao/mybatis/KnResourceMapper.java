/**
 * create by huangbaidong
 * @date 2017-03
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.KnResource;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface KnResourceMapper extends IMybatisDao<KnResource> {


    List<KnResource> selectResourcesByUserId(Long id);


    List<String> selectButtonsByResId(KnResource resource);


    List<KnResource> selectResourcesByIds(List<Long> ids);

}