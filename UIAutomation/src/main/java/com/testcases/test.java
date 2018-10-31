package com.testcases;

import com.base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utill.*;

import javax.swing.*;


@SuppressWarnings("ALL")
public class test extends TestBase {
    public WebDriver driver;


    @Test
    public void xx() throws InterruptedException {
//        打开，百度
        driver.get("https://www.baidu.com");
        Thread.sleep(2000);
//      点击，设置
        driver.findElement(By.xpath("//*[@id=\"u1\"]/a[8]")).click();
//      点击，搜索设置
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[6]/a[1]")).click();
    }


    @BeforeClass
    public void aa() {
        SeleniumDriver seleniumDriver = new SeleniumDriver();
        driver = seleniumDriver.getDriver();

    }

    @AfterClass
    public void bb() throws InterruptedException {
        Thread.sleep(3000);
        driver.close();
        driver.quit();
//        SendMailReportFunction.sendMailReport(BaseConfig.emailSender, BaseConfig.emailAuthorizationCode, BaseConfig.emailRecipient);
    }

}
