package com.application.bankApp.utility;

import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtility {

	@Bean
	public String randomPassword() {
		
		String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder salt = new StringBuilder();
		Random random = new Random();
		
		while(salt.length() < 18) {
			int index = (int) (random.nextFloat()*SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		
		return salt.toString();
	}

}
