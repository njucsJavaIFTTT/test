package web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DBHelperImpl;
import domain.UserAccount;
import exception.UserException;
import service.IUserService;
import service.impl.UserServiceImpl;

//处理用户登录的servlet
@WebServlet("/LoginServlet")

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 获取用户填写的登录用户名
		String userMailAccount = request.getParameter("username");
		// 获取用户填写的登录密码
		String password = request.getParameter("password");
		
		if (userMailAccount.equals("root") && password.equals("root")) {
			//管理员账户
			DBHelperImpl db=new DBHelperImpl();
			Vector<UserAccount> userList = db.viewAllUsers();
			
			request.getSession().setAttribute("userList", userList);
			response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		    String data = "manager";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8"));
		    return;
		}
		else {
			IUserService service = new UserServiceImpl();
			// 用户登录
			UserAccount user = null;
			try {
				user = service.loginUser(userMailAccount, password);
			} catch (UserException e) {
				System.out.println("用户名或密码错误");
				response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
			    String data = "用户名或密码错误";
			    OutputStream stream = response.getOutputStream();
			    stream.write(data.getBytes("UTF-8"));
				return;
			}
			// 登录成功后，就将用户存储到session中
			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute("userMailAccount", userMailAccount);
			response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		    String data = "Login successfully.";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8"));
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
