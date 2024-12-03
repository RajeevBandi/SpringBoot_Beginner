package com.facilitiesportal.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.facilitiesportal.model.Visitor;
import com.facilitiesportal.services.IImageService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Controller class responsible for managing image-related end points..
 */
@RestController
@RequestMapping("/image")
@SecurityRequirement(name = "bearerAuth")
public class ImageController {
	
	@Autowired
	IImageService imageService;
	
	/**
	 * API endpoint to retrieves the profile image of a specified visitor.
	 * 
	 * @param visitor The visitor object for whom the profile image is requested.
	 * @return ResponseEntity<Map<String, Object>> ResponseEntity containing the profile image.
	 */
	@PostMapping("/get-visitorProfileImage")
	@Secured({ "office_admin", "security", "management" })
	public ResponseEntity<Map<String, Object>> getVisitorProfileImage(@RequestBody Visitor visitor){
		return ResponseEntity.status(HttpStatus.OK).body(imageService.getVisitorProfileImage(visitor.getId()));
	}

	/**
	 * API endpoint to retrieves all ID proof images of a given visitor.
	 * 
	 * @param visitor The visitor object for whom the ID proof images are requested.
	 * @return ResponseEntity<Map<String, Object>> ResponseEntity containing the ID proof images.
	 */
	@PostMapping("/get-visitorIdProofImages")
	@Secured({ "office_admin", "security", "management" })
	public ResponseEntity<Map<String, Object>> getAllVisitorIdProofImages(@RequestBody Visitor visitor){
		return ResponseEntity.status(HttpStatus.OK).body(imageService.getVisitorIdProofImages(visitor.getId()));
	}
}
