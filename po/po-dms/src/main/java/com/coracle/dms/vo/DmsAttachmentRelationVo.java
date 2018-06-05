package com.coracle.dms.vo;

import com.coracle.dms.po.DmsAttachmentRelation;
import com.coracle.dms.po.DmsCommonAttachFile;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/21.
 */
public class DmsAttachmentRelationVo extends DmsAttachmentRelation {
    private List<DmsCommonAttachFile> dmsCommonAttachFileList;
    private List<Map<String,Object>> dmsCommonAttachFileVoList;

    public List<DmsCommonAttachFile> getDmsCommonAttachFileList() {
        return dmsCommonAttachFileList;
    }

    public void setDmsCommonAttachFileList(List<DmsCommonAttachFile> dmsCommonAttachFileList) {
        this.dmsCommonAttachFileList = dmsCommonAttachFileList;
    }

    public List<Map<String, Object>> getDmsCommonAttachFileVoList() {
        return dmsCommonAttachFileVoList;
    }

    public void setDmsCommonAttachFileVoList(List<Map<String, Object>> dmsCommonAttachFileVoList) {
        this.dmsCommonAttachFileVoList = dmsCommonAttachFileVoList;
    }
}
