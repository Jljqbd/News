package com.org.servlet;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.util.List;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.org.entity.theme;
import com.org.entity.user;
import com.org.service.TopicService;
import com.org.service.TopicServiceImpl;


//import com.org.entity.Topic;
//import com.org.service.topic.TopicService;
//import com.org.service.topic.TopicServiceImpl;

/**
 * 用来处理主题相关的请求
 * @author asus
 *
 */
public class TopicServlet extends HttpServlet{
	private static final long serialVersionUID = -145866155613769906L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	        doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		 //指定服务器相应给浏览器的编码
		resp.setContentType("text/html;charset=utf-8");
		//设置客户端请求和数据库取值时的编码
		req.setCharacterEncoding("utf-8");
		//获取项目的根路径
		String contextPath=req.getContextPath();
		//需要创建TopicService对象
		TopicService topicService=new TopicServiceImpl();
		//TopicService topicService=new TopicServiceImpl();
		//获取代表操作的标识符opr的值
		String opr = req.getParameter("opr");
		//根据opr的值去判断应该执行什么操作
		if("addtopic".equals(opr)){//代表执行添加主题
			//获取主题名称
			String uid = ((user)(req.getSession().getAttribute("userdata"))).iduser;
			String tname=req.getParameter("dtname");
			System.out.println("添加的主题名:"+tname);
			try {
				int result=topicService.addTopic(tname, uid);
					if(result>0){
						req.getRequestDispatcher("/newspages/OprSuccess.jsp").forward(req, resp);
					//resp.sendRedirect("topicServlet?opr=list");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resp.sendRedirect("/newspages/OprFail.jsp");
			}
			
		}
		//跳转到新闻界面
		else if(opr.equals("jumpaddtopic")){
			String uid = ((user)(req.getSession().getAttribute("userdata"))).iduser;
			List<theme> alltheme = new ArrayList<theme>();
			
			List<String> usertheme = new ArrayList<String>();
			List<String> Ndt = new ArrayList<String>();
			try {
				alltheme = topicService.getAllTopics();
				usertheme = topicService.GetUserTopic(uid);
				Ndt = topicService.CompareTheme(alltheme, usertheme);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			req.setAttribute("theme", Ndt);
			req.getRequestDispatcher("newspages/topic_add.jsp").forward(req, resp);
		}
		else if("list".equals(opr)){
			//调用topicService获取所有主题列表
			List<String> topicList = new ArrayList<String>();
			String uid = ((user)(req.getSession().getAttribute("userdata"))).iduser;
			try {
				topicList=topicService.GetUserTopic(uid);
				//主题保存到request对象中
				req.setAttribute("topicList", topicList);
				//转发topic_list.jsp
				req.getRequestDispatcher("newspages/topic_list.jsp").forward(req, resp);
				//同时将值传给modify2
				} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resp.sendRedirect("/newspages/OprFail.jsp");
			}
		}else if("modify1".equals(opr)){
		//1显示list
			
			//调用topicService获取所有主题列表
			List<String> topicList=null;
			try {
				topicList = topicService.GetUserTopic(((user)(req.getSession().getAttribute("userdata"))).iduser);
				//主题保存到request对象中
				req.setAttribute("topicList", topicList);
				//转发topic_list.jsp
				req.getRequestDispatcher("/newspages/topic_modify2.jsp").forward(req, resp);
				//同时将值传给modify2
				} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("modify2".equals(opr)){
		//2修改
			String otname=req.getParameter("otname");
			String tname=req.getParameter("tname");
			try {
				int result=topicService.modify(otname, tname, ((user)(req.getSession().getAttribute("userdata"))).iduser);
					if(result>0){
						req.getRequestDispatcher("/newspages/OprSuccess.jsp").forward(req, resp);
					//resp.sendRedirect("topicServlet?opr=list");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				resp.sendRedirect("/newspages/OprFail.jsp");
				e.printStackTrace();
			}
			
		}
		else if("delete".equals(opr)){
			//2修改
				String dtname=req.getParameter("dtname");
				try {
					int result=topicService.delete(dtname, ((user)(req.getSession().getAttribute("userdata"))).iduser);
						if(result>0){
						//resp.sendRedirect(contextPath+"/newspages/topic_list.jsp");
						resp.sendRedirect("topicServlet?opr=list");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
	}
 
}
