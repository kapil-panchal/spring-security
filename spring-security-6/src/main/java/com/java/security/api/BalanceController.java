package com.java.security.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

	@GetMapping(path = "/myBalance")
	public String getBalanceDetails() {
		return "Here are the balance details from the DB";
	}
}
