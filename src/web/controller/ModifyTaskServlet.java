package web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.UserAccount;
import service.IUserService;
import service.impl.UserServiceImpl;
import util.WebUtils;
import web.formbean.CreateTaskFormBean;

/**
 * 处理修改任务的Servlet
 * @author YJJ
 */
@WebServlet("/ModifTaskServlet")

public class ModifyTaskServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

    public ModifyTaskServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

    	int thisType = Integer.parseInt(request.getParameter("thisType"));
    	int thatType = Integer.parseInt(request.getParameter("thatType"));
    	
    	/* 将表单数据存储到formbean中 */
    	CreateTaskFormBean formBean = WebUtils.request2Bean(request, CreateTaskFormBean.class);
    	UserAccount user = (UserAccount)request.getSession().getAttribute("user");
    	formBean.setOwner(user.getMailAccount());
    	
    	/* 对用户进行扣费,并更新DB中的用户信息（余额、消费记录、任务序列） */
    	IUserService userService = new UserServiceImpl();
    	try {
    		userService.Charge(user.getMailAccount(),formBean.getTaskName());
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
