package com.coracle.dms.service;

import com.coracle.dms.po.DmsStore;
import com.coracle.dms.vo.DmsStoreVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;
import java.util.Map;

public interface DmsStoreService extends IBaseService<DmsStore> {

    /***
     * 新增门店
     * @param dmsStoreVo
     * @param userSession
     * @param type 0:PC新增门店  1：APP订货端新增门店
     */
    void addStore(DmsStoreVo dmsStoreVo,UserSession userSession,int type);

    /***
     * 修改门店
     * @param dmsStoreVo
     * @param userId
     * @param type 0:PC修改门店  1：APP订货端修改门店
     */
    void modifyStore(DmsStoreVo dmsStoreVo,long userId,int type);

    /**
     * 门店分页查询
     * @param dmsStoreVo
     * @return
     */
    PageHelper.Page<DmsStoreVo> selectForPageList(DmsStoreVo dmsStoreVo);

    /**
     * APP端门店分页查询
     * @param dmsStoreVo
     * @return
     */
    PageHelper.Page<DmsStoreVo> selectForPageListByApp(DmsStoreVo dmsStoreVo);
    /**
     * 通过用户ID获取所拥有的门店分页查询
     * @param dmsStoreVo
     * @return
     */
    PageHelper.Page<DmsStore> selectForPageListByUser(DmsStoreVo dmsStoreVo);
    /**
     * 获取门店详情
     * @param id
     * @return
     */
    Map<String,Object> getStoreDetail(Long id,UserSession userSession);

    /**
     * 通过用户ID获取门店详情
     * @param id
     * @return
     */
    DmsStore getStoreInfoByUserId(Long id);

    String getStoreIdsByChannelId(Long id);

    List<Long> selectStoreIdListByChannelId(Long channelId);
}