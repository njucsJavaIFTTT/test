package DB;

import java.sql.*;
import domain.Task;
import domain.UserAccount;
import domain.ExpenseCalendar;
import domain.Message;
import web.formbean.CreateTaskFormBean;
import java.util.Vector;
import java.util.Date;

public class DBHelperImpl implements DBHelper{// 用于打开或关闭数据库

	private Connection connect = null;
	//private String sql = null;// sql是要执行的语句
	//private PreparedStatement pst_base = null;
	//private ResultSet ret = null;// 返回结果集
	public void createALL(){//只要第一次建立网站的时候调用即可
		//已经create DB Users好了-手动在Mysql workbench弄。
		//create table users
		String sql = "create table UserAccount("
				+ "	username char(50),"
				+ "pwd char(50),"
				+"mail char(100) not null primary key,"
				+ "balance double,"
				+ "lv int,"
				+"credit int,"
				+ "discount double,"
				+ "UserState int)";
		try {
			PreparedStatement pst = connect.prepareStatement(sql);
			pst.executeQuery();// 执行语句，得到结果集
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//create table taskFormbean
		String sql2 ="create table taskFormbean("
				+ "taskID int not null primary key,"
				+ "taskName char(50),"
				+ "ownerMail char(50),"
				+ "thisType int,"
				+ "thatType int,"
				+ "time char(50),"
				+ "MonitorMailAccount char(50),"
				+ "MonitorMailpassword char(50),"
				+ "MonitorWeiboAccount char(50),"
				+ "MonitorWeiboAccessToken char(50),"
				+ "MonitorContain char(50),"
				+ "ListenMinute int,"
				+ "WeiboContent char(500),"
				+ "sendWeiboAccount char(50),"
				+ "sendWeiboAccessToken char(150),"
				+ "mailContent char(500),"
				+ "receiverMailAccount char(50) )";
		try {
			PreparedStatement pst = connect.prepareStatement(sql);
			pst.executeQuery();// 执行语句，得到结果集
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//create table expenseCalendar
		String sql3 ="create table ExpenseCalendar("
				+ "startDate timestamp,"
				+ "taskID int not null primary key,"
				+ "num int";
		try {
			PreparedStatement pst = connect.prepareStatement(sql);
			pst.executeQuery();// 执行语句，得到结果集
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//create table message（待定）
	}
	
	public DBHelperImpl() {
		// 加载MYSQL JDBC驱动程序
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Success loading Mysql Driver!");
		} catch (Exception e) {
			System.out.print("Error loading Mysql Driver!");
			e.printStackTrace();
		}
		// 连接具体的数据库
		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Users", "root", "root");
			// 连接URL为 jdbc:mysql//服务器地址/数据库名 ，后面的2个参数分别是登陆用户名和密码

			System.out.println("Success connect Mysql server!(db-Users)");
		} catch (Exception e) {
			System.out.print("connect Mysql server error!(db-Users)");
			e.printStackTrace();
		}
		
	}

	public void addUser(UserAccount user) {
		String sql = "INSERT INTO UserAccount VALUES(?,?,?,?,?,?,?)";
		try {
			PreparedStatement pst = connect.prepareStatement(sql);// 准备执行语句
			pst.setString(1, user.getUsername());
			pst.setString(2, user.getPassword());
			pst.setString(3, user.getMailAccount());
			pst.setDouble(4, user.getBalance());
			pst.setInt(5, user.getLevel());
			pst.setInt(6, user.getCredit());
			pst.setDouble(7, user.getDiscount());
			pst.executeUpdate();
			System.out.println("Success do '" + sql + "'!(db-addUser)");
		} catch (Exception e) {
			System.out.print("Fail do '" + sql + "'!(db-addUser)");
			e.printStackTrace();
		}
	}

	public String findUser(String uMailAccount) {
		// 执行sql语句
		String uPwd = null;
		String sql = "select * from UserAccount where mail=\'" + uMailAccount + "\';";
		try {
			// pst.setBoolean(2,user.getState());不好弄不是bool也不是int是enum,暂时不加进去
			PreparedStatement pst = connect.prepareStatement(sql);
			ResultSet ret = pst.executeQuery();// 执行语句，得到结果集
			if(!ret.next())
				return null;//"CF"cant find 关键词,会在调用此方法的上一级方法中检验
			else
			{
				uPwd = ret.getString("pwd");
				String uName = ret.getString("username");
				System.out.println("username:" + uName +"get pwd:" + uPwd+" mailAccount:"+uMailAccount);
				System.out.println("Success do \'" + sql + "\'!(db-findUser)");
				return uPwd;
			}
			//return new UserAccount(uName,uPwd,uMailAccount);//这边以后要用完全的复制
		} catch (Exception e) {
			System.out.print("Fail do \'" + sql + "\'!(db-findUser)");
			e.printStackTrace();
		}
		return null;//cant find 关键词,会在调用此方法的上一级方法中检验,这里也写是因为trycatch里的东西不被认可存在
	}

	public UserAccount findUser(String uMailAccount,String uPwd) {
		// 执行sql语句
		String sql = "select * from UserAccount where mail=\'" + uMailAccount + "\' and pwd=\'"+uPwd+"\';";
		try {
			// pst.setBoolean(2,user.getState());不好弄不是bool也不是int是enum,暂时不加进去
			PreparedStatement pst = connect.prepareStatement(sql);
			ResultSet ret = pst.executeQuery();// 执行语句，得到结果集
			if(ret.next()){// 结果集非空
				//String uName = ret.getString("username");
				//user=new UserAccount(uName,uPwd,uMailAccount);
				UserAccount user=new UserAccount(
				ret.getString("username"),
				ret.getString("pwd"),
				ret.getString("mail"),
				ret.getDouble("balance"),
				ret.getInt("lv"),
				ret.getInt("credit"),
				ret.getDouble("discount"));

				System.out.println(user.toString());
				System.out.println("Success do '" + sql + "'!(db-findUser(u,pwd))");
				return user;
			}
			else
				return null;//找不到对应user，需要在调用此方法的上一级方法中检验
			//return new UserAccount(uName,uPwd,uMailAccount);//这边以后要用完全的复制
		} catch (Exception e) {
			System.out.print("Fail do '" + sql + "'!(db-Users)");
			e.printStackTrace();
		}
		return null;//cant find 关键词,会在调用此方法的上一级方法中检验,这里也写是因为trycatch里的东西不被认可存在
	}
	
	public void close() {//关闭对数据库的连接
		try {
			this.connect.close();
			//this.pst_base.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean charge(String mailAccount,Task t){
		//根据任务对用户进行收费并增加消费记录
		String sql = "INSERT INTO ExpenseCalendar VALUES(?,?,?,?)";
		Date date=new Date();
		Timestamp tStamp = new Timestamp(date.getTime());
		System.out.println(tStamp.toString());
		try {
			PreparedStatement pst = connect.prepareStatement(sql);// 准备执行语句
			pst.setString(1, tStamp.toString());
			pst.setInt(2, t.getTaskID());
			pst.setString(3, mailAccount);
			pst.setDouble(4, t.getExpense());
			pst.executeUpdate();
			System.out.println("Success do '" + sql + "'!(db-charge)");
		} catch (Exception e) {
			System.out.print("Fail do '" + sql + "'!(db-charge)");
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean modifyTask(CreateTaskFormBean tf){//修改已有的taskFormbean
		//先delete再add
		if(deleteTask(tf))
			if(storeTask(tf))
				return true;
		return false;
	}
	
	public boolean deleteTask(CreateTaskFormBean tf){//删除taskFormbean
		String sql = "delete from taskFormbean where taskID=" + tf.getTaskID() +";";
		try {
			PreparedStatement pst = connect.prepareStatement(sql);
			pst.executeUpdate();// 执行语句，得到结果集
			System.out.println("Success do '" + sql + "'!(db-deleteTask)");
			return true;
		} catch (Exception e) {
			System.out.print("Fail do '" + sql + "'!(db-deleteTask)");
			e.printStackTrace();
			return false;
		}
	}
	public CreateTaskFormBean findTask(int taskID) throws SQLException{//通过taskID返回相应formbean
		String sql = "select * from taskFormbean where taskID=" + taskID +";";
		PreparedStatement pst = connect.prepareStatement(sql);
		ResultSet ret = pst.executeQuery();// 执行语句，得到结果集
		if(ret.next()){// 结果集非空
			//System.out.println("hhh");
			CreateTaskFormBean tf = new CreateTaskFormBean(ret.getString("taskName"),
			ret.getInt("taskID"),//任务ID唯一标识
			ret.getString("ownerMail"),//任务所属用户的账号邮箱
			ret.getInt("thisType"),
			ret.getInt("thatType"),
			ret.getString("orderedTime"),//定时
			ret.getString("MonitorMailAccount"),//this任务-收件QQ邮箱账号
			ret.getString("MonitorMailpassword"),//this任务-收件QQ邮箱密码
			ret.getString("MonitorWeiboAccount"),//this任务-监听微博账号
			ret.getString("MonitorWeiboAccessToken"),//this任务-监听微博授权码
			ret.getString("MonitorContain"),//this任务-监听微博内容
			ret.getInt("listenMinute"),//this任务-监听微博时长
			
			ret.getString("weiboContent"),//that任务-发送微博内容
			ret.getString("sendWeiboAccount"),//that任务-发送微博账号
			ret.getString("sendWeiboAccessToken"),//that任务-发送微博授权码
			ret.getString("mailContent"),//that任务-发送邮件内容
			ret.getString("receiverMailAccount") );//that任务-收件邮箱账号
			System.out.println(tf.toString());
			System.out.println("find-Success do '" + sql + "'!(db-tIDgetTFB)");
			return tf;
		}
		else{
			System.out.println("can't find-Success do '" + sql + "'!(db-tIDgetTFB)");
			return null;
		}
	}
	
	public Vector<ExpenseCalendar> findExpCal(String uMail){//user获取个人消费记录
		Vector<ExpenseCalendar> expC=new Vector<ExpenseCalendar>();
		String sql = "select * from ExpenseCalendar where ownerMail=\'" + uMail +"\';";
		try{
			PreparedStatement pst = connect.prepareStatement(sql);
			ResultSet ret = pst.executeQuery();// 执行语句，得到结果集
			while(ret.next()){// 结果集非空
				//System.out.println("hhh");
				ExpenseCalendar ec= new ExpenseCalendar(
				ret.getString("startDate"),
				ret.getInt("taskID"),
				ret.getString("ownerMail"),
				ret.getDouble("expense"));
				expC.add(ec);
				//System.out.println(m.toString());
			}
			System.out.println("Success do '" + sql + "'!(db-findExpCal)");
			return expC;
		} catch (Exception e) {
			System.out.print("Fail do '" + sql + "'!(db-findExpCal)");
			e.printStackTrace();
			return null;
		}
		
	
	}
	
	public Vector<Message> findMsg(String uMail){//user获取个人私人消息
		Vector<Message> msg=new Vector<Message>();
		String sql = "select * from Message where targetMail=\'" + uMail +"\';";
		try{
			PreparedStatement pst = connect.prepareStatement(sql);
			ResultSet ret = pst.executeQuery();// 执行语句，得到结果集
			while(ret.next()){// 结果集非空
				//System.out.println("hhh");
				Message m=new Message(
				ret.getString("content"),//this任务-收件QQ邮箱账号
				ret.getString("targetMail") );//that任务-收件邮箱账号
				msg.add(m);
				System.out.println(m.toString());
			}
			System.out.println("Success do '" + sql + "'!(db-findMsg)");
			return msg;
		} catch (Exception e) {
			System.out.print("Fail do '" + sql + "'!(db-findMsg)");
			e.printStackTrace();
			return null;
		}
	}
	//public Vector<Message> viewAllMsg();//管理员获取全部私人消息，暂没用不写
	public Vector<String> viewBullet(){//获取公告
		Vector<String> Bul=new Vector<String>();
		String sql = "select * from Bullet;";
		try{
			PreparedStatement pst = connect.prepareStatement(sql);
			ResultSet ret = pst.executeQuery();// 执行语句，得到结果集
			while(ret.next()){// 结果集非空
				//System.out.println("hhh");
				String cont=new String(
				ret.getString("content"));//that任务-收件邮箱账号
				Bul.add(cont);
				System.out.println(cont);
			}
			System.out.println("Success do '" + sql + "'!(db-viewBullet)");
			return Bul;
		} catch (Exception e) {
			System.out.print("Fail do '" + sql + "'!(db-viewBullet)");
			e.printStackTrace();
			return null;
		}
	}
	
	public Vector<CreateTaskFormBean> viewTask(String uMailAccount) throws SQLException{//根据用户mail查其所有Task
		Vector<CreateTaskFormBean> t=new Vector<CreateTaskFormBean>();
		String sql = "select * from taskFormbean where ownerMail=\'" + uMailAccount +"\';";
		try{
			PreparedStatement pst = connect.prepareStatement(sql);
			ResultSet ret = pst.executeQuery();// 执行语句，得到结果集
			while(ret.next()){// 结果集非空
				//System.out.println("hhh");
				CreateTaskFormBean tf=new CreateTaskFormBean(ret.getString("taskName"),
				ret.getInt("taskID"),//任务ID唯一标识
				ret.getString("ownerMail"),//任务所属用户的账号邮箱
				ret.getInt("thisType"),
				ret.getInt("thatType"),
				ret.getString("orderedTime"),//定时
				ret.getString("MonitorMailAccount"),//this任务-收件QQ邮箱账号
				ret.getString("MonitorMailpassword"),//this任务-收件QQ邮箱密码
				ret.getString("MonitorWeiboAccount"),//this任务-监听微博账号
				ret.getString("MonitorWeiboAccessToken"),//this任务-监听微博授权码
				ret.getString("MonitorContain"),//this任务-监听微博内容
				ret.getInt("listenMinute"),//this任务-监听微博时长
			
				ret.getString("weiboContent"),//that任务-发送微博内容
				ret.getString("sendWeiboAccount"),//that任务-发送微博账号
				ret.getString("sendWeiboAccessToken"),//that任务-发送微博授权码
				ret.getString("mailContent"),//that任务-发送邮件内容
				ret.getString("receiverMailAccount") );//that任务-收件邮箱账号
				System.out.println(tf.toString());
				//Task tmpTask=createTask(tf);//待实现
				t.add(tf);
			}
			System.out.println("Success do '" + sql + "'!(db-viewTask)");
			return t;
		} catch (Exception e) {
			System.out.print("Fail do '" + sql + "'!(db-viewTask)");
			e.printStackTrace();
			return null;
		}
	}
	
	public Vector<UserAccount> viewAllUsers(){//获取全部user
		Vector<UserAccount> Users=new Vector<UserAccount>();
		String sql = "select * from UserAccount ;";
		try{
			PreparedStatement pst = connect.prepareStatement(sql);
			ResultSet ret = pst.executeQuery();// 执行语句，得到结果集
			while(ret.next()){// 结果集非空
				/*
				//System.out.println("hhh");
				String mail=ret.getString("mailAccount");
				//根据userMail获取ExpenseCalendar
				Vector<ExpenseCalendar> u_ec=findExpCal(mail);
				//根据userMail获取Tasks
				Vector<Task> u_t=viewTask(mail);
				//根据userMail获取Msg
				Vector<Message> u_m=findMsg(mail);
				*/
				UserAccount u=new UserAccount(
				ret.getString("username"),
				ret.getString("pwd"),
				ret.getString("mail"),
				ret.getDouble("balance"),
				ret.getInt("lv"),
				ret.getInt("credit"),
				ret.getDouble("discount"));
				Users.add(u);
				System.out.println(u.toString());
				//Task tmpTask=createTask(tf);//待实现
			}
			System.out.println("Success do '" + sql + "'!(db-viewTask)");
			return Users;
		} catch (Exception e) {
			System.out.print("Fail do '" + sql + "'!(db-viewTask)");
			e.printStackTrace();
			return null;
		}
	}

	public boolean storeTask(CreateTaskFormBean tf){//保存taskFormbean
		String sql = "INSERT INTO taskFormBean VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pst = connect.prepareStatement(sql);// 准备执行语句
			pst.setInt(1, Integer.parseInt(tf.getTaskID()));
			pst.setString(2, tf.getTaskName());
			pst.setString(3, tf.getOwnerMail());
			pst.setInt(4, Integer.parseInt(tf.getThisType()));
			pst.setInt(5, Integer.parseInt(tf.getThatType()));
			pst.setString(6, tf.getOrderedTime());
			pst.setString(7, tf.getMonitorMailAccount());
			pst.setString(8,tf.getMonitorMailpassword());
			pst.setString(9,tf.getMonitorWeiboAccount());
			pst.setString(10,tf.getMonitorWeiboAccessToken());
			pst.setString(11,tf.getMonitorContain());
			pst.setInt(12,Integer.parseInt(tf.getListenMinute()));
			pst.setString(13,tf.getWeiboContent());
			pst.setString(14,tf.getSendWeiboAccount());
			pst.setString(15,tf.getSendWeiboAccessToken());
			pst.setString(16,tf.getMailContent());
			pst.setString(17,tf.getReceiverMailAccount());
			pst.executeUpdate();
			System.out.println("Success do '" + sql + "'!(db-storeTask)");
			return true;
		} catch (Exception e) {
			System.out.print("Fail do '" + sql + "'!(db-storeTask)");
			e.printStackTrace();
			return false;
		}
	}
	public boolean storeMsg(String uMail,String cont){//存私信
		String sql = "INSERT INTO MESSAGE VALUES(?,?)";
		try {
			PreparedStatement pst = connect.prepareStatement(sql);// 准备执行语句
			pst.setString(1, uMail);
			pst.setString(2, cont);
			pst.executeUpdate();
			System.out.println("Success do '" + sql + "'!(db-storeMsg)");
			return true;
		} catch (Exception e) {
			System.out.print("Fail do '" + sql + "'!(db-storeMsg)");
			e.printStackTrace();
			return false;
		}
	}
	public boolean storeBullet(String cont){//存公告	
		String sql = "INSERT INTO Bullet VALUES(?)";
		try {
			PreparedStatement pst = connect.prepareStatement(sql);// 准备执行语句
			pst.setString(1, cont);
			pst.executeUpdate();
			System.out.println("Success do '" + sql + "'!(db-storeBullet)");
			return true;
		} catch (Exception e) {
			System.out.print("Fail do '" + sql + "'!(db-storeBullet)");
			e.printStackTrace();
			return false;
		}
	}
	public boolean setBalance(String uMail,double balance){//set余额，可用于充值或其他修改
		String sql = "UPDATE USERS SET BALANCE="+balance+" WHERE MAIL=\'" + uMail +"\';";
		try {
			PreparedStatement pst = connect.prepareStatement(sql);// 准备执行语句
			pst.setDouble(4, balance);
			pst.executeUpdate();
			System.out.println("Success do '" + sql + "'!(db-setBalance)");
			return true;
		} catch (Exception e) {
			System.out.print("Fail do '" + sql + "'!(db-setBalance)");
			e.printStackTrace();
			return false;
		}
	}

	/*
	public boolean setUserLevel(String uMail){//修改会员资料
		
	}*/
	
}

