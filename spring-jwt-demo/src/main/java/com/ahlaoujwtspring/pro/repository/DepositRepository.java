package com.ahlaoujwtspring.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahlaoujwtspring.pro.model.Deposit;

public interface DepositRepository extends JpaRepository <Deposit, Long> {
	

}
