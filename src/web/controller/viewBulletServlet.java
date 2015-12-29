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
import domain.ExpenseCalendar;
import domain.UserAccount;
import exception.TaskException;
import service.ITaskService;
import service.impl.TaskServiceImpl;
import web.formbean.CreateTaskFormBean;

/**
 * 处理查看公告的Servlet
 * @author YJJ
 */
@WebServlet("/ViewBulletServlet")

public class viewBulletServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

    public viewBulletServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    	/* 获取当前session的用户名 */
    	UserAccount user = (UserAccount)request.getSession().getAttribute("user");
    	String userMailAccount = user.getMailAccount();
    	
    	/* 从数据库中获取公告 */
   		DBHelperImpl db=new DBHelperImpl();
   		Vector<String> Bullet=db.viewBullet();
   		db.close();

    	/* 将当前查询用户的消费记录通过data传递到前端 */
    	response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
	    String data = "";
	    Iterator<String> iterator = Bullet.iterator();
	    while(iterator.hasNext()){
	    	String bl = iterator.next();
	    	data = data + bl.toString() + "\n";
	    }
	    OutputStream stream = response.getOutputStream();
	    stream.write(data.getBytes("UTF-8"));
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
    
}
