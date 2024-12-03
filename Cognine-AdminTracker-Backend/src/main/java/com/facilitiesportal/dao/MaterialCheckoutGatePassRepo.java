package com.facilitiesportal.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.facilitiesportal.model.MaterialCheckoutGatePass;


/**
 * Repository interface for managing Material Checkout Gate Pass entities
 */
@Repository
public interface MaterialCheckoutGatePassRepo extends JpaRepository<MaterialCheckoutGatePass, Long> {

	/**
	 * Retrieves a Material Checkout Gate Pass entity by its unique identifier.
	 * 
	 * @param id The unique identifier of the Material Checkout Gate Pass entity.
	 * @return The Material Checkout Gate Pass entity if found, otherwise null.
	 */
	MaterialCheckoutGatePass findById(long id);
	
	/**
	 * Counts the number of Material Checkout Gate Pass entities with the specified type,
	 * ignoring case sensitivity.
	 * 
	 * @param type The type to match, case insensitive.
	 * @return The count of Material Checkout Gate Pass entities with the specified type.
	 */
    @Query("SELECT COUNT(g) FROM MaterialCheckoutGatePass g WHERE LOWER(g.type) = LOWER(:type)")
    long countByTypeIgnoreCase(@Param("type") String type);
    
    /**
     * Retrieves a list of material checkout gate passes based on the provided search criteria.
     *
     * @param gatePassId                  The ID of the gate pass to search for. Can be null or empty to ignore this criterion.
     * @param empNameList                 A list of employee names who sent the gate pass. Can be null to ignore this criterion.
     * @param sentTo                      The recipient of the gate pass. Can be null or empty to ignore this criterion.
     * @param sentToAddress               The recipient address of the gate pass to search for. Can be null or empty to ignore this criterion.
     * @param statusList                  A list of statuses of the gate pass. Can be null to ignore this criterion.
     * @param type                        The type of the gate pass. Can be null or empty to ignore this criterion.
     * @param itemTypeId                  The itemTypeId of the gate pass. Can be null to ignore this criterion.
     * @param sentOnDateMin               The minimum sent on date. Can be null to ignore this criterion.
     * @param sentOnDateMax               The maximum sent on date. Can be null to ignore this criterion.
     * @param expectedReturnDateMin       The minimum expected return date. Can be null to ignore this criterion.
     * @param expectedReturnDateMax       The maximum expected return date. Can be null to ignore this criterion.
     * @param itemId                      The ID of the item associated with the gate pass. Can be null or empty to ignore this criterion.
     * @param description                 The description of the item associated with the gate pass. Can be null or empty to ignore this criterion.
     * @param actualReturnDateMin         The minimum actual return date. Can be null to ignore this criterion.
     * @param actualReturnDateMax         The maximum actual return date. Can be null to ignore this criterion.
     * @param isGlobalGatePassSearch      Boolean flag to enable global search across gate pass fields.
     * @param gatePassGlobalSearch        The global search term for gate pass fields. Can be null or empty to ignore this criterion.
     * @param isGlobalItemSearch          Boolean flag to enable global search across item fields.
     * @param itemGlobalSearch            The global search term for item fields. Can be null or empty to ignore this criterion.
     * @param isSortByGatePassId          Boolean flag to enable sorting by gate pass ID.
     * @param orderBy                     The sorting order (ascending or descending). Can be null to ignore sorting.
     * @param pageable                    Pagination information.
     * @return                            A list of material checkout gate passes matching the search criteria.
     */
    @Query("SELECT gp FROM MaterialCheckoutGatePass gp WHERE "
            + " (((:isGlobalGatePassSearch = true AND ("
            + "  :gatePassGlobalSearch IS NULL OR :gatePassGlobalSearch = '' OR LOWER(gp.gatePassId) LIKE CONCAT('%', LOWER(:gatePassGlobalSearch), '%') OR"
            + "    LOWER(gp.sentToAddress) LIKE CONCAT('%', LOWER(:gatePassGlobalSearch), '%') OR"
            + "    LOWER(gp.sentTo) LIKE CONCAT('%', LOWER(:gatePassGlobalSearch), '%'))"
            + " ) OR (:isGlobalGatePassSearch = false AND ("
            + "   (:gatePassId IS NULL OR :gatePassId = '' OR LOWER(gp.gatePassId) LIKE CONCAT('%', LOWER(:gatePassId), '%')) AND"
            + "   (:sentToAddress IS NULL OR :sentToAddress = '' OR LOWER(gp.sentToAddress) LIKE CONCAT('%', LOWER(:sentToAddress), '%')) AND"
            + "   (:sentTo IS NULL OR :sentTo = '' OR LOWER(gp.sentTo) LIKE CONCAT('%', LOWER(:sentTo), '%')))))"
            + " AND ( (:empNameList) IS NULL OR LOWER(gp.sentByEmp.employeeName) IN (:empNameList))"
            + " AND ( (:statusList) IS NULL OR LOWER(gp.status) IN (:statusList))"
            + " AND (:type IS NULL OR :type = '' OR LOWER(gp.type) = LOWER(:type))"
            + " AND (:itemTypeId IS NULL OR gp.itemTypeId.id = :itemTypeId)"
            + " AND (:sentOnDateMin IS NULL OR :sentOnDateMin = '' OR :sentOnDateMax IS NULL OR :sentOnDateMax = '' OR gp.sentOn IS NULL OR (DATE(gp.sentOn) >= DATE(:sentOnDateMin) AND DATE(gp.sentOn) <= DATE(:sentOnDateMax)))"
            + " AND (:expectedReturnDateMin IS NULL OR :expectedReturnDateMin = '' OR :expectedReturnDateMax IS NULL OR :expectedReturnDateMax = '' OR (gp.expectedReturnDate >= DATE(:expectedReturnDateMin) AND gp.expectedReturnDate <= DATE(:expectedReturnDateMax)))"
            + " AND gp.id IN (SELECT DISTINCT i.materialCheckoutGatePass.id FROM MaterialCheckoutItem i WHERE "
            + "((:isGlobalItemSearch = true AND ( "
            + "   :itemGlobalSearch IS NULL OR :itemGlobalSearch = '' OR LOWER(i.itemId) LIKE CONCAT('%', LOWER(:itemGlobalSearch), '%') OR"
            + "    LOWER(i.description) LIKE CONCAT('%', LOWER(:itemGlobalSearch), '%')"
            + ")) "
            + " OR (:isGlobalItemSearch = false AND ("
            + "   (:itemId IS NULL OR :itemId = '' OR LOWER(i.itemId) LIKE CONCAT('%', LOWER(:itemId), '%')) AND"
            + "   (:description IS NULL OR :description = '' OR LOWER(i.description) LIKE CONCAT('%', LOWER(:description), '%'))"
            + " )))"
            + "   AND (:actualReturnDateMin IS NULL OR :actualReturnDateMin = '' OR :actualReturnDateMax IS NULL OR :actualReturnDateMax = '' OR (DATE(i.actualReturnDateTime) >= DATE(:actualReturnDateMin) AND DATE(i.actualReturnDateTime) <= DATE(:actualReturnDateMax)))"
            + "))"
            + "ORDER BY "
            + "  CASE WHEN :isSortByGatePassId = true AND :orderBy IS NOT NULL AND LOWER(CAST(:orderBy AS string)) = 'ascending' THEN gp.type END ASC, "
            + "  CASE WHEN :isSortByGatePassId = true AND :orderBy IS NOT NULL AND LOWER(CAST(:orderBy AS string)) = 'descending' THEN gp.type END DESC, "
            + "  CASE WHEN :isSortByGatePassId = true AND :orderBy IS NOT NULL AND LOWER(CAST(:orderBy AS string)) = 'ascending' AND gp.gatePassId LIKE 'RGP%' THEN CAST(REPLACE(gp.gatePassId, 'RGP', '') AS integer) "
            + "       WHEN :isSortByGatePassId = true AND :orderBy IS NOT NULL AND LOWER(CAST(:orderBy AS string)) = 'ascending' AND gp.gatePassId LIKE 'NRGP%' THEN CAST(REPLACE(gp.gatePassId, 'NRGP', '') AS integer) END ASC, "
            + "  CASE WHEN :isSortByGatePassId = true AND :orderBy IS NOT NULL AND LOWER(CAST(:orderBy AS string)) = 'descending' AND gp.gatePassId LIKE 'RGP%' THEN CAST(REPLACE(gp.gatePassId, 'RGP', '') AS integer) "
            + "       WHEN :isSortByGatePassId = true AND :orderBy IS NOT NULL AND LOWER(CAST(:orderBy AS string)) = 'descending' AND gp.gatePassId LIKE 'NRGP%' THEN CAST(REPLACE(gp.gatePassId, 'NRGP', '') AS integer) END DESC"
            + "")
    List<MaterialCheckoutGatePass> findMaterialCheckoutGatePassesBySearchCriteria(
            @Param("gatePassId") String gatePassId,
            @Param("empNameList") List<String> empNameList,
            @Param("sentTo") String sentTo,
            @Param("sentToAddress") String sentToAddress,
            @Param("statusList") List<String> statusList,
            @Param("type") String type,
            @Param("itemTypeId") Long itemTypeId,
            @Param("sentOnDateMin") String sentOnDateMin,
            @Param("sentOnDateMax") String sentOnDateMax,
            @Param("expectedReturnDateMin") String expectedReturnDateMin,
            @Param("expectedReturnDateMax") String expectedReturnDateMax,
            @Param("itemId") String itemId,
            @Param("description") String description,
            @Param("actualReturnDateMin") String actualReturnDateMin,
            @Param("actualReturnDateMax") String actualReturnDateMax,
            @Param("isGlobalGatePassSearch") boolean isGlobalGatePassSearch,
            @Param("gatePassGlobalSearch") String gatePassGlobalSearch,
            @Param("isGlobalItemSearch") boolean isGlobalItemSearch,
            @Param("itemGlobalSearch") String itemGlobalSearch,
            @Param("isSortByGatePassId") boolean isSortByGatePassId,
            @Param("orderBy") String orderBy,
           Pageable pageable);



    /**
     * Counts the number of material checkout gate passes based on the provided search criteria.
     * 
     * @param gatePassId                   The ID of the gate pass to search for. Can be null or empty to ignore this criterion.
     * @param empNameList                  A list of employee names who sent the gate pass. Can be null to ignore this criterion.
     * @param sentTo                       The recipient of the gate pass. Can be null or empty to ignore this criterion.
     * @param sentToAddress                 The recipient address of the gate pass to search for. Can be null or empty to ignore this criterion.
     * @param statusList                   A list of statuses of the gate pass. Can be null to ignore this criterion.
     * @param type                         The type of the gate pass. Can be null or empty to ignore this criterion.
     * @param itemTypeId                   The itemTypeId of the gate pass. Can be null to ignore this criterion.
     * @param sentOnDateMin                The minimum sent on date. Can be null to ignore this criterion.
     * @param sentOnDateMax                The maximum sent on date. Can be null to ignore this criterion.
     * @param expectedReturnDateMin        The minimum expected return date. Can be null to ignore this criterion.
     * @param expectedReturnDateMax        The maximum expected return date. Can be null to ignore this criterion.
     * @param itemId                       The ID of the item associated with the gate pass. Can be null or empty to ignore this criterion.
     * @param description                  The description of the item associated with the gate pass. Can be null or empty to ignore this criterion.
     * @param actualReturnDateMin          The minimum actual return date. Can be null to ignore this criterion.
     * @param actualReturnDateMax          The maximum actual return date. Can be null to ignore this criterion.
     * @param isGlobalGatePassSearch       Boolean flag to enable global search across gate pass fields.
     * @param gatePassGlobalSearch         The global search term for gate pass fields. Can be null or empty to ignore this criterion.
     * @param isGlobalItemSearch           Boolean flag to enable global search across item fields.
     * @param itemGlobalSearch             The global search term for item fields. Can be null or empty to ignore this criterion.
     * @return                             The count of material checkout gate passes matching the search criteria.
     */
    @Query("SELECT COUNT(gp) FROM MaterialCheckoutGatePass gp WHERE "
            + " (((:isGlobalGatePassSearch = true AND ("
            + "   :gatePassGlobalSearch IS NULL OR :gatePassGlobalSearch = '' OR LOWER(gp.gatePassId) LIKE CONCAT('%', LOWER(:gatePassGlobalSearch), '%') OR"
            + "    LOWER(gp.sentToAddress) LIKE CONCAT('%', LOWER(:gatePassGlobalSearch), '%') OR"
            + "    LOWER(gp.sentTo) LIKE CONCAT('%', LOWER(:gatePassGlobalSearch), '%'))"
            + " ) OR (:isGlobalGatePassSearch = false AND ("
            + "   (:gatePassId IS NULL OR :gatePassId = '' OR LOWER(gp.gatePassId) LIKE CONCAT('%', LOWER(:gatePassId), '%')) AND"
            + "   (:sentToAddress IS NULL OR :sentToAddress = '' OR LOWER(gp.sentToAddress) LIKE CONCAT('%', LOWER(:sentToAddress), '%')) AND"
            + "   (:sentTo IS NULL OR :sentTo = '' OR LOWER(gp.sentTo) LIKE CONCAT('%', LOWER(:sentTo), '%')))))"
            + " AND ( (:empNameList) IS NULL OR LOWER(gp.sentByEmp.employeeName) IN (:empNameList))"
            + " AND ( (:statusList) IS NULL OR LOWER(gp.status) IN (:statusList))"
            + " AND (:type IS NULL OR :type = '' OR LOWER(gp.type) = LOWER(:type))"
            + " AND (:itemTypeId IS NULL OR gp.itemTypeId.id = :itemTypeId)"
            + " AND (:sentOnDateMin IS NULL OR :sentOnDateMin = '' OR :sentOnDateMax IS NULL OR :sentOnDateMax = '' OR gp.sentOn IS NULL OR (DATE(gp.sentOn) >= DATE(:sentOnDateMin) AND DATE(gp.sentOn) <= DATE(:sentOnDateMax)))"
            + " AND (:expectedReturnDateMin IS NULL OR :expectedReturnDateMin = '' OR :expectedReturnDateMax IS NULL OR :expectedReturnDateMax = '' OR (gp.expectedReturnDate >= DATE(:expectedReturnDateMin) AND gp.expectedReturnDate <= DATE(:expectedReturnDateMax)))"
            + " AND gp.id IN (SELECT DISTINCT i.materialCheckoutGatePass.id FROM MaterialCheckoutItem i WHERE "
            + "((:isGlobalItemSearch = true AND ( "
            + "   :itemGlobalSearch IS NULL OR :itemGlobalSearch = '' OR LOWER(i.itemId) LIKE CONCAT('%', LOWER(:itemGlobalSearch), '%') OR"
            + "    LOWER(i.description) LIKE CONCAT('%', LOWER(:itemGlobalSearch), '%')"
            + ")) "
            + " OR (:isGlobalItemSearch = false AND ("
            + "   (:itemId IS NULL OR :itemId = '' OR LOWER(i.itemId) LIKE CONCAT('%', LOWER(:itemId), '%')) AND"
            + "   (:description IS NULL OR :description = '' OR LOWER(i.description) LIKE CONCAT('%', LOWER(:description), '%'))"
            + " )))"
            + "   AND (:actualReturnDateMin IS NULL OR :actualReturnDateMin = '' OR :actualReturnDateMax IS NULL OR :actualReturnDateMax = '' OR (DATE(i.actualReturnDateTime) >= DATE(:actualReturnDateMin) AND DATE(i.actualReturnDateTime) <= DATE(:actualReturnDateMax)))"
            + "))")
    long countMaterialCheckoutGatePassesBySearchCriteria(@Param("gatePassId") String gatePassId,
            @Param("empNameList") List<String> empNameList,
            @Param("sentTo") String sentTo,
            @Param("sentToAddress") String sentToAddress,
            @Param("statusList") List<String> statusList,
            @Param("type") String type,
            @Param("itemTypeId") Long itemTypeId,
            @Param("sentOnDateMin") String sentOnDateMin,
            @Param("sentOnDateMax") String sentOnDateMax,
            @Param("expectedReturnDateMin") String expectedReturnDateMin, 
            @Param("expectedReturnDateMax") String expectedReturnDateMax,
            @Param("itemId") String itemId,
            @Param("description") String description,
            @Param("actualReturnDateMin") String actualReturnDateMin, 
            @Param("actualReturnDateMax") String actualReturnDateMax,
            @Param("isGlobalGatePassSearch") boolean isGlobalGatePassSearch,
            @Param("gatePassGlobalSearch") String gatePassGlobalSearch,
            @Param("isGlobalItemSearch") boolean isGlobalItemSearch,
            @Param("itemGlobalSearch") String itemGlobalSearch );

    
    /**
     * Retrieves all distinct employee IDs and names who sent the material checkout, sorted by employee names in ascending order.
     * 
     * @return A list of maps, each containing the distinct employee ID and name, sorted by employee name in ascending order.
     */
    @Query("SELECT DISTINCT g.sentByEmp.id AS id, g.sentByEmp.employeeName AS employeeName FROM MaterialCheckoutGatePass g ORDER BY g.sentByEmp.employeeName ASC")
    List<Map<String, Object>> findDistinctSentByEmployeeIdsAndNames();
}
