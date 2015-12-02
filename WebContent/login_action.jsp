<%@page import="javax.xml.ws.Response"%>
<%@page import="com.sun.corba.se.spi.orbutil.fsm.State"%>
<%@page import="com.sun.xml.internal.bind.v2.schemagen.xmlschema.Import"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%! String userID;%>
<%! String passWord; %>
<% 
/*
TODO:
	登录信息的验证---连接数据库
*/

userID = request.getParameter("userID");//按照id取得前端的信息
passWord = request.getParameter("passWord");//同上
out.print("userid:"+userID+"; password"+passWord);//输出的信息即为服务器返回给前端的信息


//TIP：若想返回一组数据，则查看JSON数据包（这个我还没研究明白。。。）
//		反正无论结果如何，返回的数据的格式都应该是一致的
//		举个例子：{ “登陆成功与否”， “错误信息（若成功则此处为null）”}



//P.S. 注册同理，但由于要添加“邮箱验证”，结构应该会复杂一些。。容我再想0 0.。。


%>
	