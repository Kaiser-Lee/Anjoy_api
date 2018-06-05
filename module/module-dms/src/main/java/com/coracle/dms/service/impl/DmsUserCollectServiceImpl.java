package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsUserCollectMapper;
import com.coracle.dms.po.DmsUserCollect;
import com.coracle.dms.service.DmsUserCollectService;
import com.coracle.dms.vo.DmsUserCollectVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmsUserCollectServiceImpl extends BaseServiceImpl<DmsUserCollect> implements DmsUserCollectService {
    private static final Logger logger = Logger.getLogger(DmsUserCollectServiceImpl.class);

    @Autowired
    private DmsUserCollectMapper dmsUserCollectMapper;

    @Override
    public IMybatisDao<DmsUserCollect> getBaseDao() {
        return dmsUserCollectMapper;
    }

    /**
     * 分页获取用户收藏没有库存的记录详情
     * @param dmsUserCollect
     * @return
     */
    public PageHelper.Page<DmsUserCollectVo> selectForNoNumList(DmsUserCollect dmsUserCollect){
        try {
            PageHelper.startPage(dmsUserCollect.getP(), dmsUserCollect.getS());
            dmsUserCollectMapper.getPageList(dmsUserCollect.getUserId());
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            throw new ServiceException("分页查询异常"+e.getMessage());
        }
    }

    /**
     * 获取收藏，没有库存 不分页
     * @param id
     * @return
     */
    public List<DmsUserCollectVo> selectNoNumList(Long id){
        return dmsUserCollectMapper.getPageList(id);
    }

    /**
     * 获取收藏，有库存 不分页
     * @param id
     * @return
     */
    public List<DmsUserCollectVo> selectNumList(Long id){
        return dmsUserCollectMapper.getAllPageList(id);
    }

    /**
     * 分页获取用户收藏有库存的记录详情
     * @param dmsUserCollect
     * @return
     */
    public PageHelper.Page<DmsUserCollectVo> selectForByNumList(DmsUserCollect dmsUserCollect){
        try {
            PageHelper.startPage(dmsUserCollect.getP(), dmsUserCollect.getS());
            dmsUserCollectMapper.getAllPageList(dmsUserCollect.getUserId());
            return PageHelper.endPage();
        } catch (Exception e) {
            PageHelper.endPage();
            throw new ServiceException("分页查询异常"+e.getMessage());
        }
    }

    /**
     * 删除根据ID列表
     */
    public void deleteCollectByInfo(Long userId, Long productId,String specUnionKey){
        dmsUserCollectMapper.deleteCollectByInfo(userId,productId,specUnionKey);
    }
}