package com.coracle.dms.dao.mybatis;

import com.coracle.dms.dto.DmsSellNumByCategoryDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/22.
 */
public interface DmsReportFormsMapper {
    DmsSellNumByCategoryDto getProductTopByCategory(@Param("storeId") Long storeId,@Param("categoryId") Long categoryId,@Param("startDate") String startDate);
    List<Map<String ,Object>> getTopByDateType(@Param("startDate") String startDate);
    List<DmsSellNumByCategoryDto> getTopByOldLibrary(Map<String, Object> map);
    DmsSellNumByCategoryDto getChannelTopByProduct(@Param("storeIds") String storeIds,@Param("channelId") Long channelId,@Param("categoryId") Long categoryId,@Param("startDate") String startDate);
    Double getSaleMoneyByOrder(@Param("startDate") String startDate,@Param("endDate") String endDate);
    Integer getSaleNumByOrder(@Param("startDate") String startDate,@Param("endDate") String endDate);
}
