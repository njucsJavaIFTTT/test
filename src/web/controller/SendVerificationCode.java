package web.controller;

import java.io.IOException;
import java.io.OutputStream;

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

       	String smtp = "smtp.qq.com";
		String from = "809336646@qq.com";
		//String from = "376575092@qq.com";
		String to = request.getParameter("userID");
		String copyto = "";
		String subject = "[IFTTT]VerificationMail";
		String content = "[IFTTT]用户注册邮箱验证码：" 
				+ request.getParameter("verificationCode")
				+ "。如非本人直接访问IFTTT，请停止操作，切勿将验证码提供给第三方。";
		String username = "809336646";//服务器邮箱账号
		String password = "xieckoriioihbdia";//服务器邮箱密码
		//String username = "376575092";//服务器邮箱账号
		//String password = "qvdgupvuipqdbgbb";//服务器邮箱密码
		
		/* 验证邮箱是否已存在 */
		/* Add here */
		
		if(Mail.sendAndCc(smtp, from, to, copyto, subject, content, username, password))
		{
			response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		    String data = "验证码已发送至邮箱" + from + "， 请注意查收";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8")); 
		}
		else{
			response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		    String data = "服务器太过拥挤，请稍候片刻 ";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8"));
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
