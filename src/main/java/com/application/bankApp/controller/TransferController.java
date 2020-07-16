package com.application.bankApp.controller;

import java.math.BigDecimal;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.application.bankApp.model.Account;
import com.application.bankApp.model.AccountDetails;
import com.application.bankApp.model.Recipient;
import com.application.bankApp.model.User;
import com.application.bankApp.service.AccountService;
import com.application.bankApp.service.UserService;

@Controller
@RequestMapping(value="/home/accounts")
public class TransferController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	

	@RequestMapping(value="/recipient", method=RequestMethod.GET)
	public String recipient(Model model) {
		Recipient recipient = new Recipient();
		model.addAttribute("recipient", recipient);
		return "recipient";
	}
	
	@RequestMapping(value="/recipient/save", method=RequestMethod.POST)
	public String saveRecipient(Principal principal, @ModelAttribute("recipient") Recipient recipient) {
		User user = userService.findByUsername(principal.getName());
		user.setRecipient(recipient);
		accountService.saveRecipient(recipient);
		return "redirect:/home/accounts/transfer";
	}
	
	@RequestMapping(value="/transfer", method=RequestMethod.GET)
	public String transfer(Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		Account account = user.getAccount();
		AccountDetails accountDetails = user.getAccount().getAccountDetails();		
		Recipient recipient = user.getRecipient();
		
		model.addAttribute("user", user);
		model.addAttribute("account", account);
		model.addAttribute("accountDetails", accountDetails);
		model.addAttribute("recipient", recipient);
		return "transfer";
	}

	@RequestMapping(value="/transfer", method=RequestMethod.POST)
	public String transfer(@ModelAttribute("amount") BigDecimal amount, 
			Principal principal, Account account) {
		User user = userService.findByUsername(principal.getName());
		Recipient recipient = user.getRecipient();
		accountService.transfer(recipient, amount, principal);
		
		return "redirect:/home";
	}
}
