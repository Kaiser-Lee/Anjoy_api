/**
 * create by Administrator
 * @date 2018-01
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsChannelAddress;
import com.coracle.dms.vo.DmsChannelAddressVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsChannelAddressMapper extends IMybatisDao<DmsChannelAddress> {

    void batchInsert(List<DmsChannelAddressVo> dmsChannelAddressList);

    void batchInsert2(List<DmsChannelAddress> dmsChannelAddressList);

    void deleteByChannelId(Long channelId);

    List<DmsChannelAddressVo> selectByChannelId(Long channelId);

    void deleteByAddressID(Long addressId);


    /**
     * 根据渠道id取消掉该渠道的所有默认收货地址
     *
     * @param channelId
     */
    void cancelDefaultByChannelId(Long channelId);

    /**
     * 根据主键获取vo对象
     *
     * @param id
     * @return
     */
    DmsChannelAddressVo getVoByPrimaryKey(Long id);

    /**
     * 根据渠道id获取默认收货地址
     *
     * @param channelId
     * @return
     */
    DmsChannelAddressVo getDefaultByChannelId(Long channelId);

    DmsChannelAddress selectOneByCondition(DmsChannelAddress condition);

    Integer deleteChannelAddressSyncAnjoy();

}