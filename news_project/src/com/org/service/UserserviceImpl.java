package com.org.service;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.org.dao.BaseDao;
import com.org.entity.Themenews;
import com.org.entity.comment;
import com.org.entity.news_container;
import com.org.entity.newspaper;
import com.org.entity.theme;
import com.org.entity.user;
import com.org.util.DatabaseUtil;
import com.org.util.FileRW;

public class UserserviceImpl implements Userservice {

	public BaseDao m1 = null;
	private FileRW fw = null;

	public UserserviceImpl() {
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public List<newspaper> getUsernews(String id) throws SQLException {
		// TODO Auto-generated method stub
		List<newspaper> news = new ArrayList<newspaper>();
		// 允许输出的结果，这里为无穷大
		int outputnum = Integer.MAX_VALUE;
		// class类型，用于实现反射
		Class tClass = newspaper.class;
		news = m1.datasearch("newspaper", id, "authorid", outputnum, tClass, "newstime");
		return news;
	}

	// 得到用户收藏的新闻
	public List<newspaper> getUsercollect(String id) throws SQLException {
		// TODO Auto-generated method stub
		List<user> u1 = new ArrayList<user>();
		// 列名
		List<String> colname = new ArrayList<String>();
		// 对应列名的值
		List<String> data = new ArrayList<String>();
		colname.add("iduser");
		data.add(id);
		// 允许输出的结果，这里为无穷大
		int outputnum = 1;
		// class类型，用于实现反射
		Class tClass = user.class;
		u1 = m1.datasearch("user", id, "iduser", outputnum, tClass);
		// 得到用户收藏新闻记录文件路径
		String collect_path = u1.get(0).collect_news_path;
		List<String> collect_news_id = new ArrayList<String>();
		List<newspaper> collect_news = new ArrayList<newspaper>();
		try {
			collect_news_id = fw.ReadlineFile(collect_path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < collect_news_id.size(); i++) {
			collect_news.add(
					m1.datasearch("newspaper", collect_news_id.get(i), "newsid", outputnum, newspaper.class, "newstime").get(0));
		}
		return collect_news;
	}

	// 删除所有收藏新闻
	@Override
	public boolean DelCollectNews(String id) throws SQLException {
		String collect_path = null;
		collect_path = m1.datasearch("user", id, "iduser", 1, user.class).get(0).collect_news_path;
		// TODO Auto-generated method stub
		return fw.WriteFile(collect_path, "");//覆盖写入
	}

	// 增加收藏新闻
	@Override
	public boolean Addcollection(String uid, String newsid) throws SQLException {
		// TODO Auto-generated method stub
		String collect_path = null;
		boolean isok = false;
		List<String> data = new ArrayList<String>();
		data.add(newsid);
		collect_path = m1.datasearch("user", uid, "iduser", 1, user.class).get(0).collect_news_path;
		try {
			isok = fw.WriteFile(collect_path, data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isok;
	}

	// 删除用户收藏文件的特定一行
	@Override
	public boolean DelCollectlineNews(String id, String newsid) throws SQLException {
		// TODO Auto-generated method stub
		String collect_path = null;
		collect_path = m1.datasearch("user", id, "iduser", 1, user.class).get(0).collect_news_path;
		return fw.DelStr(collect_path, newsid);
	}

	// 发布新闻
	public boolean Addnews(news_container news) throws SQLException {
		// TODO Auto-generated method stub
		List<String> data = new ArrayList<String>();
		List<String> colname = new ArrayList<String>();
		newspaper n1 = new newspaper(news,
				m1.datasearch("user", news.authorid, "iduser", 1, user.class).get(0).usermakenewspath);
		n1.newsOurl ="http://localhost:8080/news_project/UserServlet?opr=viewnews&newsid="+n1.newsid;
		data.add(n1.authorid);
		colname.add("authorid");
		data.add(n1.hidename);
		colname.add("hidename");
		data.add(n1.imageurl);
		colname.add("imageurl");
		data.add(n1.news_content_path);
		colname.add("news_content_path");
		data.add(n1.newscollect);
		colname.add("newscollect");
		data.add(n1.newsid);
		colname.add("newsid");
		data.add(n1.newslike);
		colname.add("newslike");
		data.add(n1.newsOurl);
		colname.add("newsOurl");
		data.add(n1.newstheme);
		colname.add("newstheme");
		data.add(n1.newstime);
		colname.add("newstime");
		data.add(n1.newstitle);
		colname.add("newstitle");
		data.add(n1.newsviewsnum);
		colname.add("newsviewsnum");
		data.add(n1.username);
		colname.add("username");
		
		return m1.Adddata("newspaper", data, colname, newspaper.class);
	}

	// 修改新闻
	@Override
	public boolean Editnews(news_container news) throws SQLException {
		// TODO Auto-generated method stub
		List<String> data = new ArrayList<String>();
		List<String> colname = new ArrayList<String>();
		String Path = null;
		data.add(news.hidename);
		colname.add("hidename");
		data.add(news.newstheme);
		colname.add("newstheme");
		data.add(news.newstitle);
		colname.add("newstitle");
		Path = m1.datasearch("newspaper", news.authorid, "authorid", 1, newspaper.class).get(0).news_content_path;
		fw.WriteFile(Path, news.news_content);
		return m1.editdata("newspaper", news.newsid, "newsid", data, colname, newspaper.class);
	}

	// 删除新闻
	@Override
	public boolean Delnews(String newsid) throws SQLException {
		// TODO Auto-generated method stub
		m1.deletedata("comment", newsid, "newsid", comment.class);
		return m1.deletedata("newspaper", newsid, "newsid", newspaper.class);
	}

	@Override
	// 删除用户
	public boolean DelUser(String uid) throws SQLException {
		// TODO Auto-generated method stub
		m1.deletedata("comment", uid, "userid", comment.class);
		boolean flag = true;
		List<newspaper> n1 = new ArrayList<newspaper>();
		// 得到这个用户写的所有新闻
		n1 = m1.datasearch("newspaper", uid, "authorid", Integer.MAX_VALUE, newspaper.class);
		// 对于每个新闻删除评论
		for (int i = 0; n1!=null&&i < n1.size(); i++) {
			m1.deletedata("comment", n1.get(i).newsid, "newsid", comment.class);
		}
		List<user> u1 = new ArrayList<user>();
		// 获取他用户文件的路径
		u1 = m1.datasearch("user", uid, "iduser", 1, user.class);
		// 删除他的用户文件
		flag = flag && fw.delete(u1.get(0).collect_news_path) && fw.delete(u1.get(0).display_theme_path)
				&& fw.delete(u1.get(0).like_comment_path) && fw.delete(u1.get(0).like_news_path)
				&& fw.delete(u1.get(0).usermakenewspath);
		// 删除他的用户表
		return m1.deletedata("user", uid, "iduser", user.class);
	}

	// 修改个人信息
	@Override
	public boolean EditUser(user u1) throws SQLException {
		// TODO Auto-generated method stub
		List<String> data = new ArrayList<String>();
		List<String> colname = new ArrayList<String>();
		data.add(u1.userpassword);
		data.add(u1.username);
		colname.add("userpassword");
		colname.add("username");
		return m1.editdata("user", u1.iduser, "iduser", data, colname, user.class);
	}

	@Override
	// 登陆验证
	public user Comparepasswd(String uid, String passwd) throws SQLException {
		// TODO Auto-generated method stub
		List<String> data = new ArrayList<String>();
		List<String> colname = new ArrayList<String>();
		data.add(uid);
		colname.add("iduser");
		data.add(passwd);
		colname.add("userpassword");
		return m1.datasearch("user", data, colname, 1, user.class).get(0);
	}
	//得到用户允许显示的主题名
	public List<String> GetUserTheme(String uid) throws SQLException {
		String theme_path = (m1.datasearch("user", uid, "iduser", 1, user.class)).get(0).display_theme_path;
		try {
			return fw.ReadlineFile(theme_path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<String> ViewNews(String uid, String newsid) throws SQLException {
		// TODO Auto-generated method stub
		user u1 = m1.datasearch("user", uid, "iduser", 1, user.class).get(0);
		String collect_path = u1.collect_news_path;
		String like_path = u1.like_news_path;
		List<String> rv = new ArrayList<String>();
		
		try {
			rv.add(fw.IsExist(collect_path, newsid)?"true":"false");
			rv.add(fw.IsExist(like_path, newsid)?"true":"false");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return rv;
	}

	@Override
	//文章点赞
	public boolean AddNewslike(String uid, String newsid) throws SQLException {
		// TODO Auto-generated method stub
		String like  = m1.datasearch("newspaper", newsid, "newsid", 1, newspaper.class).get(0).newslike;
		List<String> data = new ArrayList<String>();
		List<String> colname = new ArrayList<String>();
		data.add((Integer.parseInt(like)+1)+"");
		colname.add("newslike");
		boolean isok = m1.editdata("newspaper", newsid, "newsid", data, colname, newspaper.class);
		String userlikepath = m1.datasearch("user", uid, "iduser", 1, user.class).get(0).like_news_path;
		return isok&&fw.WriteFile(userlikepath, newsid);
	}

	@Override
	//评论的赞+1
	public boolean AddComlike(String uid, String idcomment) throws SQLException {
		// TODO Auto-generated method stub
		//String numview = (Integer.parseInt(n1.newsviewsnum)+1)+"";
		String like  = m1.datasearch("comment", idcomment, "idcomment", 1, comment.class).get(0).clike;
		String comment_path = m1.datasearch("user", uid, "iduser", 1, user.class).get(0).like_comment_path;
		List<String> arrs = new ArrayList<String>();
		arrs.add(idcomment);
		boolean isok = false;
		try {
			isok = fw.WriteFile(comment_path, arrs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		List<String> data = new ArrayList<String>();
		List<String> colname = new ArrayList<String>();
		data.add((Integer.parseInt(like)+1)+"");
		colname.add("clike");
		return isok&&m1.editdata("comment", idcomment, "idcomment", data, colname, comment.class);//点赞数+1
	}

	@Override
	public boolean AddComment(comment c) throws SQLException {
		// TODO Auto-generated method stub
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmss");
		SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat b = new SimpleDateFormat("HH:mm:ss+08:00");
		//System.out.println(a.format(date)+"T"+b.format(date));
		c.time = a.format(date)+"T"+b.format(date);
		c.idcomment = c.userid + formatter.format(date);
		List<String> data = new ArrayList<String>();
		List<String> colname = new ArrayList<String>();
		data.add(c.content);
		data.add(c.idcomment);
		data.add(c.clike);
		data.add(c.newsid);
		data.add(c.time);
		data.add(c.userid);
		data.add(c.username);
		colname.add("content");
		colname.add("idcomment");
		colname.add("clike");
		colname.add("newsid");
		colname.add("time");
		colname.add("userid");
		colname.add("username");
		return m1.Adddata("comment", data, colname, comment.class);
	}
	//创建用户	
	@Override
	public user AddUser(user u) throws SQLException {
		// TODO Auto-generated method stub
		String username = u.username;
		String userpassword = u.userpassword;
		Date date = new Date();
		boolean isok = true;
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmss"); 
		SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat b = new SimpleDateFormat("HH:mm:ss+08:00");
		//System.out.println(a.format(date)+"T"+b.format(date));
		u.registertime = a.format(date)+"T"+b.format(date);
		u.iduser = fw.StringToMd5(username + formatter.format(date));
		String userpath = "E:/VSC_project/VSC py/newraper/userfolder/"+u.iduser+"/";
		List<theme> themelist = new ArrayList<theme>();
		String usernewspath = "E:/VSC_project/VSC py/newraper/"+u.iduser+"/";
		u.usermakenewspath = usernewspath;
		themelist = m1.datasearch("theme", "1", "'1'", Integer.MAX_VALUE, theme.class);
		fw.CreateFile(userpath + "collect.txt", "");
		u.collect_news_path = userpath + "collect.txt";
		fw.CreateFile(userpath + "comment.txt", "");
		u.like_comment_path = userpath + "comment.txt";
		fw.CreateFile(userpath + "likenews.txt", "");
		u.like_news_path = userpath + "likenews.txt";
		fw.CreateFile(userpath + "theme.txt", "");
		u.display_theme_path = userpath + "theme.txt";
		for(int i = 0; i < themelist.size(); i++){
			isok = isok&&fw.CreateFile(usernewspath+themelist.get(i).themename, "");
		}
		boolean dataopr = false;
		if(isok){
			List<String> data = new ArrayList<String>();
			List<String> colname = new ArrayList<String>();
			data.add(u.collect_news_path);colname.add("collect_news_path");
			data.add(u.display_theme_path);colname.add("display_theme_path");
			data.add(u.iduser);colname.add("iduser");
			data.add(u.like_comment_path);colname.add("like_comment_path");
			data.add(u.like_news_path);colname.add("like_news_path");
			data.add(u.registertime);colname.add("registertime");
			data.add(u.usermakenewspath);colname.add("usermakenewspath");
			data.add(u.username);colname.add("username");
			data.add(u.userpassword);colname.add("userpassword");
			dataopr = m1.Adddata("user", data, colname, user.class);
			List<theme> tl = new ArrayList<theme>();
			tl = m1.datasearch("theme", "1", "'1'", Integer.MAX_VALUE, theme.class);
			List<String> userthemelist = new ArrayList<String>();
			for(int t = 0; t < tl.size(); t++){
				userthemelist.add(tl.get(t).themename);
			}
			try {
				String collectpath = u.collect_news_path;
				String likenews = u.like_news_path;
				String likecom = u.like_comment_path;
				File dir = new File(userpath);
				dataopr = dataopr&&dir.mkdirs();
				dataopr = dataopr&&fw.CreateFile(u.display_theme_path, "");
				dataopr = dataopr&&fw.WriteFile(u.display_theme_path, userthemelist);
				dataopr = dataopr&&fw.CreateFile(collectpath, "");
				dataopr = dataopr&&fw.CreateFile(likenews, "");
				dataopr = dataopr&&fw.CreateFile(likecom, "");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			fw.delete(usernewspath);
			fw.delete(userpath);
			return null;
		}
		if(!dataopr){
			fw.delete(usernewspath);
			fw.delete(userpath);
			return null;
		}
		return u;
	}

	@Override
	public List<user> UserLikeSearch(String username) throws SQLException {
		// TODO Auto-generated method stub
		return m1.datalikesearch("user", username, "username", Integer.MAX_VALUE, user.class);
	}

	@Override
	public List<newspaper> NewsLikeSearch(String title) throws SQLException {
		// TODO Auto-generated method stub
		return m1.datalikesearch("newspaper", title, "newstitle", Integer.MAX_VALUE, newspaper.class, "newstime");
	}

	@Override
	public List<newspaper> GetTNews(String theme) throws SQLException {
		// TODO Auto-generated method stub
		return m1.datasearch("newspaper", theme, "newstheme", Integer.MAX_VALUE, newspaper.class, "newstime");
	}
	@Override
	public List<Themenews> GetTheme(String uid) throws SQLException {
		// TODO Auto-generated method stub
		user u = new user();
		u = m1.datasearch("user", uid, "iduser", 1, user.class).get(0);
		List<String> ts = new ArrayList<String>();
		try {
			ts = fw.ReadlineFile(u.display_theme_path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		List<theme> tl = new ArrayList<theme>();
		theme testt = null;
		for(int i = 0; i < ts.size(); i++){
			testt = new theme();
			testt.themename = ts.get(i);
			tl.add(testt);
		}
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
