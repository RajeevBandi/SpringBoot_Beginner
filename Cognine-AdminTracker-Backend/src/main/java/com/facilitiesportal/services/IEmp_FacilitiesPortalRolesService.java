package com.facilitiesportal.services;

import java.util.List;

import com.facilitiesportal.model.Emp_FacilitiesPortalRoles;

/**
 * Service interface for managing Employee Facilities Roles.
 */
public interface IEmp_FacilitiesPortalRolesService {
	/**
	 * Retrieves a list of all Employee Facilities Roles with pagination.
	 * 
	 * @param pageNum  The page number.
	 * @param pageSize The size of each page.
	 * @return A list of Employee Facilities Roles.
	 */
	public List<Emp_FacilitiesPortalRoles> getAllEmpFacilitiesPortalRoles(int pageNum, int pageSize);

	/**
	 * Adds a new Employee Facilities Role.
	 * 
	 * @param newEmpFacilitiesPortalRole The new Employee Facilities Role to add.
	 * @return The added Employee Facilities Role.
	 */
	public Emp_FacilitiesPortalRoles addEmp_FacilitiesPortalRole(Emp_FacilitiesPortalRoles newEmp_FacilitiesPortalRole);
}
