
    /**  
     * @Title: DeleteMessage.java
     * @Package server
     * @Description: 删除留言
     * @author 孙建旺
     * @date 2019年10月7日 下午4:54:28 
     * @version V1.0  
     */
    
package server;

import java.sql.SQLException;

import dao.UpDataMessage;

/**
     * @ClassName: DeleteMessage
     * @Description: 删除留言
     * @author 孙建旺
     * @date 2019年10月7日
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
