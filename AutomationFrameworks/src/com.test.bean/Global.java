package com.test.bean;
import com.test.utill.ConnectMysql;
import com.test.utill.ParseFunction;
import org.dom4j.Element;

import java.util.List;
import java.util.Map;

/**
 * @author d
 * @date 2018/5/17 001711:01
 */
public class Global {
    public static Map<String,String> global;
    static {
        ParseFunction pf=new ParseFunction();
        pf.readXML("test-data/"+ BaseConfig.global+".xml");
        List<Element> elements = pf.getElementObjectsXML("/*");
        for (int i = 0; i <elements.size() ; i++) {
            global = pf.getChildrenInfoByElement(elements.get(i));
        }
    }
}