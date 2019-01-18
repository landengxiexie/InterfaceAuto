package utill;

import com.bean.BaseConfig;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import utill.LogFunction;

/**
 * @author xin.wang
 *实现testng接口实现用例失败重跑
 */
public class RetryToRunCase implements IRetryAnalyzer{

    private int retryCount = 1;

    private static int maxRetryCount;

    public int getRetryCount() {
        return retryCount;
    }

    public static int getMaxRetryCount() {
        return maxRetryCount;
    }

    @SuppressWarnings("static-access")
    public RetryToRunCase(){
        this.maxRetryCount = Integer.valueOf(BaseConfig.maxRunCount);
    }

    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            String message = "running retry for  '" + result.getName() + "' on class " + this.getClass().getName() + " Retrying "
                    + retryCount + " times";
            LogFunction.logInfo(message);
            retryCount++;
            return true;
        }
        return false;
    }
}
