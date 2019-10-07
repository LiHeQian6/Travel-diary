
    /**  
     * @Title: MyFootPrint.java
     * @Package server
     * @Description: 获取用户所有信息
     * @author 孙建旺
     * @date 2019年10月6日 下午12:42:02 
     * @version V1.0  
     */
    
package server;

import java.awt.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import bean.Messages;

/**
     * @ClassName: MyFootPrint
     * @Description: 获取用户所有信息
     * @author 孙建旺
     * @date 2019年10月6日
     *
     */

public class MyFootPrint {
	public String getAllMessage(String Name) {
		ArrayList<Messages> list = new ArrayList();
		dao.AllMessgaes getALLmessage = new dao.AllMessgaes();
		JSONArray jsonarray = new JSONArray();
		try {
			list = getALLmessage.queryDate(
					"select id_message,title,content,table_message.praiseNum,time,account,latitude,longitude,position"
					+ " from table_message,table_users,talle_position "
					+ "where account='"+Name+"' and table_users.id_user = table_message.id_user and talle_position.id_position = table_message.id_position;");
			System.out.println("select id_message,title,content,table_message.praiseNum,time,account,latitude,longitude,position"
					+ " from table_message,table_users,talle_position "
					+ "where account= '"+Name+"' and table_users.id_user = table_message.id_user and talle_position.id_position = table_message.id_position;");
			for(int i=0;i<list.size();i++) {
				JSONObject jsonobject = new JSONObject();
				jsonobject.put("id", list.get(i).getId());
				jsonobject.put("title", list.get(i).getTitle());
				jsonobject.put("content", list.get(i).getContent());
				jsonobject.put("address", list.get(i).getAddress());
				jsonobject.put("lng",list.get(i).getLng());
				jsonobject.put("lat", list.get(i).getLat());
				jsonobject.put("date", list.get(i).getDate());
				jsonobject.put("user", list.get(i).getUser());
				jsonobject.put("likeNum", list.get(i).getLikeNum());
				jsonarray.put(jsonobject);
			}
			return jsonarray.toString();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonarray.toString();
	}
	public String AllMessage() {
		ArrayList<Messages> list = new ArrayList();
		dao.AllMessgaes getALLmessage = new dao.AllMessgaes();
		JSONArray jsonarray = new JSONArray();
		try {
			list = getALLmessage.queryDate("select id_message,title,content,table_message.praiseNum,time,account,latitude,longitude,position\r\n" + 
					" from table_message,table_users,talle_position \r\n" + 
					" where table_users.id_user = table_message.id_user and talle_position.id_position = table_message.id_position\r\n" + 
					" order by praiseNum,time;");
			for(int i=0;i<list.size();i++) {
				JSONObject jsonobject = new JSONObject();
				jsonobject.put("id", list.get(i).getId());
				jsonobject.put("title", list.get(i).getTitle());
				jsonobject.put("content", list.get(i).getContent());
				jsonobject.put("address", list.get(i).getAddress());
				jsonobject.put("lng",list.get(i).getLng());
				jsonobject.put("lat", list.get(i).getLat());
				jsonobject.put("date", list.get(i).getDate());
				jsonobject.put("user", list.get(i).getUser());
				jsonobject.put("likeNum", list.get(i).getLikeNum());
				jsonarray.put(jsonobject);
			}
			return jsonarray.toString();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonarray.toString();
				
	}
	public String getMessageByPosition(double lat,double lng) {
		ArrayList<Messages> list = new ArrayList();
		dao.AllMessgaes getALLmessage = new dao.AllMessgaes();
		JSONArray jsonarray = new JSONArray();
		try {
			list = getALLmessage.queryDate("select id_message,title,content,table_message.praiseNum,time,account,latitude,longitude,position" + 
					" from table_message,table_users,talle_position " + 
					" where latitude="+lat+" and longitude="+lng+" and table_users.id_user = table_message.id_user and talle_position.id_position = table_message.id_position" + 
					" order by praiseNum,time;");
			for(int i=0;i<list.size();i++) {
				JSONObject jsonobject = new JSONObject();
				jsonobject.put("id", list.get(i).getId());
				jsonobject.put("title", list.get(i).getTitle());
				jsonobject.put("content", list.get(i).getContent());
				jsonobject.put("address", list.get(i).getAddress());
				jsonobject.put("lng",list.get(i).getLng());
				jsonobject.put("lat", list.get(i).getLat());
				jsonobject.put("date", list.get(i).getDate());
				jsonobject.put("user", list.get(i).getUser());
				jsonobject.put("likeNum", list.get(i).getLikeNum());
				jsonarray.put(jsonobject);
			}
			return jsonarray.toString();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonarray.toString();
	}
}
