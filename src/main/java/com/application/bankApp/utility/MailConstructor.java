package com.application.bankApp.utility;

import java.util.Locale;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.application.bankApp.model.User;

@Component
public class MailConstructor {

	public SimpleMailMessage constructRegistrationEmail(Locale locale, User user) {
		
		String message= "\nThank you for opening an account in our bank.";
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("Online banking");
		email.setText(message);
		email.setFrom("yourEmail@domain.com");
		
		return email;
	}

}
