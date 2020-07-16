package com.application.bankApp.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="account_details")
public class AccountDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="iban", nullable=false)
	private String iban;
	
	@Column(name="balance")
	private BigDecimal balance;
	
	@Column(name="card_number")
	private String cardNumber;
	
	@Column(name="card_pin")
	private String cardPin;

	public AccountDetails() {
		super();
	}

	public AccountDetails(String iban, BigDecimal balance, String cardNumber, String cardPin) {
		super();
		this.iban = iban;
		this.balance = balance;
		this.cardNumber = cardNumber;
		this.cardPin = cardPin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardPin() {
		return cardPin;
	}

	public void setCardPin(String cardPin) {
		this.cardPin = cardPin;
	}
	
	
}
