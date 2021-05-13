<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>操作成功</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/success.css" rel="stylesheet" type="text/css" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <div class = "success">
    <h1> 操 作 成 功 </h1>
    <h1><h1>
    <%String isdel = (String)request.getAttribute("isdel"); %>
    <%if(isdel==null){ %>
    <h1><a href="UserServlet?opr=jumphomepage">返回主页</a></h1>
    <%}else{ %>
    <h1><a href="hello.jsp">返回主页</a></h1>
    <% }%>
    </div><br>
  </body>
</html>
