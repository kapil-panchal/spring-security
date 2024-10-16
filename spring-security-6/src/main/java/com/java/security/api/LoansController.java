package com.java.security.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoansController {

	@GetMapping(path = "/myLoans")
	public String getMyLoans() {
		return"Here are the loans details from the DB";
	}
}
