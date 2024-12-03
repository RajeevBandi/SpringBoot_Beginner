package com.facilitiesportal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import com.facilitiesportal.dao.Emp_FacilitiesPortalRolesRepo;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain; 

/**
 * Configuration class for application security settings.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class AppSecurityConfig {
 	
	@Autowired
	private Emp_FacilitiesPortalRolesRepo emp_FacilitiesPortalRolesRepo;
 
	

    /**
     * Bean for JWT Authentication Converter.
     * @return JwtAuthenticationConverter instance
     */
	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(new UserAutheritiesExtractor(emp_FacilitiesPortalRolesRepo));
		return converter;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers(SWAGGER_URL_LIST).permitAll().anyRequest()
				.authenticated().and().oauth2ResourceServer().jwt() // Configure JWT authentication
				.jwtAuthenticationConverter(jwtAuthenticationConverter()); // Use the jwtAuthenticationConverter bean
		return http.build();
	}
	private static final String[] SWAGGER_URL_LIST = { "/swagger-ui/**", "/v3/api-docs/**", "/openapi/**",};
	
}