package com.ahlaoujwtspring.pro.web;


import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ahlaoujwtspring.pro.exception.EmailAlreadyExist;
import com.ahlaoujwtspring.pro.exception.EmailEmpty;
import com.ahlaoujwtspring.pro.exception.EmailNotValid;
import com.ahlaoujwtspring.pro.exception.FirstnameEmpty;
import com.ahlaoujwtspring.pro.exception.LastnameEmpty;
import com.ahlaoujwtspring.pro.exception.PasswordEmpty;
import com.ahlaoujwtspring.pro.exception.PhoneEmpty;
import com.ahlaoujwtspring.pro.model.JwtRequest;
import com.ahlaoujwtspring.pro.repository.UserRepository;

import net.bytebuddy.utility.RandomString;

@RestController
public class RegisterController {
	
	@Autowired 
	private UserRepository userRepo; // the interface
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private JavaMailSender mailSender;
	  
	  
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/register")
    public ResponseEntity <?> createUser(@RequestBody JwtRequest user , HttpServletRequest request) throws URISyntaxException ,UnsupportedEncodingException, MessagingException  {
			if (user.getEmail() == null) {
				throw new EmailEmpty();
			}
			if (user.getFirstname() == null) {
				throw new FirstnameEmpty();
			}
			if (user.getLastname() == null) {
				throw new LastnameEmpty();
			}
			if (user.getPhone() == null) {
				throw new PhoneEmpty();
			}
			if (user.getPassword() == null) {
				throw new PasswordEmpty();
			}
			
			//validate email format
			String expressions = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
			Pattern pattern = Pattern.compile(expressions);
			Matcher matcher = pattern.matcher(user.getEmail());
			
			if (!matcher.matches()) {
				throw new EmailNotValid();
			}
			
			//check if email already exist
			JwtRequest emailexist = userRepo.findByemail(user.getEmail());
			if (emailexist != null) {
				throw new EmailAlreadyExist();
			}
		
		    //encrypt password
			user.setPassword(bcryptEncoder.encode(user.getPassword()));
			
			//set a random verification code for the new account
		
			String randomCode = RandomString.make(64);
		    user.setVerificationCode(randomCode);
		    user.setEnabled(false);
		    
		    //save user
			JwtRequest savedUser = userRepo.save(user);
			//send email for verif
			sendVerificationEmail(user, getSiteURL(request));
	 
			return ResponseEntity.ok().body(savedUser);
    }
	 //this method is unusfull because we added manuelly the redirect url in email due to backend will forward request
	//from backend to frontend with have a different port (different URL
	private String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
	}
	
	 
	private void sendVerificationEmail(JwtRequest user, String siteURL) throws MessagingException, UnsupportedEncodingException {
		//design the email
		String toAddress = user.getEmail();
	    String fromAddress = "AhHLAOUBank@gmail.com";
	    String senderName = "AHLAOU Bank";
	    String subject = "Please verify your registration";
	    String content = "<div style='text-align:center; background-color: rgb(240, 240, 240);display:flex;align-items: center;flex-direction: column ; gap:7px ; padding-top:7px; padding-bottom:7px'>"
	    		
	    		+"<img src='http://localhost:3000/assets/logomail.png' alt=''>"
	    		+"<p>Dear [[name]],</p>"
	            + "<p>Please click below to verify your registration:</p>"
	            + "<a style='text-decoration:none;' href=\"[[URL]]\" target=\"_self\"><button style='margin-top: 10px; padding-right:7px; padding-left:7px;height: 45px; font-size: 11px;text-transform: uppercase;letter-spacing: 2.5px;font-weight: 700;color: #000; background-color: #ff871e; border: none;border-radius: 45px;box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.1);  transition: all 0.3s ease 0s;  cursor: pointer;'>Complete Your Registration</button> </a>"
	            
	            + "<h1>Ahlaou Bank</h1>"
	            + "</div>";
	     //MIME message means “Multipurpose Internet Mail Extensions”. With MIME messages you can send HTML texts, and attach files (text, audio, images, etc)
	    MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	    helper.setFrom(fromAddress, senderName);
	    helper.setTo(toAddress);
	    helper.setSubject(subject);
	    content = content.replace("[[name]]", user.getFirstname() + ' ' + user.getLastname());
	    String verifyURL = "http://localhost:3000" + "/login?code=" + user.getVerificationCode();
	    content = content.replace("[[URL]]", verifyURL);
	    helper.setText(content, true);
	     
	    mailSender.send(message);
	}
}
