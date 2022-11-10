package com.ahlaoujwtspring.pro.web;


import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutCountroller {
	
	@RequestMapping(value = "/leave", method = RequestMethod.GET)
	public ResponseEntity<?> logout(HttpServletRequest request) throws Exception{
		String respo = "";
		ResponseCookie cookie = null;
		if (request.getCookies() != null) {
			//clear the cookies from token after logout
			respo = "Goodbye";
			cookie = ResponseCookie.from("Authorization", null).maxAge(0).build();
		}
		else {
			respo = "Log in first";
			cookie = ResponseCookie.from("Authorization", null).maxAge(0).build();
		}
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(respo);
	}
}

