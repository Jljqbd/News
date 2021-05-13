package com.org.entity;

public class News {
	private String newsid;//新闻id
	private String newstitle;//新闻标题
	private String authorid;//作者id
	private String newstheme;//新闻主题
	private String hidename;//匿名
	private String newscollect;
	private String username;
	private String news_content_path;
	private String newslike;
	private String newstime;
	private String newsOurl;
	private String newsviewsnum;
	private String imageurl;
	public String getNewsid() {
		return newsid;
	}
	public void setNewsid(String newsid) {
		this.newsid = newsid;
	}
	public String getAuthorid() {
		return authorid;
	}
	public void setAuthorid(String authorid) {
		this.authorid = authorid;
	}
	public String getNewstitle() {
		return newstitle;
	}
	public void setNewstitle(String newstitle) {
		this.newstitle = newstitle;
	}
	public String getNewstheme() {
		return newstheme;
	}
	public void setNewstheme(String newstheme) {
		this.newstheme = newstheme;
	}
	public String getHidename() {
		return hidename;
	}
	public void setHidename(String hidename) {
		this.hidename = hidename;
	}
	public String getNewscollect() {
		return newscollect;
	}
	public void setNewscollect(String newscollect) {
		this.newscollect = newscollect;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNews_content_path() {
		return news_content_path;
	}
	public void setNews_content_path(String newsContentPath) {
		news_content_path = newsContentPath;
	}
	public String getNewslike() {
		return newslike;
	}
	public void setNewslike(String newslike) {
		this.newslike = newslike;
	}
	public String getNewstime() {
		return newstime;
	}
	public void setNewstime(String newstime) {
		this.newstime = newstime;
	}
	public String getNewsOurl() {
		return newsOurl;
	}
	public void setNewsOurl(String newsOurl) {
		this.newsOurl = newsOurl;
	}
	public String getNewsviewsnum() {
		return newsviewsnum;
	}
	public void setNewsviewsnum(String newsviewsnum) {
		this.newsviewsnum = newsviewsnum;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}


}