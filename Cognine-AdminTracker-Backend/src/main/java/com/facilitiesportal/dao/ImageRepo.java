package com.facilitiesportal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facilitiesportal.model.Image;

/**
 * Repository interface for managing Image entities in the database.
 */
@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {

}
