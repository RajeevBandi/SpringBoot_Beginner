package com.facilitiesportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "facilities_dropdowns")
public class FacilitiesDropdowns {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String value;

	@Column(name = "dropdowntype")
	private String dropDownType;

	@Column
	private String description;

	@Column(name = "isactive")
	private boolean isActive;

	public FacilitiesDropdowns() {
		super();
	}

	public FacilitiesDropdowns(String value, String dropDownType, String description, boolean isActive) {
		super();
		this.value = value;
		this.dropDownType = dropDownType;
		this.description = description;
		this.isActive = isActive;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDropDownType() {
		return dropDownType;
	}

	public void setDropDownType(String dropDownType) {
		this.dropDownType = dropDownType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

}
