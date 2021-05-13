<%@ page language="java" import="java.util.*" import="com.org.entity.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<style type="text/css">
    body{
    }
    table .table-striped{
    }
    table th{
        text-align: left;
        height: 30px;
        background: #deeeee;
        padding: 5px;
        margin: 0;
        border: 0px;
    }
    table td{
        text-align: left;
        height:30px;
        margin: 0;
        padding: 5px;
        border:0px
    }
    table tr:hover{
        background: #eeeeee;
    }
    .span6{
        /*width:500px;*/
        float:inherit;
        margin:10px;
    }
    #pagiDiv span{
        background:#1e90ff;
        border-radius: .2em;
        padding:5px;
    }
</style>
<script type="text/javascript" src="js/pagination.js"></script>
    <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript">
        //全局变量
        var numCount;       //数据总数量
        var columnsCounts;  //数据列数量
        var pageCount;      //每页显示的数量
        var pageNum;        //总页数
        var currPageNum ;   //当前页数

        //页面标签变量
        var blockTable;
        var preSpan;
        var firstSpan;
        var nextSpan;
        var lastSpan;
        var pageNumSpan;
        var currPageSpan;



        window.onload=function(){
            //页面标签变量
            blockTable = document.getElementById("blocks");
            preSpan = document.getElementById("spanPre");
            firstSpan = document.getElementById("spanFirst");
            nextSpan = document.getElementById("spanNext");
            lastSpan = document.getElementById("spanLast");
            pageNumSpan = document.getElementById("spanTotalPage");
            currPageSpan = document.getElementById("spanPageNum");

            numCount = document.getElementById("blocks").rows.length - 1;       //取table的行数作为数据总数量（减去标题行1）
            alert(numCount)
            columnsCounts = blockTable.rows[0].cells.length;
            pageCount = 5;
            pageNum = parseInt(numCount/pageCount);
            if(0 != numCount%pageCount){
                pageNum += 1;
            }

            firstPage();
        };
    </script>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻中国</title>
<link href="css/read.css" rel="stylesheet" type="text/css" />
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
  <%if(udata!=null){ %>
  <div id="status">管理员：<%=udata.username %>  &#160;&#160;&#160;&#160; <a href="UserServlet?opr=jumplogin">login out</a></div>
  <%}else{ %>
  <div id="status">您还未登录  &#160;&#160;&#160;&#160; <a href="UserServlet?opr=jumplogin">这在里登录</a></div>
  <%} %>
  <div id="channel"> </div>
</div>
<div id="container">
   		  <%news_container nc1 = (news_container)request.getAttribute("newdata"); %>
          <%String authorname = (String)request.getAttribute("authorname"); %>
          <% List<String> com_author = (List)request.getAttribute("com_author"); %>
          <%  List<comment> cl = (List)request.getAttribute("cl"); %>
          <% String iscollect = (String)request.getAttribute("iscollect"); %>
          <%  String islike = (String)request.getAttribute("islike"); %>
          <% String isuser = (String)request.getAttribute("isuser"); %>
          <% List<String> imageurl = (List)request.getAttribute("imageurl"); %>
    <div class="main">
    <div class="class_type"> <img src="images/class_type.gif" alt="新闻中心" /> </div>
    <div class="content">
        <table width="80%" align="center">
          <tr width="100%">
            <td colspan="2" align="center"></td>
          </tr>
          <tr>
            <td colspan="2"><%=nc1.newstitle %><hr />
            </td>
          </tr>
          <tr>
          <%if(nc1.hidename.equals("0") ){ %>
            <td align="center">作者：<a href="UserServlet?opr=authorpage&iduser=<%=nc1.authorid %>"><%=authorname %></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </td>
            <%}else{ %>
             <td align="center">作者：匿名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <% }%>
            <td align="left">发布时间：<%=nc1.newstime %></td>
          </tr>          
          <tr>
          <% for(int i = 0; imageurl!=null&&i < imageurl.size(); i++ ){%>
         	 <img src="<%=imageurl.get(i) %>"  alt="<%=nc1.newstitle %>" />
          <%}%> 
            <td colspan="2" align="center"><%=nc1.news_content %></td>
            
          </tr>
          <tr>
            <td colspan="2"></td>
          </tr>
            <tr>
            <td colspan="2" align="center">文章原文地址:<a href="<%=nc1.newsOurl %>"><%=nc1.newsOurl %></a></td>
            
          </tr>
            <td colspan="2"><hr />
            </td>
          </tr>
        </table>
      </ul>
             
    </div>
    <%if(iscollect.equals("false")&&isuser.equals("1")){ %>
     <td align="left"><a href="UserServlet?opr=acn&newsid=<%=nc1.newsid %>">收藏:<%=nc1.newscollect %></a>&nbsp;&nbsp;&nbsp;
     <%}else{ %>
     <td align="left">已收藏:<%=nc1.newscollect %>&nbsp;&nbsp;&nbsp;
     <% }%>
     <%if(islike.equals("false")&&isuser.equals("1")){ %>
     <a href="UserServlet?opr=addlike&newsid=<%=nc1.newsid %>">点赞:<%=nc1.newslike %></a>&nbsp;&nbsp;&nbsp;
     <%}else{ %>
   		点赞:<%=nc1.newslike %>&nbsp;&nbsp;&nbsp;
     <%} %>
     	浏览量:<%=nc1.newsviewsnum %>&nbsp;&nbsp;&nbsp;</td>
     <br/>
     
    <br/>
    <br/>
	<td align="left" ><font size="3" color="red">&nbsp;&nbsp;&nbsp;评论&nbsp;&nbsp;&nbsp;</font></td>
			<label>写评论：</label>
	<br/>
		<p>
		<%if(isuser.equals("1")){ %>
		<form action="UserServlet?opr=addcomment" method="post">
		<textarea cols="50" rows="10" name="textarea"  >发个友善的评论见证当下</textarea>
		<input name="userid" type="hidden" value="<%=udata.iduser %>"/>
		<input name="username" type="hidden" value="<%=udata.username %>"/>
		<input name="newsid" type="hidden" value="<%=nc1.newsid %>"/>
		</p>
	      <input type="submit" value="提交" class="opt_sub"/>
      <input type="reset" value="重置" class="opt_sub" />
    </form>
    <% }else{%>
    <h1><a href="UserServlet?opr=jumplogin">您还没有登录还不可以发表评论哦,快去登陆吧</a></h1>
    <% }%>
	<table id="blocks" class="table table-striped" style="margin-top:25px">
        <tr>
            <th>评论内容</th>
            <th>点赞量</th>

            <th class="hidden-phone">作者</th>
        </tr>
        <%if(cl==null){ %>
        <tr>
            <td></font></td>
            <td></td>

            <td class="hidden-phone"></td>
        </tr>
        <% }%>
         <%if(cl!=null){ %>
		<%for(int i = 0;cl!=null&&i < cl.size(); i++ ){ %>
        <tr>
            <td><%=cl.get(i).content %></font></td>
            <% if(isuser.equals("1") ){ %>
            <td><a href="UserServlet?opr=acl&idcomment=<%=cl.get(i).idcomment %>&newsid=<%=nc1.newsid %>"><%=cl.get(i).clike %></a></td>
			<%}else{ %>
			<td><%=cl.get(i).clike %></td>
			<% }%>
            <td class="hidden-phone"><a href="UserServlet?opr=authorpage&iduser=<%=cl.get(i).userid %>"><%=com_author.get(i) %></a></td>
        </tr>
        <%} }%>
        </table>
         <div id="pagiDiv" align="center" style="width:1200px">
        <span id="spanFirst">First</span>  
        <span id="spanPre">Pre</span>  
        <span id="spanNext">Next</span>  
        <span id="spanLast">Last</span>  
        The <span id="spanPageNum"></span> Page/Total <span id="spanTotalPage"></span> Page
	<br></br>
  </div>
</div>
  </div>
<%--
    request.removeAttribute("news_view");
    request.removeAttribute("comments_view");
--%>
<%@ include file="../index-elements/index_bottom.html"%>
</body>
</html>
     