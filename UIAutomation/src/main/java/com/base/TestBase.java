package com.base;
import com.bean.BaseConfig;
import com.bean.Global;
import org.dom4j.Element;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import utill.ConnectPostgreSQL;
import utill.ParseFunction;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author d
 * @date 2018/5/16 001615:03
 */
@SuppressWarnings("ALL")
public class TestBase  {
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



    @DataProvider(name = "postgresqldata")
    public Object[][] providerPostgreSQL() {
        ConnectPostgreSQL c = new ConnectPostgreSQL();
        c.connect(BaseConfig.mysqlhost, BaseConfig.mysqlusername, BaseConfig.mysqlpassword);
        Object[][] query = c.query(BaseConfig.mysqlsql);
        return query;
    }

    @DataProvider(name = "xmldata",parallel = true)
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
}
