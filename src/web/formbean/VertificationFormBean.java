package web.formbean;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装的用户验证表单bean，用来接收register.jsp中的表单输入项的值
 * RegisterFormBean中的属性与register.jsp中的表单输入项的name一一对应
 * RegisterFormBean的职责为负责接收register.jsp中的表单输入项的值
 * @author YJJ
 *
 */
public class VertificationFormBean {

	private String userID;//注册邮箱
	private String verificationCode;//验证码
	
	private Map<String, String> errors = new HashMap<String, String>();
	
	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}

}
