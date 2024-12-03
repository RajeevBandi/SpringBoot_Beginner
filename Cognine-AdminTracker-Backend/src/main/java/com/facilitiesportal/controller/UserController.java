package com.facilitiesportal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facilitiesportal.services.IEmployeeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;




/**
 * Controller class for managing user roles.
 */
@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
	
	@Autowired
	IEmployeeService employeeService;

	/**
	 * Get roles of the authenticated user.
	 * 
	 * @param authentication The authentication object containing user details
	 * @return List of roles and name associated with the authenticated user
	 */
	@GetMapping("/getDetails")
    @PreAuthorize("permitAll()")
	public Map<String, Object> getUserRole(Authentication authentication) {
		// Get the authorities of the authenticated user
		
		Map<String, Object>userDetails = new HashMap<>();
		
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();

		// Extract authority roles as strings
		List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		
		Jwt jwt = (Jwt) authentication.getPrincipal();
        String name= jwt.getClaims().get("name").toString();         
		
		userDetails.put("roles", roles);
		userDetails.put("name", name);
		
		return userDetails;
	}
	
}
