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
    	//�������Ӷ������ӵ����������
        Properties properties = new Properties();
        //�����ʼ�����Э��
        properties.put("mail.transport.protocol","smtp");
        //���÷����ʼ�������
        properties.put("mail.smtp.host",MAIL_HOST);
        //������Ȩ
        properties.setProperty("mail.smtp.auth",MAIL_SMTP_AUTH);
        //���ö˿ں�
        properties.put("mail.smtp.port",MAIL_PORT);
        properties.put("mail.smtp.starttls.enable",true);
        //2.�������ô����Ự�������ں��ʼ�����������
        Session session = Session.getInstance(properties,new Authenticator() {
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication(MAIL_EMAIL, MAIL_PWD);
          }
        });
        session.setDebug(true);
        
		return session;
    }
    
	/*
	    * ����ע����֤���ʼ�
	 *
	 * @param to �ʼ����շ�
	 * @param code �ʼ�����֤��
	 * */
	public static boolean sendMail(String to){
		try {
			Session session = getSession();
	        //�����ʼ�����
	        Message message = new MimeMessage(session);
	        //���÷�����
	        message.setFrom(new InternetAddress(MAIL_EMAIL));
	        //�����ռ���
	        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
	        //���ó�����
	        message.setRecipient(Message.RecipientType.CC , new InternetAddress(MAIL_EMAIL));
	        //�����ʼ�����
	        message.setSubject("�����Ҽ�");
	        //�����ʼ�����
	        message.setContent("<h3>�������Ҽǡ�</h3>������֤����"+randomCode()+
	                    ",�����ڽ���������֤��¼,5��������Ч��(�������κ����ṩ���յ�����֤��)",
	                    "text/html;charset=UTF-8");
	        //�����ʼ�
	        message.saveChanges();
	        Transport transport = session.getTransport("smtp");
	        transport.send(message);
	        transport.close();
//	            Transport.send(message);
	        if(true){
	        	System.out.printf("----emial���ͳɹ�----"+code);
	        	return true;	
	        }
			} catch (Exception e) {
	            e.printStackTrace();
	            System.out.printf("----emial����ʧ��----");
	            return false;
	        }
		return false;
		
	 }
	 /*
	    * �����һ������ʼ�
	 *
	 * @param to �ʼ����շ�
	 * @param code �ʼ�����֤��
	 * */
	 public static boolean findPasswordByMail(String to){
		 try {
			Session session = getSession();
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(MAIL_EMAIL));
	        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
	        message.setRecipient(Message.RecipientType.CC , new InternetAddress(MAIL_EMAIL));
	        message.setSubject("�����Ҽ�");
	        randomCode();
	        message.setContent("<h3>�������Ҽǡ�</h3>������֤����"+randomCode()+
                    ",�����ڽ���������֤�����������,5��������Ч��(�������κ����ṩ���յ�����֤��)",
            "text/html;charset=UTF-8");
	        message.saveChanges();
	        Transport transport = session.getTransport("smtp");
	        transport.send(message);
	        if(true){
		        System.out.printf("----emial���ͳɹ�----"+code);
		        return true;	
		     }
			} catch (Exception e) {
		    e.printStackTrace();
		    System.out.printf("----emial����ʧ��----");
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
