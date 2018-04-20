
package com.litc.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/**
 *  Function:处理日期时间相关的工具类  
 *  @author  zhongying(281264212@qq.com)
 *  @date    2003-12-11 下午5:07:19    
 *  @version 1.0
 */
public class DateUtils
{

    /**
     * 将时间转换为数字字符串(毫秒级)
     *
     * @param date 要进行转换的时间
     * @return 转换后的字符串
     */
    public static final String dateToMillis(Date date)
    {
        return dateFormat(date, "yyyyMMddHHmmssSSS");
    }

    /**
     * 按指定的格式对日期进行格式化
     *
     * @param date 日期
     * @param format 格式
     * @return 格式化后的日期
     */
    public static final String dateFormat(Date date, String format)
    {
        SimpleDateFormat formatter = null;
        formatter = new SimpleDateFormat(format, Locale.getDefault());
        return formatter.format(date);
    }

    /**
     * 按指定的格式对日期进行格式化
     *
     * @param date 日期
     * @param format 格式
     * @return 格式化后的日期
     */
    public static final String dateFormat(long date, String format)
    {
        return dateFormat(new Date(date), format);
    }

    /**
     * 取得当前时间字符串
     *
     * @return 当前时间字符串
     */
    public static final String getNowString()
    {
        Date now = new Date();
        SimpleDateFormat formatter = null;
        formatter =
            new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss.SSS",
                Locale.getDefault());
        return formatter.format(now);
    }

    /**
     * 将当前的时间转换为数字字符串(毫秒级)
     *
     * @return 转换后的字符串
     */
    public static final String dateToMillis()
    {
        Date date = new Date();
        return dateToMillis(date);
    }

    /**
     * 将年月日字符串转换成Calendar.
     * 这里将使用缺省的时区，并且将所有的时间字段都置为最大时间"23:59:59.999"
     * 如果传入的年月日信息为NULL或空值则返回当前日期，
     * 如果参数不正确则返回1970年1月1日
     *
     * @param ymd 代表年月日的字符串,如："1970-1-1"或"1970/1/1"
     * @param flag 年月日字符串的分割符，如在上面的例子中为"-"或"/"
     * @return Calendar实例
     * @see Calendar
     */
    public static final Calendar convert2Calendar(String ymd, String flag)
    {
        if (ymd == null || flag == null || "".equals(ymd) || "".equals(flag))
        {
            return Calendar.getInstance(Locale.getDefault());
        }
        String[] dates = StringUtil.split(ymd, flag);
        int year = 1970;
        int month = 1;
        int date = 1;
        if (dates != null && dates.length == 3)
        {
            try
            {
                year = Integer.parseInt(dates[0]);
                month = Integer.parseInt(dates[1]) - 1;
                if (month < 0 || month > 11)
                {
                    throw new Exception();
                }
                date = Integer.parseInt(dates[2]);
                if (date < 1 || date > 31)
                {
                    throw new Exception();
                }
            }
            catch (Exception ex)
            {
                year = 1970;
                month = 1;
                date = 1;
            }
        }
        Calendar ret = Calendar.getInstance(Locale.getDefault());
        ret.set(year, month, date-1, 23, 59, 59);
        ret.set(Calendar.MILLISECOND, 999);
        return ret;
    }

    /**
     * 完整时间格式转换 包含年月日 时分秒
     * yyyy-MM-dd HH:mm:ss 
     * @param timeStr
     * @return
     */
    public static final Calendar convertCalendarExt(String timeStr) {
		if (StringUtils.isBlank(timeStr)) {
			return Calendar.getInstance(Locale.getDefault());
		}
		String[] dateArrays = StringUtil.split(timeStr, " ");
		
		String dateStr = dateArrays[0];
		String dayStr = dateArrays[1];
		int year = 1970;
		int month = 1;
		int date = 1;
		String[] dateArray = dateStr.split("-");
		if (dateArray != null &&dateArray.length == 3) {
			try {
				year = Integer.parseInt(dateArray[0]);
				month = Integer.parseInt(dateArray[1]) - 1;
				if (month < 0 || month > 11) {
					throw new Exception();
				}
				date = Integer.parseInt(dateArray[2]);
				if (date < 1 || date > 31) {
					throw new Exception();
				}
			} catch (Exception ex) {
				year = 1970;
				month = 1;
				date = 1;
			}
		}
		
		int hour = 23;
		int minute = 59;
		int second = 59;
		String[] dayArray = dayStr.split(":");
		if (dayArray != null &&dayArray.length == 3) {
			try {
				hour = Integer.parseInt(dayArray[0]);
				if (hour < 0 || hour > 24) {
					throw new Exception();
				}
				minute = Integer.parseInt(dayArray[1]) ;
				if (minute < 0 || minute > 60) {
					throw new Exception();
				}
				second = Integer.parseInt(dayArray[2]);
				if (second < 0 || second > 60) {
					throw new Exception();
				}
			} catch (Exception ex) {
				year = 1970;
				month = 1;
				date = 1;
			}
		}
		
		Calendar ret = Calendar.getInstance(Locale.getDefault());
		ret.set(year, month, date, hour, minute, second);
		ret.set(Calendar.MILLISECOND, 999);
		return ret;
	}
    
	public static final Calendar convert2CalendarWithNoSeparate(String ymd) {
		if (StringUtils.isNotBlank(ymd)&&ymd.length()==8) {
			String yearStr = ymd.substring(0,4);
			String monthStr = ymd.substring(4,6);
			String dateStr = ymd.substring(6,8);
			int year = 1970;
			int month = 1;
			int date = 1;
				try {
				year = Integer.parseInt(yearStr);
				month = Integer.parseInt(monthStr) - 1;
				if (month < 0 || month > 11) {
					throw new Exception();
				}
				date = Integer.parseInt(dateStr);
				if (date < 1 || date > 31) {
					throw new Exception();
				}
			} catch (Exception ex) {
				year = 1970;
				month = 1;
				date = 1;
			}
			Calendar ret = Calendar.getInstance(Locale.getDefault());
			ret.set(year, month, date);
			ret.set(Calendar.MILLISECOND, 999);
			return ret;
		}else{
			return null;
			//return Calendar.getInstance(Locale.getDefault());
		}
		
	}
    
	public static final Calendar convert2CalendarExt(String ymd, String flag) {
		if (ymd == null || flag == null || "".equals(ymd) || "".equals(flag)) {
			return null;
		}
		String[] dates = StringUtil.split(ymd, flag);
		int year = 1970;
		int month = 1;
		int date = 1;
		if (dates != null && dates.length == 3) {
			try {
				year = Integer.parseInt(dates[0]);
				month = Integer.parseInt(dates[1]) - 1;
				if (month < 0 || month > 11) {
					throw new Exception();
				}
				date = Integer.parseInt(dates[2]);
				if (date < 1 || date > 31) {
					throw new Exception();
				}
			} catch (Exception ex) {
				year = 1970;
				month = 1;
				date = 1;
			}
		}
		Calendar ret = Calendar.getInstance(Locale.getDefault());
		ret.set(year, month, date - 1, 23, 59, 59);
		ret.set(Calendar.MILLISECOND, 999);
		return ret;
	}

    /**
     * 将年月日字符串转换成Calendar.
     * 这里将使用缺省的时区，并且将所有的时间字段都置为最大时间"23:59:59.999"
     * 如果传入的年月日信息为NULL或空值则返回当前日期，
     * 如果参数不正确则返回1970年1月1日
     *
     * @param ymd 代表年月日的字符串,如："1970-1-1"
     * @return Calendar实例
     * @see Calendar
     */
    public static final Calendar convert2Calendar(String ymd){
		if(ymd.indexOf("-")!=-1){
			  return convert2CalendarExt(ymd, "-");
		}else{
			return convert2CalendarWithNoSeparate(ymd);
		}
      
    }

    /**
     * 将年月日字符串转换成Date.
     * 这里将使用缺省的时区，并且将所有的时间字段都置为最大时间"23:59:59.999"
     * 如果传入的年月日信息为NULL或空值则返回当前日期，
     * 如果参数不正确则返回1970年1月1日
     *
     * @param ymd 代表年月日的字符串,如："1970-1-1"或"1970/1/1"
     * @param flag 年月日字符串的分割符，如在上面的例子中为"-"或"/"
     * @return Date实例
     * @see Date
     */
    public static final Date convert2Date(String ymd, String flag)
    {
        Calendar cal = convert2Calendar(ymd, flag);
        return cal.getTime();
    }

    /**
     * 将年月日字符串转换成Date.
     * 这里将使用缺省的时区，并且将所有的时间字段都置为最大时间"23:59:59.999"
     * 如果传入的年月日信息为NULL或空值则返回当前日期，
     * 如果参数不正确则返回1970年1月1日
     *
     * @param ymd 代表年月日的字符串,如："1970-1-1"
     * @return Date实例
     * @see Date
     */
    public static final Date convert2Date(String ymd)
    {
        return convert2Date(ymd, "-");
    }

    public static final Calendar convert2TimeCalendar(String hms)
    {
        if (hms == null || "".equals(hms))
        {
            return null;
        }
        String[] tmp = StringUtil.split(hms, ":");
        if (tmp == null || tmp.length != 3)
        {
            return null;
        }
        int h = 0;
        int m = 0;
        int s = 0;
        try
        {
            h = Integer.parseInt(tmp[0]);
            m = Integer.parseInt(tmp[1]);
            s = Integer.parseInt(tmp[2]);
        }
        catch (Exception ex)
        {
            return null;
        }
        Calendar today = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.set(
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DATE),
            h,
            m,
            s);
        return cal;
    }

    /**
     * 将指定的时间字符串转换成Date格式
     *
     * @param hms 时间字符串 时:分:秒
     * @return Date格式的时间
     */
    public static final Date convert2Time(String hms)
    {
        Calendar cal = convert2TimeCalendar(hms);
        if (cal == null)
        {
            return null;
        }
        else
        {
            return cal.getTime();
        }
    }

    /**
     * 将日期时间字符串转换为Date类型数据
     * 默认分隔符为","
     *
     * @param datetime 保护日期时间信息的字符串
     * @return Date类型数据
     */
    public static final Date convert2DateTime(String datetime)
    {
        if (datetime == null || "".equals(datetime))
        {
            return null;
        }
        String[] tmp = StringUtil.split(datetime, ",");
        if (tmp == null || tmp.length != 6)
        {
            return null;
        }
        int year = 0;
        int month = 0;
        int date = 0;
        int hour = 0;
        int minute = 0;
        int second = 0;
        try
        {
            year = Integer.parseInt(tmp[0]);
            month = Integer.parseInt(tmp[1]);
            date = Integer.parseInt(tmp[2]);
            hour = Integer.parseInt(tmp[3]);
            minute = Integer.parseInt(tmp[4]);
            second = Integer.parseInt(tmp[5]);
        }
        catch (Exception ex)
        {
            return null;
        }
        Calendar ret = Calendar.getInstance();
        ret.set(year, month - 1, date, hour, minute, second);
        return ret.getTime();
    }

    /**
     * 从指定的日期中获取年份信息
     * @param date 日期
     *
     * @return 年份
     */
    public static int getYear(Date date)
    {
        return getField(date, Calendar.YEAR);
    }

    public static int getYear(long date)
    {
        return getYear(new Date(date));
    }

    /**
     * 获取系统当前年份
     *
     * @return 当前年份
     */
    public static int getYear()
    {
        return getYear(System.currentTimeMillis());
    }

    /**
     * 从指定的日期中获取月份信息
     *
     * @param date 日期
     * @return 月份
     */
    public static int getMonth(Date date)
    {
        return getField(date, Calendar.MONTH) + 1;
    }

    public static int getMonth(long date)
    {
        return getMonth(new Date(date));
    }

    /**
     * 获取系统当前月份
     *
     * @return 当前月份
     */
    public static int getMonth()
    {
        return getMonth(System.currentTimeMillis());
    }

    /**
     * 从指定的日期中获取日信息
     *
     * @param date 日期
     * @return 日
     */
    public static int getDate(Date date)
    {
        return getField(date, Calendar.DATE);
    }

    public static int getDate(long date)
    {
        return getDate(new Date(date));
    }

    /**
     * 获取系统当前的日
     *
     * @return 日
     */
    public static int getDate()
    {
        return getDate(System.currentTimeMillis());
    }

    /**
     * 从字符串中获取日期时间信息
     * 字符串格式："年,月,日,时,分,秒"
     *
     * @param date 日期字符串
     * @return 代表指定日期时间的长整型值
     */
    public static long getTimeInMillis(String date)
    {
        if (date == null || "".equals(date))
        {
            return 0L;
        }
        String[] tmp = null;
        tmp = StringUtil.split(date, ",");
        if (tmp == null)
        {
            return 0L;
        }
        int count = tmp.length;
        if (count != 6)
        {
            return 0L;
        }
        int tmpYear = 0;
        int tmpMonth = 0;
        int tmpDate = 0;
        int tmpHour = 0;
        int tmpMinute = 0;
        int tmpSecond = 0;
        try
        {
            tmpYear = Integer.parseInt(tmp[0]);
            tmpMonth = Integer.parseInt(tmp[1]);
            tmpDate = Integer.parseInt(tmp[2]);
            tmpHour = Integer.parseInt(tmp[3]);
            tmpMinute = Integer.parseInt(tmp[4]);
            tmpSecond = Integer.parseInt(tmp[5]);
        }
        catch (Exception ex)
        {
            return 0L;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(tmpYear, tmpMonth - 1, tmpDate, tmpHour, tmpMinute, tmpSecond);
        return cal.getTime().getTime();
    }

    /**
     * 从指定的字符串中获取日期时间信息
     * 字符串格式："年,月,日,时,分,秒"
     *
     * @param date 日期时间字符串
     * @return 日期
     */
    public static Date getTime(String date)
    {
        return new Date(getTimeInMillis(date));
    }

    /**
     * 从指定的日期中获取相应的信息
     *
     * @param date 日期
     * @param field 信息类型(同Calendar中的定义)
     * @return 日期中指定类型的值
     */
    public static int getField(Date date, int field)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(field);
    }

    /**
     * 将日期时间格式化成"xxxx年xx月xx日 时:分:秒"的格式
     *
     * @param date 要进行转换的日期时间
     * @return 表示日期时间的字符串，格式：xxxx年xx月xx日 时:分:秒
     */
    public static String formatDateTime(Date date)
    {
        if (date == null)
        {
            return null;
        }
        return DateUtils.dateFormat(date, "yyyy年MM月dd日 HH:mm:ss");
    }

    /**
     * 将日期格式化成"xxxx年xx月xx日"的格式
     *
     * @param date 要进行转换的日期
     * @return 表示日期的字符串，格式：xxxx年xx月xx日
     */
    public static String formatDate(Date date)
    {
        if (date == null)
        {
            return null;
        }
        return DateUtils.dateFormat(date, "yyyy年MM月dd日");
    }

    /**
     * 将时间格式化成"时:分:秒"的格式
     *
     * @param date 要进行转换的时间
     * @return 表示时间的字符串，格式：时:分:秒
     */
    public static String formatTime(Date date)
    {
        if (date == null)
        {
            return null;
        }
        return DateUtils.dateFormat(date, "HH:mm:ss");
    }

    /**
     * 根据给定的当前日期计算前一天，后一天
     * @param currentDate  给定的当前日期，如果为null，则取系统的当前时间,格式为
     *                     "yyyy-mm-dd",由调用者保证
     * @return "yyyy-mm-dd"格式的当前日期，前一天，后一天的日期值,数组的第一个值
     *         是前一天的值，第二个值是当前的值，最后一个是后一天的值
     */
    public static String[] getNearDate(String currentDate)
    {
        String[] retVal = new String[3];
        Calendar calendar = Calendar.getInstance();
        if (currentDate != null)
        {
            int year = Integer.parseInt(currentDate.substring(0, 4));
            int month = Integer.parseInt(currentDate.substring(5, currentDate.lastIndexOf("-")));
            int day = 1;
            int timepos = currentDate.lastIndexOf(" ");
            if(timepos>0)
              day = Integer.parseInt(currentDate.substring(currentDate.lastIndexOf("-") + 1,timepos));
            else  day = Integer.parseInt(currentDate.substring(currentDate.lastIndexOf("-") + 1));
            calendar.set(year, month - 1, day);   //月的下标从0开始，所以减1
        }
        retVal[1] = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        retVal[0] = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        retVal[2] = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        return retVal;
    }
    
    /**
     * 求两个日期之间的月数
     * @param time1 起始日期
     * @param time2 结束日期
     * @return int
     */
    public static int getTimeTemp(String time1,String time2){
    	
    	Date date1 = convert2Date(time1,"-");
    	Date date2 = convert2Date(time2,"-");
    	int result = getYear(date2) *12 + getMonth(date2) - getYear(date1)*12 - getMonth(date1);
    	return result; 
    }
    
	/**
	 * 求两个日期之间的天数
	 * @param date1 起始日期
	 * @param date2 结束日期
	 * @return long
	 */
    public static int DateDays(String date1, String date2) {

        long myTime;
        Date aDate2;
        Date aDate;
        long myTime2;
        long days = 0;
       
        try {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            aDate = formatter.parse(date1);
            myTime = (aDate.getTime() / 1000);
            aDate2 = formatter.parse(date2);
            myTime2 = (aDate2.getTime() / 1000);
            days = (myTime2 - myTime) / (1 * 60 * 60 * 24);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return StringUtil.getInt(days+"",0);

    }
    
    /**
     * 获取当前时间之前days天数的的日期
     * @param days
     * @return
     */
    public static Date getBeforeDate(int days){
    	Date dateNow = new Date();
		Calendar cl = Calendar.getInstance();
		cl.setTime(dateNow);
		cl.add(Calendar.DAY_OF_YEAR, -days+1);	//天
//		cl.add(Calendar.WEEK_OF_YEAR, -1);	//一周
//		cl.add(Calendar.MONTH, -1);			//一个月
		return cl.getTime();
    }
    
    /**
     * 获取beginDate到当前日期的时间字符串
     * @param beginDate
     * @return
     */
    public static String getDateStrs(Date beginDate){
		SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = "";
		Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(beginDate);
        endCalendar.setTime(new Date());
        while(true){
            if(startCalendar.getTimeInMillis() < endCalendar.getTimeInMillis()){
            	str+=sdf.format(startCalendar.getTime())+",";
	        }else if(startCalendar.getTimeInMillis() == endCalendar.getTimeInMillis()){
	        	str+=sdf.format(startCalendar.getTime());
	        }else{
	            break;
	        }
            startCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        return str;
	}
}
