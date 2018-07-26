package com.config;

import com.utils.ParseXML;

/**
 * @author d
 * @date 2018/5/14 001416:21
 */
public class BaseConfig {
    public static String path;
    public static String url;
    public static String beanName;
    public static String url2;
    public static String beanName2;
    public static String url3;
    public static String beanName3;
    public static String url4;
    public static String beanName4;
    public static String url5;
    public static String beanName5;
    public static String url6;
    public static String beanName6;
    public static String beanName66;
    public static String filePath;
    public static String yamlfile;
    public static String global;
    public static String emailSender;
    public static String emailAuthorizationCode;
    public static String emailRecipient;
    public static String postgresqlhost;
    public static String postgresqlusername;
    public static String postgresqlpassword;
    public static String postgresqlsql;


    static {
        ParseXML p = new ParseXML();
        p.readXML("src/config/baseConfig.xml");
        path = p.getElementText("*/caseDataPath");
        url=p.getElementText("*/url");
        beanName=p.getElementText("*/beanName");
        url2=p.getElementText("*/url2");
        beanName2=p.getElementText("*/beanName2");
        url3=p.getElementText("*/url3");
        beanName3=p.getElementText("*/beanName3");
        url4=p.getElementText("*/url4");
        beanName4=p.getElementText("*/beanName4");
        url5=p.getElementText("*/url5");
        beanName5=p.getElementText("*/beanName5");
        url6=p.getElementText("*/url6");
        beanName6=p.getElementText("*/beanName6");
        beanName66=p.getElementText("*/beanName66");

    }

/*    public static void main(String[] args) {
        System.out.println(browser + waitTime+mysqlhost+mysqlusername+mysqlpassword+mysqlsql);
    }*/
}
