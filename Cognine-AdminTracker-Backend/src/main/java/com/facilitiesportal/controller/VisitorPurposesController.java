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
import com.facilitiesportal.services.IVisitorPurposesService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Controller class for managing visitor purposes.
 */
@RestController
@RequestMapping("/visitorPurposes")
@SecurityRequirement(name = "bearerAuth")
//@CrossOrigin
public class VisitorPurposesController {

	@Autowired
	IVisitorPurposesService visitorPurposesServiceObj;

	/**
	 * Get all visitor purposes.
	 * 
	 * @return List of VisitorPurposes
	 */
	@GetMapping("/getAll")
	@Secured({ "management", "office_admin", "security" })
	public List<FacilitiesDropdowns> getAllVisitorPurposes() {
		return visitorPurposesServiceObj.getAllVisitorPurposes();
	}

	/**
	 * Add a new visitor purpose.
	 * 
	 * @param newVisitorPurposes The new visitor purpose to add
	 * @return ResponseEntity containing the added VisitorPurposes object
	 */
	@PostMapping("/add")
	@Secured({ "management", "office_admin", "security" })
	public ResponseEntity<FacilitiesDropdowns> addVisitorPurpose(@RequestBody FacilitiesDropdowns newVisitorPurposes) {
		return ResponseEntity.ok(visitorPurposesServiceObj.addVisitorPurpose(newVisitorPurposes));
	}

}
