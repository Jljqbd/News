package com.org.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.org.dao.BaseDao;
//import com.org.dao.NewsDaoImpl;
import com.org.entity.News;
import com.org.entity.Themenews;
import com.org.entity.newspaper;
import com.org.entity.theme;
import com.org.util.DatabaseUtil;
import com.org.util.FileRW;
;
//import com.org.dao.news.NewsDao;


public class NewsServiceImpl implements NewsService {

	public BaseDao m1 = null;
	private FileRW fw = null;
	public NewsServiceImpl() {
		DatabaseUtil D1 = new DatabaseUtil();
		fw = new FileRW();
		// Statement stmt = null;
		Connection conn = null;
		fw = new FileRW();
		try {
			conn = D1.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.m1 = new BaseDao(conn);

	}
	@Override
	public List<News> getNews(String kinds) throws SQLException {
		List<newspaper> newsList=null;
		List<News> newnewsList=new ArrayList<News>();
		//定义连接对象
		Connection conn = null;
		String a=new String();
		String table="newspaper";
		//设置data
		String data=kinds;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/news", "root", "root");
			BaseDao baseDao=new BaseDao(conn);
			//创建newsdao对象
			newsList=baseDao.datasearch(table, kinds, "newstheme", 5,newspaper.class);
			News news=null;
			//循环次数
			//System.out.println(newsList.newsid);
			if(newsList==null){
				return null;
			}
			int num=newsList.size();
			boolean b=true;
			while(b){
				news=new News();
				a=newsList.get(num-1).newsid;
				news.setNewsid(newsList.get(num-1).newsid);
				news.setNewstitle(newsList.get(num-1).newstitle);
				news.setNewstime(newsList.get(num-1).newstime);
				news.setUsername(newsList.get(num-1).username);
				num=num-1;
				newnewsList.add(news);
				
				if(num==0)b=false;
			}
			
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			if(conn!=null){
				conn.close();
			}
		}

		return newnewsList;//返回新闻内容
		
	
	}

	@Override
	public List<newspaper> GetTNews(String theme) throws SQLException {
		// TODO Auto-generated method stub
		return m1.datasearch("newspaper", theme, "newstheme", Integer.MAX_VALUE, newspaper.class, "newstime");
	}
	//获取每类主题新闻的最新消息
	@Override
	public List<Themenews> GetTheme() throws SQLException {
		// TODO Auto-generated method stub
		List<theme> tl = m1.datasearch("theme", "1", "'1'", Integer.MAX_VALUE, theme.class);
		List<newspaper> nlist = null;
		Themenews tn = null;
		List<Themenews> thl = new ArrayList<Themenews>();
		for(int i = 0; i < tl.size(); i++){
			nlist = new ArrayList<newspaper>();
			nlist = m1.datasearch("newspaper", tl.get(i).themename, "newstheme", Integer.MAX_VALUE, newspaper.class, "newstime");
			tn = new Themenews();
			if(nlist!=null){
			tn.newsnum = nlist.size()+"";
			tn.newstime = nlist.get(0).newstime;
			tn.themename = tl.get(i).themename;
			thl.add(tn);
			}
		}
		return thl;
	}
        
}
