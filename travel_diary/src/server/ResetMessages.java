
    /**  
     * @Title: ResetMessages.java
     * @Package server
     * @Description: �޸��ǳ�
     * @author �ｨ��
     * @date 2019��10��6�� ����2:46:06 
     * @version V1.0  
     */
    
package server;

import java.sql.SQLException;

/**
     * @ClassName: ResetMessages
     * @Description: �޸��ǳ�
     * @author �ｨ��
     * @date 2019��10��6��
     *
     */

public class ResetMessages {
	dao.UpDataMessage upData = new dao.UpDataMessage();
	public boolean resetMessage(String emailAddress,String newMessage){
		try {
			upData.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(upData.UpDate("update table_users set nickName ='"+newMessage+"' where account='"+emailAddress+"'")==1) {
			return true;
		}else {
			return false;
		}
	}
}
