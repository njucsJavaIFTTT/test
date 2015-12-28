package web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import domain.UserAccount;
import exception.UserException;
import service.IUserService;
import service.impl.UserServiceImpl;
import util.WebUtils;
import web.formbean.RegisterFormBean;

//处理用户注册的Servlet
@WebServlet("/RegisterServlet")

public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 将客户端提交的表单数据封装到RegisterFormBean对象中
		RegisterFormBean formbean = WebUtils.request2Bean(request, RegisterFormBean.class);
		//前端已校验用户注册填写的表单数据
		UserAccount user = new UserAccount();
		try {
			// 注册字符串到日期的转换器
			ConvertUtils.register(new DateLocaleConverter(), Date.class);
			BeanUtils.copyProperties(user, formbean);// 把表单中名称相同数据进行复制
			// user.setId(WebUtils.makeId());//设置用户的Id属性
			user.SetUserName(formbean.getUserName());
			user.setMailAccount(formbean.getmailAccount());
			user.setPassword(formbean.getPassword());
			IUserService service = new UserServiceImpl();
			service.registerUser(user);//调用service层提供的注册用户服务实现用户注册
			
			response.setHeader("Content-type","text/html;charset=UTF-8");
		    String data = "success";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8")); 
		    /* 应跳转到登录界面 */
		    return;
			/*String message = String.format("注册成功.3秒后为您自动跳到登录页面.<meta http-equiv='refresh' content='3;url=%s'/>",
				request.getContextPath() + "/login.jsp");
			request.setAttribute("message", "注册成功");
			System.out.println(message);
			request.getRequestDispatcher("http://localhost:8080/test/message.jsp").forward(request, response);
			response.sendRedirect("index.jsp");*/
		} catch (UserException e) {
			response.setHeader("Content-type","text/html;charset=UTF-8");
		    String data = "Fail to register.Please check your information.";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8")); 
			return;
		} catch (Exception e) {
			e.printStackTrace(); // 在后台记录异常
			response.setHeader("Content-type","text/html;charset=UTF-8");
		    String data = "Sorry,something wrong with your register,please try later.";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8"));
		    /* 此处应跳转到message界面 */
		    
			/*request.setAttribute("message", "对不起，注册失败。");
			request.getRequestDispatcher("/message.jsp").forward(request, response);*/
			return;
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}