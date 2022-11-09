package com.ahlaoujwtspring.pro.web;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ahlaoujwtspring.pro.config.JwtTokenUtil;
import com.ahlaoujwtspring.pro.exception.EmailEmpty;
import com.ahlaoujwtspring.pro.exception.PasswordEmpty;
import com.ahlaoujwtspring.pro.exception.PasswordWrong;
import com.ahlaoujwtspring.pro.exception.UserDisabled;
import com.ahlaoujwtspring.pro.exception.UserNoExist;
import com.ahlaoujwtspring.pro.model.JwtRequest;
import com.ahlaoujwtspring.pro.model.JwtResponse;
import com.ahlaoujwtspring.pro.repository.UserRepository;
import com.ahlaoujwtspring.pro.service.JWTUserDetailsService;

import lombok.var;


@RestController
@CrossOrigin
public class JwtAuthenticationController {
	
	@Autowired 
	private UserRepository userRepo; // the interface

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private JWTUserDetailsService userDetailsService;
	
	//allowCredentials to pass token in chrome in react
	@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	public ResponseEntity<?> validateRegistration(@RequestBody JwtRequest authenticationRequest , @RequestParam("code") String code) {
		//System.out.println(authenticationRequest.getPassword());
		if (authenticationRequest.getEmail() == null) {
			throw new EmailEmpty();
		}
		if (authenticationRequest.getPassword()==null) {
			throw new PasswordEmpty();
			
		}
		
final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		
		
		
		
		
		
		if (bcryptEncoder.matches(authenticationRequest.getPassword(), userDetails.getPassword()) == false) {
			throw new PasswordWrong();
			
		}
		JwtRequest user = userRepo.findByVerificationCode(code);
			//System.out.println(user.getVerificationCode());
			//System.out.println(code);
			if (user == null) {
				throw new UserNoExist();
				
			}
			else {
				 user.setVerificationCode(null);
			        user.setEnabled(true);
			       
			        
			       
			        userRepo.save(user);
			        
				
			
			}
		
		
		
		
		
		
		
		

		
		
		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		
		
		//System.out.println(token);
		
		/*HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Authorization", "Bearer "+token);*/
		ResponseCookie cookie = ResponseCookie.from("Authorization", token).build();
		//httponly makes sure that this method can't be accessed by javascript engines

		//return ResponseEntity.ok().headers(responseHeaders).body(new JwtResponse(token));
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new JwtResponse(token));
	}
	
	@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
		//System.out.println(authenticationRequest.getPassword());
		if (authenticationRequest.getEmail() == null) {
			throw new EmailEmpty();
		}
		if (authenticationRequest.getPassword()==null) {
			throw new PasswordEmpty();
			
		}
		

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		
		
		
	
	
		
		if (bcryptEncoder.matches(authenticationRequest.getPassword(), userDetails.getPassword()) == false) {
			throw new PasswordWrong();
			
		}
		
		
		
		JwtRequest user = userRepo.findByemail(authenticationRequest.getEmail());
			
		
		
		if (user.isEnabled() == false)
		{
			throw new UserDisabled();
		}
		
		
		
		
		

		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		
		
		//System.out.println(token);
		
		/*HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Authorization", "Bearer "+token);*/
		ResponseCookie cookie = ResponseCookie.from("Authorization", token).build();
		//httponly makes sure that this method can't be accessed by javascript engines

		//return ResponseEntity.ok().headers(responseHeaders).body(new JwtResponse(token));
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new JwtResponse(token));
	}
	
	/*@RequestMapping(value = "/verifyAuth", method = RequestMethod.PUT)
	public ResponseEntity<?> verifyAuthentification(@RequestParam("code") String code) {
		//System.out.println(authenticationRequest.getPassword());
		
		
		

		
		JwtRequest user = userRepo.findByVerificationCode(code);
		System.out.println(user);
		
		if (user == null)
		{
			
			throw new UserNoExist();
		}
		else {
		
		  user.setVerificationCode(null);
	        user.setEnabled(true);
	       
	        
	        System.out.println(user);
	        userRepo.save(user);
	        
	        return ResponseEntity.ok(user);
		
		
	}
	}*/
	
	
	
	private void authenticate(String email, String password ) {
		
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			
		
	}
}

