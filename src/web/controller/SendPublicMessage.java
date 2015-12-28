package web.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DBHelperImpl;

/**
 * Servlet implementation class SendPublicMessage
 */
@WebServlet("/SendPublicMessage")
public class SendPublicMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendPublicMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String msg = request.getParameter("msg");
		
		DBHelperImpl db=new DBHelperImpl();
		boolean success = db.storeBullet(msg);
		
		if (success) {
			response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		    String data = "success";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8"));
		}
		else {
			response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
		    String data = "failed";
		    OutputStream stream = response.getOutputStream();
		    stream.write(data.getBytes("UTF-8"));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
