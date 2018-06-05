package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsProductBrandMapper;
import com.coracle.dms.po.DmsProductBrand;
import com.coracle.dms.service.DmsProductBrandService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.xiruo.medbid.components.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DmsProductBrandServiceImpl extends BaseServiceImpl<DmsProductBrand> implements DmsProductBrandService {
    private static final Logger logger = Logger.getLogger(DmsProductBrandServiceImpl.class);

    @Autowired
    private DmsProductBrandMapper dmsProductBrandMapper;

    @Override
    public IMybatisDao<DmsProductBrand> getBaseDao() {
        return dmsProductBrandMapper;
    }

    /***
     * 新增或者修改产品品牌
     * @param dmsProductBrand
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void saveOrUpdate(DmsProductBrand dmsProductBrand, UserSession userSession) {
        checkParam(dmsProductBrand);
        //新增
        if(BlankUtil.isEmpty(dmsProductBrand.getId())||dmsProductBrand.getId()==0){
            dmsProductBrand.setCreatedBy(userSession.getId());
            dmsProductBrand.setLastUpdatedBy(userSession.getId());
            dmsProductBrandMapper.insert(dmsProductBrand);
        }else {//修改
            DmsProductBrand dmsProductBrand1 = dmsProductBrandMapper.selectByPrimaryKey(dmsProductBrand.getId());
            if (BlankUtil.isEmpty(dmsProductBrand1)){
                throw new ServiceException("无法获取当前id为"+dmsProductBrand1.getId()+"的信息！");
            }
            dmsProductBrand.setLastUpdatedBy(userSession.getId());
            dmsProductBrandMapper.updateByPrimaryKeySelective(dmsProductBrand);
        }
    }

    /***
     * 删除全局变量，软删除
     * @param dmsProductBrand
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void delete(DmsProductBrand dmsProductBrand, UserSession userSession) {
        if (BlankUtil.isEmpty(dmsProductBrand.getId())||dmsProductBrand.getId()==0){
            throw new ServiceException("请传入合法的ID！");
        }
        DmsProductBrand dmsProductBrand1 = dmsProductBrandMapper.selectByPrimaryKey(dmsProductBrand.getId());
        if (BlankUtil.isEmpty(dmsProductBrand1)){
            throw new ServiceException("无法获取当前id为"+dmsProductBrand1.getId()+"的信息！");
        }
        DmsProductBrand dmsProductBrand2 = new DmsProductBrand();
        dmsProductBrand2.setId(dmsProductBrand.getId());
        dmsProductBrand2.setRemoveFlag(1);
        dmsProductBrand2.setLastUpdatedBy(userSession.getId());
        dmsProductBrand2.setLastUpdatedDate(dmsProductBrand.getLastUpdatedDate());
        dmsProductBrandMapper.updateByPrimaryKeySelective(dmsProductBrand2);
    }

    /**
     * 全局变量分页查询
     * @param dmsProductBrand
     * @return
     */
    public PageHelper.Page<DmsProductBrand> selectForPageList(DmsProductBrand dmsProductBrand, UserSession userSession) {
        try {
            PageHelper.startPage(dmsProductBrand.getP(), dmsProductBrand.getS());
            dmsProductBrandMapper.getPageList(dmsProductBrand);
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            logger.error("获取全局变量分页报错信息：",e);
            throw new ServiceException("全局变量分页查询异常--->>>");
        }
    }

    /**
     * 统一检验参数
     * @param dmsProductBrand
     */
    private void checkParam(DmsProductBrand dmsProductBrand) {
        if (dmsProductBrand == null) {
            throw new ServiceException("参数异常");
        }
        if (StringUtils.isBlank(dmsProductBrand.getName())) {
            throw new ServiceException("产品品牌名称不能为空");
        }
        if (StringUtils.isBlank(dmsProductBrand.getCode())) {
            throw new ServiceException("产品品牌编码不能为空");
        }
    }
}