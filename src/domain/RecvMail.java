package domain;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

/* this-收到QQ邮件任务 */
public class RecvMail extends Request {
	private String username;
	private String password;
	//private Timer timer;
	//private MyTimerTask myTT;
	
	public RecvMail() {
		username = "";
		password = "";
		//timer = null;
		//myTT = null;
		super.thisType = ThisType.RecvMail;
	}
	
	public RecvMail(String name, String pw) {
		username = new String(name);
		password = new String(pw);
		//timer = null;
		//myTT = null;
		super.thisType = ThisType.RecvMail;
	}
	
	public void SetUserAndPw(String name,String pw) {
		username = new String(name);
		password = new String(pw);
	}

	/* 查看qq邮箱的收件箱邮件数目 */
    public int recvMail() {
		String protocol = "pop3";
		boolean isSSL = true;
		String host = "pop.qq.com";
		int port = 995;
		
		Properties props = new Properties();
		props.put("mail.pop3.ssl.enable", isSSL);
		props.put("mail.pop3.host", host);
		props.put("mail.pop3.port", port);
		props.put("mail.smtp.auth", "true"); 
		
		Session session = Session.getDefaultInstance(props);
		
		Store store = null;
		Folder folder = null;
		int size = 0;
		try {
			store = session.getStore(protocol);
			store.connect(host,username,password);
			
			folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			
			size = folder.getMessageCount();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException  e) {
			e.printStackTrace();
		} finally {
			try {
				if(folder != null) {
					folder.close(false);
				}
				if(store != null) {
					store.close();
				}
			} catch(MessagingException e) {
				e.printStackTrace();
				return -1;
			}
		}
		return size;
	}
    
	public boolean ifThis() {
		try
		{
			System.out.println("收邮件...");
			
			int size = recvMail();
			if(size == -1) {
				System.out.println("收邮件失败！");
				return false;
			}
			
			System.out.println("等待...");
			
			try{
				while(true){
					Thread.sleep(1000);
					int newsize = recvMail();
					if(newsize < 0) {
						System.out.println("收邮件失败！");
						return false;
					}
					else if(newsize > size){
						System.out.println("发现新邮件...");
						break;
					}
				}
			}
			catch(Exception e){return false;}
		}
		catch(Exception e){}		
		return true;
	}
	
	public Object clone() throws CloneNotSupportedException {
		RecvMail recvMail = (RecvMail)super.clone();
		recvMail.username = new String(this.username);
		recvMail.password = new String(this.password);
		recvMail.thisType = ThisType.RecvMail;
		//recvMail.timer = null;
		//recvMail.myTT = null;
		return recvMail;
	}
}
