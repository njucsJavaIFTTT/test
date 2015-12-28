package web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Task;
import domain.UserAccount;
import exception.TaskException;
import service.ITaskService;
import service.impl.TaskServiceImpl;
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

    	/* 将表单数据存储到formbean中 */
    	CreateTaskFormBean formBean = WebUtils.request2Bean(request, CreateTaskFormBean.class);
    	UserAccount user = (UserAccount)request.getSession().getAttribute("user");
    	formBean.setOwnerMail(user.getMailAccount());
    	
    	
    	/* 在DB中更新相应的TaskFormBean*/
    	ITaskService taskService = new TaskServiceImpl();
    	try {
    		taskService.modifyTask(formBean);
    	}
    	catch(TaskException e)
    	{
    		response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		    String data = "Fail to modify the task.";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8"));
    		e.printStackTrace();
    		/* 前端应跳转到message界面 */
    	}
    	
    	/* 向前端返回修改是否成功 */
    	response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		String data = "The task has been modified successfully.";
		OutputStream stream = response.getOutputStream();
		stream.write(data.getBytes("UTF-8")); 
		
    	/* 前端应跳转到查看任务界面 */
    	String userMailAccount = user.getMailAccount();
		Vector<CreateTaskFormBean> taskList = null;
    	try {
    		taskList = taskService.findTaskByMailAccount(userMailAccount);
    	}
    	catch(TaskException e)
    	{
    		e.printStackTrace();
    	}
    	request.getSession().setAttribute("list", taskList);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
    
}
