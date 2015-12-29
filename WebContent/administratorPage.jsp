<%@page import="DB.DBHelperImpl"%>
<%@page import="domain.UserAccount"%>
<%@page import="java.util.Vector"%>
<%@page import="dao.impl.UserDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
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
	//TODO:得到用户信息列表
	DBHelperImpl db=new DBHelperImpl();
	Vector<UserAccount> userList = db.viewAllUsers();
	UserAccount user = null;
// 	Vector<UserAccount> userList = new Vector<UserAccount>();
// 	UserAccount user = new UserAccount("aaa","bbb","ccc",23,34,45,56);
// 	userList.add(user);
// 	user = new UserAccount("daa","b","ccc",23,34,45,56);
// 	userList.add(user);
// 	user = new UserAccount("aaa","bbb","ccc",23,34,45,56);
// 	userList.add(user);
// 	user = new UserAccount("daa","b","ccc",23,34,45,56);
// 	userList.add(user);
	
	int j=0;
	int userNum = 0;
	if (userList != null && userList.size()!= 0) {
		userNum = userList.size();
	}
	%>
// var userNameList = new Array();
// var userIDList = new Array();
// var userCreditList = new Array();
// var userBalanceList = new Array();
<%-- for (var i = 0; i < <%=userNum%>; i++) { --%>
<%-- 	userNameList[i] = <%=userList.get(j).getUsername()%>; --%>
<%-- 	userIDList[i] = <%=userList.get(j).getMailAccount()%>; --%>
<%-- 	userBalanceList[i] = <%=userList.get(j).getBalance()%>; --%>
<%-- 	userCreditList[i] = <%=userList.get(j).getCredit()%>; --%>
<%-- 	<%j++;%> --%>
// }

	
	
$(document).ready(function(){
	//添加用户信息
<%-- 	<%for (int i = 0; i < userNum; i++) {%> --%>
<%-- 		<%String str = "<button type=\"button\" class=\"list-group-item users-info-btn\" id = \""+i+"\">"+userList.get(i).getMailAccount()+"-"+userList.get(i).getUsername()+"</button>";%> --%>
<%-- 		$("#users-list").append(<%=str%>); --%>
<%-- 		<%str = "<button type=\"button\" class=\"list-group-item send-message-btn\" id = \""+i+"\">"+userList.get(i).getMailAccount()+"-"+userList.get(i).getUsername()+"</button>";%> --%>
<%-- 		$("#message-receivers-list").append(<%=str%>); --%>
<%-- 	<%}%> --%>
	//添加公告
	//添加消费记录
});

$(document).ready(function(){
	$(".users-info-btn").click(function(){
		//查找用户信息
		$.post("viewExpCalServlet",
				{	
					user:$(this).attr(id)
				},
				function(data){
					$("#expence-calendar").text(data);
					<%
					user = (UserAccount)session.getAttribute("currentUser");
					%>
				});
	});
});

$(document).ready(function(){
	$("#submit-public-message-button").click(function(){
		if ($("#public-message-content").val() == "") {
			alert("请填写公告内容");
		}
		else {
			$.post("SendPublicMessage",
				{	
					msg:$("#public-message-content").val()
				},
				function(data){
					if (data == "success") {
						alert("公告发布成功");
					}
					else alert("公告发布失败");
				});
		}
	});
});


$(document).ready(function(){
	$(".send-message-btn").click(function(){
		if ($("#private-message-content").val() == "") {
			alert("请填写私信内容");
		}
		else {
			$.post("SendPrivateMessage",
				{	
					userID:$(".send-message-btn").attr(id),
					msg:$("#private-message-content").val()
					//...
				},
				function(data){
					if (data == "success") {
						alert("私信发布成功");
					}
					else alert("私信发布失败");
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
		<a href="#view-users" data-toggle="tab">会员资料查看</a>
	</li>
	<li>
		<a href="#send-public-message" data-toggle="tab">发布公告</a>
	</li>
	<li>
		<a href="#send-private-message" data-toggle="tab">发送私信</a>
	</li>
</ul>
<br></br>
<br></br>
<div id="myTabContent" class="tab-content">
	<div class="tab-pane fade in active" id="view-users">
		<div class="container">
			<div class="row">
				<div class="col-md-5">
					<div class="list-group" id = "users-list">
						<%for (int i = 0; i < userNum; i++) {%>
							<%String str = "<button type=\"button\" class=\"list-group-item users-info-btn\" id = \""+i+"\">"+userList.get(i).getMailAccount()+"-"+userList.get(i).getUsername()+"</button>";%>
							<%out.print(str); %>
							<%} %>
					</div>
				</div>
				<div class="col-md-4">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label class="col-sm-4 control-label">用户名</label>
							<div class="col-sm-6">
								<p class="form-control-static" id = "user-name">
								<%if (user != null) out.print(user.getUsername()); %>
								</p>
							</div>
						</div>
						<div class="form-group">
							<label for="user-id" class="col-sm-4 control-label">注册邮箱</label>
							<div class="col-sm-6">
								<p class="form-control-static" id = "user-id">
								<%if (user != null) out.print(user.getMailAccount()); %>
								</p>
							</div>
						</div>
						<div class="form-group">
							<label for="user-balance" class="col-sm-4 control-label">账户余额</label>
							<div class="col-sm-6">
								<p class="form-control-static user-balance">
								<%if (user != null) out.print(user.getBalance()); %>
								</p>
							</div>
						</div>
						<div class="form-group">
							<label for="user-credit" class="col-sm-4 control-label">会员积分</label>
							<div class="col-sm-6">
								<p class="form-control-static" id = "user-credit">
								<%if (user != null) out.print(user.getCredit()); %>
								</p>
							</div>
						</div>
						<div class="form-group">
							<label for="expence-calendar" class="col-sm-4 control-label">消费记录</label>
							<div class="col-sm-6">
								<p class="form-control-static" id = "expence-calendar"></p>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>		
	</div>
	<div class="tab-pane fade" id="send-public-message">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label class="col-sm-2 control-label">公告内容</label>
							<div class="col-sm-6">
								<textarea class="form-control" rows="3" id="public-message-content"></textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-offset-4 col-md-8">
								<button id = "submit-public-message-button" type="button" class="btn btn-default">发布公告</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="tab-pane fade" id="send-private-message">
		<div class="container">
			<div class="row">
				<div class="col-md-5">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label class="col-sm-2 control-label">私信内容</label>
						</div>
						<div class="form-group">
							<div class="col-sm-6">
								<textarea class="form-control" rows="3" id="private-message-content"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="col-md-2">
					<strong style = "size:200%">发送至：</strong>
				</div>
				<div class="col-md-4">
					<div class="tab-pane fade in active" id="view-users">
						<div class="list-group" id = "message-receivers-list">
							<%for (int i = 0; i < userNum; i++) {%>
								<%String str = "<button type=\"button\" class=\"list-group-item send-message-btn\" id = \""+i+"\">"+userList.get(i).getMailAccount()+"-"+userList.get(i).getUsername()+"</button>";%>
								<%out.print(str);%>
							<%}%>
						</div>
					</div>
				</div>
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