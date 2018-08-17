package testPractice;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author d
 * @date 2018/5/9 000915:27
 */
public class DemoPage extends Page{
    public DemoPage(WebDriver driver) {
        super(driver);
    }
    public  By search=By.id("kw");
    public void search(WebDriver driver,String massage){
        WebElement element = this.getElement(driver, search);
        element.sendKeys(massage);

    }
}
