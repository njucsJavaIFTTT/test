package dao.impl;
import java.text.SimpleDateFormat;
import org.dom4j.Document;
import org.dom4j.Element;
import dao.IUserDao;
import domain.UserAccount;
import util.XmlUtils;
//IUserDao接口的实现类-关于用户的增查服务实现

//这个地方不知道element要干嘛,其实应该可以改成直接连接数据库
public class UserDaoImpl implements IUserDao{
	public UserAccount find(String uName,String uPwd){
		Document docm=XmlUtils.getDocument();
		//XPath表达式来操作xml节点
		Element e=(Element)docm.selectSingleNode("//user[@username='"+uName+"' and @password='"+uPwd+"']");
		if(e==null){
			return null;
		}
		UserAccount user=new UserAccount(e.attributeValue("username"),e.attributeValue("password"),e.attributeValue("mailAccount"));
		return user;
	}
	
	@SuppressWarnings("deprecation")
	public void add(UserAccount user){
		Document docm=XmlUtils.getDocument();
		
		Element root=docm.getRootElement();
		Element userNode=root.addElement("user");//创建user节点并挂到root
		userNode.setAttributeValue("username",user.getUsername());
		userNode.setAttributeValue("password",user.getPassword());
		userNode.setAttributeValue("emailAccount",user.getMailAccount());
		
		XmlUtils.write2Xml(docm);
	}
	
	public UserAccount find(String uName){
		Document docm=XmlUtils.getDocument();
		Element e=(Element)docm.selectSingleNode("//user[@username='"+uName+"']");
		if(e==null){
			return null;
		}
		UserAccount user=new UserAccount(e.attributeValue("username"),e.attributeValue("password"),e.attributeValue("mailAccount"));
		return user;
	}
}
