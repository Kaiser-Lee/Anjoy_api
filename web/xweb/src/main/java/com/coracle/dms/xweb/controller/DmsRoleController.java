package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsModuleEnums;
import com.coracle.dms.po.DmsRole;
import com.coracle.dms.service.DmsRoleService;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Api(description = "角色管理接口")
@RequestMapping("/api/v1/dms/role")
public class DmsRoleController extends BaseController {
    private static final Logger logger = Logger.getLogger(DmsRoleController.class);

    @Autowired
    private DmsRoleService dmsRoleService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "角色列表", response = JsonResult.class)
    public Object pageList(DmsRole role) {
        role.setRemoveFlag(DmsModuleEnums.REMOVE_FLAG_STATUS.NOREMOVE.getType());
        List<DmsRole> roleList = dmsRoleService.selectByCondition(role);
        return toResult(roleList);
    }
}