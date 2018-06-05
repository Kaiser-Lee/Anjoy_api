/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsAttachmentRelation;
import com.coracle.dms.po.DmsCommonAttachFile;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DmsCommonAttachFileMapper extends IMybatisDao<DmsCommonAttachFile> {

    /**
     * 逻辑删除文件
     * @param ids
     * @return
     */
    int updateByIdsSelectiveByDelete(@Param("ids")List<Long> ids);

    /**
     * 插入附件并返回主键Id
     * @param po
     * @return
     */
    long insertByRePrimaryKey(DmsCommonAttachFile po);

    /**
     * 根据文件关联关系表参数获取文件列表
     * @param param
     * @return
     */
    List<DmsCommonAttachFile> selectByParam(Map<String, Object> param);
}