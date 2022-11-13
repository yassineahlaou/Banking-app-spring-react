package com.ahlaoujwtspring.pro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.ahlaoujwtspring.pro.exception.AccountnameEmpty;
import com.ahlaoujwtspring.pro.model.Account;
import com.ahlaoujwtspring.pro.model.Payment;
import com.ahlaoujwtspring.pro.repository.AccountRepository;
import com.ahlaoujwtspring.pro.repository.PaymentRepository;
import com.ahlaoujwtspring.pro.web.AccountController;
import com.ahlaoujwtspring.pro.web.PaymentController;

import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)//used to enable spring boot features like @Autowired and @MockBean
@SpringBootTest
class SpringJwtDemoApplicationTests {
	
	@Autowired
	
	private AccountController accountcontroller;
	
	
	
	@Autowired 
	@MockBean//to create and inject a mock for the AccountRepository , this mock will create a bean of the same type as the application context
	private AccountRepository accountRepo;
	
	@Autowired 
	@MockBean
	private PaymentRepository paymentRepo; // the interface
	
	@Autowired
	
	private PaymentController paymentcontroller;
	
	

	@Test
	public void createAccountTest1() throws URISyntaxException { 
		Account account = new Account(45L, "55555", "yassine", "check", 0.00, "mardi" );
		given(accountRepo.save(account)).willReturn(account);
		//when(accountRepo.save(account)).thenReturn(account);
		assertEquals(account.getAccountname(), accountcontroller.creatAccount(account).getAccountname());
	}
	
	@Test
	public void createAccountTest2() throws URISyntaxException { 
		Account account = new Account(45L, "5555", "", "check", 0.00, "mardi" );
		given(accountRepo.save(account)).willReturn(account);
		
		//when(accountRepo.save(account)).thenReturn(account);
		assertThrows(AccountnameEmpty.class, ()->accountcontroller.creatAccount(account));
	}
	
	
	
	@Test
	
	public void allPaymentsTest() {
		List<Payment> listTestPayment = new ArrayList<Payment>();
		Payment payment1 = new Payment(2L, "Refhgdfj", "yassine", "65456454", "lahc", "45545454", "shoop","succ", "nice", 45.89, "mardi" );
		Payment payment2 = new Payment(3L, "Refhgdfj", "yassine", "65456454", "lahc", "45545454", "shoop","succ", "nice", 45.89, "mardi" );
		listTestPayment.add(payment1);
		listTestPayment.add(payment2);
		when(paymentRepo.findAll(Sort.by(Sort.Direction.DESC, "id"))).thenReturn(listTestPayment);
		assertEquals(2,  paymentcontroller.allPayments().size());
	}
	
	@Test
	
	public void getPaymentTest() {
		Payment payment1 = new Payment(2L, "Refhgdfj", "yassine", "65456454", "lahc", "45545454", "shoop","succ", "nice", 45.89, "mardi" );
		given(paymentRepo.findByreference("Refhgdfj")).willReturn(payment1);
		assertTrue(payment1 == paymentcontroller.getPayment("Refhgdfj"));
	}
	
	
	
	

}
