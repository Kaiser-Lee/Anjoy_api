package com.coracle.dms.xweb.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coracle.yk.xframework.util.StringUtil;

@SuppressWarnings({"unchecked", "serial"})
public class DateUtils {
    /**  
     * <h1>计算两个日期之间相差的天数</h1>  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws Exception  
     * @author sunyu
     */    
    public static int daysBetween(Date smdate,Date bdate) throws Exception {    
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        smdate = sdf.parse(sdf.format(smdate));  
        bdate = sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days = (time2-time1)/(1000*3600*24);  
            
        return Integer.parseInt(String.valueOf(between_days));           
    }    
    /**  
     * <h1>计算两个日期之间相差的天数(字符串版)</h1>  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws Exception  
     * @author sunyu
     */  
    public static int daysBetween(String smdate, String bdate) throws Exception {  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days = (time2-time1)/(1000*3600*24);  
            
        return Integer.parseInt(String.valueOf(between_days));     
    } 
    /**  
     * <h1>计算两个日期之间相差的天数(字符串版)</h1>  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws Exception  
     * @author sunyu
     */  
    public static int daysBetween(String smdate, long bdate) throws Exception {  
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
    	Calendar cal = Calendar.getInstance();    
    	cal.setTime(sdf.parse(smdate));    
    	long time1 = cal.getTimeInMillis();                 
    	long between_days = (bdate-time1)/(1000*3600*24);  
    	
    	return Integer.parseInt(String.valueOf(between_days));     
    } 
    /**  
     * <h1>计算两个日期之间相差的天数(字符串版)</h1>  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws Exception  
     * @author sunyu
     */  
    public static int daysBetween(Date smdate, long bdate) throws Exception {  
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        smdate = sdf.parse(sdf.format(smdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        long between_days = (bdate-time1)/(1000*3600*24);  
            
        return Integer.parseInt(String.valueOf(between_days));     
    } 
    
    /**
     * <h1>验证提醒时间是否大于当前时间</h1>
     * @param param
     * @return
     */
	public static boolean isValid(Map<String, Object> param) {
    	try {
			String hour = StringUtil.object2String(param.get("hour"));
			String minute = StringUtil.object2String(param.get("minute"));
			for (Map<String,Object> map : (List<Map<String,Object>>) param.get("dateList")) {
				String year = StringUtil.object2String(map.get("year"));
				String month = StringUtil.object2String(map.get("month"));
				String date = StringUtil.object2String(map.get("date"));
				StringBuilder sb = new StringBuilder();
				sb.append(year).append("-").append(month).append("-").append(date)
					.append(" ").append(hour).append(":").append(minute);
				if (System.currentTimeMillis() >= new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(sb.toString()).getTime()) 
					return false;
				
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
    	
    	return true;
    }
	
	public static void main(String[] args) {
		Map<String, Object> param = new HashMap<>();
		param.put("hour", "12");
		param.put("minute", "6");
		List<Map<String,Object>> list = new ArrayList<>();
		list.add(new HashMap<String,Object>(){
			{
				put("year","2016");
				put("month","6");
				put("date","15");
			}
		});
		list.add(new HashMap<String,Object>(){
			{
				put("year","2016");
				put("month","5");
				put("date","28");
			}
		});
		param.put("dateList", list);
		
		System.out.println(isValid(param));
	}
}
