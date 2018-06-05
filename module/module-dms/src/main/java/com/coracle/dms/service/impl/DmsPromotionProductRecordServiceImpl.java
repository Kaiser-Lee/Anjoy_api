package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsPromotionProductRecordMapper;
import com.coracle.dms.po.DmsPromotionProductRecord;
import com.coracle.dms.service.DmsPromotionProductRecordService;
import com.coracle.dms.vo.DmsPromotionProductRecordVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmsPromotionProductRecordServiceImpl extends BaseServiceImpl<DmsPromotionProductRecord> implements DmsPromotionProductRecordService {
    private static final Logger logger = Logger.getLogger(DmsPromotionProductRecordServiceImpl.class);

    @Autowired
    private DmsPromotionProductRecordMapper dmsPromotionProductRecordMapper;

    @Override
    public IMybatisDao<DmsPromotionProductRecord> getBaseDao() {
        return dmsPromotionProductRecordMapper;
    }

    /**
     * 促销产品销售记录列表
     *
     * @param promotionProductRecord
     * @return
     */
    @Override
    public PageHelper.Page<DmsPromotionProductRecordVo> pageList(DmsPromotionProductRecordVo promotionProductRecord) {
        try {
            PageHelper.startPage(promotionProductRecord.getP(), promotionProductRecord.getS());
            dmsPromotionProductRecordMapper.selectVoByPromotionProductId(promotionProductRecord);
        } catch (Exception e) {
            logger.error("分页查询异常!", e);
            throw new ServiceException("分页查询异常!");
        } finally {
            return PageHelper.endPage();
        }
    }

    /**
     *  根据促销产品id逻辑删除
     *
     * @param promotionProductId
     */
    @Override
    public void removeByPromotionProductId(Long promotionProductId) {
        dmsPromotionProductRecordMapper.removeByPromotionProductId(promotionProductId);
    }
}