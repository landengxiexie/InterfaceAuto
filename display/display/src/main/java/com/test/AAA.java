package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
public class AAA {
    public static void main(String[] args) {

        String msg = "PerformanceManager(265)Product[第2个中括号]<[第3个中括号]79~";
        String s = extractMessageByRegular1(msg);

            System.out.println(s);

    }

    /**
     * 使用正则表达式提取中括号中的内容
     *
     * @param msg
     * @return
     */
    public static List<String> extractMessageByRegular(String msg) {

        List<String> list = new ArrayList<String>();
        Pattern p = Pattern.compile("(?<=\\()\\S+(?=\\))");
        Matcher m = p.matcher(msg);
        while (m.find()) {
            list.add(m.group().substring(1, m.group().length() - 1));
        }
        return list;
    }
    public static String extractMessageByRegular1(String msg) {
        List<String> list = new ArrayList<String>();
        Pattern p = Pattern.compile("(?<=\\()\\S+(?=\\))");
        Matcher m = p.matcher(msg);
        while (m.find()) {
            list.add(m.group().substring(0, m.group().length() - 0));
        }
        String s = list.get(0).toString();
        return s;
    }


}
