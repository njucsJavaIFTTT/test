package DB;

import java.sql.SQLException;
import java.util.Vector;

import domain.Task;
import domain.UserAccount;
import web.formbean.CreateTaskFormBean;

public interface DBHelper {
	public void createALL();//只要第一次建立网站的时候调用即可
	public void addUser(UserAccount user);
	public String findUser(String uMailAccount);
	public UserAccount findUser(String uMailAccount,String uPwd) ;
	public void close();//关闭对数据库的连接
	public boolean charge(String mailAccount,int ExpCnt,Task t);//根据任务对用户进行收费并增加消费记录
	public Vector<Task> viewTask(String uMailAccount) throws SQLException;//根据用户mail查其所有Task
	public CreateTaskFormBean findTask(int taskID) throws SQLException;//通过taskID返回相应formbean
	public boolean storeTask(CreateTaskFormBean tf);//保存taskFormbean
	public boolean modifyTask(CreateTaskFormBean tf);//修改已有的taskFormbean
	public boolean deleteTask(CreateTaskFormBean tf);//删除taskFormbean
}
