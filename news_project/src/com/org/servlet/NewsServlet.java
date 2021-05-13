package com.org.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.org.entity.News;
import com.org.entity.Themenews;
import com.org.service.NewsService;
import com.org.service.NewsServiceImpl;

public class NewsServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1199633259982740793L;
    //展示国内新闻
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 //指定服务器响应给浏览器的编码
		resp.setContentType("text/html;charset=utf-8");
		//设置客户端请求和数据库取值时的编码
		req.setCharacterEncoding("utf-8");
		//获取项目的根路径
//		String contextPath=req.getContextPath();
		//创建NewsService对象
		NewsService newsService=new NewsServiceImpl();
			//调用Newsservice层的新闻展示
			List<News> localnews=null;
			List<News> foriennews = null;
			List<News> amusenews = null;
			try {
				localnews=newsService.getNews("财经");
				foriennews = newsService.getNews("游戏");
				amusenews = newsService.getNews("娱乐");
				//把新闻内容保存到req对象中
				req.setAttribute("localnews", localnews);
				req.setAttribute("foriennews", foriennews);
				req.setAttribute("amusenews", amusenews);
				//转发到index_sidebar.jsp
				List<Themenews> tnl = new ArrayList<Themenews>();
				tnl = newsService.GetTheme();
				req.setAttribute("tnl", tnl);
				req.getRequestDispatcher("/index.jsp").forward(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				resp.sendRedirect("/newspages/OprFail.jsp");
				e.printStackTrace();
			}

			}
		
		}
	


    

    

