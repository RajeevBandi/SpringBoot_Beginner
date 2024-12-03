package com.facilitiesportal.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.facilitiesportal.dao.Emp_FacilitiesPortalRolesRepo;
import com.facilitiesportal.dao.EmployeeRepo;
import com.facilitiesportal.dao.FacilitiesPortalRolesRepo;
import com.facilitiesportal.exceptions.ResourceNotFoundException;
import com.facilitiesportal.model.Emp_FacilitiesPortalRoles;
import com.facilitiesportal.model.Employee;
import com.facilitiesportal.model.FacilitiesPortalRoles;
import com.facilitiesportal.services.IEmp_FacilitiesPortalRolesService;

/**
 * Service class for managing Employee Facilities Roles.
 */
@Service
public class Emp_FacilitiesPortalRolesService implements IEmp_FacilitiesPortalRolesService {

	public Emp_FacilitiesPortalRolesService() {
		super();
	}

	@Autowired
	private Emp_FacilitiesPortalRolesRepo emp_FacilitiesPortalRolesRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private FacilitiesPortalRolesRepo facilitiesPortalRolesRepo;

	/**
	 * Retrieves a list of all Employee Facilities Roles with pagination.
	 *
	 * @param pageNum  The page number.
	 * @param pageSize The size of each page.
	 * @return A list of Employee Facilities Roles.
	 */
	@Override
	public List<Emp_FacilitiesPortalRoles> getAllEmpFacilitiesPortalRoles(int pageNum, int pageSize) {
		List<Emp_FacilitiesPortalRoles> emp_FacilitiesPortalRoles = null;
		try {
			Pageable pageRequest = PageRequest.of(pageNum, pageSize);
			Page<Emp_FacilitiesPortalRoles> pageEmpFacilitiesPortalRoles = emp_FacilitiesPortalRolesRepo
					.findAll(pageRequest);
			emp_FacilitiesPortalRoles = pageEmpFacilitiesPortalRoles.getContent();
		} catch (Exception e) {
			System.out.println(e);
		}
		return emp_FacilitiesPortalRoles;
	}

	/**
	 * Adds a new Employee Facilities Role.
	 *
	 * @param newEmp_FacilitiesPortalRole The new Employee Facilities Role to add.
	 * @return The added Employee Facilities Role.
	 */
	@Override
	public Emp_FacilitiesPortalRoles addEmp_FacilitiesPortalRole(
			Emp_FacilitiesPortalRoles newEmp_FacilitiesPortalRole) {

		Emp_FacilitiesPortalRoles savedRecordEmp_FacilitiesPortalRole = null;

		try {

			int empId = newEmp_FacilitiesPortalRole.getEmployee().getId();
			Long roleId = newEmp_FacilitiesPortalRole.getFacilitiesPortalRole().getId();

			Employee existingEmployee = employeeRepo.findById(empId);

			FacilitiesPortalRoles existingRole = facilitiesPortalRolesRepo.findById(roleId).orElseThrow(
					() -> new ResourceNotFoundException("FacilitiesPortalRole not found for this id :: " + roleId));

			newEmp_FacilitiesPortalRole.setCreatedAt(LocalDateTime.now());
			newEmp_FacilitiesPortalRole.setEmployee(existingEmployee);
			newEmp_FacilitiesPortalRole.setFacilitiesPortalRole(existingRole);

			savedRecordEmp_FacilitiesPortalRole = emp_FacilitiesPortalRolesRepo.save(newEmp_FacilitiesPortalRole);

		} catch (Exception e) {
			System.out.println(e);
		}
		return savedRecordEmp_FacilitiesPortalRole;
	}

}
