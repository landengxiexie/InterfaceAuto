package com.testcases;

import com.base.TestBase;
import com.bean.BaseConfig;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;
import utill.*;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("ALL")
public class VicubeSystem extends TestBase {
    public WebDriver driver;
    private LocatorFunction l;
    private Map<String, String> param;
    private CommonObject co;
    private JavascriptExecutor e;
    private ScreenshotFunction s;

    @AfterClass
    public void testEnd() throws InterruptedException {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        Thread.sleep(3000);
        driver.close();
        driver.quit();
    }

    @BeforeClass
    public void testStart() {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        SeleniumDriver seleniumDriver = new SeleniumDriver();
        driver = seleniumDriver.getDriver();
        l = new LocatorFunction(driver);
        co = new CommonObject(driver, l);
        e = (JavascriptExecutor) driver;
        s = new ScreenshotFunction(driver);
    }


    @BeforeMethod
    public void startTime() {
        co.sleep(2000);
    }


//    -------------------------------------登录，系统设置-------------------------------------


    //    用户登陆
    @Test(dataProvider = "xmldata")
    public void userLogin(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        driver.get(param.get("VicubeUrl"));
        LogFunction.logInfo("网址成功打开：" + param.get("VicubeUrl"));
        co.sleep(1000);
        //      输入用户名
        WebElement user = l.getElement(param.get("user"));
        user.clear();
        user.sendKeys(param.get("userNameValue"));
        String userValue = param.get("userNameValue");
        LogFunction.logInfo("用户名：" + param.get("userNameValue"));
        //      输入密码
        ConnectPostgreSQL cp = new ConnectPostgreSQL();
        String passwordValue = null;
        try {
            passwordValue = cp.queryValue("select encrypted_pwd from t_user  where user_id=" + "'" + userValue + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        WebElement password = l.getElement(param.get("password"));
        password.clear();
        password.sendKeys(passwordValue);
        LogFunction.logInfo("密码：" + passwordValue);
        //      点击登陆按钮
        WebElement button = l.getElement(param.get("button"));
        String text = button.getText();
        button.click();
        LogFunction.logInfo("点击按钮:" + text);
        AssertFunction.verifyEquals(driver, text, "登录", "----是否点击登陆----");
        co.sleep(3000);
        //      验证用户登陆标识
//        WebElement userLoginID = l.getElement(param.get("userLoginID"));
//        String text1 = userLoginID.getText();
//        LogFunction.logInfo("用户登陆标识为：" + text1);
//        AssertFunction.verifyEquals(driver, text1, param.get("editNameValue"), "----是否点击登陆----");
        LogFunction.logInfo("------------------" + param.get("userNameValue") + "登陆成功-------------------");
    }

    //    用户无效登陆
    @Test(dataProvider = "xmldata", enabled = true)
    public void userInvalidLogin(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        driver.get(param.get("VicubeUrl"));
        LogFunction.logInfo("网址成功打开：" + param.get("VicubeUrl"));
        //      输入用户名
//        co.modelInputBox(param,"user","userNameValue","用户名");
        WebElement user = l.getElement(param.get("user"));
        user.clear();
        user.sendKeys(param.get("userNameValue"));
        String userValue = param.get("userNameValue");
        LogFunction.logInfo("用户名：" + param.get("userNameValue"));
        //      输入密码
        ConnectPostgreSQL cp = new ConnectPostgreSQL();
        String passwordValue = null;
        try {
            passwordValue = cp.queryValue("select encrypted_pwd from t_user  where user_id=" + "'" + userValue + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        co.sleep(500);

        WebElement password = l.getElement(param.get("password"));
        password.clear();
        password.sendKeys(passwordValue);
        LogFunction.logInfo("密码：" + passwordValue);
        //      点击登陆按钮
        co.modelClickButton(param, "button");
//        WebElement button = l.getElement(param.get("button"));
//        String text = button.getText();
//        button.click();
//        LogFunction.logInfo("点击按钮:" + text);
        co.sleep(500);
//        验证，提示信息：用户名或密码错误！
        WebElement editHint = l.getElement(param.get("userInvalidLoginHint"));
        String text3 = editHint.getText();
        LogFunction.logInfo(text3);
        AssertFunction.verifyEquals(driver, text3, "您的用户名或密码错误", "----验证提示信息：用户名或密码错误！，是否正确----");
//        点击，确认
        co.modelClickButton(param, "userInvalidLoginAffirm");
//        WebElement editAffirm = l.getElement(param.get("commonAffirm"));
//        String text4 = editAffirm.getText();
//        editAffirm.click();
//        LogFunction.logInfo("点击：" + text4);
        LogFunction.logInfo("----------------------用户无效登陆结束------------------------");

    }

    //    管理员登陆
    String passwordValue = null;

    @Test(dataProvider = "xmldata")
    public void vicubeLogin(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        driver.get(param.get("VicubeUrl"));
        LogFunction.logInfo("网址成功打开：" + param.get("VicubeUrl"));
        co.sleep(2000);
        //      输入用户名
        co.modelInputBox(param, "user", "userValue", "用户名");
        String userValue = param.get("userValue");

        //      输入密码
        ConnectPostgreSQL cp = new ConnectPostgreSQL();
        try {
            passwordValue = cp.queryValue("select encrypted_pwd from t_user  where user_id=" + "'" + userValue + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        WebElement password = l.getElement(param.get("password"));
        password.clear();
        password.sendKeys(passwordValue);
        LogFunction.logInfo("密码：" + passwordValue);
        //      点击登陆按钮
        WebElement button = l.getElement(param.get("button"));
        String text = button.getText();
        button.click();
        LogFunction.logInfo("点击按钮:" + text);
        AssertFunction.verifyEquals(driver, text, "登录", "----是否点击登陆----");
        co.sleep(6000);
        //      验证用户登陆标识
//        String valueString = co.getTextValueString(param, "userLoginID", "text");
//        LogFunction.logInfo("用户登陆标识为：" + valueString);
//        AssertFunction.assertEquals(driver, valueString, "Administrator", "----是否点击登陆----");
        LogFunction.logInfo("------------------" + userValue + "登陆成功-------------------");

    }

    //    菜单，系统设置
    @Test(dataProvider = "xmldata")
    public void systemSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        co.modelClickButton(param, "systemSetting");
        co.openLeftMenuButton(param);
        LogFunction.logInfo("-----------------打开系统设置菜单---------------------");
    }

    //    菜单-系统设置，用户管理
    @Test(dataProvider = "xmldata")
    public void userManagement(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，用户管理
        co.modelClickButton(param, "userManagement");
        LogFunction.logInfo("-----------------打开用户管理菜单---------------------");

    }

    //    菜单-系统设置，工作组管理
    @Test(dataProvider = "xmldata")
    public void workingGroupManagement(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，工作组管理
        co.modelClickButton(param, "workingGroupManagement");
        LogFunction.logInfo("-----------------打开工作组管理菜单---------------------");
    }

    //    系统设置-用户管理，新建
    @Test(dataProvider = "xmldata")
    public void userManagementCreate(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，新建
        co.createButton(param);
//         验证，保存按钮是否可用
        boolean enabled;
        WebElement save = l.getElement(param.get("createSave"));
        enabled = save.isEnabled();
        LogFunction.logInfo(save.getText() + "：按钮的状态" + enabled);
        AssertFunction.verifyEquals(driver, enabled, false, "----验证保存按钮是否可用----");
//          填写用户名
        WebElement userName = l.getElement(param.get("userName"));
        userName.sendKeys(param.get("userNameValue"));
        LogFunction.logInfo("在用户名中填入：" + param.get("userNameValue"));
//         验证，保存按钮是否可用
        enabled = save.isEnabled();
        LogFunction.logInfo(save.getText() + "：按钮的状态" + enabled);
        AssertFunction.verifyEquals(driver, enabled, false, "----验证保存按钮是否可用----");
//        填写姓名
        WebElement name = l.getElement(param.get("name"));
        name.sendKeys(param.get("nameValue"));
        LogFunction.logInfo("在姓名中填入：" + param.get("nameValue"));
//         验证，保存按钮是否可用
        enabled = save.isEnabled();
        LogFunction.logInfo(save.getText() + "：按钮的状态" + enabled);
        AssertFunction.verifyEquals(driver, enabled, false, "----验证保存按钮是否可用----");
//        填写邮箱
        WebElement email = l.getElement(param.get("email"));
        email.sendKeys(param.get("emailValue"));
        LogFunction.logInfo("在邮箱中填入：" + param.get("emailValue"));
//         验证，保存按钮是否可用
//        enabled = save.isEnabled();
//        LogFunction.logInfo(save.getText() + "：按钮的状态");
//        AssertFunction.verifyEquals(driver, enabled, false, "----验证保存按钮是否可用----");
//        点击有效标志输入框
        WebElement flag = l.getElement(param.get("commonCraeteFlag"));
        flag.click();
        LogFunction.logInfo("点击有效标志输入框");
//        选择有效标识：有效
        WebElement valid;
        try {
            valid = l.getElement(param.get("valid"));
        } catch (Exception e) {
            valid = l.getElement(param.get("Valid"));
        }
        String text1 = valid.getText();
        valid.click();
        LogFunction.logInfo("选择：" + text1);
//         验证，保存按钮是否可用
        enabled = save.isEnabled();
        LogFunction.logInfo(save.getText() + "：按钮的状态" + enabled);
        AssertFunction.verifyEquals(driver, enabled, true, "----验证保存按钮是否可用----");
//        填写岗位职责
        WebElement responsibility = l.getElement(param.get("responsibility"));
        responsibility.sendKeys(param.get("responsibilityValue"));
        LogFunction.logInfo("在岗位职责中填入：" + param.get("responsibilityValue"));
//        填写办公电话
        WebElement phone = l.getElement(param.get("phone"));
        phone.sendKeys(param.get("phoneValue"));
        LogFunction.logInfo("在办公电话中填入：" + param.get("phoneValue"));
//          填写移动电话
        WebElement mobilephone = l.getElement(param.get("mobilephone"));
        mobilephone.sendKeys(param.get("mobilephoneValue"));
        LogFunction.logInfo("在移动电话中填入：" + param.get("mobilephoneValue"));
//        填写备注
        WebElement note = l.getElement(param.get("note"));
        note.sendKeys(param.get("noteValue"));
        LogFunction.logInfo("在备注中填入：" + param.get("noteValue"));
//         验证，保存按钮是否可用
        enabled = save.isEnabled();
        LogFunction.logInfo(save.getText() + "：按钮的状态" + enabled);
        AssertFunction.verifyEquals(driver, enabled, true, "----验证保存按钮是否可用----");
        LogFunction.logInfo("-----------------用户信息创建填写通过---------------------");
    }

    //    系统设置-用户管理，新建保存
    @Test(dataProvider = "xmldata")
    public void userCreateSave(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        //        点击,保存
        WebElement createSave = l.getElement(param.get("createSave"));
        createSave.click();
        LogFunction.logInfo("点击:" + createSave.getText());
//          验证提示信息是否为：保存成功,已经向用户发送邮件！
        WebElement saveHint = l.getElement(param.get("saveHint"));
        String text1 = saveHint.getText();
        AssertFunction.verifyEquals(driver, text1, "保存成功,已经向用户发送邮件！", "----验证提示信息是否正确----");

//          提示信息，点击，确认
        WebElement saveAffirm = l.getElement(param.get("saveAffirm"));
        String text2 = saveAffirm.getText();
        saveAffirm.click();
        LogFunction.logInfo("点击:" + text2);
        LogFunction.logInfo("-----------------用户信息创建保存成功---------------------");
    }

    //    系统设置-用户管理，新建取消
    @Test(dataProvider = "xmldata")
    public void userCreateCancel(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        //        点击,取消
        WebElement cancel = l.getElement(param.get("cancel"));
        cancel.click();
        LogFunction.logInfo("点击:" + cancel.getText());
//          验证提示信息是否为：确定要退出？
        WebElement cancelHint = l.getElement(param.get("cancelHint"));
        String text1 = cancelHint.getText();
        AssertFunction.verifyEquals(driver, text1, "确定要退出？", "----验证提示信息是否正确----");
//          提示信息，点击，确认
        WebElement cancelAffirm = l.getElement(param.get("cancelAffirm"));
        String text2 = cancelAffirm.getText();
        cancelAffirm.click();
        LogFunction.logInfo("点击:" + text2);
        LogFunction.logInfo("------------------用户信息创建取消完成---------------------");
    }

    //    系统设置-用户管理，用户筛选
    @Test(dataProvider = "xmldata")
    public void userSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，筛选
        co.select(param, "Area");
//        输入用户名
        WebElement selectusername = l.getElement(param.get("selectUserName"));
        selectusername.sendKeys(param.get("userNameValue"));
        LogFunction.logInfo("输入用户名：" + param.get("userNameValue"));
//        输入姓名
        WebElement selectName = l.getElement(param.get("selectName"));
        selectName.sendKeys(param.get("nameValue"));
        LogFunction.logInfo("输入姓名：" + param.get("nameValue"));
//        输入邮箱
        WebElement selectemail = l.getElement(param.get("selectEmail"));
        selectemail.sendKeys(param.get("emailValue"));
        LogFunction.logInfo("输入邮箱：" + param.get("emailValue"));
//        点击，有效标志
//            WebElement selectFlag = l.getElement(param.get("commonSelectFlag"));
//            selectFlag.click();
//            LogFunction.logInfo("点击：有效标志");
//        选择有效标识：有效
//        WebElement selectValid = l.getElement(param.get("commonSelectValid"));
//        String text1 = selectValid.getText();
//        selectValid.click();
//        LogFunction.logInfo("选择："+text1);
//        点击，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
        co.sleep(2000);
//        验证用户名
        WebElement selectUsernameValue = l.getElement(param.get("selectUsernameValue"));
        String text3 = selectUsernameValue.getText();
        AssertFunction.verifyEquals(driver, text3, param.get("userNameValue"), "----验证用户名是否正确----");
//        验证姓名
        WebElement selectNameValue = l.getElement(param.get("selectNameValue"));
        String text4 = selectNameValue.getText();
        AssertFunction.verifyEquals(driver, text4, param.get("nameValue"), "----验证姓名是否正确----");
//        验证邮箱
        WebElement selecteMailValue = l.getElement(param.get("selecteMailValue"));
        String text5 = selecteMailValue.getText();
        AssertFunction.verifyEquals(driver, text5, param.get("emailValue"), "----验证邮箱是否正确----");
//        验证岗位职责
        WebElement selectResponsibilityValue = l.getElement(param.get("selectResponsibilityValue"));
        String text6 = selectResponsibilityValue.getText();
        AssertFunction.verifyEquals(driver, text6, param.get("responsibilityValue"), "----验证岗位职责是否正确----");
//        验证办公电话
        WebElement selectPhoneValue = l.getElement(param.get("selectPhoneValue"));
        String text7 = selectPhoneValue.getText();
        AssertFunction.verifyEquals(driver, text7, param.get("phoneValue"), "----验证办公电话是否正确----");
//        验证移动电话
        WebElement selectMobilephoneValue = l.getElement(param.get("selectMobilephoneValue"));
        String text8 = selectMobilephoneValue.getText();
        AssertFunction.verifyEquals(driver, text8, param.get("mobilephoneValue"), "----验证移动电话是否正确----");
//        验证备注
        WebElement selectNoteValue = l.getElement(param.get("selectNoteValue"));
        String text9 = selectNoteValue.getText();
        AssertFunction.verifyEquals(driver, text9, param.get("noteValue"), "----验证备注是否正确----");
//        验证有效标志
        WebElement selectValidValue = l.getElement(param.get("selectValidValue"));
        String text10 = selectValidValue.getText();
        AssertFunction.verifyEquals(driver, text10, "有效", "----验证备注是否正确----");
//        勾选用户
        WebElement selectChooseUser = l.getElement(param.get("commonSystemSetupSelectChoose"));
        selectChooseUser.click();
        LogFunction.logInfo("勾选筛选结果用户");
        LogFunction.logInfo("-----------------用户信息筛选查询完成---------------------");
    }

    //    系统设置-用户管理，筛选按钮
    @Test(dataProvider = "xmldata")
    public void clickSelectButton(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，筛选按钮
        co.select(param, "area");
    }

    @Test(dataProvider = "xmldata")
    public void chooseUserButton(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        //        勾选用户
        WebElement commonSystemSetupSelectChoose = l.getElement(param.get("commonSystemSetupSelectChoose"));
        commonSystemSetupSelectChoose.click();
        LogFunction.logInfo("勾选筛选结果用户");
        LogFunction.logInfo("-------------------勾选用户成功-------------------");
    }

    //  系统设置-用户管理-编辑
    @Test(dataProvider = "xmldata")
    public void userEdit(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击编辑
        co.editButton(param);
////        点击，微信确认
//        WebElement editAffirm = l.getElement(param.get("editAffirm"));
//        String text2 = editAffirm.getText();
//        editAffirm.click();
//        LogFunction.logInfo("点击：" + text);
        co.sleep(1000);
//        验证，用户名是否可编辑
        WebElement editUserName = l.getElement(param.get("editUserName"));
        boolean enabled = editUserName.isEnabled();
        AssertFunction.verifyEquals(driver, enabled, false, "----验证，用户名是否可编辑----");
//        编辑，姓名
        co.modelInputBox(param, "editnName", "editNameValue", "姓名");
//        WebElement editnName = l.getElement(param.get("editnName"));
//        editnName.clear();
//        editnName.sendKeys(param.get("editNameValue"));
//        LogFunction.logInfo("姓名修改为：" + param.get("editNameValue"));
//        编辑，岗位职责
        co.modelInputBox(param, "editResponsibility", "editResponsibilityValue", "岗位职责");
//        WebElement editResponsibility = l.getElement(param.get("editResponsibility"));
//        editResponsibility.clear();
//        editResponsibility.sendKeys(param.get("editResponsibilityValue"));
//        LogFunction.logInfo("岗位职责修改为：" + param.get("editResponsibilityValue"));
//        编辑，办公电话
        co.modelInputBox(param, "editPhone", "editPhoneValue", "办公电话");
//        WebElement editPhone = l.getElement(param.get("editPhone"));
//        editPhone.clear();
//        editPhone.sendKeys(param.get("editPhoneValue"));
//        LogFunction.logInfo("办公电话修改为：" + param.get("editPhoneValue"));
//        编辑，移动电话
        co.modelInputBox(param, "editMobilePhone", "editMobilePhoneValue", "移动电话");
//        WebElement editMobilePhone = l.getElement(param.get("editMobilePhone"));
//        editMobilePhone.clear();
//        editMobilePhone.sendKeys(param.get("editMobilePhoneValue"));
//        LogFunction.logInfo("移动电话修改为：" + param.get("editMobilePhoneValue"));
//        编辑，email
        co.modelInputBox(param, "editEmail", "editEmailValue", "email");
//        WebElement editEmail = l.getElement(param.get("editEmail"));
//        editEmail.clear();
//        editEmail.sendKeys(param.get("editEmailValue"));
//        LogFunction.logInfo("email修改为：" + param.get("editEmailValue"));
//        编辑，备注
        co.modelInputBox(param, "editNote", "editNoteValue", "备注");
//        WebElement editNote = l.getElement(param.get("editNote"));
//        editNote.clear();
//        editNote.sendKeys(param.get("editNoteValue"));
//        LogFunction.logInfo("备注修改为：" + param.get("editNoteValue"));
//        点击，有效标志
        WebElement editFlag = l.getElement(param.get("editFlag"));
        editFlag.click();
        LogFunction.logInfo("点击:有效标志");
//        选择，无效
        WebElement editInvalid;
        try {
            editInvalid = l.getElement(param.get("editInvalid"));
        } catch (Exception e) {
            editInvalid = l.getElement(param.get("commonInvalid1"));
        }
        String text1 = editInvalid.getText();
        editInvalid.click();
        LogFunction.logInfo("选择：" + text1);
        LogFunction.logInfo("-----------------用户信息编辑填写完成---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void commonSave(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        //        点击，保存
        WebElement editSave = l.getElement(param.get("editSave"));
        String text2 = editSave.getText();
        editSave.click();
        LogFunction.logInfo("点击：" + text2);
//        验证，提示信息：保存成功
        WebElement editHint = l.getElement(param.get("editSaveHint"));
        String text3 = editHint.getText();
        AssertFunction.verifyEquals(driver, text3, "保存成功", "----验证提示信息：保存成功，是否正确----");
//        点击，确认
        WebElement editAffirm = l.getElement(param.get("editSaveAffirm"));
        String text4 = editAffirm.getText();
        editAffirm.click();
        LogFunction.logInfo("点击：" + text4);
        LogFunction.logInfo("----------------------保存成功------------------------");
    }

    @Test(dataProvider = "xmldata")
    public void editSave(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        //        点击，保存
        WebElement editSave = l.getElement(param.get("editSave"));
        String text2 = editSave.getText();
        editSave.click();
        LogFunction.logInfo("点击：" + text2);
//        验证，提示信息：保存成功
        WebElement editHint = l.getElement(param.get("editSaveHint"));
        String text3 = editHint.getText();
        AssertFunction.verifyEquals(driver, text3, "保存成功", "----验证提示信息：保存成功，是否正确----");
//        点击，确认
        WebElement editAffirm = l.getElement(param.get("editSaveAffirm"));
        String text4 = editAffirm.getText();
        editAffirm.click();
        LogFunction.logInfo("点击：" + text4);
        LogFunction.logInfo("----------------------保存成功------------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userEditSaveSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        输入用户名
        WebElement selectusername = l.getElement(param.get("selectUserName"));
        selectusername.clear();
        selectusername.sendKeys(param.get("userNameValue"));
        LogFunction.logInfo("输入用户名：" + param.get("userNameValue"));
//        输入姓名
        WebElement selectName = l.getElement(param.get("selectName"));
        selectName.clear();
        selectName.sendKeys(param.get("editNameValue"));
        LogFunction.logInfo("输入姓名：" + param.get("editNameValue"));
//        输入邮箱
        WebElement selectemail = l.getElement(param.get("selectEmail"));
        selectemail.clear();
        selectemail.sendKeys(param.get("editEmailValue"));
        LogFunction.logInfo("输入邮箱：" + param.get("editEmailValue"));
//        点击，有效标志
       /* WebElement selectFlag = l.getElement(param.get("selectFlag"));
        selectFlag.click();
        LogFunction.logInfo("点击：有效标志");*/
//        选择有效标识：有效
      /*  WebElement selectValid = l.getElement(param.get("selectValid"));
        String text1 = selectValid.getText();
        selectValid.click();
        LogFunction.logInfo("选择："+text1);*/
//        点击，确定
        WebElement selectAffirm = l.getElement(param.get("selectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
        co.sleep(2000);
//        验证用户名
        WebElement selectUsernameValue = l.getElement(param.get("selectUsernameValue"));
        String text3 = selectUsernameValue.getText();
        AssertFunction.verifyEquals(driver, text3, param.get("userNameValue"), "----验证用户名是否正确----");
//        验证姓名
        WebElement selectNameValue = l.getElement(param.get("selectNameValue"));
        String text4 = selectNameValue.getText();
        AssertFunction.verifyEquals(driver, text4, param.get("editNameValue"), "----验证姓名是否正确----");
//        验证邮箱
        WebElement selecteMailValue = l.getElement(param.get("selecteMailValue"));
        String text5 = selecteMailValue.getText();
        AssertFunction.verifyEquals(driver, text5, param.get("editEmailValue"), "----验证邮箱是否正确----");
//        验证岗位职责
        WebElement selectResponsibilityValue = l.getElement(param.get("selectResponsibilityValue"));
        String text6 = selectResponsibilityValue.getText();
        AssertFunction.verifyEquals(driver, text6, param.get("editResponsibilityValue"), "----验证岗位职责是否正确----");
//        验证办公电话
        WebElement selectPhoneValue = l.getElement(param.get("selectPhoneValue"));
        String text7 = selectPhoneValue.getText();
        AssertFunction.verifyEquals(driver, text7, param.get("editPhoneValue"), "----验证办公电话是否正确----");
//        验证移动电话
        WebElement selectMobilephoneValue = l.getElement(param.get("selectMobilephoneValue"));
        String text8 = selectMobilephoneValue.getText();
        AssertFunction.verifyEquals(driver, text8, param.get("editMobilePhoneValue"), "----验证移动电话是否正确----");
//        验证备注
        WebElement selectNoteValue = l.getElement(param.get("selectNoteValue"));
        String text9 = selectNoteValue.getText();
        AssertFunction.verifyEquals(driver, text9, param.get("editNoteValue"), "----验证备注是否正确----");
//        验证有效标志
        WebElement selectValidValue = l.getElement(param.get("selectValidValue"));
        String text10 = selectValidValue.getText();
        AssertFunction.verifyEquals(driver, text10, "无效", "----验证备注是否正确----");
//        勾选用户
        WebElement selectChooseUser = l.getElement(param.get("selectChooseUser"));
        selectChooseUser.click();
        LogFunction.logInfo("勾选筛选结果用户");
        LogFunction.logInfo("-----------------用户信息编辑后筛选查询验证完成---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userEditSaveSelect1(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        输入用户名
        WebElement selectusername = l.getElement(param.get("selectUserName"));
        selectusername.clear();
        selectusername.sendKeys(param.get("userNameValue"));
        LogFunction.logInfo("输入用户名：" + param.get("userNameValue"));
//        输入姓名
        WebElement selectName = l.getElement(param.get("selectName"));
        selectName.clear();
        selectName.sendKeys(param.get("editNameValue"));
        LogFunction.logInfo("输入用户名：" + param.get("editNameValue"));
//        输入邮箱
        WebElement selectemail = l.getElement(param.get("selectEmail"));
        selectemail.clear();
        selectemail.sendKeys(param.get("editEmailValue"));
        LogFunction.logInfo("输入用户名：" + param.get("editEmailValue"));
//        点击，有效标志
       /* WebElement selectFlag = l.getElement(param.get("selectFlag"));
        selectFlag.click();
        LogFunction.logInfo("点击：有效标志");*/
//        选择有效标识：有效
      /*  WebElement selectValid = l.getElement(param.get("selectValid"));
        String text1 = selectValid.getText();
        selectValid.click();
        LogFunction.logInfo("选择："+text1);*/
//        点击，确定
        WebElement selectAffirm = l.getElement(param.get("selectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        验证用户名
        WebElement selectUsernameValue = l.getElement(param.get("selectUsernameValue"));
        String text3 = selectUsernameValue.getText();
        AssertFunction.verifyEquals(driver, text3, param.get("userNameValue"), "----验证用户名是否正确----");
//        验证姓名
        WebElement selectNameValue = l.getElement(param.get("selectNameValue"));
        String text4 = selectNameValue.getText();
        AssertFunction.verifyEquals(driver, text4, param.get("editNameValue"), "----验证姓名是否正确----");
//        验证邮箱
        WebElement selecteMailValue = l.getElement(param.get("selecteMailValue"));
        String text5 = selecteMailValue.getText();
        AssertFunction.verifyEquals(driver, text5, param.get("editEmailValue"), "----验证邮箱是否正确----");
//        验证岗位职责
        WebElement selectResponsibilityValue = l.getElement(param.get("selectResponsibilityValue"));
        String text6 = selectResponsibilityValue.getText();
        AssertFunction.verifyEquals(driver, text6, param.get("editResponsibilityValue"), "----验证岗位职责是否正确----");
//        验证办公电话
        WebElement selectPhoneValue = l.getElement(param.get("selectPhoneValue"));
        String text7 = selectPhoneValue.getText();
        AssertFunction.verifyEquals(driver, text7, param.get("editPhoneValue"), "----验证办公电话是否正确----");
//        验证移动电话
        WebElement selectMobilephoneValue = l.getElement(param.get("selectMobilephoneValue"));
        String text8 = selectMobilephoneValue.getText();
        AssertFunction.verifyEquals(driver, text8, param.get("editMobilePhoneValue"), "----验证移动电话是否正确----");
//        验证备注
        WebElement selectNoteValue = l.getElement(param.get("selectNoteValue"));
        String text9 = selectNoteValue.getText();
        AssertFunction.verifyEquals(driver, text9, param.get("editNoteValue"), "----验证备注是否正确----");
//        验证有效标志
        WebElement selectValidValue = l.getElement(param.get("selectValidValue"));
        String text10 = selectValidValue.getText();
        AssertFunction.verifyEquals(driver, text10, "有效", "----验证备注是否正确----");
//        勾选用户
        WebElement selectChooseUser = l.getElement(param.get("selectChooseUser"));
        selectChooseUser.click();
        LogFunction.logInfo("勾选筛选结果用户");
        LogFunction.logInfo("-----------------用户信息编辑后筛选查询验证完成---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userEditCancel(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //        点击编辑
        WebElement edit = l.getElement(param.get("edit"));
        String text = edit.getText();
        edit.click();
        LogFunction.logInfo("点击：" + text);
//       点击,取消
        WebElement editCancle = l.getElement(param.get("editCancel"));
        editCancle.click();
        LogFunction.logInfo("点击:" + editCancle.getText());
//          验证提示信息是否为：确定要退出？
        WebElement editCancleHint = l.getElement(param.get("editCancelHint"));
        String text1 = editCancleHint.getText();
        AssertFunction.verifyEquals(driver, text1, "确定要退出？", "----验证提示信息是否正确----");
//          提示信息，点击，确认
        WebElement editCancleAffirm = l.getElement(param.get("editCancelAffirm"));
        String text2 = editCancleAffirm.getText();
        editCancleAffirm.click();
        LogFunction.logInfo("点击:" + text2);
        LogFunction.logInfo("------------------用户信息编辑取消完成---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userChangeState(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，更改状态
        WebElement commonChangeState = l.getElement(param.get("commonChangeState"));
        String text = commonChangeState.getText();
        commonChangeState.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "更改状态", "----验证是否点击：更改状态按钮----");
//        验证，提示信息：是否改变用户状态？
        WebElement commonHint = l.getElement(param.get("commonHint"));
        String text3 = commonHint.getText();
        LogFunction.logInfo(text3);
        AssertFunction.verifyEquals(driver, text3, "是否改变用户状态？", "----验证提示信息：是否改变用户状态？，是否正确----");
//        点击，确认
        WebElement commonAffirm = l.getElement(param.get("commonAffirm"));
        String text1 = commonAffirm.getText();
        commonAffirm.click();
        LogFunction.logInfo("点击：" + text1);

        co.sleep(1000);

//        验证，提示信息：设置成功!
        WebElement commonHint1 = l.getElement(param.get("commonHint"));
        String text31 = commonHint1.getText();
        LogFunction.logInfo("用户更改状态成功提示信息：" + text31);
        AssertFunction.verifyEquals(driver, text31, "设置成功！", "----验证提示信息：设置成功！，是否正确----");
//        点击，确认
        WebElement commonAffirm1 = l.getElement(param.get("commonAffirm"));
        String text11 = commonAffirm1.getText();
        commonAffirm1.click();
        LogFunction.logInfo("点击：" + text11);
        co.sleep(2000);
//        勾选用户
//        WebElement selectChooseUser = l.getElement(param.get("commonSystemSetupSelectChoose"));
//        selectChooseUser.click();
//        LogFunction.logInfo("勾选筛选结果用户");
        LogFunction.logInfo("----------------------状态更改成功------------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userGroupChangeState(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，更改状态
        WebElement commonChangeState;
        try {
            commonChangeState = l.getElement(param.get("commonChangeState"));
        } catch (Exception e) {
            commonChangeState = l.getElement(param.get("commonChangeState1"));
        }
        String text = commonChangeState.getText();
        commonChangeState.click();
        LogFunction.logInfo("点击：" + text);
//        验证，提示信息：是否改变用户状态？
        WebElement commonHint = l.getElement(param.get("commonHint"));
        String text3 = commonHint.getText();
        AssertFunction.verifyEquals(driver, text3, "是否改变用户状态？", "----验证提示信息：是否改变用户状态？，是否正确----");
//        点击，确认
        WebElement commonAffirm = l.getElement(param.get("commonAffirm"));
        String text1 = commonAffirm.getText();
        commonAffirm.click();
        LogFunction.logInfo("点击：" + text1);
        co.sleep(1000);
//        验证，提示信息：设置成功!
        WebElement commonHint1 = l.getElement(param.get("commonHint"));
        String text31 = commonHint1.getText();
        LogFunction.logInfo("工作组改变状态成功提示信息" + text31);
        AssertFunction.verifyEquals(driver, text31, "设置成功！", "----验证提示信息：设置成功！，是否正确----");
//        点击，确认
        WebElement commonAffirm1 = l.getElement(param.get("commonAffirm"));
        String text11 = commonAffirm1.getText();
        commonAffirm1.click();
        LogFunction.logInfo("点击：" + text11);
        co.sleep(1000);
//        勾选用户
//        WebElement selectChooseUser = l.getElement(param.get("commonSystemSetupSelectChoose"));
//        selectChooseUser.click();
//        LogFunction.logInfo("勾选筛选结果用户");
        LogFunction.logInfo("----------------------状态更改成功------------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userDelete(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，删除
        WebElement delete = l.getElement(param.get("delete"));
        String text = delete.getText();
        delete.click();
        LogFunction.logInfo("点击:" + text);
        AssertFunction.verifyEquals(driver, text, "删除", "----验证是否点击，删除----");
//         验证，删除提示信息:确定要删除？
        WebElement deleteHint = l.getElement(param.get("deleteHint"));
        String text1 = deleteHint.getText();
        LogFunction.logInfo("删除提示信息：" + text1);
        AssertFunction.verifyEquals(driver, text1, "确定要删除？", "----验证，删除提示信息:是否删除当前用户？----");
//        点击，是否删除提示确认
        co.modelClickButton(param, "deleteAffirm");
//        WebElement deleteAffirm = l.getElement(param.get("deleteAffirm"));
//        String text2 = deleteAffirm.getText();
//        deleteAffirm.click();
//        LogFunction.logInfo("点击:" + text2);
//        AssertFunction.verifyEquals(driver, text2, "确认", "----验证是否点击，确认----");
        co.sleep(1000);
//         验证，删除成功提示：删除用户成功！
        WebElement deleteHint1 = l.getElement(param.get("deleteHint"));
        String text3 = deleteHint1.getText();
        LogFunction.logInfo("用户删除成功提示信息：" + text3);
        AssertFunction.verifyEquals(driver, text3, "删除用户成功！", "----验证，删除提示信息:删除用户成功！----");
//          点击，删除成功提示确认
        co.modelClickButton(param, "deleteAffirm");
//        WebElement deleteAffirm1 = l.getElement(param.get("deleteAffirm"));
//        String text4 = deleteAffirm1.getText();
//        deleteAffirm1.click();
//        LogFunction.logInfo("点击:" + text4);
//        AssertFunction.verifyEquals(driver, text4, "确认", "----验证是否点击，确认----");
        LogFunction.logInfo("------------------用户信息删除成功---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userGroupDelete(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，删除
        WebElement delete = l.getElement(param.get("delete"));
        String text = delete.getText();
        delete.click();
        LogFunction.logInfo("点击:" + text);
        AssertFunction.verifyEquals(driver, text, "删除", "----验证是否点击，删除----");
//         验证，删除提示信息:确定要删除？
        WebElement deleteHint = l.getElement(param.get("deleteHint"));
        String text1 = deleteHint.getText();
        LogFunction.logInfo("删除提示信息：" + text1);
        AssertFunction.verifyEquals(driver, text1, "确定要删除？", "----验证，删除提示信息:是否删除当前用户？----");
//        点击，是否删除提示确认
        WebElement deleteAffirm = l.getElement(param.get("deleteAffirm"));
        String text2 = deleteAffirm.getText();
        deleteAffirm.click();
        LogFunction.logInfo("点击:" + text2);
        AssertFunction.verifyEquals(driver, text2, "确认", "----验证是否点击，确认----");
        co.sleep(1000);
//         验证，删除成功提示：删除用户成功！
        WebElement deleteHint1 = l.getElement(param.get("deleteHint"));
        String text3 = deleteHint1.getText();
        LogFunction.logInfo("工作组删除成功提示信息：" + text3);
        AssertFunction.verifyEquals(driver, text3, "删除组成功！", "----验证，删除提示信息:删除用户成功！----");
//          点击，删除成功提示确认
        WebElement deleteAffirm1 = l.getElement(param.get("deleteAffirm"));
        String text4 = deleteAffirm1.getText();
        deleteAffirm1.click();
        LogFunction.logInfo("点击:" + text4);
        AssertFunction.verifyEquals(driver, text4, "确认", "----验证是否点击，确认----");
        LogFunction.logInfo("------------------用户组删除成功---------------------");
    }


    @Test(dataProvider = "xmldata")
    public void userResetPassword(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，重置密码
        WebElement resetPassword = l.getElement(param.get("resetPassword"));
        String text = resetPassword.getText();
        resetPassword.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "重置密码", "----验证是否点击重置密码----");
//        验证，重置密码，提示：是否重置当前用户密码？
        WebElement resetPasswordHint = l.getElement(param.get("resetPasswordHint"));
        String text1 = resetPasswordHint.getText();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "是否重置当前用户密码？", "----验证重置密码提示：是否重置当前用户密码？----");
//        点击，重置密码,确认
        WebElement resetPasswordAffirm = l.getElement(param.get("resetPasswordAffirm"));
        String text2 = resetPasswordAffirm.getText();
        resetPasswordAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确认", "----验证是否点击确认----");
        co.sleep(1000);
//        验证，重置密码成功，提示：重置密码成功,已经向用户发送邮件！
        WebElement resetPasswordHint1 = l.getElement(param.get("resetPasswordHint"));
        String text3 = resetPasswordHint1.getText();
        LogFunction.logInfo("重置密码成功提示：" + text3);
        AssertFunction.verifyEquals(driver, text3, "重置密码成功,已经向用户发送邮件！", "----重置密码成功,已经向用户发送邮件！----");
//        点击，重置密码成功,确认
        WebElement resetPasswordAffirm1 = l.getElement(param.get("resetPasswordAffirm"));
        String text5 = resetPasswordAffirm1.getText();
        resetPasswordAffirm1.click();
        LogFunction.logInfo("点击：" + text5);
        LogFunction.logInfo("------------------用户信息重置密码成功---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userAssignmentGroup(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，分配工作组
        co.modelClickButton(param, "assignmentGroup");
        co.sleep(1000);
//        选择，用户组
        WebElement selectGroup = l.getElement(param.get("chooseWorkingGroupSelect"));
        Select select = new Select(selectGroup);
        List<WebElement> allSelectedOptions = select.getOptions();
        ArrayList<String> ws = new ArrayList<>();
        for (WebElement element : allSelectedOptions) {
            ws.add(element.getText());
            LogFunction.logInfo("用户组为" + element.getText());
        }
        boolean contains = ws.contains(param.get("editWorkingGroupNameValue"));
        if (contains == true) {
            select.selectByVisibleText(param.get("editWorkingGroupNameValue"));
            LogFunction.logInfo("选择：" + param.get("editWorkingGroupNameValue"));
            //        点击，右移按钮
            WebElement selectGroupRightMove = l.getElement(param.get("selectGroupRightMove"));
            selectGroupRightMove.click();
            LogFunction.logInfo("点击：右移按钮");
            //        点击，保存
            co.modelClickButton(param, "assignmentGroupSave");
//            WebElement assignmentGroupSave = l.getElement(param.get("assignmentGroupSave"));
//            String text2 = assignmentGroupSave.getText();
//            assignmentGroupSave.click();
//            LogFunction.logInfo("点击：" + text2);
//            AssertFunction.verifyEquals(driver, text2, "保存", "----是否点击保存----");
        } else {
            WebElement selectGroup1 = l.getElement(param.get("chooseWorkingGroupSelectRight"));
            Select select1 = new Select(selectGroup1);
            List<WebElement> allSelectedOptions1 = select1.getOptions();
            ArrayList<String> wss = new ArrayList<>();
            for (WebElement e : allSelectedOptions1) {
                wss.add(e.getText());
            }
            boolean contains1 = wss.contains(param.get("editWorkingGroupNameValue"));
            if (contains1 == true) {
                //        点击，保存
                co.modelClickButton(param, "assignmentGroupSave");
//                WebElement assignmentGroupSave = l.getElement(param.get("assignmentGroupSave"));
//                String text2 = assignmentGroupSave.getText();
//                assignmentGroupSave.click();
//                LogFunction.logInfo("点击：" + text2);
//                AssertFunction.verifyEquals(driver, text2, "保存", "----是否点击保存----");
            } else {
                throw new RuntimeException(param.get("editWorkingGroupNameValue") + "不存在");
            }
        }
//          验证，提示信息：设置用户组成功！
        WebElement commonHint = l.getElement(param.get("commonHint"));
        String text3 = commonHint.getText();
        AssertFunction.verifyEquals(driver, text3, "设置用户组成功！", "----验证提示信息：设置用户组成功！，是否正确----");
//        点击，确认
        WebElement commmonAffirm = l.getElement(param.get("commonAffirm"));
        String text4 = commmonAffirm.getText();
        commmonAffirm.click();
        LogFunction.logInfo("点击：" + text4);
        LogFunction.logInfo("------------------用户分配工作组成功---------------------");
    }

    //      用户管理-工作组管理-分配管理员
    @Test(dataProvider = "xmldata")
    public void workingGroupAssignmentManagement(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        co.sleep(2000);
//        点击，分配管理员
        co.modelClickButton(param, "assignmentManagement");
//        选择，管理员
        WebElement selectUser = l.getElement(param.get("chooseUser"));
        String text1 = selectUser.getText();
        selectUser.click();
        LogFunction.logInfo("选择：" + text1);
        AssertFunction.verifyEquals(driver, text1, param.get("editNameValue"), "----是否选择" + param.get("editNameValue") + "工作组----");
//        点击，右移按钮
        WebElement selectGroupRightMove = l.getElement(param.get("selectUserRightMove"));
        selectGroupRightMove.click();
        LogFunction.logInfo("点击：右移按钮");
//        点击，保存
        WebElement assignmentGroupSave = l.getElement(param.get("assignmentUserSave"));
        String text2 = assignmentGroupSave.getText();
        assignmentGroupSave.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "保存", "----是否点击保存----");
//          验证，提示信息：设置用户成功！
        WebElement commonHint = l.getElement(param.get("commonHint"));
        String text3 = commonHint.getText();
        AssertFunction.verifyEquals(driver, text3, "设置用户成功！", "----验证提示信息：设置用户组成功！，是否正确----");
//        点击，确认
        WebElement commmonAffirm = l.getElement(param.get("commonAffirm"));
        String text4 = commmonAffirm.getText();
        commmonAffirm.click();
        LogFunction.logInfo("点击：" + text4);
        LogFunction.logInfo("------------------工作组管理：分配用户成功---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void workingGroupAssignmentUser(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        co.sleep(2000);
//        点击，分配用户
        co.modelClickButton(param, "assignmentUser");
//        选择，用户
        WebElement selectUser = l.getElement(param.get("chooseUser"));
        selectUser.click();
        String text1 = selectUser.getText();
        LogFunction.logInfo("选择：" + text1);
        AssertFunction.verifyEquals(driver, text1, param.get("editNameValue"), "----是否选择" + param.get("editNameValue") + "工作组----");
//        点击，右移按钮
        WebElement selectGroupRightMove = l.getElement(param.get("selectUserRightMove"));
        selectGroupRightMove.click();
        LogFunction.logInfo("点击：右移按钮");
//        点击，保存
        WebElement assignmentGroupSave = l.getElement(param.get("assignmentUserSave"));
        String text2 = assignmentGroupSave.getText();
        assignmentGroupSave.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "保存", "----是否点击保存----");
//          验证，提示信息：设置用户成功！
        WebElement commonHint = l.getElement(param.get("commonHint"));
        String text3 = commonHint.getText();
        AssertFunction.verifyEquals(driver, text3, "设置用户成功！", "----验证提示信息：设置用户组成功！，是否正确----");
//        点击，确认
        WebElement commmonAffirm = l.getElement(param.get("commonAffirm"));
        String text4 = commmonAffirm.getText();
        commmonAffirm.click();
        LogFunction.logInfo("点击：" + text4);
        LogFunction.logInfo("------------------工作组管理：分配用户成功---------------------");
    }

    //     用户管理-工作组管理-新建
    @Test(dataProvider = "xmldata")
    public void workingGroupManagementGreate(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，新建
        co.createButton(param);
        co.sleep(1000);
//        录入，工作组名称
        WebElement workingGroupName = l.getElement(param.get("createWorkingGroupName"));
        workingGroupName.sendKeys(param.get("createWorkingGroupNameValue"));
        LogFunction.logInfo("录入工作组名称:" + param.get("createWorkingGroupNameValue"));
//        点击，有效标识输入框
        WebElement flag = l.getElement(param.get("commonCraeteFlag"));
        flag.click();
        LogFunction.logInfo("点击有效标志输入框");
//        选择有效标识：无效
        WebElement valid;
        try {
            valid = l.getElement(param.get("commonInvalid"));
        } catch (Exception e) {
            valid = l.getElement(param.get("commonInvalid1"));
        }
        String text1 = valid.getText();
        valid.click();
        LogFunction.logInfo("选择：" + text1);
//        点击域选择框
        WebElement domainNameSelectBox = l.getElement(param.get("createDomainNameSelectBox"));
        domainNameSelectBox.click();
        LogFunction.logInfo("点击:域名选择框");
//        选择域：rootDomain
        WebElement domainName = l.getElement(param.get("createDomainName"));
        String text2 = domainName.getText();
        domainName.click();
        LogFunction.logInfo("选择：" + text2);
        AssertFunction.verifyEquals(driver, text2, "rootDomain", "----验证是否选择域：rootDomain----");
//    录入，工作组备注
        WebElement Note = l.getElement(param.get("creatWorkingGroupeNote"));
        Note.sendKeys(param.get("creatWorkingGroupeNoteValue"));
        LogFunction.logInfo("录入工作组备注:" + param.get("creatWorkingGroupeNoteValue"));
        LogFunction.logInfo("------------------工作组保存填写完成---------------------");
    }

    //     用户管理-工作组管理-编辑
    @Test(dataProvider = "xmldata")
    public void workingGroupManagementEdit(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击编辑
        co.editButton(param);
//        录入，工作组名称
        co.modelInputBox(param, "editWorkingGroupName", "editWorkingGroupNameValue", "工作组名称");
//        WebElement createWorkingGroupName = l.getElement(param.get("editWorkingGroupName"));
//        createWorkingGroupName.clear();
//        createWorkingGroupName.sendKeys(param.get("editWorkingGroupNameValue"));
//        LogFunction.logInfo("录入工作组名称:" + param.get("editWorkingGroupNameValue"));
//        点击，有效标识输入框
        WebElement flag = l.getElement(param.get("commonCraeteFlag"));
        flag.click();
        LogFunction.logInfo("点击有效标志输入框");
//        选择有效标识：有效
        WebElement valid;
        try {
            valid = l.getElement(param.get("commonValid"));
        } catch (Exception e) {
            valid = l.getElement(param.get("commonValid1"));
        }

        String text1 = valid.getText();
        valid.click();
        LogFunction.logInfo("选择：" + text1);
//        点击域选择框
        co.modelClickAndChooseValue(param, "domainNameSelectBox", "editDomainName", "域");
//        WebElement domainNameSelectBox = l.getElement(param.get("domainNameSelectBox"));
//        domainNameSelectBox.click();
//        LogFunction.logInfo("点击域名选择框");
////        选择域：rootDomain
//        WebElement domainName = l.getElement(param.get("editDomainName"));
//        String text2 = domainName.getText();
//        domainName.click();
//        LogFunction.logInfo("选择：" + text2);
//        AssertFunction.verifyEquals(driver, text2, "rootDomain", "----验证是否选择域：XMDomain----");
//         录入，工作组备注
        co.modelInputBox(param, "editWorkingGroupeNote", "editWorkingGroupeNoteValue", "工作组备注");
//        WebElement Note = l.getElement(param.get("editWorkingGroupeNote"));
//        Note.clear();
//        Note.sendKeys(param.get("editWorkingGroupeNoteValue"));
//        LogFunction.logInfo("录入工作组备注:" + param.get("editWorkingGroupeNoteValue"));
        LogFunction.logInfo("------------------工作组编辑完成---------------------");

    }

    @Test(dataProvider = "xmldata")
    public void refresh(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        driver.navigate().refresh();
        co.sleep(2000);
//        获取头像提示信息的个数
        int size = l.getElements(param.get("userNotificationNumber")).size();
        LogFunction.logInfo("提示信息个数：" + size);
//        点击，头像提示信息
        for (int i = 0; i < size; i++) {
            if (l.getElement(param.get("userNotification")).isDisplayed()) {
                WebElement userNotification = l.getElement(param.get("userNotification"));
                userNotification.click();
                String text1 = userNotification.getText();
                LogFunction.logInfo("关闭提示信息：" + text1);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //      用户管理-工作组管理-编辑查询
    @Test(dataProvider = "xmldata")
    public void workingGroupManagementEditSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，筛选
        co.select(param, "area");
//        录入，工作组名称
        WebElement selectusername = l.getElement(param.get("inputSelectWorkingGroupname"));
        selectusername.clear();
        selectusername.sendKeys(param.get("editWorkingGroupNameValue"));
        LogFunction.logInfo("录入工作组名称：" + param.get("editWorkingGroupNameValue"));

//        点击，有效标志
        WebElement selectFlag = l.getElement(param.get("commonSelectFlag"));
        selectFlag.click();
        LogFunction.logInfo("点击：有效标志");
//        选择有效标识：有效
        WebElement selectValid;
        try {
            selectValid = l.getElement(param.get("commonValid"));
        } catch (Exception e) {
            selectValid = l.getElement(param.get("commonValid1"));
        }

        String text1 = selectValid.getText();
        selectValid.click();
        LogFunction.logInfo("选择：" + text1);
//        点击，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        co.sleep(2000);
//        验证工作组名称
        WebElement selectUsernameValue = l.getElement(param.get("selectworkinggroupnameValue"));
        String text3 = selectUsernameValue.getText();
        AssertFunction.verifyEquals(driver, text3, param.get("editWorkingGroupNameValue"), "----验证工作组名称是否正确----");
//        验证有效标志
        WebElement selectValidValue = l.getElement(param.get("selectWorkingGroupFlagValue"));
        String text10 = selectValidValue.getText();
        AssertFunction.verifyEquals(driver, text10, "有效", "----验证有效标志是否正确----");
//        验证备注
        WebElement NoteValue = l.getElement(param.get("selectWorkingGroupNoteValue"));
        String text11 = NoteValue.getText();
        AssertFunction.verifyEquals(driver, text11, param.get("editWorkingGroupeNoteValue"), "----验证备注内容是否正确----");
//        勾选用户
        WebElement selectChooseUser = l.getElement(param.get("commonSystemSetupSelectChoose"));
        selectChooseUser.click();
        LogFunction.logInfo("勾选筛选结果");
        LogFunction.logInfo("-------------------筛选查询完成---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void workingGroupManagementInvalidSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，筛选
//        WebElement select = l.getElement(param.get("commonSelect"));
//        String text = select.getText();
//        select.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "筛选", "----是否点击的是：筛选按钮----");
//        录入，工作组名称
        WebElement selectusername = l.getElement(param.get("inputSelectWorkingGroupname"));
        selectusername.clear();
        selectusername.sendKeys(param.get("editWorkingGroupNameValue"));
        LogFunction.logInfo("录入工作组名称：" + param.get("editWorkingGroupNameValue"));

//        点击，有效标志
        WebElement selectFlag = l.getElement(param.get("commonSelectFlag"));
        selectFlag.click();
        LogFunction.logInfo("点击：有效标志");
//        选择有效标识：无效
        WebElement selectInvalid;
        try {
            selectInvalid = l.getElement(param.get("commonInvalid1"));
        } catch (Exception e) {
            selectInvalid = l.getElement(param.get("commonInvalid"));
        }
        String text1 = selectInvalid.getText();
        selectInvalid.click();
        LogFunction.logInfo("选择：" + text1);
//        点击，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        co.sleep(2000);
//        验证工作组名称
        WebElement selectUsernameValue = l.getElement(param.get("selectworkinggroupnameValue"));
        String text3 = selectUsernameValue.getText();
        AssertFunction.verifyEquals(driver, text3, param.get("editWorkingGroupNameValue"), "----验证工作组名称是否正确----");
//        验证有效标志
        WebElement selectValidValue = l.getElement(param.get("selectWorkingGroupFlagValue"));
        String text10 = selectValidValue.getText();
        AssertFunction.verifyEquals(driver, text10, "无效", "----验证有效标志是否正确----");
//        验证备注
        WebElement NoteValue = l.getElement(param.get("selectWorkingGroupNoteValue"));
        String text11 = NoteValue.getText();
        AssertFunction.verifyEquals(driver, text11, param.get("editWorkingGroupeNoteValue"), "----验证备注内容是否正确----");
//        勾选用户
        WebElement selectChooseUser = l.getElement(param.get("commonSystemSetupSelectChoose"));
        selectChooseUser.click();
        LogFunction.logInfo("勾选筛选结果");
        LogFunction.logInfo("-------------------筛选查询完成---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void workingGroupManagementSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，筛选
        co.select(param, "area");
//        录入，工作组名称
        WebElement selectusername = l.getElement(param.get("inputSelectWorkingGroupname"));
        selectusername.sendKeys(param.get("createWorkingGroupNameValue"));
        LogFunction.logInfo("录入工作组名称：" + param.get("createWorkingGroupNameValue"));

//        点击，有效标志
        WebElement selectFlag = l.getElement(param.get("commonSelectFlag"));
        selectFlag.click();
        LogFunction.logInfo("点击：有效标志");
//        选择有效标识：无效
        WebElement selectInvalid;
        try {
            selectInvalid = l.getElement(param.get("commonInvalid1"));
        } catch (Exception e) {
            selectInvalid = l.getElement(param.get("commonInvalid"));
        }

        String text1 = selectInvalid.getText();
        selectInvalid.click();
        LogFunction.logInfo("选择：" + text1);
//        点击，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        co.sleep(2000);
//        验证工作组名称
        WebElement selectUsernameValue = l.getElement(param.get("selectworkinggroupnameValue"));
        String text3 = selectUsernameValue.getText();
        AssertFunction.verifyEquals(driver, text3, param.get("createWorkingGroupNameValue"), "----验证工作组名称是否正确----");
//        验证无效标志
        WebElement selectValidValue = l.getElement(param.get("selectWorkingGroupFlagValue"));
        String text10 = selectValidValue.getText();
        AssertFunction.verifyEquals(driver, text10, "无效", "----验证有效标志是否正确----");
//        验证备注
        WebElement NoteValue = l.getElement(param.get("selectWorkingGroupNoteValue"));
        String text11 = NoteValue.getText();
        AssertFunction.verifyEquals(driver, text11, param.get("creatWorkingGroupeNoteValue"), "----验证备注内容是否正确----");
//        勾选用户
        WebElement selectChooseUser = l.getElement(param.get("commonSystemSetupSelectChoose"));
        selectChooseUser.click();
        LogFunction.logInfo("勾选筛选结果");
        LogFunction.logInfo("-------------------筛选查询完成---------------------");
    }

    //      用户管理-分配权限
    @Test(dataProvider = "xmldata")
    public void userAllocationPrivilege(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        co.sleep(2000);
//        点击，分配权限
        co.modelClickButton(param, "commonAllocationPrivilege");
        co.sleep(1000);
        //        选择权限：
        co.modelClickButton(param, "choosePrivilege");
//        WebElement element = l.getElement(param.get("choosePrivilege"));
//        element.click();
//        String text1 = element.getText();
//        LogFunction.logInfo("选择：" + text1);
//        AssertFunction.verifyEquals(driver, text1, "BJ-UPS1", "----验证是否选择：BJ-UPS1----");
//        选择权限：BJ-UPS2
//        WebElement element1 = l.getElement(param.get("choosePrivilegeBJ-UPS2"));
//        element1.click();
//        String text2 = element1.getText();
//        LogFunction.logInfo("选择：BJ-UPS2");
//        AssertFunction.verifyEquals(driver, text2, "BJ-UPS2", "----验证是否选择：BJ-UPS1----");
////        选择权限：BJ-UPS3
//        WebElement element2 = l.getElement(param.get("choosePrivilegeBJ-UPS3"));
//        element2.click();
//        String text3 = element2.getText();
//        LogFunction.logInfo("选择：BJ-UPS3");
//        AssertFunction.verifyEquals(driver, text3, "BJ-UPS3", "----验证是否选择：BJ-UPS1----");
//        选择权限：BJ-UPS4
//        WebElement element3 = l.getElement(param.get("choosePrivilegeBJ-UPS4"));
//        element3.click();
//        String text4 = element3.getText();
//        LogFunction.logInfo("选择：BJ-UPS4");
//        AssertFunction.verifyEquals(driver, text4, "BJ-UPS4", "----验证是否选择：BJ-UPS1----");
//        点击，右移按钮
        co.modelClickButton(param, "choosePrivilegeMoveRight", "右移按钮", "");
//        WebElement choosePrivilegeMoveRight = l.getElement(param.get("choosePrivilegeMoveRight"));
//        choosePrivilegeMoveRight.click();
//        LogFunction.logInfo("点击：右移按钮");
        LogFunction.logInfo("-------------------分配权限完成---------------------");

    }

    @Test(dataProvider = "xmldata")
    public void userAllocationPrivilegeSave(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        //        点击，保存
        co.modelClickButton(param, "allocationSave");
//        验证，提示信息：保存成功
        WebElement editHint = l.getElement(param.get("allocationSaveHint"));
        String text3 = editHint.getText();
        AssertFunction.verifyEquals(driver, text3, "授权成功！", "----验证提示信息：保存成功，是否正确----");
//        点击，确认
        WebElement editAffirm = l.getElement(param.get("allocationSaveAffirm"));
        String text4 = editAffirm.getText();
        editAffirm.click();
        LogFunction.logInfo("点击：" + text4);
        LogFunction.logInfo("---------------------分配权限保存成功------------------------");
    }

    //自动发现提示信息
    @Test(dataProvider = "xmldata")
    public void promptMessage(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        获取自动发现提示信息
        int size = l.getElements(param.get("userNotificationNumber")).size();
        LogFunction.logInfo("提示信息个数：" + size);

//        关闭，自动发现提示信息
        for (int i = 0; i < size; i++) {
            if (l.getElement(param.get("userNotification")).isDisplayed()) {
                WebElement userNotification = l.getElement(param.get("userNotification"));
                userNotification.click();
                String text1 = userNotification.getText();
                LogFunction.logInfo("关闭提示信息：" + text1);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test(dataProvider = "xmldata")
    public void userLogoutSystem(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，用户头像
        co.modelClickButton(param, "userLogoutHead", "用户头像", "");
//        WebElement userlogouthead = l.getElement(param.get("userLogoutHead"));
//        userlogouthead.click();
//        String text = userlogouthead.getText();
//        LogFunction.logInfo("点击用户：" + text);
//        点击，退出系统
        co.modelClickButton(param, "userLogoutSystem");
//        WebElement userLogoutSystem = l.getElement(param.get("userLogoutSystem"));
//        String text2 = userLogoutSystem.getText();
//        userLogoutSystem.click();
//        LogFunction.logInfo("点击：" + text2);
        LogFunction.logInfo("---------------------退出系统成功------------------------");
    }

    @Test(dataProvider = "xmldata", enabled = false)
    public void userViewConfig(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，“+”
        WebElement userviewconfigplus = l.getElement(param.get("userViewConfigPlus"));
        userviewconfigplus.click();
        LogFunction.logInfo("点击：“+”");
//        选择，自定义视图8586-BJ-UPS1
        WebElement userViewConfigViewOne = l.getElement(param.get("userViewConfigViewOne"));
        userViewConfigViewOne.click();
        String text = userViewConfigViewOne.getText();
        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver,text,"8586-BJ-UPS1","----验证是否选择视图:8588-BJ-UPS1----");
//        选择，自定义视图8585-BJ-UPS2
//        WebElement userViewConfigViewTwo = l.getElement(param.get("userViewConfigViewTwo"));
//        userViewConfigViewTwo.click();
//        String text1 = userViewConfigViewTwo.getText();
//        LogFunction.logInfo("点击：" + text1);
////        AssertFunction.verifyEquals(driver,text1,"8585-BJ-UPS2","----验证是否选择视图:8588-BJ-UPS2----");
////        选择，自定义视图8587-BJ-UPS3
//        WebElement userViewConfigViewThree = l.getElement(param.get("userViewConfigViewThree"));
//        userViewConfigViewThree.click();
//        String text2 = userViewConfigViewThree.getText();
//        LogFunction.logInfo("点击：" + text2);
////        AssertFunction.verifyEquals(driver,text2,"8587-BJ-UPS3","----验证是否选择视图:8588-BJ-UPS3----");
////        选择，自定义视图8588-BJ-UPS4
//        WebElement userViewConfigViewFour = l.getElement(param.get("userViewConfigViewFour"));
//        userViewConfigViewFour.click();
//        String text3 = userViewConfigViewFour.getText();
//        LogFunction.logInfo("点击：" + text3);
////        AssertFunction.verifyEquals(driver,text3,"8588-BJ-UPS4","----验证是否选择视图:8588-BJ-UPS4----");
////        点击，右移
//        WebElement userViewConfigMoveRight = l.getElement(param.get("userViewConfigMoveRight"));
//        userViewConfigMoveRight.click();
//        LogFunction.logInfo("点击：右移");
//        点击，保存
        WebElement userViewConfigSave = l.getElement(param.get("userViewConfigSave"));
        String text4 = userViewConfigSave.getText();
        userViewConfigSave.click();
        LogFunction.logInfo("点击：" + text4);
        AssertFunction.verifyEquals(driver, text4, "保存", "验证是否点击：保存");
//        验证，提示信息：视图保存成功
        WebElement commonHint = l.getElement(param.get("commonHint"));
        String text31 = commonHint.getText();
        AssertFunction.verifyEquals(driver, text31, "视图保存成功", "----验证提示信息：视图保存成功，是否正确----");
//        点击，确认
        WebElement commonAffirm = l.getElement(param.get("commonAffirm"));
        String text11 = commonAffirm.getText();
        commonAffirm.click();
        LogFunction.logInfo("点击：" + text11);
//        断言，视图是否添加成功
        WebElement userViewConfig8586BJUPS1 = l.getElement(param.get("userViewConfig8586BJUPS1"));
        String text5 = userViewConfig8586BJUPS1.getText();
        AssertFunction.verifyEquals(driver, text5, "8586-BJ-UPS1", "----断言视图：8586-BJ-UPS1是否添加成功");
        LogFunction.logInfo("---------------------新用户视图添加成功------------------------");

    }

    @Test(dataProvider = "xmldata")
    public void auditManagement(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        //        点击，审计管理
        co.modelClickButton(param, "auditManagement");
        co.sleep(2000);
        LogFunction.logInfo("-----------------打开审计管理菜单---------------------");
    }

    //  系统设置-审计管理-查看
    @Test(dataProvider = "xmldata")
    public void auditManagementViewLog(Map<String, String> param) {
//        选择，日志
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        WebElement userManagement = l.getElement(param.get("selectLog"));
        userManagement.click();
        LogFunction.logInfo("成功勾选：日志");
//        点击，查看
        WebElement view = l.getElement(param.get("view"));
        String text = view.getText();
        view.click();
        LogFunction.logInfo("成功点击：" + text);
//        验证，审计详情，是否打开
        WebElement auditDetail = l.getElement(param.get("auditDetail"));
        String text1 = auditDetail.getText();
        LogFunction.logInfo("日志标题为：" + text1);
//        点击，关闭，审计详情
        WebElement closeAuditDetail = l.getElement(param.get("closeAuditDetail"));
        String text2 = closeAuditDetail.getText();
        closeAuditDetail.click();
        AssertFunction.verifyEquals(driver, text2, "关闭", "-----验证是否点击：关闭----");
        LogFunction.logInfo("成功点击：" + text2 + "关闭审计详情页");
        LogFunction.logInfo("-----------------审计管理展示日志查看成功---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void auditManagementSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，筛选，按钮
        co.select(param, "Area");
//        录入，模块名称
        WebElement modelName = l.getElement(param.get("modelName"));
        modelName.sendKeys(param.get("modelNameValue"));
        LogFunction.logInfo("模块名称录入：" + param.get("modelNameValue"));
//        录入，操作账号
        WebElement operationID = l.getElement(param.get("operationID"));
        operationID.sendKeys(param.get("operationIDValue"));
        LogFunction.logInfo("操作账号录入：" + param.get("operationIDValue"));
//        录入，功能名称
        WebElement functionName = l.getElement(param.get("functionName"));
        functionName.sendKeys(param.get("functionNameValue"));
        LogFunction.logInfo("功能名称录入：" + param.get("functionNameValue"));
//       点击，筛选，确定
        co.modelClickButton(param, "confirm");
        co.sleep(3000);
//        验证，筛选结果，操作账号
        WebElement selectOperationID = l.getElement(param.get("selectOperationID"));
        String text2 = selectOperationID.getText();
        LogFunction.logInfo("筛选后的,操作账号为：" + text2);
        AssertFunction.assertEquals(driver, text2, param.get("operationIDValue"), "-----验证筛选结果，操作账号是否正确----");
        LogFunction.logInfo("-----------------审计管理查询功能验证完成---------------------");
    }

    //      用户管理-分配权限-菜单模块
    @Test(dataProvider = "xmldata")
    public void menuModle(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，模块菜单
        WebElement menuModle = l.getElement(param.get("menuModle"));
        String text = menuModle.getText();
        menuModle.click();
        AssertFunction.verifyEquals(driver, text, "菜单模块", "-----验证是否点击：菜单模块----");
        LogFunction.logInfo("成功打开：" + text);
//        勾选，系统管理
        WebElement systemManagement = l.getElement(param.get("systemManagement"));
        systemManagement.click();
        LogFunction.logInfo("成功勾选：系统管理");
        LogFunction.logInfo("-----------------模块菜单选择完毕---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void verifyMenuSystemManagement(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        co.sleep(5000);
//        验证系统设置是否存在
        String valueString = co.getTextValueString(param, "commonSystemManagement", "text");
//        WebElement systemManagement = l.getElement(param.get("commonSystemManagement"));
//        String text = systemManagement.getText();
        AssertFunction.verifyEquals(driver, valueString, "系统设置", "-----验证是否点击：系统设置----");
        LogFunction.logInfo("成功打开：" + valueString);
        LogFunction.logInfo("-----------------菜单系统设置添加，验证通过---------------------");

    }


//    -------------------------------------集中告警-------------------------------------

    //   菜单-集中告警
    @Test(dataProvider = "xmldata")
    public void concentratedAlarm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        点击，集中告警
        co.modelClickButton(param, "concentratedAlarm");
//        WebElement alarmClassifyRules = l.getElement(param.get(""));
//        String text = alarmClassifyRules.getText();
//        alarmClassifyRules.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "集中告警");
        LogFunction.logInfo("-----------------进入，集中告警页面---------------------");
    }

    //      菜单-集中告警-告警配置
    @Test(dataProvider = "xmldata")
    public void alarmConfig(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，告警配置
        co.modelAreaDisplayAndClick(param,"alarmConfigArea","alarmConfig");
//        co.modelClickButton(param, "alarmConfig");
        LogFunction.logInfo("-----------------进入，告警配置页面---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void denoiseStrategy(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，降噪策略
        co.modelClickButton(param, "denoiseStrategy");
        LogFunction.logInfo("-----------------进入，降噪策略页面---------------------");
    }

    String Oracal = null;
    String Domain = null;
    String Priority = null;

    @Test(dataProvider = "xmldata", enabled = false)
    public void alarmClassifyRulesCreate(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        co.sleep(1000);
//        点击，全部策略
        WebElement allRules = l.getElement(param.get("allRules"));
        String text2 = allRules.getText();
        allRules.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "全部策略", "----验证是否点击：全部策略按钮----");
//        点击，告警分类策略
        WebElement alarmClassifyRules = l.getElement(param.get("alarmClassifyRules"));
        String text = alarmClassifyRules.getText();
        alarmClassifyRules.click();
        LogFunction.logInfo("选择：" + text);
        AssertFunction.verifyEquals(driver, text, "告警分类策略", "告警分类策略");
        co.sleep(1000);
//          点击，新建
        WebElement commonCreate = l.getElement(param.get("commonCreate"));
        String text1 = commonCreate.getText();
        commonCreate.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "新建", "----是否点击新建----");
//          录入，规则名称
        WebElement rulesName = l.getElement(param.get("rulesName"));
        rulesName.sendKeys(param.get("alarmClassifyRulesNameValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("alarmClassifyRulesNameValue"));
//          选择，优先级，高
        WebElement priority = l.getElement(param.get("priority"));
        Priority = priority.getText();
        priority.click();
        LogFunction.logInfo("勾选优先级" + Priority);
//        点击，基础设置，下一步
        WebElement basicsNextStep = l.getElement(param.get("basicsNextStep"));
        String text3 = basicsNextStep.getText();
        basicsNextStep.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "下一步", "----验证是否点击：下一步----");
//         点击，域
        WebElement domain = l.getElement(param.get("domain"));
        domain.click();
        LogFunction.logInfo("点击：域");
//        选择，域：XMDomain
        WebElement chooseDomain = l.getElement(param.get("chooseDomain"));
        Domain = chooseDomain.getText();
        chooseDomain.click();
        LogFunction.logInfo("选择域：" + Domain);
        AssertFunction.verifyEquals(driver, Domain, "XMDomain", "----验证选择的域是否为：rootDomain----");
//          点击，类型
        WebElement type = l.getElement(param.get("type"));
        type.click();
        LogFunction.logInfo("点击：类型");
//        选择,类型:Oracle
        WebElement chooseType = l.getElement(param.get("chooseType"));
        Oracal = chooseType.getText();
        chooseType.click();
        LogFunction.logInfo("选择类型：" + Oracal);
        AssertFunction.verifyEquals(driver, Oracal, "Oracle", "----验证选择的类型是否为；Oracle----");
//        点击，告警类型选择
        WebElement alarmType = l.getElement(param.get("alarmType"));
        alarmType.click();
        LogFunction.logInfo("点击：告警类型选择");
//        选择,告警类型选择:Oracle_System_Alert
        WebElement chooseAlarmType = l.getElement(param.get("chooseAlarmType"));
        String text6 = chooseAlarmType.getText();
        chooseAlarmType.click();
        LogFunction.logInfo("选择告警类型：" + text6);
        AssertFunction.verifyEquals(driver, text6, "Oracle_System_Alert", "----验证选择的告警类型是否为；Oracle_System_Alert----");
//       点击，规则条件设置，下一步
        WebElement rulesConditionConfigNextStep = l.getElement(param.get("rulesConditionConfigNextStep"));
        String text7 = rulesConditionConfigNextStep.getText();
        rulesConditionConfigNextStep.click();
        LogFunction.logInfo("点击：" + text7);
        AssertFunction.verifyEquals(driver, text7, "下一步", "----验证是否点击：下一步----");
//        点击，节点过滤
        WebElement nodeFilter = l.getElement(param.get("nodeFilter"));
        nodeFilter.click();
        LogFunction.logInfo("点击：节点过滤");
//        选择,节点过滤:StandardTest-Oracle2
        WebElement chooseNodeFilter = l.getElement(param.get("chooseNodeFilter"));
        String text8 = chooseNodeFilter.getText();
        chooseNodeFilter.click();
        LogFunction.logInfo("选择节点过滤：" + text8);
        AssertFunction.verifyEquals(driver, text8, "StandardTest-Oracle2", "----验证选择的节点过滤是否为；StandardTest-Oracle2----");
//        点击，空白处
        WebElement blank = l.getElement(param.get("blank"));
        blank.click();
        LogFunction.logInfo("点击：空白处");
//        点击，采集系统选择
        WebElement acquisitionSystemChoose = l.getElement(param.get("acquisitionSystemChoose"));
        acquisitionSystemChoose.click();
        LogFunction.logInfo("点击：采集系统选择");
//        选择,采集系统选择:IBM ITCAM
        WebElement chooseAcquisitionSystemChoose = l.getElement(param.get("chooseAcquisitionSystemChoose"));
        String text9 = chooseAcquisitionSystemChoose.getText();
        chooseAcquisitionSystemChoose.click();
        LogFunction.logInfo("选择采集系统选择：" + text9);
        AssertFunction.verifyEquals(driver, text9, "IBM ITCAM", "----验证选择的采集系统选择是否为；IBM ITCAM----");
//        点击，空白处
        WebElement blank1 = l.getElement(param.get("blank"));
        blank1.click();
        LogFunction.logInfo("点击：空白处");
//        录入，内容关键字
        WebElement contentKeyword = l.getElement(param.get("contentKeyword"));
        contentKeyword.sendKeys(param.get("contentKeywordValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("contentKeywordValue"));
//       点击，规则条件高级设置，下一步
        WebElement rulesConditionAdvancedConfigNextStep = l.getElement(param.get("rulesConditionAdvancedConfigNextStep"));
        String text71 = rulesConditionAdvancedConfigNextStep.getText();
        rulesConditionAdvancedConfigNextStep.click();
        LogFunction.logInfo("点击：" + text71);
        AssertFunction.verifyEquals(driver, text71, "下一步", "----验证是否点击：下一步----");
//        点击，新的警告类型
        WebElement newAlarmType = l.getElement(param.get("newAlarmType"));
        newAlarmType.click();
        LogFunction.logInfo("点击：新的警告类型");
//        选择,新的警告类型:Oracle_PGA_Alert
        WebElement chooseNewAlarmType = l.getElement(param.get("chooseNewAlarmType"));
        String text91 = chooseNewAlarmType.getText();
        chooseNewAlarmType.click();
        LogFunction.logInfo("选择新的警告类型：" + text91);
        AssertFunction.verifyEquals(driver, text91, "Oracle_PGA_Alert", "----验证选择的新的警告类型是否为；Oracle_PGA_Alert----");
//        点击，新的告警级别
        WebElement newAlarmRank = l.getElement(param.get("newAlarmRank"));
        newAlarmRank.click();
        LogFunction.logInfo("点击：新的告警级别");
//        点击,新的警告类型，Deselect All
        WebElement deselectAll = l.getElement(param.get("deselectAll"));
        String text11 = deselectAll.getText();
        deselectAll.click();
        LogFunction.logInfo("点击：" + text11);
        AssertFunction.verifyEquals(driver, text11, "Deselect All", "----验证点击的是否为；取消全选----");
//        选择,新的警告类型:CRITICAL
        WebElement chooseNewAlarmRank = l.getElement(param.get("chooseNewAlarmRank"));
        String text12 = chooseNewAlarmRank.getText();
        chooseNewAlarmRank.click();
        LogFunction.logInfo("选择新的告警级别：" + text12);
        AssertFunction.verifyEquals(driver, text12, "CRITICAL", "----验证选择的新的告警级别是否为；CRITICAL----");
//        点击，空白处
        WebElement blank2 = l.getElement(param.get("blank"));
        blank2.click();
        LogFunction.logInfo("点击：空白处");
//          点击，保存
        WebElement alarmClassifyConfigSave = l.getElement(param.get("alarmClassifyConfigSave"));
        String text10 = alarmClassifyConfigSave.getText();
        alarmClassifyConfigSave.click();
        LogFunction.logInfo("点击：" + text10);
        AssertFunction.verifyEquals(driver, text10, "保存");
        LogFunction.logInfo("-----------------告警分类策略，创建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-选择，告警分类策略
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyChooseAlarmClassifyRules(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        点击，全部策略
        WebElement allRules = l.getElement(param.get("allRules"));
        String text2 = allRules.getText();
        String allRulesValue = InterceptFunction.intercept(text2, "全部策略");
        allRules.click();
        LogFunction.logInfo("点击：" + allRulesValue);
        AssertFunction.verifyEquals(driver, allRulesValue, "全部策略", "----验证是否点击：全部策略按钮----");
//        点击，告警分类策略
        WebElement alarmClassifyRules = l.getElement(param.get("alarmClassifyRules"));
        String text = alarmClassifyRules.getText();
        alarmClassifyRules.click();
        LogFunction.logInfo("选择：" + text);
        AssertFunction.verifyEquals(driver, text, "告警分类策略", "告警分类策略");
        LogFunction.logInfo("-----------------选择：告警分类策略---------------------");
    }

    //    集中告警-告警配置-降噪策略-选择，告警合并策略
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyChooseAlarmMergeRules(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
//        点击，全部策略
        co.modelClickButton(param, "allRules");
        co.sleep(300);
//        点击，告警合并策略
        co.modelClickButton(param, "alarmMergeRules");
        LogFunction.logInfo("-----------------选择：告警合并策略---------------------");
    }

    //    集中告警-告警配置-降噪策略-选择，告警过滤策略
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyChooseAlarmFilterRules(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
//        点击，全部策略
        co.modelClickButton(param, "allRules");
//        点击，告警过滤策略
        co.modelClickButton(param, "alarmFilterRules");
        LogFunction.logInfo("-----------------选择：告警过滤策略---------------------");
    }

    //    集中告警-告警配置-降噪策略-选择，告警恢复策略
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyChooseAlarmRecoveryRules(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，全部策略
        WebElement allRules = l.getElement(param.get("allRules"));
        String text2 = allRules.getText();
        String allRulesValue = InterceptFunction.intercept(text2, "全部策略");
        allRules.click();
        LogFunction.logInfo("点击：" + allRulesValue);
        AssertFunction.verifyEquals(driver, allRulesValue, "全部策略", "----验证是否点击：全部策略按钮----");
//        点击，告警恢复策略
        WebElement alarmRecoveryRules = l.getElement(param.get("alarmRecoveryRules"));
        String text = alarmRecoveryRules.getText();
        alarmRecoveryRules.click();
        LogFunction.logInfo("选择：" + text);
        AssertFunction.verifyEquals(driver, text, "告警恢复策略", "告警恢复策略");
        LogFunction.logInfo("-----------------选择：告警恢复策略---------------------");
    }

    //    集中告警-告警配置-降噪策略-选择，告警升级策略
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyChooseAlarmUpgradeRules(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，全部策略
        WebElement allRules = l.getElement(param.get("allRules"));
        String text2 = allRules.getText();
        String allRulesValue = InterceptFunction.intercept(text2, "全部策略");
        allRules.click();
        LogFunction.logInfo("点击：" + allRulesValue);
        AssertFunction.verifyEquals(driver, allRulesValue, "全部策略", "----验证是否点击：全部策略按钮----");
//        点击，告警升级策略
        WebElement alarmRecoveryRules = l.getElement(param.get("alarmUpgradeRules"));
        String text = alarmRecoveryRules.getText();
        alarmRecoveryRules.click();
        LogFunction.logInfo("选择：" + text);
        AssertFunction.verifyEquals(driver, text, "告警升级策略", "告警升级策略");
        LogFunction.logInfo("-----------------选择：告警升级策略---------------------");
    }

    //    集中告警-告警配置-降噪策略-选择，告警关联策略
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyChooseAlarmRelevanceRules(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，全部策略
        co.modelClickButton(param, "allRules");
//        点击，告警关联策略
        co.modelClickButton(param, "AlarmRelevanceRules");
        LogFunction.logInfo("-----------------选择：告警关联策略---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类策略-新建第1部分，基础设置
    @Test(dataProvider = "xmldata")
    public void alarmClassifyRulesCreateBasicSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//          点击，新建
        WebElement commonCreate = l.getElement(param.get("commonCreate"));
        String text1 = commonCreate.getText();
        commonCreate.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "新建", "----是否点击新建----");
//          录入，规则名称
        WebElement rulesName = l.getElement(param.get("rulesName"));
        rulesName.sendKeys(param.get("alarmClassifyRulesNameValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("alarmClassifyRulesNameValue"));
//          选择，优先级，高
        WebElement priority = l.getElement(param.get("priority"));
        Priority = priority.getText();
        priority.click();
        LogFunction.logInfo("勾选优先级" + Priority);
//        点击，基础设置，下一步
        WebElement basicsNextStep = l.getElement(param.get("basicsNextStep"));
        String text3 = basicsNextStep.getText();
        basicsNextStep.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警分类策略，第一步，基础设置录入完成---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警分类策略-新建第2部分，规则条件设置
    @Test(dataProvider = "xmldata")
    public void alarmClassifyRulesCreateRulesConditionSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//         点击，域
        WebElement domain = l.getElement(param.get("domain"));
        domain.click();
        LogFunction.logInfo("点击：域");
//        选择，域：rootDomain
        WebElement chooseDomain = l.getElement(param.get("chooseDomain"));
        Domain = chooseDomain.getText();
        chooseDomain.click();
        LogFunction.logInfo("选择域：" + Domain);
        AssertFunction.verifyEquals(driver, Domain, "rootDomain", "----验证选择的域是否为：rootDomain----");
//          点击，类型
        WebElement type = l.getElement(param.get("type"));
        type.click();
        LogFunction.logInfo("点击：类型");
//        选择,类型:操作系统
        WebElement chooseType = l.getElement(param.get("chooseType"));
        Oracal = chooseType.getText();
        chooseType.click();
        LogFunction.logInfo("选择类型：" + Oracal);
        AssertFunction.verifyEquals(driver, Oracal, "Oracle", "----验证选择的类型是否为；Oracle----");
//        点击，告警类型选择
//        WebElement alarmType = l.getElement(param.get("alarmType"));
//        alarmType.click();
//        LogFunction.logInfo("点击：告警类型选择");
//        选择,告警类型选择:Oracle_System_Alert
//        WebElement chooseAlarmType = l.getElement(param.get("chooseAlarmType"));
//        String text6 = chooseAlarmType.getText();
//        chooseAlarmType.click();
//        LogFunction.logInfo("选择告警类型：" + text6);
//        AssertFunction.verifyEquals(driver, text6, "Oracle_System_Alert", "----验证选择的告警类型是否为；Oracle_System_Alert----");
//       点击，规则条件设置，下一步
        WebElement rulesConditionConfigNextStep = l.getElement(param.get("rulesConditionConfigNextStep"));
        String text7 = rulesConditionConfigNextStep.getText();
        rulesConditionConfigNextStep.click();
        LogFunction.logInfo("点击：" + text7);
        AssertFunction.verifyEquals(driver, text7, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警分类策略，第二步，规则条件设置录入完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类策略-新建第3部分，规则条件高级设置
    @Test(dataProvider = "xmldata")
    public void alarmClassifyRulesCreateRulesConditionAdvancedSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        点击，节点过滤
//        WebElement nodeFilter = l.getElement(param.get("nodeFilter"));
//        nodeFilter.click();
//        LogFunction.logInfo("点击：节点过滤");
////        选择,节点过滤:StandardTest-Oracle2
//        WebElement chooseNodeFilter = l.getElement(param.get("chooseNodeFilter"));
//        String text8 = chooseNodeFilter.getText();
//        chooseNodeFilter.click();
//        LogFunction.logInfo("选择节点过滤：" + text8);
//        AssertFunction.verifyEquals(driver, text8, "StandardTest-Oracle2", "----验证选择的节点过滤是否为；StandardTest-Oracle2----");
////        点击，空白处
//        WebElement blank = l.getElement(param.get("blank"));
//        blank.click();
//        LogFunction.logInfo("点击：空白处");
////        点击，采集系统选择
//        WebElement acquisitionSystemChoose = l.getElement(param.get("acquisitionSystemChoose"));
//        acquisitionSystemChoose.click();
//        LogFunction.logInfo("点击：采集系统选择");
////        选择,采集系统选择:IBM ITCAM
//        WebElement chooseAcquisitionSystemChoose = l.getElement(param.get("chooseAcquisitionSystemChoose"));
//        String text9 = chooseAcquisitionSystemChoose.getText();
//        chooseAcquisitionSystemChoose.click();
//        LogFunction.logInfo("选择采集系统选择：" + text9);
//        AssertFunction.verifyEquals(driver, text9, "IBM ITCAM", "----验证选择的采集系统选择是否为；IBM ITCAM----");
////        点击，空白处
//        WebElement blank1 = l.getElement(param.get("blank"));
//        blank1.click();
//        LogFunction.logInfo("点击：空白处");
//        录入，内容关键字
        WebElement contentKeyword = l.getElement(param.get("contentKeyword"));
        contentKeyword.sendKeys(param.get("contentKeywordValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("contentKeywordValue"));
//       点击，规则条件高级设置，下一步
        WebElement rulesConditionAdvancedConfigNextStep = l.getElement(param.get("rulesConditionAdvancedConfigNextStep"));
        String text71 = rulesConditionAdvancedConfigNextStep.getText();
        rulesConditionAdvancedConfigNextStep.click();
        LogFunction.logInfo("点击：" + text71);
        AssertFunction.verifyEquals(driver, text71, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警分类策略，第三步，规则条件高级设置录入完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类策略-新建第4部分，告警分类设置
    @Test(dataProvider = "xmldata")
    public void alarmClassifyRulesCreateAlarmClassifySetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        点击，新的警告类型
//        WebElement newAlarmType = l.getElement(param.get("newAlarmType"));
//        newAlarmType.click();
//        LogFunction.logInfo("点击：新的警告类型");
////        选择,新的警告类型:Oracle_PGA_Alert
//        WebElement chooseNewAlarmType = l.getElement(param.get("chooseNewAlarmType"));
//        String text91 = chooseNewAlarmType.getText();
//        chooseNewAlarmType.click();
//        LogFunction.logInfo("选择新的警告类型：" + text91);
//        AssertFunction.verifyEquals(driver, text91, "Oracle_PGA_Alert", "----验证选择的新的警告类型是否为；Oracle_PGA_Alert----");
////        点击，新的告警级别
//        WebElement newAlarmRank = l.getElement(param.get("newAlarmRank"));
//        newAlarmRank.click();
//        LogFunction.logInfo("点击：新的告警级别");
////        点击,新的警告类型，Deselect All
//        WebElement deselectAll = l.getElement(param.get("deselectAll"));
//        String text11 = deselectAll.getText();
//        deselectAll.click();
//        LogFunction.logInfo("点击：" + text11);
//        AssertFunction.verifyEquals(driver, text11, "Deselect All", "----验证点击的是否为；取消全选----");
////        选择,新的警告类型:CRITICAL
//        WebElement chooseNewAlarmRank = l.getElement(param.get("chooseNewAlarmRank"));
//        String text12 = chooseNewAlarmRank.getText();
//        chooseNewAlarmRank.click();
//        LogFunction.logInfo("选择新的告警级别：" + text12);
//        AssertFunction.verifyEquals(driver, text12, "CRITICAL", "----验证选择的新的告警级别是否为；CRITICAL----");
////        点击，空白处
//        WebElement blank2 = l.getElement(param.get("blank"));
//        blank2.click();
//        LogFunction.logInfo("点击：空白处");
//          点击，保存
        WebElement alarmClassifyConfigSave = l.getElement(param.get("alarmClassifyConfigSave"));
        String text10 = alarmClassifyConfigSave.getText();
        alarmClassifyConfigSave.click();
        LogFunction.logInfo("点击：" + text10);
        LogFunction.logInfo("-----------------告警分类策略，最后一步，告警分类设置录入完成且告警分类策略创建成功---------------------");
    }

//    //    集中告警-告警配置-降噪策略-告警分类策略-筛选及校验
//    @Test(dataProvider = "xmldata")
//    public void denoiseStrategyAlarmClassifyRulesSelectVerify(Map<String, String> param) {
//        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
////        点击，筛选
//        WebElement commonSelect = l.getElement(param.get("commonSelect"));
//        String text = commonSelect.getText();
//        if (commonSelect.isDisplayed()==false) {
//            commonSelect.click();
//            LogFunction.logInfo("点击：" + text);
//            AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
//        }
////        录入，规则名称
//        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
//        selectRulesName.sendKeys(param.get("alarmClassifyRulesNameValue"));
//        LogFunction.logInfo("录入规则名称:" + param.get("alarmClassifyRulesNameValue"));
////        点击，筛选，确定
//        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
//        String text2 = selectAffirm.getText();
//        selectAffirm.click();
//        LogFunction.logInfo("点击：" + text2);
//        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
////        勾选规则
//        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
//        selectChooseRules.click();
//        LogFunction.logInfo("勾选筛选结果规则");
////        校验,规则名称
//        WebElement SelectRulesName = l.getElement(param.get("commonDenoiseStrategySelectRulesNameValue"));
//        String text1 = SelectRulesName.getText();
//        LogFunction.logInfo("规则名称为：" + text1);
//        AssertFunction.verifyEquals(driver, text1, param.get("alarmClassifyRulesNameValue"));
////        校验,规则类型
//        WebElement selectRulesType = l.getElement(param.get("commonDenoiseStrategySelectRulesTypeValue"));
//        String text3 = selectRulesType.getText();
//        LogFunction.logInfo("规则类型为：" + text3);
//        AssertFunction.verifyEquals(driver, text3, "分类");
////        校验,节点类型
//        WebElement selectNodeType = l.getElement(param.get("commonDenoiseStrategySelectNodeTypeValue"));
//        String text4 = selectNodeType.getText();
//        LogFunction.logInfo("节点类型为：" + text4);
//        AssertFunction.verifyEquals(driver, text4, Oracal);
////        校验,域
//        WebElement selectDomain = l.getElement(param.get("commonDenoiseStrategySelectDomainValue"));
//        String text5 = selectDomain.getText();
//        LogFunction.logInfo("域为：" + text5);
//        AssertFunction.verifyEquals(driver, text5, Domain);
////        校验,优先级
//        WebElement selectPriority = l.getElement(param.get("commonDenoiseStrategySelectPriorityValue"));
//        String text6 = selectPriority.getText();
//        LogFunction.logInfo("优先级为：" + text6);
//        AssertFunction.verifyEquals(driver, text6, Priority);
////        校验,优先级
//        WebElement selectStatus = l.getElement(param.get("commonDenoiseStrategySelectStatusValue"));
//        String title = selectStatus.getAttribute("title");
//        LogFunction.logInfo("状态为：" + title);
//        AssertFunction.verifyEquals(driver, title, "已启用");
//
//
//        LogFunction.logInfo("-----------------降噪策略告警分类策略筛选查询完成---------------------");
//    }

    @Test(dataProvider = "xmldata")
    public void alarmDisplay(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        co.openLeftMenuButton(param);
//        点击，告警展示
        co.modelClickButton(param, "alarmDisplay");
        LogFunction.logInfo("-----------------进入，告警展示页面---------------------");
    }

    //    集中告警-告警展示-待处理告警
    @Test(dataProvider = "xmldata")
    public void alarmDisplayPendingAlarm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        点击，待处理告警
        WebElement alarmDisplay = l.getElement(param.get("alarmDisplayPendingAlarm"));
        String text = alarmDisplay.getText();
        alarmDisplay.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text.substring(0, 5), "待处理告警");
//        获取，待处理告警数
//        WebElement Number = l.getElement(param.get("alarmDisplayPendingAlarmNumber"));
//        String numberText = Number.getText();
//        LogFunction.logInfo("待处理告警数量：" + numberText);
////        获取，列表待处理告警数量
//        List<WebElement> ListNumber = l.getElements(param.get("alarmDisplayListNumber"));
//        int size = ListNumber.size();
//        LogFunction.logInfo("列表待处理告警数量：" + String.valueOf(size));
//        AssertFunction.verifyEquals(driver, numberText, String.valueOf(size));

        LogFunction.logInfo("-----------------进入，待处理告警页面---------------------");
    }

    //    集中告警-告警展示-已确认告警
    @Test(dataProvider = "xmldata")
    public void alarmDisplayConfirmedAlarm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，已确认告警
        WebElement alarmDisplay = l.getElement(param.get("alarmDisplayConfirmedAlarm"));
        String text = alarmDisplay.getText();
        alarmDisplay.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text.substring(0, 5), "已确认告警");
//        获取，已确认告警数量
//        WebElement Number = l.getElement(param.get("alarmDisplayConfirmedAlarmNumber"));
//        String numberText = Number.getText();
//        LogFunction.logInfo("待处理告警数量：" + numberText);
////        获取，列表已确认告警数量
//        List<WebElement> ListNumber = l.getElements(param.get("alarmDisplayListNumber"));
//        int size = ListNumber.size();
//        LogFunction.logInfo("列表已确认告警数量：" + String.valueOf(size));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        勾选，信息
        WebElement chooseAlarm = l.getElement(param.get("chooseAlarm"));
        chooseAlarm.click();
        LogFunction.logInfo("成功勾选：警示信息");
//        AssertFunction.verifyEquals(driver, numberText, String.valueOf(size));

        LogFunction.logInfo("-----------------进入，已确认告警页面---------------------");
    }


    //    集中告警-告警展示-已前转告警
    @Test(dataProvider = "xmldata")
    public void alarmDisplayForwardShiftingAlarm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，已前转告警
        WebElement alarmDisplay = l.getElement(param.get("alarmDisplayForwardShiftingAlarm"));
        String text = alarmDisplay.getText();
        alarmDisplay.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text.substring(0, 5), "已前转告警");

//        获取，已前转告警数量
//        WebElement Number = l.getElement(param.get("alarmDisplayForwardShiftingAlarmNumber"));
//        String numberText = Number.getText();
//        LogFunction.logInfo("已前转告警数量：" + numberText);
////        获取，列表已前转告警数量
//        List<WebElement> ListNumber = l.getElements(param.get("alarmDisplayListNumber"));
//        int size = ListNumber.size();
//        LogFunction.logInfo("列表已前转告警数量：" + String.valueOf(size));
//        //        点击，筛选，清空
//        WebElement Clear = l.getElement(param.get("Clear"));
//        String text4 = Clear.getText();
//        Clear.click();
//        LogFunction.logInfo("点击：" + text4);
//        AssertFunction.verifyEquals(driver, text4, "清空");
        co.sleep(1000);
        //        勾选，信息
        WebElement chooseAlarm = l.getElement(param.get("chooseAlarm"));
        chooseAlarm.click();
        LogFunction.logInfo("成功勾选：警示信息");
//        AssertFunction.verifyEquals(driver, numberText, String.valueOf(size));
        LogFunction.logInfo("-----------------进入，已前转告警页面---------------------");
    }

    //    集中告警-告警展示-已解决告警
    @Test(dataProvider = "xmldata")
    public void alarmDisplayResolvedAlarm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，已解决告警
        WebElement alarmDisplay = l.getElement(param.get("alarmDisplayResolvedAlarm"));
        String text = alarmDisplay.getText();
        alarmDisplay.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text.substring(0, 5), "已解决告警");
//        获取，已解决告警数量
//        WebElement Number = l.getElement(param.get("alarmDisplayResolvedAlarmNumber"));
//        String numberText = Number.getText();
//        LogFunction.logInfo("已解决告警数量：" + numberText);
////        获取，列表已解决告警数量
//        List<WebElement> ListNumber = l.getElements(param.get("alarmDisplayListNumber"));
//        int size = ListNumber.size();
//        LogFunction.logInfo("列表已解决告警数量：" + String.valueOf(size));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //        勾选，信息
        WebElement chooseAlarm = l.getElement(param.get("chooseAlarm"));
        chooseAlarm.click();
        LogFunction.logInfo("成功勾选：警示信息");
//        AssertFunction.verifyEquals(driver, numberText, String.valueOf(size));
        LogFunction.logInfo("-----------------进入，已解决告警页面---------------------");
    }

    //    集中告警-告警展示-已忽略告警
    @Test(dataProvider = "xmldata")
    public void alarmDisplayIgnoreAlarm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，已忽略告警
        WebElement alarmDisplay = l.getElement(param.get("alarmDisplayIgnoreAlarm"));
        String text = alarmDisplay.getText();
        alarmDisplay.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text.substring(0, 5), "已忽略告警");
//        获取，已忽略告警数量
//        WebElement Number = l.getElement(param.get("alarmDisplayIgnoreAlarmNumber"));
//        String numberText = Number.getText();
//        LogFunction.logInfo("已忽略告警数量：" + numberText);
////        获取，列表已忽略告警数量
//        List<WebElement> ListNumber = l.getElements(param.get("alarmDisplayListNumber"));
//        int size = ListNumber.size();
//        LogFunction.logInfo("列表已忽略告警数量：" + String.valueOf(size));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //        勾选，信息
        WebElement chooseAlarm = l.getElement(param.get("chooseAlarm"));
        chooseAlarm.click();
        LogFunction.logInfo("成功勾选：警示信息");
//        AssertFunction.verifyEquals(driver, numberText, String.valueOf(size));
        LogFunction.logInfo("-----------------进入，已忽略告警页面---------------------");
    }

    //    集中告警-告警展示-勾选信息功能
    @Test(dataProvider = "xmldata")
    public void alarmDisplayChooseTwoFunction(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        勾选，警示信息
        WebElement chooseAlarm = l.getElement(param.get("chooseAlarm"));
        chooseAlarm.click();
        LogFunction.logInfo("成功勾选/取消：第一条警示信息");

        WebElement chooseAlarm2 = l.getElement(param.get("chooseAlarm2"));
        chooseAlarm2.click();
        LogFunction.logInfo("成功勾选/取消：第二条警示信息");


        LogFunction.logInfo("-----------------成功勾选/取消预警信息---------------------");


    }


    //    集中告警-告警展示-勾选信息功能
    @Test(dataProvider = "xmldata")
    public void alarmDisplayChooseFunction1(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        勾选，警示信息
        WebElement chooseAlarm = l.getElement(param.get("chooseAlarm"));
        chooseAlarm.click();
        LogFunction.logInfo("成功勾选：警示信息");
        LogFunction.logInfo("-----------------成功勾选预警信息---------------------");
    }

    //    集中告警-告警展示-勾选信息功能
    @Test(dataProvider = "xmldata")
    public void alarmDisplayChooseFunction(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        勾选，警示信息
        WebElement chooseAlarm = l.getElement(param.get("chooseAlarm"));
        chooseAlarm.click();
        LogFunction.logInfo("成功勾选：警示信息");
        LogFunction.logInfo("-----------------成功勾选预警信息---------------------");
    }

    //    集中告警-告警展示-勾选信息功能
    @Test(dataProvider = "xmldata")
    public void alarmDisplayChooseFunction2(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        勾选，警示信息

        WebElement chooseAlarm = l.getElement(param.get("chooseAlarm"));
        chooseAlarm.click();
        LogFunction.logInfo("成功勾选：警示信息");
        LogFunction.logInfo("-----------------成功勾选预警信息---------------------");
    }

    //    集中告警-告警展示-勾选信息功能
    @Test(dataProvider = "xmldata")
    public void alarmDisplayChooseFunction3(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        勾选，警示信息
        WebElement chooseAlarm = l.getElement(param.get("chooseAlarm"));
        chooseAlarm.click();
        LogFunction.logInfo("成功勾选：警示信息");
        LogFunction.logInfo("-----------------成功勾选预警信息---------------------");
    }

    //    集中告警-告警展示-勾选信息功能
    @Test(dataProvider = "xmldata")
    public void alarmDisplayChooseFunction4(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        勾选，警示信息
        WebElement chooseAlarm = l.getElement(param.get("chooseAlarm"));
        chooseAlarm.click();
        LogFunction.logInfo("成功勾选：警示信息");
        LogFunction.logInfo("-----------------成功勾选预警信息---------------------");
    }

    //    集中告警-告警展示-筛选
    @Test(dataProvider = "xmldata")
    public void alarmDisplaySelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，筛选
        co.selectO(param);
//        点击，CI域选择框
        WebElement CIDomain = l.getElement(param.get("CentralizedAlarmSelectCIDomain"));
        CIDomain.click();
        LogFunction.logInfo("点击： CI域选择框");
//        选择,rootDomain
        WebElement CIDomainValue = l.getElement(param.get("CentralizedAlarmSelectCIDomainValue"));
        String text1 = CIDomainValue.getText();
        CIDomainValue.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "rootDomain");
//        点击，CI类型
//        WebElement CIType = l.getElement(param.get("CentralizedAlarmSelectCIType"));
//        CIType.click();
//        LogFunction.logInfo("点击： CI类型选择框");
////        选择,rootDomain
//        WebElement CITypeValue = l.getElement(param.get("CentralizedAlarmSelectCITypeValue"));
//        String text2 = CITypeValue.getText();
//        CITypeValue.click();
//        LogFunction.logInfo("点击：" + text2);
//        AssertFunction.verifyEquals(driver, text2, "操作系统");
////        点击，CI实例
//        WebElement CICase = l.getElement(param.get("CentralizedAlarmSelectCICase"));
//        CICase.click();
//        LogFunction.logInfo("点击： CI实例选择框");
//        选择,StandardLinux_Test1
//        WebElement CICaseValue = l.getElement(param.get("CentralizedAlarmSelectCICaseValue"));
//        String text3 = CICaseValue.getText();
//        CICaseValue.click();
//        LogFunction.logInfo("点击：" + text3);
//        AssertFunction.verifyEquals(driver, text3, "StandardLinux_Test1");
//        点击，采集系统选择
//        WebElement CollectSystem = l.getElement(param.get("CentralizedAlarmSelectCollectSystem"));
//        CollectSystem.click();
//        LogFunction.logInfo("点击： 采集系统选择");
////        选择,Zabbix
//        WebElement CollectSystemValue = l.getElement(param.get("CentralizedAlarmSelectCollectSystemValue"));
//        String text4 = CollectSystemValue.getText();
//        CollectSystemValue.click();
//        LogFunction.logInfo("点击：" + text4);
//        AssertFunction.verifyEquals(driver, text4, "Zabbix");

//        点击，空白
//        WebElement systemTitleblank = l.getElement(param.get("systemTitleblank"));
//        systemTitleblank.click();
//        LogFunction.logInfo("点击： 空白");
//        co.sleep(1000);
//        点击，筛选，确定
        WebElement affirm = l.getElement(param.get("affirm"));
        String text21 = affirm.getText();
        affirm.click();
        LogFunction.logInfo("点击：" + text21);
        AssertFunction.verifyEquals(driver, text21, "确定");
//        点击，收起
//        WebElement putAway = l.getElement(param.get("putAway"));
//        String text33 = putAway.getText();
//        putAway.click();
//        LogFunction.logInfo("点击：" + text33);
//        AssertFunction.verifyEquals(driver, text33, "收起");
        co.sleep(5000);
//        校验，查询结果之消息内容
        WebElement MessageContent = l.getElement(param.get("alarmDisplayColumnDisplayMessageContent"));
        String text5 = MessageContent.getText();
        LogFunction.logInfo("警示信息：" + text5);
        AssertFunction.verifyEquals(driver, text5, param.get("alarmDisplaySearchBoxValue"));
//        勾选，警示信息
        WebElement chooseAlarm = l.getElement(param.get("chooseAlarm"));
        chooseAlarm.click();
        LogFunction.logInfo("成功勾选：警示信息");
//        点击，筛选，清空
//        WebElement Clear = l.getElement(param.get("Clear"));
//        String text4 = Clear.getText();
//        Clear.click();
//        LogFunction.logInfo("点击：" + text4);
//        AssertFunction.verifyEquals(driver, text4, "清空");
        LogFunction.logInfo("-----------------告警展示,筛选成功---------------------");
    }

    //    集中告警-告警展示-筛选
    @Test(dataProvider = "xmldata", enabled = false)
    public void alarmDisplaySelectClear(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        co.sleep(1000);
        //        点击，筛选，清空
        WebElement Clear = l.getElement(param.get("Clear"));
        String text4 = Clear.getText();
        Clear.click();
        LogFunction.logInfo("点击：" + text4);
        AssertFunction.verifyEquals(driver, text4, "清空");
    }


    //    集中告警-告警展示-点击确认Button
    @Test(dataProvider = "xmldata")
    public void alarmDisplayConfirmationClick(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，确认
        WebElement Confirmation = l.getElement(param.get("alarmDisplayConfirmation"));
        String text = Confirmation.getText();
        Confirmation.click();
        LogFunction.logInfo("点击：" + text);
        LogFunction.logInfo("-----------------告警展示,成功点击确认按钮---------------------");

    }

    //    集中告警-告警展示-确认
    @Test(dataProvider = "xmldata")
    public void alarmDisplayConfirmation(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，确认
//        WebElement Confirmation = l.getElement(param.get("alarmDisplayConfirmation"));
//        String text = Confirmation.getText();
//        Confirmation.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "确认");
//        写入，备注内容
        WebElement Remark = l.getElement(param.get("alarmDisplayConfirmationRemark"));
        Remark.sendKeys(param.get("alarmDisplayConfirmationRemarkValue"));
        LogFunction.logInfo("写入告警展示-确认备注： " + param.get("alarmDisplayConfirmationRemarkValue"));
//        点击，备注，确定
        WebElement affirm = l.getElement(param.get("affirm"));
        String text2 = affirm.getText();
        affirm.click();
        LogFunction.logInfo("点击：" + text2);
//        弹出，提示信息
        WebElement hintMessage = l.getElement(param.get("hintMessage"));
        String text3 = hintMessage.getText();
        hintMessage.click();
        LogFunction.logInfo("提示信息为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "已确认1个告警");
//        点击，备注-提示信息，确认
        WebElement affirm1 = l.getElement(param.get("hintMessageConfirm"));
        String text44 = affirm1.getText();
        affirm1.click();
        LogFunction.logInfo("点击：" + text44);
        LogFunction.logInfo("-----------------告警展示,确认，完成---------------------");
    }

    //    集中告警-告警展示-点击前转 Button
    @Test(dataProvider = "xmldata")
    public void alarmDisplayForwardShiftingClick(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，前转
        WebElement Confirmation = l.getElement(param.get("alarmDisplayForwardShifting"));
        String text = Confirmation.getText();
        Confirmation.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "通知");

    }

    //    集中告警-告警展示-点击前转 Button
    @Test(dataProvider = "xmldata")
    public void alarmDisplayForwardShiftingClick1(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，通知
        WebElement Confirmation = l.getElement(param.get("alarmDisplayForwardShifting"));
        String text = Confirmation.getText();
        Confirmation.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "通知");

    }

    //    集中告警-告警展示-点击通知 Button
    @Test(dataProvider = "xmldata")
    public void alarmDisplayInformClick(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，通知
        WebElement Confirmation = l.getElement(param.get("alarmDisplayForwardShifting"));
        String text = Confirmation.getText();
        Confirmation.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "通知");

    }

    //    集中告警-告警展示-点击通知 Button
    @Test(dataProvider = "xmldata")
    public void alarmDisplayInformClick1(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，通知
        WebElement Confirmation = l.getElement(param.get("alarmDisplayForwardShifting"));
        String text = Confirmation.getText();
        Confirmation.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "通知");

    }


    //    集中告警-告警展示-前转
    @Test(dataProvider = "xmldata")
    public void alarmDisplayForwardShifting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
////        点击，前转
//        WebElement Confirmation = l.getElement(param.get("alarmDisplayForwardShifting"));
//        String text = Confirmation.getText();
//        Confirmation.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "前转");

//        录入，标题
        WebElement forwardShiftingTitle = l.getElement(param.get("forwardShiftingTitle"));
        forwardShiftingTitle.clear();
        forwardShiftingTitle.sendKeys(param.get("commonForwardShiftingTitleValue"));
        LogFunction.logInfo("前转标题为：" + param.get("commonForwardShiftingTitleValue"));
//        点击，级别选择框
        WebElement LevelChoose = l.getElement(param.get("LevelChoose"));
        LevelChoose.click();
        LogFunction.logInfo("点击，级别选择框");
//        选择，级别选择
        WebElement LevelChooseValue = l.getElement(param.get("LevelChooseValue"));
        String text1 = LevelChooseValue.getText();
        LevelChooseValue.click();
        LogFunction.logInfo("选择级别选择：" + text1);
//        点击，故障分类(接收分组)
//        co.modelClickButton(param,"FaultClassify");
        WebElement FaultClassify = l.getElement(param.get("FaultClassify"));
        FaultClassify.click();
        LogFunction.logInfo("点击，接收分组框");
        co.sleep(1000);
//        选择，故障分类-值(接收分组)
        WebElement FaultClassifyValue = l.getElement(param.get("FaultClassifyValue"));
        String text2 = FaultClassifyValue.getText();
        FaultClassifyValue.click();
        LogFunction.logInfo("选择级别选择：" + text2);
//        点击，备注，确定
        WebElement affirm = l.getElement(param.get("affirm"));
        String text21 = affirm.getAttribute("value");
        affirm.click();
        LogFunction.logInfo("点击：" + text21);
//        弹出，提示信息
        WebElement hintMessage = l.getElement(param.get("hintMessage"));
        String text3 = hintMessage.getText();
        hintMessage.click();
        LogFunction.logInfo("提示信息为：" + text3);
//        点击，备注-提示信息，确认
//        WebElement affirm1 = l.getElement(param.get("hintMessageConfirm"));
//        String text4 = affirm1.getText();
//        affirm1.click();
//        LogFunction.logInfo("点击：" + text4);
//        AssertFunction.verifyEquals(driver, text4, "确认");
        LogFunction.logInfo("-----------------告警展示,前转，完成---------------------");
    }

    //    集中告警-告警展示-前转
    @Test(dataProvider = "xmldata")
    public void alarmDisplayForwardShiftingMode2(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
////        点击，前转
//        WebElement Confirmation = l.getElement(param.get("alarmDisplayForwardShifting"));
//        String text = Confirmation.getText();
//        Confirmation.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "前转");

//        录入，标题
        WebElement forwardShiftingTitle = l.getElement(param.get("forwardShiftingTitle"));
        forwardShiftingTitle.click();
        forwardShiftingTitle.clear();
        forwardShiftingTitle.sendKeys(param.get("commonForwardShiftingTitleValue"));
        LogFunction.logInfo("前转标题为：" + param.get("commonForwardShiftingTitleValue"));
//        点击，直接转给用户
        WebElement LevelChoose = l.getElement(param.get("DirectToUser"));
        LevelChoose.click();
        LogFunction.logInfo("点击，直接转给用户");

//        点击，前转类型选择框
        WebElement ForwardShiftingClassify = l.getElement(param.get("ForwardShiftingClassify"));
        ForwardShiftingClassify.click();
        LogFunction.logInfo("点击，前转类型选择框");
//        选择，前转类型-值
        WebElement ForwardShiftingClassifyValue = l.getElement(param.get("ForwardShiftingClassifyValue"));
        String text1 = ForwardShiftingClassifyValue.getText();
        ForwardShiftingClassifyValue.click();
        LogFunction.logInfo("选择前转类型：" + text1);

//        点击，前转用户
        WebElement ForwardShiftingUser = l.getElement(param.get("ForwardShiftingUser"));
        ForwardShiftingUser.click();
        LogFunction.logInfo("点击，前转用户选择框");
//        选择，前转用户-值
        WebElement ForwardShiftingUserValue = l.getElement(param.get("ForwardShiftingUserValue"));
        String text2 = ForwardShiftingUserValue.getText();
        ForwardShiftingUserValue.click();
        LogFunction.logInfo("选择前转用户：" + text2);
//        点击，备注，确定
        WebElement affirm = l.getElement(param.get("affirm"));
        String text21 = affirm.getAttribute("value");
        affirm.click();
        LogFunction.logInfo("点击：" + text21);
//        弹出，提示信息
        WebElement hintMessage = l.getElement(param.get("hintMessage"));
        String text3 = hintMessage.getText();
        hintMessage.click();
        LogFunction.logInfo("提示信息为：" + text3);
//        点击，备注-提示信息，确认
//        WebElement affirm1 = l.getElement(param.get("hintMessageConfirm"));
//        String text4 = affirm1.getText();
//        affirm1.click();
//        LogFunction.logInfo("点击：" + text4);
//        AssertFunction.verifyEquals(driver, text4, "确认");
        LogFunction.logInfo("-----------------告警展示,前转，完成---------------------");
    }

    //    集中告警-告警展示-前转-提示信息确认
    @Test(dataProvider = "xmldata")
    public void alarmDisplayForwardShiftingHintConfirm1(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，备注-提示信息，确认
        WebElement affirm1 = l.getElement(param.get("hintMessageConfirm"));
        String text4 = affirm1.getText();
        affirm1.click();
        LogFunction.logInfo("点击：" + text4);
        AssertFunction.verifyEquals(driver, text4, "确认");
        LogFunction.logInfo("-----------------告警展示,前转，提示信息点击确认，完成---------------------");
    }

    //    集中告警-告警展示-前转-提示信息确认
    @Test(dataProvider = "xmldata")
    public void alarmDisplayForwardShiftingHintConfirm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，备注-提示信息，确认
        WebElement affirm1 = l.getElement(param.get("hintMessageConfirm"));
        String text4 = affirm1.getText();
        affirm1.click();
        LogFunction.logInfo("点击：" + text4);
        AssertFunction.verifyEquals(driver, text4, "确认");
        LogFunction.logInfo("-----------------告警展示,前转，提示信息点击确认，完成---------------------");
    }

    //    集中告警-告警展示-解决Button
    @Test(dataProvider = "xmldata")
    public void alarmDisplayResolvedButton(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，解决
        WebElement Confirmation = l.getElement(param.get("alarmDisplayResolved"));
        String text = Confirmation.getText();
        Confirmation.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "解决");
        LogFunction.logInfo("-----------------告警展示,点击解决按钮---------------------");

    }

    //    集中告警-告警展示-解决Button2
    @Test(dataProvider = "xmldata")
    public void alarmDisplayResolvedButton2(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，解决
        WebElement Confirmation = l.getElement(param.get("alarmDisplayResolved"));
        String text = Confirmation.getText();
        Confirmation.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "解决");
        LogFunction.logInfo("-----------------告警展示,点击解决按钮---------------------");

    }

    //    集中告警-告警展示-解决Button3
    @Test(dataProvider = "xmldata")
    public void alarmDisplayResolvedButton3(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，解决
        WebElement Confirmation = l.getElement(param.get("alarmDisplayResolved"));
        String text = Confirmation.getText();
        Confirmation.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "解决");
        LogFunction.logInfo("-----------------告警展示,点击解决按钮---------------------");

    }

    //    集中告警-告警查询-历史告警-导出Button及功能验证
    @Test(dataProvider = "xmldata")
    public void alarmDisplayExportButton(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，导出
        WebElement Export = l.getElement(param.get("alarmDisplayExport"));
        String text = Export.getText();
        Export.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "导出");
        co.sleep(50000);
//        判断文件是否，下载
        File file = null;
        if ((SeleniumDriver.value).equals("w")) {
            file = new File(BaseConfig.chromeDownloadPathOfWindows + "/下载.xls");
        } else if (((SeleniumDriver.value).equals("l"))) {
//             file = new File(BaseConfig.chromeDownloadPathOfLinux+"/下载.xls");
            file = new File(BaseConfig.chromeDownloadPathOfWindows + "/下载.xls");
        } else {
            if ((SeleniumDriver.value).equals("w")) {
                LogFunction.logInfo("文件初始化错误");
                throw new RuntimeException();
            }
        }
        if (file != null) {
            String name = file.getName();
            LogFunction.logInfo("文件名称：" + name);
            boolean exists = file.exists();
            LogFunction.logInfo("文件是否存在：" + exists);
            if (exists == true) {
                LogFunction.logInfo("文件已导出");
                boolean delete = file.delete();
                if (delete) {
                    LogFunction.logInfo("导出文件已删除");
                }
            }
        } else {
            LogFunction.logInfo("文件为：null");
            throw new RuntimeException();
        }

        LogFunction.logInfo("-----------------告警查询-历史告警,导出功能验证通过---------------------");

    }


    //    集中告警-告警展示-解决-解决
    @Test(dataProvider = "xmldata")
    public void alarmDisplayResolvedOfResolved(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，解决
//        WebElement Confirmation = l.getElement(param.get("alarmDisplayResolved"));
//        String text = Confirmation.getText();
//        Confirmation.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "解决");
//        写入，处理意见
        WebElement Remark = l.getElement(param.get("alarmDisplayResovledOpinion"));
        Remark.sendKeys(param.get("alarmDisplayResovledOpinionValue"));
        LogFunction.logInfo("写入告警展示-解决-处理意见： " + param.get("alarmDisplayResovledOpinionValue"));
//        点击，处理意见，确定
        WebElement affirm = l.getElement(param.get("affirm"));
        String text2 = affirm.getText();
        affirm.click();
        LogFunction.logInfo("点击：" + text2);
//        弹出，提示信息
        WebElement hintMessage = l.getElement(param.get("hintMessage"));
        String text3 = hintMessage.getText();
        hintMessage.click();
        LogFunction.logInfo("提示信息为：" + text3);

//        点击，备注-提示信息，确认
//        WebElement affirm1 = l.getElement(param.get("hintMessageAffirm"));
//        String text4 = affirm1.getText();
//        affirm1.click();
//        LogFunction.logInfo("点击：" + text4);
//        AssertFunction.verifyEquals(driver, text4, "确认");

        LogFunction.logInfo("-----------------告警展示,解决-解决子告警，完成---------------------");
    }

    //    集中告警-告警展示-解决-解决-提示信息按钮
    @Test(dataProvider = "xmldata")
    public void alarmDisplayResolvedOfResolvedHintConfirm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，备注-提示信息，确认
        WebElement affirm1 = l.getElement(param.get("hintMessageAffirm"));
        String text4 = affirm1.getText();
        affirm1.click();
        LogFunction.logInfo("点击：" + text4);
        AssertFunction.verifyEquals(driver, text4, "确认");
        LogFunction.logInfo("-----------------告警展示,解决-解决子告警，点击提示信息确认，完成---------------------");

    }


    //    集中告警-告警展示-解决-忽略
    @Test(dataProvider = "xmldata")
    public void alarmDisplayResolvedOfIgnore(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        点击，解决
//        WebElement Confirmation = l.getElement(param.get("alarmDisplayResolved"));
//        String text = Confirmation.getText();
//        Confirmation.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "解决");
//        写入，处理意见
        WebElement Remark = l.getElement(param.get("alarmDisplayResovledOpinion"));
        Remark.sendKeys(param.get("alarmDisplayResovledOpinionValue"));
        LogFunction.logInfo("写入告警展示-确认备注： " + param.get("alarmDisplayResovledOpinionValue"));
//        处理意见，选择-设置为忽略
        WebElement IgnoreButton = l.getElement(param.get("IgnoreButton"));
        IgnoreButton.click();
        LogFunction.logInfo("成功选择：设置为忽略");
//        点击，备注，确定
        WebElement affirm = l.getElement(param.get("affirm"));
        String text2 = affirm.getText();
        affirm.click();
        LogFunction.logInfo("点击：" + text2);
//        弹出，提示信息
        WebElement hintMessage = l.getElement(param.get("hintMessage"));
        String text3 = hintMessage.getText();
        hintMessage.click();
        LogFunction.logInfo("提示信息为：" + text3);
//        点击，备注-提示信息，确认
//        WebElement affirm1 = l.getElement(param.get("hintMessageAffirm"));
//        String text4 = affirm1.getText();
//        affirm1.click();
//        LogFunction.logInfo("点击：" + text4);
//        AssertFunction.verifyEquals(driver, text4, "确认");

        LogFunction.logInfo("-----------------告警展示,解决-设置为忽略，完成---------------------");
    }

    //    集中告警-告警展示-解决-忽略-提示信息按钮
    @Test(dataProvider = "xmldata")
    public void alarmDisplayResolvedOfIgnoreHintConfirm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement affirm1 = l.getElement(param.get("hintMessageAffirm"));
        String text4 = affirm1.getText();
        affirm1.click();
        LogFunction.logInfo("点击：" + text4);
        AssertFunction.verifyEquals(driver, text4, "确认");
        LogFunction.logInfo("-----------------告警展示,解决-设置为忽略，点击提示信息确认，完成---------------------");

    }

    //    集中告警-告警展示-解决-清出内存
    @Test(dataProvider = "xmldata")
    public void alarmDisplayResolvedOfClearMemory(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，解决
//        WebElement Confirmation = l.getElement(param.get("alarmDisplayResolved"));
//        String text = Confirmation.getText();
//        Confirmation.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "解决");
//        写入，处理意见
        WebElement Remark = l.getElement(param.get("alarmDisplayResovledOpinion"));
        Remark.sendKeys(param.get("alarmDisplayResovledOpinionValue"));
        LogFunction.logInfo("写入告警展示-确认备注： " + param.get("alarmDisplayResovledOpinionValue"));
//        处理意见，选择-清出内存
        WebElement IgnoreButton = l.getElement(param.get("ClearMemoryButton"));
        IgnoreButton.click();
        LogFunction.logInfo("成功选择：清出内存");
//        点击，备注，确定
        WebElement affirm = l.getElement(param.get("affirm"));
        String text2 = affirm.getText();
        affirm.click();
        LogFunction.logInfo("点击：" + text2);
//        弹出，提示信息
        WebElement hintMessage = l.getElement(param.get("hintMessage"));
        String text3 = hintMessage.getText();
        hintMessage.click();
        LogFunction.logInfo("提示信息为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "已关闭1个告警");
//        点击，备注-提示信息，确认
//        WebElement affirm1 = l.getElement(param.get("hintMessageAffirm"));
//        String text4 = affirm1.getText();
//        affirm1.click();
//        LogFunction.logInfo("点击：" + text4);
//        AssertFunction.verifyEquals(driver, text4, "确认");

        LogFunction.logInfo("-----------------告警展示,解决-清出内存，完成---------------------");
    }

    //    集中告警-告警展示-解决-忽略-提示信息按钮
    @Test(dataProvider = "xmldata")
    public void alarmDisplayResolvedOfClearMemoryHintConfirm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement affirm1 = l.getElement(param.get("hintMessageAffirm"));
        String text4 = affirm1.getText();
        affirm1.click();
        LogFunction.logInfo("点击：" + text4);
        AssertFunction.verifyEquals(driver, text4, "确认");
        LogFunction.logInfo("-----------------告警展示,解决-清出内存，点击提示信息确认，完成---------------------");

    }


    //    集中告警-告警展示-查看
    @Test(dataProvider = "xmldata")
    public void alarmDisplayView(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，查看
        WebElement alarmDisplayView = l.getElement(param.get("alarmDisplayView"));
        String text = alarmDisplayView.getText();
        alarmDisplayView.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "查看");
        LogFunction.logInfo("-----------------告警展示,进入查看页面---------------------");
    }

    //    集中告警-告警展示-查看-其他信息
    @Test(dataProvider = "xmldata")
    public void alarmDisplayViewOtherInformation(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，其他信息
        WebElement OtherInformation = l.getElement(param.get("alarmDisplayViewOtherInformation"));
        String text = OtherInformation.getText();
        OtherInformation.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "其他信息");
        LogFunction.logInfo("-----------------告警展示,进入查看-其他信息页面---------------------");
    }


    //    集中告警-告警展示-查看
    @Test(dataProvider = "xmldata")
    public void alarmDisplayView2(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，查看
        WebElement alarmDisplayView = l.getElement(param.get("alarmDisplayView"));
        String text = alarmDisplayView.getText();
        alarmDisplayView.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "查看");
        LogFunction.logInfo("-----------------告警展示,进入查看页面---------------------");
    }

    //    集中告警-告警展示-查看
    @Test(dataProvider = "xmldata")
    public void alarmDisplayView3(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，查看
        WebElement alarmDisplayView = l.getElement(param.get("alarmDisplayView"));
        String text = alarmDisplayView.getText();
        alarmDisplayView.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "查看");
        LogFunction.logInfo("-----------------告警展示,进入查看页面---------------------");
    }

    //    集中告警-告警展示-查看
    @Test(dataProvider = "xmldata")
    public void alarmDisplayView4(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，查看
        WebElement alarmDisplayView = l.getElement(param.get("alarmDisplayView"));
        String text = alarmDisplayView.getText();
        alarmDisplayView.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "查看");
        LogFunction.logInfo("-----------------告警展示,进入查看页面---------------------");
    }

    //    集中告警-告警展示-查看-前转
    @Test(dataProvider = "xmldata")
    public void alarmDisplayViewForwardShifting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，通知
        WebElement alarmDisplayView = l.getElement(param.get("alarmDisplayViewForwardShifting"));
        String text = alarmDisplayView.getText();
        alarmDisplayView.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "通知");
        LogFunction.logInfo("-----------------告警展示,进入查看-通知页面---------------------");
    }

    //    集中告警-告警展示-查看-前转-提示信息确认按钮
    @Test(dataProvider = "xmldata")
    public void alarmDisplayViewForwardShiftingHintConfirm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，备注-提示信息，确认
        WebElement affirm1 = l.getElement(param.get("hintMessageAffirm"));
        String text4 = affirm1.getText();
        affirm1.click();
        LogFunction.logInfo("点击：" + text4);
        AssertFunction.verifyEquals(driver, text4, "确认");
        LogFunction.logInfo("-----------------告警展示,解决-解决子告警，点击提示信息确认，完成---------------------");
//*[@id="ngdialog2"]/div[2]/div/div/div/div[3]/button
    }

    //    集中告警-告警展示-查看-解决-解决-提示信息确认按钮
    @Test(dataProvider = "xmldata")
    public void alarmDisplayViewResolvedOfResolvedHintConfirm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，备注-提示信息，确认
        WebElement affirm1 = l.getElement(param.get("hintMessageAffirm"));
        String text4 = affirm1.getText();
        affirm1.click();
        LogFunction.logInfo("点击：" + text4);
        AssertFunction.verifyEquals(driver, text4, "确认");
        LogFunction.logInfo("-----------------告警展示,解决-解决子告警，点击提示信息确认，完成---------------------");

    }

    //    集中告警-告警展示-查看-解决-忽视-提示信息确认按钮
    @Test(dataProvider = "xmldata")
    public void alarmDisplayViewResolvedOfIgnoreHintConfirm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，备注-提示信息，确认
        WebElement affirm1 = l.getElement(param.get("hintMessageAffirm"));
        String text4 = affirm1.getText();
        affirm1.click();
        LogFunction.logInfo("点击：" + text4);
        AssertFunction.verifyEquals(driver, text4, "确认");
        LogFunction.logInfo("-----------------告警展示,解决-解决子告警，点击提示信息确认，完成---------------------");

    }


    //    集中告警-告警展示-查看-解决
    @Test(dataProvider = "xmldata")
    public void alarmDisplayViewResolved(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，解决
        WebElement alarmDisplayView = l.getElement(param.get("alarmDisplayViewResolved"));
        String text = alarmDisplayView.getText();
        alarmDisplayView.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "解决");
        LogFunction.logInfo("-----------------告警展示,进入查看-解决页面---------------------");
    }

    //    集中告警-告警展示-查看-解决
    @Test(dataProvider = "xmldata")
    public void alarmDisplayViewResolved2(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，解决
        WebElement alarmDisplayView = l.getElement(param.get("alarmDisplayViewResolved"));
        String text = alarmDisplayView.getText();
        alarmDisplayView.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "解决");
        LogFunction.logInfo("-----------------告警展示,进入查看-解决页面---------------------");
    }

    //    集中告警-告警展示-查看-事件查询
    @Test(dataProvider = "xmldata")
    public void alarmDisplayViewEventQuery(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        点击，事件查询
        WebElement alarmDisplayView = l.getElement(param.get("alarmDisplayViewEventQuery"));
        String text = alarmDisplayView.getText();
        alarmDisplayView.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "事件查询");
//        验证，是否进入事件查询页面
        WebElement Title = l.getElement(param.get("alarmDisplayViewEventQueryTitle"));
        String text2 = Title.getText();
        LogFunction.logInfo("标题为：" + text2);
        AssertFunction.verifyEquals(driver, text2, "告警事件查询");
//        获取，事件查询，查询信息
        WebElement EventMessage = l.getElement(param.get("alarmDisplayViewEventQueryEventMessage"));
        String text1 = EventMessage.getText();
        LogFunction.logInfo("查询信息值为：" + text1);
        AssertFunction.verifyEquals(driver, text1, "AutoTest");
//        点击，关闭
        WebElement Close = l.getElement(param.get("alarmDisplayViewEventQueryClose"));
        String text3 = Close.getText();
        Close.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "关闭");
        LogFunction.logInfo("-----------------告警展示,进入查看-事件查询页面,且关闭---------------------");
    }

    //    集中告警-告警展示-查看-操作日志
    @Test(dataProvider = "xmldata")
    public void alarmDisplayViewOperationLogs(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，操作日志
        WebElement alarmDisplayView = l.getElement(param.get("alarmDisplayViewOperationLogs"));
        String text = alarmDisplayView.getText();
        alarmDisplayView.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "操作日志");
//        验证，是否进入操作日志页面
        WebElement Title = l.getElement(param.get("alarmDisplayViewOperationLogsTitle"));
        String text2 = Title.getText();
        LogFunction.logInfo("标题为：" + text2);
        AssertFunction.verifyEquals(driver, text2, "告警操作日志查看");
//        点击，操作日志-关闭
        WebElement Close = l.getElement(param.get("alarmDisplayViewOperationLogsClose"));
        String text1 = Close.getText();
        Close.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "关闭");
        LogFunction.logInfo("-----------------告警展示,进入查看-操作日志页面，且关闭---------------------");
    }

    //    集中告警-告警展示-查看-关联告警
    @Test(dataProvider = "xmldata")
    public void alarmDisplayRelevanceAlarm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，关联告警
        WebElement alarmDisplayView = l.getElement(param.get("alarmDisplayRelevanceAlarm"));
        String text = alarmDisplayView.getText();
        alarmDisplayView.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "关联告警");
//        验证，是否进入关联告警页面
        WebElement Title = l.getElement(param.get("alarmDisplayRelevanceAlarmTitle"));
        String text2 = Title.getText();
        LogFunction.logInfo("标题：" + text2);
        AssertFunction.verifyEquals(driver, text2, "关联告警信息");
//        点击，关联告警-关闭
        WebElement Close = l.getElement(param.get("alarmDisplayRelevanceAlarmClose"));
        String text1 = Close.getText();
        Close.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "关闭");
        LogFunction.logInfo("-----------------告警展示,进入查看-关联告警页面---------------------");


    }

    //    集中告警-告警展示-查看-取消
    @Test(dataProvider = "xmldata")
    public void alarmDisplayViewCancel(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，取消
        WebElement alarmDisplayView = l.getElement(param.get("alarmDisplayViewCancel"));
        String text = alarmDisplayView.getText();
        alarmDisplayView.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "关闭");
        LogFunction.logInfo("-----------------告警展示,进入查看-取消页面---------------------");
    }


    int numberText;
    int numberText1;
    int numberText2;
    int numberText3;
    int numberText4;


    //    集中告警-告警展示-告警数量校验
    @Test(dataProvider = "xmldata")
    public void alarmDisplayAlarmsNumber(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，待处理告警
        WebElement alarmDisplay = l.getElement(param.get("alarmDisplayPendingAlarm"));
        String text = alarmDisplay.getText();
        alarmDisplay.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text.substring(0, 5), "待处理告警");
        co.sleep(5000);
//        获取，待处理告警数
        numberText = Integer.valueOf(co.extractMessageByRegular(text));
        LogFunction.logInfo("待处理告警数量：" + numberText);

//        WebElement Number = l.getElement(param.get("alarmDisplayPendingAlarmNumber"));
//        numberText = Integer.valueOf(Number.getText());
//        LogFunction.logInfo("待处理告警数量：" + numberText);
//        获取，列表待处理告警数量
        List<WebElement> ListNumber = l.getElements(param.get("alarmDisplayListNumber"));
        int size = ListNumber.size();
        WebElement NumberValue = l.getElement(param.get("alarmDisplayListNumberValue"));
        String text5 = NumberValue.getText();
        if (text5.equals(" ")) {
            size = 0;
        }
        if (text5.equals("表中数据为空")) {
            size = 0;
        }
        LogFunction.logInfo("列表待处理告警数量：" + String.valueOf(size));
        AssertFunction.verifyEquals(driver, numberText, size);

//        点击，已确认告警
        WebElement alarmDisplay1 = l.getElement(param.get("alarmDisplayConfirmedAlarm"));
        String text1 = alarmDisplay1.getText();
        alarmDisplay1.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1.substring(0, 5), "已确认告警");
        co.sleep(3000);
        //        获取，已确认告警数量
        numberText1 = Integer.valueOf(co.extractMessageByRegular(text1));
        LogFunction.logInfo("已确认告警数量：" + numberText1);
//        WebElement Number1 = l.getElement(param.get("alarmDisplayConfirmedAlarmNumber"));
//        numberText1 = Integer.valueOf(Number1.getText());
//        LogFunction.logInfo("已确认告警数量：" + numberText1);
//        获取，列表已确认告警数量
        List<WebElement> ListNumber1 = l.getElements(param.get("alarmDisplayListNumber"));
        int size1 = ListNumber1.size();
        WebElement NumberValue1 = l.getElement(param.get("alarmDisplayListNumberValue"));
        String text6 = NumberValue1.getText();
        if (text6.equals(" ")) {
            size1 = 0;
        }
        if (text6.equals("表中数据为空")) {
            size1 = 0;
        }

        LogFunction.logInfo("列表已确认告警数量：" + String.valueOf(size1));
        AssertFunction.verifyEquals(driver, numberText1, size1);

//        点击，已前转告警
        WebElement alarmDisplay2 = l.getElement(param.get("alarmDisplayForwardShiftingAlarm"));
        String text2 = alarmDisplay2.getText();
        alarmDisplay2.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2.substring(0, 5), "已前转告警");
        co.sleep(3000);
//        获取，已前转告警数量
        numberText2 = Integer.valueOf(co.extractMessageByRegular(text2));
        LogFunction.logInfo("已前转告警数量：" + numberText2);

//        WebElement Number2 = l.getElement(param.get("alarmDisplayForwardShiftingAlarmNumber"));
//        numberText2 = Integer.valueOf(Number2.getText());
//        LogFunction.logInfo("已前转告警数量：" + numberText2);

//        获取，列表已前转告警数量
        List<WebElement> ListNumber2 = l.getElements(param.get("alarmDisplayListNumber"));
        int size2 = ListNumber2.size();
        WebElement NumberValue2 = l.getElement(param.get("alarmDisplayListNumberValue"));
        String text7 = NumberValue2.getText();
        if (text7.equals(" ")) {
            size2 = 0;
        }
        if (text7.equals("表中数据为空")) {
            size2 = 0;
        }
        LogFunction.logInfo("列表已前转告警数量：" + String.valueOf(size2));
        AssertFunction.verifyEquals(driver, numberText2, size2);
//        点击，已解决告警
        WebElement alarmDisplay3 = l.getElement(param.get("alarmDisplayResolvedAlarm"));
        String text3 = alarmDisplay3.getText();
        alarmDisplay3.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3.substring(0, 5), "已解决告警");
        co.sleep(3000);
        //        获取，已解决告警数量
        numberText3 = Integer.valueOf(co.extractMessageByRegular(text3));
        LogFunction.logInfo("已解决告警数量：" + numberText3);
//        WebElement Number3 = l.getElement(param.get("alarmDisplayResolvedAlarmNumber"));
//        numberText3 = Integer.valueOf(Number3.getText());
//        LogFunction.logInfo("已解决告警数量：" + numberText3);
//        获取，列表已解决告警数量
        List<WebElement> ListNumber3 = l.getElements(param.get("alarmDisplayListNumber"));
        int size3 = ListNumber3.size();
        WebElement NumberValue3 = l.getElement(param.get("alarmDisplayListNumberValue"));
        String text8 = NumberValue3.getText();
        if (text8.equals(" ")) {
            size3 = 0;
        }
        if (text8.equals("表中数据为空")) {
            size3 = 0;
        }
        LogFunction.logInfo("列表已解决告警数量：" + size3);
        AssertFunction.verifyEquals(driver, numberText3, size3);

//        点击，已忽略告警
        WebElement alarmDisplay4 = l.getElement(param.get("alarmDisplayIgnoreAlarm"));
        String text4 = alarmDisplay4.getText();
        alarmDisplay4.click();
        LogFunction.logInfo("点击：" + text4);
        AssertFunction.verifyEquals(driver, text4.substring(0, 5), "已忽略告警");
        co.sleep(3000);
        //        获取，已忽略告警数量
        numberText4 = Integer.valueOf(co.extractMessageByRegular(text4));
        LogFunction.logInfo("已忽略告警数量：" + numberText4);
//        WebElement Number4 = l.getElement(param.get("alarmDisplayIgnoreAlarmNumber"));
//        numberText4 = Integer.valueOf(Number4.getText());
//        LogFunction.logInfo("已忽略告警数量：" + numberText4);
//        获取，列表已忽略告警数量
        List<WebElement> ListNumber4 = l.getElements(param.get("alarmDisplayListNumber"));
        int size4 = ListNumber4.size();
        WebElement NumberValue4 = l.getElement(param.get("alarmDisplayListNumberValue"));
        String text9 = NumberValue4.getText();
        if (text9.equals(" ")) {
            size4 = 0;
        }
        if (text9.equals("表中数据为空")) {
            size4 = 0;
        }
        LogFunction.logInfo("列表已忽略告警数量：" + String.valueOf(size4));
        AssertFunction.verifyEquals(driver, numberText4, size4);
        LogFunction.logInfo("-----------------告警展示,告警数量校验，成功---------------------");

    }

    //    集中告警-告警展示-告警数量校验
    @Test(dataProvider = "xmldata")
    public void alarmDisplayAlarmsNumberVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，待处理告警
        WebElement alarmDisplay = l.getElement(param.get("alarmDisplayPendingAlarm"));
        String text = alarmDisplay.getText();
        alarmDisplay.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text.substring(0, 5), "待处理告警");
        co.sleep(2000);
        //        获取，待处理告警数
        String s = co.extractMessageByRegular(text);
        LogFunction.logInfo("待处理告警数量：" + s);
//        WebElement Number = l.getElement(param.get("alarmDisplayPendingAlarmNumber"));
//        String numberText00 = Number.getText();
//        LogFunction.logInfo("待处理告警数量：" + numberText00);
//        获取，列表待处理告警数量
        List<WebElement> ListNumber = l.getElements(param.get("alarmDisplayListNumber"));
        int size = ListNumber.size();
        LogFunction.logInfo("列表待处理告警数量：" + String.valueOf(size));
        AssertFunction.verifyEquals(driver, Integer.valueOf(s), numberText - 2);

//        点击，已确认告警
        WebElement alarmDisplay1 = l.getElement(param.get("alarmDisplayConfirmedAlarm"));
        String text1 = alarmDisplay1.getText();
        alarmDisplay1.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1.substring(0, 5), "已确认告警");
        co.sleep(2000);
        //        获取，已确认告警数量
        String s1 = co.extractMessageByRegular(text1);
        LogFunction.logInfo("已确认告警数量：" + s1);
//        WebElement Number1 = l.getElement(param.get("alarmDisplayConfirmedAlarmNumber"));
//        String numberText11 = Number1.getText();
//        LogFunction.logInfo("已确认告警数量：" + numberText11);
//        获取，列表已确认告警数量
        List<WebElement> ListNumber1 = l.getElements(param.get("alarmDisplayListNumber"));
        int size1 = ListNumber1.size();
        LogFunction.logInfo("列表已确认告警数量：" + String.valueOf(size1));
        AssertFunction.verifyEquals(driver, Integer.valueOf(s1), numberText1);

//        点击，已前转告警
        WebElement alarmDisplay2 = l.getElement(param.get("alarmDisplayForwardShiftingAlarm"));
        String text2 = alarmDisplay2.getText();
        alarmDisplay2.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2.substring(0, 5), "已前转告警");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        获取，已前转告警数量
        String s2 = co.extractMessageByRegular(text2);
        LogFunction.logInfo("已前转告警数量：" + s2);
//        WebElement Number2 = l.getElement(param.get("alarmDisplayForwardShiftingAlarmNumber"));
//        String numberText22 = Number2.getText();
//        LogFunction.logInfo("已前转告警数量：" + numberText22);
//        获取，列表已前转告警数量
        List<WebElement> ListNumber2 = l.getElements(param.get("alarmDisplayListNumber"));
        int size2 = ListNumber2.size();
        LogFunction.logInfo("列表已前转告警数量：" + String.valueOf(size2));
        AssertFunction.verifyEquals(driver, Integer.valueOf(s2), numberText2 - 1);

//        点击，已解决告警
        WebElement alarmDisplay3 = l.getElement(param.get("alarmDisplayResolvedAlarm"));
        String text3 = alarmDisplay3.getText();
        alarmDisplay3.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3.substring(0, 5), "已解决告警");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //        获取，已解决告警数量
        String s3 = co.extractMessageByRegular(text3);
        LogFunction.logInfo("已解决告警数量：" + s3);
//        WebElement Number3 = l.getElement(param.get("alarmDisplayResolvedAlarmNumber"));
//        String numberText33 = Number3.getText();
//        LogFunction.logInfo("已解决告警数量：" + numberText33);
//        获取，列表已解决告警数量
        List<WebElement> ListNumber3 = l.getElements(param.get("alarmDisplayListNumber"));
        int size3 = ListNumber3.size();
        LogFunction.logInfo("列表已解决告警数量：" + String.valueOf(size3));
        AssertFunction.verifyEquals(driver, Integer.valueOf(s3), numberText3 + 1);

//        点击，已忽略告警
        WebElement alarmDisplay4 = l.getElement(param.get("alarmDisplayIgnoreAlarm"));
        String text4 = alarmDisplay4.getText();
        alarmDisplay4.click();
        LogFunction.logInfo("点击：" + text4);
        AssertFunction.verifyEquals(driver, text4.substring(0, 5), "已忽略告警");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //        获取，已忽略告警数量
        String s4 = co.extractMessageByRegular(text4);
        LogFunction.logInfo("已忽略告警数量：" + s4);
//        WebElement Number4 = l.getElement(param.get("alarmDisplayIgnoreAlarmNumber"));
//        String numberText44 = Number4.getText();
//        LogFunction.logInfo("已忽略告警数量：" + numberText44);
//        获取，列表已忽略告警数量
        List<WebElement> ListNumber4 = l.getElements(param.get("alarmDisplayListNumber"));
        int size4 = ListNumber4.size();
        LogFunction.logInfo("列表已忽略告警数量：" + String.valueOf(size4));
        AssertFunction.verifyEquals(driver, Integer.valueOf(s4), numberText4 + 1);
        LogFunction.logInfo("-----------------告警展示,告警数量校验，成功---------------------");

    }

    //    集中告警-告警展示-自动刷新
    @Test(dataProvider = "xmldata")
    public void alarmDisplayAutoRefresh(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        打开，自动刷新
        WebElement RefreshOpen = l.getElement(param.get("alarmDisplayAutoRefreshOpen"));
        String title = RefreshOpen.getAttribute("title");
        RefreshOpen.click();
        LogFunction.logInfo("点击：" + title);
        AssertFunction.verifyEquals(driver, title, "打开自动刷新");
        //        勾选，告警
        WebElement chooseAlarm = l.getElement(param.get("chooseAlarm"));
        chooseAlarm.click();
        LogFunction.logInfo("成功：勾选告警");
        boolean selected = chooseAlarm.isSelected();
        LogFunction.logInfo("告警是否勾选：" + selected);

        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean selected1 = l.getElement(param.get("chooseAlarm")).isSelected();
        LogFunction.logInfo("告警是否勾选：" + selected1);
//        关闭，自动刷新
        WebElement refreshClose = l.getElement(param.get("alarmDisplayAutoRefreshClose"));
        String title1 = refreshClose.getAttribute("title");
        refreshClose.click();
        LogFunction.logInfo("点击：" + title1);
//        勾选，告警
        WebElement chooseAlarm1 = l.getElement(param.get("chooseAlarm"));
        chooseAlarm1.click();
        LogFunction.logInfo("成功：勾选告警");
        boolean selected2 = chooseAlarm1.isSelected();
        LogFunction.logInfo("告警是否勾选：" + selected2);
        co.sleep(11000);
        boolean selected3 = l.getElement(param.get("chooseAlarm")).isSelected();
        LogFunction.logInfo("告警是否勾选：" + selected3);
        AssertFunction.verifyEquals(driver, selected3, true);
        LogFunction.logInfo("-----------------告警展示,自动刷新功能校验，成功---------------------");

    }
    //    集中告警-告警展示-查看-提示（选择多条）
    @Test(dataProvider = "xmldata")
    public void alarmDisplayViewPushAlarm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        LogFunction.logInfo("----------------推送告警，开始---------------------");
        int i = 1;
        while (i <= 20) {
            co.pushMergeAlarmInfo(param, "pushAlarmParams123");
            co.sleep(100);
            LogFunction.logInfo("推送第"+i+"条告警");
            i++;
        }
        LogFunction.logInfo("----------------推送告警，结束---------------------");
    }

    //    集中告警-告警展示-查看-提示（选择多条）
    @Test(dataProvider = "xmldata")
    public void alarmDisplayViewMultitermHint(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        勾选，第一条，警示信息
        co.modelRadioBox(param, "chooseAlarm", "告警展示第一条");
        co.modelRadioBox(param, "chooseAlarm2", "告警展示第一条");
//        WebElement chooseAlarm = l.getElement(param.get("chooseAlarm"));
//        chooseAlarm.click();
//        LogFunction.logInfo("成功勾选：第一条警示信息");
////        勾选，第二条，警示信息
//        WebElement chooseAlarm2 = l.getElement(param.get("chooseAlarm2"));
//        chooseAlarm2.click();
//        LogFunction.logInfo("成功勾选：第二条警示信息");
//          点击，查看
        WebElement alarmDisplayView = l.getElement(param.get("alarmDisplayView"));
        String text = alarmDisplayView.getText();
        alarmDisplayView.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "查看");
//        验证提示信息，一次只能操作一条记录
        WebElement hintMessage = l.getElement(param.get("hintMessage"));
        String title = hintMessage.getText();
        LogFunction.logInfo("提示信息为：" + title);
        AssertFunction.verifyEquals(driver, title, "只能选择一条数据");
//         点击，确认
        WebElement hintMessageConfirm = l.getElement(param.get("hintMessageConfirm"));
        String title1 = hintMessageConfirm.getText();
        hintMessageConfirm.click();
        LogFunction.logInfo("点击：" + title1);
        AssertFunction.verifyEquals(driver, title1, "确认");
        LogFunction.logInfo("-----------------告警展示,查看多条告警信息时，提示信息通过---------------------");

    }

    //    集中告警-告警展示-搜索
    @Test(dataProvider = "xmldata")
    public void alarmDisplaySearch(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        搜索框，输入，AutoTest
        WebElement SearchBox = l.getElement(param.get("alarmDisplaySearchBox"));
        SearchBox.sendKeys(param.get("alarmDisplaySearchBoxValue"));
        LogFunction.logInfo("在搜索框中输入：" + param.get("alarmDisplaySearchBoxValue"));
//        点击，搜索按钮
        WebElement Button = l.getElement(param.get("alarmDisplaySearchButton"));
        Button.click();
        LogFunction.logInfo("成功点击：搜索按钮");
        co.sleep(1000);
//        获取消息内容
        WebElement MessageContent = l.getElement(param.get("alarmDisplayColumnDisplayMessageContent"));
        String text = MessageContent.getText();
        LogFunction.logInfo("警示信息：" + text);
        AssertFunction.verifyEquals(driver, text, param.get("alarmDisplaySearchBoxValue"));
//        勾选，警示信息
        WebElement chooseAlarm = l.getElement(param.get("chooseAlarm"));
        chooseAlarm.click();
        LogFunction.logInfo("成功勾选：警示信息");


        LogFunction.logInfo("-----------------告警展示,查看多条告警信息时，提示信息通过---------------------");

    }

    //    集中告警-告警展示-列设置-全选
    @Test(dataProvider = "xmldata")
    public void alarmDisplayColumnSettingAllChoose(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，列设置，按钮
        WebElement ColumnSetting = l.getElement(param.get("alarmDisplayColumnSetting"));
        ColumnSetting.click();
        LogFunction.logInfo("成功点击：列设置按钮");
        co.sleep(2000);
//        勾选所有的列
        List<WebElement> ColumnElement = l.getElements(param.get("alarmDisplayColumnSettingisDisplay"));
        LogFunction.logInfo(ColumnElement.size());
        LogFunction.logInfo("共有" + String.valueOf(ColumnElement.size()) + "个列值");
        for (WebElement e : ColumnElement) {
            co.sleep(300);
            boolean selected = e.isSelected();
            if (selected == false) {
                e.click();
            }
        }
        LogFunction.logInfo("成功勾选，所有列");
//      点击，确定
        WebElement ColumnSettingConfirm = l.getElement(param.get("alarmDisplayColumnSettingConfirm"));
        String text = ColumnSettingConfirm.getText();
        ColumnSettingConfirm.click();
        LogFunction.logInfo("成功点击：" + text);
        co.sleep(1000);
//        获取，页面中所有的列值
        List<WebElement> ColumnDisplay = l.getElements(param.get("alarmDisplayColumnDisplay"));
        AssertFunction.verifyEquals(driver, ColumnDisplay.size() - 1, ColumnElement.size());
        LogFunction.logInfo("页面共展示" + String.valueOf(ColumnDisplay.size() - 1) + "个列值");
        LogFunction.logInfo("-----------------展示已勾选全部列---------------------");

    }

    //    集中告警-告警展示-列设置-全取消
    @Test(dataProvider = "xmldata")
    public void alarmDisplayColumnSettingAllCancle(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        driver.navigate().refresh();
        co.sleep(3000);
//        点击，列设置，按钮
        WebElement ColumnSetting = l.getElement(param.get("alarmDisplayColumnSetting"));
        ColumnSetting.click();
        LogFunction.logInfo("成功点击：列设置按钮");
        co.sleep(2000);
//        取消勾选所有的列
        List<WebElement> ColumnElement = l.getElements(param.get("alarmDisplayColumnSettingisDisplay"));
        LogFunction.logInfo("共有" + String.valueOf(ColumnElement.size()) + "个列值");
        for (WebElement e : ColumnElement) {
            co.sleep(100);
            boolean selected = e.isSelected();
            if (selected == true) {
                e.click();
            }
        }
        LogFunction.logInfo("取消勾选，所有列");
        co.sleep(30);
//      点击，确定
        WebElement ColumnSettingConfirm = l.getElement(param.get("alarmDisplayColumnSettingConfirm"));
        String text = ColumnSettingConfirm.getText();
        ColumnSettingConfirm.click();
        LogFunction.logInfo("成功点击：" + text);
        co.sleep(1000);
//        获取，页面中所有的列值
        List<WebElement> ColumnDisplay = l.getElements(param.get("alarmDisplayColumnDisplay"));
        LogFunction.logInfo("页面共展示" + String.valueOf(ColumnDisplay.size() - 1) + "个列值");
        co.sleep(1000);
        driver.navigate().refresh();
        LogFunction.logInfo("-----------------展示已取消全部列---------------------");

    }

    //    集中告警-告警展示-告警级别
    @Test(dataProvider = "xmldata")
    public void alarmDisplayAlarmRank(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        co.sleep(2000);
//        点击，级别L5
        WebElement RankL5 = l.getElement(param.get("alarmDisplayAlarmRankL5"));
        String text = RankL5.getText();
        Integer integer = Integer.valueOf(text);
        RankL5.click();
        LogFunction.logInfo("点击：告警级别L5");
        LogFunction.logInfo("级别L5数量为：" + text);
        co.sleep(5000);
//        获取，列表待处理告警数量
        List<WebElement> ListNumber = l.getElements(param.get("alarmDisplayListNumber"));
        int size = ListNumber.size();
        WebElement NumberValue = l.getElement(param.get("alarmDisplayListNumberValue"));
        String text5 = NumberValue.getText();
        if (text5.equals(" ")) {
            size = 0;
        }
        if (text5.equals("表中数据为空")) {
            size = 0;
        }
        LogFunction.logInfo("列表待处理告警数量：" + size);
        AssertFunction.verifyEquals(driver, integer, size);
//        点击，级别L4
        WebElement RankL4 = l.getElement(param.get("alarmDisplayAlarmRankL4"));
        String text1 = RankL4.getText();
        Integer integer1 = Integer.valueOf(text1);
        RankL4.click();
        LogFunction.logInfo("点击：告警级别L4");
        LogFunction.logInfo("级别L4数量为：" + text1);
        co.sleep(3000);
//        获取，列表待处理告警数量
        List<WebElement> ListNumber1 = l.getElements(param.get("alarmDisplayListNumber"));
        int size1 = ListNumber1.size();
        WebElement NumberValue1 = l.getElement(param.get("alarmDisplayListNumberValue"));
        String text6 = NumberValue1.getText();
        if (text6.equals(" ")) {
            size1 = 0;
        }
        if (text6.equals("表中数据为空")) {
            size1 = 0;
        }
        LogFunction.logInfo("列表待处理告警数量：" + size1);
        AssertFunction.verifyEquals(driver, integer1 + integer, size1);
//        点击，级别L3
        WebElement RankL3 = l.getElement(param.get("alarmDisplayAlarmRankL3"));
        String text2 = RankL3.getText();
        Integer integer2 = Integer.valueOf(text2);
        RankL3.click();
        LogFunction.logInfo("点击：告警级别L3");
        LogFunction.logInfo("级别L3数量为：" + text2);
        co.sleep(3000);
//        获取，列表待处理告警数量
        List<WebElement> ListNumber2 = l.getElements(param.get("alarmDisplayListNumber"));
        int size2 = ListNumber2.size();
        WebElement NumberValue2 = l.getElement(param.get("alarmDisplayListNumberValue"));
        String text7 = NumberValue2.getText();
        if (text7.equals(" ")) {
            size2 = 0;
        }
        if (text7.equals("表中数据为空")) {
            size2 = 0;
        }
        LogFunction.logInfo("列表待处理告警数量：" + size2);
        AssertFunction.verifyEquals(driver, integer + integer2 + integer1, size2);
//        点击，级别L2
        WebElement RankL2 = l.getElement(param.get("alarmDisplayAlarmRankL2"));
        String text3 = RankL2.getText();
        Integer integer3 = Integer.valueOf(text3);
        RankL2.click();
        LogFunction.logInfo("点击：告警级别L2");
        LogFunction.logInfo("级别L2数量为：" + text3);
        co.sleep(3000);
//        获取，列表待处理告警数量
        List<WebElement> ListNumber3 = l.getElements(param.get("alarmDisplayListNumber"));
        int size3 = ListNumber3.size();
        WebElement NumberValue3 = l.getElement(param.get("alarmDisplayListNumberValue"));
        String text8 = NumberValue3.getText();
        if (text8.equals(" ")) {
            size3 = 0;
        }
        if (text8.equals("表中数据为空")) {
            size3 = 0;
        }
        LogFunction.logInfo("列表待处理告警数量：" + size3);
        AssertFunction.verifyEquals(driver, integer + integer1 + integer2 + integer3, size3);
//        点击，级别L1
        WebElement RankL1 = l.getElement(param.get("alarmDisplayAlarmRankL1"));
        String text4 = RankL1.getText();
        Integer integer4 = Integer.valueOf(text4);
        RankL1.click();
        LogFunction.logInfo("点击：告警级别L4");
        LogFunction.logInfo("级别L1数量为：" + text4);
        co.sleep(3000);
//        获取，列表待处理告警数量
        List<WebElement> ListNumber4 = l.getElements(param.get("alarmDisplayListNumber"));
        int size4 = ListNumber4.size();
        WebElement NumberValue4 = l.getElement(param.get("alarmDisplayListNumberValue"));
        String text9 = NumberValue4.getText();
        if (text9.equals(" ")) {
            size4 = 0;
        }
        if (text9.equals("表中数据为空")) {
            size4 = 0;
        }
        LogFunction.logInfo("列表待处理告警数量：" + size4);
        AssertFunction.verifyEquals(driver, integer + integer1 + integer2 + integer3 + integer4, size4);
        LogFunction.logInfo("-----------------告警展示，告警等级，验证通过---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警合并策略-新建第1部分，基础设置
    @Test(dataProvider = "xmldata")
    public void alarmMergeRulesCreateBasicSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//          点击，新建
        WebElement commonCreate = l.getElement(param.get("commonCreate"));
        String text1 = commonCreate.getText();
        commonCreate.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "新建", "----是否点击新建----");
//          录入，规则名称
        WebElement rulesName = l.getElement(param.get("rulesName"));
        rulesName.sendKeys(param.get("alarmMergeRulesNameValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("alarmMergeRulesNameValue"));
//          选择，优先级，高
        WebElement priority = l.getElement(param.get("priority"));
        Priority = priority.getText();
        priority.click();
        LogFunction.logInfo("勾选优先级" + Priority);
//        点击，基础设置，下一步
        WebElement basicsNextStep = l.getElement(param.get("basicsNextStep"));
        String text3 = basicsNextStep.getText();
        basicsNextStep.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警合并策略，第一步，基础设置录入完成---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警合并策略-新建第2部分，规则条件设置
    @Test(dataProvider = "xmldata")
    public void alarmMergeRulesCreateRulesConditionSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//         点击，域
        WebElement domain = l.getElement(param.get("domain"));
        domain.click();
        LogFunction.logInfo("点击：域");
//        选择，域：rootDomain
        WebElement chooseDomain = l.getElement(param.get("chooseDomain"));
        Domain = chooseDomain.getText();
        chooseDomain.click();
        LogFunction.logInfo("选择域：" + Domain);
        AssertFunction.verifyEquals(driver, Domain, "rootDomain", "----验证选择的域是否为：rootDomain----");
//          点击，类型
        WebElement type = l.getElement(param.get("type"));
        type.click();
        LogFunction.logInfo("点击：类型");
//        选择,类型:操作系统
        WebElement chooseType = l.getElement(param.get("chooseType"));
        Oracal = chooseType.getText();
        chooseType.click();
        LogFunction.logInfo("选择类型：" + Oracal);
        AssertFunction.verifyEquals(driver, Oracal, "Oracle", "----验证选择的类型是否为；Oracle----");
//        点击，告警类型选择
//        WebElement alarmType = l.getElement(param.get("alarmType"));
//        alarmType.click();
//        LogFunction.logInfo("点击：告警类型选择");
////        选择,告警类型选择:Oracle_System_Alert
//        WebElement chooseAlarmType = l.getElement(param.get("chooseAlarmType"));
//        String text6 = chooseAlarmType.getText();
//        chooseAlarmType.click();
//        LogFunction.logInfo("选择告警类型：" + text6);
//        AssertFunction.verifyEquals(driver, text6, "Oracle_System_Alert", "----验证选择的告警类型是否为；Oracle_System_Alert----");
//       点击，规则条件设置，下一步
        WebElement rulesConditionConfigNextStep = l.getElement(param.get("rulesConditionConfigNextStep"));
        String text7 = rulesConditionConfigNextStep.getText();
        rulesConditionConfigNextStep.click();
        LogFunction.logInfo("点击：" + text7);
        AssertFunction.verifyEquals(driver, text7, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警合并策略，第二步，规则条件设置录入完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并策略-新建第3部分，规则条件高级设置
    @Test(dataProvider = "xmldata")
    public void alarmMergeRulesCreateRulesConditionAdvancedSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，节点过滤
//        WebElement nodeFilter = l.getElement(param.get("nodeFilter"));
//        nodeFilter.click();
//        LogFunction.logInfo("点击：节点过滤");
////        选择,节点过滤:StandardTest-Oracle2
//        WebElement chooseNodeFilter = l.getElement(param.get("chooseNodeFilter"));
//        String text8 = chooseNodeFilter.getText();
//        chooseNodeFilter.click();
//        LogFunction.logInfo("选择节点过滤：" + text8);
//        AssertFunction.verifyEquals(driver, text8, "StandardTest-Oracle2", "----验证选择的节点过滤是否为；StandardTest-Oracle2----");
////        点击，空白处
//        WebElement blank = l.getElement(param.get("blank"));
//        blank.click();
//        LogFunction.logInfo("点击：空白处");
////        点击，采集系统选择
//        WebElement acquisitionSystemChoose = l.getElement(param.get("acquisitionSystemChoose"));
//        acquisitionSystemChoose.click();
//        LogFunction.logInfo("点击：采集系统选择");
////        选择,采集系统选择:IBM ITCAM
//        WebElement chooseAcquisitionSystemChoose = l.getElement(param.get("chooseAcquisitionSystemChoose"));
//        String text9 = chooseAcquisitionSystemChoose.getText();
//        chooseAcquisitionSystemChoose.click();
//        LogFunction.logInfo("选择采集系统选择：" + text9);
//        AssertFunction.verifyEquals(driver, text9, "IBM ITCAM", "----验证选择的采集系统选择是否为；IBM ITCAM----");
////        点击，空白处
//        WebElement blank1 = l.getElement(param.get("blank"));
//        blank1.click();
//        LogFunction.logInfo("点击：空白处");
//        录入，内容关键字
        WebElement contentKeyword = l.getElement(param.get("contentKeyword"));
        contentKeyword.sendKeys(param.get("contentKeywordValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("contentKeywordValue"));
//       点击，规则条件高级设置，下一步
        WebElement rulesConditionAdvancedConfigNextStep = l.getElement(param.get("rulesConditionAdvancedConfigNextStep"));
        String text71 = rulesConditionAdvancedConfigNextStep.getText();
        rulesConditionAdvancedConfigNextStep.click();
        LogFunction.logInfo("点击：" + text71);
        AssertFunction.verifyEquals(driver, text71, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警合并策略，第三步，规则条件高级设置录入完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并策略-新建第4部分，告警合并设置
    @Test(dataProvider = "xmldata")
    public void alarmMergeRulesCreateAlarmMergeSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，告警内容
        WebElement alarmContent = l.getElement(param.get("alarmContent"));
        String text = alarmContent.getText();
        alarmContent.click();
        LogFunction.logInfo("点击：告警内容");
//        最大合并数量,录入，5
        WebElement maxMergeNumber = l.getElement(param.get("maxMergeNumber"));
        maxMergeNumber.sendKeys(param.get("maxMergeNumberValue"));
        LogFunction.logInfo("录入最大合并数量：" + param.get("maxMergeNumberValue"));
//        合并时间窗口，录入，1
        WebElement mergeTimeWindows = l.getElement(param.get("mergeTimeWindows"));
        mergeTimeWindows.sendKeys(param.get("mergeTimeWindowsValue"));
        LogFunction.logInfo("录入合并时间窗口：" + param.get("mergeTimeWindowsValue"));

//          点击，保存
        WebElement alarmMergeConfigSave = l.getElement(param.get("alarmMergeConfigSave"));
        String text10 = alarmMergeConfigSave.getText();
        alarmMergeConfigSave.click();
        LogFunction.logInfo("点击：" + text10);
        AssertFunction.verifyEquals(driver, text10, "保存");
        LogFunction.logInfo("-----------------告警合并策略，最后一步，告警合并设置录入完成且告警合并策略创建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并策略-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmMergeRulesSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        点击，筛选
        co.selectO(param);
//        WebElement commonSelect = l.getElement(param.get("commonSelect"));
//        String text = commonSelect.getText();
//        commonSelect.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.sendKeys(param.get("alarmMergeRulesNameValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmMergeRulesNameValue"));
//        点击，状态选择框
        WebElement selectChooseStatus = l.getElement(param.get("selectChooseStatus"));
        selectChooseStatus.click();
        LogFunction.logInfo("点开：状态选择框");
//        状态，选择：启用
        WebElement StartUsing = l.getElement(param.get("selectChooseStatusStartUsing"));
        String text7 = StartUsing.getText();
        StartUsing.click();
        LogFunction.logInfo("选择状态：" + text7);
        AssertFunction.verifyEquals(driver, text7, "启用");
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        勾选,规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
//        校验,规则名称
        WebElement SelectRulesName = l.getElement(param.get("commonDenoiseStrategySelectRulesNameValue"));
        String text1 = SelectRulesName.getText();
        LogFunction.logInfo("规则名称为：" + text1);
        AssertFunction.verifyEquals(driver, text1, param.get("alarmMergeRulesNameValue"));
//        校验,规则类型
        WebElement selectRulesType = l.getElement(param.get("commonDenoiseStrategySelectRulesTypeValue"));
        String text3 = selectRulesType.getText();
        LogFunction.logInfo("规则类型为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "合并");
//        校验,节点类型
        WebElement selectNodeType = l.getElement(param.get("commonDenoiseStrategySelectNodeTypeValue"));
        String text4 = selectNodeType.getText();
        LogFunction.logInfo("节点类型为：" + text4);
        AssertFunction.verifyEquals(driver, text4, Oracal);
//        校验,域
        WebElement selectDomain = l.getElement(param.get("commonDenoiseStrategySelectDomainValue"));
        String text5 = selectDomain.getText();
        LogFunction.logInfo("域为：" + text5);
        AssertFunction.verifyEquals(driver, text5, Domain);
//        校验,优先级
        WebElement selectPriority = l.getElement(param.get("commonDenoiseStrategySelectPriorityValue"));
        String text6 = selectPriority.getText();
        LogFunction.logInfo("优先级为：" + text6);
        AssertFunction.verifyEquals(driver, text6, Priority);
//        校验,状态
        WebElement selectStatus = l.getElement(param.get("commonDenoiseStrategySelectStatusValue"));
        String title = selectStatus.getAttribute("title");
        LogFunction.logInfo("状态为：" + title);
        AssertFunction.verifyEquals(driver, title, "已启用");
        LogFunction.logInfo("-----------------降噪策略告警规则筛选查询校验完成完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并策略-编辑，第1部分，基础设置
    @Test(dataProvider = "xmldata")
    public void alarmMergeRulesEditBasicSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//          点击，编辑
        WebElement commonEdit = l.getElement(param.get("commonEdit"));
        String text1 = commonEdit.getText();
        commonEdit.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "编辑", "----是否点击编辑----");
//          录入，规则名称
        WebElement rulesName = l.getElement(param.get("rulesName"));
        rulesName.clear();
        rulesName.sendKeys(param.get("alarmMergeRulesNameEditValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("alarmMergeRulesNameEditValue"));
//          选择，优先级，低
        WebElement priority = l.getElement(param.get("priorityLow"));
        Priority = priority.getText();
        priority.click();
        LogFunction.logInfo("勾选优先级" + Priority);
//        关闭，启动
        WebElement startOrClose = l.getElement(param.get("startOrClose"));
        boolean selected = startOrClose.isSelected();
        LogFunction.logInfo(String.valueOf(selected));
        if (selected == true) {
            startOrClose.click();
            LogFunction.logInfo("状态更改为：停用");
        }

//        点击，基础设置，下一步
        WebElement basicsNextStep = l.getElement(param.get("basicsNextStep"));
        String text3 = basicsNextStep.getText();
        basicsNextStep.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警合并策略，第一步，基础设置编辑完成---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警合并策略-编辑，第2部分，规则条件设置
    @Test(dataProvider = "xmldata")
    public void alarmMergeRulesEditRulesConditionSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//         点击，域
//        WebElement domain = l.getElement(param.get("domain"));
//        domain.click();
//        LogFunction.logInfo("点击：域");
////        选择，域：rootDomain
//        WebElement chooseDomain = l.getElement(param.get("chooseDomain"));
//        Domain = chooseDomain.getText();
//        chooseDomain.click();
//        LogFunction.logInfo("选择域：" + Domain);
//        AssertFunction.verifyEquals(driver, Domain, "rootDomain", "----验证选择的域是否为：rootDomain----");
////          点击，类型
//        WebElement type = l.getElement(param.get("type"));
//        type.click();
//        LogFunction.logInfo("点击：类型");
////        选择,类型:操作系统
//        WebElement chooseType = l.getElement(param.get("chooseType"));
//        Oracal = chooseType.getText();
//        chooseType.click();
//        LogFunction.logInfo("选择类型：" + Oracal);
//        AssertFunction.verifyEquals(driver, Oracle, "操作系统", "----验证选择的类型是否为；Oracle----");
//        点击，告警类型选择
//        WebElement alarmType = l.getElement(param.get("alarmType"));
//        alarmType.click();
//        LogFunction.logInfo("点击：告警类型选择");
////        选择,告警类型选择:Oracle_System_Alert
//        WebElement chooseAlarmType = l.getElement(param.get("chooseAlarmType"));
//        String text6 = chooseAlarmType.getText();
//        chooseAlarmType.click();
//        LogFunction.logInfo("选择告警类型：" + text6);
//        AssertFunction.verifyEquals(driver, text6, "Oracle_System_Alert", "----验证选择的告警类型是否为；Oracle_System_Alert----");
//       点击，规则条件设置，下一步
        WebElement rulesConditionConfigNextStep = l.getElement(param.get("rulesConditionConfigNextStep"));
        String text7 = rulesConditionConfigNextStep.getText();
        rulesConditionConfigNextStep.click();
        LogFunction.logInfo("点击：" + text7);
        AssertFunction.verifyEquals(driver, text7, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警合并策略，第二步，规则条件设置编辑完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并策略-编辑，第3部分，规则条件高级设置
    @Test(dataProvider = "xmldata")
    public void alarmMergeRulesEditRulesConditionAdvancedSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，节点过滤
//        WebElement nodeFilter = l.getElement(param.get("nodeFilter"));
//        nodeFilter.click();
//        LogFunction.logInfo("点击：节点过滤");
////        选择,节点过滤:StandardTest-Oracle2
//        WebElement chooseNodeFilter = l.getElement(param.get("chooseNodeFilter"));
//        String text8 = chooseNodeFilter.getText();
//        chooseNodeFilter.click();
//        LogFunction.logInfo("选择节点过滤：" + text8);
//        AssertFunction.verifyEquals(driver, text8, "StandardTest-Oracle2", "----验证选择的节点过滤是否为；StandardTest-Oracle2----");
////        点击，空白处
//        WebElement blank = l.getElement(param.get("blank"));
//        blank.click();
//        LogFunction.logInfo("点击：空白处");
////        点击，采集系统选择
//        WebElement acquisitionSystemChoose = l.getElement(param.get("acquisitionSystemChoose"));
//        acquisitionSystemChoose.click();
//        LogFunction.logInfo("点击：采集系统选择");
////        选择,采集系统选择:IBM ITCAM
//        WebElement chooseAcquisitionSystemChoose = l.getElement(param.get("chooseAcquisitionSystemChoose"));
//        String text9 = chooseAcquisitionSystemChoose.getText();
//        chooseAcquisitionSystemChoose.click();
//        LogFunction.logInfo("选择采集系统选择：" + text9);
//        AssertFunction.verifyEquals(driver, text9, "IBM ITCAM", "----验证选择的采集系统选择是否为；IBM ITCAM----");
////        点击，空白处
//        WebElement blank1 = l.getElement(param.get("blank"));
//        blank1.click();
//        LogFunction.logInfo("点击：空白处");
//        录入，内容关键字
//        WebElement contentKeyword = l.getElement(param.get("contentKeyword"));
//        contentKeyword.sendKeys(param.get("contentKeywordValue"));
//        LogFunction.logInfo("规则名称，录入：" + param.get("contentKeywordValue"));
//       点击，规则条件高级设置，下一步
        WebElement rulesConditionAdvancedConfigNextStep = l.getElement(param.get("rulesConditionAdvancedConfigNextStep"));
        String text71 = rulesConditionAdvancedConfigNextStep.getText();
        rulesConditionAdvancedConfigNextStep.click();
        LogFunction.logInfo("点击：" + text71);
        AssertFunction.verifyEquals(driver, text71, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警合并策略，第三步，规则条件高级设置编辑完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并策略-编辑，第4部分，告警合并设置
    @Test(dataProvider = "xmldata")
    public void alarmMergeRulesEditAlarmMergeSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，告警内容
//        WebElement alarmContent = l.getElement(param.get("alarmContent"));
//        String text = alarmContent.getText();
//        alarmContent.click();
//        LogFunction.logInfo("点击：告警内容");
////        最大合并数量,录入，5
//        WebElement maxMergeNumber = l.getElement(param.get("maxMergeNumber"));
//        maxMergeNumber.sendKeys(param.get("maxMergeNumberValue"));
//        LogFunction.logInfo("录入最大合并数量：" + param.get("maxMergeNumberValue"));
////        合并时间窗口，录入，1
//        WebElement mergeTimeWindows = l.getElement(param.get("mergeTimeWindows"));
//        mergeTimeWindows.sendKeys(param.get("maxMergeNumberValue"));
//        LogFunction.logInfo("录入合并时间窗口：" + param.get("mergeTimeWindowsValue"));

//          点击，保存
        WebElement alarmMergeConfigSave = l.getElement(param.get("alarmMergeConfigSave"));
        String text10 = alarmMergeConfigSave.getText();
        alarmMergeConfigSave.click();
        LogFunction.logInfo("点击：" + text10);
        AssertFunction.verifyEquals(driver, text10, "保存");
        LogFunction.logInfo("-----------------告警合并策略，最后一步，告警合并设置编辑完成且告警合并策略创建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并策略-编辑-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmMergeRulesEditSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，筛选
//        WebElement commonSelect = l.getElement(param.get("commonSelect"));
//        String text = commonSelect.getText();
//        commonSelect.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmMergeRulesNameEditValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmMergeRulesNameEditValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        获取，告警信息列表信息，无数据
        WebElement NumberValue = l.getElement(param.get("alarmRulesListNumberValue"));
        String text55 = NumberValue.getText();
        LogFunction.logInfo("告警列表信息为：" + text55);
        //        点击，状态选择框
        WebElement selectChooseStatus1 = l.getElement(param.get("selectChooseStatus"));
        selectChooseStatus1.click();
        LogFunction.logInfo("点开：状态选择框");
//        状态，选择：停用
        WebElement BlockUp = l.getElement(param.get("selectChooseStatusBlockUp"));
        String text8 = BlockUp.getText();
        BlockUp.click();
        LogFunction.logInfo("选择状态：" + text8);
        AssertFunction.verifyEquals(driver, text8, "停用");
//        点击，筛选，确定
        WebElement selectAffirm1 = l.getElement(param.get("commonSelectAffirm"));
        String text22 = selectAffirm1.getText();
        selectAffirm1.click();
        LogFunction.logInfo("点击：" + text22);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");

        //        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
//        校验,规则名称
        WebElement SelectRulesName = l.getElement(param.get("commonDenoiseStrategySelectRulesNameValue"));
        String text1 = SelectRulesName.getText();
        LogFunction.logInfo("规则名称为：" + text1);
        AssertFunction.verifyEquals(driver, text1, param.get("alarmMergeRulesNameEditValue"));
//        校验,规则类型
        WebElement selectRulesType = l.getElement(param.get("commonDenoiseStrategySelectRulesTypeValue"));
        String text3 = selectRulesType.getText();
        LogFunction.logInfo("规则类型为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "合并");
//        校验,节点类型
        WebElement selectNodeType = l.getElement(param.get("commonDenoiseStrategySelectNodeTypeValue"));
        String text4 = selectNodeType.getText();
        LogFunction.logInfo("节点类型为：" + text4);
        AssertFunction.verifyEquals(driver, text4, Oracal);
//        校验,域
        WebElement selectDomain = l.getElement(param.get("commonDenoiseStrategySelectDomainValue"));
        String text5 = selectDomain.getText();
        LogFunction.logInfo("域为：" + text5);
        AssertFunction.verifyEquals(driver, text5, Domain);
//        校验,优先级
        WebElement selectPriority = l.getElement(param.get("commonDenoiseStrategySelectPriorityValue"));
        String text6 = selectPriority.getText();
        LogFunction.logInfo("优先级为：" + text6);
        AssertFunction.verifyEquals(driver, text6, Priority);
//        校验,状态
        WebElement selectStatus = l.getElement(param.get("commonDenoiseStrategySelectStatusValue"));
        String title = selectStatus.getAttribute("title");
        LogFunction.logInfo("状态为：" + title);
        AssertFunction.verifyEquals(driver, title, "已禁用");
        LogFunction.logInfo("-----------------降噪策略,告警合并策略,筛选查询完成---------------------");
    }

    //    （公用）启用，告警规则
    @Test(dataProvider = "xmldata")
    public void alarmRulesStartUsing(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        勾选，规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        if (selectChooseRules.isSelected() == false) {
            selectChooseRules.click();
            LogFunction.logInfo("勾选筛选结果规则");
        }
//        点击，启用按钮
        WebElement commonStartUsing = l.getElement(param.get("commonStartUsing"));
        String text2 = commonStartUsing.getText();
        commonStartUsing.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "启用", "----是否点击的是：启用按钮----");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //        校验,状态
        WebElement selectStatus = l.getElement(param.get("commonDenoiseStrategySelectStatusValue"));
        String title = selectStatus.getAttribute("title");
        LogFunction.logInfo("状态为：" + title);
        AssertFunction.verifyEquals(driver, title, "已启用");
        LogFunction.logInfo("------------------告警规则，成功启用---------------------");

    }

    //    （公用）禁止，告警规则
    @Test(dataProvider = "xmldata")
    public void alarmRulesBlockUp(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        勾选，规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        if (selectChooseRules.isSelected() == false) {
            selectChooseRules.click();
            LogFunction.logInfo("勾选筛选结果规则");
        }
//        点击，停用按钮
        WebElement commonBlockUp = l.getElement(param.get("commonBlockUp"));
        String text2 = commonBlockUp.getText();
        commonBlockUp.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "停用", "----是否点击的是：停用按钮----");
        try {
            Thread.sleep(5500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //        校验,状态
        WebElement selectStatus = l.getElement(param.get("commonDenoiseStrategySelectStatusValue"));
        String title = selectStatus.getAttribute("title");
        LogFunction.logInfo("状态为：" + title);
        AssertFunction.verifyEquals(driver, title, "已禁用");
        LogFunction.logInfo("------------------告警规则，成功禁用---------------------");

    }

    //   （公用）删除，告警规则
    @Test(dataProvider = "xmldata")
    public void alarmRulesDelete(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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

        selectRulesName.sendKeys(param.get("alarmMergeRulesNameEditValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmMergeRulesNameEditValue"));
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
        LogFunction.logInfo("------------------告警规则，删除成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并策略-编辑-筛选(仅筛选，勾选规则）
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmMergeRulesEditSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        验证，筛选区域，是否展示
        WebElement SelectArea = l.getElement(param.get("denoiseStrategySelectArea"));
        boolean displayed = SelectArea.isDisplayed();
        if (displayed == false) {
//        点击，筛选
            co.selectO(param);
//            WebElement commonSelect = l.getElement(param.get("commonSelect"));
//            String text = commonSelect.getText();
//            commonSelect.click();
//            LogFunction.logInfo("点击：" + text);
//            AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
        }
//        点击，清空
        WebElement commonSelect = l.getElement(param.get("Clear"));
        String text = commonSelect.getText();
        commonSelect.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "清空");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmMergeRulesNameEditValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmMergeRulesNameEditValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
        LogFunction.logInfo("-----------------降噪策略,告警合并规则,筛选查询完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并策略-编辑-筛选(仅筛选，勾选规则）
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmMergeRulesEditDeleteAndSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        验证，筛选区域，是否展示
        WebElement SelectArea = l.getElement(param.get("denoiseStrategySelectArea"));
        boolean displayed = SelectArea.isDisplayed();
        if (displayed == false) {
//        点击，筛选
            co.selectO(param);
        }
//        点击，清空
        WebElement commonSelect = l.getElement(param.get("Clear"));
        String text = commonSelect.getText();
        commonSelect.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "清空");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmMergeRulesNameEditValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmMergeRulesNameEditValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
        co.sleep(1000);
        //        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
        co.alarmRulesDelete(param, "alarmMergeRulesNameEditValue");
        LogFunction.logInfo("-----------------降噪策略,告警合并规则,删除且筛选校验通过---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类规则-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmClassifyRulesSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        点击，筛选
        co.selectO(param);
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmClassifyRulesNameValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmClassifyRulesNameValue"));
//        点击，状态选择框
        WebElement selectChooseStatus = l.getElement(param.get("selectChooseStatus"));
        selectChooseStatus.click();
        LogFunction.logInfo("点开：状态选择框");

//        状态，选择：启用
        WebElement StartUsing = l.getElement(param.get("selectChooseStatusStartUsing"));
        String text7 = StartUsing.getText();
        StartUsing.click();
        LogFunction.logInfo("选择状态：" + text7);
        AssertFunction.verifyEquals(driver, text7, "启用");
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        勾选,规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
//        校验,规则名称
        WebElement SelectRulesName = l.getElement(param.get("commonDenoiseStrategySelectRulesNameValue"));
        String text1 = SelectRulesName.getText();
        LogFunction.logInfo("规则名称为：" + text1);
        AssertFunction.verifyEquals(driver, text1, param.get("alarmClassifyRulesNameValue"));
//        校验,规则类型
        WebElement selectRulesType = l.getElement(param.get("commonDenoiseStrategySelectRulesTypeValue"));
        String text3 = selectRulesType.getText();
        LogFunction.logInfo("规则类型为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "分类");
//        校验,节点类型
        WebElement selectNodeType = l.getElement(param.get("commonDenoiseStrategySelectNodeTypeValue"));
        String text4 = selectNodeType.getText();
        LogFunction.logInfo("节点类型为：" + text4);
        AssertFunction.verifyEquals(driver, text4, Oracal);
//        校验,域
        WebElement selectDomain = l.getElement(param.get("commonDenoiseStrategySelectDomainValue"));
        String text5 = selectDomain.getText();
        LogFunction.logInfo("域为：" + text5);
        AssertFunction.verifyEquals(driver, text5, Domain);
//        校验,优先级
        WebElement selectPriority = l.getElement(param.get("commonDenoiseStrategySelectPriorityValue"));
        String text6 = selectPriority.getText();
        LogFunction.logInfo("优先级为：" + text6);
        AssertFunction.verifyEquals(driver, text6, Priority);
//        校验,状态
        WebElement selectStatus = l.getElement(param.get("commonDenoiseStrategySelectStatusValue"));
        String title = selectStatus.getAttribute("title");
        LogFunction.logInfo("状态为：" + title);
        AssertFunction.verifyEquals(driver, title, "已启用");
        LogFunction.logInfo("-----------------降噪策略告警规则筛选查询校验完成完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类策略-编辑，第1部分，基础设置
    @Test(dataProvider = "xmldata")
    public void alarmClassifyRulesEditBasicSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//          点击，编辑
        WebElement commonEdit = l.getElement(param.get("commonEdit"));
        String text1 = commonEdit.getText();
        commonEdit.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "编辑", "----是否点击编辑----");
//          录入，规则名称
        WebElement rulesName = l.getElement(param.get("rulesName"));
        rulesName.clear();
        rulesName.sendKeys(param.get("alarmClassifyRulesNameEditValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("alarmClassifyRulesNameEditValue"));
//          选择，优先级，低
        WebElement priority = l.getElement(param.get("priorityLow"));
        Priority = priority.getText();
        priority.click();
        LogFunction.logInfo("勾选优先级" + Priority);
//        关闭，启动
        WebElement startOrClose = l.getElement(param.get("startOrClose"));
        boolean selected = startOrClose.isSelected();
        LogFunction.logInfo(String.valueOf(selected));
        if (selected == true) {
            startOrClose.click();
            LogFunction.logInfo("状态更改为：停用");
        }

//        点击，基础设置，下一步
        WebElement basicsNextStep = l.getElement(param.get("basicsNextStep"));
        String text3 = basicsNextStep.getText();
        basicsNextStep.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警分类策略，第一步，基础设置编辑完成---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警分类策略-编辑，第2部分，规则条件设置
    @Test(dataProvider = "xmldata")
    public void alarmClassifyRulesEditRulesConditionSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//         点击，域
//        WebElement domain = l.getElement(param.get("domain"));
//        domain.click();
//        LogFunction.logInfo("点击：域");
////        选择，域：rootDomain
//        WebElement chooseDomain = l.getElement(param.get("chooseDomain"));
//        Domain = chooseDomain.getText();
//        chooseDomain.click();
//        LogFunction.logInfo("选择域：" + Domain);
//        AssertFunction.verifyEquals(driver, Domain, "rootDomain", "----验证选择的域是否为：rootDomain----");
////          点击，类型
//        WebElement type = l.getElement(param.get("type"));
//        type.click();
//        LogFunction.logInfo("点击：类型");
////        选择,类型:操作系统
//        WebElement chooseType = l.getElement(param.get("chooseType"));
//        Oracal = chooseType.getText();
//        chooseType.click();
//        LogFunction.logInfo("选择类型：" + Oracal);
//        AssertFunction.verifyEquals(driver, Oracle, "操作系统", "----验证选择的类型是否为；Oracle----");
//        点击，告警类型选择
//        WebElement alarmType = l.getElement(param.get("alarmType"));
//        alarmType.click();
//        LogFunction.logInfo("点击：告警类型选择");
////        选择,告警类型选择:Oracle_System_Alert
//        WebElement chooseAlarmType = l.getElement(param.get("chooseAlarmType"));
//        String text6 = chooseAlarmType.getText();
//        chooseAlarmType.click();
//        LogFunction.logInfo("选择告警类型：" + text6);
//        AssertFunction.verifyEquals(driver, text6, "Oracle_System_Alert", "----验证选择的告警类型是否为；Oracle_System_Alert----");
//       点击，规则条件设置，下一步
        WebElement rulesConditionConfigNextStep = l.getElement(param.get("rulesConditionConfigNextStep"));
        String text7 = rulesConditionConfigNextStep.getText();
        rulesConditionConfigNextStep.click();
        LogFunction.logInfo("点击：" + text7);
        AssertFunction.verifyEquals(driver, text7, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警分类策略，第二步，规则条件设置编辑完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类策略-编辑，第3部分，规则条件高级设置
    @Test(dataProvider = "xmldata")
    public void alarmClassifyRulesEditRulesConditionAdvancedSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        点击，节点过滤
//        WebElement nodeFilter = l.getElement(param.get("nodeFilter"));
//        nodeFilter.click();
//        LogFunction.logInfo("点击：节点过滤");
////        选择,节点过滤:StandardTest-Oracle2
//        WebElement chooseNodeFilter = l.getElement(param.get("chooseNodeFilter"));
//        String text8 = chooseNodeFilter.getText();
//        chooseNodeFilter.click();
//        LogFunction.logInfo("选择节点过滤：" + text8);
//        AssertFunction.verifyEquals(driver, text8, "StandardTest-Oracle2", "----验证选择的节点过滤是否为；StandardTest-Oracle2----");
////        点击，空白处
//        WebElement blank = l.getElement(param.get("blank"));
//        blank.click();
//        LogFunction.logInfo("点击：空白处");
////        点击，采集系统选择
//        WebElement acquisitionSystemChoose = l.getElement(param.get("acquisitionSystemChoose"));
//        acquisitionSystemChoose.click();
//        LogFunction.logInfo("点击：采集系统选择");
////        选择,采集系统选择:IBM ITCAM
//        WebElement chooseAcquisitionSystemChoose = l.getElement(param.get("chooseAcquisitionSystemChoose"));
//        String text9 = chooseAcquisitionSystemChoose.getText();
//        chooseAcquisitionSystemChoose.click();
//        LogFunction.logInfo("选择采集系统选择：" + text9);
//        AssertFunction.verifyEquals(driver, text9, "IBM ITCAM", "----验证选择的采集系统选择是否为；IBM ITCAM----");
////        点击，空白处
//        WebElement blank1 = l.getElement(param.get("blank"));
//        blank1.click();
//        LogFunction.logInfo("点击：空白处");
//        录入，内容关键字
//        WebElement contentKeyword = l.getElement(param.get("contentKeyword"));
//        contentKeyword.sendKeys(param.get("contentKeywordValue"));
//        LogFunction.logInfo("规则名称，录入：" + param.get("contentKeywordValue"));
//       点击，规则条件高级设置，下一步
        WebElement rulesConditionAdvancedConfigNextStep = l.getElement(param.get("rulesConditionAdvancedConfigNextStep"));
        String text71 = rulesConditionAdvancedConfigNextStep.getText();
        rulesConditionAdvancedConfigNextStep.click();
        LogFunction.logInfo("点击：" + text71);
        AssertFunction.verifyEquals(driver, text71, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警分类策略，第三步，规则条件高级设置编辑完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类策略-编辑，第4部分，告警分类设置
    @Test(dataProvider = "xmldata")
    public void alarmClassifyRulesEditAlarmClassifySetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        点击，告警内容
//        WebElement alarmContent = l.getElement(param.get("alarmContent"));
//        String text = alarmContent.getText();
//        alarmContent.click();
//        LogFunction.logInfo("点击：告警内容");
////        最大合并数量,录入，5
//        WebElement maxMergeNumber = l.getElement(param.get("maxMergeNumber"));
//        maxMergeNumber.sendKeys(param.get("maxMergeNumberValue"));
//        LogFunction.logInfo("录入最大合并数量：" + param.get("maxMergeNumberValue"));
////        合并时间窗口，录入，1
//        WebElement mergeTimeWindows = l.getElement(param.get("mergeTimeWindows"));
//        mergeTimeWindows.sendKeys(param.get("maxMergeNumberValue"));
//        LogFunction.logInfo("录入合并时间窗口：" + param.get("mergeTimeWindowsValue"));

//          点击，保存
        WebElement alarmMergeConfigSave = l.getElement(param.get("alarmClassifyConfigSave"));
        String text10 = alarmMergeConfigSave.getText();
        alarmMergeConfigSave.click();
        LogFunction.logInfo("点击：" + text10);
        LogFunction.logInfo("-----------------告警分类策略，最后一步，告警合并设置编辑完成且告警合并策略创建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类策略-编辑-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmClassifyRulesEditSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        点击，筛选
//        WebElement commonSelect = l.getElement(param.get("commonSelect"));
//        String text = commonSelect.getText();
//        commonSelect.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmClassifyRulesNameEditValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmClassifyRulesNameEditValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        获取，告警信息列表信息，无数据
        WebElement NumberValue = l.getElement(param.get("alarmRulesListNumberValue"));
        String text55 = NumberValue.getText();
        LogFunction.logInfo("告警列表信息为：" + text55);
        //        点击，状态选择框
        WebElement selectChooseStatus1 = l.getElement(param.get("selectChooseStatus"));
        selectChooseStatus1.click();
        LogFunction.logInfo("点开：状态选择框");
//        状态，选择：停用
        WebElement BlockUp = l.getElement(param.get("selectChooseStatusBlockUp"));
        String text8 = BlockUp.getText();
        BlockUp.click();
        LogFunction.logInfo("选择状态：" + text8);
        AssertFunction.verifyEquals(driver, text8, "停用");
//        点击，筛选，确定
        WebElement selectAffirm1 = l.getElement(param.get("commonSelectAffirm"));
        String text22 = selectAffirm1.getText();
        selectAffirm1.click();
        LogFunction.logInfo("点击：" + text22);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");

        //        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
//        校验,规则名称
        WebElement SelectRulesName = l.getElement(param.get("commonDenoiseStrategySelectRulesNameValue"));
        String text1 = SelectRulesName.getText();
        LogFunction.logInfo("规则名称为：" + text1);
        AssertFunction.verifyEquals(driver, text1, param.get("alarmClassifyRulesNameEditValue"));
//        校验,规则类型
        WebElement selectRulesType = l.getElement(param.get("commonDenoiseStrategySelectRulesTypeValue"));
        String text3 = selectRulesType.getText();
        LogFunction.logInfo("规则类型为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "分类");
//        校验,节点类型
        WebElement selectNodeType = l.getElement(param.get("commonDenoiseStrategySelectNodeTypeValue"));
        String text4 = selectNodeType.getText();
        LogFunction.logInfo("节点类型为：" + text4);
        AssertFunction.verifyEquals(driver, text4, Oracal);
//        校验,域
        WebElement selectDomain = l.getElement(param.get("commonDenoiseStrategySelectDomainValue"));
        String text5 = selectDomain.getText();
        LogFunction.logInfo("域为：" + text5);
        AssertFunction.verifyEquals(driver, text5, Domain);
//        校验,优先级
        WebElement selectPriority = l.getElement(param.get("commonDenoiseStrategySelectPriorityValue"));
        String text6 = selectPriority.getText();
        LogFunction.logInfo("优先级为：" + text6);
        AssertFunction.verifyEquals(driver, text6, Priority);
//        校验,状态
        WebElement selectStatus = l.getElement(param.get("commonDenoiseStrategySelectStatusValue"));
        String title = selectStatus.getAttribute("title");
        LogFunction.logInfo("状态为：" + title);
        AssertFunction.verifyEquals(driver, title, "已禁用");
        LogFunction.logInfo("-----------------降噪策略告警分类策略筛选查询完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类策略-编辑-筛选
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmClassifyRulesEditSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        验证，筛选区域，是否展示
        WebElement SelectArea = l.getElement(param.get("denoiseStrategySelectArea"));
        boolean displayed = SelectArea.isDisplayed();
        if (displayed == false) {
//        点击，筛选
            co.selectO(param);
        }
//        点击，清空
        WebElement commonSelect = l.getElement(param.get("Clear"));
        String text = commonSelect.getText();
        commonSelect.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "清空");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmClassifyRulesNameEditValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmClassifyRulesNameEditValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
        LogFunction.logInfo("-----------------降噪策略告警分类策略筛选查询完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类策略-编辑-筛选
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmClassifyRulesDeleteAndSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        验证，筛选区域，是否展示
        WebElement SelectArea = l.getElement(param.get("denoiseStrategySelectArea"));
        boolean displayed = SelectArea.isDisplayed();
        if (displayed == false) {
//        点击，筛选
            co.selectO(param);
        }
//        点击，清空
        WebElement commonSelect = l.getElement(param.get("Clear"));
        String text = commonSelect.getText();
        commonSelect.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "清空");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmClassifyRulesNameEditValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmClassifyRulesNameEditValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
        co.alarmRulesDelete(param, "alarmClassifyRulesNameEditValue");
        LogFunction.logInfo("-----------------降噪策略,告警分类策略,删除及筛选校验通过---------------------");
    }

    //    集中告警-告警查询
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，告警查询
        co.modelAreaDisplayAndClick(param,"alarmSelectArea","alarmSelect");
//        co.modelClickButton(param, "alarmSelect");
        LogFunction.logInfo("-----------------进入，告警查询页面---------------------");

    }

    //    集中告警-告警查询-前转记录
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmSelectForwardShiftingRecording(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，前转记录
        WebElement shiftingRecording = l.getElement(param.get("shiftingRecording"));
        String text = shiftingRecording.getText();
        shiftingRecording.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "前转记录");
        LogFunction.logInfo("-----------------进入，前转记录页面---------------------");

    }

    //    集中告警-告警查询-前转记录-查看
    @Test(dataProvider = "xmldata")
    public void shiftingRecordingView(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，查看
        WebElement alarmDisplayView = l.getElement(param.get("alarmDisplayView"));
        String text = alarmDisplayView.getText();
        alarmDisplayView.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "查看");
//        点击，取消
        WebElement viewCancel = l.getElement(param.get("viewCancel"));
        String text1 = viewCancel.getText();
        viewCancel.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "取消");
        LogFunction.logInfo("-----------------告警查询-前转记录,进入查看页面---------------------");
    }

    //    集中告警-告警查询-前转记录-查看
    @Test(dataProvider = "xmldata")
    public void forwardShiftingRecodingViewCancel(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，取消
        WebElement viewCancel = l.getElement(param.get("viewCancel"));
        String text = viewCancel.getText();
        viewCancel.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "取消");
        LogFunction.logInfo("-----------------告警查询-前转记录,关闭查看页面---------------------");
    }

    //    集中告警-告警查询-前转记录-查询
    @Test(dataProvider = "xmldata")
    public void forwardShiftingRecodingSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        录入，障碍名称
        WebElement BreakdownName = l.getElement(param.get("BreakdownName"));
        BreakdownName.clear();
        BreakdownName.sendKeys(param.get("commonForwardShiftingTitleValue"));
        LogFunction.logInfo("障碍名称,录入：" + param.get("commonForwardShiftingTitleValue"));
//        点击，确定
        WebElement commonAffirm = l.getElement(param.get("commonAffirm"));
        String c = commonAffirm.getText();
        commonAffirm.click();
        LogFunction.logInfo("点击：" + c);
        AssertFunction.verifyEquals(driver, c, "确定");
        LogFunction.logInfo("-----------------告警查询-前转记录,关闭查看页面---------------------");
    }

    //    集中告警-告警查询-历史告警
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmSelectHistoryAlarm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，历史告警
        co.modelClickButton(param, "historyAlarm");
        LogFunction.logInfo("-----------------进入，历史告警页面---------------------");
    }

    //    集中告警-告警查询-历史告警-筛选
    @Test(dataProvider = "xmldata")
    public void alarmQueryHistoryAlarmSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，筛选
        co.selectO(param);
//         点击，CI域，选择框
//        WebElement selectCIDomain = l.getElement(param.get("selectCIDomain"));
//        selectCIDomain.click();
//        LogFunction.logInfo("点击：CI域");
////          选择，CI域，(rootDomain)
//        WebElement selectCIDomainValue = l.getElement(param.get("selectCIDomainValue"));
//        String text1 = selectCIDomainValue.getText();
//        selectCIDomainValue.click();
//        LogFunction.logInfo("CI域选择：" + text1);
//        AssertFunction.verifyEquals(driver, text1, "rootDomain");
////         点击，CI类型，选择框
//        WebElement selectCIType = l.getElement(param.get("selectCIType"));
//        selectCIType.click();
//        LogFunction.logInfo("点击：CI域");
////          选择，CI类型，(操作系统)
//        WebElement selectCITypeValue = l.getElement(param.get("selectCITypeValue"));
//        String text2 = selectCITypeValue.getText();
//        selectCITypeValue.click();
//        LogFunction.logInfo("CI类型选择：" + text2);
//        AssertFunction.verifyEquals(driver, text2, "操作系统");
////         点击，CI名称，选择框
//        WebElement selectCIName = l.getElement(param.get("selectCIName"));
//        selectCIName.click();
//        LogFunction.logInfo("点击：CI域");
////          选择，CI名称，(StandardLinux_Test1)
//        WebElement selectCINameValue = l.getElement(param.get("selectCINameValue"));
//        String text3 = selectCINameValue.getText();
//        selectCINameValue.click();
//        LogFunction.logInfo("CI名称选择：" + text3);
//        AssertFunction.verifyEquals(driver, text3, "StandardLinux_Test1");
////        点击，空白
//        WebElement systemTitleblank = l.getElement(param.get("systemTitleblank"));
//        systemTitleblank.click();
//        LogFunction.logInfo("点击： 空白");
//        录入，告警信息
        WebElement selectAlarmInformation = l.getElement(param.get("selectAlarmInformation"));
        selectAlarmInformation.clear();
        selectAlarmInformation.sendKeys(param.get("selectAlarmInformationValue"));
        LogFunction.logInfo("告警信息录入：" + param.get("selectAlarmInformationValue"));
//          点击，确定
        WebElement selectConfirm = l.getElement(param.get("selectConfirm"));
        String text4 = selectConfirm.getText();
        selectConfirm.click();
        LogFunction.logInfo("点击：" + text4);
        AssertFunction.verifyEquals(driver, text4, "确定");
        co.sleep(2000);
//          校验，筛选结果，信息内容：Message:AutoTest
        WebElement selecResultInformationContent = l.getElement(param.get("selecResultInformationContent"));
        String text5 = selecResultInformationContent.getText();
        LogFunction.logInfo("信息内容为：" + text5);
        AssertFunction.verifyEquals(driver, text5, "AutoTest");

        LogFunction.logInfo("-----------------历史告警,筛选校验通过---------------------");

    }


    //    集中告警-告警查询-历史告警-前转
    @Test(dataProvider = "xmldata")
    public void alarmQueryHistoryAlarmForwardShifting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

////        点击，前转
//        WebElement Confirmation = l.getElement(param.get("alarmDisplayForwardShifting"));
//        String text = Confirmation.getText();
//        Confirmation.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "前转");
        co.sleep(1000);
//        录入，标题
        WebElement forwardShiftingTitle = l.getElement(param.get("forwardShiftingTitle"));
        forwardShiftingTitle.clear();
        forwardShiftingTitle.sendKeys(param.get("commonForwardShiftingTitleValue"));
        LogFunction.logInfo("前转标题为：" + param.get("commonForwardShiftingTitleValue"));
//        点击，级别选择框
        WebElement LevelChoose = l.getElement(param.get("LevelChoose"));
        LevelChoose.click();
        LogFunction.logInfo("点击，级别选择框");
//        选择，级别选择
        WebElement LevelChooseValue = l.getElement(param.get("LevelChooseValue"));
        String text1 = LevelChooseValue.getText();
        LevelChooseValue.click();
        LogFunction.logInfo("选择级别选择：" + text1);
//        点击，故障分类
        WebElement FaultClassify = l.getElement(param.get("FaultClassify"));
        FaultClassify.click();
        LogFunction.logInfo("点击，故障分类框");
//        选择，故障分类-值
        WebElement FaultClassifyValue = l.getElement(param.get("FaultClassifyValue"));
        String text2 = FaultClassifyValue.getText();
        FaultClassifyValue.click();
        LogFunction.logInfo("选择级别选择：" + text2);
//        点击，备注，确定
        WebElement affirm = l.getElement(param.get("affirm"));
        String text21 = affirm.getAttribute("value");
        affirm.click();
        LogFunction.logInfo("点击：" + text21);
//        弹出，提示信息
        WebElement hintMessage = l.getElement(param.get("hintMessage"));
        String text3 = hintMessage.getText();
        hintMessage.click();
        LogFunction.logInfo("提示信息为：" + text3);
//        点击，备注-提示信息，确认
//        WebElement affirm1 = l.getElement(param.get("hintMessageConfirm"));
//        String text4 = affirm1.getText();
//        affirm1.click();
//        LogFunction.logInfo("点击：" + text4);
//        AssertFunction.verifyEquals(driver, text4, "确认");
        LogFunction.logInfo("-----------------告警展示,前转，完成---------------------");
    }

    //    集中告警-告警查询-历史告警-前转2
    @Test(dataProvider = "xmldata")
    public void alarmQueryHistoryAlarmForwardShiftingMode2(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
////        点击，前转
//        WebElement Confirmation = l.getElement(param.get("alarmDisplayForwardShifting"));
//        String text = Confirmation.getText();
//        Confirmation.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "前转");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        录入，标题
        WebElement forwardShiftingTitle = l.getElement(param.get("forwardShiftingTitle"));
        forwardShiftingTitle.click();
        forwardShiftingTitle.clear();
        forwardShiftingTitle.sendKeys(param.get("commonForwardShiftingTitleValue"));
        LogFunction.logInfo("前转标题为：" + param.get("commonForwardShiftingTitleValue"));
//        点击，直接转给用户
        WebElement LevelChoose = l.getElement(param.get("DirectToUser"));
        LevelChoose.click();
        LogFunction.logInfo("点击，直接转给用户");
//        点击，前转类型选择框
        WebElement ForwardShiftingClassify = l.getElement(param.get("ForwardShiftingClassify"));
        ForwardShiftingClassify.click();
        LogFunction.logInfo("点击，前转类型选择框");
//        选择，前转类型-值
        WebElement ForwardShiftingClassifyValue = l.getElement(param.get("ForwardShiftingClassifyValue"));
        String text1 = ForwardShiftingClassifyValue.getText();
        ForwardShiftingClassifyValue.click();
        LogFunction.logInfo("选择前转类型：" + text1);

//        点击，前转用户
        WebElement ForwardShiftingUser = l.getElement(param.get("ForwardShiftingUser"));
        ForwardShiftingUser.click();
        LogFunction.logInfo("点击，前转用户选择框");
//        选择，前转用户-值
        WebElement ForwardShiftingUserValue = l.getElement(param.get("ForwardShiftingUserValue"));
        String text2 = ForwardShiftingUserValue.getText();
        ForwardShiftingUserValue.click();
        LogFunction.logInfo("选择前转用户：" + text2);
//        点击，备注，确定
        WebElement affirm = l.getElement(param.get("affirm"));
        String text21 = affirm.getAttribute("value");
        affirm.click();
        LogFunction.logInfo("点击：" + text21);
//        弹出，提示信息
        WebElement hintMessage = l.getElement(param.get("hintMessage"));
        String text3 = hintMessage.getText();
        hintMessage.click();
        LogFunction.logInfo("提示信息为：" + text3);
//        点击，备注-提示信息，确认
//        WebElement affirm1 = l.getElement(param.get("hintMessageConfirm"));
//        String text4 = affirm1.getText();
//        affirm1.click();
//        LogFunction.logInfo("点击：" + text4);
//        AssertFunction.verifyEquals(driver, text4, "确认");
        LogFunction.logInfo("-----------------告警展示,前转，完成---------------------");
    }

    //    集中告警-告警查询-历史告警-分页功能
    @Test(dataProvider = "xmldata")
    public void alarmQueryHistoryAlarmPaging(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
////        点击，前转
//        WebElement Confirmation = l.getElement(param.get("alarmDisplayForwardShifting"));
//        String text = Confirmation.getText();
//        Confirmation.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "前转");
        co.sleep(1000);
//        获取，历史告警，总数
        WebElement PagingTotal = l.getElement(param.get("Total"));
        String text = PagingTotal.getText();
        String[] split = text.split(" ");
        String s1 = split[5];
        String num = "";
        Integer total = null;
        if (s1.contains(",")) {
            String[] split2 = s1.split(",");
            for (int i = 0; i < split2.length; i++) {
                num += split2[i];
            }
            total = Integer.valueOf(num);
            LogFunction.logInfo("历史告警总数是：" + total);
        } else {
            total = Integer.valueOf(s1);
            LogFunction.logInfo("历史告警总数是：" + s1);
        }
//        验证，是否从第1项开始，
        AssertFunction.verifyEquals(driver, Integer.valueOf(split[1]), 1);
//        获取，选择多少项，分页（50,100,200,All）
        WebElement TotalChoose = l.getElement(param.get("TotalChoose"));
        Select s = new Select(TotalChoose);
        WebElement webElement = s.getFirstSelectedOption();
        String text1 = webElement.getText();
//        获取，多少项，整数
        int i = InterceptFunction.interpeptNumber(text1);
        LogFunction.logInfo("当前选项是：" + i);
        if (total <= i) {

            AssertFunction.verifyEquals(driver, Integer.valueOf(split[3]), total);
        }
        LogFunction.logInfo("选择：" + text1);
//        验证，上页button的状态
        List<WebElement> PageUp = l.getElements(param.get("TotalChoosePageUpAndDown"));
        WebElement e = PageUp.get(0);
        String text2 = e.getText();
        String aClass = e.getAttribute("class");
        boolean disabled = !aClass.contains("disabled");
        LogFunction.logInfo(text2 + "button的状态为：" + disabled);
        AssertFunction.verifyEquals(driver, false, disabled);

//        验证，下页button的状态
        int size = PageUp.size();
        WebElement e1 = PageUp.get(size - 1);
        String text4 = e1.getText();
        String aClass1 = e1.getAttribute("class");
        boolean disabled1 = !aClass1.contains("disabled");
        LogFunction.logInfo(text4 + "button的状态为：" + disabled1);
        AssertFunction.verifyEquals(driver, true, disabled1);

//        点击，下页button
        List<WebElement> Details = l.getElements(param.get("TotalChoosePageUpAndDownDetails"));
        int size1 = Details.size();
        WebElement d = Details.get(size1 - 1);
        String text6 = d.getText();
        d.click();
        LogFunction.logInfo("点击：" + text6 + "按钮");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ee) {
            ee.printStackTrace();
        }
        //        验证，上页button的状态
        List<WebElement> PageUp2 = l.getElements(param.get("TotalChoosePageUpAndDown"));
        WebElement e2 = PageUp2.get(0);
        String text22 = e2.getText();
        String aClass2 = e2.getAttribute("class");
        boolean disabled2 = !aClass2.contains("disabled");
        LogFunction.logInfo(text22 + "button的状态为：" + disabled2);
        AssertFunction.verifyEquals(driver, true, disabled2);
//        验证，从51项到100项
        WebElement PagingTotal1 = l.getElement(param.get("Total"));
        String text11 = PagingTotal1.getText();
        String[] split1 = text11.split(" ");
        Integer total1 = Integer.valueOf(split1[1]);
        Integer total11 = Integer.valueOf(split1[3]);
        LogFunction.logInfo("从-" + total1 + "-项,到-" + total11 + "-项");
        AssertFunction.verifyEquals(driver, total1, 51);
        AssertFunction.verifyEquals(driver, total11, 100);
//         点击，上页button
        List<WebElement> Details1 = l.getElements(param.get("TotalChoosePageUpAndDownDetails"));
        WebElement dd = Details1.get(0);
        String text5 = dd.getText();
        dd.click();
        LogFunction.logInfo("点击：" + text5 + "按钮");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ee) {
            ee.printStackTrace();
        }
//        校验，上页button状态
        List<WebElement> PageUp3 = l.getElements(param.get("TotalChoosePageUpAndDown"));
        WebElement e3 = PageUp3.get(0);
        String text33 = e3.getText();
        String aClass3 = e3.getAttribute("class");
        boolean disabled3 = !aClass3.contains("disabled");
        LogFunction.logInfo(text33 + "button的状态为：" + disabled3);
        AssertFunction.verifyEquals(driver, false, disabled3);

//          选择，All
        s.selectByVisibleText("All");
        LogFunction.logInfo("选择：" + s.getFirstSelectedOption().getText().toString());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ee) {
            ee.printStackTrace();
        }
//        获取，历史告警，总数
        WebElement PagingTotal11 = l.getElement(param.get("Total"));
//        String text3 = PagingTotal11.getText();
//        String[] split111 = text3.split(" ");
//        Integer total111 = Integer.valueOf(split[5]);
//        LogFunction.logInfo("历史告警总数是：" + total111);
//        AssertFunction.verifyEquals(driver, Integer.valueOf(split111[3]), total111);
        String text3 = PagingTotal11.getText();
        String[] split111 = text3.split(" ");
        String s11 = split111[3];
        String num1 = "";
        if (s11.contains(",")) {
            String[] split22 = s11.split(",");
            for (int ii = 0; ii < split22.length; ii++) {
                num1 += split22[ii];
            }
            Integer total2 = Integer.valueOf(num1);
            LogFunction.logInfo("历史告警总数是：" + total2);
            AssertFunction.verifyEquals(driver, total2, total);

        } else {
            LogFunction.logInfo("历史告警总数是：" + s11);
            AssertFunction.verifyEquals(driver, s1, s11);
        }


        LogFunction.logInfo("-----------------历史告警-分页功能，验证通过---------------------");
    }

    //    集中告警-告警查询-历史告警-分页功能
    @Test(dataProvider = "xmldata")
    public void alarmQueryHistoryAlarmPaging2(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        获取，历史告警，总数
        WebElement PagingTotal = l.getElement(param.get("Total"));
        String text = PagingTotal.getText();
        String[] split = text.split(" ");
        String s1 = split[5];
        String num = "";
        Integer total = null;
        if (s1.contains(",")) {
            String[] split2 = s1.split(",");
            for (int i = 0; i < split2.length; i++) {
                num += split2[i];
            }
            total = Integer.valueOf(num);
            LogFunction.logInfo("历史告警总数是：" + total);
        } else {
            total = Integer.valueOf(s1);
            LogFunction.logInfo("历史告警总数是：" + s1);
        }
//        验证，是否从第1项开始，
        AssertFunction.verifyEquals(driver, Integer.valueOf(split[1]), 1);
//        获取，选择多少项，分页（50,100,200,All）
        WebElement TotalChoose = l.getElement(param.get("TotalChoose"));
        Select s = new Select(TotalChoose);
        WebElement webElement = s.getFirstSelectedOption();
        String text1 = webElement.getText();
//        获取，多少项，整数
        int i = InterceptFunction.interpeptNumber(text1);
        LogFunction.logInfo("当前选项是：" + i);
        if (total <= i) {
//        验证，上页button的状态
            List<WebElement> PageUp = l.getElements(param.get("TotalChoosePageUpAndDown"));
            WebElement e = PageUp.get(0);
            String text2 = e.getText();
            String aClass = e.getAttribute("class");
            boolean disabled = !aClass.contains("disabled");
            LogFunction.logInfo(text2 + "button的状态为：" + disabled);
            AssertFunction.verifyEquals(driver, false, disabled);

//        验证，下页button的状态
            int size = PageUp.size();
            WebElement e1 = PageUp.get(size - 1);
            String text4 = e1.getText();
            String aClass1 = e1.getAttribute("class");
            boolean disabled1 = !aClass1.contains("disabled");
            LogFunction.logInfo(text4 + "button的状态为：" + disabled1);
            AssertFunction.verifyEquals(driver, false, disabled1);

            LogFunction.logInfo("当前展示的，历史告警总数为" + split[3]);
            AssertFunction.verifyEquals(driver, Integer.valueOf(split[3]), total);
        } else {

            LogFunction.logInfo("当前页面展示告警数量为：" + text1);
//        验证，上页button的状态
            List<WebElement> PageUp = l.getElements(param.get("TotalChoosePageUpAndDown"));
            WebElement e = PageUp.get(0);
            String text2 = e.getText();
            String aClass = e.getAttribute("class");
            boolean disabled = !aClass.contains("disabled");
            LogFunction.logInfo(text2 + "button的状态为：" + disabled);
            AssertFunction.verifyEquals(driver, false, disabled);

//        验证，下页button的状态
            int size = PageUp.size();
            WebElement e1 = PageUp.get(size - 1);
            String text4 = e1.getText();
            String aClass1 = e1.getAttribute("class");
            boolean disabled1 = !aClass1.contains("disabled");
            LogFunction.logInfo(text4 + "button的状态为：" + disabled1);
            AssertFunction.verifyEquals(driver, true, disabled1);

//        点击，下页button
            List<WebElement> Details = l.getElements(param.get("TotalChoosePageUpAndDownDetails"));
            int size1 = Details.size();
            WebElement d = Details.get(size1 - 1);
            String text6 = d.getText();
            d.click();
            LogFunction.logInfo("点击：" + text6 + "按钮");
            co.sleep(2000);
            //        验证，上页button的状态
            List<WebElement> PageUp2 = l.getElements(param.get("TotalChoosePageUpAndDown"));
            WebElement e2 = PageUp2.get(0);
            String text22 = e2.getText();
            String aClass2 = e2.getAttribute("class");
            boolean disabled2 = !aClass2.contains("disabled");
            LogFunction.logInfo(text22 + "button的状态为：" + disabled2);
            AssertFunction.verifyEquals(driver, true, disabled2);
//        验证，从51项到100项
            WebElement PagingTotal1 = l.getElement(param.get("Total"));
            String text11 = PagingTotal1.getText();
            String[] split1 = text11.split(" ");
            Integer total1 = Integer.valueOf(split1[1]);
            Integer total11 = Integer.valueOf(split1[3]);
            LogFunction.logInfo("从-" + total1 + "-项,到-" + total11 + "-项");
            AssertFunction.verifyEquals(driver, total1, 51);
            LogFunction.logInfo("当前页面展示告警数量为：" + total11);
            //         点击，上页button
            List<WebElement> Details1 = l.getElements(param.get("TotalChoosePageUpAndDownDetails"));
            WebElement dd = Details1.get(0);
            String text5 = dd.getText();
            dd.click();
            LogFunction.logInfo("点击：" + text5 + "按钮");
            co.sleep(1000);
//        校验，上页button状态
            List<WebElement> PageUp3 = l.getElements(param.get("TotalChoosePageUpAndDown"));
            WebElement e3 = PageUp3.get(0);
            String text33 = e3.getText();
            String aClass3 = e3.getAttribute("class");
            boolean disabled3 = !aClass3.contains("disabled");
            LogFunction.logInfo(text33 + "button的状态为：" + disabled3);
            AssertFunction.verifyEquals(driver, false, disabled3);
        }
//          选择，All
        s.selectByVisibleText("All");
        LogFunction.logInfo("选择：" + s.getFirstSelectedOption().getText().toString());
        co.sleep(10000);
//        获取，历史告警，总数
        WebElement PagingTotal11 = l.getElement(param.get("Total"));
//        String text3 = PagingTotal11.getText();
//        String[] split111 = text3.split(" ");
//        Integer total111 = Integer.valueOf(split[5]);
//        LogFunction.logInfo("历史告警总数是：" + total111);
//        AssertFunction.verifyEquals(driver, Integer.valueOf(split111[3]), total111);
        String text3 = PagingTotal11.getText();
        String[] split111 = text3.split(" ");
        String s11 = split111[3];
        String num1 = "";
        if (s11.contains(",")) {
            String[] split22 = s11.split(",");
            for (int ii = 0; ii < split22.length; ii++) {
                num1 += split22[ii];
            }
            Integer total2 = Integer.valueOf(num1);
            LogFunction.logInfo("历史告警总数是：" + total2);
            AssertFunction.verifyEquals(driver, total2, total);

        } else {
            LogFunction.logInfo("历史告警总数是：" + s11);
            AssertFunction.verifyEquals(driver, s1, s11);
        }
        LogFunction.logInfo("-----------------历史告警-分页功能，验证通过---------------------");
    }


    //    集中告警-告警通知
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmInform(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，告警通知
        co.modelAreaDisplayAndClick(param,"AlarmInformArea","AlarmInform");
        LogFunction.logInfo("-----------------进入，告警通知页面---------------------");

    }

    //    集中告警-告警通知-接收分组
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmInformReceiveGrouping(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，接收分组
        co.modelClickButton(param, "ReceiveGrouping");
        LogFunction.logInfo("-----------------进入，接收分组页面---------------------");

    }

    //    集中告警-告警通知-接收分组-新建分组
    @Test(dataProvider = "xmldata")
    public void receiveGroupingNewGrouping(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，新建分组
        co.createButton(param);
//        录入，接收分组名称，（我是接收分组名称）
        WebElement GroupingName = l.getElement(param.get("NewGroupingName"));
        GroupingName.clear();
        GroupingName.sendKeys(param.get("NewGroupingNameValue"));
        LogFunction.logInfo("接收分组名称录入：" + param.get("NewGroupingNameValue"));
//        录入，接收分组描述，（我是接收分组描述）
        WebElement NewGroupingDescription = l.getElement(param.get("NewGroupingDescription"));
        NewGroupingDescription.clear();
        NewGroupingDescription.sendKeys(param.get("NewGroupingDescriptionValue"));
        LogFunction.logInfo("接收分组描述录入：" + param.get("NewGroupingDescriptionValue"));
//          点击，保存
        WebElement save = l.getElement(param.get("NewGroupingSave"));
        String text1 = save.getText();
        save.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "保存");
//          校验，保存提示信息，添加成功
        WebElement SavePromptMessage = l.getElement(param.get("SavePromptMessage"));
        String text2 = SavePromptMessage.getText();
        LogFunction.logInfo("提示信息为：" + text2);
        AssertFunction.verifyEquals(driver, text2, "添加成功");
//          点击，保存提示，确认
        WebElement SaveHintConfirm = l.getElement(param.get("SaveHintConfirm"));
        String text3 = SaveHintConfirm.getText();
        SaveHintConfirm.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "确认");
        LogFunction.logInfo("-----------------进入，新建分组，新建完成---------------------");

    }

    //    集中告警-告警通知-接收分组-筛选
    @Test(dataProvider = "xmldata")
    public void receiveGroupingQuery(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，筛选
        co.selectO(param);
//          录入，接收分组名称
        WebElement QueryName = l.getElement(param.get("receiveGroupingQueryName"));
        QueryName.clear();
        QueryName.sendKeys(param.get("NewGroupingNameValue"));
        LogFunction.logInfo("筛选条件分组名称录入：" + param.get("NewGroupingNameValue"));
//          点击，确定
        co.modelClickButton(param, "receiveGroupingQueryConfirm");
        co.sleep(2000);
//        勾选筛选结果
        WebElement selectChooseUser = l.getElement(param.get("commonSystemSetupSelectChoose"));
        boolean selected = selectChooseUser.isSelected();
        if (selected == false) {
            selectChooseUser.click();
        }
        LogFunction.logInfo("成功勾选筛选结果");
//          校验，筛选结果，分组名称
        WebElement GroupingName = l.getElement(param.get("QueryResultGroupingName"));
        String text2 = GroupingName.getText();
        LogFunction.logInfo("筛选结果分组名称为；" + text2);
        AssertFunction.verifyEquals(driver, text2, param.get("NewGroupingNameValue"));
//          校验，筛选结果，分组描述
        WebElement Description = l.getElement(param.get("QueryResultGroupingDescription"));
        String text3 = Description.getText();
        LogFunction.logInfo("筛选结果分组描述为；" + text3);
        AssertFunction.verifyEquals(driver, text3, param.get("NewGroupingDescriptionValue"));
        LogFunction.logInfo("-----------------进入，筛选及验证通过---------------------");
    }


    //    集中告警-告警通知-接收分组-编辑
    @Test(dataProvider = "xmldata")
    public void receiveGroupingEdit(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，编辑
        co.editButton(param);
//        WebElement Edit = l.getElement(param.get("commonEdit"));
//        String text = Edit.getText();
//        Edit.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "编辑");
//        校验，编辑分组名称，不可修改
        WebElement EditName = l.getElement(param.get("NewGroupingEditName"));
        boolean enabled = EditName.isEnabled();
        LogFunction.logInfo("编辑分组名称是否可修改：" + enabled);
        AssertFunction.verifyEquals(driver, enabled, false);
//        编辑，接收分组描述，（编辑接收分组描述）
        WebElement NewGroupingDescription = l.getElement(param.get("NewGroupingEditDescription"));
        NewGroupingDescription.clear();
        NewGroupingDescription.sendKeys(param.get("NewGroupingDescriptionEditValue"));
        LogFunction.logInfo("编辑分组描述：" + param.get("NewGroupingDescriptionEditValue"));
//          点击，保存
        WebElement save = l.getElement(param.get("EditNewGroupingSave"));
        String text1 = save.getText();
        save.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "保存");
//          校验，保存提示信息，修改成功
        WebElement SavePromptMessage = l.getElement(param.get("SavePromptMessage"));
        String text2 = SavePromptMessage.getText();
        LogFunction.logInfo("提示信息为：" + text2);
        AssertFunction.verifyEquals(driver, text2, "修改成功!");
//          点击，保存提示，确认
        WebElement SaveHintConfirm = l.getElement(param.get("SaveHintConfirm"));
        String text3 = SaveHintConfirm.getText();
        SaveHintConfirm.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "确认");
        LogFunction.logInfo("-----------------进入，编辑分组，完成---------------------");
    }

    //    集中告警-告警通知-接收分组-编辑筛选
    @Test(dataProvider = "xmldata")
    public void receiveGroupingEditQuery(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//          校验，筛选区域是否打开
        Boolean queryArea = l.getElementIsDisplay(param.get("queryArea"));
        LogFunction.logInfo("筛选区域的状态为：" + queryArea);
        if (queryArea == false) {
//        点击，筛选
            co.selectO(param);
        }
//          录入，接收分组名称
        WebElement QueryName = l.getElement(param.get("receiveGroupingQueryName"));
        QueryName.clear();
        QueryName.sendKeys(param.get("NewGroupingNameValue"));
        LogFunction.logInfo("筛选条件分组名称录入：" + param.get("NewGroupingNameValue"));
//          点击，确定
        WebElement QueryConfirm = l.getElement(param.get("receiveGroupingQueryConfirm"));
        String text1 = QueryConfirm.getText();
        QueryConfirm.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "确定");
        co.sleep(1000);
//        勾选筛选结果
        WebElement selectChooseUser = l.getElement(param.get("commonSystemSetupSelectChoose"));
        boolean selected = selectChooseUser.isSelected();
        if (selected == false) {
            selectChooseUser.click();
        }
        LogFunction.logInfo("成功勾选筛选结果");
//          校验，筛选结果，分组名称
        WebElement GroupingName = l.getElement(param.get("QueryResultGroupingName"));
        String text2 = GroupingName.getText();
        LogFunction.logInfo("筛选结果分组名称为；" + text2);
        AssertFunction.verifyEquals(driver, text2, param.get("NewGroupingNameValue"));
//          校验，筛选结果，分组描述
        WebElement Description = l.getElement(param.get("QueryResultGroupingDescription"));
        String text3 = Description.getText();
        LogFunction.logInfo("筛选结果分组描述为；" + text3);
        AssertFunction.verifyEquals(driver, text3, param.get("NewGroupingDescriptionEditValue"));
        LogFunction.logInfo("-----------------进入，筛选及验证通过---------------------");
    }

    //    集中告警-告警通知-接收分组-编辑筛选
    @Test(dataProvider = "xmldata")
    public void receiveGroupingEditQuery2(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//          校验，筛选区域是否打开
        Boolean queryArea = l.getElementIsDisplay(param.get("queryArea"));
        LogFunction.logInfo("筛选区域的状态为：" + queryArea);
        if (queryArea == false) {
//        点击，筛选
            co.selectO(param);
        }
//          录入，接收分组名称
        WebElement QueryName = l.getElement(param.get("receiveGroupingQueryName"));
        QueryName.clear();
        QueryName.sendKeys(param.get("NewGroupingNameValue"));
        LogFunction.logInfo("筛选条件分组名称录入：" + param.get("NewGroupingNameValue"));
//          点击，确定
        WebElement QueryConfirm = l.getElement(param.get("receiveGroupingQueryConfirm"));
        String text1 = QueryConfirm.getText();
        QueryConfirm.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "确定");
        co.sleep(1000);
//        勾选筛选结果
        WebElement selectChooseUser = l.getElement(param.get("commonSystemSetupSelectChoose"));
        boolean selected = selectChooseUser.isSelected();
        if (selected == false) {
            selectChooseUser.click();
        }
        LogFunction.logInfo("成功勾选筛选结果");
//          校验，筛选结果，分组名称
        WebElement GroupingName = l.getElement(param.get("QueryResultGroupingName"));
        String text2 = GroupingName.getText();
        LogFunction.logInfo("筛选结果分组名称为；" + text2);
        AssertFunction.verifyEquals(driver, text2, param.get("NewGroupingNameValue"));
//          校验，筛选结果，分组描述
        WebElement Description = l.getElement(param.get("QueryResultGroupingDescription"));
        String text3 = Description.getText();
        LogFunction.logInfo("筛选结果分组描述为；" + text3);
        LogFunction.logInfo("-----------------进入，筛选及验证通过---------------------");
    }


    //    集中告警-告警通知-接收分组-删除
    @Test(dataProvider = "xmldata")
    public void receiveGroupingDelete(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//          点击，删除
        WebElement delete = l.getElement(param.get("commonDelete"));
        String text1 = delete.getText();
        delete.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "删除");
//          获取，删除提示信息
        WebElement PromptMessage = l.getElement(param.get("PromptMessage"));
        String text2 = PromptMessage.getText();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定要删除？");
//          点击，确认
        WebElement HintConfirm = l.getElement(param.get("HintConfirm"));
        String text3 = HintConfirm.getText();
        HintConfirm.click();
        LogFunction.logInfo("点击：删除提示信息Button，" + text3);
        AssertFunction.verifyEquals(driver, text3, "确认");
////          获取，删除成功，提示信息
//        WebElement PromptMessage1 = l.getElement(param.get("PromptMessage"));
//        String text22 = PromptMessage1.getText();
//        LogFunction.logInfo("点击：" + text22);
//        AssertFunction.verifyEquals(driver, text22, "删除故障类型成功");
////          点击，确认
//        WebElement HintConfirm1 = l.getElement(param.get("HintConfirm"));
//        String text33 = HintConfirm1.getText();
//        HintConfirm1.click();
//        LogFunction.logInfo("点击：删除成功，提示信息Button，" + text33);
//        AssertFunction.verifyEquals(driver, text33, "确认");

        LogFunction.logInfo("-----------------分组成功删除---------------------");
    }

    //    集中告警-告警通知-接收分组-删除筛选
    @Test(dataProvider = "xmldata")
    public void receiveGroupingDeleteQuery(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//          校验，筛选区域是否打开
        Boolean queryArea = l.getElementIsDisplay(param.get("queryArea"));
        LogFunction.logInfo("筛选区域的状态为：" + queryArea);
        if (queryArea == false) {
//        点击，筛选
            co.selectO(param);
        }
//          录入，接收分组名称
        WebElement QueryName = l.getElement(param.get("receiveGroupingQueryName"));
        QueryName.clear();
        QueryName.sendKeys(param.get("NewGroupingNameValue"));
        LogFunction.logInfo("筛选条件分组名称录入：" + param.get("NewGroupingNameValue"));
//          点击，确定
        WebElement QueryConfirm = l.getElement(param.get("receiveGroupingQueryConfirm"));
        String text1 = QueryConfirm.getText();
        QueryConfirm.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "确定");
//         获取筛选结果，空数据
        co.selectResultIsNull(param);
        LogFunction.logInfo("-----------------进入，筛选及验证通过---------------------");
    }

    //    集中告警-告警查询-通知记录
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmSelectNotifyRecord(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，告警查询-通知记录
        co.modelClickButton(param, "NotifyRecord");
        LogFunction.logInfo("-----------------进入，通知记录页面---------------------");

    }

    //    集中告警-告警查询-通知记录-筛选
    @Test(dataProvider = "xmldata")
    public void alarmSelectNotifyRecordQuery(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//          校验，筛选区域是否打开
        Boolean queryArea = l.getElementIsDisplay(param.get("commonQueryArea"));
        LogFunction.logInfo("筛选区域的状态为：" + queryArea);
        if (queryArea == false) {
//        点击，筛选
            co.selectO(param);
        }
//        录入，通知标题（前转标题）
        WebElement NoticeTitle = l.getElement(param.get("NoticeTitle"));
        NoticeTitle.clear();
        NoticeTitle.sendKeys(param.get("commonForwardShiftingTitleValue"));
        LogFunction.logInfo("前转标题录入：" + param.get("commonForwardShiftingTitleValue"));
//          点击，确定
        WebElement QueryConfirm = l.getElement(param.get("QueryConfirm"));
        String text = QueryConfirm.getText();
        QueryConfirm.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "确定");
        co.sleep(2000);
//          获取，查询结果，通知标题
        List<WebElement> queryResultNoticeTitle = l.getElements(param.get("QueryResultNoticeTitle"));
        for (WebElement e : queryResultNoticeTitle) {
            String text1 = e.getText();
            if (!text1.equals(param.get("commonForwardShiftingTitleValue"))) {
                LogFunction.logInfo("通知标题的值为：" + text1);
                AssertFunction.assertEquals(driver, text1, param.get("commonForwardShiftingTitleValue"));
            } else {
                AssertFunction.verifyEquals(driver, text1, param.get("commonForwardShiftingTitleValue"));
            }
        }

//        勾选筛选结果
        WebElement selectChooseUser = l.getElement(param.get("commonSystemSetupSelectChoose"));
        boolean selected = selectChooseUser.isSelected();
        if (selected == false) {
            selectChooseUser.click();
        }
        LogFunction.logInfo("成功勾选筛选结果");
        LogFunction.logInfo("-----------------通知记录,筛选功能，查询及校验通过---------------------");
    }

    //    集中告警-告警查询-通知记录-查看
    @Test(dataProvider = "xmldata")
    public void alarmSelectNotifyRecordView(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，查看
        WebElement View = l.getElement(param.get("commonCentralizedAlarmView"));
        String text = View.getText();
        View.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "查看");
        co.sleep(1000);
//        校验，查看标题
        WebElement ViewDetailsTitle = l.getElement(param.get("ViewDetailsTitle"));
        String text1 = ViewDetailsTitle.getText();
        LogFunction.logInfo("查看标题为：" + text1);
        AssertFunction.assertEquals(driver, text1, "通知记录");
//        点击，取消
        WebElement ViewDetailsCancle = l.getElement(param.get("ViewDetailsCancle"));
        String text2 = ViewDetailsCancle.getText();
        ViewDetailsCancle.click();
        LogFunction.logInfo("点击" + text2);
        AssertFunction.verifyEquals(driver, text2, "取消");

    }

    //    集中告警-告警通知-通知方式
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmInformInformWay(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        点击，通知方式
        co.modelClickButton(param, "InformWay");
        LogFunction.logInfo("-----------------进入，通知方式页面---------------------");

    }

    //    集中告警-告警通知-通知方式-新建
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmInformInformWayCreate(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        点击，新建
        co.modelClickButton(param, "Create");
        co.sleep(1000);
//         录入，名称
        WebElement CreateName = l.getElement(param.get("AlarmInformInformWayCreateName"));
        CreateName.sendKeys(param.get("AlarmInformInformWayCreateNameValue"));
        LogFunction.logInfo("名称录入：" + param.get("AlarmInformInformWayCreateNameValue"));
//       点击，通知类型
        WebElement CreateType = l.getElement(param.get("CreateType"));
        CreateType.click();
        LogFunction.logInfo("点击：通知类型，选择框");
//         选择,通知类型
        WebElement CreateTypeValue = l.getElement(param.get("CreateTypeValue"));
        String text1 = CreateTypeValue.getText();
        CreateTypeValue.click();
        LogFunction.logInfo("选择通知类型：" + text1);
        AssertFunction.verifyEquals(driver, text1, "邮件");
//       点击，通知目标用户
        WebElement CreateUser = l.getElement(param.get("CreateUser"));
        CreateUser.click();
        LogFunction.logInfo("点击：目标用户，选择框");
//         选择,通知目标用户
        WebElement CreateUserValue = l.getElement(param.get("CreateUserValue"));
        String text6 = CreateUserValue.getText();
        CreateUserValue.click();
        LogFunction.logInfo("选择通知目标用户：" + text6);
//          校验是否选择启用
        WebElement CreateEnable = l.getElement(param.get("CreateEnable"));
        boolean selected = CreateEnable.isSelected();
        if (selected == false) {
            CreateEnable.click();
            LogFunction.logInfo("勾选启用");
        } else {
            LogFunction.logInfo("已勾选启用");
        }

//       点击，通知级别
        WebElement CreateLevel = l.getElement(param.get("CreateLevel"));
        CreateLevel.click();
        LogFunction.logInfo("点击：通知级别，选择框");
//         选择,通知级别
        WebElement CreateLevelValue = l.getElement(param.get("CreateLevelValue"));
        String text3 = CreateLevelValue.getText();
        CreateLevelValue.click();
        LogFunction.logInfo("选择通知级别：" + text3);
        AssertFunction.verifyEquals(driver, text3, "INFO");
//       点击，接收分组
        WebElement CreateReceivingPacket = l.getElement(param.get("CreateReceivingPacket"));
        CreateReceivingPacket.click();
        LogFunction.logInfo("点击：接收分组，选择框");
//         选择,接收分组
        WebElement CreateReceivingPacketValue = l.getElement(param.get("CreateReceivingPacketValue"));
        String text4 = CreateReceivingPacketValue.getText();
        CreateReceivingPacketValue.click();
        String grouping = InterceptFunction.intercept(text4, "我是接收分组名称");
        LogFunction.logInfo("选择接收分组：" + text4);
        AssertFunction.verifyEquals(driver, grouping, "我是接收分组名称");
//         点击，保存
        WebElement Save = l.getElement(param.get("Save"));
        String text5 = Save.getText();
        Save.click();
        LogFunction.logInfo("点击：" + text5);
        AssertFunction.verifyEquals(driver, text5, "保存");
        LogFunction.logInfo("-----------------通知方式，新建完成---------------------");
    }

    //    集中告警-告警通知-通知方式-筛选（新建）
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmInformInformWayCreateSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        校验，筛选区域是否打开
        Boolean commonQueryArea = l.getElementIsDisplay(param.get("commonQueryArea"));
        LogFunction.logInfo("筛选区域是否打开：" + commonQueryArea);
        if (commonQueryArea == false) {
//        点击，筛选
            co.selectO(param);
        }
//        名称，输入：我是新建通知方式
        WebElement QueryName = l.getElement(param.get("QueryName"));
        QueryName.sendKeys(param.get("AlarmInformInformWayCreateNameValue"));
        LogFunction.logInfo("名称，录入：" + param.get("AlarmInformInformWayCreateNameValue"));
//      点击，通知类型
        WebElement QueryNotificationType = l.getElement(param.get("QueryNotificationType"));
        QueryNotificationType.click();
        LogFunction.logInfo("点击：通知类型，选择框");
//         通知类型，选择：邮件
        WebElement QueryNotificationTypeValue = l.getElement(param.get("QueryNotificationTypeValue"));
        String text1 = QueryNotificationTypeValue.getText();
        QueryNotificationTypeValue.click();
        LogFunction.logInfo("选择通知类型：" + text1);
        AssertFunction.verifyEquals(driver, text1, "邮件");
//      通知目标用户，录入：超级管理员
//        WebElement QueryTargetUser = l.getElement(param.get("QueryTargetUser"));
//        QueryTargetUser.sendKeys(param.get("concentrateAlarmAlarmInformInformWayQueryTargetUserValue"));
//        LogFunction.logInfo("通知目标用户，录入：" + param.get("concentrateAlarmAlarmInformInformWayQueryTargetUserValue"));
//      点击，状态
        WebElement QueryStatus = l.getElement(param.get("QueryStatus"));
        QueryStatus.click();
        LogFunction.logInfo("点击：状态，选择框");
//         状态，选择：启用
        WebElement QueryStatusEnable = l.getElement(param.get("QueryStatusEnable"));
        String text2 = QueryStatusEnable.getText();
        QueryStatusEnable.click();
        LogFunction.logInfo("选择状态：" + text2);
        AssertFunction.verifyEquals(driver, text2, "启用");
//         点击，确定
        WebElement QueryConfirm = l.getElement(param.get("QueryConfirm"));
        String text3 = QueryConfirm.getText();
        QueryConfirm.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "确定");
        co.sleep(3000);
//       校验，筛选结果，名称
        WebElement ResultName = l.getElement(param.get("ResultName"));
        String text11 = ResultName.getText();
        LogFunction.logInfo("筛选结果，名称：" + text11);
        AssertFunction.verifyEquals(driver, text11, param.get("AlarmInformInformWayCreateNameValue"));
//       校验，筛选结果，通知类型
        WebElement ResultType = l.getElement(param.get("ResultType"));
        String text22 = ResultType.getText();
        LogFunction.logInfo("筛选结果，通知类型：" + text22);
        AssertFunction.verifyEquals(driver, text22, "邮件");
//       校验，筛选结果，状态
        WebElement ResultEnableLogo = l.getElement(param.get("ResultEnableLogo"));
        String text33 = ResultEnableLogo.getAttribute("title");
        LogFunction.logInfo("筛选结果，状态：" + text33);
        AssertFunction.verifyEquals(driver, text33, "已启用");
//      勾选，筛选结果
        WebElement ResultChoose = l.getElement(param.get("ResultChoose"));
        boolean selected = ResultChoose.isSelected();
        LogFunction.logInfo("筛选结果是否勾选：" + selected);
        if (selected == false) {
            ResultChoose.click();
            LogFunction.logInfo("成功勾选，筛选结果");
        }
        LogFunction.logInfo("-----------------筛选查询，并校验成功---------------------");
    }

    //    集中告警-告警通知-通知方式-编辑
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmInformInformWayEdit(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，编辑
        WebElement Create = l.getElement(param.get("Edit"));
        String text = Create.getText();
        Create.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "编辑");
        co.sleep(1000);
//         录入，名称
        WebElement CreateName = l.getElement(param.get("AlarmInformInformWayCreateName"));
        CreateName.clear();
        CreateName.sendKeys(param.get("AlarmInformInformWayEditNameValue"));
        LogFunction.logInfo("名称更改为：" + param.get("AlarmInformInformWayEditNameValue"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//       点击，通知类型
        WebElement EditType = l.getElement(param.get("EditType"));
        EditType.click();
        LogFunction.logInfo("点击：通知类型，选择框");
//         选择,通知类型,短信
        WebElement EditTypeValue = l.getElement(param.get("EditTypeValue"));
        String text1 = EditTypeValue.getText();
        EditTypeValue.click();
        LogFunction.logInfo("选择通知类型更改为：" + text1);
        AssertFunction.verifyEquals(driver, text1, "短信");
//          校验是否选择启用
        WebElement CreateEnable = l.getElement(param.get("CreateEnable"));
        boolean selected = CreateEnable.isSelected();
        if (selected == true) {
            CreateEnable.click();
            LogFunction.logInfo("未勾选启用");
        } else {
            LogFunction.logInfo("启用未被勾选");
        }
//         点击，保存
        WebElement Save = l.getElement(param.get("Save"));
        String text5 = Save.getText();
        Save.click();
        AssertFunction.verifyEquals(driver, text5, "保存");
        LogFunction.logInfo("点击：" + text5);
        LogFunction.logInfo("-----------------通知方式，编辑完成---------------------");
    }

    //    集中告警-告警通知-通知方式-编辑
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmInformInformWayEditOfHistoryAlarm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，编辑
        WebElement Create = l.getElement(param.get("Edit"));
        String text = Create.getText();
        Create.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "编辑");
        co.sleep(1000);
//         录入，名称
        WebElement CreateName = l.getElement(param.get("AlarmInformInformWayCreateName"));
        CreateName.clear();
        CreateName.sendKeys(param.get("AlarmInformInformWayEditNameValue"));
        LogFunction.logInfo("名称更改为：" + param.get("AlarmInformInformWayEditNameValue"));
//         点击，保存
        WebElement Save = l.getElement(param.get("Save"));
        String text5 = Save.getText();
        Save.click();
        AssertFunction.verifyEquals(driver, text5, "保存");
        LogFunction.logInfo("点击：" + text5);
        LogFunction.logInfo("-----------------通知方式，编辑完成---------------------");
    }

    //    集中告警-告警通知-通知方式-筛选（编辑）
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmInformInformWayEditSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        校验，筛选区域是否打开
        Boolean commonQueryArea = l.getElementIsDisplay(param.get("commonQueryArea"));
        LogFunction.logInfo("筛选区域是否打开：" + commonQueryArea);
        if (commonQueryArea == false) {
//        点击，筛选
            co.selectO(param);
//            WebElement Create = l.getElement(param.get("Query"));
//            String text = Create.getText();
//            Create.click();
//            LogFunction.logInfo("点击：" + text);
//            AssertFunction.verifyEquals(driver, text, "筛选");
        }
//        名称，输入：我是编辑通知方式
        WebElement QueryName = l.getElement(param.get("QueryName"));
        QueryName.clear();
        QueryName.sendKeys(param.get("AlarmInformInformWayEditNameValue"));
        LogFunction.logInfo("名称，录入：" + param.get("AlarmInformInformWayEditNameValue"));
//      点击，通知类型
        WebElement QueryNotificationType = l.getElement(param.get("QueryNotificationType"));
        QueryNotificationType.click();
        LogFunction.logInfo("点击：通知类型，选择框");
//         通知类型，选择：短信
        WebElement QueryNotificationTypeValue = l.getElement(param.get("QueryNotificationTypeValue"));
        String text1 = QueryNotificationTypeValue.getText();
        QueryNotificationTypeValue.click();
        LogFunction.logInfo("选择通知类型：" + text1);
        AssertFunction.verifyEquals(driver, text1, "短信");
//          点击，空白
        WebElement systemTitleblank = l.getElement(param.get("systemTitleblank"));
        systemTitleblank.click();
        LogFunction.logInfo("点击：空白");
//         点击，确定
        WebElement QueryConfirm = l.getElement(param.get("QueryConfirm"));
        String text3 = QueryConfirm.getText();
        QueryConfirm.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "确定");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//       校验，筛选结果，为空
        co.selectResultIsNull(param, "ResultEmpty");
//      点击，状态
        WebElement QueryStatus = l.getElement(param.get("QueryStatus"));
        QueryStatus.click();
        LogFunction.logInfo("点击：状态，选择框");
//         状态，选择：停用
        WebElement QueryStatusDisable = l.getElement(param.get("QueryStatusDisable"));
        String text2 = QueryStatusDisable.getText();
        QueryStatusDisable.click();
        LogFunction.logInfo("选择状态：" + text2);
        AssertFunction.verifyEquals(driver, text2, "停用");
        //         点击，确定
        WebElement QueryConfirm1 = l.getElement(param.get("QueryConfirm"));
        String text33 = QueryConfirm1.getText();
        QueryConfirm1.click();
        LogFunction.logInfo("点击：" + text33);
        AssertFunction.verifyEquals(driver, text33, "确定");
        co.sleep(1000);
//       校验，筛选结果，名称
        WebElement ResultName = l.getElement(param.get("ResultName"));
        String text11 = ResultName.getText();
        LogFunction.logInfo("筛选结果，名称：" + text11);
        AssertFunction.verifyEquals(driver, text11, param.get("AlarmInformInformWayEditNameValue"));
//       校验，筛选结果，通知类型
        WebElement ResultType = l.getElement(param.get("ResultType"));
        String text22 = ResultType.getText();
        LogFunction.logInfo("筛选结果，通知类型：" + text22);
        AssertFunction.verifyEquals(driver, text22, "短信");
//       校验，筛选结果，状态
        WebElement ResultEnableLogo = l.getElement(param.get("ResultEnableLogo"));
        String text333 = ResultEnableLogo.getAttribute("title");
        LogFunction.logInfo("筛选结果，状态：" + text333);
        AssertFunction.verifyEquals(driver, text333, "已禁用");
////      勾选，筛选结果
//        WebElement ResultChoose = l.getElement(param.get("ResultChoose"));
//        boolean selected = ResultChoose.isSelected();
//        LogFunction.logInfo("筛选结果是否勾选：" + selected);
//        if (selected == false) {
//            ResultChoose.click();
//            LogFunction.logInfo("成功勾选，筛选结果");
//        }
        LogFunction.logInfo("-----------------筛选查询，并校验成功---------------------");
    }


    //    集中告警-告警通知-通知方式-筛选（编辑）
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmInformInformWayEditSelect1(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        校验，筛选区域是否打开
        Boolean commonQueryArea = l.getElementIsDisplay(param.get("commonQueryArea"));
        LogFunction.logInfo("筛选区域是否打开：" + commonQueryArea);
        if (commonQueryArea == false) {
//        点击，筛选
            co.selectO(param);
//            WebElement Create = l.getElement(param.get("Query"));
//            String text = Create.getText();
//            Create.click();
//            LogFunction.logInfo("点击：" + text);
//            AssertFunction.verifyEquals(driver, text, "筛选");
        }
//        名称，输入：我是编辑通知方式
        WebElement QueryName = l.getElement(param.get("QueryName"));
        QueryName.clear();
        QueryName.sendKeys(param.get("AlarmInformInformWayEditNameValue"));
        LogFunction.logInfo("名称，录入：" + param.get("AlarmInformInformWayEditNameValue"));

//         点击，确定
        WebElement QueryConfirm = l.getElement(param.get("QueryConfirm"));
        String text3 = QueryConfirm.getText();
        QueryConfirm.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "确定");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//      勾选，筛选结果
        WebElement ResultChoose = l.getElement(param.get("ResultChoose"));
        ResultChoose.click();
        LogFunction.logInfo("成功勾选，筛选结果");
        LogFunction.logInfo("-----------------编辑，筛选查询，勾选结果成功---------------------");
    }


    //    集中告警-告警通知-通知方式-启用
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmInformInformWayEnable(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，筛选，清空
        WebElement Clear = l.getElement(param.get("QueryClear"));
        String text = Clear.getText();
        Clear.click();
        LogFunction.logInfo("点击，筛选：" + text);
        AssertFunction.verifyEquals(driver, text, "清空");
//        名称，输入：我是编辑通知方式
        WebElement QueryName = l.getElement(param.get("QueryName"));
        QueryName.clear();
        QueryName.sendKeys(param.get("AlarmInformInformWayEditNameValue"));
        LogFunction.logInfo("名称，录入：" + param.get("AlarmInformInformWayEditNameValue"));
//         点击，确定
        WebElement QueryConfirm1 = l.getElement(param.get("QueryConfirm"));
        String text33 = QueryConfirm1.getText();
        QueryConfirm1.click();
        LogFunction.logInfo("点击：" + text33);
        AssertFunction.verifyEquals(driver, text33, "确定");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //      勾选，结果
        WebElement ResultChoose1 = l.getElement(param.get("ResultChoose"));
        boolean selected1 = ResultChoose1.isSelected();
        LogFunction.logInfo("筛选结果是否勾选：" + selected1);
        if (selected1 == false) {
            ResultChoose1.click();
            LogFunction.logInfo("成功勾选，筛选结果");
        }
//         状态，选择：启用
        WebElement Enable = l.getElement(param.get("Enable"));
        String text2 = Enable.getText();
        Enable.click();
        LogFunction.logInfo("选择状态：" + text2);
        AssertFunction.verifyEquals(driver, text2, "启用");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//      勾选，结果
        WebElement ResultChoose = l.getElement(param.get("ResultChoose"));
        boolean selected = ResultChoose.isSelected();
        LogFunction.logInfo("筛选结果是否勾选：" + selected);
        if (selected == false) {
            ResultChoose.click();
            LogFunction.logInfo("成功勾选，筛选结果");
        }
//       校验，筛选结果，状态
        WebElement ResultEnableLogo = l.getElement(param.get("ResultEnableLogo"));
        String text333 = ResultEnableLogo.getAttribute("title");
        LogFunction.logInfo("筛选结果，状态：" + text333);
        AssertFunction.verifyEquals(driver, text333, "已启用");
        LogFunction.logInfo("-----------------启用成功---------------------");
    }

    //    集中告警-告警通知-通知方式-停用
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmInformInformWayDisable(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

        //      勾选，结果
//        WebElement ResultChoose = l.getElement(param.get("ResultChoose"));
//        boolean selected = ResultChoose.isSelected();
//        LogFunction.logInfo("筛选结果是否勾选：" + selected);
//        if (selected == false) {
//            ResultChoose.click();
//            LogFunction.logInfo("成功勾选，筛选结果");
//        }
//         状态，选择：停用
        WebElement commonBlockUp = l.getElement(param.get("commonBlockUp"));
        String text2 = commonBlockUp.getText();
        AssertFunction.verifyEquals(driver, text2, "停用");
        commonBlockUp.click();
        LogFunction.logInfo("点击：" + text2);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//       校验，筛选结果，状态
        WebElement ResultEnableLogo = l.getElement(param.get("ResultEnableLogo"));
        String text333 = ResultEnableLogo.getAttribute("title");
        LogFunction.logInfo("筛选结果，状态：" + text333);
        AssertFunction.verifyEquals(driver, text333, "已禁用");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//      勾选，结果
        WebElement ResultChoose1 = l.getElement(param.get("ResultChoose"));
        boolean selected1 = ResultChoose1.isSelected();
        LogFunction.logInfo("筛选结果是否勾选：" + selected1);
        if (selected1 == false) {
            ResultChoose1.click();
            LogFunction.logInfo("成功勾选，筛选结果");
        }
        LogFunction.logInfo("-----------------停用成功---------------------");
    }

    //    集中告警-告警通知-通知方式-关联告警筛选
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmInformInformWayCreateRelatedAlarm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//         点击，关联告警筛选
        WebElement RelatedAlarm = l.getElement(param.get("RelatedAlarm"));
        String text = RelatedAlarm.getText();
        RelatedAlarm.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "关联告警筛选");
//         点击，告警筛选策略Button
        WebElement RelatedAlarmFaultRule = l.getElement(param.get("RelatedAlarmFaultRule"));
        RelatedAlarmFaultRule.click();
        LogFunction.logInfo("点击：告警筛选策略");
//         选择，告警筛选策略（值）
        WebElement RelatedAlarmFaultRuleValue = l.getElement(param.get("RelatedAlarmFaultRuleValue"));
        String text1 = RelatedAlarmFaultRuleValue.getText();
        RelatedAlarmFaultRuleValue.click();
        LogFunction.logInfo("告警筛选策略选择：" + text1);
//        AssertFunction.verifyEquals(driver, text1, "关联告警筛选");
//         点击，确定
        WebElement RelatedAlarmConfirm = l.getElement(param.get("RelatedAlarmConfirm"));
        String text2 = RelatedAlarmConfirm.getText();
        RelatedAlarmConfirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        校验，关联告警筛选
        WebElement ResultRelatedAlarmSelect = l.getElement(param.get("ResultRelatedAlarmSelect"));
        String text333 = ResultRelatedAlarmSelect.getText();
        String autoFault = InterceptFunction.intercept(text333, "自动故障");
        LogFunction.logInfo("筛选结果，状态：" + text333);
        AssertFunction.verifyEquals(driver, autoFault, "自动故障");
//      勾选，结果
        WebElement ResultChoose1 = l.getElement(param.get("ResultChoose"));
        boolean selected1 = ResultChoose1.isSelected();
        LogFunction.logInfo("筛选结果是否勾选：" + selected1);
        if (selected1 == false) {
            ResultChoose1.click();
            LogFunction.logInfo("成功勾选，筛选结果");
        }
        LogFunction.logInfo("-----------------关联告警筛选设置成功---------------------");
    }

    //    集中告警-告警通知-通知方式-删除
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmInformInformWayDelete(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//         点击，删除
        WebElement Delete = l.getElement(param.get("Delete"));
        String text = Delete.getText();
        Delete.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "删除");
//        校验，删除提示
        WebElement DeleteHint = l.getElement(param.get("DeleteHint"));
        String text2 = DeleteHint.getText();
        LogFunction.logInfo("提示信息为：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定要删除？");
//         点击，确认
        WebElement DeleteConfirm = l.getElement(param.get("DeleteConfirm"));
        String text1 = DeleteConfirm.getText();
        DeleteConfirm.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "确认");
//       校验，筛选结果，为空
        co.selectResultIsNull(param);
        LogFunction.logInfo("-----------------通知方式，删除成功---------------------");
    }

    //    集中告警-告警通知-通知方式-阈值规则
    @Test(dataProvider = "xmldata")
    public void alarmConfigThresholdStrategy(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//         点击，阈值策略
        co.modelClickButton(param, "thresholdStrategy");
        LogFunction.logInfo("-----------------进入阈值策略页面---------------------");
    }

    //    集中告警-告警通知-通知方式-阈值规则-新建
    @Test(dataProvider = "xmldata")
    public void thresholdStrategyCreate(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//         点击，新建
        co.createButton(param);
//      录入，名称
        WebElement createName = l.getElement(param.get("CreateName"));
        createName.clear();
        createName.sendKeys(param.get("ThresholdStrategyCreateNameValue"));
        LogFunction.logInfo("阈值策略，名称录入：" + param.get("ThresholdStrategyCreateNameValue"));
//       选择，启动
        WebElement createEnable = l.getElement(param.get("CreateEnable"));
        boolean selected = createEnable.isSelected();
        if (selected == false) {
            createEnable.click();
            LogFunction.logInfo("勾选，启动");
        } else {
            LogFunction.logInfo("启动，已勾选");
        }
//       点击，数据域
        WebElement createDatadomain = l.getElement(param.get("CreateDatadomain"));
        createDatadomain.click();
        LogFunction.logInfo("点击，数据域");
//        选择，数据域（值）
        WebElement createDataDomainValue = l.getElement(param.get("CreateDataDomainValue"));
        String text = createDataDomainValue.getText();
        AssertFunction.verifyEquals(driver, text, "rootDomain");
        createDataDomainValue.click();
        LogFunction.logInfo("数据域，选择：" + text);
//       点击，节点类型
        WebElement CreateNodeType = l.getElement(param.get("CreateNodeType"));
        CreateNodeType.click();
        LogFunction.logInfo("点击，节点类型");
//        选择，节点类型（值）
        WebElement CreateNodeTypeValue = l.getElement(param.get("CreateNodeTypeValue"));
        String text1 = CreateNodeTypeValue.getText();
        AssertFunction.verifyEquals(driver, text1, "操作系统");
        CreateNodeTypeValue.click();
        LogFunction.logInfo("节点类型，选择：" + text1);
//       点击，KPI类型选择
        WebElement CreateKPITypeSelection = l.getElement(param.get("CreateKPITypeSelection"));
        CreateKPITypeSelection.click();
        LogFunction.logInfo("点击，KPI类型选择");
//        选择，KPI类型选择（值）
        WebElement CreateKPITypeSelectionValue = l.getElement(param.get("CreateKPITypeSelectionValue"));
        String text2 = CreateKPITypeSelectionValue.getText();
        AssertFunction.verifyEquals(driver, text2, "info告警数量");
        CreateKPITypeSelectionValue.click();
        LogFunction.logInfo("KPI类型选择，选择：" + text2);
//       点击，告警类型选择
        WebElement CreateAlarmTypeSelection = l.getElement(param.get("CreateAlarmTypeSelection"));
        CreateAlarmTypeSelection.click();
        LogFunction.logInfo("点击，告警类型选择");
//        选择，告警类型选择（值）
        WebElement CreateAlarmTypeSelectionValue = l.getElement(param.get("CreateAlarmTypeSelectionValue"));
        String text3 = CreateAlarmTypeSelectionValue.getText();
        AssertFunction.verifyEquals(driver, text3, "CommonCi_infoCount_Alert");
        CreateAlarmTypeSelectionValue.click();
        LogFunction.logInfo("告警类型选择，选择：" + text3);
//       选择，告警级别(INFO)
        WebElement CreateAlarmLevel = l.getElement(param.get("CreateAlarmLevel"));
        Select s = new Select(CreateAlarmLevel);
        s.selectByVisibleText("INFO");
        String text4 = s.getFirstSelectedOption().getText();
        LogFunction.logInfo("告警级别选择：" + text4);
        AssertFunction.verifyEquals(driver, text4, "INFO");
//       选择，阈值条件
        WebElement CreateThresholdRuleGreaterThan = l.getElement(param.get("CreateThresholdRuleGreaterThan"));
        Select s1 = new Select(CreateThresholdRuleGreaterThan);
        s1.selectByVisibleText("大于");
        String text5 = s1.getFirstSelectedOption().getText();
        LogFunction.logInfo("阈值条件选择：" + text5);
        AssertFunction.verifyEquals(driver, text5, "大于");
//       选择，阈值条件
        WebElement CreateThresholdRuleFixedValue = l.getElement(param.get("CreateThresholdRuleFixedValue"));
        Select s2 = new Select(CreateThresholdRuleFixedValue);
        s2.selectByVisibleText("固定值");
        String text6 = s2.getFirstSelectedOption().getText();
        LogFunction.logInfo("阈值条件选择：" + text6);
        AssertFunction.verifyEquals(driver, text6, "固定值");
//       录入，阈值条件
        WebElement FixedValueValue = l.getElement(param.get("CreateThresholdRuleFixedValueInput"));
        FixedValueValue.clear();
        FixedValueValue.sendKeys(param.get("CreateThresholdRuleFixedValueInputValue"));
        LogFunction.logInfo("阈值条件,大于固定值：" + param.get("CreateThresholdRuleFixedValueInputValue"));
//      点击，保存
        WebElement CreateSave = l.getElement(param.get("CreateSave"));
        String text7 = CreateSave.getText();
        AssertFunction.verifyEquals(driver, text7, "保存");
        CreateSave.click();
        LogFunction.logInfo("点击：" + text7);
//      保存，确认
        co.alarmHintAndConfirm(param, "添加阈值规则成功");

        LogFunction.logInfo("-----------------阈值策略，新建完成---------------------");
    }

    //    集中告警-告警通知-通知方式-阈值规则-新建筛选
    @Test(dataProvider = "xmldata")
    public void thresholdStrategyCreateSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//         点击，筛选
        Boolean SelectArea = l.getElementIsDisplay(param.get("SelectArea"));
        LogFunction.logInfo("筛选区域的状态为：" + SelectArea);
        if (SelectArea == false) {
            co.selectO(param);
//            WebElement Select1 = l.getElement(param.get("commonSelect"));
//            String text = Select1.getText();
//            AssertFunction.verifyEquals(driver, text, "筛选");
//            Select1.click();
//            LogFunction.logInfo("打开：" + text);
        }
//       点击，数据域
        WebElement createDatadomain = l.getElement(param.get("SelectDataDomain"));
        createDatadomain.click();
        LogFunction.logInfo("点击，数据域");
//        选择，数据域（值）
        WebElement createDataDomainValue = l.getElement(param.get("SelectDataDomainValue"));
        String text = createDataDomainValue.getText();
        AssertFunction.verifyEquals(driver, text, "rootDomain");
        createDataDomainValue.click();
        LogFunction.logInfo("数据域，选择：" + text);
//       点击，节点类型
        WebElement CreateNodeType = l.getElement(param.get("SelectNodeType"));
        CreateNodeType.click();
        LogFunction.logInfo("点击，节点类型");
//        选择，节点类型（值）
        WebElement CreateNodeTypeValue = l.getElement(param.get("SelectNodeTypeValue"));
        String text1 = CreateNodeTypeValue.getText();
        AssertFunction.verifyEquals(driver, text1, "操作系统");
        CreateNodeTypeValue.click();
        LogFunction.logInfo("节点类型，选择：" + text1);
//       点击，KPI类型选择
        WebElement CreateKPITypeSelection = l.getElement(param.get("SelectKPI"));
        CreateKPITypeSelection.click();
        LogFunction.logInfo("点击，KPI类型选择");
//        选择，KPI类型选择（值）
        WebElement CreateKPITypeSelectionValue = l.getElement(param.get("SelectKPIValue"));
        String text2 = CreateKPITypeSelectionValue.getText();
        AssertFunction.verifyEquals(driver, text2, "info告警数量");
        CreateKPITypeSelectionValue.click();
        LogFunction.logInfo("KPI类型选择，选择：" + text2);
//        选择，确定
        WebElement SelectConfirm = l.getElement(param.get("SelectConfirm"));
        String text3 = SelectConfirm.getText();
        AssertFunction.verifyEquals(driver, text3, "确定");
        SelectConfirm.click();
        LogFunction.logInfo("KPI类型选择，选择：" + text3);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//      验证,筛选结果,名称
        WebElement SelectResultName = l.getElement(param.get("SelectResultName"));
        String text11 = SelectResultName.getText();
        LogFunction.logInfo("筛选结果,名称：" + text11);
        AssertFunction.verifyEquals(driver, text11, param.get("ThresholdStrategyCreateNameValue"));

//      验证,筛选结果,节点类型
        WebElement SelectResultNodeType = l.getElement(param.get("SelectResultNodeType"));
        String text12 = SelectResultNodeType.getText();
        LogFunction.logInfo("筛选结果,节点类型：" + text12);
        AssertFunction.verifyEquals(driver, text12, "操作系统");

//      验证,筛选结果,域
        WebElement SelectResultDomain = l.getElement(param.get("SelectResultDomain"));
        String text13 = SelectResultDomain.getText();
        LogFunction.logInfo("筛选结果,域：" + text13);
        AssertFunction.verifyEquals(driver, text13, "rootDomain");

//      验证,筛选结果,KPI
        WebElement SelectResultKPI = l.getElement(param.get("SelectResultKPI"));
        String text14 = SelectResultKPI.getText();
        LogFunction.logInfo("筛选结果,KPI：" + text14);
        AssertFunction.verifyEquals(driver, text14, "info告警数量");

//      验证,筛选结果,Alert
        WebElement SelectResultAlert = l.getElement(param.get("SelectResultAlert"));
        String text15 = SelectResultAlert.getText();
        LogFunction.logInfo("筛选结果,Alert：" + text15);
        AssertFunction.verifyEquals(driver, text15, "CommonCi_infoCount_Alert");

//      验证,筛选结果,级别
        WebElement SelectResultLevel = l.getElement(param.get("SelectResultLevel"));
        String text16 = SelectResultLevel.getText();
        LogFunction.logInfo("筛选结果,级别：" + text16);
        AssertFunction.verifyEquals(driver, text16, "INFO");

//      验证,筛选结果,启用
        WebElement SelectResultEnable = l.getElement(param.get("SelectResultEnable"));
        String text17 = SelectResultEnable.getAttribute("title");
        LogFunction.logInfo("筛选结果,是否启用：" + text17);
        AssertFunction.verifyEquals(driver, text17, "已启用");


//      勾选，筛选结果
        co.chooseSelectResult(param);

        LogFunction.logInfo("-----------------阈值策略，新建，筛选及校验完成---------------------");
    }

    //    集中告警-告警通知-通知方式-阈值规则-编辑
    @Test(dataProvider = "xmldata")
    public void thresholdStrategyEdit(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//         点击，新建
        co.editButton(param);
//      录入，名称
        WebElement createName = l.getElement(param.get("CreateName"));
        createName.clear();
        createName.sendKeys(param.get("ThresholdStrategyEditNameValue"));
        LogFunction.logInfo("阈值策略，名称录入：" + param.get("ThresholdStrategyEditNameValue"));
//       选择，启动
        WebElement createEnable = l.getElement(param.get("CreateEnable"));
        boolean selected = createEnable.isSelected();
        if (selected == true) {
            createEnable.click();
            LogFunction.logInfo("取消勾选，启动");
        } else {
            LogFunction.logInfo("启动，未勾选");
        }
//       选择，告警级别(CRITICAL)
        WebElement CreateAlarmLevel = l.getElement(param.get("CreateAlarmLevel"));
        Select s = new Select(CreateAlarmLevel);
        s.selectByVisibleText("CRITICAL");
        String text4 = s.getFirstSelectedOption().getText();
        LogFunction.logInfo("告警级别选择：" + text4);
        AssertFunction.verifyEquals(driver, text4, "CRITICAL");
//       选择，阈值条件
        WebElement CreateThresholdRuleGreaterThan = l.getElement(param.get("CreateThresholdRuleGreaterThan"));
        Select s1 = new Select(CreateThresholdRuleGreaterThan);
        s1.selectByVisibleText("小于");
        String text5 = s1.getFirstSelectedOption().getText();
        LogFunction.logInfo("阈值条件选择：" + text5);
        AssertFunction.verifyEquals(driver, text5, "小于");
//      点击，保存
        WebElement CreateSave = l.getElement(param.get("CreateSave"));
        String text7 = CreateSave.getText();
        AssertFunction.verifyEquals(driver, text7, "保存");
        CreateSave.click();
        LogFunction.logInfo("点击：" + text7);
//      保存，确认
        co.alarmHintAndConfirm(param, "修改阈值规则成功");

        LogFunction.logInfo("-----------------阈值策略，编辑完成---------------------");
    }

    //    集中告警-告警通知-通知方式-阈值规则-编辑筛选
    @Test(dataProvider = "xmldata")
    public void thresholdStrategyEditSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//         点击，筛选
        Boolean SelectArea = l.getElementIsDisplay(param.get("SelectArea"));
        LogFunction.logInfo("筛选区域的状态为：" + SelectArea);
        if (SelectArea == false) {
            co.selectO(param);
//            WebElement Select1 = l.getElement(param.get("commonSelect"));
//            String text = Select1.getText();
//            AssertFunction.verifyEquals(driver, text, "筛选");
//            Select1.click();
//            LogFunction.logInfo("打开：" + text);
        }
//       点击，数据域
        WebElement createDatadomain = l.getElement(param.get("SelectDataDomain"));
        createDatadomain.click();
        LogFunction.logInfo("点击，数据域");
//        选择，数据域（值）
        WebElement createDataDomainValue = l.getElement(param.get("SelectDataDomainValue"));
        String text = createDataDomainValue.getText();
        AssertFunction.verifyEquals(driver, text, "rootDomain");
        createDataDomainValue.click();
        LogFunction.logInfo("数据域，选择：" + text);
//       点击，节点类型
        WebElement CreateNodeType = l.getElement(param.get("SelectNodeType"));
        CreateNodeType.click();
        LogFunction.logInfo("点击，节点类型");
//        选择，节点类型（值）
        WebElement CreateNodeTypeValue = l.getElement(param.get("SelectNodeTypeValue"));
        String text1 = CreateNodeTypeValue.getText();
        AssertFunction.verifyEquals(driver, text1, "操作系统");
        CreateNodeTypeValue.click();
        LogFunction.logInfo("节点类型，选择：" + text1);
//       点击，KPI类型选择
        WebElement CreateKPITypeSelection = l.getElement(param.get("SelectKPI"));
        CreateKPITypeSelection.click();
        LogFunction.logInfo("点击，KPI类型选择");
//        选择，KPI类型选择（值）
        WebElement CreateKPITypeSelectionValue = l.getElement(param.get("SelectKPIValue"));
        String text2 = CreateKPITypeSelectionValue.getText();
        AssertFunction.verifyEquals(driver, text2, "info告警数量");
        CreateKPITypeSelectionValue.click();
        LogFunction.logInfo("KPI类型选择，选择：" + text2);
//        选择，确定
        WebElement SelectConfirm = l.getElement(param.get("SelectConfirm"));
        String text3 = SelectConfirm.getText();
        AssertFunction.verifyEquals(driver, text3, "确定");
        SelectConfirm.click();
        LogFunction.logInfo("KPI类型选择，选择：" + text3);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//      验证,筛选结果,名称
        WebElement SelectResultName = l.getElement(param.get("SelectResultName"));
        String text11 = SelectResultName.getText();
        LogFunction.logInfo("筛选结果,名称：" + text11);
        AssertFunction.verifyEquals(driver, text11, param.get("ThresholdStrategyEditNameValue"));

//      验证,筛选结果,节点类型
        WebElement SelectResultNodeType = l.getElement(param.get("SelectResultNodeType"));
        String text12 = SelectResultNodeType.getText();
        LogFunction.logInfo("筛选结果,节点类型：" + text12);
        AssertFunction.verifyEquals(driver, text12, "操作系统");

//      验证,筛选结果,域
        WebElement SelectResultDomain = l.getElement(param.get("SelectResultDomain"));
        String text13 = SelectResultDomain.getText();
        LogFunction.logInfo("筛选结果,域：" + text13);
        AssertFunction.verifyEquals(driver, text13, "rootDomain");

//      验证,筛选结果,KPI
        WebElement SelectResultKPI = l.getElement(param.get("SelectResultKPI"));
        String text14 = SelectResultKPI.getText();
        LogFunction.logInfo("筛选结果,KPI：" + text14);
        AssertFunction.verifyEquals(driver, text14, "info告警数量");

//      验证,筛选结果,Alert
        WebElement SelectResultAlert = l.getElement(param.get("SelectResultAlert"));
        String text15 = SelectResultAlert.getText();
        LogFunction.logInfo("筛选结果,Alert：" + text15);
        AssertFunction.verifyEquals(driver, text15, "CommonCi_infoCount_Alert");

//      验证,筛选结果,级别
        WebElement SelectResultLevel = l.getElement(param.get("SelectResultLevel"));
        String text16 = SelectResultLevel.getText();
        LogFunction.logInfo("筛选结果,级别：" + text16);
        AssertFunction.verifyEquals(driver, text16, "CRITICAL");

//      验证,筛选结果,启用
        WebElement SelectResultEnable = l.getElement(param.get("SelectResultEnable"));
        String text17 = SelectResultEnable.getAttribute("title");
        LogFunction.logInfo("筛选结果,是否启用：" + text17);
        AssertFunction.verifyEquals(driver, text17, "已禁用");


//      勾选，筛选结果
        co.chooseSelectResult(param);

        LogFunction.logInfo("-----------------阈值策略，编辑，筛选及校验完成---------------------");
    }

    //    集中告警-告警通知-通知方式-阈值规则-删除
    @Test(dataProvider = "xmldata")
    public void thresholdStrategyDelete(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        点击，删除
        co.deleteButton(param);
//        校验提示,信息,点击确认
        co.alarmHintAndConfirm(param, "确定要删除？");
//        二级校验提示,信息,点击确认
        co.alarmHintAndConfirm(param, "删除成功!");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//         点击，筛选
        Boolean SelectArea = l.getElementIsDisplay(param.get("SelectArea"));
        LogFunction.logInfo("筛选区域的状态为：" + SelectArea);
        if (SelectArea == false) {
            co.selectO(param);
//            WebElement Select1 = l.getElement(param.get("commonSelect"));
//            String text = Select1.getText();
//            AssertFunction.verifyEquals(driver, text, "筛选");
//            Select1.click();
//            LogFunction.logInfo("打开：" + text);
        }
//        点击，筛选-清空button
        co.selectClearButton(param);
//       点击，数据域
        WebElement createDatadomain = l.getElement(param.get("SelectDataDomain"));
        createDatadomain.click();
        LogFunction.logInfo("点击，数据域");
//        选择，数据域（值）
        WebElement createDataDomainValue = l.getElement(param.get("SelectDataDomainValue"));
        String text = createDataDomainValue.getText();
        AssertFunction.verifyEquals(driver, text, "rootDomain");
        createDataDomainValue.click();
        LogFunction.logInfo("数据域，选择：" + text);
//       点击，节点类型
        WebElement CreateNodeType = l.getElement(param.get("SelectNodeType"));
        CreateNodeType.click();
        LogFunction.logInfo("点击，节点类型");
//        选择，节点类型（值）
        WebElement CreateNodeTypeValue = l.getElement(param.get("SelectNodeTypeValue"));
        String text1 = CreateNodeTypeValue.getText();
        AssertFunction.verifyEquals(driver, text1, "操作系统");
        CreateNodeTypeValue.click();
        LogFunction.logInfo("节点类型，选择：" + text1);
//       点击，KPI类型选择
        WebElement CreateKPITypeSelection = l.getElement(param.get("SelectKPI"));
        CreateKPITypeSelection.click();
        LogFunction.logInfo("点击，KPI类型选择");
//        选择，KPI类型选择（值）
        WebElement CreateKPITypeSelectionValue = l.getElement(param.get("SelectKPIValue"));
        String text2 = CreateKPITypeSelectionValue.getText();
        AssertFunction.verifyEquals(driver, text2, "info告警数量");
        CreateKPITypeSelectionValue.click();
        LogFunction.logInfo("KPI类型选择，选择：" + text2);
//        点击，确定
        WebElement SelectConfirm = l.getElement(param.get("SelectConfirm"));
        String text3 = SelectConfirm.getText();
        AssertFunction.verifyEquals(driver, text3, "确定");
        SelectConfirm.click();
        LogFunction.logInfo("点击：" + text3);

//      验证,筛选结果,是否为空
        co.selectResultIsNull(param);

        LogFunction.logInfo("-----------------阈值策略，删除成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警恢复策略-新建第1部分，基础设置
    @Test(dataProvider = "xmldata")
    public void alarmRecoveryRulesCreateBasicSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//          点击，新建
        WebElement commonCreate = l.getElement(param.get("commonCreate"));
        String text1 = commonCreate.getText();
        commonCreate.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "新建", "----是否点击新建----");
//          录入，规则名称
        WebElement rulesName = l.getElement(param.get("rulesName"));
        rulesName.sendKeys(param.get("alarmRecoveryRulesNameValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("alarmRecoveryRulesNameValue"));
//          选择，优先级，高
        WebElement priority = l.getElement(param.get("priority"));
        Priority = priority.getText();
        priority.click();
        LogFunction.logInfo("勾选优先级" + Priority);
//        点击，基础设置，下一步
        WebElement basicsNextStep = l.getElement(param.get("basicsNextStep"));
        String text3 = basicsNextStep.getText();
        basicsNextStep.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警恢复策略，第一步，基础设置录入完成---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警恢复策略-新建第2部分，规则条件设置
    @Test(dataProvider = "xmldata")
    public void alarmRecoveryRulesCreateRulesConditionSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//         点击，域
        WebElement domain = l.getElement(param.get("domain"));
        domain.click();
        LogFunction.logInfo("点击：域");
//        选择，域：rootDomain
        WebElement chooseDomain = l.getElement(param.get("chooseDomain"));
        Domain = chooseDomain.getText();
        chooseDomain.click();
        LogFunction.logInfo("选择域：" + Domain);
        AssertFunction.verifyEquals(driver, Domain, "rootDomain", "----验证选择的域是否为：rootDomain----");
//          点击，类型
        WebElement type = l.getElement(param.get("type"));
        type.click();
        LogFunction.logInfo("点击：类型");
//        选择,类型:操作系统
        WebElement chooseType = l.getElement(param.get("chooseType"));
        Oracal = chooseType.getText();
        chooseType.click();
        LogFunction.logInfo("选择类型：" + Oracal);
        AssertFunction.verifyEquals(driver, Oracal, "Oracle", "----验证选择的类型是否为；Oracle----");
//        点击，告警类型选择
//        WebElement alarmType = l.getElement(param.get("alarmType"));
//        alarmType.click();
//        LogFunction.logInfo("点击：告警类型选择");
//        选择,告警类型选择:Oracle_System_Alert
//        WebElement chooseAlarmType = l.getElement(param.get("chooseAlarmType"));
//        String text6 = chooseAlarmType.getText();
//        chooseAlarmType.click();
//        LogFunction.logInfo("选择告警类型：" + text6);
//        AssertFunction.verifyEquals(driver, text6, "Oracle_System_Alert", "----验证选择的告警类型是否为；Oracle_System_Alert----");
//       点击，规则条件设置，下一步
        WebElement rulesConditionConfigNextStep = l.getElement(param.get("rulesConditionConfigNextStep"));
        String text7 = rulesConditionConfigNextStep.getText();
        rulesConditionConfigNextStep.click();
        LogFunction.logInfo("点击：" + text7);
        AssertFunction.verifyEquals(driver, text7, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警恢复策略，第二步，规则条件设置录入完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警恢复策略-新建第3部分，规则条件高级设置
    @Test(dataProvider = "xmldata")
    public void alarmRecoveryRulesCreateRulesConditionAdvancedSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        录入，内容关键字
        WebElement contentKeyword = l.getElement(param.get("contentKeyword"));
        contentKeyword.sendKeys(param.get("contentKeywordValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("contentKeywordValue"));
//       点击，规则条件高级设置，下一步
        WebElement rulesConditionAdvancedConfigNextStep = l.getElement(param.get("rulesConditionAdvancedConfigNextStep"));
        String text71 = rulesConditionAdvancedConfigNextStep.getText();
        rulesConditionAdvancedConfigNextStep.click();
        LogFunction.logInfo("点击：" + text71);
        AssertFunction.verifyEquals(driver, text71, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警恢复策略，第三步，规则条件高级设置录入完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警恢复策略-新建第4部分，告警分类设置
    @Test(dataProvider = "xmldata")
    public void alarmRecoveryRulesCreateAlarmRecoverySetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//          点击，保存
        WebElement alarmClassifyConfigSave = l.getElement(param.get("alarmClassifyConfigSave"));
        String text10 = alarmClassifyConfigSave.getText();
        alarmClassifyConfigSave.click();
        LogFunction.logInfo("点击：" + text10);
        LogFunction.logInfo("-----------------告警恢复策略，最后一步，告警分类设置录入完成且告警分类策略创建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警恢复策略-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmRecoveryRulesSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，筛选
        co.selectO(param);
//        WebElement commonSelect = l.getElement(param.get("commonSelect"));
//        String text = commonSelect.getText();
//        commonSelect.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");

//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmRecoveryRulesNameValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmRecoveryRulesNameValue"));
//        点击，状态选择框
        WebElement selectChooseStatus = l.getElement(param.get("selectChooseStatus"));
        selectChooseStatus.click();
        LogFunction.logInfo("点开：状态选择框");

//        状态，选择：启用
        WebElement StartUsing = l.getElement(param.get("selectChooseStatusStartUsing"));
        String text7 = StartUsing.getText();
        StartUsing.click();
        LogFunction.logInfo("选择状态：" + text7);
        AssertFunction.verifyEquals(driver, text7, "启用");
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        勾选,规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
//        校验,规则名称
        WebElement SelectRulesName = l.getElement(param.get("commonDenoiseStrategySelectRulesNameValue"));
        String text1 = SelectRulesName.getText();
        LogFunction.logInfo("规则名称为：" + text1);
        AssertFunction.verifyEquals(driver, text1, param.get("alarmRecoveryRulesNameValue"));
//        校验,规则类型
        WebElement selectRulesType = l.getElement(param.get("commonDenoiseStrategySelectRulesTypeValue"));
        String text3 = selectRulesType.getText();
        LogFunction.logInfo("规则类型为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "恢复");
//        校验,节点类型
        WebElement selectNodeType = l.getElement(param.get("commonDenoiseStrategySelectNodeTypeValue"));
        String text4 = selectNodeType.getText();
        LogFunction.logInfo("节点类型为：" + text4);
        AssertFunction.verifyEquals(driver, text4, Oracal);
//        校验,域
        WebElement selectDomain = l.getElement(param.get("commonDenoiseStrategySelectDomainValue"));
        String text5 = selectDomain.getText();
        LogFunction.logInfo("域为：" + text5);
        AssertFunction.verifyEquals(driver, text5, Domain);
//        校验,优先级
        WebElement selectPriority = l.getElement(param.get("commonDenoiseStrategySelectPriorityValue"));
        String text6 = selectPriority.getText();
        LogFunction.logInfo("优先级为：" + text6);
        AssertFunction.verifyEquals(driver, text6, Priority);
//        校验,状态
        WebElement selectStatus = l.getElement(param.get("commonDenoiseStrategySelectStatusValue"));
        String title = selectStatus.getAttribute("title");
        LogFunction.logInfo("状态为：" + title);
        AssertFunction.verifyEquals(driver, title, "已启用");
        LogFunction.logInfo("-----------------降噪策略，告警恢复策略筛选查询校验完成完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警恢复策略-编辑，第1部分，基础设置
    @Test(dataProvider = "xmldata")
    public void alarmRecoveryRulesEditBasicSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//          点击，编辑
        WebElement commonEdit = l.getElement(param.get("commonEdit"));
        String text1 = commonEdit.getText();
        commonEdit.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "编辑", "----是否点击编辑----");
//          录入，规则名称
        WebElement rulesName = l.getElement(param.get("rulesName"));
        rulesName.clear();
        rulesName.sendKeys(param.get("alarmRecoveryRulesNameEditValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("alarmRecoveryRulesNameEditValue"));
//          选择，优先级，低
        WebElement priority = l.getElement(param.get("priorityLow"));
        Priority = priority.getText();
        priority.click();
        LogFunction.logInfo("勾选优先级" + Priority);
//        关闭，启动
        WebElement startOrClose = l.getElement(param.get("startOrClose"));
        boolean selected = startOrClose.isSelected();
        LogFunction.logInfo(String.valueOf(selected));
        if (selected == true) {
            startOrClose.click();
            LogFunction.logInfo("状态更改为：停用");
        }

//        点击，基础设置，下一步
        WebElement basicsNextStep = l.getElement(param.get("basicsNextStep"));
        String text3 = basicsNextStep.getText();
        basicsNextStep.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警恢复策略，第一步，基础设置编辑完成---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警恢复策略-编辑，第2部分，规则条件设置
    @Test(dataProvider = "xmldata")
    public void alarmRecoveryRulesEditRulesConditionSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//         点击，域
//        WebElement domain = l.getElement(param.get("domain"));
//        domain.click();
//        LogFunction.logInfo("点击：域");
////        选择，域：rootDomain
//        WebElement chooseDomain = l.getElement(param.get("chooseDomain"));
//        Domain = chooseDomain.getText();
//        chooseDomain.click();
//        LogFunction.logInfo("选择域：" + Domain);
//        AssertFunction.verifyEquals(driver, Domain, "rootDomain", "----验证选择的域是否为：rootDomain----");
////          点击，类型
//        WebElement type = l.getElement(param.get("type"));
//        type.click();
//        LogFunction.logInfo("点击：类型");
////        选择,类型:操作系统
//        WebElement chooseType = l.getElement(param.get("chooseType"));
//        Oracal = chooseType.getText();
//        chooseType.click();
//        LogFunction.logInfo("选择类型：" + Oracal);
//        AssertFunction.verifyEquals(driver, Oracle, "操作系统", "----验证选择的类型是否为；Oracle----");
//        点击，告警类型选择
//        WebElement alarmType = l.getElement(param.get("alarmType"));
//        alarmType.click();
//        LogFunction.logInfo("点击：告警类型选择");
////        选择,告警类型选择:Oracle_System_Alert
//        WebElement chooseAlarmType = l.getElement(param.get("chooseAlarmType"));
//        String text6 = chooseAlarmType.getText();
//        chooseAlarmType.click();
//        LogFunction.logInfo("选择告警类型：" + text6);
//        AssertFunction.verifyEquals(driver, text6, "Oracle_System_Alert", "----验证选择的告警类型是否为；Oracle_System_Alert----");
//       点击，规则条件设置，下一步
        WebElement rulesConditionConfigNextStep = l.getElement(param.get("rulesConditionConfigNextStep"));
        String text7 = rulesConditionConfigNextStep.getText();
        rulesConditionConfigNextStep.click();
        LogFunction.logInfo("点击：" + text7);
        AssertFunction.verifyEquals(driver, text7, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警恢复策略，第二步，规则条件设置编辑完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警恢复策略-编辑，第3部分，规则条件高级设置
    @Test(dataProvider = "xmldata")
    public void alarmRecoveryRulesEditRulesConditionAdvancedSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//       点击，规则条件高级设置，下一步
        WebElement rulesConditionAdvancedConfigNextStep = l.getElement(param.get("rulesConditionAdvancedConfigNextStep"));
        String text71 = rulesConditionAdvancedConfigNextStep.getText();
        rulesConditionAdvancedConfigNextStep.click();
        LogFunction.logInfo("点击：" + text71);
        AssertFunction.verifyEquals(driver, text71, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警恢复策略，第三步，规则条件高级设置编辑完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警恢复策略-编辑，第4部分，告警分类设置
    @Test(dataProvider = "xmldata")
    public void alarmRecoveryRulesEditAlarmClassifySetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，告警内容
//        WebElement alarmContent = l.getElement(param.get("alarmContent"));
//        String text = alarmContent.getText();
//        alarmContent.click();
//        LogFunction.logInfo("点击：告警内容");
////        最大合并数量,录入，5
//        WebElement maxMergeNumber = l.getElement(param.get("maxMergeNumber"));
//        maxMergeNumber.sendKeys(param.get("maxMergeNumberValue"));
//        LogFunction.logInfo("录入最大合并数量：" + param.get("maxMergeNumberValue"));
////        合并时间窗口，录入，1
//        WebElement mergeTimeWindows = l.getElement(param.get("mergeTimeWindows"));
//        mergeTimeWindows.sendKeys(param.get("maxMergeNumberValue"));
//        LogFunction.logInfo("录入合并时间窗口：" + param.get("mergeTimeWindowsValue"));

//          点击，保存
        WebElement alarmMergeConfigSave = l.getElement(param.get("alarmClassifyConfigSave"));
        String text10 = alarmMergeConfigSave.getText();
        alarmMergeConfigSave.click();
        LogFunction.logInfo("点击：" + text10);
        LogFunction.logInfo("-----------------告警恢复策略，最后一步，告警恢复设置编辑完成且告警合并策略创建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警恢复策略-编辑-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyalarmRecoveryRulesEditSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，筛选
//        WebElement commonSelect = l.getElement(param.get("commonSelect"));
//        String text = commonSelect.getText();
//        commonSelect.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmRecoveryRulesNameEditValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmRecoveryRulesNameEditValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        获取，告警信息列表信息，无数据
        WebElement NumberValue = l.getElement(param.get("alarmRulesListNumberValue"));
        String text55 = NumberValue.getText();
        LogFunction.logInfo("告警列表信息为：" + text55);
        //        点击，状态选择框
        WebElement selectChooseStatus1 = l.getElement(param.get("selectChooseStatus"));
        selectChooseStatus1.click();
        LogFunction.logInfo("点开：状态选择框");
//        状态，选择：停用
        WebElement BlockUp = l.getElement(param.get("selectChooseStatusBlockUp"));
        String text8 = BlockUp.getText();
        BlockUp.click();
        LogFunction.logInfo("选择状态：" + text8);
        AssertFunction.verifyEquals(driver, text8, "停用");
//        点击，筛选，确定
        WebElement selectAffirm1 = l.getElement(param.get("commonSelectAffirm"));
        String text22 = selectAffirm1.getText();
        selectAffirm1.click();
        LogFunction.logInfo("点击：" + text22);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");

        //        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
//        校验,规则名称
        WebElement SelectRulesName = l.getElement(param.get("commonDenoiseStrategySelectRulesNameValue"));
        String text1 = SelectRulesName.getText();
        LogFunction.logInfo("规则名称为：" + text1);
        AssertFunction.verifyEquals(driver, text1, param.get("alarmRecoveryRulesNameEditValue"));
//        校验,规则类型
        WebElement selectRulesType = l.getElement(param.get("commonDenoiseStrategySelectRulesTypeValue"));
        String text3 = selectRulesType.getText();
        LogFunction.logInfo("规则类型为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "恢复");
//        校验,节点类型
        WebElement selectNodeType = l.getElement(param.get("commonDenoiseStrategySelectNodeTypeValue"));
        String text4 = selectNodeType.getText();
        LogFunction.logInfo("节点类型为：" + text4);
        AssertFunction.verifyEquals(driver, text4, Oracal);
//        校验,域
        WebElement selectDomain = l.getElement(param.get("commonDenoiseStrategySelectDomainValue"));
        String text5 = selectDomain.getText();
        LogFunction.logInfo("域为：" + text5);
        AssertFunction.verifyEquals(driver, text5, Domain);
//        校验,优先级
        WebElement selectPriority = l.getElement(param.get("commonDenoiseStrategySelectPriorityValue"));
        String text6 = selectPriority.getText();
        LogFunction.logInfo("优先级为：" + text6);
        AssertFunction.verifyEquals(driver, text6, Priority);
//        校验,状态
        WebElement selectStatus = l.getElement(param.get("commonDenoiseStrategySelectStatusValue"));
        String title = selectStatus.getAttribute("title");
        LogFunction.logInfo("状态为：" + title);
        AssertFunction.verifyEquals(driver, title, "已禁用");
        LogFunction.logInfo("-----------------降噪策略告警恢复策略筛选查询完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警恢复策略-编辑-筛选
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmRecoveryRulesEditSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        验证，筛选区域，是否展示
        WebElement SelectArea = l.getElement(param.get("denoiseStrategySelectArea"));
        boolean displayed = SelectArea.isDisplayed();
        if (displayed == false) {
//        点击，筛选
            co.selectO(param);
//            WebElement commonSelect = l.getElement(param.get("commonSelect"));
//            String text = commonSelect.getText();
//            commonSelect.click();
//            LogFunction.logInfo("点击：" + text);
//            AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
        }
//        点击，清空
        WebElement commonSelect = l.getElement(param.get("Clear"));
        String text = commonSelect.getText();
        commonSelect.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "清空");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmRecoveryRulesNameEditValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmRecoveryRulesNameEditValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
        LogFunction.logInfo("-----------------降噪策略告警恢复策略筛选查询完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警恢复策略-删除及筛选校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmRecoveryRulesDeleteAndSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        验证，筛选区域，是否展示
        WebElement SelectArea = l.getElement(param.get("denoiseStrategySelectArea"));
        boolean displayed = SelectArea.isDisplayed();
        if (displayed == false) {
//        点击，筛选
            co.selectO(param);
//            WebElement commonSelect = l.getElement(param.get("commonSelect"));
//            String text = commonSelect.getText();
//            commonSelect.click();
//            LogFunction.logInfo("点击：" + text);
//            AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
        }
//        点击，清空
        WebElement commonSelect = l.getElement(param.get("Clear"));
        String text = commonSelect.getText();
        commonSelect.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "清空");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmRecoveryRulesNameEditValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmRecoveryRulesNameEditValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
        co.alarmRulesDelete(param, "alarmRecoveryRulesNameEditValue");
        LogFunction.logInfo("-----------------降噪策略，告警恢复策略，删除及筛选校验通过---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警升级策略-新建第1部分，基础设置
    @Test(dataProvider = "xmldata")
    public void alarmUpgradeRulesCreateBasicSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//          点击，新建
        WebElement commonCreate = l.getElement(param.get("commonCreate"));
        String text1 = commonCreate.getText();
        commonCreate.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "新建", "----是否点击新建----");
//          录入，规则名称
        WebElement rulesName = l.getElement(param.get("rulesName"));
        rulesName.sendKeys(param.get("alarmUpgradeRulesNameValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("alarmUpgradeRulesNameValue"));
//          选择，优先级，高
        WebElement priority = l.getElement(param.get("priority"));
        Priority = priority.getText();
        priority.click();
        LogFunction.logInfo("勾选优先级" + Priority);
//        点击，基础设置，下一步
        WebElement basicsNextStep = l.getElement(param.get("basicsNextStep"));
        String text3 = basicsNextStep.getText();
        basicsNextStep.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警升级策略，第一步，基础设置录入完成---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警升级策略-新建第2部分，规则条件设置
    @Test(dataProvider = "xmldata")
    public void alarmUpgradeRulesCreateRulesConditionSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//         点击，域
        WebElement domain = l.getElement(param.get("domain"));
        domain.click();
        LogFunction.logInfo("点击：域");
//        选择，域：rootDomain
        WebElement chooseDomain = l.getElement(param.get("chooseDomain"));
        Domain = chooseDomain.getText();
        chooseDomain.click();
        LogFunction.logInfo("选择域：" + Domain);
        AssertFunction.verifyEquals(driver, Domain, "rootDomain", "----验证选择的域是否为：rootDomain----");
//          点击，类型
        WebElement type = l.getElement(param.get("type"));
        type.click();
        LogFunction.logInfo("点击：类型");
//        选择,类型:操作系统
        WebElement chooseType = l.getElement(param.get("chooseType"));
        Oracal = chooseType.getText();
        chooseType.click();
        LogFunction.logInfo("选择类型：" + Oracal);
        AssertFunction.verifyEquals(driver, Oracal, "Oracle", "----验证选择的类型是否为；Oracle----");
//        点击，告警类型选择
//        WebElement alarmType = l.getElement(param.get("alarmType"));
//        alarmType.click();
//        LogFunction.logInfo("点击：告警类型选择");
//        选择,告警类型选择:Oracle_System_Alert
//        WebElement chooseAlarmType = l.getElement(param.get("chooseAlarmType"));
//        String text6 = chooseAlarmType.getText();
//        chooseAlarmType.click();
//        LogFunction.logInfo("选择告警类型：" + text6);
//        AssertFunction.verifyEquals(driver, text6, "Oracle_System_Alert", "----验证选择的告警类型是否为；Oracle_System_Alert----");
//       点击，规则条件设置，下一步
        WebElement rulesConditionConfigNextStep = l.getElement(param.get("rulesConditionConfigNextStep"));
        String text7 = rulesConditionConfigNextStep.getText();
        rulesConditionConfigNextStep.click();
        LogFunction.logInfo("点击：" + text7);
        AssertFunction.verifyEquals(driver, text7, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警升级策略，第二步，规则条件设置录入完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警升级策略-新建第3部分，规则条件高级设置
    @Test(dataProvider = "xmldata")
    public void alarmUpgradeRulesCreateRulesConditionAdvancedSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        录入，内容关键字
        WebElement contentKeyword = l.getElement(param.get("contentKeyword"));
        contentKeyword.sendKeys(param.get("contentKeywordValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("contentKeywordValue"));
//       点击，规则条件高级设置，下一步
        WebElement rulesConditionAdvancedConfigNextStep = l.getElement(param.get("rulesConditionAdvancedConfigNextStep"));
        String text71 = rulesConditionAdvancedConfigNextStep.getText();
        rulesConditionAdvancedConfigNextStep.click();
        LogFunction.logInfo("点击：" + text71);
        AssertFunction.verifyEquals(driver, text71, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警升级策略，第三步，规则条件高级设置录入完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警升级策略-新建第4部分，告警分类设置
    @Test(dataProvider = "xmldata")
    public void alarmUpgradeRulesCreateAlarmUpgradeSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//          点击，保存
        WebElement alarmClassifyConfigSave = l.getElement(param.get("alarmClassifyConfigSave"));
        String text10 = alarmClassifyConfigSave.getText();
        alarmClassifyConfigSave.click();
        LogFunction.logInfo("点击：" + text10);
        LogFunction.logInfo("-----------------告警升级策略，最后一步，告警分类设置录入完成且创建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警升级策略-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmUpgradeRulesSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        点击，筛选
        co.selectO(param);
//        WebElement commonSelect = l.getElement(param.get("commonSelect"));
//        String text = commonSelect.getText();
//        commonSelect.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");

//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmUpgradeRulesNameValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmUpgradeRulesNameValue"));
//        点击，状态选择框
        WebElement selectChooseStatus = l.getElement(param.get("selectChooseStatus"));
        selectChooseStatus.click();
        LogFunction.logInfo("点开：状态选择框");

//        状态，选择：启用
        WebElement StartUsing = l.getElement(param.get("selectChooseStatusStartUsing"));
        String text7 = StartUsing.getText();
        StartUsing.click();
        LogFunction.logInfo("选择状态：" + text7);
        AssertFunction.verifyEquals(driver, text7, "启用");
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");

//        校验,规则名称
        WebElement SelectRulesName = l.getElement(param.get("commonDenoiseStrategySelectRulesNameValue"));
        String text1 = SelectRulesName.getText();
        LogFunction.logInfo("规则名称为：" + text1);
        AssertFunction.verifyEquals(driver, text1, param.get("alarmUpgradeRulesNameValue"));
//        校验,规则类型
        WebElement selectRulesType = l.getElement(param.get("commonDenoiseStrategySelectRulesTypeValue"));
        String text3 = selectRulesType.getText();
        LogFunction.logInfo("规则类型为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "升级");
//        校验,节点类型
        WebElement selectNodeType = l.getElement(param.get("commonDenoiseStrategySelectNodeTypeValue"));
        String text4 = selectNodeType.getText();
        LogFunction.logInfo("节点类型为：" + text4);
        AssertFunction.verifyEquals(driver, text4, Oracal);
//        校验,域
        WebElement selectDomain = l.getElement(param.get("commonDenoiseStrategySelectDomainValue"));
        String text5 = selectDomain.getText();
        LogFunction.logInfo("域为：" + text5);
        AssertFunction.verifyEquals(driver, text5, Domain);
//        校验,优先级
        WebElement selectPriority = l.getElement(param.get("commonDenoiseStrategySelectPriorityValue"));
        String text6 = selectPriority.getText();
        LogFunction.logInfo("优先级为：" + text6);
        AssertFunction.verifyEquals(driver, text6, Priority);
//        校验,状态
        WebElement selectStatus = l.getElement(param.get("commonDenoiseStrategySelectStatusValue"));
        String title = selectStatus.getAttribute("title");
        LogFunction.logInfo("状态为：" + title);
        AssertFunction.verifyEquals(driver, title, "已启用");
        //        勾选,规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
        LogFunction.logInfo("-----------------降噪策略，告警升级策略，新建及筛选校验通过---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警升级策略-编辑，第1部分，基础设置
    @Test(dataProvider = "xmldata")
    public void alarmUpgradeRulesEditBasicSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//          点击，编辑
        WebElement commonEdit = l.getElement(param.get("commonEdit"));
        String text1 = commonEdit.getText();
        commonEdit.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "编辑", "----是否点击编辑----");
//          录入，规则名称
        WebElement rulesName = l.getElement(param.get("rulesName"));
        rulesName.clear();
        rulesName.sendKeys(param.get("alarmUpgradeRulesNameEditValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("alarmUpgradeRulesNameEditValue"));
//          选择，优先级，低
        WebElement priority = l.getElement(param.get("priorityLow"));
        Priority = priority.getText();
        priority.click();
        LogFunction.logInfo("勾选优先级" + Priority);
//        关闭，启动
        WebElement startOrClose = l.getElement(param.get("startOrClose"));
        boolean selected = startOrClose.isSelected();
        LogFunction.logInfo(String.valueOf(selected));
        if (selected == true) {
            startOrClose.click();
            LogFunction.logInfo("状态更改为：停用");
        }

//        点击，基础设置，下一步
        WebElement basicsNextStep = l.getElement(param.get("basicsNextStep"));
        String text3 = basicsNextStep.getText();
        basicsNextStep.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警升级策略，第一步，基础设置编辑完成---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警升级策略-编辑，第2部分，规则条件设置
    @Test(dataProvider = "xmldata")
    public void alarmUpgradeRulesEditRulesConditionSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//       点击，规则条件设置，下一步
        WebElement rulesConditionConfigNextStep = l.getElement(param.get("rulesConditionConfigNextStep"));
        String text7 = rulesConditionConfigNextStep.getText();
        rulesConditionConfigNextStep.click();
        LogFunction.logInfo("点击：" + text7);
        AssertFunction.verifyEquals(driver, text7, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警升级策略，第二步，规则条件设置编辑完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警升级策略-编辑，第3部分，规则条件高级设置
    @Test(dataProvider = "xmldata")
    public void alarmUpgradeRulesEditRulesConditionAdvancedSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//       点击，规则条件高级设置，下一步
        WebElement NextStep = l.getElement(param.get("rulesConditionAdvancedConfigNextStep"));
        String text71 = NextStep.getText();
        AssertFunction.verifyEquals(driver, text71, "下一步", "----验证是否点击：下一步----");
        NextStep.click();
        LogFunction.logInfo("点击：" + text71);
        LogFunction.logInfo("-----------------告警升级策略，第三步，规则条件高级设置编辑完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警升级策略-编辑，第4部分，告警分类设置
    @Test(dataProvider = "xmldata")
    public void alarmUpgradeRulesEditAlarmClassifySetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，告警内容
//        WebElement alarmContent = l.getElement(param.get("alarmContent"));
//        String text = alarmContent.getText();
//        alarmContent.click();
//        LogFunction.logInfo("点击：告警内容");
////        最大合并数量,录入，5
//        WebElement maxMergeNumber = l.getElement(param.get("maxMergeNumber"));
//        maxMergeNumber.sendKeys(param.get("maxMergeNumberValue"));
//        LogFunction.logInfo("录入最大合并数量：" + param.get("maxMergeNumberValue"));
////        合并时间窗口，录入，1
//        WebElement mergeTimeWindows = l.getElement(param.get("mergeTimeWindows"));
//        mergeTimeWindows.sendKeys(param.get("maxMergeNumberValue"));
//        LogFunction.logInfo("录入合并时间窗口：" + param.get("mergeTimeWindowsValue"));

//          点击，保存
        WebElement alarmMergeConfigSave = l.getElement(param.get("alarmClassifyConfigSave"));
        String text10 = alarmMergeConfigSave.getText();
        alarmMergeConfigSave.click();
        LogFunction.logInfo("点击：" + text10);
        LogFunction.logInfo("-----------------告警升级策略，最后一步，告警升级设置编辑完成且创建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警升级策略-编辑-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmUpgradeRulesEditSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        点击，筛选
//        WebElement commonSelect = l.getElement(param.get("commonSelect"));
//        String text = commonSelect.getText();
//        commonSelect.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmUpgradeRulesNameEditValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmUpgradeRulesNameEditValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        获取，告警信息列表信息，无数据
        WebElement NumberValue = l.getElement(param.get("alarmRulesListNumberValue"));
        String text55 = NumberValue.getText();
        LogFunction.logInfo("告警列表信息为：" + text55);
        //        点击，状态选择框
        WebElement selectChooseStatus1 = l.getElement(param.get("selectChooseStatus"));
        selectChooseStatus1.click();
        LogFunction.logInfo("点开：状态选择框");
//        状态，选择：停用
        WebElement BlockUp = l.getElement(param.get("selectChooseStatusBlockUp"));
        String text8 = BlockUp.getText();
        BlockUp.click();
        LogFunction.logInfo("选择状态：" + text8);
        AssertFunction.verifyEquals(driver, text8, "停用");
//        点击，筛选，确定
        WebElement selectAffirm1 = l.getElement(param.get("commonSelectAffirm"));
        String text22 = selectAffirm1.getText();
        selectAffirm1.click();
        LogFunction.logInfo("点击：" + text22);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");

        //        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
//        校验,规则名称
        WebElement SelectRulesName = l.getElement(param.get("commonDenoiseStrategySelectRulesNameValue"));
        String text1 = SelectRulesName.getText();
        LogFunction.logInfo("规则名称为：" + text1);
        AssertFunction.verifyEquals(driver, text1, param.get("alarmUpgradeRulesNameEditValue"));
//        校验,规则类型
        WebElement selectRulesType = l.getElement(param.get("commonDenoiseStrategySelectRulesTypeValue"));
        String text3 = selectRulesType.getText();
        LogFunction.logInfo("规则类型为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "升级");
//        校验,节点类型
        WebElement selectNodeType = l.getElement(param.get("commonDenoiseStrategySelectNodeTypeValue"));
        String text4 = selectNodeType.getText();
        LogFunction.logInfo("节点类型为：" + text4);
        AssertFunction.verifyEquals(driver, text4, Oracal);
//        校验,域
        WebElement selectDomain = l.getElement(param.get("commonDenoiseStrategySelectDomainValue"));
        String text5 = selectDomain.getText();
        LogFunction.logInfo("域为：" + text5);
        AssertFunction.verifyEquals(driver, text5, Domain);
//        校验,优先级
        WebElement selectPriority = l.getElement(param.get("commonDenoiseStrategySelectPriorityValue"));
        String text6 = selectPriority.getText();
        LogFunction.logInfo("优先级为：" + text6);
        AssertFunction.verifyEquals(driver, text6, Priority);
//        校验,状态
        WebElement selectStatus = l.getElement(param.get("commonDenoiseStrategySelectStatusValue"));
        String title = selectStatus.getAttribute("title");
        LogFunction.logInfo("状态为：" + title);
        AssertFunction.verifyEquals(driver, title, "已禁用");
        LogFunction.logInfo("-----------------降噪策略,告警升级策略,编辑筛选校验通过---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警升级策略-编辑-筛选
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmUpgradeRulesEditSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        验证，筛选区域，是否展示
        WebElement SelectArea = l.getElement(param.get("denoiseStrategySelectArea"));
        boolean displayed = SelectArea.isDisplayed();
        if (displayed == false) {
//        点击，筛选
            co.selectO(param);
//            WebElement commonSelect = l.getElement(param.get("commonSelect"));
//            String text = commonSelect.getText();
//            commonSelect.click();
//            LogFunction.logInfo("点击：" + text);
//            AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
        }
//        点击，清空
        WebElement commonSelect = l.getElement(param.get("Clear"));
        String text = commonSelect.getText();
        commonSelect.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "清空");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmUpgradeRulesNameEditValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmUpgradeRulesNameEditValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
        LogFunction.logInfo("-----------------降噪策略,告警升级策略,筛选查询完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警升级策略-删除及筛选校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmUpgradeRulesDeleteAndSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        验证，筛选区域，是否展示
        WebElement SelectArea = l.getElement(param.get("denoiseStrategySelectArea"));
        boolean displayed = SelectArea.isDisplayed();
        if (displayed == false) {
//        点击，筛选
            co.selectO(param);
//            WebElement commonSelect = l.getElement(param.get("commonSelect"));
//            String text = commonSelect.getText();
//            commonSelect.click();
//            LogFunction.logInfo("点击：" + text);
//            AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
        }
//        点击，清空
        WebElement commonSelect = l.getElement(param.get("Clear"));
        String text = commonSelect.getText();
        commonSelect.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "清空");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmUpgradeRulesNameEditValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmUpgradeRulesNameEditValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
        co.alarmRulesDelete(param, "alarmUpgradeRulesNameEditValue");
        LogFunction.logInfo("-----------------降噪策略,告警升级策略,删除及筛选校验完成---------------------");
    }

    //      菜单-集中告警-告警配置-预警策略
    @Test(dataProvider = "xmldata")
    public void alarmConfigWarningStrategy(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        点击，预警策略
        co.modelClickButton(param, "WarningStrategy");
        LogFunction.logInfo("-----------------进入，预警策略页面---------------------");
    }

    //      菜单-集中告警-告警配置-预警策略-新建
    @Test(dataProvider = "xmldata")
    public void alarmConfigWarningStrategyCreate(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，新建
        co.createButton(param);
//        录入，策略名称
        WebElement strategyName = l.getElement(param.get("strategyName"));
        strategyName.clear();
        strategyName.sendKeys(param.get("WarningStrategyCreateStrategyNameValue"));
        LogFunction.logInfo("录入，策略名称：" + param.get("WarningStrategyCreateStrategyNameValue"));
//         录入，项目名称
        WebElement projectName = l.getElement(param.get("projectName"));
        projectName.clear();
        projectName.sendKeys(param.get("WarningStrategyCreateProjectNameValue"));
        LogFunction.logInfo("录入，项目名称：" + param.get("WarningStrategyCreateProjectNameValue"));
//          点击，策略类型
        WebElement StrategyType = l.getElement(param.get("StrategyType"));
        StrategyType.click();
        LogFunction.logInfo("点击，策略类型");
//        选择，策略类型（值）
        WebElement StrategyTypeValue = l.getElement(param.get("StrategyTypeValue"));
        String text = StrategyTypeValue.getText();
        AssertFunction.verifyEquals(driver, text, "综合预警");
        StrategyTypeValue.click();
        LogFunction.logInfo("选择，策略类型:" + text);
//          点击，下一步
        WebElement NextStep = l.getElement(param.get("NextStep"));
        String text1 = NextStep.getAttribute("value");
        AssertFunction.verifyEquals(driver, text1, "下一步");
        NextStep.click();
        LogFunction.logInfo("点击：" + text1);
//      获取，共有，多少个策略条件
        List<WebElement> deleteButton = l.getElements(param.get("DeleteButton"));
        int size = deleteButton.size();
        LogFunction.logInfo("策略条件的数量为：" + size);
//          点击，添加新策略
        WebElement addNewStrategy = l.getElement(param.get("AddNewStrategy"));
        String text3 = addNewStrategy.getAttribute("value");
        AssertFunction.verifyEquals(driver, text3, "添加新策略");
        addNewStrategy.click();
        LogFunction.logInfo("点击：" + text3);
        List<WebElement> deleteButton1 = l.getElements(param.get("DeleteButton"));
        int size1 = deleteButton1.size();
        LogFunction.logInfo("策略条件的数量为：" + size1);
        AssertFunction.verifyEquals(driver, size1, size + 1);
        LogFunction.logInfo("成功，添加新策略");
        for (int i = 1; i < size1; i++) {
//            点击，删除按钮，留1个策略条件
            WebElement deleteButton2 = l.getElement(param.get("DeleteButton"));
            deleteButton2.click();
            LogFunction.logInfo("第" + i + "次，点击删除按钮");
        }
//        录入,变坏次数净值
        WebElement NetWorth = l.getElement(param.get("NetWorth"));
        NetWorth.clear();
        NetWorth.sendKeys(param.get("WarningStrategyCreateNetWorthValue"));
        LogFunction.logInfo("录入，变坏次数净值：" + param.get("WarningStrategyCreateNetWorthValue"));
//      点击，告警级别-超级基线历史最值
        WebElement alarmLevel = l.getElement(param.get("AlarmLevel"));
        alarmLevel.click();
        LogFunction.logInfo("点击，告警级别-超级基线历史最值");
//        选择，告警级别-超级基线历史最值
        WebElement AlarmLevelValue = l.getElement(param.get("AlarmLevelValue"));
        String text2 = AlarmLevelValue.getText();
        String[] split = text2.split(" ");
        String s = null;
        for (String a : split) {
            s = a.toString();
        }
        String info = InterceptFunction.intercept(text2, "Info");
        AssertFunction.verifyEquals(driver, info, "Info");
        AlarmLevelValue.click();
        LogFunction.logInfo("选择，告警级别-超级基线历史最值:" + s);
//      点击，提交
        co.modelClickButton(param, "Submit", "提交");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//         验证，是否新建成功
        List<WebElement> AllData = l.getElements(param.get("commonSelectResultFirstColumnAllData"));
        ArrayList arrayList = new ArrayList(AllData.size());
        for (WebElement e : AllData) {
            String text4 = e.getText();
            arrayList.add(text4);
        }
        boolean b = arrayList.contains(param.get("WarningStrategyCreateStrategyNameValue"));
        LogFunction.logInfo("新建是否成功状态:" + b);
        AssertFunction.assertEquals(driver, b, true);
        LogFunction.logInfo("-----------------预警策略新建完成页面---------------------");
    }

    //      菜单-集中告警-告警配置-预警策略-编辑
    @Test(dataProvider = "xmldata")
    public void alarmConfigWarningStrategyEdit(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        勾选，新建策略
        List<WebElement> AllData = l.getElements(param.get("commonSelectResultFirstColumnAllData"));
        ArrayList arrayList = new ArrayList(AllData.size());
        for (WebElement e : AllData) {
            String text4 = e.getText();
            arrayList.add(text4);
        }
        int iii = arrayList.indexOf(param.get("WarningStrategyCreateStrategyNameValue"));
        List<WebElement> ChooseAllData = l.getElements(param.get("commonSelectResultChooseAllData"));
        WebElement webElement = ChooseAllData.get(iii);
        webElement.click();
        LogFunction.logInfo("成功勾选，新建的预警策略");
//        点击，编辑
        co.editButton(param);
//        编辑，策略名称
        WebElement strategyName = l.getElement(param.get("strategyName"));
        strategyName.clear();
        strategyName.sendKeys(param.get("WarningStrategyEditStrategyNameValue"));
        LogFunction.logInfo("编辑，策略名称：" + param.get("WarningStrategyEditStrategyNameValue"));
//         编辑，项目名称
        WebElement projectName = l.getElement(param.get("projectName"));
        projectName.clear();
        projectName.sendKeys(param.get("WarningStrategyEditProjectNameValue"));
        LogFunction.logInfo("编辑，项目名称：" + param.get("WarningStrategyEditProjectNameValue"));
//          点击，下一步
        WebElement NextStep = l.getElement(param.get("NextStep"));
        String text1 = NextStep.getAttribute("value");
        AssertFunction.verifyEquals(driver, text1, "下一步");
        NextStep.click();
        LogFunction.logInfo("点击：" + text1);
//      获取，共有，多少个策略条件
        List<WebElement> deleteButton = l.getElements(param.get("DeleteButton"));
        int size = deleteButton.size();
        LogFunction.logInfo("策略条件的数量为：" + size);
        for (int i = 1; i <= size; i++) {
//            点击，删除按钮，留1个策略条件
            WebElement deleteButton2 = l.getElement(param.get("DeleteButton"));
            deleteButton2.click();
            LogFunction.logInfo("第" + i + "次，点击删除按钮");
        }
        //          点击，添加新策略
        WebElement addNewStrategy = l.getElement(param.get("AddNewStrategy"));
        String text3 = addNewStrategy.getAttribute("value");
        AssertFunction.verifyEquals(driver, text3, "添加新策略");
        addNewStrategy.click();
        List<WebElement> deleteButton1 = l.getElements(param.get("DeleteButton"));
        int size1 = deleteButton1.size();
        LogFunction.logInfo("策略条件的数量为：" + size1);
        AssertFunction.verifyEquals(driver, size1, 1);
        LogFunction.logInfo("成功，添加新策略");
//        录入,变坏次数净值
        WebElement NetWorth = l.getElement(param.get("NetWorth"));
        NetWorth.clear();
        NetWorth.sendKeys(param.get("WarningStrategyCreateNetWorthValue"));
        LogFunction.logInfo("录入，变坏次数净值：" + param.get("WarningStrategyCreateNetWorthValue"));
//      点击，告警级别-超级基线历史最值
        WebElement alarmLevel = l.getElement(param.get("AlarmLevel"));
        alarmLevel.click();
        LogFunction.logInfo("点击，告警级别-超级基线历史最值");
//        选择，告警级别-超级基线历史最值
        WebElement AlarmLevelValue = l.getElement(param.get("AlarmLevelValue"));
        String text2 = AlarmLevelValue.getText();
        String[] split = text2.split(" ");
        String s = null;
        for (String a : split) {
            s = a.toString();
        }
        String info = InterceptFunction.intercept(text2, "Info");
        AssertFunction.verifyEquals(driver, info, "Info");
        AlarmLevelValue.click();
        LogFunction.logInfo("选择，告警级别-超级基线历史最值:" + s);
//      点击，提交
        co.modelClickButton(param, "Submit", "提交");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//         验证，是否新建成功
        List<WebElement> AllData1 = l.getElements(param.get("commonSelectResultFirstColumnAllData"));
        ArrayList arrayList1 = new ArrayList(AllData1.size());
        for (WebElement e : AllData1) {
            String text4 = e.getText();
            arrayList1.add(text4);
        }
        boolean b = arrayList1.contains(param.get("WarningStrategyEditStrategyNameValue"));
        LogFunction.logInfo("编辑是否成功状态:" + b);
        AssertFunction.assertEquals(driver, b, true);
        LogFunction.logInfo("-----------------预警策略编辑完成页面---------------------");
    }

    //      菜单-集中告警-告警配置-预警策略-删除
    @Test(dataProvider = "xmldata")
    public void alarmConfigWarningStrategyDelete(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        勾选，编辑策略
        List<WebElement> AllData = l.getElements(param.get("commonSelectResultFirstColumnAllData"));
        ArrayList arrayList = new ArrayList(AllData.size());
        for (WebElement e : AllData) {
            String text4 = e.getText();
            arrayList.add(text4);
        }
        int iii = arrayList.indexOf(param.get("WarningStrategyEditStrategyNameValue"));
        List<WebElement> ChooseAllData = l.getElements(param.get("commonSelectResultChooseAllData"));
        WebElement webElement = ChooseAllData.get(iii);
        webElement.click();
        LogFunction.logInfo("成功勾选，编辑的预警策略");
//        点击，删除
        co.deleteButton(param);
//        确认，删除
        co.alarmHintAndConfirm(param, "确定要删除？");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//         验证，是否删除成功
        List<WebElement> AllData1 = l.getElements(param.get("commonSelectResultFirstColumnAllData"));
        ArrayList arrayList1 = new ArrayList(AllData1.size());
        for (WebElement e : AllData1) {
            String text4 = e.getText();
            arrayList1.add(text4);
        }
        boolean b = arrayList1.contains(param.get("WarningStrategyEditStrategyNameValue"));
        LogFunction.logInfo("是否删除成功:" + b);
        AssertFunction.assertEquals(driver, b, false);
        LogFunction.logInfo("-----------------预警策略，删除成功---------------------");
    }

    //    集中告警-告警通知-告警筛选
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmInformAlarmScreen(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，告警筛选
        co.modelClickButton(param, "AlarmScreen");
        LogFunction.logInfo("-----------------进入，告警筛选页面---------------------");

    }

    //    集中告警-告警通知-告警筛选-新建第1部分，基础设置
    @Test(dataProvider = "xmldata")
    public void alarmScreenCreateBasicSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//          点击，新建
        co.modelClickButton(param, "commonCreate");
//          录入，规则名称
        WebElement rulesName = l.getElement(param.get("rulesName"));
        rulesName.sendKeys(param.get("alarmScreenCreateStrategyNameValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("alarmScreenCreateStrategyNameValue"));
//          选择，优先级，高
        WebElement priority = l.getElement(param.get("priority"));
        Priority = priority.getText();
        priority.click();
        LogFunction.logInfo("勾选优先级" + Priority);
//        点击，基础设置，下一步
        WebElement basicsNextStep = l.getElement(param.get("basicsNextStep"));
        String text3 = basicsNextStep.getText();
        basicsNextStep.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警筛选，第一步，基础设置录入完成---------------------");

    }

    //    集中告警-告警通知-告警筛选--新建第2部分，规则条件设置
    @Test(dataProvider = "xmldata")
    public void alarmScreenCreateRulesConditionSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//         点击，域
        WebElement domain = l.getElement(param.get("domain"));
        domain.click();
        LogFunction.logInfo("点击：域");
//        选择，域：rootDomain
        WebElement chooseDomain = l.getElement(param.get("chooseDomain"));
        Domain = chooseDomain.getText();
        chooseDomain.click();
        LogFunction.logInfo("选择域：" + Domain);
        AssertFunction.verifyEquals(driver, Domain, "rootDomain", "----验证选择的域是否为：rootDomain----");
//          点击，类型
        WebElement type = l.getElement(param.get("type"));
        type.click();
        LogFunction.logInfo("点击：类型");
//        选择,类型:操作系统
        WebElement chooseType = l.getElement(param.get("chooseType"));
        Oracal = chooseType.getText();
        chooseType.click();
        LogFunction.logInfo("选择类型：" + Oracal);
        AssertFunction.verifyEquals(driver, Oracal, "Oracle", "----验证选择的类型是否为；Oracle----");
//        点击，告警类型选择
//        WebElement alarmType = l.getElement(param.get("alarmType"));
//        alarmType.click();
//        LogFunction.logInfo("点击：告警类型选择");
//        选择,告警类型选择:Oracle_System_Alert
//        WebElement chooseAlarmType = l.getElement(param.get("chooseAlarmType"));
//        String text6 = chooseAlarmType.getText();
//        chooseAlarmType.click();
//        LogFunction.logInfo("选择告警类型：" + text6);
//        AssertFunction.verifyEquals(driver, text6, "Oracle_System_Alert", "----验证选择的告警类型是否为；Oracle_System_Alert----");
//       点击，规则条件设置，下一步
        WebElement rulesConditionConfigNextStep = l.getElement(param.get("rulesConditionConfigNextStep"));
        String text7 = rulesConditionConfigNextStep.getText();
        rulesConditionConfigNextStep.click();
        LogFunction.logInfo("点击：" + text7);
        AssertFunction.verifyEquals(driver, text7, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警筛选，第二步，规则条件设置录入完成---------------------");
    }

    //    集中告警-告警通知-告警筛选-新建第3部分，规则条件高级设置
    @Test(dataProvider = "xmldata")
    public void alarmScreenCreateRulesConditionAdvancedSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        录入，内容关键字
        WebElement contentKeyword = l.getElement(param.get("contentKeyword"));
        contentKeyword.sendKeys(param.get("contentKeywordValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("contentKeywordValue"));
//       点击，规则条件高级设置，下一步
        WebElement ww = l.getElement(param.get("rulesConditionAdvancedConfigNextStep"));
        String text71 = ww.getText();
        ww.click();
        LogFunction.logInfo("点击：" + text71);
        AssertFunction.verifyEquals(driver, text71, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警筛选，第三步，规则条件高级设置录入完成---------------------");
    }

    //    集中告警-告警通知-告警筛选-新建第4部分，告警分类设置
    @Test(dataProvider = "xmldata")
    public void alarmScreenCreateAlarmUpgradeSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//          点击，保存
        WebElement alarmClassifyConfigSave = l.getElement(param.get("alarmClassifyConfigSave"));
        String text10 = alarmClassifyConfigSave.getText();
        alarmClassifyConfigSave.click();
        LogFunction.logInfo("点击：" + text10);
        AssertFunction.verifyEquals(driver, text10, "保存");
        LogFunction.logInfo("-----------------告警筛选，最后一步，告警分类设置录入完成且告警分类策略创建成功---------------------");
    }

    //    集中告警-告警通知-告警筛选-筛选及校验
    @Test(dataProvider = "xmldata")
    public void alarmScreenCreatSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        点击，筛选
        co.selectO(param);
//        WebElement commonSelect = l.getElement(param.get("commonSelect"));
//        String text = commonSelect.getText();
//        commonSelect.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");

//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmScreenCreateStrategyNameValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmScreenCreateStrategyNameValue"));
//        点击，状态选择框
        WebElement selectChooseStatus = l.getElement(param.get("selectChooseStatus"));
        selectChooseStatus.click();
        LogFunction.logInfo("点开：状态选择框");

//        状态，选择：启用
        WebElement StartUsing = l.getElement(param.get("selectChooseStatusStartUsing"));
        String text7 = StartUsing.getText();
        StartUsing.click();
        LogFunction.logInfo("选择状态：" + text7);
        AssertFunction.verifyEquals(driver, text7, "启用");
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
        co.sleep(1000);
//        校验,策略名称
        WebElement SelectRulesName = l.getElement(param.get("commonSelectResultTwo"));
        String text1 = SelectRulesName.getText();
        LogFunction.logInfo("策略名称为：" + text1);
        AssertFunction.verifyEquals(driver, text1, param.get("alarmScreenCreateStrategyNameValue"));
//        校验,节点类型
        WebElement selectNodeType = l.getElement(param.get("commonSelectResultThree"));
        String text4 = selectNodeType.getText();
        LogFunction.logInfo("节点类型为：" + text4);
        AssertFunction.verifyEquals(driver, text4, Oracal);
//        校验,数据权限域
        WebElement selectDomain = l.getElement(param.get("commonSelectResultFour"));
        String text5 = selectDomain.getText();
        LogFunction.logInfo("域为：" + text5);
        AssertFunction.verifyEquals(driver, text5, Domain);
//        校验,优先级
        WebElement selectPriority = l.getElement(param.get("commonSelectResultFive"));
        String text6 = selectPriority.getText();
        LogFunction.logInfo("优先级为：" + text6);
        AssertFunction.verifyEquals(driver, text6, Priority);
//        校验,状态
        WebElement selectStatus = l.getElement(param.get("commonSelectResultEight"));
        String title = selectStatus.getAttribute("title");
        LogFunction.logInfo("状态为：" + title);
        AssertFunction.verifyEquals(driver, title, "已启用");
        //        勾选,策略
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选策略");
        LogFunction.logInfo("-----------------告警筛选，新建查询校验完成---------------------");
    }

    //    集中告警-告警通知-告警筛选-编辑，第1部分，基础设置
    @Test(dataProvider = "xmldata")
    public void alarmScreenEditBasicSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

        co.chooseSelectResult(param);
//          点击，编辑
        WebElement commonEdit = l.getElement(param.get("commonEdit"));
        String text1 = commonEdit.getText();
        commonEdit.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "编辑", "----是否点击编辑----");
//          录入，规则名称
        WebElement rulesName = l.getElement(param.get("rulesName"));
        rulesName.clear();
        rulesName.sendKeys(param.get("alarmScreenEditStrategyNameValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("alarmScreenEditStrategyNameValue"));
//          选择，优先级，低
        WebElement priority = l.getElement(param.get("priorityLow"));
        Priority = priority.getText();
        priority.click();
        LogFunction.logInfo("勾选优先级:" + Priority);
//        关闭，启动
        WebElement startOrClose = l.getElement(param.get("startOrClose"));
        startOrClose.click();
        LogFunction.logInfo("状态更改为：停用");

//        点击，基础设置，下一步
        WebElement basicsNextStep = l.getElement(param.get("basicsNextStep"));
        String text3 = basicsNextStep.getText();
        basicsNextStep.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警筛选，第一步，基础设置编辑完成---------------------");

    }

    //    集中告警-告警通知-告警筛选-编辑，第2部分，规则条件设置
    @Test(dataProvider = "xmldata")
    public void alarmScreenEditRulesConditionSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//       点击，规则条件设置，下一步
        WebElement rulesConditionConfigNextStep = l.getElement(param.get("rulesConditionConfigNextStep"));
        String text7 = rulesConditionConfigNextStep.getText();
        rulesConditionConfigNextStep.click();
        LogFunction.logInfo("点击：" + text7);
        AssertFunction.verifyEquals(driver, text7, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警筛选，第二步，规则条件设置编辑完成---------------------");
    }

    //    集中告警-告警通知-告警筛选-编辑，第3部分，规则条件高级设置
    @Test(dataProvider = "xmldata")
    public void alarmScreenEditRulesConditionAdvancedSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//       点击，规则条件高级设置，下一步
        WebElement rulesConditionAdvancedConfigNextStep = l.getElement(param.get("rulesConditionAdvancedConfigNextStep"));
        String text71 = rulesConditionAdvancedConfigNextStep.getText();
        rulesConditionAdvancedConfigNextStep.click();
        LogFunction.logInfo("点击：" + text71);
        AssertFunction.verifyEquals(driver, text71, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警筛选，第三步，规则条件高级设置编辑完成---------------------");
    }

    //    集中告警-告警通知-告警筛选-编辑，第4部分，告警分类设置
    @Test(dataProvider = "xmldata")
    public void alarmScreenEditAlarmClassifySetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        点击，告警内容
//        WebElement alarmContent = l.getElement(param.get("alarmContent"));
//        String text = alarmContent.getText();
//        alarmContent.click();
//        LogFunction.logInfo("点击：告警内容");
////        最大合并数量,录入，5
//        WebElement maxMergeNumber = l.getElement(param.get("maxMergeNumber"));
//        maxMergeNumber.sendKeys(param.get("maxMergeNumberValue"));
//        LogFunction.logInfo("录入最大合并数量：" + param.get("maxMergeNumberValue"));
////        合并时间窗口，录入，1
//        WebElement mergeTimeWindows = l.getElement(param.get("mergeTimeWindows"));
//        mergeTimeWindows.sendKeys(param.get("maxMergeNumberValue"));
//        LogFunction.logInfo("录入合并时间窗口：" + param.get("mergeTimeWindowsValue"));

//          点击，保存
        WebElement alarmMergeConfigSave = l.getElement(param.get("alarmClassifyConfigSave"));
        String text10 = alarmMergeConfigSave.getText();
        alarmMergeConfigSave.click();
        LogFunction.logInfo("点击：" + text10);
        AssertFunction.verifyEquals(driver, text10, "保存");
        LogFunction.logInfo("-----------------告警筛选，最后一步，告警筛选设置编辑完成且告警合并策略创建成功---------------------");
    }

    //    集中告警-告警通知-告警筛选-编辑-筛选及校验
    @Test(dataProvider = "xmldata")
    public void alarmScreenEditSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，筛选
//        WebElement commonSelect = l.getElement(param.get("commonSelect"));
//        String text = commonSelect.getText();
//        commonSelect.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
////        点击，清空
//        co.modelClickButton(param,"commonSelectClear");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmScreenEditStrategyNameValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmScreenEditStrategyNameValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        获取，告警信息列表信息，无数据
        co.selectResultIsNull(param, "QueryResultEmptyData");
        //        点击，状态选择框
        WebElement selectChooseStatus1 = l.getElement(param.get("selectChooseStatus"));
        selectChooseStatus1.click();
        LogFunction.logInfo("点开：状态选择框");
//        状态，选择：停用
        WebElement BlockUp = l.getElement(param.get("selectChooseStatusBlockUp"));
        String text8 = BlockUp.getText();
        BlockUp.click();
        LogFunction.logInfo("选择状态：" + text8);
        AssertFunction.verifyEquals(driver, text8, "停用");
//        点击，筛选，确定
        WebElement selectAffirm1 = l.getElement(param.get("commonSelectAffirm"));
        String text22 = selectAffirm1.getText();
        selectAffirm1.click();
        LogFunction.logInfo("点击：" + text22);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");

//        校验,规则名称
        WebElement SelectRulesName = l.getElement(param.get("commonSelectResultTwo"));
        String text1 = SelectRulesName.getText();
        LogFunction.logInfo("规则名称为：" + text1);
        AssertFunction.verifyEquals(driver, text1, param.get("alarmScreenEditStrategyNameValue"));
//        校验,节点类型
        WebElement selectNodeType = l.getElement(param.get("commonSelectResultThree"));
        String text4 = selectNodeType.getText();
        LogFunction.logInfo("节点类型为：" + text4);
        AssertFunction.verifyEquals(driver, text4, Oracal);
//        校验,域
        WebElement selectDomain = l.getElement(param.get("commonSelectResultFour"));
        String text5 = selectDomain.getText();
        LogFunction.logInfo("域为：" + text5);
        AssertFunction.verifyEquals(driver, text5, Domain);
//        校验,优先级
        WebElement selectPriority = l.getElement(param.get("commonSelectResultFive"));
        String text6 = selectPriority.getText();
        LogFunction.logInfo("优先级为：" + text6);
        AssertFunction.verifyEquals(driver, text6, Priority);
//        校验,状态
        WebElement selectStatus = l.getElement(param.get("commonSelectResultEight"));
        String title = selectStatus.getAttribute("title");
        LogFunction.logInfo("状态为：" + title);
        AssertFunction.verifyEquals(driver, title, "已禁用");
        //        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
        LogFunction.logInfo("-----------------告警通知，告警筛选筛选查询完成---------------------");
    }

    //    集中告警-告警通知-告警筛选-编辑-筛选
    @Test(dataProvider = "xmldata")
    public void alarmInformAlarmScreenEditSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        验证，筛选区域，是否展示
        WebElement SelectArea = l.getElement(param.get("denoiseStrategySelectArea"));
        boolean displayed = SelectArea.isDisplayed();
        if (displayed == false) {
//        点击，筛选
            co.selectO(param);
        }
//        点击，清空
        WebElement commonSelect = l.getElement(param.get("Clear"));
        String text = commonSelect.getText();
        commonSelect.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "清空");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmScreenEditStrategyNameValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmScreenEditStrategyNameValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定");
        co.sleep(1000);
//        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
        LogFunction.logInfo("-----------------告警通知，告警筛选筛选查询完成---------------------");
    }

    //    集中告警-告警通知-告警筛选-删除及筛选验证
    @Test(dataProvider = "xmldata")
    public void alarmInformAlarmScreenDeleteAndSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        验证，筛选区域，是否展示
        Boolean displayed = l.getElementIsDisplay(param.get("denoiseStrategySelectArea"));
//        WebElement SelectArea = l.getElement(param.get("denoiseStrategySelectArea"));
//        boolean displayed = SelectArea.isDisplayed();
        if (displayed == false) {
//        点击，筛选
            co.selectO(param);
//            WebElement commonSelect = l.getElement(param.get("commonSelect"));
//            String text = commonSelect.getText();
//            commonSelect.click();
//            LogFunction.logInfo("点击：" + text);
//            AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
        }
//        点击，清空
        WebElement commonSelect = l.getElement(param.get("Clear"));
        String text = commonSelect.getText();
        commonSelect.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "清空");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmScreenEditStrategyNameValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmScreenEditStrategyNameValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
//        点击，删除，并校验
        co.alarmRulesDelete(param, "AlarmScreenScreenResultNull", "alarmScreenEditStrategyNameValue");
        LogFunction.logInfo("-----------------告警通知，告警筛选筛选查询完成---------------------");
    }

    //    集中告警-告警通知-告警筛选-启用，告警策略
    @Test(dataProvider = "xmldata")
    public void alarmInformAlarmScreenStartUsing(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        勾选，规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        if (selectChooseRules.isSelected() == false) {
            selectChooseRules.click();
            LogFunction.logInfo("勾选筛选结果规则");
        }
//        点击，启用按钮
        WebElement commonStartUsing = l.getElement(param.get("commonStartUsing"));
        String text2 = commonStartUsing.getText();
        commonStartUsing.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "启用", "----是否点击的是：启用按钮----");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //        校验,状态
        WebElement selectStatus = l.getElement(param.get("commonSelectResultEight"));
        String title = selectStatus.getAttribute("title");
        LogFunction.logInfo("状态为：" + title);
        AssertFunction.verifyEquals(driver, title, "已启用");
        LogFunction.logInfo("------------------告警规则，成功启用---------------------");

    }

    //    集中告警-告警通知-告警筛选-禁用，告警策略
    @Test(dataProvider = "xmldata")
    public void alarmInformAlarmScreenBlockUp(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        勾选，规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        if (selectChooseRules.isSelected() == false) {
            selectChooseRules.click();
            LogFunction.logInfo("勾选筛选结果规则");
        }
//        点击，停用按钮
        WebElement commonBlockUp = l.getElement(param.get("commonBlockUp"));
        String text2 = commonBlockUp.getText();
        commonBlockUp.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "停用", "----是否点击的是：停用按钮----");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text3 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "确定");
        //        校验,状态
        WebElement selectStatus = l.getElement(param.get("commonSelectResultEight"));
        String title = selectStatus.getAttribute("title");
        LogFunction.logInfo("状态为：" + title);
        AssertFunction.verifyEquals(driver, title, "已禁用");
        LogFunction.logInfo("------------------告警规则，成功禁用---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警关联策略-新建第1部分，基础设置
    @Test(dataProvider = "xmldata")
    public void alarmRelevanceRulesCreateBasicSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//          点击，新建
        WebElement commonCreate = l.getElement(param.get("commonCreate"));
        String text1 = commonCreate.getText();
        commonCreate.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "新建", "----是否点击新建----");
//          录入，规则名称
        WebElement rulesName = l.getElement(param.get("rulesName"));
        rulesName.sendKeys(param.get("alarmRelevanceRulesNameValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("alarmRelevanceRulesNameValue"));
//          选择，优先级，高
        WebElement priority = l.getElement(param.get("priority"));
        Priority = priority.getText();
        priority.click();
        LogFunction.logInfo("勾选优先级" + Priority);
//        点击，基础设置，下一步
        WebElement basicsNextStep = l.getElement(param.get("basicsNextStep"));
        String text3 = basicsNextStep.getText();
        basicsNextStep.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警关联策略，第一步，基础设置录入完成---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警关联策略-新建第2部分，根源告警设置
    @Test(dataProvider = "xmldata")
    public void alarmRelevanceRulesCreateRootAlarmSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//         点击，域
        WebElement domain = l.getElement(param.get("rootJurisdictionDomain"));
        domain.click();
        LogFunction.logInfo("点击：域");
//        选择，域：rootDomain
        WebElement chooseDomain = l.getElement(param.get("chooseDomain"));
        Domain = chooseDomain.getText();
        chooseDomain.click();
        LogFunction.logInfo("选择域：" + Domain);
        AssertFunction.verifyEquals(driver, Domain, "rootDomain", "----验证选择的域是否为：rootDomain----");
//          点击，类型
        WebElement type = l.getElement(param.get("type"));
        type.click();
        LogFunction.logInfo("点击：类型");
//        选择,类型:操作系统
        WebElement chooseType = l.getElement(param.get("chooseType"));
        Oracal = chooseType.getText();
        chooseType.click();
        LogFunction.logInfo("选择类型：" + Oracal);
        AssertFunction.verifyEquals(driver, Oracal, "Oracle", "----验证选择的类型是否为；Oracle----");
//        点击，告警类型选择
//        WebElement alarmType = l.getElement(param.get("alarmType"));
//        alarmType.click();
//        LogFunction.logInfo("点击：告警类型选择");
//        选择,告警类型选择:Oracle_System_Alert
//        WebElement chooseAlarmType = l.getElement(param.get("chooseAlarmType"));
//        String text6 = chooseAlarmType.getText();
//        chooseAlarmType.click();
//        LogFunction.logInfo("选择告警类型：" + text6);
//        AssertFunction.verifyEquals(driver, text6, "Oracle_System_Alert", "----验证选择的告警类型是否为；Oracle_System_Alert----");
//       点击，规则条件设置，下一步
        WebElement rulesConditionConfigNextStep = l.getElement(param.get("rulesConditionConfigNextStep"));
        String text7 = rulesConditionConfigNextStep.getText();
        rulesConditionConfigNextStep.click();
        LogFunction.logInfo("点击：" + text7);
        AssertFunction.verifyEquals(driver, text7, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警关联策略，第二步，根源告警设置录入完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警关联策略-新建第3部分，规则高级设置
    @Test(dataProvider = "xmldata")
    public void alarmRelevanceRulesCreateRulesAdvancedSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//       点击，规则条件高级设置，下一步
        WebElement rulesConditionAdvancedConfigNextStep = l.getElement(param.get("nextstep"));
        String text71 = rulesConditionAdvancedConfigNextStep.getText();
        rulesConditionAdvancedConfigNextStep.click();
        LogFunction.logInfo("点击：" + text71);
        AssertFunction.verifyEquals(driver, text71, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警关联策略，第三步，规则高级设置录入完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警关联策略-新建第4部分，影响告警设置-新建
    @Test(dataProvider = "xmldata")
    public void alarmRelevanceRulesCreateAffectAlarmSettingCreate(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());

//        验证，新建区域是否打开
        Boolean createArea = l.getElementIsDisplay(param.get("CreateArea"));
        LogFunction.logInfo("新建区域是否打开" + createArea);
        if (createArea == false) {
//            点击，新建
            WebElement createButton = l.getElement(param.get("CreateButton"));
            String text = createButton.getText();
            AssertFunction.verifyEquals(driver, text, "新建");
            createButton.click();
            LogFunction.logInfo("点击：" + text);
        }
//         点击，域
//        WebElement domain = l.getElement(param.get("Domain"));
//        domain.click();
//        LogFunction.logInfo("点击：域");
//        选择，域：rootDomain
//        WebElement chooseDomain = l.getElement(param.get("chooseDomain"));
//        Domain = chooseDomain.getText();
//        chooseDomain.click();
//        LogFunction.logInfo("选择域：" + Domain);
//        AssertFunction.verifyEquals(driver, Domain, "rootDomain", "----验证选择的域是否为：rootDomain----");
//          点击，节点类型
        WebElement type = l.getElement(param.get("type"));
        type.click();
        LogFunction.logInfo("点击：节点类型");
//        选择,类型:操作系统
        WebElement chooseType = l.getElement(param.get("chooseType"));
        Oracal = chooseType.getText();
        chooseType.click();
        LogFunction.logInfo("选择类型：" + Oracal);
        AssertFunction.verifyEquals(driver, Oracal, "Oracle", "----验证选择的类型是否为；Oracle----");


//        选择,节点关系:
        co.modelClickAndChooseValue(param,"NodeRelation","NodeRelationValue","节点过滤");
        //      点击，保存
        WebElement save = l.getElement(param.get("save"));
        String text10 = save.getText();
        save.click();
        LogFunction.logInfo("点击：" + text10);
        AssertFunction.verifyEquals(driver, text10, "保存");
//        校验,节点类型
        WebElement selectNodeType = l.getElement(param.get("ResultsNodeType"));
        String text4 = selectNodeType.getText();
        LogFunction.logInfo("节点类型为：" + text4);
        AssertFunction.verifyEquals(driver, text4, Oracal);

//        勾选结果
        co.modelRadioBox(param, "ChooseResult");
        LogFunction.logInfo("-----------------告警关联策略，最后一步之影响告警设置，新建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警关联策略-新建第4部分，影响告警设置-编辑
    @Test(dataProvider = "xmldata")
    public void alarmRelevanceRulesCreateAffectAlarmSettingEdit(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//            点击，编辑
        co.modelClickButton(param,"EditButton");
//          点击，类型
        WebElement type = l.getElement(param.get("type"));
        type.click();
        LogFunction.logInfo("点击：类型");
//        选择,类型:资源
        WebElement chooseType = l.getElement(param.get("chooseType"));
        Oracal = chooseType.getText();
        chooseType.click();
        LogFunction.logInfo("选择类型：" + Oracal);
        AssertFunction.verifyEquals(driver, Oracal, "资源");
        //          点击，节点关系
        co.modelClickAndChooseValue(param,"NodeRelation","NodeRelationValue","节点过滤");
//        WebElement NodeRelation = l.getElement(param.get("NodeRelation"));
//        NodeRelation.click();
//        LogFunction.logInfo("点击：节点关系");
//        co.sleep(3000);
////        选择,节点关系:
//        WebElement NodeRelationValue = l.getElement(param.get("NodeRelationValue"));
//        String text1 = NodeRelationValue.getText();
//        NodeRelationValue.click();
//        LogFunction.logInfo("选择节点关系：" + text1);
//      点击，保存
        WebElement save = l.getElement(param.get("save"));
        String text10 = save.getText();
        save.click();
        LogFunction.logInfo("点击：" + text10);
        AssertFunction.verifyEquals(driver, text10, "保存");
//        校验,节点类型
        WebElement selectNodeType = l.getElement(param.get("ResultsNodeType"));
        String text4 = selectNodeType.getText();
        LogFunction.logInfo("节点类型为：" + text4);
        AssertFunction.verifyEquals(driver, text4, Oracal);

        //        勾选结果
        co.modelRadioBox(param, "ChooseResult");
        LogFunction.logInfo("-----------------告警关联策略，最后一步之影响告警设置，编辑成功---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警关联策略-新建第4部分，影响告警设置-删除
    @Test(dataProvider = "xmldata")
    public void alarmRelevanceRulesCreateAffectAlarmSettingDelete(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//      点击，删除
        WebElement deleteButton = l.getElement(param.get("DeleteButton"));
        String text = deleteButton.getText();
        AssertFunction.verifyEquals(driver, text, "删除");
        deleteButton.click();
        LogFunction.logInfo("点击：" + text);
        //        删除，确认
        co.alarmHintAndConfirm(param, "确定要删除？");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //      检验，是否删除
        co.selectResultIsNull(param, "ResultEmpty");
        LogFunction.logInfo("-----------------告警关联策略，最后一步之影响告警设置，删除成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警关联策略-新建第4部分，影响告警设置-保存
    @Test(dataProvider = "xmldata")
    public void alarmRelevanceRulesCreateAffectAlarmSettingSave(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        //          点击，保存
        WebElement alarmClassifyConfigSave = l.getElement(param.get("alarmClassifyConfigSave"));
        String text101 = alarmClassifyConfigSave.getText();
        alarmClassifyConfigSave.click();
        LogFunction.logInfo("点击：" + text101);
        AssertFunction.verifyEquals(driver, text101, "保 存");
        LogFunction.logInfo("-----------------告警关联策略，最后一步，影响告警设置，保存成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警关联策略-筛选及校验
    @Test(dataProvider = "xmldata")
    public void alarmRelevanceRulesCreateSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，筛选
        co.selectO(param);
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmRelevanceRulesNameValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmRelevanceRulesNameValue"));
//        点击，状态选择框
        WebElement selectChooseStatus = l.getElement(param.get("selectChooseStatus"));
        selectChooseStatus.click();
        LogFunction.logInfo("点开：状态选择框");
//        状态，选择：启用
        WebElement StartUsing = l.getElement(param.get("selectChooseStatusStartUsing"));
        String text7 = StartUsing.getText();
        StartUsing.click();
        LogFunction.logInfo("选择状态：" + text7);
        AssertFunction.verifyEquals(driver, text7, "启用");
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        校验,规则名称
        WebElement SelectRulesName = l.getElement(param.get("commonDenoiseStrategySelectRulesNameValue"));
        String text1 = SelectRulesName.getText();
        LogFunction.logInfo("规则名称为：" + text1);
        AssertFunction.verifyEquals(driver, text1, param.get("alarmRelevanceRulesNameValue"));
//        校验,规则类型
        WebElement selectRulesType = l.getElement(param.get("commonDenoiseStrategySelectRulesTypeValue"));
        String text3 = selectRulesType.getText();
        LogFunction.logInfo("规则类型为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "关联");
//        校验,节点类型
        WebElement selectNodeType = l.getElement(param.get("commonDenoiseStrategySelectNodeTypeValue"));
        String text4 = selectNodeType.getText();
        LogFunction.logInfo("节点类型为：" + text4);
        AssertFunction.verifyEquals(driver, text4, "Oracle");
//        校验,域
        WebElement selectDomain = l.getElement(param.get("commonDenoiseStrategySelectDomainValue"));
        String text5 = selectDomain.getText();
        LogFunction.logInfo("域为：" + text5);
        AssertFunction.verifyEquals(driver, text5, Domain);
//        校验,优先级
        WebElement selectPriority = l.getElement(param.get("commonDenoiseStrategySelectPriorityValue"));
        String text6 = selectPriority.getText();
        LogFunction.logInfo("优先级为：" + text6);
        AssertFunction.verifyEquals(driver, text6, Priority);
//        校验,状态
        WebElement selectStatus = l.getElement(param.get("commonDenoiseStrategySelectStatusValue"));
        String title = selectStatus.getAttribute("title");
        LogFunction.logInfo("状态为：" + title);
        AssertFunction.verifyEquals(driver, title, "已启用");
        //        勾选,规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
        LogFunction.logInfo("-----------------降噪策略，告警关联策略，新建及筛选校验通过---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警关联规则-编辑，第1部分，基础设置
    @Test(dataProvider = "xmldata")
    public void alarmRelevanceRulesEditBasicSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//          点击，编辑
        WebElement commonEdit = l.getElement(param.get("commonEdit"));
        String text1 = commonEdit.getText();
        commonEdit.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "编辑", "----是否点击编辑----");
//          录入，规则名称
        WebElement rulesName = l.getElement(param.get("rulesName"));
        rulesName.clear();
        rulesName.sendKeys(param.get("alarmRelevanceRulesNameEditValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("alarmRelevanceRulesNameEditValue"));
//          选择，优先级，低
        WebElement priority = l.getElement(param.get("priorityLow"));
        Priority = priority.getText();
        priority.click();
        LogFunction.logInfo("勾选优先级" + Priority);
//        关闭，启动
        WebElement startOrClose = l.getElement(param.get("startOrClose"));
        boolean selected = startOrClose.isSelected();
        LogFunction.logInfo(String.valueOf(selected));
        if (selected == true) {
            startOrClose.click();
            LogFunction.logInfo("状态更改为：停用");
        }

//        点击，基础设置，下一步
        WebElement basicsNextStep = l.getElement(param.get("basicsNextStep"));
        String text3 = basicsNextStep.getText();
        basicsNextStep.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警关联规则，第一步，基础设置编辑完成---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警关联规则-编辑，第2部分，规则条件设置
    @Test(dataProvider = "xmldata")
    public void alarmRelevanceRulesEditRulesConditionSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//       点击，规则条件设置，下一步
        WebElement rulesConditionConfigNextStep = l.getElement(param.get("rulesConditionConfigNextStep"));
        String text7 = rulesConditionConfigNextStep.getText();
        rulesConditionConfigNextStep.click();
        LogFunction.logInfo("点击：" + text7);
        AssertFunction.verifyEquals(driver, text7, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警关联规则，第二步，规则条件设置编辑完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警关联规则-编辑，第3部分，规则条件高级设置
    @Test(dataProvider = "xmldata")
    public void alarmRelevanceRulesEditRulesConditionAdvancedSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//       点击，规则条件高级设置，下一步
        WebElement NextStep = l.getElement(param.get("rulesConditionAdvancedConfigNextStep"));
        String text71 = NextStep.getText();
        AssertFunction.verifyEquals(driver, text71, "下一步", "----验证是否点击：下一步----");
        NextStep.click();
        LogFunction.logInfo("点击：" + text71);
        LogFunction.logInfo("-----------------告警关联规则，第三步，规则条件高级设置编辑完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警关联规则-编辑，第4部分，告警分类设置
    @Test(dataProvider = "xmldata")
    public void alarmRelevanceRulesEditAlarmClassifySetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，告警内容
//        WebElement alarmContent = l.getElement(param.get("alarmContent"));
//        String text = alarmContent.getText();
//        alarmContent.click();
//        LogFunction.logInfo("点击：告警内容");
////        最大合并数量,录入，5
//        WebElement maxMergeNumber = l.getElement(param.get("maxMergeNumber"));
//        maxMergeNumber.sendKeys(param.get("maxMergeNumberValue"));
//        LogFunction.logInfo("录入最大合并数量：" + param.get("maxMergeNumberValue"));
////        合并时间窗口，录入，1
//        WebElement mergeTimeWindows = l.getElement(param.get("mergeTimeWindows"));
//        mergeTimeWindows.sendKeys(param.get("maxMergeNumberValue"));
//        LogFunction.logInfo("录入合并时间窗口：" + param.get("mergeTimeWindowsValue"));

//          点击，保存
        WebElement alarmMergeConfigSave = l.getElement(param.get("alarmClassifyConfigSave"));
        String text10 = alarmMergeConfigSave.getText();
        alarmMergeConfigSave.click();
        LogFunction.logInfo("点击：" + text10);
        LogFunction.logInfo("-----------------告警关联规则，最后一步，告警关联设置编辑完成且创建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警关联规则-编辑-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyalarmRelevanceRulesEditSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，筛选
//        WebElement commonSelect = l.getElement(param.get("commonSelect"));
//        String text = commonSelect.getText();
//        commonSelect.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmRelevanceRulesNameEditValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmRelevanceRulesNameEditValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        获取，告警信息列表信息，无数据
        WebElement NumberValue = l.getElement(param.get("alarmRulesListNumberValue"));
        String text55 = NumberValue.getText();
        LogFunction.logInfo("告警列表信息为：" + text55);
        //        点击，状态选择框
        WebElement selectChooseStatus1 = l.getElement(param.get("selectChooseStatus"));
        selectChooseStatus1.click();
        LogFunction.logInfo("点开：状态选择框");
//        状态，选择：停用
        WebElement BlockUp = l.getElement(param.get("selectChooseStatusBlockUp"));
        String text8 = BlockUp.getText();
        BlockUp.click();
        LogFunction.logInfo("选择状态：" + text8);
        AssertFunction.verifyEquals(driver, text8, "停用");
//        点击，筛选，确定
        WebElement selectAffirm1 = l.getElement(param.get("commonSelectAffirm"));
        String text22 = selectAffirm1.getText();
        selectAffirm1.click();
        LogFunction.logInfo("点击：" + text22);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        校验,规则名称
        WebElement SelectRulesName = l.getElement(param.get("commonDenoiseStrategySelectRulesNameValue"));
        String text1 = SelectRulesName.getText();
        LogFunction.logInfo("规则名称为：" + text1);
        AssertFunction.verifyEquals(driver, text1, param.get("alarmRelevanceRulesNameEditValue"));
//        校验,规则类型
        WebElement selectRulesType = l.getElement(param.get("commonDenoiseStrategySelectRulesTypeValue"));
        String text3 = selectRulesType.getText();
        LogFunction.logInfo("规则类型为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "关联");
//        校验,节点类型
        WebElement selectNodeType = l.getElement(param.get("commonDenoiseStrategySelectNodeTypeValue"));
        String text4 = selectNodeType.getText();
        LogFunction.logInfo("节点类型为：" + text4);
        AssertFunction.verifyEquals(driver, text4, "Oracle");
//        校验,域
        WebElement selectDomain = l.getElement(param.get("commonDenoiseStrategySelectDomainValue"));
        String text5 = selectDomain.getText();
        LogFunction.logInfo("域为：" + text5);
        AssertFunction.verifyEquals(driver, text5, Domain);
//        校验,优先级
        WebElement selectPriority = l.getElement(param.get("commonDenoiseStrategySelectPriorityValue"));
        String text6 = selectPriority.getText();
        LogFunction.logInfo("优先级为：" + text6);
        AssertFunction.verifyEquals(driver, text6, Priority);
//        校验,状态
        WebElement selectStatus = l.getElement(param.get("commonDenoiseStrategySelectStatusValue"));
        String title = selectStatus.getAttribute("title");
        LogFunction.logInfo("状态为：" + title);
        AssertFunction.verifyEquals(driver, title, "已禁用");
        //        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
        LogFunction.logInfo("-----------------降噪策略,告警关联规则,编辑筛选校验通过---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警关联策略-编辑-筛选
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmRelevanceRulesEditSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        验证，筛选区域，是否展示
        WebElement SelectArea = l.getElement(param.get("denoiseStrategySelectArea"));
        boolean displayed = SelectArea.isDisplayed();
        if (displayed == false) {
//        点击，筛选
            co.selectO(param);
//            WebElement commonSelect = l.getElement(param.get("commonSelect"));
//            String text = commonSelect.getText();
//            commonSelect.click();
//            LogFunction.logInfo("点击：" + text);
//            AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
        }
//        点击，清空
        WebElement commonSelect = l.getElement(param.get("Clear"));
        String text = commonSelect.getText();
        commonSelect.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "清空");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmRelevanceRulesNameEditValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmRelevanceRulesNameEditValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
        LogFunction.logInfo("-----------------降噪策略,告警关联策略,筛选查询完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警关联策略-删除及筛选校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmRelevanceRulesDeleteAndSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        验证，筛选区域，是否展示
        WebElement SelectArea = l.getElement(param.get("denoiseStrategySelectArea"));
        boolean displayed = SelectArea.isDisplayed();
        if (displayed == false) {
//        点击，筛选
            co.selectO(param);
        }
//        点击，清空
        WebElement commonSelect = l.getElement(param.get("Clear"));
        String text = commonSelect.getText();
        commonSelect.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "清空");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmRelevanceRulesNameEditValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmRelevanceRulesNameEditValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
        co.alarmRulesDelete(param, "alarmRelevanceRulesNameEditValue");
        LogFunction.logInfo("-----------------降噪策略,告警关联策略,删除及筛选校验完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警过滤策略-新建第1部分，基础设置
    @Test(dataProvider = "xmldata")
    public void alarmFilterRulesCreateBasicSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//          点击，新建
        WebElement commonCreate = l.getElement(param.get("commonCreate"));
        String text1 = commonCreate.getText();
        commonCreate.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "新建", "----是否点击新建----");
//          录入，规则名称
        WebElement rulesName = l.getElement(param.get("rulesName"));
        rulesName.sendKeys(param.get("alarmFilterRulesNameValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("alarmFilterRulesNameValue"));
//          选择，优先级，高
        WebElement priority = l.getElement(param.get("priority"));
        Priority = priority.getText();
        priority.click();
        LogFunction.logInfo("勾选优先级" + Priority);
//        点击，基础设置，下一步
        WebElement basicsNextStep = l.getElement(param.get("basicsNextStep"));
        String text3 = basicsNextStep.getText();
        basicsNextStep.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警过滤策略，第一步，基础设置录入完成---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警过滤策略-新建第2部分，规则条件设置
    @Test(dataProvider = "xmldata")
    public void alarmFilterRulesCreateRulesConditionSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//         点击，域
        WebElement domain = l.getElement(param.get("domain"));
        domain.click();
        LogFunction.logInfo("点击：域");
//        选择，域：rootDomain
        WebElement chooseDomain = l.getElement(param.get("chooseDomain"));
        Domain = chooseDomain.getText();
        chooseDomain.click();
        LogFunction.logInfo("选择域：" + Domain);
        AssertFunction.verifyEquals(driver, Domain, "rootDomain", "----验证选择的域是否为：rootDomain----");
//          点击，类型
        WebElement type = l.getElement(param.get("type"));
        type.click();
        LogFunction.logInfo("点击：类型");
//        选择,类型:操作系统
        WebElement chooseType = l.getElement(param.get("chooseType"));
        Oracal = chooseType.getText();
        chooseType.click();
        LogFunction.logInfo("选择类型：" + Oracal);
        AssertFunction.verifyEquals(driver, Oracal, "Oracle", "----验证选择的类型是否为；Oracle----");
//        点击，告警类型选择
//        WebElement alarmType = l.getElement(param.get("alarmType"));
//        alarmType.click();
//        LogFunction.logInfo("点击：告警类型选择");
//        选择,告警类型选择:Oracle_System_Alert
//        WebElement chooseAlarmType = l.getElement(param.get("chooseAlarmType"));
//        String text6 = chooseAlarmType.getText();
//        chooseAlarmType.click();
//        LogFunction.logInfo("选择告警类型：" + text6);
//        AssertFunction.verifyEquals(driver, text6, "Oracle_System_Alert", "----验证选择的告警类型是否为；Oracle_System_Alert----");
//       点击，规则条件设置，下一步
        WebElement rulesConditionConfigNextStep = l.getElement(param.get("rulesConditionConfigNextStep"));
        String text7 = rulesConditionConfigNextStep.getText();
        rulesConditionConfigNextStep.click();
        LogFunction.logInfo("点击：" + text7);
        AssertFunction.verifyEquals(driver, text7, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警过滤策略，第二步，规则条件设置录入完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警过滤策略-新建最后一部分，规则条件高级设置
    @Test(dataProvider = "xmldata")
    public void alarmFilterRulesCreateRulesConditionAdvancedSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        录入，内容关键字
        WebElement contentKeyword = l.getElement(param.get("contentKeyword"));
        contentKeyword.clear();
        contentKeyword.sendKeys(param.get("contentKeywordValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("contentKeywordValue"));
//       点击，规则条件高级设置，保存
        WebElement rulesConditionAdvancedConfigNextStep = l.getElement(param.get("alarmClassifyConfigSave"));
        String text71 = rulesConditionAdvancedConfigNextStep.getText();
        rulesConditionAdvancedConfigNextStep.click();
        LogFunction.logInfo("点击：" + text71);
        LogFunction.logInfo("-----------------告警过滤策略，最后一部分，规则条件高级设置录入完成---------------------");
    }


    //    集中告警-告警配置-降噪策略-告警过滤策略-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyalarmFilterRulesSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，筛选
        co.selectO(param);
//        WebElement commonSelect = l.getElement(param.get("commonSelect"));
//        String text = commonSelect.getText();
//        commonSelect.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");

//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmFilterRulesNameValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmFilterRulesNameValue"));
//        点击，状态选择框
        WebElement selectChooseStatus = l.getElement(param.get("selectChooseStatus"));
        selectChooseStatus.click();
        LogFunction.logInfo("点开：状态选择框");

//        状态，选择：启用
        WebElement StartUsing = l.getElement(param.get("selectChooseStatusStartUsing"));
        String text7 = StartUsing.getText();
        StartUsing.click();
        LogFunction.logInfo("选择状态：" + text7);
        AssertFunction.verifyEquals(driver, text7, "启用");
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        勾选,规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
//        校验,规则名称
        WebElement SelectRulesName = l.getElement(param.get("commonDenoiseStrategySelectRulesNameValue"));
        String text1 = SelectRulesName.getText();
        LogFunction.logInfo("规则名称为：" + text1);
        AssertFunction.verifyEquals(driver, text1, param.get("alarmFilterRulesNameValue"));
//        校验,规则类型
        WebElement selectRulesType = l.getElement(param.get("commonDenoiseStrategySelectRulesTypeValue"));
        String text3 = selectRulesType.getText();
        LogFunction.logInfo("规则类型为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "过滤");
//        校验,节点类型
        WebElement selectNodeType = l.getElement(param.get("commonDenoiseStrategySelectNodeTypeValue"));
        String text4 = selectNodeType.getText();
        LogFunction.logInfo("节点类型为：" + text4);
        AssertFunction.verifyEquals(driver, text4, Oracal);
//        校验,域
        WebElement selectDomain = l.getElement(param.get("commonDenoiseStrategySelectDomainValue"));
        String text5 = selectDomain.getText();
        LogFunction.logInfo("域为：" + text5);
        AssertFunction.verifyEquals(driver, text5, Domain);
//        校验,优先级
        WebElement selectPriority = l.getElement(param.get("commonDenoiseStrategySelectPriorityValue"));
        String text6 = selectPriority.getText();
        LogFunction.logInfo("优先级为：" + text6);
        AssertFunction.verifyEquals(driver, text6, Priority);
//        校验,状态
        WebElement selectStatus = l.getElement(param.get("commonDenoiseStrategySelectStatusValue"));
        String title = selectStatus.getAttribute("title");
        LogFunction.logInfo("状态为：" + title);
        AssertFunction.verifyEquals(driver, title, "已启用");
        LogFunction.logInfo("-----------------降噪策略，告警过滤策略筛选查询校验完成完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警过滤策略-编辑，第1部分，基础设置
    @Test(dataProvider = "xmldata")
    public void alarmFilterRulesEditBasicSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//          点击，编辑
        WebElement commonEdit = l.getElement(param.get("commonEdit"));
        String text1 = commonEdit.getText();
        commonEdit.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "编辑", "----是否点击编辑----");
//          录入，规则名称
        WebElement rulesName = l.getElement(param.get("rulesName"));
        rulesName.clear();
        rulesName.sendKeys(param.get("alarmFilterRulesNameEditValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("alarmFilterRulesNameEditValue"));
//          选择，优先级，低
        WebElement priority = l.getElement(param.get("priorityLow"));
        Priority = priority.getText();
        priority.click();
        LogFunction.logInfo("勾选优先级" + Priority);
//        关闭，启动
        WebElement startOrClose = l.getElement(param.get("startOrClose"));
        boolean selected = startOrClose.isSelected();
        LogFunction.logInfo(String.valueOf(selected));
        if (selected == true) {
            startOrClose.click();
            LogFunction.logInfo("状态更改为：停用");
        }

//        点击，基础设置，下一步
        WebElement basicsNextStep = l.getElement(param.get("basicsNextStep"));
        String text3 = basicsNextStep.getText();
        basicsNextStep.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警过滤策略，第一步，基础设置编辑完成---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警过滤策略-编辑，第2部分，规则条件设置
    @Test(dataProvider = "xmldata")
    public void alarmFilterRulesEditRulesConditionSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//         点击，域
//        WebElement domain = l.getElement(param.get("domain"));
//        domain.click();
//        LogFunction.logInfo("点击：域");
////        选择，域：rootDomain
//        WebElement chooseDomain = l.getElement(param.get("chooseDomain"));
//        Domain = chooseDomain.getText();
//        chooseDomain.click();
//        LogFunction.logInfo("选择域：" + Domain);
//        AssertFunction.verifyEquals(driver, Domain, "rootDomain", "----验证选择的域是否为：rootDomain----");
////          点击，类型
//        WebElement type = l.getElement(param.get("type"));
//        type.click();
//        LogFunction.logInfo("点击：类型");
////        选择,类型:操作系统
//        WebElement chooseType = l.getElement(param.get("chooseType"));
//        Oracal = chooseType.getText();
//        chooseType.click();
//        LogFunction.logInfo("选择类型：" + Oracal);
//        AssertFunction.verifyEquals(driver, Oracle, "操作系统", "----验证选择的类型是否为；Oracle----");
//        点击，告警类型选择
//        WebElement alarmType = l.getElement(param.get("alarmType"));
//        alarmType.click();
//        LogFunction.logInfo("点击：告警类型选择");
////        选择,告警类型选择:Oracle_System_Alert
//        WebElement chooseAlarmType = l.getElement(param.get("chooseAlarmType"));
//        String text6 = chooseAlarmType.getText();
//        chooseAlarmType.click();
//        LogFunction.logInfo("选择告警类型：" + text6);
//        AssertFunction.verifyEquals(driver, text6, "Oracle_System_Alert", "----验证选择的告警类型是否为；Oracle_System_Alert----");
//       点击，规则条件设置，下一步
        WebElement rulesConditionConfigNextStep = l.getElement(param.get("rulesConditionConfigNextStep"));
        String text7 = rulesConditionConfigNextStep.getText();
        rulesConditionConfigNextStep.click();
        LogFunction.logInfo("点击：" + text7);
        AssertFunction.verifyEquals(driver, text7, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警过滤策略，第二步，规则条件设置编辑完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警过滤策略-编辑，第3部分，规则条件高级设置
    @Test(dataProvider = "xmldata")
    public void alarmFilterRulesEditRulesConditionAdvancedSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//       点击，规则条件高级设置，保存
        WebElement rulesConditionAdvancedConfigNextStep = l.getElement(param.get("alarmClassifyConfigSave"));
        String text71 = rulesConditionAdvancedConfigNextStep.getText();
        rulesConditionAdvancedConfigNextStep.click();
        LogFunction.logInfo("点击：" + text71);
        LogFunction.logInfo("-----------------告警过滤策略，最后一步，规则条件高级设置编辑完成---------------------");
    }


    //    集中告警-告警配置-降噪策略-告警过滤策略-编辑-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyalarmFilterRulesEditSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，筛选
//        WebElement commonSelect = l.getElement(param.get("commonSelect"));
//        String text = commonSelect.getText();
//        commonSelect.click();
//        LogFunction.logInfo("点击：" + text);
//        AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmFilterRulesNameEditValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmFilterRulesNameEditValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        获取，告警信息列表信息，无数据
        WebElement NumberValue = l.getElement(param.get("alarmRulesListNumberValue"));
        String text55 = NumberValue.getText();
        LogFunction.logInfo("告警列表信息为：" + text55);
        //        点击，状态选择框
        WebElement selectChooseStatus1 = l.getElement(param.get("selectChooseStatus"));
        selectChooseStatus1.click();
        LogFunction.logInfo("点开：状态选择框");
//        状态，选择：停用
        WebElement BlockUp = l.getElement(param.get("selectChooseStatusBlockUp"));
        String text8 = BlockUp.getText();
        BlockUp.click();
        LogFunction.logInfo("选择状态：" + text8);
        AssertFunction.verifyEquals(driver, text8, "停用");
//        点击，筛选，确定
        WebElement selectAffirm1 = l.getElement(param.get("commonSelectAffirm"));
        String text22 = selectAffirm1.getText();
        selectAffirm1.click();
        LogFunction.logInfo("点击：" + text22);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");

        //        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
//        校验,规则名称
        WebElement SelectRulesName = l.getElement(param.get("commonDenoiseStrategySelectRulesNameValue"));
        String text1 = SelectRulesName.getText();
        LogFunction.logInfo("规则名称为：" + text1);
        AssertFunction.verifyEquals(driver, text1, param.get("alarmFilterRulesNameEditValue"));
//        校验,规则类型
        WebElement selectRulesType = l.getElement(param.get("commonDenoiseStrategySelectRulesTypeValue"));
        String text3 = selectRulesType.getText();
        LogFunction.logInfo("规则类型为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "过滤");
//        校验,节点类型
        WebElement selectNodeType = l.getElement(param.get("commonDenoiseStrategySelectNodeTypeValue"));
        String text4 = selectNodeType.getText();
        LogFunction.logInfo("节点类型为：" + text4);
        AssertFunction.verifyEquals(driver, text4, Oracal);
//        校验,域
        WebElement selectDomain = l.getElement(param.get("commonDenoiseStrategySelectDomainValue"));
        String text5 = selectDomain.getText();
        LogFunction.logInfo("域为：" + text5);
        AssertFunction.verifyEquals(driver, text5, Domain);
//        校验,优先级
        WebElement selectPriority = l.getElement(param.get("commonDenoiseStrategySelectPriorityValue"));
        String text6 = selectPriority.getText();
        LogFunction.logInfo("优先级为：" + text6);
        AssertFunction.verifyEquals(driver, text6, Priority);
//        校验,状态
        WebElement selectStatus = l.getElement(param.get("commonDenoiseStrategySelectStatusValue"));
        String title = selectStatus.getAttribute("title");
        LogFunction.logInfo("状态为：" + title);
        AssertFunction.verifyEquals(driver, title, "已禁用");
        LogFunction.logInfo("-----------------降噪策略告警过滤策略筛选查询完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警过滤策略-编辑-筛选
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyalarmFilterRulesEditSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        验证，筛选区域，是否展示
        WebElement SelectArea = l.getElement(param.get("denoiseStrategySelectArea"));
        boolean displayed = SelectArea.isDisplayed();
        if (displayed == false) {
//        点击，筛选
            co.selectO(param);
//            WebElement commonSelect = l.getElement(param.get("commonSelect"));
//            String text = commonSelect.getText();
//            commonSelect.click();
//            LogFunction.logInfo("点击：" + text);
//            AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
        }
//        点击，清空
        WebElement commonSelect = l.getElement(param.get("Clear"));
        String text = commonSelect.getText();
        commonSelect.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "清空");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmFilterRulesNameEditValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmFilterRulesNameEditValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
        LogFunction.logInfo("-----------------降噪策略告警过滤策略筛选查询完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警过滤策略-删除及筛选校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyalarmFilterRulesDeleteAndSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        验证，筛选区域，是否展示
        WebElement SelectArea = l.getElement(param.get("denoiseStrategySelectArea"));
        boolean displayed = SelectArea.isDisplayed();
        if (displayed == false) {
//        点击，筛选
            co.selectO(param);
//            WebElement commonSelect = l.getElement(param.get("commonSelect"));
//            String text = commonSelect.getText();
//            commonSelect.click();
//            LogFunction.logInfo("点击：" + text);
//            AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
        }
//        点击，清空
        WebElement commonSelect = l.getElement(param.get("Clear"));
        String text = commonSelect.getText();
        commonSelect.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "清空");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.clear();
        selectRulesName.sendKeys(param.get("alarmFilterRulesNameEditValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmFilterRulesNameEditValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
//        勾选规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        selectChooseRules.click();
        LogFunction.logInfo("勾选筛选结果规则");
        co.alarmRulesDelete(param, "alarmFilterRulesNameEditValue");
        LogFunction.logInfo("-----------------降噪策略，告警过滤策略，删除及筛选校验通过---------------------");
    }

    //    菜单-资源管理
    @Test(dataProvider = "xmldata")
    public void resourceManagement(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，资源管理
        co.modelClickButton2(param, "resourceManagement", "资源管理");
        LogFunction.logInfo("-----------------进入菜单-资源管理---------------------");
    }

    //    菜单-资源管理-资源模型
    @Test(dataProvider = "xmldata")
    public void resourceModel(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        co.openLeftMenuButton(param);
//        点击，资源模型
        co.modelClickButton2(param, "resourceModel", "资源模型");
        LogFunction.logInfo("-----------------进入菜单-资源模型---------------------");
    }

    //    菜单-资源管理-资源实例
    @Test(dataProvider = "xmldata")
    public void resourceExample(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        co.openLeftMenuButton(param);
//        点击，资源实例
        co.modelClickButton2(param, "resourceExample", "资源实例");
        LogFunction.logInfo("-----------------进入菜单-资源实例---------------------");
    }

    //    菜单-资源管理-自动发现
    @Test(dataProvider = "xmldata")
    public void automaticDiscovery(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，自动发现
        co.modelAreaDisplayAndClick(param,"automaticDiscoveryArea","automaticDiscovery");
//        co.modelClickButton(param, "automaticDiscovery");
        LogFunction.logInfo("-----------------进入菜单-自动发现---------------------");
    }

    //    菜单-资源管理-自动发现-发现规则
    @Test(dataProvider = "xmldata")
    public void discoveryRule(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，发现规则
        co.modelClickButton2(param, "discoveryRule", "发现规则");
        LogFunction.logInfo("-----------------进入菜单-自动发现-发现规则---------------------");
    }

    //    菜单-资源管理-自动发现-发现列表
    @Test(dataProvider = "xmldata")
    public void discoveryList(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，发现列表
        co.modelClickButton2(param, "discoveryList", "发现列表");
        LogFunction.logInfo("-----------------进入菜单-自动发现-发现列表---------------------");
    }


    //    资源管理-资源模型-新建CIType
    @Test(dataProvider = "xmldata")
    public void resourceModelCreateCIType(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        co.sleep(7000);
//        点击，URMP
        co.modelClickButton2(param, "URMPButton", "URMP");
        co.sleep(300);
//         点击，“+”（新增按钮）
        co.modelClickButton3(param, "add", "点击“+”新增button");
        co.sleep(1000);
//        录入，显示名称
        co.modelInputBox(param, "DisplayName", "URMPAddDisplayNameValue", "显示名称");
//         录入，名称
        co.modelInputBox(param, "Name", "URMPAddNameValue", "名称");
//        点击，保存
        co.modelClickButton2(param, "Save", "保存");
//        点击，保存提示信息及确认
        co.alarmHintAndConfirm(param, "保存成功");
//


        LogFunction.logInfo("----------------资源管理-资源模型-新建URMP完成---------------------");

    }

    //    资源管理-资源模型-新建CIType验证
    @Test(dataProvider = "xmldata")
    public void resourceModelCreateCITypeVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        co.sleep(2000);
//      验证，是否urmp是否新增成功
        List<WebElement> tree = l.getElements(param.get("resourceModelTree"));
        ArrayList<String> strings = new ArrayList<String>();
        for (WebElement e : tree) {
            String title = e.getAttribute("title");
            strings.add(title);
        }
        boolean urmpAddDisplayNameValue = strings.contains(param.get("URMPAddDisplayNameValue"));
        LogFunction.logInfo("是否新建成功状态为：" + urmpAddDisplayNameValue);
        if (urmpAddDisplayNameValue) {
            LogFunction.logInfo("URMP新增成功");
        } else {
            LogFunction.logInfo("URMP新增失败");
            AssertFunction.assertEquals(driver, urmpAddDisplayNameValue, true);
        }
        LogFunction.logInfo("----------------资源管理-资源模型-新建URMP校验完成---------------------");

    }

    //    资源管理-资源模型-编辑CIType
    @Test(dataProvider = "xmldata")
    public void resourceModelEditCIType(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，新增URMP显示名称
        co.modelClickButton2(param, "createDisplayNameButton", "新增URMP显示名称");
        co.sleep(500);
//         点击，编辑按钮
        co.modelClickButton3(param, "resourceModelEditButton", "点击，编辑button");
        co.sleep(1000);
//        录入，编辑显示名称
        co.modelInputBox(param, "DisplayName", "URMPEditDisplayNameValue", "显示名称");
//         验证，名称是否可用
        WebElement name = l.getElement(param.get("Name"));
        boolean enabled = name.isEnabled();
        LogFunction.logInfo("名称是否可编辑" + enabled);
        AssertFunction.verifyEquals(driver, enabled, false);
//        点击，保存
        co.modelClickButton2(param, "URMPEditSave", "保存");
//        点击，保存提示信息及确认
        co.alarmHintAndConfirm(param, "保存成功");
        LogFunction.logInfo("----------------资源管理-资源模型-编辑URMP完成---------------------");
    }

    //    资源管理-资源模型-编辑CIType验证
    @Test(dataProvider = "xmldata")
    public void resourceModelEditCITypeVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        co.sleep(2000);
//      验证，是否urmp是否新增成功
        List<WebElement> tree = l.getElements(param.get("resourceModelTree"));
        ArrayList<String> strings = new ArrayList<String>();
        for (WebElement e : tree) {
            String title = e.getAttribute("title");
            strings.add(title);
        }
        boolean urmpAddDisplayNameValue = strings.contains(param.get("URMPEditDisplayNameValue"));
        LogFunction.logInfo("是否新建成功状态为：" + urmpAddDisplayNameValue);
        if (urmpAddDisplayNameValue) {
            LogFunction.logInfo("URMP编辑成功");
        } else {
            LogFunction.logInfo("URMP编辑失败");
            AssertFunction.assertEquals(driver, urmpAddDisplayNameValue, true);
        }
        LogFunction.logInfo("----------------资源管理-资源模型-编辑URMP校验完成---------------------");

    }

    //    资源管理-资源模型-删除CIType
    @Test(dataProvider = "xmldata")
    public void resourceModelDeleteCIType(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，编辑URMP显示名称
        co.modelClickButton3(param, "editDisplayNameButton", "点击，编辑URMP显示名称");
        co.sleep(1000);
//         点击，删除按钮
        co.modelClickButton4(param, "delete", "title");
//        点击，删除提示信息及确认
        co.alarmConfirm(param);
//        点击，删除成功提示信息及确认
        co.alarmConfirm(param);

        LogFunction.logInfo("----------------资源管理-资源模型-删除URMP完成---------------------");
    }

    //    资源管理-资源模型-删除CIType
    @Test(dataProvider = "xmldata")
    public void resourceModelDeleteCITypeInterface(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HttpFunction c = new HttpFunction();
        c.post(param.get("url"), param.get("params"));
        LogFunction.logInfo("----------------资源管理-资源模型-删除URMP，调用接口，完成---------------------");
    }


    //    资源管理-资源模型-删除CIType验证
    @Test(dataProvider = "xmldata")
    public void resourceModelDeleteCITypeVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        driver.navigate().refresh();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//      验证，是否urmp是否新增成功
        List<WebElement> tree = l.getElements(param.get("resourceModelTree"));
        ArrayList<String> strings = new ArrayList<String>();
        for (WebElement e : tree) {
            String title = e.getAttribute("title");
            strings.add(title);
        }
        boolean urmpAddDisplayNameValue = strings.contains(param.get("URMPEditDisplayNameValue"));
        LogFunction.logInfo("是否新建成功状态为：" + urmpAddDisplayNameValue);
        if (!urmpAddDisplayNameValue) {
            LogFunction.logInfo("URMP删除成功");
        } else {
            LogFunction.logInfo("URMP删除失败");
            AssertFunction.assertEquals(driver, urmpAddDisplayNameValue, false);
        }
        LogFunction.logInfo("----------------资源管理-资源模型-删除URMP校验完成---------------------");

    }


    //    资源管理-资源模型-CIType-属性-新建
    @Test(dataProvider = "xmldata")
    public void resourceModelCreateCITypeAttribute(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，资源（button）
        co.modelClickButton2(param, "choosetype2", "资源");
//      点击，新建
        co.createButton(param);
//      录入，显示名称
        co.modelInputBox(param, "CITypeAttributeCreateDisplayName", "CITypeAttributeCreateDisplayNameValue", "显示名称");
//        录入，属性名称
        co.modelInputBox(param, "CITypeAttributeCreateAttributeName", "CITypeAttributeCreateAttributeNameValue", "属性名称");
//      点击，数据类型
//        co.modelClickButton3(param, "", "点击，数据类型");
//        选择，数据类型值
//        co.modelClickButton2(param, "CITypeAttributeCreateDataTypeValue", "LONG", "选择，数据类型：");
        co.modelClickAndChooseValueTwo(param, "CITypeAttributeCreateDataType", "CITypeAttributeCreateDataTypeValue", "数据类型");

        //      点击，显示类型
        co.modelClickButton3(param, "CITypeAttributeCreateDisplayType", "点击，显示类型");
//        选择，显示类型值
        co.modelClickButton2(param, "CITypeAttributeCreateDisplayTypeValue", "TEXT", "选择，显示类型：");
//      点击，所属分组
        co.modelClickButton3(param, "CITypeAttributeCreateGroup", "点击，所属分组");
//        选择，所属分组值
        co.modelClickButton2(param, "CITypeAttributeCreateGroupValue", "基本信息", "选择，所属分组：");
//      点击，保存
        co.modelClickButton2(param, "CITypeAttributeCreateSave", "保存");
//        保存提示及确认
        co.alarmHintAndConfirm(param, "保存成功");

        LogFunction.logInfo("----------------资源管理-资源模型-CI类型，属性，新建完成---------------------");
    }

    //    资源管理-资源模型-CIType-属性-新建校验
    @Test(dataProvider = "xmldata")
    public void resourceModelCreateCITypeAttributeVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        co.modelClickButton2(param, "editDisplayNameButton", "编辑URMP显示名称");
        co.sleep(200);
//        点击，筛选
        co.select(param, "CITypeAttributeSelectArea");
//      录入，显示名称
        co.modelInputBox(param, "CITypeAttributeSelectDisplayName", "CITypeAttributeCreateDisplayNameValue", "显示名称");
//        录入，属性名称
        co.modelInputBox(param, "CITypeAttributeSelectAttributeName", "CITypeAttributeCreateAttributeNameValue", "属性名称");
//      点击，筛选，确定
        co.modelClickButton2(param, "CITypeAttributeSelectAffirm", "确定");
//      校验，筛选结果
        co.selectResultVerify(param, "CITypeAttributeSelectResult", "CITypeAttributeCreateDisplayNameValue");

        LogFunction.logInfo("----------------资源管理-资源模型-CI类型，属性，新建校验完成---------------------");
    }

    //    资源管理-资源模型-CIType-属性-编辑
    @Test(dataProvider = "xmldata")
    public void resourceModelEditCITypeAttribute(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        勾选，结果
        co.chooseSelectResult(param);
//      点击，编辑
        co.editButton(param);
//      录入，编辑显示名称
        co.modelInputBox(param, "CITypeAttributeCreateDisplayName", "CITypeAttributeEditDisplayNameValue", "显示名称");
//        验证，属性名称是否可用
        co.modelElementIsEnable(param, "CITypeAttributeCreateAttributeName", false, "属性名称不可用");
//      点击，保存
        co.modelClickButton2(param, "CITypeAttributeCreateSave", "保存");
//        保存提示及确认
        co.alarmHintAndConfirm(param, "保存成功");

        LogFunction.logInfo("----------------资源管理-资源模型-CI类型，属性，编辑完成---------------------");

    }

    //    资源管理-资源模型-CIType-属性-编辑校验
    @Test(dataProvider = "xmldata")
    public void resourceModelEditCITypeAttributeVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        点击，筛选
        co.select(param, "CITypeAttributeSelectArea");
//      录入，编辑显示名称
        co.modelInputBox(param, "CITypeAttributeSelectDisplayName", "CITypeAttributeEditDisplayNameValue", "显示名称");
//        录入，属性名称
        co.modelInputBox(param, "CITypeAttributeSelectAttributeName", "CITypeAttributeCreateAttributeNameValue", "属性名称");
//      点击，筛选，确定
        co.modelClickButton2(param, "CITypeAttributeSelectAffirm", "确定");
//      校验，筛选结果
        co.selectResultVerify(param, "CITypeAttributeSelectResult", "CITypeAttributeEditDisplayNameValue");

        LogFunction.logInfo("----------------资源管理-资源模型-CI类型，属性，编辑校验完成---------------------");

    }

    //    资源管理-资源模型-CIType-属性-删除
    @Test(dataProvider = "xmldata")
    public void resourceModelDeleteCITypeAttribute(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        co.sleep(200);
//        勾选，结果
        co.chooseSelectResult(param);
//      点击，删除
        co.deleteButton(param);
//        删除确认提示及确认
        co.alarmHintAndConfirm(param);
//        删除提示及确认
        co.alarmHintAndConfirm(param);
        LogFunction.logInfo("----------------资源管理-资源模型-CI类型，属性，删除完成---------------------");

    }

    //    资源管理-资源模型-CIType-属性-删除校验
    @Test(dataProvider = "xmldata")
    public void resourceModelDeleteCITypeAttributeVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        co.sleep(200);

//        点击，筛选
        co.select(param, "CITypeAttributeSelectArea");
//      录入，编辑显示名称
        co.modelInputBox(param, "CITypeAttributeSelectDisplayName", "CITypeAttributeEditDisplayNameValue", "显示名称");
//        录入，属性名称
        co.modelInputBox(param, "CITypeAttributeSelectAttributeName", "CITypeAttributeCreateAttributeNameValue", "属性名称");
//      点击，筛选，确定
        co.modelClickButton2(param, "CITypeAttributeSelectAffirm", "确定");
//      校验，筛选结果
        co.selectResultIsNull(param, "CITypeAttributeSelectResultIsNull");
        LogFunction.logInfo("----------------资源管理-资源模型-CI类型，属性，删除校验完成---------------------");

    }

    //    资源管理-资源模型-CIType-关系属性-新建
    @Test(dataProvider = "xmldata")
    public void resourceModelCreateCITypeRelationAttribute(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，编辑URMP显示名称（button）
//        co.modelClickButton2(param, "editDisplayNameButton", "编辑URMP显示名称");
//        点击，关系属性
        co.modelClickButton2(param, "CITypeRelationAttribute", "关系属性");
        //      点击，新建
        co.createButton(param);
//      录入，显示名称
        co.modelInputBox(param, "CITypeRelationAttributeCreateDisplayName", "CITypeRelationAttributeCreateDisplayNameValue", "显示名称");
//        录入，属性名称
        co.modelInputBox(param, "CITypeRelationAttributeCreateAttributeName", "CITypeRelationAttributeCreateAttributeNameValue", "属性名称");
//      点击，目标
        co.modelClickButton3(param, "CITypeRelationAttributeCreateTarget", "点击，目标");
//        选择，目标值
        co.modelClickButton2(param, "CITypeRelationAttributeCreateTargetValue", "操作系统", "选择，目标：");
//      点击，映射类型
        co.modelClickButton3(param, "CITypeRelationAttributeCreateMapType", "点击，映射类型");
//        选择，映射类型值
        co.modelClickButton2(param, "CITypeRelationAttributeCreateMapTypeValue", "one", "选择，映射类型：");
//      点击，所属分组
        co.modelClickButton3(param, "CITypeRelationAttributeCreateGroup", "点击，所属分组");
//        选择，所属分组值
        co.modelClickButton2(param, "CITypeRelationAttributeCreateGroupValue", "基本信息", "选择，所属分组：");
//      点击，关系类型
        co.modelClickButton3(param, "CITypeRelationAttributeCreateRelationType", "点击，关系类型");
//        选择，关系类型值
        co.modelClickButton2(param, "CITypeRelationAttributeCreateRelationTypeValue", "位于", "选择，关系类型：");

        //      点击，保存
        co.modelClickButton2(param, "CITypeAttributeCreateSave", "保存");
//        保存提示及确认
        co.alarmHintAndConfirm(param, "保存成功");

        LogFunction.logInfo("----------------资源管理-资源模型-CI类型，关系属性，新建完成---------------------");

    }

    //    资源管理-资源模型-CIType-关系属性-新建校验
    @Test(dataProvider = "xmldata")
    public void resourceModelCreateCITypeRelationAttributeVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        co.modelClickButton2(param, "editDisplayNameButton", "编辑URMP显示名称");
//        co.modelClickButton2(param,"CITypeRelationAttribute","关系属性");


//        点击，筛选
        co.select(param, "CITypeRelationAttributeSelectArea");
//      录入，显示名称
        co.modelInputBox(param, "CITypeRelationAttributeSelectDisplayName", "CITypeRelationAttributeCreateDisplayNameValue", "显示名称");
//        录入，属性名称
        co.modelInputBox(param, "CITypeRelationAttributeSelectAttributeName", "CITypeRelationAttributeCreateAttributeNameValue", "属性名称");
//      点击，筛选，确定
        co.modelClickButton2(param, "CITypeRelationAttributeSelectAffirm", "确定");
//      校验，筛选结果
        co.selectResultVerify(param, "CITypeRelationAttributeSelectResult", "CITypeRelationAttributeCreateDisplayNameValue");

        LogFunction.logInfo("----------------资源管理-资源模型-CI类型，关系属性，新建校验完成---------------------");

    }

    //    资源管理-资源模型-CIType-关系属性-编辑
    @Test(dataProvider = "xmldata")
    public void resourceModelEditCITypeRelationAttribute(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        勾选，结果
        co.chooseSelectResult(param, "ResultChoose");
//      点击，编辑
        co.editButton(param);
//      录入，编辑显示名称
        co.modelInputBox(param, "CITypeRelationAttributeCreateDisplayName", "CITypeRelationAttributeEditDisplayNameValue", "显示名称");
//        验证，属性名称是否可用
        co.modelElementIsEnable(param, "CITypeRelationAttributeCreateAttributeName", false, "属性名称不可用");
//      点击，保存
        co.modelClickButton2(param, "CITypeAttributeCreateSave", "保存");
//        保存提示及确认
        co.alarmHintAndConfirm(param, "保存成功");

        LogFunction.logInfo("----------------资源管理-资源模型-CI类型，关系属性，编辑完成---------------------");

    }

    //    资源管理-资源模型-CIType-关系属性-编辑校验
    @Test(dataProvider = "xmldata")
    public void resourceModelEditCITypeRelationAttributeVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，关系属性
        co.modelClickButton2(param, "CITypeRelationAttribute", "关系属性");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，筛选
        co.select(param, "CITypeRelationAttributeSelectArea");
//      录入，编辑显示名称
        co.modelInputBox(param, "CITypeRelationAttributeSelectDisplayName", "CITypeRelationAttributeEditDisplayNameValue", "显示名称");
//        录入，属性名称
        co.modelInputBox(param, "CITypeRelationAttributeSelectAttributeName", "CITypeRelationAttributeCreateAttributeNameValue", "属性名称");
//      点击，筛选，确定
        co.modelClickButton2(param, "CITypeRelationAttributeSelectAffirm", "确定");
//      校验，筛选结果
        co.selectResultVerify(param, "CITypeRelationAttributeSelectResult", "CITypeRelationAttributeEditDisplayNameValue");

        LogFunction.logInfo("----------------资源管理-资源模型-CI类型，关系属性，编辑校验完成---------------------");

    }

    //    资源管理-资源模型-CIType-关系属性-删除
    @Test(dataProvider = "xmldata")
    public void resourceModelDeleteCITypeRelationAttribute(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，关系属性
//        co.modelClickButton2(param, "CITypeRelationAttribute", "关系属性");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        勾选，结果
        co.chooseSelectResult(param, "ResultChoose");
//      点击，删除
        co.deleteButton(param);
//        删除确认提示及确认
        co.alarmHintAndConfirm(param);
//        删除提示及确认
        co.alarmHintAndConfirm(param);
        LogFunction.logInfo("----------------资源管理-资源模型-CI类型，关系属性，删除完成---------------------");

    }

    //    资源管理-资源模型-CIType-关系属性-删除校验
    @Test(dataProvider = "xmldata")
    public void resourceModelDeleteCITypeRelationAttributeVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        co.sleep(1000);
//        点击，筛选
        co.select(param, "SelectArea");
//      录入，编辑显示名称
        co.modelInputBox(param, "DisplayName", "CITypeAttributeEditDisplayNameValue", "显示名称");
//        录入，属性名称
        co.modelInputBox(param, "AttributeName", "CITypeAttributeCreateAttributeNameValue", "属性名称");
//      点击，筛选，确定
        co.modelClickButton2(param, "Affirm", "确定");
        //      校验，筛选结果
        co.selectResultIsNull(param, "ResultEmpty");
        LogFunction.logInfo("----------------资源管理-资源模型-CI类型，关系属性，删除校验完成---------------------");
    }

    String OperationVersion = null;

    //    资源管理-资源模型-CIType-操作日志-筛选，（获取：操作版本）
    @Test(dataProvider = "xmldata")
    public void resourceModelSelectCITypeOperationLog(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        co.modelClickButton2(param, "editDisplayNameButton", "编辑URMP显示名称");

//      点击，操作日志
        co.modelClickButton2(param, "OperationLog", "操作日志");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，筛选
        co.select(param, "SelectArea");
//      录入，操作节点名称
        co.modelInputBox(param, "OperationNodeName", "CITypeAttributeEditDisplayNameValue", "操作节点名称");
//      点击，筛选，确定
        co.modelClickButton2(param, "Affirm", "确定");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//      获取，筛选结果-操作版本
        OperationVersion = co.modelAcquireElementValue(param, "SelectResultOperationVersion", "text", "版本号");

        LogFunction.logInfo("----------------资源管理-资源模型-CI类型，操作日志，通过筛选获取操作版本号，完成---------------------");
    }

    //    资源管理-资源模型-CIType-版本
    @Test(dataProvider = "xmldata")
    public void resourceModelSelectCITypeVersion(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//      点击，版本
        co.modelClickButton2(param, "Version", "版本");
        co.sleep(3000);
//        点击，筛选
        co.select(param, "SelectArea");
//      录入，操作版本号
        co.modelInputBox(param, "OperationVersionNumber", OperationVersion, "操作版本号");
//      点击，筛选，确定
        co.modelClickButton2(param, "Affirm", "确定");
        co.sleep(500);
//      验证，筛选结果-操作版本
        co.selectResultVerify(param, "VersionNumber", OperationVersion, "");

        LogFunction.logInfo("----------------资源管理-资源模型-CI类型，版本，筛选完成---------------------");
    }

    //    资源管理-资源模型-CIType-版本
    @Test(dataProvider = "xmldata")
    public void resourceModelCITypeVersionExport(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        勾选，结果
        co.chooseSelectResult(param, "ResultChoose");

//      点击，导出
        co.modelClickButton2(param, "exprot", "导出");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//      验证，是否导出
        co.exportFunction(param);
        LogFunction.logInfo("----------------资源管理-资源模型-CI类型，版本，导出完成---------------------");
    }


    //    资源管理-资源树查询
    @Test(dataProvider = "xmldata")
    public void resourceManagementResourceTreeSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        搜索框，录入，编辑URMP显示名称
        co.modelInputBox(param, "selectInputBox", "urmp", "资源树搜索输入框");
//        点击，搜索按钮
        co.modelClickButton3(param, "resourceModelSelectButton", "点击，筛选按钮");
//      URMP 是否被筛选成功
        WebElement element = driver.findElement(By.xpath("//*/li/a[contains(@title,'URMP')]"));
        boolean selected = element.isSelected();
        LogFunction.logInfo(selected);

        LogFunction.logInfo("----------------资源管理-资源树筛选，完成---------------------");
    }


    //    资源管理-资源实例-实例列表-新建
    @Test(dataProvider = "xmldata")
    public void resourceExampleExampleListCreate(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，资源(374)
        co.modelClickButton(param, "ResourceTreeResource");
        co.sleep(2000);
        //        点击，新建
        co.createButton(param);
        co.sleep(1000);
//        获取，新建，标题
        String valueString = co.getTextValueString(param, "Title", "text");
        LogFunction.logInfo("标题为：" + valueString);
        AssertFunction.assertEquals(driver, valueString, "新增");
//      录入，名称
        co.modelInputBox(param, "Name", "ResourceExampleCreateNameValue", "名称");
//      录入,显示名称
        co.modelInputBox(param, "DisplayName", "ResourceExampleCreateDisplayNameValue", "显示名称");
//        点击，保存
        co.modelClickButton2(param, "Save", "保存");
//      保存，确认
        co.alarmHintAndConfirm(param, "保存成功");
        LogFunction.logInfo("----------------资源管理-资源实例-新建完成---------------------");
    }

    //    资源管理-资源实例-实例列表-新建筛选验证
    @Test(dataProvider = "xmldata")
    public void resourceExampleExampleListCreateVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //        点击，筛选
        co.select(param, "ScreenArea");
//      录入，操作版本号
        co.modelInputBox(param, "DisplayName", "ResourceExampleCreateDisplayNameValue", "显示名称");
//      点击，筛选，确定
        co.modelClickButton2(param, "Confirm", "确定");
        co.sleep(500);
//      验证，筛选结果-显示名称
        co.selectResultVerify(param, "screenResult", "ResourceExampleCreateDisplayNameValue");
        LogFunction.logInfo("----------------资源管理-资源实例-新建，筛选验证通过---------------------");
    }

    //    资源管理-资源实例-实例列表-编辑
    @Test(dataProvider = "xmldata")
    public void resourceExampleExampleListEdit(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        勾选，结果
        co.modelClickButton3(param, "ResultChoose", "勾选结果");
        //        点击，编辑
        co.editButton(param);
        co.sleep(3000);
//        获取，编辑，标题
        String valueString = co.getTextValueString(param, "Title", "text");
        LogFunction.logInfo("标题为：" + valueString);
        AssertFunction.assertEquals(driver, valueString, "编辑");
//      录入,显示名称
        co.modelInputBox(param, "DisplayName", "ResourceExampleEditDisplayNameValue", "显示名称");
//        点击，保存
        co.modelClickButton2(param, "Save", "保存");
//      保存，确认
        co.alarmHintAndConfirm(param, "保存成功");
        LogFunction.logInfo("----------------资源管理-资源实例-编辑完成---------------------");
    }

    //    资源管理-资源实例-实例列表-编辑筛选验证
    @Test(dataProvider = "xmldata")
    public void resourceExampleExampleListEditVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //        点击，筛选
        co.select(param, "ScreenArea");
//      录入，操作版本号
        co.modelInputBox(param, "DisplayName", "ResourceExampleEditDisplayNameValue", "显示名称");
//      点击，筛选，确定
        co.modelClickButton2(param, "Confirm", "确定");
        co.sleep(500);
//      验证，筛选结果-显示名称
        co.selectResultVerify(param, "screenResult", "ResourceExampleEditDisplayNameValue");
        LogFunction.logInfo("----------------资源管理-资源实例-编辑，筛选验证通过---------------------");
    }

    //    资源管理-资源实例-实例列表-删除
    @Test(dataProvider = "xmldata")
    public void resourceExampleExampleListDelete(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        勾选，结果
        co.modelClickButton3(param, "ResultChoose", "勾选结果");
        //        点击，删除
        co.deleteButton(param);
        co.sleep(1000);
//      点击，删除提示确认
        co.alarmHintAndConfirm(param, "确定要删除？");
        co.sleep(1000);
//        点击，删除成功确认
        co.alarmHintAndConfirm(param, "删除成功");
        LogFunction.logInfo("----------------资源管理-资源实例-编辑完成---------------------");
    }

    //    资源管理-资源实例-实例列表-删除筛选验证
    @Test(dataProvider = "xmldata")
    public void resourceExampleExampleListDeleteVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //        点击，筛选
        co.select(param, "ScreenArea");
//      录入，显示名称
        co.modelInputBox(param, "DisplayName", "ResourceExampleEditDisplayNameValue", "显示名称");
//      点击，筛选，确定
        co.modelClickButton2(param, "Confirm", "确定");
        co.sleep(500);
//      验证，筛选结果-是否为空
        co.selectResultIsNull(param);
        LogFunction.logInfo("----------------资源管理-资源实例-删除，筛选验证通过---------------------");
    }

    //    资源管理-资源实例-操作日志-筛选
    @Test(dataProvider = "xmldata")
    public void resourceExampleOperationLog(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //        点击，资源Button

//        co.modelMoveToElementAndClick(param, "ResourceTreeResource", "text");
//        co.sleep(1000);
//      点击，操作日志
        co.modelClickButton2(param, "OperationLog", "操作日志");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，筛选
        co.select(param, "SelectArea");
//      录入，操作节点名称
        co.modelInputBox(param, "OperationNodeName", "ResourceExampleEditDisplayNameValue", "操作节点名称");
//      点击，筛选，确定
        co.modelClickButton2(param, "Affirm", "确定");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//      获取，筛选结果-操作版本
        String s = co.modelAcquireElementValue(param, "ScreenResult", "text", "操作节点名称");
        AssertFunction.assertEquals(driver, s, param.get("ResourceExampleEditDisplayNameValue"));
        LogFunction.logInfo("----------------资源管理-资源模型-资源实例，操作日志，筛选操作完成---------------------");
    }

    //    资源管理-自动发现-发现列表-归类-筛选
    @Test(dataProvider = "xmldata")
    public void discoveryListClassifyScreen(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        LogFunction.logInfo("----------------推送告警，开始---------------------");
        co.pushMergeAlarmInfo(param, "pushAlarmParams123");
        co.sleep(100);
        co.pushMergeAlarmInfo(param, "pushAlarmParams321");
        LogFunction.logInfo("----------------推送告警，结束---------------------");
//      点击，自动发现
//        co.modelClickButton2(param, "automaticDiscovery", "自动发现");
//      点击，发现列表
        co.modelClickButton2(param, "DiscoveryList", "发现列表");
        //      点击，未确认归类
        co.modelClickButton3(param, "ToBeConfirmClassify", "点击：待确认归类");
        //        点击，筛选
        co.select(param, "SelectArea");
//      录入，唯一标识
        co.modelInputBox(param, "UniqueLogo", "ClassifyUniqueLogoValue", "唯一标识");
//      点击，筛选，确定
        co.modelClickButton2(param, "Confirm", "确定");
        co.sleep(500);
//      验证，筛选结果-唯一标识
        co.selectResultVerify(param, "ResultUniqueLogo", "ClassifyUniqueLogoValue");
        LogFunction.logInfo("----------------资源管理-自动发现-发现列表，归类，筛选完成---------------------");
    }

    //    资源管理-自动发现-发现列表-归类
    @Test(dataProvider = "xmldata")
    public void discoveryListClassify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//      点击，勾选结果
        co.modelClickButton3(param, "ResultChoose", "勾选，结果");
//      点击，归类
        co.modelClickButton2(param, "Classify", "归类");
        //      点击，节点类型Button
        co.modelClickButton3(param, "NodeTypeButton", "点击，节点类型");
        co.sleep(1000);
        //      选择，节点类型值
        co.modelClickButton(param, "NodeTypeValue");
        //      点击，保存
//        co.modelClickButton(param, "Save", "保存");
        WebElement element = l.getElement(param.get("Save"));
        String text = element.getAttribute("value");
        Actions a = new Actions(driver);
        a.moveToElement(element).build().perform();

        ScreenshotFunction s = new ScreenshotFunction(driver);
        s.takeScreenshot();

        JavascriptExecutor j = (JavascriptExecutor) driver;
        j.executeScript("arguments[0].click();", element);
        //      点击，保存，提示确认
        co.alarmHintAndConfirm(param);
        LogFunction.logInfo("----------------资源管理-自动发现-发现列表，归类，操作完成---------------------");
    }

    //    资源管理-自动发现-发现列表-归类-验证1(发现列表环节)
    @Test(dataProvider = "xmldata")
    public void discoveryListClassifyVerifyOfDiscoveryList(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//      点击，未确认归类
        co.modelClickButton3(param, "ToBeConfirmClassify", "点击：待确认归类");
//        点击，筛选
        co.select(param, "SelectArea");
        co.sleep(200);
//      录入，唯一标识
        co.modelInputBox(param, "UniqueLogo", "ClassifyUniqueLogoValue", "唯一标识");
//      点击，筛选，确定
        co.modelClickButton2(param, "Confirm", "确定");
        co.sleep(500);
//      验证，筛选结果是否为空
        co.selectResultIsNull(param);
//        点击，已确认归类
        co.modelClickButton3(param, "ConfirmClassify", "点击：已确认归类");
//      点击，筛选，确定
        co.modelClickButton2(param, "Confirm", "确定", "点击，筛选");
//      验证，筛选结果-唯一标识
        co.selectResultVerify(param, "ResultUniqueLogo", "ClassifyUniqueLogoValue");

        LogFunction.logInfo("----------------资源管理-自动发现-发现列表，归类，(发现列表环节)筛选验证完成---------------------");
    }

    //    资源管理-自动发现-发现列表-归类-验证2(资源实例环节)
    @Test(dataProvider = "xmldata")
    public void discoveryListClassifyVerifyOfResourceExample(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//      点击，资源实例
        co.modelClickButton2(param, "resourceExample", "资源实例");
//      点击，操作系统
        co.sleep(2000);
        co.modelClickButton3(param, "OperationSystem", "点击，操作系统");
        co.sleep(200);
//        点击，筛选
        co.select(param, "ScreenArea");
//      录入，显示名称
        co.modelInputBox(param, "DisplayName", "ClassifyUniqueLogoValue", "显示名称");
//      点击，筛选，确定
        co.modelClickButton2(param, "Confirm", "确定");
        co.sleep(500);
//      验证，筛选结果-显示名称
        co.selectResultVerify(param, "screenResult", "ClassifyUniqueLogoValue");
//        勾选，结果
        co.modelClickButton3(param, "ResultChoose", "勾选结果");
//        点击，删除
        co.deleteButton(param);
//      点击，删除提示确认
        co.alarmHintAndConfirm(param, "确定要删除？");
//        点击，删除成功确认
        co.alarmHintAndConfirm(param, "删除成功");
        //        点击，筛选
        co.select(param, "ScreenArea");
        co.sleep(200);
//      录入，显示名称
        co.modelInputBox(param, "DisplayName", "ClassifyUniqueLogoValue", "显示名称");
//      点击，筛选，确定
        co.modelClickButton2(param, "Confirm", "确定");
        co.sleep(500);
//      验证，筛选结果-是否为空
        co.selectResultIsNull(param);
        //      点击，自动发现
        co.modelAreaDisplayAndClick(param,"automaticDiscoveryArea","automaticDiscovery");
//        co.modelClickButton2(param, "automaticDiscovery", "自动发现");
//      点击，发现列表
        co.modelClickButton2(param, "DiscoveryList", "发现列表");
//        点击，已确认归类
        co.modelClickButton3(param, "ConfirmClassify", "点击：已确认归类");
//        点击，筛选
        co.select(param, "SelectArea");
        co.sleep(200);
//      录入，唯一标识
        co.modelInputBox(param, "UniqueLogo", "ClassifyUniqueLogoValue", "唯一标识");
//      点击，筛选，确定
        co.modelClickButton2(param, "Confirm1", "确定");
        co.sleep(500);
//      验证，筛选结果是否为空
        co.selectResultIsNull(param);
        LogFunction.logInfo("----------------资源管理-自动发现-发现列表，归类，(资源实例环节)筛选验证完成---------------------");
    }

    //    资源管理-自动发现-发现列表-丢弃-筛选
    @Test(dataProvider = "xmldata")
    public void discoveryListDiscardScreen(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//      点击，自动发现
//        co.modelClickButton2(param, "automaticDiscovery", "自动发现");
//      点击，发现列表
        co.modelClickButton2(param, "DiscoveryList", "发现列表");
//      点击，未确认归类
        co.modelClickButton3(param, "ToBeConfirmClassify", "点击：待确认归类");
        //        点击，筛选
        co.select(param, "SelectArea");
//      录入，唯一标识
        co.modelInputBox(param, "UniqueLogo", "DiscardUniqueLogoValue", "唯一标识");
//      点击，筛选，确定
        co.modelClickButton2(param, "Confirm", "确定");
        co.sleep(500);
//      验证，筛选结果-唯一标识
        co.selectResultVerify(param, "ResultUniqueLogo", "DiscardUniqueLogoValue");
        LogFunction.logInfo("----------------资源管理-自动发现-发现列表，丢弃，筛选完成---------------------");
    }

    //    资源管理-自动发现-发现列表-丢弃
    @Test(dataProvider = "xmldata")
    public void discoveryListDiscard(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//      点击，勾选结果
        co.modelClickButton3(param, "ResultChoose", "勾选，结果");
//      点击，丢弃
        co.modelClickButton2(param, "Discard", "丢弃");
//      点击，丢弃，提示确认
        co.sleep(1000);
        co.alarmHintAndConfirm(param);
        LogFunction.logInfo("----------------资源管理-自动发现-发现列表，丢弃，操作完成---------------------");
    }

    //    资源管理-自动发现-发现列表-丢弃，筛选验证
    @Test(dataProvider = "xmldata")
    public void discoveryListDiscardVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//      点击，未确认归类
        co.modelClickButton3(param, "ToBeConfirmClassify", "点击：待确认归类");
//        点击，筛选
        co.select(param, "SelectArea");
        co.sleep(200);
//      录入，唯一标识
        co.modelInputBox(param, "UniqueLogo", "DiscardUniqueLogoValue", "唯一标识");
//      点击，筛选，确定
        co.modelClickButton2(param, "Confirm", "确定");
        co.sleep(500);
//      验证，筛选结果是否为空
        co.selectResultIsNull(param);
//        点击，已确认归类
        co.modelClickButton3(param, "ConfirmClassify", "点击：已确认归类");
//      点击，筛选，确定
        co.modelClickButton2(param, "Confirm", "确定", "点击，筛选");
//      验证，筛选结果是否为空
        co.selectResultIsNull(param);
        LogFunction.logInfo("----------------资源管理-自动发现-发现列表，丢弃，筛选验证完成---------------------");
    }

    //    资源管理-自动发现-发现规则-新建
    @Test(dataProvider = "xmldata")
    public void discoveryRuleCreate(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//      点击，新建
        co.createButton(param);
//      录入，规则名称
        co.modelInputBox(param, "name", "DiscoveryRuleRuleNameCreateValue", "规则名称");
//      录入，ip范围
        co.modelInputBox(param, "ipRange", "DiscoveryRuleIPRangeValue", "ip范围");
//      录入，时间间隔
        co.modelInputBox(param, "timeInterval", "DiscoveryRuleTimeIntervalValue", "时间间隔");
//      选择，采集插件
        co.modelClickButton(param, "collectionPlugin", "采集插件", "");
        co.sleep(500);
        co.modelClickButton(param, "collectionPluginValue");
        co.sleep(500);
        //      勾选，启用
        co.modelRadioBox(param, "enable");
//        点击，保存
        co.modelClickButton2(param, "save", "保存");

        LogFunction.logInfo("----------------资源管理-自动发现-发现规则,新建完成---------------------");
    }

    //    资源管理-自动发现-发现规则-新建，筛选验证
    @Test(dataProvider = "xmldata")
    public void discoveryRuleCreateVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        driver.navigate().refresh();
//        LogFunction.logInfo("页面刷新成功");
//        co.sleep(3000);
//        promptMessage(param);
        //      点击，筛选
        co.select(param, "area");
        co.sleep(1000);
//      录入，规则名称
        co.modelInputBox(param, "ruleName", "DiscoveryRuleRuleNameCreateValue", "规则名称");
//        点击，确定
        co.modelClickButton2(param, "confirm", "确定");
        co.sleep(500);
//        校验筛选结果，规则名称
        co.selectResultVerify(param, "resultName", "DiscoveryRuleRuleNameCreateValue");
//        校验筛选结果，已启用
        co.selectResultVerifyOfTextType(param, "resultEnable", "已启用", "title");

        LogFunction.logInfo("----------------资源管理-自动发现-发现规则,新建-筛选验证完成---------------------");
    }

    //    资源管理-自动发现-发现规则-编辑
    @Test(dataProvider = "xmldata")
    public void discoveryRuleEdit(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        勾选，结果
        co.chooseSelectResult(param);
//      点击，新建
        co.editButton(param);
//      录入，规则名称
        co.modelInputBox(param, "name", "DiscoveryRuleRuleNameEditValue", "规则名称");
        //      勾选，启用
        co.modelNoRadioBox(param, "enable", "启用");
//        点击，保存
        co.modelClickButton(param, "save");

        LogFunction.logInfo("----------------资源管理-自动发现-发现规则,编辑完成---------------------");
    }

    //    资源管理-自动发现-发现规则-编辑，筛选验证
    @Test(dataProvider = "xmldata")
    public void discoveryRuleEditVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//      点击，筛选
        co.select(param, "area");
//      录入，规则名称
        co.modelInputBox(param, "ruleName", "DiscoveryRuleRuleNameEditValue", "规则名称");
//        点击，确定
        co.modelClickButton2(param, "confirm", "确定");
        co.sleep(500);
//        校验筛选结果，规则名称
        co.selectResultVerify(param, "resultName", "DiscoveryRuleRuleNameEditValue");
//        校验筛选结果，已禁用
        co.selectResultVerifyOfTextType(param, "resultEnable", "已禁用", "title");

        LogFunction.logInfo("----------------资源管理-自动发现-发现规则,编辑-筛选验证完成---------------------");
    }

    //    资源管理-自动发现-发现规则-启用
    @Test(dataProvider = "xmldata")
    public void discoveryRuleEnable(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        勾选，结果
        co.chooseSelectResult(param);
//        点击，启用
        co.enableButton(param);
//      点击，筛选
        co.select(param, "area");
//      录入，规则名称
        co.modelInputBox(param, "ruleName", "DiscoveryRuleRuleNameEditValue", "规则名称");
//        点击，确定
        co.modelClickButton2(param, "confirm", "确定");
        co.sleep(500);
//        校验筛选结果，已启用
        co.selectResultVerifyOfTextType(param, "resultEnable", "已启用", "title");
        LogFunction.logInfo("----------------资源管理-自动发现-发现规则,启用且筛选验证完成---------------------");
    }

    //    资源管理-自动发现-发现规则-禁用
    @Test(dataProvider = "xmldata")
    public void discoveryRuleDisenable(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        勾选，结果
        co.chooseSelectResult(param);
//        点击，禁用
        co.disenableButton(param);
//      点击，筛选
        co.select(param, "area");
//      录入，规则名称
        co.modelInputBox(param, "ruleName", "DiscoveryRuleRuleNameEditValue", "规则名称");
//        点击，确定
        co.modelClickButton2(param, "confirm", "确定");
        co.sleep(500);
//        校验筛选结果，已禁用
        co.selectResultVerifyOfTextType(param, "resultEnable", "已禁用", "title");
        LogFunction.logInfo("----------------资源管理-自动发现-发现规则,禁用且筛选验证完成---------------------");
    }

    //    资源管理-自动发现-发现规则-删除
    @Test(dataProvider = "xmldata")
    public void discoveryRuleDelete(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        勾选，结果
        co.chooseSelectResult(param);
//        点击，删除
        co.deleteButton(param);
//        点击，删除确认
        co.alarmHintAndConfirm(param);
//      点击，筛选
        co.select(param, "area");
//      录入，规则名称
        co.modelInputBox(param, "ruleName", "DiscoveryRuleRuleNameEditValue", "规则名称");
//        点击，确定
        co.modelClickButton2(param, "confirm", "确定");
        co.sleep(2000);
//        校验筛选结果，是否为空
        co.selectResultIsNull(param);
        LogFunction.logInfo("----------------资源管理-自动发现-发现规则,删除且筛选验证完成---------------------");
    }

    //    菜单-视图中心
    @Test(dataProvider = "xmldata")
    public void viewCenter(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//      视图中心，是否打开并点击
        co.modelOpenArea(param, "ViewCenter", "Area");
    }

    //      菜单，视图画布
    @Test(dataProvider = "xmldata")
    public void viewCanvas(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//      视图中心，是否打开并点击
        co.modelClickButton(param, "ViewCenter");
    }

    //    视图中心-视图画布-新建
    @Test(dataProvider = "xmldata")
    public void viewCanvasCreate(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//      点击，视图中心
        co.modelClickButton(param, "ViewCenter");
        co.openLeftMenuButton(param);
//        点击，视图画布
        co.modelClickButton(param, "ViewCanvas");
//      点击，新建并校验title
        co.createButton(param);
        co.modelTitleVerify(param, "新增", "Title", "text");
//      录入，视图名称
        co.modelInputBox(param, "Name", "ViewCanvasCreateNameValue", "视图名称");
//        点击，保存
        co.modelClickButton(param, "Save");
        LogFunction.logInfo("----------------视图中心-视图画布-新建,完成---------------------");
    }

    //    视图中心-视图画布-新建-筛选验证
    @Test(dataProvider = "xmldata")
    public void viewCanvasCreateVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//      打开，筛选
        co.select(param, "Area");
//        录入，视图名称
        co.modelInputBox(param, "Name", "ViewCanvasCreateNameValue", "视图名称");
//      点击，确定
        co.modelClickButton(param, "Confirm");
//      验证，筛选结果
        co.selectResultVerify(param, "commonSelectResultThree", "ViewCanvasCreateNameValue");
        LogFunction.logInfo("----------------视图中心-视图画布-新建,筛选验证完成---------------------");
    }

    //    视图中心-视图画布-编辑
    @Test(dataProvider = "xmldata")
    public void viewCanvasEdit(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        勾选结果
        co.chooseSelectResult(param);
//      点击，编辑
        co.editButton(param);
//      录入，视图名称
        co.modelInputBox(param, "Name", "ViewCanvasEditNameValue", "视图名称");
//        点击，保存
        co.modelClickButton(param, "Save");
        LogFunction.logInfo("----------------视图中心-视图画布-编辑,完成---------------------");
    }


    //    视图中心-视图画布-编辑-筛选验证
    @Test(dataProvider = "xmldata")
    public void viewCanvasEditVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//      打开，筛选
        co.select(param, "Area");
//        录入，视图名称
        co.modelInputBox(param, "Name", "ViewCanvasEditNameValue", "视图名称");
//      点击，确定
        co.modelClickButton(param, "Confirm");
//      验证，筛选结果
        co.selectResultVerify(param, "commonSelectResultThree", "ViewCanvasEditNameValue");

        LogFunction.logInfo("----------------视图中心-视图画布-编辑,筛选验证完成---------------------");
    }

    //    视图中心-视图画布-复制及筛选校验
    @Test(dataProvider = "xmldata")
    public void viewCanvasCopyAndVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        勾选结果
        co.chooseSelectResult(param);
//      点击，复制
        co.copyButton(param);
//      打开，筛选
        co.select(param, "Area");
//        录入，视图名称
        co.modelInputBox(param, "Name", "ViewCanvasEditCopyNameValue", "视图名称");
//      点击，确定
        co.modelClickButton(param, "Confirm");
//      验证，筛选结果
        co.selectResultVerify(param, "commonSelectResultThree", "ViewCanvasEditCopyNameValue");
        LogFunction.logInfo("----------------视图中心-视图画布-复制及筛选校验,完成---------------------");
    }


    //    视图中心-视图画布-设计
    @Test(dataProvider = "xmldata")
    public void viewCanvasDesign(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，视图画布
        co.modelClickButton(param, "ViewCanvas");
        //      打开，筛选
        co.select(param, "Area");
//        录入，视图名称
        co.modelInputBox(param, "Name", param.get("ViewCanvasEditNameValue") + "-复制", "视图名称");
//      点击，确定
        co.modelClickButton(param, "Confirm");
//        勾选结果
        co.chooseSelectResult(param);
//      点击，设计
        co.designButton(param);
//      点击，组件
        co.modelClickButton(param, "viewCanvasDesignModul");
        co.sleep(500);
//      拖拽，组件
        co.dragElement(param, "viewCanvasDesignModulTwo", 250, 150, "组件");
        co.sleep(1000);
//      点击，拖拽后的，组件
        co.modelClickButton(param, "viewCanvasDesignModulTwoDragAfter", "拖拽后位置的组件", "");
//      点击，数据源
        co.modelClickButton(param, "viewCanvasDesignModulDataSource");
//      勾选，SQL数据集
        co.modelRadioBox(param, "DesignModulDataSourceSQLDataSet", "SQL数据集");
//       选择，数据集
        co.modelClickAndChooseValue(param, "DesignModulDataSourceDataSet", "DesignModulDataSourceDataSetEditSQLDataSet", "编辑SQL数据集名称");
//      选择，有效列
        co.modelClickAndChooseValue(param, "DesignModulDataSourceDataValidColumn", "DesignModulDataSourceDataValidColumnValue", "有效列");
//      选择，Y轴
        co.modelClickAndChooseValue(param, "DesignModulDataSourceDataYAxis", "DesignModulDataSourceDataYAxisValue", "Y轴");
//      点击，预览
        co.modelClickButton(param, "DesignModulDataSourceDataPreview");
//      截图
        s.takeScreenshot();
        LogFunction.logInfo("视图画布，数据集，截图成功");
//      点击，保存
        co.modelClickButton(param, "viewCanvasDesignSave", "保存", "");
//      保存，确认
        co.modelClickButton(param, "viewCanvasDesignSaveConfirm");
        co.sleep(1000);
//        点击，x，
        co.modelClickButton(param, "viewCanvasDesignExit", "设计，退出button", "");
//      点击，退出x，确定
        co.modelClickButton(param, "viewCanvasDesignExitConfirm");

        LogFunction.logInfo("----------------视图中心-视图画布-设计,完成---------------------");
    }

    //    视图中心-视图画布-删除
    @Test(dataProvider = "xmldata")
    public void viewCanvasDelete(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，视图画布
        co.modelClickButton(param, "ViewCanvas");
        //      打开，筛选
        co.select(param, "Area");
//        录入，视图名称
        co.modelInputBox(param, "Name", "ViewCanvasEditNameValue", "视图名称");
//      点击，确定
        co.modelClickButton(param, "Confirm");
//        勾选结果
        co.chooseSelectResultAll(param);
//      点击，删除
        co.deleteButton(param);
//      点击，删除确认
        co.alarmHintAndConfirm(param, "deleleConfirm", "");
        LogFunction.logInfo("----------------视图中心-视图画布-删除,完成---------------------");
    }

    //    视图中心-视图画布-删除-筛选验证
    @Test(dataProvider = "xmldata")
    public void viewCanvasDeleteVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//      打开，筛选
        co.select(param, "Area");
//        录入，视图名称
        co.modelInputBox(param, "Name", "ViewCanvasEditNameValue", "视图名称");
//      点击，确定
        co.modelClickButton(param, "Confirm");
//      验证，筛选结果
        co.selectResultIsNull(param);

        LogFunction.logInfo("----------------视图中心-视图画布-删除,筛选验证完成---------------------");
    }

    //    视图中心-视图展示
    @Test(dataProvider = "xmldata")
    public void viewDisplay(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//      点击，视图中心
        co.modelClickButton(param, "ViewCenter");
        co.openLeftMenuButton(param);
//        点击，视图展示
        co.modelClickButton(param, "ViewDisplay");
//         点击，“+”
//        co.modelClickButton(param, "AddButton", "“+”", "");
//        co.sleep(1000);
////        选择，视图
//        co.chooseSelectListValueFuzzyMatch(param, "LeftList", param.get("ViewCanvasEditNameValue") + "-复制", "");
////      点击，右移按钮
//        co.modelClickButton(param, "RightButton", "右移按钮", "");
//        co.sleep(1000);
////        验证，视图是否移动右侧列表
//        co.selectIsContainsValue(param, "RightList", "ViewCanvasEditNameValue");
////      点击，保存
//        co.modelClickButton(param, "Save");
////       点击，保存确认
//        co.alarmHintAndConfirm(param);
//      搜索框，输入
        co.modelInputBox(param, "SelectBox", "ViewCanvasEditNameValue", "视图搜索栏");
//      验证，视图是否添加成功
        String valueString = co.getTextValueString(param, "ViewResult", "text");
        boolean v = valueString.contains(param.get("ViewCanvasEditNameValue"));
        LogFunction.logInfo("是否添加成功：" + v);
        AssertFunction.assertEquals(driver, v, true);
//      点击，视图
        co.modelClickButton(param, "ViewDisplayView");
        co.sleep(3000);
//      校验，视图展示值是否正确
        String viewValue = co.getTextValueString(param, "ViewDisplayViewValue", "text");
        AssertFunction.assertEquals(driver, viewValue, passwordValue);
        co.sleep(500);
        co.modelClickButton(param, "ViewDisplayViewValue", "text");
        co.sleep(2000);
//      点击，控件，退出
        co.modelClickButton(param, "ViewDisplayViewControlExit", "退出Button", "");
        LogFunction.logInfo("----------------视图中心-视图展示-是否展示，视图画布创建的视图，验证通过---------------------");
    }

    //    数据分析
    @Test(dataProvider = "xmldata")
    public void dataAnalyze(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，数据分析
        co.modelClickButton(param, "DataAnalyze");
        co.openLeftMenuButton(param);
        LogFunction.logInfo("----------------进入，数据分析页面---------------------");
    }

    //    数据分析-基线策略-新增
    @Test(dataProvider = "xmldata")
    public void baselineStrategyCreate(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，基线策略
        co.modelClickButton(param, "BaselineStrategy");
//        点击，新增按钮
        co.createButton(param);
//      录入，策略名称
        co.modelInputBox(param, "BaselineStrategyCreateStrategyName", "BaselineStrategyCreateStrategyNameValue", "策略名称");
//      录入，周期值
        co.modelInputBox(param, "BaselineStrategyCreatePeriodic", "BaselineStrategyCreatePeriodicValue", "周期值");
//      录入，时间步长
        co.modelInputBox(param, "BaselineStrategyCreateTimeStep", "BaselineStrategyCreateTimeStepValue", "时间步长");
//      录入，样本密集区域
        co.modelInputBox(param, "BaselineStrategyCreateSampleDensityArea", "BaselineStrategyCreateSampleDensityAreaValue", "样本密集区域");
//      录入，触发时间
        co.modelInputBox(param, "BaselineStrategyCreateTriggerTime", "BaselineStrategyCreateTriggerTimeValue", "触发时间");
//      录入，西格马
        co.modelInputBox(param, "BaselineStrategyCreateSigma", "BaselineStrategyCreateSigmaValue", "西格马");
//      点击，保存
        co.modelClickButton(param, "Save", "value");
//      点击，保存确认
        co.alarmHintAndConfirm(param);
        LogFunction.logInfo("----------------数据分析，基线策略,新增完成---------------------");
    }

    //    数据分析-基线策略-新增-筛选校验
    @Test(dataProvider = "xmldata")
    public void baselineStrategyCreateVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，筛选
        co.select(param, "Area");
//        录入，策略名称
        co.modelInputBox(param, "StrategyName", "BaselineStrategyCreateStrategyNameValue", "策略名称");
//          点击，确定
        co.modelClickButton(param, "Confirm");
//      验证，筛选结果
        co.selectResultVerify(param, "commonSelectResultTwo", "BaselineStrategyCreateStrategyNameValue");
        LogFunction.logInfo("----------------数据分析，基线策略，新建，筛选验证通过---------------------");
    }

    //    数据分析-基线策略-编辑
    @Test(dataProvider = "xmldata")
    public void baselineStrategyEdit(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        勾选,结果
        co.chooseSelectResult(param);
//        点击，编辑按钮
        co.editButton(param);
//      录入，策略名称
        co.modelInputBox(param, "BaselineStrategyCreateStrategyName", "BaselineStrategyEditStrategyNameValue", "策略名称");
//      点击，保存
        co.modelClickButton(param, "Save", "value");
//      点击，保存确认
        co.alarmHintAndConfirm(param);
        LogFunction.logInfo("----------------数据分析，基线策略,编辑完成---------------------");
    }

    //    数据分析-基线策略-编辑-筛选校验
    @Test(dataProvider = "xmldata")
    public void baselineStrategyEditVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，基线策略
        co.modelClickButton(param, "BaselineStrategy");
        //        点击，筛选
        co.select(param, "Area");
//        录入，策略名称
        co.modelInputBox(param, "StrategyName", "BaselineStrategyEditStrategyNameValue", "策略名称");
//          点击，确定
        co.modelClickButton(param, "Confirm");
//      验证，筛选结果
        co.selectResultVerify(param, "commonSelectResultTwo", "BaselineStrategyEditStrategyNameValue");
        LogFunction.logInfo("----------------数据分析，基线策略，编辑，筛选验证通过---------------------");
    }

    //    数据分析-基线策略-删除
    @Test(dataProvider = "xmldata")
    public void baselineStrategyDelete(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        勾选,结果
        co.chooseSelectResult(param);
//        点击，删除按钮
        co.deleteButton(param);
//      点击，删除确认
        co.alarmHintAndConfirm(param);
        LogFunction.logInfo("----------------数据分析，基线策略,删除完成---------------------");
    }

    //    数据分析-基线策略-删除-筛选校验
    @Test(dataProvider = "xmldata")
    public void baselineStrategyDeleteVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，筛选
        co.select(param, "Area");
//        录入，策略名称
        co.modelInputBox(param, "StrategyName", "BaselineStrategyEditStrategyNameValue", "策略名称");
//          点击，确定
        co.modelClickButton(param, "Confirm");
//      验证，筛选结果
        co.selectResultIsNull(param);
        LogFunction.logInfo("----------------数据分析，基线策略，删除，筛选验证通过---------------------");
    }

    //    数据分析-基线应用-新增
    @Test(dataProvider = "xmldata")
    public void baselineApplicationCreate(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，基线应用
        co.modelClickButton(param, "BaselineApplication");
//        点击，新增按钮
        co.newlyIncreasedButton(param);
//      选择，计算方式
        co.modelClickAndChooseValue(param, "Calculation", "CalculationValue", "计算方式");
//       录入，基线标题
        co.modelInputBox(param, "BaselineTitle", "BaselineApplicationCreateBaselineTitleValue", "基线标题");
//        选择，CI类型
        co.modelClickAndChooseValueTwo(param, "CIType", "CITypeValue", "CI类型");
//        选择，CI实例
        co.modelClickAndChooseValue(param, "CIExample", "CIExampleValue", "CI实例");
//        选择，KPI
        co.modelClickAndChooseValue(param, "KPI", "KPIValue", "KPI");
//      点击，下一步1
        co.modelClickButton(param, "NextStep1", "value");
//        选择，计算策略
        co.modelClickAndChooseValue(param, "CalculationStrategy", "CalculationStrategyValue", "计算策略");
//      录入，数值精度
        co.modelInputBox(param, "NumericalPrecision", "BaselineApplicationNumericalPrecisionValue", "数值精度");
//      点击，下一步2
        co.modelClickButton(param, "NextStep2", "value");
//      点击，下一步3
        co.modelClickButton(param, "NextStep3", "value");
//        选择，预警规则
        co.modelClickAndChooseValue(param, "AlarmRules", "AlarmRulesValue", "预警规则");
//      勾选启用
        co.modelRadioBox(param, "Enable");
        //      点击，保存
        co.modelClickButton(param, "Submit", "value");
//      点击，新建确认
        co.alarmConfirm(param);
        LogFunction.logInfo("----------------数据分析，基线策略,新增完成---------------------");
    }

    //    数据分析-基线应用-新增-筛选确认
    @Test(dataProvider = "xmldata")
    public void baselineApplicationCreateVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//  点击，筛选
        co.select(param, "Area");
//  录入，基线名称
        co.modelInputBox(param, "BaselineName", "BaselineApplicationCreateBaselineTitleValue", "动态基线名称");
//  点击，筛选确定
        co.modelClickButton(param, "Confirm");
//   验证，筛选结果
        co.selectResultVerify(param, "commonSelectResultTwo", "BaselineApplicationCreateBaselineTitleValue");
        LogFunction.logInfo("----------------数据分析，基线策略,新建，筛选验证通过---------------------");
    }

    //    数据分析-基线应用-编辑
    @Test(dataProvider = "xmldata")
    public void baselineApplicationEdit(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//      勾选，结果
        co.chooseSelectResult(param);
        //        点击，编辑按钮
        co.editButton(param);
//       录入，基线标题
        co.modelInputBox(param, "BaselineTitle", "BaselineApplicationEditBaselineTitleValue", "基线标题");
//      点击，下一步1
        co.modelClickButton(param, "NextStep1", "value");
//      点击，下一步2
        co.modelClickButton(param, "NextStep2", "value");
//      点击，下一步3
        co.modelClickButton(param, "NextStep3", "value");
//      点击，提交
        co.modelClickButton(param, "Submit", "value");
        //      点击，编辑确认
        co.alarmConfirm(param);
        LogFunction.logInfo("----------------数据分析，基线策略,编辑完成---------------------");
    }

    //    数据分析-基线应用-编辑-筛选确认
    @Test(dataProvider = "xmldata")
    public void baselineApplicationEditVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//  点击，筛选
        co.select(param, "Area");
//  录入，基线名称
        co.modelInputBox(param, "BaselineName", "BaselineApplicationEditBaselineTitleValue", "动态基线名称");
//  点击，筛选确定
        co.modelClickButton(param, "Confirm");
//   验证，筛选结果
        co.selectResultVerify(param, "commonSelectResultTwo", "BaselineApplicationEditBaselineTitleValue");
        LogFunction.logInfo("----------------数据分析，基线策略,编辑，筛选验证通过---------------------");
    }

    //    数据分析-基线应用-编辑-筛选确认
    @Test(dataProvider = "xmldata")
    public void baselineApplicationDeleteAndVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//       删除，结果
        co.deleteFunc(param, "数据分析，基线策略");
//  点击，筛选
        co.select(param, "Area");
//  录入，基线名称
        co.modelInputBox(param, "BaselineName", "BaselineApplicationEditBaselineTitleValue", "动态基线名称");
//  点击，筛选确定
        co.modelClickButton(param, "Confirm");
//   验证，筛选结果
        co.selectResultIsNull(param);
        LogFunction.logInfo("----------------数据分析，基线应用,删除，筛选验证通过---------------------");
    }

    //    视图数据-数据源-新增
    @Test(dataProvider = "xmldata")
    public void viewSourceCreate(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，视图数据
        co.modelAreaDisplayAndClick(param,"ViewDataArea","ViewData");
//        点击，数据源
        co.modelClickButton(param, "DataSource");
//        点击，新增按钮
        co.createButton(param);
//      录入，数据源，名称
        co.modelInputBox(param, "DataSourceName", "DataSourceCreateNameValue", "数据源名称");
//      选择，数据库类型
        co.modelClickAndChooseValueTwo(param, "DataSourceDataLibraryType", "DataSourceDataLibraryTypeValue", "数据库类型");
//        录入，数据URL地址
        co.modelInputBox(param, "DataSourceURLAdress", "DataSourceURLAdressValue", "URL地址");
//        录入，用户名
        co.modelInputBox(param, "DataSourceUserName", "DataSourceUserNameValue", "用户名");
//        录入，密码
        co.modelInputBox(param, "DataSourcePassword", "DataSourcePasswordValue", "密码");
//         点击，数据库类型2次
        co.modelClickButton(param, "DataSourceDataLibraryType", "数据库类型", "");
        co.modelClickButton(param, "DataSourceDataLibraryType", "数据库类型", "");
        co.sleep(500);
        //          点击,测试链接
        co.modelClickButton4(param, "DataSourceTestConnect", "value");
//      获取，提示信息
        String hintMessage = co.getTextValueString(param, "DataSourceTestConnectHint", "text");
        AssertFunction.assertEquals(driver, hintMessage, "连接成功");
//      点击，确认
        co.alarmHintAndConfirm(param);
        co.sleep(500);
//      点击，保存
        co.modelClickButton(param, "DataSourceSave", "value");
        LogFunction.logInfo("----------------视图数据，数据源,新增完成---------------------");
    }

    //    视图数据-数据源-编辑
    @Test(dataProvider = "xmldata")
    public void viewSourceEdit(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        勾选，结果
        co.chooseSelectResultOfCondition(param, "DataSourceCreateNameValue");
//        点击，编辑按钮
        co.editButton(param);
//      录入，数据源，名称
        co.modelInputBox(param, "DataSourceName", "DataSourceEditNameValue", "数据源名称");
//          点击,测试链接
        co.modelClickButton4(param, "DataSourceTestConnect", "value");
//      获取，提示信息
        String hintMessage = co.getTextValueString(param, "DataSourceTestConnectHint", "text");
        AssertFunction.assertEquals(driver, hintMessage, "连接成功");
//      点击，确认
        co.alarmHintAndConfirm(param);
        co.sleep(500);
//      点击，保存
        co.modelClickButton(param, "DataSourceSave", "value");
        LogFunction.logInfo("----------------视图数据，数据源,编辑完成---------------------");
    }

    //    视图数据-数据源-删除
    @Test(dataProvider = "xmldata")
    public void viewSourceDelete(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，视图数据
        co.modelAreaDisplayAndClick(param,"ViewDataArea","ViewData");
//        co.modelClickButton(param, "ViewData");
//        点击，数据源
        co.modelClickButton(param, "DataSource");
//        勾选，结果
        co.chooseSelectResultOfCondition(param, "DataSourceEditNameValue");
//        点击，删除按钮
        co.deleteFunc1(param, param.get("DataSourceEditNameValue"));
//        验证，筛选结果
        co.deleteFuncVerify(param, "DataSourceEditNameValue");
        LogFunction.logInfo("----------------视图数据，数据源,删除验证完成---------------------");
    }


    //    视图数据-SQL数据集-新增
    @Test(dataProvider = "xmldata")
    public void SQLDataSetCreate(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，SQL数据集
        co.modelClickButton(param, "SQLDataSet");
//        点击，新增按钮
        co.createButton(param);
//      录入，SQL数据集，名称
        co.modelInputBox(param, "SQLDataSetName", "SQLDataSetCreateNameValue", "SQL数据集名称");
//      选择，数据源
        co.modelClickAndChooseValueTwo(param, "SQLDataSetCreateDataSource", "SQLDataSetCreateDataSourceValue", "数据源");
//      录入，SQL语句
        co.modelInputBox(param, "SQLDataSetCreateSQL", "SQLDataSetCreateSQLValue", "SQL语句");
//      点击，预览
        co.modelClickButton(param, "SQLDataSetCreatePreview", "value");
        co.sleep(1000);
//      获取，预览结果
        String valueString = co.getTextValueString(param, "SQLDataSetCreatePreviewResult", "text");
        AssertFunction.assertEquals(driver, valueString, passwordValue);
//      点击，保存
        co.modelClickButton(param, "SQLDataSetCreateSave", "value");
        LogFunction.logInfo("----------------视图数据，SQL数据集,新增完成---------------------");
    }

    //    视图数据-SQL数据集-新增-筛选验证
    @Test(dataProvider = "xmldata")
    public void SQLDataSetCreateVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//      点击，筛选
        co.select(param, "SQLDataSetScreenArea");
//      录入，数据集名称
        co.modelInputBox(param, "SQLDataSetScreenDataSetName", "SQLDataSetCreateNameValue", "数据集名称");
//      点击，确定
        co.modelClickButton(param, "SQLDataSetScreenConfirm");
//      验证，筛选结果
        co.selectResultVerify(param, "commonSelectResultTwo", "SQLDataSetCreateNameValue");
        LogFunction.logInfo("----------------视图数据，SQL数据集,新增，筛选验证通过---------------------");

    }

    //    视图数据-SQL数据集-编辑
    @Test(dataProvider = "xmldata")
    public void SQLDataSetEdit(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        勾选，结果
        co.chooseSelectResult(param);
//        点击,编辑按钮
        co.editButton(param);
//      录入，SQL数据集，名称
        co.modelInputBox(param, "SQLDataSetName", "SQLDataSetEditNameValue", "SQL数据集名称");
//      点击，预览
        co.modelClickButton(param, "SQLDataSetCreatePreview", "value");
        co.sleep(1000);
//      获取，预览结果
        String valueString = co.getTextValueString(param, "SQLDataSetCreatePreviewResult", "text");
        AssertFunction.assertEquals(driver, valueString, passwordValue);
//      点击，保存
        co.modelClickButton(param, "SQLDataSetCreateSave", "value");
        LogFunction.logInfo("----------------视图数据，SQL数据集,编辑完成---------------------");
    }

    //      视图数据-SQL数据集-编辑筛选验证
    @Test(dataProvider = "xmldata")
    public void SQLDataSetEditVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//      点击，筛选
        co.select(param, "SQLDataSetScreenArea");
//      录入，数据集名称
        co.modelInputBox(param, "SQLDataSetScreenDataSetName", "SQLDataSetEditNameValue", "数据集名称");
//      点击，确定
        co.modelClickButton(param, "SQLDataSetScreenConfirm");
//      验证，筛选结果
        co.selectResultVerify(param, "commonSelectResultTwo", "SQLDataSetEditNameValue");
        LogFunction.logInfo("----------------视图数据，SQL数据集,编辑，筛选验证通过---------------------");

    }

    //    视图数据-SQL数据集-复制及筛选校验
    @Test(dataProvider = "xmldata")
    public void SQLDataSetCopyAndVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        勾选，结果
        co.chooseSelectResult(param);
//        点击,复制按钮
        co.copyButton(param);
//      点击，筛选
        co.select(param, "SQLDataSetScreenArea");
//      录入，数据集名称
        co.modelInputBox(param, "SQLDataSetScreenDataSetName", param.get("SQLDataSetEditNameValue") + "-复制", "数据集名称");
//      点击，确定
        co.modelClickButton(param, "SQLDataSetScreenConfirm");
//      验证，筛选结果
        co.selectResultVerify(param, "commonSelectResultTwo", param.get("SQLDataSetEditNameValue") + "-复制", "");
        LogFunction.logInfo("----------------视图数据，SQL数据集,复制且筛选验证通过---------------------");
    }

    //    视图数据-SQL数据集-删除及筛选验证
    @Test(dataProvider = "xmldata")
    public void SQLDataSetDeleteAndVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，视图数据
        co.modelAreaDisplayAndClick(param,"ViewDataArea","ViewData");
//        co.modelClickButton(param, "ViewData");
//        点击，SQL数据集
        co.modelClickButton(param, "SQLDataSet");
//      点击，筛选
        co.select(param, "SQLDataSetScreenArea");
//      录入，数据集名称
        co.modelInputBox(param, "SQLDataSetScreenDataSetName", "SQLDataSetEditNameValue", "数据集名称");
//      点击，确定
        co.modelClickButton(param, "SQLDataSetScreenConfirm");
//        勾选，结果
        co.chooseSelectResultAll(param);
//        点击,删除操作
        co.deleteFunc(param, "SQL数据集");
//      点击，筛选
        co.select(param, "SQLDataSetScreenArea");
//      录入，数据集名称
        co.modelInputBox(param, "SQLDataSetScreenDataSetName", "SQLDataSetEditNameValue", "数据集名称");
//      点击，确定
        co.modelClickButton(param, "SQLDataSetScreenConfirm");
//      验证，筛选结果
        co.selectResultIsNull(param);
        LogFunction.logInfo("----------------视图数据，SQL数据集,复制且筛选验证通过---------------------");
    }

    //    集中告警-告警配置-降噪策略-合并规则-合并验证：来源节点、正则表达式、时间窗口
    @Test(dataProvider = "xmldata")
    public void mergeRulesVerifySourceNodeAndRE(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        LogFunction.logInfo("----------------合并验证：来源节点、正则表达式、时间窗口，开始---------------------");
//          点击，新建
        co.createButton(param);
//          录入，规则名称
        co.modelInputBox(param, "rulesName", "alarmMergeRulesNameValue", "规则名称");
//          点击，基础设置，下一步
        co.modelClickButton(param, "basicsNextStep");
        LogFunction.logInfo("第一步，基础设置录入完成");
//          验证，域默认值
        String valueString = co.getTextValueString(param, "domainDefault", "text");
        AssertFunction.verifyEquals(driver, valueString, "rootDomain");
//          选择，类型
        co.modelClickAndChooseValueTwo(param, "type", "chooseType", "类型");
//          选择，告警类型
        co.modelClickAndChooseValueTwo(param, "alarmType", "chooseAlarmType", "告警类型");
//       点击，规则条件设置，下一步
        co.modelClickButton(param, "rulesConditionConfigNextStep");
        LogFunction.logInfo("第二步，规则条件设置录入完成");
//        选择，节点过滤
        co.modelClickAndChooseValue(param, "nodeFilter", "chooseNodeFilter", "节点过滤");
//        选择，采集系统选择
        co.modelClickAndChooseValue(param, "acquisitionSystemChoose", "chooseAcquisitionSystemChoose", "采集系统选择");
//        录入，内容关键字（正则表达式）
        co.modelInputBox(param, "contentKeyword", "contentKeywordValue", "内容关键词");
//       点击，规则条件高级设置，下一步
        co.modelClickButton(param, "rulesConditionAdvancedConfigNextStep");
        LogFunction.logInfo("告警合并策略，第三步，规则条件高级设置录入完成");
//         选择告警合并依据-来源节点
        co.chooseListValue(param, "MergeGist", "MergeGistBox", "chooseMergeGistSourceNode");
//        最大合并数量,录入，4
        co.modelInputBox(param, "maxMergeNumber", "maxMergeNumberValue", "最大合并数量");
//        合并时间窗口(秒)，录入，3
        co.modelInputBox(param, "mergeTimeWindows", "mergeTimeWindowsValue", "合并时间窗口");
//          点击，保存
        co.modelClickButton(param, "alarmMergeConfigSave");

        LogFunction.logInfo("告警合并策略，最后一步，告警合并设置录入完成且告警合并策略创建成功");
        LogFunction.logInfo("----------------推送告警，开始---------------------");
        for (int i = 1; i <= 5; i++) {
            co.pushMergeAlarmInfo(param, "pushMergeAlarmSourceNode1");
            co.pushMergeAlarmInfo(param, "pushMergeAlarmSourceNode2");
            LogFunction.logInfo("推送，第" + i + "次");
            if(i==5){
               break;
            }else {
                co.sleep(1100);
            }
        }
        LogFunction.logInfo("----------------推送告警，结束---------------------");

        co.verifyMergeRule(param);

        LogFunction.logInfo("----------------合并验证：来源节点、正则表达式、时间窗口，结束---------------------");

    }

    //    集中告警-告警配置-降噪策略-合并规则-合并验证：告警类型、最大合并数
    @Test(dataProvider = "xmldata")
    public void mergeRulesVerifyAlarmTypeAndMergeNum(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        LogFunction.logInfo("----------------合并验证：告警类型、最大告警数量，开始---------------------");
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
//      勾选，告警
        co.chooseSelectResultOfCondition(param, "alarmMergeRulesNameValue");
//          点击，编辑
        co.editButton(param);
//          点击，基础设置，下一步
        co.modelClickButton(param, "basicsNextStep");
//          选择，类型
        co.modelClickAndChooseValueTwo(param, "type", "chooseType", "类型");
//          选择，告警类型
        co.modelClickAndChooseValueTwo(param, "alarmType", "chooseAlarmType", "告警类型");
//       点击，规则条件设置，下一步
        co.modelClickButton(param, "rulesConditionConfigNextStep");
//        选择，节点过滤
        co.modelClickAndChooseValue(param, "nodeFilter", "chooseNodeFilter", "节点过滤");
//        清空，内容关键字（正则表达式）
        WebElement contentKeyword = l.getElement(param.get("contentKeyword"));
        contentKeyword.clear();
        LogFunction.logInfo("内容关键字（正则表达式）,清空");
//       点击，规则条件高级设置，下一步
        co.modelClickButton(param, "rulesConditionAdvancedConfigNextStep");
//         选择告警合并依据-告警类型
        co.chooseListValue(param, "MergeGist", "MergeGistBox", "chooseMergeGistAlarmType");
//        最大合并数量,录入，3
        co.modelInputBox(param, "maxMergeNumber", "maxMergeNumberValue", "最大合并数量");
//        合并时间窗口(秒)，录入，4
        co.modelInputBox(param, "mergeTimeWindows", "mergeTimeWindowsValue", "合并时间窗口");
//          点击，保存
        co.modelClickButton(param, "alarmMergeConfigSave");
        LogFunction.logInfo("----------------推送告警，开始---------------------");
        for (int i = 1; i <= 5; i++) {
            co.pushMergeAlarmInfo(param, "pushMergeAlarmAlarmType");
            co.sleep(100);
            LogFunction.logInfo("推送，第" + i + "次");
        }
        LogFunction.logInfo("----------------推送告警，结束---------------------");

        //        点击，告警展示
        co.modelClickButton(param, param.get("alarmDisplay"));
        co.sleep(1000);
//        搜索框录入，Selenium
        co.modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      勾选，筛选结果，通过指定名称勾选。。
        co.sleep(2000);
        List<WebElement> r = l.getElements(param.get("commonSelectResultAll"));
        List<WebElement> r12 = l.getElements(param.get("commonselectResultAll12"));
        LogFunction.logInfo("告警条数为：" + r.size());
        AssertFunction.verifyEquals(driver, r.size(), 2);
        int text = Integer.valueOf(r12.get(0).getText());
        int text1 = Integer.valueOf(r12.get(1).getText());
        LogFunction.logInfo("告警,次数分别为:" + r12.get(0).getText() + "丶" + r12.get(1).getText());
        AssertFunction.verifyEquals(driver, text, 2);
        AssertFunction.verifyEquals(driver, text1, 3);
//        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);
        LogFunction.logInfo("----------------合并验证：告警类型、最大告警数量，结束---------------------");

    }

    //    集中告警-告警配置-降噪策略-合并规则-合并验证：告警类型、策略例外
    @Test(dataProvider = "xmldata")
    public void mergeRulesVerifyAlarmTypeAndStrategyException(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        LogFunction.logInfo("----------------合并验证：告警类型、此策略为例外，开始---------------------");
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
//      勾选，告警
        co.chooseSelectResultOfCondition(param, "alarmMergeRulesNameValue");
//          点击，编辑
        co.editButton(param);
//          点击，基础设置，下一步
        co.modelClickButton(param, "basicsNextStep");
//       点击，规则条件设置，下一步
        co.modelClickButton(param, "rulesConditionConfigNextStep");
//        勾选，此策略为例外
        co.modelRadioBox(param, "strategyException", "此策略为例外");
//       点击，规则条件高级设置，下一步
        co.modelClickButton(param, "rulesConditionAdvancedConfigNextStep");
//          点击，保存
        co.modelClickButton(param, "alarmMergeConfigSave");
        LogFunction.logInfo("----------------推送告警，开始---------------------");
        for (int i = 1; i <= 3; i++) {
            co.pushMergeAlarmInfo(param, "pushMergeAlarmAlarmType");
            co.pushMergeAlarmInfo(param, "pushMergeAlarmAlarmTypeStrategyException");
            co.sleep(100);
            LogFunction.logInfo("推送，第" + i + "次");
        }
        LogFunction.logInfo("----------------推送告警，结束---------------------");

        //        点击，告警展示
        co.modelClickButton(param, param.get("alarmDisplay"));
        co.sleep(1000);
//        搜索框录入，Selenium
        co.modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      验证selenium 告警信息没有合并
        co.sleep(2000);
        List<WebElement> r = l.getElements(param.get("commonSelectResultAll"));
        List<WebElement> r12 = l.getElements(param.get("commonselectResultAll12"));
        LogFunction.logInfo("告警条数为：" + r.size());
        AssertFunction.verifyEquals(driver, r.size(), 3);
        int text1 = Integer.valueOf(r12.get(0).getText());
        LogFunction.logInfo("告警,次数为:" + text1);
        AssertFunction.verifyEquals(driver, text1, 1);
//        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);
        //        搜索框录入，Aele
        co.modelInputBox(param, "alarmDisplaySearchBox", "Aele", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      勾选，筛选结果，通过指定名称勾选。。
        co.sleep(2000);
        List<WebElement> rr = l.getElements(param.get("commonSelectResultAll"));
        List<WebElement> rr12 = l.getElements(param.get("commonselectResultAll12"));
        LogFunction.logInfo("告警条数为：" + rr.size());
        AssertFunction.verifyEquals(driver, rr.size(), 1);
        for (WebElement e : rr12) {
            int text11 = Integer.valueOf(e.getText());
            AssertFunction.verifyEquals(driver, text11, 3);
        }
//        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);
        LogFunction.logInfo("----------------合并验证：告警类型、此策略为例外，结束---------------------");

    }

    //    集中告警-告警配置-降噪策略-合并规则-合并验证：来源实例
    @Test(dataProvider = "xmldata")
    public void mergeRulesVerifySourceInstance(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        LogFunction.logInfo("----------------合并验证：来源实例，开始---------------------");
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
//      勾选，告警
        co.chooseSelectResultOfCondition(param, "alarmMergeRulesNameValue");
//          点击，编辑
        co.editButton(param);
//          点击，基础设置，下一步
        co.modelClickButton(param, "basicsNextStep");
//       点击，规则条件设置，下一步
        co.modelClickButton(param, "rulesConditionConfigNextStep");
//        取消勾选，此策略为例外
        co.modelNoRadioBox(param, "strategyException", "此策略为例外");
//       点击，规则条件高级设置，下一步
        co.modelClickButton(param, "rulesConditionAdvancedConfigNextStep");
//         清空，告警合并依据复选框
        co.modelCheckBoxClearAllOption(param, "MergeGistBox");
//        选择，告警合并依据-来源实例
        co.modelRadioBox(param, "MergeGistBoxOfSourceInstance", "来源实例");
//          点击，保存
        co.modelClickButton(param, "alarmMergeConfigSave");
        LogFunction.logInfo("----------------推送告警，开始---------------------");
        for (int i = 1; i <= 2; i++) {
            co.pushMergeAlarmInfo(param, "pushMergeAlarmAlarmType");
            co.pushMergeAlarmInfo(param, "pushMergeAlarmSourceInstance");
            co.sleep(100);
            LogFunction.logInfo("推送，第" + i + "次");
        }
        LogFunction.logInfo("----------------推送告警，结束---------------------");

        //        点击，告警展示
        co.modelClickButton(param, param.get("alarmDisplay"));
        co.sleep(1000);
//        搜索框录入，Selenium
        co.modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      验证selenium 告警信息没有合并
        co.sleep(2000);
        List<WebElement> r = l.getElements(param.get("commonSelectResultAll"));
        List<WebElement> r12 = l.getElements(param.get("commonselectResultAll12"));
        LogFunction.logInfo("告警条数为：" + r.size());
        AssertFunction.verifyEquals(driver, r.size(), 2);
        int text1 = Integer.valueOf(r12.get(0).getText());
        int text2 = Integer.valueOf(r12.get(1).getText());
        LogFunction.logInfo("告警,次数分别为:" + text1 + "、" + text2);
        AssertFunction.verifyEquals(driver, text1, 2);
        AssertFunction.verifyEquals(driver, text2, 2);
//        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);

        LogFunction.logInfo("----------------合并验证：来源实例，结束---------------------");

    }

    //    集中告警-告警配置-降噪策略-合并规则-合并验证：自动恢复
    @Test(dataProvider = "xmldata")
    public void mergeRulesVerifyAutoRecovery(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        LogFunction.logInfo("----------------合并验证：自动恢复，开始---------------------");
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
//      勾选，告警
        co.chooseSelectResultOfCondition(param, "alarmMergeRulesNameValue");
//          点击，编辑
        co.editButton(param);
//          点击，基础设置，下一步
        co.modelClickButton(param, "basicsNextStep");
//       点击，规则条件设置，下一步
        co.modelClickButton(param, "rulesConditionConfigNextStep");
//       点击，规则条件高级设置，下一步
        co.modelClickButton(param, "rulesConditionAdvancedConfigNextStep");
//         清空，合并选项复选框
        co.modelCheckBoxClearAllOption(param, "MergeOptionBox");
//        选择，合并选项-更新告警内容
        co.modelRadioBox(param, "MergeOptionBoxUpdateAlert", "更新告警内容");
//        选择，合并选项-自动恢复
        co.modelRadioBox(param, "MergeOptionBoxAutoRecovery", "自动恢复");
//          点击，保存
        co.modelClickButton(param, "alarmMergeConfigSave");
        LogFunction.logInfo("----------------推送告警，开始---------------------");
        co.pushMergeAlarmInfo(param, "pushMergeAlarmAlarmType");
        co.sleep(100);
        LogFunction.logInfo("第1条告警，推送成功");
        co.pushMergeAlarmInfo(param, "pushMergeAlarmAlarmType");
        co.sleep(100);
        LogFunction.logInfo("第2条告警，推送成功");
        co.pushMergeAlarmInfo(param, "pushMergeAlarmAlarmType");
        co.sleep(100);
        LogFunction.logInfo("第3条告警，推送成功");
        co.pushMergeAlarmInfo(param, "pushMergeAlarmAlarmType");
        co.sleep(100);
        LogFunction.logInfo("第4条告警，推送成功");
        co.pushMergeAlarmInfo(param, "pushMergeAlarmAlarmRecovery");
        co.sleep(100);
        LogFunction.logInfo("第5条告警，推送成功,告警等级为：-1");
        LogFunction.logInfo("----------------推送告警，结束---------------------");
        //        点击，告警展示
        co.modelClickButton(param, param.get("alarmDisplay"));
        co.sleep(1000);
//        搜索框录入，Selenium
        co.modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      验证selenium 告警信息没有合并
        co.sleep(2000);
        List<WebElement> r = l.getElements(param.get("commonSelectResultAll"));
        List<WebElement> r12 = l.getElements(param.get("commonselectResultAll12"));
        LogFunction.logInfo("告警条数为：" + r.size());
        AssertFunction.verifyEquals(driver, r.size(), 1);
        int text1 = Integer.valueOf(r12.get(0).getText());
        LogFunction.logInfo("告警,次数为:" + text1);
        AssertFunction.verifyEquals(driver, text1, 3);
//        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);
        co.sleep(1000);
        //        点击，已解决告警
        co.modelClickButton(param, "resolvedAlarm", "已解决告警", "");
        co.sleep(1000);
//        搜索框录入，Selenium
        co.modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      验证selenium 告警信息没有合并
        co.sleep(2000);
        List<WebElement> r1 = l.getElements(param.get("commonSelectResultAll"));
        List<WebElement> r121 = l.getElements(param.get("commonselectResultAll12"));
        LogFunction.logInfo("告警条数为：" + r1.size());
        AssertFunction.verifyEquals(driver, r1.size(), 1);
        int text11 = Integer.valueOf(r121.get(0).getText());
        LogFunction.logInfo("告警,次数为:" + text11);
        AssertFunction.verifyEquals(driver, text11, 2);
//        将发送的通知，解决
        co.concentrateAlarmAffirm(param);
        co.sleep(500);
//        点击，已确认告警
        co.modelClickButton(param, "confirmedAlarm", "已确认告警", "");
        co.sleep(1000);
//        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);
        LogFunction.logInfo("----------------合并验证：自动恢复，结束---------------------");

    }

    //    集中告警-告警配置-降噪策略-合并规则-合并验证：告警级别
    @Test(dataProvider = "xmldata")
    public void mergeRulesVerifyAlarmLevel(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        LogFunction.logInfo("----------------合并验证：告警级别，开始---------------------");
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
//      勾选，告警
        co.chooseSelectResultOfCondition(param, "alarmMergeRulesNameValue");
//          点击，编辑
        co.editButton(param);
//          点击，基础设置，下一步
        co.modelClickButton(param, "basicsNextStep");
//       点击，规则条件设置，下一步
        co.modelClickButton(param, "rulesConditionConfigNextStep");
//       点击，规则条件高级设置，下一步
        co.modelClickButton(param, "rulesConditionAdvancedConfigNextStep");
//         清空，告警合并依据复选框
        co.modelCheckBoxClearAllOption(param, "MergeGistBox");
//        选择，告警合并依据-告警级别
        co.modelRadioBox(param, "MergeGistBoxOfAlarmLevel", "告警级别");
//          点击，保存
        co.modelClickButton(param, "alarmMergeConfigSave");
        LogFunction.logInfo("----------------推送告警，开始---------------------");
        for (int i = 1; i <= 2; i++) {
            co.pushMergeAlarmInfo(param, "pushMergeAlarmAlarmType");
            co.pushMergeAlarmInfo(param, "pushMergeAlarmAlarmLevel");
            co.sleep(100);
            LogFunction.logInfo("推送，第" + i + "次");
        }
        LogFunction.logInfo("----------------推送告警，结束---------------------");

        //        点击，告警展示
        co.modelClickButton(param, param.get("alarmDisplay"));
        co.sleep(1000);
//        搜索框录入，Selenium
        co.modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      验证selenium 告警信息没有合并
        co.sleep(2000);
        List<WebElement> r = l.getElements(param.get("commonSelectResultAll"));
        List<WebElement> r12 = l.getElements(param.get("commonselectResultAll12"));
        LogFunction.logInfo("告警条数为：" + r.size());
        AssertFunction.verifyEquals(driver, r.size(), 2);
        int text1 = Integer.valueOf(r12.get(0).getText());
        int text2 = Integer.valueOf(r12.get(1).getText());
        LogFunction.logInfo("告警,次数分别为:" + text1 + "、" + text2);
        AssertFunction.verifyEquals(driver, text1, 2);
        AssertFunction.verifyEquals(driver, text2, 2);
//        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);

        LogFunction.logInfo("----------------合并验证：告警级别，结束---------------------");

    }

    //    集中告警-告警配置-降噪策略-合并规则-合并验证：采集系统
    @Test(dataProvider = "xmldata")
    public void mergeRulesVerifyCollectorSystem(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        LogFunction.logInfo("----------------合并验证：采集系统，开始---------------------");
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
//      勾选，告警
        co.chooseSelectResultOfCondition(param, "alarmMergeRulesNameValue");
//          点击，编辑
        co.editButton(param);
//          点击，基础设置，下一步
        co.modelClickButton(param, "basicsNextStep");
//       点击，规则条件设置，下一步
        co.modelClickButton(param, "rulesConditionConfigNextStep");
//       点击，规则条件高级设置，下一步
        co.modelClickButton(param, "rulesConditionAdvancedConfigNextStep");
//         清空，告警合并依据复选框
        co.modelCheckBoxClearAllOption(param, "MergeGistBox");
//        选择，告警合并依据-采集系统
        co.modelRadioBox(param, "MergeGistBoxOfCollectorSystem", "采集系统");
//          点击，保存
        co.modelClickButton(param, "alarmMergeConfigSave");
        LogFunction.logInfo("----------------推送告警，开始---------------------");
        for (int i = 1; i <= 2; i++) {
            co.pushMergeAlarmInfo(param, "pushMergeAlarmAlarmType");
            co.pushMergeAlarmInfo(param, "pushMergeAlarmCollectSystem");
            co.sleep(100);
            LogFunction.logInfo("推送，第" + i + "次");
        }
        LogFunction.logInfo("----------------推送告警，结束---------------------");

        //        点击，告警展示
        co.modelClickButton(param, param.get("alarmDisplay"));
        co.sleep(1000);
//        搜索框录入，Selenium
        co.modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      验证selenium 告警信息没有合并
        co.sleep(2000);
        List<WebElement> r = l.getElements(param.get("commonSelectResultAll"));
        List<WebElement> r12 = l.getElements(param.get("commonselectResultAll12"));
        LogFunction.logInfo("告警条数为：" + r.size());
        AssertFunction.verifyEquals(driver, r.size(), 1);
        int text1 = Integer.valueOf(r12.get(0).getText());
        LogFunction.logInfo("告警,次数分别为:" + text1);
        AssertFunction.verifyEquals(driver, text1, 2);
//        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);

//        搜索框录入，Aele
        co.modelInputBox(param, "alarmDisplaySearchBox", "Aele", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      验证selenium 告警信息没有合并
        co.sleep(2000);
        List<WebElement> r1 = l.getElements(param.get("commonSelectResultAll"));
        List<WebElement> r112 = l.getElements(param.get("commonselectResultAll12"));
        LogFunction.logInfo("告警条数为：" + r1.size());
        AssertFunction.verifyEquals(driver, r1.size(), 2);
        int text11 = Integer.valueOf(r112.get(0).getText());
        int text12 = Integer.valueOf(r112.get(0).getText());
        LogFunction.logInfo("告警,次数分别为:" + text11 + "、" + text12);
        AssertFunction.verifyEquals(driver, text11, 1);
        AssertFunction.verifyEquals(driver, text12, 1);
//        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);

        LogFunction.logInfo("----------------合并验证：采集系统，结束---------------------");

    }

    //    集中告警-告警配置-降噪策略-合并规则-合并验证：告警内容
    @Test(dataProvider = "xmldata")
    public void mergeRulesVerifyAlarmContent(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        LogFunction.logInfo("----------------合并验证：告警内容，开始---------------------");
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
//      勾选，告警
        co.chooseSelectResultOfCondition(param, "alarmMergeRulesNameValue");
//          点击，编辑
        co.editButton(param);
//          点击，基础设置，下一步
        co.modelClickButton(param, "basicsNextStep");
//       点击，规则条件设置，下一步
        co.modelClickButton(param, "rulesConditionConfigNextStep");
//       点击，规则条件高级设置，下一步
        co.modelClickButton(param, "rulesConditionAdvancedConfigNextStep");
//         清空，告警合并依据复选框
        co.modelCheckBoxClearAllOption(param, "MergeGistBox");
//        选择，告警合并依据-告警内容
        co.modelRadioBox(param, "MergeGistBoxOfAlarmContent", "采集系统");
//          点击，保存
        co.modelClickButton(param, "alarmMergeConfigSave");
        LogFunction.logInfo("----------------推送告警，开始---------------------");
        co.pushMergeAlarmInfo(param, "pushMergeAlarmAlarmType");
        co.sleep(100);
        co.pushMergeAlarmInfo(param, "pushMergeAlarmAlarmType");
        co.sleep(100);
        co.pushMergeAlarmInfo(param, "pushMergeAlarmAlarmContent");
        LogFunction.logInfo("----------------推送告警，结束---------------------");

        //        点击，告警展示
        co.modelClickButton(param, param.get("alarmDisplay"));
        co.sleep(1000);
//        搜索框录入，Selenium
        co.modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      验证selenium 告警信息没有合并
        co.sleep(2000);
        List<WebElement> r = l.getElements(param.get("commonSelectResultAll"));
        List<WebElement> r12 = l.getElements(param.get("commonselectResultAll12"));
        LogFunction.logInfo("告警条数为：" + r.size());
        AssertFunction.verifyEquals(driver, r.size(), 1);
        int text1 = Integer.valueOf(r12.get(0).getText());
        LogFunction.logInfo("告警,次数为:" + text1);
        AssertFunction.verifyEquals(driver, text1, 2);
//        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);
        //        搜索框录入，Aele
        co.modelInputBox(param, "alarmDisplaySearchBox", "Aele", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      勾选，筛选结果，通过指定名称勾选。。
        co.sleep(2000);
        List<WebElement> rr = l.getElements(param.get("commonSelectResultAll"));
        List<WebElement> rr12 = l.getElements(param.get("commonselectResultAll12"));
        LogFunction.logInfo("告警条数为：" + rr.size());
        AssertFunction.verifyEquals(driver, rr.size(), 1);
        for (WebElement e : rr12) {
            int text11 = Integer.valueOf(e.getText());
            AssertFunction.verifyEquals(driver, text11, 1);
        }
//        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);
        LogFunction.logInfo("----------------合并验证：告警内容，结束---------------------");

    }

    //    集中告警-告警配置-降噪策略-合并规则-合并验证：告警内容、绝对时间
    @Test(dataProvider = "xmldata")
    public void mergeRulesVerifyAlarmContentAndAbsoluteTime(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        LogFunction.logInfo("----------------合并验证：告警内容、绝对时间，开始---------------------");
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
//      勾选，告警
        co.chooseSelectResultOfCondition(param, "alarmMergeRulesNameValue");
//          点击，编辑
        co.editButton(param);
//          点击，基础设置，下一步
        co.modelClickButton(param, "basicsNextStep");
//       点击，规则条件设置，下一步
        co.modelClickButton(param, "rulesConditionConfigNextStep");
//         清空，告警时间过滤复选框
        co.modelCheckBoxClearAllOption(param, "AlarmTimeFilterBox");
//        选择，告警合并依据-绝对
        co.modelRadioBox(param, "alarmTimeFilterOfAbsolute", "绝对");
//      录入，最小时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long min = System.currentTimeMillis();
        long max = min + 60 * 1000;
        String formatMin = df.format(new Date(min));
        String formatMax = df.format(new Date(max));
        co.modelInputBox(param, "MinTime", formatMin, "最小时间");
//        录入，最大时间,最小值+25秒
        co.modelInputBox(param, "MaxTime", formatMax, "最大时间");
//       点击，规则条件高级设置，下一步
        co.modelClickButton(param, "rulesConditionAdvancedConfigNextStep");
//        合并时间窗口，录入，60
        co.modelInputBox(param, "mergeTimeWindows", "mergeTimeWindowsValue", "合并时间窗口");
//          点击，保存
        co.modelClickButton(param, "alarmMergeConfigSave");
        LogFunction.logInfo("----------------推送告警，开始---------------------");
        co.pushMergeAlarmInfo(param, "pushMergeAlarmAlarmType");
        co.sleep(100);
        co.pushMergeAlarmInfo(param, "pushMergeAlarmAlarmType");
        co.sleep(60000);
        co.pushMergeAlarmInfo(param, "pushMergeAlarmAlarmType");
        LogFunction.logInfo("----------------推送告警，结束---------------------");

        //        点击，告警展示
        co.modelClickButton(param, param.get("alarmDisplay"));
        co.sleep(1000);
//        搜索框录入，Selenium
        co.modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      验证selenium 告警信息没有合并
        co.sleep(2000);
        List<WebElement> r = this.l.getElements(param.get("commonSelectResultAll"));
        List<WebElement> r12 = this.l.getElements(param.get("commonselectResultAll12"));
        LogFunction.logInfo("告警条数为：" + r.size());
        AssertFunction.verifyEquals(driver, r.size(), 2);
        int text1 = Integer.valueOf(r12.get(0).getText());
        int text2 = Integer.valueOf(r12.get(1).getText());
        LogFunction.logInfo("告警,次数分别为:" + text1 + "丶" + text2);
        AssertFunction.verifyEquals(driver, text1, 1);
        AssertFunction.verifyEquals(driver, text2, 2);
//        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);
        LogFunction.logInfo("----------------合并验证：告警内容、绝对时间，结束---------------------");

    }

    //    集中告警-告警配置-降噪策略-合并规则-删除
    @Test(dataProvider = "xmldata")
    public void mergeRulesVerifyDelete(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
//      勾选，告警
        co.chooseSelectResultOfCondition(param, "alarmMergeRulesNameValue");
//          点击，删除
        co.deleteButton(param);
//        弹出，提示信息
        co.alarmHintAndConfirm(param);
        LogFunction.logInfo("----------------合并规则，删除成功---------------------");
    }


    //    集中告警-告警配置-降噪策略-过滤规则-过滤验证：过滤
    @Test(dataProvider = "xmldata")
    public void filterRulesVerifyFilter(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        LogFunction.logInfo("----------------过滤验证：过滤,开始---------------------");
//          点击，新建
        co.createButton(param);
//          录入，规则名称
        co.modelInputBox(param, "rulesName", "alarmFilterRulesNameValue", "规则名称");
//          点击，基础设置，下一步
        co.modelClickButton(param, "basicsNextStep");
        LogFunction.logInfo("第一步，基础设置录入完成");
//          验证，域默认值
        String valueString = co.getTextValueString(param, "domainDefault", "text");
        AssertFunction.verifyEquals(driver, valueString, "rootDomain");
//          选择，类型
        co.modelClickAndChooseValueTwo(param, "type", "chooseType", "类型");
//          选择，告警类型
        co.modelClickAndChooseValueTwo(param, "alarmType", "chooseAlarmType", "告警类型");
//       点击，规则条件设置，下一步
        co.modelClickButton(param, "rulesConditionConfigNextStep");
        LogFunction.logInfo("第二步，规则条件设置录入完成");
//        取消，节点过滤
        co.modelClickAndChooseValue(param, "nodeFilter", "chooseNodeFilter", "节点过滤");
//        选择，采集系统选择
        co.modelClickAndChooseValue(param, "acquisitionSystemChoose", "chooseAcquisitionSystemChoose", "采集系统选择");
//          点击，保存
        co.modelClickButton(param, "rulesConditionAdvancedConfigSave");
        LogFunction.logInfo("告警过滤策略，最后一步，规则条件高级设置录入完成");
        LogFunction.logInfo("----------------推送告警，开始---------------------");
        co.pushMergeAlarmInfo(param, "pushFilterAlarmOracle");
        LogFunction.logInfo("----------------推送告警，结束---------------------");
//        点击，告警展示
        co.modelClickButton(param, param.get("alarmDisplay"));
        co.sleep(1000);
//        搜索框录入，Selenium
        co.modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      勾选，筛选结果，通过指定名称勾选。。
        co.sleep(2000);
        List<WebElement> r = l.getElements(param.get("commonSelectResultAll"));
        LogFunction.logInfo("告警条数为：" + r.size());
        AssertFunction.verifyEquals(driver, r.size(), 0);
        LogFunction.logInfo("----------------过滤验证：过滤，结束---------------------");
    }

    //    集中告警-告警配置-降噪策略-过滤规则-过滤验证：过滤,此策略为例外
    @Test(dataProvider = "xmldata")
    public void filterRulesVerifyFilterAndStrategyException(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        LogFunction.logInfo("----------------过滤验证：过滤,此策略为例外，开始---------------------");
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
//         勾选，告警
        co.chooseSelectResultOfCondition(param, "alarmFilterRulesNameValue");
//          点击，编辑
        co.editButton(param);
//          点击，基础设置，下一步
        co.modelClickButton(param, "basicsNextStep");
//       点击，规则条件设置，下一步
        co.modelClickButton(param, "rulesConditionConfigNextStep");
//        选择，节点过滤
//        co.modelClickAndChooseValue(param, "nodeFilter", "chooseNodeFilter", "节点过滤");
//        LogFunction.logInfo("取消选择，过滤节点，StandardTest-Oracle2");
//        勾选，此策略为例外
        co.modelRadioBox(param, "strategyException", "此策略为例外");
//       点击，规则条件高级设置，保存
        co.modelClickButton(param, "rulesConditionAdvancedConfigSave");
        LogFunction.logInfo("告警过滤策略，最后一步，规则条件高级设置录入完成");
        LogFunction.logInfo("----------------推送告警，开始---------------------");
        co.pushMergeAlarmInfo(param, "pushFilterAlarmOracle");
        LogFunction.logInfo("----------------推送告警，结束---------------------");
//        点击，告警展示
        co.modelClickButton(param, param.get("alarmDisplay"));
        co.sleep(1000);
//        搜索框录入，Selenium
        co.modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      勾选，筛选结果，通过指定名称勾选。。
        co.sleep(2000);
        List<WebElement> r = l.getElements(param.get("commonSelectResultAll"));
        LogFunction.logInfo("告警条数为：" + r.size());
        AssertFunction.verifyEquals(driver, r.size(), 1);
        //        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);
        LogFunction.logInfo("----------------过滤验证：过滤,此策略为例外，结束---------------------");

    }

    //    集中告警-告警配置-降噪策略-过滤规则-过滤验证：过滤,正则表达式
    @Test(dataProvider = "xmldata")
    public void filterRulesVerifyFilterAndRE(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        LogFunction.logInfo("----------------过滤验证：过滤,正则表达式，开始---------------------");
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
//      勾选，告警
        co.chooseSelectResultOfCondition(param, "alarmFilterRulesNameValue");
//          点击，编辑
        co.editButton(param);
//          点击，基础设置，下一步
        co.modelClickButton(param, "basicsNextStep");
//       点击，规则条件设置，下一步
        co.modelClickButton(param, "rulesConditionConfigNextStep");
//        选择，节点过滤
        co.modelClickAndChooseValue(param, "nodeFilter", "chooseNodeFilter", "节点过滤");
        LogFunction.logInfo("取消选择，过滤节点，StandardTest-Oracle2");
//        录入，内容关键字（正则表达式）
        co.modelInputBox(param, "contentKeyword", "contentKeywordValue", "内容关键词");
//        取消勾选，此策略为例外
        co.modelNoRadioBox(param, "strategyException", "此策略为例外");
//       点击，规则条件高级设置，保存
        co.modelClickButton(param, "rulesConditionAdvancedConfigSave");
        LogFunction.logInfo("告警过滤策略，最后一步，规则条件高级设置录入完成");
        LogFunction.logInfo("----------------推送告警，开始---------------------");
        co.pushMergeAlarmInfo(param, "pushFilterAlarmOracle");
        co.pushMergeAlarmInfo(param, "pushFilterAlarmOracleRE");
        LogFunction.logInfo("----------------推送告警，结束---------------------");
//        点击，告警展示
        co.modelClickButton(param, param.get("alarmDisplay"));
        co.sleep(1000);
//        搜索框录入，Selenium
        co.modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      勾选，筛选结果，通过指定名称勾选。。
        co.sleep(2000);
        List<WebElement> r = l.getElements(param.get("commonSelectResultAll"));
        LogFunction.logInfo("告警条数为：" + r.size());
        AssertFunction.verifyEquals(driver, r.size(), 0);
//        搜索框录入，Aele
        co.modelInputBox(param, "alarmDisplaySearchBox", "Aele", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      勾选，筛选结果，通过指定名称勾选。。
        co.sleep(2000);
        List<WebElement> r1 = l.getElements(param.get("commonSelectResultAll"));
        LogFunction.logInfo("告警条数为：" + r1.size());
        AssertFunction.verifyEquals(driver, r1.size(), 1);
        //        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);
        LogFunction.logInfo("----------------过滤验证：过滤,正则表达式，结束---------------------");

    }

    //    集中告警-告警配置-降噪策略-过滤规则-删除
    @Test(dataProvider = "xmldata")
    public void filterRulesVerifyDelete(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
//      勾选，告警
        co.chooseSelectResultOfCondition(param, "alarmFilterRulesNameValue");
//          点击，删除
        co.deleteButton(param);
//        弹出，提示信息
        co.alarmHintAndConfirm(param);
        LogFunction.logInfo("----------------过滤规则，删除成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-分类规则-分类验证：分类
    @Test(dataProvider = "xmldata")
    public void classifyRulesVerifyClassify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
        co.sleep(1000);
//        点击，全部策略
        co.modelClickButton(param,"allRules");
//        点击，告警分类策略
        co.modelClickButton(param,"alarmClassifyRules");
        co.sleep(1000);
        LogFunction.logInfo("----------------分类验证：分类,开始---------------------");
//          点击，新建
        co.createButton(param);
//          录入，规则名称
        co.modelInputBox(param, "rulesName", "alarmClassifyRulesNameValue", "规则名称");
//          点击，基础设置，下一步
        co.modelClickButton(param, "basicsNextStep");
        LogFunction.logInfo("第一步，基础设置录入完成");
//          验证，域默认值
        String valueString = co.getTextValueString(param, "domainDefault", "text");
        AssertFunction.verifyEquals(driver, valueString, "rootDomain");
//          选择，类型,Oracle
        co.modelClickAndChooseValueTwo(param, "type", "chooseType", "类型");
//          选择，告警类型,Oracle_System_Alert
        co.modelClickAndChooseValueTwo(param, "alarmType", "chooseAlarmType", "告警类型");
//       点击，规则条件设置，下一步
        co.modelClickButton(param, "rulesConditionConfigNextStep");
        LogFunction.logInfo("第二步，规则条件设置录入完成");
//        选择，节点过滤
        co.modelClickAndChooseValue(param, "nodeFilter", "chooseNodeFilter", "节点过滤");
//        选择，采集系统选择
        co.modelClickAndChooseValue(param, "acquisitionSystemChoose", "chooseAcquisitionSystemChoose", "采集系统选择");
////        录入，内容关键字（正则表达式）
//        co.modelInputBox(param, "contentKeyword", "contentKeywordValue", "内容关键词");
//       点击，规则条件高级设置，下一步
        co.modelClickButton(param, "rulesConditionAdvancedConfigNextStep");
        LogFunction.logInfo("第三步，规则条件高级设置录入完成");
//        选择，新的警告类型,Oracle_CPU_Alert
        co.modelClickAndChooseValueTwo(param,"newAlarmType","chooseNewAlarmType","新的警告类型");
//        点击，新的告警级别,INFO
        co.modelClickAndChooseValueThree(param,"newAlarmRank","deselectAll","chooseNewAlarmRank","新的告警级别");
//          点击，保存
        co.modelClickButton(param,"alarmClassifyConfigSave");
        LogFunction.logInfo("告警分类策略，最后一步，告警分类设置录入完成，告警分类策略创建成功");
        LogFunction.logInfo("----------------推送告警，开始---------------------");
        co.pushMergeAlarmInfo(param, "pushClassifyAlarmOracle");
        LogFunction.logInfo("----------------推送告警，结束---------------------");
//        点击，告警展示
        co.modelClickButton(param, param.get("alarmDisplay"));
        co.sleep(1000);
//        搜索框录入，Selenium
        co.modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      勾选，筛选结果，通过指定名称勾选。。
        co.sleep(2000);
        List<WebElement> r = l.getElements(param.get("commonSelectResultAll"));
        LogFunction.logInfo("告警条数为：" + r.size());
        AssertFunction.verifyEquals(driver, r.size(), 1);
//        获取告警级别，并，校验
        String rank = co.getTextValueString(param, "commonSelectResultTwo2", "title");
        LogFunction.logInfo("分类后，告警级别为："+rank);
        AssertFunction.verifyEquals(driver,rank,"INFO");
//        勾选，结果
        co.chooseSelectResult(param);
//        点击，查看
        co.modelClickButton(param,"alarmDisplayView");
//      点击，其他信息
        co.modelClickButton(param,"otherInformation");
//        获取，告警类型，并，校验
        String alarmType = co.getTextValueString(param, "otherInformationAlarmType", "value");
        LogFunction.logInfo("分类后，告警类型为："+alarmType);
        AssertFunction.verifyEquals(driver,alarmType,"Oracle_CPU_Alert");
//      点击，查看，关闭
        co.modelClickButton(param,"alarmDisplayViewCancel");
//        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);
        LogFunction.logInfo("----------------分类验证：分类，结束---------------------");
    }

    //    集中告警-告警配置-降噪策略-分类规则-分类验证：分类，正则表达式
    @Test(dataProvider = "xmldata")
    public void classifyRulesVerifyClassifyAndRE(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        LogFunction.logInfo("----------------分类验证：分类,正则表达式开始---------------------");
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
//      勾选，告警
        co.chooseSelectResultOfCondition(param, "alarmClassifyRulesNameValue");
//          点击，编辑
        co.editButton(param);
//          点击，基础设置，下一步
        co.modelClickButton(param, "basicsNextStep");
        LogFunction.logInfo("第一步，基础设置录入完成");
//       点击，规则条件设置，下一步
        co.modelClickButton(param, "rulesConditionConfigNextStep");
        LogFunction.logInfo("第二步，规则条件设置录入完成");
//        录入，内容关键字（正则表达式）
        co.modelInputBox(param, "contentKeyword", "contentKeywordValue", "内容关键词");
//       点击，规则条件高级设置，下一步
        co.modelClickButton(param, "rulesConditionAdvancedConfigNextStep");
        LogFunction.logInfo("第三步，规则条件高级设置录入完成");
//          点击，保存
        co.modelClickButton(param,"alarmClassifyConfigSave");
        LogFunction.logInfo("告警分类策略，最后一步，告警分类设置录入完成，告警分类策略创建成功");
        LogFunction.logInfo("----------------推送告警，开始---------------------");
        co.pushMergeAlarmInfo(param, "pushClassifyAlarmOracle");
        co.pushMergeAlarmInfo(param, "pushClassifyAlarmOracleRE");
        LogFunction.logInfo("----------------推送告警，结束---------------------");
//        点击，告警展示
        co.modelClickButton(param, param.get("alarmDisplay"));
        co.sleep(1000);
//        搜索框录入，Selenium
        co.modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      勾选，筛选结果，通过指定名称勾选。。
        co.sleep(2000);
        List<WebElement> r = l.getElements(param.get("commonSelectResultAll"));
        LogFunction.logInfo("告警条数为：" + r.size());
        AssertFunction.verifyEquals(driver, r.size(), 1);
//        获取告警级别，并，校验
        String rank = co.getTextValueString(param, "commonSelectResultTwo2", "title");
        LogFunction.logInfo("分类后，告警级别为："+rank);
        AssertFunction.verifyEquals(driver,rank,"INFO");
//        勾选，结果
        co.chooseSelectResult(param);
//        点击，查看
        co.modelClickButton(param,"alarmDisplayView");
//      点击，其他信息
        co.modelClickButton(param,"otherInformation");
//        获取，告警类型，并，校验
        String alarmType = co.getTextValueString(param, "otherInformationAlarmType", "value");
        LogFunction.logInfo("分类后，告警类型为："+alarmType);
        AssertFunction.verifyEquals(driver,alarmType,"Oracle_CPU_Alert");
//      点击，查看，关闭
        co.modelClickButton(param,"alarmDisplayViewCancel");
//        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);

//        搜索框录入，Aele
        co.modelInputBox(param, "alarmDisplaySearchBox", "Aele", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      勾选，筛选结果，通过指定名称勾选。。
        co.sleep(2000);
        List<WebElement> r1 = l.getElements(param.get("commonSelectResultAll"));
        LogFunction.logInfo("告警条数为：" + r1.size());
        AssertFunction.verifyEquals(driver, r1.size(), 1);
//        获取告警级别，并，校验
        String rank1 = co.getTextValueString(param, "commonSelectResultTwo2", "title");
        LogFunction.logInfo("分类后，告警级别为："+rank1);
        AssertFunction.verifyEquals(driver,rank1,"MINOR");
//        勾选，结果
        co.chooseSelectResult(param);
//        点击，查看
        co.modelClickButton(param,"alarmDisplayView");
//      点击，其他信息
        co.modelClickButton(param,"otherInformation");
//        获取，告警类型，并，校验
        String alarmType1 = co.getTextValueString(param, "otherInformationAlarmType", "value");
        LogFunction.logInfo("分类后，告警类型为："+alarmType1);
        AssertFunction.verifyEquals(driver,alarmType1,"Oracle_System_Alert");
//      点击，查看，关闭
        co.modelClickButton(param,"alarmDisplayViewCancel");
//        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);
        LogFunction.logInfo("----------------分类验证：分类，结束---------------------");
    }

    //    集中告警-告警配置-降噪策略-分类规则-分类验证：分类，合并
    @Test(dataProvider = "xmldata")
    public void classifyRulesVerifyClassifyAndMerge(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        LogFunction.logInfo("----------------分类验证：分类,正则表达式开始---------------------");
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
//      勾选，告警
        co.chooseSelectResultOfCondition(param, "alarmClassifyRulesNameValue");
//          点击，编辑
        co.editButton(param);
//          点击，基础设置，下一步
        co.modelClickButton(param, "basicsNextStep");
        LogFunction.logInfo("第一步，基础设置录入完成");
//       点击，规则条件设置，下一步
        co.modelClickButton(param, "rulesConditionConfigNextStep");
        LogFunction.logInfo("第二步，规则条件设置录入完成");
//        清空，内容关键字（正则表达式）
        co.modelInputBoxClear(param, "contentKeyword","内容关键词");
//       点击，规则条件高级设置，下一步
        co.modelClickButton(param, "rulesConditionAdvancedConfigNextStep");
        LogFunction.logInfo("第三步，规则条件高级设置录入完成");
//          点击，保存
        co.modelClickButton(param,"alarmClassifyConfigSave");
        LogFunction.logInfo("----------------合并规则，新建，开始---------------------");
//        点击，全部规则+合并规则
        denoiseStrategyChooseAlarmMergeRules(param);
//          点击，新建
        co.createButton(param);
//          录入，规则名称
        co.modelInputBox(param, "rulesName", "alarmMergeRulesNameValue", "规则名称");
//          点击，基础设置，下一步
        co.modelClickButton(param, "basicsNextStep");
        LogFunction.logInfo("第一步，基础设置录入完成");
//          验证，域默认值
        String valueString = co.getTextValueString(param, "domainDefault", "text");
        AssertFunction.verifyEquals(driver, valueString, "rootDomain");
//          选择，类型
        co.modelClickAndChooseValueTwo(param, "type", "chooseType", "类型");
//          选择，告警类型
        co.modelClickAndChooseValueTwo(param, "alarmType", "chooseAlarmType", "告警类型");
//       点击，规则条件设置，下一步
        co.modelClickButton(param, "rulesConditionConfigNextStep");
        LogFunction.logInfo("第二步，规则条件设置录入完成");
//        选择，节点过滤
        co.modelClickAndChooseValue(param, "nodeFilter", "chooseNodeFilter", "节点过滤");
//        选择，采集系统选择
//        co.modelClickAndChooseValue(param, "acquisitionSystemChoose", "chooseAcquisitionSystemChoose", "采集系统选择");
//       点击，规则条件高级设置，下一步
        co.modelClickButton(param, "rulesConditionAdvancedConfigNextStep");
        LogFunction.logInfo("告警合并策略，第三步，规则条件高级设置录入完成");
//         选择告警合并依据-来源节点
        co.chooseListValue(param, "MergeGist", "MergeGistBox", "chooseMergeGistSourceNode");
//        最大合并数量,录入，4
        co.modelInputBox(param, "maxMergeNumber", "maxMergeNumberValue", "最大合并数量");
//        合并时间窗口(秒)，录入，3
        co.modelInputBox(param, "mergeTimeWindows", "mergeTimeWindowsValue", "合并时间窗口");
//          点击，保存
        co.modelClickButton(param, "alarmMergeConfigSave");
        LogFunction.logInfo("----------------合并规则，创建，结束---------------------");

        LogFunction.logInfo("告警分类策略，最后一步，告警分类设置录入完成，告警分类策略创建成功");
        LogFunction.logInfo("----------------推送告警，开始---------------------");
        co.pushMergeAlarmInfo(param, "pushClassifyAlarmOracle");
        co.sleep(100);
        co.pushMergeAlarmInfo(param, "pushClassifyAlarmOracle");
        LogFunction.logInfo("----------------推送告警，结束---------------------");
//        点击，告警展示
        co.modelClickButton(param, param.get("alarmDisplay"));
        co.sleep(1000);
//        搜索框录入，Selenium
        co.modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      勾选，筛选结果，通过指定名称勾选。。
        co.sleep(2000);
        List<WebElement> r = l.getElements(param.get("commonSelectResultAll"));
        List<WebElement> r12 = l.getElements(param.get("commonselectResultAll12"));
        LogFunction.logInfo("告警条数为：" + r.size());
        AssertFunction.verifyEquals(driver, r.size(), 1);
        int text = Integer.valueOf(r12.get(0).getText());
        LogFunction.logInfo("告警,次数分别为:" + r12.get(0).getText());
        AssertFunction.verifyEquals(driver, text, 2);

//        获取告警级别，并，校验
        String rank = co.getTextValueString(param, "commonSelectResultTwo2", "title");
        LogFunction.logInfo("分类后，告警级别为："+rank);
        AssertFunction.verifyEquals(driver,rank,"INFO");
//        勾选，结果
        co.chooseSelectResult(param);
//        点击，查看
        co.modelClickButton(param,"alarmDisplayView");
//      点击，其他信息
        co.modelClickButton(param,"otherInformation");
//        获取，告警类型，并，校验
        String alarmType = co.getTextValueString(param, "otherInformationAlarmType", "value");
        LogFunction.logInfo("分类后，告警类型为："+alarmType);
        AssertFunction.verifyEquals(driver,alarmType,"Oracle_CPU_Alert");
//      点击，查看，关闭
        co.modelClickButton(param,"alarmDisplayViewCancel");
//        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);
        LogFunction.logInfo("----------------分类验证：分类，结束---------------------");
    }


    //    集中告警-告警配置-降噪策略-分类规则-删除
    @Test(dataProvider = "xmldata")
    public void classifyRulesVerifyDelete(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
//      勾选，告警规则，合并+分类
        co.chooseSelectResultOfCondition(param, "alarmMergeRulesNameValue");
        co.chooseSelectResultOfCondition(param, "alarmClassifyRulesNameValue");
//          点击，删除
        co.deleteButton(param);
//        弹出，提示信息
        co.alarmHintAndConfirm(param);
        LogFunction.logInfo("----------------分类、合并规则，删除成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-恢复规则-恢复验证：恢复，清出内存，时间门限
    @Test(dataProvider = "xmldata")
    public void recoveryRulesVerifyRecoveryAndClearMemoryRE(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
        co.sleep(1000);
//        点击，全部策略
        co.modelClickButton(param,"allRules");
//        点击，告警恢复策略
        co.modelClickButton(param,"alarmRecoveryRules");
        co.sleep(1000);
        LogFunction.logInfo("----------------恢复验证：恢复、清出内存、时间门限、正则表达式，开始---------------------");
//          点击，新建
        co.createButton(param);
//          录入，规则名称
        co.modelInputBox(param, "rulesName", "alarmRecoveryRulesNameValue", "规则名称");
//          点击，基础设置，下一步
        co.modelClickButton(param, "basicsNextStep");
        LogFunction.logInfo("第一步，基础设置录入完成");
//          验证，域默认值
        String valueString = co.getTextValueString(param, "domainDefault", "text");
        AssertFunction.verifyEquals(driver, valueString, "rootDomain");
//          选择，类型,Oracle
        co.modelClickAndChooseValueTwo(param, "type", "chooseType", "类型");
//          选择，告警类型,Oracle_System_Alert
        co.modelClickAndChooseValueTwo(param, "alarmType", "chooseAlarmType", "告警类型");
//       点击，规则条件设置，下一步
        co.modelClickButton(param, "rulesConditionConfigNextStep");
        LogFunction.logInfo("第二步，规则条件设置录入完成");
//        选择，节点过滤
        co.modelClickAndChooseValue(param, "nodeFilter", "chooseNodeFilter", "节点过滤");
//        选择，采集系统选择
        co.modelClickAndChooseValue(param, "acquisitionSystemChoose", "chooseAcquisitionSystemChoose", "采集系统选择");
//        录入，内容关键字（正则表达式）
        co.modelInputBox(param, "contentKeyword", "contentKeywordValue", "内容关键词");
//       点击，规则条件高级设置，下一步
        co.modelClickButton(param, "rulesConditionAdvancedConfigNextStep");
        LogFunction.logInfo("第三步，规则条件高级设置录入完成");
//        恢复时间门限，录入，1
        co.modelInputBox(param,"recoveryTimeThreshold","recoveryTimeThresholdValue","恢复时间门限");
//        选择，清出内存
        co.modelCheckBoxClearAllOption(param,"recoveryOption");
        co.modelRadioBox(param,"recoveryOptionClearMemory","清出内存");
//          点击，保存
        co.modelClickButton(param,"alarmClassifyConfigSave");
        LogFunction.logInfo("告警恢复策略，最后一步，告警恢复策略录入完成，告警恢复策略创建成功");
        LogFunction.logInfo("----------------推送告警，开始---------------------");
        for (int i = 1; i < 4; i++) {
            co.pushMergeAlarmInfo(param, "pushRecoveryAlarmSelenium");
            co.sleep(100);
            co.pushMergeAlarmInfo(param, "pushRecoveryAlarmAele");
            LogFunction.logInfo("第"+i+"次，推送告警，完成");
            if (i==3){
                break;
            }else{
                co.sleep(40000);
            }
        }
        LogFunction.logInfo("----------------推送告警，结束---------------------");
//        点击，告警展示
        co.modelClickButton(param, param.get("alarmDisplay"));
        co.sleep(1000);
//        搜索框录入，Selenium
        co.modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      勾选，筛选结果，通过指定名称勾选。。
        co.sleep(2000);
        List<WebElement> r = l.getElements(param.get("commonSelectResultAll"));
        LogFunction.logInfo("告警条数为：" + r.size());
        AssertFunction.verifyEquals(driver, r.size(), 2);
//        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);
//        搜索框录入，Aele
        co.modelInputBox(param, "alarmDisplaySearchBox", "Aele", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      勾选，筛选结果，通过指定名称勾选。。
        co.sleep(2000);
        List<WebElement> r1 = l.getElements(param.get("commonSelectResultAll"));
        LogFunction.logInfo("告警条数为：" + r1.size());
        AssertFunction.verifyEquals(driver, r1.size(), 3);
//        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);
        LogFunction.logInfo("----------------恢复验证：恢复、清出内存、时间门限、正则表达式，结束---------------------");
    }

    //    集中告警-告警配置-降噪策略-恢复规则-恢复验证：恢复、设置为忽略、此策略为例外
    @Test(dataProvider = "xmldata")
    public void recoveryRulesVerifyRecoveryAndIgnoreStrategyException(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
        co.sleep(1000);
        LogFunction.logInfo("----------------恢复验证：恢复、设置为忽略、此策略为例外，开始---------------------");
//          勾选，告警
        co.chooseSelectResultOfCondition(param, "alarmRecoveryRulesNameValue");
//          点击，编辑
        co.editButton(param);
//          点击，基础设置，下一步
        co.modelClickButton(param, "basicsNextStep");
        LogFunction.logInfo("第一步，基础设置录入完成");
//       点击，规则条件设置，下一步
        co.modelClickButton(param, "rulesConditionConfigNextStep");
        LogFunction.logInfo("第二步，规则条件设置录入完成");
//        清空，内容关键字（正则表达式）
        co.modelInputBoxClear(param, "contentKeyword", "内容关键词");
//        勾选，此策略为例外
        co.modelRadioBox(param, "strategyException", "此策略为例外");
//       点击，规则条件高级设置，下一步
        co.modelClickButton(param, "rulesConditionAdvancedConfigNextStep");
        LogFunction.logInfo("第三步，规则条件高级设置录入完成");
//        选择，设置为忽略
        co.modelCheckBoxClearAllOption(param,"recoveryOption");
        co.modelRadioBox(param,"recoveryOptionSetIgnore","设置为忽略");
//          点击，保存
        co.modelClickButton(param,"alarmClassifyConfigSave");
        LogFunction.logInfo("告警恢复策略，最后一步，告警恢复策略录入完成，告警恢复策略创建成功");
        LogFunction.logInfo("----------------推送告警，开始---------------------");
        for (int i = 1; i < 4; i++) {
            co.pushMergeAlarmInfo(param, "pushRecoveryAlarmSelenium");
            LogFunction.logInfo("第"+i+"次，推送告警");
            if (i==3){
                break;
            }else{
                co.sleep(40000);
            }
        }
        LogFunction.logInfo("----------------推送告警，结束---------------------");
//        点击，告警展示
        co.modelClickButton(param, param.get("alarmDisplay"));
        co.sleep(1000);
//        搜索框录入，Selenium
        co.modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      勾选，筛选结果，通过指定名称勾选。。
        co.sleep(2000);
        List<WebElement> r = l.getElements(param.get("commonSelectResultAll"));
        LogFunction.logInfo("告警条数为：" + r.size());
        AssertFunction.verifyEquals(driver, r.size(), 3);
//        将发送的告警，解决，清空内存
        co.resolvedClearMemory(param);
        //        点击，已忽略告警
        co.modelClickButton(param, "alarmDisplayIgnoreAlarm", "已忽略告警", "");
        co.sleep(1000);
//        搜索框录入，Selenium
        co.modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      勾选，筛选结果，通过指定名称勾选。。
        co.sleep(2000);
        List<WebElement> r1 = l.getElements(param.get("commonSelectResultAll"));
        LogFunction.logInfo("告警条数为：" + r1.size());
        AssertFunction.verifyEquals(driver, r1.size(), 0);
        if(r1.size()!=0){
//        将发送的告警，解决，清空内存
            co.resolvedClearMemory(param);
        }
        LogFunction.logInfo("----------------恢复验证：恢复、设置为忽略、此策略为例外，结束---------------------");
    }

    //    集中告警-告警配置-降噪策略-恢复规则-恢复验证：恢复、合并、忽略
    @Test(dataProvider = "xmldata")
    public void recoveryRulesVerifyRecoveryAndMerge(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
        co.sleep(1000);
        LogFunction.logInfo("----------------恢复验证：恢复、合并、忽略，开始---------------------");
//          勾选，告警
        co.chooseSelectResultOfCondition(param, "alarmRecoveryRulesNameValue");
//          点击，编辑
        co.editButton(param);
//          点击，基础设置，下一步
        co.modelClickButton(param, "basicsNextStep");
        LogFunction.logInfo("第一步，基础设置录入完成");
//       点击，规则条件设置，下一步
        co.modelClickButton(param, "rulesConditionConfigNextStep");
        LogFunction.logInfo("第二步，规则条件设置录入完成");
//        取消勾选，此策略为例外
        co.modelNoRadioBox(param, "strategyException", "此策略为例外");
//       点击，规则条件高级设置，下一步
        co.modelClickButton(param, "rulesConditionAdvancedConfigNextStep");
        LogFunction.logInfo("第三步，规则条件高级设置录入完成");
//          点击，保存
        co.modelClickButton(param,"alarmClassifyConfigSave");
        LogFunction.logInfo("告警恢复策略，最后一步，告警恢复策略录入完成，告警恢复策略创建成功");
        LogFunction.logInfo("----------------合并规则，新建，开始---------------------");
//        点击，全部规则+合并规则
        denoiseStrategyChooseAlarmMergeRules(param);
//          点击，新建
        co.createButton(param);
//          录入，规则名称
        co.modelInputBox(param, "rulesName", "alarmMergeRulesNameValue", "规则名称");
//          点击，基础设置，下一步
        co.modelClickButton(param, "basicsNextStep");
        LogFunction.logInfo("第一步，基础设置录入完成");
//          验证，域默认值
        String valueString = co.getTextValueString(param, "domainDefault", "text");
        AssertFunction.verifyEquals(driver, valueString, "rootDomain");
//          选择，类型
        co.modelClickAndChooseValueTwo(param, "type", "chooseType", "类型");
//          选择，告警类型
        co.modelClickAndChooseValueTwo(param, "alarmType", "chooseAlarmType", "告警类型");
//       点击，规则条件设置，下一步
        co.modelClickButton(param, "rulesConditionConfigNextStep");
        LogFunction.logInfo("第二步，规则条件设置录入完成");
//        选择，节点过滤
        co.modelClickAndChooseValue(param, "nodeFilter", "chooseNodeFilter", "节点过滤");
//        选择，采集系统选择
//        co.modelClickAndChooseValue(param, "acquisitionSystemChoose", "chooseAcquisitionSystemChoose", "采集系统选择");
//       点击，规则条件高级设置，下一步
        co.modelClickButton(param, "rulesConditionAdvancedConfigNextStep");
        LogFunction.logInfo("告警合并策略，第三步，规则条件高级设置录入完成");
//         选择告警合并依据-来源节点
        co.chooseListValue(param, "MergeGist", "MergeGistBox", "chooseMergeGistSourceNode");
//        最大合并数量,录入，4
        co.modelInputBox(param, "maxMergeNumber", "maxMergeNumberValue", "最大合并数量");
//        合并时间窗口(秒)，录入，600
        co.modelInputBox(param, "mergeTimeWindows", "mergeTimeWindowsValue", "合并时间窗口");
//          点击，保存
        co.modelClickButton(param, "alarmMergeConfigSave");
        LogFunction.logInfo("----------------合并规则，创建，结束---------------------");
        LogFunction.logInfo("----------------推送告警，开始---------------------");
        for (int i = 1; i < 4; i++) {
            co.pushMergeAlarmInfo(param, "pushRecoveryAlarmSelenium");
            LogFunction.logInfo("第"+i+"次，推送告警");
            if (i==3){
                break;
            }else{
                co.sleep(40000);
            }
        }
        LogFunction.logInfo("----------------推送告警，结束---------------------");
//        点击，告警展示
        co.modelClickButton(param, param.get("alarmDisplay"));
        co.sleep(1000);
//        搜索框录入，Selenium
        co.modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      勾选，筛选结果，通过指定名称勾选。。
        co.sleep(2000);
        List<WebElement> r = l.getElements(param.get("commonSelectResultAll"));
        LogFunction.logInfo("告警条数为：" + r.size());
        AssertFunction.verifyEquals(driver, r.size(), 1);
        if(r.size()!=0){
//        将发送的告警，解决，清空内存
            co.resolvedClearMemory(param);
        }
        //        点击，已忽略告警
        co.modelClickButton(param, "alarmDisplayIgnoreAlarm", "已忽略告警", "");
        co.sleep(1000);
//        搜索框录入，Selenium
        co.modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        co.modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      勾选，筛选结果，通过指定名称勾选。。
        co.sleep(2000);
        List<WebElement> r1 = l.getElements(param.get("commonSelectResultAll"));
        LogFunction.logInfo("告警条数为：" + r1.size());
        AssertFunction.verifyEquals(driver, r1.size(), 1);
        if(r1.size()!=0){
//        将发送的告警，解决，清空内存
            co.resolvedClearMemory(param);
        }
        LogFunction.logInfo("----------------恢复验证：恢复、合并、忽略，结束---------------------");
    }

    //    集中告警-告警配置-降噪策略-恢复规则-删除
    @Test(dataProvider = "xmldata")
    public void recoveryRulesVerifyDelete(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
//        点击，告警配置，降噪策略
        co.alarmConfigurationDenoiseStrategy(param);
//      勾选，告警规则，合并+恢复
        co.chooseSelectResultOfCondition(param, "alarmMergeRulesNameValue");
        co.chooseSelectResultOfCondition(param, "alarmRecoveryRulesNameValue");
//          点击，删除
        co.deleteButton(param);
//        弹出，提示信息
        co.alarmHintAndConfirm(param);
        LogFunction.logInfo("----------------恢复，合并规则，删除成功---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void test0001(Map<String, String> param) throws InterruptedException {


//        driver.get("https://www.hao123.com/");
//        co.sleep(2000);
//        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
//        LogFunction.logInfo("1213");
//        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 700)");
//        Thread.sleep(3000);
////结合上面的scrollBy语句，相当于移动到700+800=1600像素位置
//        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 800)");
////移动到窗口绝对位置坐标，如下移动到纵坐标1600像素位置
//        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 1600)");
//        Thread.sleep(3000);
////结合上面的scrollTo语句，仍然移动到纵坐标1200像素位置
//        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 1200)");
    }
}