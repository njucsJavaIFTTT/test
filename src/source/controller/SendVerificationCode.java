package source.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Mail;



/**
 * 处理邮箱验证的Servlet
 * @author YJJ
 */
@WebServlet("/SendVerificationCode")

public class SendVerificationCode extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SendVerificationCode() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//将客户端提交的表单数据封装到VerificationFormBean对象中
		System.out.println("hahaha");
       	String smtp = "smtp.qq.com";
		String from = "809336646@qq.com";
		String to = request.getParameter("userID");
		String copyto = "";
		String subject = "[IFTTT]VerificationMail";
		String content = "[IFTTT]用户注册邮箱验证码：" 
				+ request.getParameter("verificationCode")
				+ "。如非本人直接访问IFTTT，请停止操作，切勿将验证码提供给第三方。";
		String username = "809336646";//服务器邮箱账号
		String password = "294112009yaoyao";//服务器邮箱密码
		
		/* 验证邮箱是否已存在 */
		/* Add here */
		
		if(Mail.sendAndCc(smtp, from, to, copyto, subject, content, username, password))
		{
			String message = "验证成功 ";
			request.setAttribute("message",message);
			
			request.getRequestDispatcher("/register.jsp").forward(request,response);
		}
		else{
			String message = "服务器太过拥挤，请稍候片刻 ";
			request.setAttribute("message",message);
			request.getRequestDispatcher("/register.jsp").forward(request,response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
