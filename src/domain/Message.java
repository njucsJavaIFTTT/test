package domain;

public class Message{//私人消息
	String content;
	String targetMail;
	public Message(String content, String targetMail) {
		super();
		this.content = content;
		this.targetMail = targetMail;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTargetMail() {
		return targetMail;
	}

	public void setTargetMail(String targetMail) {
		this.targetMail = targetMail;
	}

	@Override
	public String toString() {
		return "Massage [content=" + content + ", targetMail=" + targetMail + "]";
	}
	
}