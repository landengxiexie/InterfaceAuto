package com.test.base;
import com.test.utill.LocatorFunction;
import com.test.utill.ParseFunction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * @author d
 * @date 2018/5/28 002813:36
 */
public class Page extends LocatorFunction {
    private WebDriver driver;
    public Page(WebDriver driver){
        super(driver);
        ParseFunction p=new ParseFunction();
        p.setYamlFile(this.getClass().getSimpleName());
        p.getYamlFile();
    }

    public Actions getAction(){
        return new Actions(driver);
    }

    public void switchWindownByIndex(int index){
        Object[] handles=driver.getWindowHandles().toArray();
        if (index>handles.length){
            return;
        }
        driver.switchTo().window(handles[index].toString());
    }

    public boolean isExist(WebElement element){
        if (element==null){
            return false;
        }else {
            return true;
        }
    }
}
