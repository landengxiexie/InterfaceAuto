package com.test.testcases;

import com.test.utill.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

@SuppressWarnings("ALL")
public class VicubeConcentratedAlarm {
    public WebDriver driver;
    private LocatorFunction l;
    private ScreenshotFunction ssf;

    @Test(dataProvider = "xmldata")
    public void concentratedAlarm(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，集中告警
        WebElement alarmClassifyRules = l.getElement(param.get("concentratedAlarm"));
        String text = alarmClassifyRules.getText();
        alarmClassifyRules.click();
        LogFunction.logInfo("点击："+text);
        AssertFunction.assertEquals(driver,text,"集中告警");
    }

    @Test(dataProvider = "xmldata")
    public void alarmConfig(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，告警配置
        WebElement alarmClassifyRules = l.getElement(param.get("alarmConfig"));
        String text = alarmClassifyRules.getText();
        alarmClassifyRules.click();
        LogFunction.logInfo("点击："+text);
        AssertFunction.assertEquals(driver,text,"告警配置");
    }

    @Test(dataProvider = "xmldata")
    public void denoiseStrategy(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，降噪策略
        WebElement alarmClassifyRules = l.getElement(param.get("denoiseStrategy"));
        String text = alarmClassifyRules.getText();
        alarmClassifyRules.click();
        LogFunction.logInfo("点击："+text);
        AssertFunction.assertEquals(driver,text,"降噪策略");
    }

    @Test(dataProvider = "xmldata")
    public void alarmClassifyRulesCreate(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，告警分类规则
        WebElement alarmClassifyRules = l.getElement(param.get("alarmClassifyRules"));
        String text = alarmClassifyRules.getText();
        alarmClassifyRules.click();
        LogFunction.logInfo("选择："+text);
        AssertFunction.verifyEquals(driver,text,"","告警分类规则");
//          点击，新建
        WebElement commonCreate = l.getElement(param.get("commonCreate"));
        String text1 = commonCreate.getText();
        commonCreate.click();
        LogFunction.logInfo("点击："+text1);
        AssertFunction.verifyEquals(driver,text1,"新建","----是否点击新建----");

    }


    @BeforeClass
    public void aa() {
        SeleniumDriver seleniumDriver = new SeleniumDriver();
        driver = seleniumDriver.getDriver();
        l = new LocatorFunction(driver);
        ssf = new ScreenshotFunction(driver);
    }

    @AfterClass
    public void bb() throws InterruptedException {
        Thread.sleep(3000);
        driver.close();
        driver.quit();
//        SendMailReportFunction.sendMailReport(BaseConfig.emailSender, BaseConfig.emailAuthorizationCode, BaseConfig.emailRecipient);
    }
}
