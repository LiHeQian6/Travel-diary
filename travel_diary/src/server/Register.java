  
    /**  
     * @Title: Register.java
     * @Package server
     * @Description: 注册账号
     * @author 孙建旺
     * @date 2019年10月3日 下午7:56:30 
     * @version V1.0  
     */
    
package server;

import java.sql.SQLException;

import util.EmailUtil;

/**
     * @ClassName: Register
     * @Description: 注册账号
     * @author 孙建旺
     * @date 2019年10月3日
     *
     */

public class Register {
	public boolean registerUser(String userName,String Password,String verifyCode) {
		String sendVerifyCode=EmailUtil.getCode();
		dao.UpDataMessage addmessage = new dao.UpDataMessage();
		try {
			addmessage.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(addmessage.UpDate("insert into table_users (account, password) values ('"+userName+"','"+Password+"')")==1 && sendVerifyCode.equals(verifyCode)) {
			return true;
		}else {
			return false;
		}
	}
	public boolean ifSame(String userName) {
		dao.getUserMessage isName = new dao.getUserMessage();
		String[] Message;
		try {
			isName.getConnection();
			Message = isName.queryDate("select distinct * from table_users where account='"+userName+"'").split(";");
			if(Message[0].equals(userName)) {
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
