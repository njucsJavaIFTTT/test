package test;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

class ExpenceCanlendar {//һ�����Ѽ�¼
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

class Massage{//һ����Ϣ
	private String Content;
	private Date time;
	public Massage(String c){
		Content=new String(c);
		time=new Date();
	}
}

public class Account {
	private String Username;//�ǳƣ���Ψһ
	private String Password;//����
	private String MailAccount;//�����˺ţ�Ψһ
	private double Balance;//���
	private int Level;
	private Set<ExpenceCanlendar> ExpCanlendar;//���Ѽ�¼
	private int Credit;//����
	private double Discount;//�ۿ�
	
	public Account() {
		Balance = 1000;
		ExpCanlendar = new HashSet<ExpenceCanlendar>();
		Level = 1;
		Credit = 0;//����
		Discount = 1;//�ۿ�
	}
	
	public Account(String user,String pw,String mail){
		Username = user;
		Password = pw;
		MailAccount = mail;
		Balance = 1000;
		Level = 1;
		ExpCanlendar = new HashSet<ExpenceCanlendar>();
		Credit = 0;
		Discount = 1;
	}
		
	/* �޸��ǳ� */
	public void setUsername(String newName)
	{
		Username = newName;
	}
	
	/* �޸����룬����������ԭ���벻��ͬ */
	public boolean setPassword(String pass)
	{
		if(pass.compareTo(Password) == 0)
			return false;
		else
			Password = pass;
		return true;
	}
	
	/* �޸������˺ţ�����������ԭ���䲻ͬ��һ�㲻�ɵ��� */
	public boolean setMailAccount(String mail)
	{
		if(mail.compareTo(MailAccount) == 0)
			return false;
		else
			MailAccount = mail;
		return true;
	}
	
	/* ��ֵ */
	public double recharge(double pay){
		Balance += pay;
		return Balance;
	}
	
	/* ��ʾ��Ҫ�����ڹ���Աǿ���޸ĵȼ�������Ӧ�ظı���ֺ��ۿ� */
	public void setLevel(int l)
	{
		Level = l;
		if(Level == 1)
		{
			Discount = 1.0;
			if(Credit > 1000)
				Credit = 0;
		}
		else if(Level == 2)
		{
			Discount = 0.95;
			if(Credit <= 1000 || Credit > 2000)
				Credit = 1001;
		}
		else if(Level == 3)
		{
			Discount = 0.9;
			if(Credit <= 2000 || Credit > 3000)
				Credit = 2001;
		}
	}
	
	/* ���ѣ��������Ѽ�¼�ı���֡���Ա�ȼ����ۿ� */
	public boolean Expense(double amount,int thisn,int thatn)
	{
		if(Balance < amount * Discount)//���˻������������������
			return false;
		Balance -= amount * Discount;
		Credit += amount;
		ExpCanlendar.add(new ExpenceCanlendar(amount*Discount, thisn, thatn, ExpCanlendar.size()));
		if(Credit <= 1000)
		{
			Level = 1;
			Discount = 1.0;
		}
		else if(Credit <= 2000)
		{
			Level = 2;
			Discount = 0.95;
		}
		else if(Credit <= 4000)
		{
			Level = 3;
			Discount = 0.9;
		}
		return true;
	}
	
	/* ��ȡ�˺���Ϣ */
	public String getUserName(){ return Username;}
	public String getPassword(){ return Password;}
	public String getMailAccount(){ return MailAccount;}
	public double getBalance(){ return Balance;}
	public int setLevel(){ return Level;}
	public Set<ExpenceCanlendar> getRecords(){
		Set<ExpenceCanlendar> records = new HashSet<ExpenceCanlendar>();
		records.addAll(ExpCanlendar);
		return records;
	}
}