//package com.cases;
//
//import bsh.util.BshCanvas;
//import com.config.VicubeCasesConfig;
//import com.utils.AssertFunction;
//import com.utils.HttpCookiesFunction;
//import com.utils.HttpFunction;
//import com.utils.ParseXML;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.json.JSONObject;
//import org.jsoup.Connection;
//import org.testng.annotations.Test;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//@SuppressWarnings("ALL")
//public class test {
//    private static HttpCookiesFunction httpFunction = HttpCookiesFunction.getInstance();
//    private static HttpFunction httpFunction1 = HttpFunction.getInstance();
//
//    private static CloseableHttpClient httpClient1=null;
//    private static CloseableHttpClient httpClient2=null;
//
//    @Test
//    public void d1() {
//        HashMap<String, String> params = ParseXML.readXMLDocument(VicubeCasesConfig.beanName4);
//         httpClient1 = httpFunction.getHttpClient();
//        JSONObject jsonObject = httpFunction.get(httpClient1,VicubeCasesConfig.url4, params);
//        System.out.println(jsonObject);
//        AssertFunction.verifyEquals("恭喜获得cookies信息",jsonObject.get("msg"),"-");
//    }
//    @Test(dependsOnMethods = "d1")
//    public void t2() {
//        HashMap<String, String> params = ParseXML.readXMLDocument(VicubeCasesConfig.beanName2);
//        JSONObject post = httpFunction.post(httpClient1,VicubeCasesConfig.url2,params,2);
//        System.out.println(post);
//        AssertFunction.verifyEquals("success",post.get("asd"),"-");
//        AssertFunction.verifyEquals("2",post.get("status"),"-");
//    }
//    @Test
//    public void d2() {
//        HashMap<String, String> params = ParseXML.readXMLDocument(VicubeCasesConfig.beanName5);
//         httpClient2 = httpFunction.getHttpClient();
//        JSONObject jsonObject = httpFunction.get(httpClient2,VicubeCasesConfig.url5, params);
//        System.out.println(jsonObject);
//        AssertFunction.verifyEquals("this is  get  cookies request",jsonObject.get("msg"),"-");
//    }
//    @Test(dependsOnMethods = "d2")
//    public void t1() {
//        JSONObject post = httpFunction.post(httpClient2,VicubeCasesConfig.url);
//        System.out.println(post);
//        AssertFunction.verifyEquals("success",post.get("asd"),"-");
//        AssertFunction.verifyEquals("1",post.get("status"),"-");
//    }
//    @Test
//    public void t3() {
//        HashMap<String, String> params = ParseXML.readXMLDocument(VicubeCasesConfig.beanName3);
//        JSONObject post = httpFunction1.post(VicubeCasesConfig.url3,"header3",params,2);
//        System.out.println(post);
//        AssertFunction.verifyEquals("success",post.get("asd"),"-");
//        AssertFunction.verifyEquals("3",post.get("status"),"-");
//    }
//
//    @Test
//    public void zz() {
//        HashMap<String, String> params = ParseXML.readXMLDocument(VicubeCasesConfig.beanName6);
//        JSONObject post = httpFunction1.post(VicubeCasesConfig.url6,VicubeCasesConfig.beanName66,params,2);
//        System.out.println(post);
//    }
//
//    @Test
//    public void www() {
//        HashMap<String, String> params = ParseXML.readXMLDocument(VicubeCasesConfig.beanName7);
//        JSONObject post = httpFunction1.post(VicubeCasesConfig.url7,VicubeCasesConfig.beanName77);
//        System.out.println(post);
//    }
//}
