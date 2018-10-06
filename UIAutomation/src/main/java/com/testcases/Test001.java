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
    public void xx1(){
        this.assertEquals(2,3 );
    }


    public static void assertEquals(Object actual,Object expected){
        try {
            Assert.assertEquals(actual,expected);
            System.out.println("的萨菲");
        }catch (Error e){
            LogFunction.logInfo("广泛地");
//            e.printStackTrace();

        }
    }
}
