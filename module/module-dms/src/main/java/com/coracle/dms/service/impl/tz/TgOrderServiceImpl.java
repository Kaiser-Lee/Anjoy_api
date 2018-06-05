package com.coracle.dms.service.impl.tz;

import com.coracle.dms.dao.mybatis.tz.TgOrderMapper;
import com.coracle.dms.dao.mybatis.tz.TgOrderProductMapper;
import com.coracle.dms.po.tz.TgOrder;
import com.coracle.dms.po.tz.TgOrderProduct;
import com.coracle.dms.po.tz.TgOrderProductPart;
import com.coracle.dms.service.DmsSerialNumService;
import com.coracle.dms.service.tz.TgOrderProductPartService;
import com.coracle.dms.service.tz.TgOrderProductService;
import com.coracle.dms.service.tz.TgOrderService;
import com.coracle.dms.vo.tz.TgOrderVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.xiruo.medbid.util.BeanConvertHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeSet;

@Service
public class TgOrderServiceImpl extends BaseServiceImpl<TgOrder> implements TgOrderService {
    private static final Logger logger = Logger.getLogger(TgOrderServiceImpl.class);

    @Autowired
    private TgOrderMapper tgOrderMapper;

    @Autowired
    private TgOrderProductService tgOrderProductService;
    @Autowired
    private TgOrderProductPartService tgOrderProductPartService;
    @Autowired
    private TgOrderProductMapper tgOrderProductMapper;
    @Autowired
    private DmsSerialNumService dmsSerialNumService;

    @Override
    public IMybatisDao<TgOrder> getBaseDao() {
        return tgOrderMapper;
    }

    /**
     * 天正订单列表
     * @param order
     * @param userSession
     * @return
     */
    @Override
    public PageHelper.Page<TgOrder> pageList(TgOrder order, UserSession userSession) {
        String roleCode = userSession.getRoleCode();
       /* if (roleCode.equals(DmsRoleCodeConstants.CO)) {  //订货端用户只能查看自己的订单
            order.setUserId(userSession.getId());
        }*/
        try {
            PageHelper.startPage(order.getP(), order.getS());
            tgOrderMapper.tgOrderList(order);
        } catch (Exception e) {
            logger.error("订单分页查询异常!", e);
            throw new ServiceException("订单分页查询异常!");
        } finally {
            return PageHelper.endPage();
        }

    }

    /**
     * 天正添加订单产品分批发运信息
     * @param po
     * @return
     */
    @Override
    public int insterOrderProPart(TgOrderProductPart po) {
        int count = tgOrderProductPartService.insert(po);
        if (count <= 0) {
            throw new ServiceException("新增订单产品分批发运信息异常");
        }
        return count;
    }

    /**
     * 天正订单,删除订单产品
     * @param map
     * @return
     */
    @Override
    public void deleteOrderPro(Map<String, Object> map) {
        tgOrderMapper.deleteOrderPro(map);
    }

    /**
     * 天正订单,删除订单产品分批发运信息
     * @param map
     * @return
     */
    @Override
    public void deleteOrderProPart(Map<String, Object> map) {
        tgOrderMapper.deleteOrderProPart(map);
    }

    /**
     * 批量设置提货地点
     * @param ids
     * @param pickAddress
     */
    @Override
    public void updatePickAddress(String ids, String pickAddress) {
        String[] arr = ids.split(",");
        for (String id:arr) {
            TgOrderProduct tgOrderProduct = tgOrderProductMapper.selectByPrimaryKey(Long.valueOf(id.toString()));
            tgOrderProduct.setPickProAddress(pickAddress);
            tgOrderProductMapper.updateByPrimaryKey(tgOrderProduct);
        }
    }

    /**
     * 天正订单:新增订单
     * @param orderVo
     * @param userSession
     * @return
     */
    @Override
    public TgOrder create(TgOrderVo orderVo, UserSession userSession) {
        /* 新增订单信息 */

        TreeSet<String> lineOraddress = new TreeSet<>();
        /* 判断同一产品线是否有多个提货地点 */
        for (TgOrderProduct po:orderVo.getProductList()) {
            StringBuilder sb = new StringBuilder();
            sb.append(po.getProLine());
            sb.append(",");
            sb.append(po.getPickProAddress());
            lineOraddress.add(sb.toString());
        }
        //要货单号
        String code = dmsSerialNumService.createSerialNumStr("order_serial_key");
        /* 根据产品线和提货地点,生成订单,如果有多个产品线和提货地点,则拆分订单 */
        if(lineOraddress.size()>1){
            Integer i = 1;
            for (String s:lineOraddress) {
                orderVo.setGetProCode(code+"-"+(i++).toString());
                tgOrderMapper.insert(orderVo);
                Long orderId = orderVo.getId();
                String[] arr = s.split(",");
                //将订单ID赋值到订单产品中
                for (TgOrderProduct po:orderVo.getProductList()) {
                    if (po.getProLine().equals(arr[0]) && po.getPickProAddress().equals(arr[1])){
                        po.setOrderId(orderId);
                        tgOrderProductMapper.insert(po);
                    }
                }
            }
        }else {
            orderVo.setGetProCode(code);
            tgOrderMapper.insert(orderVo);
            Long orderId = orderVo.getId();
            for (TgOrderProduct po:orderVo.getProductList()) {
                po.setOrderId(orderId);
                tgOrderProductMapper.insert(po);
            }
        }

         /* 返回新增的订单信息 */
        TgOrder result = tgOrderMapper.selectByPrimaryKey(orderVo.getId());
        return result;
    }

    /**
     * 获取订单详情
     * @param param
     * @param session
     * @return
     */
    @Override
    public TgOrder detail(TgOrderVo param, UserSession session) {
        TgOrderVo result = new TgOrderVo();

        Long userId = param.getLastUpdatedBy();

        /* 如果id为空或0，则是进入商城的确认订单页面操作 */
        /* 如果id为正数，则是查询订单详情操作 */
        if (param.getId() == 0) {  //返回下订单时需要的一些参数

        }else {  //查询订单详情
//            param.setLoginId(session.getId());
            TgOrder order = tgOrderMapper.selectVoByOrder(param);
            if (order == null) {
                throw new ServiceException("数据库中查询不到id为: " + param.getId() + "的订单");
            }
            BeanConvertHelper.copyProperties(order, result);
        }
        return null;
    }

}