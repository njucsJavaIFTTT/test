package web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DBHelperImpl;
import domain.UserAccount;
import exception.TaskException;
import service.ITaskService;
import service.impl.TaskServiceImpl;
import util.WebUtils;
import web.formbean.CreateTaskFormBean;
import domain.ExpenseCalendar;
import DB.DBHelperImpl;

/**
 * 通过mail查ExpenseCalendar的Servlet
 * @author YJJ
 */
@WebServlet("/viewExpCalServlet")

public class viewExpCalServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    public viewExpCalServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    	/* 获取当前session的用户名 */
    	String userMailAccount = (String)request.getParameter("userId");
    	
    	/* 从数据库中获取该用户名下的消费记录 */
   		DBHelperImpl db=new DBHelperImpl();
   		Vector<ExpenseCalendar> expCal=db.findExpCal(userMailAccount);
   		db.close();
   		
    	/* 将当前查询的用户名通过Session传递到前端 */
    	request.getSession().setAttribute("currentUser", userMailAccount);
    	
    	/* 将当前查询用户的消费记录通过data传递到前端 */
    	response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
	    String data = "";
	    Iterator<ExpenseCalendar> iterator = expCal.iterator();
	    while(iterator.hasNext()){
	    	ExpenseCalendar expenseCalendar = iterator.next();
	    	data = data + expenseCalendar.toString() + "\n";
	    }
	    OutputStream stream = response.getOutputStream();
	    stream.write(data.getBytes("UTF-8"));
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
    
}
