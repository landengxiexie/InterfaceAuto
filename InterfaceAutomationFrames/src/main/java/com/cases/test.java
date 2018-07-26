package com.cases;

import com.config.BaseConfig;
import com.utils.AssertFunction;
import com.utils.HttpCookiesFunction;
import com.utils.HttpFunction;
import com.utils.ParseXML;
import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;

@SuppressWarnings("ALL")
public class test {
    private static HttpCookiesFunction httpFunction = HttpCookiesFunction.getInstance();
    private static HttpFunction httpFunction1 = HttpFunction.getInstance();

    private static CloseableHttpClient httpClient1=null;
    private static CloseableHttpClient httpClient2=null;

    @Test
    public void d1() {
        HashMap<String, String> params = ParseXML.readXMLDocument(BaseConfig.beanName4);
         httpClient1 = httpFunction.getHttpClient();
        JSONObject jsonObject = httpFunction.get(httpClient1,BaseConfig.url4, params);
        System.out.println(jsonObject);
        AssertFunction.verifyEquals("恭喜获得cookies信息",jsonObject.get("msg"),"-");
    }
    @Test(dependsOnMethods = "d1")
    public void t2() {
        HashMap<String, String> params = ParseXML.readXMLDocument(BaseConfig.beanName2);
        JSONObject post = httpFunction.post(httpClient1,BaseConfig.url2,params,2);
        System.out.println(post);
        AssertFunction.verifyEquals("success",post.get("asd"),"-");
        AssertFunction.verifyEquals("2",post.get("status"),"-");
    }
    @Test
    public void d2() {
        HashMap<String, String> params = ParseXML.readXMLDocument(BaseConfig.beanName5);
         httpClient2 = httpFunction.getHttpClient();
        JSONObject jsonObject = httpFunction.get(httpClient2,BaseConfig.url5, params);
        System.out.println(jsonObject);
        AssertFunction.verifyEquals("this is  get  cookies request",jsonObject.get("msg"),"-");
    }
    @Test(dependsOnMethods = "d2")
    public void t1() {
        JSONObject post = httpFunction.post(httpClient2,BaseConfig.url);
        System.out.println(post);
        AssertFunction.verifyEquals("success",post.get("asd"),"-");
        AssertFunction.verifyEquals("1",post.get("status"),"-");
    }
    @Test
    public void t3() {
        HashMap<String, String> params = ParseXML.readXMLDocument(BaseConfig.beanName3);
        JSONObject post = httpFunction1.post(BaseConfig.url3,"header3",params,2);
        System.out.println(post);
        AssertFunction.verifyEquals("success",post.get("asd"),"-");
        AssertFunction.verifyEquals("3",post.get("status"),"-");
    }

    @Test
    public void zz() {
        HashMap<String, String> params = ParseXML.readXMLDocument(BaseConfig.beanName6);
        JSONObject post = httpFunction1.post(BaseConfig.url6,BaseConfig.beanName66,params,2);
        System.out.println(post);

    }
}
