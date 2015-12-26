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
import service.IUserService;
import service.impl.TaskServiceImpl;
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

    	/* 将表单数据存储到formbean中 */
    	CreateTaskFormBean formBean = WebUtils.request2Bean(request, CreateTaskFormBean.class);
    	UserAccount user = (UserAccount)request.getSession().getAttribute("user");
    	formBean.setOwner(user.getMailAccount());
    	
    	
    	/* 在DB中更新相应的TaskFormBean*/
    	ITaskService taskService = new TaskServiceImpl();
    	boolean flag = false;
    	try {
    		flag = taskService.modifyTask(formBean);
    	}
    	catch(TaskException e)
    	{
    		e.printStackTrace();
    	}
    	
    	/* 向前端返回修改是否成功 */
    	if(flag){
    		response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		    String data = "任务已修改。";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8")); 
    	}
    	else {
    		response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		    String data = "修改任务失败。 ";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8"));
		}
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
    
}
