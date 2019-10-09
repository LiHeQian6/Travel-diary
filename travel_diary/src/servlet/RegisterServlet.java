package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.Login;
import util.EmailUtil;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
		server.Register register = new server.Register();
		PrintWriter writer = response.getWriter();
		String nameString = request.getParameter("name");
		String passString = request.getParameter("password");
		String verifyCode = request.getParameter("verifyCode"); 
		System.out.println(nameString+"  "+passString+"  "+verifyCode);
		if(register.ifSame(nameString)) {
			writer.write("S");
			System.out.println("ÖØ¸´");
			return;
		}
//		if(register.registerUser(nameString, passString,verifyCode)) {
//			writer.write("T");
//			System.out.println("×¢²á³É¹¦");
//		}else {
//			writer.write("F");
//			System.out.println("×¢²áÊ§°Ü£¡");
//		}
		if(register.registerUser(nameString, passString,verifyCode)) {
			writer.write("T");
			System.out.println("×¢²á³É¹¦");
		}else {
			writer.write("F");
			System.out.println("×¢²áÊ§°Ü£¡");
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
