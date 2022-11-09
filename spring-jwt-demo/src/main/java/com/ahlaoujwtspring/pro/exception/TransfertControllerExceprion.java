package com.ahlaoujwtspring.pro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice  //annotation to handle exceptions globally
public class TransfertControllerExceprion {
	
	@ExceptionHandler(value = RecieverNameEmpty.class)
	   public ResponseEntity<Object> exception(RecieverNameEmpty exception) {
	      return new ResponseEntity<>("Reciever Name Should Not Be Empty!", HttpStatus.BAD_REQUEST);
	   }
	
	@ExceptionHandler(value = RecieverAccountEmpty.class)
	   public ResponseEntity<Object> exception(RecieverAccountEmpty exception) {
	      return new ResponseEntity<>("Reciever Account Number Should Not Be Empty!", HttpStatus.BAD_REQUEST);
	   }
	
	@ExceptionHandler(value = SenderNameEmpty.class)
	   public ResponseEntity<Object> exception(SenderNameEmpty exception) {
	      return new ResponseEntity<>("Sender Name Should Not Be Empty!", HttpStatus.BAD_REQUEST);
	   }
	@ExceptionHandler(value = SenderAccountEmpty.class)
	   public ResponseEntity<Object> exception(SenderAccountEmpty exception) {
	      return new ResponseEntity<>("Sender Account Number Should Not Be Empty!", HttpStatus.BAD_REQUEST);
	   }
	
	
	
	@ExceptionHandler(value = AmountEmpty.class)
	   public ResponseEntity<Object> exception(AmountEmpty exception) {
	      return new ResponseEntity<>("Amount Should Not Be Empty!", HttpStatus.BAD_REQUEST);
	   }
	
	@ExceptionHandler(value = SenderNoExist.class)
	   public ResponseEntity<Object> exception(SenderNoExist exception) {
	      return new ResponseEntity<>("This Sender Don't Exist!", HttpStatus.BAD_REQUEST);
	   }
	@ExceptionHandler(value = NotEnoughBalance.class)
	   public ResponseEntity<Object> exception(NotEnoughBalance exception) {
	      return new ResponseEntity<>("This Benefactor Doesn't have enough balance!", HttpStatus.BAD_REQUEST);
	   }
	@ExceptionHandler(value = WrongSenderNumber.class)
	   public ResponseEntity<Object> exception(WrongSenderNumber exception) {
	      return new ResponseEntity<>(" Wrong Sender Account Number!", HttpStatus.BAD_REQUEST);
	   }
	@ExceptionHandler(value = RecieverNoExist.class)
	   public ResponseEntity<Object> exception(RecieverNoExist exception) {
	      return new ResponseEntity<>(" This Reciever doesn't exist!", HttpStatus.BAD_REQUEST);
	   }
	@ExceptionHandler(value = WrongRecieverNumber.class)
	   public ResponseEntity<Object> exception(WrongRecieverNumber exception) {
	      return new ResponseEntity<>("Wrong Reciever Account Number", HttpStatus.BAD_REQUEST);
	   }

	
	

}
