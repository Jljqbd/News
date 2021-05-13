<%@ page language="java" import="java.util.*,java.sql.*" pageEncoding="utf-8" import="com.org.entity.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻中国</title>
<link href="css/main.css" rel="stylesheet" type="text/css" />

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
    <script type="text/javascript" src="pagination.js"></script>
    <script type="text/javascript" src="jquery-1.11.1.min.js"></script>
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
            
            columnsCounts = blockTable.rows[0].cells.length;
            pageCount = 14;
            pageNum = parseInt(numCount/pageCount);
            if(0 != numCount%pageCount){
                pageNum += 1;
            }

            firstPage();
        };
    </script>


</head>

<body>
  
<div id="header">
  <div id="top_login">
    <form action="UserServlet?opr=login" method="post">
      <label> 登录名 :</label>
      <input type="text" name="uname" class="login_input" />
      <label> 密&#160;&#160;码 </label>
      <input type="password" name="upwd" class="login_input" />
      <input type="submit" class="login_sub" value="登录" />
      <label id="error"> </label>
      <label><a href="UserServlet?opr=jumpreg">注册新账户</a></label>
      <img src="images/friend_logo.gif" alt="Google" id="friend_logo" />
    </form>
  </div>
  <div id="nav">
    <div id="logo"> <img src="images/logo.jpg" alt="新闻中国" /> </div>
    <div id="a_b01"> <img src="images/a_b01.gif" alt="" /> </div>
    <!--mainnav end-->
  </div>
</div>
<div id="container">

<%@ include file="index-elements/index_sidebar.jsp"%>
 
  <div class="main">
    <div class="class_type"> <img src="images/class_type.gif" alt="新闻中心" /> </div>
    <div class="content" style="width:500px">
    
    
       <table id="blocks" class="table table-striped" style="margin-top:10px">
            <tr>
            <th style="width:350px">主题名称</th>
            <th style="width:50px">新闻数</th>
            <th style="width:50px">最新发布时间</th>
           	</tr>
 <!--       <ul class="class_date">--> 
			 <% List<Themenews> tnl = (List)request.getAttribute("tnl"); %>
			 <%if(tnl!=null){ %>
			 <%for(int i = 0; i < tnl.size(); i++){ %>
		      	<tr>
		      	   	<td ><a href="UserServlet?opr=themenews&themename=<%=tnl.get(i).themename %>"><li><%=tnl.get(i).themename %></li></a><font color="green"></font></td>
		        	<td><%=tnl.get(i).newsnum %></td>
		        	<td><%=tnl.get(i).newstime %></td>
		        </tr>
		        <% }}else{%>
		        <tr>
		        <td></td>
		        <td></td>
		        <td></td>
		        </tr>
      	   <%} %>
 <!--     </ul>--> 
      </table>
      
      
      <!-- <ul class="classlist">--> 
      
    <div id="pagiDiv" align="center" style="width:500px">
        <span id="spanFirst">First</span>  
        <span id="spanPre">Pre</span>  
        <span id="spanNext">Next</span>  
        <span id="spanLast">Last</span>  
        The <span id="spanPageNum"></span> Page/Total <span id="spanTotalPage"></span> Page
    </div>
    
     <!--  <p align="center"> 当前页数:[1/5]&nbsp;
      </p>
      </ul>--> 
    </div>
    <%@ include file="index-elements/index_rightbar.jsp"%>
  </div>
</div>
  <%@ include file="index-elements/index_bottom.html"%>
</body>
</html>
