package com.java.security.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import com.java.security.exceptions.CustomBasicAuthenticationEntryPoint;

@Configuration
@Profile(value = "prod")
public class ProjectSecurityProdConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.sessionManagement(smc -> smc.invalidSessionUrl("/invalidSession").maximumSessions(1).maxSessionsPreventsLogin(true))
			.requiresChannel(rcc -> rcc.anyRequest().requiresSecure())
			.csrf(csrfConfig -> csrfConfig.disable())
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/myAccount", "/myBalance", "/myCards", "/myLoans").authenticated()
				.requestMatchers("/contact", "/notices", "/error", "/register", "/invalidSession").permitAll())
			.formLogin(withDefaults())
			.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
		return http.build();
	}
	
//	@Bean
//	UserDetailsService userDetailsService(DataSource dataSource){
//		return new JdbcUserDetailsManager(dataSource);
//	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	CompromisedPasswordChecker compromisedPasswordChecker() {
		return new HaveIBeenPwnedRestApiPasswordChecker();
	}
}
