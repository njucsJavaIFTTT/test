package web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理查看任务的Servlet
 * @author YJJ
 */
@WebServlet("/ViewTaskServlet")

public class ViewTaskServlet {

	private static final long serialVersionUID = 1L;

    public ViewTaskServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    	
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
}
