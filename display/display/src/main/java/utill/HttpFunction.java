package utill;


import net.minidev.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by andywu on 2018/3/19.
 */
@SuppressWarnings("ALL")
public class HttpFunction {
    private static CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    private static CloseableHttpResponse response = null;

    private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();//设置请求和传输超时时间


    public static HttpFunction getInstance() {
        return new HttpFunction();
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

        } else {
            LogFunction.logInfo("请求失败:" + "statusCode = " + statusCode);
            throw new RuntimeException("请求失败");
        }
        return result;
    }

    public void setRequestListParamsOfPost(String formsParams, HttpPost post) {
        HttpEntity entity = new StringEntity(formsParams, "utf-8");
        //发送json数据需要设置contentType
        ((StringEntity) entity).setContentType("application/json");
        LogFunction.logInfo("params:" + formsParams);
        post.setEntity(entity);
    }
    //处理String[]有参post请求，得到response内容
    public Object post(String url, String params) {
        JSONObject resultOfPost = null;
        String stringResultOfPost = null;
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        LogFunction.logInfo("Post请求URL:--" + url);
        this.setRequestListParamsOfPost(params, post);
        return this.getStringResultOfPost(post);
    }

}
