package com.ahlaoujwtspring.pro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice  //annotation to handle exceptions globally

public class DepositControllerExceprion {
	
	@ExceptionHandler(value = AccountNumberEmpty.class)
	   public ResponseEntity<Object> exception(AccountNumberEmpty exception) {
	      return new ResponseEntity<>("Account Number Should Not Be Empty!", HttpStatus.BAD_REQUEST);
	   }
	
	@ExceptionHandler(value = AccountNoExist.class)
	   public ResponseEntity<Object> exception(AccountNoExist exception) {
	      return new ResponseEntity<>("This Account doesn't Exist!", HttpStatus.BAD_REQUEST);
	   }
	
	
	@ExceptionHandler(value = WrongAccountNumber.class)
	   public ResponseEntity<Object> exception(WrongAccountNumber exception) {
	      return new ResponseEntity<>("Wrong Account Number!", HttpStatus.BAD_REQUEST);
	   }
	
	@ExceptionHandler(value = AccountNotEnoughBalance.class)
	   public ResponseEntity<Object> exception(AccountNotEnoughBalance exception) {
	      return new ResponseEntity<>("Not Enought Balance!", HttpStatus.BAD_REQUEST);
	   }

}
