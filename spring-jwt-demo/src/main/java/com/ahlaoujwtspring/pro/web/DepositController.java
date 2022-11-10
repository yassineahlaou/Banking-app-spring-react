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
import com.ahlaoujwtspring.pro.exception.AccountNumberEmpty;
import com.ahlaoujwtspring.pro.exception.AccountnameEmpty;
import com.ahlaoujwtspring.pro.exception.AmountEmpty;
import com.ahlaoujwtspring.pro.exception.WrongAccountNumber;
import com.ahlaoujwtspring.pro.model.Account;
import com.ahlaoujwtspring.pro.model.Deposit;
import com.ahlaoujwtspring.pro.repository.AccountRepository;
import com.ahlaoujwtspring.pro.repository.DepositRepository;


@RestController
public class DepositController {
	
	@Autowired 
	private DepositRepository depositRepo; // the interface
	
	@Autowired 
	private AccountRepository accountRepo;
	
	
	 @CrossOrigin(origins = "http://localhost:3000")
	 @PostMapping("/makeDeposit")
	 public ResponseEntity<?> makeDeposit(@RequestBody Deposit deposit ) throws URISyntaxException  {
		 //.equals not allowed if input is empty cause it will throw .equal not accepting null
		 if (deposit.getAccount_name() == null) {
				throw new AccountnameEmpty();
			}
			if (deposit.getAccount_number()== null) {
				throw new AccountNumberEmpty();
			}
			if (deposit.getAmount() == null) {
				throw new AmountEmpty();
			}
			
			Account account = accountRepo.findByaccountname(deposit.getAccount_name());
			
			if (account == null) {
				throw new AccountNoExist();
			}
			else {
				if (!account.getAccountnumber().equals(deposit.getAccount_number())) {
					throw new WrongAccountNumber();
				}
			}
			
			//format deposit created date
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); 
			String formatDateTime = now.format(format);
			deposit.setCreated_at(formatDateTime);
			
			//random unique reference number
			Random rand = new Random();
		    String reference_number = "REF";
		    for (int i = 0; i < 12; i++)
		    {
		        int n = rand.nextInt(10) + 0;
		        reference_number += Integer.toString(n);
		    }
		    reference_number += "DEP"; 
			deposit.setReference(reference_number);
			
			//set deposit status
			deposit.setStatus("Success");
			
			//set Deposit message
			deposit.setMessage("Deposit validated");
			
			//set deposit balance
			Double balanceAccount_update = account.getBalance() + deposit.getAmount();
			account.setBalance(balanceAccount_update);
			
			//save deposit
			Deposit savedDeposit = depositRepo.save(deposit);
			return ResponseEntity.ok().body(savedDeposit);
	 }

}
