package utill;

import com.bean.BaseConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Map;

/**
 * @author d
 * @date 2018/5/14 001417:33
 */
@SuppressWarnings("Duplicates")
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
        this.map = p.map;
        this.driver = driver;
    }

    private WebElement getLocator(String tagName, String replace, boolean wait) {
        WebElement element = null;
        if (map.containsKey(tagName)) {
            Map<String, String> m = map.get(tagName);
            String type = m.get("type");
            String value = m.get("value");
            if (replace != null) {
                value = this.getLocatorString(value, replace);
            }
            By by = this.getBy(type, value);
            if (wait) {
                element = this.WaitForElement(by);
                boolean flag = this.waitElementToBeDisplayed(element);
                if (!flag) {
                    element = null;
                }
            } else {
                try {
                    element = driver.findElement(by);
                } catch (Exception e) {
                    element = null;
                }
            }
        } else {
            LogFunction.logError("locator " + tagName + "--元素是不存在的--" + yamlfile + ".yaml");
        }
        return element;
    }

    private List<WebElement> getLocator(String tagName) {
        List<WebElement> elements = null;
        if (map.containsKey(tagName)) {
            Map<String, String> m = map.get(tagName);
            String type = m.get("type");
            String value = m.get("value");
            By by = this.getBy(type, value);
            elements = driver.findElements(by);
        } else {
            LogFunction.logError("locator " + tagName + "--元素是不存在的--" + yamlfile + ".yaml");
        }
        return elements;
    }

    private Boolean getLocator(String tagName, boolean wait) {
        WebElement element = null;
        if (map.containsKey(tagName)) {
            Map<String, String> m = map.get(tagName);
            String type = m.get("type");
            String value = m.get("value");

            By by = this.getBy(type, value);
            if (wait) {
                element = this.WaitForElement(by);
            } else {
                try {
                    element = driver.findElement(by);
                } catch (Exception e) {
                    element = null;
                }
            }
        } else {
            LogFunction.logError("locator " + tagName + "--元素是不存在的--" + yamlfile + ".yaml");
        }
        return element.isDisplayed();
    }


    private String getLocatorString(String locatorString, String ss) {
        locatorString = locatorString.replaceFirst("%s", ss);
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
            LogFunction.logError(by.toString() + "--is not exist until--" + "TimeOut:" + waitTime);
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
                LogFunction.logError(element.toString() + "--is not displayed");
            }
            return wait;
        }
    }



    public List<WebElement> getElements(String tagName) {
        return this.getLocator(tagName);
    }

    public Boolean getElementIsDisplay(String tagName) {
        return this.getLocator(tagName,true);
    }

    public WebElement getElement(String tagName) {
        return this.getLocator(tagName, null, true);
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

}
