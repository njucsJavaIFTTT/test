package service.impl;

import java.sql.SQLException;
import java.util.Vector;

import DB.DBHelper;
import DB.DBHelperImpl;
import domain.Execute;
import domain.ExecuteQueue;
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
import exception.TaskException;
import service.ITaskService;
import web.formbean.CreateTaskFormBean;

public class TaskServiceImpl implements ITaskService{

	@Override
	public CreateTaskFormBean findTask(int taskId) throws TaskException {
		DBHelper db = new DBHelperImpl();
		CreateTaskFormBean  formBean = null;
		try{
			formBean = db.findTask(taskId);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		db.close();
		return formBean;
	}

	@Override
	public void addTaskIntoExecuteList(Execute execute) throws TaskException {
		ExecuteQueue.addExecute(execute);
	}

	@Override
	public Execute findExecuteInList(int taskId) throws TaskException {
		Execute execute = ExecuteQueue.findExecuteInList(taskId);
		return execute;
	}

	@Override
	public Task createTask(CreateTaskFormBean formBean) throws TaskException {
		/* 按任务种类创建this任务 */
	//	System.out.println(formBean.getThisType());
		int thistype = Integer.parseInt(formBean.getThisType());
		
		Request rqt = null;
		switch (thistype) {
		case 0:{
			String timeset = formBean.getOrderedTime();
			System.out.println("haha"+timeset);
			String year = timeset.substring(0, 4);
			String month = timeset.substring(5, 7);
			String day = timeset.substring(8, 10);
			String hour = timeset.substring(11,13);
			String minute = timeset.substring(14,16);
			String second = timeset.substring(17,19);
			MyDate date = new MyDate(year, month, day);
			MyTime time = new MyTime(hour, minute, second);
			rqt = new OrderTime(date, time);
			System.out.println(year + "," + month);
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
			int minute = Integer.parseInt(formBean.getListenMinute());
			rqt = new MonitorWeiboWithinLimitTime(accessToken, userWeiboAccount, minute);
			break;
		}
		default:
		{
			throw new TaskException("Wrong This Type!");
		}
		}
		
		/* 按任务种类创建that任务 */
		int thattype = Integer.parseInt(formBean.getThatType());
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
			throw new TaskException("Wrong That Type!");
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
		return task;
	}

	@Override
	public void storeTask(CreateTaskFormBean formBean) throws TaskException {
		DBHelper db = new DBHelperImpl();
		db.storeTask(formBean);
		db.close();
	}

	@Override
	public void modifyTask(CreateTaskFormBean formBean) throws TaskException {
		DBHelper db = new DBHelperImpl();
		db.modifyTask(formBean);
		db.close();
	}

	@Override
	public Vector<CreateTaskFormBean> findTaskByMailAccount(String userMailAccount) throws TaskException {
		DBHelper db = new DBHelperImpl();
		Vector<CreateTaskFormBean> vector = null;
		try{
			vector = db.viewTask(userMailAccount);
		}
		catch(SQLException e){
			e.printStackTrace();
			db.close();
			return null;
		}
		db.close();
		return vector;
	}

	@Override
	public void deleteTaskFormBean(CreateTaskFormBean formBean) throws TaskException {
		DBHelper db = new DBHelperImpl();
		db.deleteTask(formBean);
		db.close();;
	}

}
