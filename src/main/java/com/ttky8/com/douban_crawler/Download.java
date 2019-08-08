package com.ttky8.com.douban_crawler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Download {

	public static boolean downloadFile(String urlPath, String savePath) {
        try {
			URL url = new URL(urlPath);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			int code = httpURLConnection.getResponseCode();
			if (code == 200) {
				//System.out.println("发送下载请求");
			}
			InputStream inputStream = httpURLConnection.getInputStream();
			File file = new File(savePath);
			File dir = new File(file.getParent());
			dir.mkdirs();
			OutputStream out = new FileOutputStream(file);
			int size = 0;
			byte[] buf = new byte[1024];
			while ((size = inputStream.read(buf)) != -1) {
				out.write(buf, 0, size);
			}
			inputStream.close();
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
}
