package domain;

import weibo4j.Timeline;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

enum ThatType {SendWeibo,SendMail};

/* that任务抽象类 */
public abstract class Goal implements Cloneable{
	ThatType thatType;
	public abstract boolean thenThat() throws Exception;
	public Object clone() throws CloneNotSupportedException {
		Goal goal = (Goal)super.clone();
		return goal;
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

