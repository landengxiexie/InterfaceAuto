package com.test.utill;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Reporter;

import java.io.File;

/**
 * @author d
 * @date 2018/4/13 0013 15:17
 * @method: param(message，所要存储的日志数据)
 * logInfo,       基本信息
 * <p>
 * logError,      错误信息
 * <p>
 * logWarn,       警示信息
 */
public class LogFunction {
    private static Logger logger;
    private static String filePath = "src/log4j.properties";
    private static boolean flag = false;

    private static synchronized void getPropertyFile() {
        logger = Logger.getLogger(LogFunction.class.getName());
        PropertyConfigurator.configure(new File(filePath).getAbsolutePath());
        flag = true;
    }

    private static void getFlag() {
        if (flag == false) {
            LogFunction.getPropertyFile();
        }
    }

    public static void logInfo(String message) {
        LogFunction.getFlag();
        logger.info(new TimeString().getSimpleDateFormat()+":"+message);
        Reporter.log(new TimeString().getSimpleDateFormat()+":"+message);
    }

    public static void logError(String message) {
        LogFunction.getFlag();
        logger.info(new TimeString().getSimpleDateFormat()+":"+message);
        Reporter.log(new TimeString().getSimpleDateFormat()+":"+message);

    }

    public static void logWarn(String message) {
        LogFunction.getFlag();
        logger.info(new TimeString().getSimpleDateFormat()+":"+message);
        Reporter.log(new TimeString().getSimpleDateFormat()+":"+message);

    }

}
