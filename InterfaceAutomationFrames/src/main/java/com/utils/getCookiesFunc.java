package com.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.util.List;

/**
 * Created by andywu on 2018/3/20.
 */
public class getCookiesFunc {

    //创建CookieStore实例
    static CookieStore cookieStore=null;

    //通过CookieStore存储cookie
    public CookieStore setCookieStore(HttpResponse httpResponse){
        cookieStore=new BasicCookieStore();

        //JSESSIONID
        String setCookie=httpResponse.getFirstHeader("Set-Cookie").getValue();
        String JSESSIONID=setCookie.substring("JSESSIONID=".length(),setCookie.indexOf(";"));

        //新建一个Cookie
        BasicClientCookie cookie=new BasicClientCookie("JSESSIONID",JSESSIONID);
        cookie.setVersion(0);
        cookie.setDomain("127.0.0.1");
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
        return cookieStore;
    }
}
