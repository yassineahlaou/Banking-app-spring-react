package com.ahlaoujwtspring.pro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table (name="accounts") //if we don't give a name it will take the same name as the class name

public class Account {
	
	
	@Id // identifiant attribute
	@GeneratedValue(strategy = GenerationType.IDENTITY) //this will increment the id automaticly
	
	 private Long id; //id here is a class not primitive , so we used Long not long
	
	@Column(name="accountnumber",length=255, nullable = false) //give a column name
	private String accountnumber;
	
	@Column(name="accountname", length=255, nullable = false) //give a column name
	private String accountname;
	
	@Column(name="accounttype", length=255,nullable = false) //give a column name
	private String accounttype;
	
	@Column(name="balance", columnDefinition="Decimal(10,2)", nullable = false) //give a column name
	private Double balance;
	
	@Column(name="created_at", length=255,nullable = false) //give a column name
	private String created_at;

}
