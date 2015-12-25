package service.impl;
import org.eclipse.jdt.internal.compiler.ast.ThrowStatement;

import com.mysql.jdbc.exceptions.MySQLTimeoutException;

import dao.IUserDao;
import dao.impl.UserDaoImpl;
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
import domain.UserAccount;
import exception.UserException;
import service.IUserService;
import web.formbean.CreateTaskFormBean;
import weibo4j.model.User;

//IUserService接口的实现-提供给用户的服务的实现
public class UserServiceImpl implements IUserService{
	private IUserDao uDao=new UserDaoImpl();
	
	public void registerUser(UserAccount user)throws UserException{
		if (uDao.find(user.getMailAccount())!=null) {
			throw new UserException("注册的邮箱已存在!");
		}
		uDao.add(user);
	}
	
	public UserAccount loginUser(String uName,String uPwd)throws UserException{
		UserAccount user = uDao.find(uName,uPwd);
		if (user==null) {
			throw new UserException("邮箱或密码错误!");
		}
		return user;
	}

	@Override
	public void chargeUser(CreateTaskFormBean formBean) throws UserException {
		String uMailAccount = formBean.getOwner();
		String password = uDao.find(uMailAccount);
		if(password == null)
			throw new UserException("任务所属用户不存在!");
			
		/* 按任务种类创建this任务 */
		int thistype = formBean.getThisType();
		Request request = null;
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
			request = new OrderTime(date, time);
			break;
		}
		case 1:{
			String userQQAccount = formBean.getMonitorMailAccount();
			String userQQPassword = formBean.getMonitorMailpassword();
			request = new RecvMail(userQQAccount, userQQPassword);
			break;
		}
		case 2:{
			String userWeiboAccount = formBean.getMonitorWeiboAccount();
			String accessToken = formBean.getMonitorWeiboAccessToken();
			String content = formBean.getMonitorContain();
			request = new MonitorWeibo(accessToken, userWeiboAccount, content);
			break;
		}
		case 3:{
			String userWeiboAccount = formBean.getMonitorWeiboAccount();
			String accessToken = formBean.getMonitorWeiboAccessToken();
			int minute = formBean.getMinute();
			request = new MonitorWeiboWithinLimitTime(accessToken, userWeiboAccount, minute);
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
			task = new Task(formBean.getTaskName(), request, goal);
		}
		catch(CloneNotSupportedException e){
			e.printStackTrace();
		}
		
		uDao.charge(uMailAccount,task);
		
	}
	
}
