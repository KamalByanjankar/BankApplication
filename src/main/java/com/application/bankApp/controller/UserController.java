package com.application.bankApp.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.application.bankApp.model.User;
import com.application.bankApp.model.UserProfile;
import com.application.bankApp.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	
	
	@RequestMapping(value="/accountInformation", method=RequestMethod.GET)
	public String accountInformation(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		
		model.addAttribute("user", user);
		return "accountInformation";
	}

	@RequestMapping(value="/userInformation", method=RequestMethod.GET)
	public String userInformation(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		UserProfile userProfile = new UserProfile();
		 
		model.addAttribute("user", user);
		model.addAttribute("userProfile", userProfile);
		return "userInformation";
	}
	
	@RequestMapping(value="/userInformation/save", method=RequestMethod.POST)
	public String userInformation(@ModelAttribute("userProfile") UserProfile userProfile, 
			Principal principal, Model model, @ModelAttribute("newPassword") String newPassword) {
		
		User user = userService.findByUsername(principal.getName());
		
		if(newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")) {
			user.setPassword(bCryptPasswordEncoder.encode(newPassword));
		}
		
		
		userProfile.setUser(user);
		
		model.addAttribute("userProfile", userProfile);
		
		userService.save(user);
		userService.saveUserInformation(userProfile);
		
		
		return "redirect:/user/userInformation";
	}
	
	@RequestMapping(value = "/userInformation/edit", method = RequestMethod.GET)
    public String userProfileEdit(@RequestParam(value = "address") String address, Model model, Principal principal) {

        UserProfile userProfile = userService.findByAddress(address);
        
        User user = userService.findByUsername(principal.getName());
        
        model.addAttribute("user", user);
        model.addAttribute("userProfile", userProfile);

        return "userInformation";
    }
	
}
