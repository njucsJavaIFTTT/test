package dao.impl;
import dao.IUserDao;
import domain.ExpenseCalendar;
import domain.Task;
import domain.UserAccount;

import java.util.List;

import DB.DBHelper;
//import util.XmlUtils;
//IUserDao接口的实现类-关于用户的各种服务实现-连接数据库

public class UserDaoImpl implements IUserDao{
	
	public UserAccount find(String uMailAccount,String uPwd){	
		DBHelper db=new DBHelper();
		UserAccount user=db.findUser(uMailAccount,uPwd);
		db.close();
		return user;
	}
	
	public void add(UserAccount user){

		DBHelper db=new DBHelper();
		db.addUser(user);
		db.close();
	}
	
	/* 返回pwd,在调用此方法的方法中校验,以后写好UserAccount深复制可能改成返回UserAccount */
	public String find(String uMailAccount){

		DBHelper db=new DBHelper();
		String pwd=db.findUser(uMailAccount);
		db.close();
		return pwd;
	}
	
	public boolean charge(String uMailAccount,int ExpCnt,Task task){
		//开始任务时对用户收费
		//①修改userAccount的余额 ②增加user的expenseCalendar
		//ExpCnt为user.ExpenseCalendar.size
		DBHelper db=new DBHelper();
		boolean ret=db.charge(uMailAccount,ExpCnt,task);
		db.close();
		return ret;
	};
	
	public List<Task> viewTask(String uMailAccount){//根据用户mail查其所有Task
		List<Task> t;
		return t;
	};
	
	public List<ExpenseCalendar> viewExpCalendar(String uMailAccount){//根据用户mail查其所有消费记录
		List<ExpenseCalendar> expC;
		return expC;
	};
}
