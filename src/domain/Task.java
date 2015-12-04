package domain;

/* ����ʵ���� */
public class Task implements Cloneable{
	private String TaskName;//������������ͬһ�û������ظ�
	private Request request;//that����ָ��
	private Goal goal;//that����ָ��
	
	public Object clone() throws CloneNotSupportedException {
		Task temp = (Task)super.clone();
		temp.TaskName = this.TaskName;
		temp.request = (Request)this.request.clone();
		temp.goal = (Goal)this.goal.clone();
		return temp;
	}
	
	/* �½����� ��ҳ����ʾ��ѡ�� THIS�� THAT���¼�ͼ�꣬ �û�ֻ��ֱ�ӵ��ͼ�꣬
����� THIS�� THAT���¼���ѡ�� �û�������֮�󣬷��ظոմ�����������Ϣ��
�û��������ǲ��ÿ۳���Ա��ѵķ�ʽ�� */
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
	
	public Object clone()throws CloneNotSupportedException {
		TaskQueue temp = (TaskQueue)super.clone();
		temp.UserMailAccount = this.UserMailAccount;
		temp.top = this.top;
		temp.tasks = null;
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
	public abstract Object clone() throws CloneNotSupportedException;
}

/* that��������� */
abstract class Goal implements Cloneable{
	ThatType thatType;
	public abstract boolean thenThat() throws Exception;
	public abstract Object clone() throws CloneNotSupportedException;
}

/* this-��ʱ���� */
class OrderTime extends Request {
	public boolean ifThis() {
		return true;
	}
}

/* this-�յ�QQ�ʼ����� */
class RecvMail extends Request {
	public boolean ifThis() {
		return true;
	}
}

/* this-����΢������ */
class MonitorWeibo extends Request {
	public boolean ifThis() {
		return true;
	}
}

/* that-����΢������ */
class SendWeibo extends Goal {
	public boolean thenThat() {
		return true;
	}
}

/* that-�����ʼ����� */
class SendMail extends Goal {
	public boolean thenThat() {
		return true;
	}
}

