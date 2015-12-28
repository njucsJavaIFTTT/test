package web.controller;

import java.io.IOException;
import java.io.OutputStream;
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
    	UserAccount user = (UserAccount)request.getSession().getAttribute("user");
    	String userMailAccount = user.getMailAccount();
    	
    	/* 从数据库中获取该用户名下的消费记录 */
   		DBHelperImpl db=new DBHelperImpl();
   		Vector<ExpenseCalendar> expCal=db.findExpCal(userMailAccount);
   		db.close();
    	/* 将任务列表通过Session传递到前端 */
    	request.getSession().setAttribute("list", expCal);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
    
}
