package com.bean;

import utill.ParseFunction;

/**
 * @author d
 * @date 2018/5/14 001416:21
 */
public class BaseConfig {
    public static String browser;
    public static int waitTime;
    public static String mysqlhost;
    public static String mysqlusername;
    public static String mysqlpassword;
    public static String mysqlsql;
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
        ParseFunction p = new ParseFunction();
        p.readXML("src/main/java/config/config.xml");
        browser = p.getElementText("/*/browser");
        waitTime = Integer.valueOf(p.getElementText("/*/waitTime"));
        mysqlhost = p.getElementText("/*/mysqlhost");
        mysqlusername = p.getElementText("/*/mysqlusername");
        mysqlpassword = p.getElementText("/*/mysqlpassword");
        mysqlsql = p.getElementText("/*/mysqlsql");
        filePath=p.getElementText("/*/filePath");
        yamlfile=p.getElementText("/*/yamlfile");
        global=p.getElementText("/*/global");
        emailSender=p.getElementText("/*/emailSender");
        emailAuthorizationCode=p.getElementText("/*/emailAuthorizationCode");
        emailRecipient=p.getElementText("/*/emailRecipient");
        postgresqlhost = p.getElementText("/*/postgresqlhost");
        postgresqlusername = p.getElementText("/*/postgresqlusername");
        postgresqlpassword = p.getElementText("/*/postgresqlpassword");
        postgresqlsql = p.getElementText("/*/postgresqlsql");
    }

}
