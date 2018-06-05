/**
 * create by Administrator
 * @date 2018-01
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsChannelEmployee;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.po.DmsChannelMutilVo;

import java.util.List;
import java.util.Map;

public interface DmsChannelEmployeeMapper extends IMybatisDao<DmsChannelEmployee> {

    void batchInsert(List<DmsChannelEmployee> dmsChannelEmployeeList);

    void deleteByChannelId(Long channeldId);

    List<DmsChannelEmployee> selectByChannelId(Long channeldId);

    void deleteByEmployeeID(Long EmployeeId);

    Integer deleteChannelEmployeeSyncAnjoy();

    /**
     * 通过业务员获取经销商
     * @param paramMap
     * userId：用户ID
     * @return
     */
    List<DmsChannelMutilVo> selectChannelList(Map<String, Object> paramMap);

}