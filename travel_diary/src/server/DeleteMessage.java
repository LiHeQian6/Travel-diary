
    /**  
     * @Title: DeleteMessage.java
     * @Package server
     * @Description: ɾ������
     * @author �ｨ��
     * @date 2019��10��7�� ����4:54:28 
     * @version V1.0  
     */
    
package server;

import java.sql.SQLException;

import dao.UpDataMessage;

/**
     * @ClassName: DeleteMessage
     * @Description: ɾ������
     * @author �ｨ��
     * @date 2019��10��7��
     *
     */

public class DeleteMessage {
	dao.UpDataMessage upData = new dao.UpDataMessage();
	public boolean deleteMessage(String id) {
		try {
			upData.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(upData.UpDate("delete from table_message where id_message='"+id+"'")==1) {
			return true;
		}else {
			return false;
		}

	}
}
