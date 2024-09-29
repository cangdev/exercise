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
import com.ing.broker.app.repository.CustomerOrderRepository;
import com.ing.broker.app.repository.CustomerRepository;
import com.ing.broker.app.repository.TransactionRepository;

@Service
public class BrokerageService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerOrderRepository orderRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // Create a new order for a customer
    public CustomerOrder createOrder(Long customerId, String asset, OrderSide side, int size, double price) {
    	
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerId));
        
        double amount = size * price;
        
        //OrderSide Control
        if (side == OrderSide.BUY) {
        	// Withdraw money if the side is BUY
            withdrawMoney(customerId, amount, null); 	
        } else if (side == OrderSide.SELL) {
        	// Deposit money if the side is SELL
            depositMoney(customerId, amount); 			
        }
        
        CustomerOrder order = new CustomerOrder(null, customer, asset, side, size, price, OrderStatus.PENDING, LocalDateTime.now());
        
        return orderRepository.save(order);
    }

    // List all orders for a specific customer
    public List<CustomerOrder> listOrders(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    // List all orders for a specific customer by Date
    public List<CustomerOrder> listOrdersByDate(Long customerId, LocalDateTime startDate, LocalDateTime endDate) {
    	return orderRepository.findByCustomerIdAndOrderDateBetween(customerId, startDate, endDate);
    }

    // Cancel a pending order
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
    public Transaction depositMoney(Long customerId, double amount) {
    	
    	// Check: If amount is negative, throw an error.
        if (amount < 0) {
            throw new IllegalArgumentException("Withdraw amount cannot be negative: " + amount);
        }
    	
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerId));
        customer.setBalance(customer.getBalance() + amount);
        customerRepository.save(customer);
        
        return transactionRepository.save(new Transaction(null, customer, TransactionType.DEPOSIT, amount, null));
        
    }

    // Withdraw money from a customer's account
    public Transaction withdrawMoney(Long customerId, double amount, String iban) {
    	
    	// Check: If amount is negative, throw an error. Otherwise, balance will be increased.
        if (amount < 0) {
            throw new IllegalArgumentException("Withdraw amount cannot be negative: " + amount);
        }
    	
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerId));
        
        // Check: If insufficient balance, throw an error
        if (customer.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance for customer ID: " + customerId);
        }
        
        customer.setBalance(customer.getBalance() - amount);
        customerRepository.save(customer);
        
        return transactionRepository.save(new Transaction(null, customer, TransactionType.WITHDRAW, amount, iban));
    }

    // List all assets (transactions) for a specific customer
    public List<Transaction> listAssets(Long customerId) {
        return transactionRepository.findByCustomerId(customerId);
    }


}
