package mrhi.adventure.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import mrhi.adventure.model.vo.AccountVO;

public class EMailSender {
	private static final int max = 99999;
	private static final int min = 10000;
	private static final String host     = "smtp.naver.com";
	private static final String user   = "sexy_eod";
	private static final String password  = "ghgusdl23";
	private Map<String, Integer> authMap = new HashMap<>();

	
	public EMailSender() {}

	public Map<String, Integer> getAuthMap() {
		return authMap;
	}

	public void setAuthMap(Map<String, Integer> authMap) {
		this.authMap = authMap;
	}

	public void sendAuthEMail(AccountVO accountVO) {
		Random random = new Random();
		int randonNumber = random.nextInt(max - min + 1) + min;
		
		//수정해야됨
		if(accountVO.getAcc_id()!=null)
			this.authMap.put(accountVO.getAcc_id(), randonNumber);
		else
			this.authMap.put(accountVO.getAcc_email(), randonNumber);
		
		System.out.println(accountVO.getAcc_email() + " : " + randonNumber);
		
		// Get the session object
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		// Compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(accountVO.getAcc_email()));

			// Subject
			message.setSubject("인증번호");

			// Text
			message.setText("인증 번호는 : " + randonNumber);

			// send the message
			Transport.send(message);
			System.out.println("message sent successfully...");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
