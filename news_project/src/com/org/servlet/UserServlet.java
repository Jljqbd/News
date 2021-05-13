package com.org.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.org.entity.News;
import com.org.entity.Page;
import com.org.entity.Themenews;
import com.org.entity.comment;
import com.org.entity.news_container;
import com.org.entity.newspaper;
import com.org.entity.user;
import com.org.service.NewsService;
import com.org.service.NewsServiceImpl;
import com.org.service.UserserviceImpl;
import com.org.util.FileRW;


public class UserServlet extends HttpServlet {
	
	public static List<String> array2List(String [] stringArray) {
        if (stringArray == null) {
            return null;
        }
        return Arrays.asList(stringArray);
    }
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 指定服务器相应给浏览器的编码
		resp.setContentType("text/html;charset=utf-8");
		// 设置客户端请求和数据库取值时的编码
		req.setCharacterEncoding("utf-8");
		// 获取项目的根路径
		String contextPath = req.getContextPath();
		// 操作标识符
		String opr = req.getParameter("opr");
		UserserviceImpl usi = new UserserviceImpl();
		// 用户登陆验证
		if ("login".equals(opr)) {
			// 获取前端传过来的账号和密码，
			String uid = req.getParameter("uname");
			String upwd = req.getParameter("upwd");
			// Boolean flag = false;
			user u = new user();
			HttpSession session = req.getSession();
			try {
				u = usi.Comparepasswd(uid, upwd);
				// 如果用户存在，跳转到后台维护
				if (u != null) {
					//req.setAttribute("userdata", u);
					NewsService newsService=new NewsServiceImpl();
					//调用Newsservice层的新闻展示
					List<News> localnews=null;
					List<News> foriennews = null;
					List<News> amusenews = null;
					localnews=newsService.getNews("财经");
					foriennews = newsService.getNews("游戏");
					amusenews = newsService.getNews("娱乐");
					//把新闻内容保存到req对象中
					req.setAttribute("localnews", localnews);
					req.setAttribute("foriennews", foriennews);
					req.setAttribute("amusenews", amusenews);
					List<Themenews> tnl = new ArrayList<Themenews>();
					tnl = usi.GetTheme(uid);
					req.setAttribute("tnl", tnl);
					session.setAttribute("userdata", u);
					//System.out.println(contextPath);
					req.getRequestDispatcher("/newspages/homepage.jsp").forward(req, resp);
				} else {
					req.getRequestDispatcher("/newspages/LoginError.jsp").forward(req, resp);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else if(opr.equals("jumpadmin")){//进入后台管理
			req.getRequestDispatcher("/newspages/admin.jsp").forward(req, resp);
		}
		else if(opr.equals("jumphomepage")){
			//req.setAttribute("userdata", u);
			NewsService newsService=new NewsServiceImpl();
			//调用Newsservice层的新闻展示
			List<News> localnews=null;
			List<News> foriennews = null;
			List<News> amusenews = null;
			try {
				localnews=newsService.getNews("财经");
				foriennews = newsService.getNews("游戏");
				amusenews = newsService.getNews("娱乐");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//把新闻内容保存到req对象中
			req.setAttribute("localnews", localnews);
			req.setAttribute("foriennews", foriennews);
			req.setAttribute("amusenews", amusenews);
			List<Themenews> tnl = new ArrayList<Themenews>();
			user u = (user)req.getSession().getAttribute("userdata");
			if(u == null){
				resp.sendRedirect("hello.jsp");
			}else{
					String uid = ((user)(req.getSession().getAttribute("userdata"))).iduser;
			try {
				tnl = usi.GetTheme(uid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			req.setAttribute("tnl", tnl);
			//System.out.println(contextPath);
			req.getRequestDispatcher("/newspages/homepage.jsp").forward(req, resp);
			}
		}
		else if(opr.equals("jumplogin")){
			resp.sendRedirect("hello.jsp");
		}
		else if(opr.equals("jumpreg")){
			req.getRequestDispatcher("/newspages/reg.jsp").forward(req, resp);
		}
		//注册
		else if(opr.equals("reg")){
			user u = new user();
			u.username = req.getParameter("username");
			u.userpassword = req.getParameter("password");
			user userdata = new user();
			try {
				userdata = usi.AddUser(u);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				req.setAttribute("errordata", "发生了未知的错误");
				req.getRequestDispatcher("/newspages/usernewslist_error.jsp").forward(req, resp);
			}
			if(userdata!=null){
				req.setAttribute("uid", userdata.iduser);
				req.setAttribute("password", u.userpassword);
				req.getRequestDispatcher("/newspages/RegSuccess.jsp").forward(req, resp);
			}else{
				resp.sendRedirect("newspages/OprFail.jsp");
			}
		}
		else if(opr.equals("authorpage")){
			String authorid = req.getParameter("iduser");
			List<newspaper> n1 = new ArrayList<newspaper>();
			try {
				n1 = usi.getUsernews(authorid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				req.setAttribute("errordata", "发生了未知的错误");
				req.getRequestDispatcher("/newspages/usernewslist_error.jsp").forward(req, resp);
				e.printStackTrace();
			}
			req.setAttribute("usernewspaperdata", n1);
			req.getRequestDispatcher("/newspages/apage.jsp").forward(req, resp);
		}
		// 查询用户发布的新闻fun-->findupnews
		else if(opr.equals("jumpeditnews")){
			String uid = ((user)(req.getSession().getAttribute("userdata"))).iduser;
			List<newspaper> usernews = new ArrayList<newspaper>();
			Page vp = null;
			int num = 15;
			try {
				usernews = usi.getUsernews(uid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				req.setAttribute("errordata", "网页遇到了未知的错误");
				req.getRequestDispatcher("/newspages/usernewslist_error.jsp").forward(req, resp);;
			}
			if(usernews==null){
				req.setAttribute("errordata", "您没有发布的新闻,赶快去发表一篇吧");
				req.getRequestDispatcher("/newspages/usernewslist_error.jsp").forward(req, resp);
			}else{
				req.setAttribute("usernewspaperdata", usernews);
				req.getRequestDispatcher("/newspages/usernewslist.jsp").forward(req, resp);
				/*
				 List<Page> p1 = new ArrayList<Page>();
				int allpage = usernews.size()/num;
				for(int i = 0; i < allpage; i++){
					if(allpage!=0 && i != (allpage-1))//不是最后一页
						vp = new Page(usernews.subList(i*num, ((i+1)*num)-1), i, allpage-1);
					else//是最后一页
						vp = new Page(usernews.subList(i*num, usernews.size()-1), allpage, allpage);
					p1.add(vp);
				}
				//req.setAttribute("usernewspaperdata", usernews);
				req.setAttribute("pagedata", p1);
				req.getRequestDispatcher("/newspages/usernewslist.jsp").forward(req, resp);
				*/
			}
		}
		else if(opr.equals("jumpupnews")){//提交新闻包括修改和增加
			String isedit = req.getParameter("isedit");
			//编辑
			if(isedit.equals("1")){//是编辑新闻的
				String newsid = req.getParameter("newsid");
				newspaper n1 = new newspaper();
				List<String> tl = new ArrayList<String>();
				n1 = usi.m1.datasearch("newspaper", newsid, "newsid", 1, newspaper.class).get(0);
				news_container nc1 = new news_container(n1);
				req.setAttribute("ncdata", nc1);//这个修改的新闻信息
				req.setAttribute("isedit", isedit);//是否是编辑网页跳过来的
				
				String iduser = ((user)(req.getSession().getAttribute("userdata"))).iduser;
				try {
					tl = usi.GetUserTheme(iduser);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					req.getRequestDispatcher("/newspages/OprFail.jsp").forward(req, resp);
					e.printStackTrace();
				}
				if(tl==null){
					req.getRequestDispatcher("/newspages/OprFail.jsp").forward(req, resp);
				}else{
					req.setAttribute("themelist", tl);
					req.setAttribute("oprname", "修改");
					req.getRequestDispatcher("/newspages/upnews.jsp").forward(req, resp);
				}
				
			}
			//添加
			else if(isedit.equals("0")){
				Date date = new Date();
				FileRW fw = new FileRW();
				news_container nc1 = new news_container();
				List<String> tl = new ArrayList<String>();
				//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss+08:00");
				SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat b = new SimpleDateFormat("HH:mm:ss+08:00");
				//System.out.println(a.format(date)+"T"+b.format(date));
				nc1.newstime = a.format(date)+"T"+b.format(date);
				nc1.authorid = ((user)(req.getSession().getAttribute("userdata"))).iduser;
				nc1.newsid = fw.StringToMd5((nc1.authorid + nc1.newstime));
				nc1.news_content = "请在此处填写正文";
				nc1.newstitle = "请在此处填写标题";
				nc1.imageurl = "";
				nc1.newscollect = "0";
				nc1.newslike = "0";
				nc1.newsOurl = "";
				nc1.newsviewsnum = "0";
				nc1.username = ((user)(req.getSession().getAttribute("userdata"))).username;
				nc1.hidename="0";
				//theme
				try {
					tl = usi.GetUserTheme(nc1.authorid);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					req.getRequestDispatcher("/newspages/OprFail.jsp").forward(req, resp);
					e.printStackTrace();
				}
				if(tl==null){
					resp.sendRedirect("newspages/OprFail.jsp");
				}else{
					req.setAttribute("ncdata", nc1);//这个修改的新闻信息
					req.setAttribute("isedit", isedit);//是否是编辑网页跳过来的
					req.setAttribute("themelist", tl);
					req.setAttribute("oprname", "发布");
					req.getRequestDispatcher("/newspages/upnews.jsp").forward(req, resp);
				}
				
			}
			
		}
		//添加评论
		else if(opr.equals("addcomment")){
			comment c = new comment();
			c.newsid = (String)req.getParameter("newsid");
			c.username = (String)req.getParameter("username");
			c.userid = (String)req.getParameter("userid");
			c.content =  (String)req.getParameter("textarea");
			c.clike = "0";
			boolean isok =false;
			try {
				isok = usi.AddComment(c);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				resp.sendRedirect("newspages/OprFail.jsp");
				e.printStackTrace();
			}
			if(isok){
				req.getRequestDispatcher("UserServlet?opr=viewnews&newsid="+c.newsid).forward(req, resp);
			}else{
				resp.sendRedirect("newspages/OprFail.jsp");
			}
		}
		// 删除所有收藏新闻dcn-->delcollectnews
		else if(opr.equals("dcn")){
			String iduser = ((user)(req.getSession().getAttribute("userdata"))).iduser;
			boolean isok = false;
			try {
				isok = usi.DelCollectNews(iduser);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				resp.sendRedirect("newspages/OprFail.jsp");
				e.printStackTrace();
			}
			if(isok){
				req.getRequestDispatcher("/newspages/OprSuccess.jsp").forward(req, resp);
			}else{
				resp.sendRedirect("newspages/OprFail.jsp");
			}
		}
		//删除收藏新闻的其中一行dcln-->delcollectlinenews
		else if(opr.equals("dcln")){
			String newsid = (String)req.getParameter("newsid");
			String iduser = ((user)(req.getSession().getAttribute("userdata"))).iduser;
			boolean isok = false;
			try {
				isok = usi.DelCollectlineNews(iduser, newsid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				resp.sendRedirect("newspages/OprFail.jsp");
				e.printStackTrace();
			}
			if(isok){
				req.getRequestDispatcher("/newspages/OprSuccess.jsp").forward(req, resp);
			}else{
				resp.sendRedirect("newspages/OprFail.jsp");
			}
		}
		 //获取所有的收藏新闻
		else if(opr.equals("collect")){
			String iduser = ((user)(req.getSession().getAttribute("userdata"))).iduser;
			List<newspaper> n1 = new ArrayList<newspaper>();
			List<news_container> nc1 = new ArrayList<news_container>();
			news_container tnc = null;
			List<String> authorname = new ArrayList<String>();
			try {
				n1 = usi.getUsercollect(iduser);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				req.setAttribute("errordata", "发生了未知的错误");
				req.getRequestDispatcher("/newspages/usernewslist_error.jsp").forward(req, resp);
				e.printStackTrace();
			}
			if(n1==null||n1.size()==0){
				req.setAttribute("errordata", "您没有收藏的新闻,赶快去收藏一篇吧");
				req.getRequestDispatcher("/newspages/usernewslist_error.jsp").forward(req, resp);
			}else{
				for(int i = 0; i < n1.size(); i++){
					tnc = new news_container(n1.get(i));
					authorname.add((usi.m1.datasearch("user", tnc.authorid, "iduser", 1, user.class)).get(0).username);
					nc1.add(tnc);
				}
				req.setAttribute("authorname", authorname);
				req.setAttribute("usercollectlist", nc1);
				req.getRequestDispatcher("/newspages/Usercollectlist.jsp").forward(req, resp);
			}
		}
		//增加点赞
		else if(opr.equals("addlike")){
			String newsid = (String)req.getParameter("newsid");
			String iduser = ((user)(req.getSession().getAttribute("userdata"))).iduser;
			boolean isok = false;
			try {
				isok = usi.AddNewslike(iduser, newsid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				resp.sendRedirect("newspages/OprFail.jsp");
				e.printStackTrace();
			}
			if(isok){
				req.getRequestDispatcher("UserServlet?opr=viewnews&newsid="+newsid).forward(req, resp);
			}else{
				resp.sendRedirect("newspages/OprFail.jsp");
			}
			
		}
		
		// 增加收藏新闻acn-->addcollectnews
		else if(opr.equals("acn")){
			String newsid = (String)req.getParameter("newsid");
			String iduser = ((user)(req.getSession().getAttribute("userdata"))).iduser;
			boolean isok = false;
			try {
				isok = usi.Addcollection(iduser, newsid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				resp.sendRedirect("newspages/OprFail.jsp");
				e.printStackTrace();
			}
			if(isok){
				req.getRequestDispatcher("/newspages/OprSuccess.jsp").forward(req, resp);
			}else{
				resp.sendRedirect("newspages/OprFail.jsp");
			}
		}
		// 发布新闻un-->upnews,		// 修改新闻en-->editnews
		else if(opr.equals("upnews")){
			news_container nc = new news_container();
			String isedit = (String)req.getParameter("isedit");
			nc.authorid = (String)req.getParameter("authorid");
			nc.hidename = (String)req.getParameter("hidename");
			nc.imageurl = (String)req.getParameter("imageurl");
			nc.news_content = (String)req.getParameter("textarea");
			nc.newscollect = (String)req.getParameter("newscollect");
			nc.newsid = (String)req.getParameter("newsid");
			nc.newslike = (String)req.getParameter("newslike");
			nc.newsOurl = (String)req.getParameter("newsOurl");
			nc.newstheme = (String)req.getParameter("selecttheme");
			nc.newstime = (String)req.getParameter("newstime");
			nc.newstitle = (String)req.getParameter("tname");
			nc.newsviewsnum = (String)req.getParameter("newsviewsnum");
			nc.username = (String)req.getParameter("username");
			//System.out.println(nc);
			if(isedit.equals("1")){
				boolean isok = false; 
				try {
					isok = usi.Editnews(nc);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					resp.sendRedirect("newspages/OprFail.jsp");
					e.printStackTrace();
				}
				if(!isok){
					resp.sendRedirect("newspages/OprFail.jsp");
				}else{
					req.getRequestDispatcher("/newspages/OprSuccess.jsp").forward(req, resp);
				}
			}
			if(isedit.equals("0")){
				boolean isok = false;
				try {
					isok = usi.Addnews(nc);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					resp.sendRedirect("newspages/OprFail.jsp");
					e.printStackTrace();
				}
				if(!isok){
					resp.sendRedirect("newspages/OprFail.jsp");
				}else{
					req.getRequestDispatcher("/newspages/OprSuccess.jsp").forward(req, resp);
				}
				
			}
			
		}
		//获取此主题下所有新闻信息
		else if(opr.equals("themenews")){
			String themename1 = req.getParameter("themename");
			String themename = new String(themename1.getBytes("iso-8859-1"),"utf-8");
			List<newspaper> nl = new ArrayList<newspaper>();
			try {
				nl = usi.GetTNews(themename);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				resp.sendRedirect("newspages/OprFail.jsp");
				e.printStackTrace();
			}
			if(nl==null){
				req.setAttribute("errordata", "这个主题没有任何新闻");
				req.getRequestDispatcher("/newspages/usernewslist_error.jsp").forward(req, resp);
			}else{
				req.setAttribute("usernewspaperdata", nl);
				req.getRequestDispatcher("/newspages/apage.jsp").forward(req, resp);
			}
		}
		//模糊查询
		else if(opr.equals("likesearch")){
			String object = req.getParameter("object");
			String data = req.getParameter("data");
			if(object.equals("title")){
				List<newspaper> n1 = new ArrayList<newspaper>();
				try {
					n1 = usi.NewsLikeSearch(data);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					resp.sendRedirect("newspages/OprFail.jsp");
					e.printStackTrace();
				}
				if(n1 == null){
					req.setAttribute("errordata", "没有找到合适的数据");
					req.getRequestDispatcher("/newspages/usernewslist_error.jsp").forward(req, resp);
				}else{
					req.setAttribute("usernewspaperdata", n1);
					req.getRequestDispatcher("/newspages/apage.jsp").forward(req, resp);
				}
			}
			else if(object.equals("username")){
				List<user> u1 = new ArrayList<user>();
				boolean isok= false;
				try {
					u1 = usi.UserLikeSearch(data);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					resp.sendRedirect("newspages/OprFail.jsp");
				}
				if(u1 == null){
					req.setAttribute("errordata", "没有找到合适的数据");
					req.getRequestDispatcher("/newspages/usernewslist_error.jsp").forward(req, resp);
				}else{
					req.setAttribute("udatalist", u1);
					req.getRequestDispatcher("/newspages/finduserlist.jsp").forward(req, resp);
				}
			}
			
		}
		//增加评论点赞 add commment like
		else if(opr.equals("acl")){
			String idcomment = req.getParameter("idcomment");
			String newsid = req.getParameter("newsid");
			String userid = ((user)(req.getSession().getAttribute("userdata"))).iduser;
			boolean isok =false;
			try {
				isok = usi.AddComlike(userid, idcomment);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resp.sendRedirect("newspages/OprFail.jsp");
			}
			if(!isok){
				resp.sendRedirect("newspages/OprFail.jsp");
			}else{
				req.getRequestDispatcher("UserServlet?opr=viewnews&newsid="+newsid).forward(req, resp);
			}
		}
		//添加新闻评论
		//查看新闻的内容
		else if(opr.equals("viewnews")){
			String newsid = req.getParameter("newsid");
			user tu = ((user)(req.getSession().getAttribute("userdata")));
			String iduser;
			if(tu!=null){
			iduser = tu.iduser;
			}else{
				iduser = null;
			}
			List<comment> cl = new ArrayList<comment>();
			List<String> com_author = new ArrayList<String>();
			newspaper n1 = new newspaper();
			List<String> data = new ArrayList<String>();
			List<String> colname = new ArrayList<String>();
			String islike = "false";
			String iscollect = "false";
			List<String> clv = new ArrayList<String>();
			//得到新闻信息
			n1 = usi.m1.datasearch("newspaper", newsid, "newsid", 1, newspaper.class).get(0);
			//查询作者姓名
			String authorname = usi.m1.datasearch("user", n1.authorid, "iduser", 1, user.class).get(0).username;
			//查询评论列表
			cl = usi.m1.datasearch("comment", newsid, "newsid", Integer.MAX_VALUE, comment.class);
			//浏览量+1
			String numview = (Integer.parseInt(n1.newsviewsnum)+1)+"";
			news_container nc1 = new news_container(n1);
			List<String> imageurl = array2List(n1.imageurl.split(";"));
			if(iduser==null){//没登录查看
				req.setAttribute("isuser", "0");
			}else{
				try {
					clv = usi.ViewNews(iduser, newsid);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				iscollect =  clv.get(0);
				islike =  clv.get(1);
				System.out.println("iscollect:"+iscollect);
				System.out.println("islike:"+islike);
				data.add(numview);
				colname.add("newsviewsnum");
				req.setAttribute("isuser", "1");
				usi.m1.editdata("newspaper", newsid, "newsid", data, colname, newspaper.class);//浏览量+1
				
			}
			for(int i = 0; cl!=null&&i < cl.size(); i++){
				com_author.add(usi.m1.datasearch("user", cl.get(i).userid, "iduser", 1, user.class).get(0).username);
			}
			req.setAttribute("imageurl", imageurl);
			req.setAttribute("iscollect", iscollect);
			req.setAttribute("islike", islike);
			req.setAttribute("cl", cl);
			req.setAttribute("com_author", com_author);
			req.setAttribute("authorname", authorname);
			req.setAttribute("newdata", nc1);
			req.getRequestDispatcher("/newspages/newsview.jsp").forward(req, resp);
		}

		// 删除新闻dn-->delnews
		else if(opr.equals("delnews")){
			String newsid = req.getParameter("newsid");
			boolean isok = false;
			try {
				isok = usi.Delnews(newsid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				resp.sendRedirect("newspages/OprFail.jsp");
				e.printStackTrace();
			}
			if(isok){
				req.getRequestDispatcher("/newspages/OprSuccess.jsp").forward(req, resp);
			}else{
				resp.sendRedirect("newspages/OprFail.jsp");
			}
		}
		else if(opr.equals("jumpdeluser")){
			req.setAttribute("confirm_text", "是否要彻底删除您的账号?");
			req.setAttribute("opr_url", "UserServlet?opr=deluser");
			req.getRequestDispatcher("/newspages/ConfirmOpr.jsp").forward(req, resp);
		}
			// 删除用户du-->delusers
		else if(opr.equals("deluser")){
			String userid = ((user)(req.getSession().getAttribute("userdata"))).iduser;
			boolean isok = false;
			try {
				isok = usi.DelUser(userid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				resp.sendRedirect("newspages/OprFail.jsp");
				e.printStackTrace();
			}
			if(isok){
				req.setAttribute("isdel", "1");
				req.getRequestDispatcher("/newspages/OprSuccess.jsp").forward(req, resp);
			}else{
				resp.sendRedirect("newspages/OprFail.jsp");
			}
		}
		// 修改用户个人信息eu-->edituser
		else if(opr.equals("jumpedituser")){
			req.getRequestDispatcher("/newspages/EditUserData.jsp").forward(req, resp);
		}
		else if(opr.equals("edituserdata")){
			String iduser = req.getParameter("iduser");
			String password = req.getParameter("password");
			String username = req.getParameter("username");
			user u1 = new user();
			u1.iduser = iduser;
			u1.username = username;
			u1.userpassword = password;
			boolean isok = false;
			try {
				isok = usi.EditUser(u1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(isok){
				resp.sendRedirect("index.jsp");
			}else{
				resp.sendRedirect("newspages/OprFail.jsp");
			}
		}

	}

}
