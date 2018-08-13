package com.utils;


import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by andywu on 2018/3/19.
 */
@SuppressWarnings("ALL")
public class HttpFunction {
    private static CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    private static CloseableHttpResponse response = null;
    private static BasicCookieStore cookieStore;
    private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();//设置请求和传输超时时间


    public static HttpFunction getInstance() {
        return new HttpFunction();
    }


    //处理无参get请求，得到response内容
    public Object get(String url) {
        HttpGet get = new HttpGet(url);
        get.setConfig(requestConfig);

            String stringResultOfPost = this.getStringResultOfGet(get);
            return stringResultOfPost;

    }

    //处理有参get请求，得到response内容
    public Object get(String url, Map<String, String> params) {
        HttpGet get = this.getRequestParamsOfGet(params, url);
        get.setConfig(requestConfig);
        if (this.getJsonResultOfGet(get) != null) {
            JSONObject resultOfPost = this.getJsonResultOfGet(get);
            return resultOfPost;
        } else {
            String stringResultOfPost = this.getStringResultOfGet(get);
            return stringResultOfPost;
        }
    }

    //处理有参get请求，得到response内容
    public Object get(String url, String headerBeanName, Map<String, String> params) {
        HttpGet get = this.getRequestParamsOfGet(params, url);
        get.setConfig(requestConfig);
        this.setHeaderParamOfGet(headerBeanName, get);
        if (this.getJsonResultOfGet(get) != null) {
            JSONObject resultOfPost = this.getJsonResultOfGet(get);
            return resultOfPost;
        } else {
            String stringResultOfPost = this.getStringResultOfGet(get);
            return stringResultOfPost;
        }
    }

    //处理无参post请求，得到response内容
    public Object post(String url) {
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        LogFunction.logInfo("Post请求URL:" + url);
        String stringResultOfPost = this.getStringResultOfPost(post);
        return stringResultOfPost;
    }

    //处理String[]有参post请求，得到response内容
    public Object post(String url, String params) {
        JSONObject resultOfPost = null;
        String stringResultOfPost = null;
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        LogFunction.logInfo("Post请求URL:" + url);
        this.setRequestListParamsOfPost(params, post);
        return this.getStringResultOfPost(post);
    }

//    将String[]转换成String字符串
/*    private String listToString(String[] params){
        StringBuffer str  = new StringBuffer("{\"");
        int count = 0;
        for(String param:params){
            if (count>0){
                str.append("\",\"");
            }
            str.append(param);
            count++;
        }
        str.append("\"}");
        return  str.toString();
    }*/

    //处理有参post请求，得到response内容

    public Object post(String url, Map<String, String> params, int flag1FormsOR2Json) {
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        LogFunction.logInfo("Post请求URL:" + url);
        //调用“参数进行封装方法”,flag=1,forms请求参数，flag=2，json请求参数
        if (flag1FormsOR2Json == 1) {
            this.setRequestFormsParamsOfPost(params, post);
        } else if (flag1FormsOR2Json == 2) {
            this.setRequestJsonParamsOfPost(params, post);
        } else {
            LogFunction.logError("flag输入错误，只能输入1,2");
        }
        String stringResultOfPost = this.getStringResultOfPost(post);
        return stringResultOfPost;
    }


    //      处理有参,有header的post请求，得到response内容
    public Object post(String url, String headerBeanName, Map<String, String> params, int flag1FormsOR2Json) {
        LogFunction.logInfo("Post请求URL:" + url);
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        if (flag1FormsOR2Json == 1) {
            this.setRequestFormsParamsOfPost(params, post);
        } else if (flag1FormsOR2Json == 2) {
            this.setRequestJsonParamsOfPost(params, post);
        } else {
            LogFunction.logError("flag输入错误，只能输入1,2");
        }
        this.setHeaderParamOfPost(headerBeanName, post);
        return this.getStringResultOfPost(post);

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

//    //    设置String参数的post请求
/*    public void setRequestListParamsOfPost(String[] formsParams, HttpPost post) {
        List<BasicNameValuePair> formparams = new ArrayList<>();
        String s = this.listToString(formsParams);
        LogFunction.logInfo("value:" + formparams.toString());
        StringEntity entity = new StringEntity(s,"utf-8");
        System.out.println(entity.toString()+"213123");
        post.setEntity(entity);
    }*/

    public void setRequestListParamsOfPost(String formsParams, HttpPost post) {
        HttpEntity entity = new StringEntity(formsParams, "utf-8");
        LogFunction.logInfo("entity:" + entity);
        post.setEntity(entity);
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
    public void setHeaderParamOfGet(String beanName, HttpGet get) {
        HashMap<String, String> params = ParseXML.readXMLDocument(beanName);
        List<BasicNameValuePair> headerparams = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            get.setHeader(entry.getKey(), entry.getValue());
            LogFunction.logInfo("key：" + entry.getKey() + "  ----  value：" + entry.getValue());
        }
    }

    //    获取post请求的result
    private String getStringResultOfPost(HttpPost post) {
        String result = null;
        try {
            response = httpClient.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取响应的状态码
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            LogFunction.logInfo("请求成功statusCode = " + statusCode);
            try {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (result.length() > 500) {
                LogFunction.logInfo("resultEntity获取成功:" + result.toString().substring(0, 500));
            } else {
                LogFunction.logInfo("resultEntity获取成功:" + result.toString());
            }
        } else {
            LogFunction.logInfo("请求失败:" + "statusCode = " + statusCode);
            throw new RuntimeException("请求失败");
        }
        return result;
    }


    //    获取get请求的result
    private JSONObject getJsonResultOfGet(HttpGet get) {
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
                LogFunction.logInfo("resultEntity获取成功:" + result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            LogFunction.logInfo("请求失败" + "statusCode = " + statusCode);
        }
        JSONObject resultJson = new JSONObject(result);
        return resultJson;
    }

    private String getStringResultOfGet(HttpGet get) {
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
                LogFunction.logInfo("resultEntity获取成功:" + result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            LogFunction.logInfo("请求失败" + "statusCode = " + statusCode);
        }
        return result;
    }

    //获取cookies信息
    public List<Cookie> getCookiestore() {
        List<Cookie> cookies = cookieStore.getCookies();
        String result;
        String JSESSIONID = null;
        String cookie_user = null;
        for (int i = 0; i < cookies.size(); i++) {
            if (cookies.get(i).getName().equals("JSESSIONID")) {
                JSESSIONID = cookies.get(i).getValue();
            }
            if (cookies.get(i).getName().equals("cookie_user")) {
                cookie_user = cookies.get(i).getValue();
            }
        }
        if (cookie_user != null) {
            result = JSESSIONID;
        }
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            String value = cookie.getValue();
            LogFunction.logInfo("cookie name = " + name
                    + ";  cookie value = " + value);
        }
        return cookies;
    }
}
