package domain;

import java.util.Date;
import java.util.Timer;

import weibo4j.Users;
import weibo4j.model.Status;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

public class MonitorWeiboWithinLimitTime extends Request {
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
