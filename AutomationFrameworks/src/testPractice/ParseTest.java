package testPractice;
import com.test.utill.LogFunction;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author d
 * @date 2018/5/10 001017:51
 */
public class ParseTest {
    private Document document;
    private String filePath;

    public ParseTest(String filePath) {
        this.filePath = filePath;
        this.read(this.filePath);
    }

    public void read(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            SAXReader saxReader = new SAXReader();
            try {
                document = saxReader.read(file);
            } catch (DocumentException e) {
                LogFunction.logError("文件加载异常：" + filePath);
            }
        } else {
            LogFunction.logError("文件不存在：" + filePath);
        }
    }

    public Element getElementObject(String elementPath) {
        return (Element) document.selectSingleNode(elementPath);
    }

    public String getElementText(String elementPath) {
        Element element = this.getElementObject(elementPath);
        if (element != null) {
            return element.getTextTrim();
        } else {
            LogFunction.logError("元素不存在-------" + element);
        }
        return element.getText();
    }

    public List<Element> getElementObjects(String elementPath) {
        return document.selectNodes(elementPath);
    }

    public Map<String, String> getChildInfoByElement(Element element) {
        Map<String, String> map = new HashMap<String, String>();
        List<Element> elements = element.elements();
        for (Element e : elements) {
            map.put(e.getName(), e.getText());
        }
        return map;
    }

    public boolean isExist(String elementPath) {
        boolean flag = false;
        Element element = this.getElementObject(elementPath);
        if (element != null) {
            flag = true;
        }
        return flag;
    }
    public static void main(String[] args) {
        ParseTest parseTest = new ParseTest("src/config/testBaidu.xml");
        List<Element> elementObjects = parseTest.getElementObjects("/data/testUI");
        Object[][] objects=new Object[elementObjects.size()][];
        for (int i = 0; i < elementObjects.size(); i++) {
            Object[] temp=new Object[]{parseTest.getChildInfoByElement(elementObjects.get(i))};
            objects[i]=temp;
        }
        Object o=objects[0][0];
        System.out.println(((Map<String,String>) o).get("browser"));
    }
}
