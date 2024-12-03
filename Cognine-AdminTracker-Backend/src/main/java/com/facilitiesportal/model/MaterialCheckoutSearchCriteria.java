package com.facilitiesportal.model;

import java.util.List;

public class MaterialCheckoutSearchCriteria {

	private String gatePassId;
	private List<String> empNameList;
	private String sentTo;
	private String sentToAddress;
	private List<String> statusList;
	private String type;
	private Long itemTypeId;

	private String sentOnDateMin;
	private String sentOnDateMax;

	private String expectedReturnDateMin;
	private String expectedReturnDateMax;

	private String gatePassSortBy;
	private String gatePassOrderBy;

	private String itemId;
	private String description;

	private String actualReturnDateMin;
	private String actualReturnDateMax;
	
	private String gatePassGlobalSearch;
	private String itemGlobalSearch;

	public MaterialCheckoutSearchCriteria() {
		super();
	}

	public MaterialCheckoutSearchCriteria(String gatePassId, List<String> empNameList, String sentTo, String sentToAddress,
			 List<String> statusList, String type, String sentOnDateMin, String sentOnDateMax,
			String expectedReturnDateMin, String expectedReturnDateMax, String gatePassSortBy,
			String gatePassOrderBy, String itemId, String description, String actualReturnDateMin,
			String actualReturnDateMax,  Long itemTypeId, String gatePassGlobalSearch, String itemGlobalSearch) {
		super();
		this.gatePassId = gatePassId;
		this.empNameList = empNameList;
		this.sentTo = sentTo;
		this.sentToAddress = sentToAddress;
		this.statusList = statusList;
		this.type = type;
		this.sentOnDateMin = sentOnDateMin;
		this.sentOnDateMax = sentOnDateMax;
		this.expectedReturnDateMin = expectedReturnDateMin;
		this.expectedReturnDateMax = expectedReturnDateMax;
		this.gatePassSortBy = gatePassSortBy;
		this.gatePassOrderBy = gatePassOrderBy;
		this.itemId = itemId;
		this.description = description;
		this.actualReturnDateMin = actualReturnDateMin;
		this.actualReturnDateMax = actualReturnDateMax;
		this.itemTypeId = itemTypeId;
		this.gatePassGlobalSearch=gatePassGlobalSearch;
		this.itemGlobalSearch=itemGlobalSearch;
	}

	public String getGatePassId() {
		return gatePassId;
	}

	public void setGatePassId(String gatePassId) {
		this.gatePassId = gatePassId;
	}

	public List<String> getEmpNameList() {
		return empNameList;
	}

	public void setEmpNameList(List<String> empNameList) {
		this.empNameList = empNameList;
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

	public  List<String> getStatusList() {
		return statusList;
	}

	public void setStatusList( List<String> statusList) {
		this.statusList = statusList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getItemTypeId() {
		return itemTypeId;
	}

	public void setItemTypeId(Long itemTypeId) {
		this.itemTypeId = itemTypeId;
	}

	public String getSentOnDateMin() {
		return sentOnDateMin;
	}

	public void setSentOnDateMin(String sentOnDateMin) {
		this.sentOnDateMin = sentOnDateMin;
	}

	public String getSentOnDateMax() {
		return sentOnDateMax;
	}

	public void setSentOnDateMax(String sentOnDateMax) {
		this.sentOnDateMax = sentOnDateMax;
	}

	public String getExpectedReturnDateMin() {
		return expectedReturnDateMin;
	}

	public void setExpectedReturnDateMin(String expectedReturnDateMin) {
		this.expectedReturnDateMin = expectedReturnDateMin;
	}

	public String getExpectedReturnDateMax() {
		return expectedReturnDateMax;
	}

	public void setExpectedReturnDateMax(String expectedReturnDateMax) {
		this.expectedReturnDateMax = expectedReturnDateMax;
	}

	public String getGatePassSortBy() {
		return gatePassSortBy;
	}

	public void setGatePassSortBy(String gatePassSortBy) {
		this.gatePassSortBy = gatePassSortBy;
	}

	public String getGatePassOrderBy() {
		return gatePassOrderBy;
	}

	public void setGatePassOrderBy(String gatePassOrderBy) {
		this.gatePassOrderBy = gatePassOrderBy;
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

	public String getActualReturnDateMin() {
		return actualReturnDateMin;
	}

	public void setActualReturnDateMin(String actualReturnDateMin) {
		this.actualReturnDateMin = actualReturnDateMin;
	}

	public String getActualReturnDateMax() {
		return actualReturnDateMax;
	}

	public void setActualReturnDateMax(String actualReturnDateMax) {
		this.actualReturnDateMax = actualReturnDateMax;
	}

	public String getGatePassGlobalSearch() {
		return gatePassGlobalSearch;
	}

	public void setGatePassGlobalSearch(String gatePassGlobalSearch) {
		this.gatePassGlobalSearch = gatePassGlobalSearch;
	}

	public String getItemGlobalSearch() {
		return itemGlobalSearch;
	}

	public void setItemGlobalSearch(String itemGlobalSearch) {
		this.itemGlobalSearch = itemGlobalSearch;
	}

}
