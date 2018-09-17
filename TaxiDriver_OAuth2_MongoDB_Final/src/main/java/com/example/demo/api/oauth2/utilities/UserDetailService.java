package com.example.demo.api.oauth2.utilities;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.persistence.UserRepository;

@Service("userDetailsService")
public class UserDetailService implements UserDetailsService {

	private UserRepository userRepository=new UserRepository();;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findOneByUsername(username);
	}
}
