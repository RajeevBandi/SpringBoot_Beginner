package com.jpa.test.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jpa.test.entities.User;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {
    public List<User> findByName(String name);
    
//    @Procedure(procedureName = "get_user_count")
//    Long getUserCount();
    
    // Call the stored procedure to get user by ID
//    @Transactional
//    @Query(value = "CALL get_user_by_id(:userId)", nativeQuery = true)
//    User getUserById(@Param("userId") int userId);
    
    // Call the stored procedure to get user status by ID
    @Transactional
    @Query(value = "CALL get_user_status_by_id(:userId, :userStatus)", nativeQuery = true)
    void getUserStatusById(@Param("userId") int userId, @Param("userStatus") String userStatus);
}
