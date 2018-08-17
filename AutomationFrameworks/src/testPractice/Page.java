package testPractice;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

/**
 * @author d
 * @date 2018/5/9 000914:04
 */
public class Page {
    public WebDriver driver;
    public Page(WebDriver driver){
        this.driver=driver;
    }
    private boolean waitToDisplayed(final By key){
        Boolean waitDisplayed = new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return input.findElement(key).isDisplayed();
            }
        });
        return waitDisplayed;
    }

    public WebElement getElement(WebDriver driver,By key){
        WebElement element=null;
        if (this.waitToDisplayed(key)){
            element=driver.findElement(key);
            return element;
        }
        return element;
    }
}
