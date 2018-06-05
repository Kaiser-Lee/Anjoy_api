/**
 * create by Administrator
 * @date 2017-08
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.DmsMessage;
import com.coracle.dms.vo.DmsMessageVo;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DmsMessageMapper extends IMybatisDao<DmsMessage> {

    /**
     * 获取我的消息记录
     * @param message
     * @return
     */
    List<DmsMessageVo> getPageList(DmsMessageVo message);

    /**
     * 获取未读记录数
     * @param staffId
     * @return
     */
    Long getCount(Long staffId);

    /**
     * 获取未读记录数
     *
     * @return
     */
    Long getCountForPC();

    /**
     * 设置消息已读
     * @param ids
     */
    void read(@Param("ids") List<Long> ids);

    /**
     * 批量新增消息
     * @param dmsMessageList
     */
    void batchInsert(List<DmsMessage> dmsMessageList);
}