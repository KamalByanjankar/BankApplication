package com.application.bankApp.service;

import java.security.Principal;
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

	List<UserProfile> findAllUserProfileList(Principal principal);

	UserProfile findByAddress(String userAddress);

	Page<User> findPaginated(Pageable pageable);

	User checkUsernameExists(String username);

	User checkEmailExists(String email);

	void createTokenForUser(User user, String token);

	VerificationToken getVerificationToken(String token);

	void save(User user);

}
