package web.controller;

import java.io.IOException;
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
		System.out.println("00000");
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
			String message = String.format("注册成功.3秒后为您自动跳到登录页面.<meta http-equiv='refresh' content='3;url=%s'/>",
					request.getContextPath() + "/servlet/LoginUIServlet");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);

		} catch (UserException e) {
			System.out.println("用户已存在");
			formbean.getErrors().put("userName", "注册用户已存在");
			request.setAttribute("formbean", formbean);
			request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace(); // 在后台记录异常
			request.setAttribute("message", "对不起，注册失败。");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		 
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}