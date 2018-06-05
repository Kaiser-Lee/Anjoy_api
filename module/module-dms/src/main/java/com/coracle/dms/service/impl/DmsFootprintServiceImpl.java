package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsFootprintMapper;
import com.coracle.dms.po.DmsFootprint;
import com.coracle.dms.service.DmsFootprintService;
import com.coracle.dms.service.DmsProductService;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DmsFootprintServiceImpl extends BaseServiceImpl<DmsFootprint> implements DmsFootprintService {
    private static final Logger logger = Logger.getLogger(DmsFootprintServiceImpl.class);

    @Autowired
    private DmsFootprintMapper dmsFootprintMapper;

    @Autowired
    private DmsProductService dmsProductService;

    @Override
    public IMybatisDao<DmsFootprint> getBaseDao() {
        return dmsFootprintMapper;
    }

    /**
     * 新增用户产品浏览历史记录
     *
     * @param userId
     * @param productId
     */
    @Override
    public void insert(Long userId, Long productId) {
        DmsFootprint footprint = new DmsFootprint();
        footprint.setUserId(userId);
        footprint.setProductId(productId);
        footprint.setCreatedBy(userId);
        footprint.setCreatedDate(new Date());
        footprint.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());

        dmsFootprintMapper.insert(footprint);
    }

    /**
     * 我的足迹
     *
     *
     * @param param
     * @param session
     * @return
     */
    @Override
    public PageHelper.Page<DmsProductVo> list(DmsFootprint param, UserSession session) {
        DmsProductVo condition = new DmsProductVo();
        condition.setP(param.getP());
        condition.setS(param.getS());
        condition.setFootprint(true);
        condition.setUserId(session.getId());
        //设置参数：用户最近浏览的十条产品
        condition.setProductIdList(this.listRecentTenProductIdByUserId(session.getId()));

        return dmsProductService.findProductPageList(condition, session);
    }

    /**
     * 根据用户id获取该用户最近浏览的十条商品id
     *
     * @param userId
     * @return
     */
    public List<Long> listRecentTenProductIdByUserId(Long userId) {
        return dmsFootprintMapper.listRecentTenProductIdByUserId(userId);
    }
}