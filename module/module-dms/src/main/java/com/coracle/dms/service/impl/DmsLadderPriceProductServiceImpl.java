package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;

import com.coracle.dms.dao.mybatis.DmsLadderPriceProductMapper;
import com.coracle.dms.dao.mybatis.DmsProductMapper;
import com.coracle.dms.po.DmsLadderPriceProduct;
import com.coracle.dms.service.DmsLadderPriceProductService;
import com.coracle.dms.service.DmsLadderPriceScopeService;
import com.coracle.dms.service.DmsLadderPriceService;
import com.coracle.dms.support.SystemParamerConfiguration;
import com.coracle.dms.vo.DmsChannelVo;
import com.coracle.dms.vo.DmsLadderPriceProductVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DmsLadderPriceProductServiceImpl extends BaseServiceImpl<DmsLadderPriceProduct> implements DmsLadderPriceProductService {

    private static final Logger logger = Logger.getLogger(DmsLadderPriceProductServiceImpl.class);
    @Autowired
    private DmsLadderPriceProductMapper dmsLadderPriceProductMapper;
    @Autowired
    private DmsProductMapper dmsProductMapper;
    @Autowired
    private DmsLadderPriceService dmsLadderPriceService;
    @Autowired
    private DmsLadderPriceScopeService dmsLadderPriceScopeService;
    @Override
    public IMybatisDao<DmsLadderPriceProduct> getBaseDao() {
        return dmsLadderPriceProductMapper;
    }

    /**
     * 新增阶梯价格产品&类别信息
     */
    @Override
    public void insertOrUpdate(DmsLadderPriceProductVo dmsLadderPriceProductVo, UserSession userSession){

        boolean isInsert = dmsLadderPriceProductVo.getId()== null? true:false ;
        if(dmsLadderPriceProductVo.getProductCode()!=null){
            String productCategoryName =  dmsProductMapper.selectByProductCode(dmsLadderPriceProductVo.getProductCode());
            dmsLadderPriceProductVo.setCategoryName(productCategoryName);
            dmsLadderPriceProductVo.setFlag(1);
        }
        //新增阶梯价格产品
        if(isInsert){
            dmsLadderPriceProductVo.setCode(SystemParamerConfiguration.getInstance().createSerialNumStr(DmsModuleEnums.PROMOTION_SERIAL_KEY));
            insertCheck(dmsLadderPriceProductVo);
            dmsLadderPriceProductMapper.insert(dmsLadderPriceProductVo);
        }else{
            dmsLadderPriceProductMapper.updateByPrimaryKeySelective(dmsLadderPriceProductVo);
        }
        Long dmsLadderPriceProductId = dmsLadderPriceProductVo.getId();
        //创建更新阶梯价格
        dmsLadderPriceService.insertOrUpdate(dmsLadderPriceProductId,dmsLadderPriceProductVo.getPriceList(),userSession );
        //创建更新范围
        dmsLadderPriceScopeService.insertOrUpdate(dmsLadderPriceProductId,dmsLadderPriceProductVo.getScopeIdList(),userSession);

    }
    /**
     * 阶梯价格分页（分页）
     *
     * @param dmsLadderPriceProductVo
     * @return
     */
    @Override
    public PageHelper.Page<DmsLadderPriceProductVo> pageList(DmsLadderPriceProductVo dmsLadderPriceProductVo) {
        try {
            PageHelper.startPage(dmsLadderPriceProductVo.getP(),dmsLadderPriceProductVo.getS());
            dmsLadderPriceProductMapper.selectVoByCondition(dmsLadderPriceProductVo);
        } catch (Exception e) {
            logger.error("阶梯价格分页查询异常!", e);
            throw new ServiceException("促销活动分页查询异常!");
        } finally {
            return PageHelper.endPage();
        }
    }

    /**
     * 根据阶梯价格项查询详情
     */

    @Override
    public DmsLadderPriceProductVo selectDetailById(Long ladderPriceProductId) {
       return  dmsLadderPriceProductMapper.selectVoByPrimaryKey(ladderPriceProductId);
    }
    /**
     * 新增时参数检查
     *
     * @param
     */
    private void insertCheck(DmsLadderPriceProductVo dmsLadderPriceProductVo) {
        Date now = new Date();
        if (dmsLadderPriceProductVo.getStartTime().compareTo(now) < 0 || dmsLadderPriceProductVo.getEndTime().compareTo(now) < 0) {
            throw new ServiceException("开始时间/结束时间不得早于当前时间");
        }
    }



}