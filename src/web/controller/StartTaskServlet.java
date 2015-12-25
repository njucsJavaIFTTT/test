package web.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Execute;
import domain.Goal;
import domain.MonitorWeibo;
import domain.MonitorWeiboWithinLimitTime;
import domain.MyDate;
import domain.MyTime;
import domain.OrderTime;
import domain.RecvMail;
import domain.Request;
import domain.SendMail;
import domain.SendWeibo;
import domain.Task;
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
    	
    	/* 从前台获取Taskowner和taskname */
    	String TaskOwner = request.getParameter("userMailAccount");
    	String TaskName = request.getParameter("taskName");
    	
    	/* 从数据库中查找到该任务的TaskFormBean */
    	ITaskService service = new TaskServiceImpl();
    	CreateTaskFormBean formBean = service.findTask(TaskOwner,TaskName);
    	
    	/* 创建相应的Task类 */
    	/* 按任务种类创建this任务 */
		int thistype = formBean.getThisType();
		Request rqt = null;
		switch (thistype) {
		case 0:{
			String timeset = formBean.getTime();
			String year = timeset.substring(0, 3);
			String month = timeset.substring(5, 6);
			String day = timeset.substring(8, 9);
			String hour = timeset.substring(11,12);
			String minute = timeset.substring(14,15);
			String second = timeset.substring(17,18);
			MyDate date = new MyDate(year, month, day);
			MyTime time = new MyTime(hour, minute, second);
			rqt = new OrderTime(date, time);
			break;
		}
		case 1:{
			String userQQAccount = formBean.getMonitorMailAccount();
			String userQQPassword = formBean.getMonitorMailpassword();
			rqt = new RecvMail(userQQAccount, userQQPassword);
			break;
		}
		case 2:{
			String userWeiboAccount = formBean.getMonitorWeiboAccount();
			String accessToken = formBean.getMonitorWeiboAccessToken();
			String content = formBean.getMonitorContain();
			rqt = new MonitorWeibo(accessToken, userWeiboAccount, content);
			break;
		}
		case 3:{
			String userWeiboAccount = formBean.getMonitorWeiboAccount();
			String accessToken = formBean.getMonitorWeiboAccessToken();
			int minute = formBean.getMinute();
			rqt = new MonitorWeiboWithinLimitTime(accessToken, userWeiboAccount, minute);
			break;
		}
		default:
		{
			throw new UserException("Wrong This Type!");
		}
		}
		
		/* 按任务种类创建that任务 */
		int thattype = formBean.getThisType();
		Goal goal = null;
		switch (thattype) {
		case 0:{
			String accessToken = formBean.getSendWeiboAccessToken(); 
			String WeiboAccount = formBean.getSendWeiboAccount();
			String content = formBean.getWeiboContent();
			goal = new SendWeibo(accessToken, WeiboAccount, content);
			break;	
		}
		case 1:{
			String recvMailAccount = formBean.getReceiverMailAccount();
			String content = formBean.getMailContent();
			goal = new SendMail(recvMailAccount, content);
			break;
		}
		default:{
			throw new UserException("Wrong That Type!");
		}
		}
		
		/* 创建Task用于收费 */
		Task task = null;
		try {
			task = new Task(formBean.getTaskName(), rqt, goal);
		}
		catch(CloneNotSupportedException e){
			e.printStackTrace();
		}
		
		/* 将Task添加到ExecuteList中，返回指向该任务的指针 */
		Execute execute = service.addTaskIntoExecuteList(task);
		
    	/* 运行该任务，并设置返回信息 */
		if(execute != null){
			execute.start();
			response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		    String data = "任务启动成功。";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8")); 
		    /* 传递execute指针到前端 */
		    /* Add Here */
		}
		else {
			response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		    String data = "任务启动失败。 ";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8"));
		}
		
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
}
