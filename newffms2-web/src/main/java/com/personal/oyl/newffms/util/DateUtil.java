package com.personal.oyl.newffms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public String format(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public Date parse(String param, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(param);
	}
    
    public Date getFirstTimeOfCurrentMonth() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        return c.getTime();
    }
    
    public Date getFirstTimeOfLastMonth() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        return c.getTime();
    }
    
    public Date getLastTimeOfCurrentMonth() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DAY_OF_YEAR, -1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        
        return c.getTime();
    }
    
    public Date getLastTimeOfLastMonth() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.DAY_OF_YEAR, -1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        
        return c.getTime();
    }
    
    public Date getBeginTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        return c.getTime();
    }
    
    public Date getEndTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        
        return c.getTime();
    }
    
    public Date getFirstTimeOfYear(String year) {
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(year), 0, 1, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        return c.getTime();
    }
    
    public Date getLastTimeOfYear(String year) {
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(year), 11, 31, 23, 59, 59);
        c.set(Calendar.MILLISECOND, 999);
        
        return c.getTime();
    }
    
    public Date getNextMonthTime(Date param) {
        Calendar c = Calendar.getInstance();
        c.setTime(param);
        c.add(Calendar.MONTH, 1);
        
        return c.getTime();
    }
    
    public String getYear(Date param) {
        Calendar c = Calendar.getInstance();
        c.setTime(param);
        
        return Integer.toString(c.get(Calendar.YEAR));
    }
    
    public Date getFirstTimeOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.MONTH, (month -1) );
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.YEAR, year);
        
        return c.getTime();
    }
    
    public Date getLastTimeOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.MONTH, (month -1) );
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DAY_OF_YEAR, -1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        c.set(Calendar.YEAR, year);
        
        return c.getTime();
    }
    
    public static void main(String[] args) {
        System.out.println(DateUtil.getInstance().getFirstTimeOfCurrentMonth());
        System.out.println(DateUtil.getInstance().getFirstTimeOfLastMonth());
        System.out.println(DateUtil.getInstance().getLastTimeOfCurrentMonth());
        System.out.println(DateUtil.getInstance().getLastTimeOfLastMonth());
        System.out.println(DateUtil.getInstance().getBeginTime(new Date()));
        System.out.println(DateUtil.getInstance().getEndTime(new Date()));
    }

    private static DateUtil instance;
    private DateUtil() {
        
    }
    
    public static DateUtil getInstance() {
        if (null == instance) {
            synchronized(DateUtil.class) {
                if (null == instance) {
                    instance = new DateUtil();
                }
            }
        }
        
        return instance;
    }
}
