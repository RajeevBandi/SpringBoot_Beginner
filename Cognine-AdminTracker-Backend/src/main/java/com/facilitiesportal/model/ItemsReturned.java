package com.facilitiesportal.model;

import java.time.LocalDateTime;
import java.util.List;

public class ItemsReturned {
    private List<ItemQuantityReturnLog> itemQuantityReturnLogs;
	private LocalDateTime returnDateTime;
	
	public ItemsReturned() {
		super();
	}

	public ItemsReturned(List<ItemQuantityReturnLog> itemQuantityReturnLogs, LocalDateTime returnDateTime) {
		super();
		this.itemQuantityReturnLogs = itemQuantityReturnLogs;
		this.returnDateTime = returnDateTime;
	}

	public List<ItemQuantityReturnLog> getItemQuantityReturnLogs() {
		return itemQuantityReturnLogs;
	}

	public void setItemQuantityReturnLogs(List<ItemQuantityReturnLog> itemQuantityReturnLogs) {
		this.itemQuantityReturnLogs = itemQuantityReturnLogs;
	}

	public LocalDateTime getReturnDateTime() {
		return returnDateTime;
	}

	public void setReturnDateTime(LocalDateTime returnDateTime) {
		this.returnDateTime = returnDateTime;
	}
	
}
