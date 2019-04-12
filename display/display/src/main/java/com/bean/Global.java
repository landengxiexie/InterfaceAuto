package com.bean;
import org.dom4j.Element;
import utill.ParseFunction;

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
        pf.readXML("./src/test-data/"+ com.bean.BaseConfig.global+".xml");
        List<Element> elements = pf.getElementObjectsXML("/*");
        for (int i = 0; i <elements.size() ; i++) {
            global = pf.getChildrenInfoByElement(elements.get(i));
        }
    }
}
