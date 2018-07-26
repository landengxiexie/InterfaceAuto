package com.utils;
import org.testng.Assert;

/**
 * @author d
 * @date 2018/5/8 000817:20
 */
public class AssertFunction {
    public static boolean flag=true;
    public static void assertEquals(Object actual,Object expected){
        try {
            LogFunction.logError("期望值为："+expected+"    ---"+"实际值为："+actual);
            Assert.assertEquals(actual,expected);
        }catch (Exception e){
            flag=false;
        }
    }

    public static void verifyEquals(Object actual,Object expected,String massage){
        try {
            Assert.assertEquals(actual,expected,massage);
        }catch (Error e){
            LogFunction.logError("期望值为："+expected+"    ---"+"实际值为："+actual);
            flag=false;
        }
    }
}
