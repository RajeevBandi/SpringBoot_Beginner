package com.facilitiesportal.model;

import java.time.LocalDate;

public class SearchOrSortCriteria {

	private String searchByName;
	private Long searchByPurposeId;
	private LocalDate searchByStartDate;
	private LocalDate searchByEndDate;

	private String searchByMaterialType;
	private String searchByMaterialStatus;
	private Long searchByMaterialItemTypeId;

	private String sortBy;
	private String orderBy;

	public SearchOrSortCriteria() {
		super();
	}

	public SearchOrSortCriteria(String searchByName, Long searchByPurposeId, LocalDate searchByStartDate,
			LocalDate searchByEndDate, String sortBy, String orderBy, String searchByMaterialType,
			String searchByMaterialStatus, Long searchByMaterialItemTypeId) {
		super();
		this.searchByName = searchByName;
		this.searchByPurposeId = searchByPurposeId;
		this.searchByStartDate = searchByStartDate;
		this.searchByEndDate = searchByEndDate;
		this.searchByMaterialType = searchByMaterialType;
		this.searchByMaterialStatus = searchByMaterialStatus;
		this.searchByMaterialItemTypeId = searchByMaterialItemTypeId;
		this.sortBy = sortBy;
		this.orderBy = orderBy;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSearchByName() {
		return searchByName;
	}

	public void setSearchByName(String searchByName) {
		this.searchByName = searchByName;
	}

	public Long getSearchByPurposeId() {
		return searchByPurposeId;
	}

	public void setSearchByPurposeId(Long searchByPurposeId) {
		this.searchByPurposeId = searchByPurposeId;
	}

	public LocalDate getSearchByStartDate() {
		return searchByStartDate;
	}

	public void setSearchByStartDate(LocalDate searchByStartDate) {
		this.searchByStartDate = searchByStartDate;
	}

	public LocalDate getSearchByEndDate() {
		return searchByEndDate;
	}

	public void setSearchByEndDate(LocalDate searchByEndDate) {
		this.searchByEndDate = searchByEndDate;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSearchByMaterialType() {
		return searchByMaterialType;
	}

	public void setSearchByMaterialType(String searchByMaterialType) {
		this.searchByMaterialType = searchByMaterialType;
	}

	public String getSearchByMaterialStatus() {
		return searchByMaterialStatus;
	}

	public void setSearchByMaterialStatus(String searchByMaterialStatus) {
		this.searchByMaterialStatus = searchByMaterialStatus;
	}

	public Long getSearchByMaterialItemTypeId() {
		return searchByMaterialItemTypeId;
	}

	public void setSearchByMaterialItemTypeId(Long searchByMaterialItemTypeId) {
		this.searchByMaterialItemTypeId = searchByMaterialItemTypeId;
	}

}
