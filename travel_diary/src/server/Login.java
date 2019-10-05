
    /**  
     * @Title: Login.java
     * @Package server
     * @Description: TODO(用一句话描述该文件做什么)
     * @author Administrator
     * @date 2019年10月3日 下午4:11:48 
     * @version V1.0  
     */
    
package server;


    /**
     * @ClassName: Login
     * @Description: 获取用户数据并检查对比
     * @author 孙建旺
     * @date 2019年10月3日
     *
     */

public class Login {
	
	public boolean getloginMessage(String inputName,String inputPassword) {
		String messageString="";
		String[] Message;
		dao.getUserMessage getusermessage = new dao.getUserMessage();
		try {
			messageString = getusermessage.queryDate("select distinct * from table_users where account='"+inputName+"'");
			System.out.println(messageString);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(messageString.equals("null")){
			return false;
		}
		Message = messageString.split(";");
		if(inputName.equals(Message[0]) && inputPassword.equals(Message[1])) {
			return true;
		}else
			return false;
	}
}
