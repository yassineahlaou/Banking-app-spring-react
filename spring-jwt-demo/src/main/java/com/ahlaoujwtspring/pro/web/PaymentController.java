package com.ahlaoujwtspring.pro.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.ahlaoujwtspring.pro.exception.AccountnameEmpty;
import com.ahlaoujwtspring.pro.exception.AccounttypeEmpty;
import com.ahlaoujwtspring.pro.exception.AmountEmpty;
import com.ahlaoujwtspring.pro.exception.BenefactorAccountEmpty;
import com.ahlaoujwtspring.pro.exception.BenefactorNameEmpty;
import com.ahlaoujwtspring.pro.exception.BenefactorNoExist;
import com.ahlaoujwtspring.pro.exception.BeneficiaryAccountEmpty;
import com.ahlaoujwtspring.pro.exception.BeneficiaryNameEmpty;
import com.ahlaoujwtspring.pro.exception.BeneficiaryNoExist;
import com.ahlaoujwtspring.pro.exception.MotifEmpty;
import com.ahlaoujwtspring.pro.exception.NotEnoughBalance;
import com.ahlaoujwtspring.pro.exception.WrongBenefactorNumber;
import com.ahlaoujwtspring.pro.exception.WrongBeneficiaryNumber;
import com.ahlaoujwtspring.pro.model.Account;
import com.ahlaoujwtspring.pro.model.Payment;
import com.ahlaoujwtspring.pro.repository.AccountRepository;
import com.ahlaoujwtspring.pro.repository.PaymentRepository;
import com.ahlaoujwtspring.pro.service.InvoiceService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;



@RestController
public class PaymentController {
	
	@Autowired 
	private PaymentRepository paymentRepo; // the interface
	
	@Autowired 
	private AccountRepository accountRepo;
	
	@Autowired 
	private InvoiceService invoiceService;
	
	 @CrossOrigin(origins = "http://localhost:3000")
	 @PostMapping("/makePayment")
	 public void makePayment(@RequestBody Payment payment, HttpServletResponse response ) throws URISyntaxException, IOException, JRException  {
		 //.equals not allowed if input is empty cause it will throw .equal not accepting null
		 	if (payment.getBenefactor_name() == null) {
				throw new BenefactorNameEmpty();
			}
			if (payment.getBenefactor_account()== null) {
				throw new BenefactorAccountEmpty();
			}
		 
			if (payment.getBeneficiary_name() == null) {
				throw new BeneficiaryNameEmpty();
			}
			if (payment.getBeneficiary_account() == null) {
				throw new BeneficiaryAccountEmpty();
			}
			
			if (payment.getMotif()== null) {
				throw new MotifEmpty();
			}
			if (payment.getAmount() == null) {
				throw new AmountEmpty();
			}
			
			Account benefactor = accountRepo.findByaccountname(payment.getBenefactor_name());
			
			if (benefactor == null) {
				throw new BenefactorNoExist();
			}
			else {
				if (!benefactor.getAccountnumber().equals(payment.getBenefactor_account())) {
					throw new WrongBenefactorNumber();
				}
				if (benefactor.getBalance() < payment.getAmount()) {
					throw new NotEnoughBalance();
				}
			}
			Account beneficiary = accountRepo.findByaccountname(payment.getBeneficiary_name());
			
			if (beneficiary == null) {
				throw new BeneficiaryNoExist();
			}
			else {
				if (!beneficiary.getAccountnumber().equals(payment.getBeneficiary_account())) {
					throw new WrongBeneficiaryNumber();
				}
			}
			
			//format payment date
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); 
			String formatDateTime = now.format(format);
			payment.setCreated_at(formatDateTime);
			
			//assign a random unique reference number
			Random rand = new Random();
		    String reference_number = "REF";
		    for (int i = 0; i < 12; i++)
		    {
		        int n = rand.nextInt(10) + 0;
		        reference_number += Integer.toString(n);
		    }
		    reference_number += "PAY";
			payment.setReference(reference_number);
			//set status of payment
			payment.setStatus("Success");
			//set message payment
			payment.setMessage("Payment validated");
			
			
			//update balance of both sides after operation
			Account benefactor_update = accountRepo.findByaccountname(payment.getBenefactor_name());
			Double balanceBenefactor_update = benefactor_update.getBalance() - payment.getAmount();
			benefactor_update.setBalance(balanceBenefactor_update);
			
			Account beneficiary_update = accountRepo.findByaccountname(payment.getBeneficiary_name());
			Double balanceBeneficiary_update = beneficiary_update.getBalance() + payment.getAmount();
			beneficiary_update.setBalance(balanceBeneficiary_update);
			
			//save payment
			paymentRepo.save(payment);
			
			//generate report payment
			JasperPrint invoicePdf = null;
			Payment foundPayment = paymentRepo.findByreference(payment.getReference());
			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition", String.format("attachment; filename=transaction-statement.pdf"));
			response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
			OutputStream out = response.getOutputStream();
			invoicePdf =  invoiceService.generateInvoiceFor(foundPayment);
			JasperExportManager.exportReportToPdfStream(invoicePdf, out);
			
	 }
	 
	 @CrossOrigin(origins = "http://localhost:3000")
	 @GetMapping("/allPayments")
     public List<Payment> allPayments()   {
		List<Payment> listPayments = paymentRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		return listPayments;
		//return ResponseEntity.ok().body(listPayments);
	 }
	 
	 @CrossOrigin(origins = "http://localhost:3000")
	 @GetMapping("/getPayment/{reference}")
	 public  Payment getPayment(@PathVariable String reference){
		Payment foundPayment = paymentRepo.findByreference(reference);
		return foundPayment;
		//return ResponseEntity.ok(foundPayment);
	 }
	 
	 @CrossOrigin(origins = "http://localhost:3000")
	 @GetMapping("/getPayment/{startdate}/{enddate}")
	public  ResponseEntity <?> getPaymentWithDate(@PathVariable String startdate, @PathVariable String enddate ){
	 
	 List<Payment> listPayments = new ArrayList<>();
	 paymentRepo.findAll(Sort.by(Sort.Direction.DESC, "id")).forEach(item->{
		 
		 try {
			String [] parts = item.getCreated_at().split(" ");
			//convert string to date with parse method
			Date createdDate = new SimpleDateFormat("dd-MM-yyyy").parse(parts[0].toString()); 
			Date startInput = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
			Date endInput = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
			//check if created date is in the intervall
			if (createdDate.compareTo(startInput) >= 0) {
				 if (endInput.compareTo(createdDate) >=0) {
					 listPayments.add(item);
				 }    
	        }
		 } catch (ParseException e) {
				e.printStackTrace();
		 }
	 });//end forEach
	 return ResponseEntity.ok(listPayments);
		
	}
	 
	 //get operation report
 	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/getInvoice/{id}")
    public void getPayment(@PathVariable Long id, HttpServletResponse response) throws IOException, JRException   {
 		JasperPrint invoicePdf = null;
		Payment foundPayment = paymentRepo.findById(id).orElseThrow(RuntimeException::new);
		response.setContentType("application/x-download");
		response.setHeader("Content-Disposition", String.format("attachment; filename=transaction-statement.pdf"));response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
		OutputStream out = response.getOutputStream();
		invoicePdf =  invoiceService.generateInvoiceFor(foundPayment);
		JasperExportManager.exportReportToPdfStream(invoicePdf, out);
 	}
}
