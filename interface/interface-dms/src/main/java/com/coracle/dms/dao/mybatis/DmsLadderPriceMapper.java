/**
 * create by Administrator
 * @date 2018-01
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsLadderPrice;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsLadderPriceMapper extends IMybatisDao<DmsLadderPrice> {

    List<DmsLadderPrice> selectVoByLadderPriceProductId(Long ladderPriceProductId);
    /**
     * 根据阶梯价格项id查询阶梯价格id list
     */
    List<Long> selectIdByLadderPriceProductId(Long ladderPriceProductId );


    /**
     * 批量删除
     */

    void batchRemove(List<Long> ids) ;

}