package dao.impl;
import java.text.SimpleDateFormat;
import org.dom4j.Document;
import org.dom4j.Element;
import dao.IUserDao;
import domain.UserAccount;
import DB.DBHelper;
//import util.XmlUtils;
//IUserDao接口的实现类-关于用户的增查服务实现

//这个地方不知道element要干嘛,其实应该可以改成直接连接数据库
public class UserDaoImpl implements IUserDao{
	
	public UserAccount find(String uMailAccount,String uPwd){	
		DBHelper db=new DBHelper();
		UserAccount user=db.findUser(uMailAccount,uPwd);
		db.close();
		return user;
	}
	
	@SuppressWarnings("deprecation")
	public void add(UserAccount user){

		DBHelper db=new DBHelper();
		db.addUser(user);
		db.close();
	}
	
	public String find(String uMailAccount){//返回pwd,在调用此方法的方法中校验,以后写好UserAccount深复制可能改成返回UserAccount

		DBHelper db=new DBHelper();
		String pwd=db.findUser(uMailAccount);
		db.close();
		return pwd;
	}
}
