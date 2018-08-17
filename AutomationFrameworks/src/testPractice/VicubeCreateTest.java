package testPractice;

import com.test.utill.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



public class VicubeCreateTest {
    public static void main(String[] args) throws InterruptedException {

        SeleniumDriver seleniumDriver = new SeleniumDriver();
        WebDriver driver = seleniumDriver.getDriver();
//        try {
            driver.get("http://192.168.20.121:48080/h5/");
            Thread.sleep(3000);
            WebElement element = driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[1]/input"));
            element.clear();
            element.sendKeys("administrator");
            WebElement element1 = driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[2]/input"));
            element1.clear();
            element1.sendKeys("vicube2017");
            driver.findElement(By.xpath("//*[@id='page-container']/div/div/div/div/div[2]/div[2]/form/div[3]/button")).click();

            Thread.sleep(5000);
            driver.findElement(By.xpath("//*[@id='sidebar']/div/div[1]/ul/li[7]/a")).click();
            Thread.sleep(3000);
            driver.findElement(By.xpath("//*[@id='sidebar']/div/div[1]/ul/li[7]/ul/li[1]/a")).click();
            System.out.println("成功");
            Thread.sleep(5000);
            driver.findElement(By.linkText("新建")).click();
            Thread.sleep(2000);
//           断言 保存
        WebElement element2 = driver.findElement(By.xpath("//*[@class='modal-footer']/div[@class='ng-isolate-scope']/div[1]/button[1]"));
        boolean isEnabled;
        isEnabled = element2.isEnabled();
        System.out.println(isEnabled);
            //用户名
            WebElement userId = driver.findElement(By.xpath("//*[@class='col-md-6']/div[@class='form-group']/input[@id=\"userId\"]"));
            userId.sendKeys("11");
//           断言 保存
            isEnabled = element2.isEnabled();
            System.out.println(isEnabled);
//            姓名

            WebElement staffName = driver.findElement(By.xpath("//*[@class='col-md-6']/div[@class='form-group']/input[@id=\"staffName\"]"));
            staffName.sendKeys("22");

            isEnabled = element2.isEnabled();
            System.out.println(isEnabled);
//            邮箱
            Thread.sleep(2000);
            WebElement email = driver.findElement(By.xpath("//*[@class='col-md-6']/div[@class='form-group']/input[@id=\"email\"]"));
            email.sendKeys("33");
            Thread.sleep(2000);

            isEnabled = element2.isEnabled();
            System.out.println(isEnabled);
//            岗位职责
        driver.findElement(By.id("jobTitle")).sendKeys("44");
//        办公电话
        driver.findElement(By.xpath("//*[@id=\"phone\"]")).sendKeys("55");
//        移动电话
        driver.findElement(By.xpath("//*[@id=\"mobilephone\"]")).sendKeys("66");
//        备注
        driver.findElement(By.id("note")).sendKeys("77");

//          有效标志
        WebElement element4 = driver.findElement(By.xpath("//*/ol[@name='flag']/button[@class='btn btn-nya dropdown-toggle form-control show-special-title']"));
        element4.click();

//          有效
        Thread.sleep(2000);
          WebElement element3 = driver.findElement(By.linkText("有效"));
        element3.click();

        Thread.sleep(5000);



//           断言 保存
//         element2.click();
//
////        提示    保存成功,已经向用户发送邮件！
//        Thread.sleep(2000);
//        String text = driver.findElement(By.xpath("//*[@class='modal-dialog']/div[@class='modal-content']/div[@class='modal-body']/span[@id='content']")).getText();
//        System.out.println(text);
//
////        确认
//        Thread.sleep(2000);
//        driver.findElement(By.xpath("//*[@id=\"ngdialog1\"]/div[2]/div/div/div/div[3]/button")).click();
//

//          取消
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@class='modal-footer']/div[@class='ng-isolate-scope']/div[1]/button[2]")).click();
//        提示：确定要退出？
        Thread.sleep(2000);
        String text = driver.findElement(By.xpath("//div[@class='modal-body']/span[@id=\"content\"]")).getText();
        System.out.println(text);
        Thread.sleep(2000);
//          确认
        driver.findElement(By.xpath("//*[@class='modal-footer']/button[1]")).click();


        Thread.sleep(5000);
        driver.close();
            driver.quit();
//        } catch (Exception e) {
//            Thread.sleep(5000);
//            driver.close();
//            driver.quit();
//
//        }
    }

}
