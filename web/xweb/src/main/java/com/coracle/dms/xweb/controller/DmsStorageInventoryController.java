package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.po.DmsStorageInventory;
import com.coracle.dms.service.DmsStorageInventoryService;
import com.coracle.dms.vo.DmsStorageInventoryVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.xiruo.medbid.util.PathUtil;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import com.xiruo.medbid.util.DownExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.*;


@Controller
@RequestMapping(value = "/api/v1/dms/storageInventory")
@Api(description = "库存管理接口")
public class DmsStorageInventoryController extends BaseController {

	@Resource
	private DmsStorageInventoryService dmsStorageInventoryService;
	
	/**
     * 根据条件获取入库单列表
     */
    @ResponseBody
    @ApiOperation(value = "库存列表",response = DmsStorageInventoryVo.class)
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Object getList(@RequestBody DmsStorageInventoryVo inventoryVo){
    	PageHelper.Page<DmsStorageInventoryVo> pageList = dmsStorageInventoryService.findStorageInventoryPageList(inventoryVo);
        return toResult(pageList);
    }

    /**
     * app-按产品，按门店
     */
    @ResponseBody
    @ApiOperation(value = "app-库存列表（1按产品，2按门店）",response = DmsStorageInventory.class)
    @RequestMapping(value = "/appList",method = RequestMethod.POST)
    public Object getListByProductidSid(@ApiParam("app-库存列表（1按产品，2按门店）") @RequestBody DmsStorageInventoryVo paramVo){
        UserSession userSession = LoginManager.getUserSession();
        PageHelper.Page<Map<String,Object>> recordList = null;
        if("1".equals(paramVo.getListType())){
            recordList = dmsStorageInventoryService.findStorageInventoryListByProduct(paramVo,userSession);
        }else{
            recordList = dmsStorageInventoryService.findStorageInventoryListByStoreList(paramVo,userSession);
        }
        return toResult(recordList);
    }

    /**
     * app-按产品，按门店
     */
    @ResponseBody
    @ApiOperation(value = "app-库存列表（按门店---按门店id（渠道则为渠道id）获取库存列表，不区分产品规格）",response = DmsStorageInventory.class)
    @RequestMapping(value = "/getListByStore",method = RequestMethod.POST)
    public Object getListByStore(@RequestBody DmsStorageInventoryVo paramVo){
        UserSession userSession = LoginManager.getUserSession();
        PageHelper.Page<Map<String,Object>> recordList = null;
        recordList = dmsStorageInventoryService.findStorageInventoryListByStore(paramVo,userSession);
        return toResult(recordList);
    }
    
    @ResponseBody
    @ApiOperation(value = "增减库存", response = JsonResult.class)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsStorageInventoryVo paramVo){
        UserSession userSession = LoginManager.getUserSession();
        dmsStorageInventoryService.addOrSubtract(paramVo,userSession);
        return toResult();
    }

    @ResponseBody
    @ApiOperation(value = "调整库存", response = JsonResult.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody DmsStorageInventoryVo paramVo){
        UserSession userSession = LoginManager.getUserSession();
        dmsStorageInventoryService.adjustment(paramVo,userSession);
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "根据产品id，产品规格id 查询库存", response = JsonResult.class)
    @RequestMapping(value = "/findByProductId", method = RequestMethod.POST)
    public Object findByProductId(@RequestBody DmsStorageInventoryVo paramVo){
        Long storageNum = dmsStorageInventoryService.findByProductId(paramVo);
        return toResult(storageNum);
    }

    @ResponseBody
    @ApiOperation(value = "app-库存详情", response = DmsStorageInventoryVo.class)
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object selectDetail(@RequestBody DmsStorageInventoryVo paramVo){
        if (BlankUtil.isEmpty(paramVo.getProductId())){
            throw new ControllerException("请输入要获取产品的ID！");
        }
        UserSession userSession = LoginManager.getUserSession();
        PageHelper.Page<Map<String, Object>> pageList = dmsStorageInventoryService.getDetail(paramVo,userSession);
        return toResult(pageList);
    }

    /**
     * pc导出功能
     * 参数：List<Integer> ids （要导出的ids集合）,int type（要导出的类型  0 全部导出(按分页) 1选择性ids导出）
     */
    @ResponseBody
    @ApiOperation(value = "库存列表")
    @RequestMapping(value = "/exportInventory",method = RequestMethod.GET)
    public Object exportInventory(@ApiParam("要导出的类型")@RequestParam(value = "exPortType",required = false) Integer exPortType,
                                  @ApiParam("分页页码")@RequestParam(value = "p",required = false) Integer p,
                                  @ApiParam("每页条数")@RequestParam(value = "s",required = false) Integer s,
                                  @ApiParam("机构id")@RequestParam(value = "orgId",required = false) Long orgId,
                                  @ApiParam("机构类型1品牌商，2渠道")@RequestParam(value = "orgType",required = false) Long orgType,
                                  @ApiParam("要导出的ids集合")@RequestParam(value = "exPortIds",required = false) List<Integer> exPortIds
                                  ) {

        DmsStorageInventoryVo vo = new DmsStorageInventoryVo();
        vo.setP(p);
        vo.setS(s);
        vo.setRemoveFlag(0);
        vo.setExPortType(exPortType);
        vo.setOrgType(orgType);
        vo.setOrgId(orgId);


        Map<String, Object> result = new HashMap<String, Object>();
        try {

            PageHelper.Page<DmsStorageInventoryVo> pageList = null;
            if (vo.getExPortType() == 0) {
                pageList = dmsStorageInventoryService.findStorageInventoryPageList(vo);
            } else {
                //暂时不做
            }

            //导出出入库记录列表
            Map<String, List<String[]>> resultMap = new HashMap<>();
            String title = "库存列表";
            String excelFilePath = PathUtil.getRootPath() + DmsModuleEnums.BASEADDRESS + DmsModuleEnums.excelAddress;
            //String excelFilePath = "E://dms//web//xweb//target//classes"+ DmsModuleEnums.BASEADDRESS+DmsModuleEnums.excelAddress;
            List<String[]> arrayList = new ArrayList<String[]>();
            int i = 0;
            arrayList.add(new String[]{"序号", "产品编码", "产品名称", "规格", "产品类别", "单位", "仓库", "货位", "库存数量", "可用量"});
            for (DmsStorageInventoryVo dmsStorageInventoryVo : pageList.getResult()) {

                String productCode = dmsStorageInventoryVo.getProductCode();
                String productName = dmsStorageInventoryVo.getProductName();
                String specName = dmsStorageInventoryVo.getSpecName();
                String categoryText = dmsStorageInventoryVo.getCategoryText();
                String unitText = dmsStorageInventoryVo.getUnitText();
                String storageName = dmsStorageInventoryVo.getStorageName();
                String localCode = dmsStorageInventoryVo.getLocalCode();
                Integer storageNum = dmsStorageInventoryVo.getStorageNum();
                Integer useNum = dmsStorageInventoryVo.getUseNum();
                i++;
                arrayList.add(new String[]{String.valueOf(i), productCode, productName, specName, categoryText, unitText, storageName, localCode, storageNum+"",useNum+""});
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

    @ResponseBody
    @ApiOperation(value = "根据条件返回可用量", response = DmsStorageInventoryVo.class)
    @RequestMapping(value = "/count", method = RequestMethod.POST)
    public Object count(@RequestBody DmsStorageInventory param){
        DmsStorageInventoryVo storageInventoryVo = dmsStorageInventoryService.selectVoCondition(param);
        if (param == null) {
            throw new ServiceException("库存量查询异常");
        }
        return toResult(storageInventoryVo.getUseNumTotal());
    }
}
