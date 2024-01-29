package com.blogApiApp.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.blogApiApp.entities.User;
import com.blogApiApp.exceptions.ResourceNotFoundException;
import com.blogApiApp.repositories.UserRepo;

public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User user= this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User","email:"+username, 0 ));
		return user;
	}
	

}
