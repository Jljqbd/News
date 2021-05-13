<%@ page language="java" import="java.util.*,java.sql.*" pageEncoding="utf-8" import="com.org.entity.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<title>所有主题--管理后台</title>
<link href="css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="header">
  <div id="welcome">欢迎使用新闻管理系统！</div>
  <div id="nav">
    <div id="logo"><img src="images/logo.jpg" alt="新闻中国" /></div>
    <div id="a_b01"><img src="images/a_b01.gif" alt="" /></div>
  </div>
</div>
<% user udata = (user)request.getSession().getAttribute("userdata");%>
<div id="admin_bar">
  <div id="status">管理员：<%=udata.username %>  &#160;&#160;&#160;&#160; <a href="UserServlet?opr=jumplogin">login out</a></div>
  <div id="channel"> </div>
</div>
<div id="main">
  <%@ include file="console_element/left.html" %>
  <div id="opt_area">
    <ul class="classlist">
         
           <%List<String> themeList=(List)request.getAttribute("topicList"); %>
        	<%for(int i=0;i<themeList.size();i++){ %>
        	<li>&nbsp;&nbsp;&nbsp;<%=themeList.get(i) %>&nbsp;&nbsp;&nbsp;<a href="topicServlet?opr=modify1">修改</a>&nbsp;&nbsp;&nbsp;<a href="topicServlet?opr=modify1">删除</a></li>
        <% }%>
      
    </ul>
  </div>
</div>
<div id="footer">
  <%@ include file="console_element/bottom.html" %>
</div>
</body>
</html>
