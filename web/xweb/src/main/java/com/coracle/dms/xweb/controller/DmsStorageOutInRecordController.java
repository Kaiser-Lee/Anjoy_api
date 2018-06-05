package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.po.DmsStorageOutInRecord;
import com.coracle.dms.service.DmsStorageOutInRecordService;
import com.coracle.dms.vo.DmsStorageOutInRecordVo;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.xiruo.medbid.util.DownExcel;
import com.xiruo.medbid.util.PathUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/api/v1/dms/storageOutInRecord")
@Api(description = "出入库记录管理接口")
public class DmsStorageOutInRecordController extends BaseController {

    @Resource
    private DmsStorageOutInRecordService dmsStorageOutInRecordService;

    /**
     * 根据条件获取入库单列表
     */
    @ResponseBody
    @ApiOperation(value = "出入库记录列表", response = DmsStorageOutInRecord.class)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object getList(@RequestBody DmsStorageOutInRecordVo outInRecordVo) {
        PageHelper.Page<DmsStorageOutInRecordVo> pageList = dmsStorageOutInRecordService.findStorageOutInRecordPageList(outInRecordVo);
        return toResult(pageList);
    }

    /**
     * app-按产品，按门店
     */
    @ResponseBody
    @ApiOperation(value = "出入库记录列表", response = DmsStorageOutInRecord.class)
    @RequestMapping(value = "/appList", method = RequestMethod.POST)
    public Object getListByPidSid(@RequestBody DmsStorageOutInRecordVo outInRecordVo) {
        PageHelper.Page<DmsStorageOutInRecordVo> pageList = dmsStorageOutInRecordService.findStorageOutInRecordList(outInRecordVo);
        return toResult(pageList);
    }

    /**
     * pc导出功能
     * 参数：List<Integer> ids （要导出的ids集合）,int type（要导出的类型  0 全部导出(按分页) 1选择性ids导出）
     */
    @ResponseBody
    @ApiOperation(value = "出入库记录列表")
    @RequestMapping(value = "/exportOutIn",method = RequestMethod.GET)
    public Object exportOutIn(@ApiParam("要导出的类型")@RequestParam(value = "exPortType",required = false) Integer exPortType,
                              @ApiParam("分页页码")@RequestParam(value = "p",required = false) Integer p,
                              @ApiParam("每页条数")@RequestParam(value = "s",required = false) Integer s,
                              @ApiParam("机构id")@RequestParam(value = "orgId",required = false) Long orgId,
                              @ApiParam("机构类型1品牌商，2渠道")@RequestParam(value = "orgType",required = false) Long orgType,
                              @ApiParam("开始时间")@RequestParam(value = "startDate",required = false) String startDate,
                              @ApiParam("结束时间")@RequestParam(value = "endDate",required = false) String endDate,
                              @ApiParam("要导出的ids集合")@RequestParam(value = "exPortIds",required = false) List<Integer> exPortIds) {

        DmsStorageOutInRecordVo vo = new DmsStorageOutInRecordVo();
        vo.setP(p);
        vo.setS(s);
        vo.setRemoveFlag(0);
        vo.setExPortType(exPortType);
        vo.setOrgType(orgType);
        vo.setOrgId(orgId);
        vo.setStartDate(startDate);
        vo.setEndDate(endDate);
        Map<String, Object> result = new HashMap<String, Object>();
        try {

            PageHelper.Page<DmsStorageOutInRecordVo> pageList = null;
            List<DmsStorageOutInRecordVo> list = null;
            if (vo.getExPortType()== 0) {
                pageList = dmsStorageOutInRecordService.findStorageOutInRecordPageList(vo);
            } else {
                //暂时不做
                //list = dmsStorageOutInRecordService.findStorageOutInRecordByIds(outInRecordVo.getExPortIds());
            }

            //导出出入库记录列表
            Map<String, List<String[]>> resultMap = new HashMap<>();
            String title = "出入库记录列表";
            String excelFilePath = PathUtil.getRootPath() + DmsModuleEnums.BASEADDRESS + DmsModuleEnums.excelAddress;
            //String excelFilePath = "E://dms//web//xweb//target//classes"+DmsModuleEnums.BASEADDRESS+DmsModuleEnums.excelAddress;
            List<String[]> arrayList = new ArrayList<String[]>();
            int i = 0;
            arrayList.add(new String[]{"序号", "操作时间", "出入库类型", "产品编码", "产品名称", "规格", "产品类别", "单位", "仓库", "货位", "数量"});
            for (DmsStorageOutInRecordVo dmsStorageOutInRecordVo : pageList.getResult()) {
                String createTime = "";
                if (dmsStorageOutInRecordVo.getCreatedDate() != null) {
                    Date date = dmsStorageOutInRecordVo.getCreatedDate();
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    createTime = sdf.format(date);
                }
                String assignWayText = dmsStorageOutInRecordVo.getAssignWayText();
                String productCode = dmsStorageOutInRecordVo.getProductCode();
                String productName = dmsStorageOutInRecordVo.getProductName();
                String specName = dmsStorageOutInRecordVo.getSpecName();
                String categoryText = dmsStorageOutInRecordVo.getCategoryText();
                String unitText = dmsStorageOutInRecordVo.getUnitText();
                String storageName = dmsStorageOutInRecordVo.getStorageName();
                String localCode = dmsStorageOutInRecordVo.getLocalCode();
                Long num = dmsStorageOutInRecordVo.getNum();
                i++;
                arrayList.add(new String[]{String.valueOf(i), createTime, assignWayText, productCode, productName, specName, categoryText, unitText, storageName, localCode, num + ""});
            }
            resultMap.put(title, arrayList);
            DownExcel downExcel = DownExcel.getInstall();
            downExcel.downLoadFile(response, downExcel.exportXlsExcel(resultMap, excelFilePath, String.valueOf(System.currentTimeMillis())), title, true);
            result.put("success", true);
            result.put("datas", resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
