package web.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DBHelperImpl;
import domain.UserAccount;

/**
 * 处理查看公告的Servlet
 * @author YJJ
 */
@WebServlet("/RechargeServlet")

public class RechargeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    public RechargeServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    	/* 获取当前session的用户名 */
    	UserAccount user = (UserAccount)request.getSession().getAttribute("user");
    	String uMail = user.getMailAccount();
    	String old_balance_s=String.valueOf(user.getBalance());
    	double old_balance=Double.valueOf(old_balance_s);
    	double value=Double.valueOf(request.getParameter("value"));
    	
    	/* 从数据库中充值该用户名下的Balance */
    	DBHelperImpl db=new DBHelperImpl();
   		boolean ret=db.setBalance(uMail,old_balance+value);
   		db.close();
   		
    	/* 将当前查询的用户名通过Session传递到前端 */
    	DBHelperImpl db2=new DBHelperImpl();
   		UserAccount userAc=db2.findUser_Ac(uMail);
   		db2.close();
    	request.getSession().setAttribute("user", userAc);
    	
    	/* 将结果通过Session传递到前端 */	
    	String data;
    	if(ret==true)
    		data = "Recharge successfully.";
    	else
    		data= "Fail to recharge.";
	    OutputStream stream = response.getOutputStream();
	    stream.write(data.getBytes("UTF-8"));
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
}
