package com.coracle.dms.service;

import com.coracle.dms.po.DmsCommonAttachFile;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsCommonAttachFileService extends IBaseService<DmsCommonAttachFile> {

    /**
     * 逻辑删除文件
     * @param ids
     * @return
     */
    int updateByIdsSelectiveByDelete(List<Long>ids);

    /**
     * 插入附件并返回对象
     * @param po
     * @return
     */
    DmsCommonAttachFile insertByRePrimaryKey(DmsCommonAttachFile po);

    /**
     * 下载次数增加
     * @return
     */
    int updateByIdsSelectiveByAddCount(DmsCommonAttachFile po);
}