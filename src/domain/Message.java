package domain;

import java.util.Date;

/* 站内消息类 */
public class Message{
	private String content;
	private Date time;
	
	public Message(String c){
		content=new String(c);
		time=new Date();
	}
}

/* 公共消息类（公告） */
class PublicMessage extends Message {

	public PublicMessage(String c) {
		super(c);
		// TODO Auto-generated constructor stub
	}
	
}

/* ˽�ţ���˽����Ϣʵ���� */
class PrivateMessage extends Message {
	public PrivateMessage(String c) {
		super(c);
		// TODO Auto-generated constructor stub
	}
	
}
