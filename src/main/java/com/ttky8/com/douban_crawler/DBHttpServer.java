package com.ttky8.com.douban_crawler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.Headers; 


@SuppressWarnings("restriction")
public class DBHttpServer {
	public static void startServer() {
        try {
			HttpServer httpServer = HttpServer.create(new InetSocketAddress(8088), 0);
			httpServer.createContext("/g", new GetDouBanHandler());
			httpServer.start();
			System.out.println("服务已启用...");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}


@SuppressWarnings("restriction")
class GetDouBanHandler implements HttpHandler {
	@Override
    public void handle(HttpExchange exchange) throws IOException {
		new Thread(new Runnable() {
            @Override
            public void run() {
            	try {
					OutputStream os = exchange.getResponseBody();
					String queryString = exchange.getRequestURI().getQuery();
					Map<String, String> q = formData(queryString);
					Headers responseHeaders = exchange.getResponseHeaders(); 
					responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
					exchange.sendResponseHeaders(200, 0);
					
					if (null != q.get("id")) {
						Map<String, Object> movie = DataBase.get(q.get("id"));
						if (null != movie) {
							os.write(JSONObject.toJSONString(movie).getBytes("UTF-8"));
						} else {
							if (new Run().start(q.get("id"))) {
								movie = DataBase.get(q.get("id"));
								os.write(JSONObject.toJSONString(movie).getBytes("UTF-8"));
							} else {
								Map<String, String> map = new HashMap<String, String>();
								map.put("code", "0");
								map.put("msg", "豆瓣ID不存在");
								os.write(JSONObject.toJSONString(map).getBytes("UTF-8"));
							}
						}

					} else {
						Map<String, String> map = new HashMap<String, String>();
						map.put("code", "0");
						map.put("msg", "豆瓣ID不能为空");
						System.out.println(JSONObject.toJSONString(map));
						os.write(JSONObject.toJSONString(map).getBytes("UTF-8"));
					}
					os.flush();
					os.close();
					exchange.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
		}).start();
    }
	
	public static Map<String,String> formData(String requestUri) {
        Map<String,String> result = new HashMap<>();
        if(requestUri== null || requestUri.trim().length() == 0) {
            return result;
        }
        final String[] items = requestUri.split("&");
        Arrays.stream(items).forEach(item ->{
            final String[] keyAndVal = item.split("=");
            if( keyAndVal.length == 2) {
                try{
                    final String key = URLDecoder.decode( keyAndVal[0],"utf8");
                    final String val = URLDecoder.decode( keyAndVal[1],"utf8");
                    result.put(key,val);
                }catch (UnsupportedEncodingException e) {}
            }
        });
        return result;
    }
}