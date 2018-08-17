package com.test.base;
import com.test.bean.BaseConfig;
import com.test.bean.Global;
import com.test.utill.ConnectMysql;
import com.test.utill.ConnectPostgreSQL;
import com.test.utill.ExcelFunction;
import com.test.utill.ParseFunction;
import org.dom4j.Element;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author d
 * @date 2018/5/16 001615:03
 */
@SuppressWarnings("ALL")
public class TestBase {
    private ParseFunction pf;
    public Map<String, String> commonMap;
    private WebDriver driver;


    private void initialPF() {
        if (pf == null) {
            pf = new ParseFunction();
            pf.readXML("test-data/" + this.getClass().getSimpleName() + ".xml");
        }
    }

    private void getCommonMap() {
        if (commonMap == null) {
            List<Element> elements = pf.getElementObjectsXML("/*/common");
            for (int i = 0; i < elements.size(); i++) {
                commonMap = pf.getChildrenInfoByElement(elements.get(i));
            }
        }
    }


    @DataProvider(name = "exceldata")
    public Object[][] provideExcel() {
        ExcelFunction e = new ExcelFunction(BaseConfig.filePath);
        Object[][] objectCells = e.getObjectCells();
        return objectCells;
    }


    @DataProvider(name = "mysqldata")
    public Object[][] providerMySQL() {
        ConnectMysql c = new ConnectMysql();
        c.connect(BaseConfig.mysqlhost, BaseConfig.mysqlusername, BaseConfig.mysqlpassword);
        Object[][] query = c.query(BaseConfig.mysqlsql);
        return query;
    }

    @DataProvider(name = "postgresqldata")
    public Object[][] providerPostgreSQL() {
        ConnectPostgreSQL c = new ConnectPostgreSQL();
        c.connect(BaseConfig.mysqlhost, BaseConfig.mysqlusername, BaseConfig.mysqlpassword);
        Object[][] query = c.query(BaseConfig.mysqlsql);
        return query;
    }

    @DataProvider(name = "xmldata")
    public Object[][] providerMethod(Method method) {
        this.initialPF();
        this.getCommonMap();
        String methodName = method.getName();
        List<Element> elements = pf.getElementObjectsXML("/*/" + methodName);
        Object[][] objects = new Object[elements.size()][];
        for (int i = 0; i < elements.size(); i++) {
//            Object[] temp = new Object[]{this.getMergeMapData(pf.getChildrenInfoByElement(elements.get(i)),commonMap)};
            Map<String, String> mergeCommon = this.getMergeMapData(pf.getChildrenInfoByElement(elements.get(i)), commonMap);
            Map<String, String> mergeGlobal = this.getMergeMapData(mergeCommon, Global.global);
            Object[] temp = new Object[]{mergeGlobal};
            objects[i] = temp;
        }
        return objects;
    }

    public Map<String, String> getMergeMapData(Map<String, String> map1, Map<String, String> map2) {
        map2.forEach((k, v) -> {
            map1.put(k, v);
        });
        return map1;
    }

    public void goTo(String url){
        driver.get(url);
    }

    public void switchWindownByIndex(int index,WebDriver driver){
        this.driver=driver;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Object[] handles=driver.getWindowHandles().toArray();
        if (index>handles.length){
            return;
        }
        driver.switchTo().window(handles[index].toString());

    }
   /* @BeforeClass
    public void initialDriver() {
        SeleniumDriver seleniumDriver = new SeleniumDriver();
        driver = seleniumDriver.getDriver();
    }

    @AfterClass
    public void closeDriver(){
        if (driver!=null){
            driver.close();
        }
        if (driver!=null){
            driver.quit();
        }
    }*/
}
