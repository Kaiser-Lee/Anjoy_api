package com.xiruo.medbid.components.date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @title：
 * @describe：
 * @copyright：Copyright (c) 1997-2017 coracle, Inc.
 * @company：圆舟科技
 * @author：taok
 * @date：2017-11-14 9:38
 * @version：1.0
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);
    public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE = "yyyy-MM-dd";
    public static final String TIME = "HH:mm:ss";
    public static final String YEAR = "yyyy";
    public static final String MONTH = "MM";
    public static final String DAY = "dd";
    public static final String WEEK = "E";
    public static final String HH = "HH";
    public static final String MM = "mm";
    public static final String SS = "ss";
	public static final int QUARTER_1 = 1;//第一季度
	public static final int QUARTER_2 = 2;//第二季度
	public static final int QUARTER_3 = 3;//第三季度
	public static final int QUARTER_4 = 4;//第四季度
	public static final int SECOND_MILLIS_TIME = 1000;
	public static final int MINUTE_MILLIS_TIME = 60 * SECOND_MILLIS_TIME;
	public static final int HOUR_MILLIS_TIME = 60 * MINUTE_MILLIS_TIME;
	public static final int DAY_MILLIS_TIME = 24 * HOUR_MILLIS_TIME;
	public static final String DAY_START_TIME = " 00:00:00";
	public static final String DAY_END_TIME = " 23:59:59";

	public static String[] parsePatterns = {
		"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", "yyyy-MM-dd", "yyyy-MM",
		"yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH", "yyyy/MM/dd", "yyyy/MM",
		"yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM.dd HH", "yyyy.MM.dd", "yyyy.MM",
		"yyyy", "HH:mm:ss"
	};

	public static String[] datePatterns = {
		"yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
	};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String formatDate() {
		return formatDate(new Date());
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime() {
		return formatDateTime(new Date());
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, DATETIME);
	}

	public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String formatTime() {
		return formatTime(new Date());
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String formatTime(Date date) {
		return formatDate(date, TIME);
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String formatYear() {
		return formatYear(new Date());
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String formatYear(Date date) {
		return formatDate(date, YEAR);
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String formatMonth() {
		return formatMonth(new Date());
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String formatMonth(Date date) {
		return formatDate(date, MONTH);
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String formatDay() {
		return formatDay(new Date());
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String formatDay(Date date) {
		return formatDate(date, DAY);
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String formatWeek() {
		return formatWeek(new Date());
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String formatWeek(Date date) {
		return formatDate(date, WEEK);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为：
		 "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", "yyyy-MM-dd", "yyyy-MM",
		 "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH", "yyyy/MM/dd", "yyyy/MM",
		 "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM.dd HH", "yyyy.MM.dd", "yyyy.MM",
		 "yyyy", "HH:mm:ss"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			String strPattern = pattern[0].toString();
			if(strPattern.equals("Q")){//2016Q1
				return DateFormatUtils.format(date, YEAR) + "Q" + getQuarter(date);
			}else if(strPattern.equals("H")){//2016Q1
				return DateFormatUtils.format(date, YEAR) + getHalfYear(date);
			}else{
				formatDate = DateFormatUtils.format(date, strPattern);
			}
		} else {
			formatDate = DateFormatUtils.format(date, DATE);
		}
		return formatDate;
	}

	/**
	 * 日期型字符串转化为日期 格式
		 "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", "yyyy-MM-dd", "yyyy-MM",
		 "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH", "yyyy/MM/dd", "yyyy/MM",
		 "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM.dd HH", "yyyy.MM.dd", "yyyy.MM",
		 "yyyy", "HH:mm:ss"
	 */
	public static Date parseDate(String formatDate) {
		Date date = null;Long time = null;
		try {time = Long.parseLong(formatDate);}catch (Exception e){}
		if(time != null && time > 0){
			date = new Date(time);
		}else{
			try {
				date = parseDate(formatDate.toString(), parsePatterns);
			} catch (Exception e) {
				logger.error("日期格式化异常：{}", e.getMessage());
			}
		}
		return date;
	}

	/**
	 * 传入一个日期参数，返回该日期对应的季度：1，2，3，4
	 * @return
	 */
	public static int getQuarter(String dateFormat) {
		Date date = parseDate(dateFormat);
		return getQuarter(date);
	}

	/**
	 * 传入一个日期参数，返回该日期对应的季度：1，2，3，4
		一月：January 二月：February 三月：March
		四月：April 五月：May 六月：June
		七月：July 八月：August 九月：September
		十月：October 十一月：November 十二月：December
	 * @return
	 */
	public static int getQuarter(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int currentMonth = c.get(Calendar.MONTH);
		try {
			if (currentMonth >= Calendar.JANUARY && currentMonth <= Calendar.MARCH)
				return QUARTER_1;
			else if (currentMonth >= Calendar.APRIL && currentMonth <= Calendar.JUNE)
				return QUARTER_2;
			else if (currentMonth >= Calendar.JULY && currentMonth <= Calendar.SEPTEMBER)
				return QUARTER_3;
			else if (currentMonth >= Calendar.OCTOBER && currentMonth <= Calendar.DECEMBER)
				return QUARTER_4;
		} catch (Exception e) {
			logger.error("日期转换异常：{}", e.getMessage());
		}
		return 0;
	}

	/**
	 * 传入一个日期参数，返回该日期所在半年：上，下
	 * @return
	 */
	public static String getHalfYear(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int currentMonth = c.get(Calendar.MONTH);
		try {
			if (currentMonth >= Calendar.JANUARY && currentMonth <= Calendar.JUNE){
				return "上";
			}else if (currentMonth >= Calendar.JULY && currentMonth <= Calendar.DECEMBER){
				return "下";
			}
		} catch (Exception e) {
			logger.error("日期转换异常：{}", e.getMessage());
		}
		return "";

	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long getPastDay(Date date) {
		long time = new Date().getTime() - date.getTime();
		return time / DAY_MILLIS_TIME;
	}

	/**
	 * 获取两个日期之间的天数
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public static long getDifferDay(Date startDate, Date endDate) {
		long startDateTime = startDate.getTime();
		long endDateTime = endDate.getTime();
		return (endDateTime - startDateTime) / DAY_MILLIS_TIME;
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long getPastHour(Date date) {
		long time = new Date().getTime() - date.getTime();
		return time / HOUR_MILLIS_TIME;
	}

	/**
	 * 获取两个日期之间的小时数
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public static long getDifferHour(Date startDate, Date endDate) {
		long startDateTime = startDate.getTime();
		long endDateTime = endDate.getTime();
		return (endDateTime - startDateTime) / HOUR_MILLIS_TIME;
	}

	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long getPastMinute(Date date) {
		long time = new Date().getTime() - date.getTime();
		return time / MINUTE_MILLIS_TIME;
	}

	/**
	 * 获取两个日期之间的分钟数
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public static long getDifferMinute(Date startDate, Date endDate) {
		long startDateTime = startDate.getTime();
		long endDateTime = endDate.getTime();
		return (endDateTime - startDateTime) / MINUTE_MILLIS_TIME;
	}

	/**
	 * 获取过去的秒数
	 * @param date
	 * @return
	 */
	public static long getPastSecond(Date date) {
		long time = new Date().getTime() - date.getTime();
		return time / SECOND_MILLIS_TIME;
	}

	/**
	 * 获取两个日期之间的秒数
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public static long getDifferSecond(Date startDate, Date endDate) {
		long startDateTime = startDate.getTime();
		long endDateTime = endDate.getTime();
		return (endDateTime - startDateTime) / SECOND_MILLIS_TIME;
	}

	/**
	 * 判断传入的日期是否为当天
	 * @param date
	 * @return
	 */
	public static boolean isSameDay(Date date) {
		String source = formatDate(date, DATE);
		String current = formatDate(new Date(), DATE);
		if (current.equals(source)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 对指定日期进行运算
	 * 负数：往前推N，正数：向后推N
	 * 往前推两年：formatDate(add(parseDate("2017-10-05"), CalendarField.YEAR.getIndex(), -2))
	 * 往后推一个月：formatDate(add(parseDate("2017-08-11"), CalendarField.MONTH.getIndex(), 1))
	 * 往后推5天：formatDate(add(parseDate("2017-03-21"), CalendarField.DATE, 5))
	 * 往前推3周：formatDate(add(new Date(), CalendarField.WEEK, -3))
	 * @param field -- 日历字段
	 * @param amount --要添加到该字段的日期或时间的量。
	 * @return
	 */
	public static Date add(Date date, CalendarField field, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field.getIndex(), amount);
		return calendar.getTime();
	}

	/**
	 * 传入一个日期参数，返回该日期的最开始时间（秒）
	 * @return
	 */
	public static Date getDayStartTime(String dateFormat){
		Date date = null;
		try {
			dateFormat = dateFormat.trim();
			int index = dateFormat.indexOf(" ");
			if(index > 0){
				dateFormat = dateFormat.substring(0 ,index);
			}
			date = parseDate(dateFormat, datePatterns);
		}catch (Exception e){
			logger.error("日期转换失败：{}", e.getMessage());
		}
		return date;
	}

	/**
	 * 传入一个日期参数，返回该日期的最开始时间（秒）
	 * @return
	 */
	public static Date getDayStartTime(Date date){
		Date newDate = null;
		try {
			newDate = parseDate(formatDate(date, datePatterns), datePatterns);
		}catch (Exception e){
			logger.error("日期转换失败：{}", e.getMessage());
		}
		return newDate;
	}

	/**
	 * 传入一个日期参数，返回该日期的最后时间（秒）
	 * @return
	 */
	public static Date getDayEndTime(String dateFormat){
		Date date = getDayEndTime(dateFormat, false);
		return date;
	}

	/**
	 * 传入一个日期参数，返回该日期的最开始时间（秒）
	 * @return
	 */
	public static Date getDayEndTime(Date date){
		Date newDate = null;
		try {
			newDate = getDayEndTime(formatDate(date, datePatterns), false);
		}catch (Exception e){
			logger.error("日期转换失败：{}", e.getMessage());
		}
		return newDate;
	}

	/**
	 * 传入一个日期参数，返回该日期的最后时间（毫秒）
	 * @return
	 */
	public static Date getDayEndMillitTime(String dateFormat){
		Date date = getDayEndTime(dateFormat, true);
		return date;
	}

	/**
	 * 传入一个日期参数，返回该日期的最开始时间（毫秒）
	 * @return
	 */
	public static Date getDayEndMillitTime(Date date){
		Date newDate = null;
		try {
			newDate = getDayEndTime(formatDate(date, datePatterns), true);
		}catch (Exception e){
			logger.error("日期转换失败：{}", e.getMessage());
		}
		return newDate;
	}

	/**
	 * 传入一个日期参数，返回该日期的最后时间（秒、毫秒）
	 * @param dateFormat
	 * @param millit
     * @return
     */
	public static Date getDayEndTime(String dateFormat, boolean millit){
		Date date = null;
		try {
			dateFormat = dateFormat.trim();
			int index = dateFormat.indexOf(" ");
			if(index > 0){
				dateFormat = dateFormat.substring(0 ,index);
			}
			if(millit){
				date = new Date(addSeconds(getDayStartTime(dateFormat), DAY_MILLIS_TIME / SECOND_MILLIS_TIME).getTime() - 1);
			}else {
				date = addSeconds(getDayStartTime(dateFormat), DAY_MILLIS_TIME / SECOND_MILLIS_TIME - 1);
			}
		}catch (Exception e){
			logger.error("日期转换失败：{}", e.getMessage());
		}
		return date;
	}

	/**
	 * 传入一个日期参数，返回该日期所在年的第一天
	 * @return
	 */
	public static Date getYearFirstDay(String dateFormat){
		Date date = parseDate(dateFormat);
		return getYearFirstDay(date);
	}

	/**
	 * 传入一个日期参数，返回该日期所在年的第一天
	 * @return
	 */
	public static Date getYearFirstDay(Date date){
		int year = Integer.parseInt(formatYear(date));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.DAY_OF_YEAR, c.getActualMinimum(Calendar.DAY_OF_YEAR));
		//SimpleDateFormat format = new SimpleDateFormat(DATE);
		//String yearFirstDay = format.format(c.getTime());
		return c.getTime();
	}

	/**
	 * 传入一个日期参数，返回该日期所在年的最后一天
	 * @return
	 */
	public static Date getYearLastDay(String dateFormat){
		Date date = parseDate(dateFormat);
		return getYearLastDay(date);
	}

	/**
	 * 传入一个日期参数，返回该日期所在年的最后一天
	 * @return
	 */
	public static Date getYearLastDay(Date date){
		int year = Integer.parseInt(formatYear(date));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR));
		//SimpleDateFormat format = new SimpleDateFormat(DATE);
		//String yearLastDay = format.format(c.getTime());
		return c.getTime();
	}

	/**
	 * 传入一个日期参数，返回该日期所在年的最后一天
	 * @return
	 */
	public static Date getYearEndTime(String dateFormat){
		Date date = parseDate(dateFormat);
		return getYearEndTime(date);
	}

	/**
	 * 传入一个日期参数，返回该日期所在年的最后一天
	 * @return
	 */
	public static Date getYearEndTime(Date date){
		String formatDate = formatDate(getYearLastDay(date), DATE);
		return parseDate(formatDate + DAY_END_TIME);
	}

	/**
	 * 获取某月第一天
	 * @return
	 */
	public static String getMonthFirstDay(String dateFormat) {
		Date date = parseDate(dateFormat);
		return getMonthFirstDay(date);
	}

	/**
	 * 获取某月的第一天
	 * @return
	 */
	public static String getMonthFirstDay(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(DATE);
		int year = Integer.parseInt(formatYear(date));
		int month = Integer.parseInt(formatMonth(date));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
		String firstDay = format.format(c.getTime());
		return firstDay;
	}

	/**
	 * 传入一个日期参数，返回该日期所在月份的第一天
	 * @return
	 */
	public static Date getMonthStartTime(String dateFormat) {
		Date date = parseDate(dateFormat);
		return getMonthStartTime(date);
	}

	/**
	 * 传入一个日期参数，返回该日期所在月份的第一天
	 * @return
	 */
	public static Date getMonthStartTime(Date date) {
		return parseDate(getMonthFirstDay(date) + DAY_START_TIME);
	}

	/**
	 * 获取某月的最后一天
	 * @return
	 */
	public static String getMonthLastDay(String dateFormat){
		Date date = parseDate(dateFormat);
		return getMonthLastDay(date);
	}

	/**
	 * 获取某月的最后一天
	 * @return
	 */
	public static String getMonthLastDay(Date date){
		SimpleDateFormat format = new SimpleDateFormat(DATE);
		int year = Integer.parseInt(formatYear(date));
		int month = Integer.parseInt(formatMonth(date));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		String lastDay = format.format(c.getTime());
		return lastDay;
	}

	/**
	 * 传入一个日期参数，返回该日期所在月份的最后一天
	 * @return
	 */
	public static Date getMonthEndTime(String dateFormat) {
		Date date = parseDate(dateFormat);
		return getMonthEndTime(date);
	}

	/**
	 * 传入一个日期参数，返回该日期所在月份的最后一天
	 * @return
	 */
	public static Date getMonthEndTime(Date date) {
		return parseDate(getMonthLastDay(date) + DAY_END_TIME);
	}

	/**
	 * 传入一个日期参数，返回该日期所在年的第几天
	 * @return
	 */
	public static int getDayOfYear(String dateFormat){
		Date date = parseDate(dateFormat);
		return getDayOfYear(date);
	}

	/**
	 * 传入一个日期参数，返回该日期所在年的第几天
	 * @return
	 */
	public static int getDayOfYear(Date date){
		int year = Integer.parseInt(formatYear(date));
		int month = Integer.parseInt(formatMonth(date));
		int day = Integer.parseInt(formatDay(date));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DATE, day);
		return c.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 传入一个日期参数，返回该日期所在月的第几天
	 * @return
	 */
	public static int getDayOfMonth(String dateFormat){
		Date date = parseDate(dateFormat);
		return getDayOfMonth(date);
	}

	/**
	 * 传入一个日期参数，返回该日期所在月的第几天
	 * @return
	 */
	public static int getDayOfMonth(Date date){
		int year = Integer.parseInt(formatYear(date));
		int month = Integer.parseInt(formatMonth(date));
		int day = Integer.parseInt(formatDay(date));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DATE, day);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 传入一个日期参数，返回该日期所在周的第几天
	 * @return
	 */
	public static int getDayOfWeek(String dateFormat){
		Date date = parseDate(dateFormat);
		return getDayOfWeek(date);
	}

	/**
	 * 传入一个日期参数，返回该日期所在周的第几天
	 * 星期日: 1, 星期一: 2, 星期二: 3, 星期三: 4, 星期四: 5, 星期五: 6, 星期六: 7
	 * @return
	 */
	public static int getDayOfWeek(Date date){
		int year = Integer.parseInt(formatYear(date));
		int month = Integer.parseInt(formatMonth(date));
		int day = Integer.parseInt(formatDay(date));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DATE, day);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 传入一个日期参数，返回该日期所在周的第几天
	 * 星期日: 7, 星期一: 1, 星期二: 2, 星期三: 3, 星期四: 4, 星期五: 5, 星期六: 6
	 * @Param formatCN：中文方式
	 * @return
	 */
	public static int getDayOfWeek(Date date, boolean formatCN){
		int dayOfWeek = getDayOfWeek(date);
		if(formatCN){
			if(dayOfWeek == 1){
				dayOfWeek = 7;
			}else{
				dayOfWeek = dayOfWeek - 1;
			}
		}
		return dayOfWeek;
	}

	/**
	 * 传入一个日期参数，返回该日期所在年的第几周
	 * @return
	 */
	public static int getWeekOfYear(String dateFormat){
		Date date = parseDate(dateFormat);
		return getWeekOfYear(date);
	}

	/**
	 * 传入一个日期参数，返回该日期所在年的第几周
	 * @return
	 */
	/*public static int getWeekOfYear(Date date){
		int year = Integer.parseInt(formatYear(date));
		int month = Integer.parseInt(formatMonth(date));
		int day = Integer.parseInt(formatDay(date));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DATE, day);
		return c.get(Calendar.WEEK_OF_YEAR);
	}*/

	/**
	 * 传入一个日期参数，返回该日期所在月的第几周
	 * @return
	 */
	public static int getWeekOfMonth(String dateFormat){
		Date date = parseDate(dateFormat);
		return getWeekOfMonth(date);
	}

	/**
	 * 传入一个日期参数，返回该日期所在月的第几周
	 * @return
	 */
	public static int getWeekOfMonth(Date date){
		int year = Integer.parseInt(formatYear(date));
		int month = Integer.parseInt(formatMonth(date));
		int day = Integer.parseInt(formatDay(date));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DATE, day);
		return c.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 传入一个日期参数，返回星期名称
	 * @return
	 */
	public static String getWeekName(String dateFormat){
		Date date = parseDate(dateFormat);
		return getWeekName(date);
	}

	/**
	 * 传入一个日期参数，返回星期名称
	 * @return
	 */
	public static String getWeekName(Date date){
		String weekName = null;
		int dayOfWeek = getDayOfWeek(date);
		switch (dayOfWeek){
			case 1 : weekName = "星期日"; break;
			case 2 : weekName = "星期一"; break;
			case 3 : weekName = "星期二"; break;
			case 4 : weekName = "星期三"; break;
			case 5 : weekName = "星期四"; break;
			case 6 : weekName = "星期五"; break;
			case 7 : weekName = "星期六"; break;
		}
		return weekName;
	}

	// 获取年所在周
	public static int getWeekOfYear(Date date) {
		//Calendar c = new GregorianCalendar();
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	// 获取当前时间所在年的最大周数
	public static int getMaxWeekNumOfYear(int year) {
		Calendar c = Calendar.getInstance();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
		return getWeekOfYear(c.getTime());
	}

	// 获取某年的第几周的开始日期
	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);
		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);
		return getFirstDayOfWeek(cal.getTime());
	}

	// 获取某年的第几周的结束日期
	public static Date getLastDayOfWeek(int year, int week) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);
		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);
		return getLastDayOfWeek(cal.getTime());
	}

	// 获取当前时间所在周的开始日期
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	// 获取当前时间所在周的结束日期
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}

	// 获取当前时间所在周的结束日期（周五：Calendar.WEDNESDAY）
	public static Date getLastDayOfWeek(Date date, int endDay) {
		if(endDay < Calendar.SUNDAY || endDay > Calendar.SATURDAY){
			endDay = Calendar.WEDNESDAY;
		}
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + endDay);
		return c.getTime();
	}

	/**
	 * 传入一个日期参数，返回该日期所在季度的第一天
	 * @return
	 */
	public static Date getQuarterFistDay(Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int currentMonth = c.get(Calendar.MONTH);

			if (currentMonth >= Calendar.JANUARY && currentMonth <= Calendar.MARCH) {
				c.set(Calendar.MONTH, Calendar.JANUARY);
			} else if (currentMonth >= Calendar.APRIL && currentMonth <= Calendar.JUNE) {
				c.set(Calendar.MONTH, Calendar.APRIL);
			} else if (currentMonth >= Calendar.JULY && currentMonth <= Calendar.SEPTEMBER) {
				c.set(Calendar.MONTH, Calendar.JULY);
			} else if (currentMonth >= Calendar.OCTOBER && currentMonth <= Calendar.DECEMBER) {
				c.set(Calendar.MONTH, Calendar.OCTOBER);
			}
			c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
			return parseDate(formatDate(c.getTime(), DATE)+ DAY_START_TIME);
		} catch (Exception e) {
			logger.error("日期转换异常：{}", e.getMessage());
		}
		return null;
	}

	/**
	 * 传入一个日期参数，返回该日期所在季度的最后一天
	 * @return
	 */
	public static Date getQuarterLastDay(Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int currentMonth = c.get(Calendar.MONTH);

			if (currentMonth >= Calendar.JANUARY && currentMonth <= Calendar.MARCH) {
				c.set(Calendar.MONTH, Calendar.MARCH);
			} else if (currentMonth >= Calendar.APRIL && currentMonth <= Calendar.JUNE) {
				c.set(Calendar.MONTH, Calendar.JUNE);
			} else if (currentMonth >= Calendar.JULY && currentMonth <= Calendar.SEPTEMBER) {
				c.set(Calendar.MONTH, Calendar.SEPTEMBER);
			} else if (currentMonth >= Calendar.OCTOBER && currentMonth <= Calendar.DECEMBER) {
				c.set(Calendar.MONTH, Calendar.DECEMBER);
			}
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			return parseDate(formatDate(c.getTime(), DATE) + DAY_END_TIME);
		} catch (Exception e) {
			logger.error("日期转换异常：{}", e.getMessage());
		}
		return null;
	}

	/**
	 * 传入一个日期参数，返回该日期所在半年的第一天
	 * @return
	 */
	public static Date getHalfYearFistDay(Date date){
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int currentMonth = c.get(Calendar.MONTH);

			if (currentMonth >= Calendar.JANUARY && currentMonth <= Calendar.JUNE){
				c.set(Calendar.MONTH, Calendar.JANUARY);
			}else if (currentMonth >= Calendar.JULY && currentMonth <= Calendar.DECEMBER){
				c.set(Calendar.MONTH, Calendar.JULY);
			}
			c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
			//c.set(Calendar.DATE, 1);
			return parseDate(formatDate(c.getTime(), DATE)+ DAY_START_TIME);
		} catch (Exception e) {
			logger.error("日期转换异常：{}", e.getMessage());
		}
		return null;

	}
	/**
	 * 传入一个日期参数，返回该日期所在半年的最后一天
	 * @return
	 */
	public static Date getHalfYearLastDay(Date date){
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int currentMonth = c.get(Calendar.MONTH);

			if (currentMonth >= Calendar.JANUARY && currentMonth <= Calendar.JUNE){
				c.set(Calendar.MONTH, Calendar.JUNE);
			}else if (currentMonth >= Calendar.JULY && currentMonth <= Calendar.DECEMBER){
				c.set(Calendar.MONTH, Calendar.DECEMBER);
			}
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			return parseDate(formatDate(c.getTime(), DATE) + DAY_END_TIME);
		} catch (Exception e) {
			logger.error("日期转换异常：{}", e.getMessage());
		}
		return null;
	}
    
    /*public static void main(String[] args) {
		test();
	}*/

	public static void test(){
		try {
			String date = "2017-04-27", dateWeek = "2017-12-31";
			//addYears()，addMonths()，addDays()，addHours()，addMinutes()，addSeconds()
			System.out.println("日期："+date+"，前两年："+formatDate(add(parseDate(date), CalendarField.YEAR, -2)));
			System.out.println("日期："+date+"，下个月："+formatDate(add(parseDate(date), CalendarField.MONTH, 1)));
			System.out.println("日期："+date+"，往后推6天："+formatDate(add(parseDate(date), CalendarField.DATE, 6)));
			System.out.println("日期："+date+"，往前推3周："+formatDate(add(new Date(), CalendarField.WEEK, -3)));
			String timeStr = "1514610449567";//new Date().getTime()
			System.out.println("格式化日期："+formatDate(parseDate(timeStr), DATETIME));
			System.out.println("日期："+dateWeek+"，中文周第："+getDayOfWeek(parseDate(dateWeek))+" 天");
			System.out.println("日期："+dateWeek+"，英文周第："+getDayOfWeek(parseDate(dateWeek), true)+" 天");
			System.out.println("日期："+dateWeek+"，周名称："+getWeekName(dateWeek));

			Date date1 = parseDate("2017-12-30 12:05");
			String timeStr2 = date1.getTime()+"";
			System.out.println(formatDate(parseDate(timeStr2), DATETIME));
			System.out.println("今年第："+getWeekOfYear(new Date())+" 周");
			System.out.println("本月第："+getWeekOfMonth(new Date())+" 周");
			System.out.println("周开始日期："+formatDate(getFirstDayOfWeek(new Date()), DATETIME));
			System.out.println("周结束日期："+formatDate(getLastDayOfWeek(new Date()), DATETIME));
			System.out.println("工作周结束日期："+formatDate(getLastDayOfWeek(new Date(), Calendar.WEDNESDAY), DATETIME));
			System.out.println(formatDate(getHalfYearLastDay(parseDate("2017-06-10"))));

			String date2 = "2017-06-10 13";
			System.out.println(formatDateTime(parseDate(date2)));

			System.out.println(DateUtils.formatDate(DateUtils.addHours(new Date(), 2), DATETIME));

			Date date3 = new Date();
			String dt = "2018-01-09 10:58:04";
			System.out.println(date3.getTime());
			System.out.println(getDayStartTime(dt).getTime());
			System.out.println(getDayStartTime(date3).getTime());
			System.out.println(addSeconds(getDayStartTime(dt), DAY_MILLIS_TIME / SECOND_MILLIS_TIME - 1).getTime());
			System.out.println(formatDate(addSeconds(getDayStartTime(dt), DAY_MILLIS_TIME / SECOND_MILLIS_TIME - 1), DATETIME));

			System.out.println(getDayEndTime(dt).getTime());
			System.out.println(getDayEndMillitTime(dt).getTime());
			System.out.println(getDayEndTime(date3).getTime());
		}catch (Exception e){
			e.printStackTrace();
		}

	}

}
