package net.xxs.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtil
{
	public static String getNowTime(){
		Date now = new Date();//声明一个日期[当前日期]
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//添加不同的参数，显示不同格式的日期
		return simpleDateFormat.format(now).toString();
	  }
}

