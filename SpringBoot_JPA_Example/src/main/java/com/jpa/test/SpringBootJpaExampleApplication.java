package com.jpa.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.jpa.test.dao.UserRepo;
import com.jpa.test.entities.User;
import com.jpa.test.service.UserService;


@SpringBootApplication
public class SpringBootJpaExampleApplication implements CommandLineRunner {
	
	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringBootJpaExampleApplication.class, args);
		
		UserRepo userRepo = context.getBean(UserRepo.class);		
		
		
//		User user = new User();
//		user.setId(1);
//		user.setName("Rajeev");
//		user.setCity("HYD");
//		user.setStatus("Java programmer");
//		
//		// Saving single user 
//		User user1 = userRepo.save(user);
//		System.out.println(user1);
//		
//
//		
//		User user2 = new User();
//		user2.setId(2);
//		user2.setName("David");
//		user2.setCity("UP");
//		user2.setStatus("Python programmer");
//		
//		User user3 = new User();
//		user3.setId(3);
//		user3.setName("Rahul");
//		user3.setCity("TN");
//		user3.setStatus("C# programmer");
//		
//		//saving multiple users 
//		Iterable<User> result = userRepo.saveAll(List.of(user2, user3));
//		
//		result.forEach(e->{
//			System.out.println(e);
//		});
//				
//		
//		
//		Optional<User> optional = userRepo.findById(1);
//		User userResult = optional.get();
//		
//		userResult.setName("John");
//		userRepo.save(userResult);
//		
//		System.out.println(userResult);
//		
//		
//		System.out.println("Print all details");
//		
//		Iterable<User> itr = userRepo.findAll();
//		
//		itr.forEach(e->{
//			System.out.println(e);
//		});
//		
//		userRepo.delete(user3);
//		
//		List<User> resultUser = userRepo.findByName("David");
//		resultUser.forEach(u->System.out.println(u));
		
//        Long userCount = userRepo.getUserCount();
//        System.out.println("Total User Count: " + userCount);
	}

    @Override
    public void run(String... args) throws Exception {
        // Call the method to get user by ID
//        User user = userService.getUserById(1);
//        System.out.println("User fetched by ID: " + user);

        // Call the method to get user status by ID
        String userStatus = userService.getUserStatusById(1);
        System.out.println("User status fetched by ID: " + userStatus);
    }
}
