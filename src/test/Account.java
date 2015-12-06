package test;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

class ExpenceCanlendar {//一条消费记录
	private Date date;//时间
	private double amount;//金额
	private int thisnum;//this任务编号
	private int thatnum;//that任务编号
	private int num;//消费记录编号
	
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

class Massage{//一条消息
	private String Content;
	private Date time;
	public Massage(String c){
		Content=new String(c);
		time=new Date();
	}
}

public class Account {
	private String Username;//昵称，不唯一
	private String Password;//密码
	private String MailAccount;//邮箱账号，唯一
	private double Balance;//余额
	private int Level;
	private Set<ExpenceCanlendar> ExpCanlendar;//消费记录
	private int Credit;//积分
	private double Discount;//折扣
	
	public Account() {
		Balance = 1000;
		ExpCanlendar = new HashSet<ExpenceCanlendar>();
		Level = 1;
		Credit = 0;//积分
		Discount = 1;//折扣
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
		
	/* 修改昵称 */
	public void setUsername(String newName)
	{
		Username = newName;
	}
	
	/* 修改密码，所改密码与原密码不相同 */
	public boolean setPassword(String pass)
	{
		if(pass.compareTo(Password) == 0)
			return false;
		else
			Password = pass;
		return true;
	}
	
	/* 修改邮箱账号，所改邮箱与原邮箱不同，一般不可调用 */
	public boolean setMailAccount(String mail)
	{
		if(mail.compareTo(MailAccount) == 0)
			return false;
		else
			MailAccount = mail;
		return true;
	}
	
	/* 充值 */
	public double recharge(double pay){
		Balance += pay;
		return Balance;
	}
	
	/* 演示需要，用于管理员强制修改等级，并相应地改变积分和折扣 */
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
	
	/* 消费，并按消费记录改变积分、会员等级和折扣 */
	public boolean Expense(double amount,int thisn,int thatn)
	{
		if(Balance < amount * Discount)//若账户金额不够付任务所需费用
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
	
	/* 获取账号信息 */
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