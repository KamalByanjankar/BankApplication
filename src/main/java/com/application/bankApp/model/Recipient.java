package com.application.bankApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="recipient")
public class Recipient {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="recipient_name", nullable=false)
	private String recipientName;
	
	@Column(name="account_number", nullable=false)
	private String accountNumber;
	
	@Column(name="description", nullable=false)
	private String description;
	
	public Recipient() {
		super();
	}

	public Recipient(String recipientName, String accountNumber, String description) {
		super();
		this.recipientName = recipientName;
		this.accountNumber = accountNumber;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
