package com.ttky8.com.douban_crawler;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class ShortMoiveModel {
	String directors;
	String rate;
	int cover_x;
	String star;
	String title;
	String url;
	String casts;
	String cover;
	String id;
	int cover_y;
	public String getDirectors() {
		return StringUtils.join(JSONArray.parseArray(directors).toJavaList(String.class), ",");
	}
	public void setDirectors(String directors) {
		this.directors = directors;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	
	public int getCover_x() {
		return cover_x;
	}
	public void setCover_x(int cover_x) {
		this.cover_x = cover_x;
	}
	public void setCover_y(int cover_y) {
		this.cover_y = cover_y;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCasts() {
		return StringUtils.join(JSONArray.parseArray(casts).toJavaList(String.class), ",");
	}
	public void setCasts(String casts) {
		this.casts = casts;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCover_y() {
		return cover_y;
	}
}

class MovieList {
	ArrayList<ShortMoiveModel> list = new ArrayList<ShortMoiveModel>();

	public ArrayList<ShortMoiveModel> getList() {
		return list;
	}


	public void setList(ArrayList<ShortMoiveModel> list) {
		this.list = list;
	}


	public static MovieList fromJson(String json) {
		MovieList list = new MovieList();
		JSONArray arr = JSON.parseObject(json).getJSONArray("data");
		for (int i = 0; i < arr.size(); i++) { 
			list.list.add(JSON.toJavaObject(arr.getJSONObject(i), ShortMoiveModel.class));
		}
		
		return list;
	}
}


