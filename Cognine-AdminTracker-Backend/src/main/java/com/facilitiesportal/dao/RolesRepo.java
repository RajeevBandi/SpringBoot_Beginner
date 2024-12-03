package com.facilitiesportal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facilitiesportal.model.Roles;

/**
 * Repository interface for managing Roles entities
 */
@Repository
public interface RolesRepo extends JpaRepository<Roles, Long> {

}
