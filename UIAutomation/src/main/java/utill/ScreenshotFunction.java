package utill;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class ScreenshotFunction {
    public WebDriver driver;
    private String Path="E:/testScreen/snapshot";
    public ScreenshotFunction(WebDriver driver){
        this.driver=driver;
    }
    private void takeScreenshot(String ScreenPath){
        try {
        File scrFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile,new File(ScreenPath));
        }catch (IOException e){
            LogFunction.logError("Screen shot error"+ScreenPath);
        }
    }
    public void takeScreenshot(){
//      将时间戳转换
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        String screenName=String.valueOf(simpleDateFormat.format(new Date().getTime()))+".jpg";
        File dir=new File(Path);
        if (!dir.exists()){
            dir.mkdir();
            String screenPath=dir.getAbsolutePath()+"/"+screenName;
            this.takeScreenshot(screenPath);
        }else {
            String screenPath=Path+"/"+screenName;
            this.takeScreenshot(screenPath);
        }

    }
}
