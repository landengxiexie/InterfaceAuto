package testPractice;
import org.testng.annotations.*;

/**
 * @author d
 * @date 2018/5/4 000417:18
 */

//
public class testOne {
    @BeforeClass
    public void signUp() {
        System.out.println("signUp");
    }

    @BeforeMethod
    public void setUp() {
        System.out.println("setUp");
    }

    @Test(dataProvider = "loginData")
    public void test1(String info) {
        System.out.println("test1++++++++"+info);
    }


    @DataProvider
    public Object[][] loginData(){
        return new Object[][]{{"a"},{"b"}};
    }
    @DataProvider
    public  Object[][] ddd(){
        return new Object[][]{{"c"},{"d"}};

    }
    @Test(dataProvider = "ddd")
    public void test112(String info){
        System.out.println("test112++++++++:"+info);
    }

    @DataProvider(name="logoutData")
    public Object[][] logoutInfo(){
        return new Object[][]{{"1"},{"2"},{"3"}};
    }
    @Test(dataProvider = "logoutData")
    public void test2(String info) {
        System.out.println("test2-----------------"+info);
    }

    @DataProvider(name = "aaa")
    public Object[][] aa(){
        return new Object[][]{{333},{666},{999}};
    }
    @Test(dataProvider = "aaa")
    public void test3(int a){
        System.out.println("test3=-=-=-=-=-=-=-"+a);
    }

    @DataProvider
    public Object[][] bb(){
        return new Object[][]{{789},{123},{456}};
    }

    @Test(dataProvider = "bb")
    public void test4(int abc){
        System.out.println("test4*********"+abc);
    }


    @AfterMethod
    public void quit() {
        System.out.println("quit");
    }

    @AfterClass
    public void close() {
        System.out.println("close");
    }
}
