
    /**  
     * @Title: Forget.java
     * @Package server
     * @Description: TODO(用一句话描述该文件做什么)
     * @author Administrator
     * @date 2019年10月4日 下午7:22:26 
     * @version V1.0  
     */
    
package server;

import java.sql.SQLException;

/**
     * @ClassName: Forget
     * @Description: TODO(这里用一句话描述这个类的作用)
     * @author Administrator
     * @date 2019年10月4日
     *
     */

public class Forget {
	private String[] Message;
	private String sendVerifyCode = "1234";
	public boolean ifSame(String userName,String verifyCode) {
		dao.getUserMessage isName = new dao.getUserMessage();
		try {
			isName.getConnection();
			Message = isName.queryDate("select distinct * from table_users where account='"+userName+"'").split(";");
			if(userName.equals(Message[0]) && sendVerifyCode.equals(verifyCode)) {
				return true;
			}else {
				return false;
			}	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
