package com.cantest.liveBet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LiveBetApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiveBetApplication.class, args);
	}

}
