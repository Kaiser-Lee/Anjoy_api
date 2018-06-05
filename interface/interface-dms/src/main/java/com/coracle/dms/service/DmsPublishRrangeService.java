package com.coracle.dms.service;

import com.coracle.dms.po.DmsChannelInformation;
import com.coracle.dms.po.DmsPublishRrange;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;
import java.util.Map;

public interface DmsPublishRrangeService extends IBaseService<DmsPublishRrange> {

    /**
     * 批量添加发布范围
     * @param dmsPublishRrangeList
     * @return
     */
    void batchInsertRange(List<DmsPublishRrange> dmsPublishRrangeList);

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
    List<Map<String,Object>> getPublishRangeNameIds(List<Long> ids);
}