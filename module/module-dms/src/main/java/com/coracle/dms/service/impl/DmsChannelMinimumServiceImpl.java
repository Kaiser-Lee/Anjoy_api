package com.coracle.dms.service.impl;


import com.coracle.dms.dao.mybatis.DmsChannelMapper;
import com.coracle.dms.dao.mybatis.DmsChannelMinimumMapper;
import com.coracle.dms.dao.mybatis.DmsProductCategoryMapper;
import com.coracle.dms.dao.mybatis.DmsProductMapper;
import com.coracle.dms.po.DmsChannelMinimum;

import com.coracle.dms.service.DmsChannelMinimumService;
import com.coracle.dms.vo.DmsChannelVo;
import com.coracle.dms.vo.DmsProductVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xservice.base.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DmsChannelMinimumServiceImpl extends BaseServiceImpl<DmsChannelMinimum> implements DmsChannelMinimumService {
    private static final Logger logger = Logger.getLogger(DmsChannelMinimumServiceImpl.class);

    @Autowired
    private DmsChannelMinimumMapper dmsChannelMinimumMapper;
    @Autowired
    private DmsProductMapper dmsProductMapper;
    @Autowired
    private DmsChannelMapper dmsChannelMapper;
    @Autowired
    private DmsProductCategoryMapper dmsProductCategoryMapper;
    @Override
    public IMybatisDao<DmsChannelMinimum> getBaseDao() { return  dmsChannelMinimumMapper;}

    @Override
   public DmsChannelVo findProductByID(Long id) {

        List<DmsProductVo> productList = dmsProductMapper.findProductById(id);
        DmsChannelVo dmsChannelVo = new DmsChannelVo();
        //设置起订量产品List
       // dmsChannelVo.setDmsProductVoForMinList(productList);
        //设置起订量
        dmsChannelVo.setMinOrderQuantity(dmsChannelMapper.selectDetailByPrimaryKey(id).getMinOrderQuantity());

        dmsChannelVo.setTotal(productList.size());
      return dmsChannelVo;
   }


    /**
     * 根据条件查询渠道下的商品列表
     */

       public PageHelper.Page<DmsProductVo> findProductForMinimum(DmsProductVo product){

        try {
            if(product.getS()==null){
                product.setS(10);
            }

            PageHelper.startPage(product.getP(), product.getS());

            dmsProductMapper.findProductForMinimum(product);
        } catch (Exception e) {
            System.out.println("分页查询异常"+e.getMessage());
            throw new ServiceException("分页查询异常");
        } finally {
            return PageHelper.endPage();
        }
    }
    /**
     * 根据条件查询渠道下的商品列表 new
     */

        public DmsChannelVo findProductForMinimum2(DmsChannelVo dmsChannelVo){

            DmsProductVo product = new DmsProductVo();

            Long categoryId = dmsChannelVo.getCategoryId();
            Long channelId = dmsChannelVo.getId();
            Long brandId = dmsChannelVo.getBrandId();

            product.setChannelId(channelId);
            product.setP(dmsChannelVo.getP());
            product.setS(dmsChannelVo.getS());
            product.setBrandId(brandId);
            product.setCategoryId(categoryId);
            //判断当前渠道是是否在白名单内
            Integer existProductWhiteList = dmsProductMapper.findCountChannelProductWhite(channelId);
            product.setExistProductWhiteList(existProductWhiteList);


            PageHelper.Page<DmsProductVo> ProductVoPageList =  findProductForMinimum(product);
            dmsChannelVo.setDmsProductPage(ProductVoPageList);

            //给渠道设置起订量
            if(dmsChannelMapper.selectDetailByPrimaryKey(channelId)!=null){
                dmsChannelVo.setMinOrderQuantity(dmsChannelMapper.selectDetailByPrimaryKey(channelId).getMinOrderQuantity());
            }
            //设置pathIds
            if(dmsChannelVo.getCategoryId()!=null&&dmsChannelVo.getCategoryId()!=0){
                String pathIds = selectPathId(categoryId);
                dmsChannelVo.setPathIds(pathIds);

            }



            return dmsChannelVo;
        }

    /**
     *创建起订量 整板下单量
     */
    public void insert(DmsChannelVo dmsChannelVo, UserSession userSession){
        //给渠道设置起订量
        Long minOrderQuantity = dmsChannelVo.getMinOrderQuantity();
        Long id = dmsChannelVo.getId();
        dmsChannelMapper.updateMinOrderQuantity(minOrderQuantity,id);


        List<DmsChannelMinimum> dmsChannelMinimumList = dmsChannelVo.getDmsChannelMinimumList();
        List<DmsChannelMinimum> newMinimumList = new ArrayList<>();

        //设置渠道 id

        if(dmsChannelMinimumList!=null && !dmsChannelMinimumList.isEmpty()){
            for(DmsChannelMinimum dmsChannelMinimum:dmsChannelMinimumList){
                dmsChannelMinimum.setChannelId(id);
                dmsChannelMinimum.setCreateDate(new Date());
                dmsChannelMinimum.setLastUpdateDate(new Date());
                dmsChannelMinimum.setCreateBy(userSession.getId());
                dmsChannelMinimum.setLastUpdateBy(userSession.getId());
                dmsChannelMinimum.setRemoveFlag(0);
                newMinimumList.add(dmsChannelMinimum);
            }

        }

        if(newMinimumList!=null &&! newMinimumList.isEmpty()){
            //批量删除
            dmsChannelMinimumMapper.batchDelete2(newMinimumList);
            //批量插入
            dmsChannelMinimumMapper.batchInsert2(newMinimumList);
        }




    }




    public void updateMinOrderQuantity(Long minOrderQuantity,Long id){


            dmsChannelMapper.updateMinOrderQuantity(minOrderQuantity,id);
    }

    public String selectPathId(Long categoryId){

            String PathIds =dmsProductCategoryMapper.selectByPrimaryKey(categoryId).getPath();
            return PathIds;


    }

    @Override
    public void batchDelete(List<DmsChannelMinimum> list) {

        //批量删除
        dmsChannelMinimumMapper.batchDelete2(list);


    }

    @Override
    public void batchInsert(List<DmsChannelMinimum> list) {


        //批量插入
        dmsChannelMinimumMapper.batchInsert2(list);

    }


}