package web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.UserAccount;
import exception.TaskException;
import service.ITaskService;
import service.impl.TaskServiceImpl;
import util.WebUtils;
import web.formbean.CreateTaskFormBean;

/**
 * 处理删除任务的Servlet
 * @author YJJ
 */
@WebServlet("/DeleteTaskServlet")

public class DeleteTaskServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

    public DeleteTaskServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    	/* 获取需要删除的FormBean */
    	CreateTaskFormBean formBean = WebUtils.request2Bean(request, CreateTaskFormBean.class);
    	
    	/* 去DB中删除相应记录 */
    	ITaskService taskService = new TaskServiceImpl();
    	try{
    		taskService.deleteTaskFormBean(formBean);
    	}
    	catch(TaskException e){
    		response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		    String data = "Fail to delete the task.";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8"));
    		e.printStackTrace();
    		/* 跳转到message界面 */
    	}
    	
    	/* 向前端返回删除信息，和新的任务序列 */
    	response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		String data = "Delete the task successfully.";
		OutputStream stream = response.getOutputStream();
		stream.write(data.getBytes("UTF-8")); 
		/* 跳转到查看任务界面 */
		
		UserAccount user = (UserAccount)request.getSession().getAttribute("user");
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
