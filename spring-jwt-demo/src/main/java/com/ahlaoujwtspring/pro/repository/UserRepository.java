package com.ahlaoujwtspring.pro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ahlaoujwtspring.pro.model.JwtRequest;



public interface UserRepository extends JpaRepository<JwtRequest,Long> {
	
	//public List<JwtRequest> findByusernameList(String username);
	
	public JwtRequest findByemail(String email);
	public JwtRequest findByVerificationCode(String verificationCode);
	
	
	
	
}
