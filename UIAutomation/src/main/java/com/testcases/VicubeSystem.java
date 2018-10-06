package com.testcases;

import com.base.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;
import utill.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public class VicubeSystem extends TestBase {
    public WebDriver driver;
    private LocatorFunction l;
    private ScreenshotFunction ssf;
    private Map<String, String> param;

//    -------------------------------------登录，系统设置-------------------------------------

    //    用户登陆
    @Test(dataProvider = "xmldata")
    public void userLogin(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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
        AssertFunction.verifyEquals(driver, text, "登录", "----是否点击登陆----");
//      验证用户登陆标识
        WebElement userLoginID = l.getElement(param.get("userLoginID"));
        String text1 = userLoginID.getText();
        LogFunction.logInfo("用户登陆标识为：" + text1);
        AssertFunction.verifyEquals(driver, text1, param.get("editNameValue"), "----是否点击登陆----");
        LogFunction.logInfo("------------------" + text1 + "登陆成功-------------------");
    }

    //    用户无效登陆
    @Test(dataProvider = "xmldata", enabled = true)
    public void userInvalidLogin(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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
        AssertFunction.verifyEquals(driver, text3, "您的用户名或密码错误", "----验证提示信息：用户名或密码错误！，是否正确----");
//        点击，确认
        WebElement editAffirm = l.getElement(param.get("commonAffirm"));
        String text4 = editAffirm.getText();
        editAffirm.click();
        LogFunction.logInfo("点击：" + text4);
        LogFunction.logInfo("----------------------用户无效登陆结束------------------------");

    }

    //    管理员登陆
    @Test(dataProvider = "xmldata")
    public void vicubeLogin(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        driver.get(param.get("VicubeUrl"));
        LogFunction.logInfo("网址成功打开：" + param.get("VicubeUrl"));
        //      输入用户名
        WebElement user = l.getElement(param.get("user"));
        user.clear();
        user.sendKeys(param.get("userValue"));
        String userValue = param.get("userValue");
        LogFunction.logInfo("用户名：" + param.get("userValue"));

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
//        //      输入密码
//        WebElement password = l.getElement(param.get("password"));
//        password.clear();
//        password.sendKeys(param.get("passwordValue"));
//        LogFunction.logInfo("密码：" + param.get("passwordValue"));
        //      点击登陆按钮
        WebElement button = l.getElement(param.get("button"));
        String text = button.getText();
        button.click();
        LogFunction.logInfo("点击按钮:" + text);
        AssertFunction.verifyEquals(driver, text, "登录", "----是否点击登陆----");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //      验证用户登陆标识
        WebElement userLoginID = l.getElement(param.get("userLoginID"));
        String text1 = userLoginID.getText();
        LogFunction.logInfo("用户登陆标识为：" + text1);
        AssertFunction.verifyEquals(driver, text1, "超级管理员", "----是否点击登陆----");
        LogFunction.logInfo("------------------" + text1 + "登陆成功-------------------");

    }

    //    菜单，系统设置
    @Test(dataProvider = "xmldata")
    public void systemSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
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

    //    菜单-系统设置，用户管理
    @Test(dataProvider = "xmldata")
    public void userManagement(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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

    //    菜单-系统设置，工作组管理
    @Test(dataProvider = "xmldata")
    public void workingGroupManagement(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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

    //    系统设置-用户管理，新建
    @Test(dataProvider = "xmldata")
    public void userManagementCreate(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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
        AssertFunction.verifyEquals(driver, enabled, false, "----验证保存按钮是否可用----");
//          填写用户名
        WebElement userName = l.getElement(param.get("userName"));
        userName.sendKeys(param.get("userNameValue"));
        LogFunction.logInfo("在用户名中填入：" + param.get("userNameValue"));
//         验证，保存按钮是否可用
        enabled = save.isEnabled();
        LogFunction.logInfo(save.getText() + "：按钮的状态");
        AssertFunction.verifyEquals(driver, enabled, false, "----验证保存按钮是否可用----");
//        填写姓名
        WebElement name = l.getElement(param.get("name"));
        name.sendKeys(param.get("nameValue"));
        LogFunction.logInfo("在姓名中填入：" + param.get("nameValue"));
//         验证，保存按钮是否可用
        enabled = save.isEnabled();
        LogFunction.logInfo(save.getText() + "：按钮的状态");
        AssertFunction.verifyEquals(driver, enabled, false, "----验证保存按钮是否可用----");
//        填写邮箱
        WebElement email = l.getElement(param.get("email"));
        email.sendKeys(param.get("emailValue"));
        LogFunction.logInfo("在邮箱中填入：" + param.get("emailValue"));
//         验证，保存按钮是否可用
        enabled = save.isEnabled();
        LogFunction.logInfo(save.getText() + "：按钮的状态");
        AssertFunction.verifyEquals(driver, enabled, false, "----验证保存按钮是否可用----");
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
        LogFunction.logInfo("-----------------用户信息创建填写通过---------------------");
//         验证，保存按钮是否可用
        enabled = save.isEnabled();
        LogFunction.logInfo(save.getText() + "：按钮的状态");
        AssertFunction.verifyEquals(driver, enabled, true, "----验证保存按钮是否可用----");
    }

    //    系统设置-用户管理，新建保存
    @Test(dataProvider = "xmldata")
    public void userCreateSave(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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

    //    系统设置-用户管理，新建取消
    @Test(dataProvider = "xmldata")
    public void userCreateCancel(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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

    //    系统设置-用户管理，用户筛选
    @Test(dataProvider = "xmldata")
    public void userSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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
        AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");

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
        AssertFunction.verifyEquals(driver, text2, "确定", "----是否点击的是：确定按钮----");
        try {
            Thread.sleep(1000);
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
        WebElement selectChooseUser = l.getElement(param.get("commonSystemSetupSelectChoose"));
        selectChooseUser.click();
        LogFunction.logInfo("勾选筛选结果用户");
        LogFunction.logInfo("-----------------用户信息筛选查询完成---------------------");
    }

    //    系统设置-用户管理，筛选按钮
    @Test(dataProvider = "xmldata")
    public void clickSelectButton(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //        勾选用户
        WebElement commonSystemSetupSelectChoose = l.getElement(param.get("commonSystemSetupSelectChoose"));
        commonSystemSetupSelectChoose.click();
        LogFunction.logInfo("勾选筛选结果用户");
        LogFunction.logInfo("-------------------勾选用户成功-------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userEdit(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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
    public void editSave(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        勾选用户
//        WebElement selectChooseUser = l.getElement(param.get("commonSystemSetupSelectChoose"));
//        selectChooseUser.click();
//        LogFunction.logInfo("勾选筛选结果用户");
        LogFunction.logInfo("----------------------状态更改成功------------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userGroupChangeState(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        勾选用户
//        WebElement selectChooseUser = l.getElement(param.get("commonSystemSetupSelectChoose"));
//        selectChooseUser.click();
//        LogFunction.logInfo("勾选筛选结果用户");
        LogFunction.logInfo("----------------------状态更改成功------------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userDelete(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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
        deleteAffirm1.click();
        LogFunction.logInfo("点击:" + text4);
        AssertFunction.verifyEquals(driver, text4, "确认", "----验证是否点击，确认----");
        LogFunction.logInfo("------------------用户信息删除成功---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userGroupDelete(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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
        AssertFunction.verifyEquals(driver, text1, "是否删除当前组？", "----验证，删除提示信息:是否删除当前用户？----");
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
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "是否重置当前用户密码？", "----验证重置密码提示：是否重置当前用户密码？----");
//        点击，重置密码,确认
        WebElement resetPasswordAffirm = l.getElement(param.get("resetPasswordAffirm"));
        String text2 = resetPasswordAffirm.getText();
        resetPasswordAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确认", "----验证是否点击确认----");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        验证，重置密码成功，提示：重置密码成功,已经向用户发送邮件！
        WebElement resetPasswordHint1 = l.getElement(param.get("resetPasswordHint"));
        String text3 = resetPasswordHint1.getText();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "重置密码成功,已经向用户发送邮件！", "----重置密码成功,已经向用户发送邮件！----");
//        点击，重置密码成功,确认
        WebElement resetPasswordAffirm1 = l.getElement(param.get("resetPasswordAffirm"));
        String text5 = resetPasswordAffirm1.getText();
        resetPasswordAffirm1.click();
        LogFunction.logInfo("点击：" + text5);
        AssertFunction.verifyEquals(driver, text5, "确认", "----验证是否点击确认----");
        LogFunction.logInfo("------------------用户信息重置密码成功---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void userAssignmentGroup(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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
        WebElement selectGroup = l.getElement(param.get("chooseWorkingGroupSelect"));
        Select select=new Select(selectGroup);
        List<WebElement> allSelectedOptions = select.getAllSelectedOptions();
        boolean  contains= allSelectedOptions.contains(param.get("chooseWorkingGroupSelect"));
        if (contains==true) {
            select.selectByVisibleText(param.get("chooseWorkingGroupSelect"));
        }else {
            select.selectByVisibleText("超级管理员组");
        }
        LogFunction.logInfo("选择：" + param.get("editWorkingGroupNameValue"));
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
    public void workingGroupAssignmentManagement(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        点击，分配管理员
        WebElement assignmentGroup = l.getElement(param.get("assignmentManagement"));
        String text = assignmentGroup.getText();
        assignmentGroup.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "分配管理员", "----是否点击：分配管理员----");
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
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        点击，分配用户
        WebElement assignmentGroup = l.getElement(param.get("assignmentUser"));
        String text = assignmentGroup.getText();
        assignmentGroup.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "分配用户", "----是否点击：分配用户----");
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

    @Test(dataProvider = "xmldata")
    public void workingGroupManagementGreate(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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
//        选择有效标识：无效
        WebElement valid = l.getElement(param.get("commonInvalid"));
        String text1 = valid.getText();
        valid.click();
        LogFunction.logInfo("选择：" + text1);
        AssertFunction.verifyEquals(driver, text1, "无效", "----验证是否选择有效----");
//        点击域选择框
        WebElement domainNameSelectBox = l.getElement(param.get("createDomainNameSelectBox"));
        domainNameSelectBox.click();
        LogFunction.logInfo("点击域名选择框");
//        选择域：rootDomain
        WebElement domainName = l.getElement(param.get("createDomainName"));
        String text2 = domainName.getText();
        domainName.click();
        LogFunction.logInfo("选择：" + text2);
        AssertFunction.verifyEquals(driver, text2, "rootDomain", "----验证是否选择域：rootDomain----");
        LogFunction.logInfo("------------------工作组保存填写完成---------------------");
//    录入，工作组备注
        WebElement Note = l.getElement(param.get("creatWorkingGroupeNote"));
        Note.sendKeys(param.get("creatWorkingGroupeNoteValue"));
        LogFunction.logInfo("录入工作组备注:" + param.get("creatWorkingGroupeNoteValue"));
    }

    @Test(dataProvider = "xmldata")
    public void workingGroupManagementEdit(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击编辑
        WebElement edit = l.getElement(param.get("editWorkingGroup"));
        String text = edit.getText();
        edit.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "编辑", "----验证是否点击编辑按钮----");
//        录入，工作组名称
        WebElement createWorkingGroupName = l.getElement(param.get("editWorkingGroupName"));
        createWorkingGroupName.clear();
        createWorkingGroupName.sendKeys(param.get("editWorkingGroupNameValue"));
        LogFunction.logInfo("录入工作组名称:" + param.get("editWorkingGroupNameValue"));
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
//        点击域选择框
        WebElement domainNameSelectBox = l.getElement(param.get("domainNameSelectBox"));
        domainNameSelectBox.click();
        LogFunction.logInfo("点击域名选择框");
//        选择域：rootDomain
        WebElement domainName = l.getElement(param.get("editDomainName"));
        String text2 = domainName.getText();
        domainName.click();
        LogFunction.logInfo("选择：" + text2);
        AssertFunction.verifyEquals(driver, text2, "rootDomain", "----验证是否选择域：XMDomain----");
        LogFunction.logInfo("------------------工作组编辑完成---------------------");
//         录入，工作组备注
        WebElement Note = l.getElement(param.get("editWorkingGroupeNote"));
        Note.clear();
        Note.sendKeys(param.get("editWorkingGroupeNoteValue"));
        LogFunction.logInfo("录入工作组备注:" + param.get("editWorkingGroupeNoteValue"));
    }

    @Test(dataProvider = "xmldata")
    public void refresh(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        driver.navigate().refresh();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    @Test(dataProvider = "xmldata")
    public void workingGroupManagementEditSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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
        AssertFunction.verifyEquals(driver, text, "筛选", "----是否点击的是：筛选按钮----");
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
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        WebElement selectValid = l.getElement(param.get("commonSelectInvalid"));
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
        AssertFunction.verifyEquals(driver, text, "筛选", "----是否点击的是：筛选按钮----");
//        录入，工作组名称
        WebElement selectusername = l.getElement(param.get("inputSelectWorkingGroupname"));
        selectusername.sendKeys(param.get("createWorkingGroupNameValue"));
        LogFunction.logInfo("录入工作组名称：" + param.get("createWorkingGroupNameValue"));

//        点击，有效标志
        WebElement selectFlag = l.getElement(param.get("commonSelectFlag"));
        selectFlag.click();
        LogFunction.logInfo("点击：有效标志");
//        选择有效标识：无效
        WebElement selectInvalid = l.getElement(param.get("commonSelectInvalid"));
        String text1 = selectInvalid.getText();
        selectInvalid.click();
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

    @Test(dataProvider = "xmldata")
    public void userAllocationPrivilege(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //        选择权限：
        WebElement element = l.getElement(param.get("choosePrivilege"));
        element.click();
        String text1 = element.getText();
        LogFunction.logInfo("选择：" + text1);
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
        WebElement choosePrivilegeMoveRight = l.getElement(param.get("choosePrivilegeMoveRight"));
        choosePrivilegeMoveRight.click();
        LogFunction.logInfo("点击：右移按钮");
        LogFunction.logInfo("-------------------分配权限完成---------------------");

    }

    @Test(dataProvider = "xmldata")
    public void userAllocationPrivilegeSave(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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
        AssertFunction.verifyEquals(driver, text3, "授权成功！", "----验证提示信息：保存成功，是否正确----");
//        点击，确认
        WebElement editAffirm = l.getElement(param.get("allocationSaveAffirm"));
        String text4 = editAffirm.getText();
        editAffirm.click();
        LogFunction.logInfo("点击：" + text4);
        LogFunction.logInfo("---------------------分配权限保存成功------------------------");
    }

    @Test(dataProvider = "xmldata")
    public void promptMessage(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    @Test(dataProvider = "xmldata")
    public void userLogoutSystem(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

////        点击，头像提示信息
//        if (l.getElement(param.get("userNotification")).isDisplayed()) {
//            WebElement userNotification = l.getElement(param.get("userNotification"));
//            userNotification.click();
//            String text1 = userNotification.getText();
//            LogFunction.logInfo("关闭提示信息：" + text1);
//        }
////        WebElement userNotification = l.getElement(param.get("userNotification"));
////        userNotification.click();
////        String text1 = userNotification.getText();
////        LogFunction.logInfo("关闭提示信息：" + text1);
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        点击，用户头像
        WebElement userlogouthead = l.getElement(param.get("userLogoutHead"));
        userlogouthead.click();
        String text = userlogouthead.getText();
        LogFunction.logInfo("点击用户：" + text);
//        点击，退出系统
        WebElement userLogoutSystem = l.getElement(param.get("userLogoutSystem"));
        String text2 = userLogoutSystem.getText();
        userlogouthead.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.assertEquals(driver, text2, "退出系统");
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
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        WebElement userManagement = l.getElement(param.get("auditManagement"));
        String text1 = userManagement.getText();
        userManagement.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1, "审计管理", "----当前菜单是否为审计管理----");
        LogFunction.logInfo("-----------------打开审计管理菜单---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void auditManagementViewLog(Map<String, String> param) {
//        选择，日志
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        WebElement userManagement = l.getElement(param.get("selectLog"));
        userManagement.click();
        LogFunction.logInfo("成功勾选：日志");
//        点击，查看
        WebElement view = l.getElement(param.get("view"));
        String text = view.getText();
        view.click();
        AssertFunction.verifyEquals(driver, text, "查看", "-----验证是否点击：查看----");
        LogFunction.logInfo("成功点击：" + text);
//        验证，审计详情，是否打开
        WebElement auditDetail = l.getElement(param.get("auditDetail"));
        String text1 = auditDetail.getText();
        AssertFunction.verifyEquals(driver, text1, "审计详情", "-----验证：审计详情是否打开----");
        LogFunction.logInfo("成功打开：" + text1);
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
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        点击，筛选，按钮
        WebElement selectButton = l.getElement(param.get("selectButton"));
        String text3 = selectButton.getText();
        selectButton.click();
        AssertFunction.verifyEquals(driver, text3, "筛选", "-----验证是否点击：筛选----");
        LogFunction.logInfo("成功打开：" + text3);
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
        LogFunction.logInfo("模块名称录入：" + param.get("functionNameValue"));
//       点击，筛选，确定
        WebElement commonSelectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text = commonSelectAffirm.getText();
        commonSelectAffirm.click();
        AssertFunction.verifyEquals(driver, text, "确定", "-----验证是否点击：筛选-确定----");
        LogFunction.logInfo("成功点击：" + text + "按钮");
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        验证，筛选结果，操作账号
        WebElement selectOperationID = l.getElement(param.get("selectOperationID"));
        String text2 = selectOperationID.getText();
        AssertFunction.verifyEquals(driver, text2, param.get("operationIDValue"), "-----验证筛选结果，操作账号是否正确----");
        LogFunction.logInfo("筛选后的,操作账号为：" + text2);
        LogFunction.logInfo("-----------------审计管理查询功能验证完成---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void menuModle(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        验证系统设置是否存在
        WebElement systemManagement = l.getElement(param.get("commonSystemManagement"));
        String text = systemManagement.getText();
        AssertFunction.verifyEquals(driver, text, "系统设置", "-----验证是否点击：系统设置----");
        LogFunction.logInfo("成功打开：" + text);
        LogFunction.logInfo("-----------------菜单系统设置添加，验证通过---------------------");

    }


//    -------------------------------------集中告警-------------------------------------

    @Test(dataProvider = "xmldata")
    public void concentratedAlarm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，集中告警
        WebElement alarmClassifyRules = l.getElement(param.get("concentratedAlarm"));
        String text = alarmClassifyRules.getText();
        alarmClassifyRules.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.assertEquals(driver, text, "集中告警");
        LogFunction.logInfo("-----------------进入，集中告警页面---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void alarmConfig(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，告警配置
        WebElement alarmClassifyRules = l.getElement(param.get("alarmConfig"));
        String text = alarmClassifyRules.getText();
        alarmClassifyRules.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.assertEquals(driver, text, "告警配置");
        LogFunction.logInfo("-----------------进入，告警配置页面---------------------");
    }

    @Test(dataProvider = "xmldata")
    public void denoiseStrategy(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，降噪策略
        WebElement alarmClassifyRules = l.getElement(param.get("denoiseStrategy"));
        String text = alarmClassifyRules.getText();
        alarmClassifyRules.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.assertEquals(driver, text, "降噪策略");
        LogFunction.logInfo("-----------------进入，降噪策略页面---------------------");
    }

    String Oracal = null;
    String Domain = null;
    String Priority = null;

    @Test(dataProvider = "xmldata", enabled = false)
    public void alarmClassifyRulesCreate(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，全部规则
        WebElement allRules = l.getElement(param.get("allRules"));
        String text2 = allRules.getText();
        allRules.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "全部规则", "----验证是否点击：全部规则按钮----");
//        点击，告警分类规则
        WebElement alarmClassifyRules = l.getElement(param.get("alarmClassifyRules"));
        String text = alarmClassifyRules.getText();
        alarmClassifyRules.click();
        LogFunction.logInfo("选择：" + text);
        AssertFunction.verifyEquals(driver, text, "告警分类规则", "告警分类规则");
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
        AssertFunction.assertEquals(driver, text10, "保存");
        LogFunction.logInfo("-----------------告警分类规则，创建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-选择，告警分类规则
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyChooseAlarmClassifyRules(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，全部规则
        WebElement allRules = l.getElement(param.get("allRules"));
        String text2 = allRules.getText();
        String allRulesValue = InterceptFunction.intercept(text2, "全部规则");
        allRules.click();
        LogFunction.logInfo("点击：" + allRulesValue);
        AssertFunction.verifyEquals(driver, allRulesValue, "全部规则", "----验证是否点击：全部规则按钮----");
//        点击，告警分类规则
        WebElement alarmClassifyRules = l.getElement(param.get("alarmClassifyRules"));
        String text = alarmClassifyRules.getText();
        alarmClassifyRules.click();
        LogFunction.logInfo("选择：" + text);
        AssertFunction.verifyEquals(driver, text, "告警分类规则", "告警分类规则");
        LogFunction.logInfo("-----------------选择：告警分类规则---------------------");
    }

    //    集中告警-告警配置-降噪策略-选择，告警合并规则
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyChooseAlarmMergeRules(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，全部规则
        WebElement allRules = l.getElement(param.get("allRules"));
        String text2 = allRules.getText();
        String allRulesValue = InterceptFunction.intercept(text2, "全部规则");
        allRules.click();
        LogFunction.logInfo("点击：" + allRulesValue);
        AssertFunction.verifyEquals(driver, allRulesValue, "全部规则", "----验证是否点击：全部规则按钮----");
//        点击，告警分类规则
        WebElement alarmMergeRules = l.getElement(param.get("alarmMergeRules"));
        String text = alarmMergeRules.getText();
        alarmMergeRules.click();
        LogFunction.logInfo("选择：" + text);
        AssertFunction.verifyEquals(driver, text, "告警合并规则", "告警合并规则");
        LogFunction.logInfo("-----------------选择：告警合并规则---------------------");
    }


    //    集中告警-告警配置-降噪策略-告警分类规则-新建第1部分，基础设置
    @Test(dataProvider = "xmldata")
    public void alarmClassifyRulesCreateBasicSetting(Map<String, String> param) {
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
        LogFunction.logInfo("-----------------告警分类规则，第一步，基础设置录入完成---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警分类规则-新建第2部分，规则条件设置
    @Test(dataProvider = "xmldata")
    public void alarmClassifyRulesCreateRulesConditionSetting(Map<String, String> param) {
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
        AssertFunction.verifyEquals(driver, Oracal, "操作系统", "----验证选择的类型是否为；Oracle----");
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
        LogFunction.logInfo("-----------------告警分类规则，第二步，规则条件设置录入完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类规则-新建第3部分，规则条件高级设置
    @Test(dataProvider = "xmldata")
    public void alarmClassifyRulesCreateRulesConditionAdvancedSetting(Map<String, String> param) {
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
        WebElement contentKeyword = l.getElement(param.get("contentKeyword"));
        contentKeyword.sendKeys(param.get("contentKeywordValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("contentKeywordValue"));
//       点击，规则条件高级设置，下一步
        WebElement rulesConditionAdvancedConfigNextStep = l.getElement(param.get("rulesConditionAdvancedConfigNextStep"));
        String text71 = rulesConditionAdvancedConfigNextStep.getText();
        rulesConditionAdvancedConfigNextStep.click();
        LogFunction.logInfo("点击：" + text71);
        AssertFunction.verifyEquals(driver, text71, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警分类规则，第三步，规则条件高级设置录入完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类规则-新建第4部分，告警分类设置
    @Test(dataProvider = "xmldata")
    public void alarmClassifyRulesCreateAlarmClassifySetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        AssertFunction.assertEquals(driver, text10, "保存");
        LogFunction.logInfo("-----------------告警分类规则，最后一步，告警分类设置录入完成且告警分类规则创建成功---------------------");
    }

//    //    集中告警-告警配置-降噪策略-告警分类规则-筛选及校验
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
//        LogFunction.logInfo("-----------------降噪策略告警分类规则筛选查询完成---------------------");
//    }

    @Test(dataProvider = "xmldata")
    public void alarmDisplay(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，告警展示
        WebElement alarmDisplay = l.getElement(param.get("alarmDisplay"));
        String text = alarmDisplay.getText();
        alarmDisplay.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.assertEquals(driver, text, "告警展示");
        LogFunction.logInfo("-----------------进入，告警展示页面---------------------");
    }

    //    集中告警-告警展示-待处理告警
    @Test(dataProvider = "xmldata")
    public void alarmDisplayPendingAlarm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
//        AssertFunction.assertEquals(driver, numberText, String.valueOf(size));

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
//        AssertFunction.assertEquals(driver, numberText, String.valueOf(size));

        LogFunction.logInfo("-----------------进入，已确认告警页面---------------------");
    }


    //    集中告警-告警展示-已前转告警
    @Test(dataProvider = "xmldata")
    public void alarmDisplayForwardShiftingAlarm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //        勾选，信息
        WebElement chooseAlarm = l.getElement(param.get("chooseAlarm"));
        chooseAlarm.click();
        LogFunction.logInfo("成功勾选：警示信息");
//        AssertFunction.assertEquals(driver, numberText, String.valueOf(size));
        LogFunction.logInfo("-----------------进入，已前转告警页面---------------------");
    }

    //    集中告警-告警展示-已解决告警
    @Test(dataProvider = "xmldata")
    public void alarmDisplayResolvedAlarm(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
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
//        AssertFunction.assertEquals(driver, numberText, String.valueOf(size));
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
//        AssertFunction.assertEquals(driver, numberText, String.valueOf(size));
        LogFunction.logInfo("-----------------进入，已忽略告警页面---------------------");
    }

    //    集中告警-告警展示-勾选信息功能
    @Test(dataProvider = "xmldata")
    public void alarmDisplayChooseTwoFunction(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
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
    public void alarmDisplayChooseFunction(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，筛选
        WebElement Select = l.getElement(param.get("commonCentralizedAlarmSelect"));
        String text = Select.getText();
        Select.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "筛选");
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
        WebElement CIType = l.getElement(param.get("CentralizedAlarmSelectCIType"));
        CIType.click();
        LogFunction.logInfo("点击： CI类型选择框");
//        选择,rootDomain
        WebElement CITypeValue = l.getElement(param.get("CentralizedAlarmSelectCITypeValue"));
        String text2 = CITypeValue.getText();
        CITypeValue.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "操作系统");
//        点击，CI实例
        WebElement CICase = l.getElement(param.get("CentralizedAlarmSelectCICase"));
        CICase.click();
        LogFunction.logInfo("点击： CI实例选择框");
//        选择,StandardLinux_Test1
        WebElement CICaseValue = l.getElement(param.get("CentralizedAlarmSelectCICaseValue"));
        String text3 = CICaseValue.getText();
        CICaseValue.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3, "StandardLinux_Test1");
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
////        点击，空白
//        WebElement systemTitleblank = l.getElement(param.get("systemTitleblank"));
//        systemTitleblank.click();
//        LogFunction.logInfo("点击： 空白");
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        校验，查询结果之消息内容
        WebElement MessageContent = l.getElement(param.get("alarmDisplayColumnDisplayMessageContent"));
        String text5 = MessageContent.getText();
        String substring = text5.substring(8);
        LogFunction.logInfo("警示信息：" + text5);
        AssertFunction.verifyEquals(driver, substring, param.get("alarmDisplaySearchBoxValue"));
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，确认
        WebElement Confirmation = l.getElement(param.get("alarmDisplayConfirmation"));
        String text = Confirmation.getText();
        Confirmation.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "确认");
        LogFunction.logInfo("-----------------告警展示,成功点击确认按钮---------------------");

    }

    //    集中告警-告警展示-确认
    @Test(dataProvider = "xmldata")
    public void alarmDisplayConfirmation(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        AssertFunction.verifyEquals(driver, text2, "确定");
//        弹出，提示信息
        WebElement hintMessage = l.getElement(param.get("hintMessage"));
        String text3 = hintMessage.getText();
        hintMessage.click();
        LogFunction.logInfo("提示信息为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "已确认1个告警.");
//        点击，备注-提示信息，确认
        WebElement affirm1 = l.getElement(param.get("hintMessageConfirm"));
        String text44 = affirm1.getText();
        affirm1.click();
        LogFunction.logInfo("点击：" + text44);
        AssertFunction.verifyEquals(driver, text44, "确认");

        LogFunction.logInfo("-----------------告警展示,确认，完成---------------------");
    }

    //    集中告警-告警展示-点击前转 Button
    @Test(dataProvider = "xmldata")
    public void alarmDisplayForwardShiftingClick(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，前转
        WebElement Confirmation = l.getElement(param.get("alarmDisplayForwardShifting"));
        String text = Confirmation.getText();
        Confirmation.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "前转");

    }

    //    集中告警-告警展示-前转
    @Test(dataProvider = "xmldata")
    public void alarmDisplayForwardShifting(Map<String, String> param) {
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
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        AssertFunction.verifyEquals(driver, text21, "确定");
//        弹出，提示信息
        WebElement hintMessage = l.getElement(param.get("hintMessage"));
        String text3 = hintMessage.getText();
        hintMessage.click();
        LogFunction.logInfo("提示信息为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "已前转1条告警!");
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
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        AssertFunction.verifyEquals(driver, text21, "确定");
//        弹出，提示信息
        WebElement hintMessage = l.getElement(param.get("hintMessage"));
        String text3 = hintMessage.getText();
        hintMessage.click();
        LogFunction.logInfo("提示信息为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "已前转1条告警!");
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

    //    集中告警-告警展示-解决Button2
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

    //    集中告警-告警展示-解决Button
    @Test(dataProvider = "xmldata")
    public void alarmDisplayResolvedButton2(Map<String, String> param) {
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

    //    集中告警-告警展示-解决Button
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

    //    集中告警-告警展示-解决-解决
    @Test(dataProvider = "xmldata")
    public void alarmDisplayResolvedOfResolved(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        AssertFunction.verifyEquals(driver, text2, "确定");
//        弹出，提示信息
        WebElement hintMessage = l.getElement(param.get("hintMessage"));
        String text3 = hintMessage.getText();
        hintMessage.click();
        LogFunction.logInfo("提示信息为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "已关闭1个告警.");

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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        AssertFunction.verifyEquals(driver, text2, "确定");
//        弹出，提示信息
        WebElement hintMessage = l.getElement(param.get("hintMessage"));
        String text3 = hintMessage.getText();
        hintMessage.click();
        LogFunction.logInfo("提示信息为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "已关闭1个告警.");
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        AssertFunction.verifyEquals(driver, text2, "确定");
//        弹出，提示信息
        WebElement hintMessage = l.getElement(param.get("hintMessage"));
        String text3 = hintMessage.getText();
        hintMessage.click();
        LogFunction.logInfo("提示信息为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "已关闭1个告警.");
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

    //    集中告警-告警展示-查看-其他信息
    @Test(dataProvider = "xmldata")
    public void alarmDisplayViewOtherInformation(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，前转
        WebElement alarmDisplayView = l.getElement(param.get("alarmDisplayViewForwardShifting"));
        String text = alarmDisplayView.getText();
        alarmDisplayView.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "前转");
        LogFunction.logInfo("-----------------告警展示,进入查看-前转页面---------------------");
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        AssertFunction.verifyEquals(driver, text1, "Message:AutoTest");
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，关联告警
        WebElement alarmDisplayView = l.getElement(param.get("alarmDisplayRelevanceAlarm"));
        String text = alarmDisplayView.getText();
        alarmDisplayView.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "关联告警");
//        验证，是否进入关联告警页面
        WebElement Title = l.getElement(param.get("alarmDisplayRelevanceAlarmTitle"));
        String text2 = Title.getText();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "关联告警查看");
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，取消
        WebElement alarmDisplayView = l.getElement(param.get("alarmDisplayViewCancel"));
        String text = alarmDisplayView.getText();
        alarmDisplayView.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "取消");
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，待处理告警
        WebElement alarmDisplay = l.getElement(param.get("alarmDisplayPendingAlarm"));
        String text = alarmDisplay.getText();
        alarmDisplay.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text.substring(0, 5), "待处理告警");
//        获取，待处理告警数
        WebElement Number = l.getElement(param.get("alarmDisplayPendingAlarmNumber"));
        numberText = Integer.valueOf(Number.getText());
        LogFunction.logInfo("待处理告警数量：" + numberText);
//        获取，列表待处理告警数量
        List<WebElement> ListNumber = l.getElements(param.get("alarmDisplayListNumber"));
        int size = ListNumber.size();
        WebElement NumberValue = l.getElement(param.get("alarmDisplayListNumberValue"));
        String text5 = NumberValue.getText();
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
//        获取，已确认告警数量
        WebElement Number1 = l.getElement(param.get("alarmDisplayConfirmedAlarmNumber"));
        numberText1 = Integer.valueOf(Number1.getText());
        LogFunction.logInfo("待处理告警数量：" + numberText1);
//        获取，列表已确认告警数量
        List<WebElement> ListNumber1 = l.getElements(param.get("alarmDisplayListNumber"));
        int size1 = ListNumber1.size();
        WebElement NumberValue1 = l.getElement(param.get("alarmDisplayListNumberValue"));
        String text6 = NumberValue1.getText();
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
//        获取，已前转告警数量
        WebElement Number2 = l.getElement(param.get("alarmDisplayForwardShiftingAlarmNumber"));
        numberText2 = Integer.valueOf(Number2.getText());
        LogFunction.logInfo("已前转告警数量：" + numberText2);
//        获取，列表已前转告警数量
        List<WebElement> ListNumber2 = l.getElements(param.get("alarmDisplayListNumber"));
        int size2 = ListNumber2.size();
        WebElement NumberValue2 = l.getElement(param.get("alarmDisplayListNumberValue"));
        String text7 = NumberValue2.getText();
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
//        获取，已解决告警数量
        WebElement Number3 = l.getElement(param.get("alarmDisplayResolvedAlarmNumber"));
        numberText3 = Integer.valueOf(Number3.getText());
        LogFunction.logInfo("已解决告警数量：" + numberText3);
//        获取，列表已解决告警数量
        List<WebElement> ListNumber3 = l.getElements(param.get("alarmDisplayListNumber"));
        int size3 = ListNumber3.size();
        WebElement NumberValue3 = l.getElement(param.get("alarmDisplayListNumberValue"));
        String text8 = NumberValue3.getText();
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
//        获取，已忽略告警数量
        WebElement Number4 = l.getElement(param.get("alarmDisplayIgnoreAlarmNumber"));
        numberText4 = Integer.valueOf(Number4.getText());
        LogFunction.logInfo("已忽略告警数量：" + numberText4);
//        获取，列表已忽略告警数量
        List<WebElement> ListNumber4 = l.getElements(param.get("alarmDisplayListNumber"));
        int size4 = ListNumber4.size();
        WebElement NumberValue4 = l.getElement(param.get("alarmDisplayListNumberValue"));
        String text9 = NumberValue4.getText();
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，待处理告警
        WebElement alarmDisplay = l.getElement(param.get("alarmDisplayPendingAlarm"));
        String text = alarmDisplay.getText();
        alarmDisplay.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text.substring(0, 5), "待处理告警");
//        获取，待处理告警数
        WebElement Number = l.getElement(param.get("alarmDisplayPendingAlarmNumber"));
        String numberText00 = Number.getText();
        LogFunction.logInfo("待处理告警数量：" + numberText00);
//        获取，列表待处理告警数量
        List<WebElement> ListNumber = l.getElements(param.get("alarmDisplayListNumber"));
        int size = ListNumber.size();
        LogFunction.logInfo("列表待处理告警数量：" + String.valueOf(size));
        AssertFunction.verifyEquals(driver, Integer.valueOf(numberText00), numberText - 2);

//        点击，已确认告警
        WebElement alarmDisplay1 = l.getElement(param.get("alarmDisplayConfirmedAlarm"));
        String text1 = alarmDisplay1.getText();
        alarmDisplay1.click();
        LogFunction.logInfo("点击：" + text1);
        AssertFunction.verifyEquals(driver, text1.substring(0, 5), "已确认告警");
//        获取，已确认告警数量
        WebElement Number1 = l.getElement(param.get("alarmDisplayConfirmedAlarmNumber"));
        String numberText11 = Number1.getText();
        LogFunction.logInfo("待处理告警数量：" + numberText11);
//        获取，列表已确认告警数量
        List<WebElement> ListNumber1 = l.getElements(param.get("alarmDisplayListNumber"));
        int size1 = ListNumber1.size();
        LogFunction.logInfo("列表已确认告警数量：" + String.valueOf(size1));
        AssertFunction.verifyEquals(driver, Integer.valueOf(numberText11), numberText1);

//        点击，已前转告警
        WebElement alarmDisplay2 = l.getElement(param.get("alarmDisplayForwardShiftingAlarm"));
        String text2 = alarmDisplay2.getText();
        alarmDisplay2.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2.substring(0, 5), "已前转告警");
//        获取，已前转告警数量
        WebElement Number2 = l.getElement(param.get("alarmDisplayForwardShiftingAlarmNumber"));
        String numberText22 = Number2.getText();
        LogFunction.logInfo("已前转告警数量：" + numberText22);
//        获取，列表已前转告警数量
        List<WebElement> ListNumber2 = l.getElements(param.get("alarmDisplayListNumber"));
        int size2 = ListNumber2.size();
        LogFunction.logInfo("列表已前转告警数量：" + String.valueOf(size2));
        AssertFunction.verifyEquals(driver, Integer.valueOf(numberText22), numberText2 - 1);

//        点击，已解决告警
        WebElement alarmDisplay3 = l.getElement(param.get("alarmDisplayResolvedAlarm"));
        String text3 = alarmDisplay3.getText();
        alarmDisplay3.click();
        LogFunction.logInfo("点击：" + text3);
        AssertFunction.verifyEquals(driver, text3.substring(0, 5), "已解决告警");
//        获取，已解决告警数量
        WebElement Number3 = l.getElement(param.get("alarmDisplayResolvedAlarmNumber"));
        String numberText33 = Number3.getText();
        LogFunction.logInfo("已解决告警数量：" + numberText33);
//        获取，列表已解决告警数量
        List<WebElement> ListNumber3 = l.getElements(param.get("alarmDisplayListNumber"));
        int size3 = ListNumber3.size();
        LogFunction.logInfo("列表已解决告警数量：" + String.valueOf(size3));
        AssertFunction.verifyEquals(driver, Integer.valueOf(numberText33), numberText3 + 1);

//        点击，已忽略告警
        WebElement alarmDisplay4 = l.getElement(param.get("alarmDisplayIgnoreAlarm"));
        String text4 = alarmDisplay4.getText();
        alarmDisplay4.click();
        LogFunction.logInfo("点击：" + text4);
        AssertFunction.verifyEquals(driver, text4.substring(0, 5), "已忽略告警");
//        获取，已忽略告警数量
        WebElement Number4 = l.getElement(param.get("alarmDisplayIgnoreAlarmNumber"));
        String numberText44 = Number4.getText();
        LogFunction.logInfo("已忽略告警数量：" + numberText44);
//        获取，列表已忽略告警数量
        List<WebElement> ListNumber4 = l.getElements(param.get("alarmDisplayListNumber"));
        int size4 = ListNumber4.size();
        LogFunction.logInfo("列表已忽略告警数量：" + String.valueOf(size4));
        AssertFunction.verifyEquals(driver, Integer.valueOf(numberText44), numberText4 + 1);
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
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean selected3 = l.getElement(param.get("chooseAlarm")).isSelected();
        LogFunction.logInfo("告警是否勾选：" + selected3);
        AssertFunction.verifyEquals(driver, selected3, true);
        LogFunction.logInfo("-----------------告警展示,自动刷新功能校验，成功---------------------");

    }

    //    集中告警-告警展示-查看-提示（选择多条）
    @Test(dataProvider = "xmldata")
    public void alarmDisplayViewMultitermHint(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        勾选，第一条，警示信息
        WebElement chooseAlarm = l.getElement(param.get("chooseAlarm"));
        chooseAlarm.click();
        LogFunction.logInfo("成功勾选：第一条警示信息");
//        勾选，第二条，警示信息
        WebElement chooseAlarm2 = l.getElement(param.get("chooseAlarm2"));
        chooseAlarm2.click();
        LogFunction.logInfo("成功勾选：第二条警示信息");
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
        AssertFunction.verifyEquals(driver, title, "一次只能操作一条记录");
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        搜索框，输入，AutoTest
        WebElement SearchBox = l.getElement(param.get("alarmDisplaySearchBox"));
        SearchBox.sendKeys(param.get("alarmDisplaySearchBoxValue"));
        LogFunction.logInfo("在搜索框中输入：" + param.get("alarmDisplaySearchBoxValue"));
//        点击，搜索按钮
        WebElement Button = l.getElement(param.get("alarmDisplaySearchButton"));
        Button.click();
        LogFunction.logInfo("成功点击：搜索按钮");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        获取消息内容
        WebElement MessageContent = l.getElement(param.get("alarmDisplayColumnDisplayMessageContent"));
        String text = MessageContent.getText();
        String substring = text.substring(8);
        LogFunction.logInfo("警示信息：" + text);
        AssertFunction.verifyEquals(driver, substring, param.get("alarmDisplaySearchBoxValue"));
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，列设置，按钮
        WebElement ColumnSetting = l.getElement(param.get("alarmDisplayColumnSetting"));
        ColumnSetting.click();
        LogFunction.logInfo("成功点击：列设置按钮");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        勾选所有的列
        List<WebElement> ColumnElement = l.getElements(param.get("alarmDisplayColumnSettingisDisplay"));
        LogFunction.logInfo("共有" + String.valueOf(ColumnElement.size()) + "个列值");
        for (WebElement e : ColumnElement) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ee) {
                ee.printStackTrace();
            }
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，列设置，按钮
        WebElement ColumnSetting = l.getElement(param.get("alarmDisplayColumnSetting"));
        ColumnSetting.click();
        LogFunction.logInfo("成功点击：列设置按钮");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        取消勾选所有的列
        List<WebElement> ColumnElement = l.getElements(param.get("alarmDisplayColumnSettingisDisplay"));
        LogFunction.logInfo("共有" + String.valueOf(ColumnElement.size()) + "个列值");
        for (WebElement e : ColumnElement) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ee) {
                ee.printStackTrace();
            }
            boolean selected = e.isSelected();
            if (selected == true) {
                e.click();
            }
        }
        LogFunction.logInfo("取消勾选，所有列");
        try {
            Thread.sleep(30);
        } catch (InterruptedException ee) {
            ee.printStackTrace();
        }
//      点击，确定
        WebElement ColumnSettingConfirm = l.getElement(param.get("alarmDisplayColumnSettingConfirm"));
        String text = ColumnSettingConfirm.getText();
        ColumnSettingConfirm.click();
        LogFunction.logInfo("成功点击：" + text);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ee) {
            ee.printStackTrace();
        }
//        获取，页面中所有的列值
        List<WebElement> ColumnDisplay = l.getElements(param.get("alarmDisplayColumnDisplay"));
        LogFunction.logInfo("页面共展示" + String.valueOf(ColumnDisplay.size() - 1) + "个列值");
        LogFunction.logInfo("-----------------展示已取消全部列---------------------");

    }

    //    集中告警-告警展示-告警级别
    @Test(dataProvider = "xmldata")
    public void alarmDisplayAlarmRank(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，级别L5
        WebElement RankL5 = l.getElement(param.get("alarmDisplayAlarmRankL5"));
        String text = RankL5.getText();
        Integer integer = Integer.valueOf(text);
        RankL5.click();
        LogFunction.logInfo("点击：告警级别L5");
        LogFunction.logInfo("级别L5数量为：" + text);
//        获取，列表待处理告警数量
        List<WebElement> ListNumber = l.getElements(param.get("alarmDisplayListNumber"));
        int size = ListNumber.size();
        WebElement NumberValue = l.getElement(param.get("alarmDisplayListNumberValue"));
        String text5 = NumberValue.getText();
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
//        获取，列表待处理告警数量
        List<WebElement> ListNumber1 = l.getElements(param.get("alarmDisplayListNumber"));
        int size1 = ListNumber1.size();
        WebElement NumberValue1 = l.getElement(param.get("alarmDisplayListNumberValue"));
        String text6 = NumberValue1.getText();
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
//        获取，列表待处理告警数量
        List<WebElement> ListNumber2 = l.getElements(param.get("alarmDisplayListNumber"));
        int size2 = ListNumber2.size();
        WebElement NumberValue2 = l.getElement(param.get("alarmDisplayListNumberValue"));
        String text7 = NumberValue2.getText();
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
//        获取，列表待处理告警数量
        List<WebElement> ListNumber3 = l.getElements(param.get("alarmDisplayListNumber"));
        int size3 = ListNumber3.size();
        WebElement NumberValue3 = l.getElement(param.get("alarmDisplayListNumberValue"));
        String text8 = NumberValue3.getText();
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
//        获取，列表待处理告警数量
        List<WebElement> ListNumber4 = l.getElements(param.get("alarmDisplayListNumber"));
        int size4 = ListNumber4.size();
        WebElement NumberValue4 = l.getElement(param.get("alarmDisplayListNumberValue"));
        String text9 = NumberValue4.getText();
        if (text9.equals("表中数据为空")) {
            size4 = 0;
        }
        LogFunction.logInfo("列表待处理告警数量：" + size4);
        AssertFunction.verifyEquals(driver, integer + integer1 + integer2 + integer3 + integer4, size4);

    }

    //    集中告警-告警配置-降噪策略-告警合并规则-新建第1部分，基础设置
    @Test(dataProvider = "xmldata")
    public void alarmMergeRulesCreateBasicSetting(Map<String, String> param) {
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
        LogFunction.logInfo("-----------------告警合并规则，第一步，基础设置录入完成---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警合并规则-新建第2部分，规则条件设置
    @Test(dataProvider = "xmldata")
    public void alarmMergeRulesCreateRulesConditionSetting(Map<String, String> param) {
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
        AssertFunction.verifyEquals(driver, Oracal, "操作系统", "----验证选择的类型是否为；Oracle----");
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
        LogFunction.logInfo("-----------------告警合并规则，第二步，规则条件设置录入完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并规则-新建第3部分，规则条件高级设置
    @Test(dataProvider = "xmldata")
    public void alarmMergeRulesCreateRulesConditionAdvancedSetting(Map<String, String> param) {
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
        WebElement contentKeyword = l.getElement(param.get("contentKeyword"));
        contentKeyword.sendKeys(param.get("contentKeywordValue"));
        LogFunction.logInfo("规则名称，录入：" + param.get("contentKeywordValue"));
//       点击，规则条件高级设置，下一步
        WebElement rulesConditionAdvancedConfigNextStep = l.getElement(param.get("rulesConditionAdvancedConfigNextStep"));
        String text71 = rulesConditionAdvancedConfigNextStep.getText();
        rulesConditionAdvancedConfigNextStep.click();
        LogFunction.logInfo("点击：" + text71);
        AssertFunction.verifyEquals(driver, text71, "下一步", "----验证是否点击：下一步----");
        LogFunction.logInfo("-----------------告警合并规则，第三步，规则条件高级设置录入完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并规则-新建第4部分，告警合并设置
    @Test(dataProvider = "xmldata")
    public void alarmMergeRulesCreateAlarmMergeSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        AssertFunction.assertEquals(driver, text10, "保存");
        LogFunction.logInfo("-----------------告警合并规则，最后一步，告警合并设置录入完成且告警合并规则创建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并规则-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmMergeRulesSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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
        AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
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

    //    集中告警-告警配置-降噪策略-告警合并规则-编辑，第1部分，基础设置
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
        LogFunction.logInfo("-----------------告警合并规则，第一步，基础设置编辑完成---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警合并规则-编辑，第2部分，规则条件设置
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
//        AssertFunction.verifyEquals(driver, Oracal, "操作系统", "----验证选择的类型是否为；Oracle----");
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
        LogFunction.logInfo("-----------------告警合并规则，第二步，规则条件设置编辑完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并规则-编辑，第3部分，规则条件高级设置
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
        LogFunction.logInfo("-----------------告警合并规则，第三步，规则条件高级设置编辑完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并规则-编辑，第4部分，告警合并设置
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
        AssertFunction.assertEquals(driver, text10, "保存");
        LogFunction.logInfo("-----------------告警合并规则，最后一步，告警合并设置编辑完成且告警合并规则创建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并规则-编辑-筛选及校验
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
        AssertFunction.verifyEquals(driver, text55, "表中数据为空");
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
        LogFunction.logInfo("-----------------降噪策略告警分类规则筛选查询完成---------------------");
    }

    //    （公用）启用，告警规则
    @Test(dataProvider = "xmldata")
    public void alarmRulesStartUsing(Map<String, String> param) {
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
//         验证，删除提示信息:是否删除选中项？
        WebElement deleteHint = l.getElement(param.get("deleteHint"));
        String text1 = deleteHint.getText();
        LogFunction.logInfo("删除提示信息：" + text1);
        AssertFunction.verifyEquals(driver, text1, "是否删除选中项", "----验证，删除提示信息:是否删除选中项----");
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
        AssertFunction.verifyEquals(driver, text55, "表中数据为空");

        LogFunction.logInfo("------------------告警规则，删除成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并规则-编辑-筛选(仅筛选，勾选规则）
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
            WebElement commonSelect = l.getElement(param.get("commonSelect"));
            String text = commonSelect.getText();
            commonSelect.click();
            LogFunction.logInfo("点击：" + text);
            AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
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
        LogFunction.logInfo("-----------------降噪策略告警分类规则筛选查询完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并规则-编辑-筛选(仅筛选，勾选规则）
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmMergeRulesEditSelect1(Map<String, String> param) {
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
            WebElement commonSelect = l.getElement(param.get("commonSelect"));
            String text = commonSelect.getText();
            commonSelect.click();
            LogFunction.logInfo("点击：" + text);
            AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
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
        LogFunction.logInfo("-----------------降噪策略告警分类规则筛选查询完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并规则-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmClassifyRulesSelectVerify(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
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
            AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");

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

    //    集中告警-告警配置-降噪策略-告警分类规则-编辑，第1部分，基础设置
    @Test(dataProvider = "xmldata")
    public void alarmClassifyRulesEditBasicSetting(Map<String, String> param) {
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
        LogFunction.logInfo("-----------------告警合并规则，第一步，基础设置编辑完成---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警分类规则-编辑，第2部分，规则条件设置
    @Test(dataProvider = "xmldata")
    public void alarmClassifyRulesEditRulesConditionSetting(Map<String, String> param) {
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
//        AssertFunction.verifyEquals(driver, Oracal, "操作系统", "----验证选择的类型是否为；Oracle----");
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
        LogFunction.logInfo("-----------------告警合并规则，第二步，规则条件设置编辑完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类规则-编辑，第3部分，规则条件高级设置
    @Test(dataProvider = "xmldata")
    public void alarmClassifyRulesEditRulesConditionAdvancedSetting(Map<String, String> param) {
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
        LogFunction.logInfo("-----------------告警合并规则，第三步，规则条件高级设置编辑完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类规则-编辑，第4部分，告警分类设置
    @Test(dataProvider = "xmldata")
    public void alarmClassifyRulesEditAlarmClassifySetting(Map<String, String> param) {
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
        WebElement alarmMergeConfigSave = l.getElement(param.get("alarmClassifyConfigSave"));
        String text10 = alarmMergeConfigSave.getText();
        alarmMergeConfigSave.click();
        LogFunction.logInfo("点击：" + text10);
        AssertFunction.assertEquals(driver, text10, "保存");
        LogFunction.logInfo("-----------------告警合并规则，最后一步，告警合并设置编辑完成且告警合并规则创建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类规则-编辑-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmClassifyRulesEditSelectVerify(Map<String, String> param) {
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
        AssertFunction.verifyEquals(driver, text55, "表中数据为空");
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
        LogFunction.logInfo("-----------------降噪策略告警分类规则筛选查询完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类规则-编辑-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmClassifyRulesEditSelect(Map<String, String> param) {
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
            WebElement commonSelect = l.getElement(param.get("commonSelect"));
            String text = commonSelect.getText();
            commonSelect.click();
            LogFunction.logInfo("点击：" + text);
            AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
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
        LogFunction.logInfo("-----------------降噪策略告警分类规则筛选查询完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类规则-编辑-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmClassifyRulesEditSelect1(Map<String, String> param) {
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
            WebElement commonSelect = l.getElement(param.get("commonSelect"));
            String text = commonSelect.getText();
            commonSelect.click();
            LogFunction.logInfo("点击：" + text);
            AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");
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
        LogFunction.logInfo("-----------------降噪策略告警分类规则筛选查询完成---------------------");
    }


    @BeforeClass
    public void aa() {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        SeleniumDriver seleniumDriver = new SeleniumDriver();
        driver = seleniumDriver.getDriver();
        l = new LocatorFunction(driver);
        ssf = new ScreenshotFunction(driver);
    }

    @AfterClass
    public void bb() throws InterruptedException {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        Thread.sleep(3000);
        driver.close();
        driver.quit();
    }

}
