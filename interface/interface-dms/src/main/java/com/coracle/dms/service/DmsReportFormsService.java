package com.coracle.dms.service;

import com.coracle.dms.dto.DmsSellNumByCategoryDto;
import com.coracle.yk.xframework.po.UserSession;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/21.
 */
public interface DmsReportFormsService {
    /**
     * 获取app端产品分类销量top5
     * @param userSession
     * @param type
     * @return
     */
    Map<String,Object> getTop5ForProductCategoryByAPP(UserSession userSession, Integer type);

    /**
     * 获取app端库龄top5
     * @param userSession
     * @return
     */
    List<DmsSellNumByCategoryDto> getTopByOldLibrary(UserSession userSession);

    /**
     * 根据类型查询PC端top5
     * @param type
     * @return
     */
    List<Map<String ,Object>> getTop5ByPC(Integer type);

    /**
     * PC首页订单信息报表
     * @param searchType
     * @param dateType
     * @return
     */
    List<Map<String, Object>> getOrderTopByPC(Integer searchType,Integer dateType);
}
