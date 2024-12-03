package com.facilitiesportal.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.facilitiesportal.dao.FacilitiesPortalRolesRepo;
import com.facilitiesportal.exceptions.ResourceNotFoundException;
import com.facilitiesportal.model.FacilitiesPortalRoles;
import com.facilitiesportal.services.IFacilitiesPortalRolesService;

/**
 * Service class for managing Facilities Portal Roles.
 */
@Service
public class FacilitiesPortalRolesService implements IFacilitiesPortalRolesService {

	@Autowired
	private FacilitiesPortalRolesRepo facilitiesPortalRolesRepo;

	public FacilitiesPortalRolesService() {
		super();
	}

	/**
	 * Retrieves a list of all Facilities Portal Roles with pagination.
	 * 
	 * @param pageNum  The page number.
	 * @param pageSize The size of each page.
	 * @return A list of Facilities Portal Roles.
	 */
	@Override
	public List<FacilitiesPortalRoles> getAllFacilitiesPortalRoles(int pageNum, int pageSize) {

		List<FacilitiesPortalRoles> facilitiesPortalRoles = null;
		try {
			Pageable pageRequest = PageRequest.of(pageNum, pageSize);
			Page<FacilitiesPortalRoles> pageFacilitiesPortalRoles = this.facilitiesPortalRolesRepo.findAll(pageRequest);
			facilitiesPortalRoles = pageFacilitiesPortalRoles.getContent();
		} catch (Exception e) {
			System.out.println(e);
		}
		return facilitiesPortalRoles;
	}

	/**
	 * Adds a new Facilities Portal Role.
	 * 
	 * @param newFacilitiesPortalRole The new Facilities Portal Role to add.
	 * @return The added Facilities Portal Role.
	 */
	@Override
	public FacilitiesPortalRoles addFacilitiesPortalRole(FacilitiesPortalRoles newFacilitiesPortalRole) {
		FacilitiesPortalRoles savedFacilitiesPortalRole = null;
		try {
			newFacilitiesPortalRole.setCreatedAt(LocalDateTime.now());
			savedFacilitiesPortalRole = facilitiesPortalRolesRepo.save(newFacilitiesPortalRole);
		} catch (Exception e) {
			System.out.println(e);
		}
		return savedFacilitiesPortalRole;
	}

	/**
	 * Updates an existing Facilities Portal Role.
	 * 
	 * @param roleId                      The ID of the role to update.
	 * @param updatedFacilitiesPortalRole The updated Facilities Portal Role object.
	 * @return The updated Facilities Portal Role record.
	 */
	@Override
	public FacilitiesPortalRoles updateFacilitiesPortalRole(Long roleId,
			FacilitiesPortalRoles updatedFacilitiesPortalRole) {
		FacilitiesPortalRoles existingFacilitiesPortalRole = null;
		try {

			existingFacilitiesPortalRole = facilitiesPortalRolesRepo.findById(roleId)
					.orElseThrow(() -> new ResourceNotFoundException("Role not found with id : " + roleId));

			updatedFacilitiesPortalRole.setId(existingFacilitiesPortalRole.getId());
			existingFacilitiesPortalRole = facilitiesPortalRolesRepo.save(updatedFacilitiesPortalRole);

		} catch (Exception e) {
			System.out.println(e);
		}
		return existingFacilitiesPortalRole;
	}

}
