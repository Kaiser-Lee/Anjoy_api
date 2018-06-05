package com.coracle.dms.xweb.controller;

import com.alibaba.fastjson.JSON;
import com.coracle.dms.service.DmsChannelProductAnjoyWhiteListService;
import com.coracle.dms.vo.DmdChannelProductAnjoyVo;
import com.google.common.collect.Maps;
import com.xiruo.medbid.util.ImportExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @title：
 * @describe：
 * @copyright：Copyright (c) 1997-2017 coracle, Inc.
 * @company：圆舟科技
 * @author：taok
 * @date：2018-03-04 15:26
 * @version：1.0
 */
@Controller
@RequestMapping(value = "/api/v1/dms/anjoyProductWhiteList")
@Api(description = "经销商-产品关系接口")
public class DmsAnjoyProductWhiteListController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(DmsAnjoyProductWhiteListController.class);
    @Autowired
    private DmsChannelProductAnjoyWhiteListService dmsProductWhiteListService;

    @ResponseBody
    @ApiOperation(value = "安井渠道-产品白名单关系同步")
    @RequestMapping(value = "/anjoy-syn", method = RequestMethod.GET)
    public Object anjoySyn() {
        dmsProductWhiteListService.anjoySyn();
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "导入安井经销商-产品关系数据")
    @RequestMapping(value = "/importAnjoyExcel", method = RequestMethod.POST)
    public Object importAnjoyExcel(@RequestParam("file") MultipartFile file,
                                   HttpServletRequest request,
                                   HttpServletResponse response
    ) {
        Map<String, Object> map = Maps.newHashMap();
        List<DmdChannelProductAnjoyVo> dataList = new ArrayList<>();
        try {
            ImportExcel excelData = new ImportExcel(file, 0, 0);
            for (int i = excelData.getDataRowNum(); i <= excelData.getLastDataRowNum(); i++) {
                Row row = excelData.getRow(i);
                //安井-渠道编码
                String anjoyChannelCode = excelData.getCellValue2(row, 0);
                //安井-渠道名称
                String anjoyChannelName = null;//excelData.getCellValue2(row, 4);
                //安井-商品编码
                String anjoyProductCode = excelData.getCellValue2(row, 1);
                //安井-商品名称
                String anjoyProductName = null;//excelData.getCellValue2(row, 8);

                DmdChannelProductAnjoyVo anjoyVo = new DmdChannelProductAnjoyVo();
                anjoyVo.setAnjoyChannelCode(anjoyChannelCode);
                anjoyVo.setAnjoyChannelName(anjoyChannelName);
                anjoyVo.setAnjoyProductCode(anjoyProductCode);
                anjoyVo.setAnjoyProductName(anjoyProductName);
                dataList.add(anjoyVo);
            }
            logger.info("渠道-产品关系数据：总共 {} 条, \n {}", dataList.size(), JSON.toJSONString(dataList));
            map = dmsProductWhiteListService.importAnjoyExcel(dataList);
        } catch (Exception e) {
            logger.error("渠道-产品关系数据导入出错：", e);
            map.put("code", 500);
            map.put("msg", "渠道-产品关系数据失败");
        }
        return toResult(map);
    }


}
