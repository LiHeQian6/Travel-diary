
    /**  
     * @Title: InsertMessage.java
     * @Package server
     * @Description: TODO(��һ�仰�������ļ���ʲô)
     * @author Administrator
     * @date 2019��10��7�� ����8:12:22 
     * @version V1.0  
     */
    
package server;

import java.sql.SQLException;

import bean.Messages;

/**
     * @ClassName: InsertMessage
     * @Description: TODO(������һ�仰��������������)
     * @author Administrator
     * @date 2019��10��7��
     *
     */

public class InsertMessage {
	dao.InsertMessage insert = new dao.InsertMessage();
	public int InsertMessage(Messages m) {
		try {
			insert.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i=0;
		int n=0;
		try {
			i=insert.queryDate("SELECT id_user from table_users where account='"+m.getUser()+"';");
			n = insert.queryDate("SELECT id_position from talle_position where latitude="+m.getLat()+"and longitude="+m.getLng()+";");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int id=-1;
		System.out.println(n+" "+i);
		if(n==0) {
			n=insert.insert("insert into talle_position values(null,"+m.getLng()+","+m.getLat()+",'"+m.getAddress()+"');");
		}
		id=insert.insert("insert into table_message values(null,"+m.getTitle()+","+m.getContent()+","+m.getLikeNum()+","+
				n+","+i+","+m.getUser()+","+m.getDate()+")");
		if(id!=-1) {
			return id;
		}else {
			return -1;
		}
	}
}
