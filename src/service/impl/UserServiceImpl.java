package service.impl;
import dao.IUserDao;
import dao.impl.UserDaoImpl;
import domain.UserAccount;
import exception.UserException;
import service.IUserService;
//IUserService接口的实现-提供给用户的服务的实现
public class UserServiceImpl implements IUserService{
	private IUserDao uDao=new UserDaoImpl();
	
	public void registerUser(UserAccount user)throws UserException{
		if (uDao.find(user.getMailAccount())!=null) {
			throw new UserException("注册的邮箱已存在!");
		}
		uDao.add(user);
	}
	
	public UserAccount loginUser(String uName,String uPwd){
		if (uDao.find(uName,uPwd)=null) {
			throw new UserException("MailAccount或pwd错误!");
		}
		
	}
	
}
