package com.ahlaoujwtspring.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahlaoujwtspring.pro.model.Account;
import com.ahlaoujwtspring.pro.model.JwtRequest;


public interface AccountRepository  extends JpaRepository<Account,Long>{
	
	public Account findByaccountname(String accountname);

}
