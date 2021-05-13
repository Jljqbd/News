<%@ page language="java" import="java.util.*,java.sql.*" pageEncoding="utf-8" import="com.org.entity.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<title>作者发布过的新闻</title>
<link href="css/admin.css" rel="stylesheet" type="text/css" />
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
</head>
<body align="center">
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

<div id="main">
   <div class="container" align="center" >
   <% List<user> udatalist = (List)request.getAttribute("udatalist");%>
        <div id="opt_area">
    <h2 align="center">用户查找结果</h2>
     <table id="blocks" class="table table-striped" style="margin-top:25px">
        <tr>
            <th>用户姓名</th>
            <th>注册时间</th>

            <th class="hidden-phone">个人主页</th>
        </tr>
		<%for(int i = 0;i < udatalist.size(); i++ ){ %>
        <tr>
            <td><%=udatalist.get(i).username %></font></td>
            <td><%=udatalist.get(i).registertime %></td>

            <td class="hidden-phone"><a href="UserServlet?opr=authorpage?iduser=<%=udatalist.get(i).iduser %>">个人主页</a></td>
        </tr>
        <%} %>
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
  </div>
<div id="footer">
  <%@ include file="console_element/bottom.html" %>
</div>
</body>
</html>