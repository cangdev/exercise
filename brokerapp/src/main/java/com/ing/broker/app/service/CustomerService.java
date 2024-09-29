package com.ing.broker.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing.broker.app.entity.Customer;
import com.ing.broker.app.repository.CustomerRepository;
import com.ing.broker.app.request.CustomerRequest;

@Service
public class CustomerService {
	
	@Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(CustomerRequest customerRequest) {
    	
    	Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setBalance(customerRequest.getBalance());
        return customerRepository.save(customer);
        
    }
    

    public Customer getCustomerById(Long customerId) {
    	
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerId));
        
    }

}
