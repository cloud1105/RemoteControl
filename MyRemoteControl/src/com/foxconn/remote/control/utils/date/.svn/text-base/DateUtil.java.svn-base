package com.foxconn.remote.control.utils.date;

import android.annotation.SuppressLint;
import android.text.format.DateUtils;
import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 動畫,日期,字符串,配置工具類
 * 
 * @author KrisLight
 * 
 */
public class DateUtil {
	
	public static final long SECOND_IN_MILLIS = 1000;
    public static final long MINUTE_IN_MILLIS = SECOND_IN_MILLIS * 60;
    public static final long HOUR_IN_MILLIS = MINUTE_IN_MILLIS * 60;
    public static final long DAY_IN_MILLIS = HOUR_IN_MILLIS * 24;
    public static final long WEEK_IN_MILLIS = DAY_IN_MILLIS * 7;
	
	// UTILS
	private DateUtil() {
	}

	/**
	 * 包括年,月,日的日期格式 yyyy-MM-dd
	 */
	@SuppressLint("SimpleDateFormat")
	private static SimpleDateFormat dateFormatShort = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * Sql中的日期格式 yyyy-MM-dd kk:mm.ss
	 */
	@SuppressLint("SimpleDateFormat")
	private static SimpleDateFormat dateFormatSql = new SimpleDateFormat(
			"yyyy-MM-dd kk:mm.ss");



	/**
	 * 得到 yyyy-MM-dd 格式字符串
	 */
	public static String getShortDate(Calendar date) {
		return dateFormatShort.format(date.getTime());
	}

	/**
	 * 
	 * 將日期轉換成SQL中需要的日期格式 形如yyyy-MM-dd kk:mm.ss
	 * 
	 */
	public static String dateToSqlStr(Calendar date) {
		return dateFormatSql.format(date.getTime());
	}


	/**
	 * @Description: 集合中的元素以指定分隔符隔開
	 * @param list  集合List
	 * @param delim  分隔符
	 * @return 用分隔符隔開的集合元素字符串
	 * @version: v1.0
	 * @author: KrisLight
	 */
	public static String join(List<String> list, String delim) {
		StringBuilder buf = new StringBuilder();
		int num = list.size();
		for (int i = 0; i < num; i++) {
			if (i != 0) {
				buf.append(delim);
			}
			buf.append((String) list.get(i));
		}
		return buf.toString();
	}
	
	/**
	 * check date is today
	 * @param date
	 * @return
	 */
	public static boolean isToday(Date date){
		return isSameDate(new Date(), date);
	}
	
	/**
	 * check Date is same day
	 * @param baseDate
	 * @param thenDate
	 * @return
	 */
	public static boolean isSameDate(Date baseDate, Date thenDate){
		Time time = new Time();
        time.set(thenDate.getTime());

        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;

        time.set(baseDate.getTime());
        return (thenYear == time.year)
                && (thenMonth == time.month)
                && (thenMonthDay == time.monthDay);
	}
	
	/**
	 * caculate day counts between startDate and endDate
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int diffDays(Date startDate, Date endDate){
		return (int)((endDate.getTime() - startDate.getTime()) / DateUtils.DAY_IN_MILLIS);
	}
	
	/**
	 * caculate week counts between startDate and endDate
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int diffWeeks(Date startDate, Date endDate){
		return (int)((endDate.getTime() - startDate.getTime()) / DateUtils.WEEK_IN_MILLIS);
	}
	
	/**
	 * caculate month counts between startDate and endDate
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int diffMonths(Date startDate, Date endDate){
		Time startTime = new Time();
		startTime.set(startDate.getTime());
		Time endTime = new Time();
		endTime.set(endDate.getTime());
		int diffYears = endTime.year - startTime.year;
		
		return diffYears * 12 + endTime.month - startTime.month;
	}
	
	/**
	 * caculate year counts between startDate and endDate
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int diffYears(Date startDate, Date endDate){
		Time startTime = new Time();
		startTime.set(startDate.getTime());
		Time endTime = new Time();
		endTime.set(endDate.getTime());
		int diffYears = endTime.year - startTime.year;
		return diffYears;
	}
	
	/**
	 * return date is Saturday or Sunday
	 * @param date
	 * @return
	 */
	public static boolean isWeekend(Date date){
		Time time = new Time();
		time.set(date.getTime());
		return (time.weekDay == Time.SATURDAY || time.weekDay == Time.SUNDAY);
	}
}
