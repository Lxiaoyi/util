package cn.thinkjoy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期,时间 工具类
 */
public class DateUtil {
	public static String defaultDatePattern = "yyyy-MM-dd";
	public static String longDatePattern = "yyyy-MM-dd HH:mm:ss";
	
	 /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return defaultDatePattern;
    }

    /**
     * 返回预设Format的当前日期字符串
     */
    public static String getToday() {
        Date today = new Date();
        return format(today);
    }

    /**
     * 使用预设Format格式化Date成字符串
     */
    public static String format(Date date) {
        return format(date, getDatePattern());
    }

    /**
     * 使用参数Format格式化Date成字符串
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";

        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }

        return (returnValue);
    }

    /**
     * 使用预设格式将字符串转为Date
     */
    public static Date parse(String strDate) throws ParseException {
        return parse(strDate, getDatePattern());
    }

    /**
     * 使用参数Format将字符串转为Date
     */
    public static Date parse(String strDate, String pattern) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.parse(strDate);
    }
    
    /**
     * 使用预设格式判断指定字符串是否可转换为日期
     */
    public static boolean parsAble(String strDate, String pattern) {
    	SimpleDateFormat df = new SimpleDateFormat(pattern);
    	try {
    		df.parse(strDate);
		} catch (Exception e) {
			return false;
		}
		return true;
    }

    /**
     * 在日期上增加数个整月
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }
    
    /**
     * 在日期上增加数分钟
     */
    public static Date addMinute(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, n);
        return cal.getTime();
    }
    
    /**
     * 在日期上增加数个整日(n为负数则是减少数日)
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, n);
        return cal.getTime();
    }
    
    /**
     * 获取给定日期所在周的星期一
     * */
    public static Date getMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, -1 * (dayOfWeek - 2));
		return cal.getTime();
	}

    /**
     * 获取给定日期年
     * */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取给定日期月
     * */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取给定日期日
     * */
    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }
	
	/**
	 * date1比date2大n天/小时/分钟/秒
	 * @param date1
	 * @param date2
	 * @param type y:年,M:月,D:日,H:小时,m:分钟,S:秒
	 * @return
	 */
     public static int minusDate(Date date1, Date date2, Character type) {
		Long timeMinus = date1.getTime() - date2.getTime();
		Long result = -1L;
		switch (type) {
        case 'y':
            result = new Long(getYear(date1) - getYear(date2));
            break;
        case 'M':
            result = new Long(getMonth(date1) - getMonth(date2));
            break;
		case 'D':
			result = timeMinus/1000/60/60/24;
			break;
		case 'H':
			result = timeMinus/1000/60/60;
			break;
		case 'm':
			result = timeMinus/1000/60;
			break;
		case 'S':
			result = timeMinus/1000;
			break;
		default:
			break;
		}
		return result.intValue();
	}
    
     public static Date parseDate(String str, String[] parsePatterns) throws ParseException {
		return DateUtil.parseDate(str, parsePatterns);
	}
	
	
	
	
}
