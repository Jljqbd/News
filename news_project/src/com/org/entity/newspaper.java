package com.org.entity;

import com.org.dao.BaseDao;
import com.org.util.FileRW;

public class newspaper {
	public String newsid;// 新闻id
	public String authorid;// 作者id
	public String newstitle;// 新闻标题
	public String newstheme;// 新闻主题
	public String hidename;// 匿名
	public String newscollect;
	public String username;
	public String news_content_path;
	public String newslike;
	public String newstime;
	public String newsOurl;
	public String newsviewsnum;
	public String imageurl;

	public newspaper(news_container news, String usermakenewspath) {
		FileRW fw = new FileRW();
		authorid = news.authorid;
		newsid = news.newsid;
		newstitle = news.newstitle;
		newstheme = news.newstheme;
		hidename = news.hidename;
		newscollect = news.newscollect;
		username = news.username;
		news_content_path = usermakenewspath +"/"+newstheme+ "/" + newsid + ".txt";
		if(fw.CreateFile(news_content_path, news.news_content)){
			System.out.println("文件创建成功");
		}else{
			System.out.println("文件创建失败");
		}
		System.out.println("文件内容:"+news.news_content);
		newslike = news.newslike;
		newstime = news.newstime;
		newsOurl = news.newsOurl;
		newsviewsnum = news.newsviewsnum;
		imageurl = news.imageurl;
	}

	public newspaper() {

	}

	@Override
	public String toString() {
		return "newspaper [authorid=" + authorid + ", hidename=" + hidename + ", imageurl=" + imageurl + ", newsOurl="
				+ newsOurl + ", news_content_path=" + news_content_path + ", newscollect=" + newscollect + ", newsid="
				+ newsid + ", newslike=" + newslike + ", newstheme=" + newstheme + ", newstime=" + newstime
				+ ", newstitle=" + newstitle + ", newsviewsnum=" + newsviewsnum + ", username=" + username + "]";
	}

}
