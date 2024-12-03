package com.facilitiesportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facilitiesportal.model.Roles;
import com.facilitiesportal.services.IRolesService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Controller class for managing roles.
 */
@RestController
@RequestMapping("/role")
@SecurityRequirement(name = "bearerAuth")
public class RolesController {

	@Autowired
	IRolesService rolesServiceObj;

	/**
	 * Get all roles.
	 * 
	 * @return List of roles
	 */
	@GetMapping("/getAll")
	public List<Roles> getAllRoles() {
		return rolesServiceObj.getAllRoles();
	}

	/**
	 * Add a new role.
	 * 
	 * @param newRole The new role to add
	 * @return The added Roles object
	 */
	@PostMapping("/add")
	public Roles addRole(@RequestBody Roles newRole) {
		return rolesServiceObj.addRole(newRole);
	}

}
