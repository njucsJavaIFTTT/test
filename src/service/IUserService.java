package service;

import domain.UserAccount;
import exception.UserException;

//提供给用户的服务的接口
public interface IUserService {
	// 注册服务
	void registerUser(UserAccount user) throws UserException;

	// 登录服务
	UserAccount loginUser(String uName, String uPwd);
}
