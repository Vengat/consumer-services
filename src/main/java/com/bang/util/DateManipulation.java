package com.bang.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.bang.misc.DaySegment;
import com.bang.service.JobService;

public class DateManipulation {
	
	private static final Logger logger = Logger.getLogger(DateManipulation.class);
	
	public static DateTime getYesterdayDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		logger.info("Yesterday "+new Date(cal.getTimeInMillis()));
		return new DateTime(cal.getTimeInMillis());
	}
	
	public static Date getTomorrowDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, +1);
		logger.info("Tomorrow "+new Date(cal.getTimeInMillis()));
		return new Date(cal.getTimeInMillis());
	}
	
	public static boolean validAssignDate(DateTime date) {
		logger.info("date.after(getYesterdayDate()) "+date.isAfter(getYesterdayDate()));
		if(date.isAfter(getYesterdayDate().withZone(date.getZone()))) return true;
		return false;
	}
	
	public static boolean isTodayDate(DateTime date) {
		/*DateFormat dateFormat = new SimpleDateFormat("d");
		return dateFormat.format(date).equals(dateFormat.format(new Date()));*/
		logger.info("new DateTime(date.getZone()).getDayOfMonth() "+new DateTime(date.getZone()).getDayOfMonth());
		logger.info("date.getDayOfMonth() "+date.getDayOfMonth());
		return new DateTime(date.getZone()).getDayOfMonth() == date.getDayOfMonth();
		
	}
	
	public static boolean isSegmentAssignableToday_Obsolete(DateTime date, DaySegment daySegment, TimeZone timeZone) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		DateFormat dfHour = new SimpleDateFormat("HH");
		DateFormat dfMin = new SimpleDateFormat("mm");
		Date nowHourMin = new Date();
		String timeHour = dfHour.format(nowHourMin);
		String timeMinute = dfMin.format(nowHourMin);
		Float time = Float.parseFloat(timeHour) + (Float.parseFloat(timeMinute)/60);
		float offSetDuration = 0;
		if (!timeZone.equals(TimeZone.getDefault()) && TimeZone.getDefault().equals(TimeZone.getTimeZone("UTC"))) {
			offSetDuration = utcTimeZoneOffset(timeZone);
		}
		float offSetTime = offSetDuration + time;
		logger.info(",,,,,,,,,,,,,,,, "+date);
		logger.info("+++++++++++++++++ offSetDuration ++++++++++++   "+offSetDuration);
		logger.info("+++++++++++++++++ offSetTime ++++++++++++   "+offSetTime);
		logger.info(" df.format(date) "+time);
		logger.info(time < 17.50);
		logger.info("daySegment "+DaySegment.valueOf(daySegment.toString()));
		String dSegment = DaySegment.valueOf(daySegment.toString()).getDaySegment();
		logger.info("date.equals(new Date(cal.getTimeInMillis())) "+date.equals(new Date(cal.getTimeInMillis())));
		//Check if the date is today's
		if (isTodayDate(date)) {
			
			if (offSetTime >=0 && offSetTime < 9) {
                if (!dSegment.isEmpty()) {
                    return true;
                }        
			//If the CURRENT time is 9-11 then except 9-11 segment all other greater segments are applicable
			 } else if (offSetTime >= 9 && offSetTime < 11) {
				 if (!dSegment.equals("9-11") || !dSegment.isEmpty()) {
					 return true;
				 }
			 } else if (offSetTime >= 11 && offSetTime < 13) {
				 if (!dSegment.equals("9-11") && !dSegment.equals("11-1") || !dSegment.isEmpty()) {
					 return true;
				 }
			 } else if (offSetTime >= 13 && offSetTime < 15) {
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
	
	public static boolean isSegmentAssignableToday(DateTime date, DaySegment daySegment, TimeZone timeZone) {
		//Check if the date is today's
		DateTime clientDateTime = new DateTime().withZone(DateTimeZone.forTimeZone(timeZone));
		int offSetTime = clientDateTime.getHourOfDay();
		logger.info("clientDateTime "+clientDateTime);
		logger.info("date sent from client" +date);
		logger.info("Hour of day at client zone" +clientDateTime.getHourOfDay());
		logger.info("time zone at client"+clientDateTime.getZone());
		String dSegment = DaySegment.valueOf(daySegment.toString()).getDaySegment();
		if (date.getDayOfMonth() == clientDateTime.getDayOfMonth()) logger.info("It is today!");
		if (isTodayDate(date)) {
			logger.info("Is todays date");
			if (offSetTime >=0 && offSetTime < 9) {
                if (!dSegment.isEmpty()) {
                    return true;
                }        
			//If the CURRENT time is 9-11 then except 9-11 segment all other greater segments are applicable
			 } else if (offSetTime >= 9 && offSetTime < 11) {
				 if (!dSegment.equals("9-11") && !dSegment.isEmpty()) {
					 return true;
				 }
			 } else if (offSetTime >= 11 && offSetTime < 13) {
				 if (!dSegment.equals("9-11") && !dSegment.equals("11-1") && !dSegment.isEmpty()) {
					 return true;
				 }
			 } else if (offSetTime >= 13 && offSetTime < 15) {
				 if (!dSegment.toString().equals("9-11") && !dSegment.toString().equals("11-1") && !dSegment.toString().equals("1-3") && !dSegment.toString().isEmpty()) {
					 return true;
				 }
			 } else {//if (Long.parseLong(time) >= 3 && Long.parseLong(time) < 9) {
				 logger.info("Day is over for services");
				 return false;
			 }
		} 
		return false;
	}
	
	
	public static boolean isDateInFuture(DateTime date) {
		return date.isAfter(new DateTime(date.getZone()));
	}
	
	public static boolean validAssignDateDaySegment(DateTime date, DaySegment daySegment, TimeZone timeZone) {
		 if (!validAssignDate(date)) return false;
		 logger.info("Not yesterday's date");
		 if (isDateInFuture(date)) return true;
		 logger.info("Date not in future");
	     if (!isSegmentAssignableToday(date, daySegment, timeZone)) return false;
	     logger.info("isSegmentAssignableToday ");
		 return true;
	}
	
	public static float utcTimeZoneOffset(TimeZone timeZone) {
		TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
		logger.info("&&&&&&&&&&&&&&&&&&&&&&&    "+timeZone);
		logger.info("Time offset from utc "+(float)timeZone.getOffset(utcTimeZone.getRawOffset())/3600000);
		return (float)timeZone.getOffset(utcTimeZone.getRawOffset())/3600000;
	}
	
	/**
	 * The above method and this method seems to do the same thing
	 * @param timeZone
	 * @return
	 */
	public static float timeZoneOffset(TimeZone timeZone) {
		return (float) timeZone.getOffset(TimeZone.getDefault().getRawOffset())/3600000;
	}
	
	
/*
 * 	public static boolean isSegmentAssignableToday(Date date, DaySegment daySegment, TimeZone timeZone) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		DateFormat df = new SimpleDateFormat("HH");
		String time = df.format(date);
		float offSetDuration = utcTimeZoneOffset(timeZone);
		float offSetTime = offSetDuration + Float.parseFloat(time);
		logger.info(" df.format(date) "+df.format(date));
		logger.info("daySegment "+DaySegment.valueOf(daySegment.toString()));
		String dSegment = DaySegment.valueOf(daySegment.toString()).getDaySegment();
		logger.info("date.equals(new Date(cal.getTimeInMillis())) "+date.equals(new Date(cal.getTimeInMillis())));
		//Check if the date is today's
		if (isTodayDate(date)) {
			
			if (Long.parseLong(time) >=0 && Long.parseLong(time) < 9) {
                if (!dSegment.isEmpty()) {
                    return true;
                }        
			//If the CURRENT time is 9-11 then except 9-11 segment all other greater segments are applicable
			 } else if (Long.parseLong(time) >= 9 && Long.parseLong(time) < 11) {
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

 */
}
