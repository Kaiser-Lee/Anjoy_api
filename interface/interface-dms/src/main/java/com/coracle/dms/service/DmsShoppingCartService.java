package com.coracle.dms.service;

import com.coracle.dms.po.DmsShoppingCart;
import com.coracle.dms.vo.DmsShoppingCartInfoVo;
import com.coracle.dms.vo.DmsShoppingCartVo;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.Date;
import java.util.List;

public interface DmsShoppingCartService extends IBaseService<DmsShoppingCart> {

    void insert(Long userId, Long productId, Long productSpecId, String specUnionKey, Long count,
                Integer type, Date balanceDate);

    JsonResult add(DmsShoppingCartVo shoppingCart);

    /**
     * 根据用户(账号)id获取购物车列表(分页)
     * @param userSession
     * @return
     */
    DmsShoppingCartInfoVo detail(UserSession userSession);

    DmsShoppingCartInfoVo listOftenBuyProduct(DmsShoppingCartVo param);

    void addOftenBuyProduct(List<DmsShoppingCartVo> param, UserSession session);

    /**
     * 根据id列表批量逻辑删除
     * @param ids
     */
    void batchRemove(List<Long> ids);

    Integer count(Long userId);

    void updateCount(DmsShoppingCart shoppingCart);

    /**
     * 删除常购商品
     * @param dmsShoppingCartVo
     */
    Integer deleteOftenBuyProduct(DmsShoppingCartVo dmsShoppingCartVo);


    /**
     *根据产品id和userid删除购物车
     */

    void removeShopCart(DmsShoppingCart dmsShoppingCart,UserSession userSession);

    void historyOfftenBuy (DmsShoppingCartVo dmsShoppingCart);
}