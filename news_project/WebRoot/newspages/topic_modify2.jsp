<%@ page language="java" import="java.util.*" pageEncoding="utf-8" import="com.org.entity.theme" import="com.org.entity.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<title>添加主题--管理后台</title>
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
<div id="main">
  <%@ include file="console_element/left.html" %>
  <div id="opt_area">
    <h1 id="opt_type"> 修改主题： </h1>
    <form action="topicServlet" method="post" onsubmit="return check()">
      <p>
        <label> 主题名称 </label>
        <select name="otname">
      <%--   	 <c:forEach items="${topicList}" var="topic">
			<option value="${topic.themename}">${topic.themename}</option>
		</c:forEach>
		--%>
		<option value="0" selected>请选择：</option>
		<%List<String> list = (List)request.getAttribute("topicList");%>
		<% for(int i=0;i<list.size();i++){%>
		<option value="<%=list.get(i) %>"><%=list.get(i) %></option>
		<%} %>
        </select>
        <label> &nbsp; &nbsp; &nbsp;修改为 </label>
        <input name="tname" type="text" class="opt_input" value=""/>
      </p>
      <input name="opr" type="hidden" value="modify2"/>
      <input type="submit" value="提交" class="opt_sub" />
      <input type="reset" value="重置" class="opt_sub" />
    </form>  
     <form action="topicServlet" method="post" >
      <p>
        <label> 主题名称 </label>
        <select name="dtname">
		<option value="0" selected>请选择：</option>
		<% for(int i=0;i<list.size();i++){%>
		<option value="<%=list.get(i) %>"><%=list.get(i) %></option>
		<%} %>
        </select>
      </p>
      <input name="opr" type="hidden" value="delete"/>
      <input type="submit" value="删除" class="opt_sub" />
    </form>
    
  </div>
</div>
<%@ include file="console_element/bottom.html" %>
