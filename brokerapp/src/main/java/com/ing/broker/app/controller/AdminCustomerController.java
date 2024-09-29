package com.ing.broker.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ing.broker.app.entity.Customer;
import com.ing.broker.app.request.CustomerRequest;
import com.ing.broker.app.service.CustomerService;

@RestController
@RequestMapping("/api/admin/customers")
public class AdminCustomerController {

	@Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerRequest customerRequest) {
        Customer createdCustomer = customerService.createCustomer(customerRequest);
        return ResponseEntity.ok(createdCustomer);
    }

    @GetMapping
    public ResponseEntity<Customer> getCustomer(@RequestParam Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }

}

