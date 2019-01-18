package utill;

import org.apache.log4j.DailyRollingFileAppender;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

@SuppressWarnings("ALL")
public class InterceptFunction {
    private static WebDriver driver;
    public static String intercept(String result, String interceptString) {
        try {
            int i = result.indexOf(interceptString);
            int length = interceptString.length();
            String interceptResult = result.substring(i, i + length);
//        LogFunction.logInfo("截取字符串的值为："+interceptResult);
            return interceptResult;
        } catch (Exception e) {
            LogFunction.logError("字符串截取失败--" + "\"" + interceptString + "\"" + "--不存在");
            throw new RuntimeException("字符串截取失败--" + "\"" + interceptString + "\"" + "--不存在");
        }
    }

    public static int interpeptNumber(String s) {
        String numberString = null;
        for (int i = 0; i < s.length(); i++) {
            String one = s.substring(i, i + 1);
            char ar = one.charAt(0);
            if (ar >= '0' && ar <= '9') {
                //是数字
                if (i == 0) {
                    numberString = String.valueOf(ar);
                } else {
                    numberString = numberString + String.valueOf(ar);
                }
            }
        }
        return Integer.valueOf(numberString);
    }

    public static boolean interceptState(String result, String interceptString) {
        boolean state = false;
        if (!state) {
            int i = result.indexOf(interceptString);
            int length = interceptString.length();
            try {
                String interceptResult = result.substring(i, i + length);
                AssertFunction.assertEquals(driver,interceptString,interceptResult);
                state = true;
                return state;
            } catch (Exception e) {
                return state;

            }

        }
        return state;
    }
}
