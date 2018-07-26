package com.utils;


import org.apache.http.Consts;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andywu on 2018/3/19.
 */
@SuppressWarnings("ALL")
public class HttpCookiesFunction {
    private static CloseableHttpResponse response;



    public static HttpCookiesFunction getInstance() {
        return new HttpCookiesFunction();
    }


    //处理无参get请求，得到response内容
    public JSONObject get(CloseableHttpClient httpClient,String url) {
        HttpGet get = new HttpGet(url);
        JSONObject resultOfGet = this.getResultOfGet(httpClient,get);
        return resultOfGet;
    }

    //处理有参get请求，得到response内容
    public JSONObject get(CloseableHttpClient httpClient,String url, Map<String, String> params) {
        HttpGet get = this.getRequestParamsOfGet(params, url);
        JSONObject resultOfGet = this.getResultOfGet(httpClient,get);
        return resultOfGet;
    }

    //处理有参get请求，得到response内容
    public JSONObject get(CloseableHttpClient httpClient,String url, String headerBeanName, Map<String, String> params) {
        HttpGet get = this.getRequestParamsOfGet(params, url);
        this.setHeaderParamOfPost(headerBeanName, get);
        JSONObject resultOfGet = this.getResultOfGet(httpClient,get);
        return resultOfGet;
    }

    //处理无参post请求，得到response内容
    public JSONObject post(CloseableHttpClient httpClient,String url) {
        HttpPost post = new HttpPost(url);
        LogFunction.logInfo("Post请求URL:"+url);
        JSONObject resultOfPost = this.getResultOfPost(httpClient,post);
        return resultOfPost;
    }

    //处理有参post请求，得到response内容
    public JSONObject post(CloseableHttpClient httpClient,String url, Map<String, String> params, int flag1FormsOR2Json) {
        HttpPost post = new HttpPost(url);
        LogFunction.logInfo("Post请求URL:"+url);
        //调用“参数进行封装方法”,flag=1,forms请求参数，flag=2，json请求参数
        if (flag1FormsOR2Json == 1) {
            this.setRequestFormsParamsOfPost(params, post);
        } else if (flag1FormsOR2Json == 2) {
            this.setRequestJsonParamsOfPost(params, post);
        } else {
            LogFunction.logError("flag输入错误，只能输入1,2");
        }
        JSONObject resultOfPost = this.getResultOfPost(httpClient,post);
        return resultOfPost;
    }

    //      处理有参,有header的post请求，得到response内容
    public JSONObject post(CloseableHttpClient httpClient,String url, String headerBeanName, Map<String, String> params, int flag1FormsOR2Json) {
        HttpPost post = new HttpPost(url);
        LogFunction.logInfo("Post请求URL:"+url);
        if (flag1FormsOR2Json == 1) {
            this.setRequestFormsParamsOfPost(params, post);
        } else if (flag1FormsOR2Json == 2) {
            this.setRequestJsonParamsOfPost(params, post);
        } else {
            LogFunction.logError("flag输入错误，只能输入1,2");
        }
        this.setHeaderParamOfPost(headerBeanName, post);
        JSONObject resultOfPost = this.getResultOfPost(httpClient,post);
        return resultOfPost;
    }

    //    设置JSON参数的post请求
    public void setRequestJsonParamsOfPost(Map<String, String> params, HttpPost post) {
        JSONObject param = new JSONObject();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            param.put(entry.getKey(), entry.getValue());
            LogFunction.logInfo("key：" + entry.getKey() + "  ----  value：" + entry.getValue());
        }
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);
    }

    //    设置Forms参数的post请求
    public void setRequestFormsParamsOfPost(Map<String, String> formsParams, HttpPost post) {
        List<BasicNameValuePair> formparams = new ArrayList<>();
        for (Map.Entry<String, String> entry : formsParams.entrySet()) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            LogFunction.logInfo("key:" + entry.getKey() + "value:" + entry.getValue());
        }
        UrlEncodedFormEntity entitys = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        post.setEntity(entitys);
    }

    //    设置参数的get请求
    public HttpGet getRequestParamsOfGet(Map<String, String> formsParams, String url) {
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
//        第一种添加参数的形式
        for (Map.Entry<String, String> entry : formsParams.entrySet())
            uriBuilder.addParameter(entry.getKey(), entry.getValue());
        LogFunction.logInfo("Get请求URL:" + uriBuilder);

//        第二种添加参数的形式
//        List<BasicNameValuePair> list = new LinkedList<>();
//        BasicNameValuePair param1 = new BasicNameValuePair("name", "root");
//        BasicNameValuePair param2 = new BasicNameValuePair("password", "123456");
//        list.add(param1);
//        list.add(param2);
//        uriBuilder.setParameters((NameValuePair) list);

        // 根据带参数的URI对象构建GET请求对象
        HttpGet get = null;
        try {
            get = new HttpGet(uriBuilder.build());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return get;
    }

    //    设置请求头的post请求
    public void setHeaderParamOfPost(String beanName, HttpPost post) {
        HashMap<String, String> params = ParseXML.readXMLDocument(beanName);
        List<BasicNameValuePair> headerparams = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            post.setHeader(entry.getKey(), entry.getValue());
            LogFunction.logInfo("key：" + entry.getKey() + "  ----  value：" + entry.getValue());
        }
    }

    //    设置请求头的post请求
    public void setHeaderParamOfPost(String beanName, HttpGet get) {
        HashMap<String, String> params = ParseXML.readXMLDocument(beanName);
        List<BasicNameValuePair> headerparams = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            get.setHeader(entry.getKey(), entry.getValue());
            LogFunction.logInfo("key：" + entry.getKey() + "  ----  value：" + entry.getValue());
        }
    }

    //    获取post请求的result
    private JSONObject getResultOfPost(CloseableHttpClient httpClient,HttpPost post) {
        try {
            response = httpClient.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取响应的状态码
        int statusCode = response.getStatusLine().getStatusCode();
        LogFunction.logInfo("statusCode = " + statusCode);
        String result = null;
        if (statusCode == 200) {
            try {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            LogFunction.logInfo("请求失败" + "statusCode = " + statusCode);
        }
        JSONObject resultJson = new JSONObject(result);
        return resultJson;
    }

    //    获取get请求的result
    private JSONObject getResultOfGet(CloseableHttpClient httpClient,HttpGet get) {
        try {
            response = httpClient.execute(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int statusCode = response.getStatusLine().getStatusCode();
        LogFunction.logInfo("statusCode = " + statusCode);
        String result = null;
        if (statusCode == 200) {
            try {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            LogFunction.logInfo("请求失败" + "statusCode = " + statusCode);
        }
        JSONObject resultJson = new JSONObject(result);
        return resultJson;
//        //获取到结果值
//        String success = (String) resultJson.get("huhansan");
//        String status = (String) resultJson.get("status");
//        //具体的判断返回结果的值
//        Assert.assertEquals("success",success);
//        Assert.assertEquals("1",status);
    }

    public CloseableHttpClient getHttpClient() {
        BasicCookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        return httpClient;
    }
}
