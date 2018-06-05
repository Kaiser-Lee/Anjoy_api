/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsSeller;
import com.coracle.dms.vo.DmsSellerVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DmsSellerMapper extends IMybatisDao<DmsSeller> {

    /**
     * 获取门店店员列表全部数据
     * @param condition
     * @return
     */
    List<DmsSellerVo> selectVoByCondition(DmsSellerVo condition);

    /**
     * 获取门店店员详情全部数据
     * @param id
     * @return
     */
    DmsSellerVo selectVoByPrimaryKey(Long id);

    /**
     * 根据门店id列表获取用户ID列表
     * @param ids
     * @return
     */
    List<Long> getUserIdsByStoreIds(@Param("ids")List<Long> ids);
    /**
     * 根据用户id门店ID
     * @param id
     * @return
     */
    Long getStoreIdByUserId(@Param("id") Long id);
}