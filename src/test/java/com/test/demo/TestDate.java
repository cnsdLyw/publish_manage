package com.test.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.litc.common.util.StringUtil;

public class TestDate {
	public static void main(String[] args) {
		String dateStr = "2016-03-31 10:05:49";
		
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar cal = convertCalendarExt(dateStr);
		
		System.out.println("  "+sFormat.format(cal.getTime()));

	}

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
}
