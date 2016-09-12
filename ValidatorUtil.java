package cn.thinkjoy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据类型验证工具<p/>
 * 创建时间: 16/2/3<br/>
 *
 * @author xule
 * @since v0.0.1
 */
public class ValidatorUtil {

    /**
     * 是否是整数
     * @param text
     */
    public static boolean isInt(String text) {
        Pattern p = Pattern.compile("^-?[1-9]\\d*$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是正整数
     * @param text
     */
    public static boolean isIntPos(String text) {
        Pattern p = Pattern.compile("^[1-9]\\d*$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是负整数
     * @param text
     */
    public static boolean isIntNeg(String text) {
        Pattern p = Pattern.compile("^-[1-9]\\d*$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是数字
     * @param text
     */
    public static boolean isNum(String text) {
        Pattern p = Pattern.compile("^([+-]?)\\d*\\.?\\d+$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是正数
     * @param text
     */
    public static boolean isNumPos(String text) {
        Pattern p = Pattern.compile("^[1-9]\\d*\\.?\\d+$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是负数
     * @param text
     */
    public static boolean isNumNeg(String text) {
        Pattern p = Pattern.compile("^-[1-9]\\d*\\.?\\d+$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是浮点数
     * @param text
     */
    public static boolean isDecimal(String text) {
        Pattern p = Pattern.compile("^([+-]?)\\d*\\.\\d+$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是正浮点数
     * @param text
     */
    public static boolean isDecimalPos(String text) {
        Pattern p = Pattern.compile("^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是负浮点数
     * @param text
     */
    public static boolean isDecimalNeg(String text) {
        Pattern p = Pattern.compile("^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是邮件
     * @param text
     */
    public static boolean isEmail(String text) {
        Pattern p = Pattern.compile("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是颜色
     * @param text
     */
    public static boolean isColor(String text) {
        Pattern p = Pattern.compile("^[a-fA-F0-9]{6}$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是url
     * @param text
     */
    public static boolean isUrl(String text) {
        Pattern p = Pattern.compile("^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是中文
     * @param text
     */
    public static boolean isChinese(String text) {
        Pattern p = Pattern.compile("^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是ACSII字符
     * @param text
     */
    public static boolean isAscii(String text) {
        Pattern p = Pattern.compile("^[\\x00-\\xFF]+$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是邮编
     * @param text
     */
    public static boolean isZipcode(String text) {
        Pattern p = Pattern.compile("^\\d{6}$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是手机号码
     * @param text
     */
    public static boolean isMobile(String text) {
        Pattern p = Pattern.compile("^[1][3578][0-9]{9}$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是ipv4地址
     * @param text
     */
    public static boolean isIpV4(String text) {
        Pattern p = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是非空
     * @param text
     */
    public static boolean isNotEmpty(String text) {
        Pattern p = Pattern.compile("^\\S+$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是图片
     * @param text
     */
    public static boolean isPicture(String text) {
        Pattern p = Pattern.compile("(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是压缩文件
     * @param text
     */
    public static boolean isRar(String text) {
        Pattern p = Pattern.compile("(.*)\\.(rar|zip|7zip|tgz)$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是日期
     * @param text
     */
    public static boolean isDate(String text, String format) {
        return DateUtil.parsAble(text, format);
    }

    /**
     * 是否是QQ号码
     * @param text
     */
    public static boolean isQQ(String text) {
        Pattern p = Pattern.compile("^[1-9]*[1-9][0-9]*$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是电话号码的函数(包括验证国内区号,国际区号,分机号)
     * @param text
     */
    public static boolean isTel(String text) {
        Pattern p = Pattern.compile("^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是字母
     * @param text
     */
    public static boolean isLetter(String text) {
        Pattern p = Pattern.compile("^[A-Za-z]+$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是大写字母
     * @param text
     */
    public static boolean isLetterUpper(String text) {
        Pattern p = Pattern.compile("^[A-Z]+$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 是否是小写字母
     * @param text
     */
    public static boolean isLetterLower(String text) {
        Pattern p = Pattern.compile("^[a-z]+$");
        Matcher m = p.matcher(text);
        return m.matches();
    }
}
