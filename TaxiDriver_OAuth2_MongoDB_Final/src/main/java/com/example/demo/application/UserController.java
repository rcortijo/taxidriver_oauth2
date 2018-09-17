package com.example.demo.application;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.domain.AbstractUser;
import com.example.demo.domain.Customer;
import com.example.demo.domain.Driver;
import com.example.demo.persistence.UserRepository;

public class UserController {
	
	public void registCustomer(UserDTO user) throws Exception {
		String userId=UserRepository.getNextUserId();
		
		AbstractUser userRegistered=new Customer(userId,user);				
		UserRepository.saveUser(userRegistered);				
		
	}
	
	public void registDriver(UserDTO user) throws Exception {
		String userId=UserRepository.getNextUserId();
		
		AbstractUser userRegistered=new Driver(userId,user);				
		UserRepository.saveUser(userRegistered);				
		
	}
	

	public List<UserDTO> getAllUsers() {
		List<AbstractUser> users=UserRepository.findAllUsers();
		List<UserDTO> result=new ArrayList<>();
		for(AbstractUser u: users){
			result.add(new UserDTO(u));
		}
		return result;
	}
	
	public List<UserDTO> getAllCustomers() {
		List<AbstractUser> users=UserRepository.findAllUsers();
		List<UserDTO> result=new ArrayList<>();
		for(AbstractUser u: users){
			if(u instanceof Customer)result.add(new UserDTO(u));
		}
		return result;
	}

	public List<UserDTO> getAllDrivers() {
		List<AbstractUser> users=UserRepository.findAllUsers();
		List<UserDTO> result=new ArrayList<>();
		for(AbstractUser u: users){
			if(u instanceof Driver)result.add(new UserDTO(u));
		}
		return result;
	}


	public void createAdminUser() throws Exception {
		AbstractUser user = UserRepository.findUserByEmail("admin@admin.com");
		if (user==null) {
			String userId=UserRepository.getNextUserId();
			user = new AbstractUser(userId, "admin@admin.com","admin");
			UserRepository.saveUser(user);	
		}
		
	}
	
}
