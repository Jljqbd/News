package com.org.entity;

public class user {
	public String iduser;// 用户id
	public String username;// 用户名
	public String userpassword;// 用户密码
	public String registertime;// 用户注册时间
	public String display_theme_path;// 主题的数据路径
	public String usermakenewspath;// 用户发布的新闻的数据路径
	public String collect_news_path;// 收藏的新闻的数据路径
	public String like_news_path;// 喜欢的新闻的数据路径
	public String like_comment_path;// 喜欢的评论的数据路径

	@Override
	public String toString() {
		return "user [collect_news_path=" + collect_news_path + ", display_theme_path=" + display_theme_path
				+ ", iduser=" + iduser + ", like_comment_path=" + like_comment_path + ", like_news_path="
				+ like_news_path + ", registertime=" + registertime + ", usermakenewspath=" + usermakenewspath
				+ ", username=" + username + ", userpassword=" + userpassword + "]";
	}

}
