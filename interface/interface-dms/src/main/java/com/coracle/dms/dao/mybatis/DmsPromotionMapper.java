/**
 * create by hcs
 * @date 2017-10
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsPromotion;
import com.coracle.dms.vo.DmsPromotionVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsPromotionMapper extends IMybatisDao<DmsPromotion> {

    /**
     * 根据主键id获取vo对象
     *
     * @param id
     * @return
     */
    DmsPromotionVo selectVoByPrimaryKey(Long id);

    /**
     * 根据条件获取vo对象列表
     *
     * @param promotionVo
     * @return
     */
    List<DmsPromotionVo> selectVoByCondition(DmsPromotionVo promotionVo);
}