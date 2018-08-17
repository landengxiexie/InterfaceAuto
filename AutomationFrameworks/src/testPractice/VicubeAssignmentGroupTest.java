package testPractice;

import com.test.utill.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import javax.swing.*;

public class VicubeAssignmentGroupTest {
    public static void main(String[] args) throws InterruptedException {
        SeleniumDriver seleniumDriver = new SeleniumDriver();
        WebDriver driver = seleniumDriver.getDriver();
        try {
            driver.get("http://192.168.20.121:48080/h5/");
            Thread.sleep(4000);
            WebElement element = driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[1]/input"));
            element.clear();
            element.sendKeys("administrator");
            WebElement element1 = driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[2]/input"));
            element1.clear();
            element1.sendKeys("vicube2017");
            driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[3]/button")).click();

            Thread.sleep(3000);
            driver.findElement(By.xpath("//*[@id='sidebar']/div/div[1]/ul/li[7]/a")).click();
            Thread.sleep(3000);
            driver.findElement(By.xpath("//*[@id='sidebar']/div/div[1]/ul/li[7]/ul/li[1]/a")).click();
            Thread.sleep(3000);
//        点击筛选
            driver.findElement(By.linkText("筛选")).click();
            Thread.sleep(1000);

//        输入用户名
            WebElement element2 = driver.findElement(By.xpath("//*[@class='col-md-4']/div[@class='form-group']/input[@id=\"userId\"]"));
            element2.sendKeys("user0015");
//        输入姓名
            driver.findElement(By.xpath("//*[@class='col-md-4']/div[@class='form-group']/input[@id=\"staffName\"]")).sendKeys("edit016");
//        输入邮箱
            driver.findElement(By.xpath("//*[@class='col-md-4']/div[@class='form-group']/input[@id=\"email\"]")).sendKeys("edit016@edit.com");

            //点击确定
            Thread.sleep(3000);
            driver.findElement(By.linkText("确定")).click();
            Thread.sleep(2000);
//          勾选用户
            driver.findElement(By.xpath("//*[@class='table table-striped table-bordered  ng-scope ng-isolate-scope no-footer dataTable']/tbody/tr[@role='row']/td[1]")).click();
//          点击分配工作组
            driver.findElement(By.linkText("分配工作组")).click();
            Thread.sleep(3000);

//            select用户组，成员
                driver.findElement(By.xpath("//*[@name='from[]']/option[4]")).click();
            WebElement element5 = driver.findElement(By.xpath("//*/div[@class='col-md-12']/div[1]/div[@class='col-sm-2']/button[2]/i[@class=\"glyphicon glyphicon-chevron-right\"]"));
            element5.click();



//          点击，全体移动
//            WebElement element3 = driver.findElement(By.xpath("//*/div[@class='col-md-12']/div[1]/div[2]/button[1]/i[@class='glyphicon glyphicon-forward']"));
//            element3.click();


            Thread.sleep(3000);
            driver.close();
            driver.quit();
        } catch (Exception e) {

            Thread.sleep(3000);
            driver.close();
            driver.quit();
        }
    }
}
