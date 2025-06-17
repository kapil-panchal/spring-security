package com.java.security.exceptions;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// Populate Dynamic Response
		LocalDateTime currentTimeStamp = LocalDateTime.now();
		String message = (accessDeniedException != null && accessDeniedException.getMessage() != null) ? 
				accessDeniedException.getMessage():
					"Authorization Failed";
		String path = request.getRequestURI();
		
		response.setHeader("easybank-denied-reason", "Authorization Failed");
		
		// 401 Exception
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setContentType("application/json;charset=UTF-8");
		
		// Construct JSON Response
		String jsonResponse = 
				String.format("{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
						currentTimeStamp, 
						HttpStatus.FORBIDDEN.value(), 
						HttpStatus.FORBIDDEN.getReasonPhrase(),
						message,
						path);
		response.getWriter().write(jsonResponse);
	}
}
