package com.application.bankApp.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.application.bankApp.model.Account;
import com.application.bankApp.model.AccountDetails;
import com.application.bankApp.model.AccountType;
import com.application.bankApp.model.User;
import com.application.bankApp.repository.RoleRepository;
import com.application.bankApp.security.UserRole;
import com.application.bankApp.security.VerificationToken;
import com.application.bankApp.service.UserSecurityService;
import com.application.bankApp.service.UserService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	
	
	
	@GetMapping(value="/")
	public String index() {
		return "redirect:/signin";
	}
	
	@GetMapping(value="/signin")
	public String signin() {
		return "signin";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signup(Model model, @ModelAttribute("user") User user) {
		
		model.addAttribute("classActiveNewAccount", true);
		
		if(userService.checkUsernameExists(user.getUsername()) != null){
			model.addAttribute("usernameExists", true);
			return "signup";
		}
		
		else if(userService.checkEmailExists(user.getEmail()) != null) {
			model.addAttribute("emailExists", true);
			return "signup";
		}
		
		else {
			Set<UserRole> userRoles = new HashSet<>();
			userRoles.add(new UserRole(user, roleRepository.findByName("ADMIN")));
			userService.createUser(user, userRoles);
		
			String token = UUID.randomUUID().toString();
			userService.createTokenForUser(user, token);
			
			String appUrl = "http://localhost:8080/signup?token="+token;
			String message= "\nClick the link to verify your email address";
			
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(user.getEmail());
			email.setSubject("Online banking-Verify email address");
			email.setText(appUrl + message);
			email.setFrom("kamalbyanjankar@gmail.com");
			
			mailSender.send(email);
			model.addAttribute("emailSent", true);
			
			return "signup";
		}
	}
	
	@RequestMapping(value="/signup")
	public String confirmUserAccount(Locale locale, Model model) {
		User user = new User();
//		VerificationToken passToken = userService.getVerificationToken(token); 
//		
//		if(passToken == null) {
//			String message = "Invalid Token";
//			model.addAttribute("message", message);
//			return "redirect:/badRequest";
//		}
//		
//		User user = passToken.getUser();
//		String username = user.getUsername();
//		
//		UserDetails userDetails = userSecurityService.loadUserByUsername(username);
//		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		
//		user.setEnabled(true);
//		userService.save(user);
		
		model.addAttribute("user", user);
		
		return "home";
	}
	
	
	@GetMapping(value="/home")
	public String success(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		
		Account account = user.getAccount();
		AccountDetails accountDetails = user.getAccount().getAccountDetails();
		AccountType accountType = user.getAccountType();
		
		model.addAttribute("user", user);
		model.addAttribute("accountType", accountType);
		model.addAttribute("account", account);
		model.addAttribute("accountDetails", accountDetails);
		return "home";
	}
}
