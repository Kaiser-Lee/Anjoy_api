package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsDataDictionayMapper;
import com.coracle.dms.po.DmsDataDictionay;
import com.coracle.dms.service.DmsDataDictionayService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmsDataDictionayServiceImpl extends BaseServiceImpl<DmsDataDictionay> implements DmsDataDictionayService {
    private static final Logger logger = Logger.getLogger(DmsDataDictionayServiceImpl.class);

    @Autowired
    private DmsDataDictionayMapper dmsDataDictionayMapper;

    @Override
    public IMybatisDao<DmsDataDictionay> getBaseDao() {
        return dmsDataDictionayMapper;
    }

    @Override
    public PageHelper.Page<DmsDataDictionay> findDictionayPageList(DmsDataDictionay dmsDataDictionay) {
        try {
            PageHelper.startPage(dmsDataDictionay.getP(),dmsDataDictionay.getS());
            dmsDataDictionayMapper.findDictionayPageList(dmsDataDictionay);
        } catch (Exception e) {
            System.out.println("分页查询异常"+e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }

    @Override
    public List<DmsDataDictionay> findDictionayList(DmsDataDictionay dmsDataDictionay) {
        return dmsDataDictionayMapper.findDictionayPageList(dmsDataDictionay);
    }

    @Override
    public DmsDataDictionay selectByName(String name) {
        return dmsDataDictionayMapper.selectByName(name);
    }

    @Override
    public DmsDataDictionay selectBySkey(String sKey) {
        return dmsDataDictionayMapper.selectBySkey(sKey);
    }

    @Override
    public void updateById(Long id) {
        dmsDataDictionayMapper.updateById(id);
    }

    @Override
    public void insertTo(DmsDataDictionay dmsDataDictionay) {

        //先校验数据项key是否存在
        DmsDataDictionay dataDictionay = dmsDataDictionayMapper.selectBySkey(dmsDataDictionay.getsKey());
        if(dataDictionay!=null){
            throw new ServiceException("数据项key重复，请重复输入！");
        }
        dmsDataDictionayMapper.insert(dmsDataDictionay);
    }
}