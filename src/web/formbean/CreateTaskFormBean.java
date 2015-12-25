package web.formbean;

/**
 * 封装的创建任务表单，用来接收task.jsp中的表单输入项的值
 * Bean中的属性与jsp中的表单输入项的名字一一对应
 * @author YJJ
 *
 */

public class CreateTaskFormBean {

	private String taskName;//任务名
	private int taskID;//任务ID唯一标识
	private String ownerMail;//任务所属用户的账号邮箱
	private int thisType;
	private int thatType;
	private String orderedTime;//定时
	private String MonitorMailAccount;//this任务-收件QQ邮箱账号
	private String MonitorMailpassword;//this任务-收件QQ邮箱密码
	private String MonitorWeiboAccount;//this任务-监听微博账号
	private String MonitorWeiboAccessToken;//this任务-监听微博授权码
	private String MonitorContain;//this任务-监听微博内容
	private int listenMinute;//this任务-监听微博时长
	
	private String weiboContent;//that任务-发送微博内容
	private String sendWeiboAccount;//that任务-发送微博账号
	private String sendWeiboAccessToken;//that任务-发送微博授权码
	private String mailContent;//that任务-发送邮件内容
	private String receiverMailAccount;//that任务-收件邮箱账号
	
	public String getOwner() {
		return ownerMail;
	}

	public void setOwner(String owner) {
		this.ownerMail = owner;
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
		return listenMinute;
	}

	public void setMinute(int minute) {
		this.listenMinute = minute;
	}

	public String getWeiboContent() {
		return weiboContent;
	}

	public void setWeiboContent(String weiboContent) {
		weiboContent = weiboContent;
	}

	public String getSendWeiboAccount() {
		return sendWeiboAccount;
	}

	public void setSendWeiboAccount(String sendWeiboAccount) {
		sendWeiboAccount = sendWeiboAccount;
	}

	public String getSendWeiboAccessToken() {
		return sendWeiboAccessToken;
	}

	public void setSendWeiboAccessToken(String sendWeiboAccessToken) {
		this.sendWeiboAccessToken = sendWeiboAccessToken;
	}
	
	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		mailContent = mailContent;
	}

	public String getReceiverMailAccount() {
		return receiverMailAccount;
	}

	public void setReceiverMailAccount(String receiverMailAccount) {
		receiverMailAccount = receiverMailAccount;
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
		return orderedTime;
	}

	public void setTime(String time) {
		this.orderedTime = time;
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
