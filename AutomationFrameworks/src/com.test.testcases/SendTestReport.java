package com.test.testcases;

import com.test.bean.BaseConfig;
import com.test.utill.SendMailReportFunction;
import org.testng.annotations.Test;

public class SendTestReport {
   /* @Test
    public  void sendReport() throws InterruptedException {
        Thread.sleep(2000);
        SendMailReportFunction.sendMailReport(BaseConfig.emailSender, BaseConfig.emailAuthorizationCode, BaseConfig.emailRecipient);
    }*/

    public static void main(String[] args) {
        SendMailReportFunction.sendMailReport(BaseConfig.emailSender, BaseConfig.emailAuthorizationCode, BaseConfig.emailRecipient);
    }
}
