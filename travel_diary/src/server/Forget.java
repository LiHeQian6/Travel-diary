
    /**  
     * @Title: Forget.java
     * @Package server
     * @Description: TODO(��һ�仰�������ļ���ʲô)
     * @author Administrator
     * @date 2019��10��4�� ����7:22:26 
     * @version V1.0  
     */
    
package server;

import java.sql.SQLException;

/**
     * @ClassName: Forget
     * @Description: TODO(������һ�仰��������������)
     * @author Administrator
     * @date 2019��10��4��
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
