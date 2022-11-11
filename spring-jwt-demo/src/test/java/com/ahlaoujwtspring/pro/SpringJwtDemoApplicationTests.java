package com.ahlaoujwtspring.pro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.ahlaoujwtspring.pro.model.Account;
import com.ahlaoujwtspring.pro.repository.AccountRepository;
import com.ahlaoujwtspring.pro.web.AccountController;


@RunWith(SpringRunner.class)//used to enable spring boot features like @Autowired and @MockBean
@SpringBootTest
class SpringJwtDemoApplicationTests {
	
	@Autowired
	
	private AccountController accountcontroller;
	
	@Autowired 
	@MockBean//to create and inject a mock for the AccountRepository , this mock will create a bean of the same type as the application context
	private AccountRepository accountRepo;

	@Test
	public void createAccountTest() throws URISyntaxException { 
		Account account = new Account(45L, "55555", "yassine", "check", 0.00, "mardi" );
		when(accountRepo.save(account)).thenReturn(account);
		assertNotEquals(account.getAccountnumber(), accountcontroller.creatAccount(account).getAccountnumber());
	}

}
