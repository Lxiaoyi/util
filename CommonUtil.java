package cn.thinkjoy.qky.xyzk.common;


import cn.thinkjoy.qky.xyzk.domain.QkyCreateBaseDomain;
import cn.thinkjoy.utils.DateUtil;
import com.qtone.open.api.uic.dto.AccountDto;
import com.qtone.open.service.ucm.context.UserContext;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Edwin on 2016/6/1 0001.
 */
public class CommonUtil {
    public static int QUERY=0;
    public static int ADD=1;
    public static int DEL=2;
    public static int UPDATE=3;


    public static String[] opMsg={"查询","新增","删除","更新"};


    /**
     * 返回数据封装
     * @param data
     * @param resultMsg
     * @param isSuc
     * @return
     */
    public  static Map<String, Object>  wrapData(Object data,String resultMsg,boolean isSuc){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resultMsg", resultMsg);
        resultMap.put("result", isSuc?1:0);
        resultMap.put("data", data==null?new ArrayList():data);
        return resultMap;
    }

    /**
     * 返回数据封装
     * @param data
     * @param isSuc
     * @return
     */
    public  static Map<String, Object>  wrapData(Object data,int type,boolean isSuc){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resultMsg", opMsg[type]+(isSuc?"成功":"失败"));
        resultMap.put("result", isSuc?1:0);
        resultMap.put("data", data==null?new ArrayList():data);
        return resultMap;
    }

    /**
     * 返回数据封装
     * @param type
     * @param isSuc
     * @return
     */
    public  static Map<String, Object>  wrapData(int type,boolean isSuc){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resultMsg", opMsg[type]+(isSuc?"成功":"失败"));
        resultMap.put("result", isSuc?1:0);
        resultMap.put("data", new ArrayList());
        return resultMap;
    }


    /**
     * 初始化新增实体需要的一些属性
     * @param base
     */
    public static void initAddEntity(QkyCreateBaseDomain base)  {
        AccountDto currentUser = UserContext.getCurrentUser();
        base.setCreateDate(new Date().getTime());
        base.setLastModDate(new Date().getTime());
        base.setLastModifier(currentUser.getUid());
        base.setCreator(currentUser.getUid());
        base.setStatus(1);
    }

    /**
     * 初始化更新实体需要的一些属性
     * @param base
     */
    public static void initUpdateEntity(QkyCreateBaseDomain base)  {
        AccountDto currentUser = UserContext.getCurrentUser();
        base.setLastModDate(new Date().getTime());
        base.setLastModifier(currentUser.getUid());
    }

    /**
     * 初始化更新实体需要的一些属性
     * @param request
     */
    public static void initUpdateEntity(HttpServletRequest request,Map<String,String> updateMap)  {
        AccountDto currentUser = (AccountDto)request.getSession().getAttribute("account");
        updateMap.put("lastModDate", new Date().getTime() + "");
        updateMap.put("lastModifier", currentUser.getUid());
    }

    // 获取当前时间所在年的周数
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    // 获取当前时间所在年的最大周数
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

        return getWeekOfYear(c.getTime());
    }

    // 获取某年的第几周的开始日期
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getFirstDayOfWeek(cal.getTime());
    }

    // 获取某年的第几周的结束日期
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getLastDayOfWeek(cal.getTime());
    }

    // 获取当前时间所在周的开始日期
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    // 获取当前时间所在周的结束日期
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }
}
