package com.coracle.dms.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsOrderReturnCartMapper;
import com.coracle.dms.po.DmsOrderReturnCart;
import com.coracle.dms.service.DmsOrderReturnCartService;
import com.coracle.dms.vo.DmsOrderReturnCartVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;

/**
 * 退换货清单管理
 * @author tanyb
 */
@Service
public class DmsOrderReturnCartServiceImpl extends BaseServiceImpl<DmsOrderReturnCart> implements DmsOrderReturnCartService {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DmsOrderReturnCartMapper dmsOrderReturnCartMapper;

    @Override
    public IMybatisDao<DmsOrderReturnCart> getBaseDao() {
        return dmsOrderReturnCartMapper;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public PageHelper.Page<DmsOrderReturnCartVo> selectForListPage(DmsOrderReturnCartVo search) {
        try {
            PageHelper.startPage(search.getP(), search.getS());
            dmsOrderReturnCartMapper.getPageList(search);
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            throw new ServiceException("退换货清单分页查询异常--->>>");
        }
    }
    
    @Override
	public void create(DmsOrderReturnCart paramVo) {
		this.checkParam(paramVo);
		DmsOrderReturnCartVo cartSearch = new DmsOrderReturnCartVo();
		cartSearch.setUserId(paramVo.getCreatedBy());
		cartSearch.setProductId(paramVo.getProductId());
		cartSearch.setProductSpecId(paramVo.getProductSpecId());
		cartSearch.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
		List<DmsOrderReturnCartVo> cartList = this.dmsOrderReturnCartMapper.getPageList(cartSearch);
		if(BlankUtil.isNotEmpty(cartList)){
			DmsOrderReturnCartVo cart = cartList.get(0);
			//获取购物车实际数量
			int num = paramVo.getReturnNum() + cart.getReturnNum();
			if (num > cart.getOldOrderNum()) {
				// throw new ServiceException("退换货数量不能大于订单产品数量");
			} else {
				cart.setReturnNum(num);
				cart.setLastUpdatedBy(paramVo.getCreatedBy());
				cart.setLastUpdatedDate(new Date());
				this.dmsOrderReturnCartMapper.updateByPrimaryKeySelective(cart);
			}
		}else{
			this.dmsOrderReturnCartMapper.insert(paramVo);
		}
		
	}
    @Override
    public void batchUpdate(List<DmsOrderReturnCart> cartList,UserSession session){
		if (BlankUtil.isEmpty(cartList)) {
			throw new ServiceException("参数异常");
		}
		for (DmsOrderReturnCart cart : cartList) {
			if (cart == null || cart.getId() == null || cart.getId() == 0 || cart.getReturnNum() == null) {
				continue;
			}
			DmsOrderReturnCart entity = this.dmsOrderReturnCartMapper.selectByPrimaryKey(cart.getId());
			if(entity == null){
				throw new ServiceException("获取数据为空");
			}
			cart.setLastUpdatedBy(session.getId());
			cart.setLastUpdatedDate(new Date());
			this.dmsOrderReturnCartMapper.updateByPrimaryKeySelective(cart);
		}
    }
    @Override
	public void deleteByIdSoft(List<Long> ids) {
        this.dmsOrderReturnCartMapper.batchRemove(ids);;
	}
    @Override
    public Integer getOrderReturnCartCount(DmsOrderReturnCart paramVo) {
    	return this.dmsOrderReturnCartMapper.getOrderReturnCartCount(paramVo);
    }
    @Override
    public Integer selectReturnCount(DmsOrderReturnCart paramVo) {
    	return this.dmsOrderReturnCartMapper.selectReturnCount(paramVo);
    }
    private void checkParam(DmsOrderReturnCart paramVo) {
		if (paramVo == null) {
			throw new ServiceException("参数异常");
		}
		if(paramVo.getUserId() == null || paramVo.getUserId() == 0){
			throw new ServiceException("用户ID不能为空");
		}
		if (paramVo.getOldOrderId() == null || paramVo.getOldOrderId() == 0) {
			throw new ServiceException("旧订单ID不能为空");
		}
		if (paramVo.getProductId() == null || paramVo.getProductId() == 0) {
			throw new ServiceException("产品ID不能为空");
		}
//		if (paramVo.getProductSpecId() == null || paramVo.getProductSpecId() == 0) {
//			throw new ServiceException("产品规格ID不能为空");
//		}
//		if (BlankUtil.isEmpty(paramVo.getSpecUnionKey())) {
//			throw new ServiceException("产品规格名称不能为空");
//		}
		if(BlankUtil.isEmpty(paramVo.getUnitId())){
			throw new ServiceException("单位不能为空");
		}
		if(paramVo.getPrice() == null || paramVo.getPrice().equals(BigDecimal.ZERO)){
			throw new ServiceException("价格不能为空或者不能为零");
		}
		if(paramVo.getReturnNum() == null || paramVo.getReturnNum() == 0){
			throw new ServiceException("退换数量不能为空或者不能为零");
		}
	}

	
}