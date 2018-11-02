package com.testcases;

import com.base.TestBase;
import com.bean.BaseConfig;
import org.apache.log4j.lf5.LF5Appender;
import org.apache.poi.hssf.record.chart.LineFormatRecord;
import org.apache.poi.ss.formula.functions.Intercept;
import org.omg.PortableInterceptor.LOCATION_FORWARD;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.*;
import utill.*;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public class VicubeSystem extends TestBase {
    public WebDriver driver;
    private LocatorFunction l;
    private Map<String, String> param;
    private CommonObject co;
//    -------------------------------------登录，系统设置-------------------------------------

    //    用户登陆
    @Test(dataProvider = "xmldata")
    public void userLogin(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        driver.get(param.get("VicubeUrl"));
        LogFunction.logInfo("网址成功打开：" + param.get("VicubeUrl"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
//        enabled = save.isEnabled();
//        LogFunction.logInfo(save.getText() + "：按钮的状态");
//        AssertFunction.verifyEquals(driver, enabled, false, "----验证保存按钮是否可用----");
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
////        点击，微信确认
//        WebElement editAffirm = l.getElement(param.get("editAffirm"));
//        String text2 = editAffirm.getText();
//        editAffirm.click();
//        LogFunction.logInfo("点击：" + text);
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
        Select select = new Select(selectGroup);
        List<WebElement> allSelectedOptions = select.getAllSelectedOptions();
        boolean contains = allSelectedOptions.contains(param.get("chooseWorkingGroupSelect"));
        if (contains == true) {
            select.selectByVisibleText(param.get("chooseWorkingGroupSelect"));
        } else {
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
//         录入，工作组备注
        WebElement Note = l.getElement(param.get("editWorkingGroupeNote"));
        Note.clear();
        Note.sendKeys(param.get("editWorkingGroupeNoteValue"));
        LogFunction.logInfo("录入工作组备注:" + param.get("editWorkingGroupeNoteValue"));
        LogFunction.logInfo("------------------工作组编辑完成---------------------");

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
        AssertFunction.verifyEquals(driver, text2, "退出系统");
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

    //   菜单-集中告警
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
        AssertFunction.verifyEquals(driver, text, "集中告警");
        LogFunction.logInfo("-----------------进入，集中告警页面---------------------");
    }

    //      菜单-集中告警-告警配置
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
        AssertFunction.verifyEquals(driver, text, "告警配置");
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
        AssertFunction.verifyEquals(driver, text, "降噪策略");
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
        AssertFunction.verifyEquals(driver, text10, "保存");
        LogFunction.logInfo("-----------------告警分类策略，创建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-选择，告警分类策略
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyChooseAlarmClassifyRules(Map<String, String> param) {
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
//        点击，告警分类策略
        WebElement alarmMergeRules = l.getElement(param.get("alarmMergeRules"));
        String text = alarmMergeRules.getText();
        alarmMergeRules.click();
        LogFunction.logInfo("选择：" + text);
        AssertFunction.verifyEquals(driver, text, "告警合并策略", "告警合并策略");
        LogFunction.logInfo("-----------------选择：告警合并策略---------------------");
    }

    //    集中告警-告警配置-降噪策略-选择，告警过滤策略
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyChooseAlarmFilterRules(Map<String, String> param) {
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
//        点击，告警过滤策略
        WebElement alarmRecoveryRules = l.getElement(param.get("alarmFilterRules"));
        String text = alarmRecoveryRules.getText();
        alarmRecoveryRules.click();
        LogFunction.logInfo("选择：" + text);
        AssertFunction.verifyEquals(driver, text, "告警过滤策略");
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
//        点击，告警关联策略
        WebElement alarmRecoveryRules = l.getElement(param.get("AlarmRelevanceRules"));
        String text = alarmRecoveryRules.getText();
        alarmRecoveryRules.click();
        LogFunction.logInfo("选择：" + text);
        AssertFunction.verifyEquals(driver, text, "告警关联策略", "告警升级策略");
        LogFunction.logInfo("-----------------选择：告警关联策略---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类策略-新建第1部分，基础设置
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
        LogFunction.logInfo("-----------------告警分类策略，第一步，基础设置录入完成---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警分类策略-新建第2部分，规则条件设置
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
        LogFunction.logInfo("-----------------告警分类策略，第二步，规则条件设置录入完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类策略-新建第3部分，规则条件高级设置
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
        LogFunction.logInfo("-----------------告警分类策略，第三步，规则条件高级设置录入完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类策略-新建第4部分，告警分类设置
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
        AssertFunction.verifyEquals(driver, text10, "保存");
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
        AssertFunction.verifyEquals(driver, text, "告警展示");
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
        AssertFunction.verifyEquals(driver, text.substring(0, 5), "已通知告警");

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
        try {
            Thread.sleep(3000);
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
    public void alarmDisplayChooseFunction(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(3000);
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
            Thread.sleep(3000);
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

//        点击，空白
        WebElement systemTitleblank = l.getElement(param.get("systemTitleblank"));
        systemTitleblank.click();
        LogFunction.logInfo("点击： 空白");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            Thread.sleep(3000);
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
        AssertFunction.verifyEquals(driver, text, "通知");

    }

    //    集中告警-告警展示-点击前转 Button
    @Test(dataProvider = "xmldata")
    public void alarmDisplayForwardShiftingClick1(Map<String, String> param) {
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
        AssertFunction.verifyEquals(driver, text, "通知");

    }

    //    集中告警-告警展示-点击通知 Button
    @Test(dataProvider = "xmldata")
    public void alarmDisplayInformClick(Map<String, String> param) {
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
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        AssertFunction.verifyEquals(driver, text3, "已发送1条告警!");
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
        AssertFunction.verifyEquals(driver, text21, "确定");
//        弹出，提示信息
        WebElement hintMessage = l.getElement(param.get("hintMessage"));
        String text3 = hintMessage.getText();
        hintMessage.click();
        LogFunction.logInfo("提示信息为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "已发送1条告警!");
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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，导出
        WebElement Export = l.getElement(param.get("alarmDisplayExport"));
        String text = Export.getText();
        Export.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "导出");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
            Thread.sleep(3000);
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        AssertFunction.verifyEquals(driver, text2.substring(0, 5), "已通知告警");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        AssertFunction.verifyEquals(driver, text2.substring(0, 5), "已通知告警");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
//        勾选所有的列
        List<WebElement> ColumnElement = l.getElements(param.get("alarmDisplayColumnSettingisDisplay"));
        LogFunction.logInfo(ColumnElement.size());
        LogFunction.logInfo("共有" + String.valueOf(ColumnElement.size()) + "个列值");
        for (WebElement e : ColumnElement) {
            try {
                Thread.sleep(500);
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ee) {
            ee.printStackTrace();
        }
        driver.navigate().refresh();
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        LogFunction.logInfo("-----------------告警展示，告警等级，验证通过---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警合并策略-新建第1部分，基础设置
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
        LogFunction.logInfo("-----------------告警合并策略，第一步，基础设置录入完成---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警合并策略-新建第2部分，规则条件设置
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
        LogFunction.logInfo("-----------------告警合并策略，第二步，规则条件设置录入完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并策略-新建第3部分，规则条件高级设置
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
        LogFunction.logInfo("-----------------告警合并策略，第三步，规则条件高级设置录入完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并策略-新建第4部分，告警合并设置
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
        AssertFunction.verifyEquals(driver, text10, "保存");
        LogFunction.logInfo("-----------------告警合并策略，最后一步，告警合并设置录入完成且告警合并策略创建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警合并策略-筛选及校验
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
        LogFunction.logInfo("-----------------降噪策略,告警合并策略,筛选查询完成---------------------");
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
        AssertFunction.verifyEquals(driver, text55, "表中数据为空");
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
        co.alarmRulesDelete(param, "alarmMergeRulesNameEditValue");
        LogFunction.logInfo("-----------------降噪策略,告警合并规则,删除且筛选校验通过---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类规则-筛选及校验
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

    //    集中告警-告警配置-降噪策略-告警分类策略-编辑，第1部分，基础设置
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
        LogFunction.logInfo("-----------------告警分类策略，第一步，基础设置编辑完成---------------------");

    }

    //    集中告警-告警配置-降噪策略-告警分类策略-编辑，第2部分，规则条件设置
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
        LogFunction.logInfo("-----------------告警分类策略，第二步，规则条件设置编辑完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类策略-编辑，第3部分，规则条件高级设置
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
        LogFunction.logInfo("-----------------告警分类策略，第三步，规则条件高级设置编辑完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类策略-编辑，第4部分，告警分类设置
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
        AssertFunction.verifyEquals(driver, text10, "保存");
        LogFunction.logInfo("-----------------告警分类策略，最后一步，告警合并设置编辑完成且告警合并策略创建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类策略-编辑-筛选及校验
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
        LogFunction.logInfo("-----------------降噪策略告警分类策略筛选查询完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类策略-编辑-筛选
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
        LogFunction.logInfo("-----------------降噪策略告警分类策略筛选查询完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警分类策略-编辑-筛选
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmClassifyRulesDeleteAndSelectVerify(Map<String, String> param) {
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
        co.alarmRulesDelete(param, "alarmClassifyRulesNameEditValue");
        LogFunction.logInfo("-----------------降噪策略,告警分类策略,删除及筛选校验通过---------------------");
    }

    //    集中告警-告警查询
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，告警查询
        WebElement alarmSelect = l.getElement(param.get("alarmSelect"));
        String text = alarmSelect.getText();
        alarmSelect.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "告警查询");
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，历史告警
        WebElement historyAlarm = l.getElement(param.get("historyAlarm"));
        String text = historyAlarm.getText();
        historyAlarm.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "历史告警");
        LogFunction.logInfo("-----------------进入，历史告警页面---------------------");

    }

    //    集中告警-告警查询-历史告警-筛选
    @Test(dataProvider = "xmldata")
    public void alarmQueryHistoryAlarmSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，筛选
        WebElement Select = l.getElement(param.get("commonCentralizedAlarmSelect"));
        String text = Select.getText();
        Select.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "筛选");
//         点击，CI域，选择框
        WebElement selectCIDomain = l.getElement(param.get("selectCIDomain"));
        selectCIDomain.click();
        LogFunction.logInfo("点击：CI域");
//          选择，CI域，(rootDomain)
        WebElement selectCIDomainValue = l.getElement(param.get("selectCIDomainValue"));
        String text1 = selectCIDomainValue.getText();
        selectCIDomainValue.click();
        LogFunction.logInfo("CI域选择：" + text1);
        AssertFunction.verifyEquals(driver, text1, "rootDomain");
//         点击，CI类型，选择框
        WebElement selectCIType = l.getElement(param.get("selectCIType"));
        selectCIType.click();
        LogFunction.logInfo("点击：CI域");
//          选择，CI类型，(操作系统)
        WebElement selectCITypeValue = l.getElement(param.get("selectCITypeValue"));
        String text2 = selectCITypeValue.getText();
        selectCITypeValue.click();
        LogFunction.logInfo("CI类型选择：" + text2);
        AssertFunction.verifyEquals(driver, text2, "操作系统");
//         点击，CI名称，选择框
        WebElement selectCIName = l.getElement(param.get("selectCIName"));
        selectCIName.click();
        LogFunction.logInfo("点击：CI域");
//          选择，CI名称，(StandardLinux_Test1)
        WebElement selectCINameValue = l.getElement(param.get("selectCINameValue"));
        String text3 = selectCINameValue.getText();
        selectCINameValue.click();
        LogFunction.logInfo("CI名称选择：" + text3);
        AssertFunction.verifyEquals(driver, text3, "StandardLinux_Test1");
//        点击，空白
        WebElement systemTitleblank = l.getElement(param.get("systemTitleblank"));
        systemTitleblank.click();
        LogFunction.logInfo("点击： 空白");
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//          校验，筛选结果，信息内容：Message:AutoTest
        WebElement selecResultInformationContent = l.getElement(param.get("selecResultInformationContent"));
        String text5 = selecResultInformationContent.getText();
        LogFunction.logInfo("信息内容为：" + text5);
        AssertFunction.verifyEquals(driver, text5, "Message:AutoTest");

        LogFunction.logInfo("-----------------历史告警,筛选校验通过---------------------");

    }


    //    集中告警-告警查询-历史告警-前转
    @Test(dataProvider = "xmldata")
    public void alarmQueryHistoryAlarmForwardShifting(Map<String, String> param) {
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
        AssertFunction.verifyEquals(driver, text21, "确定");
//        弹出，提示信息
        WebElement hintMessage = l.getElement(param.get("hintMessage"));
        String text3 = hintMessage.getText();
        hintMessage.click();
        LogFunction.logInfo("提示信息为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "已发送1条告警!");
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
        AssertFunction.verifyEquals(driver, text21, "确定");
//        弹出，提示信息
        WebElement hintMessage = l.getElement(param.get("hintMessage"));
        String text3 = hintMessage.getText();
        hintMessage.click();
        LogFunction.logInfo("提示信息为：" + text3);
        AssertFunction.verifyEquals(driver, text3, "已发送1条告警!");
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

    //    集中告警-告警通知
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmInform(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，告警查询
        WebElement AlarmInform = l.getElement(param.get("AlarmInform"));
        String text = AlarmInform.getText();
        AlarmInform.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "告警通知");
        LogFunction.logInfo("-----------------进入，告警通知页面---------------------");

    }

    //    集中告警-告警通知-接收分组
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmInformReceiveGrouping(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，接收分组
        WebElement ReceiveGrouping = l.getElement(param.get("ReceiveGrouping"));
        String text = ReceiveGrouping.getText();
        ReceiveGrouping.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "接收分组");
        LogFunction.logInfo("-----------------进入，接收分组页面---------------------");

    }

    //    集中告警-告警通知-接收分组-新建分组
    @Test(dataProvider = "xmldata")
    public void receiveGroupingNewGrouping(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，新建分组
        WebElement NewGrouping = l.getElement(param.get("NewGrouping"));
        String text = NewGrouping.getText();
        NewGrouping.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "新建");
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，筛选
        WebElement Select = l.getElement(param.get("commonCentralizedAlarmSelect"));
        String text = Select.getText();
        Select.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "筛选");
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，编辑
        WebElement Edit = l.getElement(param.get("commonEdit"));
        String text = Edit.getText();
        Edit.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "编辑");
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
        AssertFunction.verifyEquals(driver, text2, "修改成功");
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
            WebElement Select = l.getElement(param.get("commonCentralizedAlarmSelect"));
            String text = Select.getText();
            Select.click();
            LogFunction.logInfo("点击：" + text);
            AssertFunction.verifyEquals(driver, text, "筛选");
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

    //    集中告警-告警通知-接收分组-删除
    @Test(dataProvider = "xmldata")
    public void receiveGroupingDelete(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            WebElement Select = l.getElement(param.get("commonCentralizedAlarmSelect"));
            String text = Select.getText();
            Select.click();
            LogFunction.logInfo("点击：" + text);
            AssertFunction.verifyEquals(driver, text, "筛选");
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
        WebElement EmptyData = l.getElement(param.get("QueryResultEmptyData"));
        String text = EmptyData.getText();
        LogFunction.logInfo("筛选结果:" + text);
        AssertFunction.assertEquals(driver, text, "表中数据为空");
        LogFunction.logInfo("-----------------进入，筛选及验证通过---------------------");
    }

    //    集中告警-告警查询-通知记录
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmSelectNotifyRecord(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，告警查询-通知记录
        WebElement AlarmInform = l.getElement(param.get("NotifyRecord"));
        String text = AlarmInform.getText();
        AlarmInform.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "通知记录");
        LogFunction.logInfo("-----------------进入，通知记录页面---------------------");

    }

    //    集中告警-告警查询-通知记录-筛选
    @Test(dataProvider = "xmldata")
    public void alarmSelectNotifyRecordQuery(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//          校验，筛选区域是否打开
        Boolean queryArea = l.getElementIsDisplay(param.get("commonQueryArea"));
        LogFunction.logInfo("筛选区域的状态为：" + queryArea);
        if (queryArea == false) {
//        点击，筛选
            WebElement Select = l.getElement(param.get("commonCentralizedAlarmSelect"));
            String text = Select.getText();
            Select.click();
            LogFunction.logInfo("点击：" + text);
            AssertFunction.verifyEquals(driver, text, "筛选");
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
//          获取，查询结果，通知标题
        List<WebElement> queryResultNoticeTitle = l.getElements(param.get("QueryResultNoticeTitle"));
        for (WebElement e : queryResultNoticeTitle) {
            String text1 = e.getText();
            if (!text1.equals(param.get("commonForwardShiftingTitleValue"))) {
                LogFunction.logInfo("通知标题的值为：" + text1);
                AssertFunction.verifyEquals(driver, text1, param.get("commonForwardShiftingTitleValue"));
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，查看
        WebElement View = l.getElement(param.get("commonCentralizedAlarmView"));
        String text = View.getText();
        View.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "查看");
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，通知方式
        WebElement ReceiveGrouping = l.getElement(param.get("InformWay"));
        String text = ReceiveGrouping.getText();
        ReceiveGrouping.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "通知方式");
        LogFunction.logInfo("-----------------进入，通知方式页面---------------------");

    }

    //    集中告警-告警通知-通知方式-新建
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmInformInformWayCreate(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，新建
        WebElement Create = l.getElement(param.get("Create"));
        String text = Create.getText();
        Create.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "新建");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        String text66 = InterceptFunction.intercept(text6, "超级管理员");
        LogFunction.logInfo("选择通知目标用户：" + text6);
        AssertFunction.verifyEquals(driver, text66, "超级管理员");
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        校验，筛选区域是否打开
        Boolean commonQueryArea = l.getElementIsDisplay(param.get("commonQueryArea"));
        LogFunction.logInfo("筛选区域是否打开：" + commonQueryArea);
        if (commonQueryArea == false) {
//        点击，筛选
            WebElement Create = l.getElement(param.get("Query"));
            String text = Create.getText();
            Create.click();
            LogFunction.logInfo("点击：" + text);
            AssertFunction.verifyEquals(driver, text, "筛选");
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
        WebElement QueryTargetUser = l.getElement(param.get("QueryTargetUser"));
        QueryTargetUser.sendKeys(param.get("concentrateAlarmAlarmInformInformWayQueryTargetUserValue"));
        LogFunction.logInfo("通知目标用户，录入：" + param.get("concentrateAlarmAlarmInformInformWayQueryTargetUserValue"));
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，编辑
        WebElement Create = l.getElement(param.get("Edit"));
        String text = Create.getText();
        Create.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "编辑");
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

    //    集中告警-告警通知-通知方式-筛选（编辑）
    @Test(dataProvider = "xmldata")
    public void concentrateAlarmAlarmInformInformWayEditSelect(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        校验，筛选区域是否打开
        Boolean commonQueryArea = l.getElementIsDisplay(param.get("commonQueryArea"));
        LogFunction.logInfo("筛选区域是否打开：" + commonQueryArea);
        if (commonQueryArea == false) {
//        点击，筛选
            WebElement Create = l.getElement(param.get("Query"));
            String text = Create.getText();
            Create.click();
            LogFunction.logInfo("点击：" + text);
            AssertFunction.verifyEquals(driver, text, "筛选");
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
        WebElement ResultEmpty = l.getElement(param.get("ResultEmpty"));
        String text10 = ResultEmpty.getText();
        LogFunction.logInfo("点击：" + text10);
        AssertFunction.verifyEquals(driver, text10, "表中数据为空");
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        校验，筛选区域是否打开
        Boolean commonQueryArea = l.getElementIsDisplay(param.get("commonQueryArea"));
        LogFunction.logInfo("筛选区域是否打开：" + commonQueryArea);
        if (commonQueryArea == false) {
//        点击，筛选
            WebElement Create = l.getElement(param.get("Query"));
            String text = Create.getText();
            Create.click();
            LogFunction.logInfo("点击：" + text);
            AssertFunction.verifyEquals(driver, text, "筛选");
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//       校验，筛选结果，为空
        WebElement ResultEmpty = l.getElement(param.get("ResultEmpty"));
        String text10 = ResultEmpty.getText();
        LogFunction.logInfo("点击：" + text10);
        AssertFunction.verifyEquals(driver, text10, "表中数据为空");
        LogFunction.logInfo("-----------------通知方式，删除成功---------------------");
    }

    //    集中告警-告警通知-通知方式-阈值规则
    @Test(dataProvider = "xmldata")
    public void alarmConfigThresholdStrategy(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//         点击，阈值策略
        WebElement thresholdStrategy = l.getElement(param.get("thresholdStrategy"));
        String text = thresholdStrategy.getText();
        thresholdStrategy.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "阈值策略");
        LogFunction.logInfo("-----------------进入阈值策略页面---------------------");
    }

    //    集中告警-告警通知-通知方式-阈值规则-新建
    @Test(dataProvider = "xmldata")
    public void thresholdStrategyCreate(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            WebElement Select1 = l.getElement(param.get("commonSelect"));
            String text = Select1.getText();
            AssertFunction.verifyEquals(driver, text, "筛选");
            Select1.click();
            LogFunction.logInfo("打开：" + text);
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
            WebElement Select1 = l.getElement(param.get("commonSelect"));
            String text = Select1.getText();
            AssertFunction.verifyEquals(driver, text, "筛选");
            Select1.click();
            LogFunction.logInfo("打开：" + text);
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，删除
        co.deleteButton(param);
//        校验提示,信息,点击确认
        co.alarmHintAndConfirm(param, "确定要删除？");
//        二级校验提示,信息,点击确认
        co.alarmHintAndConfirm(param, "删除阈值规则成功");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//         点击，筛选
        Boolean SelectArea = l.getElementIsDisplay(param.get("SelectArea"));
        LogFunction.logInfo("筛选区域的状态为：" + SelectArea);
        if (SelectArea == false) {
            WebElement Select1 = l.getElement(param.get("commonSelect"));
            String text = Select1.getText();
            AssertFunction.verifyEquals(driver, text, "筛选");
            Select1.click();
            LogFunction.logInfo("打开：" + text);
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
        co.selectResultIsNull(param, "表中数据为空");

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
        AssertFunction.verifyEquals(driver, text10, "保存");
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
        WebElement commonSelect = l.getElement(param.get("commonSelect"));
        String text = commonSelect.getText();
        commonSelect.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "筛选", "----验证点击的是否是：筛选----");

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
        AssertFunction.verifyEquals(driver, text10, "保存");
        LogFunction.logInfo("-----------------告警恢复策略，最后一步，告警恢复设置编辑完成且告警合并策略创建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警恢复策略-编辑-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyalarmRecoveryRulesEditSelectVerify(Map<String, String> param) {
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
        LogFunction.logInfo("-----------------告警升级策略，最后一步，告警分类设置录入完成且创建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警升级策略-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmUpgradeRulesSelectVerify(Map<String, String> param) {
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
        AssertFunction.verifyEquals(driver, text10, "保存");
        LogFunction.logInfo("-----------------告警升级策略，最后一步，告警升级设置编辑完成且创建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警升级策略-编辑-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyAlarmUpgradeRulesEditSelectVerify(Map<String, String> param) {
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，预警策略
        WebElement alarmClassifyRules = l.getElement(param.get("WarningStrategy"));
        String text = alarmClassifyRules.getText();
        alarmClassifyRules.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "预警策略");
        LogFunction.logInfo("-----------------进入，预警策略页面---------------------");
    }

    //      菜单-集中告警-告警配置-预警策略-新建
    @Test(dataProvider = "xmldata")
    public void alarmConfigWarningStrategyCreate(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        点击，告警筛选
        WebElement AlarmInform = l.getElement(param.get("AlarmScreen"));
        String text = AlarmInform.getText();
        AlarmInform.click();
        LogFunction.logInfo("点击：" + text);
        AssertFunction.verifyEquals(driver, text, "告警筛选");
        LogFunction.logInfo("-----------------进入，告警筛选页面---------------------");

    }

    //    集中告警-告警通知-告警筛选-新建第1部分，基础设置
    @Test(dataProvider = "xmldata")
    public void alarmScreenCreateBasicSetting(Map<String, String> param) {
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
        LogFunction.logInfo("-----------------告警筛选，第二步，规则条件设置录入完成---------------------");
    }

    //    集中告警-告警通知-告警筛选-新建第3部分，规则条件高级设置
    @Test(dataProvider = "xmldata")
    public void alarmScreenCreateRulesConditionAdvancedSetting(Map<String, String> param) {
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        LogFunction.logInfo("-----------------告警筛选，第一步，基础设置编辑完成---------------------");

    }

    //    集中告警-告警通知-告警筛选-编辑，第2部分，规则条件设置
    @Test(dataProvider = "xmldata")
    public void alarmScreenEditRulesConditionSetting(Map<String, String> param) {
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
        LogFunction.logInfo("-----------------告警筛选，第二步，规则条件设置编辑完成---------------------");
    }

    //    集中告警-告警通知-告警筛选-编辑，第3部分，规则条件高级设置
    @Test(dataProvider = "xmldata")
    public void alarmScreenEditRulesConditionAdvancedSetting(Map<String, String> param) {
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
        LogFunction.logInfo("-----------------告警筛选，第三步，规则条件高级设置编辑完成---------------------");
    }

    //    集中告警-告警通知-告警筛选-编辑，第4部分，告警分类设置
    @Test(dataProvider = "xmldata")
    public void alarmScreenEditAlarmClassifySetting(Map<String, String> param) {
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
        AssertFunction.verifyEquals(driver, text10, "保存");
        LogFunction.logInfo("-----------------告警筛选，最后一步，告警筛选设置编辑完成且告警合并策略创建成功---------------------");
    }

    //    集中告警-告警通知-告警筛选-编辑-筛选及校验
    @Test(dataProvider = "xmldata")
    public void alarmScreenEditSelectVerify(Map<String, String> param) {
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
        selectRulesName.sendKeys(param.get("alarmScreenEditStrategyNameValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmScreenEditStrategyNameValue"));
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
        selectRulesName.sendKeys(param.get("alarmScreenEditStrategyNameValue"));
        LogFunction.logInfo("录入规则名称:" + param.get("alarmScreenEditStrategyNameValue"));
//        点击，筛选，确定
        WebElement selectAffirm = l.getElement(param.get("commonSelectAffirm"));
        String text2 = selectAffirm.getText();
        selectAffirm.click();
        LogFunction.logInfo("点击：" + text2);
        AssertFunction.verifyEquals(driver, text2, "确定");
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        验证，筛选区域，是否展示
        Boolean displayed = l.getElementIsDisplay(param.get("denoiseStrategySelectArea"));
//        WebElement SelectArea = l.getElement(param.get("denoiseStrategySelectArea"));
//        boolean displayed = SelectArea.isDisplayed();
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
        co.alarmRulesDelete(param, "alarmScreenEditStrategyNameValue");
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        AssertFunction.verifyEquals(driver, Oracal, "操作系统", "----验证选择的类型是否为；Oracle----");

        //          点击，节点关系
        WebElement NodeRelation = l.getElement(param.get("NodeRelation"));
        NodeRelation.click();
        LogFunction.logInfo("点击：节点关系");
//        选择,节点关系:归属于
        WebElement NodeRelationValue = l.getElement(param.get("NodeRelationValue"));
        String text = NodeRelationValue.getText();
        NodeRelationValue.click();
        LogFunction.logInfo("选择节点关系：" + text);
        AssertFunction.verifyEquals(driver, text, "归属于");


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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//            点击，编辑
        WebElement createButton = l.getElement(param.get("EditButton"));
        String text = createButton.getText();
        AssertFunction.verifyEquals(driver, text, "编辑");
        createButton.click();
        LogFunction.logInfo("点击：" + text);
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        WebElement resultEmpty = l.getElement(param.get("ResultEmpty"));
        String text1 = resultEmpty.getText();
        LogFunction.logInfo("结果为：" + text1);
        AssertFunction.assertEquals(driver, text1, "表中数据为空");
        LogFunction.logInfo("-----------------告警关联策略，最后一步之影响告警设置，删除成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警关联策略-新建第4部分，影响告警设置-保存
    @Test(dataProvider = "xmldata")
    public void alarmRelevanceRulesCreateAffectAlarmSettingSave(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //          点击，保存
        WebElement alarmClassifyConfigSave = l.getElement(param.get("alarmClassifyConfigSave"));
        String text101 = alarmClassifyConfigSave.getText();
        alarmClassifyConfigSave.click();
        LogFunction.logInfo("点击：" + text101);
        AssertFunction.verifyEquals(driver, text101, "保存");
        LogFunction.logInfo("-----------------告警关联策略，最后一步，影响告警设置，保存成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警关联策略-筛选及校验
    @Test(dataProvider = "xmldata")
    public void alarmRelevanceRulesCreateSelectVerify(Map<String, String> param) {
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
        AssertFunction.verifyEquals(driver, text4, "操作系统");
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
        AssertFunction.verifyEquals(driver, text10, "保存");
        LogFunction.logInfo("-----------------告警关联规则，最后一步，告警关联设置编辑完成且创建成功---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警关联规则-编辑-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyalarmRelevanceRulesEditSelectVerify(Map<String, String> param) {
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
        AssertFunction.verifyEquals(driver, text4, "操作系统");
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
        LogFunction.logInfo("-----------------告警过滤策略，第二步，规则条件设置录入完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警过滤策略-新建最后一部分，规则条件高级设置
    @Test(dataProvider = "xmldata")
    public void alarmFilterRulesCreateRulesConditionAdvancedSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        AssertFunction.verifyEquals(driver, text71, "保存", "----验证是否点击：保存----");
        LogFunction.logInfo("-----------------告警过滤策略，最后一部分，规则条件高级设置录入完成---------------------");
    }


    //    集中告警-告警配置-降噪策略-告警过滤策略-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyalarmFilterRulesSelectVerify(Map<String, String> param) {
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
        LogFunction.logInfo("-----------------告警过滤策略，第二步，规则条件设置编辑完成---------------------");
    }

    //    集中告警-告警配置-降噪策略-告警过滤策略-编辑，第3部分，规则条件高级设置
    @Test(dataProvider = "xmldata")
    public void alarmFilterRulesEditRulesConditionAdvancedSetting(Map<String, String> param) {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//       点击，规则条件高级设置，保存
        WebElement rulesConditionAdvancedConfigNextStep = l.getElement(param.get("alarmClassifyConfigSave"));
        String text71 = rulesConditionAdvancedConfigNextStep.getText();
        rulesConditionAdvancedConfigNextStep.click();
        LogFunction.logInfo("点击：" + text71);
        AssertFunction.verifyEquals(driver, text71, "保存", "----验证是否点击：保存----");
        LogFunction.logInfo("-----------------告警过滤策略，最后一步，规则条件高级设置编辑完成---------------------");
    }


    //    集中告警-告警配置-降噪策略-告警过滤策略-编辑-筛选及校验
    @Test(dataProvider = "xmldata")
    public void denoiseStrategyalarmFilterRulesEditSelectVerify(Map<String, String> param) {
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


    @BeforeClass
    public void testStart() {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        SeleniumDriver seleniumDriver = new SeleniumDriver();
        driver = seleniumDriver.getDriver();
        l = new LocatorFunction(driver);
        co = new CommonObject(driver, l);
    }

    @AfterClass
    public void testEnd() throws InterruptedException {
        LogFunction.logInfo(Thread.currentThread().getStackTrace()[1].getMethodName());
        Thread.sleep(3000);
        driver.close();
        driver.quit();
    }

//    @BeforeMethod
//    public void startTime(){
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

}

