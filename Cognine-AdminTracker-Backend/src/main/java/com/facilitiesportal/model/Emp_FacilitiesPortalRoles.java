package com.facilitiesportal.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "emp_facilities_portal_roles")
public class Emp_FacilitiesPortalRoles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "FPRole_id", referencedColumnName = "id", nullable = false)
	private FacilitiesPortalRoles facilitiesPortalRole;

	@Column(name = "createdat", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "isactive", nullable = false)
	private boolean isActive;

	public Emp_FacilitiesPortalRoles() {
		super();
	}

	public Emp_FacilitiesPortalRoles(Long id, Employee employee, FacilitiesPortalRoles role, LocalDateTime createdAt,
			boolean isActive) {
		super();
		this.id = id;
		this.employee = employee;
		this.facilitiesPortalRole = role;
		this.createdAt = createdAt;
		this.isActive = isActive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public FacilitiesPortalRoles getFacilitiesPortalRole() {
		return facilitiesPortalRole;
	}

	public void setFacilitiesPortalRole(FacilitiesPortalRoles facilitiesPortalRole) {
		this.facilitiesPortalRole = facilitiesPortalRole;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

}
