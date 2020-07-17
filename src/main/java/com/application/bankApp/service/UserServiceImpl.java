package com.application.bankApp.service;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.application.bankApp.model.AccountType;
import com.application.bankApp.model.User;
import com.application.bankApp.model.UserProfile;
import com.application.bankApp.repository.RoleRepository;
import com.application.bankApp.repository.UserProfileRepository;
import com.application.bankApp.repository.UserRepository;
import com.application.bankApp.security.UserRole;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	

	@Override
	public List<User> byName(String name) {
		return userRepository.findByFirstNameOrLastNameAllIgnoreCase(name, name);
	}

	@Override
	public User createUser(User user, Set<UserRole> role) {
		String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		
		if(user.getAccountType().equals(AccountType.Checking)) {
			user.setAccount(accountService.createCheckingAccount(user.getSocialSecurityNumber()));
			
			for(UserRole ur : role) {
				roleRepository.save(ur.getRole());
			}
			
			user.getUserRoles().addAll(role);
			return userRepository.save(user);
		}
		
		else if(user.getAccountType().equals(AccountType.Saving)) {
			user.setAccount(accountService.createSavingAccount(user.getSocialSecurityNumber()));
			
			for(UserRole ur : role) {
				roleRepository.save(ur.getRole());
			}
			
			user.getUserRoles().addAll(role);
			return userRepository.save(user);
		}
		else {
			return null;
		}
		
	}

	@Override
	public UserProfile saveUserInformation(UserProfile userProfile) {
		return userProfileRepository.save(userProfile);
	}

	@Override
	public List<UserProfile> findAllUserProfileList(Principal principal) {
		String username = principal.getName();
		List<UserProfile> userProfileList = userProfileRepository.findAll().stream().filter(userProfile -> username.equals(userProfile.getUser().getUsername())).collect(Collectors.toList());
		return userProfileList;
	}

	@Override
	public UserProfile findByAddress(String userAddress) {
		return userProfileRepository.findByAddress(userAddress);
	}

	@Override
	public Page<User> findPaginated(Pageable pageable) {
		List<User> userList = userRepository.findAll();
		
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		
		List<User> list;
		
		if(userList.size() < startItem) {
			list = Collections.emptyList();
		}
		
		else {
			int toIndex = Math.min(startItem + pageSize, userList.size());
			list = userList.subList(startItem, toIndex);
		}
		
		Page<User> userPage = new PageImpl<User>(list, PageRequest.of(currentPage, pageSize), userList.size());
		
		return userPage;
	}

	@Override
	public User checkUsernameExists(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User checkEmailExists(String email) {
		return userRepository.findByEmail(email);
	}


	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public User findByEmail(String userEmail) {
		return userRepository.findByEmail(userEmail);
	}

}
