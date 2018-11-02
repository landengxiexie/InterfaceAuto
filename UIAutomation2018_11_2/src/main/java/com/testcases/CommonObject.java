package com.testcases;

import com.bean.BaseConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import utill.*;

import java.util.Map;

@SuppressWarnings("ALL")
public class CommonObject {
    public WebDriver driver;
    public LocatorFunction l;


    public CommonObject(WebDriver driver, LocatorFunction l) {
        this.driver = driver;
        this.l = l;
    }


    //      点击，新建Button
    public void createButton(Map<String, String> param) {
        WebElement commonCreate = l.getElement(param.get("commonCreate"));
        String text = commonCreate.getText();
        AssertFunction.verifyEquals(driver, text, "新建");
        commonCreate.click();
        LogFunction.logInfo("点击：" + text);
    }

    //      点击，编辑Button
    public void editButton(Map<String, String> param) {
        WebElement commonCreate = l.getElement(param.get("commonEdit"));
        String text = commonCreate.getText();
        AssertFunction.verifyEquals(driver, text, "编辑");
        commonCreate.click();
        LogFunction.logInfo("点击：" + text);
    }

    //      点击，删除Button
    public void deleteButton(Map<String, String> param) {
        WebElement commonCreate = l.getElement(param.get("commonDelete"));
        String text = commonCreate.getText();
        AssertFunction.verifyEquals(driver, text, "删除");
        commonCreate.click();
        LogFunction.logInfo("点击：" + text);

    }

    //      点击，筛选-清空Button
    public void selectClearButton(Map<String, String> param) {
        WebElement commonCreate = l.getElement(param.get("commonSelectClear"));
        String text = commonCreate.getText();
        AssertFunction.verifyEquals(driver, text, "清空");
        commonCreate.click();
        LogFunction.logInfo("点击：" + text);

    }

    //      点击，筛选(不共用)
    public void select(Map<String, String> param) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Boolean SelectArea = l.getElementIsDisplay(param.get("commonSelectArea"));
        LogFunction.logInfo("筛选区域的状态为：" + SelectArea);
        if (SelectArea == false) {
            WebElement Select1 = l.getElement(param.get("commonSelect"));
            String text = Select1.getText();
            AssertFunction.verifyEquals(driver, text, "筛选");
            Select1.click();
            LogFunction.logInfo("打开：" + text);
        }
    }

    //      校验，筛选结果，为空的情况
    public void selectResultIsNull(Map<String, String> param, String message) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//       校验，筛选结果，为空
        WebElement ResultEmpty = l.getElement(param.get("commonSelectResultIsEmpty"));
        String text10 = ResultEmpty.getText();
        LogFunction.logInfo("筛选结果为：" + text10);
        AssertFunction.verifyEquals(driver, text10, message);
    }

    //      勾选，筛选结果
    public void chooseSelectResult(Map<String, String> param) {
        WebElement selectResult = l.getElement(param.get("commonSelectResult"));
        boolean selected = selectResult.isSelected();
        LogFunction.logInfo("筛选结果是否选择：" + selected);
        if (selected == false) {
            selectResult.click();
            LogFunction.logInfo("成功，勾选筛选结果");
        } else {
            LogFunction.logInfo("勾选筛选结果，已被勾选");
        }
    }

    //      Alarm提示信息校验及确定
    public void alarmHintAndConfirm(Map<String, String> param, String hintMessage) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//       校验，提示信息
        WebElement Hint = l.getElement(param.get("commonDeleteHint"));
        String text = Hint.getText();
        LogFunction.logInfo("保存提示信息为：" + text);
        AssertFunction.verifyEquals(driver, text, hintMessage);
//        点击，确认
        WebElement Confirm = l.getElement(param.get("commonDeleteConfirm"));
        String text1 = Confirm.getText();
        AssertFunction.verifyEquals(driver, text1, BaseConfig.commonObjectConfirm);
        Confirm.click();
        LogFunction.logInfo("点击：" + text1);
    }

    //      模型一,点击按钮操作
    public void modelClickButton(Map<String, String> param,String buttonElement, String buttonName) {
        WebElement element = l.getElement(param.get(buttonElement));
        String text = element.getAttribute("value");
        AssertFunction.verifyEquals(driver,text,buttonName);
        element.click();
        LogFunction.logInfo("点击："+text);
    }

    //      模型二,警告框，确认
    public void modelAlertBox(Map<String, String> param,String hintElement, String hintName,String affirmElement,String affirmName) {
//        校验，提示信息
        WebElement element = l.getElement(param.get(hintElement));
        String text = element.getText();
        AssertFunction.verifyEquals(driver,text,hintName);
//        点击，确认按钮
        WebElement element1 = l.getElement(param.get(affirmElement));
        String text1 = element1.getText();
        AssertFunction.verifyEquals(driver,text,affirmName);
        element1.click();
        LogFunction.logInfo("点击："+text1);
    }

    //    模式三，勾选单选框
public void modelRadioBox(Map<String, String> param,String RadioBoxElement) {
//        校验，单选框是否勾选
    WebElement element = l.getElement(param.get(RadioBoxElement));
    boolean text = element.isSelected();
    LogFunction.logInfo("勾选状态为；"+text);
    if (text==false){
        element.click();

        boolean selected = element.isSelected();
        LogFunction.logInfo("勾选状态为；"+selected);

        if (selected==true){
            LogFunction.logInfo("成功,勾选");
        }else {
            LogFunction.logInfo("勾选,失败");

        }
    }else {
        LogFunction.logInfo("已被勾选");
    }
//        点击，确认按钮

}



    //   （公用）删除，告警规则
    @Test(dataProvider = "xmldata")
    public void alarmRulesDelete(Map<String, String> param,String rules) {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        勾选，规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        if (selectChooseRules.isSelected() == false) {
            selectChooseRules.click();
            LogFunction.logInfo("勾选筛选结果规则");
        }
//        点击，删除按钮
        WebElement delete = l.getElement(param.get("delete"));
        String text = delete.getText();
        delete.click();
        LogFunction.logInfo("点击:" + text);
        AssertFunction.verifyEquals(driver, text, "删除", "----验证是否点击，删除----");
//         验证，删除提示信息:确定要删除？
        WebElement deleteHint = l.getElement(param.get("deleteHint"));
        String text1 = deleteHint.getText();
        LogFunction.logInfo("删除提示信息：" + text1);
        AssertFunction.verifyEquals(driver, text1, "确定要删除？");
//          点击，删除提示信息，确认
        WebElement deleteAffirm1 = l.getElement(param.get("deleteAffirm"));
        String text4 = deleteAffirm1.getText();
        deleteAffirm1.click();
        LogFunction.logInfo("点击:" + text4);
        AssertFunction.verifyEquals(driver, text4, "确认");
//        验证，筛选区域，是否展示
        WebElement SelectArea = l.getElement(param.get("denoiseStrategySelectArea"));
        boolean displayed = SelectArea.isDisplayed();
        if (displayed == false) {
//        点击，筛选
            WebElement commonSelect = l.getElement(param.get("commonSelect"));
            String text2 = commonSelect.getText();
            commonSelect.click();
            LogFunction.logInfo("点击：" + text2);
            AssertFunction.verifyEquals(driver, text2, "筛选", "----验证点击的是否是：筛选----");
        }
//        点击，清空
//        WebElement commonSelect = l.getElement(param.get("Clear"));
//        String text3 = commonSelect.getText();
//        commonSelect.click();
//        LogFunction.logInfo("点击：" + text3);
//        AssertFunction.verifyEquals(driver, text3, "清空");

//        录入，规则名称


        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();

        selectRulesName.sendKeys(param.get(rules));
        LogFunction.logInfo("录入规则名称:" + param.get(rules));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text45 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text45);
        AssertFunction.verifyEquals(driver, text45, "确定", "----是否点击的是：确定按钮----");
//        获取，告警信息列表信息，无数据
        WebElement NumberValue = l.getElement(param.get("alarmRulesListNumberValue"));
        String text55 = NumberValue.getText();
        LogFunction.logInfo("告警列表信息为：" + text55);
        AssertFunction.verifyEquals(driver, text55, "表中数据为空");
    }


//    @BeforeClass
//    public void aa() {
//        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        SeleniumDriver seleniumDriver = new SeleniumDriver();
//        driver = seleniumDriver.getDriver();
//        l = new LocatorFunction(driver);
//        ssf = new ScreenshotFunction(driver);
//    }
//
//    @AfterClass
//    public void bb() throws InterruptedException {
//        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        Thread.sleep(3000);
//        driver.close();
//        driver.quit();
//    }

}
