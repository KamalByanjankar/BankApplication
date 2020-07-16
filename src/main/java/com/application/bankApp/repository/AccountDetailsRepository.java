package com.application.bankApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.bankApp.model.AccountDetails;

public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Long>{

}
