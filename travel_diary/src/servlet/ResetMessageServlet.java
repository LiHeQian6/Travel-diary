package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ResetMessageServlet
 */
@WebServlet("/ResetMessageServlet")
public class ResetMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetMessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		server.ResetMessages newMessage = new server.ResetMessages();
		PrintWriter writer = response.getWriter();
		String nameString = request.getParameter("name");
		String newName = request.getParameter("newName"); 
		System.out.println(nameString+"  "+newName);
		if(newMessage.resetMessage(nameString,newName)) {
			writer.write("T");
			System.out.println("�޸��ǳƳɹ���");
		}else {
			writer.write("F");
			System.out.println("�޸��ǳ�ʧ�ܣ�");
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
