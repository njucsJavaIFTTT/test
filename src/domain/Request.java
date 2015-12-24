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

import weibo4j.Users;
import weibo4j.model.Status;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

enum ThisType {OrderTime,RecvMail,MonitorWeibo,MonitorWeiboWithinLimitTime};

/* this任务抽象类 */
public abstract class Request implements Cloneable{
	ThisType thisType;
	public abstract boolean ifThis() throws Exception;
	public Object clone() throws CloneNotSupportedException {
		Request request = (Request)super.clone();
		return request;
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

class MonitorWeiboWithinLimitTime extends Request {
	private String accessToken;//被监听微博的授权accessToken
	private String uid;//被监听微博id
	private int minutes;
	MyTimerTask myTT;
	Timer timer;
	
	public MonitorWeiboWithinLimitTime() {
		accessToken = "";
		uid = "";
		minutes = -1;
		myTT = null;
		timer = null;
		thisType = ThisType.MonitorWeiboWithinLimitTime;
	}
	
	public MonitorWeiboWithinLimitTime(String access, String id, int min) {
		accessToken = new String(access);
		uid = new String(id);
		minutes = min;
		myTT = null;
		timer = null;
		thisType = ThisType.MonitorWeiboWithinLimitTime;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = new String(accessToken);
	}

	public void setUid(String uid) {
		this.uid = new String(uid);
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
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

		boolean sig = true;
		
		try{
			System.out.println("监听微博...");
			
			Status oldStatus = GetLatestWeibo();
			
			if(oldStatus == null) {
				System.out.println("监听失败！");
				return false;
			}
			
			System.out.println("等待...");
			
			timer = new Timer();
			Date tmp = new Date(new Date().getTime() + minutes * 60000);
			timer.schedule(myTT, tmp);
			
			Status newstatus = null;
			
			try{
				while(! myTT.getReady()){
					Thread.sleep(1000);
					newstatus = GetLatestWeibo();
					if(newstatus == null) {
						System.out.println("监听失败！");
						return false;
					}
					else if(newstatus.getCreatedAt().after(oldStatus.getCreatedAt())){
						System.out.println("发现新微博...");
						sig = false;
						break;
					}
				}
			}
			catch(Exception e){return false;}
		}
		catch(Exception e){ e.printStackTrace();}
		
		if(sig)
			return true;
		else 
			return false;
	}

	public Object clone() throws CloneNotSupportedException
	{
		MonitorWeiboWithinLimitTime monitorWeiboWithinLimitTime = (MonitorWeiboWithinLimitTime)super.clone();
		monitorWeiboWithinLimitTime.accessToken = new String(accessToken);
		monitorWeiboWithinLimitTime.uid = new String(uid);
		monitorWeiboWithinLimitTime.minutes = minutes;
		monitorWeiboWithinLimitTime.thisType = ThisType.MonitorWeiboWithinLimitTime;
		monitorWeiboWithinLimitTime.timer = null;
		monitorWeiboWithinLimitTime.myTT = null;
		return monitorWeiboWithinLimitTime;
	}
}
