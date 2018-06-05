package com.coracle.dms.xweb.controller;

import com.coracle.dms.constant.DmsRoleCodeConstants;
import com.coracle.dms.service.DmsMessageService;
import com.coracle.dms.service.DmsShoppingCartService;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.yk.base.vo.JsonResult;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/api/v1/dms/common")
@Api(description = "公共接口")
public class DmsCommonController extends BaseController {

    @Autowired
    private DmsMessageService dmsMessageService;

    @Autowired
    private DmsShoppingCartService dmsShoppingCartService;

    @ResponseBody
    @ApiOperation(value = "获取各种角标值",response = JsonResult.class)
    @RequestMapping(value = "/cornerMark", method = RequestMethod.GET)
    public Object cornerMark(){
        Map<String, Object> data = Maps.newHashMap();
        Long userId = LoginManager.getCurrentUserId();
        String roleCode = LoginManager.getUserSession().getRoleCode();
        boolean isChannelContact = roleCode.equals(DmsRoleCodeConstants.CO);
        boolean isPC = roleCode.equals(DmsRoleCodeConstants.ADMIN) || roleCode.equals(DmsRoleCodeConstants.BM);

        //未读消息数量
        Long messageCount = 0L;
        if (isPC) {
            messageCount = dmsMessageService.getCountForPC();
        } else {
            messageCount = dmsMessageService.getCount(userId);
        }
        data.put("message", messageCount);

        //购物车内产品数量
        Integer shoppingCartCount = 0;
        if (isChannelContact) {
            shoppingCartCount = dmsShoppingCartService.count(userId);
        }
        data.put("shoppingCart", shoppingCartCount);
        return toResult(data);
    }
}
