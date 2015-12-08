package domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

enum UserState{login,logout};//登入、登出

/* 消费记录类 */
class ExpenceCalendar {
	private Date date;//时间
	private double amount;//金额
	private int thisnum;//this任务编号
	private int thatnum;//that任务编号
	private int num;//消费记录编号
	
	public ExpenceCalendar(double cost,int thisn,int thatn,int n)
	{
		date= new Date();
		amount = cost;
		thisnum = thisn;
		thatnum = thatn;
		num = n;
	}
	
	public ExpenceCalendar getRecord()
	{
		return this;
	}
}

/* 用户账号实体类 */
/* 尚未实现用户消息查看、管理 */
/* 用户可以查看系统发布的公共消息 */
/* 用户可以查看、删除系统发布的私人消息 */
/* 尚未考虑序列化 */
public class UserAccount {
	private String username;//昵称，不唯一
	private String password;//密码
	private String mailAccount;//邮箱账号，唯一
	private double balance;//余额
	private int level;
	private Set<ExpenceCalendar> expenceCalendar;//消费记录
	private int credit;//积分
	private double discount;//折扣
	private TaskQueue taskQueue;//任务队列类
	private UserState state;//登录状态，包括登入、登出
	
	public UserAccount() {
		balance = 1000;
		level = 1;
		expenceCalendar = new HashSet<ExpenceCalendar>();
		credit = 0;
		discount = 1;
		taskQueue = new TaskQueue(mailAccount);
		state = UserState.logout;
	}
	
	public UserAccount(String user,String pw,String mail){
		username = new String(user);
		password = new String(pw);
		mailAccount = new String(mail);
		balance = 1000;
		level = 1;
		expenceCalendar = new HashSet<ExpenceCalendar>();
		credit = 0;
		discount = 1;
		taskQueue = new TaskQueue(mailAccount);
		state = UserState.logout;
	}
		
	/* Getters，注意获取消费记录传递的不是原指针，即不可修改消费记录 */
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

	public Set<ExpenceCalendar> getExpenceCanlendar() {
		Set<ExpenceCalendar> records = new HashSet<ExpenceCalendar>();
		records.addAll(expenceCalendar);
		return records;
	}

	public int getCredit() {
		return credit;
	}

	public double getDiscount() {
		return discount;
	}

	public TaskQueue getTaskQueue() throws Exception{
		TaskQueue temp = new TaskQueue(mailAccount);
		temp = (TaskQueue)taskQueue.clone();
		return temp;
	}
	
	public UserState getState() {
		return state;
	}
	
	/* 修改昵称 */
	public void SetUserName(String newName)
	{
		username = new String(newName);
	}
	
	/* 修改密码，所改密码与原密码不相同 */
	public boolean setPassword(String pass)
	{
		if(pass.compareTo(password) == 0)
			return false;
		else
			password = new String(pass);
		return true;
	}
	
	/* 修改邮箱账号，所改邮箱与原邮箱不同，一般不可调用 */
	public boolean setMailAccount(String mail)
	{
		if(mail.compareTo(mailAccount) == 0)
			return false;
		else
			mailAccount = new String(mail);
		return true;
	}
	
	/* 演示需要，用于管理员强制修改等级，并相应地改变积分和折扣 */
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

	/* 改变登陆状态 */
	public void setState(UserState s)
	{
		state = s;
	}
	
	/* 充值 */
	public double recharge(double pay){
		balance += pay;
		return balance;
	}
	
	/* 消费，并按消费记录改变积分、会员等级和折扣 */
	public boolean Expense(double amount,int thisn,int thatn)
	{
		if(balance < amount * discount)
			return false;
		balance -= amount * discount;
		credit += amount;
		expenceCalendar.add(new ExpenceCalendar(amount*discount, thisn, thatn, expenceCalendar.size()));
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
