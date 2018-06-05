package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsChannelMapper;
import com.coracle.dms.dao.mybatis.DmsShoppingCartMapper;
import com.coracle.dms.po.DmsChannel;
import com.coracle.dms.po.DmsChannelContacts;
import com.coracle.dms.po.DmsShoppingCart;
import com.coracle.dms.service.*;
import com.coracle.dms.vo.DmsChannelContactsVo;
import com.coracle.dms.vo.DmsChannelVo;
import com.coracle.dms.vo.DmsShoppingCartInfoVo;
import com.coracle.dms.vo.DmsShoppingCartVo;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Maps;
import com.xiruo.medbid.components.DateUtils;
import com.xiruo.medbid.util.DecimalUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DmsShoppingCartServiceImpl extends BaseServiceImpl<DmsShoppingCart> implements DmsShoppingCartService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DmsShoppingCartServiceImpl.class);

    @Autowired
    private DmsShoppingCartMapper dmsShoppingCartMapper;
    @Autowired
    private DmsChannelMapper dmsChannelMapper;
    @Autowired
    private DmsOrderService dmsOrderService;
    @Autowired
    private DmsStorageInventoryService dmsStorageInventoryService;
    @Autowired
    private DmsProductService dmsProductService;
    @Autowired
    private DmsChannelService dmsChannelService;
    @Autowired
    private DmsChannelContactsService dmsChannelContactsService;
    @Autowired
    private DmsPromotionProductService dmsPromotionProductService;
    @Override
    public IMybatisDao<DmsShoppingCart> getBaseDao() {
        return dmsShoppingCartMapper;
    }

    /**
     * 新增购物车数据
     *
     * @param userId
     * @param productId
     * @param productSpecId
     * @param specUnionKey
     * @param count
     * @param type
     * @param balanceDate
     */
    @Override
    public void insert(Long userId, Long productId, Long productSpecId, String specUnionKey, Long count,
                       Integer type, Date balanceDate) {
        DmsShoppingCart shoppingCart = new DmsShoppingCart();
        shoppingCart.setUserId(userId);
        shoppingCart.setProductId(productId);
        shoppingCart.setProductSpecId(productSpecId);
        shoppingCart.setSpecUnionKey(specUnionKey);
        shoppingCart.setCount(count);
        shoppingCart.setCreatedDate(new Date());
        shoppingCart.setCreatedBy(userId);
        shoppingCart.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        shoppingCart.setType(type);
        shoppingCart.setBalanceDate(balanceDate);
        this.insert(shoppingCart);
    }

    /**
     * 加入购物车
     *
     * @param shoppingCart
     */
    @Override
    public JsonResult add(DmsShoppingCartVo shoppingCart) {


        //设置购物车数据类型为：购物车
        shoppingCart.setType(DmsModuleEnums.SHOPPINT_CART_TYPE.SHOPPING_CART.getType());

        //获取当前登录人所属的渠道
        Long channelId = null;
        DmsChannelContacts channelContacts = dmsChannelContactsService.queryContactByUserId(shoppingCart.getUserId());
        if (channelContacts != null) {
            channelId = channelContacts.getChannelId();
        }

        JsonResult result = new JsonResult();
        result.setStatus(true);
        result.setCode(200);

//        Long productId = shoppingCart.getProductId();  //产品id
//        Long count = shoppingCart.getCount();  //订购数量

        //阳光：订单数量大于促销剩余数量，弹框显示"超出可购数量"
//        DmsProductVo p = new DmsProductVo();
//        p.setId(productId);
//        p.setSpecId(productSpecId);
//        p.setChannelId(channelId);
//        DmsPromotionProductVo promotionProduct = dmsPromotionProductService.bestChoice(p);
//        if (promotionProduct != null) {
//            Long promotionProductId = promotionProduct.getId();
//            DmsPromotionProductVo pp = new DmsPromotionProductVo();
//            pp.setId(promotionProductId);
//            pp.setChannelId(channelId);
//            Integer availablePromotionCount = dmsPromotionProductService.selectAvailableCountByCondition(pp);
//            if (availablePromotionCount < count) {
//                result.setCode(601);
//                result.setMessage("超出可购数量");
//            }
//        }
        //加入购物车时先检验产品的库存量（安井不需要）
        //订单数量大于可用库存数量，弹框显示库存不足
//        DmsStorageInventory si = new DmsStorageInventory();
//        si.setProductId(shoppingCart.getProductId());
//        si.setProductSpecId(shoppingCart.getProductSpecId());
//        DmsStorageInventoryVo storageInventoryVo = dmsStorageInventoryService.selectVoCondition(si);
//        Long inventory = storageInventoryVo.getUseNumTotal();  //todo 获取库存量可能需要修改
//        if (inventory < count) {
//            result.setCode(600);
//            result.setMessage("库存不足");
//        }
//        if (shoppingCart.getCount() > storageInventoryVo.getUseNumTotal()) {
//            throw new ServiceException("库存量不足!产品: " + storageInventoryVo.getProductName() + " | 规格: [" + storageInventoryVo.getSpecName() + "] 的库存量为" + storageInventoryVo.getUseNumTotal());
//        }

        //首先判断购物车商品的数量是否是最小包装量的倍数，不是的话不允许提交
//        DmsProduct product = dmsProductService.selectByPrimaryKey(productId);
//        Long minPackageQuantity = product.getMinPackageQuantity();
//        if (minPackageQuantity != null && count % minPackageQuantity != 0) {
//            throw new ServiceException("订购数量必须是最小包装量的倍数");
//        }

        //根据用户id、产品id和产品规格获取数据库中的购物车记录
        //若数据库中存在记录，则只将数量进行累加，否则新增一条记录
        DmsShoppingCart param = new DmsShoppingCart();
        param.setType(DmsModuleEnums.SHOPPINT_CART_TYPE.SHOPPING_CART.getType());
        param.setUserId(shoppingCart.getUserId());
        param.setProductId(shoppingCart.getProductId());
        param.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

        List<DmsShoppingCart> shoppingCartList = dmsShoppingCartMapper.selectByCondition(param);
        //同一用户同一产品出现两次删除一个
        if(shoppingCartList.size()>1){
            dmsShoppingCartMapper.deleteByPrimaryKey(shoppingCartList.get(1).getId());
        }
        if (shoppingCartList != null && !shoppingCartList.isEmpty()) {

            param.setId(shoppingCartList.get(0).getId());
            param.setCount(shoppingCart.getCount());
            //param.setCount(shoppingCart.getCount() + shoppingCartList.get(0).getCount());
            param.setLastUpdatedBy(shoppingCart.getCreatedBy());
            param.setLastUpdatedDate(new Date());

            dmsShoppingCartMapper.updateByPrimaryKeySelective(param);
        } else {
            dmsShoppingCartMapper.insert(shoppingCart);
        }
        return result;
    }

    /**
     * 根据用户(账号)id获取购物车详情
     *
     * @param userSession
     * @return
     */
    @Override
    public DmsShoppingCartInfoVo detail(UserSession userSession) {

        Long userId = userSession.getId();//用户ID
        Long channelId = userSession.getOrgId();//渠道ID

        DmsChannelVo dmsChannelVo = dmsChannelMapper.selectByUserId(userSession.getId());

        String channelCode = dmsChannelVo.getEasNum();
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("userId", userId);
        paramMap.put("channelId", channelId);
        paramMap.put("channelCode", channelCode);


        DmsShoppingCartInfoVo detail = new DmsShoppingCartInfoVo();
        //购物车列表的产品信息
        List<DmsShoppingCartVo> productList = dmsShoppingCartMapper.selectVoByUserId(paramMap);


        //获取渠道信息
        DmsChannel channel = dmsChannelService.selectByPrimaryKey(channelId);

        BigDecimal sum = new BigDecimal(0);
        BigDecimal total = new BigDecimal(0);
       // BigDecimal discount = new BigDecimal(0);

        for (DmsShoppingCartVo sc : productList) {
            BigDecimal count = sc.getCount() != null ? new BigDecimal(sc.getCount()) : new BigDecimal("0");
            BigDecimal price = sc.getPrice() != null ? sc.getPrice() : new BigDecimal("0");
//            BigDecimal promotionPrice = sc.getPromotionPrice() != null ? sc.getPromotionPrice() : new BigDecimal("0");
//            Integer promotionCount = sc.getPromotionCount() != null ? sc.getPromotionCount() : 0;

//            总额 += 原价 * 数量
//            如果产品有一般定价类型的促销活动，则将折扣价直接计算入总额，
//             安井暂无促销不考虑促销
//            if (promotionCount == 0 && promotionPrice.compareTo(price) < 0) {
//                total = total.add(promotionPrice.multiply(count));
//            } else {
//                total = total.add(price.multiply(count));
//            }
//          //合计 += 折扣价 * 数量
//            sum = sum.add(promotionPrice.multiply(count));
            //如果当前购物车产品有促销活动，则将其总额与合计的差额累加到立减中
            //排除掉一般定价类型的促销
//            if (promotionCount > 0) {
//                discount = discount.add(total.subtract(sum));
//            }

            total = total.add(price.multiply(count));

        }

        detail.setSum(DecimalUtil.round(sum));
        detail.setTotal(DecimalUtil.round(total));
        //detail.setDiscount(DecimalUtil.round(discount));
        if (channel != null) {
            Long minOrderQuantity = channel.getMinOrderQuantity() != null ? channel.getMinOrderQuantity() : 0L;
            detail.setMinOrderQuantity(minOrderQuantity);
        }

        //todo 可使用额度：从安井同步过来的，先写死
        Long time1 = System.currentTimeMillis();
        detail.setAvailableLimit(dmsOrderService.getAvailableLimit(userId));
        Long time2 = System.currentTimeMillis();
        logger.info("********可用额度查询耗时:{}********",(time2-time1)/1000);

        if (detail != null) {
            if (!productList.isEmpty()) {
                detail.setProductList(productList);
            } else {
                //购物车列表为空时，返回空数组而不是null
                detail.setProductList(new ArrayList<DmsShoppingCartVo>());
            }
        }

        return detail;

    }

    /**
     * 常购商品列表
     *
     * @return
     */
    @Override
    public DmsShoppingCartInfoVo listOftenBuyProduct(DmsShoppingCartVo param) {
        DmsShoppingCartInfoVo result = new DmsShoppingCartInfoVo();

        //设置去年同期销售量、本月已下单数量的查询参数
        param.setStartTimeLastYear(DateUtils.getStartTimeThisMonthLastYearText());  //去年今月的开始日期
        param.setEndTimeLastYear(DateUtils.getEndTimeThisMonthLastYearText());  //去年今月的结束日期
        param.setStartTimeThisMonth(DateUtils.getStartTimeThisMonthText());  //本月开始日期
        param.setStartTimeFirstDateThisYear(DateUtils.getStartTimeThisYearText());
        param.setStartTimeFirstDateLastYear(DateUtils.getStartTimeLastYearText());
        param.setNow(DateUtils.parseToTimeText(new Date()));  //本月昨日

        //获取当前登录人的渠道信息
        DmsChannelContactsVo channelContacts = dmsChannelContactsService.queryContactByUserId(param.getUserId());
        DmsChannelVo dmsChannelVo = dmsChannelMapper.selectByUserId(param.getUserId());
        String channelCode = dmsChannelVo.getEasNum();
        Long channelId = channelContacts.getChannelId();
        param.setChannelId(channelId);
        param.setChannelCode(channelCode);

        //常购商品里面的产品列表
        List<DmsShoppingCartVo> oftenBuyProductList = dmsShoppingCartMapper.listOftenBuyProductByCondition(param);
        if(oftenBuyProductList != null && oftenBuyProductList.size() > 0){
            for(DmsShoppingCartVo entity : oftenBuyProductList){
                entity.setCount(0L);
            }
        }
        result.setProductList(oftenBuyProductList);
        //todo 设置可使用额度
        result.setAvailableLimit(dmsOrderService.getAvailableLimit(param.getUserId()));

        return result;
    }

    /**
     * 添加常购商品
     *
     * @param param
     * @param session
     */
    @Override
    public void addOftenBuyProduct(List<DmsShoppingCartVo> param, UserSession session) {
        for (DmsShoppingCartVo sc : param) {
            sc.setCount(0L);  //常购商品数量默认为0
            sc.setUserId(session.getId());
            sc.setType(DmsModuleEnums.SHOPPINT_CART_TYPE.MANUAL.getType());  //手动添加的常购商品
            sc.setCreatedBy(session.getId());
            sc.setCreatedDate(new Date());
            sc.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
            dmsShoppingCartMapper.insert(sc);
        }
    }

    /**
     * 根据id列表批量逻辑删除
     *
     * @param ids
     */
    @Override
    public void batchRemove(List<Long> ids) {
        dmsShoppingCartMapper.batchRemove(ids);
    }

    /**
     * 根据用户(账号)id获取购物车内商品数量
     *
     * @param userId
     * @return
     */
    @Override
    public Integer count(Long userId) {
        return dmsShoppingCartMapper.selectCountByUserId(userId);
    }

    /**
     * 修改购物车内商品的数量
     *
     * @param shoppingCart
     */
    @Override
    public void updateCount(DmsShoppingCart shoppingCart) {
        Long id = shoppingCart.getId();
        if (id == null) {
            throw new ServiceException("参数异常，id为空");
        }
        DmsShoppingCart entity = dmsShoppingCartMapper.selectByPrimaryKey(id);
        if (entity == null) {
            throw new ServiceException("数据库中不存在id为: " + id + "的购物车数据");
        }

        //todo 阳光照明：首先判断购物车商品的数量是否是最小包装量的倍数，不是的话不允许提交

        shoppingCart.setLastUpdatedDate(new Date());
        shoppingCart.setLastUpdatedBy(shoppingCart.getLastUpdatedBy());
        dmsShoppingCartMapper.updateByPrimaryKeySelective(shoppingCart);
    }

    /**
     * 删除常购商品
     *
     * @param dmsShoppingCartVo
     */
    @Override
    public Integer deleteOftenBuyProduct(DmsShoppingCartVo dmsShoppingCartVo) {
        return dmsShoppingCartMapper.deleteOftenBuyProduct(dmsShoppingCartVo);
    }

    @Override
    public void removeShopCart(DmsShoppingCart dmsShoppingCart,UserSession userSession) {
         Long userId = userSession.getId();
        dmsShoppingCart.setUserId(userId);

        dmsShoppingCartMapper.removeShopCart(dmsShoppingCart);
    }

    /**
     *导入历史下单数据作为常购商品
     */


    @Override
    public void historyOfftenBuy(DmsShoppingCartVo dmsShoppingCart) {

        dmsShoppingCartMapper.historyOfftenBuy(dmsShoppingCart);

    }

}