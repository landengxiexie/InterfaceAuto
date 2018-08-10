package com.memorandom;

import com.config.VicubeCasesConfig;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;

public class Test0002 {
    @Test
    public void aaa() throws IOException {


        CloseableHttpClient httpclient = HttpClientBuilder.create().build();


//        HttpPost post = new HttpPost(VicubeCasesConfig.findByNameNotBuiltInAttrURL);
//        HttpEntity entity = new StringEntity(VicubeCasesConfig.findByNameNotBuiltInAttrParams, "utf-8");
//        post.setEntity(entity);
//        CloseableHttpResponse execute = httpclient.execute(post);
//        Object s = EntityUtils.toString(execute.getEntity(),"utf-8");
//        System.out.println(s.toString());


    }
}

