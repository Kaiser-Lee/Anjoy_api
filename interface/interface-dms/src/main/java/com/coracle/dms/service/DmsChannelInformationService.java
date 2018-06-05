package com.coracle.dms.service;

import com.coracle.dms.po.DmsChannelInformation;
import com.coracle.dms.vo.DmsChannelInformationVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsChannelInformationService extends IBaseService<DmsChannelInformation> {
    /**
     * 添加通知，返回主键ID
     * @param dmsChannelInformationVo
     * @eturn
     */
    long insertChannelInformation(DmsChannelInformationVo dmsChannelInformationVo);

    /**
     * 修改渠道赋能
     * @param dmsChannelInformationVo
     * @return
     */
    void modifyChannelInformation(DmsChannelInformationVo dmsChannelInformationVo);

    /**
     * 分页获取渠道赋能列表
     * @param dmsChannelInformationVo
     * @return
     */
    PageHelper.Page<DmsChannelInformationVo> selectInformationPageList(DmsChannelInformationVo dmsChannelInformationVo);

    DmsChannelInformationVo selectVoByPrimaryKey(Long id);

    /**
     * 分页获取渠道赋能列表
     * @param id
     * @param p
     * @param s
     * @return
     */
    PageHelper.Page<DmsChannelInformationVo> selectChannelInforsForPageList(Long id, Integer p, Integer s,String type,String title);
}