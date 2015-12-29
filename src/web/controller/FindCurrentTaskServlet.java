package web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.TaskException;
import service.ITaskService;
import service.impl.TaskServiceImpl;
import web.formbean.CreateTaskFormBean;

public class FindCurrentTaskServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public FindCurrentTaskServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		int taskId = Integer.parseInt(request.getParameter("currentTaskId"));
		System.out.println(taskId);
		ITaskService taskService = new TaskServiceImpl();
		CreateTaskFormBean formBean = null;
		try{
			formBean = taskService.findTask(taskId);
		}
		catch(TaskException e){
			e.printStackTrace();
			return;
		}
		
		request.getSession().setAttribute("currentTaskFormBean", formBean);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
}
