package domain;

import weibo4j.Timeline;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

/* that-发送微博任务 */
public class SendWeibo extends Goal {
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