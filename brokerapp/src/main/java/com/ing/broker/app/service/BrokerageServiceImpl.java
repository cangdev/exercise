package com.ing.broker.app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing.broker.app.entity.Customer;
import com.ing.broker.app.entity.CustomerOrder;
import com.ing.broker.app.entity.OrderSide;
import com.ing.broker.app.entity.OrderStatus;
import com.ing.broker.app.entity.Transaction;
import com.ing.broker.app.entity.TransactionType;
import com.ing.broker.app.exception.CustomerNotFoundException;
import com.ing.broker.app.repository.CustomerOrderRepository;
import com.ing.broker.app.repository.CustomerRepository;
import com.ing.broker.app.repository.TransactionRepository;

@Service
public class BrokerageServiceImpl implements BrokerageService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerOrderRepository orderRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // Create a new order for a customer
    @Override
    public CustomerOrder createOrder(Long customerId, String asset, OrderSide side, int size, double price) {
    	
        Customer customer = findCustomerById(customerId);
        
        double amount = size * price;
        
        //OrderSide Control
        if (side.equals(OrderSide.BUY)) {
            executeBuyOrder(customerId, amount); 	
        } else if (side.equals(OrderSide.SELL)) {
        	executeSellOrder(customerId, amount); 			
        }
        
        CustomerOrder order = new CustomerOrder(null, customer, asset, side, size, price, OrderStatus.PENDING, LocalDateTime.now());
        
        return orderRepository.save(order);
    }

    // List all orders for a specific customer
    @Override
    public List<CustomerOrder> listOrders(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    // List all orders for a specific customer by Date
    @Override
    public List<CustomerOrder> listOrdersByDate(Long customerId, LocalDateTime startDate, LocalDateTime endDate) {
    	return orderRepository.findByCustomerIdAndOrderDateBetween(customerId, startDate, endDate);
    }

    // Cancel a pending order
    @Override
    public void cancelOrder(Long orderId) {
        
    	CustomerOrder order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));
        
    	if (order.getStatus().equals(OrderStatus.PENDING)) {
            order.setStatus(OrderStatus.CANCELED);
            orderRepository.save(order);
            
            double amount = order.getSize() * order.getPrice();
            depositMoney(order.getCustomer().getId(), amount);
            
        } else {
            throw new IllegalStateException("Only pending orders can be canceled");
        }
    	
    }

    // Deposit money for a customer
    @Override
    public Transaction depositMoney(Long customerId, double amount) {
    	
    	// Check: If amount is negative, throw an error.
        if (amount < 0) {
            throw new IllegalArgumentException("Withdraw amount cannot be negative: " + amount);
        }
    	
        Customer customer = findCustomerById(customerId);
        customer.setBalance(customer.getBalance() + amount);
        customerRepository.save(customer);
        
        return transactionRepository.save(new Transaction(null, customer, TransactionType.DEPOSIT, amount, null));
        
    }

    // Withdraw money from a customer's account
    @Override
    public Transaction withdrawMoney(Long customerId, double amount, String iban) {
    	
    	// Check: If amount is negative, throw an error. Otherwise, balance will be increased.
        if (amount < 0) {
            throw new IllegalArgumentException("Withdraw amount cannot be negative: " + amount);
        }
        
        // Check: If iban is valid format.
		if (!iban.matches("^[A-Z]{2}\\d{2}[A-Z0-9]{1,30}$")) {
		    throw new IllegalArgumentException("Invalid IBAN format. iban" + iban);
		}
    	
        Customer customer = findCustomerById(customerId);
        
        // Check: If insufficient balance, throw an error
        if (customer.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance for customer ID: " + customerId);
        }
        
        customer.setBalance(customer.getBalance() - amount);
        customerRepository.save(customer);
        
        return transactionRepository.save(new Transaction(null, customer, TransactionType.WITHDRAW, amount, iban));
    }

    // List all assets (transactions) for a specific customer
    @Override
    public List<Transaction> listAssets(Long customerId) {
    	return transactionRepository.findByCustomerId(customerId);
    }
    
    // Buy order. If request is valid, update customer's balance.
    private Customer executeBuyOrder(Long customerId, double amount) {
    	
    	// Check: If amount is negative, throw an error. Otherwise, balance will be increased.
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative: " + amount);
        }
        
        Customer customer = findCustomerById(customerId);
        
        // Check: If insufficient balance, throw an error
        if (customer.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance for customer ID: " + customerId);
        }
        
        customer.setBalance(customer.getBalance() - amount);
        return customerRepository.save(customer);
        
    }
    
    // Sell order. If request is valid, update customer's balance.
    private Customer executeSellOrder(Long customerId, double amount) {
    	
    	// Check: If amount is negative, throw an error.
        if (amount < 0) {
            throw new IllegalArgumentException("Withdraw amount cannot be negative: " + amount);
        }
    	
        Customer customer = findCustomerById(customerId);
        customer.setBalance(customer.getBalance() + amount);
        return customerRepository.save(customer);
        
    }

    
    private Customer findCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + customerId));
    }


}
