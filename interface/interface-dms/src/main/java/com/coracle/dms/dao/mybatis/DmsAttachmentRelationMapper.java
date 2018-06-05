/**
 * create by Administrator
 *
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.dto.DmsAttachmentDto;
import com.coracle.dms.po.DmsAttachmentRelation;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DmsAttachmentRelationMapper extends IMybatisDao<DmsAttachmentRelation> {
    /**
     * 逻辑删除文件
     * @param ids
     * @return
     */
    int updateByIdsSelectiveByDelete(@Param("ids") List<Long> ids);

    /**
     * 附件分页查询
     * @param dmsAttachmentDto
     * @return
     */
    List<DmsAttachmentDto> getPageList(DmsAttachmentDto dmsAttachmentDto);

    /**
     * 附件查询
     * @param dmsAttachmentDto
     * @return
     */
    List<DmsAttachmentDto> getList(DmsAttachmentDto dmsAttachmentDto);

    /**
     * 下载次数增加
     * @return
     */
    int updateByIdsSelectiveByAddNum(Long id);

    /**
     * 获取记录数
     * @param dmsAttachmentDto
     * @return
     */
    Integer getRecordCount(DmsAttachmentDto dmsAttachmentDto);

    /**
     * 批量插入
     * @param attachmentRelationList
     */
    void batchInsert(List<DmsAttachmentRelation> attachmentRelationList);
}