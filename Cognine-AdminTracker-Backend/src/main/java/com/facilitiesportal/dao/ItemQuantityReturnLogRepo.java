package com.facilitiesportal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.facilitiesportal.model.ItemQuantityReturnLog;

/**
 * Repository interface for managing Item Quantity Return Log entities
 */
@Repository
public interface ItemQuantityReturnLogRepo extends JpaRepository<ItemQuantityReturnLog, Long> {
	
    /**
     * Retrieves a list of ItemQuantityReturnLog entities where the associated item's ID matches the given itemId.
     * 
     * @param itemId The ID of the item for which the return logs are to be retrieved.
     * @return A list of ItemQuantityReturnLog entities with the specified item ID.
     */
	@Query("SELECT log FROM ItemQuantityReturnLog log WHERE log.item.id = :itemId")
    List<ItemQuantityReturnLog> findByItemId(Long itemId);

}
