package com.ls3.oie.tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import edu.stanford.nlp.util.StringUtils;

public class testing {
	
	public static void main(String args[]){
	    String result="NONE";
		String txt0="11-2-1989";
		String[] arr=txt0.split("-");
		int txm0year=Integer.parseInt(arr[2]);
		int txm0month=Integer.parseInt(arr[0]);
		int txm0day=Integer.parseInt(arr[1]);
		Calendar calendarTMX0 = new GregorianCalendar(txm0year,txm0year,txm0day);
		
		String txtTime1="1989-Q3";
		String txtTime2="1989-11-2";
		
		// For time event 1
		int txm1year=0;
		int txm1month=0;
		int txm1day=0;
		
		
		Calendar calendarTime1_min = new GregorianCalendar();
		Calendar calendarTime1_max = new GregorianCalendar();
		
		String[] arrTime1=txtTime1.split("-");
		
		if (StringUtils.isNumeric(txtTime1)) {
			txm1year=Integer.parseInt(txtTime1);
			txm1month=1;
			txm1day=1;
			calendarTime1_min = new GregorianCalendar(txm0year,txm1month,txm1day);
			calendarTime1_max = new GregorianCalendar(txm0year,txm1month,txm1day);
		}else if (txtTime1.contains("PRESENT_REF")) {
			txm1year=txm0year;
			txm1month=txm0month;
			txm1day=txm0day;
			calendarTime1_min = new GregorianCalendar(txm1year,txm1month,txm1day);
			calendarTime1_max = new GregorianCalendar(txm1year,txm1month,txm1day);
		}else if (arrTime1.length==3) {
			txm1year=Integer.parseInt(arrTime1[0]);
			txm1month=Integer.parseInt(arrTime1[1]);
			txm1day=Integer.parseInt(arrTime1[2]);
			calendarTime1_min = new GregorianCalendar(txm1year,txm1month,txm1day);
			calendarTime1_max = new GregorianCalendar(txm1year,txm1month,txm1day);
		}else if (arrTime1.length==2) {
			if (StringUtils.isNumeric(arrTime1[1])){
				txm1year=Integer.parseInt(arrTime1[0]);
				txm1month=Integer.parseInt(arrTime1[1]);
				txm1day=1;
				calendarTime1_min = new GregorianCalendar(txm1year,txm1month,txm1day);
				calendarTime1_max = new GregorianCalendar(txm1year,txm1month,txm1day);
			}else{
				txm1year=Integer.parseInt(arrTime1[0]);
				String txm1month_txt=arrTime1[1].toLowerCase();
				txm1day=1;
				if(txm1month_txt.indexOf("q1")!=-1){
					calendarTime1_min = new GregorianCalendar(txm1year,1,txm1day);
					calendarTime1_max = new GregorianCalendar(txm1year,3,0);
				}
				if(txm1month_txt.indexOf("q2")!=-1){
					calendarTime1_min = new GregorianCalendar(txm1year,3,txm1day);
					calendarTime1_max = new GregorianCalendar(txm1year,5,0);
				}
				if(txm1month_txt.indexOf("q3")!=-1){
					calendarTime1_min = new GregorianCalendar(txm1year,6,txm1day);
					calendarTime1_max = new GregorianCalendar(txm1year,8,0);
				}
				if(txm1month_txt.indexOf("q4")!=-1){
					calendarTime1_min = new GregorianCalendar(txm1year,9,txm1day);
					calendarTime1_max = new GregorianCalendar(txm1year,12,0);
				}
			}
			
		}else if (txtTime1.indexOf("1M")!=-1&&arrTime1.length==1) {
			txm1year=txm0year;
			txm1month=1;
			txm1day=1;
			calendarTime1_min = new GregorianCalendar(txm1year,txm1month,txm1day);
			calendarTime1_max = new GregorianCalendar(txm1year,txm1month,txm1day);
		}else if (txtTime1.indexOf("2M")!=-1&&arrTime1.length==1) {
			txm1year=txm0year;
			txm1month=2;
			txm1day=1;
			calendarTime1_min = new GregorianCalendar(txm1year,txm1month,txm1day);
			calendarTime1_max = new GregorianCalendar(txm1year,txm1month,txm1day);
		}else if (txtTime1.indexOf("3M")!=-1&&arrTime1.length==1) {
			txm1year=txm0year;
			txm1month=3;
			txm1day=1;
			calendarTime1_min = new GregorianCalendar(txm1year,txm1month,txm1day);
			calendarTime1_max = new GregorianCalendar(txm1year,txm1month,txm1day);
		}else if (txtTime1.indexOf("4M")!=-1&&arrTime1.length==1) {
			txm1year=txm0year;
			txm1month=4;
			txm1day=1;
			calendarTime1_min = new GregorianCalendar(txm1year,txm1month,txm1day);
			calendarTime1_max = new GregorianCalendar(txm1year,txm1month,txm1day);
		}else if (txtTime1.indexOf("5M")!=-1&&arrTime1.length==1) {
			txm1year=txm0year;
			txm1month=5;
			txm1day=1;
			calendarTime1_min = new GregorianCalendar(txm1year,txm1month,txm1day);
			calendarTime1_max = new GregorianCalendar(txm1year,txm1month,txm1day);
		}else if (txtTime1.indexOf("6M")!=-1&&arrTime1.length==1) {
			txm1year=txm0year;
			txm1month=6;
			txm1day=1;
			calendarTime1_min = new GregorianCalendar(txm1year,txm1month,txm1day);
			calendarTime1_max = new GregorianCalendar(txm1year,txm1month,txm1day);
		}else if (txtTime1.indexOf("7M")!=-1&&arrTime1.length==1) {
			txm1year=txm0year;
			txm1month=7;
			txm1day=1;
			calendarTime1_min = new GregorianCalendar(txm1year,txm1month,txm1day);
			calendarTime1_max = new GregorianCalendar(txm1year,txm1month,txm1day);
		}else if (txtTime1.indexOf("8M")!=-1&&arrTime1.length==1) {
			txm1year=txm0year;
			txm1month=8;
			txm1day=1;
			calendarTime1_min = new GregorianCalendar(txm1year,txm1month,txm1day);
			calendarTime1_max = new GregorianCalendar(txm1year,txm1month,txm1day);
		}else if (txtTime1.indexOf("9M")!=-1&&arrTime1.length==1) {
			txm1year=txm0year;
			txm1month=9;
			txm1day=1;
			calendarTime1_min = new GregorianCalendar(txm1year,txm1month,txm1day);
			calendarTime1_max = new GregorianCalendar(txm1year,txm1month,txm1day);
		}else if (txtTime1.indexOf("10M")!=-1&&arrTime1.length==1) {
			txm1year=txm0year;
			txm1month=10;
			txm1day=1;
			calendarTime1_min = new GregorianCalendar(txm1year,txm1month,txm1day);
			calendarTime1_max = new GregorianCalendar(txm1year,txm1month,txm1day);
		}else if (txtTime1.indexOf("11M")!=-1&&arrTime1.length==1) {
			txm1year=txm0year;
			txm1month=11;
			txm1day=1;
			calendarTime1_min = new GregorianCalendar(txm1year,txm1month,txm1day);
			calendarTime1_max = new GregorianCalendar(txm1year,txm1month,txm1day);
		}else if (txtTime1.indexOf("12M")!=-1&&arrTime1.length==1) {
			txm1year=txm0year;
			txm1month=12;
			txm1day=1;
			calendarTime1_min = new GregorianCalendar(txm1year,txm1month,txm1day);
			calendarTime1_max = new GregorianCalendar(txm1year,txm1month,txm1day);
		}
		
		
		// For time event 2
		int txm2year=0;
		int txm2month=0;
		int txm2day=0;
		//String txtTime2="1990";
		
		Calendar calendarTime2_min = new GregorianCalendar();
		Calendar calendarTime2_max = new GregorianCalendar();
		
		String[] arrTime2=txtTime2.split("-");
		
		if (StringUtils.isNumeric(txtTime2)) {
			txm2year=Integer.parseInt(txtTime2);
			txm2month=1;
			txm2day=1;
			calendarTime2_min = new GregorianCalendar(txm2year,txm2month,txm2day);
			calendarTime2_max = new GregorianCalendar(txm2year,txm2month,txm2day);
		}else if (txtTime2.contains("PRESENT_REF")) {
			txm2year=txm0year;
			txm2month=txm0month;
			txm2day=txm0day;
			calendarTime2_min = new GregorianCalendar(txm2year,txm2month,txm2day);
			calendarTime2_max = new GregorianCalendar(txm2year,txm2month,txm2day);
		}else if (arrTime2.length==3) {
			txm2year=Integer.parseInt(arrTime2[0]);
			txm2month=Integer.parseInt(arrTime2[1]);
			txm2day=Integer.parseInt(arrTime2[2]);
			calendarTime2_min = new GregorianCalendar(txm2year,txm2month,txm2day);
			calendarTime2_max = new GregorianCalendar(txm2year,txm2month,txm2day);
		}else if (arrTime2.length==2) {
			if (StringUtils.isNumeric(arrTime2[1])){
				txm2year=Integer.parseInt(arrTime2[0]);
				txm2month=Integer.parseInt(arrTime2[1]);
				txm2day=1;
				calendarTime2_min = new GregorianCalendar(txm2year,txm2month,txm2day);
				calendarTime2_max = new GregorianCalendar(txm2year,txm2month,txm2day);
			}else{
				txm2year=Integer.parseInt(arrTime2[0]);
				String txm2month_txt=arrTime2[1].toLowerCase();
				txm2day=1;
				if(txm2month_txt.indexOf("q1")!=-1){
					calendarTime2_min = new GregorianCalendar(txm2year,1,txm2day);
					calendarTime2_max = new GregorianCalendar(txm2year,3,0);
				}
				if(txm2month_txt.indexOf("q2")!=-1){
					calendarTime2_min = new GregorianCalendar(txm2year,3,txm2day);
					calendarTime2_max = new GregorianCalendar(txm2year,5,0);
				}
				if(txm2month_txt.indexOf("q3")!=-1){
					calendarTime2_min = new GregorianCalendar(txm2year,6,txm2day);
					calendarTime2_max = new GregorianCalendar(txm2year,8,0);
				}
				if(txm2month_txt.indexOf("q4")!=-1){
					calendarTime2_min = new GregorianCalendar(txm2year,9,txm2day);
					calendarTime2_max = new GregorianCalendar(txm2year,12,0);
				}
			}
			
		}else if (txtTime2.indexOf("1M")!=-1&&arrTime2.length==1) {
			txm2year=txm0year;
			txm2month=1;
			txm2day=1;
			calendarTime2_min = new GregorianCalendar(txm2year,txm2month,txm2day);
			calendarTime2_max = new GregorianCalendar(txm2year,txm2month,txm2day);
		}else if (txtTime2.indexOf("2M")!=-1&&arrTime2.length==1) {
			txm2year=txm0year;
			txm2month=2;
			txm2day=1;
			calendarTime2_min = new GregorianCalendar(txm2year,txm2month,txm2day);
			calendarTime2_max = new GregorianCalendar(txm2year,txm2month,txm2day);
		}else if (txtTime2.indexOf("3M")!=-1&&arrTime2.length==1) {
			txm2year=txm0year;
			txm2month=3;
			txm2day=1;
			calendarTime2_min = new GregorianCalendar(txm2year,txm2month,txm2day);
			calendarTime2_max = new GregorianCalendar(txm2year,txm2month,txm2day);
		}else if (txtTime2.indexOf("4M")!=-1&&arrTime2.length==1) {
			txm2year=txm0year;
			txm2month=4;
			txm2day=1;
			calendarTime2_min = new GregorianCalendar(txm2year,txm2month,txm2day);
			calendarTime2_max = new GregorianCalendar(txm2year,txm2month,txm2day);
		}else if (txtTime2.indexOf("5M")!=-1&&arrTime2.length==1) {
			txm2year=txm0year;
			txm2month=5;
			txm2day=1;
			calendarTime2_min = new GregorianCalendar(txm2year,txm2month,txm2day);
			calendarTime2_max = new GregorianCalendar(txm2year,txm2month,txm2day);
		}else if (txtTime2.indexOf("6M")!=-1&&arrTime2.length==1) {
			txm2year=txm0year;
			txm2month=6;
			txm2day=1;
			calendarTime2_min = new GregorianCalendar(txm2year,txm2month,txm2day);
			calendarTime2_max = new GregorianCalendar(txm2year,txm2month,txm2day);
		}else if (txtTime2.indexOf("7M")!=-1&&arrTime2.length==1) {
			txm2year=txm0year;
			txm2month=7;
			txm2day=1;
			calendarTime2_min = new GregorianCalendar(txm2year,txm2month,txm2day);
			calendarTime2_max = new GregorianCalendar(txm2year,txm2month,txm2day);
		}else if (txtTime2.indexOf("8M")!=-1&&arrTime2.length==1) {
			txm2year=txm0year;
			txm2month=8;
			txm2day=1;
			calendarTime2_min = new GregorianCalendar(txm2year,txm2month,txm2day);
			calendarTime2_max = new GregorianCalendar(txm2year,txm2month,txm2day);
		}else if (txtTime2.indexOf("9M")!=-1&&arrTime2.length==1) {
			txm2year=txm0year;
			txm2month=9;
			txm2day=1;
			calendarTime2_min = new GregorianCalendar(txm2year,txm2month,txm2day);
			calendarTime2_max = new GregorianCalendar(txm2year,txm2month,txm2day);
		}else if (txtTime2.indexOf("10M")!=-1&&arrTime2.length==1) {
			txm2year=txm0year;
			txm2month=10;
			txm2day=1;
			calendarTime2_min = new GregorianCalendar(txm2year,txm2month,txm2day);
			calendarTime2_max = new GregorianCalendar(txm2year,txm2month,txm2day);
		}else if (txtTime2.indexOf("11M")!=-1&&arrTime2.length==1) {
			txm2year=txm0year;
			txm2month=11;
			txm2day=1;
			calendarTime2_min = new GregorianCalendar(txm2year,txm2month,txm2day);
			calendarTime2_max = new GregorianCalendar(txm2year,txm2month,txm2day);
		}else if (txtTime2.indexOf("12M")!=-1&&arrTime2.length==1) {
			txm2year=txm0year;
			txm2month=12;
			txm2day=1;
			calendarTime2_min = new GregorianCalendar(txm2year,txm2month,txm2day);
			calendarTime2_max = new GregorianCalendar(txm2year,txm2month,txm2day);
		}		

		/*
		String txt3="1989-Q3";
		String txt4="1989-10";
		String txt5="1989-Q4";
		String txt6="P9M";
		String txt7="1988-Q2";
		*/
		
		
		int yearTime1 = calendarTime1_min.get(Calendar.YEAR);
		int DayofYearTime1_min = calendarTime1_min.get(Calendar.DAY_OF_YEAR);
		int DayofYearTime1_max = calendarTime1_max.get(Calendar.DAY_OF_YEAR);
		
		int yearTime2 = calendarTime2_min.get(Calendar.YEAR);
		int DayofYearTime2_min = calendarTime2_min.get(Calendar.DAY_OF_YEAR);
		int DayofYearTime2_max = calendarTime2_max.get(Calendar.DAY_OF_YEAR);
		
		System.out.println(txtTime1);
		System.out.println(yearTime1);
		System.out.println(calendarTime1_min.get(Calendar.MONTH));
		System.out.println(DayofYearTime1_min);
		System.out.println(DayofYearTime1_max);
		
		System.out.println();
		System.out.println(txtTime2);
		System.out.println(yearTime2);
		System.out.println(calendarTime2_min.get(Calendar.MONTH));;
		System.out.println(DayofYearTime2_min);
		System.out.println(DayofYearTime2_max);
		
		
		if (txtTime1.equals(txtTime2)) {
			result="IDENTITY";
			System.out.println(result);
		}else if (yearTime1>yearTime2) {
			result="AFTER";
			System.out.println(result);
		}else if(yearTime1<yearTime2) {
			result="BEFORE";
			System.out.println(result);
		}else if (DayofYearTime1_min>DayofYearTime2_min) {
			result="AFTER";
			System.out.println(result);
		}else if (DayofYearTime1_min<DayofYearTime2_min) {
			result="BEFORE";
			System.out.println(result);
		}else if (DayofYearTime1_min==DayofYearTime2_min) {
			if (DayofYearTime1_max>DayofYearTime2_max) {
				result="AFTER";
				System.out.println(result);
			}else if (DayofYearTime1_max<DayofYearTime2_max) {
				result="BEFORE";
				System.out.println(result);
			}
		} 
		
		/*
		SimpleDateFormat sdf = new SimpleDateFormat("");	
		Calendar calendar = new GregorianCalendar(1990,5,15);

		int year       = calendar.get(Calendar.YEAR);
		int month      = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
		int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR); 
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); 
		int dayOfWeek  = calendar.get(Calendar.DAY_OF_WEEK);
		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		int weekOfMonth= calendar.get(Calendar.WEEK_OF_MONTH);

		int hour       = calendar.get(Calendar.HOUR);        // 12 hour clock
		int hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
		int minute     = calendar.get(Calendar.MINUTE);
		int second     = calendar.get(Calendar.SECOND);
		int millisecond= calendar.get(Calendar.MILLISECOND);
			
		System.out.println(sdf.format(calendar.getTime()));
			
		System.out.println("year \t\t: " + year);
		System.out.println("month \t\t: " + month);
		System.out.println("dayOfYear \t: " + dayOfYear);
		System.out.println("dayOfMonth \t: " + dayOfMonth);
		System.out.println("dayOfWeek \t: " + dayOfWeek);
		System.out.println("weekOfYear \t: " + weekOfYear);
		System.out.println("weekOfMonth \t: " + weekOfMonth);
			
		System.out.println("hour \t\t: " + hour);
		System.out.println("hourOfDay \t: " + hourOfDay);
		System.out.println("minute \t\t: " + minute);
		System.out.println("second \t\t: " + second);
		System.out.println("millisecond \t: " + millisecond);
		*/
	}
	
}
