package com.coracle.dms.service;

import com.coracle.dms.po.DmsNews;
import com.coracle.dms.vo.DmsNewsVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xservice.base.intf.IBaseService;

import java.util.List;

public interface DmsNewsService extends IBaseService<DmsNews> {
    /**
     * 插入新闻资讯返回主键ID
     * @param po
     * @return
     */
    long insertNews(DmsNewsVo po);

    /**
     *  分页获取新闻列表
     * @param dmsNewsVo
     * @return
     */
    PageHelper.Page<DmsNewsVo> findNewsPageList(DmsNewsVo dmsNewsVo);

    /**
     * 通过Id查询新闻详情
     * @param id
     * @return
     */
    DmsNewsVo selectNewByPrimaryKey(Long id);

    /**
     * 分页获取用户可以看到的新闻
     * @param id 用户所属范围ID
     * @param p
     * @param s
     * @return
     */
    PageHelper.Page<DmsNewsVo> selectNewsPageByUser(Long id,Integer isShow,Integer p,Integer s);

    /**
     * 修改资讯新闻
     * @param dmsNewsVo
     */
    void updateNews(DmsNewsVo dmsNewsVo);
}