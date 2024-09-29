package com.ing.broker.app.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ing.broker.app.entity.CustomerOrder;
import com.ing.broker.app.entity.OrderSide;
import com.ing.broker.app.entity.Transaction;

public interface BrokerageService {
	
	public CustomerOrder createOrder(Long customerId, String asset, OrderSide side, int size, double price);
	
	public List<CustomerOrder> listOrders(Long customerId);
	
	public List<CustomerOrder> listOrdersByDate(Long customerId, LocalDateTime startDate, LocalDateTime endDate);
	
	public void cancelOrder(Long orderId);
	
	public Transaction depositMoney(Long customerId, double amount);
	
	public Transaction withdrawMoney(Long customerId, double amount, String iban);
	
	public List<Transaction> listAssets(Long customerId);
	

}
