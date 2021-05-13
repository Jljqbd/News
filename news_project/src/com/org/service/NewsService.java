package com.org.service;

import java.sql.SQLException;
import java.util.List;

import com.org.entity.News;
import com.org.entity.Themenews;
import com.org.entity.newspaper;

public interface NewsService {
    //����չʾ
	public List<News> getNews(String kinds) throws SQLException;
	//获取这个主题下所有的新闻
	public List<newspaper> GetTNews(String theme) throws SQLException;
	//获取所有的主题
	public List<Themenews> GetTheme() throws SQLException;
}
