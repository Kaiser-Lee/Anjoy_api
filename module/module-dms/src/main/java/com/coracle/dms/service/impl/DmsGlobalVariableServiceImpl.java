package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsGlobalVariableMapper;
import com.coracle.dms.po.DmsGlobalVariable;
import com.coracle.dms.service.DmsGlobalVariableService;
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

import java.util.Date;

@Service
public class DmsGlobalVariableServiceImpl extends BaseServiceImpl<DmsGlobalVariable> implements DmsGlobalVariableService {
    private static final Logger logger = Logger.getLogger(DmsGlobalVariableServiceImpl.class);

    @Autowired
    private DmsGlobalVariableMapper dmsGlobalVariableMapper;

    @Override
    public IMybatisDao<DmsGlobalVariable> getBaseDao() {
        return dmsGlobalVariableMapper;
    }

    /***
     * 新增或者修改全局变量
     * @param dmsGlobalVariable
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void saveOrUpdate(DmsGlobalVariable dmsGlobalVariable, UserSession userSession) {
        checkParam(dmsGlobalVariable);
        //新增
        if(BlankUtil.isEmpty(dmsGlobalVariable.getId())||dmsGlobalVariable.getId()==0){
            dmsGlobalVariable.setId(null);
            dmsGlobalVariable.setCreatedBy(userSession.getId());
            dmsGlobalVariable.setLastUpdatedBy(userSession.getId());
            dmsGlobalVariableMapper.insert(dmsGlobalVariable);
        }else {//修改
            DmsGlobalVariable dmsGlobalVariable2 = dmsGlobalVariableMapper.selectByPrimaryKey(dmsGlobalVariable.getId());
            if (BlankUtil.isEmpty(dmsGlobalVariable2)){
                throw new ServiceException("无法获取当前id为"+dmsGlobalVariable.getId()+"的信息！");
            }
            dmsGlobalVariable.setLastUpdatedBy(userSession.getId());
            dmsGlobalVariableMapper.updateByPrimaryKeySelective(dmsGlobalVariable);
        }
    }

    /***
     * 删除全局变量，软删除
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void delete(Long id, UserSession userSession) {
        if (BlankUtil.isEmpty(id)||id==0){
            throw new ServiceException("请传入合法的ID！");
        }
        DmsGlobalVariable dmsGlobalVariable1 = dmsGlobalVariableMapper.selectByPrimaryKey(id);
        if (BlankUtil.isEmpty(dmsGlobalVariable1)){
            throw new ServiceException("id为"+dmsGlobalVariable1.getId()+"的全局变量信息不存在！");
        }
        DmsGlobalVariable dmsGlobalVariable = new DmsGlobalVariable();
        dmsGlobalVariable.setId(id);
        dmsGlobalVariable.setRemoveFlag(1);
        dmsGlobalVariable.setLastUpdatedBy(userSession.getId());
        dmsGlobalVariable.setLastUpdatedDate(new Date());
        dmsGlobalVariableMapper.updateByPrimaryKeySelective(dmsGlobalVariable);
    }

    /**
     * 全局变量分页查询
     * @param dmsGlobalVariable
     * @return
     */
    public PageHelper.Page<DmsGlobalVariable> getPageList(DmsGlobalVariable dmsGlobalVariable, UserSession userSession) {
        try {
            PageHelper.startPage(dmsGlobalVariable.getP(), dmsGlobalVariable.getS());
            dmsGlobalVariableMapper.getPageList(dmsGlobalVariable);
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            logger.error("获取全局变量分页报错信息：",e);
            throw new ServiceException("全局变量分页查询异常--->>>");
        }
    }
    /**
     * 统一检验参数
     * @param dmsGlobalVariable
     */
    private void checkParam(DmsGlobalVariable dmsGlobalVariable) {
        if (dmsGlobalVariable == null) {
            throw new ServiceException("参数异常");
        }
        if (StringUtils.isBlank(dmsGlobalVariable.getsKey())) {
            throw new ServiceException("全局变量key不能为空");
        }
        if (StringUtils.isBlank(dmsGlobalVariable.getValue())) {
            throw new ServiceException("全局变量value不能为空");
        }
        if (StringUtils.isBlank(dmsGlobalVariable.getName())) {
            throw new ServiceException("全局变量名称不能为空");
        }
    }
    
    /**
     * 根据参数key获取值.后续考虑加缓存处理
     */
	@Override
	public String queryValueByKey(String paramKey) {
		if (StringUtils.isBlank(paramKey)) {
            return null;
        }
		DmsGlobalVariable entity = this.dmsGlobalVariableMapper.queryByKey(paramKey);
        if (entity == null) return null;
        return entity.getValue();
	}
}