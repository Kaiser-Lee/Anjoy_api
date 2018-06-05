/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsChannelInformation;
import com.coracle.dms.po.DmsPublishRrange;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DmsPublishRrangeMapper extends IMybatisDao<DmsPublishRrange> {
    /**
     * 批量添加发布范围
     * @param list
     * @return
     */
    void batchInsert(List<DmsPublishRrange> list);
    /**
     * 删除原有的范围
     * @param dmsPublishRrange
     * @return
     */
    void deleteByRelatedInfo(DmsPublishRrange dmsPublishRrange);

    /**
     * 依据发布范围ID，获取名字
     * @param ids
     * @return
     */
    List<Map<String,Object>> getPublishRangeNameIds(@Param("ids") List<Long> ids);
}