package com.java.school.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		log.info("Login successful for user: {}", authentication.getName());
		log.info("Login successful for username: {}", userDetails.getUsername());
		log.info("Login successful for password: {}", userDetails.getPassword());
		log.info("Login successful for getAutorities: {}", userDetails.getAuthorities());
		log.info("Login successful for isAccountNonExpired: {}", userDetails.isAccountNonExpired());
		log.info("Login successful for isAccountNonLocked: {}", userDetails.isAccountNonLocked());
		log.info("Login successful for isCredentialsNonExpired: {}", userDetails.isCredentialsNonExpired());
		log.info("Login successful for isEnabled: {}", userDetails.isEnabled());
		response.sendRedirect("/dashboard");
	}
}
