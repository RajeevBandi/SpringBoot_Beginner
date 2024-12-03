package com.facilitiesportal.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.facilitiesportal.dao.*;
import com.facilitiesportal.exceptions.ResourceNotFoundException;
import com.facilitiesportal.model.*;
import com.facilitiesportal.services.IMailService;
import com.facilitiesportal.services.IVisitorIdentityCardService;
import com.facilitiesportal.services.IVisitorService;
import com.facilitiesportal.util.ImageConverterUtil;
import com.facilitiesportal.util.SecurityUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * service class to manage visitor-related operations.
 */
@Service
public class VisitorService implements IVisitorService {

	private static final Logger LOGGER = LogManager.getLogger(VisitorService.class);

	public VisitorService() {
		super();
	}

	@Autowired
	private VisitorRepo visitorRepo;
	@Autowired
	private FacilitiesDropdownsRepo facilitiesDropdownsRepo;
	@Autowired
	private EmployeeRepo employeeRepo;
	@Autowired
	private ImageRepo imageRepo;
	@Autowired
	private Visitor_IdProofImageRepo visitor_IdProofImageRepo;

	@Autowired
	private VisitorIdentityCardRepo visitorIdentityCardRepo;

	@Autowired
	private IMailService mailService;

	@Autowired
	private SecurityUtil securityUtil;

	@Autowired
	IVisitorIdentityCardService visitorIdentityCardService;

	@Value("${visitor.max-idproof-images:2}")
	private int maxIdProofImages;

	/**
	 * Retrieves a list of visitors based on the given parameters.
	 * 
	 * @param pageNum  The page number.
	 * @param pageSize The page size.
	 * @param role     The role of the user.
	 * @return A list of visitors.
	 */
	@Override
	public List<Visitor> getAllVisitors(int pageNum, int pageSize) {
		List<Visitor> visitors = null;

		try {
			// Pageable object with sorting
			Pageable pageRequest = PageRequest.of(pageNum, pageSize,
					Sort.by(Sort.Order.desc("date"), Sort.Order.desc("inTime"), Sort.Order.desc("id")));
			Page<Visitor> pageVisitors = this.visitorRepo.findAll(pageRequest);
			visitors = pageVisitors.getContent();

		} catch (Exception e) {
			System.out.print(e);
		}
		return visitors;
	}

	/**
	 * Retrieves a visitor by id.
	 * 
	 * @param visitorId the Id of the visitor.
	 * @return The Visitor.
	 */
	@Override
	public Visitor getVisitorById(Long visitorId) {
		Visitor visitor = null;
		try {
			visitor = visitorRepo.findById(visitorId)
					.orElseThrow(() -> new ResourceNotFoundException("Visitor not found for this id :: " + visitorId));
		} catch (Exception e) {
			System.out.print(e);
		}
		return visitor;
	}

	/**
	 * Save a list of new Visitors.
	 * 
	 * @param newVisitors the list of new visitors to save.
	 * @return The list of recently saved visitors.
	 */
	@Override
	@Transactional
	public List<Visitor> saveVisitors(List<Visitor> newVisitors) {

		List<Visitor> savedVisitors = new ArrayList<>();

		for (Visitor newVisitor : newVisitors) {
			try {
				Long DropdownPurposeId = newVisitor.getDropdownObj().getId();
				FacilitiesDropdowns existingDropdownPurpose = facilitiesDropdownsRepo.findById(DropdownPurposeId)
						.orElseThrow(() -> new ResourceNotFoundException(
								"Purpose not found for this id in facilities dropdows  : " + DropdownPurposeId));

				if (newVisitor.getContactPersonEmp() != null) {
					int empId = newVisitor.getContactPersonEmp().getId();
					Employee existingEmployee = employeeRepo.findById(empId);
					newVisitor.setContactPersonEmp(existingEmployee);
					newVisitor.setOtherContactPersonName(null);
				}


				if (newVisitor.getProfileImageBase64() != null) {
					Image savedprofileImage = saveImage(newVisitor.getProfileImageBase64());

					newVisitor.setProfileImageId(savedprofileImage);
				}

				List<Image> savedIdProofImages = new ArrayList<>();

				List<String> visitorIdProofImagesBase64 = newVisitor.getIdProofImagesBase64();

				if (visitorIdProofImagesBase64 != null && visitorIdProofImagesBase64.size() > 0) {
					for (String idProofImage : visitorIdProofImagesBase64) {
						Image savedIdProofImage = saveImage(idProofImage);
						if (savedIdProofImage != null) {
							savedIdProofImages.add(savedIdProofImage);
						}
					}
				}

				if (newVisitor.getVisitorIdentityCard() != null
						&& newVisitor.getVisitorIdentityCard().getPhysicalId() != null
						&& newVisitor.getVisitorIdentityCard().getPhysicalId() != "") {
					VisitorIdentityCard existingVisitorIdentityCard = visitorIdentityCardRepo
							.findByPhysicalId(newVisitor.getVisitorIdentityCard().getPhysicalId());

						if (existingVisitorIdentityCard != null) {
							if (existingVisitorIdentityCard.getIsAvailable()) {
								
								newVisitor.setVisitorIdentityCard(existingVisitorIdentityCard);
								newVisitor.setIsVisitorIdentityCardReturned(false);
								existingVisitorIdentityCard.setIsAvailable(false);
								visitorIdentityCardService.updateVisitorIdentityCard(existingVisitorIdentityCard);
							}
							else {
								newVisitor.setVisitorIdentityCard(null);
							}
						}
				}
				else {
					newVisitor.setIsVisitorIdentityCardReturned(null);
					newVisitor.setVisitorIdentityCard(null);
				}
				
				
				newVisitor.setIsActive(true);
				newVisitor.setDropdownObj(existingDropdownPurpose);
				newVisitor.setCreatedAt(LocalDateTime.now());
				newVisitor.setCreatedBy(securityUtil.getLoggedInUsername());
				
				if(newVisitor.getVisitorIdentityCard()!=null && newVisitor.getIsVisitorIdentityCardReturned()!=null) {
					VisitorIdentityCard visitorIdentityCard= visitorIdentityCardService.getVisitorIdentityCard(newVisitor.getVisitorIdentityCard().getId());
					visitorIdentityCard.setIsAvailable(newVisitor.getIsVisitorIdentityCardReturned());
					visitorIdentityCardService.updateVisitorIdentityCard(visitorIdentityCard);
				}

				savedVisitors.add(visitorRepo.save(newVisitor));

				addVisitorIdProofImageMapping(savedIdProofImages, newVisitor);

				if (newVisitor.getContactPersonEmp()!=null && newVisitor.getContactPersonEmp().isActive()) {
					newVisitor.setCheckInMailMessageId(mailService.sendVisitorCheckInMail(newVisitor));
					updateVisitor(newVisitor.getId(), newVisitor);
				}
			} catch (Exception e) {
				System.out.println(e);
				throw new RuntimeException("Error saving visitor ", e);
			}
		}
		return savedVisitors;
	}

	/**
	 * Adds mappings between a visitor and their ID proof images.
	 *
	 * @param visitorIdProofImages The list of ID proof images to be mapped.
	 * @param visitor              The visitor to whom the images belong.
	 * @return True if the mappings were successfully added, false otherwise.
	 */
	private boolean addVisitorIdProofImageMapping(List<Image> visitorIdProofImages, Visitor visitor) {

		if (visitorIdProofImages != null && visitorIdProofImages.size() > 0) {
			for (Image visitorIdProofImage : visitorIdProofImages) {

				Visitor_IdProofImage visitorIdProofImageMapping = new Visitor_IdProofImage();
				visitorIdProofImageMapping.setVisitor(visitor);
				visitorIdProofImageMapping.setImage(visitorIdProofImage);

				visitor_IdProofImageRepo.save(visitorIdProofImageMapping);
			}
		}
		return true;
	}

	/**
	 * Saves an image with the provided base64 string representation.
	 *
	 * @param base64String The base64 string representation of the image.
	 * @return The saved image entity.
	 */
	private Image saveImage(String base64String) {
		byte[] byteArray = ImageConverterUtil.base64ToByteArray(base64String);

		Image image = new Image();
		image.setEncodedImage(byteArray);
		image.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
		image.setCreatedBy(securityUtil.getLoggedInUsername());
		image.setIsActive(true);

		Image savedImage = imageRepo.save(image);
		return savedImage;
	}

	/**
	 * Update a visitor with new details.
	 * 
	 * @param visitorId      The Id of the visitor to update
	 * @param visitorDetails The new details of visitor.
	 * @return The updated visitor.
	 */
	@Override
	public Visitor updateVisitor(Long visitorId, Visitor updatedVisitorDetails) {
		Visitor updatedVisitor = null;
		try {
			Visitor existingVisitor = visitorRepo.findById(visitorId)
					.orElseThrow(() -> new ResourceNotFoundException("Visitor not found for this id : " + visitorId));

			boolean isCheckoutMailSent = true;
			if (existingVisitor.getOutDateTime() == null && updatedVisitorDetails.getOutDateTime() != null) {
				isCheckoutMailSent = false;
			}

			Long DropdownPurposeId = updatedVisitorDetails.getDropdownObj().getId();
			FacilitiesDropdowns updatedDropdownPurpose = facilitiesDropdownsRepo.findById(DropdownPurposeId)
					.orElseThrow(() -> new ResourceNotFoundException(
							"Purpose not found for this id in facilities dropdows  : " + DropdownPurposeId));

			if (updatedVisitorDetails.getContactPersonEmp() != null) {
				int empId = updatedVisitorDetails.getContactPersonEmp().getId();
				Employee updatedContactPerson = employeeRepo.findById(empId);
				updatedVisitorDetails.setContactPersonEmp(updatedContactPerson);
			}
			
			if (existingVisitor.getVisitorIdentityCard() == null
					&& updatedVisitorDetails.getVisitorIdentityCard() != null
					&& updatedVisitorDetails.getVisitorIdentityCard().getPhysicalId() != null
					&& updatedVisitorDetails.getVisitorIdentityCard().getPhysicalId() != "") {
				
				VisitorIdentityCard existingVisitorIdentityCard = visitorIdentityCardRepo
						.findByPhysicalId(updatedVisitorDetails.getVisitorIdentityCard().getPhysicalId());
				
					if (existingVisitorIdentityCard != null) {
						if (existingVisitorIdentityCard.getIsAvailable()) {
							
							updatedVisitorDetails.setVisitorIdentityCard(existingVisitorIdentityCard);
							updatedVisitorDetails.setIsVisitorIdentityCardReturned(false);
							existingVisitorIdentityCard.setIsAvailable(false);
							visitorIdentityCardService.updateVisitorIdentityCard(existingVisitorIdentityCard);
						}
						else {
							updatedVisitorDetails.setVisitorIdentityCard(null);
						}
				}
			}
			else if (existingVisitor != null && existingVisitor.getVisitorIdentityCard() != null
					&& existingVisitor.getVisitorIdentityCard().getPhysicalId() != null
					&& existingVisitor.getVisitorIdentityCard().getPhysicalId() != "") {
				VisitorIdentityCard existingVisitorIdentityCard = visitorIdentityCardRepo
						.findByPhysicalId(existingVisitor.getVisitorIdentityCard().getPhysicalId());
				updatedVisitorDetails.setVisitorIdentityCard(existingVisitorIdentityCard);
			}
			
			if(existingVisitor.getVisitorIdentityCard() == null) {
				updatedVisitorDetails.setIsVisitorIdentityCardReturned(null);
			}

			updatedVisitorDetails.setId(visitorId);
			updatedVisitorDetails.setCreatedAt(existingVisitor.getCreatedAt());
			updatedVisitorDetails.setCreatedBy(existingVisitor.getCreatedBy());
			updatedVisitorDetails.setUpdatedAt(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
			updatedVisitorDetails.setUpdatedBy(securityUtil.getLoggedInUsername());
			updatedVisitorDetails.setIsActive(existingVisitor.getIsActive());
			updatedVisitorDetails.setDropdownObj(updatedDropdownPurpose);
			updatedVisitorDetails.setProfileImageId(existingVisitor.getProfileImageId());
			updatedVisitorDetails.setCheckInMailMessageId(existingVisitor.getCheckInMailMessageId());
			updatedVisitorDetails.setCheckOutMailMessageId(existingVisitor.getCheckOutMailMessageId());
			updatedVisitorDetails.setOtherContactPersonName(existingVisitor.getOtherContactPersonName());
			
			if(updatedVisitorDetails.getVisitorIdentityCard()!=null && updatedVisitorDetails.getIsVisitorIdentityCardReturned()!=null) {
				VisitorIdentityCard visitorIdentityCard= visitorIdentityCardService.getVisitorIdentityCard(updatedVisitorDetails.getVisitorIdentityCard().getId());
				visitorIdentityCard.setIsAvailable(updatedVisitorDetails.getIsVisitorIdentityCardReturned());
				visitorIdentityCardService.updateVisitorIdentityCard(visitorIdentityCard);
			}

			updatedVisitor = visitorRepo.save(updatedVisitorDetails);
			
			

			if (!isCheckoutMailSent) {
				visitorCheckout(updatedVisitor);
			}
		} catch (Exception e) {
			System.out.print(e);
		}
		return updatedVisitor;
	}

	/**
	 * Manages the functionality related to visitor checkout.
	 *
	 * @param visitor The visitor object who is checking out.
	 */
	public void visitorCheckout(Visitor visitor) {
		if (visitor.getContactPersonEmp()!=null && visitor.getContactPersonEmp().isActive()) {
			visitor.setCheckOutMailMessageId(
					mailService.sendVisitorCheckOutMail(visitor.getCheckInMailMessageId(), visitor));
			updateVisitor(visitor.getId(), visitor);
		}
	}

	/**
	 * Delete a visitor by Id
	 * 
	 * @param visitorId The Id of the visitor to be delete.
	 * @return A map indicating weather the visitor was deleted successfully .
	 */
	@Override
	public Map<String, Boolean> deleteVisitor(Long visitorId) {
		Map<String, Boolean> response = null;
		try {
			Visitor visitor = visitorRepo.findById(visitorId)
					.orElseThrow(() -> new ResourceNotFoundException("Visitor not found for this id : " + visitorId));
			visitorRepo.delete(visitor);
			response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
		} catch (Exception e) {
			System.out.print(e);
		}
		return response;
	}

	/**
	 * Searches visitors based on the provided search criteria and returns a
	 * paginated list of matching visitors.
	 * 
	 * @param searchOrSortCriteria        The search or sort criteria.
	 * @param searchCheckedInVisitorsOnly Boolean indicating whether to search among
	 *                                    checked-in visitors only.
	 * @param pageNum                     The page number for pagination.
	 * @param pageSize                    The page size for pagination.
	 * @return A map containing the paginated list of visitors matching the search
	 *         criteria and the total count of search results.
	 */
	@Override
	public Map<String, Object> searchVisitors(SearchOrSortCriteria searchOrSortCriteria,
			boolean searchCheckedInVisitorsOnly, int pageNum, int pageSize) {

		Map<String, Object> searchResult = new HashMap<>();

		try {

			Pageable pageRequest = null;
			String name = null;
			if (searchOrSortCriteria.getSearchByName() != null) {
				name = searchOrSortCriteria.getSearchByName().toLowerCase();
			}
			Long purpose_Id = searchOrSortCriteria.getSearchByPurposeId();
			LocalDate startDate = searchOrSortCriteria.getSearchByStartDate();
			LocalDate endDate = searchOrSortCriteria.getSearchByEndDate();
			String sortBy = searchOrSortCriteria.getSortBy();
			String orderBy = searchOrSortCriteria.getOrderBy();

			if (sortBy != null && orderBy != null) {
				if (sortBy.equals("name") && orderBy.equals("ascending")) {
					pageRequest = PageRequest.of(pageNum, pageSize,
							Sort.by(Sort.Order.asc("firstname"), Sort.Order.desc("id")));
				} else if (sortBy.equals("name") && orderBy.equals("descending")) {
					pageRequest = PageRequest.of(pageNum, pageSize,
							Sort.by(Sort.Order.desc("firstname"), Sort.Order.desc("id")));
				} else if (sortBy.equals("contactPerson") && orderBy.equals("ascending")) {
					pageRequest = PageRequest.of(pageNum, pageSize,
							Sort.by(Sort.Order.asc("contactPersonEmp.employeeName"), Sort.Order.desc("id")));
				} else if (sortBy.equals("contactPerson") && orderBy.equals("descending")) {
					pageRequest = PageRequest.of(pageNum, pageSize,
							Sort.by(Sort.Order.desc("contactPersonEmp.employeeName"), Sort.Order.desc("id")));
				} else if (sortBy.equals("date") && orderBy.equals("ascending")) {
					pageRequest = PageRequest.of(pageNum, pageSize,
							Sort.by(Sort.Order.asc("date"), Sort.Order.asc("inTime"), Sort.Order.desc("id")));
				} else {
					pageRequest = PageRequest.of(pageNum, pageSize,
							Sort.by(Sort.Order.desc("date"), Sort.Order.desc("inTime"), Sort.Order.desc("id")));
				}
			} else {
				pageRequest = PageRequest.of(pageNum, pageSize,
						Sort.by(Sort.Order.desc("date"), Sort.Order.desc("inTime"), Sort.Order.desc("id")));
			}

			boolean isDateRangeNull = true;
			if (startDate != null && endDate != null) {
				isDateRangeNull = false;
			}

			Page<Visitor> pageVisitors = this.visitorRepo.searchVisitors(name, purpose_Id, startDate, endDate,
					isDateRangeNull, searchCheckedInVisitorsOnly, pageRequest);
			List<Visitor> searchResultVisitors = pageVisitors.getContent();

			long SearchResultCount = this.visitorRepo.countSearchVisitors(name, purpose_Id, startDate, endDate,
					isDateRangeNull, searchCheckedInVisitorsOnly);

			searchResult.put("SearchResult", searchResultVisitors);
			searchResult.put("TotalSearchRecords", SearchResultCount);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			LOGGER.info(e);
			LOGGER.info(e.getCause());
		}

		return searchResult;
	}

	/**
	 * Retrieves the count of visitors records on the current day.
	 * 
	 * @return The count of visitors records on the current day.
	 */
	@Override
	public long totalVisitorsCount() {
		long totalRecords = 0;
		try {
			totalRecords = this.visitorRepo.count();
		} catch (Exception e) {
			System.out.println(e);
		}
		return totalRecords;
	}

	/**
	 * Searches for visitors based on the provided criteria for the visitor
	 * checkout.
	 *
	 * @param visitorCheckoutSearchCriteria The criteria for searching visitors for
	 *                                      checkout.
	 * @return A list of visitors matching the search criteria.
	 */
	@Override
	public List<Visitor> searchVisitorsForCheckout(VisitorCheckoutSearchCriteria visitorCheckoutSearchCriteria) {
		List<Visitor> searchResult = null;
		try {

			String firstName = null;
			if (visitorCheckoutSearchCriteria.getFirstName() != null) {
				firstName = visitorCheckoutSearchCriteria.getFirstName().toLowerCase();
			}

			String lastName = null;
			if (visitorCheckoutSearchCriteria.getLastName() != null) {
				lastName = visitorCheckoutSearchCriteria.getLastName().toLowerCase();
			}

			String phoneNum = visitorCheckoutSearchCriteria.getPhoneNum();

			searchResult = this.visitorRepo.findVisitorsByCheckoutSearchCriteria(firstName, lastName, phoneNum);
		} catch (Exception e) {
			System.out.println(e);
		}

		return searchResult;
	}

	/**
	 * Retrieves the maximum number of ID proof images allowed for a visitor.
	 *
	 * @return A map containing the maximum number of ID proof images as the value,
	 *         keyed by "maxIdProofImages".
	 */
	@Override
	public Map<String, Integer> getMaxIdProofImages() {
		Map<String, Integer> response = new HashMap<>();
		response.put("maxIdProofImages", maxIdProofImages);
		return response;
	}

}
