package testPractice;

import com.test.utill.AssertFunction;
import com.test.utill.SeleniumDriver;
import jdk.nashorn.internal.ir.CatchNode;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class userView {
    public static void main(String[] args) throws InterruptedException {


        SeleniumDriver seleniumDriver = new SeleniumDriver();
        WebDriver driver = seleniumDriver.getDriver();

            driver.get("http://192.168.20.121:48080/h5/");
            Thread.sleep(4000);
            WebElement element = driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[1]/input"));
            element.clear();
            element.sendKeys("user0015");
            WebElement element1 = driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[2]/input"));
            element1.clear();

            element1.sendKeys("e9a4bf");
        WebElement element2 = driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[3]/button"));
        String text = element2.getText();
        AssertFunction.verifyEquals(driver,text,"登录1","213");
//        element2.click();

        Thread.sleep(5000);

////            点击+
//            driver.findElement(By.id("addBtn")).click();
//            Thread.sleep(5000);
////          选择 8586_BJ-UPS1
//
//            driver.findElement(By.xpath("//*[@name=\"from[]\"]/option[1]")).click();
//        Thread.sleep(1000);
//        driver.findElement(By.xpath("//*[@name=\"from[]\"]/option[2]")).click();
//        Thread.sleep(1000);
//        driver.findElement(By.xpath("//*[@name=\"from[]\"]/option[3]")).click();
//        Thread.sleep(1000);
//        driver.findElement(By.xpath("//*[@name=\"from[]\"]/option[4]")).click();
//        Thread.sleep(2000);
////点击右移
//        driver.findElement(By.xpath("//*[@class='col-md-12']/div[1]/div[@class='col-sm-2']/button[2]")).click();
//        Thread.sleep(1000);
//
////        点击保存
//        driver.findElement(By.xpath("//*/div[@class='modal-footer ng-scope']/button[1]")).click();
//
//            Thread.sleep(3000);
//

    }
}
