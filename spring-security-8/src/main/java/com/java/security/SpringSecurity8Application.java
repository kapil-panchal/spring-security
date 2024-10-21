package com.java.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity(debug=true)
public class SpringSecurity8Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurity8Application.class, args);
	}

}
