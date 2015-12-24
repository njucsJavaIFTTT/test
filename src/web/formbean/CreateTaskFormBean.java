package web.formbean;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装的创建任务表单，用来接收task.jsp中的表单输入项的值
 * Bean中的属性与jsp中的表单输入项的名字一一对应
 * @author YJJ
 *
 */

public class CreateTaskFormBean {

	private String taskName;//任务名
	private String owner;//任务所属用户的账号邮箱
	private int thisType;
	private int thatType;
	private String time;//定时
	private String MonitorMailAccount;//this任务-收件QQ邮箱账号
	private String MonitorMailpassword;//this任务-收件QQ邮箱密码
	private String MonitorWeiboAccount;//this任务-监听微博账号
	private String MonitorWeiboAccessToken;//this任务-监听微博授权码
	private String MonitorContain;//this任务-监听微博内容
	private int minute;//this任务-监听微博时长
	
	private String WeiboContent;//that任务-发送微博内容
	private String SendWeiboAccount;//that任务-发送微博账号
	private String MailContent;//that任务-发送邮件内容
	private String ReceiverMailAccount;//that任务-收件邮箱账号
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getThatType() {
		return thatType;
	}

	public void setThatType(int thatType) {
		this.thatType = thatType;
	}

	public String getMonitorMailAccount() {
		return MonitorMailAccount;
	}

	public void setMonitorMailAccount(String monitorMailAccount) {
		MonitorMailAccount = monitorMailAccount;
	}

	public String getMonitorMailpassword() {
		return MonitorMailpassword;
	}

	public void setMonitorMailpassword(String monitorMailpassword) {
		MonitorMailpassword = monitorMailpassword;
	}

	public String getMonitorWeiboAccount() {
		return MonitorWeiboAccount;
	}

	public void setMonitorWeiboAccount(String monitorWeiboAccount) {
		MonitorWeiboAccount = monitorWeiboAccount;
	}

	public String getMonitorWeiboAccessToken() {
		return MonitorWeiboAccessToken;
	}

	public void setMonitorWeiboAccessToken(String monitorWeiboAccessToken) {
		MonitorWeiboAccessToken = monitorWeiboAccessToken;
	}

	public String getMonitorContain() {
		return MonitorContain;
	}

	public void setMonitorContain(String monitorContain) {
		MonitorContain = monitorContain;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public String getWeiboContent() {
		return WeiboContent;
	}

	public void setWeiboContent(String weiboContent) {
		WeiboContent = weiboContent;
	}

	public String getSendWeiboAccount() {
		return SendWeiboAccount;
	}

	public void setSendWeiboAccount(String sendWeiboAccount) {
		SendWeiboAccount = sendWeiboAccount;
	}

	public String getMailContent() {
		return MailContent;
	}

	public void setMailContent(String mailContent) {
		MailContent = mailContent;
	}

	public String getReceiverMailAccount() {
		return ReceiverMailAccount;
	}

	public void setReceiverMailAccount(String receiverMailAccount) {
		ReceiverMailAccount = receiverMailAccount;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = new String(taskName);
	}

	public int getThisType() {
		return thisType;
	}

	public void setThisType(int thisType) {
		this.thisType = thisType;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	/*
	private Map<String, String> errors = new HashMap<String, String>();

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
	*/
}
