package domain;
import java.util.Vector;
import DB.DBHelperImpl;

public class Manager {
	Vector<String> bullet;//公告信息
	Vector<Message> masg;//私人消息
	public Vector<UserAccount> viewAllUser(){//查看所有User信息
		Vector<UserAccount> Users=new Vector<UserAccount>();
		DBHelperImpl db=new DBHelperImpl();
		Users=db.viewAllUsers();
		db.close();
		return Users;
	}
	public boolean sendMsg(UserAccount u,String cont){//发给user私人消息
		Message msg=new Message(u.getMailAccount(),cont);
		DBHelperImpl db=new DBHelperImpl();
		boolean ret=db.storeMsg(u.getMailAccount(),cont);		
		db.close();
		u.addMas(msg);
		return ret;
	}
	public boolean sendBullet(String cont){//发布公告
		DBHelperImpl db=new DBHelperImpl();
		boolean ret=db.storeBullet(cont);
		db.close();
		return ret;
	}
}
