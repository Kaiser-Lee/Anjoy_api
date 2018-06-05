package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsPostMapper;
import com.coracle.dms.po.DmsPost;
import com.coracle.dms.service.DmsPostService;
import com.coracle.yk.base.vo.TreeNodeVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import com.xiruo.medbid.components.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmsPostServiceImpl extends BaseServiceImpl<DmsPost> implements DmsPostService {
    private static final Logger logger = Logger.getLogger(DmsPostServiceImpl.class);

    @Autowired
    private DmsPostMapper dmsPostMapper;

    @Override
    public IMybatisDao<DmsPost> getBaseDao() {
        return dmsPostMapper;
    }

    /**
     * 新增/修改岗位
     * @param post
     */
    @Override
    public void insertOrUpdate(DmsPost post) {
        checkParam(post);

        boolean isInsert = post.getId() == null ? true : false;
        if (isInsert) {  //新增操作
            int count = dmsPostMapper.insert(post);
            if (count <= 0) {
                throw new ServiceException("新增岗位失败!");
            }
            /* 设置路径：路径在修改时不会改变，所以只在新增时做处理 */
            Long parentId = post.getParentId();
            if (parentId == null || parentId == 0) {  //顶层岗位
                post.setParentId(0L);
                post.setPath(post.getId() + ".");
            } else {  //添加子岗位
                DmsPost parentPost = dmsPostMapper.selectByPrimaryKey(parentId);
                String path = parentPost.getPath();
                post.setPath(path + post.getId() + ".");
            }
        } else {  //修改操作
            DmsPost entity = dmsPostMapper.selectByPrimaryKey(post);
            if (entity == null) {
                throw new ServiceException("无法获取id为" + post.getId() + "的岗位信息!");
            }
        }
        dmsPostMapper.updateByPrimaryKeySelective(post);  //保存修改
    }

    /**
     * 岗位列表
     * @param post
     * @return
     */
    @Override
    public PageHelper.Page<DmsPost> pageList(DmsPost post) {
        try {
            PageHelper.startPage(post.getP(), post.getS());
            post.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
            dmsPostMapper.selectByCondition(post);
        } catch (Exception e) {
            logger.error("岗位分页查询异常!", e);
            throw new ServiceException("岗位分页查询异常!");
        } finally {
            return PageHelper.endPage();
        }
    }

    /**
     * 获取岗位树形结构数据
     * @param id
     * @return
     */
    @Override
    public List<TreeNodeVo> tree(Long id) {
        if (id == null) {  //id为null时，默认查询整个岗位结构树
            id = 0L;
        }
        return dmsPostMapper.selectByParentId(id);
    }

    /**
     * 逻辑删除岗位
     * @param post
     */
    @Override
    public void remove(DmsPost post) {
        if (post == null || post.getId() == null) {
            throw new ServiceException("参数异常");
        }

        /* 如果岗位下存在子岗位，则不允许删除 */
        Integer childCount = dmsPostMapper.selectCountByParentId(post.getId());
        if (childCount > 0) {
            throw new ServiceException("该岗位下存在子岗位，不允许删除!");
        }

        dmsPostMapper.removeByPrimaryKey(post.getId());
    }

    /**
     * 参数检查
     * @param post
     */
    private void checkParam(DmsPost post) {
        if (post == null) {
            throw new ServiceException("参数异常");
        }
        if (StringUtils.isBlank(post.getName())) {
            throw new ServiceException("岗位名称不能为空");
        }
        if (StringUtils.isBlank(post.getCode())) {
            throw new ServiceException("岗位编码不能为空");
        }
        if (StringUtils.isBlank(post.getCode())) {
            throw new ServiceException("岗位编码不能为空");
        }
        if (post.getActive() == null) {
            throw new ServiceException("有效性不能为空");
        }

        /* 新增或修改时，检查岗位编码，不允许岗位编码重复 */
        DmsPost param = new DmsPost();
        param.setCode(post.getCode());
        param.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        List<DmsPost> postList = dmsPostMapper.selectByCondition(param);
        if (post.getId() == null && postList.size() > 0
                || post.getId() != null && postList.size() > 1) {
            throw new ServiceException("岗位编码重复，请重新填写");
        }
    }
}