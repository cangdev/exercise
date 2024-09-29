package com.ing.broker.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ing.broker.app.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
}
