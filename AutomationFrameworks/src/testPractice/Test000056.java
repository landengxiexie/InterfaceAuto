package testPractice;

import com.test.utill.AssertFunction;
import com.test.utill.SeleniumDriver;
import org.openqa.selenium.WebDriver;

import org.testng.annotations.Test;

public class Test000056 {
  @Test
    public static void aaa(){
      SeleniumDriver driver1=new SeleniumDriver();
      WebDriver driver = driver1.getDriver();
      AssertFunction a=new AssertFunction();
      a.verifyEquals(driver,2,3,"11");

    }
}
