package com.coracle.dms.service;

import com.coracle.dms.po.DmsChannelProductAnjoyWhiteList;
import com.coracle.dms.vo.DmdChannelProductAnjoyVo;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;
import java.util.Map;

public interface DmsChannelProductAnjoyWhiteListService extends IBaseService<DmsChannelProductAnjoyWhiteList> {
    /**
     * 同步安井渠道-产品关系数据信息
     */
    void anjoySyn();

    /**
     * 手动导入Excel 安井渠道-产品关系数据
     */
    Map<String, Object> importAnjoyExcel(List<DmdChannelProductAnjoyVo> dataList);

}