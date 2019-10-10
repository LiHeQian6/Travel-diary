
    /**  
     * @Title: ResetPassword.java
     * @Package server
     * @Description: TODO(用一句话描述该文件做什么)
     * @author Administrator
     * @date 2019年10月5日 下午4:25:55 
     * @version V1.0  
     */
    
package server;

import java.sql.SQLException;

import dao.UpDataMessage;

/**
     * @ClassName: ResetPassword
     * @Description: TODO(这里用一句话描述这个类的作用)
     * @author Administrator
     * @date 2019年10月5日
     *
     */

public class ResetPassword {
	dao.UpDataMessage upData = new dao.UpDataMessage();
	public boolean resetPassword(String emailAddress,String password){
		try {
			upData.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(upData.UpDate("update table_users set password ='"+password+"' where account='"+emailAddress+"'")==1) {
			return true;
		}else {
			return false;
		}
	}
	
}
