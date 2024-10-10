package com.ing.broker.app;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ing.broker.app.controller.CustomerController;
import com.ing.broker.app.entity.Customer;
import com.ing.broker.app.request.CustomerRequest;
import com.ing.broker.app.service.CustomerService;

@SpringBootTest
public class BrokerApplicationTests {

	@InjectMocks
	private CustomerController customerController;

	@Mock
	private CustomerService customerService;

	@Test
	public void testCreateCustomer() throws Exception {
		
		// Create a Request to create a customer (sample CustomerRequest)
		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setName("AHMET DURSUN");
		customerRequest.setBalance(1903);

		// Create a mock customer that the service will return
		Customer mockCustomer = new Customer();
		mockCustomer.setId(1L); // Setting an ID to simulate a created customer
		mockCustomer.setName(customerRequest.getName());
		mockCustomer.setBalance(customerRequest.getBalance());

		// When: Mock the behavior of customerService.createCustomer
		when(customerService.createCustomer(customerRequest)).thenReturn(mockCustomer);

		// Act: Call the createCustomer method in the controller
		ResponseEntity<Customer> responseEntity = customerController.createCustomer(customerRequest);

		// Then: Verify the response
		assertNotNull(responseEntity);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isEqualTo(mockCustomer);

		// Optional: Print the response for debugging
		System.out.println("createdCustomer: " + mockCustomer.toString());
		System.out.println("responseEntity.getBody(): " + responseEntity.getBody());

		// Further assertions
		assertEquals("AHMET DURSUN", responseEntity.getBody().getName());
		assertEquals(1903, responseEntity.getBody().getBalance());
		
	}

}
