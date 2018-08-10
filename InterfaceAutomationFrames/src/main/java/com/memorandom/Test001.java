//package com.cases;
//
//import com.config.VicubeCasesConfig;
//import com.utils.AssertFunction;
//import com.utils.HttpFunction;
//import com.utils.ParseXML;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.CookieStore;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.cookie.Cookie;
//import org.apache.http.impl.client.BasicCookieStore;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.json.JSONObject;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import java.util.*;
//
//@SuppressWarnings("ALL")
//public class Test001 {
//    private static HttpFunction httpFunction = HttpFunction.getInstance();
//
//    @Test
//    public void a() {
//        HashMap<String, String> params = ParseXML.readXMLDocument(VicubeCasesConfig.beanName);
//        JSONObject post = httpFunction.post(VicubeCasesConfig.url,params,2);
//        System.out.println(post);
//    }
//    @Test
//    public void d1() {
//        HashMap<String, String> params = ParseXML.readXMLDocument(VicubeCasesConfig.beanName4);
//        JSONObject jsonObject = httpFunction.get(VicubeCasesConfig.url4, params);
//        System.out.println(jsonObject);
//        AssertFunction.verifyEquals("恭喜获得cookies信息",jsonObject.get("msg"),"-");
//    }
//    @Test
//    public void d2() {
//        HashMap<String, String> params = ParseXML.readXMLDocument(VicubeCasesConfig.beanName5);
//        JSONObject jsonObject = httpFunction.get(VicubeCasesConfig.url5, params);
//        System.out.println(jsonObject);
//        AssertFunction.verifyEquals("this is  get  cookies request",jsonObject.get("msg"),"-");
//    }
//    @Test(dependsOnMethods = "d2")
//    public void t1() {
//        JSONObject post = httpFunction.post(VicubeCasesConfig.url);
//        System.out.println(post);
//        AssertFunction.verifyEquals("success",post.get("asd"),"-");
//        AssertFunction.verifyEquals("1",post.get("status"),"-");
//    }
//    @Test(dependsOnMethods = "d1")
//    public void t2() {
//        HashMap<String, String> params = ParseXML.readXMLDocument(VicubeCasesConfig.beanName2);
//        JSONObject post = httpFunction.post(VicubeCasesConfig.url2,params,2);
//        System.out.println(post);
//        AssertFunction.verifyEquals("success",post.get("asd"),"-");
//        AssertFunction.verifyEquals("2",post.get("status"),"-");
//    }
//    @Test
//    public void t3() {
//        HashMap<String, String> params = ParseXML.readXMLDocument(VicubeCasesConfig.beanName3);
//        JSONObject post = httpFunction.post(VicubeCasesConfig.url3,"header3",params,2);
//        System.out.println(post);
//        AssertFunction.verifyEquals("success",post.get("asd"),"-");
//        AssertFunction.verifyEquals("3",post.get("status"),"-");
//    }
//    @Test
//    public void aa() {
//        HttpFunction httpFunction = HttpFunction.getInstance();
//        HashMap<String, String> params = ParseXML.readXMLDocument("params1");
//        JSONObject header = httpFunction.post(VicubeCasesConfig.url, "header", params, 1);
//        System.out.println(header);
//    }
//
//    @Test
//    public void cc() {
//        HttpFunction httpFunction = HttpFunction.getInstance();
//        HashMap<String, String> params = ParseXML.readXMLDocument("params");
//        JSONObject jsonObject = httpFunction.get(VicubeCasesConfig.url, "header", params);
//        System.out.println(jsonObject);
//        Assert.assertEquals("我有回来了", jsonObject.get("msg"));
//        Assert.assertEquals("哈哈", jsonObject.get("haha"));
//
////        List<Cookie> cookies = httpFunction.getCookies();
////        System.out.println(cookies.get(0).getName());
////        System.out.println(cookies.get(0).getValue());
////        Assert.assertEquals(cookies.get(0).getName(), "quit");
//
//
//    }
//
//
//    @Test
//    public void d() {
//        HashMap<String, String> map = ParseXML.readXMLDocument("params");
//
//        CloseableHttpClient httpClient = null;
//        HttpPost httpPost = null;
//        String result = null;
//        try {
//            CookieStore cookieStore = new BasicCookieStore();
//            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
//            httpPost = new HttpPost("http://localhost:8088/postdemo3");
//            List<NameValuePair> list = new ArrayList<>();
//            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<String, String> elem = iterator.next();
//                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
//            }
//            if (list.size() > 0) {
//                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
//                httpPost.setEntity(entity);
//            }
//            httpClient.execute(httpPost);
//            String JSESSIONID = null;
//            String cookie_user = null;
//            List<Cookie> cookies = cookieStore.getCookies();
//            for (int i = 0; i < cookies.size(); i++) {
//                if (cookies.get(i).getName().equals("JSESSIONID")) {
//                    JSESSIONID = cookies.get(i).getValue();
//                }
//                if (cookies.get(i).getName().equals("cookie_user")) {
//                    cookie_user = cookies.get(i).getValue();
//                }
//            }
//            if (cookie_user != null) {
//                result = JSESSIONID;
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        System.out.println(result);
//
//    }
//
//}
//
//
