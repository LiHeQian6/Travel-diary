
    /**  
     * @Title: LikeMessage.java
     * @Package server
     * @Description: TODO(��һ�仰�������ļ���ʲô)
     * @author Administrator
     * @date 2019��10��8�� ����9:05:27 
     * @version V1.0  
     */
    
package server;

import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import dao.UpDataMessage;

/**
     * @ClassName: LikeMessage
     * @Description: TODO(������һ�仰��������������)
     * @author Administrator
     * @date 2019��10��8��
     *
     */

public class LikeMessage {
	public boolean addLikeNum(int id,String user) {
		UpDataMessage dataMessage=new UpDataMessage();
		int n=0;
		try {
			dataMessage.getConnection();
			n=dataMessage.UpDate("UPDATE table_message set praiseNum=praiseNum+1,liked=CONCAT(liked,"+",\""+user+"\") where id_message="+id+";");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("UPDATE table_message set praiseNum=praiseNum+1,liked=CONCAT(liked,"+"',"+user+"') where id_message="+id+";");
		System.out.println(n);
		if(n>0) {
			return true;
		}else
			return false;
	}
}
