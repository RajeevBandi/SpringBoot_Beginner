package com.facilitiesportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facilitiesportal.model.VisitorIdentityCard;
import com.facilitiesportal.services.IVisitorIdentityCardService;
import com.facilitiesportal.util.VisitorIdentityCardStatus;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Controller class for managing Visitor identity card related end points.
 */
@RestController
@RequestMapping("/visitor-identity-card")
@SecurityRequirement(name = "bearerAuth")
public class VisitorIdentityCardController {
	
	@Autowired
	private IVisitorIdentityCardService visitorIdentityCardService;
	
	/**
	 * Adds a new visitor identity card.
	 *
	 * @param newVisitorIdentityCard The visitor identity card to be added.
	 * @return ResponseEntity containing the added visitor identity card and HTTP status code 201 (Created).
	 */
	@PostMapping("/add")
	@Secured({"office_admin"})
	public ResponseEntity<VisitorIdentityCard> saveVisitorIdentityCard(@RequestBody VisitorIdentityCard newVisitorIdentityCard){
		return ResponseEntity.status(HttpStatus.CREATED).body(visitorIdentityCardService.saveVisitorIdentityCard(newVisitorIdentityCard));
	}
	
	/**
	 * Updates a visitor identity card.
	 *
	 * @param updatedVisitorIdentityCard The visitor identity card containing the updated information.
	 * @return ResponseEntity containing the updated visitor identity card.
	 */
	@PutMapping("/update")
	@Secured({"office_admin"})
	public ResponseEntity<VisitorIdentityCard> updateVisitorIdentityCard(@RequestBody VisitorIdentityCard updatedVisitorIdentityCard){
		return ResponseEntity.ok(visitorIdentityCardService.updateVisitorIdentityCard(updatedVisitorIdentityCard));
	}
	
		
	/**
	 * Gets the status of a visitor identity card.
	 *
	 * @param visitorIdentityCard The visitor identity card for which the status is requested.
	 * @return ResponseEntity containing the visitor identity card status.
	 */
	@PostMapping("/get-status")
	@Secured({ "office_admin", "security", "management" })
	public ResponseEntity<VisitorIdentityCardStatus> getVisitorIdentityCardStatus(@RequestBody VisitorIdentityCard visitorIdentityCard){
		return ResponseEntity.ok(visitorIdentityCardService.getVisitorIdentityCardStatus(visitorIdentityCard));
	}
}
