package com.ing.broker.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ing.broker.app.entity.CustomerOrder;
import com.ing.broker.app.entity.OrderSide;
import com.ing.broker.app.entity.Transaction;
import com.ing.broker.app.request.CustomerOrderRequest;
import com.ing.broker.app.request.DepositMoneyRequest;
import com.ing.broker.app.request.WithdrawMoneyRequest;
import com.ing.broker.app.service.BrokerageService;

@RestController
@RequestMapping("/api/admin/brokerage")
public class AdminBrokerageController {
	
	@Autowired
	private BrokerageService brokerageService;

	// Endpoint to create a new order
	@PostMapping("/order")
	public ResponseEntity<CustomerOrder> createOrder(@RequestBody CustomerOrderRequest customerOrderRequest) {

		System.out.println("New CustomerOrder Request. customerOrderDto: " + customerOrderRequest.toString());
		CustomerOrder order = brokerageService.createOrder(customerOrderRequest.getCustomerId(),
															customerOrderRequest.getAsset(),
															OrderSide.valueOf(customerOrderRequest.getSide().toUpperCase()),
															customerOrderRequest.getSize(),
															customerOrderRequest.getPrice()
														);

		return ResponseEntity.ok(order);

	}

	// Endpoint to list orders for a customer
	@GetMapping("/orders")
	public ResponseEntity<List<CustomerOrder>> listOrders(@RequestParam Long customerId) {

		System.out.println("New CustomerOrder request. customerId:" + customerId);
		List<CustomerOrder> orders = brokerageService.listOrders(customerId);

		return ResponseEntity.ok(orders);

	}

	// Endpoint to list orders for a customer by Date
	@GetMapping("/ordersByDate")
	public ResponseEntity<List<CustomerOrder>> listOrdersByDate(@RequestParam Long customerId,
																@RequestParam LocalDateTime startDate, 
																@RequestParam LocalDateTime endDate) {
		
		System.out.println("New CustomerOrder request. customerId:" + customerId + " startDate:" + startDate + " endDate:" + endDate);
		List<CustomerOrder> orders = brokerageService.listOrdersByDate(customerId, startDate, endDate);
		
		return ResponseEntity.ok(orders);
		
	}

	// Endpoint to cancel an order
	@DeleteMapping("/order/{id}")
	public ResponseEntity<String> cancelOrder(@PathVariable Long id) {

		System.out.println("New CancelOrder request. orderID:" + id);
		brokerageService.cancelOrder(id);
		
		return ResponseEntity.ok(id + " Deleted.");
	}

	// Endpoint to deposit money for a customer
	@PostMapping("/deposit")
	public ResponseEntity<Transaction> depositMoney(@RequestBody DepositMoneyRequest depositMoneyRequest) {

		Transaction transaction = brokerageService.depositMoney(depositMoneyRequest.getCustomerId(), depositMoneyRequest.getAmount());
		
		return ResponseEntity.ok(transaction);

	}

	// Endpoint to withdraw money from a customer's account
	@PostMapping("/withdraw")
	public ResponseEntity<Transaction> withdrawMoney(@RequestBody WithdrawMoneyRequest withdrawMoneyRequest) {
		
		if (!withdrawMoneyRequest.getIban().matches("^[A-Z]{2}\\d{2}[A-Z0-9]{1,30}$")) {
		    throw new IllegalArgumentException("Invalid IBAN format");
		}

		Transaction transaction = brokerageService.withdrawMoney(withdrawMoneyRequest.getCustomerId(), withdrawMoneyRequest.getAmount(), withdrawMoneyRequest.getIban());

		return ResponseEntity.ok(transaction);

	}

	// Endpoint to list all assets for a given customer
	@GetMapping("/assets")
	public ResponseEntity<List<Transaction>> listAssets(@RequestParam Long customerId) {

		List<Transaction> transactions = brokerageService.listAssets(customerId);

		return ResponseEntity.ok(transactions);

	}

}
