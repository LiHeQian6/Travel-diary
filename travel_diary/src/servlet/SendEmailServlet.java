package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.Register;

/**
 * Servlet implementation class SendEmailServlet
 */
@WebServlet("/SendEmailServlet")
public class SendEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendEmailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		util.EmailUtil sendEmail = new util.EmailUtil();
		server.Register ifSame = new server.Register();
		PrintWriter writer = response.getWriter();
		String emailAddress = request.getParameter("emailAddress");
		String tag = request.getParameter("Tag");
		switch(tag) {
			case "register":
				if(ifSame.ifSame(emailAddress)) {
					writer.write("S");
				}else {
					if(sendEmail.sendMail(emailAddress))
						writer.write("T");
					else
						writer.write("F");
				}
				break;
			case "forgetPassword":
				if(ifSame.ifSame(emailAddress)) {
					if(sendEmail.findPasswordByMail(emailAddress))
						writer.write("T");
					else
						writer.write("F");
				}else {
					writer.write("S");
				}
				break;
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
