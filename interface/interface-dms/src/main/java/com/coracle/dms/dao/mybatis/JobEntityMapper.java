/**
 * create by lenovo
 * @date 2018-03
 */
package com.coracle.dms.dao.mybatis;

import com.coracle.dms.po.JobEntity;
import com.coracle.yk.xdatabase.base.mybatis.intf.IMybatisDao;

public interface JobEntityMapper extends IMybatisDao<JobEntity> {

    JobEntity selectOneByCondition(JobEntity jobEntity);

}