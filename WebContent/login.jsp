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
$(document).ready(function(){
  $("#submit-button").click(function(){
	//alert("heihei");
	$.post("login_action.jsp",
	{
		userID:$("#user-ID").val(),
		passWord:$("#password").val()
	},
	/*TODO: 对登录信息进行处理*/
	function(data,status){
		alert("Data: " + data + "Status: " + status);
	});
  });
});
</script>

</head>
<body>

<form id = "login" class="form-horizontal">
   <div class="form-group">
      <label for="userID" class="col-sm-2 control-label">用户名</label>
      <div class="col-sm-5">
         <input type="text" class="form-control" id="user-ID" placeholder="请输入注册邮箱">
      </div>
   </div>
   <div class="form-group">
      <label for="passWord" class="col-sm-2 control-label">密码</label>
      <div class="col-sm-5">
         <input type="text" class="form-control" id="password" placeholder="请输入密码">
      </div>
   </div>
   <div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
         <button id = "submit-button" type="button" class="btn btn-default">登录</button>
      </div>
   </div>
</form>



<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</body>
</html>