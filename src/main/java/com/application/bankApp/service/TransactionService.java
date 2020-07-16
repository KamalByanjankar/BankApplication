package com.application.bankApp.service;

import java.util.List;

import com.application.bankApp.model.Transaction;

public interface TransactionService {

	void saveTransaction(Transaction transaction);

	List<Transaction> findTransactionList(String name);

}
