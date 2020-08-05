package com.application.bankApp.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.application.bankApp.model.User;
import com.application.bankApp.model.UserProfile;
import com.application.bankApp.security.UserRole;
import com.application.bankApp.security.VerificationToken;

public interface UserService {

	List<User> findAll();

	List<User> byName(String name);

	User createUser(User user, Set<UserRole> role);

	User findByUsername(String username);

	UserProfile saveUserInformation(UserProfile userProfile);

	UserProfile findByAddress(String userAddress);

	Page<User> findPaginated(Pageable pageable);

	User checkUsernameExists(String username);

	User checkEmailExists(String email);

	void save(User user);

	User findByEmail(String userEmail);

	void createResetPasswordToken(String token, User user);

	VerificationToken getVerificationToken(String token);

}
