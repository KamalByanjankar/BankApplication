package com.application.bankApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.bankApp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	List<User> findByFirstNameOrLastNameAllIgnoreCase(String firstname, String lastName);
	User findByUsername(String username);
	User findByEmail(String email);
}
