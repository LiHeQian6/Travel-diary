
    /**  
     * @Title: ChangeMessage.java
     * @Package server
     * @Description: �޸�������Ϣ
     * @author �ｨ��
     * @date 2019��10��7�� ����3:53:05 
     * @version V1.0  
     */
    
package server;

import java.sql.SQLException;

import bean.Messages;

/**
     * @ClassName: ChangeMessage
     * @Description: �޸�������Ϣ
     * @author �ｨ��
     * @date 2019��10��7��
     *
     */

public class ChangeMessage {
	dao.UpDataMessage upData = new dao.UpDataMessage();
	public boolean ChangeMessage(Messages messages) {
		try {
			upData.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("update table_message,table_users set title ='"+messages.getTitle()+"',content = '"+messages.getContent()+"' where account='"+messages.getId()+"' and table_users.id_user=table_message.id_user");
		if(upData.UpDate("update table_message set title ='"+messages.getTitle()+"',content = '"+messages.getContent()+"' where id_message='"+messages.getId()+"'")==1) {
			return true;
		}else {
			return false;
		}
	}
	
}
