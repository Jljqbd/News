<%@ page language="java" import="java.util.*,java.sql.*" pageEncoding="utf-8" import="com.org.entity.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发布新闻--管理后台</title>
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
  <% news_container ncdata = (news_container)request.getAttribute("ncdata");%>
<div id="admin_bar">
  <div id="status">管理员：<%=udata.username %>  &#160;&#160;&#160;&#160; <a href="UserServlet?opr=jumplogin">login out</a></div>
  <div id="channel"> </div>
</div>
<div id="main">
  <%@ include file="console_element/left.html" %>
  <div id="opt_area">
  <%String oprname = (String)request.getAttribute("oprname"); %>
    <h1 id="opt_type"> <%=oprname%>新闻： </h1>
    <%String isedit = (String)request.getAttribute("isedit"); %>
    <form action="UserServlet?opr=upnews&isedit=<%=isedit%>" method="post">
      <p>
        <label> 新闻标题： </label>
        <input name="tname" type="text" class="opt_input" id="tname" value="<%=ncdata.newstitle %>"/>
     </p>
     <br></br>
     <p>
        <% List<String> themelist = (List)request.getAttribute("themelist");%>
        <label> 新&nbsp;闻 主&nbsp;题 ：</label>
        <!-- 后端直接request.getParameter("themelist"); -->
        <select name="selecttheme">
        <% for(int i = 0; i < themelist.size(); i++){ %>
  		<option value ="<%=themelist.get(i) %>"><%=themelist.get(i) %></option>
  		<% }%>
		</select>
		</p>
		<br></br>
		<p>
		<label>是&nbsp;否 匿&nbsp;名：</label>
		&nbsp;&nbsp;是：
		<input type="radio" checked="checked" name="hidename" value="1" />
		&nbsp;&nbsp;否：
		<input type="radio" name="hidename" value="0" />
		</p>
		<br></br>
		<p>
		<label>新闻内容：</label>
		<textarea cols="50" rows="10" name="textarea"><%=ncdata.news_content %></textarea>
		</p>
		<br></br>
      <input name="authorid" type="hidden" value="<%=ncdata.authorid%>"/>
      <input name="imageurl" type="hidden" value="<%=ncdata.imageurl %>"/>
      <input name="newscollect" type="hidden" value="<%=ncdata.newscollect %>"/>
      <input name="newsid" type="hidden" value="<%=ncdata.newsid %>"/>
      <input name="newslike" type="hidden" value="<%=ncdata.newslike %>"/>
      <input name="newsOurl" type="hidden" value="<%=ncdata.newsOurl %>"/>
      <input name="newstime" type="hidden" value="<%=ncdata.newstime %>" />
      <input name="newsviewnum" type="hidden" value="<%=ncdata.newsviewsnum %>"/>
      <input name="username" type="hidden" value="<%=ncdata.username %>"/>
      <input type="submit" value="提交" class="opt_sub"/>
      <input type="reset" value="重置" class="opt_sub" />
    </form>
  </div>
</div>
<div id="footer">
  <%@ include file="console_element/bottom.html" %>
</div>
</body>
</html>
