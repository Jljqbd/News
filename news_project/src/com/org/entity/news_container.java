package com.org.entity;

import com.org.util.FileRW;

public class news_container {
	public String newsid;// 新闻id
	public String authorid;// 作者id
	public String newstitle;// 新闻标题
	public String newstheme;// 新闻主题
	public String hidename;// 匿名
	public String newscollect;
	public String username;
	public String news_content;
	public String newslike;
	public String newstime;
	public String newsOurl;
	public String newsviewsnum;
	public String imageurl;

	public news_container(newspaper news) {
		FileRW fw = new FileRW();
		authorid = news.authorid;
		newsid = news.newsid;
		newstitle = news.newstitle;
		newstheme = news.newstheme;
		hidename = news.hidename;
		newscollect = news.newscollect;
		username = news.username;
		news_content = fw.ReadFileContent(news.news_content_path);
		newslike = news.newslike;
		newstime = news.newstime;
		newsOurl = news.newsOurl;
		newsviewsnum = news.newsviewsnum;
		imageurl = news.imageurl;
	}

	public news_container() {

	}

	@Override
	public String toString() {
		return "newspaper [authorid=" + authorid + ", hidename=" + hidename + ", imageurl=" + imageurl + ", newsOurl="
				+ newsOurl + ", news_content_path=" + news_content + ", newscollect=" + newscollect + ", newsid="
				+ newsid + ", newslike=" + newslike + ", newstheme=" + newstheme + ", newstime=" + newstime
				+ ", newstitle=" + newstitle + ", newsviewsnum=" + newsviewsnum + ", username=" + username + "]";
	}
}
