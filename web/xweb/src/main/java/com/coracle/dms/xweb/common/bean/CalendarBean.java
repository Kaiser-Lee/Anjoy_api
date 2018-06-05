package com.coracle.dms.xweb.common.bean;

import java.util.Calendar;

public class CalendarBean {
	
	public CalendarBean() {
		super();
		Calendar c = Calendar.getInstance();
		this.second = 0;
		this.minute = c.get(Calendar.MINUTE);
		this.hour = c.get(Calendar.HOUR_OF_DAY);
		this.date = c.get(Calendar.DAY_OF_MONTH);
		this.month = c.get(Calendar.MONTH) + 1;
		this.year = c.get(Calendar.YEAR);
	}

	public CalendarBean(int year, int month, int date, int hour, int minute,
			int second) {
		super();
		this.year = year;
		this.month = month;
		this.date = date;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}
	
	public static CalendarBean create() {
		return new CalendarBean();
	}

	int year;
	
	int month;
	
	int date;
	
	int hour;
	
	int minute;
	
	int second;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	@Override
	public String toString() {
		return "CalendarBean [year=" + year + ", month=" + month + ", date="
				+ date + ", hour=" + hour + ", minute=" + minute + ", second="
				+ second + "]";
	}
	
}
