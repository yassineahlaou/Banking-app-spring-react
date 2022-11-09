package com.ahlaoujwtspring.pro.web;


import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.ahlaoujwtspring.pro.exception.AccountAlreadyExist;
import com.ahlaoujwtspring.pro.exception.AccountnameEmpty;
import com.ahlaoujwtspring.pro.exception.AccounttypeEmpty;

import com.ahlaoujwtspring.pro.model.Account;

import com.ahlaoujwtspring.pro.repository.AccountRepository;



@RestController
public class AccountController {
	@Autowired 
	private AccountRepository accountRepo; // the interface
	
	 @CrossOrigin(origins = "http://localhost:3000")
		@PostMapping("/addAccount")
	    public ResponseEntity <?> creatAccount(@RequestBody Account account ) throws URISyntaxException  {
		 //System.out.println(account.getAccountname());
				if (account.getAccountname() == "") {
					throw new AccountnameEmpty();
				}
		
				if (account.getAccounttype().equals("--Please choose an option--")) {
					throw new AccounttypeEmpty();
				}
				
				
			  
			  Account accountexist = accountRepo.findByaccountname(account.getAccountname());
			
			if (accountexist != null) {
				throw new AccountAlreadyExist();
			}
			
			account.setBalance(0.00);
			
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); 
			String formatDateTime = now.format(format);
			account.setCreated_at(formatDateTime);
			  Random rand = new Random();
			    String account_number = "";
			    for (int i = 0; i < 16; i++)
			    {
			        int n = rand.nextInt(10) + 0;
			        account_number += Integer.toString(n);
			    }
			   
			account.setAccountnumber(account_number);
			
			Account savedAccount = accountRepo.save(account);
			
	       return ResponseEntity.ok().body(savedAccount);
	    }
	 
	 @CrossOrigin(origins = "http://localhost:3000")
		@GetMapping("/allAccounts")
	    public ResponseEntity <?> allAccounts()   {
		 //System.out.println(account.getAccountname());
				List<Account> listAccounts = accountRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
			
	       return ResponseEntity.ok().body(listAccounts);
	    }
	 
	 @CrossOrigin(origins = "http://localhost:3000")
		@GetMapping("/getAccount/{id}")
		public  ResponseEntity<?>  getAccount(@PathVariable Long id){
			Account foundAccount = accountRepo.findById(id).orElseThrow(RuntimeException::new);
			
			return ResponseEntity.ok(foundAccount);
			
		}
	 
	 @CrossOrigin(origins = "http://localhost:3000")
		@GetMapping("/totalBalance")
		public  ResponseEntity<?>  getTotalBalance(){
		
				List<Account> allAccounts = accountRepo.findAll();
				String pattern = "###,###.###";
				Locale locale  = new Locale("en", "US");
				DecimalFormat df = (DecimalFormat)NumberFormat.getNumberInstance(locale);
				df.applyPattern(pattern);
				//DecimalFormat df = new DecimalFormat("0.00");
				Double total = 0.00;
				for (int i=0 ; i< allAccounts.size() ; i++) {
					
					total = total + allAccounts.get(i).getBalance();
					
					
				}
				
				
			

			return ResponseEntity.ok(df.format(total));
			
		}
	
	

}
