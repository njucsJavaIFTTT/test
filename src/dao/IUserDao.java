package dao;
import domain.Task;
import domain.ExpenseCalendar;
import  java.util.Vector;
import domain.UserAccount;
//关于用户的各种服务接口
public interface IUserDao {
	UserAccount find(String uMailAccount,String uPwd);//根据mail和密码来查找用户
	String find(String uMailAccount);//根据mail查找用户
	void add(UserAccount user);//添加用户
	boolean charge(String uMailAccount,int ExpCnt,Task task);//根据任务对用户进行收费并增加消费记录，收费成功则true
	Vector<Task> viewTask(String uMailAccount);//根据用户mail查其所有Task
	Vector<ExpenseCalendar> viewExpCalendar(String uMailAccount);//根据用户mail查其所有消费记录
}
