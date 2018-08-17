package testPractice;
import com.test.utill.AssertFunction;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


/**
 * @author d
 * @date 2018/5/8 000816:19
 */
public class TestAssert {
    WebDriver driver=null;
    @Test
    public void test2(){
        int actual=2;
        int expected=2;

            System.out.println("断言开始");
            Assert.assertEquals(actual, expected);
            System.out.println("断言结束");

    }

    @Test(dataProvider = "dataInfo")
    public void test1(String actual){
        String expected="2";
        for (int i = 0; i < 3; i++) {
            System.out.println("断言开始"+i);
            AssertFunction.verifyEquals(driver,actual, expected, "------测试2个字符串是否相同------");
            System.out.println("断言结束"+i);
        }
        Assert.assertTrue(com.test.utill.AssertFunction.flag);
    }

    @DataProvider(name = "dataInfo")
    public Object[][] aa(){
        return new Object[][]{{"1"},{"2"},{"3"}};
    }
}
