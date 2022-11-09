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

import com.ahlaoujwtspring.pro.exception.AmountEmpty;
import com.ahlaoujwtspring.pro.exception.BenefactorAccountEmpty;
import com.ahlaoujwtspring.pro.exception.BenefactorNameEmpty;
import com.ahlaoujwtspring.pro.exception.BenefactorNoExist;
import com.ahlaoujwtspring.pro.exception.BeneficiaryAccountEmpty;
import com.ahlaoujwtspring.pro.exception.BeneficiaryNameEmpty;
import com.ahlaoujwtspring.pro.exception.BeneficiaryNoExist;
import com.ahlaoujwtspring.pro.exception.MotifEmpty;
import com.ahlaoujwtspring.pro.exception.NotEnoughBalance;
import com.ahlaoujwtspring.pro.exception.RecieverAccountEmpty;
import com.ahlaoujwtspring.pro.exception.RecieverNameEmpty;
import com.ahlaoujwtspring.pro.exception.RecieverNoExist;
import com.ahlaoujwtspring.pro.exception.SenderAccountEmpty;
import com.ahlaoujwtspring.pro.exception.SenderNameEmpty;
import com.ahlaoujwtspring.pro.exception.SenderNoExist;
import com.ahlaoujwtspring.pro.exception.WrongBenefactorNumber;
import com.ahlaoujwtspring.pro.exception.WrongBeneficiaryNumber;
import com.ahlaoujwtspring.pro.exception.WrongRecieverNumber;
import com.ahlaoujwtspring.pro.exception.WrongSenderNumber;
import com.ahlaoujwtspring.pro.model.Account;
import com.ahlaoujwtspring.pro.model.Payment;
import com.ahlaoujwtspring.pro.model.Transfert;
import com.ahlaoujwtspring.pro.repository.AccountRepository;

import com.ahlaoujwtspring.pro.repository.TransfertRepository;

@RestController
public class TransfertController {
	
	@Autowired 
	private TransfertRepository transfertRepo; // the interface
	
	@Autowired 
	private AccountRepository accountRepo;
	
	 @CrossOrigin(origins = "http://localhost:3000")
		@PostMapping("/makeTransfert")
	    public ResponseEntity<?> makeTransfert(@RequestBody Transfert transfert ) throws URISyntaxException  {
		 
		 //.equals not allowed if input is empty cause it will throw .equal not accepting null
		 
		 
		 if (transfert.getSender_name() == null) {
				throw new SenderNameEmpty();
			}
			if (transfert.getSender_account() == null) {
				throw new SenderAccountEmpty();
			}
			
		 if (transfert.getReciever_name() == null) {
				throw new RecieverNameEmpty();
			}
			if (transfert.getReciever_account()== null) {
				throw new RecieverAccountEmpty();
			}
		
			
			if (transfert.getAmount() == null) {
				throw new AmountEmpty();
				
			}
			
			Account sender = accountRepo.findByaccountname(transfert.getSender_name());
			
			if (sender == null) {
				throw new SenderNoExist();
			}
			else {
				if (!sender.getAccountnumber().equals(transfert.getSender_account())) {
					throw new WrongSenderNumber();
				}
				if (sender.getBalance() < transfert.getAmount()) {
					throw new NotEnoughBalance();
					
				}
				
				
			}
			Account reciever = accountRepo.findByaccountname(transfert.getReciever_name());
			
			if (reciever == null) {
				throw new RecieverNoExist();
			}
			else {
				if (!reciever.getAccountnumber().equals(transfert.getReciever_account())) {
					throw new WrongRecieverNumber();
				}
			}
			
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); 
			String formatDateTime = now.format(format);
			transfert.setCreated_at(formatDateTime);
			  Random rand = new Random();
			    String reference_number = "REF";
			    for (int i = 0; i < 12; i++)
			    {
			        int n = rand.nextInt(10) + 0;
			        reference_number += Integer.toString(n);
			    }
			    reference_number += "TRANS";
			   
			transfert.setReference(reference_number);
			
			transfert.setStatus("Success");
			transfert.setMessage("Transfert validated");
			
			Account sender_update = accountRepo.findByaccountname(transfert.getSender_name());
			
			Double balanceSender_update = sender_update.getBalance() - transfert.getAmount();
			
			sender_update.setBalance(balanceSender_update);
			
			Account reciever_update = accountRepo.findByaccountname(transfert.getReciever_name());
			
			Double balanceReciever_update = reciever_update.getBalance() + transfert.getAmount();
			
			reciever_update.setBalance(balanceReciever_update);
			
			
			
			Transfert savedTransfert = transfertRepo.save(transfert);
			
			
			
	
		
		 	
		 
		 return ResponseEntity.ok().body(savedTransfert);
	 }

}
