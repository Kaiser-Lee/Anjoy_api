package com.coracle.dms.service;

import com.coracle.dms.po.DmsNotice;
import com.coracle.dms.vo.DmsNoticeVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsNoticeService extends IBaseService<DmsNotice> {

    /**
     * 添加通知，返回主键ID
     * @param dmsNoticeVo
     * @return
     */
    long insertNotice(DmsNoticeVo dmsNoticeVo);

    /**
     * 分页查询通知列表，pc查询
     * @param dmsNoticeVo
     * @return
     */
    PageHelper.Page<DmsNoticeVo> selectForPcList(DmsNoticeVo dmsNoticeVo);

    DmsNoticeVo getNoticeDetail(Long id);
}