package com.facilitiesportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facilitiesportal.model.FacilitiesPortalRoles;
import com.facilitiesportal.services.IFacilitiesPortalRolesService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Controller class for managing Facilities Portal roles.
 */
@RestController
@RequestMapping("/FPRoles")
@SecurityRequirement(name = "bearerAuth")
//@CrossOrigin
public class FacilitiesPortalRoleseController {
	@Autowired
	IFacilitiesPortalRolesService facilitiesPortalRolesService;

	/**
	 * Get all records from Facilities Portal roles.
	 * 
	 * @param pageNum  Page number for pagination (optional, default: 0)
	 * @param pageSize Page size for pagination (optional, default: 10)
	 * @return List of FacilitiesPortalRoles
	 */
	@GetMapping("/getAll")
	public List<FacilitiesPortalRoles> getAllFacilitiesPortalRoles(
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

		return facilitiesPortalRolesService.getAllFacilitiesPortalRoles(pageNum, pageSize);
	}

	/**
	 * Add a new Facilities Portal role.
	 * 
	 * @param facilitiesPortalRoles The Facilities Portal role to add
	 * @return ResponseEntity containing the added FacilitiesPortalRoles object
	 */
	@PostMapping("/add")
	public ResponseEntity<FacilitiesPortalRoles> addFacilitiesPortalRole(
			@RequestBody FacilitiesPortalRoles facilitiesPortalRoles) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(facilitiesPortalRolesService.addFacilitiesPortalRole(facilitiesPortalRoles));
	}

	/**
	 * Update an existing Facilities Portal role.
	 * 
	 * @param updateFacilitiesPortalRole The Facilities Portal role with updated
	 *                                   information
	 * @return ResponseEntity containing the updated FacilitiesPortalRoles object
	 */
	@PutMapping("/update")
	public ResponseEntity<FacilitiesPortalRoles> updateFacilitiesPortalRole(
			@RequestBody FacilitiesPortalRoles updateFacilitiesPortalRole) {
		Long roleId = updateFacilitiesPortalRole.getId();
		return ResponseEntity
				.ok(facilitiesPortalRolesService.updateFacilitiesPortalRole(roleId, updateFacilitiesPortalRole));
	}

}
