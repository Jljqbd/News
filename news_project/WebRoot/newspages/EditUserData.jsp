<%@ page language="java" import="java.util.*,java.sql.*" pageEncoding="utf-8" import="com.org.entity.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改个人信息--管理后台</title>
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
    <h1 id="opt_type"> 修改个人信息： </h1>
    <form action="UserServlet?opr=edituserdata" method="post">
      <p>
        <label> 用户姓名： </label>
        <input name="username" type="text" class="opt_input" id="username" value="<%=udata.username %>"/>
     </p>
     <br></br>
     <p>
     <label> 用户新密码： </label>
        <input name="password" type="password" class="opt_input" id="password" value="<%=udata.userpassword %>"/>
		</p>
		<br></br>
      <input name="iduser" type="hidden" value="<%=udata.iduser %>"/>
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
