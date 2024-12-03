package com.facilitiesportal.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "facilities_portal_visitor_identity_cards")
public class VisitorIdentityCard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "physicalid", unique = true)
    private String physicalId;
	
	@Column(name = "isavailable", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isAvailable;
	
	@Column(name = "isactive", columnDefinition = "BOOLEAN DEFAULT TRUE")
	private boolean isActive;
	
    @Column(name = "createdat", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
	@JsonIgnore
    private LocalDateTime createdAt;

	@Column(name = "created_by")
	@JsonIgnore
    private String createdBy;
	
	@Column(name = "comments", length = 500)
	private String comments;
	
	public VisitorIdentityCard() {
		super();
	}

	public VisitorIdentityCard(String physicalId, boolean isAvailable, String createdBy,String comments) {
		super();
		this.physicalId = physicalId;
		this.isAvailable = isAvailable;
		this.createdBy = createdBy;
		this.comments=comments;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhysicalId() {
		return physicalId;
	}

	public void setPhysicalId(String physicalId) {
		this.physicalId = physicalId;
	}

	public boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
