package com.example.demo.application;

import com.example.demo.domain.AbstractUser;
import com.google.gson.annotations.Expose;

public class UserDTO {
	@Expose
	private String username;	
	private String password;
	
	public UserDTO(AbstractUser u) {
		this.username=u.getUsername();
		this.password=u.getPassword();
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
}
