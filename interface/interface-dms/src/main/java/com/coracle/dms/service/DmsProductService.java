package com.coracle.dms.service;

import com.coracle.dms.po.DmsProduct;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;
import java.util.Map;

public interface DmsProductService extends IBaseService<DmsProduct> {

    /***
     * app-app-根据条件获取产品列表
     * @return
     */
    PageHelper.Page<DmsProductVo> findProductPageList(DmsProductVo dmsProduct, UserSession session);

    /***
     * app-根据条件获取产品列表(新增零售时产品列表)
     * @return
     */
    PageHelper.Page<DmsProductVo> findProductPageListForStore(DmsProductVo dmsProductVo, UserSession userSession);

    /***
     * PC产品列表查询
     * @return
     * @param product
     */
    PageHelper.Page<DmsProductVo> findProductPCPageList(DmsProductVo product);

    /***
     * 产品-规格列表
     * @return
     */
    PageHelper.Page<DmsProductVo> findProductPCSpecPageList(DmsProductVo dmsProduct);

    /**
     * 获取产品详情
     * @param id 产品id
     * @return
     */
    DmsProductVo getDetails(Long id,UserSession userSession);


    /**
     * App获取产品详情
     * @param id 产品id
     * @return
     */
    DmsProductVo getDetailsApp(Long id,UserSession userSession);

    /**
     * 通过产品编码查询产品信息
     * @param code
     * @return
     */
    DmsProduct getByCode(String code);

    /**
     * 获取产品详情页规格矩阵列表（规格矩阵列表，作用于前端判断是否可以删除该规格）
     * @param id 产品id
     * @return
     */
    List<DmsProductVo> getDetailSpecMatrix(Long id,UserSession userSession);


    /**
     * 新增产品
     * @param productVo 产品实体
     * @param userSession 登录信息
     */
    void create(DmsProductVo productVo, UserSession userSession);

    /**
     * 更新产品
     * @param productVo 产品实体
     * @param userSession 登录信息
     */
    void update(DmsProductVo productVo, UserSession userSession);

    /**
     * 添加产品浏览记录
     * @param productId 产品id
     * @param userId
     */
    void browseRecord(Long productId, Long userId);


    /**
     * 批量上架产品
     * @param ids
     */
    void up(List<Long> ids);


    /**
     * 批量下架产品
     * @param ids
     */
    void down(List<Long> ids);

    /**
     *
     * 通过id 软删除
     */
    void updateById(Long id);


    /**
     *
     * 根据产品id和规格id查询产品名称，规格名称
     */
    Map<String, Object> findProductNameSpecName(Map<String, Object> map);


    void addSalesVolume(Long productId, Integer count);

    List<DmsProductVo> listBySpecifications(DmsProductVo param, UserSession session);

    void anjoySyn();

    void anjoySpecificationSyn();

}