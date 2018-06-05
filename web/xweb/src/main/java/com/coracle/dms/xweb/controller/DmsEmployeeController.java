package com.coracle.dms.xweb.controller;

import com.coracle.dms.po.DmsEmployee;
import com.coracle.dms.service.DmsEmployeeService;
import com.coracle.dms.vo.DmsEmployeeVo;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.po.UserSession;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(description = "员工管理接口")
@RequestMapping("/api/v1/dms/employee")
public class DmsEmployeeController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(DmsEmployeeController.class);

    @Autowired
    private DmsEmployeeService dmsEmployeeService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "员工列表", response = JsonResult.class)
    public Object pageList(@RequestBody DmsEmployeeVo employeeVo) {
        Long time1 = System.currentTimeMillis();
        employeeVo.setOrderField(" e.id ");
        employeeVo.setOrderString(" desc ");
        PageHelper.Page<DmsEmployeeVo> pageList = dmsEmployeeService.pageList(employeeVo);
        Long time2 = System.currentTimeMillis();
        logger.info("**************员工列表:{}*************",(time2-time1)+"ms");
        return toResult(pageList);
    }

    @ResponseBody
    @ApiOperation(value = "新增员工", response = JsonResult.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@RequestBody DmsEmployeeVo employeeVo) {
        PoDefaultValueGenerator.putDefaultValue(employeeVo);
        dmsEmployeeService.insertOrUpdate(employeeVo, LoginManager.getUserSession());
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "修改员工", response = JsonResult.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@RequestBody DmsEmployeeVo employeeVo) {
        PoDefaultValueGenerator.putDefaultValue(employeeVo);
        dmsEmployeeService.insertOrUpdate(employeeVo, LoginManager.getUserSession());
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "员工详情", response = DmsEmployeeVo.class)
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(@ApiParam("员工id") @RequestParam Long id) {
        DmsEmployeeVo employeeVo = dmsEmployeeService.detail(id);
        return toResult(employeeVo);
    }

    @ResponseBody
    @ApiOperation(value = "删除员工", response = JsonResult.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestBody DmsEmployee employee) {
        dmsEmployeeService.batchRemove(employee.getIds());
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "获取子员工列表", response = DmsEmployee.class)
    @RequestMapping(value = "/childList", method = RequestMethod.GET)
    public Object childList(@ApiParam("父员工id") @RequestParam Long id) {
        return toResult(null);
    }

    @ResponseBody
    @ApiOperation(value = "安井组织同步", response = DmsEmployee.class)
    @RequestMapping(value = "/anjoy-syn", method = RequestMethod.GET)
    public Object anjoySyn() {
        dmsEmployeeService.anjoySyn(LoginManager.getUserSession());
        return toResult();
    }
}