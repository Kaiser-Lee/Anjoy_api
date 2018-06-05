package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.service.DmsStorageBillService;
import com.coracle.dms.vo.DmsStorageBillVo;
import com.coracle.dms.vo.DmsStorageOutInRecordVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
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
@RequestMapping(value = "/api/v1/dms/storageBill")
@Api(description = "入库单管理接口")
public class DmsStorageBillController extends BaseController {

	@Resource
	private DmsStorageBillService dmsStorageBillService;
	
	/**
     * 根据条件获取入库单列表
     */
    @ResponseBody
    @ApiOperation(value = "入库单列表",response = DmsStorageBillVo.class)
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Object getList(@RequestBody DmsStorageBillVo billVo){
    	PageHelper.Page<DmsStorageBillVo> pageList = dmsStorageBillService.selectForListPage(billVo);
        return toResult(pageList);
    }
    
    @ResponseBody
    @ApiOperation(value = "新增入库单", response = JsonResult.class)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsStorageBillVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        UserSession userSession= LoginManager.getUserSession();
        dmsStorageBillService.create(paramVo,userSession);
        return toResult(null);
    }
    
    @ResponseBody
    @ApiOperation(value = "修改入库单", response = JsonResult.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody DmsStorageBillVo paramVo){
        PoDefaultValueGenerator.putDefaultValue(paramVo);
        dmsStorageBillService.update(paramVo);
        return toResult(null);
    }

    /**
     * pc导出功能
     * 参数：List<Integer> ids （要导出的ids集合）,int type（要导出的类型  0 全部导出(按分页) 1选择性ids导出）
     */
    @ResponseBody
    @ApiOperation(value = "入库单列表")
    @RequestMapping(value = "/exportBill",method = RequestMethod.GET)
    public Object exportBill(@ApiParam("要导出的类型")@RequestParam(value = "exPortType",required = false) Integer exPortType,
                             @ApiParam("分页页码")@RequestParam(value = "p",required = false) Integer p,
                             @ApiParam("每页条数")@RequestParam(value = "s",required = false) Integer s,
                             @ApiParam("机构id")@RequestParam(value = "orgId",required = false) Long orgId,
                             @ApiParam("机构类型1品牌商，2渠道")@RequestParam(value = "orgType",required = false) Long orgType,
                             @ApiParam("开始时间")@RequestParam(value = "startDate",required = false) String startDate,
                             @ApiParam("结束时间")@RequestParam(value = "endDate",required = false) String endDate,
                             @ApiParam("要导出的ids集合")@RequestParam(value = "exPortIds",required = false) List<Integer> exPortIds) {

        DmsStorageBillVo vo = new DmsStorageBillVo();
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

            PageHelper.Page<DmsStorageBillVo> pageList = null;
            List<DmsStorageOutInRecordVo> list = null;
            if (vo.getExPortType() == 0) {
                pageList = dmsStorageBillService.selectForListPage(vo);
            } else {
                //暂时不做
            }

            //导出出入库记录列表
            Map<String, List<String[]>> resultMap = new HashMap<>();
            String title = "入库单列表";
            String excelFilePath = PathUtil.getRootPath() + DmsModuleEnums.BASEADDRESS + DmsModuleEnums.excelAddress;
            //String excelFilePath = "E://dms//web//xweb//target//classes"+DmsModuleEnums.BASEADDRESS+DmsModuleEnums.excelAddress;
            List<String[]> arrayList = new ArrayList<String[]>();
            int i = 0;
            arrayList.add(new String[]{"序号", "操作时间", "出入库类型", "产品编码", "产品名称", "规格", "产品类别", "单位", "仓库", "货位", "数量"});
            for (DmsStorageBillVo dmsStorageBillVo : pageList.getResult()) {
                String createTime = "";
                if (dmsStorageBillVo.getCreatedDate() != null) {
                    Date date = dmsStorageBillVo.getCreatedDate();
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    createTime = sdf.format(date);
                }
                String assignWayText = dmsStorageBillVo.getAssignWayText();
                String productCode = dmsStorageBillVo.getProductCode();
                String productName = dmsStorageBillVo.getProductName();
                String specName = dmsStorageBillVo.getSpecName();
                String categoryText = dmsStorageBillVo.getCategoryText();
                String unitText = dmsStorageBillVo.getUnitText();
                String storageName = dmsStorageBillVo.getStorageName();
                String localCode = dmsStorageBillVo.getLocalCode();
                String storageNum = dmsStorageBillVo.getStorageNum();
                i++;
                arrayList.add(new String[]{String.valueOf(i), createTime, assignWayText, productCode, productName, specName, categoryText, unitText, storageName, localCode, storageNum});
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
