package com.application.bankApp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="accounts")
public class Account {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="account_number", nullable=false)
	private String accountNumber;
	
	@OneToOne
	private AccountDetails accountDetails;

	@OneToOne(mappedBy="account", cascade = CascadeType.ALL)
	private User user;
	
	
	@OneToMany(mappedBy="account",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Transaction> transaction;
	
	public Account() {
		super();
	}

	public Account(String accountNumber, AccountDetails accountDetails, User user) {
		super();
		this.accountNumber = accountNumber;
		this.accountDetails = accountDetails;
		this.user = user;
	}

	public List<Transaction> getTransaction() {
		return transaction;
	}

	public void setTransaction(List<Transaction> transaction) {
		this.transaction = transaction;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	
	public AccountDetails getAccountDetails() {
		return accountDetails;
	}

	public void setAccountDetails(AccountDetails accountDetails) {
		this.accountDetails = accountDetails;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", accountNumber=" + accountNumber + ", accountDetails=" + accountDetails + "]";
	}
	
	
}
