package com.facilitiesportal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.facilitiesportal.model.MaterialCheckoutItem;

/**
 * Repository interface for managing Material Checkout Item entities
 */
@Repository
public interface MaterialCheckoutItemRepo extends JpaRepository<MaterialCheckoutItem, Long> {
	
	/**
	 * Finds material checkout item ids based on the provided search criteria.
	 * 
	 * @param itemId                    The ID of the item to search for. Can be null or empty to ignore this criterion.
	 * @param description               The description of the item to search for. Can be null or empty to ignore this criterion.
	 * @param actualReturnDateMin       The minimum actual return date. Can be null to ignore this criterion.
	 * @param actualReturnDateMax       The maximum actual return date. Can be null to ignore this criterion.
	 * @param isGlobalItemSearch        Boolean flag to enable global search across item fields.
	 * @param itemGlobalSearch          The global search term for item fields. Can be null or empty to ignore this criterion.
	 * @return                          A list of material checkout item ids matching the search criteria.
	 */
	@Query("SELECT i.id FROM MaterialCheckoutItem i WHERE "
            + "((:isGlobalItemSearch = true AND ( "
            + "   :itemGlobalSearch IS NULL OR :itemGlobalSearch = '' OR LOWER(i.itemId) LIKE CONCAT('%', LOWER(:itemGlobalSearch), '%') OR"
            + "   LOWER(i.description) LIKE CONCAT('%', LOWER(:itemGlobalSearch), '%')"
            + ")) "
            + " OR (:isGlobalItemSearch = false AND ("
            + "   (:itemId IS NULL OR :itemId = '' OR LOWER(i.itemId) LIKE CONCAT('%', LOWER(:itemId), '%')) AND"
            + "   (:description IS NULL OR :description = '' OR LOWER(i.description) LIKE CONCAT('%', LOWER(:description), '%'))"
            + " )))"
			+ "AND ( :actualReturnDateMin IS NULL OR :actualReturnDateMin = '' OR :actualReturnDateMax IS NULL OR :actualReturnDateMax = '' OR ( DATE(i.actualReturnDateTime) >= DATE(:actualReturnDateMin)  AND DATE(i.actualReturnDateTime) <= DATE(:actualReturnDateMax) ) ) ")
	List<Long> findMaterialCheckoutItemBySearchCriteria(
	        @Param("itemId") String itemId,
	        @Param("description") String description,
	        @Param("actualReturnDateMin") String actualReturnDateMin,
	        @Param("actualReturnDateMax") String actualReturnDateMax,
	        @Param("isGlobalItemSearch") boolean isGlobalItemSearch,
            @Param("itemGlobalSearch") String itemGlobalSearch);

	
	/**
	 * Finds a MaterialCheckoutItem by its ID.
	 * 
	 * @param id The ID of the MaterialCheckoutItem to search for.
	 * @return The MaterialCheckoutItem with the specified ID, or null if not found.
	 */
	MaterialCheckoutItem findById(long id);

	/**
	 * Finds all MaterialCheckoutItems associated with a specific MaterialCheckoutGatePass.
	 * 
	 * @param materialGatepassId The ID of the MaterialCheckoutGatePass to search for associated items.
	 * @return A list of MaterialCheckoutItems linked to the provided MaterialCheckoutGatePass ID.
	 */
	List<MaterialCheckoutItem> findAllByMaterialCheckoutGatePass_IdOrderByIdDesc(long materialGatepassId);
	  
	  /**
		 * Calculates the status of a material checkout gate pass based on the return status of its associated items.
		 * 
		 * @param gatePassId The ID of the material checkout gate pass.
		 * @return A String representing the status of the gate pass, which can be "Returned", "Partly Returned", or "Yet to be Returned".
		 */
	  @Query("SELECT " +
	            "CASE " +
	            "   WHEN COUNT(*) = SUM(CASE WHEN i.actualReturnDateTime IS NOT NULL THEN 1 ELSE 0 END) THEN " +
	            "       CASE " +
	            "           WHEN :expectedReturnDate IS NOT NULL AND :expectedReturnDate <> '' AND MAX(DATE(i.actualReturnDateTime)) > DATE(:expectedReturnDate) THEN 'Delayed Return' " +
	            "           ELSE 'Returned' " +
	            "       END " +
	            "   WHEN COUNT(*) = SUM(CASE WHEN i.actualReturnDateTime IS NULL AND i.totalReturnedQuantity = 0 THEN 1 ELSE 0 END) THEN 'Yet to be Returned' " +
	            "   ELSE 'Partially Returned' " +
	            "END AS status " +
	            "FROM MaterialCheckoutItem i " +
	            "WHERE i.materialCheckoutGatePass.id = :gatePassId")
		String getGatePassStatus(Long gatePassId,  String expectedReturnDate);
	}
