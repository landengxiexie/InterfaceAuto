package testPractice;

import com.test.utill.SeleniumDriver;
import org.openqa.selenium.*;

public class VicubeAlert {
    public static void main(String[] args) throws InterruptedException {

        SeleniumDriver seleniumDriver = new SeleniumDriver();
        WebDriver driver = seleniumDriver.getDriver();
        driver.get("http://192.168.20.121:48080/h5/");
        Thread.sleep(5000);
        WebElement element = driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[1]/input"));
        element.clear();
        element.sendKeys("administrator");
        WebElement element1 = driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[2]/input"));
        element1.clear();
        element1.sendKeys("vicube2017");
        driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[3]/button")).click();
        Thread.sleep(5000);

//        点击，集中警告
        driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[1]/ul/li[2]/a/span")).click();
        Thread.sleep(1000);
//        点击，告警配置
        driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[1]/ul/li[2]/ul/li[3]/a")).click();
        Thread.sleep(1000);
//        点击，降噪策略
        driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[1]/ul/li[2]/ul/li[3]/ul/li[1]/a")).click();
        Thread.sleep(4000);
//        点击，全部规则
        driver.findElement(By.xpath("//*[@id=\"cbAlertRuleType\"]/button[@class='btn btn-default dropdown-toggle']")).click();
        Thread.sleep(1000);
//        点击，告警分类规则
        driver.findElement(By.linkText("告警分类规则")).click();
        Thread.sleep(1000);
//        点击，新建
        driver.findElement(By.linkText("新建")).click();
        Thread.sleep(1000);
//          录入，规则名称
        driver.findElement(By.id("alertRuleRuleTitle")).sendKeys("ruleName001");
//        点击，下一步
        driver.findElement(By.xpath("//*[@id=\"alertRuleMadal\"]/div/div/div[2]/div/div[2]/button[1]")).click();
        Thread.sleep(1000);
//         点击域，
        driver.findElement(By.xpath("//*[@id=\"alertRuleMadal\"]/div/div/div[2]/div/div[1]/section[2]/form/div[1]/div/div/div")).click();
        Thread.sleep(1000);
//          选择，域
        driver.findElement(By.linkText("rootDomain")).click();

//        driver.findElement(By.xpath("//*[@class=\"form-control nya-bs-select ng-pristine ng-untouched btn-group ng-scope ng-empty ng-invalid ng-invalid-required open\"]/div/ul/li[1]/a")).click();
        //         点击类型
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"alertRuleMadal\"]/div/div/div[2]/div/div[1]/section[2]/form/div[2]/div/div/div")).click();
//          选择，类型
        Thread.sleep(1000);
        driver.findElement(By.linkText("Oracle")).click();
//        driver.findElement(By.xpath("//*[@class='form-control nya-bs-select btn-group ng-scope ng-touched ng-not-empty ng-dirty ng-valid-parse ng-valid ng-valid-required open']/div[@class='dropdown-menu open']/ul/li[36]/a"))
        Thread.sleep(1000);
//          选择，类型   Oracle_System_Alert
        Thread.sleep(1000);
//        点击，告警类型选择
        driver.findElement(By.xpath("//*[@id=\"alertRuleMadal\"]/div/div/div[2]/div/div[1]/section[2]/form/div[3]/div/div/div")).click();
//          选择，告警类型选择，Oracle_System_Alert
        Thread.sleep(1000);
        driver.findElement(By.linkText("Oracle_System_Alert")).click();
        Thread.sleep(1000);
        //        点击，下一步
        driver.findElement(By.xpath("//*[@id=\"alertRuleMadal\"]/div/div/div[2]/div/div[2]/button[2]")).click();
        Thread.sleep(1000);
//        点击，节点过滤
        driver.findElement(By.xpath("//*[@id=\"alertRuleMadal\"]/div/div/div[2]/div/div[1]/section[3]/form/div[1]/div/div/div/div")).click();
        Thread.sleep(1000);
//         选择，节点过滤  StandardTest-Oracle2
        driver.findElement(By.linkText("StandardTest-Oracle2")).click();
        //        点击，空白
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"alertRuleMadal\"]/div/div/div[2]/ul")).click();
        Thread.sleep(2000);
//        点击，采集系统选择
        driver.findElement(By.xpath("//*[@id=\"alertRuleMadal\"]/div/div/div[2]/div/div[1]/section[3]/form/div[3]/div/div/div/div")).click();
        Thread.sleep(1000);
//         选择，采集系统选择  IBM ITCAM
        driver.findElement(By.linkText("IBM ITCAM")).click();
//        点击，空白
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"alertRuleMadal\"]/div/div/div[2]/ul")).click();

        Thread.sleep(1000);
//        点击，内容关键词
        driver.findElement(By.xpath("//*[@id=\"alertRuleMessageFilter\"]")).sendKeys("内容关键词001");
        Thread.sleep(1000);
        //        点击，下一步
        driver.findElement(By.xpath("//*[@id=\"alertRuleMadal\"]/div/div/div[2]/div/div[2]/button[2]")).click();
        Thread.sleep(1000);
//          点击，新的警告类型
        driver.findElement(By.xpath("//*[@id=\"alertRuleClassify\"]/div[1]/div")).click();
        Thread.sleep(1000);
//        选择，新的警告类型  Oracle_PGA_Alert

        driver.findElement(By.linkText("Oracle_PGA_Alert")).click();
        Thread.sleep(1000);
//          点击，新的告警级别
        driver.findElement(By.xpath("//*[@id=\"alertRuleClassify\"]/div[2]/div")).click();
        Thread.sleep(1000);
//          点击 取消全选
        driver.findElement(By.xpath("//*[@id=\"alertRuleClassify\"]/div[2]/div/div/div/div/ol/div/div[2]/div/button[2]")).click();
//        Thread.sleep(1000);
//        driver.findElement(By.linkText("MINOR")).click();
        //        选择，新的告警级别 CRITICAL
        Thread.sleep(1000);
        driver.findElement(By.linkText("CRITICAL")).click();
        //        点击，空白
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"alertRuleMadal\"]/div/div/div[2]/ul")).click();
//          点击，保存
        Thread.sleep(1000);

        driver.findElement(By.xpath("//*[@id=\"alertRuleMadal\"]/div/div/div[2]/div/div[2]/button[2]")).click();
        Thread.sleep(2000);
        driver.close();
        driver.quit();
    }
}
