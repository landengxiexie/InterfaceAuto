package com.test;
import com.bean.BaseConfig;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
public class TestngRetry implements IRetryAnalyzer{
    private static int retryCount = 1;
    private  static int maxRetryCount;

    @Override
    public boolean retry(ITestResult result) {
        maxRetryCount = Integer.valueOf(BaseConfig.maxRunCount);
        if (retryCount <= maxRetryCount) {
            String message = "running retry for  '" + result.getName() + "' on class " + this.getClass().getName() + " Retrying "
                    + retryCount + " times";
            System.out.println(message);
            retryCount++;
            return true;
        }

        return false;
    }
}
