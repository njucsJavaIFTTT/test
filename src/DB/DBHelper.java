package DB;

import java.sql.*;
import domain.Task;
import domain.UserAccount;
import domain.ExpenseCalendar;
import web.formbean.CreateTaskFormBean;
import java.util.Vector;
import java.util.Date;

public class DBHelper {// 用于打开或关闭数据库

	Connection connect = null;
	static String sql = null;// sql是要执行的语句
	public PreparedStatement pst = null;
	static ResultSet ret = null;// 返回结果集
	public void createALL(){//只要第一次建立网站的时候调用即可
		//已经create DB Users好了-手动在Mysql workbench弄。
		//create table users
		sql = "create table UserAccount("
				+ "	username char(50),"
				+ "pwd char(50),"
				+"mail char(100) not null primary key,"
				+ "balance double,"
				+ "lv int,"
				+"credit int,"
				+ "discount double,"
				+ "UserState int)";
		try {
			pst = connect.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ret = pst.executeQuery();// 执行语句，得到结果集
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//create table taskFormbean
		sql ="create table taskFormbean("
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
				+ "WeiboContent char(50),"
				+ "sendWeiboAccount char(50),"
				+ "sendWeiboAccessToken char(50),"
				+ "mailContent char(50),"
				+ "receiverMailAccount char(50) )";
		try {
			pst = connect.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ret = pst.executeQuery();// 执行语句，得到结果集
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//create table expenseCalendar
		sql ="create table ExpenseCalendar("
				+ "startDate timestamp,"
				+ "taskID int not null primary key,"
				+ "num int";
		try {
			pst = connect.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ret = pst.executeQuery();// 执行语句，得到结果集
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//create table message（待定）
	}
	public DBHelper() {
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
		// 执行sql语句
		try {
			sql = "INSERT INTO UserAccount VALUES(?,?,?,?,?,?,?)";
			pst = connect.prepareStatement(sql);// 准备执行语句
			pst.setString(1, user.getUsername());
			pst.setString(2, user.getPassword());
			pst.setString(3, user.getMailAccount());
			pst.setDouble(4, user.getBalance());
			pst.setInt(5, user.getLevel());
			pst.setInt(6, user.getCredit());
			pst.setDouble(7, user.getDiscount());
			// pst.setBoolean(2,user.getState());不好弄不是bool也不是int是enum,暂时不加进去
			pst.executeUpdate();
			System.out.println("Success do '" + sql + "'!(db-Users)");
		} catch (Exception e) {
			System.out.print("Fail do '" + sql + "'!(db-Users)");
			e.printStackTrace();
		}
	}

	public String findUser(String uMailAccount) {
		// 执行sql语句
		String uPwd = null;
		try {

			sql = "select * from UserAccount where mail=\'" + uMailAccount + "\';";
			// pst.setBoolean(2,user.getState());不好弄不是bool也不是int是enum,暂时不加进去
			pst = connect.prepareStatement(sql);
			ret = pst.executeQuery();// 执行语句，得到结果集
			if(!ret.next())
				return null;//"CF"cant find 关键词,会在调用此方法的上一级方法中检验
			else
			{
				uPwd = ret.getString("pwd");
				String uName = ret.getString("username");
				System.out.println("username:" + uName +"get pwd:" + uPwd+" mailAccount:"+uMailAccount);
				System.out.println("Success do \'" + sql + "\'!(db-Users)");
				return uPwd;
			}
			//return new UserAccount(uName,uPwd,uMailAccount);//这边以后要用完全的复制
		} catch (Exception e) {
			System.out.print("Fail do \'" + sql + "\'!(db-Users)");
			e.printStackTrace();
		}
		return null;//cant find 关键词,会在调用此方法的上一级方法中检验,这里也写是因为trycatch里的东西不被认可存在
	}

	public UserAccount findUser(String uMailAccount,String uPwd) {
		// 执行sql语句
		UserAccount user=null;
		try {
			sql = "select * from UserAccount where mail=\'" + uMailAccount + "\' and pwd=\'"+uPwd+"\';";
			// pst.setBoolean(2,user.getState());不好弄不是bool也不是int是enum,暂时不加进去
			pst = connect.prepareStatement(sql);
			ret = pst.executeQuery();// 执行语句，得到结果集
			if(ret.next()){// 结果集非空
				//System.out.println("hhh");
				String uName = ret.getString("username");
				user=new UserAccount(uName,uPwd,uMailAccount);

				System.out.println("username:" + uName +" get pwd:" + uPwd+" mailAccount:"+uMailAccount);
				System.out.println("Success do '" + sql + "'!(db-Users)");
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
			this.pst.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean charge(String mailAccount,int ExpCnt,Task t){
		//根据任务对用户进行收费并增加消费记录
		try {
			sql = "INSERT INTO ExpenseCalendar VALUES(?,?,?)";
			pst = connect.prepareStatement(sql);// 准备执行语句
			Date date=new Date();
			Timestamp tStamp = new Timestamp(date.getTime());
		
			pst.setTimestamp(1, tStamp);
			pst.setInt(2, t.getTaskID());
			pst.setInt(3, ExpCnt);
			pst.executeUpdate();
			System.out.println("Success do '" + sql + "'!(db-Users)");
		} catch (Exception e) {
			System.out.print("Fail do '" + sql + "'!(db-Users)");
			e.printStackTrace();
		}
		return true;
	}
	
	public Vector<Task> viewTask(String uMailAccount) throws SQLException{//根据用户mail查其所有Task
		Vector<Task> t=new Vector<Task>();
		sql = "select * from taskFormbean where ownerMail=\'" + uMailAccount +"\';";
		pst = connect.prepareStatement(sql);
		ret = pst.executeQuery();// 执行语句，得到结果集
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
			//t.add(tmpTask);
		}
		System.out.println("Success do '" + sql + "'!(db-viewTask)");
		return t;
	}
	
	public CreateTaskFormBean findTask(int taskID) throws SQLException{//通过taskID返回相应formbean
		sql = "select * from taskFormbean where taskID=" + taskID +";";
		pst = connect.prepareStatement(sql);
		ret = pst.executeQuery();// 执行语句，得到结果集
		if(ret.next()){// 结果集非空
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
			System.out.println("find-Success do '" + sql + "'!(db-tIDgetTFB)");
			return tf;
		}
		else{
			System.out.println("can't find-Success do '" + sql + "'!(db-tIDgetTFB)");
			return null;
		}
		
	}
}
