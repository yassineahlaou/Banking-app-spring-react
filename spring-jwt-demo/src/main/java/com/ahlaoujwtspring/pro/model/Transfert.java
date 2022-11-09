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
@Table (name="transferts") //if we don't give a name it will take the same name as the class name


public class Transfert {
	
	
	@Id // identifiant attribute
	@GeneratedValue(strategy = GenerationType.IDENTITY) //this will increment the id automaticly
	
	 private Long id; //id here is a class not primitive , so we used Long not long
	
	
	@Column(name="reference", length=255, nullable = false) //give a column name
	private String reference;
	
	@Column(name="reciever_name",length=255, nullable = false) //give a column name
	private String reciever_name;
	
	@Column(name="reciever_account", length=255, nullable = false) //give a column name
	private String reciever_account;
	
	@Column(name="sender_name", length=255,nullable = false) //give a column name
	private String sender_name;
	
	@Column(name="sender_account", length=255, nullable = false) //give a column name
	private String sender_account;
	
	
	
	@Column(name="status", length=255, nullable = false) //give a column name
	private String status;
	
	@Column(name="message", length=255, nullable = false) //give a column name
	private String message;
	
	@Column(name="amount", columnDefinition="Decimal(10,2)", nullable = false) //give a column name
	private Double amount;
	
	
	@Column(name="created_at", length=255,nullable = false) //give a column name
	private String created_at;

}
