package com.ahlaoujwtspring.pro.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.ExpiredJwtException;

@RestController
public class WelcomeController {
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ResponseEntity<?> firstPage(HttpServletRequest request)  throws Exception {
	     
		//String requestTokenHeader = null;
		String respo = "";
		//int count = 0;
		if (request.getCookies() != null) {
			respo = "Hi there";
		}
		
		else {
			respo = "Log in first";
			
		}
		

		
		return ResponseEntity.ok().body(respo);
	}
	
	
	/*public String readCookie(
	    @CookieValue(name = "Authorization", defaultValue = "null") String tokenValue) {
	    return tokenValue;
	}*/

}
