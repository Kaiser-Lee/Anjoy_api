package com.coracle.yk.xframework.util;

import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
* @ClassName: ExceptionUtil
* @Description：异常相关处理类
* @author：生活家.杨青见
* @date：2016年7月19日 下午7:37:58
* 修改备注：
* 序号 		修改人：		修改时间：			                备注
* 001		生活家.杨青见	2016年7月19日 下午7:37:58
* @version
 */
public class ExceptionUtil {
	/**
	 * 返回堆栈的错误信息
	 * @param t
	 * @return
	 */
	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			t.printStackTrace(pw);
			return sw.toString();
		} finally {
			pw.close();
		}
	}
	/**
	 * 
	* @Title：getMessage
	* @param @param stack_message
	* @param @return
	* @return String  
	* 创建人：生活家.杨青见
	* 创建时间：2016年7月20日 上午11:15:03
	* @Description：解析dubbo抛出的异常
	* 序号 		修改人：		修改时间：			                备注
	* 001		生活家.杨青见	2016年7月20日 上午11:15:03		
	* @version
	 */
	public static String getMessage(String stack_message){
		try{
			boolean businessFlag =  stack_message.contains(ServiceException.class.getName());
			boolean controllerFlag =  stack_message.contains(ControllerException.class.getName());
			String returnmsg =  "" ;
			if(businessFlag){
				int start_index = stack_message.indexOf(ServiceException.class.getName())+ServiceException.class.getName().length()+1;
				int end_index = stack_message.indexOf("\n", start_index);
				String message_str = stack_message.substring(start_index,end_index);
				returnmsg = ""+message_str;
			}else if(controllerFlag){
				int start_index = stack_message.indexOf(ControllerException.class.getName())+ControllerException.class.getName().length()+1;
				int end_index = stack_message.indexOf("\n", start_index);
				String message_str = stack_message.substring(start_index,end_index);
				returnmsg = "参数异常："+message_str;//参数异常
			}else{
				returnmsg = stack_message;
			}
			
			//去掉消息中的异常英文信息
			if(BlankUtil.isNotEmpty(returnmsg)){
				returnmsg=returnmsg.replace(ServiceException.class.getName(), "");
				returnmsg=returnmsg.replace(ControllerException.class.getName(), "");
			}
			return returnmsg;
		}catch(Exception ex){
			return stack_message;
		}
	}

	/**
	 * 获取报错的类名方法行号(只取轨迹中的最后一条)
	 * @param errorMessage
	 */
	public static String getErrorClassInfo(String errorMessage) {
		if(errorMessage!=null) {
			Pattern pattern = Pattern.compile("at.*\\(.+\\.java:\\d+\\)");
			Matcher matcher = pattern.matcher(errorMessage);
			if (matcher.find()) {
				return matcher.group();
			}
		}
		return "";
	}
}
