package web.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Execute;
import exception.TaskException;
import service.ITaskService;
import service.impl.TaskServiceImpl;

/**
 * 处理停止任务的Servlet
 * @author YJJ
 */
@WebServlet("/StopTaskServlet")

public class StopTaskServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    public StopTaskServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    	
    	/* 从前台获取任务的id */
    	int taskId = Integer.parseInt(request.getParameter("taskId"));
    	
    	/* 到ExecuteList中搜索ID为id的Execute任务 */
    	ITaskService service = new TaskServiceImpl();
    	Execute execute = null;
    	try{
    		execute = service.findExecuteInList(taskId);
    	}
    	catch(TaskException e){
    		e.printStackTrace();
    	}
    	
    	boolean flag  = true;
    	if(execute == null)
    	{
    		System.out.println("任务已停止。");
    		return;
    	}
    	else{
    		try {
    	    	/* 调用ExecuteQueue类的方法，停止该任务 */
        		execute.stopExecute();
        	}
        	catch(Exception e){
        		flag = false;
        		e.printStackTrace();
        	}
    	}
    	
    	
    	/* 返回停止成功或失败 */
    	if(flag){
    		response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		    String data = "任务已停止。";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8")); 
    	}
    	else {
    		response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		    String data = "停止任务失败。 ";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8"));
		}
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
}
