package com.ahlaoujwtspring.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ahlaoujwtspring.pro.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
	
	public Payment findByreference(String reference); 
	

}
