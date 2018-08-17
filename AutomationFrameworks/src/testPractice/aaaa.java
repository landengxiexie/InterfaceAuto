package testPractice;
import com.test.utill.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * @author d
 * @date 2018/5/28 002814:52
 */
public class aaaa {
    public static void main(String[] args) throws InterruptedException {
        SeleniumDriver s=new SeleniumDriver();
        WebDriver driver = s.getDriver();
        driver.get("http://www.jd.com");
        Thread.sleep(5000);
        WebElement element = driver.findElement(By.xpath("//li[@data-index='2']/a[@href='//shouji.jd.com/']"));
        Actions actions=new Actions(driver);
        actions.moveToElement(element).perform();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id='cate_item2']/div[1]/div[2]/dl[1]/dd/a[2]")).click();
            Thread.sleep(3000);
        Object[] handles=driver.getWindowHandles().toArray();
        driver.switchTo().window(handles[1].toString());
        driver.findElement(By.xpath("/html/body/div[8]/div[1]/div[1]/div[2]/div/div[2]/div[2]/ul/li[1]/a")).click();
        driver.findElement(By.xpath("/html/body/div[8]/div[1]/div[1]/div[2]/div/div[2]/div/ul/li[3]/a")).click();
//        driver.close();
    }
}
