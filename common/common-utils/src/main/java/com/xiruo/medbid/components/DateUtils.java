package com.xiruo.medbid.components;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * Date operation
 * </p>
 * 
 * @author Lucky
 * 
 */
public class DateUtils {

	/**
	 * <p>
	 * Add field value
	 * </p>
	 * 
	 * @param date
	 *            the date to add
	 * @param amount
	 *            value
	 * @param field
	 * @see Calendar.YEAR, Calendar.MONTH ...
	 * @return
	 */
	public static Date add(Date date, int amount, int field) {
		Calendar c = toCalendar(date);
		c.add(field, amount);
		return toDate(c);
	}

	/**
	 * <p>
	 * Add year value
	 * </p>
	 * 
	 * @param date
	 *            the date to add
	 * @param year
	 *            value
	 * @return
	 */
	public static Date addYear(Date date, int year) {
		return add(date, year, Calendar.YEAR);
	}

	/**
	 * <p>
	 * Add month value
	 * </p>
	 * 
	 * @param date
	 *            the date to add
	 * @param month
	 *            month value
	 * @return
	 */
	public static Date addMonth(Date date, int month) {
		return add(date, month, Calendar.MONTH);
	}

	/**
	 * <p>
	 * Add date value
	 * </p>
	 * 
	 * @param d
	 *            the date to add
	 * @param date
	 *            date of month
	 * @return
	 */
	public static Date addDate(Date d, int date) {
		return TimeUnit.addDay(d, date);
	}

	/**
	 * <p>
	 * Add hour value
	 * </p>
	 * 
	 * @param date
	 *            the date to add
	 * @param hour
	 *            hour of day
	 * @return
	 */
	public static Date addHour(Date date, int hour) {
		return TimeUnit.addHour(date, hour);
	}

	/**
	 * <p>
	 * Add minute value
	 * </p>
	 * 
	 * @param date
	 *            the date to add
	 * @param minute
	 *            minute of hour
	 * @return
	 */
	public static Date addMinute(Date date, int minute) {
		return TimeUnit.addMinute(date, minute);
	}

	/**
	 * <p>
	 * Add second value
	 * </p>
	 * 
	 * @param date
	 *            the date to add
	 * @param second
	 *            second of minute
	 * @return
	 */
	public static Date addSecond(Date date, int second) {
		return TimeUnit.addSecond(date, second);
	}

	/**
	 * <p>
	 * Convert Date to Calendar
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar toCalendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	/**
	 * <p>
	 * Convert Calendar to Date
	 * </p>
	 * 
	 * @param c
	 *            the Calendar to convert
	 * @return
	 */
	public static Date toDate(Calendar c) {
		if (c != null) {
			return c.getTime();
		}
		return null;
	}

	/**
	 * <p>
	 * Build a Date by appointing year,month and date
	 * </p>
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static Date buildDate(int year, int month, int date) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MILLISECOND, 0);
		c.set(year, month - 1, date, 0, 0, 0);
		return toDate(c);
	}

	/**
	 * <p>
	 * Build a Date by appointing year,month,date,hour,minute and second
	 * </p>
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date buildDate(int year, int month, int date, int hour,
			int minute, int second) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MILLISECOND, 0);
		c.set(year, month - 1, date, hour, minute, second);
		return toDate(c);
	}

	// 这个方法是把指定格式的字符串转换成Date，也是比较常见的需求，请注意，这种转换效率并不高
	public static Date toDate(String str) {
		if (str.contains(":")) {
			try {
				return DateFormatUtils.parse(DateFormatUtils
						.getDefaultTimeFormat(), str);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			try {
				return DateFormatUtils.parse(DateFormatUtils
						.getDefaultDateFormat(), str);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 将时间格式化为 年:月:日 时:分:秒 的格式
	 * @param date
	 * @return
	 */
	public static String parseToTimeText(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(date);
	}

	/**
	 * 获取本月第一天的开始时间
	 * @return
	 */
	public static String getStartTimeThisMonthText() {
		Calendar calendar = Calendar.getInstance();

		parseStartTime(calendar);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		return parseToTimeText(calendar.getTime());
	}

	/**
	 * 获取昨天的结束时间
	 * @return
	 */
	public static String getEndTimeYesterdayText() {
		Calendar calendar = Calendar.getInstance();

		parseEndTime(calendar);
		calendar.add(Calendar.DATE, -1);
		return parseToTimeText(calendar.getTime());
	}

	/**
	 * 获取去年本月第一天的开始时间
	 * @return
	 */
	public static String getStartTimeThisMonthLastYearText() {
		Calendar calendar = Calendar.getInstance();

		parseStartTime(calendar);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.YEAR, -1);
		return parseToTimeText(calendar.getTime());
	}

	/**
	 * 获取去年本月第一天的结束时间
	 * @return
	 */
	public static String getEndTimeThisMonthLastYearText() {
		Calendar calendar = Calendar.getInstance();

		parseEndTime(calendar);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.YEAR, -1);
		return parseToTimeText(calendar.getTime());
	}

	/**
	 *获取今年的第一天为开始时间
	 */

	public static String getStartTimeThisYearText(){
		Calendar calendar = Calendar.getInstance();
		parseStartTime(calendar);
		calendar.set(Calendar.DAY_OF_YEAR,1);


		return parseToTimeText(calendar.getTime());


	}



	/**
	 * 获取去年的第一天为开始时间
	 */
	public static String getStartTimeLastYearText(){
		Calendar calendar = Calendar.getInstance();
		parseStartTime(calendar);
		calendar.set(Calendar.DAY_OF_YEAR,1);
		calendar.add(Calendar.YEAR,-1);


		return parseToTimeText(calendar.getTime());


	}



	/**
	 * 将时间设置为一天的开始时间 00:00:00
	 * @param calendar
	 */
	public static void parseStartTime(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	/**
	 * 将时间设置为一天的结束时间 23:59:59
	 * @param calendar
	 */
	public static void parseEndTime(Calendar calendar) {
	    parseStartTime(calendar);
		calendar.add(Calendar.DATE, 1);
		calendar.add(Calendar.MILLISECOND, -1);
	}

	public static void main(String[] args) {
		System.out.println(getStartTimeThisMonthText());
		System.out.println(getEndTimeThisMonthLastYearText());
		System.out.println(getStartTimeThisMonthLastYearText());
		System.out.println(getEndTimeYesterdayText());
		System.out.println(getStartTimeThisYearText());
		System.out.print(getStartTimeLastYearText());
	}
}
