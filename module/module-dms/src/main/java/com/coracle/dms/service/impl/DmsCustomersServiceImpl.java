package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsCustomersMapper;
import com.coracle.dms.po.DmsCustomers;
import com.coracle.dms.service.DmsCustomersService;
import com.coracle.dms.vo.DmsCustomersVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DmsCustomersServiceImpl extends BaseServiceImpl<DmsCustomers> implements DmsCustomersService {
    private static final Logger logger = Logger.getLogger(DmsCustomersServiceImpl.class);

    @Autowired
    private DmsCustomersMapper dmsCustomersMapper;

    @Override
    public IMybatisDao<DmsCustomers> getBaseDao() {
        return dmsCustomersMapper;
    }

    /***
     * 修改客户信息
     * @param dmsCustomersVo
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void updateCustomer(DmsCustomersVo dmsCustomersVo, long userId){
        checkParams(dmsCustomersVo);
        DmsCustomers dmsCustomers = dmsCustomersMapper.selectByPrimaryKey(dmsCustomersVo.getId());
        if (BlankUtil.isEmpty(dmsCustomers)) {
            throw new ServiceException("无法获取ID为【"+dmsCustomersVo.getId()+"】的客户信息！");
        }
        dmsCustomersMapper.updateByPrimaryKeySelective(dmsCustomersVo);
    }

    /***
     * 新增客户信息
     * @param dmsCustomersVo
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Long insertCustomer(DmsCustomersVo dmsCustomersVo, long userId){
        checkParams(dmsCustomersVo);
        dmsCustomersMapper.insert(dmsCustomersVo);
        return dmsCustomersVo.getId();
    }

    /**
     * 分页获取客户信息列表
     * @param dmsCustomersVo
     * @return
     */
    public PageHelper.Page<DmsCustomersVo> selectForPage(DmsCustomersVo dmsCustomersVo){
        try {
            PageHelper.startPage(dmsCustomersVo.getP(), dmsCustomersVo.getS());
            dmsCustomersMapper.getPageList(dmsCustomersVo);
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            logger.error("分页获取客户信息列表报错信息：",e);
            throw new ServiceException("客户信息列表分页查询异常--->>>");
        }
    }

    /**
     * 获取客户vo
     * @param id
     * @return
     */
    public DmsCustomersVo selectVoByPrimaryKey(Long id){
        return dmsCustomersMapper.selectVoByPrimaryKey(id);
    }

    /**
     * 统一检验参数
     * @param dmsCustomersVo
     */
    private void checkParams(DmsCustomersVo dmsCustomersVo) {
        if (dmsCustomersVo == null) {
            throw new ServiceException("参数异常");
        }
        if (StringUtils.isBlank(dmsCustomersVo.getName())) {
            throw new ServiceException("客户名称不能为空");
        }
        if (StringUtils.isBlank(dmsCustomersVo.getPhone())) {
            throw new ServiceException("客户手机不能为空");
        }
    }
}