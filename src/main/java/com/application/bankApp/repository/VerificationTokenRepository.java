package com.application.bankApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.bankApp.security.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{

	VerificationToken findByToken(String token);

}
