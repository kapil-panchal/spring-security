package com.java.session.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http.authorizeHttpRequests((request) -> 	request.requestMatchers("/api/v1/hello").authenticated());
			http.formLogin(Customizer.withDefaults());
			http.httpBasic(Customizer.withDefaults());
			return http.build();
		}
}
