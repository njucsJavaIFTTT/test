package domain;

/* that-发送邮件任务 */
public class SendMail extends Goal {
	private String username;//收信人id
	private String content;

	public SendMail() {
		username = null;
		content = null;
		thatType = ThatType.SendMail;
	}
	
	public SendMail(String user,String con) {
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
