package web.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import domain.UserAccount;
import exception.UserException;
import service.IUserService;
import service.impl.UserServiceImpl;

//处理用户登录的servlet
@WebServlet("/LoginServlet")

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 获取用户填写的登录用户名
		String username = request.getParameter("username");
		// 获取用户填写的登录密码
		String password = request.getParameter("password");

		IUserService service = new UserServiceImpl();
		// 用户登录
		UserAccount user;
		try {
			user = service.loginUser(username, password);
		} catch (UserException e) {
			System.out.println("用户名或密码错误");
			response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		    String data = "用户名或密码错误";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8"));
		//	response.sendRedirect("/test/message.jsp");
//			String message = String.format("用户名或密码有误.请重新登录.2秒后为您自动跳到登录页面.<meta http-equiv='refresh' content='2;url=%s'",
//					request.getContextPath() + "/servlet/LoginUIServlet");
//			request.setAttribute("message", message);
//			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		// 登录成功后，就将用户存储到session中
		request.getSession().setAttribute("user", user);
		String message = String.format("%s登陆成功.本页将在3秒后跳到首页.<meta http-equiv='refresh' content='3;url=%s'",
				user.getUsername(), request.getContextPath() + "/index.jsp");
		request.setAttribute("message", message);
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
