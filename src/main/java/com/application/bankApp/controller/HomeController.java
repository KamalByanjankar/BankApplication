package com.application.bankApp.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.application.bankApp.model.Account;
import com.application.bankApp.model.AccountDetails;
import com.application.bankApp.model.AccountType;
import com.application.bankApp.model.User;
import com.application.bankApp.repository.RoleRepository;
import com.application.bankApp.security.UserRole;
import com.application.bankApp.service.UserService;
import com.application.bankApp.utility.MailConstructor;
import com.application.bankApp.utility.SecurityUtility;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailConstructor mailConstructor;
	
	@Autowired
	private SecurityUtility securityUtility;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	@GetMapping(value="/")
	public String index() {
		return "redirect:/signin";
	}
	
	@GetMapping(value="/signin")
	public String signin(Model model) {
		model.addAttribute("classActiveLogin", true);
		return "signin";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signup(@ModelAttribute("user") User user, Model model,  HttpServletRequest request) throws Exception {
		
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
			
			SimpleMailMessage email = mailConstructor.constructRegistrationEmail(request.getLocale(), user);
			
			mailSender.send(email);
			model.addAttribute("emailSent", true);
			model.addAttribute("signupSuccess", true);
			
			return "redirect:/signup";
		}
	}
	
	@RequestMapping(value="/signup")
	public String confirmUserAccount(Model model) {
		User user = new User();		
		model.addAttribute("user", user);
		
		return "signup";
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
	
	@RequestMapping(value="/forgetPassword")
	public String forgetPassword(Model model, @ModelAttribute("email") String userEmail) {
		User user = userService.findByEmail(userEmail);
		if(user == null) {
			model.addAttribute("emailNotExists", true);
			return "forgetPassword";
		}
		
		
		
		String password = securityUtility.randomPassword();
		String encryptedPassword = bCryptPasswordEncoder.encode(password);
		user.setPassword(encryptedPassword);
		
		userService.save(user);
		
		System.out.println(password);
		
		String message = "Your temporary password is: " + password;
		
		SimpleMailMessage email = new SimpleMailMessage();
		
		email.setTo(user.getEmail());
		email.setSubject("Reset Password");
		email.setText(message);
		email.setFrom("yourEmail@domain.com");
		
		mailSender.send(email);
		
		model.addAttribute("resetEmailSent", true);
		
		return "redirect:/signin";
	}
}
