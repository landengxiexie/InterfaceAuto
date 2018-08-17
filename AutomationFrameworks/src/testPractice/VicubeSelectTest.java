package testPractice;

import com.test.utill.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import sun.security.krb5.internal.rcache.DflCache;

public class VicubeSelectTest {
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
        driver.findElement(By.xpath("//*[@id='sidebar']/div/div[1]/ul/li[7]/a")).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id='sidebar']/div/div[1]/ul/li[7]/ul/li[1]/a")).click();
        Thread.sleep(5000);
//        点击筛选
        driver.findElement(By.linkText("筛选")).click();
        Thread.sleep(1000);

//        输入用户名
        WebElement element2 = driver.findElement(By.xpath("//*[@class='col-md-4']/div[@class='form-group']/input[@id=\"userId\"]"));
        element2.sendKeys("11");
//        输入姓名
        driver.findElement(By.xpath("//*[@class='col-md-4']/div[@class='form-group']/input[@id=\"staffName\"]")).sendKeys("aa");
//        输入邮箱
        driver.findElement(By.xpath("//*[@class='col-md-4']/div[@class='form-group']/input[@id=\"email\"]")).sendKeys("ee");
//          选择有效
        driver.findElement(By.xpath("//*[@id=\"flag\"]/button")).click();
//        driver.findElement(By.xpath("//*[@id=\"flag\"]/div/ul/li[2]/a")).click();
//        选择无效
        driver.findElement(By.xpath("//*[@id=\"flag\"]/div/ul/li[3]/a")).click();

        //点击确定
        Thread.sleep(3000);
        driver.findElement(By.linkText("确定")).click();
        Thread.sleep(2000);

//      断言用户名
        WebElement element3 = driver.findElement(By.xpath("//*[@class='table table-striped table-bordered  ng-scope ng-isolate-scope no-footer dataTable']/tbody/tr[@role='row']/td[2]"));
        Thread.sleep(2000);
        String text = element3.getText();
        System.out.println(text);
//断言姓名
        String text1 = driver.findElement(By.xpath("//*[@class='table table-striped table-bordered  ng-scope ng-isolate-scope no-footer dataTable']/tbody/tr[@role='row']/td[3]")).getText();
        System.out.println(text1);
//断言岗位职责
        String text2 = driver.findElement(By.xpath("//*[@class='table table-striped table-bordered  ng-scope ng-isolate-scope no-footer dataTable']/tbody/tr[@role='row']/td[4]")).getText();
        System.out.println(text2);
        //断言办公电话
        String text3= driver.findElement(By.xpath("//*[@class='table table-striped table-bordered  ng-scope ng-isolate-scope no-footer dataTable']/tbody/tr[@role='row']/td[5]")).getText();
        System.out.println(text3);
        //断言移动电话
        String text4= driver.findElement(By.xpath("//*[@class='table table-striped table-bordered  ng-scope ng-isolate-scope no-footer dataTable']/tbody/tr[@role='row']/td[6]")).getText();
        System.out.println(text4);
        //断言email
        String text5 = driver.findElement(By.xpath("//*[@class='table table-striped table-bordered  ng-scope ng-isolate-scope no-footer dataTable']/tbody/tr[@role='row']/td[7]")).getText();
        System.out.println(text5);
//断言备注
        String text6 = driver.findElement(By.xpath("//*[@class='table table-striped table-bordered  ng-scope ng-isolate-scope no-footer dataTable']/tbody/tr[@role='row']/td[8]")).getText();
        System.out.println(text6);
        //断言有效标志
        String text7 = driver.findElement(By.xpath("//*[@class='table table-striped table-bordered  ng-scope ng-isolate-scope no-footer dataTable']/tbody/tr[@role='row']/td[9]")).getText();
        System.out.println(text7);
//          勾选用户
       driver.findElement(By.xpath("//*[@class='table table-striped table-bordered  ng-scope ng-isolate-scope no-footer dataTable']/tbody/tr[@role='row']/td[1]")).click();

//       点击，编辑
        driver.findElement(By.linkText("编辑")).click();


//      断言用户名是否可编辑

        WebElement element4 = driver.findElement(By.xpath("//*[@class='col-md-6']/div[@class='form-group']/input[@id=\"userId\"]"));
        Thread.sleep(2000);
        boolean enabled = element4.isEnabled();
        System.out.println(enabled);
//修改姓名
        WebElement element5 = driver.findElement(By.xpath("//*[@class='col-md-6']/div[@class='form-group']/input[@id=\"staffName\"]"));
        element5.clear();
        element5.sendKeys("aa");


//修改岗位职责
        WebElement element6 = driver.findElement(By.xpath("//*[@class='col-md-6']/div[@class='form-group']/input[@id=\"jobTitle\"]"));
        element6.clear();
        element6.sendKeys("bb");
        //修改办公电话
        WebElement element7 = driver.findElement(By.xpath("//*[@class='col-md-6']/div[@class='form-group']/input[@id=\"phone\"]"));
        element7.clear();

        element7.sendKeys("cc");
        //修改移动电话
        WebElement element8 = driver.findElement(By.xpath("//*[@class='col-md-6']/div[@class='form-group']/input[@id=\"mobilephone\"]"));
        element8.clear();

        element8.sendKeys("dd");
        //修改email
        WebElement element9 = driver.findElement(By.xpath("//*[@class='col-md-6']/div[@class='form-group']/input[@id=\"email\"]"));
        element9.clear();

        element9.sendKeys("ee");
        //修改备注
        WebElement element10 = driver.findElement(By.xpath("//*[@class='col-md-6']/div[@class='form-group']/textarea[@id=\"note\"]"));
        element10.clear();
        element10.sendKeys("ff");
        //修改有效标志
        WebElement element11 = driver.findElement(By.xpath("//*[@ng-model='formModel.instance.flag']/button[@class='btn btn-nya dropdown-toggle form-control']"));
        element11.click();
        driver.findElement(By.linkText("无效")).click();
//      点击保存
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@class='ng-isolate-scope']/div[@compile=\"buttonHtml\"]/button[1]")).click();
//      断言提示：保存成功
        Thread.sleep(1000);

        WebElement element12 = driver.findElement(By.xpath("//*/span[@id='content']"));
        String text8 = element12.getText();
        System.out.println(text8);
//        点击确认
        Thread.sleep(1000);
        By.xpath("//*/div[@class='modal-content']/div[@class='modal-footer']/button").findElement(driver).click();



        Thread.sleep(3000);
        driver.close();
        driver.quit();
    }
}
