package service.impl;

import dao.IUserDao;
import dao.impl.UserDaoImpl;
import domain.Task;
import domain.UserAccount;
import exception.TaskException;
import exception.UserException;
import service.ITaskService;
import service.IUserService;
import web.formbean.CreateTaskFormBean;

//IUserService接口的实现-提供给用户的服务的实现
public class UserServiceImpl implements IUserService{
	private IUserDao uDao=new UserDaoImpl();
	
	public void registerUser(UserAccount user)throws UserException{
		if (uDao.find(user.getMailAccount())!=null) {
			throw new UserException("注册的邮箱已存在!");
		}
		uDao.add(user);
	}
	
	public UserAccount loginUser(String uName,String uPwd)throws UserException{
		UserAccount user = uDao.find(uName,uPwd);
		if (user==null) {
			throw new UserException("邮箱或密码错误!");
		}
		return user;
	}

	@Override
	public void chargeUser(CreateTaskFormBean formBean) throws UserException {
		String uMailAccount = formBean.getOwnerMail();
		String password = uDao.find(uMailAccount);
		if(password == null)
			throw new UserException("任务所属用户不存在!");
		
		Task task = null;
		ITaskService taskService = new TaskServiceImpl();
		try {
			task = taskService.createTask(formBean);
		} catch (TaskException e) {
			e.printStackTrace();
		}
		
		/* 收费 */
		uDao.charge(uMailAccount,task);
		
	}
	
}
