package com.java.security.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

	@GetMapping(path = "/contact")
	public String saveContactEnquiryDetails() {
		return "Inquiry details are saved to the DB";	
	}
}
