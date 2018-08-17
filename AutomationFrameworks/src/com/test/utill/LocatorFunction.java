package com.test.utill;
import com.test.bean.BaseConfig;
import com.test.utill.LogFunction;
import com.test.utill.ParseFunction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;

/**
 * @author d
 * @date 2018/5/14 001417:33
 */
public class LocatorFunction {
    private String yamlfile;
    private WebDriver driver;
    private By by;
    private Map<String, Map<String, String>> map;

    public LocatorFunction(WebDriver driver) {
        this.yamlfile = BaseConfig.yamlfile;
        ParseFunction p = new ParseFunction();
        p.setYamlFile(yamlfile);
        p.getYamlFile();
        this.map=p.map;
        this.driver = driver;
    }

    private WebElement getLocator(String tagName,String replace,boolean wait){
        WebElement element=null;
        if (map.containsKey(tagName)){
            Map<String,String> m=map.get(tagName);
            String type=m.get("type");
            String value=m.get("value");
            if (replace!=null){
                value=this.getLocatorString(value,replace);
            }
            By by=this.getBy(type,value);
            if (wait){
                element=this.WaitForElement(by);
                boolean flag=this.waitElementToBeDisplayed(element);
                if (!flag) {
                    element = null;
                }
            }else {
                try {
                    element=driver.findElement(by);
                }catch (Exception e){
                    element=null;
                }
            }
        }else {
            LogFunction.logError("locator "+tagName+"--is not exist in--"+yamlfile+".yaml");
        }
        return element;
    }

    private String getLocatorString(String locatorString ,String ss){
        locatorString=locatorString.replaceFirst("%s",ss);
        return locatorString;
    }

    private WebElement WaitForElement(final By by) {
        int waitTime = BaseConfig.waitTime;
        WebElement element = null;
        try {
            element = new WebDriverWait(driver, waitTime).until(new ExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(by);
                }
            });
        } catch (Exception e) {
            LogFunction.logError(by.toString() + "--is not exist until--" + waitTime);
        }
        return element;
    }

    private boolean waitElementToBeDisplayed(final WebElement element) {
        boolean wait = false;
        if (element == null) {
            return wait;
        } else {
            try {
                wait = new WebDriverWait(driver, BaseConfig.waitTime).until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver input) {
                        return element.isDisplayed();
                    }
                });
            } catch (Exception e) {
                LogFunction.logError(element.toString() + "is not displayed");
            }
            return wait;
        }
    }

    private boolean waitElementToBeNonDisplayed(final WebElement element) {
        boolean wait = false;
        if (element == null) {
            return wait;
        } else {
            try {
                wait = new WebDriverWait(driver, BaseConfig.waitTime).until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver input) {
                        return !element.isDisplayed();
                    }
                });
            } catch (Exception e) {
                LogFunction.logError(element.toString() + "is also displayed");
            }
            return wait;
        }
    }

    public WebElement getElement(String tagName) {
        /*String type = map.get(tagName).get("type");
        String value = map.get(tagName).get("value");
        WebElement element;
        element = this.WaitForElement(this.getBy(type, value));
        if (!this.waitElementToBeDisplayed(element)) {
            element = null;
        }
        return element;*/
        return this.getLocator(tagName,null,true);
    }
    public WebElement getElement(String tagName,String replace) {
        return this.getLocator(tagName,replace,true);
    }

    public WebElement getElementNoWait(String tagName){
        /*WebElement element;
        String type = map.get(tagName).get("type");
        String value = map.get(tagName).get("value");
        try {
            element=driver.findElement(this.getBy(type,value));
        }catch (Exception e){
            element=null;
        }
        return element;*/
        return this.getLocator(tagName,null,false);
    }

    public WebElement getElementNoWait(String tagName,String replace){
        return this.getLocator(tagName,replace,false);
    }

    private By getBy(String type, String value) {
        if ("id".equals(type)) {
            by = By.id(value);
        }
        if ("name".equals(type)) {
            by = By.name(value);
        }
        if ("link".equals(type)) {
            by = By.linkText(value);
        }
        if ("class".equals(type)) {
            by = By.className(value);
        }
        if ("xpath".equals(type)) {
            by = By.xpath(value);
        }
        return by;
    }

    public static void main(String[] args) {
        /*SeleniumDriver s = new SeleniumDriver();
        WebDriver driver = s.getDriver();
        LocatorFunction l = new LocatorFunction(driver);
        driver.get("http://www.baidu.com");
        WebElement baidu_input = l.getElement("baidu_input","kw");
        baidu_input.sendKeys("ss");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.close();*/
    }
}
