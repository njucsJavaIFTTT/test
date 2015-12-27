<%@page import="java.util.Vector"%>
<%@ page language="java" import="domain.UserAccount; import domain.Task" contentType="text/html; charset=UTF-8"
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
// /*
// TODO:传入管理员发布的公告
//		int currentTask
// */
	UserAccount user = (UserAccount)request.getSession().getAttribute("user");
	
	%>
var userID = <%=user.getMailAccount()%>;
var userName = <%=user.getUsername()%>;
var userBalance = <%=user.getBalance()%>;
var userCredit = <%=user.getCredit()%>;



$(document).ready(function(){
	$("#userName").html("Hi~ "+userName);
	$("#user-name").text(userName);
	$("#user-id").text(userID);
	$(".user-balance").text(userBalance);
	$("#user-credit").text(userCredit);
	var listlength = <%=user.getMsg().size()%>;
	<%
	int j = 0;
	%>
	var msg = <%=user.getMsg().get(j).getContent()%>;
	for (var i = 0; i < listlength; i++) {
		$("#private-message-list").append("<li class=\"list-group-item\">"+msg+"</li>");
		<%j++;%>
		msg = <%=user.getMsg().get(j).getContent()%>;
	}
	//添加公告
});

$(document).ready(function(){
	$("recharge-button").click(function(){
		if ($("#recharge-value").val() == "") {
			alert("请填写充值金额");
		}
		else {
			$.post("",
				{	
					taskID: taskID
				},
				function(data){//返回的data为充值之后的金额或者0
					if (data != "0") {
						$(".user-balance").text(data);
						alert("充值成功");
					}
					else alert("充值失败");
				});
		}
	});
});

</script>

</head>
<body>
<div class="form-group" style= "padding-top: 1%;background-color: #87CEFA;">
	<strong class="col-md-offset-1 col-sm-offset-1" style = "font-size:120%;color:#FFFFFF">IFTTT</strong>
	<strong id = "userName" class="col-md-offset-8 col-sm-offset-8" style = "font-size:120%;color:#FFFFFF"></strong>
</div>
<ul id="myTab" class="nav nav-tabs">
	<li class="active">
		<a href="#user-account" data-toggle="tab">我的资料</a>
	</li>
	<li>
		<a href="#recharge" data-toggle="tab">账户充值</a>
	</li>
	<li class="dropdown">
		<a href="#" id="myTabDrop1" class="dropdown-toggle" data-toggle="dropdown">消息盒子 
			<b class="caret"></b>
		</a>
		<ul class="dropdown-menu" role="menu" aria-labelledby="myTabDrop1">
			<li><a href="#public-message" tabindex="-1" data-toggle="tab">公告消息</a></li>
			<li><a href="#private-message" tabindex="-1" data-toggle="tab">私信消息</a></li>
		</ul>
	</li>
</ul>
<div id="myTabContent" class="tab-content">
	<div class="tab-pane fade in active" id="user-account">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label class="col-sm-2 control-label">用户名</label>
							<div class="col-sm-6">
								<p class="form-control-static" id = "user-name"></p>
							</div>
						</div>
						<div class="form-group">
							<label for="user-id" class="col-sm-2 control-label">注册邮箱</label>
							<div class="col-sm-6">
								<p class="form-control-static" id = "user-id"></p>
							</div>
						</div>
						<div class="form-group">
							<label for="user-balance" class="col-sm-2 control-label">账户余额</label>
							<div class="col-sm-6">
								<p class="form-control-static user-balance"></p>
							</div>
						</div>
						<div class="form-group">
							<label for="user-credit" class="col-sm-2 control-label">我的积分</label>
							<div class="col-sm-6">
								<p class="form-control-static" id = "user-credit"></p>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="tab-pane fade" id="recharge">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label for="user-balance" class="col-sm-2 control-label">账户余额</label>
							<div class="col-sm-6">
								<p class="form-control-static user-balance"></p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">充值金额</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="recharge-value">
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-offset-4 col-md-8">
								<button id = "recharge-button" type="button" class="btn btn-default">充值</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="tab-pane fade" id="public-message">
		<ul class="list-group" id = "public-message-list">
			<li class="list-group-item"></li>
		</ul>
	</div>
	<div class="tab-pane fade" id="private-message">
		<ul class="list-group" id="private-message-list">
			<li class="list-group-item"></li>
		</ul>
	</div>
</div>


<!-- jQuery (Bootstrap 的 JavaScript 插件需要引入 jQuery) -->
<script src="https://code.jquery.com/jquery.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>

</body>
</html>