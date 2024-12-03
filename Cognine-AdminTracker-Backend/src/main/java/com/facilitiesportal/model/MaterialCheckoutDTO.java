package com.facilitiesportal.model;

import java.util.ArrayList;
import java.util.List;

public class MaterialCheckoutDTO {
    private MaterialCheckoutGatePass gatePass;
    private List<MaterialCheckoutItem> items;    

    public MaterialCheckoutDTO() {
		super();
		this.items = new ArrayList<>();
	}

	public MaterialCheckoutDTO(MaterialCheckoutGatePass gatePass, List<MaterialCheckoutItem> items) {
        this.gatePass = gatePass;
        this.items = items;
    }

    public MaterialCheckoutGatePass getGatePass() {
        return gatePass;
    }

    public void setGatePass(MaterialCheckoutGatePass gatePass) {
        this.gatePass = gatePass;
    }

    public List<MaterialCheckoutItem> getItems() {
        return items;
    }

    public void setItems(List<MaterialCheckoutItem> items) {
        this.items = items;
    }
}
