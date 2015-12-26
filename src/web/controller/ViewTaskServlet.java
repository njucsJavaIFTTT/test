package web.controller;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import domain.Task;
import domain.UserAccount;
import exception.TaskException;
import service.ITaskService;
import service.impl.TaskServiceImpl;

/**
 * 处理查看任务的Servlet
 * @author YJJ
 */
@WebServlet("/ViewTaskServlet")

public class ViewTaskServlet {

	private static final long serialVersionUID = 1L;

    public ViewTaskServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    	/* 获取当前session的用户名 */
    	UserAccount user = (UserAccount)request.getSession().getAttribute("user");
    	String userMailAccount = user.getMailAccount();
    	
    	/* 从数据库中获取该用户名下的任务列表 */
    	ITaskService taskService = new TaskServiceImpl();
    	Vector<Task> taskList = null;
    	try {
    		taskList = taskService.findTaskByMailAccount(userMailAccount);
    	}
    	catch(TaskException e)
    	{
    		e.printStackTrace();
    	}
    	/* 将任务列表通过Session传递到前端 */
    	request.getSession().setAttribute("list", taskList);
    	/* 待完成 */
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
}
