package com.org.service;

import java.sql.SQLException;
import java.util.List;

import com.org.entity.Themenews;
import com.org.entity.comment;
import com.org.entity.news_container;
import com.org.entity.newspaper;
import com.org.entity.user;

public interface Userservice {

	// 查询用户收藏新闻
	public List<newspaper> getUsernews(String id) throws SQLException;

	// 查询用户发布的新闻
	public List<newspaper> getUsercollect(String id) throws SQLException;

	// 删除所有收藏新闻
	public boolean DelCollectNews(String id) throws SQLException;

	// 删除收藏新闻的其中一行
	public boolean DelCollectlineNews(String id, String newsid) throws SQLException;

	// 增加收藏新闻
	public boolean Addcollection(String uid, String newsid) throws SQLException;

	// 发布新闻
	public boolean Addnews(news_container news) throws SQLException;

	// 修改新闻
	public boolean Editnews(news_container news) throws SQLException;

	// 删除新闻
	public boolean Delnews(String newsid) throws SQLException;

	// 删除用户
	public boolean DelUser(String uid) throws SQLException;

	// 修改用户个人信息
	public boolean EditUser(user u1) throws SQLException;

	// 密码比对
	public user Comparepasswd(String uid, String passwd) throws SQLException;
	
	//获得用户允许显示的主题
	public List<String> GetUserTheme(String uid) throws SQLException;
	
	//浏览新闻的操作
	public List<String> ViewNews(String uid, String newsid) throws SQLException;
	//文章点赞
	public boolean AddNewslike(String uid, String newsid) throws SQLException;
	//评论点赞
	public boolean AddComlike(String uid, String idcomment) throws SQLException;
	//添加评论
	public boolean AddComment(comment c) throws SQLException;
	//添加用户,用户注册
	public user AddUser(user u) throws SQLException;
	//模糊查询用户
	public List<user> UserLikeSearch(String username) throws SQLException;
	//模糊查询新闻
	public List<newspaper> NewsLikeSearch(String title) throws SQLException;
	//获取某一类的主题
	public List<newspaper> GetTNews(String themename) throws SQLException;
	//获取用户想要显示的主题类新闻
	public List<Themenews> GetTheme(String uid) throws SQLException;
	
}
