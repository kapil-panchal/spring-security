package com.java.security.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

	@GetMapping(path = "/welcome")
	public String sayWelcome() {
		return "Welcome to Spring Application with Security";
	}
}
