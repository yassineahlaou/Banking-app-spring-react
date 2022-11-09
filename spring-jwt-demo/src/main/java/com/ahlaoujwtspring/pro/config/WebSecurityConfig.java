package com.ahlaoujwtspring.pro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	
	 @Bean
	  public DaoAuthenticationProvider authenticationProvider() {
		
	     DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	       
	      authProvider.setUserDetailsService(jwtUserDetailsService);
	      authProvider.setPasswordEncoder(passwordEncoder());
	   
	      return authProvider;
	  }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	
	@Bean
	  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
	    return authConfiguration.getAuthenticationManager();
	  }

	
	  @Bean
	  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.cors().and().csrf().disable()
	        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	        .authorizeRequests().antMatchers("/authenticate").permitAll()
	        .antMatchers("/welcome").permitAll()
	        .antMatchers("/register").permitAll()
	        .antMatchers("/leave").permitAll()
	        .antMatchers("/validate").permitAll()
	        .antMatchers("/addAccount").permitAll()
	        .antMatchers("/allAccounts").permitAll()
	        .antMatchers("/getAccount/{id}").permitAll()
	        .antMatchers("/totalBalance").permitAll()
	        .antMatchers("/makePayment").permitAll()
	        .antMatchers("/makeTransfert").permitAll()
	        .antMatchers("/makeDeposit").permitAll()
	        .antMatchers("/makeWithdraw").permitAll()	
	        .antMatchers("/allPayments").permitAll()
	        .antMatchers("/getPayment/{reference}").permitAll()
	        .antMatchers("/getPayment/{startdate}/{enddate}").permitAll()
	        .antMatchers("/getInvoice/{id}").permitAll()
	       
	        
	        .anyRequest().authenticated();
	        
	        
	    
	 

// Add a filter to validate the tokens with every request
    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	    
	    // http....;
	    
	    return http.build();
	  }
}
