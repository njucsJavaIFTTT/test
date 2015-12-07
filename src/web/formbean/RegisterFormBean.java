package web.formbean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

/**
 * 封装的用户注册表单bean，用来接收register.jsp中的表单输入项的值
 * RegisterFormBean中的属性与register.jsp中的表单输入项的name一一对应
 * RegisterFormBean的职责除了负责接收register.jsp中的表单输入项的值之外还担任着校验表单输入项的值的合法性
 * @author gacl
 *
 */
public class RegisterFormBean {

	//RegisterFormBean中的属性与register.jsp中的表单输入项的name一一对应
	//private String userName;//前端还未实现昵称
	private String password;
	private String mailAccount;
	//private String verificationCode;//验证码

	
	/**
	 * 存储校验不通过时给用户的错误提示信息
	 */
	private Map<String, String> errors = new HashMap<String, String>();

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	/*
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getmailAccount() {
		return mailAccount;
	}
	public void setmailAccount(String mailAccount) {
		this.mailAccount = mailAccount;
	}
	
	/*
	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verCode) {
		this.verificationCode = verCode;
	}
	*/
}
