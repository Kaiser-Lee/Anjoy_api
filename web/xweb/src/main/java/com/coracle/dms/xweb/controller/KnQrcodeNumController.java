package com.coracle.dms.xweb.controller;

import com.coracle.dms.service.KnQrcodeNumService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("knQrcodeNum")
public class KnQrcodeNumController extends BaseController {
    private static final Logger logger = Logger.getLogger(KnQrcodeNumController.class);

    @Autowired
    private KnQrcodeNumService knQrcodeNumService;
}