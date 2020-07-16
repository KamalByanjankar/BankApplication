package com.application.bankApp.config;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//import com.application.bankApp.service.UserSecurityService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private static final String SALT = "salt";
	
//	@Autowired
//	private UserSecurityService userSecurityService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
	}
	
	private static final String[] PUBLIC_MATCHERS = {
			"/css/**",
			"/js/**",
			"/images/**",
			"/",
			"/signin",
			"/signup",
			"/home",
	};
	
	@Override
	public void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
			.antMatchers(PUBLIC_MATCHERS)
			.permitAll()
			.anyRequest()
			.authenticated();
		
		http
			.csrf()
			.disable()
			.cors()
			.disable()
			.formLogin()
			.failureUrl("/signin?error")
			.defaultSuccessUrl("/home")
			.loginPage("/signin")
			.permitAll()
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/?logout")
			.deleteCookies("remember-me")
			.permitAll()
			.and()
			.rememberMe();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication().withUser("jones").password(passwordEncoder().encode("jones123")).roles("USER")
		.and()
        .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
        .and()
        .withUser("james").password(passwordEncoder().encode("james123")).roles("ADMIN");
        
//		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}
}
