/**
 * create by Administrator
 * @date 2018-01
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsLadderPriceScope;
import com.coracle.dms.po.DmsPromotionScope;
import com.coracle.dms.vo.DmsLadderProductScopeVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsLadderPriceScopeMapper extends IMybatisDao<DmsLadderPriceScope> {

    //根据阶梯价格产品id删除范围
    void deleteByLadderPriceProductId(Long ladderPriceProductId);

    /**
     * 批量插入
     *
     * @param ladderPriceScopeList
     */
    void batchInsert(List<DmsLadderPriceScope> list);


    /**
     * 根据阶梯价格项的id查询适用范围vo对象
     */
    List<DmsLadderProductScopeVo> selectVoByLadderPriceProductId(Long ladderPriceProductId);

    /**
     *根据计提价格项id 获取使用范围
     */


    String selectScopeByLadderPriceProductId(Long adderPriceProductId);



}