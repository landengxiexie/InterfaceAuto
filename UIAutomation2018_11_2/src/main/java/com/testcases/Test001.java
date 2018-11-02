package com.testcases;

import autoitx4java.AutoItX;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import sun.java2d.pipe.AAShapePipe;
import utill.AssertFunction;
import utill.LogFunction;
import utill.ScreenshotFunction;

import java.io.IOException;
import java.sql.Driver;

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
        }else {
            LogFunction.logInfo("历史告警总数是：" + s1);
        }
    }
    @Test
    public void dd(){
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
        }else {
            LogFunction.logInfo("历史告警总数是：" + s11);
        }

    }

}
