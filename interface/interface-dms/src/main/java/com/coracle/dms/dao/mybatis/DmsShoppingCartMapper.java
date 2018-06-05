/**
 * create by hcs
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsShoppingCart;
import com.coracle.dms.vo.DmsShoppingCartVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;
import java.util.Map;

public interface DmsShoppingCartMapper extends IMybatisDao<DmsShoppingCart> {

    /**
     * 根据用户(账号)id获取购物车商品列表
     * @param paramMap
     * @return
     */
    List<DmsShoppingCartVo> selectVoByUserId(Map<String, Object> paramMap);

    /**
     * 根据id列表批量逻辑删除
     * @param ids
     */
    void batchRemove(List<Long> ids);

    /**
     * 根据用户(账号)id获取购物车内商品数量
     * @param userId
     * @return
     */
    Integer selectCountByUserId(Long userId);

    /**
     * 常购商品列表
     * @param param
     * @return
     */
    List<DmsShoppingCartVo> listOftenBuyProductByCondition(DmsShoppingCartVo param);

    /**
     * 删除常购商品
     * @param dmsShoppingCartVo
     */
    Integer deleteOftenBuyProduct(DmsShoppingCartVo dmsShoppingCartVo);

    /**
     *根据产品id和userid 软删除购物车
     */

    void removeShopCart(DmsShoppingCart dmsShoppingCart);


    /**
     *根据产品id和userid 软删除购物车
     */

    void deleteShopCart(DmsShoppingCart dmsShoppingCart);



    void historyOfftenBuy (DmsShoppingCart dmsShoppingCart);
}