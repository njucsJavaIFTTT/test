package web.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.TaskException;
import service.ITaskService;
import service.impl.TaskServiceImpl;

/**
 * 处理删除任务的Servlet
 * @author YJJ
 */
@WebServlet("/DeleteTaskServlet")

public class DeleteTaskServlet {

	private static final long serialVersionUID = 1L;

    public DeleteTaskServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    	/* 获取需要删除的TaskId */
    	int taskId = Integer.parseInt(request.getParameter("taskId"));
    	
    	/* 按TaskId去DB中删除相应记录 */
    	ITaskService taskService = new TaskServiceImpl();
    	boolean flag = false;
    	try{
    		flag = taskService.deleteTaskFormBean(taskId);
    	}
    	catch(TaskException e){
    		e.printStackTrace();
    	}
    	/* 向前端返回删除是否成功的信息 */
    	if(flag){
    		response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		    String data = "任务已删除。";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8")); 
    	}
    	else{
    		response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		    String data = "删除任务失败。 ";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8"));
    	}
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
}
