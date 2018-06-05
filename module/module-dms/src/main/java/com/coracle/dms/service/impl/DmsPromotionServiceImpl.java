package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsPromotionMapper;
import com.coracle.dms.po.DmsPromotion;
import com.coracle.dms.service.DmsProductService;
import com.coracle.dms.service.DmsPromotionProductService;
import com.coracle.dms.service.DmsPromotionScopeService;
import com.coracle.dms.service.DmsPromotionService;
import com.coracle.dms.support.SystemParamerConfiguration;
import com.coracle.dms.vo.DmsPromotionVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.xiruo.medbid.components.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DmsPromotionServiceImpl extends BaseServiceImpl<DmsPromotion> implements DmsPromotionService {
    private static final Logger logger = Logger.getLogger(DmsPromotionServiceImpl.class);

    @Autowired
    private DmsPromotionMapper dmsPromotionMapper;

    @Autowired
    private DmsPromotionProductService dmsPromotionProductService;

    @Autowired
    private DmsProductService dmsProductService;

    @Autowired
    private DmsPromotionScopeService dmsPromotionScopeService;

    @Override
    public IMybatisDao<DmsPromotion> getBaseDao() {
        return dmsPromotionMapper;
    }

    /**
     * 新增/修改促销活动
     *
     * @param promotionVo
     * @param session
     */
    @Override
    public void insertOrUpdate(DmsPromotionVo promotionVo, UserSession session) {
        paramCheck(promotionVo);

        boolean isInsert = promotionVo.getId() == null ? true : false;

        //新增/修改促销活动信息
        if (isInsert) {
            promotionVo.setCode(SystemParamerConfiguration.getInstance().createSerialNumStr(DmsModuleEnums.PROMOTION_SERIAL_KEY));
            promotionVo.setMeanwhile(1);
            insertCheck(promotionVo);
            dmsPromotionMapper.insert(promotionVo);
        } else {
            updateCheck(promotionVo);
            dmsPromotionMapper.updateByPrimaryKeySelective(promotionVo);
        }
        Long promotionId = promotionVo.getId();

        //新增/修改促销活动适用范围
        dmsPromotionScopeService.insertOrUpdate(promotionId, promotionVo.getScopeIdList(), session);

        //新增/修改促销适用产品信息
        dmsPromotionProductService.insertOrUpdate(promotionId, promotionVo.getProductList(), session);
    }

    /**
     * 促销活动列表（分页）
     *
     * @param promotionVo
     * @return
     */
    @Override
    public PageHelper.Page<DmsPromotionVo> pageList(DmsPromotionVo promotionVo) {
        try {
            PageHelper.startPage(promotionVo.getP(), promotionVo.getS());
            dmsPromotionMapper.selectVoByCondition(promotionVo);
        } catch (Exception e) {
            logger.error("促销活动分页查询异常!", e);
            throw new ServiceException("促销活动分页查询异常!");
        } finally {
            return PageHelper.endPage();
        }
    }

    /**
     * 根据主键id获取vo对象
     *
     * @param id
     * @return
     */
    @Override
    public DmsPromotionVo detail(Long id) {
        return dmsPromotionMapper.selectVoByPrimaryKey(id);
    }

    /**
     * 根据促销活动id判断是否可以进行编辑
     *
     * @param id
     * @return
     */
    @Override
    public Boolean modifiable(Long id) {
        DmsPromotion promotion = dmsPromotionMapper.selectVoByPrimaryKey(id);
        if (promotion == null) {
            throw new ServiceException("数据库中不存在id为: {} 的促销活动信息", id);
        }

        Date now = new Date();
        boolean isFinished = now.compareTo(promotion.getEndTime()) > 0;
        boolean isActive = promotion.getActive().equals(DmsModuleEnums.PROMOTION_ACTIVE_TYPE.VALID.getType());

        //已结束的活动，不可编辑
        //下架(失效)的活动，不可编辑
        if (isFinished || !isActive) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 参数检查
     *
     * @param promotionVo
     */
    private void paramCheck(DmsPromotionVo promotionVo) {
        if (promotionVo == null) {
            throw new ServiceException("参数异常");
        }
        if (StringUtils.isBlank(promotionVo.getSubject())) {
            throw new ServiceException("促销活动主题不能为空");
        }
        if (StringUtils.isBlank(promotionVo.getCategory())) {
            throw new ServiceException("请选择促销活动分类");
        }
        if (promotionVo.getStartTime() == null) {
            throw new ServiceException("请填写活动开始时间");
        }
        if (promotionVo.getEndTime() == null) {
            throw new ServiceException("请填写活动结束时间");
        }
        if (promotionVo.getStartTime().compareTo(promotionVo.getEndTime()) > 0) {
            throw new ServiceException("结束时间不得早于开始时间");
        }
    }

    /**
     * 新增时参数检查
     *
     * @param promotionVo
     */
    private void insertCheck(DmsPromotionVo promotionVo) {
        Date now = new Date();
        if (promotionVo.getStartTime().compareTo(now) < 0 || promotionVo.getEndTime().compareTo(now) < 0) {
            throw new ServiceException("开始时间/结束时间不得早于当前时间");
        }
    }

    /**
     * 修改时参数检查
     *
     * @param promotionVo
     */

    private void updateCheck(DmsPromotionVo promotionVo) {
        if (promotionVo == null || promotionVo.getId() == null) {
            throw new ServiceException("参数异常");
        }

        DmsPromotion entity = dmsPromotionMapper.selectByPrimaryKey(promotionVo.getId());
        if (entity == null) {
            throw new ServiceException("数据库中不存在id为: {} 的促销活动信息", promotionVo.getId());
        }

        //下架(失效)的活动，不可编辑
        Integer status = entity.getActive();
        if (status.equals(DmsModuleEnums.PROMOTION_ACTIVE_TYPE.INVALID.getType())) {
            throw new ServiceException("活动已下架，不可编辑");
        }

        Date now = new Date();
        Date startTime = entity.getStartTime();
        Date endTime = entity.getEndTime();

        //已结束的活动，不可编辑
        if (now.compareTo(endTime) > 0) {
            throw new ServiceException("活动已结束，不可编辑");
        }

        //活动已开始，不可编辑 开始时间；
        if (now.compareTo(startTime) > 0 && startTime.compareTo(promotionVo.getStartTime()) != 0) {
            throw new ServiceException("活动已开始，不可编辑 开始时间");
        }

        //活动编码不允许修改
        String code = entity.getCode();
        if (!code.equals(promotionVo.getCode())) {
            throw new ServiceException("活动编码不允许修改");
        }

        //活动分类不可编辑修改
        String type = entity.getCategory();
        if (!type.equals(promotionVo.getCategory())) {
            throw new ServiceException("活动分类不可编辑修改");
        }
    }
}