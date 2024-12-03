package com.facilitiesportal.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facilitiesportal.dao.RolesRepo;
import com.facilitiesportal.model.Roles;
import com.facilitiesportal.services.IRolesService;

/**
 * Service class for managing roles.
 */
@Service
public class RolesService implements IRolesService {

	public RolesService() {
		super();
	}

	@Autowired
	private RolesRepo rolesRepo;

	/**
	 * Retrieves a list of all roles.
	 * 
	 * @return A list of roles.
	 */
	@Override
	public List<Roles> getAllRoles() {
		List<Roles> roles = null;
		try {
			roles = rolesRepo.findAll();
		} catch (Exception e) {
			System.out.println(e);
		}
		return roles;
	}

	/**
	 * Adds a new role.
	 * 
	 * @param newRole The new role to add.
	 * @return The added role.
	 */
	@Override
	public Roles addRole(Roles newRole) {
		Roles savedRole = null;
		try {
			savedRole = rolesRepo.save(newRole);
		} catch (Exception e) {
			System.out.println(e);
		}
		return savedRole;
	}

}
