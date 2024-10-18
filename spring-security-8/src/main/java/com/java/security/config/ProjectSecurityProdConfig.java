package com.java.security.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.java.security.exceptions.CustomAccessDeniedHandler;
import com.java.security.exceptions.CustomBasicAuthenticationEntryPoint;
import com.java.security.filter.CsrfCookieFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@Profile(value = "prod")
public class ProjectSecurityProdConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		
		CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new
				CsrfTokenRequestAttributeHandler();
		
		http.sessionManagement(sessionConfig -> sessionConfig
				.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
			.cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {

			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Collections.singletonList("https://localhost:4200"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setMaxAge(3600L);
                return config;
			}
		}))
//			.sessionManagement(smc -> smc
//				.sessionFixation(sfc -> sfc.changeSessionId())
//				.invalidSessionUrl("/invalidSession")
//				.maximumSessions(3)
//				.maxSessionsPreventsLogin(true))
			.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure()) // Enforces HTTPS
			.csrf(csrfConfig -> csrfConfig
					.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
					.ignoringRequestMatchers("/contact","/register")
					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
			.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
				.requestMatchers("/myBalance").hasAnyAuthority("VIEWBALANCE", "VIEWACCOUNT")
				.requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
				.requestMatchers("/myCards").hasAuthority("VIEWCARDS")
				.requestMatchers("/user").authenticated()
				.requestMatchers("/contact", "/notices", "/error", "/register", "/invalidSession").permitAll())
			.formLogin(withDefaults())
			.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
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
