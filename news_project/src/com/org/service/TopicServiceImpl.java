package com.org.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.org.dao.BaseDao;
import com.org.entity.newspaper;
import com.org.entity.theme;
import com.org.entity.user;
import com.org.util.FileRW;


public class TopicServiceImpl implements TopicService {

	@Override
	public int addTopic(String tname, String uid) throws SQLException {
		int result=0;
		boolean b = false;
		Connection conn=null;
		//设置data
		List<String> data=new ArrayList<String>();
		data.add(tname);
		FileRW fw = new FileRW();
		String displaypath = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/news", "root", "root");
			BaseDao m1 = new BaseDao(conn);
			displaypath = m1.datasearch("user", uid, "iduser", 1, user.class).get(0).display_theme_path;
			try {
				b = fw.WriteFile(displaypath, data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//b=baseDao.Adddata(table,data,colname,theme.class);
			if(b==true){
				result=1;
			}else{
				result=0;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(conn!=null){
				conn.close();
			}
		}

		return result;
	}
	public List<String> GetUserTopic(String uid) throws SQLException{
		List<String> topicList = new ArrayList<String>();
		Connection conn=null;	
		FileRW fw = new FileRW();
		//设置列名colname
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/news", "root", "root");
			BaseDao m1=new BaseDao(conn);
			String collectpath = m1.datasearch("user", uid, "iduser", 1, user.class).get(0).display_theme_path;
			try {
				topicList = fw.ReadlineFile(collectpath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		return topicList;
	}
	public List<theme> getAllTopics() throws SQLException {
		List<theme> topicList=null;
		Connection conn=null;
		String table="theme";
		//设置data
		String data="1";
		
		//设置列名colname
		String colname="themename=\"1\" OR '1'";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/news", "root", "root");
			BaseDao baseDao=new BaseDao(conn);
			topicList=baseDao.datasearch(table, data, colname, Integer.MAX_VALUE, theme.class);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(conn!=null){
				conn.close();
			}
		}
			
		return topicList;
	}
	//返回用户屏蔽的主题
	public List<String> CompareTheme(List<theme> alltheme, List<String> usertheme) throws SQLException{
		List<String> rv = new ArrayList<String>();
		if(alltheme==null){
			return null;
		}
		if(usertheme==null){
			for(int i = 0; i < alltheme.size(); i++){
					rv.add(alltheme.get(i).themename);
				}
			return rv;
		}
		for(int i = 0; i < alltheme.size(); i++){
			if(!usertheme.contains(alltheme.get(i).themename)){
				rv.add(alltheme.get(i).themename);
			}
		}
		return rv;
	}
	@Override
	public int modify(String otname,String tname, String uid) throws SQLException {
		int result=0;
		boolean b = true;
		Connection conn=null;
		//要插入的表格名
		String table="theme";
		//原始
		List<String> odata = new ArrayList<String>();
		odata.add(otname);
		odata.add(uid);
		List<String> ocolname = new ArrayList<String>();
		ocolname.add("newstheme");
		ocolname.add("authorid");
		FileRW fw = new FileRW();
		
		//设置data
		List<String> data=new ArrayList<String>();
		data.add(tname);
		//设置列名colname
		List<String> colname=new ArrayList<String>();
		colname.add("themename");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/news", "root", "root");
			BaseDao baseDao=new BaseDao(conn);
			b=baseDao.editdata(table, odata, ocolname, data, colname, theme.class);//将这个作者所有的该主题的文章改为他想要的
			if(!getAllTopics().contains(tname)){//新的主题不存在
				List<String> temp1 = new ArrayList<String>();
				temp1.add(tname);
				List<String> temp2 = new ArrayList<String>();
				temp2.add("themename");
				List<String> temp3 = new ArrayList<String>();
				temp3.add("tname");
				b = b&&baseDao.Adddata("theme", temp1, temp2, theme.class);
				String userdispath = baseDao.datasearch("user", uid, "iduser", 1, user.class).get(0).display_theme_path;
				try {
					b = b&&fw.WriteFile(userdispath, temp3);//用户默认关注自己修改的主题
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(b==true){
				result=1;
			}else{
				result=0;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(conn!=null){
				conn.close();
			}
		}
			
		return result;
	}
	@Override
	public int delete(String dtname, String uid) throws SQLException {
		int result=0;
		boolean b = true;
		FileRW fw = new FileRW();
		Connection conn = null;
		//要插入的表格名
		String table="theme";
		List<String> data= new ArrayList<String>();
		List<String> colname = new ArrayList<String>();
		data.add(uid);
		data.add(dtname);
		colname.add("authorid");
		colname.add("newstheme");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/news", "root", "root");
			BaseDao baseDao=new BaseDao(conn);
			List<String> ndata = new ArrayList<String>();
			List<String> ncolname = new ArrayList<String>();
			ndata.add("未知");
			ncolname.add("newstheme");
			
			baseDao.editdata("newspaper", data, colname, ndata, ncolname, newspaper.class);//将这些原属于此主题的新闻主题更改为未知
			String userdispath = baseDao.datasearch("user", uid, "iduser", 1, user.class).get(0).display_theme_path;
			b = fw.DelStr(userdispath, dtname);
			if(b==true){
				result=1;
			}else{
				result=0;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

}
