package com.application.bankApp.controller;

import java.math.BigDecimal;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.application.bankApp.service.AccountService;

@Controller
@RequestMapping(value="/home/accounts")
public class AccountController {
	
	@Autowired
	private AccountService accountService;

	@RequestMapping(value="/deposit", method=RequestMethod.GET)
	public String deposit() {
		return "deposit";
	}
	
	@RequestMapping(value="/deposit", method=RequestMethod.POST)
	public String deposit(Principal principal, @ModelAttribute("amount") BigDecimal amount) {
		accountService.deposit(amount, principal);
		return "redirect:/home";
	}
	
	@RequestMapping(value="/withdraw", method=RequestMethod.GET)
	public String withdraw() {
		return "withdraw";
	}
	
	@RequestMapping(value="/withdraw", method=RequestMethod.POST)
	public String withdraw(Principal principal, @ModelAttribute("amount") BigDecimal amount) {
		accountService.withdraw(amount, principal);
		return "redirect:/home";
	}
	
}
