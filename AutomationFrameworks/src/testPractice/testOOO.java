package testPractice;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.swing.plaf.SliderUI;

/**
 * @author d
 * @date 2018/5/9 000915:40
 */
public class testOOO {
    private WebDriver driver;

    @Test
    public void aa() {
        DemoPage demoPage = new DemoPage(driver);
        demoPage.search(driver,"哈哈");


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeClass
    public void bb() {
        System.setProperty("webdriver.gecko.driver", "D:\\iedriver\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://www.baidu.com");
    }

    @AfterClass
    public void cc() {

        driver.close();
    }
}
