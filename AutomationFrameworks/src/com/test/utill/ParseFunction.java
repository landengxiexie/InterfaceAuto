package com.test.utill;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.ho.yaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author d
 * @date 2018/4/13  15:16
 * @method: getXMLValue,     获取XML文件对应标签的值
 * param (XMLFilePath , XML文件路径；rootName,一级标签的签名；childName,二级标签的签名)
 */

public class ParseFunction {
    public String stringValue;
    public Map<String, Map<String, String>> map;
    public String type;
    public String value;
    public String yamlFile;
    public Document document;
    public Element element;
    public List<Element> elements;

    public void setYamlFile(String yamlFile){
        this.yamlFile=yamlFile;
    }

    public void getYamlFile(){
        File f=new File("src/locator/"+yamlFile+".yaml");
        try {
            map = Yaml.loadType(new FileInputStream(f.getAbsolutePath()), HashMap.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Map<String, String>> getYamlValue(String tagName) {
        try {
            if (getTagExist(yamlFile, tagName)) {
                Map<String, String> map1 = map.get(tagName);
                type = map1.get("type");
                value = map1.get("value");
            }
        } catch (Exception e) {
            System.out.println("文件解析异常：" + yamlFile);
        }
        return map;
    }

    public Boolean getTagExist(String filePath, String tagName) {
        boolean flag = false;
        try {
            File file = new File(filePath);
            map = Yaml.loadType(new FileInputStream(file), HashMap.class);
            if (map.containsKey(tagName)) {
                flag = true;
            }
        } catch (FileNotFoundException e) {
            System.out.println("标签不存在：" + tagName);
        }
        return flag;
    }

    public String getXMLValue(String XMLFilePath, String rootName, String childName) {
        try {
            File file = new File(XMLFilePath);
            SAXReader saxReader = new SAXReader();
            Document read = saxReader.read(file);
            Element rootElement = read.getRootElement();
            Element element = rootElement.element(rootName);
            try {
                if (childName != null) {
                    Element childElement = element.element(childName);
                    stringValue = childElement.getStringValue();
//                    System.out.println(stringValue);
                } else if (childName == null) {
                    stringValue = element.getStringValue();
//                    System.out.println(stringValue);
                }
            } catch (Exception e) {
                LogFunction.logError("传入的参数不存在:" + '\n' + "rootName--" + rootName + '\n' + "childName--" + childName);
            }

        } catch (DocumentException e) {
            LogFunction.logError("传入的参数不存在:" + '\n' + "rootName--" + rootName + '\n' + "childName--" + childName);
        }
        return stringValue;
    }

    public void readXML(String XMLFilePath) {
        File file = new File(XMLFilePath);
        SAXReader saxReader = new SAXReader();
        try {
            document = saxReader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public Element getElementObjectXML(String elementPath) {
        return (Element) document.selectSingleNode(elementPath);
    }

    public List<Element> getElementObjectsXML(String elementPath) {
        return document.selectNodes(elementPath);
    }

    public Map<String, String> getChildrenInfoByElement(Element element) {
        Map<String, String> map = new HashMap<>();
        List<Element> elements = element.elements();
/*        elements.forEach((e -> {
            map.put(e.getName(), e.getText());
        }));*/
        for (Element e : elements) {
            map.put(e.getName(), e.getText());
        }
        return map;
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

    public static void main(String[] args) {
        /*ParseFunction parseFunction = new ParseFunction();
        parseFunction.getYamlValue("./src/locator/TestBaidu.yaml","dd");
        String type = parseFunction.type;
        String value = parseFunction.value;
        System.out.println(type+"------"+value);*/
    }
}

