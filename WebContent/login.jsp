<%@ page language="java" contentType="text/html; charset=UTF-8"
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
var userIDChecked = false, passwordChecked = false;

$(document).ready(function(){
	$("#user-ID").blur(function(){
		if( $("#user-ID").val()=="") {
			$("#user-ID").addClass("warn");
			$("#user-ID").removeClass("checked");
			$(".empty-id").html("请输入注册邮箱");
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
	$("#submit-button").click(function(){
	//alert("heihei");
	location.href = "http://www.baidu.com";
	if (userIDChecked == false || passwordChecked == false) {
    	alert("填写信息有误");
    	
    }
	else {
		$.post(
    			"LoginServlet",
    	        {
    				username: $("#user-ID").val(),
    				password: $("#password").val()
    	       	},
    	       	function(data,status){
    	          	//登录成功or失败
    	          	alert(data);
    	        });
	}
  });
});
</script>

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
</div>
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<form id = "login" class="form-horizontal" style= "padding-top: 10%;">
				<div class="form-group">
					<label for="userID" class="col-md-4 control-label">用户名</label>
					<div class="col-md-4">
						<input type="text" class="form-control" id="user-ID" placeholder="请输入注册邮箱"/>
						<span class = "error empty-id"></span>
					</div>
				</div>
				<div class="form-group">
					<label for="passWord" class="col-md-4 control-label">密码</label>
					<div class="col-md-4">
						<input type="password" class="form-control" id="password" placeholder="请输入密码"/>
       		  			<span class = "error empty-password"></span>
					</div> 
				</div>
				<div class="form-group">
					<div class="col-md-offset-4 col-md-8">
						<button id = "submit-button" type="button" class="btn btn-default">登录</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>



<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</body>
</html>