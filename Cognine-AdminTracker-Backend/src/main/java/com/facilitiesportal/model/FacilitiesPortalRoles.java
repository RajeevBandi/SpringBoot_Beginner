package com.facilitiesportal.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "facilities_portal_roles")
public class FacilitiesPortalRoles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String role;
	@Column(nullable = true)
	private String description;
	@Column(nullable = true)
	private LocalDateTime createdat;
	@Column(nullable = true)
	private Boolean isactive;
	@Column(nullable = true)
	private String createdby;

	public FacilitiesPortalRoles() {
		super();
	}

	public FacilitiesPortalRoles(String role, String description, LocalDateTime createdat, Boolean isactive,
			String createdby) {
		super();
		this.role = role;
		this.description = description;
		this.createdat = createdat;
		this.isactive = isactive;
		this.createdby = createdby;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreatedat() {
		return createdat;
	}

	public void setCreatedAt(LocalDateTime createdat) {
		this.createdat = createdat;
	}

	public Boolean getIsActive() {
		return isactive;
	}

	public void setIsActive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getCreatedBy() {
		return createdby;
	}

	public void setCreatedBy(String createdby) {
		this.createdby = createdby;
	}

}
