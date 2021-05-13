package com.org.entity;

import java.util.ArrayList;
import java.util.List;

public class Page {
	public int cpn;// current page number
	public int tp;// total pages
	public List<news_container> c_news;// 当前页数新闻
	public Page(List<newspaper> n1, int cpn, int tp){
		this.cpn = cpn;
		this.tp = tp;
		c_news = new ArrayList<news_container>();
		news_container nc = null;
		for(int i = 0; i < n1.size(); i++){
			nc = new news_container(n1.get(i));
			c_news.add(nc);
		}
	}
	public Page() {
		c_news = new ArrayList<news_container>();
	}
}
