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
        driver.get("http://192.168.20.121:48080/h5/");
        Thread.sleep(4000);
//        登陆
        WebElement element = driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[1]/input"));
        element.clear();
        element.sendKeys("administrator");
        WebElement element1 = driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[2]/input"));
        element1.clear();
        element1.sendKeys("vicube2017");
        driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[3]/button")).click();
//点击，系统设置
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id='sidebar']/div/div[1]/ul/li[7]/a")).click();
        Thread.sleep(3000);

//       点击，审计管理
        driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[1]/ul/li[7]/ul/li[3]")).click();
        Thread.sleep(3000);
//选择，日志
        driver.findElement(By.xpath("//*/div[2]/table/tbody/tr[1]/td[1]")).click();
        Thread.sleep(3000);
//       点击，查看
        driver.findElement(By.xpath("//*[@class=\"dataTables_wrapper form-inline dt-bootstrap no-footer\"]/div[1]/a[1]")).click();
        Thread.sleep(3000);
//      验证，是否打开，审计详情
        String element2 = driver.findElement(By.xpath("//*[@id=\"detailForm\"]/div/div/div/h4")).getText();
        System.out.println(element2);
        Thread.sleep(3000);
//      关闭，审计详情
        driver.findElement(By.xpath("//*[@id=\"detailForm\"]/div/div/form/div[2]/div/div/button")).click();
        Thread.sleep(3000);
//        点击筛选
        driver.findElement(By.xpath("//*[@class=\"dataTables_wrapper form-inline dt-bootstrap no-footer\"]/div[1]/a[2]")).click();
        Thread.sleep(3000);
//      筛选，录入，操作账号
        WebElement element3 = driver.findElement(By.xpath("//*[@compile='queryHtml']/form/div[1]/div[2]/div/input"));
        element3.sendKeys("user0015");
        Thread.sleep(3000);

//       点击，筛选，确定
        driver.findElement(By.linkText("确定")).click();
        Thread.sleep(3000);
//     验证，筛选结果，
        String text = driver.findElement(By.xpath("//*/div[2]/table/tbody/tr[1]/td[4]")).getText();
        System.out.println(text);
        Thread.sleep(3000);


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
