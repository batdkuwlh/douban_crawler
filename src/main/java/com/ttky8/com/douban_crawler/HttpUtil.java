package com.ttky8.com.douban_crawler;

import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	 
    private static final CloseableHttpClient httpclient = HttpClients.createDefault();
 
    /**
     * 发送HttpGet请求
     * @param url
     * @return
     */
    public static String GetWithCookie(String url, String ip, int port) {
 
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpget.setHeader("Host", "movie.douban.com");
        httpget.setHeader("User-Agent", "\"Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Safari/537.36\"");
        
        CookieStore cookieStore = new BasicCookieStore();
		//new BasicClientCookie对象 用来注入cookie
		//cookie.setDomain("movie.douban.com");//设置cookie的作用域
		cookieStore.addCookie(new BasicClientCookie("gr_user_id", "ef23f977-51c9-4983-92ff-cba0477c1eda"));//将cookie添加到cookieStore中
		cookieStore.addCookie(new BasicClientCookie("viewed", "25885921_6709809_6811366_10750155_6709783_1089243_1016107_3224524_25965995_3735649"));
		cookieStore.addCookie(new BasicClientCookie("bid", "v205EAKxUho"));
		cookieStore.addCookie(new BasicClientCookie("_vwo_uuid_v2", "9736679276E1838D988AE94ADBE05679|252f3512dd984088c29c6b5bea8804fa"));
		cookieStore.addCookie(new BasicClientCookie("ps", "y"));
		cookieStore.addCookie(new BasicClientCookie("ll", "118174"));
		cookieStore.addCookie(new BasicClientCookie("_pk_ref.100001.8cb4", "%5B%22%22%2C%22%22%2C1488798908%2C%22https%3A%2F%2Faccounts.douban.com%2Flogin%3Falias%3D15751866073%26redir%3Dhttps%253A%252F%252Fwww.douban.com%252Fgroup%252Fsearch%253Fstart%253D0%2526cat%253D1019%2526sort%253Drelevance%2526q%253D%2525E8%2525AF%2525B7%2525E4%2525B8%25258D%2525E8%2525A6%252581%2525E5%2525AE%2525B3%2525E7%2525BE%25259E%26source%3DNone%26error%3D1027%22%5D"));
		cookieStore.addCookie(new BasicClientCookie("__utmt", "1")); 
		cookieStore.addCookie(new BasicClientCookie("ap", "1"));
		cookieStore.addCookie(new BasicClientCookie("ue", "452756565@qq.com"));
		cookieStore.addCookie(new BasicClientCookie("dbcl2", "\"58506014:eswYQlmt2lY\""));
		cookieStore.addCookie(new BasicClientCookie("ck", "ZdMH"));
		cookieStore.addCookie(new BasicClientCookie("push_noty_num", "0"));
		cookieStore.addCookie(new BasicClientCookie("push_doumail_num", "0"));
		cookieStore.addCookie(new BasicClientCookie("_pk_id.100001.8cb4", "47b2976ee4f3a82f.1427790671.69.1488800072.1488471613."));
		cookieStore.addCookie(new BasicClientCookie("_pk_ses.100001.8cb4", "*"));
		cookieStore.addCookie(new BasicClientCookie("__utma", "30149280.1333754830.1427790902.1488467949.1488798909.74"));
		cookieStore.addCookie(new BasicClientCookie("__utmb", "30149280.56.5.1488800072003"));
		cookieStore.addCookie(new BasicClientCookie("__utmc", "30149280"));
		cookieStore.addCookie(new BasicClientCookie("__utmz", "30149280.1488467949.73.24.utmcsr=accounts.douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/login"));
		cookieStore.addCookie(new BasicClientCookie("__utmv", "30149280.4548"));
		// 创建get方法的执行对象 HttpClient4.X之后是这样创建client对象 设置cookies和header信息
		CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		//HttpHost proxy = new HttpHost(ip, port, "HTTP"); //添加代理，IP为本地IP 8888就是fillder的端口
	
		RequestConfig requestConfig = RequestConfig.custom()/*.setProxy(proxy)*/.setSocketTimeout(60000).setConnectTimeout(60000).build();
		httpget.setConfig(requestConfig);
		CloseableHttpResponse response = null;
        try {
            response = client.execute(httpget);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String result = null;
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    public static String sendGet(String url) {
        HttpGet httpget = new HttpGet(url);
		CloseableHttpClient client = HttpClients.custom().build();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
		httpget.setConfig(requestConfig);
		CloseableHttpResponse response = null;
        try {
            response = client.execute(httpget);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String result = null;
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
 
    /**
     * 发送HttpPost请求，参数为map
     * @param url
     * @param map
     * @return
     */
    public static String sendPost(String url, Map<String, String> map) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity1 = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(entity1);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }
 
    /**
     * 发送不带参数的HttpPost请求
     * @param url
     * @return
     */
    public static String sendPost(String url) {
        HttpPost httppost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(entity);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }
 
}