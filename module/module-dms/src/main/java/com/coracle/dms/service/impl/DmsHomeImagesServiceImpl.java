package com.coracle.dms.service.impl;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.dao.mybatis.DmsHomeImagesMapper;
import com.coracle.dms.po.DmsHomeImages;
import com.coracle.dms.service.DmsHomeImagesService;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DmsHomeImagesServiceImpl extends BaseServiceImpl<DmsHomeImages> implements DmsHomeImagesService {
    private static final Logger logger = Logger.getLogger(DmsHomeImagesServiceImpl.class);

    @Autowired
    private DmsHomeImagesMapper dmsHomeImagesMapper;

    @Override
    public IMybatisDao<DmsHomeImages> getBaseDao() {
        return dmsHomeImagesMapper;
    }

    /**
     * 保存首页轮播图片集合
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(List<DmsHomeImages> indexImages, long userId) {
        if(indexImages!=null&&indexImages.size()>0){
            long i = 1;
            for(DmsHomeImages images:indexImages){
                images.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
                images.setCreatedBy(userId);
                images.setCreatedDate(new Date());
                images.setLastUpdatedBy(userId);
                images.setLastUpdatedDate(new Date());
                images.setImageType(DmsModuleEnums.HOME_IMAGES_TYPE.BANNER.getType());
                images.setSort(i);
                i++;
            }
            dmsHomeImagesMapper.batchDelete(DmsModuleEnums.HOME_IMAGES_TYPE.BANNER.getType());//先删除一遍banner图片
            dmsHomeImagesMapper.batchInsert(indexImages);//批量新增一遍
        }

    }

    /**
     * 获取首页轮播图片集合
     */
    public List<Map<String,Object>> getList(DmsHomeImages image) {
        return dmsHomeImagesMapper.findAll(image);
    }

}