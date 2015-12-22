package domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import weibo4j.model.Status;
import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.Timeline;
import weibo4j.Users;

/* 任务实体类 */
public class Task implements Cloneable{
	private String TaskName;//任务名，对于同一用户不可重复
	private Request request;//that任务指针
	private Goal goal;//that任务指针
	private double expense;//费用
	
	public Task(String name,Request r,Goal g) throws Exception
	{
		TaskName = new String(name);
		request = (Request)r.clone();
		goal = (Goal)g.clone();
		/* 收费方式 */
		expense = 0;
		switch (request.thisType) {
		case OrderTime:
			expense += 50;
			break;
		case RecvMail:
			expense += 100;
			break;
		case MonitorWeibo:
			expense += 150;
			break;
		default:
			break;
		}
		switch (goal.thatType) {
		case SendMail:
			expense += 50;
			break;
		case SendWeibo:
			expense += 100;
			break;
		default:
			break;
		}
	}
	
	public String getTaskName() {
		return TaskName;
	}

	public double getExpense() {
		return expense;
	}

	public Object clone() throws CloneNotSupportedException {
		Task temp = (Task)super.clone();
		temp.TaskName = new String(this.TaskName);
		temp.request = (Request)this.request.clone();
		temp.goal = (Goal)this.goal.clone();
		return temp;
	}
	
	/* 新建任务：  用户创建好之后，返回刚刚创建的任务信息给用户。
付款是采用扣除会员会费的方式。 */
	/* 查看、删除、修改任务： 任务查看功能可以显示用户已经创建的所有任务， 每条显
示内容必须包括任务的完整信息描述。 用户也可以选择删除已有的任务。 同时，用
户可以修改已有的任务。 */
	/* 开始、停止任务：用户可以手动开始、停止已有的任务。 任务的运行收费采用扣除
会员会费的方式，同时根据会员的等级来给予一定的折扣和积分。 */
}

/* 面向用户的任务序列实体类 */
class TaskQueue implements Cloneable{
	private String UserMailAccount;//所属用户的账号邮箱
	private Task[] tasks;//任务数组指针
	private int top;
	private int maxLength;//当前序列最大容量
	
	/* 创建任务时传入任务所属用户的邮箱 */
	public TaskQueue(String MailAccount) {
		UserMailAccount = new String(MailAccount);
		maxLength = 10;
		tasks = new Task[maxLength];
		top = 0;
	}
	
	/* 增加任务，返回值0-正确，1-任务名重复，2-错误 */
	public int addTask(Task t) throws Exception
	{
		boolean repetition = false;
		for(int i = 0; i < top; i ++)
		{
			if(tasks[i].getTaskName().equals(t.getTaskName()))
			{
				repetition = true;
				break;
			}
		}
		if(repetition)
			return 1;
		if(top < maxLength && top >= 0)
		{
			tasks[top] = (Task)t.clone();
			top ++;
		}
		else if (top < 0)
			return 2;
		else
		{
			maxLength += 10;
			Task[] temp = new Task[maxLength];
			for(int i = 0; i < top; i ++)
			{
				temp[i] = tasks[i];
			}
			temp[top] = (Task)t.clone();
			top ++;
			tasks = temp;
		}
		return 1;
	}
	
	/* 删除任务，按任务名（任务名唯一），返回值0-找到，1-找不到 */
	public int deleteTask(String name)
	{
		boolean find = false;
		for(int i = 0; i < top; i ++)
		{
			if(tasks[i].getTaskName().equals(name))
			{
				find = true;
				for(int j = i; j < top - 1; j ++)
				{
					tasks[j] = tasks[j+1];
				}
				top --;
				tasks[top] = null;
				break;
			}
		}
		if(find)
			return 0;
		else
			return 1;
	}
	
	/* 修改任务，按任务名，返回值0-成功，1-名字重复，2-未找到 */
	public int setTask(String name,Task t) throws Exception
	{
		boolean find = false;
		for(int i = 0; i < top; i ++)
		{
			if(tasks[i].getTaskName().equals(name))
			{
				find = true;
				boolean repetition = false;
				for(int j = 0; j < top; j ++)
				{
					if(tasks[j].getTaskName().equals(t.getTaskName()))
					{
						repetition = true;
						break;
					}
				}
				if(repetition)
					return 1;
				else
					tasks[i] = (Task)t.clone();
				break;
			}
		}
		if(find)
			return 0;
		else 
			return 2;
	}
	
	/* 深复制 */
	public Object clone()throws CloneNotSupportedException {
		TaskQueue temp = (TaskQueue)super.clone();
		temp.UserMailAccount = new String(this.UserMailAccount);
		temp.top = this.top;
		temp.maxLength = this.maxLength;
		temp.tasks = new Task[maxLength];
		for(int i = 0; i < this.top; i ++)
		{
			temp.tasks[i] = (Task)this.tasks[i].clone();
		}
		return temp;
	}
}

enum ThisType {OrderTime,RecvMail,MonitorWeibo};
enum ThatType {SendWeibo,SendMail};

/* this任务抽象类 */
abstract class Request implements Cloneable{
	ThisType thisType;
	public abstract boolean ifThis() throws Exception;
	public Object clone() throws CloneNotSupportedException {
		Request request = (Request)super.clone();
		return request;
	}
}

/* that任务抽象类 */
abstract class Goal implements Cloneable{
	ThatType thatType;
	public abstract boolean thenThat() throws Exception;
	public Object clone() throws CloneNotSupportedException {
		Goal goal = (Goal)super.clone();
		return goal;
	}
}

/* this-定时任务 */
class OrderTime extends Request {
	private MyDate date;
	private MyTime time;
	Timer timer;
	MyTimerTask myTT;
	
	public OrderTime() {
		date = new MyDate();
		time = new MyTime();
		timer = null;
		myTT = null;
		super.thisType = ThisType.OrderTime;
	}
	
	public OrderTime(MyDate d,MyTime t) {
		date = new MyDate(d);
		time = new MyTime(t);
		timer = null;
		myTT = null;
		super.thisType = ThisType.OrderTime;
	}

	public void SetDateAndTime(MyDate d,MyTime t)throws Exception
	{
		d = (MyDate)this.date.clone();
		t = (MyTime)this.time.clone();
	}
	
	public boolean ifThis() {
		timer = new Timer();
		myTT = new MyTimerTask(); //设置定时器进行定时
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date tmp = null;
		try{
			tmp = df.parse(date.year+"-"+date.month+"-"+date.day+" "+time.hour+":"+time.minute+":"+time.second);
			timer.schedule(myTT, tmp);
		}
		catch(ParseException e){}
		try{
			while(true){
				Thread.sleep(1000);
				if(myTT.getReady() == true)
					break;
			}
		}
		catch(Exception e){return false;}
		return true;
	}
	
	public Object clone() throws CloneNotSupportedException
	{
		OrderTime orderTime = (OrderTime)super.clone();
		orderTime.date = (MyDate)this.date.clone();
		orderTime.time = (MyTime)this.time.clone();
		orderTime.thisType = ThisType.OrderTime;
		return orderTime;
	}
}

/* this-收到QQ邮件任务 */
class RecvMail extends Request {
	private String username;
	private String password;
	//private Timer timer;
	//private MyTimerTask myTT;
	
	public RecvMail() {
		username = "";
		password = "";
		//timer = null;
		//myTT = null;
		super.thisType = ThisType.RecvMail;
	}
	
	public RecvMail(String name, String pw) {
		username = new String(name);
		password = new String(pw);
		//timer = null;
		//myTT = null;
		super.thisType = ThisType.RecvMail;
	}
	
	public void SetUserAndPw(String name,String pw) {
		username = new String(name);
		password = new String(pw);
	}

	/* 查看qq邮箱的收件箱邮件数目 */
    public int recvMail() {
		String protocol = "pop3";
		boolean isSSL = true;
		String host = "pop.qq.com";
		int port = 995;
		
		Properties props = new Properties();
		props.put("mail.pop3.ssl.enable", isSSL);
		props.put("mail.pop3.host", host);
		props.put("mail.pop3.port", port);
		
		Session session = Session.getDefaultInstance(props);
		
		Store store = null;
		Folder folder = null;
		int size = 0;
		try {
			store = session.getStore(protocol);
			store.connect(host,username,password);
			
			folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			
			size = folder.getMessageCount();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException  e) {
			e.printStackTrace();
		} finally {
			try {
				if(folder != null) {
					folder.close(false);
				}
				if(store != null) {
					store.close();
				}
			} catch(MessagingException e) {
				e.printStackTrace();
				return -1;
			}
		}
		return size;
	}
    
	public boolean ifThis() {
		try
		{
			System.out.println("收邮件...");
			
			int size = recvMail();
			if(size == -1) {
				System.out.println("收邮件失败！");
				return false;
			}
			
			System.out.println("等待...");
			
			try{
				while(true){
					Thread.sleep(1000);
					int newsize = recvMail();
					if(newsize < 0) {
						System.out.println("收邮件失败！");
						return false;
					}
					else if(newsize > size){
						System.out.println("发现新邮件...");
						break;
					}
				}
			}
			catch(Exception e){return false;}
		}
		catch(Exception e){}		
		return true;
	}
	
	public Object clone() throws CloneNotSupportedException {
		RecvMail recvMail = (RecvMail)super.clone();
		recvMail.username = new String(this.username);
		recvMail.password = new String(this.password);
		recvMail.thisType = ThisType.RecvMail;
		//recvMail.timer = null;
		//recvMail.myTT = null;
		return recvMail;
	}
}

/* this-监听微博任务 */
class MonitorWeibo extends Request {
	private String accessToken;//被监听微博的授权accessToken
	private String uid;//被监听微博id
	private String contain;//监听所包含的内容
	//MyTimerTask myTT;//设置TimerTask
	//Timer timer;//设置Timer
	
	public MonitorWeibo() {
		accessToken = "";
		uid = "";
		contain = "";
		//timer = null;
		//myTT = null;
		super.thisType = ThisType.MonitorWeibo;
	}
	
	/* 参数分别为accessToken，用户名，监听内容 */
	public MonitorWeibo(String access,String id,String con) {
		accessToken = new String(access);
		uid = new String(id);
		contain = new String(con);
		//timer = null;
		//myTT = null;
		super.thisType = ThisType.MonitorWeibo;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = new String(accessToken);
	}

	public void setUid(String uid) {
		this.uid = new String(uid);
	}

	public void setContain(String contain) {
		this.contain = new String(contain);
	}

	/* 获取当前授权登录用户的最新微博内容 */
	public Status GetLatestWeibo() {
		Users users = new Users(accessToken);
		User user = null;
		try {
			user = users.showUserById(uid);
		}
		catch(WeiboException ex) {
			ex.printStackTrace();
		}
		Status status = null;
		if(user != null)
		{
			status = user.getStatus();
			return status;
		}
		return null;
	}

	public boolean ifThis() {
		try{
			System.out.println("监听微博...");
			
			Status oldStatus = GetLatestWeibo();
			
			if(oldStatus == null) {
				System.out.println("监听失败！");
				return false;
			}
			
			System.out.println("等待...");
			
			Status newstatus = null;
			try{
				while(true){
					Thread.sleep(1000);
					newstatus = GetLatestWeibo();
					if(newstatus == null) {
						System.out.println("监听失败！");
						return false;
					}
					else if(newstatus.getCreatedAt().after(oldStatus.getCreatedAt())){
						System.out.println("发现新微博...");
						if(newstatus.getText().contains(contain))
						{
							System.out.println("监听到指定信息！");
							break;
						}
						oldStatus = newstatus;
						newstatus = null;
					}
				}
			}
			catch(Exception e){return false;}
		}
		catch(Exception e){}		
		return true;	
	}
	
	public Object clone() throws CloneNotSupportedException
	{
		MonitorWeibo monitorWeibo = (MonitorWeibo)super.clone();
		monitorWeibo.accessToken = new String(accessToken);
		monitorWeibo.uid = new String(uid);
		monitorWeibo.contain = new String(contain);
		monitorWeibo.thisType = ThisType.MonitorWeibo;
		//monitorWeibo.timer = null;
		//monitorWeibo.myTT = null;
		return monitorWeibo;
	}
}

/* that-发送微博任务 */
class SendWeibo extends Goal {
	private String accessToken;
	private String uid;
	private String content;

	public SendWeibo() {
		accessToken = "";
		uid = "";
		content = "";
		thatType = ThatType.SendWeibo;
	}
	
	public SendWeibo(String access, String id, String con)
	{
		accessToken = new String(access);
		uid = new String(uid);
		content = new String(con);
		thatType = ThatType.SendWeibo;
	}
	
	public void SendWeiboStatus()
	{   
		Timeline tm = new Timeline(accessToken);
		try{ 
			Status status = tm.updateStatus(content); 
			Log.logInfo(status.toString()); 
		} 
		catch(WeiboException e) { 
			e.printStackTrace(); 
		} 
	}
	
	public boolean thenThat() {
		try{
			System.out.println("发送微博...");
			SendWeiboStatus();
			System.out.println("发送完毕...");
		}
		catch(Exception e){
			System.out.println("发送微博失败！");
			return false;
		}
		return true;
	}
	
	public Object clone() throws CloneNotSupportedException
	{
		SendWeibo sendWeibo = (SendWeibo)super.clone();
		sendWeibo.accessToken = new String(accessToken);
		sendWeibo.uid = new String(uid);
		sendWeibo.content = new String(content);
		sendWeibo.thatType = ThatType.SendWeibo;
		return sendWeibo;
	}
}

/* that-发送邮件任务 */
class SendMail extends Goal {
	private String username;//收信人id
	private String content;

	public SendMail() {
		username = null;
		content = null;
		thatType = ThatType.SendMail;
	}
	
	public SendMail(String user, String pass, String con) {
		username = new String(user);
		content = new String(con);
		thatType = ThatType.SendMail;
	}
	
	public void setUsername(String username) {
		this.username = new String(username);
	}

	public void setContent(String content) {
		this.content = new String(content);
	}

	public boolean thenThat() {
		System.out.println("发送邮件...");	
		String smtp = "smtp.qq.com";
		String from = "809336646@qq.com";
		String to = username;
		String copyto = "";
		String subject = "[IFTTT]任务邮件";
		String content = this.content;
		String username = "809336646";
		String password = "xieckoriioihbdia";
		if(Mail.sendAndCc(smtp, from, to, copyto, subject, content, username, password))
			System.out.println("发送完毕...");
		else{
			System.out.println("发送失败！");
			return false;
		}
		return true;
	}
	
	public Object clone() throws CloneNotSupportedException
	{
		SendMail sendMail = (SendMail)super.clone();
		sendMail.username = new String(username);
		sendMail.content = new String(content);
		sendMail.thatType = ThatType.SendMail;
		return sendMail;
	}
}

