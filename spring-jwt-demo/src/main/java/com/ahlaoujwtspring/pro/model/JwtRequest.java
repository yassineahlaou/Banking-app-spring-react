package com.ahlaoujwtspring.pro.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data //generate getters and setters
@NoArgsConstructor
@AllArgsConstructor
@ToString
//les annotations help hibernate to create table (mapping)
@Entity  //means this class is for a table in database
@Table (name="users") //if we don't give a name it will take the same name as the class name
public class JwtRequest  {


	@Id // identifiant attribute
	@GeneratedValue(strategy = GenerationType.IDENTITY) //this will increment the id automaticly
	
	 private Long id; //id here is a class not primitive , so we used Long not long
	
	@Column(name="email", length=255, nullable = false) //give a column name
	//@Email(message = "Email is not valid", regexp = "")

	
	
	
	private String email;
	@Column(name="firstname", length=255,nullable = false) //give a column name
	
	
	
		
		private String firstname;
	@Column(name="lastname", length=255, nullable = false) //give a column name
	
	
	
	
	private String lastname;
	@Column(name="phone", length=255, nullable = false) //give a column name
	
	
	
	
	private String phone;
	
	@Column(name="password", length=191, nullable = false) //give a column name
	
	
	
	private String password;
	
	@Column(name = "verification_code", length = 64)
    private String verificationCode;
	
	 private boolean enabled;
	
	
}

