/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsSysArea;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsSysAreaMapper extends IMybatisDao<DmsSysArea> {

    List<DmsSysArea> findAreaPageList(DmsSysArea dmsSysArea);

    List<TreeNode> selectByParentId(Long id);

    List<DmsSysArea> findAreaByParentId(Long parentId);

    DmsSysArea selectByName(String name);

    DmsSysArea selectById(Long id);

    Integer findChildById(Long id);

    DmsSysArea selectByAreaCode(String areaCode);

    int findCount();
}