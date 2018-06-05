package com.coracle.dms.xweb.controller.tz;

import com.coracle.dms.service.tz.TgOrderProductPartService;
import com.coracle.dms.xweb.controller.BaseController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("tgOrderProductPart")
public class TgOrderProductPartController extends BaseController {
    private static final Logger logger = Logger.getLogger(TgOrderProductPartController.class);

    @Autowired
    private TgOrderProductPartService tgOrderProductPartService;
}