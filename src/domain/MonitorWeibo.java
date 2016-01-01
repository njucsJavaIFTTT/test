package domain;

import weibo4j.Users;
import weibo4j.model.Status;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

/* this-监听微博任务 */
public class MonitorWeibo extends Request {
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
					Thread.sleep(60000);
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
