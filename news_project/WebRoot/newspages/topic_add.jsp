<%@ page language="java" import="java.util.*" pageEncoding="utf-8" import="com.org.entity.*"%>
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
<% user udata = (user)session.getAttribute("userdata");%>
<div id="admin_bar">
  <div id="status">管理员：<%=udata.username %>  &#160;&#160;&#160;&#160; <a href="UserServlet?opr=jumplogin">login out</a></div>
  <div id="channel"> </div>
</div>
<div id="main">
  <%@ include file="console_element/left.html" %>
  <div id="opt_area">
    <h1 id="opt_type"> 添加主题： </h1>
    <form action="topicServlet" method="post">
      <p>
        <label> 主题名称 </label>  
        <%List<String> theme = (List)request.getAttribute("theme"); %>
        <select name="dtname">
		<option value="0" selected>请选择：</option>
		<% for(int i=0; i < theme.size(); i++){%>
		<option value="<%=theme.get(i) %>"><%=theme.get(i) %></option>
		<%} %>
        </select>
      </p>
      <input name="opr" type="hidden" value="addtopic"/>
      <input type="submit" value="提交" class="opt_sub" />
      <input type="reset" value="重置" class="opt_sub" />
    </form>
  </div>
</div>
<div id="footer">
  <%@ include file="console_element/bottom.html" %>
</div>
</body>
</html>
