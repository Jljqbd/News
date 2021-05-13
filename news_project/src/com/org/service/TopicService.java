package com.org.service;

import java.sql.SQLException;
import java.util.List;

import com.org.entity.theme;

public interface TopicService {
	public int addTopic(String tname, String uid)throws SQLException;
	
	public List<theme> getAllTopics()throws SQLException;
	
	public int modify(String otname,String tname, String uid)throws SQLException;
	
	public int delete(String dtname, String uid)throws SQLException;
	//获取用户允许显示的主题
	public List<String> GetUserTopic(String uid) throws SQLException;
	//用户可以添加的
	public List<String> CompareTheme(List<theme> alltheme, List<String> usertheme) throws SQLException;
}
