package com.java.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.authorizeHttpRequests((request) -> 
			request
				.requestMatchers("/secure")
				.authenticated()
				.anyRequest()
				.permitAll())
		.formLogin(Customizer.withDefaults())
		.oauth2Login(Customizer.withDefaults());
		
		return httpSecurity.build();
	}
	
//	@Bean
//	ClientRegistrationRepository clientRegistrationRepository() {
//		
//		ClientRegistration github = githubClientRegistration();
////		ClientRegistration facebook = facebookClientRegistration();
//		
//		return new InMemoryClientRegistrationRepository(github);
//	}
//	
//	private ClientRegistration githubClientRegistration() {
//		return CommonOAuth2Provider
//			.GITHUB
//			.getBuilder("github")
//			.clientId("Ov23liQSNJaUGzqc53QG")
//			.clientSecret("6a5d466489c847e48962de64f28de7b707286c0b")
//			.build();
//	}
	
//	private ClientRegistration facebookClientRegistration() {
//		return CommonOAuth2Provider
//			.FACEBOOK
//			.getBuilder("facebook")
//			.clientId("")
//			.clientSecret("")
//			.build();
//	}
}
