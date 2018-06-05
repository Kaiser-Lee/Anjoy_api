package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsAttachmentRelationMapper;
import com.coracle.dms.dto.DmsAttachmentDto;
import com.coracle.dms.po.DmsAttachmentRelation;
import com.coracle.dms.po.DmsCommonAttachFile;
import com.coracle.dms.service.DmsAttachmentRelationService;
import com.coracle.dms.vo.DmsCommonAttachFileVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class DmsAttachmentRelationServiceImpl extends BaseServiceImpl<DmsAttachmentRelation> implements DmsAttachmentRelationService {
    private static final Logger logger = Logger.getLogger(DmsAttachmentRelationServiceImpl.class);

    @Autowired
    private DmsAttachmentRelationMapper dmsAttachmentRelationMapper;

    @Override
    public IMybatisDao<DmsAttachmentRelation> getBaseDao() {
        return dmsAttachmentRelationMapper;
    }

    /**
     * 逻辑删除文件
     * @param ids
     * @return
     */
    @Override
    public int updateByIdsSelectiveByDelete(List<Long> ids) {
        return dmsAttachmentRelationMapper.updateByIdsSelectiveByDelete(ids);
    }

    /**
     * 附件分页查询
     * @param dmsAttachmentDto
     * @return
     */
    public PageHelper.Page<DmsAttachmentDto> getPageList(DmsAttachmentDto dmsAttachmentDto, UserSession userSession) {
        try {
            PageHelper.startPage(dmsAttachmentDto.getP(),dmsAttachmentDto.getS());
            dmsAttachmentRelationMapper.getPageList(dmsAttachmentDto);
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            logger.error("获取附件分页报错信息：",e);
            throw new ServiceException("附件分页查询异常--->>>");
        }
    }

    @Override
    public List<DmsAttachmentDto> getList(DmsAttachmentDto dmsAttachmentDto, UserSession userSession) {
        return dmsAttachmentRelationMapper.getList(dmsAttachmentDto);
    }

    @Override
    public int updateByIdsSelectiveByAddNum(Long id) {
        return dmsAttachmentRelationMapper.updateByIdsSelectiveByAddNum(id);
    }

    @Override
    public void batchInsert(List<DmsAttachmentRelation> attachmentRelationList) {
        dmsAttachmentRelationMapper.batchInsert(attachmentRelationList);
    }
}