package com.cantest.liveBet.controller;

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

import com.cantest.liveBet.model.Bulletin;
import com.cantest.liveBet.model.SportType;
import com.cantest.liveBet.request.BulletinRequest;
import com.cantest.liveBet.service.BulletinService;

@SpringBootTest
public class BulletinControllerTest {
	
	@InjectMocks
	private BulletinController bulletinController;

	@Mock
	private BulletinService bulletinService;
	
	
	@Test
    public void testCreateBulletin() throws Exception {
		
		// Create a Request to create a bulletin (sample Bulletin)
		BulletinRequest bulletinRequest = new BulletinRequest();
		bulletinRequest.setName("17-20 Ekim Bulteni");
		bulletinRequest.setSportType("FOOTBALL");

		// Create a mock bulletin that the service will return
		Bulletin mockBulletin = Bulletin.builder()
				.id(1L)									// Setting an ID to simulate a created bulletin
				.name("17-20 Ekim Bulteni")
				.sportType(SportType.FOOTBALL).build();

		// When: Mock the behavior of bulletinService.addBulletin
		when(bulletinService.addBulletin(bulletinRequest)).thenReturn(mockBulletin);

		// Act: Call the createCustomer method in the controller
		ResponseEntity<Bulletin> responseEntity = bulletinController.addBulletin(bulletinRequest);

		// Then: Verify the response
		assertNotNull(responseEntity);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(responseEntity.getBody()).isEqualTo(mockBulletin);

		// Print the response for debugging
		System.out.println("createdBulletin: " + mockBulletin.toString());
		System.out.println("responseEntity.getBody(): " + responseEntity.getBody());

		// Further assertions
		assertEquals("17-20 Ekim Bulteni", responseEntity.getBody().getName());
		
	}

}
