package com.application.bankApp.service;

import java.math.BigDecimal;
import java.security.Principal;
import com.application.bankApp.model.Account;
import com.application.bankApp.model.Recipient;

public interface AccountService {
	
	Account findByAccountNumber(String accountNumber);

	Account createCheckingAccount(String socialSecurityNumber);
	
	Account createSavingAccount(String socialSecurityNumber);

	void deposit(BigDecimal amount, Principal principal);

	void withdraw(BigDecimal amount, Principal principal);

	void transfer(Recipient recipient, BigDecimal amount, Principal principal);

	Recipient saveRecipient(Recipient recipient);

	Recipient findByRecipientName(String recipientName);

}
