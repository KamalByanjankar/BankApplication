package com.application.bankApp.utility;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.application.bankApp.model.User;

@Component
public class MailConstructor {
	
	@Autowired
	private JavaMailSender mailSender;

	public MimeMessage constructRegistrationEmail(Locale locale, User user, String token) {

		String text1 = "Please click the link to verify your account:";
		String appUrl = "http://localhost:8080/signin?token="+token;
		String text = "Thank you for creating an account in our bank.";
		
		String content ="<html>"+text1+"</html>" + 
						"<html><a href='"+appUrl+"'>"+appUrl+"</a></html>" + 
						"<html><br/></html>" +
						"<html><br/></html>" +
						"<html>"+text+"</html>";
		
		
		MimeMessage message = mailSender.createMimeMessage(); 
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setSubject("New Registration");
            helper.setText(content, true);
            helper.setFrom("yourEmail@gmail.com");
            helper.setTo(user.getEmail());
        } catch (MessagingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }        
		return message;
	}

}
