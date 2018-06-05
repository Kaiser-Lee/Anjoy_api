package com.coracle.dms.service;

import com.coracle.dms.dto.DmsAttachmentDto;
import com.coracle.dms.po.DmsAttachmentRelation;
import com.coracle.dms.po.DmsCommonAttachFile;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.intf.IBaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DmsAttachmentRelationService extends IBaseService<DmsAttachmentRelation> {
    /**
     * 逻辑删除文件
     * @param ids
     * @return
     */
    int updateByIdsSelectiveByDelete(List<Long> ids);

    /**
     * 附件分页查询
     * @param dmsAttachmentDto
     * @return
     */
    PageHelper.Page<DmsAttachmentDto> getPageList(DmsAttachmentDto dmsAttachmentDto, UserSession userSession);
    /**
     * 附件查询
     * @param dmsAttachmentDto
     * @return
     */
    List<DmsAttachmentDto> getList(DmsAttachmentDto dmsAttachmentDto, UserSession userSession);
    /**
     * 下载次数增加
     * @return
     */
    int updateByIdsSelectiveByAddNum(Long id);

    /**
     * 批量插入
     * @param attachmentRelationList
     */
    void batchInsert(List<DmsAttachmentRelation> attachmentRelationList);
}