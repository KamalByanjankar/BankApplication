package com.application.bankApp.service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.bankApp.model.Account;
import com.application.bankApp.model.AccountDetails;
import com.application.bankApp.model.Recipient;
import com.application.bankApp.model.Transaction;
import com.application.bankApp.model.User;
import com.application.bankApp.repository.AccountDetailsRepository;
import com.application.bankApp.repository.AccountRepository;
import com.application.bankApp.repository.RecipientRepository;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountDetailsRepository accountDetailsRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RecipientRepository recipientRepository;
	
	@Autowired
	private TransactionService transactionService;
	
	
	@Override
	public Account findByAccountNumber(String accountNumber) {
		return accountRepository.findByAccountNumber(accountNumber);
	}

	@Override
	public Account createCheckingAccount(String socialSecurityNumber) {
		Account account = new Account();
		AccountDetails accountDetails = new AccountDetails();
		account.setAccountNumber(generateAccountNumber(socialSecurityNumber, "Checking"));
		accountDetails.setIban(generateIban(account.getAccountNumber()));
		accountDetails.setBalance(new BigDecimal(0.0));
		accountDetails.setCardNumber(generateCardNumber());
		accountDetails.setCardPin(generateCardPin());
		
		accountDetailsRepository.save(accountDetails);
		
		account.setAccountDetails(accountDetails);
		return accountRepository.save(account);
	}

	@Override
	public Account createSavingAccount(String socialSecurityNumber) {
		Account account = new Account();
		AccountDetails accountDetails = new AccountDetails();
		
		account.setAccountNumber(generateAccountNumber(socialSecurityNumber, "Saving"));
		accountDetails.setIban(generateIban(account.getAccountNumber()));
		accountDetails.setBalance(new BigDecimal(0.0));
		accountDetails.setCardNumber("Not Available");
		accountDetails.setCardPin("N/A");
		
		accountDetailsRepository.save(accountDetails);
		
		account.setAccountDetails(accountDetails);
		return accountRepository.save(account);
	}
	
	private String generateAccountNumber(String ssn, String accountType) {
		int socialNumber = Integer.parseInt(ssn) % 1000;
		if(accountType.equals("Checking")) {
			String accountNumber = 2 + Double.toString(Math.random()).substring(2,5) + socialNumber;
			return accountNumber;
		}
		
		else if(accountType.equals("Saving")) {
			String accountNumber = 1 + Double.toString(Math.random()).substring(2,5) + socialNumber;
			return accountNumber;
		}
		
		else {
			return null;
		}
	}

	private String generateIban(String accountNumber) {
		String randNumber = Double.toString(Math.random()).substring(2,13);
		String ibanNumber = "DE" + randNumber + accountNumber;
		return ibanNumber;
	}


	private String generateCardNumber() {
		String randCardNumber = Double.toString(Math.random()).substring(2,13);
		String cardNumber = 2 + randCardNumber;
		return cardNumber;
	}
	
	private String generateCardPin() {
		String pinNumber = Double.toString(Math.random()).substring(2,6);
		return pinNumber;
	}

	@Override
	public void deposit(BigDecimal amount, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		AccountDetails accountDetails = user.getAccount().getAccountDetails();
		accountDetails.setBalance(accountDetails.getBalance().add(amount));
		accountDetailsRepository.save(accountDetails);
		
		Date date = new Date();
		Transaction transaction = new Transaction(date, "Deposit", "Deposit", "Completed", amount, accountDetails.getBalance(), user.getAccount());
		transactionService.saveTransaction(transaction);
	}

	@Override
	public void withdraw(BigDecimal amount, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		if((user.getAccount().getAccountDetails().getBalance()).doubleValue() > amount.doubleValue()) {
			AccountDetails accountDetails = user.getAccount().getAccountDetails();
			accountDetails.setBalance(accountDetails.getBalance().subtract(amount));
			accountDetailsRepository.save(accountDetails);
			
			Date date = new Date();
			Transaction transaction = new Transaction(date, "Withdraw", "Deposit", "Completed", amount, accountDetails.getBalance(), user.getAccount());
			transactionService.saveTransaction(transaction);
		}
		
	}
	
	@Override
	public Recipient saveRecipient(Recipient recipient) {
		return recipientRepository.save(recipient);
	}

	@Override
	public void transfer(Recipient recipient, BigDecimal amount,Principal principal) {
		User user = userService.findByUsername(principal.getName());
		if((user.getAccount().getAccountDetails().getBalance().doubleValue()) > amount.doubleValue()) {
			AccountDetails accountDetails = user.getAccount().getAccountDetails();
			accountDetails.setBalance(accountDetails.getBalance().subtract(amount));
			accountDetailsRepository.save(accountDetails);
			
			Date date = new Date();
			Transaction transaction = new Transaction(date, "Transfer", "Deposit", "Completed", amount, accountDetails.getBalance(), user.getAccount());
			transactionService.saveTransaction(transaction);
		}
	}

	@Override
	public Recipient findByRecipientName(String recipientName) {
		return recipientRepository.findByRecipientName(recipientName);
	}


}
