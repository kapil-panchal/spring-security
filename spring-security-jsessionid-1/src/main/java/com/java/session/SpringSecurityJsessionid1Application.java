package com.java.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity(debug = true)
public class SpringSecurityJsessionid1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJsessionid1Application.class, args);
	}

}
