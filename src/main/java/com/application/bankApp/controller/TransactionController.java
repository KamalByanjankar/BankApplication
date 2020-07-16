package com.application.bankApp.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.application.bankApp.model.Transaction;
import com.application.bankApp.service.TransactionService;

@Controller
@RequestMapping(value="/home/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	

	@GetMapping("/all")
	public String allTransaction(Model model, Principal principal) {
		List<Transaction> transactionList = transactionService.findTransactionList(principal.getName());
		
		model.addAttribute("transactionList", transactionList);
		return "transaction";
	}
	
}
