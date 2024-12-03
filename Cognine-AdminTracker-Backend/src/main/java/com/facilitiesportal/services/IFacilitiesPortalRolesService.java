package com.facilitiesportal.services;

import java.util.List;

import com.facilitiesportal.model.FacilitiesPortalRoles;

/**
 * Service interface for managing Facilities Portal Roles.
 */
public interface IFacilitiesPortalRolesService {
	/**
	 * Retrieves a list of all Facilities Portal Roles with pagination.
	 * 
	 * @param pageNum  The page number.
	 * @param pageSize The size of each page.
	 * @return A list of Facilities Portal Roles.
	 */
	public List<FacilitiesPortalRoles> getAllFacilitiesPortalRoles(int pageNum, int pageSize);

	/**
	 * Adds a new Facilities Portal Role.
	 * 
	 * @param newFacilitiesPortalRole The new Facilities Portal Role to add.
	 * @return The added Facilities Portal Role.
	 */
	public FacilitiesPortalRoles addFacilitiesPortalRole(FacilitiesPortalRoles newFacilitiesPortalRole);

	/**
	 * Updates an existing Facilities Portal Role.
	 * 
	 * @param roleId  The ID of the role to update.
	 * @param updatedFacilitiesPortalRole The updated Facilities Portal Role object.
	 * @return The updated Facilities Portal Role record.
	 */
	public FacilitiesPortalRoles updateFacilitiesPortalRole(Long roleId, FacilitiesPortalRoles updatedFacilitiesPortalRole);
}
