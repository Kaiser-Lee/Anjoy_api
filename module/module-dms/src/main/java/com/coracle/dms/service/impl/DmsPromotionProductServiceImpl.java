package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsPromotionProductMapper;
import com.coracle.dms.po.DmsProduct;
import com.coracle.dms.po.DmsPromotionProduct;
import com.coracle.dms.service.DmsProductService;
import com.coracle.dms.service.DmsPromotionProductService;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.dms.vo.DmsPromotionProductVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DmsPromotionProductServiceImpl extends BaseServiceImpl<DmsPromotionProduct> implements DmsPromotionProductService {
    private static final Logger logger = Logger.getLogger(DmsPromotionProductServiceImpl.class);

    @Autowired
    private DmsPromotionProductMapper dmsPromotionProductMapper;

    @Autowired
    private DmsProductService dmsProductService;

    @Override
    public IMybatisDao<DmsPromotionProduct> getBaseDao() {
        return dmsPromotionProductMapper;
    }

    /**
     * 新增/修改促销适用产品信息
     *
     * @param promotionId
     * @param promotionProductList
     * @param session
     */
    @Override
    public void insertOrUpdate(Long promotionId, List<DmsPromotionProductVo> promotionProductList, UserSession session) {

        Long userId = session.getId();
        Date now = new Date();

        //数据库中的促销适用产品信息id列表
        List<Long> existIdList = dmsPromotionProductMapper.selectIdListByPromotionId(promotionId);

        //要删除的数据的id列表，初始化为数据库中存在的id
        //id：数据库中存在，用户提交的参数中不存在的，则为要删除的数据
        List<Long> removeIdList = Lists.newArrayList(existIdList);

        for (DmsPromotionProduct ps : promotionProductList) {
            //如果产品id不为空，则从数据库中获取该产品的分类id
            Long productId = ps.getProductId();
            if (productId != null) {
                DmsProduct product = dmsProductService.selectByPrimaryKey(productId);
                ps.setProductCategoryId(product.getCategoryId());
            }

            Long id = ps.getId();
            if (id == null) {
                ps.setPromotionId(promotionId);
                ps.setSoldQuantity(0);
                ps.setCreatedBy(userId);
                ps.setCreatedDate(now);
                ps.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
                dmsPromotionProductMapper.insert(ps);
            } else {
                if (existIdList.contains(ps.getId())) {
                    ps.setLastUpdatedBy(userId);
                    ps.setLastUpdatedDate(now);
                    dmsPromotionProductMapper.updateByPrimaryKeySelective(ps);
                    removeIdList.remove(id);
                } else {
                    throw new ServiceException("促销适用产品信息异常，数据库中不存在id为: " + id + " 的促销适用产品信息");
                }
            }
        }

        if (!removeIdList.isEmpty()) {
            dmsPromotionProductMapper.batchRemove(removeIdList);
        }
    }

    /**
     * 根据条件获取折扣最高的一条促销信息
     * 用于订单下单时选择最佳的一条促销
     *
     * @param product
     * @return
     */
    @Override
    public DmsPromotionProductVo bestChoice(DmsProductVo product) {
        return dmsPromotionProductMapper.selectBestByProduct(product);
    }

    /**
     * 根据条件获取可用的产品促销数量
     *
     * @param promotionProduct
     * @return
     */
    @Override
    public Integer selectAvailableCountByCondition(DmsPromotionProductVo promotionProduct) {
        return dmsPromotionProductMapper.selectAvailableCountByCondition(promotionProduct);
    }

}