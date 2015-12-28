<%@page import="web.formbean.CreateTaskFormBean"%>
<%@page import="sun.font.CreatedFontTracker"%>
<%@page import="java.util.Vector"%>
<%@ page language="java" import="domain.UserAccount,domain.Task" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 引入bootstrap -->
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
	<!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
	<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
		<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
	<![endif]-->
	
<title>IFTTT</title>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<script type="text/javascript">
	<% 
// /*
// TODO:传入任务列表
//		int currentTask
// */
	int currentTask = 0, taskNum = 0;
	String currentTaskName = "";
	String currentTaskID;
	//...
 //UserAccount user = new UserAccount("lucy","000","376575092@qq.com",12,23,34,0.5);
	UserAccount user = (UserAccount)request.getSession().getAttribute("user");
	Vector<CreateTaskFormBean> tasklist = (Vector<CreateTaskFormBean>)request.getSession().getAttribute("list");
	//Vector<CreateTaskFormBean> tasklist =null;
	
	if (tasklist != null) {
		taskNum = tasklist.size();
		currentTask = 1;
		currentTaskName = tasklist.get(1).getTaskName();
		currentTaskID = tasklist.get(1).getTaskID();
	}
	else currentTask = 0;
	String thisType="",thatType="";
	String outputInfo="";
	
	%>
var ownerMail = "", orderedTime = "", MonitorMailAccount = "", MonitorMailpassword = "";
var MonitorWeiboAccount = "", MonitorWeiboAccessToken = "", MonitorContain = "", listenMinute = 0;
var sendWeiboAccount = "", sendWeiboAccessToken = "", weiboContent = "", mailContent = "", receiverMailAccount = "";

$(document).ready(function(){
	<%if (currentTask == 0) {%>
		$(".container").hide();
		$("#empty-task").show();
	<%} else {%>
		$("#empty-task").hide();
//  		TODO：
//  			对当前任务进行设置
 		$("#task-name").text(<%out.print(currentTaskName);%>);

	 		<%		thisType = tasklist.get(currentTask).getThisType();%>
	 		<%	if (thisType.equals("0")) {
					outputInfo = "定时"+tasklist.get(currentTask).getOrderedTime();
				}
				else if (thisType.equals("1")) {
					outputInfo = tasklist.get(currentTask).getMonitorMailAccount() + "收邮件";
				}
				else if (thisType.equals("2")) {
					outputInfo = tasklist.get(currentTask).getListenMinute() + "分钟内监听微博";
				}
				else if (thisType.equals("3")) {
					outputInfo = "监听内容为"+tasklist.get(currentTask).getMonitorContain() + "的微博";
				}
				else {
					outputInfo = "";
				}
				%>
 		$("#this-detail").text(<%out.print(outputInfo);%>);

			<%		thatType = tasklist.get(currentTask).getThisType();%>
			<%	if (thatType.equals("0")) {
	 				outputInfo = "用"+tasklist.get(currentTask).getSendWeiboAccount() + "发微博";
	 				}
	 				else if (thatType.equals("1")) {
	 					outputInfo = "向"+tasklist.get(currentTask).getReceiverMailAccount() + "发邮件";
	 				}
	 				else {
	 					outputInfo = "";
	 				}%>
 				$("#that-detail").text(<%out.print(outputInfo);%>);
	<%}%>
});

$(document).ready(function(){
	$(".before-task").click(function(){
	<%	if (currentTask != 1 && currentTask != 0) {
			currentTask--;%>
			//currentTaskID = 
			$("#empty-task").hide();
//  		TODO：
//  			对当前任务进行设置
 		$("#task-name").text(<%out.print(currentTaskName);%>);

 		<%		thisType = tasklist.get(currentTask).getThisType();%>
 		<%	if (thisType.equals("0")) {
				outputInfo = "定时"+tasklist.get(currentTask).getOrderedTime();
			}
			else if (thisType.equals("1")) {
				outputInfo = tasklist.get(currentTask).getMonitorMailAccount() + "收邮件";
			}
			else if (thisType.equals("2")) {
				outputInfo = tasklist.get(currentTask).getListenMinute() + "分钟内监听微博";
			}
			else if (thisType.equals("3")) {
				outputInfo = "监听内容为"+tasklist.get(currentTask).getMonitorContain() + "的微博";
			}
			else {
				outputInfo = "";
			}
			%>
		$("#this-detail").text(<%out.print(outputInfo);%>);

		<%		thatType = tasklist.get(currentTask).getThisType();%>
		<%	if (thatType.equals("0")) {
 				outputInfo = "用"+tasklist.get(currentTask).getSendWeiboAccount() + "发微博";
 				}
 				else if (thatType.equals("1")) {
 					outputInfo = "向"+tasklist.get(currentTask).getReceiverMailAccount() + "发邮件";
 				}
 				else {
 					outputInfo = "";
 				}%>
				$("#that-detail").text(<%out.print(outputInfo);%>);
	<%	}	%>	
	});
});

$(document).ready(function(){
	$(".next-task").click(function(){
	<%	if (currentTask != taskNum && currentTask != 0) {%>
		<%	currentTask++;	%>

			//currentTaskID = 
			$("#empty-task").hide();
//  		TODO：
//  			对当前任务进行设置
 		$("#task-name").text(<%out.print(currentTaskName);%>);

 		<%		thisType = tasklist.get(currentTask).getThisType();%>
	 		<%	if (thisType.equals("0")) {
					outputInfo = "定时"+tasklist.get(currentTask).getOrderedTime();
				}
				else if (thisType.equals("1")) {
					outputInfo = tasklist.get(currentTask).getMonitorMailAccount() + "收邮件";
				}
				else if (thisType.equals("2")) {
					outputInfo = tasklist.get(currentTask).getListenMinute() + "分钟内监听微博";
				}
				else if (thisType.equals("3")) {
					outputInfo = "监听内容为"+tasklist.get(currentTask).getMonitorContain() + "的微博";
				}
				else {
					outputInfo = "";
				}
				%>
 		$("#this-detail").text(<%out.print(outputInfo);%>);

			<%		thatType = tasklist.get(currentTask).getThisType();%>
			<%	if (thatType.equals("0")) {
	 				outputInfo = "用"+tasklist.get(currentTask).getSendWeiboAccount() + "发微博";
	 				}
	 				else if (thatType.equals("1")) {
	 					outputInfo = "向"+tasklist.get(currentTask).getReceiverMailAccount() + "发邮件";
	 				}
	 				else {
	 					outputInfo = "";
	 				}%>
 				$("#that-detail").text(<%out.print(outputInfo);%>);
	<%}%>
	});
});

$(document).ready(function(){
	$(".start-button").click(function(){
		<%if (currentTask != 0) {%>
		$.post("StartTaskServlet",
			{	
				taskID: <%out.print(tasklist.get(currentTask).getTaskID());%>
			},
			function(data){
				alert(data);
		       	//刷新界面
		});	
		<%}%>
	});
});
$(document).ready(function(){
	$(".stop-button").click(function(){
		<%if (currentTask != 0) {%>
		$.post("StopTaskServlet",
			{	
				taskID: <%out.print(tasklist.get(currentTask).getTaskID());%>
			},
			function(data){
				alert(data);
		       	//刷新界面
		});
		<%}%>
	});
});
$(document).ready(function(){
	$(".edit-button").click(function(){
		<%if (currentTask != 0) {%>
		MonitorMailAccount = <%out.print(tasklist.get(currentTask).getMonitorMailAccount());%>;
		orderedTime = <%out.print(tasklist.get(currentTask).getOrderedTime());%>;
		MonitorMailpassword = <%out.print(tasklist.get(currentTask).getMonitorMailpassword());%>;
		MonitorWeiboAccount = <%out.print(tasklist.get(currentTask).getMonitorWeiboAccount());%>;
		MonitorWeiboAccessToken = <%out.print(tasklist.get(currentTask).getMonitorWeiboAccessToken());%>;
		MonitorContain = <%out.print(tasklist.get(currentTask).getMonitorContain());%>;
		listenMinute = <%out.print(tasklist.get(currentTask).getListenMinute());%>;
		weiboContent = <%out.print(tasklist.get(currentTask).getWeiboContent());%>;
		mailContent = <%out.print(tasklist.get(currentTask).getMailContent());%>;

		receiverMailAccount = <%out.print(tasklist.get(currentTask).getReceiverMailAccount());%>;
		sendWeiboAccount = <%out.print(tasklist.get(currentTask).getSendWeiboAccount());%>;
		sendWeiboAccessToken = <%out.print(tasklist.get(currentTask).getSendWeiboAccessToken());%>;
		
		<%if (thisType.equals("0")) {%>
			$(".this-time").show();
			$(".this-weibo-time").hide();
			$(".this-weibo-content").hide();
		<%}else if (thisType.equals("1")) {%>
			$(".this-time").hide();
			$(".this-weibo-time").hide();
			$(".this-weibo-content").hide();
		<%}else if (thisType.equals("2")) {%>
			$(".this-time").hide();
			$(".this-weibo-time").hide();
			$(".this-weibo-content").show();
		<%}else if (thisType.equals("3")) {%>
			$(".this-time").hide();
			$(".this-weibo-time").show();
			$(".this-weibo-content").hide();
		<%}%>
		
		<%if (thatType.equals("0")) {%>
			$(".that-weibo").show();
			$(".that-mail").hide();
		<%}else if (thatType.equals("1")) {%>
			$(".that-weibo").hide();
			$(".that-mail").show();
		<%}%>
		<%}%>
	});
});
$(document).ready(function(){
	$(".delete-button").click(function(){
		<%if (currentTask != 0) {%>
		$.post("DeleteTaskServlet",
			{	
				taskID: <%out.print(tasklist.get(currentTask).getTaskID());%>
			},
			function(data){
			   	//后台控制刷新界面
			});
		<%}%>
	});
});

$(document).ready(function(){
	$("#submit-change").click(function(){
		<%if (currentTask != 0) {%>
		<%if (thisType.equals("0")) {%>
			orderedTime = $("#this-time-time").val();
		<%}else if (thisType.equals("2")) {%>
			MonitorContain = $("#this-weibo-content").val();
		<%}else if (thisType.equals("3")) {%>
			listenMinute = $("#this-weibo-time").val();
		<%}%>
		
		<%if (thatType.equals("0")) {%>
			weiboContent = $("#that-weibo").val();
		<%}else if (thatType.equals("1")) {%>
			mailContent = $("#that-mail-content").val();
			receiverMailAccount = $("#that-mail-id").val();
		<%}%>
		$.post("ModifyTaskServlet",
				{	
					taskName: <%out.print(tasklist.get(currentTask).getTaskName());%>,
					taskID: <%out.print(tasklist.get(currentTask).getTaskID());%>,//////////////////////////////////////////////////////////
					ownerMail: <%out.print(tasklist.get(currentTask).getOwnerMail());%>,
					thisType: <%out.print(thisType);%>,
					thatType: <%out.print(thatType);%>,
					orderedTime: orderedTime,
					MonitorMailAccount: MonitorMailAccount,
					MonitorMailpassword: MonitorMailpassword,
					MonitorWeiboAccount: MonitorWeiboAccount,
					MonitorWeiboAccessToken: MonitorWeiboAccessToken,
					MonitorContain: MonitorContain,
					listenMinute: listenMinute,
					weiboContent: weiboContent,
					mailContent: mailContent,
					sendWeiboAccount: sendWeiboAccount,
					sendWeiboAccessToken: sendWeiboAccessToken,
					receiverMailAccount: receiverMailAccount
				},
				function(data){
					
				});
		<%}%>
	});
});
</script>

</head>
<body>
<div class="form-group" style= "padding-top: 1%;background-color: #87CEFA;">
	<strong class="col-md-offset-1 col-sm-offset-1" style = "font-size:120%;color:#FFFFFF">IFTTT</strong>
	<p id = "userName" class="col-md-offset-8 col-sm-offset-8" style = "font-size:120%;color:#FFFFFF"><%out.print("Hi! " +user.getUsername()); %></p>
</div>

<div id = "empty-task">
当前没有任务
</div>

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<ul class="pagination">
				<li><a class = "before-task">&laquo;</a></li>
				<li><a class = "next-task">&raquo;</a></li>
			</ul>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 id = "task-name" class="panel-title">任务名</h3>
				</div>
				<ul class="list-group">
					<li class="list-group-item">
						<strong style = "font-size:120%">if   </strong>
						<p id = "this-detail"></p>
					</li>
					<li class="list-group-item">
						<strong style = "font-size:120%">then </strong>
						<p id = "that-detail"></p>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<div class="btn-group">
				<button type="button" class="start-button btn btn-default btn-sm">
					<span class="glyphicon glyphicon-play"></span>
        		</button>
        		<button type="button" class="stop-button btn btn-default btn-sm">
					<span class="glyphicon glyphicon-stop"></span>
        		</button>
        		<button type="button" class="edit-button btn btn-default btn-sm" data-toggle="modal" data-target="#edit-modal">
					<span class="glyphicon glyphicon-pencil"></span>
        		</button>
        		<button type="button" class="delete-button btn btn-default btn-sm">
					<span class="glyphicon glyphicon-trash"></span>
        		</button>
			</div>
		</div>
	</div>
	<div><br></br></div>
	<div class = "divider" style = "border-top-style:outset; border-top-color:#c0c0c0;"></div>
</div>

<div class="modal fade" id="edit-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">修改任务</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal">
					<div class="form-group this-time">
						<label for="this-time-time" class="col-md-4 control-label">if-时间</label>
						<div class="col-md-4">
							<input type="text" class="form-control" id="this-time-time"/>
						</div>
					</div>
					<div class="form-group this-weibo-time">
						<label for="this-weibo-time" class="col-md-4 control-label">if-微博监听时间</label>
						<div class="col-md-4 input-group">
							<input type="text" class="form-control" id="this-weibo-time"/>
							<span class="input-group-addon">分钟</span>
						</div> 
					</div>
					<div class="form-group this-weibo-content">
						<label for="this-weibo-content" class="col-md-4 control-label">if-微博监听内容</label>
						<div class="col-md-4">
							<input type="text" class="form-control" id="this-weibo-content"/>
						</div> 
					</div>
					<div class="form-group that-weibo">
						<label for="that-weibo" class="col-md-4 control-label">then-发微博-内容</label>
						<div class="col-md-4">
							<input type="text" class="form-control" id="that-weibo"/>
						</div> 
					</div>
					<div class="form-group that-mail">
						<label for="that-mail-id" class="col-md-4 control-label">then-发邮件-邮箱</label>
						<div class="col-md-4">
							<input type="text" class="form-control" id="that-mail-id"/>
						</div> 
					</div>
					<div class="form-group that-mail">
						<label for="that-mail-content" class="col-md-4 control-label">then-发邮件-内容</label>
						<div class="col-md-4">
							<input type="text" class="form-control" id="that-mail-content"/>
						</div> 
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button id = "submit-change" type="button" class="btn btn-primary" data-dismiss="modal">提交更改</button>
			</div>
		</div>
	</div>
</div>


<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</body>
</html>