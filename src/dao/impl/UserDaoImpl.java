package dao.impl;
import dao.IUserDao;
import domain.ExpenseCalendar;
import domain.Task;
import java.util.Vector;

import DB.DBHelperImpl;
import domain.UserAccount;
import web.formbean.CreateTaskFormBean;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements IUserDao{
	
	public UserAccount find(String uMailAccount,String uPwd){	
		DBHelperImpl db=new DBHelperImpl();
		UserAccount user=db.findUser(uMailAccount,uPwd);
		db.close();
		return user;
	}
	
	public void add(UserAccount user){

		DBHelperImpl db=new DBHelperImpl();
		db.addUser(user);
		db.close();
	}
	
	/* 返回pwd,在调用此方法的方法中校验,以后写好UserAccount深复制可能改成返回UserAccount */
	public String find(String uMailAccount){

		DBHelperImpl db=new DBHelperImpl();
		String pwd=db.findUser(uMailAccount);
		db.close();
		return pwd;
	}
	
	public boolean charge(String uMailAccount,int ExpCnt,Task task){
		//开始任务时对用户收费，收费成功则true
		//①修改userAccount的余额 ②增加user的expenseCalendar
		//ExpCnt为user.ExpenseCalendar.size
		DBHelperImpl db=new DBHelperImpl();
		boolean ret=db.charge(uMailAccount,ExpCnt,task);
		db.close();
		return ret;
	};
	
	public Vector<CreateTaskFormBean> viewTask(String uMailAccount){//根据用户mail查其所有Task
		DBHelperImpl db=new DBHelperImpl();
		Vector<CreateTaskFormBean> t = null;
		try {
			t = db.viewTask(uMailAccount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.close();
		return t;
	};
	
	public Vector<ExpenseCalendar> viewExpCalendar(String uMailAccount){//根据用户mail查其所有消费记录
		Vector<ExpenseCalendar> expC;
		DBHelperImpl db=new DBHelperImpl();
		expC= db.findExpCal(uMailAccount);
		db.close();
		return expC;
	};
}
