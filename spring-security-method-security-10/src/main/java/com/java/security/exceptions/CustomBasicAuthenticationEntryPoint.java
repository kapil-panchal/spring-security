package com.java.security.exceptions;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		// Populate Dynamic Response
		LocalDateTime currentTimeStamp = LocalDateTime.now();
		String message = (authException != null && authException.getMessage() != null) ? 
				authException.getMessage():
					"UnAuthorized";
		String path = request.getRequestURI();
		
		response.setHeader("easybank-error-reason", "Authentication Failed");
		
		// 401 Exception
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json;charset=UTF-8");
		
		// Construct JSON Response
		String jsonResponse = 
				String.format("{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
						currentTimeStamp, 
						HttpStatus.UNAUTHORIZED.value(), 
						HttpStatus.UNAUTHORIZED.getReasonPhrase(),
						message,
						path);
		response.getWriter().write(jsonResponse);
	}
}
