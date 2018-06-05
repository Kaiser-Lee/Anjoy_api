package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.po.DmsStorageTransportation;
import com.coracle.dms.service.DmsStorageTransportationService;
import com.coracle.dms.vo.DmsStorageTransportationVo;
import com.xiruo.medbid.util.PathUtil;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.xiruo.medbid.util.DownExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/api/v1/dms/transportation")
@Api(description = "在途库存管理接口")
public class DmsStorageTransportationController extends BaseController {

    @Autowired
    private DmsStorageTransportationService dmsStorageTransportationService;

    /**
     * 根据条件获取在途库存列表
     */
    @ResponseBody
    @ApiOperation(value = "在途库存列表",response = DmsStorageTransportation.class)
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Object getList(@RequestBody DmsStorageTransportationVo transportationVo){
        PageHelper.Page<DmsStorageTransportationVo> pageList = dmsStorageTransportationService.selectForListPage(transportationVo);
        return toResult(pageList);
    }

    /**
     * pc导出功能
     * 参数：List<Integer> ids （要导出的ids集合）,int type（要导出的类型  0 全部导出(按分页) 1选择性ids导出）
     */
    @ResponseBody
    @ApiOperation(value = "在途库存列表")
    @RequestMapping(value = "/exportTransport",method = RequestMethod.GET)
    public Object exportTransport(@ApiParam("要导出的类型")@RequestParam(value = "exPortType",required = false) Integer exPortType,
                                  @ApiParam("分页页码")@RequestParam(value = "p",required = false) Integer p,
                                  @ApiParam("每页条数")@RequestParam(value = "s",required = false) Integer s,
                                  @ApiParam("机构id")@RequestParam(value = "orgId",required = false) Long orgId,
                                  @ApiParam("机构类型1品牌商，2渠道")@RequestParam(value = "orgType",required = false) Long orgType,
                                  @ApiParam("要导出的ids集合")@RequestParam(value = "exPortIds",required = false) List<Integer> exPortIds) {

        DmsStorageTransportationVo vo = new DmsStorageTransportationVo();
        vo.setP(p);
        vo.setS(s);
        vo.setRemoveFlag(0);
        vo.setExPortType(exPortType);
        vo.setOrgType(orgType);
        vo.setOrgId(orgId);

        Map<String, Object> result = new HashMap<String, Object>();
        try {

            PageHelper.Page<DmsStorageTransportationVo> pageList = null;
            if (vo.getExPortType() == 0) {
                pageList = dmsStorageTransportationService.selectForListPage(vo);
            } else {
                //暂时不做
            }

            //导出出入库记录列表
            Map<String, List<String[]>> resultMap = new HashMap<>();
            String title = "在途库存列表";
            String excelFilePath = PathUtil.getRootPath() + DmsModuleEnums.BASEADDRESS + DmsModuleEnums.excelAddress;
            //String excelFilePath = "E://dms//web//xweb//target//classes"+ DmsModuleEnums.BASEADDRESS+DmsModuleEnums.excelAddress;
            List<String[]> arrayList = new ArrayList<String[]>();
            int i = 0;
            arrayList.add(new String[]{"序号", "产品编码", "产品名称", "规格", "产品类别", "单位", "数量", "发出仓库", "发出货位", "发出时间", "相关单据"});
            for (DmsStorageTransportationVo dmsStorageTransportationVo : pageList.getResult()) {
                String productCode = dmsStorageTransportationVo.getProductCode();
                String productName = dmsStorageTransportationVo.getProductName();
                String specName = dmsStorageTransportationVo.getSpecName();
                String categoryText = dmsStorageTransportationVo.getCategoryText();
                String unitText = dmsStorageTransportationVo.getUnitText();
                Integer transportationNum = dmsStorageTransportationVo.getTransportationNum();
                String storageName = dmsStorageTransportationVo.getStorageName();
                String localCode = dmsStorageTransportationVo.getLocalCode();
                String sendTime = "";
                if (dmsStorageTransportationVo.getSendTime() != null) {
                    Date date = dmsStorageTransportationVo.getSendTime();
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    sendTime = sdf.format(date);
                }
                String sendBill = dmsStorageTransportationVo.getSendBill();
                i++;
                arrayList.add(new String[]{String.valueOf(i), productCode, productName, specName, categoryText, unitText, transportationNum+"", storageName, localCode+"",sendTime,sendBill});
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