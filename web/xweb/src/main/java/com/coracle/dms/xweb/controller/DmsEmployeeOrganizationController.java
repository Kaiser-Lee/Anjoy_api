package com.coracle.dms.xweb.controller;

import com.coracle.dms.service.DmsEmployeeOrganizationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("dmsEmployeeOrganization")
public class DmsEmployeeOrganizationController extends BaseController {
    private static final Logger logger = Logger.getLogger(DmsEmployeeOrganizationController.class);

    @Autowired
    private DmsEmployeeOrganizationService dmsEmployeeOrganizationService;
}