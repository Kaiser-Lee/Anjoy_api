package com.coracle.dms.xweb.controller;

import java.util.HashMap;
import java.util.Map;

import com.coracle.dms.xweb.common.enums.ErrorCodeEnum;
import com.coracle.dms.xweb.common.enums.StatusEnum;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/api/v1/dms/version")
@Api(description = "APP版本检查")
public class AppVersionController {
	
	@Value("${app.version:1.0}")
	private String appversion;
	
	@Value("${app.updateUrl:}")
	private String updateUrl;
	
	/**
	 * 检查更新
	 * @param paraMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/checkAppVersion")  
	@ResponseBody
	public Map<String,Object> checkAppVersion(@RequestBody Map paraMap) {
		Map<String,Object> map = new HashMap<String,Object>();

		try {
			String version = (String)paraMap.get("version");
			
			//版本相同，不需要更新
			if(appversion.equals(version)){
				map.put("status", StatusEnum.SUCCESS.getStatus());
				map.put("needUpdate", false);
			}else{
				map.put("status", StatusEnum.SUCCESS.getStatus());
				map.put("needUpdate", true);
				map.put("updateUrl", updateUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", StatusEnum.ERROR.getStatus());
			map.put("message", ErrorCodeEnum.DEFAULT.getMessage());
		}
		return map;
	}
}
