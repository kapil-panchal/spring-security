package com.java.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http.authorizeHttpRequests((request) -> 	request.anyRequest().authenticated()	);
			http.formLogin(Customizer.withDefaults());
			http.httpBasic((hbc) -> hbc.disable());
			
			return http.build();
		}
}
