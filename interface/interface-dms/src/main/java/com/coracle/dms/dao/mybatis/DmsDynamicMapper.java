/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsDynamic;
import com.coracle.dms.vo.DmsDynamicVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsDynamicMapper extends IMybatisDao<DmsDynamic> {

    /**
     * 分页获取客户购买列表
     * @param dmsDynamicVo
     * @return
     */
    List<DmsDynamicVo> getPageList(DmsDynamicVo dmsDynamicVo);
}