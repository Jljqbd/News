<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>危险操作确认</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/success.css" rel="stylesheet" type="text/css" />
	 <%String confirm_text = (String)request.getAttribute("confirm_text"); %>
    <%String opr_url = (String)request.getAttribute("opr_url"); %>

  </head>
  <body>
    <div class="success">
    <h1><%=confirm_text%></h1>
    <h1><h1>
    <h1><a href="UserServlet?opr=jumphomepage">否返回主页</a></h1><h1></h1><a href="<%=opr_url%>">我已知晓,继续执行</h1>
    </div><br>
  </body>
</html>
    