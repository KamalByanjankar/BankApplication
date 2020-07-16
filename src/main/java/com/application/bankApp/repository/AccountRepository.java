package com.application.bankApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.bankApp.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

	Account findByAccountNumber(String accountNumber);
}
