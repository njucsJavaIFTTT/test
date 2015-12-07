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
</head>
<body>

<!-- TODO:
		对submit的内容进行处理
 -->
 
<form class="form-horizontal" role="form">
   <div class="form-group">
      <label for="userID" class="col-sm-2 control-label">请输入注册邮箱</label>
      <div class="col-sm-5">
         <input type="text" class="form-control" id="userID">
      </div>
   </div>
   <div class="form-group">
      <label for="passWord" class="col-sm-2 control-label">请输入密码</label>
      <div class="col-sm-5">
         <input type="text" class="form-control" id="passWord">
      </div>
   </div>
   <div class="form-group">
      <label for="passWordRepeat" class="col-sm-2 control-label">请再次输入密码</label>
      <div class="col-sm-5">
         <input type="text" class="form-control" id="passWordRepeat">
      </div>
   </div>
   <div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
         <button type="submit" class="btn btn-default">发送验证码</button>
      </div>
    
<!--       TODO：增加发验证码到注册邮箱的text	 -->
   </div>
   <div class="form-group">
      <label for="verificationCode" class="col-sm-2 control-label">验证码</label>
      <div class="col-sm-5">
         <input type="text" class="form-control" id="verificationCode">
      </div>
   </div>
   <div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
         <button type="submit" class="btn btn-default">完成注册</button>
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