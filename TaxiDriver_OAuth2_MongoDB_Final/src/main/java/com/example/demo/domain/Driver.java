package com.example.demo.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.demo.application.UserDTO;
import com.example.demo.utilities.ConstantUtilities;

public class Driver extends AbstractUser {

	public Driver(){
		
	}
	public Driver(String id, UserDTO user) {
		super(id,user);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(ConstantUtilities.DRIVER_ROLE));
		return authorities;
	}

}
