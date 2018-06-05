/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsNotice;
import com.coracle.dms.vo.DmsNoticeVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

import java.util.List;

public interface DmsNoticeMapper extends IMybatisDao<DmsNotice> {

    /**
     * 分页查询通知列表，管理查询
     * @param dmsNoticeVo
     * @return
     */
    List<DmsNoticeVo> selectPageByCondition(DmsNoticeVo dmsNoticeVo);

    DmsNoticeVo selectVoByPrimaryKey(Long id);
}