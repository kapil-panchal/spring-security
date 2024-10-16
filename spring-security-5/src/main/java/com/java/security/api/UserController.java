package com.java.security.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.java.security.model.Customer;
import com.java.security.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final CustomerRepository customerRepository;
	private final PasswordEncoder passwordEncoder;

	@PostMapping(path = "/register")
	public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
		try {
			String hashPwd = passwordEncoder.encode(customer.getPwd());
			customer.setPwd(hashPwd);
			Customer saveCustomer = customerRepository.save(customer);
			
			if(saveCustomer.getId() > 0) {
				return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Given user details are successfully registered!");
			} else {
				return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body("User registration failed!");
			}
		} catch(Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An exception occured: " + e.getMessage());
		}
	}
}
