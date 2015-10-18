package com.wanma.client.utils;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class DateUtils {

	public static String formatDateToString(Date date){
		return DateTimeFormat.getFormat("yyyy年MM月dd日HH时").format(date);
	}
	
}
