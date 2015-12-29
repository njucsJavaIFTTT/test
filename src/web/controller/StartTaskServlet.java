package web.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Execute;
import domain.Task;
import exception.TaskException;
import exception.UserException;
import service.ITaskService;
import service.IUserService;
import service.impl.TaskServiceImpl;
import service.impl.UserServiceImpl;
import web.formbean.CreateTaskFormBean;

/**
 * 处理开始任务的Servlet
 * @author YJJ
 */
@WebServlet("/StartTaskServlet")

public class StartTaskServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    public StartTaskServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    	
    	/* 从前台获取TaskId */
    	int taskId = Integer.parseInt(request.getParameter("taskId"));
    	
    	System.out.println(taskId);
    	
    	boolean flag = true;
    	
    	/* 从数据库中查找到该任务的TaskFormBean */
    	ITaskService service = new TaskServiceImpl();
    	CreateTaskFormBean formBean = null;
    	try{
    		formBean = service.findTask(taskId);
    	}
    	catch(TaskException e){
    		flag = false;
    		e.printStackTrace();
    	}
    	
    	/* 创建相应的Task类 */
    	Task task = null;
    	try{
    		task = service.createTask(formBean);
    	}
    	catch(TaskException e){
    		e.printStackTrace();
    		flag = false;
    	}
    	
		/* 对用户进行收费 */
    	IUserService userService = new UserServiceImpl();
    	try{
    		userService.chargeUser(formBean);
    	}
    	catch(UserException e){
    		e.printStackTrace();
    		flag = false;
    	}
    	
		/* 将Task添加到ExecuteList中  */
		Execute execute = new Execute(task, formBean.getOwnerMail(), task.getTaskID());
		try{
			service.addTaskIntoExecuteList(execute);
		}
		catch(TaskException e){
			e.printStackTrace();
			flag = false;
		}
		
		if(!flag) {
			response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		    String data = "任务启动失败。 ";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8"));
		}
		else {
			execute.start();
			response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
			String data = "任务启动成功。";
			OutputStream stream = response.getOutputStream();
			stream.write(data.getBytes("UTF-8")); 
		}
		/* 此处不需跳转 */
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
}
