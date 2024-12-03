package com.facilitiesportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facilitiesportal.model.Emp_FacilitiesPortalRoles;
import com.facilitiesportal.services.IEmp_FacilitiesPortalRolesService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Controller class for managing employee roles.
 */
@RestController
@RequestMapping("emp_roles")
@SecurityRequirement(name = "bearerAuth")
public class Emp_FacilitiesPortalRolesController {

	@Autowired
	private IEmp_FacilitiesPortalRolesService emp_FacilitiesPortalRolesService;
	
	/**
     * Get all employee roles.
     * 
     * @param pageNum Page number for pagination (optional, default: 0)
     * @param pageSize Page size for pagination (optional, default: 10)
     * @return List of EmpFacilitiesPortalRoles
     */
	@GetMapping("/getAll")
	public List<Emp_FacilitiesPortalRoles>getAllEmp_FacilitiesPortalRoles(
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize){
		return emp_FacilitiesPortalRolesService.getAllEmpFacilitiesPortalRoles(pageNum, pageSize);
	}
	
	/**
     * Add a new employee role.
     * 
     * @param newEmpFacilitiesPortalRoles The new employee role to add
     * @return The added EmpFacilitiesPortalRoles object
     */
	@PostMapping("/add")
	public Emp_FacilitiesPortalRoles addEmp_FacilitiesPortalRole(@RequestBody Emp_FacilitiesPortalRoles newEmp_FacilitiesPortalRoles) {
		return emp_FacilitiesPortalRolesService.addEmp_FacilitiesPortalRole(newEmp_FacilitiesPortalRoles);
	}
}
