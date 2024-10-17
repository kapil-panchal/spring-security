package com.java.school.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String displayLoginPage(@RequestParam(value = "error", required = false) 
    		String error,
    		@RequestParam(value = "logout", required = false) 
    		String logout,
    		Model model) {
        
    	String errorMessge = null;
    	System.err.println(error);
    	
    	if(error != null) {
    		errorMessge = "Username or Password is Incorrect!";
    	}
    	
    	if(logout != null) {
    		errorMessge = "You have been successfully logged out";
    	}
    	
    	model.addAttribute("errorMessge" + errorMessge);
    	
    	return "login.html";
    }
}