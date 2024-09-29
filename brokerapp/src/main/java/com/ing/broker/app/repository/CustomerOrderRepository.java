package com.ing.broker.app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ing.broker.app.entity.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
	
    List<CustomerOrder> findByCustomerId(Long customerId);
    
    List<CustomerOrder> findByCustomerIdAndOrderDateBetween(Long customerId, LocalDateTime startDate, LocalDateTime endDate);

    
}
