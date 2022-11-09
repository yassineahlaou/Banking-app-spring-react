package com.ahlaoujwtspring.pro.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ahlaoujwtspring.pro.exception.EmailNoExist;

import com.ahlaoujwtspring.pro.model.JwtRequest;
import com.ahlaoujwtspring.pro.repository.UserRepository;

@Service
public class JWTUserDetailsService implements UserDetailsService {
	
	@Autowired 
	private UserRepository userRepo; // the interface

	@Override
	public UserDetails loadUserByUsername(String email) {
		
		JwtRequest emailexist = userRepo.findByemail(email);
		if (emailexist != null) {
			return new User(emailexist.getEmail(), emailexist.getPassword(),
					new ArrayList<>());
		} else {
			System.out.println("User not found with username: " + email);
			throw new EmailNoExist();
			
		}
	}
}


