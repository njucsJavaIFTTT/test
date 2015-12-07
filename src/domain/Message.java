package domain;

import java.util.Date;

/* 消息类 */
public class Message{//一条消息
	private String content;
	private Date time;
	
	public Message(String c){
		content=new String(c);
		time=new Date();
	}
}

/* 公告，即公共消息实体类 */
class PublicMessage extends Message {
	
}

/* 私信，即私人消息实体类 */
class PrivateMessage extends Message {
	
}
