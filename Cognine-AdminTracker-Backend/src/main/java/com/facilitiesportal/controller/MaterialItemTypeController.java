package com.facilitiesportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facilitiesportal.model.FacilitiesDropdowns;
import com.facilitiesportal.services.IMaterialItemTypeServiec;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Controller class for managing material item types purposes.
 */
@RestController
@RequestMapping("/materialItemType")
@SecurityRequirement(name = "bearerAuth")
public class MaterialItemTypeController {
	
	@Autowired
	IMaterialItemTypeServiec materialItemTypeServiceObj;
	
	/**
	 * Get all Material Item Types.
	 * 
	 * @return List of ItemTypes
	 */
	@GetMapping("/getAll")
	@Secured({ "management", "office_admin", "system_admin" })
	public List<FacilitiesDropdowns> getAllMaterialItemType() {
		return materialItemTypeServiceObj.getAllMaterialItemType();
	}

	/**
	 * Add a new Material Item Type.
	 * 
	 * @param newMaterialItemType The new  Material Item Type to add
	 * @return ResponseEntity containing the added Material Item Type object
	 */
	@PostMapping("/add")
	@Secured({ "management", "office_admin", "system_admin" })
	public ResponseEntity<FacilitiesDropdowns> addMaterialItemType(@RequestBody FacilitiesDropdowns newMaterialItemType) {
		return ResponseEntity.ok(materialItemTypeServiceObj.addMaterialItemType(newMaterialItemType));
	}

}
