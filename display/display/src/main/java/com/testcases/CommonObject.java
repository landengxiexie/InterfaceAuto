package com.testcases;

import com.bean.BaseConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import utill.*;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings("ALL")
public class CommonObject {
    public WebDriver driver;
    public LocatorFunction l;
    public JavascriptExecutor j;
    public Actions a;

    public CommonObject(WebDriver driver, LocatorFunction l) {
        this.driver = driver;
        this.l = l;
        j = (JavascriptExecutor) this.driver;
        a = new Actions(this.driver);
    }

    //      点击，新建Button,并校验页面title
    public void createButtonAndVerifyTitle(Map<String, String> param, String textType, String titleElement) {
        WebElement commonCreate = l.getElement(param.get("commonCreate"));
        String text = commonCreate.getText();
        AssertFunction.verifyEquals(driver, text, "新建");
        click(param, commonCreate);
        LogFunction.logInfo("点击：" + text);
        modelTitleVerify(param, text, titleElement, textType);
        sleep(1000);
    }

    //      点击，新建Button
    public void createButton(Map<String, String> param) {
        WebElement commonCreate;
        String text = null;
        try {
            sleep(500);
            commonCreate = l.getElement(param.get("commonNewlyIncreased"));
            text = commonCreate.getText();
        } catch (Exception e) {
            try {
                commonCreate = l.getElement(param.get("commonCreate"));
                text = commonCreate.getText();
            } catch (Exception e1) {
                commonCreate = l.getElement(param.get("commonCreateButton"));
                text = commonCreate.getText();
            }
        }
        click(param, commonCreate);
        LogFunction.logInfo("点击：" + text);
        sleep(1000);
    }

    //      点击，编辑Button
    public void editButton(Map<String, String> param) {
        WebElement commonEdit;
        String text=null;
        try {
            commonEdit = l.getElement(param.get("commonEdit"));
            text = commonEdit.getText();
        } catch (Exception e1) {
            commonEdit = l.getElement(param.get("commonEditButton"));
            text = commonEdit.getText();
        }
        click(param, commonEdit);
        LogFunction.logInfo("点击：" + text);
        sleep(1000);
    }

    //      点击，编辑Button
    public void copyButton(Map<String, String> param) {
        WebElement commonCopy = l.getElement(param.get("commonCopy"));
        String text = commonCopy.getText();
        AssertFunction.verifyEquals(driver, text, "复制");
        click(param, commonCopy);
        LogFunction.logInfo("点击：" + text);
        sleep(1000);
    }

    //      点击，编辑Button,并校验页面title
    public void editButtonAndVerifyTitle(Map<String, String> param, String textType, String titleElement) {
        WebElement commonEdit = l.getElement(param.get("commonEdit"));
        String text = commonEdit.getText();
        AssertFunction.verifyEquals(driver, text, "编辑");
        click(param, commonEdit);
        LogFunction.logInfo("点击：" + text);
        modelTitleVerify(param, text, titleElement, textType);
        sleep(1000);
    }

    //      点击，筛选Button
    public void screenButton(Map<String, String> param) {
        WebElement commonSelect = l.getElement(param.get("commonSelect"));
        String text = commonSelect.getText();
        click(param, commonSelect);
        LogFunction.logInfo("点击：" + text);
        sleep(1000);
    }

    //      点击，删除Button
    public void deleteButton(Map<String, String> param) {
        WebElement commonDelete;
        String text=null;
        try {
            commonDelete = l.getElement(param.get("commonDelete"));
            text = commonDelete.getText();
        } catch (Exception e1) {
            commonDelete = l.getElement(param.get("commonDeleteButton"));
            text = commonDelete.getText();
        }
        click(param, commonDelete);
        LogFunction.logInfo("点击：" + text);
        sleep(1000);
    }

    //      点击，设计Button
    public void designButton(Map<String, String> param) {
        WebElement commonDelete = l.getElement(param.get("commonDesign"));
        String text = commonDelete.getText();
        AssertFunction.verifyEquals(driver, text, "设计");
        click(param, commonDelete);
        LogFunction.logInfo("点击：" + text);
        sleep(1000);
    }

    //    删除，功能，含删除提示确认
    public void deleteFunc(Map<String, String> param, String message) {
//      勾选，结果
        chooseSelectResult(param);
//      点击，删除
        deleteButton(param);
//      点击，删除确认
        alarmConfirm(param);
//      点击，删除提示确认
        alarmConfirm(param);
        LogFunction.logInfo("----------------" + message + ",删除完成---------------------");
    }

    //    删除，功能，无，删除提示确认
    public void deleteFunc1(Map<String, String> param, String message) {
//      勾选，结果
        chooseSelectResult(param);
//      点击，删除
        deleteButton(param);
//      点击，删除确认
        alarmConfirm(param);
        LogFunction.logInfo("----------------" + message + ",删除完成---------------------");
    }

    //      点击，新增按钮
    public void newlyIncreasedButton(Map<String, String> param) {
        WebElement commonDelete = l.getElement(param.get("commonNewlyIncreased"));
        String text = commonDelete.getText();
        AssertFunction.verifyEquals(driver, text, "新增");
        click(param, commonDelete);
        LogFunction.logInfo("点击：" + text);
        sleep(1000);
    }

    //      点击，启用
    public void enableButton(Map<String, String> param) {
        WebElement commonStartUsing = l.getElement(param.get("commonStartUsing"));
        String text = commonStartUsing.getText();
        AssertFunction.verifyEquals(driver, text, "启用");
        click(param, commonStartUsing);
        LogFunction.logInfo("点击：" + text);
    }

    //      点击，停用
    public void disenableButton(Map<String, String> param) {
        WebElement commonCreate = l.getElement(param.get("commonBlockUp"));
        String text = commonCreate.getText();
        AssertFunction.verifyEquals(driver, text, "停用");
        click(param, commonCreate);
        LogFunction.logInfo("点击：" + text);
    }

    //      点击，筛选-清空Button
    public void selectClearButton(Map<String, String> param) {
        WebElement commonSelectClear = l.getElement(param.get("commonSelectClear"));
        String text = commonSelectClear.getText();
        AssertFunction.verifyEquals(driver, text, "清空");
        click(param, commonSelectClear);
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
            click(param, Select1);
            LogFunction.logInfo("打开：" + text);
        }
    }

    //      点击，筛选(共用)
    public void select(Map<String, String> param, String selectArea) {
        sleep(300);
        Boolean SelectArea = l.getElementIsDisplay(param.get(selectArea));
        LogFunction.logInfo("筛选区域的状态为：" + SelectArea);
        if (SelectArea == false) {
            WebElement Select1;
            String text = null;
            try {
                Select1 = l.getElement(param.get("commonSearch"));
                text = Select1.getText();
            } catch (Exception e) {
                Select1 = l.getElement(param.get("commonSelect"));
                text = Select1.getText();
            }
            click(param, Select1);
            LogFunction.logInfo("打开：" + text);
        }
        sleep(1000);
    }

    public void selectO(Map<String, String> param) {
        sleep(300);
        WebElement Select1;
        String text = null;
        try {
            Select1 = l.getElement(param.get("commonSearch"));
            text = Select1.getText();
        } catch (Exception e) {
            Select1 = l.getElement(param.get("commonSelect"));
            text = Select1.getText();
        }
        click(param, Select1);
        LogFunction.logInfo("打开：" + text);
        sleep(2000);
    }


    //      校验，筛选结果，为空的情况
    public void selectResultIsNull(Map<String, String> param) {
        sleep(2000);
//       校验，筛选结果，为空
        WebElement ResultEmpty = l.getElement(param.get("commonSelectResultIsEmpty"));
        String text10 = ResultEmpty.getText();
        try {
            AssertFunction.assertEquals(driver, text10, " ", "筛选结果不存在，验证失败,");
        } catch (Exception e) {
            try {
                AssertFunction.assertEquals(driver, text10, "", "筛选结果不存在，验证失败,");
            } catch (Exception el) {
                AssertFunction.assertEquals(driver, text10, "表中数据为空", "筛选结果不存在，验证失败,");
            }
        }
        LogFunction.logInfo("筛选结果为：" + text10);
    }

    //      校验，筛选结果，为空的情况
    public void selectResultIsNull(Map<String, String> param, String element) {
        sleep(2000);
//       校验，筛选结果，为空
        WebElement ResultEmpty = l.getElement(param.get(element));
        String text10 = ResultEmpty.getText();
        LogFunction.logInfo("筛选结果为：" + text10);
        try {
            AssertFunction.assertEquals(driver, text10, " ", "筛选结果不存在，验证失败,");
        } catch (Exception e) {
            try {
                AssertFunction.assertEquals(driver, text10, "", "筛选结果不存在，验证失败,");
            } catch (Exception el) {
                AssertFunction.assertEquals(driver, text10, "表中数据为空", "筛选结果不存在，验证失败,");
            }
        }
    }

    //      校验，筛选结果，为空的情况
    public void selectResultIsNull(Map<String, String> param, String element, String message) {
        sleep(2000);
//       校验，筛选结果，为空
        WebElement ResultEmpty = l.getElement(param.get(element));
        String text10 = ResultEmpty.getText();
        LogFunction.logInfo("筛选结果为：" + text10);
        AssertFunction.assertEquals(driver, text10, message, "筛选结果不存在，验证失败,");
    }

    //      校验，筛选结果，验证
    public void selectResultVerify(Map<String, String> param, String elementName, String verifyMessage) {
        sleep(2000);
//       校验，筛选结果
        Boolean isExist = l.getElementIsExist(param.get(elementName));
        LogFunction.logInfo("筛选结果是否存在:" + isExist);
        if (isExist) {
            WebElement Result = l.getElement(param.get(elementName));
            String text10 = Result.getText();
            AssertFunction.assertEquals(driver, text10, param.get(verifyMessage), "筛选结果不存在，验证失败,");
            LogFunction.logInfo("筛选结果：" + text10 + ",验证通过");
        } else {
            AssertFunction.assertEquals(driver, isExist, true, "筛选结果不存在，验证失败,");
        }
    }

    //      校验，删除，
    public void deleteFuncVerify(Map<String, String> param, String chooseValue) {
        sleep(2000);
        try {
            List<WebElement> r1 = l.getElements(param.get("commonSelectResultAll2"));
            LogFunction.logInfo("筛序结果的数量为：" + r1.size());
            String t;
            if (r1.size() > 0) {
                for (int i = 0; i < r1.size(); i++) {
                    WebElement e1 = r1.get(i);
                    t = e1.getText();
                    LogFunction.logInfo("删选结果为：" + t);
                    if (t.equals(param.get(chooseValue))) {
                        throw new RuntimeException("未获取到：" + param.get(chooseValue));
                    } else {
                        LogFunction.logInfo("删除成功：" + param.get(chooseValue));
                    }
                }
            } else {
                LogFunction.logInfo("删除成功：" + param.get(chooseValue));
            }
        } catch (Exception e) {
            selectResultIsNull(param);
            LogFunction.logInfo("删除成功：" + param.get(chooseValue));
        }
    }


    //      校验，筛选结果，验证
    public void selectResultVerify(Map<String, String> param, String elementName, String verifyMessage, String verifyMessageNotGet) {
        sleep(2000);
//       校验，筛选结果
        Boolean isExist = l.getElementIsExist(param.get(elementName));
        LogFunction.logInfo("筛选结果是否存在:" + isExist);
        if (isExist) {
            WebElement Result = l.getElement(param.get(elementName));
            String text10 = Result.getText();
            AssertFunction.assertEquals(driver, text10, verifyMessage, "筛选结果不存在，验证失败,");
            LogFunction.logInfo("筛选结果：" + text10 + ",验证通过");
        } else {
            AssertFunction.assertEquals(driver, isExist, true, "筛选结果不存在，验证失败,");
        }
    }

    //      校验，筛选结果，验证
    public void selectResultVerifyOfTextType(Map<String, String> param, String elementName, String message, String type) {
        sleep(2000);
//       校验，筛选结果
        Boolean isExist = l.getElementIsExist(param.get(elementName));
        LogFunction.logInfo("筛选结果是否存在:" + isExist);
        if (isExist) {
            WebElement Result = l.getElement(param.get(elementName));
            String textValue = getTextValue(Result, type);
            AssertFunction.assertEquals(driver, textValue, message, "筛选结果不存在，验证失败,");
            LogFunction.logInfo("筛选结果：" + textValue + ",验证通过");
        } else {
            AssertFunction.assertEquals(driver, isExist, true, "筛选结果不存在，验证失败,");
        }
    }


    //      勾选，筛选结果
    public void chooseSelectResultTwo(Map<String, String> param) {
        WebElement selectResult = l.getElement(param.get("commonSelectResult"));
        boolean selected = selectResult.isSelected();
        LogFunction.logInfo("筛选结果是否选择：" + selected);
        if (selected == false) {
            click(param, selectResult);
            LogFunction.logInfo("成功，勾选筛选结果");
        } else {
            LogFunction.logInfo("勾选筛选结果，已被勾选");
        }
    }

    //      勾选，筛选结果
    public void chooseSelectResult(Map<String, String> param) {
        WebElement selectResult = l.getElementIgnoreDisplay(param.get("commonSelectResult"));
        boolean selected = selectResult.isSelected();
        LogFunction.logInfo("筛选结果是否选择：" + selected);
        if (selected == false) {
            click(param, selectResult);
            LogFunction.logInfo("成功，勾选筛选结果");
        } else {
            LogFunction.logInfo("勾选筛选结果，已被勾选");
        }
    }

    //      勾选，筛选结果，通过指定名称勾选。。
    public void chooseSelectResultOfCondition(Map<String, String> param, String chooseValue) {
        sleep(2000);
        List<WebElement> r = l.getElements(param.get("commonSelectResultAll"));
        List<WebElement> r1 = l.getElements(param.get("commonSelectResultAll2"));
//        LogFunction.logInfo("筛序结果的数量为：" + r.size());
        if (r.size() > 0) {
            for (int i = 0; i < r.size(); i++) {
                WebElement e = r.get(i);
                boolean s = e.isSelected();
                WebElement e1 = r1.get(i);
                String t = e1.getText();
                if (t.equals(param.get(chooseValue)) && s == false) {
                    click(param, e);
                    sleep(200);
                    LogFunction.logInfo("成功勾选：" + param.get(chooseValue));
                }
            }
        } else {
            throw new RuntimeException("未获取到：" + param.get(chooseValue));
        }
    }
    //      勾选，筛选结果，通过指定名称勾选。。
    public void relevanceAnalyzeChooseSelectResultOfCondition(Map<String, String> param, String chooseValue) {
        sleep(2000);
        List<WebElement> r = l.getElements(param.get("relecanceAnalyzeList1"));
        List<WebElement> r1 = l.getElements(param.get("relecanceAnalyzeList2"));
//        LogFunction.logInfo("筛序结果的数量为：" + r.size());
        if (r.size() > 0) {
            for (int i = 0; i < r.size(); i++) {
                WebElement e = r.get(i);
                boolean s = e.isSelected();
                WebElement e1 = r1.get(i);
                String t = e1.getText();
                if (t.equals(param.get(chooseValue)) && s == false) {
                    click(param, e);
                    sleep(200);
                    LogFunction.logInfo("成功勾选：" + param.get(chooseValue));
                    break;
                }
            }
        } else {
            throw new RuntimeException("未获取到：" + param.get(chooseValue));
        }
    }

    //      通过指定名称，点击同一列参数
    public void relevanceAnalyzeClickOfCondition(Map<String, String> param, String listValue,String name) {
        sleep(2000);
        List<WebElement> r = l.getElements(param.get("relecanceAnalyzeList3"));
        List<WebElement> r1 = l.getElements(param.get("relecanceAnalyzeList2"));
        if (r.size() > 0) {
            for (int i = 0; i < r.size(); i++) {
                WebElement e = r.get(i);
                WebElement e1 = r1.get(i);
                String t = e1.getText();
                LogFunction.logInfo(t);
                if (t.equals(param.get(listValue))) {
                    click(param, e);
                    sleep(200);
                    LogFunction.logInfo("成功打开：" + param.get(listValue)+name);
                    break;
                }else {
                    LogFunction.logInfo("未打开：" + param.get(listValue)+name+"!!!!!!!!!");
                    break;
                }
            }
        } else {
            throw new RuntimeException("未获取到：" + param.get(listValue)+name);
        }
    }


    //      勾选，筛选结果，通过指定名称勾选。。
    public void chooseSelectResultOfConditionNumber(Map<String, String> param, String chooseValue,String numberElement) {
        sleep(2000);
        List<WebElement> r = l.getElements(param.get("commonSelectResultAll"));
        List<WebElement> r1 = l.getElements(param.get(numberElement));
//        LogFunction.logInfo("筛序结果的数量为：" + r.size());
        if (r.size() > 0) {
            for (int i = 0; i < r.size(); i++) {
                WebElement e = r.get(i);
                boolean s = e.isSelected();
                WebElement e1 = r1.get(i);
                String t = e1.getText();
                if (t.equals(param.get(chooseValue)) && s == false) {
                    click(param, e);
                    sleep(200);
                    LogFunction.logInfo("成功勾选：" + param.get(chooseValue));
                }
            }
        } else {
            throw new RuntimeException("未获取到：" + param.get(chooseValue));
        }
    }

    //      勾选，筛选结果
    public void chooseSelectResultAll(Map<String, String> param) {
        sleep(2000);
        List<WebElement> r = l.getElements(param.get("commonSelectResultAll"));
        LogFunction.logInfo("筛序结果的数量为：" + r.size());
        if (r.size() > 0) {
            for (int i = 0; i < r.size(); i++) {
                WebElement e = r.get(i);
                boolean selected = e.isSelected();
                if (selected == false) {
                    click(param, e);
                    sleep(200);
                    LogFunction.logInfo("成功勾选，第" + (i + 1) + "个，筛序结果");
                } else {
                    LogFunction.logInfo("勾选筛选结果，已被勾选");
                }
            }
        } else {
            throw new RuntimeException("未获取到，筛序结果");
        }
    }


    //      勾选，筛选结果
    public void chooseSelectResult(Map<String, String> param, String element) {
        WebElement selectResult = l.getElement(param.get(element));
        boolean selected = selectResult.isSelected();
        LogFunction.logInfo("筛选结果是否选择：" + selected);
        if (selected == false) {
            click(param, selectResult);
            LogFunction.logInfo("成功，勾选筛选结果");
        } else {
            LogFunction.logInfo("勾选筛选结果，已被勾选");
        }
    }

    //      Alarm提示信息校验及确定
    public void alarmConfirm(Map<String, String> param) {
        sleep(1000);
//        点击，确认
        WebElement Confirm = l.getElement(param.get("commonDeleteConfirm"));
        String text1 = Confirm.getText();
        AssertFunction.verifyEquals(driver, text1, BaseConfig.commonObjectConfirm);
        click(param, Confirm);
        LogFunction.logInfo("点击：" + text1);
    }

    //      Alarm提示信息校验及确定
    public void alarmHintAndConfirm(Map<String, String> param, String hintMessage) {
        sleep(1000);
//       校验，提示信息
        WebElement Hint = l.getElement(param.get("commonDeleteHint"));
        String text = Hint.getText();
        LogFunction.logInfo("保存提示信息为：" + text);
        AssertFunction.verifyEquals(driver, text, hintMessage);
//        点击，确认
        WebElement Confirm = l.getElement(param.get("commonDeleteConfirm"));
        String text1 = Confirm.getText();
        AssertFunction.verifyEquals(driver, text1, BaseConfig.commonObjectConfirm);
        click(param, Confirm);
        LogFunction.logInfo("点击：" + text1);
    }

    //      Alarm提示信息校验及确定
    public void alarmHintAndConfirm(Map<String, String> param) {
        sleep(1000);
//       校验，提示信息
        WebElement Hint = l.getElement(param.get("commonDeleteHint"));
        String text = Hint.getText();
        LogFunction.logInfo("提示信息为：" + text);
//        点击，确认
        WebElement Confirm = l.getElement(param.get("commonDeleteConfirm"));
        String text1 = Confirm.getText();
        AssertFunction.verifyEquals(driver, text1, BaseConfig.commonObjectConfirm);
        click(param, Confirm);
        LogFunction.logInfo("点击：" + text1);
    }

    //      Alarm提示信息校验及确定
    public void alarmHintAndConfirm(Map<String, String> param, String confirmElement, String messageIsNull) {
        sleep(1000);
//       校验，提示信息
        WebElement Hint = l.getElement(param.get("commonDeleteHint"));
        String text = Hint.getText();
        LogFunction.logInfo("提示信息为：" + text);
//        点击，确认
        WebElement Confirm = l.getElement(param.get(confirmElement));
        String text1 = Confirm.getText();
        AssertFunction.verifyEquals(driver, text1, BaseConfig.commonObjectConfirm);
        click(param, Confirm);
        LogFunction.logInfo("点击：" + text1);
    }

    //    模型，校验当前页面，title
    public void modelTitleVerify(Map<String, String> param, String title, String element, String textType) {
        WebElement e = l.getElement(param.get(element));
        String textValue = getTextValue(e, textType);
        AssertFunction.assertEquals(driver, textValue, title);
    }

    //      模型，点击按钮，打开相应区域，已打开则不点击
    public void modelOpenArea(Map<String, String> param, String buttonElement, String areaElement) {
        WebElement element = l.getElement(param.get(buttonElement));
        String t = element.getText();
        Boolean isDisplay = l.getElementIsDisplay(param.get(areaElement));
        LogFunction.logInfo(t + "区域是否打开：" + isDisplay);
        if (isDisplay == false) {
            click(param, element);
            LogFunction.logInfo(t + "区域已打开");
        }

    }

    //      模型,判断按钮区域是否展示,展示点击按钮
    public void modelAreaDisplayAndClick(Map<String, String> param, String areaElement, String buttonElement) {
        Boolean e = l.getElementIsDisplay(param.get(areaElement));
        LogFunction.logInfo("区域状态为："+e);
        WebElement element = l.getElement(param.get(buttonElement));
        String text = element.getText();
        if (!e) {
            click(param, element);
            LogFunction.logInfo("点击：" + text);
            sleep(500);
        }
    }

    //      模型,判断按钮区域是否展示,展示点击按钮
    public void modelAreaDisplayAndClick(Map<String, String> param, String areaElement, String buttonElement, String buttonName) {
        Boolean e = l.getElementIsDisplay(param.get(areaElement));
        LogFunction.logInfo("区域状态为："+e);
        if (!e) {
            WebElement element = l.getElement(param.get(buttonElement));
            click(param, element);
            LogFunction.logInfo("点击：" + buttonName);
            sleep(500);
        }
    }

    //      模型一,点击按钮操作,不校验按钮
    public void modelClickButton(Map<String, String> param, String buttonElement, String buttonName, String isNull) {
        WebElement element = l.getElement(param.get(buttonElement));
        click(param, element);
        LogFunction.logInfo("点击：" + buttonName);
    }

    //      模型一,点击按钮操作,不校验按钮
    public void modelClickButton(Map<String, String> param, String buttonElement) {
        WebElement element = l.getElement(param.get(buttonElement));
        String text = element.getText();
        click(param, element);
        LogFunction.logInfo("点击：" + text);
    }

    //      模型一,点击按钮操作,不校验按钮，根据类型取值
    public void modelClickButton(Map<String, String> param, String buttonElement, String valueType) {
        WebElement element = l.getElement(param.get(buttonElement));
        String textValue = getTextValue(element, valueType);
        click(param, element);
        LogFunction.logInfo("点击：" + textValue);
    }

    //    模型一，点击按钮，操作，不校校验按钮，根据buttonType获取按钮value
    public void modelClickButton4(Map<String, String> param, String buttonElement, String buttonType) {
        WebElement element = l.getElement(param.get(buttonElement));
        String textValue = getTextValue(element, buttonType);
        click(param, element);
        LogFunction.logInfo("点击：" + textValue);
    }

    //      模型一,点击按钮操作,并校验按钮值
    public void modelClickButton2(Map<String, String> param, String buttonElement, String buttonNameText) {
        WebElement element = l.getElement(param.get(buttonElement));
        String text = element.getText();
        AssertFunction.verifyEquals(driver, text, buttonNameText);
        sleep(500);
        click(param, element);
        LogFunction.logInfo("点击：" + text);
    }

    //      模型一,点击按钮操作,并校验按钮值，输出信息可更改
    public void modelClickButton2(Map<String, String> param, String buttonElement, String buttonName, String message) {
        WebElement element = l.getElement(param.get(buttonElement));
        String text = element.getText();
        AssertFunction.verifyEquals(driver, text, buttonName);
        click(param, element);
        LogFunction.logInfo(message + text);
    }

    //      模型一,点击按钮操作，不校验按钮值
    public void modelClickButton3(Map<String, String> param, String buttonElement, String messageNoAssert) {
        WebElement element = l.getElement(param.get(buttonElement));
        click(param, element);
        LogFunction.logInfo(messageNoAssert);
    }

    //      模型一,移动到元素位置并点击
    public void modelMoveToElementAndClick(Map<String, String> param, String element, String textType) {
        WebElement ele = l.getElement(param.get(element));
        String textValue = this.getTextValue(ele, textType);
//        Actions a = new Actions(driver);
        a.moveToElement(ele).click().build().perform();
        LogFunction.logInfo("点击：" + textValue);
    }

    //      模型二,警告框，确认
    public void modelAlertBox(Map<String, String> param, String hintElement, String hintName, String affirmElement, String affirmName) {
//        校验，提示信息
        WebElement element = l.getElement(param.get(hintElement));
        String text = element.getText();
        AssertFunction.verifyEquals(driver, text, hintName);
//        点击，确认按钮
        WebElement element1 = l.getElement(param.get(affirmElement));
        String text1 = element1.getText();
        AssertFunction.verifyEquals(driver, text, affirmName);
        click(param, element1);
        LogFunction.logInfo("点击：" + text1);
    }

    //    模式三，勾选单选框
    public void modelRadioBox(Map<String, String> param, String RadioBoxElement) {
//        校验，单选框是否勾选
        WebElement element = l.getElementIgnoreDisplay(param.get(RadioBoxElement));
        boolean text = element.isSelected();
        LogFunction.logInfo("勾选状态为；" + text);
        if (text == false) {
            click(param, element);
            LogFunction.logInfo("成功,勾选");
//            boolean selected = element.isSelected();
////            LogFunction.logInfo("勾选状态为；" + selected);
//
//            if (selected == true) {
//                LogFunction.logInfo("成功,勾选");
//            } else {
//                LogFunction.logInfo("勾选,失败");
//            }
        } else {
            LogFunction.logInfo("已被勾选");
        }
    }

    //    模式三，勾选单选框
    public void modelRadioBox(Map<String, String> param, String RadioBoxElement, String RadioBoxName) {
//        校验，单选框是否勾选
        WebElement element = l.getElementIgnoreDisplay(param.get(RadioBoxElement));
        boolean text = element.isSelected();
        LogFunction.logInfo(RadioBoxElement+"勾选状态为；" + text);
        if (text == false) {
            click(param, element);
            LogFunction.logInfo("成功,勾选," + RadioBoxName);
//            sleep(500);
//            boolean selected = element.isSelected();
////            LogFunction.logInfo("勾选状态为；" + selected);
//
//            if (selected == true) {
//
//            } else {
//                LogFunction.logInfo(RadioBoxName + ",勾选,失败");
//
//            }
        } else {
            LogFunction.logInfo(RadioBoxName + ",已被勾选");
        }
//        点击，确认按钮

    }

    //    模式三，不勾选单选框
    public void modelNoRadioBox(Map<String, String> param, String radioBoxElement, String radioBoxName) {
//        校验，单选框是否勾选
        WebElement element = l.getElementIgnoreDisplay(param.get(radioBoxElement));
        boolean text = element.isSelected();
//        LogFunction.logInfo(radioBoxName+"勾选状态为；" + text);
        if (text == true) {
            click(param, element);

            boolean selected = element.isSelected();
//            LogFunction.logInfo(radioBoxName+"勾选状态为；" + selected);

            if (selected == false) {
                LogFunction.logInfo(radioBoxName + "取消勾选,成功");
            } else {
                LogFunction.logInfo(radioBoxName + "取消勾选,失败");

            }
        } else {
            LogFunction.logInfo(radioBoxName + "未被勾选");
        }
    }

    //      模型四,输入框操作，录入
    public void modelInputBox(Map<String, String> param, String boxElement, String inputContent, String boxName) {
        WebElement element = l.getElement(param.get(boxElement));
        try {
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"), param.get(inputContent));
            LogFunction.logInfo(boxName + "录入：" + param.get(inputContent));
        } catch (Exception e) {
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"), inputContent);
            LogFunction.logInfo(boxName + "录入：" + inputContent);
        }
    }

    //      模型四,输入框操作,清空
    public void modelInputBoxClear(Map<String, String> param, String boxElement, String boxName) {
        WebElement element = l.getElement(param.get(boxElement));
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.chord(Keys.BACK_SPACE));
        LogFunction.logInfo(boxName + "，已清空");
    }

    //      模式五，元素是否可用
    public void modelElementIsEnable(Map<String, String> param, String boxElement, boolean isEnable, String message) {
        WebElement element = l.getElement(param.get(boxElement));
        boolean enabled = element.isEnabled();
        if (isEnable == false) {
            if (enabled == false) {
                LogFunction.logInfo(message);
            } else {
                AssertFunction.assertEquals(driver, enabled, false, message + "验证失败");
            }
        } else {
            if (enabled == true) {
                LogFunction.logInfo(message);
            } else {
                AssertFunction.assertEquals(driver, enabled, true, message + "验证失败");
            }
        }
    }

    //      模式六，获取元素的值
    public String modelAcquireElementValue(Map<String, String> param, String element, String acquireType, String message) {
        WebElement e = l.getElement(param.get(element));
        if (acquireType.equals("text")) {
            String text = e.getText();
            LogFunction.logInfo("获取" + message + "的值为：" + text);
            return text;
        } else {
            String attribute = e.getAttribute(acquireType);
            LogFunction.logInfo("获取" + message + "的值为：" + attribute);
            return attribute;
        }
    }

    //      模式七，按钮点击后并选择值
    public void modelClickAndChooseValue(Map<String, String> param, String elementButton, String elementValue, String buttonName) {
        WebElement e = l.getElement(param.get(elementButton));
        click(param, e);
        LogFunction.logInfo("点击：" + buttonName);
        sleep(1000);
        WebElement e2 = l.getElement(param.get(elementValue));
        String text = e2.getText();
        click(param, e2);
        LogFunction.logInfo("选择" + buttonName + "值：" + text);
        click(param, e);
    }

    //      模式七，按钮点击后并选择值
    public void modelClickAndChooseValueTwo(Map<String, String> param, String elementButton, String elementValue, String buttonName) {
        WebElement e = l.getElement(param.get(elementButton));
        click(param, e);
        LogFunction.logInfo("点击：" + buttonName);
        sleep(1000);

        WebElement e2 = l.getElement(param.get(elementValue));
        String text = e2.getText();
        click(param, e2);
        LogFunction.logInfo("选择" + buttonName + "值：" + text);
    }

    //      模式七，按钮点击后并选择值
    public void modelClickAndChooseValueThree(Map<String, String> param, String elementButton, String elementButton1, String elementValue, String buttonName) {
        WebElement e = l.getElement(param.get(elementButton));
        click(param, e);
        LogFunction.logInfo("点击：" + buttonName);
        sleep(1000);
        modelClickButton(param, elementButton1);
        sleep(1000);
        WebElement e2 = l.getElement(param.get(elementValue));
        String text = e2.getText();
        click(param, e2);
        LogFunction.logInfo("选择" + buttonName + "值：" + text);
    }

    //   （公用）删除，告警规则
    @Test(dataProvider = "xmldata")
    public void alarmRulesDelete(Map<String, String> param, String rules) {
        sleep(2000);
//        勾选，规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        if (selectChooseRules.isSelected() == false) {
            selectChooseRules.click();
            LogFunction.logInfo("勾选筛选结果规则");
        }
//        点击，删除按钮
        WebElement delete = l.getElement(param.get("delete"));
        String text = delete.getText();
        click(param, delete);
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
        selectResultIsNull(param, "alarmRulesListNumberValue");
//        WebElement NumberValue = l.getElement(param.get("alarmRulesListNumberValue"));
//        String text55 = NumberValue.getText();
//        LogFunction.logInfo("告警列表信息为：" + text55);
//        AssertFunction.verifyEquals(driver, text55, "表中数据为空");
    }

    //   （公用）删除，告警规则
    @Test(dataProvider = "xmldata")
    public void alarmRulesDelete(Map<String, String> param, String resultElement, String rules) {
        sleep(2000);
//        勾选，规则
        WebElement selectChooseRules = l.getElement(param.get("commonDenoiseStrategySelectChoose"));
        if (selectChooseRules.isSelected() == false) {
            selectChooseRules.click();
            LogFunction.logInfo("勾选筛选结果规则");
        }
//        点击，删除按钮
        WebElement delete = l.getElement(param.get("delete"));
        String text = delete.getText();
        click(param, delete);
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
        selectResultIsNull(param, param.get(resultElement));
    }

    //    文件导出功能
    public void exportFunction(Map<String, String> param) {
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
    }

    //    获取元素的值
    public String getTextValueString(Map<String, String> param, String element, String type) {
        WebElement e = l.getElement(param.get(element));
        String textValue = getTextValue(e, type);
        LogFunction.logInfo("获取的元素值为：" + textValue);
        return textValue;
    }


    //    获取元素的值
    private String getTextValue(WebElement element, String type) {
        if (type.equals("text")) {
            String text = element.getText();
            return text;
        } else {
            String attribute = element.getAttribute(type);
            return attribute;
        }
    }

    //    获取Post请求结果
    public String getStringResultOfPost(HttpPost post) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        String result = null;
        try {
            response = httpClient.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取响应的状态码
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            LogFunction.logInfo("请求成功statusCode = " + statusCode);
            try {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (result.length() > 500) {
                LogFunction.logInfo("resultEntity获取成功:" + result.toString().substring(0, 500));
            } else {
                LogFunction.logInfo("resultEntity获取成功:" + result.toString());
            }
        } else {
            LogFunction.logInfo("请求失败:" + "statusCode = " + statusCode);
            throw new RuntimeException("请求失败");
        }
        return result;
    }

    //    等待n秒
    public void sleep(long millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //    验证，选择列表是否含有数据
    public void chooseSelectListValueFuzzyMatch(Map<String, String> param, String element, String verifyValue) {
        Select s = new Select(l.getElement(param.get(element)));
        List<WebElement> o = s.getOptions();
        LogFunction.logInfo("列表的数量为：" + o.size());
        for (int i = 0; i < o.size(); i++) {
            WebElement e = o.get(i);
            String text = e.getText();
            if (text.contains(param.get(verifyValue))) {
                click(param, e);
                LogFunction.logInfo("成功，勾选数据" + param.get(verifyValue));
                return;
            }
        }
    }

    //    验证，选择列表是否含有数据
    public void chooseSelectListValueFuzzyMatch(Map<String, String> param, String element, String verifyValue, String notGetVerifyValue) {
        Select s = new Select(l.getElement(param.get(element)));
        List<WebElement> o = s.getOptions();
        LogFunction.logInfo("列表的数量为：" + o.size());
        for (int i = 0; i < o.size(); i++) {
            WebElement e = o.get(i);
            String text = e.getText();
            if (text.contains(verifyValue)) {
                click(param, e);
                LogFunction.logInfo("成功，勾选数据" + verifyValue);
                return;
            }
        }
    }

    //    验证，列表是否含有数据
    public void selectIsContainsValue(Map<String, String> param, String element, String verifyValue) {
        Select s = new Select(l.getElement(param.get(element)));
        List<WebElement> o = s.getOptions();
        for (int i = 0; i < o.size(); i++) {
            String text = o.get(i).getText();
            if (text.equals(verifyValue)) {
                LogFunction.logInfo("数据存在于，列表中");
                return;
            }
        }
    }

    //      模型一,移动到元素位置并点击
    public void modelMoveToElementAndClick(Map<String, String> param, WebElement element) {
//        Actions a = new Actions(driver);
        a.moveToElement(element).click().build().perform();
    }

    //    点击
    public void click(Map<String, String> param, WebElement element) {
        try {
            j.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            try {
                modelMoveToElementAndClick(param, element);
            } catch (Exception e1) {
                element.click();
            }
        }
    }
        public void doubleClick(Map<String, String> param, String element,String name){
            try {
                j.executeScript("arguments[0].dblclick();", l.getElement(param.get(element)));
                LogFunction.logInfo("双击："+name);
            } catch (Exception e) {
                    Actions a=new Actions(driver);
                    a.doubleClick(l.getElement(param.get(element)));
                    LogFunction.logInfo("双击："+name);
            }
        }


    //    点击，拖拽，元素
    public void dragElement(Map<String, String> param, String elementName, int x, int y, String message) {
        WebElement element = l.getElement(param.get(elementName));
        a.dragAndDropBy(element, x, y).build().perform();
        LogFunction.logInfo(message + "被拖拽" + "X:" + String.valueOf(x) + "-Y:" + String.valueOf(y));
    }

    //    点击，拖拽，元素
    public void dragElement(Map<String, String> param, String elementName, int x, int y) {
        WebElement element = l.getElement(param.get(elementName));
        a.clickAndHold(element).moveToElement(element, x, y).release().build().perform();
        LogFunction.logInfo("元素，被拖拽" + "X:" + String.valueOf(x) + "-Y:" + String.valueOf(y));

    }

    //    推送，告警信息
    public void pushAlarmInfo(Map<String, String> param, String num1233or3211) {
        HttpFunction h = new HttpFunction();
        if (num1233or3211.equals("123")) {
            Object post = h.post(param.get("pushAlarmUrl"), param.get("pushAlarmParams123"));
        } else if (num1233or3211.equals("321")) {
            Object post = h.post(param.get("pushAlarmUrl"), param.get("pushAlarmParams321"));
        } else {
            throw new RuntimeException("num输入有误，请输入：123或321");
        }
    }

    //    推送，告警信息
    public void pushMergeAlarmInfo(Map<String, String> param, String body) {
        HttpFunction h = new HttpFunction();
        Object post = h.post(param.get("pushAlarmUrl"), param.get(body));
    }

    //    推送，KPI信息
    public void pushKPIInfo(Map<String, String> param, String body) {
        HttpFunction h = new HttpFunction();
        Object post = h.post(param.get("pushKPIUrl"), param.get(body));
    }

    //        点击，右侧菜单展开按钮
    public void openLeftMenuButton(Map<String, String> param) {
        //        点击，右侧菜单展开按钮
        Boolean a = l.getElementIsDisplay(param.get("menuButtonArea"));
        LogFunction.logInfo("左侧菜单状态为：" + a);
        if (!a) {
//         点击，左侧菜单按钮
            modelClickButton(param, "menuButton", "左侧菜单展开按钮", "");
            LogFunction.logInfo("打开左侧菜单");
        }
        sleep(500);
    }

    public void mergeRulesCreate(Map<String, String> param) {
//          点击，新建
        createButton(param);
//          录入，规则名称
        modelInputBox(param, "rulesName", "alarmMergeRulesNameValue", "规则名称");
//          点击，基础设置，下一步
        modelClickButton(param, "basicsNextStep");
        LogFunction.logInfo("第一步，基础设置录入完成");
//          验证，域默认值
        String valueString = getTextValueString(param, "domainDefault", "text");
        AssertFunction.verifyEquals(driver, valueString, "rootDomain");
//          选择，类型
        modelClickAndChooseValueTwo(param, "type", "chooseType", "类型");
//          选择，告警类型
        modelClickAndChooseValueTwo(param, "alarmType", "chooseAlarmType", "告警类型");
//       点击，规则条件设置，下一步
        modelClickButton(param, "rulesConditionConfigNextStep");
        LogFunction.logInfo("第二步，规则条件设置录入完成");
//        选择，节点过滤
        modelClickAndChooseValue(param, "nodeFilter", "chooseNodeFilter", "节点过滤");
//        选择，采集系统选择
        modelClickAndChooseValue(param, "acquisitionSystemChoose", "chooseAcquisitionSystemChoose", "采集系统选择");
//        录入，内容关键字（正则表达式）
        modelInputBox(param, "contentKeyword", "contentKeywordValue", "内容关键词");
//       点击，规则条件高级设置，下一步
        modelClickButton(param, "rulesConditionAdvancedConfigNextStep");
        LogFunction.logInfo("告警合并策略，第三步，规则条件高级设置录入完成");
//         选择告警合并依据
        chooseListValue(param, "MergeGist", "MergeGistBox", "chooseMergeGistSourceNode");
//        最大合并数量,录入，4
        modelInputBox(param, "maxMergeNumber", "maxMergeNumberValue", "最大合并数量");
//        合并时间窗口(秒)，录入，3
        modelInputBox(param, "mergeTimeWindows", "mergeTimeWindowsValue", "合并时间窗口");
//          点击，保存
        modelClickButton(param, "alarmMergeConfigSave");

        LogFunction.logInfo("告警合并策略，最后一步，告警合并设置录入完成且告警合并策略创建成功");
    }

    //    复选框，根据条件，单选
    public void chooseListValue(Map<String, String> param, String radioText, String radioBox, String chooseName) {
        List<WebElement> mergeGist = l.getElements(param.get(radioText));
        List<WebElement> mergeGistB = l.getElements(param.get(radioBox));
        for (int i = 0; i < mergeGist.size(); i++) {
            WebElement e = mergeGist.get(i);
            WebElement eb = mergeGistB.get(i);
            String text = e.getText();
            boolean b = InterceptFunction.interceptState(text, param.get(chooseName));
            boolean selected = eb.isSelected();
            if (!selected && b) {
                click(param, e);
                LogFunction.logInfo("勾选：" + text);
            }
            if (selected && !b)
                click(param, e);
        }

    }

    //    复选框，清空所有选项
    public void modelCheckBoxClearAllOption(Map<String, String> param, String radioBox) {
        List<WebElement> mergeGistB = l.getElements(param.get(radioBox));
        for (int i = 0; i < mergeGistB.size(); i++) {
            WebElement eb = mergeGistB.get(i);
            boolean selected = eb.isSelected();
            if (selected)
                click(param, eb);
        }
        LogFunction.logInfo("复选框被清空");
    }

    public int numberText;
    public int numberText1;
    public int numberText2;
    public int numberText3;
    public int numberText4;

    public void verifyMergeRule(Map<String, String> param) {
//        点击，告警展示
        modelClickButton(param, param.get("alarmDisplay"));
        sleep(1000);
//        搜索框录入，Selenium
        modelInputBox(param, "alarmDisplaySearchBox", "Selenium", "搜索框");
//        点击，搜索按钮
        modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      勾选，筛选结果，通过指定名称勾选。。
        sleep(2000);
        List<WebElement> r = l.getElements(param.get("commonSelectResultAll"));
//        List<WebElement> r1 = l.getElements(param.get("commonselectResultAll9"));
        List<WebElement> r12 = l.getElements(param.get("commonselectResultAll12"));
        LogFunction.logInfo("告警条数为：" + r.size());
        AssertFunction.verifyEquals(driver, r.size(), 2);
//        LogFunction.logInfo("告警,message:"+r1.get(1).getText());
        int text = Integer.valueOf(r12.get(0).getText());
        int text1 = Integer.valueOf(r12.get(1).getText());
        LogFunction.logInfo("告警,次数分别为:" + r12.get(0).getText() + "丶" + r12.get(1).getText());
        AssertFunction.verifyEquals(driver, text, 2);
        AssertFunction.verifyEquals(driver, text1, 3);
        //        将发送的告警，解决，清空内存
        resolvedClearMemory(param);

//        搜索框录入，Aele
        modelInputBox(param, "alarmDisplaySearchBox", "Aele", "搜索框");
//        点击，搜索按钮
        modelClickButton(param, "alarmDisplaySearchButton", "搜索按钮", "");
//      勾选，筛选结果，通过指定名称勾选。。
        sleep(2000);
        List<WebElement> rr = l.getElements(param.get("commonSelectResultAll"));
//        List<WebElement> r1 = l.getElements(param.get("commonselectResultAll9"));
        List<WebElement> rr12 = l.getElements(param.get("commonselectResultAll12"));
        LogFunction.logInfo("告警条数为：" + rr.size());
        AssertFunction.verifyEquals(driver, rr.size(), 5);
//        LogFunction.logInfo("告警,message:"+r1.get(1).getText());
        int textr = Integer.valueOf(rr12.get(0).getText());
        int text1r = Integer.valueOf(rr12.get(1).getText());
        for (WebElement e : rr12) {
            int text11 = Integer.valueOf(e.getText());
            AssertFunction.verifyEquals(driver, text11, 1);
        }
        //        将发送的告警，解决，清空内存
        resolvedClearMemory(param);
    }

    //     勾选所有的告警，解决，清出内存
    public void resolvedClearMemory(Map<String, String> param) {
        //      勾选所有告警
        chooseSelectResultAll(param);
//       解决告警信息， 点击，解决
        modelClickButton(param, "alarmDisplayResolved");
        sleep(500);
        //        处理意见，选择-清出内存
        modelRadioBox(param, "ClearMemoryButton", "清出内存");
        //        点击，备注，确定
        modelClickButton(param, "affirm");
        //        弹出，提示信息
        alarmHintAndConfirm(param);

    }

    //     勾选所有的告警，通知，清出内存
    public void concentrateAlarmAffirm(Map<String, String> param) {
        //      勾选所有告警
        chooseSelectResultAll(param);
//       解决告警信息
//        点击，确认
        modelClickButton(param, "commonCentralizedAlarmConfirmation");
        //        点击，备注，确定
        modelClickButton(param, "affirm");
        //        弹出，提示信息
        alarmHintAndConfirm(param);
    }

    //    点击，告警配置+降噪策略
    public void alarmConfigurationDenoiseStrategy(Map<String, String> param) {
        Boolean area = l.getElementIsDisplay(param.get("commonAlarmConfigArea"));
        if (area == false) {
            //        点击，告警配置
            modelClickButton(param, "commonAlarmConfig");
        }
//        点击，降噪策略
        modelClickButton(param, "commonDenoiseStrategy");
    }

    //      截取（）内的值，用正则表达式
    public String extractMessageByRegular(String msg) {
        List<String> list = new ArrayList<String>();
        Pattern p = Pattern.compile("(?<=\\()\\S+(?=\\))");
        Matcher m = p.matcher(msg);
        while (m.find()) {
            list.add(m.group().substring(0, m.group().length() - 0));
        }
        String s = list.get(0).toString();
        return s;
    }

    public String getCurrentTimeAndOneMinute(Map<String, String> param,long addTimeMinute) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String f = df.format(new Date().getTime() + addTimeMinute * 60 * 1000);
        LogFunction.logInfo("当前的时间是："+f);
        return f;
    }

    //      去除多余的0
    public static String getPrettyNumber(String number) {
        return BigDecimal.valueOf(Double.parseDouble(number))
                .stripTrailingZeros().toPlainString();
    }

//    执行JavaScript
    public void executeJS(Map<String, String> param, String element,String js){
            j.executeScript(js, l.getElementIgnoreDisplay(param.get(element)));
            LogFunction.logInfo("执行脚本："+js);
    }

}
