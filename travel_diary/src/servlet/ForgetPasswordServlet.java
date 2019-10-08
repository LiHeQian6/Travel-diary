package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.EmailUtil;

/**
 * Servlet implementation class ForgetPasswordServlet
 */
@WebServlet("/ForgetPasswordServlet")
public class ForgetPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForgetPasswordServlet() {
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
		server.Forget forget = new server.Forget();
		PrintWriter writer = response.getWriter();
		String nameString = request.getParameter("name");
		String verifyCode = request.getParameter("verifyCode"); 
		System.out.println(nameString+"  "+verifyCode);
		if("".equals(verifyCode)) {
			EmailUtil.sendMail(nameString);
			writer.write("N");
			System.out.println("请输入验证码");	
		}else {
			if(forget.ifSame(nameString,verifyCode)) {
				writer.write("T");
				System.out.println("验证成功！");
			}else {
				writer.write("Nobody");
				System.out.println("查无此人");
			}
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
