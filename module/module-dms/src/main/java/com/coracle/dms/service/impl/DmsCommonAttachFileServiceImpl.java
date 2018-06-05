package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsCommonAttachFileMapper;
import com.coracle.dms.po.DmsCommonAttachFile;
import com.coracle.dms.service.DmsAttachmentRelationService;
import com.coracle.dms.service.DmsCommonAttachFileService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.util.BlankUtil;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmsCommonAttachFileServiceImpl extends BaseServiceImpl<DmsCommonAttachFile> implements DmsCommonAttachFileService {
    private static final Logger logger = Logger.getLogger(DmsCommonAttachFileServiceImpl.class);

    @Autowired
    private DmsCommonAttachFileMapper dmsCommonAttachFileMapper;
    @Autowired
    private DmsAttachmentRelationService dmsAttachmentRelationService;

    @Override
    public IMybatisDao<DmsCommonAttachFile> getBaseDao() {
        return dmsCommonAttachFileMapper;
    }

    /**
     * 逻辑删除文件
     * @param ids
     * @return
     */
    @Override
    public int updateByIdsSelectiveByDelete(List<Long> ids) {
        try {
            dmsAttachmentRelationService.updateByIdsSelectiveByDelete(ids);
            dmsCommonAttachFileMapper.updateByIdsSelectiveByDelete(ids);
            return 1;
        } catch (Exception e) {
            throw new ServiceException("逻辑删除文件异常!");
        }
    }

    @Override
    public DmsCommonAttachFile insertByRePrimaryKey(DmsCommonAttachFile po) {
        dmsCommonAttachFileMapper.insertByRePrimaryKey(po);
        final String fileDownloadUrl = "/api/v1/dms/commonAttachUpload/getFile?id=";
        DmsCommonAttachFile dmsCommonAttachFile = new DmsCommonAttachFile();
        dmsCommonAttachFile.setId(po.getId());
        dmsCommonAttachFile.setFilePath(fileDownloadUrl + po.getId());
        dmsCommonAttachFileMapper.updateByPrimaryKeySelective(dmsCommonAttachFile);
        po.setFilePath(dmsCommonAttachFile.getFilePath());
        return po;
    }

    @Override
    public int updateByIdsSelectiveByAddCount(DmsCommonAttachFile po) {
        DmsCommonAttachFile dmsCommonAttachFile = new DmsCommonAttachFile();
        dmsCommonAttachFile.setId(po.getId());
        dmsCommonAttachFile.setDownloadCount(BlankUtil.isEmpty(po.getDownloadCount()) ? 1 : po.getDownloadCount()+1);
        return dmsCommonAttachFileMapper.updateByPrimaryKeySelective(dmsCommonAttachFile);
    }
}