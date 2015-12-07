package domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.eclipse.jdt.internal.compiler.ast.ThrowStatement;
import org.eclipse.jdt.internal.compiler.flow.SwitchFlowContext;

/* ����ʵ���� */
public class Task implements Cloneable{
	private String TaskName;//������������ͬһ�û������ظ�
	private Request request;//that����ָ��
	private Goal goal;//that����ָ��
	private double expense;//����
	
	public Task(String name,Request r,Goal g) throws Exception
	{
		TaskName = new String(name);
		request = (Request)r.clone();
		goal = (Goal)g.clone();
		/* �շѷ�ʽ */
		expense = 0;
		switch (request.thisType) {
		case OrderTime:
			expense += 50;
			break;
		case RecvMail:
			expense += 100;
			break;
		case MonitorWeibo:
			expense += 150;
			break;
		default:
			break;
		}
		switch (goal.thatType) {
		case SendMail:
			expense += 50;
			break;
		case SendWeibo:
			expense += 100;
			break;
		default:
			break;
		}
	}
	
	public String getTaskName() {
		return TaskName;
	}

	public double getExpense() {
		return expense;
	}

	public Object clone() throws CloneNotSupportedException {
		Task temp = (Task)super.clone();
		temp.TaskName = new String(this.TaskName);
		temp.request = (Request)this.request.clone();
		temp.goal = (Goal)this.goal.clone();
		return temp;
	}
	
	/* �½�����  �û�������֮�󣬷��ظոմ�����������Ϣ���û���
�����ǲ��ÿ۳���Ա��ѵķ�ʽ�� */
	/* �鿴��ɾ�����޸����� ����鿴���ܿ�����ʾ�û��Ѿ��������������� ÿ����
ʾ���ݱ�����������������Ϣ������ �û�Ҳ����ѡ��ɾ�����е����� ͬʱ����
�������޸����е����� */
	/* ��ʼ��ֹͣ�����û������ֶ���ʼ��ֹͣ���е����� ����������շѲ��ÿ۳�
��Ա��ѵķ�ʽ��ͬʱ���ݻ�Ա�ĵȼ�������һ�����ۿۺͻ��֡� */
}

/* �����û�����������ʵ���� */
class TaskQueue implements Cloneable{
	private String UserMailAccount;//�����û����˺�����
	private Task[] tasks;//��������ָ��
	private int top;
	private int maxLength;//��ǰ�����������
	
	/* ��������ʱ�������������û������� */
	public TaskQueue(String MailAccount) {
		UserMailAccount = new String(MailAccount);
		maxLength = 10;
		tasks = new Task[maxLength];
		top = 0;
	}
	
	/* �������񣬷���ֵ0-��ȷ��1-�������ظ���2-���� */
	public int addTask(Task t) throws Exception
	{
		boolean repetition = false;
		for(int i = 0; i < top; i ++)
		{
			if(tasks[i].getTaskName().equals(t.getTaskName()))
			{
				repetition = true;
				break;
			}
		}
		if(repetition)
			return 1;
		if(top < maxLength && top >= 0)
		{
			tasks[top] = (Task)t.clone();
			top ++;
		}
		else if (top < 0)
			return 2;
		else
		{
			maxLength += 10;
			Task[] temp = new Task[maxLength];
			for(int i = 0; i < top; i ++)
			{
				temp[i] = tasks[i];
			}
			temp[top] = (Task)t.clone();
			top ++;
			tasks = temp;
		}
		return 1;
	}
	
	/* ɾ�����񣬰���������������Ψһ��������ֵ0-�ҵ���1-�Ҳ��� */
	public int deleteTask(String name)
	{
		boolean find = false;
		for(int i = 0; i < top; i ++)
		{
			if(tasks[i].getTaskName().equals(name))
			{
				find = true;
				for(int j = i; j < top - 1; j ++)
				{
					tasks[j] = tasks[j+1];
				}
				top --;
				tasks[top] = null;
				break;
			}
		}
		if(find)
			return 0;
		else
			return 1;
	}
	
	/* �޸����񣬰�������������ֵ0-�ɹ���1-�����ظ���2-δ�ҵ� */
	public int setTask(String name,Task t) throws Exception
	{
		boolean find = false;
		for(int i = 0; i < top; i ++)
		{
			if(tasks[i].getTaskName().equals(name))
			{
				find = true;
				boolean repetition = false;
				for(int j = 0; j < top; j ++)
				{
					if(tasks[j].getTaskName().equals(t.getTaskName()))
					{
						repetition = true;
						break;
					}
				}
				if(repetition)
					return 1;
				else
					tasks[i] = (Task)t.clone();
				break;
			}
		}
		if(find)
			return 0;
		else 
			return 2;
	}
	
	/* ��� */
	public Object clone()throws CloneNotSupportedException {
		TaskQueue temp = (TaskQueue)super.clone();
		temp.UserMailAccount = new String(this.UserMailAccount);
		temp.top = this.top;
		temp.maxLength = this.maxLength;
		temp.tasks = new Task[maxLength];
		for(int i = 0; i < this.top; i ++)
		{
			temp.tasks[i] = (Task)this.tasks[i].clone();
		}
		return temp;
	}
}

enum ThisType {OrderTime,RecvMail,MonitorWeibo};
enum ThatType {SendWeibo,SendMail};

/* this��������� */
abstract class Request implements Cloneable{
	ThisType thisType;
	public abstract boolean ifThis() throws Exception;
	public Object clone() throws CloneNotSupportedException {
		Request request = (Request)super.clone();
		return request;
	}
}

/* that��������� */
abstract class Goal implements Cloneable{
	ThatType thatType;
	public abstract boolean thenThat() throws Exception;
	public Object clone() throws CloneNotSupportedException {
		Goal goal = (Goal)super.clone();
		return goal;
	}
}

/* this-��ʱ���� */
class OrderTime extends Request {
	private MyDate date;
	private MyTime time;
	
	public OrderTime() {
		date = new MyDate();
		time = new MyTime();
		super.thisType = ThisType.OrderTime;
	}
	
	public OrderTime(MyDate d,MyTime t) {
		date = new MyDate(d);
		time = new MyTime(t);
		super.thisType = ThisType.OrderTime;
	}

	public void SetDateAndTime(MyDate d,MyTime t)throws Exception
	{
		d = (MyDate)this.date.clone();
		t = (MyTime)this.time.clone();
	}
	
	public boolean ifThis() {
		Timer timer = new Timer();
		MyTimerTask myTT = new MyTimerTask(); //���ö�ʱ�����ж�ʱ
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date tmp = null;
		try{
			tmp = df.parse(date.year+"-"+date.month+"-"+date.day+" "+time.hour+":"+time.minute+":"+time.second);
			timer.schedule(myTT, tmp);
		}
		catch(ParseException e){}
		try{
			while(true){
				Thread.sleep(1000);
				if(myTT.getReady() == true)
					break;
			}
		}
		catch(Exception e){return false;}
		return true;
	}
	
	public Object clone() throws CloneNotSupportedException
	{
		OrderTime orderTime = (OrderTime)super.clone();
		orderTime.date = (MyDate)this.date.clone();
		orderTime.time = (MyTime)this.time.clone();
		orderTime.thisType = ThisType.OrderTime;
		return orderTime;
	}
}

/* this-�յ�QQ�ʼ����� */
class RecvMail extends Request {
	private String username;
	private String password;
	
	public RecvMail() {
		username = "";
		password = "";
		super.thisType = ThisType.RecvMail;
	}
	
	public RecvMail(String name, String pw) {
		username = new String(name);
		password = new String(pw);
		super.thisType = ThisType.RecvMail;
	}
	
	public void SetUserAndPw(String name,String pw) {
		username = new String(name);
		password = new String(pw);
	}

	/* �鿴qq������ռ����ʼ���Ŀ */
    public int recvMail() {
		String protocol = "pop3";
		boolean isSSL = true;
		String host = "pop.qq.com";
		int port = 995;
		
		Properties props = new Properties();
		props.put("mail.pop3.ssl.enable", isSSL);
		props.put("mail.pop3.host", host);
		props.put("mail.pop3.port", port);
		
		Session session = Session.getDefaultInstance(props);
		
		Store store = null;
		Folder folder = null;
		int size = 0;
		try {
			store = session.getStore(protocol);
			store.connect(username,password);
			
			folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			
			size = folder.getMessageCount();
			Message message = folder.getMessage(size);
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
		try{
			printRes("���ʼ�...");
			
			int size = recvMail();
			if(size == -1) {
				printRes("���ʼ�ʧ�ܣ�");
				return false;
			}
			
			printRes("�ȴ�...");
			
			class myTimerTask extends TimerTask{
				boolean ready = false;
				public void run() {
					ready = true;
				}
				public boolean getReady(){
					return ready;
				}
			}
			
			Timer timer = new Timer();
			myTimerTask myTT = new myTimerTask();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date tmp = new Date();
			boolean sig = true;
			while(sig) {
				timer.schedule(myTT, tmp);
				
				try{
					while(true){
						Thread.sleep(1000);
						if(myTT.getReady() == true){
							int newsize = recvMail();
							if(newsize < 0) {
								printRes("���ʼ�ʧ�ܣ�");
								return false;
							}
							else if(newsize > size){
								printRes("�������ʼ�...");
								sig = false;
								break;
							}
							else {
								tmp = new Date();
							}
						}
					}
				}
				catch(Exception e){return false;}
			}
		}
		catch(Exception e){}		
		return true;	
	}
	
	public Object clone() throws CloneNotSupportedException {
		return;
	}
}

/* this-����΢������ */
class MonitorWeibo extends Request {
	public boolean ifThis() {
		return true;
	}
	
	public Object clone() throws CloneNotSupportedException
	{
		return;
	}
}

/* that-����΢������ */
class SendWeibo extends Goal {
	public boolean thenThat() {
		return true;
	}
	
	public Object clone() throws CloneNotSupportedException
	{
		return;
	}
}

/* that-�����ʼ����� */
class SendMail extends Goal {
	public boolean thenThat() {
		return true;
	}
	
	public Object clone() throws CloneNotSupportedException
	{
		return;
	}
}

