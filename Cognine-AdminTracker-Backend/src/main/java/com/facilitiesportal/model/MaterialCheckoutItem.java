package com.facilitiesportal.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "facilities_portal_material_checkout_items")
public class MaterialCheckoutItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "materialcheckoutgatepassid", referencedColumnName = "id")
	@JsonIgnore
	private MaterialCheckoutGatePass materialCheckoutGatePass;

	@Column(name = "itemid", length = 75)
	private String itemId;

	@Column(name = "description", length = 75)
	private String description;

	@Column(name = "actualreturndatetime")
	private LocalDateTime actualReturnDateTime;
	
	@Column(name="totalquantity")
	private Integer totalQuantity=1;
	
	@Column(name = "totalreturnedquantity")
	private Integer totalReturnedQuantity=0;

	@Column(name = "createdby", columnDefinition = "TEXT")
	@JsonIgnore
	private String createdBy;

	@Column(name = "updatedby", columnDefinition = "TEXT")
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

	@Transient
	private boolean isSearchResult;
	
	@Transient
	private List<ItemQuantityReturnLog> itemQuantityReturnLogs;

	public MaterialCheckoutItem() {
		super();
	}

	public MaterialCheckoutItem(MaterialCheckoutGatePass materialCheckoutGatePass, String itemId, String description,
			LocalDateTime actualReturnDateTime, int totalQuantity, int totalReturnedQuantity, String createdBy,
			String updatedBy, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isActive,
			boolean isSearchResult, List<ItemQuantityReturnLog> itemQuantityReturnLogs) {
		super();
		this.materialCheckoutGatePass = materialCheckoutGatePass;
		this.itemId = itemId;
		this.description = description;
		this.actualReturnDateTime = actualReturnDateTime;
		this.totalQuantity = totalQuantity;
		this.totalReturnedQuantity = totalReturnedQuantity;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isActive = isActive;
		this.isSearchResult = isSearchResult;
		this.itemQuantityReturnLogs = itemQuantityReturnLogs;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MaterialCheckoutGatePass getMaterialCheckoutGatePass() {
		return materialCheckoutGatePass;
	}

	public void setMaterialCheckoutGatePass(MaterialCheckoutGatePass materialCheckoutGatePass) {
		this.materialCheckoutGatePass = materialCheckoutGatePass;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getActualReturnDateTime() {
		return actualReturnDateTime;
	}

	public void setActualReturnDateTime(LocalDateTime actualReturnDateTime) {
		this.actualReturnDateTime = actualReturnDateTime;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Integer getTotalReturnedQuantity() {
		return totalReturnedQuantity;
	}

	public void setTotalReturnedQuantity(Integer totalReturnedQuantity) {
		this.totalReturnedQuantity = totalReturnedQuantity;
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

	public boolean getIsSearchResult() {
		return isSearchResult;
	}

	public void setIsSearchResult(boolean isSearchResult) {
		this.isSearchResult = isSearchResult;
	}

	public List<ItemQuantityReturnLog> getItemQuantityReturnLogs() {
		return itemQuantityReturnLogs;
	}

	public void setItemQuantityReturnLogs(List<ItemQuantityReturnLog> itemQuantityReturnLogs) {
		this.itemQuantityReturnLogs = itemQuantityReturnLogs;
	}

}
