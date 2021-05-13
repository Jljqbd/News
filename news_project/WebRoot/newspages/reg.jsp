<%@ page language="java" import="java.util.*,java.sql.*" pageEncoding="utf-8" import="com.org.entity.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>注册</title>
<meta http-equiv='content-type' content='text/html;charset=utf-8'/> 
        <link rel="stylesheet" type="text/css" href="css/reg.css">
        <script type='text/javascript' src='js/jquery-1.7.2.js'></script> 
        <script type='text/javascript'> 
        //校验验证码   
        function validate(){
            
            var password=document.getElementById("password").value;
            if (password.length<8){
                alert("密码至少为8位！");      
            }else { //输入正确时   
                alert("合格！^-^"); 
            } 
        } 
        </script> 
        <style type='text/css'> 
        #code{ 
            font-family:Arial,宋体; 
            font-style:italic; 
            color:blue;
            size:12px;            
            border:0; 
            padding:2px 3px; 
            letter-spacing:8px; 
            font-weight:bolder; 
        } 
        </style> 
</head>
<body οnlοad='createCode()'>
<div id="d1">
<table border=0><tr><th width=700 height=400></th><th>
<form action="UserServlet?opr=reg" method="post">
<table border=0 height=400 >
<tr><th height=65><font  size='4'>用 户 名</th><th><input type="text" style="height:40px" placeholder="请输入用户名" size=40 name="username" id="username"></th></tr>
<tr><th height=65><font  size='4'>密    码</th><th><input type="password" style="height:40px" placeholder="密码长度为6~18字符" size=40 id="password" name="password"></th></tr>
<tr><th height=65><font  size='4'>确认密码</th><th><input type="password" style="height:40px" placeholder="请重新输入密码" size=40 id="password1" name="password"></th></tr>
<tr><th height=65><font  size='4'>联系方式</th><th><input type="text" style="height:40px" placeholder="+86" size=40 name="userphone"></th></tr>
            <!--<input type = "button"  style="height:40px"  value = "验证" onclick = "validate()"/> -->
            </th>
        </div>  </tr>
        
        <tr><th colspan=2 height=65><input type="checkbox"><font size='2' color='red' required/>我已阅读并同意相关条例</font></input></th></tr>
<tr><th colspan=2 height=65><input type='submit' style="background-color:red;height:55px;width:160px;font-size:25px;color:white;border:none" value='注册'id='l' onclick = "validate()" ></th></tr>
</table>
</form></th></tr></table>
</div>
</body>
</html>