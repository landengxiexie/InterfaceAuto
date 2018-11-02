package utill;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * @author d
 * @date 2018/5/8 000817:20
 */
@SuppressWarnings("ALL")
public class AssertFunction {
    public static boolean flag=true;
    public static void assertEquals(WebDriver driver,Object actual,Object expected){
        try {
            Assert.assertEquals(actual,expected);
        }catch (Error e){
            LogFunction.logError("期望值为："+expected+"    ---"+"实际值为："+actual);
            ScreenshotFunction s=new ScreenshotFunction(driver);
            s.takeScreenshot();
            flag=false;
            throw new RuntimeException(e);
        }
    }


    public static void verifyEquals(WebDriver driver,Object actual,Object expected,String massage){
        try {
            Assert.assertEquals(actual,expected,massage);
        }catch (Error e){
            ScreenshotFunction s=new ScreenshotFunction(driver);
            s.takeScreenshot();
            LogFunction.logError("期望值为："+expected+"    ---"+"实际值为："+actual);
            flag=false;
        }
    }

    public static void verifyEquals(WebDriver driver,Object actual,Object expected){
        try {
            Assert.assertEquals(actual,expected);
        }catch (Error e){
            ScreenshotFunction s=new ScreenshotFunction(driver);
            s.takeScreenshot();
            LogFunction.logError("期望值为："+expected+"    ---"+"实际值为："+actual);
            flag=false;
        }
    }
}
