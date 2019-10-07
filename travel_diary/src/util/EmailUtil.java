package util;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class EmailUtil {
	private static String MAIL_EMAIL = "17631233185@163.com";
	private static String MAIL_PWD = "19980804lmy";
	private static String MAIL_PORT = "25";
	private static String MAIL_HOST = "smtp.163.com";
	private static String MAIL_SMTP_AUTH = "true";
	public static String code ="";
	 public static String getCode() {
			return code;
		}

		public static void setCode(String code) {
			EmailUtil.code = code;
		}

	
   

	public static Session getSession() {
    	//创建连接对象，链接到邮箱服务器
        Properties properties = new Properties();
        //设置邮件发送协议
        properties.put("mail.transport.protocol","smtp");
        //设置发送邮件服务器
        properties.put("mail.smtp.host",MAIL_HOST);
        //设置授权
        properties.setProperty("mail.smtp.auth",MAIL_SMTP_AUTH);
        //设置端口号
        properties.put("mail.smtp.port",MAIL_PORT);
        properties.put("mail.smtp.starttls.enable",true);
        //2.根据配置创建会话对象，用于和邮件服务器交互
        Session session = Session.getInstance(properties,new Authenticator() {
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication(MAIL_EMAIL, MAIL_PWD);
          }
        });
        session.setDebug(true);
        
		return session;
    }
    
	/*
	    * 发送注册验证码邮件
	 *
	 * @param to 邮件接收方
	 * @param code 邮件的验证码
	 * */
	public static boolean sendMail(String to){
		try {
			Session session = getSession();
	        //创建邮件对象
	        Message message = new MimeMessage(session);
	        //设置发件人
	        message.setFrom(new InternetAddress(MAIL_EMAIL));
	        //设置收件人
	        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
	        //设置抄送者
	        message.setRecipient(Message.RecipientType.CC , new InternetAddress(MAIL_EMAIL));
	        //设置邮件主题
	        message.setSubject("且行且记");
	        //设置邮件正文
	        message.setContent("<h3>【且行且记】</h3>您的验证码是"+randomCode()+
	                    ",您正在进行邮箱验证登录,5分钟内有效。(请勿向任何人提供您收到的验证码)",
	                    "text/html;charset=UTF-8");
	        //发送邮件
	        message.saveChanges();
	        Transport transport = session.getTransport("smtp");
	        transport.send(message);
	        transport.close();
//	            Transport.send(message);
	        if(true){
	        	System.out.printf("----emial发送成功----"+code);
	        	return true;	
	        }
			} catch (Exception e) {
	            e.printStackTrace();
	            System.out.printf("----emial发送失败----");
	            return false;
	        }
		return false;
		
	 }
	 /*
	    * 发送找回密码邮件
	 *
	 * @param to 邮件接收方
	 * @param code 邮件的验证码
	 * */
	 public static boolean findPasswordByMail(String to){
		 try {
			Session session = getSession();
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(MAIL_EMAIL));
	        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
	        message.setRecipient(Message.RecipientType.CC , new InternetAddress(MAIL_EMAIL));
	        message.setSubject("且行且记");
	        randomCode();
	        message.setContent("<h3>【且行且记】</h3>您的验证码是"+randomCode()+
                    ",您正在进行邮箱验证重置密码操作,5分钟内有效。(请勿向任何人提供您收到的验证码)",
            "text/html;charset=UTF-8");
	        message.saveChanges();
	        Transport transport = session.getTransport("smtp");
	        transport.send(message);
	        if(true){
		        System.out.printf("----emial发送成功----"+code);
		        return true;	
		     }
			} catch (Exception e) {
		    e.printStackTrace();
		    System.out.printf("----emial发送失败----");
		       return false;
		    }
		return false;
	    }

	 public static String randomCode() {
		 String c = "";
	     for (int i = 0; i < 4; i++) {
	        c += String.valueOf((int) (Math.random() * 10));
	     }
	     setCode(c);
	     return c;
	 }
}
