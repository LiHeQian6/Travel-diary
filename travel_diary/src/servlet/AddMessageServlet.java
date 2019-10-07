package servlet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DefaultEditorKit.InsertBreakAction;

import org.json.JSONObject;

import bean.Messages;
import server.InsertMessage;

/**
 * Servlet implementation class AddMessageServlet
 */
@WebServlet("/AddMessageServlet")
public class AddMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InsertMessage insertMessage=new InsertMessage();
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
        messages.setLikeNum(jsonObject.getInt("likenum"));      //赞数
        messages.setTitle(jsonObject.getString("title"));       //标题
        messages.setContent(jsonObject.getString("content"));   //留言内容
        messages.setAddress(jsonObject.getString("address"));   //地址
        messages.setDate(jsonObject.getString("date"));
        messages.setLat(jsonObject.getDouble("lat"));
        messages.setLng(jsonObject.getDouble("lng"));
        System.out.println(messages.toString());
        int id=insertMessage.InsertMessage(messages);
        if(id!=-1) {
        	writer.write(id);
        	System.out.println("添加成功！");
        }else {
        	writer.write(id);
        	System.out.println("添加失败！");
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
