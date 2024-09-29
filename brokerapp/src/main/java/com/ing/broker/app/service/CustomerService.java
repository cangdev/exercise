package com.ing.broker.app.service;

import com.ing.broker.app.entity.Customer;
import com.ing.broker.app.request.CustomerRequest;

public interface CustomerService {
	
	public Customer createCustomer(CustomerRequest customerRequest);

    public Customer getCustomerById(Long customerId);

}
