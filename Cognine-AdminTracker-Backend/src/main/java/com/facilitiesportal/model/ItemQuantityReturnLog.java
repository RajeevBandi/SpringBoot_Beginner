package com.facilitiesportal.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "facilities_portal_item_quantity_return_log")
public class ItemQuantityReturnLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "itemid", referencedColumnName="id")
	@JsonIgnore
	private MaterialCheckoutItem item;
	
	@Column(name = "returndatetime")
	private LocalDateTime returnDateTime;
	
	@Column(name = "returnedquantity")
	private int returnedQuantity;
	
	@Column(name = "createdat", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
	@JsonIgnore
	private LocalDateTime createdAt;
	
	@Column(name = "createdby", columnDefinition = "TEXT")
	@JsonIgnore
	private String createdBy;
	
	@Transient
	private long itemId;

	public ItemQuantityReturnLog() {
		super();
	}

	public ItemQuantityReturnLog(MaterialCheckoutItem item, LocalDateTime returnDateTime, int returnedQuantity,
			LocalDateTime createdAt, String createdBy) {
		super();
		this.item = item;
		this.returnDateTime = returnDateTime;
		this.returnedQuantity = returnedQuantity;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MaterialCheckoutItem getItem() {
		return item;
	}

	public void setItem(MaterialCheckoutItem item) {
		this.item = item;
	}

	public LocalDateTime getReturnDateTime() {
		return returnDateTime;
	}

	public void setReturnDateTime(LocalDateTime returnDateTime) {
		this.returnDateTime = returnDateTime;
	}

	public int getReturnedQuantity() {
		return returnedQuantity;
	}

	public void setReturnedQuantity(int returnedQuantity) {
		this.returnedQuantity = returnedQuantity;
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

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
		
}
