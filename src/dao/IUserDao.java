package dao;
import domain.Task;
import domain.UserAccount;
//关于用户的增查服务接口
public interface IUserDao {
	UserAccount find(String uMailAccount,String uPwd);//根据mail和密码来查找用户
	String find(String uMailAccount);//根据mail查找用户
	void add(UserAccount user);//添加用户
	void charge(String uMailAccount,Task task);//根据任务对用户进行收费
}
