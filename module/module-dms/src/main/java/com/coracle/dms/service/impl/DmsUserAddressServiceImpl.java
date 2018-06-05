package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsUserAddressMapper;
import com.coracle.dms.po.DmsUserAddress;
import com.coracle.dms.service.DmsGlobalVariableService;
import com.coracle.dms.service.DmsUserAddressService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.xiruo.medbid.components.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DmsUserAddressServiceImpl extends BaseServiceImpl<DmsUserAddress> implements DmsUserAddressService {
    private static final Logger logger = Logger.getLogger(DmsUserAddressServiceImpl.class);

    @Autowired
    private DmsUserAddressMapper dmsUserAddressMapper;
    @Autowired
    private DmsGlobalVariableService dmsGlobalVariableService;

    @Override
    public IMybatisDao<DmsUserAddress> getBaseDao() {
        return dmsUserAddressMapper;
    }

    /**
     * 添加收货地址，返回主键ID
     * @param dmsUserAddress
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveOrUpdate(DmsUserAddress dmsUserAddress){
        checkParams(dmsUserAddress);
        if (dmsUserAddress.getId()==null||dmsUserAddress.getId()==0) {//添加
            if (dmsUserAddress.getIsDefault()==1){//如果设置了默认地址就要吧原有表中的默认地址改成非默认
                dealDefaultAddress(dmsUserAddress);
            }
            dmsUserAddressMapper.insert(dmsUserAddress);
        }else {//更新
            DmsUserAddress dmsUserAddress1 = dmsUserAddressMapper.selectByPrimaryKey(dmsUserAddress.getId());
            if (BlankUtil.isEmpty(dmsUserAddress1)){
                throw new ServiceException("无法获取当前id为"+dmsUserAddress.getId()+"的地址信息！");
            }
            if (dmsUserAddress.getIsDefault()==1&&dmsUserAddress1.getIsDefault()!=1){//如果设置了默认地址就要吧原有表中的默认地址改成非默认
                dealDefaultAddress(dmsUserAddress);
            }
            dmsUserAddressMapper.updateByPrimaryKeySelective(dmsUserAddress);
        }
    }

    /**
     * 把所有的默认地址变为非默认地址
     * @param dmsUserAddress
     */
    public void dealDefaultAddress(DmsUserAddress dmsUserAddress){
        //Map<String,Object> map = dmsUserAddressMapper.findAddressIds(dmsUserAddress.getUserId());
        //map.get("addressIds") == null : "",map.get("addressIds").toString()
        DmsUserAddress dmsUserAddress1 = new DmsUserAddress();
        dmsUserAddress1.setUserId(dmsUserAddress.getUserId());
        dmsUserAddress1.setIsDefault(1);
        List<DmsUserAddress> list = dmsUserAddressMapper.selectByCondition(dmsUserAddress1);
        if (list!=null&&list.size()>0){
            List<Long> ids = new ArrayList<>();
            for(DmsUserAddress da:list){
                ids.add(da.getId());
            }
            DmsUserAddress dmsUserAddress2 = new DmsUserAddress();
            dmsUserAddress2.setUserId(dmsUserAddress.getUserId());
            dmsUserAddress2.setIsDefault(0);
            dmsUserAddressMapper.updateByIdsSelective(dmsUserAddress2,ids);
        }
    }

    /**
     * 获取用户的默认收货地址
     * @param userId
     */
    @Override
    public DmsUserAddress getDefaultAddress(Long userId) {
        DmsUserAddress param = new DmsUserAddress();
        param.setUserId(userId);
        param.setIsDefault(1);
        param.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        List<DmsUserAddress> userAddressList = dmsUserAddressMapper.selectByCondition(param);
        if (userAddressList.isEmpty()) {
            return null;
        } else {
            return userAddressList.get(0);
        }
    }

   /**
     * 校验参数
     * @param dmsUserAddress
     */
    private void checkParams(DmsUserAddress dmsUserAddress){
        if (dmsUserAddress == null) {
            throw new ServiceException("参数异常");
        }
        if (StringUtils.isBlank(dmsUserAddress.getRecipientName())) {
            throw new ServiceException("收货人不能为空");
        }
        if (StringUtils.isBlank(dmsUserAddress.getMobile())) {
            throw new ServiceException("手机不能为空");
        }
        if (StringUtils.isBlank(dmsUserAddress.getProvince())||StringUtils.isBlank(dmsUserAddress.getCity())||StringUtils.isBlank(dmsUserAddress.getCounty())){
            throw new ServiceException("请选择合适的省市区");
        }
        if (StringUtils.isBlank(dmsUserAddress.getShippingAddress())) {
            throw new ServiceException("请输入详细的收货地址");
        }
    }
}