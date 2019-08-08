package com.ttky8.com.douban_crawler;

import java.net.URLEncoder;
import java.sql.Connection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONObject;

public class Run  {
	static int start = 0;
	static String saveDir = "E:\\douban";
    static String ip;
    static int port;
	public static void SetGlobalProxy() {
		String result = HttpUtil.sendGet("http://webapi.http.zhimacangku.com/getip?num=1&type=2&pro=&city=0&yys=100017&port=1&time=1&ts=0&ys=0&cs=0&lb=1&sb=0&pb=4&mr=2&regions=");
		System.out.println(result);
		JSONObject ipport = JSONObject.parseObject(result).getJSONArray("data").getJSONObject(0);
		ip = ipport.getString("ip");     
		port = ipport.getIntValue("port");     
		//全局代理设置
		//System.getProperties().put("proxySet","false");   
		//System.getProperties().put("socksProxyHost",proxyHost);     
		//System.getProperties().put("socksProxyPort",proxyPort);  
		//System.getProperties().put("proxySet","true");  
	}

	
	public boolean start(String doubanId) {
		//SetGlobalProxy();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			MovieModel movie = GetMovieById(doubanId);
			if (movie == null) {
				return false;
			}
			DataBase.saveMovie(movie);
			//保存封面图片到本地
			//String fileName = new File(movie.getCover()).getName();
	        //String suffix = fileName.substring(fileName.lastIndexOf("."));
			//String imgPath = saveDir + File.separator + doubanId + suffix;
			//if (!new File(imgPath).exists()) {
				//Download.downloadFile(movie.getCover(), imgPath);
			//}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != conn) {
				 conn.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return true;
	}
	
	public static String stripNonCharCodepoints(String input) {
		try {
			return URLEncoder.encode(input, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static void main_expired(String[] args) {
		SetGlobalProxy();
		while(true) {
			String url = "https://movie.douban.com/j/new_search_subjects?sort=T&range=0,10&tags=电影&start=" + start;
			System.out.println(url);
			String result = HttpUtil.GetWithCookie(url, ip, port);
			MovieList list = MovieList.fromJson(result);
			if (list.list.size() == 0) {
				break;
			}
			DataBase.InsertMovies(list.list);
			start = start + 10;
		}
	}
	
	public static MovieModel GetMovieById(String doubanId) {
		String result = HttpUtil.GetWithCookie("https://movie.douban.com/subject/" + doubanId, ip, port);
		
		Document doc = Jsoup.parse(result);
		Elements eles = doc.select("div[id=info]");
		if (eles.size() == 0) {
			return null;
		}
		MovieModel model = new MovieModel();
		String[] lines = eles.toString().split("\n");
		
		for (int i = 0; i < lines.length; i++) {
			model.setDoubanId(doubanId);
			String l = Jsoup.parse(lines[i]).text();
			if (l.indexOf("导演") != -1) {
				String[] c = l.split(":");
				if (c.length == 2) {
					model.setDirectors(c[1].trim().replaceAll("/", ","));
				}
			} else if (l.indexOf("编剧") != -1) {
				String[] c = l.split(":");
				if (c.length == 2) {
					model.setScriptwriter(c[1].trim().replaceAll("/", ","));
				}
			} else if (l.indexOf("主演") != -1) {
				String[] c = l.split(":");
				if (c.length == 2) {
					model.setActors(c[1].trim().replaceAll("/", ","));
				}
			} else if (l.indexOf("语言") != -1) {
				String[] c = l.split(":");
				if (c.length == 2) {
					model.setLang(c[1].trim().replaceAll("/", ","));
				}
			} else if (l.indexOf("制片国家/地区") != -1) {
				String[] c = l.split(":");
				if (c.length == 2) {
					model.setRegion(c[1].trim().replaceAll("/", ","));
				}
			} else if (l.indexOf("又名") != -1) {
				String[] c = l.split(":");
				if (c.length == 2) {
					model.setAlias(c[1].trim().replaceAll("/", ","));
				}
			} else if (l.indexOf("集数") != -1) {
				String[] c = l.split(":");
				if (c.length == 2) {
					model.setEpisodes(c[1].trim().replaceAll("/", ","));
				}
			} else if (l.indexOf("单集片长") != -1) {
				String[] c = l.split(":");
				if (c.length == 2) {
					model.setSingleDuration(c[1].trim().replaceAll("/", ","));
				}
			} 
		}
		model.setType(eles.select("span[property='v:genre']").text().replaceAll(" ", ","));
		model.setDuration(eles.select("span[property='v:runtime']").text().replaceAll(" ", ","));
		model.setPublishDate(eles.select("span[property='v:initialReleaseDate']").text().replaceAll(" ", ","));
		model.setTitle(doc.select("span[property='v:itemreviewed']").text());
		model.setYear(doc.select(".year").text().replaceAll("[()]", ""));
		model.setScore(doc.select("strong[property='v:average']").text());
		model.setVotes(doc.select("span[property='v:votes']").text());
		model.setCover(doc.select("img[rel='v:image']").attr("src"));
		model.setSummary(doc.select("span[property='v:summary']").text());
		Elements comments = doc.select(".comment-item .comment");
		//获取头三条评论
		for (int i = 0; i < comments.size(); i++) {
			Elements shorts = comments.get(i).select("span.short");
			
			if (shorts.size() > 0) {
				if (i == 0) {
					model.setComment1(stripNonCharCodepoints(shorts.get(0).text().trim()));
				} else if (i == 1) {
					model.setComment2(stripNonCharCodepoints(shorts.get(0).text().trim()));
				} else if (i == 2) {
					model.setComment3(stripNonCharCodepoints(shorts.get(0).text().trim()));
				} 
			} else {
				Elements fulls = comments.get(i).select("span.full");
				if (fulls.size() > 0) {
					if (i == 0) {
						model.setComment1(stripNonCharCodepoints(fulls.get(0).text().trim()));
					} else if (i == 1) {
						model.setComment2(stripNonCharCodepoints(fulls.get(0).text().trim()));
					} else if (i == 2) {
						model.setComment3(stripNonCharCodepoints(fulls.get(0).text().trim()));
					} 
				}
			}
		}
		return model;
	} 
	
	
}
