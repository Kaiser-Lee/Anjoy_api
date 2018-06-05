package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsChannelMinimum;
import com.coracle.dms.po.DmsProduct;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

/**
 * Created by Administrator on 2018-1-17.
 */
public interface DmsChannelMinimumMapper extends IMybatisDao<DmsChannelMinimum > {

    List<DmsProductVo> findProductForMinimum(DmsProductVo product);

    List<Long> selectIdListByChannelId(Long channelId);



    void deleteByChannelIdandProductID(DmsChannelMinimum dmsChannelMinimum);

    void updateInfo(DmsChannelMinimum dmsChannelMinimum);

    /**
     * 根据条件获取整板下单量
     *
     * @param param
     * @return
     */
    Long getMinOrderQuantityByCondition(DmsChannelMinimum param);


    void batchDelete2(List<DmsChannelMinimum> list);

    void batchInsert2(List<DmsChannelMinimum> list);
}
