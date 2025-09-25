package com.messageconverter.kfmi;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinancialMessageConverterApplication {

	public static void main(String[] args) {
		System.out.println(UUID.randomUUID());
		SpringApplication.run(FinancialMessageConverterApplication.class, args);
	}

}
