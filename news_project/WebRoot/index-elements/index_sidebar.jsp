<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
  <div class="sidebar">
    <h1> <img src="images/title_1.gif" alt="财经新闻" /> </h1>
    <div class="side_list">
      <ul>
        <!--  <a href="util/News" >查看</a> -->
        <c:forEach items="${localnews}" var="localnews">
        	<a href="UserServlet?opr=viewnews&newsid=${localnews.newsid}"><li>${localnews.newstitle}</li></a>
        </c:forEach>
      </ul>
    </div>
    <h1> <img src="images/title_2.gif" alt="游戏新闻" /> </h1>
    <div class="side_list">
      <ul>
            <c:forEach items="${foriennews}" var="foriennews">
        		<a href="UserServlet?opr=viewnews&newsid=${foriennews.newsid}"><li>${foriennews.newstitle}</li></a>
        	</c:forEach>
      </ul>
    </div>
    <h1> <img src="images/title_3.gif" alt="娱乐新闻" /> </h1>
    <div class="side_list">
      <ul>
      	    <c:forEach items="${amusenews}"  var="amusenews">
      	    <a href="UserServlet?opr=viewnews&newsid=${amusenews.newsid}"><li>${amusenews.newstitle}</li></a>
      	    </c:forEach>
      </ul>
    </div>
  </div>
<%--
	request.removeAttribute("news_in_topic");
--%>
