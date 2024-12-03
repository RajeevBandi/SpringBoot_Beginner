package com.facilitiesportal.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facilitiesportal.model.FacilitiesPortalRoles;

/**
 * Repository interface for managing Facilities Portal Roles entities
 */
public interface FacilitiesPortalRolesRepo extends JpaRepository<FacilitiesPortalRoles, Long>{

}
