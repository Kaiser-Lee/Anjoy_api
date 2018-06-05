/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.coracle.dms.po.DmsContactInfo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

public interface DmsContactInfoMapper extends IMybatisDao<DmsContactInfo> {
	
	/**
     * 批量新增
     * @param list
     */
    void batchInsert(List<DmsContactInfo> list);
    
    List<DmsContactInfo> getContactInfoByChannel(Long relatedTableId);
    
    void deleteByRelated(DmsContactInfo entity);

    List<Map<String,Object>> getDmsSellerContactList(Long id);
}