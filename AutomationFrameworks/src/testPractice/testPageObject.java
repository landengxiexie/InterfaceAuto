package testPractice;
import com.sun.deploy.resources.Deployment_sv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author d
 * @date 2018/5/9 000910:45
 */
public class testPageObject {
    public static By input=By.id("user");
    public WebDriver driver;
    public void test1(){
        WebElement element=driver.findElement(testPageObject.input);
    }
}
