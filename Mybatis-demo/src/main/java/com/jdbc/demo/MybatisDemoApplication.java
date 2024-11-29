package com.jdbc.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jdbc.demo.model.UserTest;
import com.jdbc.demo.service.IUserService;

@SpringBootApplication
public class MybatisDemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MybatisDemoApplication.class, args);
	}
	
	@Autowired
	private IUserService userService;

	@Override
	public void run(String... args) throws Exception {
		UserTest user = new UserTest(6, "HYD" , "Ria" , "Tester");
//		userService.addUser(user);
		
		userService.getAll().forEach(System.out::println);	
	}

}
