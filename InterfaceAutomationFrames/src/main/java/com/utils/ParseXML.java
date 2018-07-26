package com.utils;

import com.config.BaseConfig;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ParseXML {
    private Document document;
    private Map<String, String> commonMap;

    public void readXML(String XMLFilePath) {
        File file = new File(XMLFilePath);
        SAXReader saxReader = new SAXReader();
        try {
            document = saxReader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public String getElementText(String elementPath) {
        Element e = this.getElementObjectXML(elementPath);
        if (e != null) {
            String elementText = e.getText();
            return elementText;
        } else {
            return null;
        }

    }

    public Element getElementObjectXML(String elementPath) {
        return (Element) document.selectSingleNode(elementPath);
    }


    public Map<String, String> getChildrenInfoByElement(Element element) {
        Map<String, String> map = new HashMap<>();
        List<Element> elements = element.elements();

        for (Element e : elements) {
            map.put(e.getName(), e.getText());
        }
        return map;
    }

    public List<Element> getElementObjectsXML(String elementPath) {
        return document.selectNodes(elementPath);
    }

    private Map<String, String> getCommonMap() {
        if (commonMap == null) {
            List<Element> elements = this.getElementObjectsXML("/*/common");
            for (int i = 0; i < elements.size(); i++) {
                commonMap = this.getChildrenInfoByElement(elements.get(i));
            }
        }
        return commonMap;
    }

    public static HashMap<String,String> readXMLDocument(String beanName){

        String path=BaseConfig.path;
        HashMap<String,String> locatorMap=new HashMap<>();
        File file=new File(path);
        //下面可以加一个判断file文件不存在的异常处理
        if (!file.exists()){
            try {
                throw new IOException("Can't find"+ path);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        SAXReader saxReader = new SAXReader();
        Document document;

        try {
            document=saxReader.read(file);
            for (Iterator iter = document.getRootElement().elementIterator(); iter.hasNext();){
                Element element= (Element) iter.next();

                if (element.attributeValue("beanName").equalsIgnoreCase(beanName)){

//                    LogFunction.logInfo("BeanName:"+element.attributeValue("beanName"));

                    for (Iterator iter1=element.elementIterator();iter1.hasNext();){
                        Element e2= (Element) iter1.next();
                        String elementName=e2.attributeValue("name");
                        String elementValue=e2.attributeValue("value");
//                        LogFunction.logInfo(element+","+elementValue);
                        locatorMap.put(elementName,elementValue);
                    }
                    return locatorMap;
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return locatorMap;
    }

    public static void main(String[] args) {
        HashMap<String,String> locatorMap=ParseXML.readXMLDocument("params");
        for(Map.Entry<String,String> entry:locatorMap.entrySet()){
            System.out.println("map:"+entry.getKey()+","+entry.getValue());
        }
    }
}