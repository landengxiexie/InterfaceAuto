package com.test.utill;
import com.test.bean.BaseConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.concurrent.TimeUnit;


public class SeleniumDriver {
    private WebDriver driver;
    public WebDriver getDriver() {
        return driver;
    }
    public SeleniumDriver(){
        this.initialDriver();
    }
    private void initialDriver(){
        BaseConfig config=new BaseConfig();
        if ("firefox".equals(config.browser)){
            System.setProperty("webdriver.gecko.driver","\\PersonProject\\AutomationFrameworks\\seleniumDriver\\geckodriver.exe");
            driver=new FirefoxDriver();
        }else if ("chrome".equals(config.browser)){
            System.setProperty("webdriver.chrome.driver", "\\PersonProject\\AutomationFrameworks\\seleniumDriver\\chromedriver.exe");
            driver=new ChromeDriver();
        }else if ("ie".equals(config.browser)) {
            System.setProperty("webdriver.ie.driver", "\\PersonProject\\AutomationFrameworks\\seleniumDriver\\IEDriverServer.exe");
            driver = new InternetExplorerDriver();
        }else {
            System.out.println("浏览器配置错误"+config.browser);
        }

        driver.manage().window().maximize();
//        driver.manage().timeouts().pageLoadTimeout(config.waitTime, TimeUnit.SECONDS);
    }
}