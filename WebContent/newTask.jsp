<%@ page language="java" import="domain.UserAccount" contentType="text/html; charset=UTF-8"
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
<%-- <% --%>
// UserAccount user = (UserAccount)request.getSession().getAttribute("user");
<%-- %> --%>
<%-- var userID = <%=user.getMailAccount()%>; --%>
<%-- var userName = <%=user.getUsername()%>; --%>
var userName = "username";


var thisType = 0;
var thatType = 0;
var taskID = 0;
var ownerMail = "", orderedTime = "", MonitorMailAccount = "", MonitorMailpassword = "";
var MonitorWeiboAccount = "", MonitorWeiboAccessToken = "", MonitorContain = "", listenMinute = 0;
var sendWeiboAccount = "", sendWeiboAccessToken = "", weiboContent = "", mailContent = "", receiverMailAccount = "";
$(document).ready(function(){
	$("#userName").text("Hi~ "+userName);
	$("#this-to-choose").hide();
	$("#weibo-login").hide();
	$("#mail-login").hide();
	$("#time-set").hide();
	$("#that-to-choose").hide();
	$("#mail-edit").hide();
	$("#weibo-login-that").hide();
	$("#weibo-edit").hide();
	$("#weibo-choose").hide();
	$("#weibo-time").hide();
	$("#weibo-content").hide();
	$("#task-confirm").hide();
});

$(document).ready(function(){
	$("#to-this").click(function(){
		$("#head").html("if this then that");
		$("#sub-head").hide();
		$("#this-to-choose").show();
	});
});

$(document).ready(function(){
	$("#this-weibo").click(function(){
		$("#this-to-choose").hide();
		$("#weibo-login").show();
	});
});

$(document).ready(function(){
	$("#this-mail").click(function(){
		$("#this-to-choose").hide();
		$("#mail-login").show();
	});
});

$(document).ready(function(){
	$("#this-time").click(function(){
		$("#this-to-choose").hide();
		$("#time-set").show();
	});
});

$(document).ready(function(){
	$("#weibo-rtn-to-this").click(function(){
		$("#weibo-login").hide();
		$("#this-to-choose").show();
	});
});

$(document).ready(function(){
	$("#weibo-choose-rtn-to-this").click(function(){
		$("#weibo-choose").hide();
		$("#this-to-choose").show();
	});
});

$(document).ready(function(){
	$("#time-rtn-to-weibo-choose").click(function(){
		$("#weibo-time").hide();
		$("#weibo-choose").show();
	});
});

$(document).ready(function(){
	$("#content-rtn-to-weibo-choose").click(function(){
		$("#weibo-content").hide();
		$("#weibo-choose").show();
	});
});

$(document).ready(function(){
	$("#mail-rtn-to-this").click(function(){
		$("#mail-login").hide();
		$("#this-to-choose").show();
	});
});

$(document).ready(function(){
	$("#time-rtn-to-this").click(function(){
		$("#time-set").hide();
		$("#this-to-choose").show();
	});
});

$(document).ready(function(){
	$("#that-rtn-to-this").click(function(){
		$("#that-to-choose").hide();
		$("#this-to-choose").show();
	});
});

$(document).ready(function(){
	$("#that-weibo").click(function(){
		$("#that-to-choose").hide();
		$("#weibo-login-that").show();
	});
});

$(document).ready(function(){
	$("#that-mail").click(function(){
		$("#that-to-choose").hide();
		$("#mail-edit").show();
	});
});
$(document).ready(function(){
	$("#weibo-login-rtn-to-that").click(function(){
		$("#weibo-login-that").hide();
		$("#that-to-choose").show();
	});
});
$(document).ready(function(){
	$("#weibo-rtn-to-that").click(function(){
		$("#weibo-edit").hide();
		$("#that-to-choose").show();
	});
});

$(document).ready(function(){
	$("#mail-rtn-to-that").click(function(){
		$("#mail-edit").hide();
		$("#that-to-choose").show();
	});
});
$(document).ready(function(){
	$("#confirm-rtn-to-that").click(function(){
		$("#task-confirm").hide();
		$("#that-to-choose").show();
	});
});



$(document).ready(function(){
	$("#weibo-access").click(function(){
		if ($("#access-code").val()=="" || $("#weibo-id").val()=="") {
	    	alert("输入信息有误");
	    }
		else {
			$("#weibo-login").hide();
			$("#weibo-choose").show();
			MonitorWeiboAccount = $("#weibo-id").val();
			MonitorWeiboAccessToken = $("#access-code").val();
		}
	});
});


$(document).ready(function(){
	$("#weibo-choose-time").click(function(){
		$("#weibo-choose").hide();
		$("#weibo-time").show();
	});
});
$(document).ready(function(){
	$("#weibo-choose-content").click(function(){
		$("#weibo-choose").hide();
		$("#weibo-content").show();
	});
});

$(document).ready(function(){
	$("#weibo-time-submit-button").click(function(){
		if ($("#weibo-time-val").val()=="") {
	    	alert("请填写监听时间");
	    }
		else {
			thisType = 3;
			$("#weibo-time").hide();
			$("#that-to-choose").show();
			var str = $("#weibo-time-val").val() + "分钟内监听微博";
			$("#this-chosen-img").html("<img class=\"img-rounded\" src=\"/test/img/weibo.png\" alt=\"通用的占位符缩略图\">");
			$("#this-chosen-text").html(str);
			listenMinute = $("#weibo-time-val").val();
		}
	});
});

$(document).ready(function(){
	$("#weibo-content-submit-button").click(function(){
		if ($("#weibo-content-val").val()=="") {
	    	alert("请填写微博内容");
	    }
		else {
			thisType = 2;
			$("#weibo-content").hide();
			$("#that-to-choose").show();
			var str = "监听内容为 " + $("#weibo-content-val").val() + " 的微博";
			$("#this-chosen-img").html("<img class=\"img-rounded\" src=\"/test/img/weibo.png\" alt=\"通用的占位符缩略图\">");
			$("#this-chosen-text").html(str);
			MonitorContain = $("#weibo-content-val").val();
		}
	});
});




var userIDChecked = false, passwordChecked = false;
$(document).ready(function(){
	$("#user-ID").blur(function(){
		if( $("#user-ID").val()=="") {
			$("#user-ID").addClass("warn");
			$("#user-ID").removeClass("checked");
			$(".empty-id").html("请输入邮箱");
			$(".empty-id").css({
				"display":"inline",
				"color":"red"        	   
			});
			userIDChecked = false;
		}
		else {
			$("#user-ID").addClass("checked");
			$("#user-ID").removeClass("warn");
			$(".empty-id").css("display","none");
			userIDChecked = true;
		}
	});
});
$(document).ready(function(){
	$("#password").blur(function(){
		if( $("#password").val()=="") {
			$("#password").addClass("warn");
			$("#password").removeClass("checked");
			$(".empty-password").html("请输入密码");
			$(".empty-password").css({
				"display":"inline",
				"color":"red"        	   
			});
			passwordChecked = false;
		}
		else {
			$("#password").addClass("checked");
			$("#password").removeClass("warn");
			$(".empty-password").css("display","none");
			passwordChecked = true;
		}
	});
});

$(document).ready(function(){
	$("#mail-submit-button").click(function(){
		if (userIDChecked == false || passwordChecked == false) {
	    	alert("填写信息有误");
	    }
		else {
			thisType = 1;
			$("#mail-login").hide();
			$("#that-to-choose").show();
			var str = $("#user-ID").val() + "收邮件";
			$("#this-chosen-img").html("<img class=\"img-rounded\" src=\"/test/img/mail.png\" alt=\"通用的占位符缩略图\">");
			$("#this-chosen-text").html(str);
			MonitorMailAccount = $("#user-ID").val();
			MonitorMailpassword = $("#password").val();
		}
	});
});



var yearChecked = false, monthChecked = false, dayChecked = false;
var hourChecked = false, minuteChecked = false;
$(document).ready(function(){
	$("#year").blur(function(){
		if( $("#year").val()=="") {
			$("#year").addClass("warn");
			$("#year").removeClass("checked");
			yearChecked = false;
		}
		else {
			$("#year").addClass("checked");
			$("#year").removeClass("warn");
			yearChecked = true;
		}
	});
});
$(document).ready(function(){
	$("#month").blur(function(){
		if( $("#month").val()=="") {
			$("#month").addClass("warn");
			$("#month").removeClass("checked");
			monthChecked = false;
		}
		else {
			$("#month").addClass("checked");
			$("#month").removeClass("warn");
			monthChecked = true;
		}
	});
});
$(document).ready(function(){
	$("#day").blur(function(){
		if( $("#day").val()=="") {
			$("#day").addClass("warn");
			$("#day").removeClass("checked");
			dayChecked = false;
		}
		else {
			$("#day").addClass("checked");
			$("#day").removeClass("warn");
			dayChecked = true;
		}
	});
});
$(document).ready(function(){
	$("#hour").blur(function(){
		if( $("#hour").val()=="") {
			$("#hour").addClass("warn");
			$("#hour").removeClass("checked");   
			hourChecked = false;
		}
		else {
			$("#hour").addClass("checked");
			$("#hour").removeClass("warn");
			hourChecked = true;
		}
	});
});
$(document).ready(function(){
	$("#minute").blur(function(){
		if( $("#minute").val()=="") {
			$("#minute").addClass("warn");
			$("#minute").removeClass("checked");
			minuteChecked = false;
		}
		else {
			$("#minute").addClass("checked");
			$("#minute").removeClass("warn");
			minuteChecked = true;
		}
	});
});
$(document).ready(function(){
	$("#time-submit-button").click(function(){
		if (yearChecked == false || monthChecked == false || dayChecked == false
			|| hourChecked == false || minuteChecked == false) {
	    	alert("填写信息有误");
	    }
		else {
			thisType = 0;
			$("#time-set").hide();
			$("#that-to-choose").show();
			var str = $("#year").val()+"-"+$("#month").val()+"-"+$("#day").val()+" "+$("#hour").val()+":"+$("#minute").val()+":00";
			$("#this-chosen-img").html("<img class=\"img-rounded\" src=\"/test/img/time.png\" alt=\"通用的占位符缩略图\">");
			$("#this-chosen-text").html(str);
			orderedTime = str;
		}
	});
});



var receiverChecked = false, mailContentChecked = false;

$(document).ready(function(){
	$("#receiver").blur(function(){
		if( $("#receiver").val()=="") {
			$("#receiver").addClass("warn");
			$("#receiver").removeClass("checked");
			$(".empty-receiver").html("请输入收件人邮箱");
			$(".empty-receiver").css({
				"display":"inline",
				"color":"red"        	   
			});
			receiverChecked = false;
		}
		else {
			$("#receiver").addClass("checked");
			$("#receiver").removeClass("warn");
			$(".empty-receiver").css("display","none");
			receiverChecked = true;
		}
	});
});
$(document).ready(function(){
	$("#mail-content").blur(function(){
		if( $("#mail-content").val()=="") {
			$("#mail-content").addClass("warn");
			$("#mail-content").removeClass("checked");
			$(".empty-mail-content").html("请输入邮件内容");
			$(".empty-mail-content").css({
				"display":"inline",
				"color":"red"        	   
			});
			mailContentChecked = false;
		}
		else {
			$("#mail-content").addClass("checked");
			$("#mail-content").removeClass("warn");
			$(".empty-mail-content").css("display","none");
			mailContentChecked = true;
		}
	});
});

$(document).ready(function(){
	$("#send-mail-submit-button").click(function(){
		if (receiverChecked == false || mailContentChecked == false) {
	    	alert("填写信息有误");
	    }
		else {
			thatType = 1;
			$("#mail-edit").hide();
			var str = "向"+$("#receiver").val()+"发邮件";
			$("#that-chosen-img").html("<img class=\"img-rounded\" src=\"/test/img/mail.png\" alt=\"通用的占位符缩略图\">");
			$("#that-chosen-text").html(str);
			$("#task-confirm").show();
			mailContent = $("#mail-content").val();
			receiverMailAccount = $("#receiver").val();
		}
	});
});


$(document).ready(function(){
	$("#weibo-access-that").click(function(){
		if ($("#access-code-that").val()=="" || $("#weibo-id-that").val()=="") {
	    	alert("输入信息有误");
	    }
		else {
			$("#weibo-login-that").hide();
			$("#weibo-edit").show();
			sendWeiboAccount = $("#weibo-id-that").val();
			sendWeiboAccessToken = $("#access-code-that").val();
		}
	});
});

$(document).ready(function(){
	$("#send-weibo-submit-button").click(function(){
		if ($("#send-weibo-content").val()=="") {
	    	alert("请输入微博内容");
	    }
		else {
			thatType = 0;
			$("#weibo-edit").hide();
			var str = "发微博";
			$("#that-chosen-img").html("<img class=\"img-rounded\" src=\"/test/img/weibo.png\" alt=\"通用的占位符缩略图\">");
			$("#that-chosen-text").html(str);
			$("#task-confirm").show();
			weiboContent = $("#send-weibo-content").val();
		}
	});
});


$(document).ready(function(){
	$("#task-confirm-button").click(function(){
		if ($("#task-name").val()=="") {
	    	alert("请输入任务名");
	    }
		else {
			$.post("CreateTaskServlet",
					{	
						taskName: $("#task-name").val(),
						taskID: taskID,//////////////////////////////////////////////////////////
						ownerMail: userID,
						thisType: thisType,
						thatType: thatType,
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
						alert(data);
				       		//$('#verifivation-code-msg').html(message);
					});
		}
		
	});
});



</script>

<style type="text/css">
.container {
    padding-right: 0px;
    padding-left: 0px;
}
</style>

<style type="text/css">
.warn
{
border: 1px solid red;
}
</style>

<style type="text/css">
.checked
{
border: 1px solid green;
}
</style>



</head>
<body>
<div class="form-group" style= "padding-top: 1%;background-color: #87CEFA;" >
	<strong class="col-md-offset-1 col-sm-offset-1" style = "font-size:120%;color:#FFFFFF">IFTTT</strong>
	<strong id = "userName" class="col-md-offset-8 col-sm-offset-8" style = "font-size:120%;color:#FFFFFF"></strong>
</div>
<div class="container">
	<ul style = "list-style-type:none;">
		<li>
			<h1 id = "head" style = "font-size:400%">if <a id = "to-this" href = "#this-to-choose">this</a> then that</h1>
			<h4 id = "sub-head" style = "color:#c0c0c0">点击‘this’开始创建任务</h4>
		</li>
		<li class = "divider" style = "border-top-style:outset; border-top-color:#c0c0c0;"></li>
		<li id = "this-to-choose">
			<h1>请选择一个this事件</h1>
			<br></br>
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class = "col-md-2 col-sm-4 col-xs-4">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<a id = "this-time" class="col-md-12 col-sm-12 col-xs-12" href = "#time-set">
									<img id = "time-img" class="img-rounded" src = "/test/img/time.png">
								</a>
							</div>
							<div class = "col-md-11 col-sm-11 col-xs-11">
								<p class="text-center">Date&Time</p>
							</div>
						</div>
					</div>
					<div class = "col-md-2 col-sm-4 col-xs-4">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<a id = "this-mail" class = "col-md-12 col-sm-12 col-xs-12" href = "#mail-login">
									<img id = "time-img" class="img-rounded" src = "/test/img/mail.png">
								</a>
							</div>
							<div class = "col-md-11 col-sm-11 col-xs-11">
								<p class="text-center">邮件</p>
							</div>
						</div>
					</div>
					<div class = "col-md-2 col-sm-4 col-xs-4">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<a id = "this-weibo" class = "col-md-12 col-sm-12 col-xs-12" href = "#weibo-login">
									<img id = "time-img" class="img-rounded" src = "/test/img/weibo.png">
								</a>
							</div>
							<div class = "col-md-11 col-sm-11 col-xs-11">
								<p class="text-center">新浪微博</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<br></br>
		</li>
		<li class = "divider"></li>
		
		<li id = "weibo-login">
			<a id = "weibo-rtn-to-this" type="button" class="btn btn-default btn-sm col-md-offset-11" href = "#this-to-choose">
				<span class="glyphicon glyphicon-chevron-up"></span> 返回
        	</a>
			<h1>新浪微博</h1>
			<p>点击以下按钮获取授权</p>
			<br></br>
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class = "col-md-2 col-sm-4 col-xs-4">
						<img id = "time-img" class="img-rounded" src = "/test/img/weibo.png">
					</div>
					<div class = "col-md-10 col-sm-8 col-xs-8">
					<form id = "weibo-login" class="form-horizontal">
						<div class="form-group">
							<div class = "col-md-offset-3 col-md-3 col-sm-3 col-xs-3">
 								<a type="button" class="btn btn-default" id = "weibo-login-btn" href = "https://api.weibo.com/oauth2/authorize?client_id=1828050850&redirect_uri=http://127.0.0.1:8080/test/getAccessToken.jsp&response_type=code" target="_blank">点击此处进行授权登录</a> 
							</div>
						</div>
						<div class="form-group">
							<label for="weibo-id" class="col-md-3 control-label">微博账号</label>
							<div class="col-md-4">
								<input type="text" class="form-control" id="weibo-id" placeholder="请输入微博账号"/>
							</div> 
						</div>
						<div class="form-group">
							<label for="access-code" class="col-md-3 control-label">请输入授权码完成授权</label>
							<div class="col-md-4">
								<input type="text" class="form-control" id="access-code" placeholder="请输入授权码"/>
							</div> 
						</div>
						<div class="form-group">
							<div class="col-md-offset-3 col-md-8">
								<a id = "weibo-access" type="button" class="btn btn-success" href = "#weibo-choose">确认授权</a>
							</div>
						</div>
					</form>
					</div>
				</div>
			</div>
		</li>
		
		<li id = "weibo-choose">
			<a id = "weibo-choose-rtn-to-this" type="button" class="btn btn-default btn-sm col-md-offset-11" href = "#this-to-choose">
				<span class="glyphicon glyphicon-chevron-up"></span> 返回
        	</a>
			<h1>新浪微博</h1>
			<p>请在以下两项中选择一项this任务</p>
			<br></br>
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class = "col-md-2 col-sm-4 col-xs-4">
						<img id = "time-img" class="img-rounded" src = "/test/img/weibo.png">
					</div>
					<div class = "col-md-offset-1 col-md-10 col-sm-8 col-xs-8">
					<form id = "weibo-login" class="form-horizontal">
						<div class="form-group">
							<a id = "weibo-choose-time" type="button" class="btn btn-default" href = "#weibo-time">在指定时间长度内监听微博</a>
						</div>
						<div class="form-group">
							<a id = "weibo-choose-content" type="button" class="btn btn-default" href = "#weibo-content">监听包含指定内容的微博</a>
						</div>
					</form>
					</div>
				</div>
			</div>
		</li>
		
		<li id = "weibo-time">
			<a id = "time-rtn-to-weibo-choose" type="button" class="btn btn-default btn-sm col-md-offset-11" href = "#weibo-choose">
				<span class="glyphicon glyphicon-chevron-up"></span> 返回
        	</a>
			<h1>新浪微博</h1>
			<p>在指定时间长度内监听微博</p>
			<br></br>
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class = "col-md-2 col-sm-4 col-xs-4">
						<img id = "time-img" class="img-rounded" src = "/test/img/weibo.png">
					</div>
					<div class = "col-md-10 col-sm-8 col-xs-8">
					<form id = "weibo-login" class="form-horizontal">
						<div class="form-group">
							<label for="weibo-time" class="col-md-3 control-label">请输入监听的时间间隔</label>
							<div class="input-group">
								<input type="text" class="form-control col-md-2" id = "weibo-time-val">
								<span class="input-group-addon">分钟</span>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-offset-3 col-md-8">
								<a id = "weibo-time-submit-button" type="button" class="btn btn-success" href = "#">OK</a>
							</div>
						</div>
					</form>
					</div>
				</div>
			</div>
		</li>
		
		<li id = "weibo-content">
			<a id = "content-rtn-to-weibo-choose" type="button" class="btn btn-default btn-sm col-md-offset-11" href = "#weibo-choose">
				<span class="glyphicon glyphicon-chevron-up"></span> 返回
        	</a>
			<h1>新浪微博</h1>
			<p>监听包含指定内容的微博</p>
			<br></br>
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class = "col-md-2 col-sm-4 col-xs-4">
						<img id = "time-img" class="img-rounded" src = "/test/img/weibo.png">
					</div>
					<div class = "col-md-10 col-sm-8 col-xs-8">
					<form id = "weibo-login" class="form-horizontal">
						<label for="weibo-content-val" class="col-md-2 control-label">内容</label>
						<div class="col-md-4">
							<textarea class="form-control" rows="3" id="weibo-content-val"></textarea>
						</div>
						<div class="form-group">
							<div class="col-md-offset-2 col-md-8">
								<a id = "weibo-content-submit-button" type="button" class="btn btn-success" href = "#">OK</a>
							</div>
						</div>
					</form>
					</div>
				</div>
			</div>
		</li>
		
		<li id = "mail-login">
			<a id = "mail-rtn-to-this" type="button" class="btn btn-default btn-sm col-md-offset-11" href = "#this-to-choose">
				<span class="glyphicon glyphicon-chevron-up"></span> 返回
        	</a>
			<h1>QQ邮箱</h1>
			<p>请填写QQ邮箱的账号和密码</p>
			<br></br>
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<form id = "mail-login" class="form-horizontal">
						<div class="form-group">
							<label for="userID" class="col-md-2 control-label">邮箱账号</label>
							<div class="col-md-4">
								<input type="text" class="form-control" id="user-ID" placeholder="请输入邮箱账号"/>
								<span class = "error empty-id"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="passWord" class="col-md-2 control-label">密码</label>
							<div class="col-md-4">
								<input type="password" class="form-control" id="password" placeholder="请输入密码"/>
       		  					<span class = "error empty-password"></span>
							</div> 
						</div>
						<div class="form-group">
							<div class="col-md-offset-2 col-md-8">
								<a id = "mail-submit-button" type="button" class="btn btn-default" href = "#that-to-choose">OK</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</li>
		
		<li id = "time-set">
			<a id = "time-rtn-to-this" type="button" class="btn btn-default btn-sm col-md-offset-11" href = "#this-to-choose">
				<span class="glyphicon glyphicon-chevron-up"></span> 返回
        	</a>
			<h1>时间设置</h1>
			<p>请输入that任务执行的时间</p>
			<br></br>
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<form id = "time" class="form-horizontal">
						<div class="form-group">
							<label for="year" class="col-md-1 control-label">年：</label>
							<div class="col-md-1">
								<input type="text" class="form-control" id="year"/>
							</div>
							<label for="month" class="col-md-1 control-label">月：</label>
							<div class="col-md-1">
								<input type="text" class="form-control" id="month"/>
							</div>
							<label for="day" class="col-md-1 control-label">日：</label>
							<div class="col-md-1">
								<input type="text" class="form-control" id="day"/>
							</div>
						</div>
						<div class="form-group">
							<label for="hour" class="col-md-1 control-label">时：</label>
							<div class="col-md-1">
								<input type="text" class="form-control" id="hour"/>
							</div>
							<label for="minute" class="col-md-1 control-label">分：</label>
							<div class="col-md-1">
								<input type="text" class="form-control" id="minute"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-offset-1 col-md-8">
								<a id = "time-submit-button" type="button" class="btn btn-default" href = "#that-to-choose">OK</a>
							</div>
						</div>
					</form>
					
				</div>
			</div>
		</li>
		<li id = "that-to-choose">
			<a id = "that-rtn-to-this" type="button" class="btn btn-default btn-sm col-md-offset-11" href = "#this-to-choose">
				<span class="glyphicon glyphicon-chevron-up"></span> 返回
        	</a>
			<h1>请选择一个that事件</h1>
			<br></br>
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class = "col-md-2 col-sm-4 col-xs-4">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<a id = "that-mail" class = "col-md-12 col-sm-12 col-xs-12" href = "#mail-edit">
									<img id = "time-img" class="img-rounded" src = "/test/img/mail.png">
								</a>
							</div>
							<div class = "col-md-11 col-sm-11 col-xs-11">
								<p class="text-center">邮件</p>
							</div>
						</div>
					</div>
					<div class = "col-md-2 col-sm-4 col-xs-4">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<a id = "that-weibo" class = "col-md-12 col-sm-12 col-xs-12" href = "#weibo-login-that">
									<img id = "time-img" class="img-rounded" src = "/test/img/weibo.png">
								</a>
							</div>
							<div class = "col-md-11 col-sm-11 col-xs-11">
								<p class="text-center">新浪微博</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<br></br>
		</li>
		<li id = "mail-edit">
			<a id = "mail-rtn-to-that" type="button" class="btn btn-default btn-sm col-md-offset-11" href = "#that-to-choose">
				<span class="glyphicon glyphicon-chevron-up"></span> 返回
        	</a>
			<h1>发邮件</h1>
			<p>请填写目的邮箱邮件内容</p>
			<br></br>
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<form id = "login" class="form-horizontal">
						<div class="form-group">
							<label for="receiver" class="col-md-3 control-label">邮箱账号</label>
							<div class="col-md-4">
								<input type="text" class="form-control" id="receiver" placeholder="请输入邮箱账号"/>
								<span class = "error empty-receiver"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="mail-content" class="col-md-3 control-label">邮件内容</label>
							<div class="col-md-4">
								<textarea class="form-control" rows="3" id="mail-content"></textarea>
       		  					<span class = "error empty-mail-content"></span>
							</div> 
						</div>
						<div class="form-group">
							<div class="col-md-offset-3 col-md-8">
								<button id = "send-mail-submit-button" type="button" class="btn btn-default">OK</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</li>
		
		<li id = "weibo-login-that">
			<a id = "weibo-login-rtn-to-that" type="button" class="btn btn-default btn-sm col-md-offset-11" href = "#that-to-choose">
				<span class="glyphicon glyphicon-chevron-up"></span> 返回
        	</a>
			<h1>新浪微博</h1>
			<p>点击以下按钮获取授权</p>
			<br></br>
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class = "col-md-2 col-sm-4 col-xs-4">
						<img id = "time-img" class="img-rounded" src = "/test/img/weibo.png">
					</div>
					<div class = "col-md-10 col-sm-8 col-xs-8">
					<form class="form-horizontal">
						<div class="form-group">
							<div class = "col-md-offset-3 col-md-3 col-sm-3 col-xs-3">
 								<a type="button" class="btn btn-default" id = "weibo-login-btn-that" href = "https://api.weibo.com/oauth2/authorize?client_id=1828050850&redirect_uri=http://127.0.0.1:8080/test/getAccessToken.jsp&response_type=code" target="_blank">点击此处进行授权登录</a> 
							</div>
						</div>
						<div class="form-group">
							<label for="weibo-id-that" class="col-md-3 control-label">微博账号</label>
							<div class="col-md-4">
								<input type="text" class="form-control" id="weibo-id-that" placeholder="请输入微博账号"/>
							</div> 
						</div>
						<div class="form-group">
							<label for="access-code-that" class="col-md-3 control-label">请输入授权码完成授权</label>
							<div class="col-md-4">
								<input type="text" class="form-control" id="access-code-that" placeholder="请输入授权码"/>
							</div> 
						</div>
						<div class="form-group">
							<div class="col-md-offset-3 col-md-8">
								<a id = "weibo-access-that" type="button" class="btn btn-success" href = "#weibo-edit">确认授权</a>
							</div>
						</div>
					</form>
					</div>
				</div>
			</div>
		</li>
		<li id = "weibo-edit">
			<a id = "weibo-rtn-to-that" type="button" class="btn btn-default btn-sm col-md-offset-11" href = "#that-to-choose">
				<span class="glyphicon glyphicon-chevron-up"></span> 返回
        	</a>
			<h1>发微博</h1>
			<p>请填写微博内容</p>
			<br></br>
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<form id = "login" class="form-horizontal">
						<div class="form-group">
							<label for="send-weibo-content" class="col-md-3 control-label">微博内容</label>
							<div class="col-md-4">
								<textarea class="form-control" rows="3" id="send-weibo-content"></textarea>
							</div> 
						</div>
						<div class="form-group">
							<div class="col-md-offset-3 col-md-8">
								<button id = "send-weibo-submit-button" type="button" class="btn btn-default">OK</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</li>
		
		<li id = "task-confirm">
			<a id = "confirm-rtn-to-that" type="button" class="btn btn-default btn-sm col-md-offset-11" href = "#that-to-choose">
				<span class="glyphicon glyphicon-chevron-up"></span> 返回
        	</a>
			<h1>任务确认</h1>
			<p>点击以下按钮完成创建</p>
			<br></br>
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<form class="form-horizontal">
						<div class="form-group">
							<label for="task-name" class="col-md-1 control-label">任务名</label>
							<div class="col-md-3">
								<input type="text" class="form-control" id="task-name"/>
							</div> 
						</div>
					</form>
				</div>
			</div>
			<br></br>
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class="col-md-1">
						<h1 style = "font-size:400%; text-align:right">if</h1>
					</div>
					<div class="col-md-2">
						<div id = "this-chosen-img" class="thumbnail">
							
						</div>
						<div class="caption">
							<p id = "this-chosen-text" style = "text-align:center"></p>
						</div>
					</div>
					<div class="col-md-2">
						<h1 style = "font-size:400%; text-align:center">then</h1>
					</div>
					<div class="col-md-2">
						<div id = "that-chosen-img" class="thumbnail">
							
						</div>
						<div class="caption">
							<p id = "that-chosen-text" style = "text-align:center"></p>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class="col-md-offset-1 col-md-8">
						<a id = "task-confirm-button" type="button" class="btn btn-success" href = "#">确认创建</a>
					</div>
				</div>
			</div>
		</li>
	</ul>
</div>


<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</body>
</html>