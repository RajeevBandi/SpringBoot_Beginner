package com.facilitiesportal.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.facilitiesportal.exceptions.ResourceNotFoundException;
import com.facilitiesportal.model.SearchOrSortCriteria;
import com.facilitiesportal.model.Visitor;
import com.facilitiesportal.model.VisitorCheckoutSearchCriteria;
import com.facilitiesportal.services.IVisitorService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controller class for managing visitors-related end points.
 */
@RestController
@RequestMapping("/visitor")
@SecurityRequirement(name = "bearerAuth")
//@CrossOrigin
public class VisitorController {

	@Autowired
	IVisitorService visitorServiceobj;

	/**
	 * Retrieves a list of visitors and the count of total records.
	 *
	 * @param pageNum  The page number.
	 * @param pageSize The page size.
	 * @return A List of visitors and the count of total records.
	 */
	@GetMapping("/getAll")
	@Secured({ "office_admin", "management" })
	public ResponseEntity<Map<String, Object>> getAllVisitor(
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

		List<Visitor> visitors = visitorServiceobj.getAllVisitors(pageNum, pageSize);
		long totalRecords = visitorServiceobj.totalVisitorsCount();
		Map<String, Object> response = new HashMap<>();
		response.put("visitors", visitors);
		response.put("totalRecords", totalRecords);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}


	/**
	 * Saves new visitors
	 *
	 * @param newVisitors The list of new visitors to save.
	 * @return A response entity with the list of saved visitors and HTTP status
	 *         CREATED.
	 */
	@PostMapping("/add")
	@Secured({ "office_admin", "security", "management" })
	public ResponseEntity<List<Visitor>> saveVisitor(@RequestBody List<Visitor> newVisitors) {
		return ResponseEntity.status(HttpStatus.CREATED).body(visitorServiceobj.saveVisitors(newVisitors));
	}

	/**
	 * Update Visitor details.
	 *
	 * @param visitoDetails The updated visitor details.
	 * @return A response entity with the updated visitors and HTTP status OK.
	 */
	@PutMapping("/update")
	@Secured({ "office_admin", "security", "management" })
	public ResponseEntity<Visitor> updateVisitor(@Validated @RequestBody Visitor visitorDetails)
			throws ResourceNotFoundException {
		return ResponseEntity.ok(visitorServiceobj.updateVisitor(visitorDetails.getId(), visitorDetails));
	}

	/**
	 * Delete a visitor by Id
	 *
	 * @param id The ID of the visitor to delete.
	 * @return A map including weather the visitor was deleted successfully.
	 */
	@DeleteMapping("/delete")
	@Secured({ "office_admin", "security", "management" })
	public Map<String, Boolean> deleteVisitor(@Validated @RequestBody Long id) throws ResourceNotFoundException {
		return visitorServiceobj.deleteVisitor(id);
	}

	/**
	 * Endpoint for searching visitors based on specified criteria.
	 *
	 * @param searchOrSortCriteria The criteria for searching or sorting visitors.
	 * @param pageNum              The page number for pagination (default: 0).
	 * @param pageSize             The page size for pagination (default: 10).
	 * @return ResponseEntity containing a map with search results and pagination
	 *         information.
	 */
	@PostMapping("/search")
	@Secured({ "office_admin", "management" })
	public ResponseEntity<Map<String, Object>> searchVisitors(@RequestBody SearchOrSortCriteria searchOrSortCriteria,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(visitorServiceobj.searchVisitors(searchOrSortCriteria, false, pageNum, pageSize));
	}


	/**
	 * API endpoint to search for visitors based on specified criteria, focusing on visitors who have not checked out yet.
	 * 
	 * @param searchOrSortCriteria The criteria for searching or sorting visitors.
	 * @param pageNum              The page number for pagination (default is 0).
	 * @param pageSize             The size of each page for pagination (default is 10).
	 * @return ResponseEntity<Map<String, Object>> ResponseEntity containing the search results.
	 */
	@PostMapping("/not-checked-out/search")
	@Secured({ "security" })
	public ResponseEntity<Map<String, Object>> searchOnCheckedInVisitors(
			@RequestBody SearchOrSortCriteria searchOrSortCriteria,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(visitorServiceobj.searchVisitors(searchOrSortCriteria, true, pageNum, pageSize));
	}

	/**
	 * Searches for visitors based on provided search criteria for the purpose of visitor checkout.
	 *
	 * @param visitorCheckoutSearchCriteria The criteria for searching visitors for checkout.
	 * @return ResponseEntity containing a list of visitors matching the search criteria.
	 */
	@PostMapping("/checkoutVisitorSearch")
	@Secured({ "office_admin", "security", "management" })
	public ResponseEntity<List<Visitor>> searchVisitorsForCheckout(
			@RequestBody VisitorCheckoutSearchCriteria visitorCheckoutSearchCriteria) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(visitorServiceobj.searchVisitorsForCheckout(visitorCheckoutSearchCriteria));
	}
	
	
	/**
	 *  API endpoint to retrieves the maximum number of ID proof images that a visitor can upload.
	 * 
	 * @return ResponseEntity<Map<String, Integer>> ResponseEntity containing the maximum number of ID proof images.
	 */
    @GetMapping("/max-idproof-images")
	@Secured({ "office_admin", "security", "management" })
    public  ResponseEntity<Map<String, Integer>> getMaxIdProofImages() {
        return ResponseEntity.status(HttpStatus.OK).body(visitorServiceobj.getMaxIdProofImages());
    }
}
