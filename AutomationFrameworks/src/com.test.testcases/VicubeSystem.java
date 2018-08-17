package com.test.testcases;

import com.sun.javafx.binding.SelectBinding;
import com.test.base.TestBase;
import com.test.utill.*;
import org.apache.log4j.lf5.LF5Appender;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

import java.sql.SQLException;
import java.time.Year;
import java.util.Map;

@SuppressWarnings("ALL")
public class VicubeSystem extends TestBase {
    public WebDriver driver;
    private LocatorFunction l;
    private ScreenshotFunction ssf;

//    -------------------------------------登录，系统设置-------------------------------------

    @Test(dataProvider = "xmldata")
    public void userLogin(Map<String, String> param) {
        driver.get(param.get("VicubeUrl"));
        LogFunction.logInfo("网址成功打开：" + param.get("VicubeUrl"));
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
        AssertFunction.verifyEquals(driver, text, "登陆", "----是否点击登陆----");
//      验证用户登陆标识
        WebElement userLoginID = l.getElement(param.get("userLoginID"));
        String text1 = userLoginID.getText();
        LogFunction.logInfo("用户登陆标识为：" + text1);
        AssertFunction.verifyEquals(driver, text1, param.get("nameValue"), "----是否点击登陆----");
        LogFunction.logInfo("------------------" + text1 + "登陆成功-------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userInvalidLogin(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.get(param.get("VicubeUrl"));
        LogFunction.logInfo("网址成功打开：" + param.get("VicubeUrl"));
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
        //        验证，提示信息：用户名或密码错误！
        WebElement editHint = l.getElement(param.get("commonHint"));
        String text3 = editHint.getText();
        AssertFunction.verifyEquals(driver, text3, "用户名或密码错误！", "----验证提示信息：用户名或密码错误！，是否正确----");
//        点击，确认
        WebElement editAffirm = l.getElement(param.get("commonAffirm"));
        String text4 = editAffirm.getText();
        editAffirm.click();
        LogFunction.logInfo("点击：" + text4);
        LogFunction.logInfo("----------------------用户无效登陆结束------------------------");

    }

    @Test(dataProvider = "xmldata")
    public void vicubeLogin(Map<String, String> param) {
        driver.get(param.get("VicubeUrl"));
        LogFunction.logInfo("网址成功打开：" + param.get("VicubeUrl"));
        //      输入用户名
        WebElement user = l.getElement(param.get("user"));
        user.clear();
        user.sendKeys(param.get("userValue"));
        LogFunction.logInfo("用户名：" + param.get("userValue"));
        //      输入密码
        WebElement password = l.getElement(param.get("password"));
        password.clear();
        password.sendKeys(param.get("passwordValue"));
        LogFunction.logInfo("密码：" + param.get("passwordValue"));
        //      点击登陆按钮
        WebElement button = l.getElement(param.get("button"));
        String text = button.getText();
        button.click();
        LogFunction.logInfo("点击按钮:" + text);
        AssertFunction.verifyEquals(driver, text, "登陆", "----是否点击登陆----");
        //      验证用户登陆标识
        WebElement userLoginID = l.getElement(param.get("userLoginID"));
        String text1 = userLoginID.getText();
        LogFunction.logInfo("用户登陆标识为：" + text1);
        AssertFunction.verifyEquals(driver, text1, "超级管理员", "----是否点击登陆----");
        LogFunction.logInfo("------------------" + text1 + "登陆成功-------------------");
    }

    @Test(dataProvider = "xmldata")
    public void systemSetting(Map<String, String> param) {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        点击，系统设置
        WebElement systemSetting = l.getElement(param.get("systemSetting"));
        String text = systemSetting.getText();
        systemSetting.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "系统设置", "----当前菜单是否为系统设置----");
        LogFunction.logInfo("-----------------打开系统设置菜单---------------------");
        ssf.takeScreenshot();
    }

    @Test(dataProvider = "xmldata")
    public void userManagement(Map<String, String> param) {
//        点击，用户管理
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        WebElement userManagement = l.getElement(param.get("userManagement"));
        String text1 = userManagement.getText();
        userManagement.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "用户管理", "----当前菜单是否为用户管理----");
        LogFunction.logInfo("-----------------打开用户管理菜单---------------------");

    }

    @Test(dataProvider = "xmldata")
    public void workingGroupManagement(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        点击，工作组管理
        WebElement workingGroupManagement = l.getElement(param.get("workingGroupManagement"));
        String text = workingGroupManagement.getText();
        workingGroupManagement.click();
        LogFunction.logInfo("点击；" + text);
        AssertFunction.verifyEquals(driver, text, "工作组管理", "----验证是否点击：工作组管理----");
        LogFunction.logInfo("-----------------打开工作组管理菜单---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userManagementCreate(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        点击，新建
        WebElement userManagementCreate = l.getElement(param.get("commonCreate"));
        String text = userManagementCreate.getText();
        userManagementCreate.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "新建", "----是否进入新建页----");
//         验证，保存按钮是否可用
        boolean enabled;
        WebElement save = l.getElement(param.get("createSave"));
        enabled = save.isEnabled();
        LogFunction.logInfo(save.getText() + "：按钮的状态");
        AssertFunction.verifyEquals(driver, enabled, "false", "----验证保存按钮是否可用----");
//          填写用户名
        WebElement userName = l.getElement(param.get("userName"));
        userName.sendKeys(param.get("userNameValue"));
        LogFunction.logInfo("在用户名中填入：" + param.get("userNameValue"));
//         验证，保存按钮是否可用
        enabled = save.isEnabled();
        LogFunction.logInfo(save.getText() + "：按钮的状态");
        AssertFunction.verifyEquals(driver, enabled, "false", "----验证保存按钮是否可用----");
//        填写姓名
        WebElement name = l.getElement(param.get("name"));
        name.sendKeys(param.get("nameValue"));
        LogFunction.logInfo("在姓名中填入：" + param.get("nameValue"));
//         验证，保存按钮是否可用
        enabled = save.isEnabled();
        LogFunction.logInfo(save.getText() + "：按钮的状态");
        AssertFunction.verifyEquals(driver, enabled, "false", "----验证保存按钮是否可用----");
//        填写邮箱
        WebElement email = l.getElement(param.get("email"));
        email.sendKeys(param.get("emailValue"));
        LogFunction.logInfo("在邮箱中填入：" + param.get("emailValue"));
//         验证，保存按钮是否可用
        enabled = save.isEnabled();
        LogFunction.logInfo(save.getText() + "：按钮的状态");
        AssertFunction.verifyEquals(driver, enabled, "ture", "----验证保存按钮是否可用----");
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
//        点击有效标志输入框
        WebElement flag = l.getElement(param.get("commonCraeteFlag"));
        flag.click();
        LogFunction.logInfo("点击有效标志输入框");
//        选择有效标识：有效
        WebElement valid = l.getElement(param.get("valid"));
        String text1 = valid.getText();
        valid.click();
        LogFunction.logInfo("选择：" + text1);
//         验证，保存按钮是否可用
        enabled = save.isEnabled();
        LogFunction.logInfo(save.getText() + "：按钮的状态");
        AssertFunction.verifyEquals(driver, enabled, "ture", "----验证保存按钮是否可用----");
        LogFunction.logInfo("-----------------用户信息创建填写通过---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userCreateSave(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Test(dataProvider = "xmldata")
    public void userCreateCancel(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Test(dataProvider = "xmldata")
    public void userSelect(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        点击，筛选
        WebElement select = l.getElement(param.get("select"));
        String text = select.getText();
        select.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver,text,"筛选","----验证点击的是否是：筛选----");

//        输入用户名
        WebElement selectusername = l.getElement(param.get("selectUserName"));
        selectusername.sendKeys(param.get("userNameValue"));
        LogFunction.logInfo("输入用户名：" + param.get("userNameValue"));
//        输入姓名
        WebElement selectName = l.getElement(param.get("selectName"));
        selectName.sendKeys(param.get("nameValue"));
        LogFunction.logInfo("输入用户名：" + param.get("nameValue"));
//        输入邮箱
        WebElement selectemail = l.getElement(param.get("selectEmail"));
        selectemail.sendKeys(param.get("emailValue"));
        LogFunction.logInfo("输入用户名：" + param.get("emailValue"));
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
        AssertFunction.verifyEquals(driver,text2,"确定","----是否点击的是：确定按钮----");
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
        WebElement selectChooseUser = l.getElement(param.get("commonSelectChoose"));
        selectChooseUser.click();
        LogFunction.logInfo("勾选筛选结果用户");
        LogFunction.logInfo("-----------------用户信息筛选查询完成---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void clickSelectButton(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，筛选按钮
        WebElement select = l.getElement(param.get("commonSelect"));
        String text = select.getText();
        select.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "筛选", "----验证是否点击筛选按钮----");
    }

    @Test(dataProvider = "xmldata")
    public void chooseUserButton(Map<String, String> param) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //        勾选用户
        WebElement commonSelectChoose = l.getElement(param.get("commonSelectChoose"));
        commonSelectChoose.click();
        LogFunction.logInfo("勾选筛选结果用户");
        LogFunction.logInfo("-------------------勾选用户成功-------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userEdit(Map<String, String> param) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击编辑
        WebElement edit = l.getElement(param.get("edit"));
        String text = edit.getText();
        edit.click();
        LogFunction.logInfo("点击：" + text);
//        验证，用户名是否可编辑
        WebElement editUserName = l.getElement(param.get("editUserName"));
        boolean enabled = editUserName.isEnabled();
        AssertFunction.verifyEquals(driver, enabled, false, "----验证，用户名是否可编辑----");
//        编辑，姓名
        WebElement editnName = l.getElement(param.get("editnName"));
        editnName.clear();
        editnName.sendKeys(param.get("editNameValue"));
        LogFunction.logInfo("姓名修改为：" + param.get("editNameValue"));
//        编辑，岗位职责
        WebElement editResponsibility = l.getElement(param.get("editResponsibility"));
        editResponsibility.clear();
        editResponsibility.sendKeys(param.get("editResponsibilityValue"));
        LogFunction.logInfo("岗位职责修改为：" + param.get("editResponsibilityValue"));
//        编辑，办公电话
        WebElement editPhone = l.getElement(param.get("editPhone"));
        editPhone.clear();
        editPhone.sendKeys(param.get("editPhoneValue"));
        LogFunction.logInfo("办公电话修改为：" + param.get("editPhoneValue"));
//        编辑，移动电话
        WebElement editMobilePhone = l.getElement(param.get("editMobilePhone"));
        editMobilePhone.clear();
        editMobilePhone.sendKeys(param.get("editMobilePhoneValue"));
        LogFunction.logInfo("移动电话修改为：" + param.get("editMobilePhoneValue"));
//        编辑，email
        WebElement editEmail = l.getElement(param.get("editEmail"));
        editEmail.clear();
        editEmail.sendKeys(param.get("editEmailValue"));
        LogFunction.logInfo("email修改为：" + param.get("editEmailValue"));
//        编辑，备注
        WebElement editNote = l.getElement(param.get("editNote"));
        editNote.clear();
        editNote.sendKeys(param.get("editNoteValue"));
        LogFunction.logInfo("备注修改为：" + param.get("editNoteValue"));
//        点击，有效标志
        WebElement editFlag = l.getElement(param.get("editFlag"));
        editFlag.click();
        LogFunction.logInfo("点击:有效标志");
//        选择，无效
        WebElement editInvalid = l.getElement(param.get("editInvalid"));
        String text1 = editInvalid.getText();
        editInvalid.click();
        LogFunction.logInfo("选择：" + text1);
        LogFunction.logInfo("-----------------用户信息编辑填写完成---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void commonSave(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        AssertFunction.verifyEquals(driver,text2,"确定","----是否点击的是：确定按钮----");
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
        AssertFunction.verifyEquals(driver, text10, "无效", "----验证备注是否正确----");
//        勾选用户
        WebElement selectChooseUser = l.getElement(param.get("selectChooseUser"));
        selectChooseUser.click();
        LogFunction.logInfo("勾选筛选结果用户");
        LogFunction.logInfo("-----------------用户信息编辑后筛选查询验证完成---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userEditCancel(Map<String, String> param) {
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，更改状态
        WebElement commonChangeState = l.getElement(param.get("commonChangeState"));
        String text = commonChangeState.getText();
        commonChangeState.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "更改状态", "----验证是否点击：更改状态按钮----");
//        验证，提示信息：是否改变用户状态？
        WebElement commonHint = l.getElement(param.get("commonHint"));
        String text3 = commonHint.getText();
        AssertFunction.verifyEquals(driver, text3, "是否改变用户状态？", "----验证提示信息：是否改变用户状态？，是否正确----");
//        点击，确认
        WebElement commonAffirm = l.getElement(param.get("commonAffirm"));
        String text1 = commonAffirm.getText();
        commonAffirm.click();
        LogFunction.logInfo("点击：" + text1);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        验证，提示信息：设置成功!
        WebElement commonHint1 = l.getElement(param.get("commonHint"));
        String text31 = commonHint1.getText();
        AssertFunction.verifyEquals(driver, text31, "设置成功！", "----验证提示信息：设置成功！，是否正确----");
//        点击，确认
        WebElement commonAffirm1 = l.getElement(param.get("commonAffirm"));
        String text11 = commonAffirm1.getText();
        commonAffirm1.click();
        LogFunction.logInfo("点击：" + text11);
        LogFunction.logInfo("----------------------状态更改成功------------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userDelete(Map<String, String> param) {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        点击，删除
        WebElement delete = l.getElement(param.get("delete"));
        String text = delete.getText();
        delete.click();
        LogFunction.logInfo("点击:" + text);
        AssertFunction.verifyEquals(driver, text, "删除", "----验证是否点击，删除----");
//         验证，删除提示信息:是否删除当前用户？
        WebElement deleteHint = l.getElement(param.get("deleteHint"));
        String text1 = deleteHint.getText();
        LogFunction.logInfo("删除提示信息：" + text1);
        AssertFunction.verifyEquals(driver, text1, "是否删除当前用户？", "----验证，删除提示信息:是否删除当前用户？----");
//        点击，是否删除提示确认
        WebElement deleteAffirm = l.getElement(param.get("deleteAffirm"));
        String text2 = deleteAffirm.getText();
        deleteAffirm.click();
        LogFunction.logInfo("点击:" + text2);
        AssertFunction.verifyEquals(driver, text2, "确认", "----验证是否点击，确认----");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//         验证，删除成功提示：删除用户成功！
        WebElement deleteHint1 = l.getElement(param.get("deleteHint"));
        String text3 = deleteHint1.getText();
        LogFunction.logInfo("删除提示信息：" + text3);
        AssertFunction.verifyEquals(driver, text3, "删除用户成功！", "----验证，删除提示信息:删除用户成功！----");
//          点击，删除成功提示确认
        WebElement deleteAffirm1 = l.getElement(param.get("deleteAffirm"));
        String text4 = deleteAffirm1.getText();
        LogFunction.logInfo("点击:" + text4);
        AssertFunction.verifyEquals(driver, text4, "确认", "----验证是否点击，确认----");
        LogFunction.logInfo("------------------用户信息删除成功---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userResetPassword(Map<String, String> param) {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        点击，重置密码
        WebElement resetPassword = l.getElement(param.get("resetPassword"));
        String text = resetPassword.getText();
        resetPassword.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "重置密码", "----验证是否点击重置密码----");
//        验证，重置密码，提示：是否重置当前用户密码？
        WebElement resetPasswordHint = l.getElement(param.get("resetPasswordHint"));
        String text1 = resetPasswordHint.getText();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text1, "是否重置当前用户密码？", "----验证重置密码提示：是否重置当前用户密码？----");
//        点击，重置密码,确认
        WebElement resetPasswordAffirm = l.getElement(param.get("resetPasswordAffirm"));
        String text2 = resetPasswordAffirm.getText();
        resetPasswordAffirm.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text2, "确认", "----验证是否点击确认----");
        LogFunction.logInfo("------------------用户信息重置密码成功---------------------");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        验证，重置密码成功，提示：重置密码成功,已经向用户发送邮件！
        WebElement resetPasswordHint1 = l.getElement(param.get("resetPasswordHint"));
        String text3 = resetPasswordHint1.getText();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text1, "重置密码成功,已经向用户发送邮件！", "----重置密码成功,已经向用户发送邮件！----");
//        点击，重置密码成功,确认
        WebElement resetPasswordAffirm1 = l.getElement(param.get("resetPasswordAffirm"));
        String text5 = resetPasswordAffirm1.getText();
        resetPasswordAffirm1.click();
        LogFunction.logInfo("点击：" + text5);
        AssertFunction.verifyEquals(driver, text2, "确认", "----验证是否点击确认----");
        LogFunction.logInfo("------------------用户信息重置密码成功---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userAssignmentGroup(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        点击，分配工作组
        WebElement assignmentGroup = l.getElement(param.get("assignmentGroup"));
        String text = assignmentGroup.getText();
        assignmentGroup.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "分配工作组", "----是否点击：分配工作组----");
//        选择，用户组
        WebElement selectGroup = l.getElement(param.get("selectGroup"));
        selectGroup.click();
        String text1 = selectGroup.getText();
        LogFunction.logInfo("选择：" + text1);
        AssertFunction.verifyEquals(driver, text1, param.get("createWorkingGroupNameValue"), "----是否选择" + param.get("createWorkingGroupNameValue") + "工作组----");
//        点击，右移按钮
        WebElement selectGroupRightMove = l.getElement(param.get("selectGroupRightMove"));
        selectGroupRightMove.click();
        LogFunction.logInfo("点击：右移按钮");
//        点击，保存
        WebElement assignmentGroupSave = l.getElement(param.get("assignmentGroupSave"));
        String text2 = assignmentGroupSave.getText();
        assignmentGroupSave.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "保存", "----是否点击保存----");
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

    @Test(dataProvider = "xmldata")
    public void workingGroupManagementGreate(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，新建
        WebElement commonCreate = l.getElement(param.get("commonCreate"));
        String text = commonCreate.getText();
        commonCreate.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "新建", "----验证是否点击新建按钮----");
//        录入，工作组名称
        WebElement workingGroupName = l.getElement(param.get("createWorkingGroupName"));
        workingGroupName.sendKeys(param.get("createWorkingGroupNameValue"));
        LogFunction.logInfo("录入工作组名称:" + param.get("createWorkingGroupNameValue"));
//        点击，有效标识输入框
        WebElement flag = l.getElement(param.get("commonCraeteFlag"));
        flag.click();
        LogFunction.logInfo("点击有效标志输入框");
//        选择有效标识：有效
        WebElement valid = l.getElement(param.get("commonValid"));
        String text1 = valid.getText();
        valid.click();
        LogFunction.logInfo("选择：" + text1);
        AssertFunction.verifyEquals(driver, text1, "有效", "----验证是否选择有效----");
        LogFunction.logInfo("------------------工作组新建填写完成---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void workingGroupManagementSelect(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        点击，筛选
        WebElement select = l.getElement(param.get("commonSelect"));
        String text = select.getText();
        select.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver,text,"筛选","----是否点击的是：筛选按钮----");
//        录入，工作组名称
        WebElement selectusername = l.getElement(param.get("inputSelectWorkingGroupname"));
        selectusername.sendKeys(param.get("createWorkingGroupNameValue"));
        LogFunction.logInfo("录入工作组名称：" + param.get("createWorkingGroupNameValue"));

//        点击，有效标志
        WebElement selectFlag = l.getElement(param.get("commonSelectFlag"));
        selectFlag.click();
        LogFunction.logInfo("点击：有效标志");
//        选择有效标识：有效
        WebElement selectValid = l.getElement(param.get("commonSelectValid"));
        String text1 = selectValid.getText();
        selectValid.click();
        LogFunction.logInfo("选择：" + text1);
//        点击，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        验证工作组名称
        WebElement selectUsernameValue = l.getElement(param.get("selectworkinggroupnameValue"));
        String text3 = selectUsernameValue.getText();
        AssertFunction.verifyEquals(driver, text3, param.get("createWorkingGroupNameValue"), "----验证工作组名称是否正确----");
//        验证有效标志
        WebElement selectValidValue = l.getElement(param.get("selectWorkingGroupFlagValue"));
        String text10 = selectValidValue.getText();
        AssertFunction.verifyEquals(driver, text10, "有效", "----验证有效标志是否正确----");
//        勾选用户
        WebElement selectChooseUser = l.getElement(param.get("commonSelectChoose"));
        selectChooseUser.click();
        LogFunction.logInfo("勾选筛选结果");
        LogFunction.logInfo("-------------------筛选查询完成---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userAllocationPrivilege(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，分配权限
        WebElement commonAllocationPrivilege = l.getElement(param.get("commonAllocationPrivilege"));
        String text = commonAllocationPrivilege.getText();
        commonAllocationPrivilege.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "分配权限", "----验证是否点击分配权限----");
//        选择权限：BJ-UPS1
        WebElement element = l.getElement(param.get("choosePrivilegeBJ-UPS1"));
        element.click();
        String text1 = element.getText();
        LogFunction.logInfo("选择：BJ-UPS1");
        AssertFunction.verifyEquals(driver, text1, "BJ-UPS1", "----验证是否选择：BJ-UPS1----");
//        选择权限：BJ-UPS2
        WebElement element1 = l.getElement(param.get("choosePrivilegeBJ-UPS2"));
        element1.click();
        String text2 = element1.getText();
        LogFunction.logInfo("选择：BJ-UPS2");
        AssertFunction.verifyEquals(driver, text2, "BJ-UPS2", "----验证是否选择：BJ-UPS1----");
//        选择权限：BJ-UPS3
        WebElement element2 = l.getElement(param.get("choosePrivilegeBJ-UPS3"));
        element2.click();
        String text3 = element2.getText();
        LogFunction.logInfo("选择：BJ-UPS3");
        AssertFunction.verifyEquals(driver, text3, "BJ-UPS3", "----验证是否选择：BJ-UPS1----");
//        选择权限：BJ-UPS4
        WebElement element3 = l.getElement(param.get("choosePrivilegeBJ-UPS4"));
        element3.click();
        String text4 = element3.getText();
        LogFunction.logInfo("选择：BJ-UPS4");
        AssertFunction.verifyEquals(driver, text4, "BJ-UPS4", "----验证是否选择：BJ-UPS1----");
//        点击，右移按钮
        WebElement choosePrivilegeMoveRight = l.getElement(param.get("choosePrivilegeMoveRight"));
        choosePrivilegeMoveRight.click();
        LogFunction.logInfo("点击：右移按钮");
    }

    @Test(dataProvider = "xmldata")
    public void userAllocationPrivilegeSave(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //        点击，保存
        WebElement editSave = l.getElement(param.get("allocationSave"));
        String text2 = editSave.getText();
        editSave.click();
        LogFunction.logInfo("点击：" + text2);
//        验证，提示信息：保存成功
        WebElement editHint = l.getElement(param.get("allocationSaveHint"));
        String text3 = editHint.getText();
        AssertFunction.verifyEquals(driver, text3, "保存成功", "----验证提示信息：保存成功，是否正确----");
//        点击，确认
        WebElement editAffirm = l.getElement(param.get("allocationSaveAffirm"));
        String text4 = editAffirm.getText();
        editAffirm.click();
        LogFunction.logInfo("点击：" + text4);
        LogFunction.logInfo("---------------------保存成功------------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userLogoutSystem(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，用户头像
        WebElement userlogouthead = l.getElement(param.get("userLogoutHead"));
        userlogouthead.click();
        String text = userlogouthead.getText();
        LogFunction.logInfo("点击用户：" + text);
//        点击，退出系统
        WebElement userLogoutSystem = l.getElement(param.get("userLogoutSystem"));
        String text1 = userLogoutSystem.getText();
        userlogouthead.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.assertEquals(driver, text1, "退出系统");
        LogFunction.logInfo("---------------------退出系统成功------------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userViewConfig(Map<String, String> param) {
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
        LogFunction.logInfo("点击："+text4);
        AssertFunction.verifyEquals(driver,text4,"保存","验证是否点击：保存");
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
        AssertFunction.verifyEquals(driver,text5,"8586-BJ-UPS1","----断言视图：8586-BJ-UPS1是否添加成功");
        LogFunction.logInfo("---------------------新用户视图添加成功------------------------");

    }



//    -------------------------------------集中告警-------------------------------------

    @Test(dataProvider = "xmldata")
    public void concentratedAlarm(Map<String, String> param) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，集中告警
        WebElement alarmClassifyRules = l.getElement(param.get("concentratedAlarm"));
        String text = alarmClassifyRules.getText();
        alarmClassifyRules.click();
        LogFunction.logInfo("点击："+text);
        AssertFunction.assertEquals(driver,text,"集中告警");
    }

    @Test(dataProvider = "xmldata")
    public void alarmConfig(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，告警配置
        WebElement alarmClassifyRules = l.getElement(param.get("alarmConfig"));
        String text = alarmClassifyRules.getText();
        alarmClassifyRules.click();
        LogFunction.logInfo("点击："+text);
        AssertFunction.assertEquals(driver,text,"告警配置");
    }

    @Test(dataProvider = "xmldata")
    public void denoiseStrategy(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，降噪策略
        WebElement alarmClassifyRules = l.getElement(param.get("denoiseStrategy"));
        String text = alarmClassifyRules.getText();
        alarmClassifyRules.click();
        LogFunction.logInfo("点击："+text);
        AssertFunction.assertEquals(driver,text,"降噪策略");
    }

    @Test(dataProvider = "xmldata")
    public void alarmClassifyRulesCreate(Map<String, String> param) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，全部规则
        WebElement allRules = l.getElement(param.get("allRules"));
        String text2 = allRules.getText();
        allRules.click();
        LogFunction.logInfo("点击："+text2);
        AssertFunction.verifyEquals(driver,text2,"全部规则","----验证是否点击：全部规则按钮----");
//        点击，告警分类规则
        WebElement alarmClassifyRules = l.getElement(param.get("alarmClassifyRules"));
        String text = alarmClassifyRules.getText();
        alarmClassifyRules.click();
        LogFunction.logInfo("选择："+text);
        AssertFunction.verifyEquals(driver,text,"告警分类规则","告警分类规则");
//          点击，新建
        WebElement commonCreate = l.getElement(param.get("commonCreate"));
        String text1 = commonCreate.getText();
        commonCreate.click();
        LogFunction.logInfo("点击："+text1);
        AssertFunction.verifyEquals(driver,text1,"新建","----是否点击新建----");
//          录入，规则名称
        WebElement rulesName = l.getElement(param.get("rulesName"));
        rulesName.sendKeys(param.get("alarmClassifyRulesNameValue"));
        LogFunction.logInfo("规则名称，录入："+param.get("alarmClassifyRulesNameValue"));
//        点击，基础设置，下一步
        WebElement basicsNextStep = l.getElement(param.get("basicsNextStep"));
        String text3 = basicsNextStep.getText();
        basicsNextStep.click();
        LogFunction.logInfo("点击："+text3);
        AssertFunction.verifyEquals(driver,text3,"下一步","----验证是否点击：下一步----");
//         点击，域
        WebElement domain = l.getElement(param.get("domain"));
        domain.click();
        LogFunction.logInfo("点击：域");
//        选择，域：rootDomain
        WebElement chooseDomain = l.getElement(param.get("chooseDomain"));
        String text4 = chooseDomain.getText();
        chooseDomain.click();
        LogFunction.logInfo("选择域："+text4);
        AssertFunction.verifyEquals(driver,text4,"rootDomain","----验证选择的域是否为：rootDomain----");
//          点击，类型
        WebElement type = l.getElement(param.get("type"));
        type.click();
        LogFunction.logInfo("点击：类型");
//        选择,类型:Oracle
        WebElement chooseType = l.getElement(param.get("chooseType"));
        String text5 = chooseType.getText();
        chooseType.click();
        LogFunction.logInfo("选择类型："+text5);
        AssertFunction.verifyEquals(driver,text5,"Oracle","----验证选择的类型是否为；Oracle----");
//        点击，告警类型选择
        WebElement alarmType = l.getElement(param.get("alarmType"));
        alarmType.click();
        LogFunction.logInfo("点击：告警类型选择");
//        选择,告警类型选择:Oracle_System_Alert
        WebElement chooseAlarmType = l.getElement(param.get("chooseAlarmType"));
        String text6 = chooseAlarmType.getText();
        chooseAlarmType.click();
        LogFunction.logInfo("选择告警类型："+text6);
        AssertFunction.verifyEquals(driver,text6,"Oracle_System_Alert","----验证选择的告警类型是否为；Oracle_System_Alert----");
//       点击，规则条件设置，下一步
        WebElement rulesConditionConfigNextStep = l.getElement(param.get("rulesConditionConfigNextStep"));
        String text7 = rulesConditionConfigNextStep.getText();
        rulesConditionConfigNextStep.click();
        LogFunction.logInfo("点击："+text7);
        AssertFunction.verifyEquals(driver,text7,"下一步","----验证是否点击：下一步----");
//        点击，节点过滤
        WebElement nodeFilter = l.getElement(param.get("nodeFilter"));
        nodeFilter.click();
        LogFunction.logInfo("点击：节点过滤");
//        选择,节点过滤:StandardTest-Oracle2
        WebElement chooseNodeFilter = l.getElement(param.get("chooseNodeFilter"));
        String text8 = chooseNodeFilter.getText();
        chooseNodeFilter.click();
        LogFunction.logInfo("选择节点过滤："+text8);
        AssertFunction.verifyEquals(driver,text8,"StandardTest-Oracle2","----验证选择的节点过滤是否为；StandardTest-Oracle2----");
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
        LogFunction.logInfo("选择采集系统选择："+text9);
        AssertFunction.verifyEquals(driver,text9,"IBM ITCAM","----验证选择的采集系统选择是否为；IBM ITCAM----");
//        点击，空白处
        WebElement blank1 = l.getElement(param.get("blank"));
        blank1.click();
        LogFunction.logInfo("点击：空白处");
//        录入，内容关键字
        WebElement contentKeyword = l.getElement(param.get("contentKeyword"));
        contentKeyword.sendKeys(param.get("contentKeywordValue"));
        LogFunction.logInfo("规则名称，录入："+param.get("contentKeywordValue"));
//       点击，规则条件高级设置，下一步
        WebElement rulesConditionAdvancedConfigNextStep = l.getElement(param.get("rulesConditionAdvancedConfigNextStep"));
        String text71 = rulesConditionAdvancedConfigNextStep.getText();
        rulesConditionAdvancedConfigNextStep.click();
        LogFunction.logInfo("点击："+text71);
        AssertFunction.verifyEquals(driver,text71,"下一步","----验证是否点击：下一步----");
//        点击，新的警告类型
        WebElement newAlarmType = l.getElement(param.get("newAlarmType"));
        newAlarmType.click();
        LogFunction.logInfo("点击：新的警告类型");
//        选择,新的警告类型:Oracle_PGA_Alert
        WebElement chooseNewAlarmType = l.getElement(param.get("chooseNewAlarmType"));
        String text91 = chooseNewAlarmType.getText();
        chooseNewAlarmType.click();
        LogFunction.logInfo("选择新的警告类型："+text91);
        AssertFunction.verifyEquals(driver,text91,"Oracle_PGA_Alert","----验证选择的新的警告类型是否为；Oracle_PGA_Alert----");
//        点击，新的告警级别
        WebElement newAlarmRank = l.getElement(param.get("newAlarmRank"));
        newAlarmRank.click();
        LogFunction.logInfo("点击：新的告警级别");
//        点击,新的警告类型，Deselect All
        WebElement deselectAll = l.getElement(param.get("deselectAll"));
        String text11 = deselectAll.getText();
        deselectAll.click();
        LogFunction.logInfo("点击："+text11);
        AssertFunction.verifyEquals(driver,text11,"Deselect All","----验证点击的是否为；取消全选----");
//        选择,新的警告类型:CRITICAL
        WebElement chooseNewAlarmRank = l.getElement(param.get("chooseNewAlarmRank"));
        String text12 = chooseNewAlarmRank.getText();
        chooseNewAlarmRank.click();
        LogFunction.logInfo("选择新的告警级别："+text12);
        AssertFunction.verifyEquals(driver,text12,"CRITICAL","----验证选择的新的告警级别是否为；CRITICAL----");
//        点击，空白处
        WebElement blank2 = l.getElement(param.get("blank"));
        blank2.click();
        LogFunction.logInfo("点击：空白处");
//          点击，保存
        WebElement alarmClassifyConfigSave = l.getElement(param.get("alarmClassifyConfigSave"));
        String text10 = alarmClassifyConfigSave.getText();
        alarmClassifyConfigSave.click();
        LogFunction.logInfo("点击："+text10);
        AssertFunction.assertEquals(driver,text10,"保存");


    }

    @Test(dataProvider = "xmldata")
    public void denoiseStrategySelect(Map<String, String> param){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，筛选
        WebElement commonSelect = l.getElement(param.get("commonSelect"));
        String text = commonSelect.getText();
        commonSelect.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver,text,"筛选","----验证点击的是否是：筛选----");
//        录入，规则名称
        WebElement selectRulesName = l.getElement(param.get("selectRulesName"));
        selectRulesName.sendKeys(param.get("alarmClassifyRulesNameValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmClassifyRulesNameValue"));
//         点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver,text2,"确定","----是否点击的是：确定按钮----");
//        勾选规则
        WebElement selectChooseUser = l.getElement(param.get("commonSelectChoose"));
        selectChooseUser.click();
        LogFunction.logInfo("勾选筛选结果"+ param.get("alarmClassifyRulesNameValue"));
        LogFunction.logInfo("-----------------降噪策略筛选查询完成---------------------");
    }



    @BeforeClass
    public void aa() {
        SeleniumDriver seleniumDriver = new SeleniumDriver();
        driver = seleniumDriver.getDriver();
        l = new LocatorFunction(driver);
        ssf = new ScreenshotFunction(driver);
    }

    @AfterClass
    public void bb() throws InterruptedException {
        Thread.sleep(3000);
        driver.close();
        driver.quit();
//        SendMailReportFunction.sendMailReport(BaseConfig.emailSender, BaseConfig.emailAuthorizationCode, BaseConfig.emailRecipient);
    }
}