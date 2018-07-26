package com.utils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author d
 * @date 2018/4/25 002518:11
 */
public class TimeString {
    /**
     * @param str 被补零的字符串
     * @return String
     * @throws
     * @Title: valueOfString
     * @Description: 在参数 str 前面 补 len 个 0
     */
    private String valueOfString(String str, int len) {
        String string = "";
        for (int i = 0; i < len - str.length(); i++) {
            string = string + "0";
        }
        return (str.length() == len) ? (str) : (string + str);
    }

    /**
     * @param
     * @return String
     * @throws
     * @Title: getSimpleDateFormat
     * @Description: 返回当前时间， 格式为 2015-04-23 17:16:34
     */
    public String getSimpleDateFormat() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    /**
     * 返回当前时间戳
     *
     * @return String
     */
    public String getTime() {
        return String.valueOf(new Date().getTime());
    }

    /**
     * 生成一个长度为 17 的时间字符串，精确到毫秒
     *
     * @return String
     */
    public String getTimeString() {
        Calendar calendar = new GregorianCalendar();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = this.valueOfString(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2);
        String day = this.valueOfString(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2);
        String hour = this.valueOfString(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)), 2);
        String minute = this.valueOfString(String.valueOf(calendar.get(Calendar.MINUTE)), 2);
        String second = this.valueOfString(String.valueOf(calendar.get(Calendar.SECOND)), 2);
        String millosecond =
                this.valueOfString(String.valueOf(calendar.get(Calendar.MILLISECOND)), 3);
        return year + month + day + hour + minute + second + millosecond;
    }

    /**
     * @param args
     */
/*    public static void main(String[] args) {
        TimeString ts = new TimeString();
        System.out.println(ts.valueOfString("adnfi5", 8));
        System.out.println(ts.getTime());
        System.out.println(ts.getSimpleDateFormat());
        System.out.println(ts.getTimeString());
    }*/
}