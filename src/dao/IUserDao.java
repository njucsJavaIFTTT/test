package dao;
import domain.UserAccount;
//关于用户的增查服务接口
public interface IUserDao {
	UserAccount find(String uName,String uPwd);//根据用户名密码来查找用户
	UserAccount find(String uName);//根据用户名查找用户
	void add(UserAccount user);//添加用户
}
