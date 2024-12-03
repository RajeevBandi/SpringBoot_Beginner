package com.facilitiesportal.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.facilitiesportal.model.ItemsReturned;
import com.facilitiesportal.model.MaterialCheckoutDTO;
import com.facilitiesportal.model.MaterialCheckoutSearchCriteria;
import com.facilitiesportal.services.IMaterialsCheckoutService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Controller class for managing material checkout related end points.
 */
@RestController
@RequestMapping("/materialcheckout")
@SecurityRequirement(name = "bearerAuth")
public class MaterialCheckoutController {

	@Autowired
	IMaterialsCheckoutService materialsCheckoutService;

	/**
	 * Controller method for adding material checkouts.
	 * 
	 * @param materialCheckoutDTO The MaterialCheckoutDTO containing the materials checkout data.
	 * @param authentication      The authentication object containing user details.
	 * @return ResponseEntity with the added MaterialCheckoutDTO and HTTP status code 201 (Created).
	 */
	@PostMapping("/add")
	@Secured({ "office_admin", "system_admin", "management" })
	public ResponseEntity<MaterialCheckoutDTO> addMaterialCheckouts(
			@RequestBody MaterialCheckoutDTO materialCheckoutDTO, Authentication authentication) {
		
		Jwt jwt = (Jwt) authentication.getPrincipal();
		String email = jwt.getClaims().get("unique_name").toString();
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(materialsCheckoutService.addMaterialsCheckouts(materialCheckoutDTO, email));
	}
	
	/**
	 * Controller method for retrieving all material checkouts based on search criteria.
	 * 
	 * @param searchCriteria The criteria to filter material checkouts.
	 * @param pageNum        The page number for pagination. Default is 0.
	 * @param pageSize       The page size for pagination. Default is 10.
	 * @return ResponseEntity containing a map with search results and metadata, with HTTP status code 200 (OK).
	 */
    @PostMapping("/getAll")
	@Secured({ "office_admin", "system_admin", "management" })
    public ResponseEntity<Map<String, Object>> getAllMaterialCheckout(
            @RequestBody MaterialCheckoutSearchCriteria searchCriteria,
            @RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        
    	Map<String, Object> result = materialsCheckoutService.getAllMaterialCheckout(searchCriteria, pageNum, pageSize);
        
        return ResponseEntity.ok(result);
    }
    
    /**
	 * Controller method for editing a material checkout.
	 * 
	 * @param editMaterialCheckoutDTO The MaterialCheckoutDTO containing the updated material checkout data.
	 * @return ResponseEntity with the edited MaterialCheckoutDTO and HTTP status code 200 (OK).
	 */
	@PutMapping("/edit")
	@Secured({ "office_admin", "system_admin", "management" })
	public ResponseEntity<MaterialCheckoutDTO> editMaterialCheckout(@RequestBody MaterialCheckoutDTO editMaterialCheckoutDTO) {
        return ResponseEntity.ok(materialsCheckoutService.editMaterialCheckout(editMaterialCheckoutDTO));
	}
	
	/**
	 * Controller method for returning checked out materials.
	 * 
	 * @param itemsReturned The ItemsReturned object containing the information about the items being returned.
	 * @return ResponseEntity containing a map with search results and metadata, with HTTP status code 200 (OK),
	 *  or HTTP status code 400 (Bad Request) if an exception occurs.
	 */
	@PutMapping("/multiple-checkin")
	@Secured({ "office_admin", "system_admin", "management" })
	public ResponseEntity<Map<String, String> > multipleCheckin(@RequestBody ItemsReturned itemsReturned) {
		Map<String, String> result = new HashMap<>();
		try {
			materialsCheckoutService.checkinMultipleItems(itemsReturned);
			result.put("Result", "success");
			return ResponseEntity.ok().body(result);
		} catch (RuntimeException e) {
			result.put("Error checking in items: ", e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
	}
	
	/**
	 * Controller method for retrieving all distinct employees who sent the material checkout.
	 * 
	 * @return ResponseEntity containing a list of maps, each containing a distinct employee ID and name, with an HTTP status code 200 (OK).
	 */
	@GetMapping("/sentByEmp/id-and-names")
	@Secured({ "office_admin", "system_admin", "management" })
	public ResponseEntity< List<Map<String, Object>>> getDistinctSentByEmployeeIdsAndNames() {
		return ResponseEntity.ok(materialsCheckoutService.getDistinctSentByEmployeeIdsAndNames());
	}
}
