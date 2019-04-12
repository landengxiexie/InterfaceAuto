package com.testcases;

import autoitx4java.AutoItX;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.Assert;
import org.testng.annotations.Test;
import sun.java2d.pipe.AAShapePipe;
import utill.*;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Driver;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CheckedOutputStream;

@SuppressWarnings("ALL")
public class Test001 {

    @Test
    public void aa() {

        AutoItX x = new AutoItX();
        String notepad = "无标题";
        String testString = "this is a test.";
// x.run("notepad.exe");
        x.run("notepad", "C:/Windows/System32", AutoItX.SW_MAXIMIZE);


    }

    @Test
//    运行，PanTools
    public void aaa() throws InterruptedException {
        AutoItX x = new AutoItX();
        x.run("cmd /k java -jar  c:/Users/丹哥/Desktop/PanTools-0.1.1.jar");


        String aaa = x.winGetHandle("工具");

        Thread.sleep(1000);

        x.mouseClick("left", 860, 610, 1, 10);

        Thread.sleep(1000);

        x.mouseClick("left", 720, 315, 2, 10);
        Thread.sleep(1000);
        String aa1 = x.winGetHandle("发送Syslog测试");
        System.out.println(aa1);
        x.ControlSetText("SunAwtDialog", "", aa1, "111");


    }


    @Test
    public void openApplication() {
        try {
            String[] cmd = new String[3];
            cmd[0] = "cmd";
            cmd[1] = "/c";
            cmd[2] = "java -jar  c:/Users/丹哥/Desktop/PanTools-0.1.1.jar";
            Runtime.getRuntime().exec(cmd);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void containsTest() {
        String a = "9531_编辑画布视图名称";
        String b = "编辑画布视图名称";
        boolean contains = a.contains(b);
        System.out.println(contains);
    }


    @Test
    public void xx1() {
        String aa = "显示第 1 至 50 项结果，共 421 项";
        String[] split = aa.split(" ");
        Integer.valueOf(split[5]);
        for (String a : split) {
            System.out.println(a);
        }
        System.out.println(split[5]);

//        this.assertEquals(2,3 );
    }


    public static void assertEquals(Object actual, Object expected) {
        try {
            Assert.assertEquals(actual, expected);
            System.out.println("的萨菲");
        } catch (Error e) {
            LogFunction.logInfo("广泛地");
//            e.printStackTrace();

        }
    }


    @Test
    public void xx2() {
        try {
            WebDriver driver = null;
            AssertFunction.verifyEquals(driver, 1, 1);
            System.out.println(1111);
        } catch (Exception e) {
            WebDriver driver = null;
            AssertFunction.verifyEquals(driver, 1, 1);
            System.out.println(222);
        }
//        this.assertEquals(2,3 );
    }


    @Test
    public void aaaaa() {
        String text = "显示第 1 至 50 项结果，共 16 项";
        String[] split = text.split(" ");
        String s1 = split[5];
        String num = "";
        if (s1.contains(",")) {
            String[] split2 = s1.split(",");
            for (int i = 0; i < split2.length; i++) {
                num += split2[i];
            }
            Integer total = Integer.valueOf(num);
            LogFunction.logInfo("历史告警总数是：" + total);
        } else {
            LogFunction.logInfo("历史告警总数是：" + s1);
        }
    }

    @Test
    public void dd() {
        String text3 = "显示第 1 至 2,5161 项结果，共 2,516 项";
        String[] split111 = text3.split(" ");
        String s11 = split111[3];
        String num = "";
        if (s11.contains(",")) {
            String[] split22 = s11.split(",");
            for (int i = 0; i < split22.length; i++) {
                num += split22[i];
            }
            Integer total = Integer.valueOf(num);
            LogFunction.logInfo("历史告警总数是：" + total);
        } else {
            LogFunction.logInfo("历史告警总数是：" + s11);
        }

    }

    @Test
    public void aaaa() {

    }

    public static String getClassResource(Class<?> klass) {
        return klass.getClassLoader().getResource(
                klass.getName().replace('.', '/') + ".class").toString();
    }


    @Test
    public void aa11() {
        SeleniumDriver s = new SeleniumDriver();
        WebDriver driver = s.getDriver();
        int a, b, c, d;
        a = 1;
        b = 2;
        c = 3;
        d = 4;
        try {
            AssertFunction.assertEquals(driver, a, b);
        } catch (Exception e) {
            try {
                AssertFunction.assertEquals(driver, a, c);
            } catch (Exception e1) {
                AssertFunction.assertEquals(driver, a, d);
            }
        }
    }


    @Test
    public void aa1() {
        SeleniumDriver s = new SeleniumDriver();
        WebDriver driver = s.getDriver();
        Set<String> w = driver.getWindowHandles();

        String aa[] = new String[5];
        aa[0] = "String1";
        aa[1] = "String2";
        aa[2] = "String3";
        aa[3] = "String4";
        aa[4] = "String5";

        for (String a : aa) {
            System.out.println(a.toString());
        }
    }

    @Test
    public void a12() throws InterruptedException {
        SeleniumDriver s = new SeleniumDriver();
        WebDriver driver = s.getDriver();
        driver.get("http://ytcstest.lenovo.net");
        Thread.sleep(3000);
        WebElement element = driver.findElement(By.xpath("/html/body/div[5]/div/div[2]/div/ul/li[2]/a/div[2]/h3"));
        element.click();
    }


    @Test
    public void a123() {
        LinuxExecute l = new LinuxExecute();
        String cd_aa = l.run("ls");
        System.out.println(cd_aa);
    }

    @Test
    public void a1231() {
        String[][] a = {{"zhansan", "11111"}, {"lisi", "22222"}, {"xiaowu", "33333"}};
        for (int i = 0; i < a.length; i++) {
//            用户名
            System.out.println("第" + i + "位，用户名为：" + a[i][0]);
//            密码
            System.out.println("第" + i + "位，密码为：" + a[i][1]);
        }
    }

    @Test
    public void a1232() {
        String regex = "[1][234][0-9]{9}";
        String p = "12123123123";
        boolean matches = p.matches(regex);
        System.out.println(matches);
    }

    @Test
    public void a1233() {
        String regex = "[0-9]{17}[0-9X]";
        String p = "12345678912345678X";
        boolean matches = p.matches(regex);
        System.out.println(matches);
    }

    @Test
    public void a1234() {
//        List<String> list = new ArrayList<String>();
//        Pattern p = Pattern.compile("<img.*?>");
//        Matcher m = p.matcher("<img,sfsd><fsdf><imgfasfsdf>");
//        while (m.find()) {
//            list.add(m.group().substring(0, m.group().length() - 0));
//        }
//        for (int i = 0; i < list.size(); i++) {
//            String s = list.get(i);
//            System.out.println(s);
//
//        }
        String pageSource = readToString("C:\\Users\\丹哥\\Desktop\\123123.txt");
        List<String> list = new ArrayList<String>();
        Pattern p = Pattern.compile("<img.*?>");
        Matcher m = p.matcher(pageSource);
        while (m.find()) {
            list.add(m.group().substring(0, m.group().length() - 0));
        }
        System.out.println("数量为" + list.size());
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            System.out.println(s);
        }
    }

    public static String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }


    @Test
    public void a1236() {
        int[] arr = {122, 23, 32, 562, 56, 32, 78};
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];

                    arr[j] = arr[j + 1];

                    arr[j + 1] = temp;
                }
            }
        }
        System.out.println(arr[0]);
    }

    @Test
    public void bubbleSort() {
        int[] arr = {11, 33, 22, 55, 44, 6};
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        for (int a : arr) {
            System.out.println(a);
        }
    }

    @Test
    public void getEven() {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            if (i % 2 == 0) {
                integers.add(i);
            }
        }
        for (int a : integers) {
            System.out.println(a);
        }
    }

    @Test
    public void getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        String n = df.format(date);
        System.out.println("系统当前时间为：" + n);
        String f = df.format(date.getTime() + 60 * 1000);
        System.out.println("系统当前时间1分钟后的时间为" + f);
    }

    @Test
    public void getPostgreSQLResult() {

        ConnectPostgreSQL cp = new ConnectPostgreSQL();
        Object[][] query = cp.query(" select t.arising_time,t.instance_id,t.kpi_code,t.ci_id,t.agent_id from t_kpi_current t where t.kpi_code=1101020708 and t.ci_id = 381700460870000 and arising_time BETWEEN '2019-03-15 01:00:00' and '2019-03-15 01:10:00'  ORDER BY arising_time desc ");
        LogFunction.logInfo("---------------------------查询结果1------------------------------");

        for (int i = 0; i < query.length; i++) {
            for (int j = 0; j < query[i].length; j++) {
                LogFunction.logInfo(query[i][j]);
            }
        }
        LogFunction.logInfo("---------------------------查询结果1，共：" + query.length + "条------------------------------");


        Object[][] query1 = cp.query("select t.arising_time,t.instance_id,t.kpi_code,t.ci_id,t.agent_id from t_kpi_current t where t.kpi_code=1102080203 and t.ci_id = 378770219510000 and arising_time BETWEEN '2019-03-15 01:00:00' and '2019-03-15 01:10:00'  ORDER BY arising_time desc");

        LogFunction.logInfo("---------------------------查询结果2------------------------------");
        for (int i = 0; i < query1.length; i++) {
            for (int j = 0; j < query1[i].length; j++) {
                LogFunction.logInfo(query1[i][j]);
            }
        }
        LogFunction.logInfo("---------------------------查询结果2，共：" + query1.length + "条------------------------------");

    }

    @Test
    public static String getPrettyNumber(String number) {
        return BigDecimal.valueOf(Double.parseDouble(number))
                .stripTrailingZeros().toPlainString();
    }

    public static void main(String[] args) {
        String intNumber = "00012340.1100";
        System.out.println(getPrettyNumber(intNumber));
    }










}






