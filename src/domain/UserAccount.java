package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.sun.corba.se.spi.orbutil.fsm.State;

enum UserState{login,logout};//���롢�ǳ�

/* ���Ѽ�¼�� */
class ExpenceCanlendar {
	private Date date;//ʱ��
	private double amount;//���
	private int thisnum;//this������
	private int thatnum;//that������
	private int num;//���Ѽ�¼���
	
	public ExpenceCanlendar(double cost,int thisn,int thatn,int n)
	{
		date= new Date();
		amount = cost;
		thisnum = thisn;
		thatnum = thatn;
		num = n;
	}
	
	public ExpenceCanlendar getRecord()
	{
		return this;
	}
}

/* �û��˺�ʵ���� */
/* ��δʵ���û���Ϣ�鿴������ */
/* �û����Բ鿴ϵͳ�����Ĺ�����Ϣ */
/* �û����Բ鿴��ɾ��ϵͳ������˽����Ϣ */
/* ��δ�������л� */
public class UserAccount {
	private String username;//�ǳƣ���Ψһ
	private String password;//����
	private String mailAccount;//�����˺ţ�Ψһ
	private double balance;//���
	private int level;
	private Set<ExpenceCanlendar> expenceCanlendar;//���Ѽ�¼
	private int credit;//����
	private double discount;//�ۿ�
	private TaskQueue taskQueue;//���������
	private UserState state;//��¼״̬���������롢�ǳ�
	
	public UserAccount() {
		balance = 1000;
		expenceCanlendar = new HashSet<ExpenceCanlendar>();
		level = 1;
		credit = 0;
		discount = 1;
		taskQueue = null;
		state = UserState.logout;
	}
	
	public UserAccount(String user,String pw,String mail){
		username = user;
		password = pw;
		mailAccount = mail;
		balance = 1000;
		level = 1;
		expenceCanlendar = new HashSet<ExpenceCanlendar>();
		credit = 0;
		discount = 1;
		taskQueue = null;
		state = UserState.logout;
	}
		
	/* Getters��ע���ȡ���Ѽ�¼���ݵĲ���ԭָ�룬�������޸����Ѽ�¼ */
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getMailAccount() {
		return mailAccount;
	}

	public double getBalance() {
		return balance;
	}

	public int getLevel() {
		return level;
	}

	public Set<ExpenceCanlendar> getExpenceCanlendar() {
		Set<ExpenceCanlendar> records = new HashSet<ExpenceCanlendar>();
		records.addAll(expenceCanlendar);
		return records;
	}

	public int getCredit() {
		return credit;
	}

	public double getDiscount() {
		return discount;
	}

	public TaskQueue getTaskQueue() throws Exception{
		TaskQueue temp = new TaskQueue();
		temp = (TaskQueue)taskQueue.clone();
		return temp;
	}
	
	public UserState getState() {
		return state;
	}
	
	/* �޸��ǳ� */
	public void SetUserName(String newName)
	{
		username = newName;
	}
	
	/* �޸����룬����������ԭ���벻��ͬ */
	public boolean setPassword(String pass)
	{
		if(pass.compareTo(password) == 0)
			return false;
		else
			password = pass;
		return true;
	}
	
	/* �޸������˺ţ�����������ԭ���䲻ͬ��һ�㲻�ɵ��� */
	public boolean setMailAccount(String mail)
	{
		if(mail.compareTo(mailAccount) == 0)
			return false;
		else
			mailAccount = mail;
		return true;
	}
	
	/* ��ʾ��Ҫ�����ڹ���Աǿ���޸ĵȼ�������Ӧ�ظı���ֺ��ۿ� */
	public void setLevel(int l)
	{
		level = l;
		if(level == 1)
		{
			discount = 1.0;
			if(credit > 1000)
				credit = 0;
		}
		else if(level == 2)
		{
			discount = 0.95;
			if(credit <= 1000 || credit > 2000)
				credit = 1001;
		}
		else if(level == 3)
		{
			discount = 0.9;
			if(credit <= 2000 || credit > 3000)
				credit = 2001;
		}
	}

	/* �ı��½״̬ */
	public void setState(UserState s)
	{
		state = s;
	}
	
	/* ��ֵ */
	public double recharge(double pay){
		balance += pay;
		return balance;
	}
	
	/* ���ѣ��������Ѽ�¼�ı���֡���Ա�ȼ����ۿ� */
	public boolean Expense(double amount,int thisn,int thatn)
	{
		if(balance < amount * discount)
			return false;
		balance -= amount * discount;
		credit += amount;
		expenceCanlendar.add(new ExpenceCanlendar(amount*discount, thisn, thatn, expenceCanlendar.size()));
		if(credit <= 1000)
		{
			level = 1;
			discount = 1.0;
		}
		else if(credit <= 2000)
		{
			level = 2;
			discount = 0.95;
		}
		else if(credit <= 4000)
		{
			level = 3;
			discount = 0.9;
		}
		return true;
	}
}
