package com.bang.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.bang.misc.DaySegment;
import com.bang.service.JobService;

public class DateManipulation {
	
	private static final Logger logger = Logger.getLogger(DateManipulation.class);
	
	public static Date getYesterdayDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		logger.info("Yesterday "+new Date(cal.getTimeInMillis()));
		return new Date(cal.getTimeInMillis());
	}
	
	public static Date getTomorrowDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, +1);
		logger.info("Tomorrow "+new Date(cal.getTimeInMillis()));
		return new Date(cal.getTimeInMillis());
	}
	
	public static boolean validAssignDate(Date date) {
		logger.info("date.after(getYesterdayDate()) "+date.after(getYesterdayDate()));
		if(date.after(getYesterdayDate())) return true;
		return false;
	}
	
	public static boolean isTodayDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("d");
		return dateFormat.format(date).equals(dateFormat.format(new Date()));
	}
	
	public static boolean isSegmentAssignableToday(Date date, DaySegment daySegment) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		DateFormat df = new SimpleDateFormat("h");
		String time = df.format(date);
		logger.info(" df.format(date) "+df.format(date));
		logger.info("daySegment "+DaySegment.valueOf(daySegment.toString()));
		String dSegment = DaySegment.valueOf(daySegment.toString()).getDaySegment();
		logger.info("date.equals(new Date(cal.getTimeInMillis())) "+date.equals(new Date(cal.getTimeInMillis())));
		//Check if the date is today's
		if (isTodayDate(date)) {
			//If the CURRENT time is 9-11 then except 9-11 segment all other greater segments are applicable
			 if (Long.parseLong(time) >= 9 && Long.parseLong(time) < 11) {
				 if (!dSegment.equals("9-11") || !dSegment.isEmpty()) {
					 return true;
				 }
			 } else if (Long.parseLong(time) >= 11 && Long.parseLong(time) < 1) {
				 if (!dSegment.equals("9-11") && !dSegment.equals("11-1") || !dSegment.isEmpty()) {
					 return true;
				 }
			 } else if (Long.parseLong(time) >= 1 && Long.parseLong(time) < 3) {
				 if (!dSegment.toString().equals("9-11") && !dSegment.toString().equals("11-1") && !dSegment.toString().equals("1-3") || !dSegment.toString().isEmpty()) {
					 return true;
				 }
			 } else {//if (Long.parseLong(time) >= 3 && Long.parseLong(time) < 9) {
				 logger.info("Day is over for services");
				return false;
			 }
		} 
		return false;
	}
	
	public static boolean isDateInFuture(Date date) {
		return date.after(new Date());
	}
	
	public static boolean validAssignDateDaySegment(Date date, DaySegment daySegment) {
		 if (!validAssignDate(date)) return false;
		 logger.info("Not yesterday's date");
		 if (isDateInFuture(date)) return true;
	     if (!isSegmentAssignableToday(date, daySegment)) return false;
	     logger.info("isSegmentAssignableToday ");
		 return true;
	}

}
