package com.java.security.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@Configuration
public class ProjectSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//		http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/myAccount", "/myBalance", "/myCards", "/myLoans").authenticated()
				.requestMatchers("/contact", "/notices", "/error").permitAll());
		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
//		http.formLogin(flc -> flc.disable());
//		http.httpBasic(hbc -> hbc.disable());
		return http.build();
	}
	
	@Bean
	UserDetailsService userDetailsService(){
		UserDetails user = User
				.withUsername("user")
				.password("{noop}UserUser@1234")
				.authorities("read")
				.build();
		
		UserDetails admin = User
				.withUsername("admin")
				.password("{bcrypt}$2a$12$56/aRxKgGwepwU4vYnFS7uDZBtL4OmmmjF8WGfYx1.9UDI1cWcyCK")
				.authorities("admin")
				.build();
		
		return new InMemoryUserDetailsManager(user, admin);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	CompromisedPasswordChecker compromisedPasswordChecker() {
		return new HaveIBeenPwnedRestApiPasswordChecker();
	}
}
