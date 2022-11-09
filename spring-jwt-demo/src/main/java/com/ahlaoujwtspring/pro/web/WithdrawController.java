package com.ahlaoujwtspring.pro.web;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ahlaoujwtspring.pro.exception.AccountNoExist;
import com.ahlaoujwtspring.pro.exception.AccountNotEnoughBalance;
import com.ahlaoujwtspring.pro.exception.AccountNumberEmpty;
import com.ahlaoujwtspring.pro.exception.AccountnameEmpty;
import com.ahlaoujwtspring.pro.exception.AmountEmpty;
import com.ahlaoujwtspring.pro.exception.NotEnoughBalance;
import com.ahlaoujwtspring.pro.exception.WrongAccountNumber;
import com.ahlaoujwtspring.pro.model.Account;
import com.ahlaoujwtspring.pro.model.Deposit;
import com.ahlaoujwtspring.pro.model.Withdraw;
import com.ahlaoujwtspring.pro.repository.AccountRepository;
import com.ahlaoujwtspring.pro.repository.DepositRepository;
import com.ahlaoujwtspring.pro.repository.WithdrawRepository;

@RestController
public class WithdrawController {
	
	@Autowired 
	private WithdrawRepository withdrawRepo; // the interface
	
	@Autowired 
	private AccountRepository accountRepo;
	
	
	 @CrossOrigin(origins = "http://localhost:3000")
		@PostMapping("/makeWithdraw")
	    public ResponseEntity<?> makeWithdraw(@RequestBody Withdraw withdraw ) throws URISyntaxException  {
		 
		 //.equals not allowed if input is empty cause it will throw .equal not accepting null
		 if (withdraw.getAccount_name() == null) {
				throw new AccountnameEmpty();
			}
			if (withdraw.getAccount_number()== null) {
				throw new AccountNumberEmpty();
			}
		 
		
			
			
			if (withdraw.getAmount() == null) {
				throw new AmountEmpty();
				
			}
			
			Account account = accountRepo.findByaccountname(withdraw.getAccount_name());
			
			if (account == null) {
				throw new AccountNoExist();
			}
			else {
				if (!account.getAccountnumber().equals(withdraw.getAccount_number())) {
					throw new WrongAccountNumber();
				}
				if (account.getBalance() < withdraw.getAmount()) {
					throw new AccountNotEnoughBalance();
				}
				
				
				
			}
			
			
		
			
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); 
			String formatDateTime = now.format(format);
			withdraw.setCreated_at(formatDateTime);
			  Random rand = new Random();
			    String reference_number = "REF";
			    for (int i = 0; i < 12; i++)
			    {
			        int n = rand.nextInt(10) + 0;
			        reference_number += Integer.toString(n);
			    }
			    reference_number += "WITH";
			   
			withdraw.setReference(reference_number);
			
			withdraw.setStatus("Success");
			withdraw.setMessage("Withdraw validated");
			
			
			
			
			Double balanceAccount_update = account.getBalance() - withdraw.getAmount();
			
			account.setBalance(balanceAccount_update);
			
			
			
			Withdraw savedWithdraw = withdrawRepo.save(withdraw);
			
			
			
	
		
		 	
		 
		 return ResponseEntity.ok().body(savedWithdraw);
	 }

}
