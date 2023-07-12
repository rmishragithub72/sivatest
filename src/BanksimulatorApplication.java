package com.example.banksimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BanksimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanksimulatorApplication.class, args);
	}

}
