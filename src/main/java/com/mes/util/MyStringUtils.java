package com.mes.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

final public class MyStringUtils {
	//默认处理日期
	private static String defaultDateFormat="yyyy-MM-dd";
	
	//日期转字符串
	public static String date2String(Object... obj) {
		Date date=null;
		String format=null;
		if(obj!=null&&obj.length>0) { 
			//如果是一个�?? date  defaultDateFormat
			if(obj[0]!=null&&obj[0] instanceof Date) {
				date=(Date) obj[0];
			}
			//format
			if(obj[1]!=null&&obj[1] instanceof String) {
				//这里可以用正则表达式
				format=(String) obj[1];
			}else {
				format=defaultDateFormat;
			}
		}
		if(date!=null) {
			SimpleDateFormat sdf=new SimpleDateFormat(format);
			return sdf.format(date);
		}
		throw new RuntimeException("日期格式有问题，请检查传入参�?");
	}
	//字符串转日志
	public static Date string2Date(String date,String format) {
		try {
			Date dateTemp=null;
			if(date!=null&&date.length()>0) {
				if(format==null) format=defaultDateFormat;
				SimpleDateFormat sdf=new SimpleDateFormat(format);
				dateTemp=sdf.parse(date);
			}
			return dateTemp;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
}
