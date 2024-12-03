package com.facilitiesportal.model;

import java.time.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "facilities_portal_material_checkout_gate_pass")
public class MaterialCheckoutGatePass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "gatepassid", length = 75)
	private String gatePassId;
	
	@ManyToOne
	@JoinColumn(name = "itemtypeid", referencedColumnName = "id")
	@JsonIgnoreProperties({ "description", "isActive" })
	private FacilitiesDropdowns itemTypeId;

	@Column(name = "status", length = 75)
	private String status;

	@Column(name = "type", length = 20)
	private String type;

	@ManyToOne
	@JoinColumn(name = "sentby_empid", referencedColumnName = "id")
	@JsonIgnoreProperties({ "email", "dateOfJoining", "jobTitle", "department", "reportsTo", "createdAt", "updatedAt",
			"newReportingHead", "active" })
	private Employee sentByEmp;

	@Column(name = "sentto", length = 500)
	private String sentTo;
	
	@Column(name = "senttoaddress", columnDefinition = "TEXT")
	private String sentToAddress;

	@Column(name = "senton")
	private LocalDateTime sentOn;

	@Column(name = "expectedreturndate")
	private LocalDate expectedReturnDate;

	@Column(name = "comments", length = 500)
	private String comments;

	@Column(name = "createdby", columnDefinition = "TEXT")
	@JsonIgnore
	private String createdBy;

	@Column(name = "updatedby",columnDefinition = "TEXT")
	@JsonIgnore
	private String updatedBy;

	@Column(name = "createdat", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
	@JsonIgnore
	private LocalDateTime createdAt;

	@Column(name = "updatedat")
	@JsonIgnore
	private LocalDateTime updatedAt;

	@Column(name = "isactive", columnDefinition = "BOOLEAN DEFAULT TRUE")
	@JsonIgnore
	private Boolean isActive;

	public MaterialCheckoutGatePass() {
		super();
	}

	public MaterialCheckoutGatePass(String gatePassId, FacilitiesDropdowns itemTypeId, String status, String type,
			Employee sentByEmp, String sentTo, String sentToAddress, LocalDateTime sentOn, LocalDate expectedReturnDate,
			String comments, String createdBy, String updatedBy, LocalDateTime createdAt, LocalDateTime updatedAt,
			Boolean isActive) {
		super();
		this.gatePassId = gatePassId;
		this.itemTypeId = itemTypeId;
		this.status = status;
		this.type = type;
		this.sentByEmp = sentByEmp;
		this.sentTo = sentTo;
		this.sentToAddress = sentToAddress;
		this.sentOn = sentOn;
		this.expectedReturnDate = expectedReturnDate;
		this.comments = comments;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isActive = isActive;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGatePassId() {
		return gatePassId;
	}

	public void setGatePassId(String gatePassId) {
		this.gatePassId = gatePassId;
	}

	public FacilitiesDropdowns getItemTypeId() {
		return itemTypeId;
	}

	public void setItemTypeId(FacilitiesDropdowns itemTypeId) {
		this.itemTypeId = itemTypeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Employee getSentByEmp() {
		return sentByEmp;
	}

	public void setSentByEmp(Employee sentByEmp) {
		this.sentByEmp = sentByEmp;
	}

	public String getSentTo() {
		return sentTo;
	}

	public void setSentTo(String sentTo) {
		this.sentTo = sentTo;
	}

	public String getSentToAddress() {
		return sentToAddress;
	}

	public void setSentToAddress(String sentToAddress) {
		this.sentToAddress = sentToAddress;
	}

	public LocalDateTime getSentOn() {
		return sentOn;
	}

	public void setSentOn(LocalDateTime sentOn) {
		this.sentOn = sentOn;
	}

	public LocalDate getExpectedReturnDate() {
		return expectedReturnDate;
	}

	public void setExpectedReturnDate(LocalDate expectedReturnDate) {
		this.expectedReturnDate = expectedReturnDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
