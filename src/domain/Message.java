package domain;

import java.util.Date;

/* ��Ϣ�� */
public class Message{//һ����Ϣ
	private String content;
	private Date time;
	
	public Message(String c){
		content=new String(c);
		time=new Date();
	}
}

/* ���棬��������Ϣʵ���� */
class PublicMessage extends Message {
	
}

/* ˽�ţ���˽����Ϣʵ���� */
class PrivateMessage extends Message {
	
}
