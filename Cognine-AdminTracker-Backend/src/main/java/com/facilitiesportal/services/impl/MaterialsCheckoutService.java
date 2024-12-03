package com.facilitiesportal.services.impl;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facilitiesportal.dao.EmployeeRepo;
import com.facilitiesportal.dao.FacilitiesDropdownsRepo;
import com.facilitiesportal.dao.ItemQuantityReturnLogRepo;
import com.facilitiesportal.dao.MaterialCheckoutGatePassRepo;
import com.facilitiesportal.dao.MaterialCheckoutItemRepo;
import com.facilitiesportal.exceptions.ResourceNotFoundException;
import com.facilitiesportal.model.Employee;
import com.facilitiesportal.model.FacilitiesDropdowns;
import com.facilitiesportal.model.ItemQuantityReturnLog;
import com.facilitiesportal.model.ItemsReturned;
import com.facilitiesportal.model.MaterialCheckoutDTO;
import com.facilitiesportal.model.MaterialCheckoutGatePass;
import com.facilitiesportal.model.MaterialCheckoutItem;
import com.facilitiesportal.model.MaterialCheckoutSearchCriteria;
import com.facilitiesportal.services.IMaterialsCheckoutService;
import com.facilitiesportal.util.SecurityUtil;

/**
 * Service class for managing material checkouts.
 */
@Service
public class MaterialsCheckoutService implements IMaterialsCheckoutService {
	
	@Autowired
	private ItemQuantityReturnLogRepo itemQuantityReturnLogRepo;

	@Autowired
	private FacilitiesDropdownsRepo facilitiesDropdownsRepo;

	@Autowired
	private MaterialCheckoutGatePassRepo materialCheckoutGatePassRepo;

	@Autowired
	private MaterialCheckoutItemRepo materialCheckoutItemRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private SecurityUtil securityUtil;

	
	/**
	 * Adds material checkouts based on the provided MaterialCheckoutDTO and user email.
	 * 
	 * @param newMaterialsCheckout The MaterialCheckoutDTO containing the material checkout data.
	 * @param email                The email of the authenticated user.
	 * @return The MaterialCheckoutDTO with added material checkouts.
	 */
	@Override
	@Transactional
	public MaterialCheckoutDTO addMaterialsCheckouts(MaterialCheckoutDTO newMaterialsCheckout, String email) {
		MaterialCheckoutDTO savedMaterialCheckout = new MaterialCheckoutDTO();
		try {

			MaterialCheckoutGatePass newGatePass = newMaterialsCheckout.getGatePass();

			if (newMaterialsCheckout.getGatePass() == null || 
					newMaterialsCheckout.getItems().isEmpty()|| 
					newGatePass.getId() !=0 ||
					newGatePass.getGatePassId() !=null ||
					newGatePass.getStatus() !=null ||
					newGatePass.getSentByEmp()!=null ||
					newGatePass.getSentToAddress()==null || 
					newGatePass.getSentToAddress().isEmpty() ||	
					newGatePass.getType() == null || 
					newGatePass.getType().isEmpty()|| 
					newGatePass.getItemTypeId() == null || 
					newGatePass.getSentTo() == null || 
					newGatePass.getSentTo().isEmpty() || 
					newGatePass.getSentOn() == null || 
					(newGatePass.getType().toLowerCase().equals("returnable") && 
						newGatePass.getExpectedReturnDate() == null  ) ) {
				throw new IllegalArgumentException("Invalid data provided for material checkout gate pass.");
			}
			
			Long itemTypeId = newMaterialsCheckout.getGatePass().getItemTypeId().getId();
			FacilitiesDropdowns existingDropdownItemType = facilitiesDropdownsRepo.findById(itemTypeId)
					.orElseThrow(() -> new ResourceNotFoundException(
							"Item type not found for this id in facilities dropdows  : " + itemTypeId));
			
			if(newGatePass.getType().toLowerCase().equals("returnable")) {
				newGatePass.setStatus("Yet to be Returned");
				newGatePass.setGatePassId("RGP"+(materialCheckoutGatePassRepo.countByTypeIgnoreCase(newGatePass.getType())+1));
			}
			else if(newGatePass.getType().toLowerCase().equals("non-returnable")) {
				newGatePass.setStatus(null);
				newGatePass.setExpectedReturnDate(null);
				newGatePass.setGatePassId("NRGP"+(materialCheckoutGatePassRepo.countByTypeIgnoreCase(newGatePass.getType())+1));
			}

			Employee loggedInEmployee = employeeRepo.findByEmail(email);
			newGatePass.setSentByEmp(loggedInEmployee);

			String loggedInUsername = securityUtil.getLoggedInUsername();
			newGatePass.setCreatedBy(loggedInUsername);

			newGatePass.setItemTypeId(existingDropdownItemType);
			newGatePass.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
			newGatePass.setIsActive(true);

			savedMaterialCheckout.setGatePass(saveGatePass(newGatePass));

			List<MaterialCheckoutItem> newMaterialCheckoutItems = newMaterialsCheckout.getItems();

			for (MaterialCheckoutItem newMaterialCheckoutItem : newMaterialCheckoutItems) {

				if (newMaterialCheckoutItem.getDescription() == null
						|| newMaterialCheckoutItem.getDescription() == ""
						|| newMaterialCheckoutItem.getId() != 0) {
					throw new IllegalArgumentException("Invalid data provided for material checkout item.");
				}

				newMaterialCheckoutItem.setMaterialCheckoutGatePass(
						materialCheckoutGatePassRepo.findById(savedMaterialCheckout.getGatePass().getId()));

				newMaterialCheckoutItem.setTotalReturnedQuantity(0);
				newMaterialCheckoutItem.setActualReturnDateTime(null);
				newMaterialCheckoutItem.setCreatedBy(loggedInUsername);
				newMaterialCheckoutItem.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
				newMaterialCheckoutItem.setIsActive(true);

				savedMaterialCheckout.getItems().add(saveMaterialCheckoutItem(newMaterialCheckoutItem));
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException("An error occurred while saving new material checkout entry "+ e.getMessage());
		}
		return savedMaterialCheckout;
	}

	/**
	 * Saves the given MaterialCheckoutGatePass entity to the database.
	 * 
	 * @param gatePass The MaterialCheckoutGatePass entity to be saved.
	 * @return The saved MaterialCheckoutGatePass entity.
	 */
	public MaterialCheckoutGatePass saveGatePass(MaterialCheckoutGatePass gatePass) {
		return materialCheckoutGatePassRepo.save(gatePass);
	}

	/**
	 * Saves the given MaterialCheckoutItem entity to the database.
	 * 
	 * @param item The MaterialCheckoutItem entity to be saved.
	 * @return The saved MaterialCheckoutItem entity.
	 */

	public MaterialCheckoutItem saveMaterialCheckoutItem(MaterialCheckoutItem item) {
		return materialCheckoutItemRepo.save(item);
	}
	
	public ItemQuantityReturnLog saveItemQuantityReturnLog(ItemQuantityReturnLog itemQuantityReturnLog) {
		return itemQuantityReturnLogRepo.save(itemQuantityReturnLog);
	}
	
	/**
	 * Retrieves all material checkout records based on the provided search criteria, pagination parameters, and sorting options.
	 * 
	 * @param searchCriteria The criteria used to filter the material checkout records.
	 * @param pageNum        The page number of the pagination.
	 * @param pageSize       The size of each page in the pagination.
	 * @return               A map containing the search result, which includes material checkout DTOs,
	 *                       and the count of gate passes matching the search criteria.
	 */
	@Override
	public Map<String, Object> getAllMaterialCheckout(MaterialCheckoutSearchCriteria searchCriteria, int pageNum, int pageSize){

        Map<String, Object> response = new LinkedHashMap<>();
		try {
			
	        List<MaterialCheckoutDTO> searchResult = new ArrayList<>();      	       

			Pageable pageRequest = null;	
			boolean isSortByGatePassId = false;
			
			if(searchCriteria.getGatePassSortBy()!=null && searchCriteria.getGatePassOrderBy()!=null) {
				if(searchCriteria.getGatePassSortBy().toLowerCase().equals("gatepassid")) {
					pageRequest = PageRequest.of(pageNum, pageSize);
					isSortByGatePassId = true;
				}
				else if (searchCriteria.getGatePassOrderBy().equals("ascending")) {
					pageRequest = PageRequest.of(pageNum, pageSize,
							Sort.by(Sort.Order.asc(searchCriteria.getGatePassSortBy()), Sort.Order.desc("id")));
				} else if (searchCriteria.getGatePassOrderBy().equals("descending")) {
					pageRequest = PageRequest.of(pageNum, pageSize,
							Sort.by(Sort.Order.desc(searchCriteria.getGatePassSortBy()), Sort.Order.desc("id")));
				}
			}
			else {
				pageRequest  = PageRequest.of(pageNum, pageSize,
						Sort.by(Sort.Order.desc("sentOn"), Sort.Order.desc("id")));
			}
			
			boolean isGlobalItemSearch = false;
			boolean isGlobalGatePassSearch = false;
			if(searchCriteria.getItemGlobalSearch() != null && searchCriteria.getItemGlobalSearch()!="") {
				isGlobalItemSearch = true;
			}
			
			if(searchCriteria.getGatePassGlobalSearch() != null && searchCriteria.getGatePassGlobalSearch() != "") {
				isGlobalGatePassSearch = true;
			}
			
			if(searchCriteria.getStatusList() !=null && !searchCriteria.getStatusList().isEmpty()) {
				searchCriteria.setStatusList(searchCriteria.getStatusList().stream().map(String::toLowerCase).collect(Collectors.toList()));
			}
			else {
				searchCriteria.setStatusList(null);
			}
			
			if(searchCriteria.getEmpNameList() !=null && !searchCriteria.getEmpNameList().isEmpty()) {
				searchCriteria.setEmpNameList(searchCriteria.getEmpNameList().stream().map(String::toLowerCase).collect(Collectors.toList()));
			}
			else {
				searchCriteria.setEmpNameList(null);
			}
			
			
			List<MaterialCheckoutGatePass> GatePassesResult = materialCheckoutGatePassRepo
					.findMaterialCheckoutGatePassesBySearchCriteria(searchCriteria.getGatePassId(),
							searchCriteria.getEmpNameList(), 
							searchCriteria.getSentTo(),
							searchCriteria.getSentToAddress(),
							searchCriteria.getStatusList(), 
							searchCriteria.getType(), 
							searchCriteria.getItemTypeId(),
							searchCriteria.getSentOnDateMin(), 
							searchCriteria.getSentOnDateMax(),
							searchCriteria.getExpectedReturnDateMin(),
							searchCriteria.getExpectedReturnDateMax(), 
							searchCriteria.getItemId(),
							searchCriteria.getDescription(), 
							searchCriteria.getActualReturnDateMin(), 
							searchCriteria.getActualReturnDateMax(),
							isGlobalGatePassSearch,
							searchCriteria.getGatePassGlobalSearch(),
							isGlobalItemSearch,
							searchCriteria.getItemGlobalSearch(),
							isSortByGatePassId,
							searchCriteria.getGatePassOrderBy(),
							pageRequest);
			
			long gatePassesCount = materialCheckoutGatePassRepo.countMaterialCheckoutGatePassesBySearchCriteria(
							searchCriteria.getGatePassId(),
							searchCriteria.getEmpNameList(), 
							searchCriteria.getSentTo(),
							searchCriteria.getSentToAddress(),
							searchCriteria.getStatusList(), 
							searchCriteria.getType(), 
							searchCriteria.getItemTypeId(),
							searchCriteria.getSentOnDateMin(), 
							searchCriteria.getSentOnDateMax(),
							searchCriteria.getExpectedReturnDateMin(),
							searchCriteria.getExpectedReturnDateMax(), 
							searchCriteria.getItemId(),
							searchCriteria.getDescription(), 
							searchCriteria.getActualReturnDateMin(), 
							searchCriteria.getActualReturnDateMax(),
							isGlobalGatePassSearch,
							searchCriteria.getGatePassGlobalSearch(),
							isGlobalItemSearch,
							searchCriteria.getItemGlobalSearch());
			
			List<Long> searchResultItemIds = materialCheckoutItemRepo.findMaterialCheckoutItemBySearchCriteria(							
							searchCriteria.getItemId(),
							searchCriteria.getDescription(), 
							searchCriteria.getActualReturnDateMin(), 
							searchCriteria.getActualReturnDateMax(),
							isGlobalItemSearch,
							searchCriteria.getItemGlobalSearch());
			
			
			for(MaterialCheckoutGatePass gatePass : GatePassesResult) {
				MaterialCheckoutDTO materialCheckoutDTO = new MaterialCheckoutDTO();
				materialCheckoutDTO.setGatePass(gatePass);
				
				List<MaterialCheckoutItem> gatePassItems = materialCheckoutItemRepo.findAllByMaterialCheckoutGatePass_IdOrderByIdDesc(gatePass.getId());
				for(MaterialCheckoutItem item : gatePassItems) {
					if(searchResultItemIds.contains(item.getId())) {
						item.setIsSearchResult(true);
					}
					else {
						item.setIsSearchResult(false);
					}
					
					item.setItemQuantityReturnLogs(itemQuantityReturnLogRepo.findByItemId(item.getId()));
					materialCheckoutDTO.getItems().add(item);
				}
				searchResult.add(materialCheckoutDTO);
			}
			
			response.put("searchResult", searchResult);
			response.put("gatePassesCount", gatePassesCount);

		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException("An error occurred while fetching material checkout records: " + e.getMessage());
		}
		return response;
	}

	/**
	 * Checks in multiple material checkout items based on the provided information.
	 * 
	 * @param itemsReturned An object containing information about the items being checked in, including their IDs and actual return date.
	 */
	@Override
	@Transactional
	public void checkinMultipleItems(ItemsReturned itemsReturned) {
		String loggedInUsername = securityUtil.getLoggedInUsername();
		Set<Long> gatePassIds = new HashSet<>();
		
		if (itemsReturned != null && itemsReturned.getItemQuantityReturnLogs() != null && !itemsReturned.getItemQuantityReturnLogs().isEmpty())
			for (ItemQuantityReturnLog itemQuantityReturn : itemsReturned.getItemQuantityReturnLogs()) {
				MaterialCheckoutItem materialCheckoutItem = materialCheckoutItemRepo.findById(itemQuantityReturn.getItemId());
				int sumOfReturnedQuantity = materialCheckoutItem.getTotalReturnedQuantity() + itemQuantityReturn.getReturnedQuantity();
				if (itemsReturned.getReturnDateTime() != null
						&& itemsReturned.getReturnDateTime() instanceof LocalDateTime
						&& materialCheckoutItem.getMaterialCheckoutGatePass() != null
						&& materialCheckoutItem.getMaterialCheckoutGatePass().getType() != null 
						&& materialCheckoutItem.getMaterialCheckoutGatePass().getType().toLowerCase().equals("returnable")
						&& materialCheckoutItem.getActualReturnDateTime() == null
						&& materialCheckoutItem.getTotalQuantity() >= sumOfReturnedQuantity
						&& itemQuantityReturn.getReturnedQuantity()>=1) {
					
					//save itemQuentityReturnLog
					itemQuantityReturn.setItem(materialCheckoutItem);
					itemQuantityReturn.setReturnDateTime(itemsReturned.getReturnDateTime());
					itemQuantityReturn.setCreatedBy(loggedInUsername);
					itemQuantityReturn.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
					saveItemQuantityReturnLog(itemQuantityReturn);
					
					if(materialCheckoutItem.getTotalQuantity() == sumOfReturnedQuantity) {
						materialCheckoutItem.setActualReturnDateTime(itemsReturned.getReturnDateTime());
					}
					materialCheckoutItem.setTotalReturnedQuantity(sumOfReturnedQuantity);
					materialCheckoutItem.setUpdatedBy(loggedInUsername);
					materialCheckoutItem.setUpdatedAt(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
					
					saveMaterialCheckoutItem(materialCheckoutItem);
					gatePassIds.add(materialCheckoutItem.getMaterialCheckoutGatePass().getId());
				}
				else {
					throw new IllegalArgumentException("Invalid check-in data provided.");
				}
			}
		
		//update gate pass status
		for(long id : gatePassIds) {
			MaterialCheckoutGatePass materialCheckoutGatePass = materialCheckoutGatePassRepo.findById(id);
			if (materialCheckoutGatePass.getType().toLowerCase().equals("returnable")) {
				materialCheckoutGatePass.setStatus(materialCheckoutItemRepo.getGatePassStatus(id, materialCheckoutGatePass.getExpectedReturnDate().toString()));
			} else {
				materialCheckoutGatePass.setStatus(null);
			}
			materialCheckoutGatePass.setUpdatedBy(loggedInUsername);
			materialCheckoutGatePass.setUpdatedAt(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));	
			saveGatePass(materialCheckoutGatePass);
		}
	}
	
	/**
	 * Edits a material checkout record. This method allows updating both the material checkout gate pass and its associated items.
	 * 
	 * @param updatedMaterialCheckoutDTO A MaterialCheckoutDTO object containing the updated details for the material checkout.
	 * @return A MaterialCheckoutDTO object representing the saved material checkout record with updated information.
	 */
	@Override
	@Transactional
	public MaterialCheckoutDTO editMaterialCheckout(MaterialCheckoutDTO updatedMaterialCheckoutDTO) {
		MaterialCheckoutDTO savedMaterialCheckoutDTO = new MaterialCheckoutDTO();
		try {			
			if(updatedMaterialCheckoutDTO.getGatePass().getId()>=1) {
								
				//Updating item changes 
				List<MaterialCheckoutItem> updateMaterialCheckoutItems = updatedMaterialCheckoutDTO.getItems();
				if(updateMaterialCheckoutItems!=null && !updateMaterialCheckoutItems.isEmpty()) {
					for(MaterialCheckoutItem updatedItem: updateMaterialCheckoutItems) {
					
						if (updatedItem.getId() != 0) {
							MaterialCheckoutItem existingItem = materialCheckoutItemRepo.findById(updatedItem.getId());
							if (existingItem.getMaterialCheckoutGatePass().getId() == updatedMaterialCheckoutDTO
									.getGatePass().getId()) {
								// update itemId
								if (updatedItem.getItemId() != null && updatedItem.getItemId() != "") {
									existingItem.setItemId(updatedItem.getItemId());
								}
								// update description
								if (updatedItem.getDescription() != null && updatedItem.getDescription() != "") {
									existingItem.setDescription(updatedItem.getDescription());
								}

								// update actualReturnedDate
//								if (updatedItem.getActualReturnDateTime() != null
//										&& updatedItem.getActualReturnDateTime() instanceof LocalDateTime
//										&& existingItem.getMaterialCheckoutGatePass() != null
//										&& existingItem.getMaterialCheckoutGatePass().getType() != null
//										&& existingItem.getMaterialCheckoutGatePass().getType().toLowerCase()
//												.equals("returnable")) {
//									existingItem.setActualReturnDateTime(updatedItem.getActualReturnDateTime());
//								}

								// Update Is active
								if (updatedItem.getIsActive()!=null && existingItem.getIsActive() != updatedItem.getIsActive()) {
									existingItem.setIsActive(updatedItem.getIsActive());
								}

								String loggedInUsername = securityUtil.getLoggedInUsername();
								existingItem.setUpdatedBy(loggedInUsername);
								existingItem.setUpdatedAt(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
								savedMaterialCheckoutDTO.getItems().add( (saveMaterialCheckoutItem(existingItem)));
							}
							else {
								throw new IllegalArgumentException("Error: Material Item not found");
							}
						}
					}
				}
								
				//updating Gate Pass changes 
				MaterialCheckoutGatePass updatedCheckoutGatePass=updatedMaterialCheckoutDTO.getGatePass();
				MaterialCheckoutGatePass existingCheckoutGatePass = materialCheckoutGatePassRepo.findById(updatedCheckoutGatePass.getId());
				
				//update sentToAddress
				if(updatedCheckoutGatePass.getSentToAddress() != null && updatedCheckoutGatePass.getSentToAddress() != "" ) {
					existingCheckoutGatePass.setSentToAddress(updatedCheckoutGatePass.getSentToAddress());
				}
				
				//update itemTypeId
				if(updatedCheckoutGatePass.getItemTypeId() != null) {
					long itemTypeId = updatedCheckoutGatePass.getItemTypeId().getId();
					FacilitiesDropdowns existingDropdownItemType = facilitiesDropdownsRepo.findById(itemTypeId)
							.orElseThrow(() -> new ResourceNotFoundException(
									"Item type not found for this id in facilities dropdows  : " + itemTypeId));
					
					existingCheckoutGatePass.setItemTypeId(existingDropdownItemType);
				}
				
				//Update type
				if(updatedCheckoutGatePass.getType()!=null && updatedCheckoutGatePass.getType()!= "" ) {
					existingCheckoutGatePass.setType(updatedCheckoutGatePass.getType());
				}
				
				//update SentTo 
				if(updatedCheckoutGatePass.getSentTo()!=null && updatedCheckoutGatePass.getSentTo()!="") {
					existingCheckoutGatePass.setSentTo(updatedCheckoutGatePass.getSentTo());
				}
				
				//update sentOn
				if(updatedCheckoutGatePass.getSentOn()!=null && updatedCheckoutGatePass.getSentOn() instanceof LocalDateTime) {
					existingCheckoutGatePass.setSentOn(updatedCheckoutGatePass.getSentOn());
				}
				
				//update expectedReturnDate
				if(updatedCheckoutGatePass.getExpectedReturnDate()!=null && updatedCheckoutGatePass.getExpectedReturnDate() instanceof LocalDate) {
					existingCheckoutGatePass.setExpectedReturnDate(updatedCheckoutGatePass.getExpectedReturnDate());
				}
				
				//update comments
				if(updatedCheckoutGatePass.getComments()!=null && updatedCheckoutGatePass.getComments()!="") {
					existingCheckoutGatePass.setComments(updatedCheckoutGatePass.getComments());
				}
				
				//update Status
				if (existingCheckoutGatePass.getType().toLowerCase().equals("returnable")) {
					existingCheckoutGatePass
							.setStatus(materialCheckoutItemRepo.getGatePassStatus(existingCheckoutGatePass.getId(),existingCheckoutGatePass.getExpectedReturnDate().toString()));
				}
				else {
					existingCheckoutGatePass.setStatus(null);
				}
				
				//Update Is active
				if(updatedCheckoutGatePass.getIsActive()!=null && existingCheckoutGatePass.getIsActive() != updatedCheckoutGatePass.getIsActive()) {
					existingCheckoutGatePass.setIsActive(updatedCheckoutGatePass.getIsActive());
				}
				
				String loggedInUsername = securityUtil.getLoggedInUsername();
				existingCheckoutGatePass.setUpdatedBy(loggedInUsername);
				existingCheckoutGatePass.setUpdatedAt(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));	
				
			savedMaterialCheckoutDTO.setGatePass(	saveGatePass(existingCheckoutGatePass));
			}			
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		} catch (Exception e) {
			throw new RuntimeException("An error occurred while updating material checkout records: " + e.getMessage());
		}
		return savedMaterialCheckoutDTO;
	}	
	
	/**
	 * Retrieves all distinct employee ids and names who sent the material checkout.
	 * 
	 * @return A list of maps, each containing the distinct employee ID and name.
	 */
	@Override
	public  List<Map<String, Object>> getDistinctSentByEmployeeIdsAndNames() {
		try {
			return materialCheckoutGatePassRepo.findDistinctSentByEmployeeIdsAndNames();
		} catch (Exception e) {
			throw new RuntimeException("An error occurred while updating material checkout records: " + e.getMessage());
		}
	}
}