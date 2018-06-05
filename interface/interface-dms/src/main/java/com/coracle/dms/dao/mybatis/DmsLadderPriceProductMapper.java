/**
 * create by Administrator
 * @date 2018-01
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsLadderPriceProduct;
import com.coracle.dms.vo.DmsLadderPriceProductVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.po.UserSession;

import java.util.List;

public interface DmsLadderPriceProductMapper extends IMybatisDao<DmsLadderPriceProduct> {
    /**
    * 查询详情
     */
    DmsLadderPriceProductVo selectVoByPrimaryKey(Long id);

    /**
     * 根据条件分页查询
     */

    List<DmsLadderPriceProductVo> selectVoByCondition(DmsLadderPriceProductVo dmsLadderPriceProductVo);







}