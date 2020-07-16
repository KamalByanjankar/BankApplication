package com.application.bankApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.bankApp.model.Transaction;
import com.application.bankApp.model.User;
import com.application.bankApp.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UserService userService;
	

	@Override
	public void saveTransaction(Transaction transaction) {
		transactionRepository.save(transaction);
		
	}

	@Override
	public List<Transaction> findTransactionList(String name) {
		User user = userService.findByUsername(name);
		List<Transaction> transaction = user.getAccount().getTransaction();		
		return transaction;
	}

}
