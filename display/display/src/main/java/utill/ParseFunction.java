package utill;

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


public class ParseFunction {
    public Map<String, Map<String, String>> map;
    public String type;
    public String yamlFile;
    public Document document;
    public Element element;

    public void setYamlFile(String yamlFile){
        this.yamlFile=yamlFile;
    }

    public void getYamlFile(){
        File f=new File("src/main/java/locator/"+yamlFile+".yaml");
        try {
            map = Yaml.loadType(new FileInputStream(f.getAbsolutePath()), HashMap.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
}

