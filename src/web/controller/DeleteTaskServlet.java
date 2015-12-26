package web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理删除任务的Servlet
 * @author YJJ
 */
@WebServlet("/DeleteTaskServlet")

public class DeleteTaskServlet {

	private static final long serialVersionUID = 1L;

    public DeleteTaskServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    	
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
}
