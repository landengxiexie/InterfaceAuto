package testPractice;

import com.test.utill.SeleniumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@SuppressWarnings("ALL")
public class VicubeUserWorkingGroup {
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
        driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[1]/ul/li[7]/ul/li[2]/a")).click();
        Thread.sleep(5000);
//        点击新建
        driver.findElement(By.linkText("新建")).click();
        Thread.sleep(1000);

        Thread.sleep(2000);
//        输入工作组名称
        WebElement element2 = driver.findElement(By.xpath("//*/div[@class='col-md-6']/div[1]/input[@id=\"groupName\"]"));
        element2.sendKeys("AutoTestGroup");
//        点击有效标识
        driver.findElement(By.xpath("//*/ol[@name='flag']/button[@class='btn btn-nya dropdown-toggle form-control show-special-title']")).click();
        Thread.sleep(1000);

        //        选择有效
        driver.findElement(By.linkText("有效")).click();


        //点击保存
        driver.findElement(By.xpath("//*[@class='modal-footer']/div[@class='ng-isolate-scope']/div[1]/button[1]")).click();

        Thread.sleep(1000);

//            保存提示:保存成功
        String text = driver.findElement(By.xpath("//*[@class='modal-dialog']/div[@class='modal-content']/div[@class='modal-body']/span[@id='content']")).getText();
        System.out.println(text);
//            保存确认
        driver.findElement(By.xpath("//*[@class='modal-footer']/button[1]")).click();
        Thread.sleep(1000);
//          点击筛选
        driver.findElement(By.linkText("筛选")).click();
        Thread.sleep(1000);

//            输入用户组名
        driver.findElement(By.xpath("//*[@class='col-md-4']/div[1]/input[@id=\"groupName\"]")).sendKeys("AutoTestGroup");
//            点击筛选确认
        driver.findElement(By.linkText("确定")).click();
        Thread.sleep(1000);
//            勾选用户组名
        driver.findElement(By.xpath("//*[@class='table table-striped table-bordered  ng-scope ng-isolate-scope no-footer dataTable']/tbody/tr[@role='row']/td[1]")).click();
//              点击分配权限
        driver.findElement(By.linkText("分配权限")).click();
        Thread.sleep(5000);
//            选择：BJ-UPS1，BJ-UPS2，BJ-UPS3，BJ-UPS4
        WebElement element3 = driver.findElement(By.xpath("//*[@name=\"from[]\"]/option[9]"));
        element3.click();


        driver.findElement(By.xpath("//*[@name=\"from[]\"]/option[10]")).click();
        driver.findElement(By.xpath("//*[@name=\"from[]\"]/option[11]")).click();
        driver.findElement(By.xpath("//*[@name=\"from[]\"]/option[12]")).click();
        Thread.sleep(3000);

        WebElement element5 = driver.findElement(By.xpath("//*[@id='view']/div[1]/div[@class='col-sm-2']/button[2]"));
        element5.click();

        driver.findElement(By.linkText("保存")).click();



    }
}