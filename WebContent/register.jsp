<%@page import="java.util.Random"%>
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

<script>
<%
//生成验证码
String base = "0123456789";
Random random = new Random();
StringBuffer sb = new StringBuffer();     
for (int i = 0; i < 6; i++) {     
  int number = random.nextInt(base.length());     
  sb.append(base.charAt(number));     
}     
String verificationCode = sb.toString();
%>
var userIDChecked = false,
passwordChecked = false,
verificationCodeChecked = false;

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
			passwordChecked = false;
		}
	});
});
$(document).ready(function(){
	$("#password-repeat").blur(function(){
		alert($("#password-repeat").val() + "   " + $("#password").val());
		if($("#password-repeat").val() == "" || !($("#password-repeat").val()==$("#password").val())) {
			$("#password-repeat").addClass("warn");
			$("#password-repeat").removeClass("checked");
			$(".wrong-repeat").html("密码不一致");
			$(".wrong-repeat").css({
				"display":"inline",
				"color":"red"        	   
			});
			passwordChecked = false;
		}
		else {
			$("#password-repeat").addClass("checked");
			$("#password-repeat").removeClass("warn");
			$(".wrong-repeat").css("display","none");
			passwordChecked = true;
		}
	});
});
$(document).ready(function(){
	$("#verification-code").blur(function(){
		if($("#verification-code").val()=="") {
			$("#verification-code").addClass("warn");
			$("#verification-code").removeClass("checked");
			$(".empty-vCode").html("请输入验证码");
			$(".empty-vCode").css({
				"display":"inline",
				"color":"red"        	   
			});
			verificationCodeChecked = false;
		}
		else if (!($("#verification-code").val()==<%=verificationCode%>)) {
			$("#verification-code").addClass("warn");
			$("#verification-code").removeClass("checked");
			$(".empty-vCode").html("验证码不正确");
			$(".empty-vCode").css({
				"display":"inline",
				"color":"red"        	   
			});
			verificationCodeChecked = false;
		}
		else {
			$("#verification-code").addClass("checked");
			$("#verification-code").removeClass("warn");
			$(".empty-vCode").css("display","none");
			verificationCodeChecked = true;
		}
	});
});
$(document).ready(function(){
	$("#send-verification-code").click(function(){
		$.post(
			"../src/servlet/controller/SendVerificationCode.java",
	    	{
			verificationCode: <%=verificationCode%>,
			userID: $("#user-ID").val()
	       	},
	        function(data,status){
	          	alert("验证码已发送至邮箱，请查收");
	        });
  	});
});

$(document).ready(function(){
    $("#submit").click(function(){
    	if (userIDChecked == false || passwordChecked == false || verificationCodeChecked == false) {
    		alert("填写信息有误");
    	}
    	else {
    		$.post(
    			"../src/servlet/controller/RegisterServlet.java",
    	        {
    				userID: $("#user-ID").val(),
    				password: $("#password").val()
    	       	},
    	       	function(data,status){
    	          	//alert("验证码已发送至邮箱，请查收");
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

<!-- TODO:
		对submit的内容进行处理
 -->
 
<form class="form-horizontal" role="form">
   <div class="form-group">
      <label for="user-ID" class="col-sm-2 control-label">请输入注册邮箱</label>
      <div class="col-sm-5">
         <input type="text" class="form-control" id="user-ID">
         <span class = "error empty-id"></span>
      </div>
   </div>
   <div class="form-group">
      <label for="password" class="col-sm-2 control-label">请输入密码</label>
      <div class="col-sm-5">
         <input type="password" class="form-control" id="password">
         <span class = "error empty-password"></span>
      </div>
   </div>
   <div class="form-group">
      <label for="password-repeat" class="col-sm-2 control-label">请再次输入密码</label>
      <div class="col-sm-5">
         <input type="password" class="form-control" id="password-repeat">
         <span class = "error wrong-repeat"></span>
      </div>
   </div>
   <div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
         <button type="button" class="btn btn-default" id = "send-verification-code">发送验证码</button>
      </div>
    
<!--       TODO：增加发验证码到注册邮箱的text	 -->
   </div>
   <div class="form-group">
      <label for="verification-code" class="col-sm-2 control-label">验证码</label>
      <div class="col-sm-5">
         <input type="text" class="form-control" id="verification-code">
         <span class = "error empty-vCode"></span>
      </div>
   </div>
   <div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
         <button type="button" class="btn btn-default" id = "submit">完成注册</button>
      </div>
<!--       TODO：增加检查内容	 -->
   </div>
</form>


<!-- jQuery (Bootstrap 的 JavaScript 插件需要引入 jQuery) -->
<script src="https://code.jquery.com/jquery.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>

</body>
</html>