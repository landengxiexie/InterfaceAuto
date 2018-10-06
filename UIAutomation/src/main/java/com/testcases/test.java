package com.testcases;

import com.base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utill.*;

import java.util.Map;

@SuppressWarnings("ALL")
public class test extends TestBase {
    public WebDriver driver;


    @Test
    public void xx() throws InterruptedException {
//        打开，url
        driver.get("http://192.168.20.143:48080/h5/");
        Thread.sleep(4000);
//        登陆
        WebElement element = driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[1]/input"));
        element.clear();
        element.sendKeys("administrator");
        WebElement element1 = driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[2]/input"));
        element1.clear();
        element1.sendKeys("@Vic_2017_Rg");
        driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[3]/button")).click();
//点击，系统设置
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id='sidebar']/div/div[1]/ul/li[7]/a")).click();
// 点击，集中告警
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[1]/ul/li[2]/a/span")).click();
//点击，告警配置
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[1]/ul/li[2]/ul/li[3]/a")).click();
//点击，集中告警-告警配置-降噪策略
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='sidebar']/div/div[1]/ul/li[2]/ul/li[3]/ul/li[1]/a")).click();
//  点击，全部规则
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='cbAlertRuleType']/button[@class='btn btn-default dropdown-toggle']")).click();
//点击，告警分类规则
        Thread.sleep(1000);
        driver.findElement(By.linkText("告警分类规则")).click();
//点击，新建
        Thread.sleep(1000);
        driver.findElement(By.linkText("新建")).click();
//录入，规则名称
        Thread.sleep(1000);
        driver.findElement(By.id("alertRuleRuleTitle")).sendKeys("test Strategy");
//点击，下一步
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='alertRuleMadal']/div/div/div[2]/div/div[2]/button[1]")).click();
//点击，域
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='alertRuleMadal']/div/div/div[2]/div/div[1]/section[2]/form/div[1]/div/div/div")).click();
//        选择，域
        Thread.sleep(1000);
        driver.findElement(By.linkText("Domain")).click();
//点击，类型
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='alertRuleMadal']/div/div/div[2]/div/div[1]/section[2]/form/div[2]/div/div/div")).click();
//       选择类型，值
        Thread.sleep(1000);
        driver.findElement(By.linkText("监控对象")).click();
//点击，告警类型选择
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='alertRuleMadal']/div/div/div[2]/div/div[1]/section[2]/form/div[3]/div/div/div")).click();
//       选择类型，告警类型选择值
        Thread.sleep(1000);
        driver.findElement(By.linkText("厦门告警")).click();
//点击，下一步
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id='alertRuleMadal']/div/div/div[2]/div/div[2]/button[2]")).click();


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
