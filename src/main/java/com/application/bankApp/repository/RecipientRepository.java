package com.application.bankApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.bankApp.model.Recipient;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long>{


	Recipient findByRecipientName(String recipientName);

}
