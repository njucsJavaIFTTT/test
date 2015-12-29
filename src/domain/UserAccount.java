package domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Vector;
import java.util.Vector;


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
	private int credit;//积分
	private double discount;//折扣
	private Vector<ExpenseCalendar> expenseCalendar;//消费记录
	//private TaskQueue taskQueue;//任务队列类
	private Vector<Task> taskQueue;//任务
	private Vector<Message> msg;//私人消息
	public UserAccount(){
		balance = 1000;
		level = 1;
		expenseCalendar = new Vector<ExpenseCalendar>();
		credit = 0;
		discount = 1;
		//taskQueue = new TaskQueue(mailAccount);
	}
	
	public UserAccount(String user,String pw,String mail){
		if(user != null)
			username = new String(user);
		password = new String(pw);
		mailAccount = new String(mail);
		balance = 1000;
		level = 1;
		expenseCalendar = new Vector<ExpenseCalendar>();
		credit = 0;
		discount = 1;
		taskQueue = new Vector<Task>();
	}
		


	public UserAccount(String username, String password, String mailAccount, double balance, int level, int credit,
			double discount) {
		super();
		this.username = username;
		this.password = password;
		this.mailAccount = mailAccount;
		this.balance = balance;
		this.level = level;
		this.credit = credit;
		this.discount = discount;
	}

	public UserAccount(String username, String password, String mailAccount, double balance, int level, int credit,
			double discount, Vector<ExpenseCalendar> expenseCalendar, Vector<Task> taskQueue, Vector<Message> msg) {
		super();
		this.username = username;
		this.password = password;
		this.mailAccount = mailAccount;
		this.balance = balance;
		this.level = level;
		this.credit = credit;
		this.discount = discount;
		this.expenseCalendar = expenseCalendar;
		this.taskQueue = taskQueue;
		this.msg = msg;
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

	public Vector<ExpenseCalendar> getExpenceCanlendar() {
		Vector<ExpenseCalendar> records = new Vector<ExpenseCalendar>();
		records.addAll(expenseCalendar);
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
	
	public Vector<Message> getMsg() {
		return msg;
	}

	public void setMsg(Vector<Message> msg) {
		this.msg = msg;//需要深复制吗？
	}
	public void addMas(Message m){//增加msg
		msg.add(m);
	}
	/* 修改昵称 */
	public void SetUserName(String newName)
	{
		username = new String(newName);
	}
	
	/* 修改密码，所改密码与原密码不相同 */
	public boolean setPassword(String pass)
	{
		if(password == null)
		{
			password = new String(pass);
			return true;
		}
		if(pass.compareTo(password) == 0)
			return false;
		else
			password = new String(pass);
		return true;
	}
	
	/* 修改邮箱账号，所改邮箱与原邮箱不同，一般不可调用 */
	public boolean setMailAccount(String mail)
	{
		if(mailAccount == null)
		{
			mailAccount = new String(mail);
			return true;
		}
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
		//expenseCalendar.add(new ExpenseCalendar(, thisn, thatn));
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

	@Override
	public String toString() {
		return "UserAccount [username=" + username + ", password=" + password + ", mailAccount=" + mailAccount
				+ ", balance=" + balance + ", level=" + level + ", credit=" + credit + ", discount=" + discount + "]";
	}
	
}
