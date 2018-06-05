package com.coracle.dms.xweb.controller;

import com.alibaba.fastjson.JSON;
import com.coracle.dms.xweb.common.resolver.ManageResolver;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
import com.xiruo.medbid.util.Xiruo;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyDescriptor;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 *      
 *     
 * @author jianglfa       
 * @version 1.0     
 * @created Aug 11, 2011 6:59:34 PM
 */
@SuppressWarnings("rawtypes")
public class BaseController {


	protected final Logger LOG = Logger.getLogger(getClass());

	protected HttpServletResponse response;
	protected HttpServletRequest request;
	protected HttpSession session;
	@Autowired
	protected ManageResolver manageResolver;
	
    @ModelAttribute
    public void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response){  
        this.request = request;  
        this.response = response;  
        this.session = request.getSession();  
    } 
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

	protected Object toResult() {
		return toResult(null, "操作成功", true);
	}

	protected Object toResult(boolean encode) {
		return toResult(null, "操作成功", encode);
	}

	protected Object toResult(Object data) {
//		return toResult(data, "操作成功");  //换成base64编码返回数据
		return toResult(data,"操作成功",true);
	}

	/**
	 *
	 * @param data
	 * @param message
	 * @return
	 */
	protected Object toResult(Object data, String message) {
		return toResult(data, message, true);
	}

	/**
	 *
	 * @param data
	 * @param message
	 * @return
	 */
	protected Object toResult(Object data, String message, boolean encode) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] zero = new String[0];
		JsonResult jsonResult = new JsonResult();
		jsonResult.setEncode(encode);
		jsonResult.setStatus(true);
		//如果是分页需要设置总条数total
		if(data instanceof PageHelper.Page) {
			PageHelper.Page page = (PageHelper.Page) data;
			jsonResult.setTotal(page.getTotal());
			if(page.getResult()==null){//统一返回[]，避免返回null
				jsonResult.setData(zero);
			}else {
				jsonResult.setData(page.getResult());
			}
		} else if(data instanceof Collection) {
			Collection list = (Collection)data;
			jsonResult.setTotal(list.size());
			jsonResult.setData(list);
		} else {
			jsonResult.setTotal(1);
			jsonResult.setData(data);
		}
		jsonResult.setMessage(message);
		return jsonResult;
	}


	/**
	 * 返回base64位数据
	 * @param data
	 * @param message
	 * @return
	 */
	protected Object toJsonBase64Result(Object data, String message){
		Map<String, Object> result = new HashMap<String, Object>();
		JsonResult jsonResult = new JsonResult();
		jsonResult.setStatus(true);
		Object resultData=new Object();
		//如果是分页需要设置总条数total
		//如果是分页需要设置总条数total
		if(data instanceof PageHelper.Page) {
			PageHelper.Page page = (PageHelper.Page) data;
			jsonResult.setTotal(page.getTotal());
			resultData=page.getResult();
		} else if(data instanceof Collection) {
			Collection list = (Collection)data;
			jsonResult.setTotal(list.size());
			resultData=list;
		} else {
			jsonResult.setTotal(1);
			resultData=data;
		}
		try{
			String baseStr = new String(org.apache.commons.codec.binary.Base64.encodeBase64(JSON.toJSONString(resultData).getBytes("UTF-8")));
			jsonResult.setData(baseStr);
		}catch (Exception e){
			throw new ControllerException("数据转换异常");
		}
		jsonResult.setMessage(message);
		return jsonResult;
	}




	/**
	 * 说明：将实体bean转成Map,并在key前增加prefix对象名前缀
	 * {
	 * 	"org.id":"100",
	 * 	"org.name":"分公司1"
	 * }
	 * created by huangbaidong 20160718
	 * @param prefix （字符串,如：“org.”）
	 * @param bean	实体Bean, 如：Org
	 * @return
	 */
	public Map<String, Object> beanToMap(String prefix, Object bean) {
		Map<String, Object> params = new HashMap<String, Object>(0);
		if(bean != null) {
			if(prefix == null) prefix = "";
			try {
				PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
				PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(bean);
				for (int i = 0; i < descriptors.length; i++) {
					String name = descriptors[i].getName();
					if (!StringUtils.equals(name, "class")) {
						Object o = propertyUtilsBean.getNestedProperty(bean, name);
						if (descriptors[i].getPropertyType().getName().equals("java.util.Date")) {
							if (o != null) {
								//默认日期类型多转换成yyyy-MM-dd, 如果需要不同格式, 在controller中覆盖key-value
								params.put(prefix + name, Xiruo.dateToString(((Date) o), "yyyy-MM-dd"));
							}
						} else {
							params.put(prefix + name, o);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return params;
	}

	//将javabean转为map类型，然后返回一个map类型的值
	public Map<String, Object> beanToMap(Object bean) {
		return beanToMap("",bean);

	}
}