
    /**  
     * @Title: Login.java
     * @Package server
     * @Description: TODO(��һ�仰�������ļ���ʲô)
     * @author Administrator
     * @date 2019��10��3�� ����4:11:48 
     * @version V1.0  
     */
    
package server;


    /**
     * @ClassName: Login
     * @Description: ��ȡ�û����ݲ����Ա�
     * @author �ｨ��
     * @date 2019��10��3��
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
