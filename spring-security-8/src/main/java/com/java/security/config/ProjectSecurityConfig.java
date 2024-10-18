package com.java.security.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.java.security.exceptions.CustomAccessDeniedHandler;
import com.java.security.exceptions.CustomBasicAuthenticationEntryPoint;

@Configuration
@Profile(value = "!prod")
public class ProjectSecurityConfig {
	
	@Autowired
	private IpAddressFilter ipAddressFilter;

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.sessionManagement(smc -> smc
				.sessionFixation(sfc -> sfc.changeSessionId())
				.invalidSessionUrl("/invalidSession")
				.maximumSessions(3)
				.maxSessionsPreventsLogin(true))
			.addFilterBefore(ipAddressFilter, BasicAuthenticationFilter.class)
			.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())
			.csrf(csrfConfig -> csrfConfig.disable())
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/myAccount", "/myBalance", "/myCards", "/myLoans", "/user").authenticated()
				.requestMatchers("/contact", "/notices", "/error", "/register", "/invalidSession").permitAll())
			.formLogin(withDefaults())
			.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
//		http.exceptionHandling(ehc -> ehc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
		http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
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
