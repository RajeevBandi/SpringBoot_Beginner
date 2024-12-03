package com.facilitiesportal.services;

import java.util.List;

import com.facilitiesportal.model.Roles;

/**
 * Service interface for managing roles.
 */
public interface IRolesService {
	/**
	 * Retrieves a list of all roles.
	 * 
	 * @return A list of roles.
	 */
	public List<Roles> getAllRoles();

	/**
	 * Adds a new role.
	 * 
	 * @param newRole The new role to add.
	 * @return The added role.
	 */
	public Roles addRole(Roles newRole);
}
