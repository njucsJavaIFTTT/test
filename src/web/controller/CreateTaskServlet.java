package web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

import domain.Goal;
import domain.Request;
import domain.Task;
import domain.UserAccount;
import service.IUserService;
import service.impl.UserServiceImpl;
import util.WebUtils;
import web.formbean.CreateTaskFormBean;

/**
 * 处理创建任务的Servlet
 * @author YJJ
 */
@WebServlet("/CreateTaskServlet")

public class CreateTaskServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    public CreateTaskServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    	/* 
    	 * Task.jsp中的thisType应为下拉框，post的值为int型；
    	 * 其中，0 - 定时，1 - 收邮件， 2 - 监听微博, 3 - 在给定时间内监听微博；
    	 * thatType同理，0 - 发微博， 1 - 发邮件；
    	 */
    	int thisType = Integer.parseInt(request.getParameter("thisType"));
    	int thatType = Integer.parseInt(request.getParameter("thatType"));
    	
    	/* 将表单数据存储到formbean中 */
    	CreateTaskFormBean formBean = WebUtils.request2Bean(request, CreateTaskFormBean.class);
    	UserAccount user = (UserAccount)request.getSession().getAttribute("user");
    	formBean.setOwner(user.getMailAccount());
    	
    	/* 对用户进行扣费,并更新DB中的用户信息（余额、消费记录、任务序列） */
    	IUserService userService = new UserServiceImpl();
    	try {
    		userService.chargeUser(formBean);
    	}
    	catch()
    	{
    		
    	}
    	
    	/* 将formBean存储到DB中 */
    	ITaskService taskService = new TaskServiceImpl();
    	try {
    		taskService.storeTask(formBean);
    	}
    	catch()
    	{
    		
    	}
    	
    	/* 返回任务信息给用户 */
    	
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
    
}
