package com.ing.broker.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ing.broker.app.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findByCustomerId(Long customerId);

}
