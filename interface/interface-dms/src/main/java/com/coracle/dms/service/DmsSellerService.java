package com.coracle.dms.service;

import com.coracle.dms.po.DmsSeller;
import com.coracle.dms.vo.DmsSellerVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface DmsSellerService extends IBaseService<DmsSeller> {
    /***
     * 新增或者修改门店店员
     * @param dmsSellerVo
     */
    void saveOrUpdate(DmsSellerVo dmsSellerVo, UserSession session);

    List<DmsSellerVo> selectVoByCondition(DmsSellerVo dmsSellerVo, UserSession userSession);

    /**
     * 获取门店店员详情全部数据
     * @param id
     * @return
     */
    DmsSellerVo selectVoByPrimaryKey(Long id);

    /**
     * 获取店员详情
     * @param id
     * @param userSession
     * @return
     */
    Map<String,Object> getSellerDetail(Long id,UserSession userSession);

    /**
     * 开通账号
     */
    String openAccount(Long sellerId, UserSession session);

    /**
     * 根据门店id列表获取用户ID列表
     * @param ids
     * @return
     */
    List<Long> getUserIdsByStoreIds(List<Long> ids);

    PageHelper.Page<DmsSellerVo> pageList(DmsSellerVo dmsSellerVo);

    Long getStoreIdByUserId(Long id);
}