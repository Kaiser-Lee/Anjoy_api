package com.coracle.dms.xweb.controller;

import com.coracle.dms.service.DmsUserRoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("dmsUserRole")
public class DmsUserRoleController extends BaseController {
    private static final Logger logger = Logger.getLogger(DmsUserRoleController.class);

    @Autowired
    private DmsUserRoleService dmsUserRoleService;
}