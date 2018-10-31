package utill;

import com.bean.BaseConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;


public class SeleniumDriver {
    private WebDriver driver;
    public static String value;

    public WebDriver getDriver() {
        return driver;
    }

    public SeleniumDriver() {
        this.initialDriver();
    }

    private void initialDriver() {
        com.bean.BaseConfig config = new com.bean.BaseConfig();
        if ("firefox".equals(config.browser)) {
            System.setProperty("webdriver.gecko.driver", "./seleniumDriver/geckodriver.exe");
            driver = new FirefoxDriver();
        } else if ("chrome".equals(config.browser)) {
//          Windows下使用

//            value = isWindows1();

            value = isWindows();

//          Linux下使用

//            value= isLinux();


            driver.manage().window().maximize();

        } else if ("ie".equals(config.browser)) {
            System.setProperty("webdriver.ie.driver", "./seleniumDriver/IEDriverServer.exe");
            driver = new InternetExplorerDriver();

        } else {
            System.out.println("浏览器配置错误" + config.browser);
        }
        driver.manage().window().maximize();

//        driver.manage().timeouts().pageLoadTimeout(config.waitTime, TimeUnit.SECONDS);
    }


    public String isWindows() {
        //          Windows下使用
        System.setProperty("webdriver.chrome.driver", "./seleniumDriver/chromedriver.exe");
        //            设置，默认下载路径
        DesiredCapabilities caps = this.setDownloadsPath();
        driver = new ChromeDriver(caps);
        return "w";
    }

    public String isWindows1() {
        //          Windows下使用
        System.setProperty("webdriver.chrome.driver", "./seleniumDriver/chromedriver.exe");
        //            设置，默认下载路径
        ChromeOptions options = new ChromeOptions();
        DesiredCapabilities caps = this.setDownloadsPath();
        options.addArguments("--headless");
        caps.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(caps);
        return "w";
    }



    public String isLinux() {
//          Linux下使用
        System.setProperty("webdriver.chrome.driver", "./seleniumDriver/chromedriver");
//         不启动浏览器,后台运行,
//        String downloadsPath = BaseConfig.chromeDownloadPathOfLinux;
        String downloadsPath = BaseConfig.chromeDownloadPathOfWindows;
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("download.default_directory", downloadsPath);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        DesiredCapabilities caps = new DesiredCapabilities();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--start-maximized");
        caps.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(caps);
        return "l";
    }


    //    设置默认下载路径
    public DesiredCapabilities setDownloadsPath() {
        String downloadsPath = BaseConfig.chromeDownloadPathOfWindows;
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("download.default_directory", downloadsPath);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(ChromeOptions.CAPABILITY, options);
        return caps;
    }

}
