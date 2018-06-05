/**
 * create by xiaobu2012
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsSysRegion;
import com.coracle.yk.base.vo.TreeNode;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsSysRegionMapper extends IMybatisDao<DmsSysRegion> {

    List<DmsSysRegion> findRegionPageList(DmsSysRegion dmsSysRegion);

    List<DmsSysRegion> findRegionByParentId(Long parentId);

    DmsSysRegion selectByName(String name);

    List<TreeNode> selectByParentId(Long id);

    /**
     * 根据类型获取地区列表
     *
     * @param type
     * @return
     */
    List<DmsSysRegion> listByType(String type);
}