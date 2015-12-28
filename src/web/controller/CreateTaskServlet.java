package web.controller;

import java.io.IOException;
import java.io.OutputStream;

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
    	 * Task.jsp中的thisType的post值为int型；
    	 * 其中，0 - 定时，1 - 收邮件， 2 - 监听微博, 3 - 在给定时间内监听微博；
    	 * thatType同理，0 - 发微博， 1 - 发邮件；
    	 */
    	
    	/* 将表单数据存储到formbean中 */
    	CreateTaskFormBean formBean = WebUtils.request2Bean(request, CreateTaskFormBean.class);
    	//UserAccount user = (UserAccount)request.getSession().getAttribute("user");
    	//System.out.println(formBean.getMailContent());
    	//System.out.println(request.getParameter("mailContent"));
    	//System.out.println(formBean.getReceiverMailAccount());
    	//formBean.setOwnerMail(user.getMailAccount());
    	
    	boolean flag = true;//用于记录存储formBean是否成功的标志位
    	
    	/* 将formBean存储到DB中的Task表中 */
    	ITaskService taskService = new TaskServiceImpl();
    	try {
    		taskService.storeTask(formBean);
    	}
    	catch(TaskException e)
    	{
    		flag = false;
    		e.printStackTrace();
    	}

    	/* 返回创建任务信息给用户 */
    	if(flag){
    		response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		    String data = "Create task successfully.";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8")); 
		    /* 前端应跳转到当前任务的详细内容查看界面 */
    	}
    	else {
    		response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		    String data = "Fail to create the task. Please try later.";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8"));
		    /* 不需跳转 */
    	}
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
    
}
