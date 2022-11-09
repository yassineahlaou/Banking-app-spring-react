package com.ahlaoujwtspring.pro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice  //annotation to handle exceptions globally
public class PaymentControllerExceprion {
	@ExceptionHandler(value = BeneficiaryNameEmpty.class)
	   public ResponseEntity<Object> exception(BeneficiaryNameEmpty exception) {
	      return new ResponseEntity<>("Beneficiary Name Should Not Be Empty!", HttpStatus.BAD_REQUEST);
	   }
	@ExceptionHandler(value = BeneficiaryAccountEmpty.class)
	   public ResponseEntity<Object> exception(BeneficiaryAccountEmpty exception) {
	      return new ResponseEntity<>("Beneficiary Account Number Should Not Be Empty!", HttpStatus.BAD_REQUEST);
	   }
	
	@ExceptionHandler(value = BenefactorNameEmpty.class)
	   public ResponseEntity<Object> exception(BenefactorNameEmpty exception) {
	      return new ResponseEntity<>("Benefactor Name Should Not Be Empty!", HttpStatus.BAD_REQUEST);
	   }
	@ExceptionHandler(value = BenefactorAccountEmpty.class)
	   public ResponseEntity<Object> exception(BenefactorAccountEmpty exception) {
	      return new ResponseEntity<>("Benefactor Account Number Should Not Be Empty!", HttpStatus.BAD_REQUEST);
	   }
	
	@ExceptionHandler(value = MotifEmpty.class)
	   public ResponseEntity<Object> exception(MotifEmpty exception) {
	      return new ResponseEntity<>("Motif Should Not Be Empty!", HttpStatus.BAD_REQUEST);
	   }
	
	@ExceptionHandler(value = AmountEmpty.class)
	   public ResponseEntity<Object> exception(AmountEmpty exception) {
	      return new ResponseEntity<>("Amount Should Not Be Empty!", HttpStatus.BAD_REQUEST);
	   }
	
	@ExceptionHandler(value = BenefactorNoExist.class)
	   public ResponseEntity<Object> exception(BenefactorNoExist exception) {
	      return new ResponseEntity<>("This Benefactor Don't Exist!", HttpStatus.BAD_REQUEST);
	   }
	@ExceptionHandler(value = NotEnoughBalance.class)
	   public ResponseEntity<Object> exception(NotEnoughBalance exception) {
	      return new ResponseEntity<>("This Benefactor Doesn't have enough balance!", HttpStatus.BAD_REQUEST);
	   }
	@ExceptionHandler(value = WrongBenefactorNumber.class)
	   public ResponseEntity<Object> exception(WrongBenefactorNumber exception) {
	      return new ResponseEntity<>(" Wrong Benefactor Account Number!", HttpStatus.BAD_REQUEST);
	   }
	@ExceptionHandler(value = BeneficiaryNoExist.class)
	   public ResponseEntity<Object> exception(BeneficiaryNoExist exception) {
	      return new ResponseEntity<>(" This Beneficiary doesn't exist!", HttpStatus.BAD_REQUEST);
	   }
	@ExceptionHandler(value = WrongBeneficiaryNumber.class)
	   public ResponseEntity<Object> exception(WrongBeneficiaryNumber exception) {
	      return new ResponseEntity<>("Wrong Beneficiary Account Number", HttpStatus.BAD_REQUEST);
	   }

}
