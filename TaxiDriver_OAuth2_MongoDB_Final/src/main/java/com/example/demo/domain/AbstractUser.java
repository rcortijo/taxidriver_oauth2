package com.example.demo.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.application.UserDTO;
import com.example.demo.utilities.ConstantUtilities;
import com.example.demo.utilities.Encryptor;

@Entity("users")
public class AbstractUser implements UserDetails{


	private static final long serialVersionUID = 1L;

	@Id	
	private String id;
	
	private String username;
	
	private String password;
	
	private boolean enabled;
	
	
	public AbstractUser(){
		
	}
	public AbstractUser(String id, UserDTO user) {
		this.id=id;
		this.username=user.getUsername();
		this.password=Encryptor.encryptPassword(user.getPassword());
		this.enabled=true;
	}
	
	public AbstractUser(String id, String email, String password) {
		this.id=id;
		this.username=email;
		this.password=Encryptor.encryptPassword(password);
		this.enabled=true;
	}


	
	public AbstractUser(JSONObject principalJson) throws JSONException {
        this.id=principalJson.getString("id");    
        this.password = principalJson.getString("password");
        this.username = principalJson.getString("username");
	}

    
    
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();		
		authorities.add(new SimpleGrantedAuthority(ConstantUtilities.ADMIN_ROLE));
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
