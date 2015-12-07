package service;
import domain.UserAccount;
import exception.UserExistException;
//提供给用户的服务的接口
public interface IUserService {
	//注册服务
	void registerUser(UserAccount user) throws UserExistException;
	//登录服务
	UserAccount loginUser(String uName,String uPwd);
}
