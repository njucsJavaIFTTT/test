package service;

import domain.UserAccount;
import exception.UserException;
import web.formbean.CreateTaskFormBean;

//提供给用户的服务的接口
public interface IUserService {
	// 注册服务
	void registerUser(UserAccount user) throws UserException;

	// 登录服务
	UserAccount loginUser(String uName, String uPwd) throws UserException;
	
	// 扣费服务
	void chargeUser(CreateTaskFormBean formBean) throws UserException;
}
