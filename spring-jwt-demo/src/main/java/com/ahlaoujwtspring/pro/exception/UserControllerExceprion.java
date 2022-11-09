package com.ahlaoujwtspring.pro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice  //annotation to handle exceptions globally
public class UserControllerExceprion {
	
	
	@ExceptionHandler(value = EmailAlreadyExist.class)
	   public ResponseEntity<Object> exception(EmailAlreadyExist exception) {
	      return new ResponseEntity<>("This Email Already Exist", HttpStatus.BAD_REQUEST);
	   }
	
	@ExceptionHandler(value = EmailNoExist.class)
	   public ResponseEntity<Object> exception(EmailNoExist exception) {
	      return new ResponseEntity<>("This Email Does't Exist", HttpStatus.BAD_REQUEST);
	   }
	
	
	@ExceptionHandler(value = PasswordWrong.class)
	   public ResponseEntity<Object> exception(PasswordWrong exception) {
	      return new ResponseEntity<>("Wrong Password", HttpStatus.BAD_REQUEST);
	   }
	
	@ExceptionHandler(value = UserDisabled.class)
	   public ResponseEntity<Object> exception(UserDisabled exception) {
	      return new ResponseEntity<>("Check your email to verify your registration", HttpStatus.BAD_REQUEST);
	   }
	
	
	
	@ExceptionHandler(value = UserNoExist.class)
	   public ResponseEntity<Object> exception(UserNoExist exception) {
	      return new ResponseEntity<>("This User doesn't exist", HttpStatus.BAD_REQUEST);
	   }
	
	@ExceptionHandler(value = EmailEmpty.class)
	   public ResponseEntity<Object> exception(EmailEmpty exception) {
	      return new ResponseEntity<>("Email Should not be empty", HttpStatus.BAD_REQUEST);
	   }
	@ExceptionHandler(value = PasswordEmpty.class)
	   public ResponseEntity<Object> exception(PasswordEmpty exception) {
	      return new ResponseEntity<>("Password should not be empty", HttpStatus.BAD_REQUEST);
	   }
	@ExceptionHandler(value = FirstnameEmpty.class)
	   public ResponseEntity<Object> exception(FirstnameEmpty exception) {
	      return new ResponseEntity<>("FirstName should not be empty", HttpStatus.BAD_REQUEST);
	   }
	@ExceptionHandler(value = LastnameEmpty.class)
	   public ResponseEntity<Object> exception(LastnameEmpty exception) {
	      return new ResponseEntity<>("LastName should not be empty", HttpStatus.BAD_REQUEST);
	   }
	@ExceptionHandler(value = PhoneEmpty.class)
	   public ResponseEntity<Object> exception(PhoneEmpty exception) {
	      return new ResponseEntity<>("Phone should not be empty", HttpStatus.BAD_REQUEST);
	   }
	@ExceptionHandler(value = EmailNotValid.class)
	   public ResponseEntity<Object> exception(EmailNotValid exception) {
	      return new ResponseEntity<>("Email is not valid!", HttpStatus.BAD_REQUEST);
	   }
	@ExceptionHandler(value = AccountnameEmpty.class)
	   public ResponseEntity<Object> exception(AccountnameEmpty exception) {
	      return new ResponseEntity<>("Account Name Should not be Empty!", HttpStatus.BAD_REQUEST);
	   }
	
	@ExceptionHandler(value = AccounttypeEmpty.class)
	   public ResponseEntity<Object> exception(AccounttypeEmpty exception) {
	      return new ResponseEntity<>("Select Account Type!", HttpStatus.BAD_REQUEST);
	   }
	
	@ExceptionHandler(value = AccountAlreadyExist.class)
	   public ResponseEntity<Object> exception(AccountAlreadyExist exception) {
	      return new ResponseEntity<>("This Account Already Exist!", HttpStatus.BAD_REQUEST);
	   }
	
	
	

}
