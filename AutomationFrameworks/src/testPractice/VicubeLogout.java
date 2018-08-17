package testPractice;

import com.test.utill.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class VicubeLogout {
    public static void main(String[] args) throws InterruptedException {
        SeleniumDriver seleniumDriver = new SeleniumDriver();
        WebDriver driver = seleniumDriver.getDriver();

        driver.get("http://192.168.20.121:48080/h5/");
        Thread.sleep(5000);
        WebElement element = driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[1]/input"));
        element.clear();
        element.sendKeys("administrator");
        WebElement element1 = driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[2]/input"));
        element1.clear();
        element1.sendKeys("vicube2017");
        driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[3]/button")).click();

        Thread.sleep(3000);
        //          点击 姓名
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/ul/li[2]/a/span")).click();
//          点击退出登录
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/ul/li[2]/ul/li[4]/a")).click();


    }
}
