package com.jdbc.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jdbc.demo.model.UserTest;

@Mapper
public interface IUserRepository {
	
	@Insert("INSERT INTO users_test(id, city, name, status) VALUES (#{id}, #{city}, #{name}, #{status})")
	void addUser(UserTest user);
	
	@Update("UPDATE  users_test SET city = #{city} WHERE id = #{id}")
	void updateUser(UserTest user);
	
	@Delete("DELETE from users_test WHERE id = #{id}")
	void deleteUser(int id);
	
	@Select("SELECT * FROM users_test WHERE id = #{id}")
	@Results({ 
		@Result(property = "name", column = "name"),
		@Result(property = "city", column = "city")
		})
	UserTest getById(int id);
	
	@Select("SELECT * FROM users_test")
	List<UserTest> getAll();
	
	@Select("SELECT * FROM users_test WHERE city = #{city}")
	List<UserTest> getByCity(String city);
	
	@Select("SELECT * FROM users_test WHERE status = #{status}")
	List<UserTest> getByStatus(String status);

}
