<%@page import="dao.impl.UserDaoImpl"%>
<%@ page language="java" import="domain.UserAccount,dao.impl.UserDaoImpl" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 引入bootstrap -->
<link href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
<!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
	<!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
	<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
		<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
	<![endif]-->

<title>IFTTT</title>

<!-- jQuery (Bootstrap 的 JavaScript 插件需要引入 jQuery) -->
<script src="https://code.jquery.com/jquery.js">
</script>

<script type="text/javascript">
<%
	String userMailAccount = null;
	userMailAccount = (String)session.getAttribute("userMailAccount");
	if(null == userMailAccount){
		response.sendRedirect("index.jsp");
		return;  
	}
	else if (userMailAccount.compareTo("admin") == 0){
		response.sendRedirect("administratorPage.jsp");
		return;  
	}
	String password = (new UserDaoImpl()).find(userMailAccount);
	UserAccount user = (new UserDaoImpl()).find(userMailAccount, password);
%>
var userID = <%=user.getMailAccount()%>;
var userName = <%=user.getUsername()%>;

$(document).ready(function(){
	$("#userName").html("Hi~ "+userName);

});
</script>

</head>
<body>
<div class="form-group" style= "padding-top: 1%;background-color: #87CEFA;" >
	<strong class="col-md-offset-1 col-sm-offset-1" style = "font-size:120%;color:#FFFFFF">IFTTT</strong>
	<p id = "userName" class="col-md-offset-8 col-sm-offset-8" style = "font-size:120%;color:#FFFFFF"></p>
</div>

<div class="container">
	<h1>新浪微博</h1>
	<p>请在以下两项中选择一项this任务</p>
	<br></br>
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class = "col-md-offset-1 col-md-10 col-sm-8 col-xs-8">
				<form id = "weibo-login" class="form-horizontal">
					<div class="form-group">
						<a id = "newTask" type="button" class="btn btn-default" href = "/test/newTask.jsp">新建任务</a>
					</div>
					<div class="form-group">
						<a id = "editTask" type="button" class="btn btn-default" href = "/test/editTask.jsp">查看任务</a>
					</div>
					<div class="form-group">
						<a id = "account-center" type="button" class="btn btn-default" href = "/test/personalAccount.jsp">用户中心</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<!-- jQuery (Bootstrap 的 JavaScript 插件需要引入 jQuery) -->
<script src="https://code.jquery.com/jquery.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>

</body>
</html>