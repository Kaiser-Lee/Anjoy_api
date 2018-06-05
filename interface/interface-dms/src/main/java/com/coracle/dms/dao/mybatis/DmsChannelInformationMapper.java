/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsChannelInformation;
import com.coracle.dms.vo.DmsChannelInformationVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DmsChannelInformationMapper extends IMybatisDao<DmsChannelInformation> {

    /**
     * 分页获取渠道赋能列表
     * @param dmsChannelInformationVo
     * @return
     */
    List<DmsChannelInformationVo> selectPageByCondition(DmsChannelInformationVo dmsChannelInformationVo);

    void modifyComentCount(@Param(value = "ids") List<Long> ids, @Param(value = "count") int count);

    DmsChannelInformationVo selectVoByPrimaryKey(Long id);

    /**
     * 分页获取渠道赋能列表
     * @param id
     * @return
     */
    List<DmsChannelInformationVo> getChannelInforsList(@Param("id") Long id,@Param("type") String type,@Param("title") String title);
}