package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import bean.Messages;

/**
 * Servlet implementation class ChangeMessageServlet
 */
@WebServlet("/ChangeMessageServlet")
public class ChangeMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeMessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		server.ChangeMessage changemessage = new server.ChangeMessage();
		PrintWriter writer = response.getWriter();
        Messages messages = new Messages();
        String x = "";
		InputStreamReader reader=new InputStreamReader(request.getInputStream(),"UTF-8");
		char [] buff=new char[2048];
		int length=0;
		while((length=reader.read(buff))!=-1){
		     x+=new String(buff,0,length);
		}
		JSONObject jsonObject = new JSONObject(x);
		messages.setId(jsonObject.getInt("id"));                //id
        messages.setLikeNum(jsonObject.getInt("likenum"));      //����
        messages.setTitle(jsonObject.getString("title"));       //����
        messages.setContent(jsonObject.getString("content"));   //��������
        messages.setAddress(jsonObject.getString("address"));   //��ַ
        messages.setDate(jsonObject.getString("date"));
        messages.setLat(jsonObject.getDouble("lat"));
        messages.setLng(jsonObject.getDouble("lng"));
        System.out.println(messages.toString());
        if(changemessage.ChangeMessage(messages)) {
        	writer.write("true");
        	System.out.println("�޸ĳɹ���");
        }else {
        	writer.write("false");
        	System.out.println("�޸�ʧ�ܣ�");
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
