package com.coracle.dms.service;

import com.coracle.dms.po.KnResource;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface KnResourceService extends IBaseService<KnResource> {

    /**
     * 根据用户ID查询用户菜单
     * @param id
     * @return
     */
    List<KnResource> selectResourcesByUserId(Long id);

    List<KnResource> selectResourcesByIds(List<Long> ids);

    List<String> selectButtonsByMxmIdAndResId(Long userId,Long resId);
}